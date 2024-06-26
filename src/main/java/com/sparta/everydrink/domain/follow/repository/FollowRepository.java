package com.sparta.everydrink.domain.follow.repository;

import com.sparta.everydrink.domain.follow.entity.Follow;
import com.sparta.everydrink.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFromUserAndToUser(User fromUser, User toUser);

    List<Follow> findByFromUser(User currentUser);
}
