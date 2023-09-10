document.addEventListener("DOMContentLoaded", function() {
    const boardId = window.location.pathname.split("/")[2];

    // accessToken 불러오기
    let accessToken = localStorage.getItem('accessToken');
    const headersWithToken = {
        'Authorization': 'Bearer ' + accessToken,
        'Content-Type': 'application/json'
    };

    document.body.addEventListener('submit', function(event) {
        if (event.target.matches('.comment-section form')) {
            event.preventDefault();

            const content = event.target.elements.commentContent.value;

            if (content) {
                // 여기서 boardId가 어디서 왔는지 확실하지 않으므로, 만약 이전 코드에 정의되지 않았다면
                // 적절한 방법으로 boardId 값을 가져올 필요가 있습니다.
                fetch(`/boards/${boardId}/comments`, {
                    method: 'POST',
                    headers: headersWithToken,
                    body: JSON.stringify({ content: content })
                }).then(response => {
                    if (response.ok) {
                        alert("댓글이 저장되었습니다.");
                        location.reload();
                    } else {
                        alert("댓글 저장 중 오류가 발생했습니다.");
                    }
                });
            }
        }
    });

    // 댓글 삭제 기능
    document.querySelectorAll('.delete-button').forEach(function(button) {
        button.addEventListener('click', function(event) {
            const commentId = event.target.getAttribute('data-comment-id');
            if (confirm("댓글을 삭제하시겠습니까?")) {
                fetch(`/boards/${boardId}/comments/${commentId}`, {
                    method: 'DELETE',
                    headers: headersWithToken
                }).then(response => {
                    if (response.ok) {
                        alert("댓글이 삭제되었습니다.");
                        location.reload();
                    } else {
                        alert("댓글 삭제 중 오류가 발생했습니다.");
                    }
                });
            }
        });
    });

    // 댓글 수정 기능
    document.querySelectorAll('.edit-button').forEach(function(button) {
        button.addEventListener('click', function(event) {
            const commentId = event.target.getAttribute('data-comment-id');
            const newContent = prompt("댓글 내용을 수정하세요.");
            if (newContent) {
                fetch(`/boards/${boardId}/comments/${commentId}`, {
                    method: 'PUT',
                    headers: headersWithToken,
                    body: JSON.stringify({ content: newContent })
                }).then(response => {
                    if (response.ok) {
                        alert("댓글이 수정되었습니다.");
                        location.reload();
                    } else {
                        alert("댓글 수정 중 오류가 발생했습니다.");
                    }
                });
            }
        });
    });

    // 답글쓰기 기능
    document.querySelectorAll('.reply-button').forEach(function(button) {
        button.addEventListener('click', function(event) {
            // 답글 폼 보이기
            const replyForm = event.target.parentElement.nextElementSibling;
            replyForm.style.display = replyForm.style.display === 'none' ? 'block' : 'none';
        });
    });

    document.querySelectorAll('.post-reply').forEach(function(button) {
        button.addEventListener('click', function(event) {
            const parentId = event.target.getAttribute('data-parent-id');
            const content = event.target.previousElementSibling.value;

            if (content) {
                fetch(`/boards/${boardId}/comments/${parentId}`, {
                    method: 'POST',
                    headers: headersWithToken,
                    body: JSON.stringify({ content: content })
                }).then(response => {
                    if (response.ok) {
                        alert("답글이 저장되었습니다.");
                        location.reload();
                    } else {
                        alert("답글 저장 중 오류가 발생했습니다.");
                    }
                });
            }
        });
    });
});
