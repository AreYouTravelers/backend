// 회원가입 페이지로 이동하는 함수
function goToSignup() {
  // 페이지를 회원가입 페이지로 변경
  document.location.href = "signup";
}

// 회원가입 페이지로 이동하는 클릭 이벤트 처리
document
  .getElementById("signup-link")
  .addEventListener("click", function (event) {
    event.preventDefault(); // 기본 링크 동작 방지
    goToSignup(); // 회원가입 페이지로 이동
  });
