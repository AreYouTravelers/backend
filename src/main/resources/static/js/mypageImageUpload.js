document.addEventListener('DOMContentLoaded', function () {
    const uploadButton = document.getElementById('upload-image-button');
    const imageInput = document.getElementById('image-input');
    const profileImage = document.getElementById('profile-image');
    const accessToken = localStorage.getItem('accessToken');

    // '업로드' 버튼을 클릭하면 파일 선택 창이 나타나도록 이벤트 리스너를 등록합니다.
    uploadButton.addEventListener('click', function () {
        imageInput.click();
    });

    // 이미지 선택이 완료되면 프로필 이미지를 업데이트합니다.
    imageInput.addEventListener('change', function () {
        const selectedImage = imageInput.files[0];
        if (selectedImage) {
            // 이미지를 선택하면 파일을 FormData에 추가합니다.
            const formData = new FormData();
            formData.append('image', selectedImage);

            // 프로필 이미지 업로드를 위한 PATCH 요청을 보냅니다.
            fetch('/users/image', {
                method: 'PATCH',
                body: formData,
                headers: {
                    'Authorization': 'Bearer ' + accessToken // JWT 토큰을 포함
                }
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('서버에서 응답 오류 발생');
                    }
                    // 프로필 이미지 업로드가 성공하면 프로필 이미지를 업데이트합니다.
                    return fetch("/users/my-profile", {
                        headers: {
                            'Authorization': 'Bearer ' + accessToken // JWT 토큰을 포함
                        }
                    });
                })
                .then(response => {
                    if (response.ok) {
                        return response.json();
                    } else {
                        throw new Error('프로필 이미지 업데이트에 실패했습니다.');
                    }
                })
                .then(data => {
                    // 서버에서 반환한 데이터를 이용하여 프로필 이미지를 업데이트합니다.
                    profileImage.src = data.profileImg;
                    alert('프로필 이미지가 업데이트되었습니다.');
                })
                .catch(error => {
                    console.error('에러:', error);
                    alert('프로필 이미지 업데이트에 실패했습니다.');
                });
        }
    });
});
