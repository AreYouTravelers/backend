// accessToken 불러오기
let accessToken = localStorage.getItem('accessToken');
const id = window.location.pathname.split("/")[3];

fetch(`/api/review/received/${id}`, {
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
            alert("받은후기 조회에 실패했습니다.");
            throw new Error('서버에서 응답 오류 발생');
        }
    })
    .then(responseData => {
        const data = responseData.data;

        const boardLink = document.getElementById('board-link');
        boardLink.href = "/boards/" + data.requestedBoardInfoDto.id;

        // JSON 데이터를 받은 후 input 요소의 value 속성을 변경합니다.
        const countryImage = document.getElementById('country-image');
        const profileImage = document.getElementById('profile-image');
        const profileUsername = document.getElementById('profile-username');
        const profileAge = document.getElementById('profile-age');
        const profileMbti = document.getElementById('profile-mbti');
        const profileGender = document.getElementById('profile-gender');
        const profileTemp = document.getElementById('profile-temp');
        const boardTitle = document.getElementById('board-title');
        const rating = document.getElementById('rating');
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
            profileImage.src = data.requestedUserInfoDto.profileImage;
            profileUsername.innerText = data.requestedUserInfoDto.username;
            profileAge.innerText = data.requestedUserInfoDto.age;
            profileMbti.innerText = data.requestedUserInfoDto.mbti;
            profileGender.innerText = data.requestedUserInfoDto.gender;
            profileTemp.innerHTML = `<span>${data.requestedUserInfoDto.temperature}</span>℃`;

            const tempValue = data.requestedUserInfoDto.temperature;
            let tempClass = '';
            if (tempValue >= 30 && tempValue <= 39) {
                tempClass = 'temp-3';
            } else if (tempValue >= 40 && tempValue <= 49) {
                tempClass = 'temp-4';
            } else if (tempValue >= 50) {
                tempClass = 'temp-5';
            }
            profileTemp.querySelector('span').className = tempClass;

            boardTitle.innerText = '< ' + data.requestedBoardInfoDto.title;
            const ratingValue = data.rating;

            // 기존의 별을 모두 제거합니다.
            rating.innerHTML = ''; // 기존 별들을 모두 제거합니다.

            const fullStar = '★';
            const halfStar = '☆';
            const emptyStar = '☆';

            for (let i = 1; i <= 5; i++) {
                const star = document.createElement('span');
                if (ratingValue >= i) {
                    star.className = 'full-star';
                    star.innerText = fullStar;
                } else if (ratingValue >= i - 0.5) {
                    star.className = 'half-star';
                    star.innerText = halfStar;
                } else {
                    star.className = 'empty-star';
                    star.innerText = emptyStar;
                }
                rating.appendChild(star);
            }
            message.innerText = data.message;
            createdAt.innerText = formattedDate;
        } else {
            console.error('No data received');
        }
    })
    .catch(error => {
        console.error('에러:', error);
    });