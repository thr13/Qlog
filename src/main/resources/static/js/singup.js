document.getElementById('signupForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const formData = new FormData(this);
    const data = Object.fromEntries(formData.entries());

    fetch('/api/users/signup', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.status === 201) {
                alert('회원가입에 성공하였습니다.');
                window.location.href = '/view/users/login';
            } else {
                response.json().then(error => {
                    alert('회원가입에 실패했습니다: ' + (error.message || '알 수 없는 오류'));
                });
            }
        }).catch(error => {
        console.error('Error:', error);
        alert('요청 중 오류가 발생했습니다.');
    });
});