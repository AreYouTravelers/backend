// accessToken 불러오기
let accessToken = localStorage.getItem('accessToken');
const id = window.location.pathname.split("/")[3];

fetch(`/api/accompany/sent/${id}`, {
    method: 'GET',
    headers: {
        'Authorization': `Bearer ` + accessToken,
        'Content-Type': 'application/json'
    }
})
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            alert("보낸동행 조회에 실패했습니다.");
            throw new Error('서버에서 응답 오류 발생');
        }
    })
    .then(responseData => {
        const data = responseData.data;
        const boardLink = document.getElementById('board-link');

        // JSON 데이터를 받은 후 input 요소의 value 속성을 변경합니다.
        const originalBoard = document.getElementById('original-board');
        const profileUsername = document.getElementById('profile-username');
        const profileImage = document.getElementById('profile-image');
        const boardTitle = document.getElementById('board-title')
        const boardCountry = document.getElementById('board-country');
        const boardStartDate = document.getElementById('board-start-date');
        const boardEndDate = document.getElementById('board-end-date');
        const message = document.getElementById('message');
        const currentTime = document.getElementById('current-time');
        // data.updatedAt을 사용하여 Date 객체로 변환
        const date = new Date(data.updatedAt);

        // 년, 월, 일, 시, 분 추출
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1 필요
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');

        // 원하는 형식으로 변환
        const formattedDate = `${year}-${month}-${day} ${hours}:${minutes}`;

        if (data) {
            if (data.requestedBoardInfoDto.deletedAt == null) {
                boardLink.href = "/boards/" + data.requestedBoardInfoDto.id;
            } else {
                originalBoard.style.backgroundColor = 'rgba(0, 0, 0, 0.7)';
                const deletedMessage = document.createElement('p'); // 삭제된 게시글 문구 추가
                deletedMessage.className = 'deleted-message'; // 클래스 이름 추가
                deletedMessage.textContent = '삭제된 게시글입니다'; // 삭제된 게시글 문구 설정
                originalBoard.appendChild(deletedMessage); // 삭제된 게시글 문구를 원본 게시글 div에 추가
            }
            profileUsername.innerText = data.requestedBoardInfoDto.username;
            profileImage.src = data.requestedBoardInfoDto.userProfileImage;
            boardTitle.innerText = data.requestedBoardInfoDto.title;
            boardCountry.innerText = data.requestedBoardInfoDto.country;
            boardStartDate.innerText = data.requestedBoardInfoDto.startDate;
            boardEndDate.innerText = data.requestedBoardInfoDto.endDate;
            message.innerText = data.message;
            currentTime.innerText = formattedDate; // 변환된 날짜를 표시
        } else {
            console.error('No data received');
        }
    })
    .catch(error => {
        console.error('에러:', error);
    });
document.addEventListener('DOMContentLoaded', function () {
    const editButton = document.getElementById('edit-button');
    const message = document.getElementById('message');
    const cancelButton = document.getElementById('cancel-button');
    const updateButton = document.getElementById('update-button');
    const listButton = document.getElementById('list-button');
    const deleteButton = document.getElementById('delete-button');

    const updateForm = document.getElementById('updateForm');

    // 초기 스타일 설정
    message.style.backgroundColor = '#e9ecef'; // 회색 배경
    message.style.cursor = 'not-allowed'; // 클릭 불가능한 커서

    // 원래 메시지 값을 저장할 변수
    let originalMessage = message.value;

    // 수정 버튼 클릭 시 입력 활성화
    editButton.addEventListener('click', function () {
        originalMessage = message.value; // 현재 메시지 값을 저장
        message.removeAttribute('disabled');
        message.style.backgroundColor = ''; // 배경색을 원래대로 되돌림
        message.style.cursor = 'text'; // 커서를 텍스트 입력 가능하도록 변경
        editButton.style.display = 'none';
        cancelButton.style.display = 'block';
        updateButton.style.display = 'block';
        listButton.style.display = 'none';
        deleteButton.style.display = 'none';
    });

    // 취소 버튼 클릭 시 입력 비활성화 및 버튼 원래 상태로 복귀
    cancelButton.addEventListener('click', function () {
        message.setAttribute('disabled', 'disabled');
        message.style.backgroundColor = '#e9ecef'; // 회색 배경
        message.value = originalMessage; // 원래 메시지 값으로 복원
        editButton.style.display = 'block';
        cancelButton.style.display = 'none';
        updateButton.style.display = 'none';
        listButton.style.display = 'block';
        deleteButton.style.display = 'block';
    });

    updateButton.addEventListener('click', function (event) {
        event.preventDefault();
        const formData = new FormData(updateForm);
        const messageValue = formData.get('message');

        fetch(`/api/accompany/sent/${id}`, {
            method: 'PATCH',
            headers: {
                'Authorization': 'Bearer ' + accessToken,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(Object.fromEntries(formData.entries()))
        })
            .then(response => {
                if (response.ok) {
                    alert('동행 요청이 업데이트되었습니다.');
                    window.location.href = `/accompany/sent/${id}`;
                } else {
                    alert('동행 요청 수정에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('Network error:', error);
            });
    });

    deleteButton.addEventListener("click", function (event) {
        event.preventDefault();
        if (confirm("동행 요청을 삭제하시겠습니까?")) {
            fetch(`/api/accompany/sent/${id}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': 'Bearer ' + accessToken,
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    if (response.ok) {
                        alert("동행 요청이 삭제되었습니다.");
                        window.location.href = `/accompany/sent`;
                    } else {
                        alert("동행 요청 삭제에 실패했습니다.");
                    }
                })
                .catch(error => {
                    console.error('Network error:', error);
                });
        } else {
            alert("동행 요청 삭제가 취소되었습니다.");
        }
    });
});