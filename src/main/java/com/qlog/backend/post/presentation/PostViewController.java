package com.qlog.backend.post.presentation;

import com.qlog.backend.post.application.CategoryService;
import com.qlog.backend.post.application.PostService;
import com.qlog.backend.post.presentation.dto.request.PostCreateRequest;
import com.qlog.backend.post.presentation.dto.response.CommentResponse;
import com.qlog.backend.post.presentation.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    private final CategoryService categoryService;

    /**
     * 게시글 목록 조회 페이지
     * localhost:/8080/posts
     *
     * @param pageable 페이지 정보
     * @param model    postList
     * @return resources/templates/post/list.html
     */
    @GetMapping
    public String postList(@PageableDefault(sort = "createdAt") Pageable pageable, Model model) {
        Page<PostResponse> postList = postService.getPostList(pageable);
        model.addAttribute("postList", postList);

        return "post/list";
    }

    /**
     * 게시글 상세 조회 페이지
     * localhost:8080/posts/{postId}
     *
     * @param postId 게시글 ID
     * @param model  post
     * @return resources/templates/post/detail.html
     */
    @GetMapping("/{postId}")
    public String postDetail(@PathVariable Long postId, @PageableDefault(sort = "createdAt") Pageable pageable, Model model) {
        PostResponse post = postService.getPostById(postId);
        Page<CommentResponse> comment = postService.getCommentsByPost(postId, pageable);

        model.addAttribute("post", post);
        model.addAttribute("comment", comment);

        return "post/detail";
    }

    /**
     * 게시글 작성 페이지
     *
     * @param model postCreateRequest, categories
     * @return resources/templates/post/form.html
     */
    @GetMapping("/new")
    public String postCreateForm(Model model) {
        model.addAttribute("postCreateRequest", new PostCreateRequest());
        model.addAttribute("categories", categoryService.getAllCategories());

        return "post/form";
    }

    /**
     * 게시글 수정
     *
     * @param postId 게시글 ID
     * @param model  post, categories
     * @return resources/templates/post/form.html
     */
    @GetMapping("/{postId}/edit")
    public String postUpdateForm(@PathVariable Long postId, Model model) {
        PostResponse post = postService.getPostById(postId);
        model.addAttribute("post", post);
        model.addAttribute("categories", categoryService.getAllCategories());

        return "post/form";
    }
}
