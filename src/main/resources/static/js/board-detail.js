// accessToken 불러오기
let accessToken = localStorage.getItem('accessToken');
const boardId = window.location.pathname.split("/")[2];

// 상대적 시간 표시를 위한 함수 추가
function timeAgo(date) {
    const now = new Date();
    const seconds = Math.floor((now - new Date(date)) / 1000);
    let interval = Math.floor(seconds / 31536000);

    if (interval >= 1) {
        return interval + "년 전";
    }
    interval = Math.floor(seconds / 2592000);
    if (interval >= 1) {
        return interval + "달 전";
    }
    interval = Math.floor(seconds / 86400);
    if (interval >= 1) {
        return interval + "일 전";
    }
    interval = Math.floor(seconds / 3600);
    if (interval >= 1) {
        return interval + "시간 전";
    }
    interval = Math.floor(seconds / 60);
    if (interval >= 1) {
        return interval + "분 전";
    }
    return "방금 전";
}

document.addEventListener('DOMContentLoaded', function () {
    const editButton = document.getElementById('edit-button');
    const updateForm = document.getElementById('updateForm');
    const updateButton = document.getElementById('update-button');
    const cancelButton = document.getElementById('cancel-button');
    const listButton = document.getElementById('list-button');
    const deleteButton = document.getElementById('delete-button');
    const accompanyButton = document.getElementById('accompany-button');
    const commentButton = document.getElementById('comment-button');
    const maxPeople = document.getElementById('maxPeople');
    const title = document.getElementById('title');
    const message = document.getElementById('message');
    const commentContainer = document.querySelector('.comment-form-list-container');
    const bottomContainer1 = document.querySelector('.bottom-container1');
    const bottomContainer2 = document.querySelector('.bottom-container2');

    // 게시글 수정
    updateButton.addEventListener('click', function (event) {
        event.preventDefault();
        const formData = new FormData(updateForm);

        const selectedCountry = document.getElementById("country").value;
        const countryId = parseInt(selectedCountry);

        formData.append('countryId', countryId);

        fetch(`/boards/${boardId}`, {
            method: 'PUT',
            headers: {
                'Authorization': 'Bearer ' + accessToken,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(Object.fromEntries(formData.entries()))
        })
            .then(response => {
                if (response.ok) {
                    alert('게시물이 업데이트되었습니다.');
                    window.location.href = `/boards/${boardId}`;
                } else {
                    alert('게시물 업데이트에 실패했습니다.');
                }
            })
            .catch(error => {
                console.error('Network error:', error);
            });
    });

    // 상대적 시간 표시 업데이트
    document.querySelectorAll('.relative-time').forEach(function (span) {
        const date = span.getAttribute('data-created-at');
        span.textContent = timeAgo(new Date(date));
    });

    // 연필 모양 아이콘 클릭
    editButton.addEventListener('click', function (event) {
        event.preventDefault();
        originalMessage = message.value;
        maxPeople.removeAttribute('disabled');
        title.removeAttribute('disabled');
        message.removeAttribute('disabled');
        message.style.backgroundColor = '';
        message.style.cursor = 'text';
        editButton.style.visibility = 'hidden';
        cancelButton.style.display = 'block';
        updateButton.style.display = 'block';
        bottomContainer1.style.display = 'flex';
        listButton.style.visibility = 'hidden';
        deleteButton.style.visibility = 'hidden';
        accompanyButton.style.visibility = 'hidden';
        commentButton.style.visibility = 'hidden';
        bottomContainer2.style.display = 'none';
    });

    // 취소 버튼 클릭 시 입력 비활성화 및 버튼 원래 상태로 복귀
    cancelButton.addEventListener('click', function (event) {
        event.preventDefault();
        maxPeople.setAttribute('disabled', 'disabled');
        title.setAttribute('disabled', 'disabled');
        message.setAttribute('disabled', 'disabled');
        message.style.backgroundColor = '#e9ecef';
        message.value = originalMessage;
        editButton.style.visibility = 'visible';
        cancelButton.style.display = 'none';
        updateButton.style.display = 'none';
        bottomContainer1.style.display = 'none';
        listButton.style.visibility = 'visible';
        deleteButton.style.visibility = 'visible';
        accompanyButton.style.visibility = 'visible';
        commentButton.style.visibility = 'visible';
        bottomContainer2.style.display = 'flex';
    });

    // 모집 마감시 동행 요청하기 버튼 비활성화
    accompanyButton.addEventListener('click', function (event) {
        let statusTitle = document.getElementById('status-title');
        let statusValue = statusTitle.getAttribute('data-status-value');

        if (statusValue === '모집마감') {
            event.preventDefault(); // 기본 동작 막기
            alert('모집이 마감되었습니다.');
        } else {
            window.location.href = document.querySelector('#accompany-button a').href;
        }
    });

    // 게시글 삭제
    deleteButton.addEventListener("click", function (event) {
        event.preventDefault();
        if (confirm("게시물을 삭제하시겠습니까?")) {
            fetch(`/boards/${boardId}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': 'Bearer ' + accessToken,
                    'Content-Type': 'application/json'
                }
            })
                .then(response => {
                    if (response.ok) {
                        alert("게시물이 삭제되었습니다.");
                        window.location.href = '/boards';
                    } else {
                        alert("게시물 삭제를 실패했습니다.");
                    }
                })
                .catch(error => {
                    console.error('Network error:', error);
                });
        } else {
            alert("게시물 삭제가 취소되었습니다.");
        }
    });

    // 댓글 토글 버튼 클릭
    commentButton.addEventListener('click', function () {
        commentContainer.classList.toggle('collapsed');
        const commentIcon = document.getElementById('comment-icon');
        if (commentContainer.classList.contains('collapsed')) {
            commentIcon.classList.remove('fa-angle-down');
            commentIcon.classList.add('fa-angle-up');
        } else {
            commentIcon.classList.remove('fa-angle-up');
            commentIcon.classList.add('fa-angle-down');
        }
    });

    // 댓글 토글 초기 상태 설정
    const commentIcon = document.getElementById('comment-icon');
    commentIcon.classList.remove('fa-angle-down');
    commentIcon.classList.add('fa-angle-up');

    // 댓글 작성 후 페이지 리로드
    document.body.addEventListener('submit', function(event) {
        if (event.target.matches('.comment-section form')) {
            event.preventDefault();
            const content = event.target.elements.commentContent.value;
            if (content) {
                fetch(`/boards/${boardId}/comments`, {
                    method: 'POST',
                    headers: {
                        'Authorization': 'Bearer ' + accessToken,
                        'Content-Type': 'application/json'
                    },
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

    // 댓글 삭제
    document.querySelectorAll('.delete-button').forEach(function(button) {
        button.addEventListener('click', function(event) {
            const commentId = event.target.getAttribute('data-comment-id');
            if (confirm("댓글을 삭제하시겠습니까?")) {
                fetch(`/boards/${boardId}/comments/${commentId}`, {
                    method: 'DELETE',
                    headers: {
                        'Authorization': 'Bearer ' + accessToken,
                        'Content-Type': 'application/json'
                    }
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

    // 댓글 수정 버튼 클릭 시
    document.querySelectorAll('.edit-button').forEach(function(button) {
        button.addEventListener('click', function(event) {
            const parentComment = event.target.closest('.parent-comment');
            const commentContents = parentComment.querySelector('.comment-contents');
            const commentContent = parentComment.querySelector('.comment-content');
            const editContent = parentComment.querySelector('.edit-content');
            const editButton = parentComment.querySelector('.edit-button');
            const deleteButton = parentComment.querySelector('.delete-button');
            const cancelButton = parentComment.querySelector('.cancel-button');
            const confirmButton = parentComment.querySelector('.confirm-button');
            const replyButton = parentComment.querySelector('.reply-button');
            commentContents.style.marginBottom = '15px';
            commentContent.style.display = 'none';
            editContent.style.display = 'block';
            editButton.style.display = 'none';
            deleteButton.style.display = 'none';
            cancelButton.style.display = 'inline-block';
            confirmButton.style.display = 'inline-block';
            replyButton.style.display = 'none';
        });
    });

    // 댓글 수정 취소 버튼 클릭 시
    document.querySelectorAll('.cancel-button').forEach(function(button) {
        button.addEventListener('click', function(event) {
            const parentComment = event.target.closest('.parent-comment');
            const commentContents = parentComment.querySelector('.comment-contents');
            const commentContent = parentComment.querySelector('.comment-content');
            const editContent = parentComment.querySelector('.edit-content');
            const editButton = parentComment.querySelector('.edit-button');
            const deleteButton = parentComment.querySelector('.delete-button');
            const cancelButton = parentComment.querySelector('.cancel-button');
            const confirmButton = parentComment.querySelector('.confirm-button');
            const replyButton = parentComment.querySelector('.reply-button');
            commentContents.style.marginBottom = '15px';
            commentContent.style.display = 'block';
            editContent.style.display = 'none';
            editButton.style.display = 'inline-block';
            deleteButton.style.display = 'inline-block';
            cancelButton.style.display = 'none';
            confirmButton.style.display = 'none';
            replyButton.style.display = 'inline-block';
        });
    });

    // 댓글 수정 확인 버튼 클릭 시
    document.querySelectorAll('.confirm-button').forEach(function(button) {
        button.addEventListener('click', function(event) {
            const commentId = event.target.getAttribute('data-comment-id');
            const parentComment = event.target.closest('.parent-comment');
            const newContent = parentComment.querySelector('.edit-content').value;

            if (newContent) {
                fetch(`/boards/${boardId}/comments/${commentId}`, {
                    method: 'PUT',
                    headers: {
                        'Authorization': 'Bearer ' + accessToken,
                        'Content-Type': 'application/json'
                    },
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

    // 답글 쓰기 기능
    document.querySelectorAll('.reply-button').forEach(function(button) {
        button.addEventListener('click', function(event) {
            const parentComment = event.target.closest('.parent-comment');
            const replyForm = parentComment.querySelector('.reply-form');
            const replyButton = parentComment.querySelector('.reply-button');
            const replyCancelButton = parentComment.querySelector('.reply-cancel-button');
            const replyPostButton = parentComment.querySelector('.reply-post-button');
            const editButton = parentComment.querySelector('.edit-button');
            const deleteButton = parentComment.querySelector('.delete-button');

            replyForm.style.display = 'block';
            replyButton.style.display = 'none';
            replyCancelButton.style.display = 'inline-block';
            replyPostButton.style.display = 'inline-block';
            editButton.style.display = 'none';
            deleteButton.style.display = 'none';
        });
    });

    // 답글 취소 버튼 클릭 시
    document.querySelectorAll('.reply-cancel-button').forEach(function(button) {
        button.addEventListener('click', function(event) {
            const parentComment = event.target.closest('.parent-comment');
            const replyForm = parentComment.querySelector('.reply-form');
            const replyButton = parentComment.querySelector('.reply-button');
            const replyCancelButton = parentComment.querySelector('.reply-cancel-button');
            const replyPostButton = parentComment.querySelector('.reply-post-button');
            const editButton = parentComment.querySelector('.edit-button');
            const deleteButton = parentComment.querySelector('.delete-button');

            replyForm.style.display = 'none';
            replyButton.style.display = 'inline-block';
            replyCancelButton.style.display = 'none';
            replyPostButton.style.display = 'none';
            editButton.style.display = 'inline-block';
            deleteButton.style.display = 'inline-block';
        });
    });

    // 답글 저장 버튼 클릭 시
    document.querySelectorAll('.reply-post-button').forEach(function(button) {
        button.addEventListener('click', function(event) {
            const parentComment = event.target.closest('.parent-comment');
            const parentId = button.getAttribute('data-parent-id');
            const content = parentComment.querySelector('.reply-content').value;
            if (content) {
                fetch(`/boards/${boardId}/comments/${parentId}`, {
                    method: 'POST',
                    headers: {
                        'Authorization': 'Bearer ' + accessToken,
                        'Content-Type': 'application/json'
                    },
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

    // 답글 수정 버튼 클릭 시
    document.querySelectorAll('.reply-edit-button').forEach(function(button) {
        button.addEventListener('click', function(event) {
            const childComment = event.target.closest('.child-comment');
            const commentContents = childComment.querySelector('.comment-contents');
            const replyCommentContent = childComment.querySelector('.reply-comment-content');
            const replyEditContent = childComment.querySelector('.reply-edit-content');
            const replyEditButton = childComment.querySelector('.reply-edit-button');
            const replyDeleteButton = childComment.querySelector('.reply-delete-button');
            const replyCancelButton = childComment.querySelector('.reply-cancel-button');
            const replyConfirmButton = childComment.querySelector('.reply-confirm-button');
            commentContents.style.marginBottom = '15px';
            replyCommentContent.style.display = 'none';
            replyEditContent.style.display = 'block';
            replyEditButton.style.display = 'none';
            replyDeleteButton.style.display = 'none';
            replyCancelButton.style.display = 'inline-block';
            replyConfirmButton.style.display = 'inline-block';
        });
    });

    // 답글 수정 취소 버튼 클릭 시
    document.querySelectorAll('.reply-cancel-button').forEach(function(button) {
        button.addEventListener('click', function(event) {
            const childComment = event.target.closest('.child-comment');
            const replyCommentContent = childComment.querySelector('.reply-comment-content');
            const replyEditContent = childComment.querySelector('.reply-edit-content');
            const replyEditButton = childComment.querySelector('.reply-edit-button');
            const replyDeleteButton = childComment.querySelector('.reply-delete-button');
            const replyCancelButton = childComment.querySelector('.reply-cancel-button');
            const replyConfirmButton = childComment.querySelector('.reply-confirm-button');
            replyCommentContent.style.display = 'block';
            replyEditContent.style.display = 'none';
            replyEditButton.style.display = 'inline-block';
            replyDeleteButton.style.display = 'inline-block';
            replyCancelButton.style.display = 'none';
            replyConfirmButton.style.display = 'none';
        });
    });

    // 답글 수정 확인 버튼 클릭 시
    document.querySelectorAll('.reply-confirm-button').forEach(function(button) {
        button.addEventListener('click', function(event) {
            const commentId = event.target.getAttribute('data-comment-id');
            const childComment = event.target.closest('.child-comment');
            const newContent = childComment.querySelector('.reply-edit-content').value;

            if (newContent) {
                fetch(`/boards/${boardId}/comments/${commentId}`, {
                    method: 'PUT',
                    headers: {
                        'Authorization': 'Bearer ' + accessToken,
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ content: newContent })
                }).then(response => {
                    if (response.ok) {
                        alert("답글이 수정되었습니다.");
                        location.reload();
                    } else {
                        alert("답글 수정 중 오류가 발생했습니다.");
                    }
                });
            }
        });
    });

    // 답글 삭제
    document.querySelectorAll('.reply-delete-button').forEach(function(button) {
        button.addEventListener('click', function(event) {
            const commentId = event.target.getAttribute('data-comment-id');
            if (confirm("답글을 삭제하시겠습니까?")) {
                fetch(`/boards/${boardId}/comments/${commentId}`, {
                    method: 'DELETE',
                    headers: {
                        'Authorization': 'Bearer ' + accessToken,
                        'Content-Type': 'application/json'
                    }
                }).then(response => {
                    if (response.ok) {
                        alert("답글이 삭제되었습니다.");
                        location.reload();
                    } else {
                        alert("답글 삭제 중 오류가 발생했습니다.");
                    }
                });
            }
        });
    });
});