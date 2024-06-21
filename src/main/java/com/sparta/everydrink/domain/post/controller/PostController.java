package com.sparta.everydrink.domain.post.controller;


import com.sparta.everydrink.domain.common.CommonResponseDto;
import com.sparta.everydrink.domain.post.dto.PostPageRequestDto;
import com.sparta.everydrink.domain.post.dto.PostPageResponseDto;
import com.sparta.everydrink.domain.post.dto.PostRequestDto;
import com.sparta.everydrink.domain.post.dto.PostResponseDto;
import com.sparta.everydrink.domain.post.repository.PostRepository;
import com.sparta.everydrink.domain.post.service.PostService;
import com.sparta.everydrink.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;
    //private final CommentService commentService;
    private final PostRepository postRepository;

    // 게시물 등록
    @PostMapping
    public ResponseEntity<CommonResponseDto<PostResponseDto>> addPost(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody PostRequestDto postRequestDto
    ) {
        PostResponseDto postResponseDto = postService.addPost(postRequestDto, userDetails.getUser());
        return ResponseEntity.ok()
                .body(CommonResponseDto.<PostResponseDto>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("게시물 등록 성공")
                        .data(postResponseDto)
                        .build());
    }

    // 게시물 전체 조회
    @GetMapping
    public ResponseEntity<CommonResponseDto<Object>> findAll() {
        List<PostResponseDto> posts = postService.findAll();

        return ResponseEntity.ok()
                .body(CommonResponseDto.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("게시물 전체 조회 성공")
                        .data(posts)
                        .build());
    }

    //    // 게시물 단일 조회 + 해당 게시물에 달린 댓글 전체 조회
//    @GetMapping("/{postId}")
//    public ResponseEntity<CommonResponseDto<PostWithCommentsResponseDto>> findById(@PathVariable(name = "postId") long id) {
//        // 게시물 단일 조회
//        PostResponseDto post = postService.findById(id);
//
//        // 해당 게시물에 달린 댓글 전체 조회
//        List<CommentResponseDto> comments = commentService.findAllCommentsByPostId(id);
//
//        // Post와 Comments를 하나의 객체로 병합
//        PostWithCommentsResponseDto postWithCommentsResponse = new PostWithCommentsResponseDto(post, comments);
//
//        return ResponseEntity.ok()
//                .body(CommonResponseDto.<PostWithCommentsResponseDto>builder()
//                        .statusCode(HttpStatus.OK.value())
//                        .message("게시물 단일 조회, 댓글 조회 성공")
//                        .data(postWithCommentsResponse)
//                        .build());
//    }

    @GetMapping("/{postId}")
    public ResponseEntity<CommonResponseDto<PostResponseDto>> findById(@PathVariable(name = "postId") long id) {
        // 게시물 단일 조회
        PostResponseDto post = postService.findById(id);

        return ResponseEntity.ok()
                .body(CommonResponseDto.<PostResponseDto>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("게시물 단일 조회, 댓글 조회 성공")
                        .data(post)
                        .build());
    }

    //게시물 수정
    @PutMapping("/{postId}")
    public ResponseEntity<CommonResponseDto<PostResponseDto>> updatePost(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody PostRequestDto postRequestDto,
            @PathVariable("postId") Long postId
    ) {
        PostResponseDto postResponseDto = postService.updatePost(postRequestDto, postId, userDetails.getUser());
        return ResponseEntity.ok()
                .body(CommonResponseDto.<PostResponseDto>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("게시물 수정 성공")
                        .data(postResponseDto)
                        .build());
    }

    //게시물 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<CommonResponseDto<PostResponseDto>> deletePost(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @PathVariable("postId") Long postId
    ) {
        postService.deletePost(postId, userDetails.getUser());
        return ResponseEntity.ok()
                .body(CommonResponseDto.<PostResponseDto>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("게시물 삭제 성공")
                        .build());
    }

    //페이지네이션
    @PostMapping("/page")
    public ResponseEntity<CommonResponseDto<Page<PostPageResponseDto>>> getPostPage(@Valid @RequestBody PostPageRequestDto requestDto) {
        Page<PostPageResponseDto> page = postService.getPostPage(requestDto);
        return ResponseEntity.ok()
                .body(CommonResponseDto.<Page<PostPageResponseDto>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("게시물 페이지 조회 성공")
                        .data(page)
                        .build());
    }
}
