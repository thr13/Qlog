-- 사용자
CREATE TABLE users
(
    id         BINARY(16) NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    created_at DATETIME     NOT NULL DEFAULT NOW(),
    is_admin   TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);
-- select uuid();

-- 프로필
CREATE TABLE profiles
(
    id       BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    user_id  BINARY(16) NOT NULL UNIQUE,
    nickname VARCHAR(255) NOT NULL UNIQUE,
    gender   ENUM('M', 'F') NOT NULL,
    name     VARCHAR(30) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_users_TO_profiles FOREIGN KEY (user_id) REFERENCES users (id)
);

-- 카테고리
CREATE TABLE categories
(
    id   BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

-- 게시글
CREATE TABLE posts
(
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    profile_id BIGINT UNSIGNED NOT NULL,
    title      VARCHAR(50) NOT NULL,
    content    TEXT        NOT NULL,
    view       INT UNSIGNED NOT NULL DEFAULT 0,
    is_deleted TINYINT(1) NOT NULL DEFAULT 0,
    created_at DATETIME    NOT NULL DEFAULT NOW(),
    updated_at DATETIME    NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    PRIMARY KEY (id),
    CONSTRAINT FK_profiles_TO_posts FOREIGN KEY (profile_id) REFERENCES profiles (id)
);

-- 댓글
CREATE TABLE comments
(
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    post_id    BIGINT UNSIGNED NOT NULL,
    profile_id BIGINT UNSIGNED NOT NULL,
    content    TEXT     NOT NULL,
    is_deleted TINYINT(1) NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT NOW(),
    updated_at DATETIME NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    PRIMARY KEY (id),
    CONSTRAINT FK_posts_TO_comments FOREIGN KEY (post_id) REFERENCES posts (id),
    CONSTRAINT FK_profiles_TO_comments FOREIGN KEY (profile_id) REFERENCES profiles (id)
);

-- 파일
CREATE TABLE files
(
    id         BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    post_id    BIGINT UNSIGNED NOT NULL,
    name       VARCHAR(255) NOT NULL,
    path       VARCHAR(512) NOT NULL,
    size       BIGINT UNSIGNED NOT NULL,
    type       VARCHAR(50) NULL,
    is_deleted TINYINT(1) NOT NULL DEFAULT 0,
    created_at DATETIME     NOT NULL DEFAULT NOW(),
    updated_at DATETIME     NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    PRIMARY KEY (id),
    CONSTRAINT FK_posts_TO_files FOREIGN KEY (post_id) REFERENCES posts (id)
);

-- 카테고리_게시글
CREATE TABLE category_post
(
    category_id BIGINT UNSIGNED NOT NULL,
    post_id     BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (category_id, post_id),
    CONSTRAINT FK_categories_TO_category_post FOREIGN KEY (category_id) REFERENCES categories (id),
    CONSTRAINT FK_posts_TO_category_post FOREIGN KEY (post_id) REFERENCES posts (id)
);

-- 게시글 추천
CREATE TABLE post_like
(
    profile_id BIGINT UNSIGNED NOT NULL,
    post_id    BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (profile_id, post_id),
    CONSTRAINT FK_profiles_TO_post_like FOREIGN KEY (profile_id) REFERENCES profiles (id),
    CONSTRAINT FK_posts_TO_post_like FOREIGN KEY (post_id) REFERENCES posts (id)
);

-- 댓글 추천
CREATE TABLE comment_like
(
    profile_id BIGINT UNSIGNED NOT NULL,
    comment_id BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (profile_id, comment_id),
    CONSTRAINT FK_profiles_TO_comment_like FOREIGN KEY (profile_id) REFERENCES profiles (id),
    CONSTRAINT FK_comments_TO_comment_like FOREIGN KEY (comment_id) REFERENCES comments (id)
);