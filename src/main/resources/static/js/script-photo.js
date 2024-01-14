document.addEventListener('DOMContentLoaded', function () {
    const fileInputs = document.querySelectorAll('input[type="file"]');
    let currentImageIndex = 0; // Индекс текущей рамки

    fileInputs.forEach(function (fileInput, index) {
        fileInput.addEventListener('change', function (e) {
            const imagePreview = document.getElementById(`image-preview-${currentImageIndex}`);
            const file = e.target.files[0];

            if (file) {
                const reader = new FileReader();

                reader.onload = function (e) {
                    imagePreview.src = e.target.result;
                };

                reader.readAsDataURL(file);
            } else {
                imagePreview.src = '/image/photo-search.jpg';
            }

            // Увеличиваем индекс текущей рамки на 1
            currentImageIndex++;

            // Если текущий индекс выходит за пределы рамок, вернуться к началу
            if (currentImageIndex >= fileInputs.length) {
                currentImageIndex = 0;
            }
        });
    });
});
