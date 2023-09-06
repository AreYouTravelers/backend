// 메인 페이지로 이동하는 함수
function goToMain() {
  // 페이지를 메인 페이지로 변경
  document.location.href = "/";
}

// 로그인 페이지로 이동하는 함수
function goToLogin() {
    // 페이지를 로그인 페이지로 변경
    document.location.href = "login"
}

// 회원가입 페이지로 이동하는 함수
function goToSignup() {
    // 페이지를 로그인 페이지로 변경
    document.location.href = "signup"
}

// 메인 페이지로 이동하는 클릭 이벤트 처리
document
  .getElementById("main-link")
  .addEventListener("click", function (event) {
    event.preventDefault(); // 기본 링크 동작 방지
    goToMain(); // 메인 페이지로 이동
  });

// 로그인 페이지로 이동하는 클릭 이벤트 처리
document
    .getElementById("main-link")
    .addEventListener("click", function (event) {
        event.preventDefault(); // 기본 링크 동작 방지
        goToLogin(); // 로그인 페이지로 이동
    });

// 회원가입 페이지로 이동하는 클릭 이벤트 처리
document
    .getElementById("main-link")
    .addEventListener("click", function (event) {
        event.preventDefault(); // 기본 링크 동작 방지
        goToSignup(); // 회원가입 페이지로 이동
    });
