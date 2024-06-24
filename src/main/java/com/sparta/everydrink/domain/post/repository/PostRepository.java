package com.sparta.everydrink.domain.post.repository;

import com.sparta.everydrink.domain.post.dto.PostPageResponseDto;
import com.sparta.everydrink.domain.post.entity.Post;
import com.sparta.everydrink.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT p.id AS postId, u.id AS userId, p.title, p.content, u.username AS name, " +
            "p.created_at AS createdAt, p.modified_at AS updatedAt, " +
            "IFNULL(c.likeCount, 0) AS likeCount " +
            "FROM post p " +
            "LEFT JOIN users u ON u.id = p.user_id " +
            "LEFT JOIN ( " +
            "    SELECT l.contents_id AS postId, COUNT(l.contents_id) AS likeCount " +
            "    FROM liked l " +
            "    WHERE l.content_type = 'POST' " +
            "    GROUP BY l.contents_id " +
            ") c ON p.id = c.postId " +
            "WHERE p.created_at BETWEEN :startDate AND :endDate",
            countQuery = "SELECT COUNT(p.id) " +
                    "FROM post p " +
                    "WHERE p.created_at BETWEEN :startDate AND :endDate",
            nativeQuery = true)
    Page<PostPageResponseDto> findPostPages(@Param("startDate") String startDate,
                                            @Param("endDate") String endDate,
                                            Pageable pageable);


    List<Post> findByUserInOrderByCreatedAtDesc(List<User> followedUsers);
}
