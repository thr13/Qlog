document.addEventListener('DOMContentLoaded', () => {
    const commentForm = document.getElementById('commentForm');

    if (commentForm) {
        commentForm.addEventListener('submit', function (e) {
            e.preventDefault();

            const postId = this.getAttribute('data-post-id');
            const content = this.elements.content.value;

            if (!content || content.trim() === '') {
                alert('댓글 내용을 입력해주세요.');
                return;
            }

            fetch('/api/posts/' + postId + '/comments', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({content: content})
            })
                .then(response => {
                    if (response.ok) {
                        alert('댓글 작성에 성공했습니다.');
                        window.location.reload();
                    } else {
                        alert('댓글 작성에 실패했습니다.');
                    }
                });
        });
    }
});