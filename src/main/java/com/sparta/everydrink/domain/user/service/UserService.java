package com.sparta.everydrink.domain.user.service;

import com.sparta.everydrink.domain.user.dto.UserRequestDto;
import com.sparta.everydrink.domain.user.dto.UserSignupRequestDto;
import com.sparta.everydrink.domain.user.entity.User;
import com.sparta.everydrink.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void signUp(UserSignupRequestDto requestDto) {
        User user = new User(requestDto);
        userRepository.save(user);
    }

}
