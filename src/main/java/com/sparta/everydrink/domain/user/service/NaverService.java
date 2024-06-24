package com.sparta.everydrink.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.everydrink.domain.user.entity.NaverUserInfoDto;
import com.sparta.everydrink.domain.user.entity.User;
import com.sparta.everydrink.domain.user.entity.UserRoleEnum;
import com.sparta.everydrink.domain.user.entity.UserStatusEnum;
import com.sparta.everydrink.domain.user.repository.UserRepository;
import com.sparta.everydrink.security.jwt.JwtService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j(topic = "NAVER Login")
@Service
@RequiredArgsConstructor
public class NaverService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final JwtService jwtService;

    public String[] naverLogin(String code, String state) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getToken(code, state);

        // 2. 토큰으로 네이버 API 호출 : "액세스 토큰"으로 "네이버 사용자 정보" 가져오기
        NaverUserInfoDto naverUserInfo = getNaverUserInfo(accessToken);

        // 3. 필요시에 회원가입
        User naverUser = registerNaverUserIfNeeded(naverUserInfo);

        // 4. JWT 토큰 반환
        String createToken = jwtService.createToken(naverUser.getUsername());
        String refreshToken = jwtService.createRefreshToken(naverUser.getUsername());

        String[] Token = {createToken, refreshToken};

        return Token;
    }

    private String getToken(String code, String state) throws JsonProcessingException {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://nid.naver.com")
                .path("/oauth2.0/token")
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", "n9mYiy5KOBAkpIQWmbVj")
                .queryParam("client_secret", "bYWoGhLWAB")
                .queryParam("code", code)
                .queryParam("state", state)
                .encode()
                .build()
                .toUri();

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        log.info(String.valueOf(jsonNode));
        return jsonNode.get("access_token").asText();


    }

    private NaverUserInfoDto getNaverUserInfo(String accessToken) throws JsonProcessingException {
        // 요청 URL 만들기
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/nid/me")
                .encode()
                .build()
                .toUri();

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity
                .post(uri)
                .headers(headers)
                .body(new LinkedMultiValueMap<>());

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(
                requestEntity,
                String.class
        );

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        Long id = jsonNode.get("response").get("id").asLong();
        String nickname = jsonNode.get("response").get("nickname").asText();

        log.info("네이버 사용자 정보: " + id + ", " + nickname);
        return new NaverUserInfoDto(id, nickname);
    }

    private User registerNaverUserIfNeeded(NaverUserInfoDto naverUserInfo) {
        // DB 에 중복된 naver Id 가 있는지 확인
        Long naverId = naverUserInfo.getId();
        String naverUsername = String.valueOf(naverId);
        User naverUser = userRepository.findByUsername(naverUsername).orElse(null);

        if (naverUser == null) {
            // 신규 회원가입
            // password: random UUID
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);

            naverUser = new User(naverUsername, naverUserInfo.getNickname(), encodedPassword,
                    UserRoleEnum.USER, UserStatusEnum.ACTIVE);

            userRepository.save(naverUser);
        }
        return naverUser;
    }


}