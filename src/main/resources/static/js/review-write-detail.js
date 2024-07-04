// accessToken 불러오기
let accessToken = localStorage.getItem('accessToken');
const accompanyId = window.location.pathname.split("/")[3];

fetch(`/api/review/write/${accompanyId}`, {
    method: 'GET',
    headers: {
        'Authorization': 'Bearer ' + accessToken,
        'Content-Type': 'application/json'
    }
}) .then(response => {
    if (response.ok) {
        return response.json();
    } else {
        throw new Error(response.status.toString());
    }
})
    .then(responseData => {
        const data = responseData.data;
        const boardLink = document.getElementById('board-link');

        // JSON 데이터를 받은 후 input 요소의 value 속성을 변경합니다.
        const profileUsername = document.getElementById('profile-username')
        const profileImage = document.getElementById('profile-image');
        const boardTitle = document.getElementById('board-title')
        const boardCountry = document.getElementById('board-country');
        const boardStartDate = document.getElementById('board-start-date');
        const boardEndDate = document.getElementById('board-end-date');
        const originalBoard = document.getElementById('original-board');

        if (data) {
            if (data.deletedAt == null) {
                boardLink.href = "/boards/" + data.requestedBoardInfoDto.id;
            } else {
                originalBoard.style.backgroundColor = 'rgba(0, 0, 0, 0.7)';
                const deletedMessage = document.createElement('p'); // 삭제된 게시글 문구 추가
                deletedMessage.className = 'deleted-message'; // 클래스 이름 추가
                deletedMessage.textContent = '삭제된 게시글입니다'; // 삭제된 게시글 문구 설정
                originalBoard.appendChild(deletedMessage); // 삭제된 게시글 문구를 원본 게시글 div에 추가
            }
            profileUsername.innerText = data.requestedBoardInfoDto.username;
            if (data.requestedBoardInfoDto.userProfileImage)
                profileImage.src = data.requestedBoardInfoDto.userProfileImage;
            else profileImage.src = "/static/images/user-profile-basic.png";
            boardTitle.innerText = data.requestedBoardInfoDto.title;
            boardCountry.innerText = data.requestedBoardInfoDto.country;
            boardStartDate.innerText = data.requestedBoardInfoDto.startDate;
            boardEndDate.innerText = data.requestedBoardInfoDto.endDate;
            originalBoard.href = "/boards/" + data.requestedBoardInfoDto.id;
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
        const ratingValue = document.getElementById('rating').innerText;
        formData.append('accompanyId', accompanyId);
        formData.append('message', messageValue);
        formData.append('rating', ratingValue);

        fetch(`/api/review/write/${accompanyId}`, {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + accessToken,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(Object.fromEntries(formData.entries())) // FormData를 JSON으로 변환
        })
            .then(response => {
                if (response.ok) {
                    alert("후기가 작성되었습니다.");
                    window.location.href = '/review/sent';
                } else {
                    alert("후기 작성에 실패했습니다.");
                }
            })
            .catch(error => {
                console.error('Network error:', error);
            });
    });
});