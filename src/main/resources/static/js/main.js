import * as userModule from './user.js';

const goToLogin = function () {
    document.location.href = '/login';
}

const goToMypage = function () {
    document.location.href = '/mypage';
}

userModule.fetchLoggedIn().then(userInfo => {
    // userInfo가 있으면 로그인 상태로 처리
    if (userInfo.role === '회원') {
        removeLoginLink(); // 로그인, 회원가입 링크 제거
        showLogoutMypageLink(); // 로그아웃 링크 보이게 설정
    } else if (userInfo.role === '관리자') {
        removeLoginLink(); // 로그인, 회원가입 링크 제거
        showLogoutAdminLink(); // 로그아웃 링크 보이게 설정
    }
})

// 로그아웃 링크를 보이게 하고 관리자 페이지를 보이게 하는 함수
function showLogoutAdminLink() {
    const logoutLink = document.getElementById("logout-link");
    const adminLink = document.getElementById("admin-link");

    // 로그아웃 링크를 보이게 설정
    if (logoutLink) {
        logoutLink.style.display = "block";
    }
    // 어드민 링크를 보이게 설정
    if (adminLink) {
        adminLink.style.display = "block";
    }
}

// 로그인, 회원가입 링크를 삭제하는 함수
function removeLoginLink() {
    const loginLink = document.getElementById("login-link");

    // login-link 제거
    if (loginLink) {
        loginLink.remove();
    }
}
// 로그아웃 링크를 보이게 하는 함수
function showLogoutMypageLink() {
    const logoutLink = document.getElementById("logout-link");
    const mypageLink = document.getElementById("mypage-link");

    // 로그아웃 링크를 보이게 설정
    if (logoutLink) {
        logoutLink.style.display = "block";
    }
    // 마이페이지 링크를 보이게 설정
    if (mypageLink) {
        mypageLink.style.display = "block";
    }
}

// 마이페이지 기능
document.getElementById('mypage-link').addEventListener('click', event => {
    event.preventDefault();
    goToMypage();
})

// 로그인 링크
document.getElementById('login-link').addEventListener('click', event => {
    event.preventDefault();
    goToLogin();
})

// 로그아웃 기능
document.getElementById('logout-link').addEventListener('click', event => {
    event.preventDefault();
    fetch('/users/logout', {
        method: 'post',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${userModule.getJwt()}`
        }
    }).then(response => {
        if (response.ok) {
            // 로컬 스토리지에서 accessToken 삭제
            localStorage.removeItem('accessToken')
            alert('로그아웃 되었습니다.')
            location.href = '/'
        } else throw new Error(response.status.toString())
    }).catch(event => {
        console.log(event.message)
        alert('잘못된 접근입니다.')
    })
})