/* 헤더 광고 */
let carouselItems = Array.from(document.querySelectorAll('.carousel-item'));
let radioButtons = Array.from(document.querySelectorAll('.carousel-radio'));
let currentIndex = 0;
let intervalId = setInterval(nextImage, 3000); // 3초마다 이미지 변경

document.getElementById('prev').addEventListener('click', prevImage);
document.getElementById('next').addEventListener('click', nextImage);
radioButtons.forEach((radio, index) => {
    radio.addEventListener('change', () => {
        setCurrentImage(index);
    });
});

function setCurrentImage(index) {
    carouselItems[currentIndex].classList.remove('active');
    radioButtons[currentIndex].checked = false;
    currentIndex = index;
    carouselItems[currentIndex].classList.add('active');
    radioButtons[currentIndex].checked = true;
    clearInterval(intervalId);
    intervalId = setInterval(nextImage, 3000);
}

function prevImage() {
    let index = currentIndex - 1 < 0 ? carouselItems.length - 1 : currentIndex - 1;
    setCurrentImage(index);
}

function nextImage() {
    let index = currentIndex + 1 >= carouselItems.length ? 0 : currentIndex + 1;
    setCurrentImage(index);
}

/* 상품 목록 */
const images = [
    {url: 'image1.jpg', alt: 'Image 1', author: 'Author 1', price: 100},
    {url: 'image2.jpg', alt: 'Image 2', author: 'Author 2', price: 200},
    {url: 'image3.jpg', alt: 'Image 3', author: 'Author 3', price: 300},
    {url: 'image4.jpg', alt: 'Image 4', author: 'Author 4', price: 400},
    {url: 'image5.jpg', alt: 'Image 5', author: 'Author 5', price: 500},
    {url: 'image6.jpg', alt: 'Image 6', author: 'Author 6', price: 600},
    {url: 'image7.jpg', alt: 'Image 7', author: 'Author 7', price: 400},
    {url: 'image8.jpg', alt: 'Image 8', author: 'Author 8', price: 500},
    {url: 'image9.jpg', alt: 'Image 9', author: 'Author 9', price: 600},
    // 이미지 데이터를 추가하세요.
];

images.forEach(image => {
    const gridItem = document.createElement('div');
    gridItem.classList.add('grid-item');

    gridItem.innerHTML = `
        <img src="${image.url}" alt="${image.alt}">
        <div class="product-info">
            <h2>${image.author}</h2>
            <p>${image.likes} likes</p>
        </div>
    `;

   document.getElementById('imageGrid').appendChild(gridItem);
});