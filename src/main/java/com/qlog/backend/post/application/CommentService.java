package com.qlog.backend.post.application;

import com.qlog.backend.global.security.SecurityUtil;
import com.qlog.backend.post.domain.model.Comment;
import com.qlog.backend.post.domain.model.Post;
import com.qlog.backend.post.domain.repository.CommentRepository;
import com.qlog.backend.post.domain.repository.PostRepository;
import com.qlog.backend.post.exception.NotFoundCommentException;
import com.qlog.backend.post.exception.NotFoundPostException;
import com.qlog.backend.post.presentation.dto.request.CommentCreateRequest;
import com.qlog.backend.post.presentation.dto.request.CommentUpdateRequest;
import com.qlog.backend.user.domain.model.Profile;
import com.qlog.backend.user.domain.model.User;
import com.qlog.backend.user.domain.repository.ProfileRepository;
import com.qlog.backend.user.domain.repository.UserRepository;
import com.qlog.backend.user.exception.NotFoundProfileException;
import com.qlog.backend.user.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    //댓글 생성
    @Transactional
    public void createComment(Long postId, CommentCreateRequest req) {
        UUID userId = SecurityUtil.getCurrentUserId();
        Profile authorProfile = findProfileByUserId(userId);
        Post post = findPostById(postId);

        post.addComment(authorProfile, req.getContent());

        postRepository.save(post);
    }

    //댓글 수정
    @Transactional
    public void updateComment(Long commentId, CommentUpdateRequest request) {
        UUID currentUserId = SecurityUtil.getCurrentUserId();
        User currentUser = findUserById(currentUserId);
        Comment comment = findCommentById(commentId);

        if (!comment.getProfile().getUser().getId().equals(currentUserId) && !currentUser.isAdmin()) {
            throw new SecurityException("댓글 수정 권한이 없습니다.");
        }

        comment.updateComment(request.getContent());
    }

    //댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        UUID currentUserId = SecurityUtil.getCurrentUserId();
        User currentUser = findUserById(currentUserId);
        Comment comment = findCommentById(commentId);

        if (!comment.getProfile().getUser().getId().equals(currentUserId) && !currentUser.isAdmin()) {
            throw new SecurityException("댓글 삭제 권한이 없습니다.");
        }

        comment.deleteComment();
    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new NotFoundPostException(postId + " ID에 해당하는 게시글을 찾을 수 없습니다.")
        );
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundCommentException(commentId + " ID에 해당하는 댓글을 찾을 수 없습니다.")
        );
    }

    private Profile findProfileByUserId(UUID userId) {
        return profileRepository.findByUser_Id(userId).orElseThrow(
                () -> new NotFoundProfileException("해당 사용자의 프로필을 찾을 수 없습니다.")
        );
    }

    private User findUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundUserException(userId + " 해당 회원을 찾을 수 없습니다.")
        );
    }
}
