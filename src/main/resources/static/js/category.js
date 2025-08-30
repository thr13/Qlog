const deleteButtons = document.querySelectorAll('.delete-btn');
deleteButtons.forEach(button => {
    button.addEventListener('click', function () {
        const categoryId = this.getAttribute('data-id');

        if (confirm(categoryId + '번 카테고리를 삭제하시겠습니까? 게시글에 사용되지 않은 카테고리만 삭제 가능합니다.')) {
            fetch('/api/categories/' + categoryId, {
                method: 'DELETE'
            })
                .then(response => {
                    if (response.ok) {
                        alert('카테고리가 삭제되었습니다.');
                        window.location.reload();
                    } else {
                        response.json().then(error => alert(error.message));
                    }
                });
        }
    });
});

const editButtons = document.querySelectorAll('.edit-btn');
editButtons.forEach(button => {
    button.addEventListener('click', function () {
        const categoryId = this.getAttribute('data-id');
        const name = this.getAttribute('data-name');
        const changeName = prompt('카테고리 이름을 입력하세요.', name);

        if (changeName && changeName.trim() !== '') {
            fetch('/api/categories/' + categoryId, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({name: changeName})
            })
                .then(response => {
                    if (response.ok) {
                        alert('카테고리 이름이 수정되었습니다.');
                        window.location.reload();
                    } else {
                        response.json().then(error => alert(error.message));
                    }
                });
        }
    });
});