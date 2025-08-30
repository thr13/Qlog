package com.qlog.backend.post.application;

import com.qlog.backend.global.security.SecurityUtil;
import com.qlog.backend.post.domain.model.Category;
import com.qlog.backend.post.domain.model.Post;
import com.qlog.backend.post.domain.repository.CategoryRepository;
import com.qlog.backend.post.domain.repository.CommentRepository;
import com.qlog.backend.post.domain.repository.PostRepository;
import com.qlog.backend.post.exception.NotFoundCategoryException;
import com.qlog.backend.post.exception.NotFoundPostException;
import com.qlog.backend.post.presentation.dto.request.PostCreateRequest;
import com.qlog.backend.post.presentation.dto.request.PostSearchRequest;
import com.qlog.backend.post.presentation.dto.request.PostUpdateRequest;
import com.qlog.backend.post.presentation.dto.response.CommentResponse;
import com.qlog.backend.post.presentation.dto.response.PostResponse;
import com.qlog.backend.user.domain.model.Profile;
import com.qlog.backend.user.domain.model.User;
import com.qlog.backend.user.domain.repository.ProfileRepository;
import com.qlog.backend.user.domain.repository.UserRepository;
import com.qlog.backend.user.exception.NotFoundProfileException;
import com.qlog.backend.user.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final FileService fileService;


    //게시글 작성
    @Transactional
    public PostResponse createPost(PostCreateRequest req, List<MultipartFile> files) {
        UUID userId = SecurityUtil.getCurrentUserId();
        Profile profile = findProfileByUserId(userId);
        List<Category> categories = categoryRepository.findAllById(req.getCategoryIds());

        Post post = new Post(profile, req.getTitle(), req.getContent());
        categories.forEach(post::addCategory);

        Post newPost = postRepository.save(post);

        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                fileService.storeFile(file, newPost);
            }
        }

        return PostResponse.from(post);
    }

    //전체 게시글 조회
    @Transactional(readOnly = true)
    public Page<PostResponse> getPostList(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostResponse::from);
    }

    //게시글 조회
    @Transactional
    public PostResponse getPostById(Long postId) {
        Post post = findPostById(postId);
        post.addViewCount();

        return PostResponse.from(post);
    }

    //카테고리별 게시글 목록 조회
    @Transactional(readOnly = true)
    public Page<PostResponse> getPostListByCategory(Long categoryId, Pageable pageable) {
        PostSearchRequest search = new PostSearchRequest();
        search.setCategoryId(categoryId);

        return postRepository.searchPosts(search, pageable).map(PostResponse::from);
    }

    //게시글에 소속된 댓글 조회
    @Transactional(readOnly = true)
    public Page<CommentResponse> getCommentsByPost(Long postId, Pageable pageable) {
        if (!postRepository.existsById(postId)) {
            throw new NotFoundPostException(postId + " ID에 해당하는 게시글을 찾을 수 없습니다.");
        }

        return commentRepository.findByPost_Id(postId, pageable).map(CommentResponse::from);
    }

    //게시글 수정
    @Transactional
    public void updatePost(Long postId, PostUpdateRequest req) {
        UUID currentUserId = SecurityUtil.getCurrentUserId();
        User currentUser = findUserById(currentUserId);

        Post post = findPostById(postId);

        if (!post.getProfile().getUser().getId().equals(currentUserId) && !currentUser.isAdmin()) {
            throw new SecurityException("게시글 수정 권한이 없습니다.");
        }

        post.updatePost(req.getTitle(), req.getContent());
    }

    //게시글 삭제
    @Transactional
    public void deletePost(Long postId) {
        UUID currentUserId = SecurityUtil.getCurrentUserId();
        User currentUser = findUserById(currentUserId);
        Post post = findPostById(postId);

        if (!post.getProfile().getUser().getId().equals(currentUserId) && !currentUser.isAdmin()) {
            throw new SecurityException("게시글 삭제 권한이 없습니다.");
        }

        post.deletePost();
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new NotFoundPostException(postId + " 해당 게시물을 찾을 수 없습니다.")
        );
    }

    private Profile findProfileByUserId(UUID userId) {
        return profileRepository.findByUser_Id(userId).orElseThrow(
                () -> new NotFoundProfileException(userId + " 해당 사용자의 프로필을 찾을 수 없습니다.")
        );
    }

    private Category findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundCategoryException(categoryId + " 해당 카테고리를 찾을 수 없습니다.")
        );
    }

    private User findUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundUserException(userId + " 해당 사용자를 찾을 수 없습니다")
        );
    }
}
