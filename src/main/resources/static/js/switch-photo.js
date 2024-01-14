document.addEventListener('DOMContentLoaded', function () {
    let imagesId; // Объявление переменной imagesId в более высокой области видимости
    let currentImageIndex = 0;

    function viewProductImages(productId) {
        $.ajax({
            url: '/product/' + productId + '/images',
            method: 'GET',
            success: function (data) {
                imagesId = data; // Присваиваем значение переменной imagesId
                updateImage();
            },
            error: function (error) {
                console.error('Произошла ошибка:', error);
            }
        });
    }

    function updateImage() {
        if (imagesId && currentImageIndex >= 0 && currentImageIndex < imagesId.length) {
            const imageElement = document.getElementById('product-image');
            const imageUrl = "/images/" + imagesId[currentImageIndex];
            imageElement.src = imageUrl;
        }
    }

    function showPreviousImage() {
        currentImageIndex = (currentImageIndex - 1 + imagesId.length) % imagesId.length;
        updateImage();
    }

    function showNextImage() {
        currentImageIndex = (currentImageIndex + 1) % imagesId.length;
        updateImage();
    }

    // Находим все элементы с классом "product" и извлекаем data-product-id
    const productContainer = document.querySelectorAll(".product-container");
    productContainer.forEach(product => {
        const productId = product.getAttribute('data-product-id');
        console.log(productId)
        // Вызываем функцию viewProductImages с productId
        viewProductImages(productId);
    });

    // Получение HTML-элементов и установка обработчиков событий
    const prevButton = document.getElementById('prev-image');
    const nextButton = document.getElementById('next-image');

    prevButton.addEventListener('click', showPreviousImage);
    nextButton.addEventListener('click', showNextImage);
});