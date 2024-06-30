const getJwt = localStorage.getItem('accessToken');

fetch('/users/my-profile', {
    method: 'GET',
    headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${getJwt}`
    }
})
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error(response.status.toString());
        }
    })
    .then(data => {
        // JSON 데이터를 받은 후 input 요소의 value 속성을 변경합니다.
        const profileImage = document.getElementById('profile-image');
        const profileUsername = document.getElementById('profile-username')
        const profileFullname = document.getElementById('profile-fullname')
        const infoMbti = document.getElementById('info-mbti');
        const infoTemp = document.getElementById('info-temp');
        const infoGender = document.getElementById('info-gender');

        const fullNameInput = document.getElementById('fullName');
        const usernameInput = document.getElementById('username');
        const birthDateInput = document.getElementById('birthDate');
        const mbtiInput = document.getElementById('mbti');
        const emailInput = document.getElementById('email');

        // 프로필 사진 존재 시 실행. 없는 경우 기본 이미지
        if (data.profileImg)
            profileImage.src = data.profileImg;
        profileUsername.innerText = data.username;
        profileFullname.innerText = data.fullName;
        infoMbti.innerText = data.mbti;
        infoTemp.innerText = data.temperature;
        let tempClass;
        if (data.temperature <= 39) {
            tempClass = 'temp-3';
        } else if (data.temperature <= 49) {
            tempClass = 'temp-4';
        } else if (data.temperature > 49) {
            tempClass = 'temp-5';
        }
        infoTemp.className = tempClass;
        infoGender.innerText = data.gender;

        fullNameInput.value = data.fullName;
        usernameInput.value = data.username;
        birthDateInput.value = data.birthDate;
        mbtiInput.value = data.mbti;
        emailInput.value = data.email;
    })
    .catch(error => {
        console.error('Error:', error);
    });

