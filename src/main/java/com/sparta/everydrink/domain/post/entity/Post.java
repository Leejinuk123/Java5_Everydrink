package com.sparta.everydrink.domain.post.entity;

import com.sparta.everydrink.domain.common.TimeStampEntity;
import com.sparta.everydrink.domain.post.dto.PostRequestDto;
import com.sparta.everydrink.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
// Todo 배포시 여기 @Setter 삭제
@Setter
@Entity
@NoArgsConstructor
public class Post extends TimeStampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Setter
    private Long likeCount;

//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Comment> comments = new ArrayList<>();

//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<PostMedia> postMedias = new ArrayList<>();

    public Post(PostRequestDto postRequestDto, User user) {
        this.user = user;
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.likeCount = 0L;
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
    }

//    public void setTestDateTime() {
//        super.setDateTimeInit();
//    }
}
