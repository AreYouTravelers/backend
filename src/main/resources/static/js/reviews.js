// accessToken 불러오기
let accessToken = localStorage.getItem('accessToken');
const boardId = window.location.pathname.split("/")[2];

document.addEventListener('DOMContentLoaded', function () {
    const createForm = document.getElementById('createForm');
    const createButton = document.getElementById('createButton');

    createButton.addEventListener('click', function (event) {
        event.preventDefault();

        const formData = new FormData(createForm);

        fetch(`/boards/${boardId}/reviews/write`, {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + accessToken,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(Object.fromEntries(formData.entries())) // FormData를 JSON으로 변환
        })
            .then(response => {
                if (response.ok) {
                    alert("후기가 작성 되었습니다.");
                    window.location.href = '/boards/reviews/sender';
                } else {
                    alert("후기 작성에 실패하였습니다.");
                }
            })
            .catch(error => {
                console.error('Network error:', error);
            });
    });
});