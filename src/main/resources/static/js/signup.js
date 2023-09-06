// 로그인 페이지로 이동하는 함수
function goToLogin() {
  // 페이지를 로그인 페이지로 변경
  document.location.href = "login.html";
}

// 로그인 페이지로 이동하는 클릭 이벤트 처리
document
  .getElementById("login-link")
  .addEventListener("click", function (event) {
    event.preventDefault(); // 기본 링크 동작 방지
    goToLogin(); // 로그인 페이지로 이동
  });
