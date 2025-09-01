document.addEventListener('DOMContentLoaded', () => {
    const likeButtons = document.querySelectorAll('.like-btn');

    likeButtons.forEach(button => {
        button.addEventListener('click', handleLikeClick);
    });
});

async function handleLikeClick(event) {
    const button = event.currentTarget;
    const type = button.getAttribute('data-type');
    const id = button.getAttribute('data-id');
    const isLiked = button.getAttribute('data-liked') === 'true';

    const url = `/api/${type}s/${id}/like`;
    const method = isLiked ? 'DELETE' : 'POST';

    try {
        const response = await fetch(url, {
            method: method,
        });

        if (response.ok) {
            updateLikeButton(button, !isLiked);
        } else {
            const error = await response.json();
            alert(error.message || '요청에 실패했습니다.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('오류가 발생했습니다.');
    }
}

function updateLikeButton(button, isLiked) {
    const likeCountSpan = button.nextElementSibling;
    let currentCount = parseInt(likeCountSpan.textContent);

    if (isLiked) {
        button.setAttribute('data-liked', 'true');
        button.classList.add('liked');
        button.textContent = '좋아요 취소';
        likeCountSpan.textContent = currentCount + 1;
    } else {
        button.setAttribute('data-liked', 'false');
        button.classList.remove('liked'); // 'liked' 클래스 제거
        button.textContent = '🤍 좋아요';
        likeCountSpan.textContent = currentCount - 1;
    }
}