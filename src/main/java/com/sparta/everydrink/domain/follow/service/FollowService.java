package com.sparta.everydrink.domain.follow.service;

import com.sparta.everydrink.domain.follow.dto.FollowRequestDto;
import com.sparta.everydrink.domain.follow.dto.FollowResponseDto;
import com.sparta.everydrink.domain.follow.entity.Follow;
import com.sparta.everydrink.domain.follow.repository.FollowRepository;
import com.sparta.everydrink.domain.user.entity.User;
import com.sparta.everydrink.domain.user.repository.UserRepository;
import com.sparta.everydrink.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public FollowResponseDto followUser(FollowRequestDto followRequestDto, UserDetailsImpl user) {
        User currentUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        User targetUser = userRepository.findByUsername(followRequestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("팔로우할 사용자를 찾을 수 없습니다."));

        if (currentUser.getUsername().equals(followRequestDto.getUsername())) {
            throw new IllegalArgumentException("본인은 팔로우할 수 없습니다.");
        }

        if(followRepository.findByFromUserAndToUser(currentUser, targetUser).isPresent()) {
            throw new IllegalArgumentException("이미 팔로우한 사용자입니다.");
        }


        Follow follow = new Follow(currentUser, targetUser);
        followRepository.save(follow);

        return new FollowResponseDto(follow);

    }

    @Transactional
    public FollowResponseDto unfollowUser(FollowRequestDto followRequestDto, UserDetailsImpl user){
        User currentUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        User targetUser = userRepository.findByUsername(followRequestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("팔로우할 사용자를 찾을 수 없습니다."));

        Follow follow = followRepository.findByFromUserAndToUser(currentUser, targetUser)
                .orElseThrow(() -> new IllegalArgumentException("팔로우 관계가 존재하지 않습니다."));

        followRepository.delete(follow);

        return new FollowResponseDto(follow);
    }
}
