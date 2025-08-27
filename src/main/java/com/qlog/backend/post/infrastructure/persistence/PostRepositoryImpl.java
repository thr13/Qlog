package com.qlog.backend.post.infrastructure.persistence;

import com.qlog.backend.post.domain.model.Post;
import com.qlog.backend.post.domain.repository.PostQueryRepository;
import com.qlog.backend.post.presentation.dto.request.PostSearchRequest;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

import static com.qlog.backend.post.domain.model.QPost.post;

@Repository
public class PostRepositoryImpl implements PostQueryRepository {

    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<Post> searchPosts(PostSearchRequest req, Pageable pageable) {
        List<Post> content = queryFactory
                .selectFrom(post)
                .where(
                        textSearch(req.getSearch()),
                        categorySearch(req.getCategoryId()),
                        userSearch(req.getUserId()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.createdAt.desc())
                .fetch();

        long totalCount = queryFactory
                .select(post.count())
                .from(post)
                .where(
                        textSearch(req.getSearch()),
                        categorySearch(req.getCategoryId()),
                        userSearch(req.getUserId())
                )
                .fetchOne();
        return PageableExecutionUtils.getPage(content, pageable, () -> totalCount);
    }

    /**
     * 게시글 제목, 내용에 검색어가 포함되는지 확인
     *
     * @param text 검색어
     * @return 검색어가 포함되는지 여부
     */
    private BooleanExpression textSearch(String text) {
        return StringUtils.hasText(text) ? post.title.containsIgnoreCase(text).or(post.content.containsIgnoreCase(text)) : null;
    }

    /**
     * 게시글에 카테고리 ID 에 해당하는 카테고리가 있는지 검사
     *
     * @param categoryId 카테고리 고유 식별키
     * @return 카테고리가 포함되는지 여부
     */
    private BooleanExpression categorySearch(Long categoryId) {
        return categoryId != null ? post.categories.any().id.eq(categoryId) : null;
    }

    /**
     * 특정 사용자가 게시글을 작성했는지 검사
     *
     * @param userId 사용자 고유 식별키
     * @return 사용자가 작성했는지 여부
     */
    private BooleanExpression userSearch(UUID userId) {
        return userId != null ? post.profile.user.id.eq(userId) : null;
    }
}
