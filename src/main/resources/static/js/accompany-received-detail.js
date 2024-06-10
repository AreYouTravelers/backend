// accessToken 불러오기
let accessToken = localStorage.getItem('accessToken');
const id = window.location.pathname.split("/")[3];

fetch(`/api/accompany/received/${id}`, {
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
            alert("받은동행 조회에 실패했습니다.");
            throw new Error('서버에서 응답 오류 발생');
        }
    })
    .then(responseData => {
        const data = responseData.data;

        const boardLink = document.getElementById('board-link');
        boardLink.href = "/boards/" + data.requestedBoardInfoDto.id;

        // JSON 데이터를 받은 후 input 요소의 value 속성을 변경합니다.
        const countryImage = document.getElementById('country-image')
        const profileImage = document.getElementById('profile-image');
        const profileUsername = document.getElementById('profile-username')
        const profileAge = document.getElementById('profile-age')
        const profileMbti = document.getElementById('profile-mbti');
        const profileGender = document.getElementById('profile-gender');
        const profileTemp = document.getElementById('profile-temp');
        const boardTitle = document.getElementById('board-title')
        const message = document.getElementById('message');
        const createdAt = document.getElementById('created-at');
        // data.createdAt을 사용하여 Date 객체로 변환
        const date = new Date(data.createdAt);

        // 년, 월, 일, 시, 분 추출
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1 필요
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');

        // 원하는 형식으로 변환
        const formattedDate = `${year}-${month}-${day} ${hours}:${minutes}`;

        if (data) {
            countryImage.style.backgroundImage = 'url(/static/images/country/' + data.requestedBoardInfoDto.country + '.jpg)';
            profileImage.src = data.requestedUsersInfoDto.profileImage;
            profileUsername.innerText = data.requestedUsersInfoDto.username;
            profileAge.innerText = data.requestedUsersInfoDto.age;
            profileMbti.innerText = data.requestedUsersInfoDto.mbti;
            profileGender.innerText = data.requestedUsersInfoDto.gender;
            profileTemp.innerHTML = `<span>${data.requestedUsersInfoDto.temperature}</span>℃`;

            const temperature = data.requestedUsersInfoDto.temperature;
            let tempClass;
            if (temperature <= 39) {
                tempClass = 'temp-3';
            } else if (temperature <= 49) {
                tempClass = 'temp-4';
            } else if (temperature > 49) {
                tempClass = 'temp-5';
            }
            profileTemp.querySelector('span').className = tempClass;

            boardTitle.innerText = '< ' + data.requestedBoardInfoDto.title;
            message.value = data.message;
            createdAt.innerText = formattedDate;
        } else {
            console.error('No data received');
        }
    })
    .catch(error => {
        console.error('에러:', error);
    });

document.addEventListener('DOMContentLoaded', function () {
    // 수락 버튼 요소를 가져옴
    const acceptButton = document.getElementById('accept-button');
    // 거절 버튼 요소를 가져옴
        const declineButton = document.getElementById('reject-button');

    // 공통으로 사용할 클릭 이벤트 핸들러 함수
    function handleButtonClick(event) {
        // 기본 동작을 막음
        event.preventDefault();

        // 클릭된 버튼의 value 값 가져오기
        const buttonValue = event.target.value;

        // 서버에 보낼 데이터
        const data = {
            status: buttonValue
        };

        // fetch 요청 보내기
        fetch(`/api/accompany/received/${id}`, { // 서버 URL을 여기에 입력
            method: 'PATCH',
            headers: {
                'Authorization': 'Bearer ' + accessToken,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data) // JSON 문자열로 변환하여 body에 포함
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else if (response.status === 409) {
                    alert("동행 요청을 수락할 수 없습니다. 최대 인원을 초과했습니다.");
                    throw new Error('Maximum capacity exceeded');
                } else {
                    alert("동행 요청 처리에 실패했습니다.");
                    throw new Error('Server response error');
                }
            })
            .then(data => {
                alert("동행 요청을 " + buttonValue + "하였습니다.");
                console.log('Success:', data);
            })
            .catch((error) => {
                console.error('Error:', error);
            });
        }

    // 각 버튼에 클릭 이벤트 리스너 추가
    acceptButton.addEventListener('click', handleButtonClick);
    declineButton.addEventListener('click', handleButtonClick);
});