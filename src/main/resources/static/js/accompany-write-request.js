// accessToken 불러오기
let accessToken = localStorage.getItem('accessToken');
const boardId = window.location.pathname.split("/")[2];

fetch(`/api/boards/${boardId}/info`, {
    method: 'GET',
    headers: {
        'Authorization': 'Bearer ' + accessToken,
        'Content-Type': 'application/json'
    }
})
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error(response.status.toString());
        }
    })
    .then(responseData => {
        const data = responseData.data;

        // JSON 데이터를 받은 후 input 요소의 value 속성을 변경합니다.
        const profileUsername = document.getElementById('profile-username')
        const profileImage = document.getElementById('profile-image');
        const boardTitle = document.getElementById('board-title')
        const boardCountry = document.getElementById('board-country');
        const boardStartDate = document.getElementById('board-start-date');
        const boardEndDate = document.getElementById('board-end-date');
        const originalBoard = document.getElementById('original-board');

        if (data) {
            profileUsername.innerText = data.username;
            if (data.userProfileImage)
                profileImage.src = data.userProfileImage;
            else profileImage.src = "/static/images/user-profile-basic.png";
            boardTitle.innerText = data.title;
            boardCountry.innerText = data.country;
            boardStartDate.innerText = data.startDate;
            boardEndDate.innerText = data.endDate;
            originalBoard.href = "/boards/" + data.id;
        } else {
            console.error('No data received');
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });

document.addEventListener('DOMContentLoaded', function () {
    const createForm = document.getElementById('createForm');
    const createButton = document.getElementById('createButton');

    createButton.addEventListener('click', function (event) {
        event.preventDefault();
        const formData = new FormData(createForm);

        const messageValue = document.getElementById('messages').value;
        formData.append('boardId', boardId);
        formData.append('message', messageValue);
        console.log(messageValue);

        fetch(`/api/boards/${boardId}/accompany`, {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + accessToken,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(Object.fromEntries(formData.entries())) // FormData를 JSON으로 변환
        })
            .then(response => {
                if (response.ok) {
                    alert("동행 요청이 되었습니다.");
                    window.location.href = '/boards';
                } else {
                    alert("동행 요청에 실패했습니다.");
                }
            })
            .catch(error => {
                console.error('Network error:', error);
            });
    });
});