// 메인 페이지로 이동하는 함수
function goToMain() {
  // 페이지를 메인 페이지로 변경
  document.location.href = "index.html";
}

// 메인 페이지로 이동하는 클릭 이벤트 처리
document
  .getElementById("main-link")
  .addEventListener("click", function (event) {
    event.preventDefault(); // 기본 링크 동작 방지
    goToMain(); // 메인 페이지로 이동
  });
