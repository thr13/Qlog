package com.qlog.backend.post.application;

import com.qlog.backend.global.security.SecurityUtil;
import com.qlog.backend.post.domain.model.*;
import com.qlog.backend.post.domain.repository.CommentLikeRepository;
import com.qlog.backend.post.domain.repository.CommentRepository;
import com.qlog.backend.post.domain.repository.PostLikeRepository;
import com.qlog.backend.post.domain.repository.PostRepository;
import com.qlog.backend.post.exception.DuplicateCommentLikeException;
import com.qlog.backend.post.exception.DuplicatePostLikeException;
import com.qlog.backend.post.exception.NotFoundCommentException;
import com.qlog.backend.post.exception.NotFoundPostException;
import com.qlog.backend.user.domain.model.Profile;
import com.qlog.backend.user.domain.repository.ProfileRepository;
import com.qlog.backend.user.exception.NotFoundProfileException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ProfileRepository profileRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

    //게시글 좋아요 추가
    @Transactional
    public void likePost(Long postId) {
        UUID userId = SecurityUtil.getCurrentUserId();
        Profile profile = findProfileByUserId(userId);
        Post post = findPostById(postId);

        PostLikeId postLikeId = new PostLikeId(profile.getId(), post.getId());
        if (postLikeRepository.existsById(postLikeId)) {
            throw new DuplicatePostLikeException("이미 좋아요를 누른 게시글입니다.");
        }

        postLikeRepository.save(new PostLike(profile, post));
    }

    //게시글 좋아요 취소
    @Transactional
    public void unlikePost(Long postId) {
        UUID userId = SecurityUtil.getCurrentUserId();
        Profile profile = findProfileByUserId(userId);
        PostLikeId postLikeId = new PostLikeId(profile.getId(), postId);

        postLikeRepository.deleteById(postLikeId);
    }

    //댓글 좋아요 추가
    @Transactional
    public void likeComment(Long commentId) {
        UUID userId = SecurityUtil.getCurrentUserId();
        Profile profile = findProfileByUserId(userId);
        Comment comment = findCommentById(commentId);

        CommentLikeId commentLikeId = new CommentLikeId(profile.getId(), comment.getId());
        if (commentLikeRepository.existsById(commentLikeId)) {
            throw new DuplicateCommentLikeException("이미 좋아요를 누른 댓글입니다.");
        }

        commentLikeRepository.save(new CommentLike(profile, comment));
    }

    //댓글 좋아요 취소
    @Transactional
    public void unlikeComment(Long commentId) {
        UUID userId = SecurityUtil.getCurrentUserId();
        Profile profile = findProfileByUserId(userId);
        CommentLikeId commentLikeId = new CommentLikeId(profile.getId(), commentId);

        commentLikeRepository.deleteById(commentLikeId);
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

    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundCommentException(commentId + " ID에 해당하는 댓글을 찾을 수 없습니다.")
        );
    }

}
