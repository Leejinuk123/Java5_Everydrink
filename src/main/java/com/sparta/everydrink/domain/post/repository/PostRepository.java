package com.sparta.everydrink.domain.post.repository;

import com.sparta.everydrink.domain.post.dto.PostPageResponseDto;
import com.sparta.everydrink.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select p.id as postId, u.id as userId, p.title, p.content, u.name, " +
            "p.created_at as createdAt, p.updated_at as updatedAt, " +
            "IF(c.count is null, 0, c.count) as likeCount " +
            "from (select id, count(*) as count " +
            "from (select a.id, a.title from post a " +
            "left join liked b on a.id = b.contents_id " +
            "where b.content_type = 'POST') a " +
            "group by a.id) c " +
            "right join post p on c.id = p.id " +
            "left join user u on u.id = p.user_id " +
            "where p.created_at between :startDate AND :endDate ", nativeQuery = true)
    Page<PostPageResponseDto> findPostPages(@Param("startDate") String startDate,
                                            @Param("endDate") String endDate,
                                            Pageable pageable);
}
