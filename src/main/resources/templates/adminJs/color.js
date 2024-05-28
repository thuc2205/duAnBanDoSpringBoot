document.getElementById('colorForm').addEventListener('submit', async (event) => {
    event.preventDefault();

    if (!confirm("Bạn có chắc muốn thêm sản phẩm này?")) {
        return;
    }

    const name = document.getElementById('name').value;
    const token = getCookie('token');

    console.log('Token:', token); // Log token để kiểm tra

    if (!token) {
        alert('Không tìm thấy token');
        return;
    }

    try {
        const response = await fetch(`/api/thuc/color`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            },
            body: JSON.stringify({ name: name })
        });

        console.log('Response status:', response.status); // Log trạng thái phản hồi

        if (response.ok) {
            alert('Thành công tạo màu');
            window.location.reload();
        } else {
            const errorData = await response.json();
            alert('Thất bại khi tạo màu: ' + errorData.message);
        }
    } catch (error) {
        console.error('Lỗi:', error);
        alert('Đã xảy ra lỗi khi cố gắng tạo màu.');
    }
});



function getCookie(name) {
    let cookieArr = document.cookie.split(";");
    for (let i = 0; i < cookieArr.length; i++) {
        let cookiePair = cookieArr[i].split("=");
        if (name === cookiePair[0].trim()) {
            return decodeURIComponent(cookiePair[1]);
        }
    }
    return null;
}