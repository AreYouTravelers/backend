document.addEventListener("DOMContentLoaded", function () {
    // 오늘의 날짜를 가져옵니다.
    const today = new Date();

    // 모든 후기 작성 버튼 요소를 선택합니다.
    const reviewButton = document.querySelectorAll(".board-item-review .possible");

    // 각 후기 작성 버튼 요소를 순회합니다.
    reviewButton.forEach(function (reviewButton) {
        // 해당 후기 작성 버튼 요소의 부모 요소에서 end-date 클래스를 가진 요소를 찾아낸 후 그 요소의 텍스트를 가져옵니다.
        const endDateText = reviewButton.closest(".board-item").querySelector(".end-date").textContent;

        // endDate 값을 Date 객체로 변환합니다.
        const endDate = new Date(endDateText);

        // endDate와 오늘 날짜(today)를 비교합니다.
        if (endDate > today) {
            // endDate가 오늘 이후인 경우에만 버튼을 활성화합니다.
            reviewButton.addEventListener("click", function (event) {
                event.preventDefault(); // 기본 링크 동작을 막습니다.
            });
            reviewButton.style.backgroundColor = "#999";
        }
    });
});
