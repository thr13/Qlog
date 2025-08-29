package com.qlog.backend.post.presentation;

import com.qlog.backend.post.application.PostService;
import com.qlog.backend.post.presentation.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostViewController {

    private final PostService postService;

    /**
     * 게시글 상세 조회 페이지
     * localhost:8080/posts/{postId}
     * @param postId 게시글 ID
     * @param model html 에 사용할 데이터
     * @return resources/templates/post/detail.html
     */
    @GetMapping("/{postId}")
    public String postDetail(@PathVariable Long postId, Model model) {
        PostResponse post = postService.getPostById(postId);
        model.addAttribute("post", post);

        return "post/detail";
    }
}
