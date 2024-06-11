// accessToken 불러오기
let accessToken = localStorage.getItem('accessToken');

document.addEventListener('DOMContentLoaded', function () {
    const postForm = document.getElementById('postForm');
    const postButton = document.getElementById('postButton');

    postButton.addEventListener("click", function (event) {
        event.preventDefault();
        const formData = new FormData(postForm);

        fetch(`/reports/write`, {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + accessToken,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(Object.fromEntries(formData.entries()))
        })
            .then(response => {
                if (response.ok) {
                    alert('신고가 완료되었습니다.');
                    window.location.href = '/reports/write';
                } else {
                    alert('신고에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('Network error:', error);
            });
    });
});