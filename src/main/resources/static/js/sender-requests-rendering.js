// accessToken 불러오기
let accessToken = localStorage.getItem('accessToken');
const boardId = window.location.pathname.split("/")[2];

document.addEventListener('DOMContentLoaded', function () {
    const createForm = document.getElementById('createForm');
    const createButton = document.getElementById('createButton');

    createButton.addEventListener('click', function (event) {
        event.preventDefault();
        const formData = new FormData(createForm);
        const messageValue = document.getElementById('messages').value;
        formData.append('message', messageValue);
        console.log(messageValue);

        fetch(`/boards/${boardId}/sender-requests`, {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + accessToken,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(Object.fromEntries(formData.entries())) // FormData를 JSON으로 변환
        })
            .then(response => {
                if (response.ok) {
                    console.log(JSON.stringify(Object.fromEntries(formData.entries())))
                    alert("동행 요청이 되었습니다.");
                    // window.location.href = '/boards/' + boardId +
                    //     '/sender-requests/' + senderRequestsId;
                    window.location.href = '/sender-requests'
                    // location.reload();
                } else {
                    alert("동행 요청에 실패했습니다.");
                }
            })
            .catch(error => {
                console.error('Network error:', error);
            });
    });
});