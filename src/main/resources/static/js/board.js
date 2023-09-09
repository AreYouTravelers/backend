const dataToSend = {
    // DTO 필드에 해당하는 데이터를 여기에 포함
    title: "게시글 제목",
    content: "게시글 내용",
    // 다른 필드들도 포함 가능
};

fetch('/boards', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
    },
    body: JSON.stringify(dataToSend),
})
    .then(response => response.json())
    .then(data => {
        console.log('서버로부터 받은 데이터:', data);
        // 원하는 동작 수행
    })
    .catch(error => {
        console.error('에러:', error);
    });
