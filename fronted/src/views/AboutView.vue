<script setup>
    import { ref,onMounted,onUnmounted } from 'vue'
    import Footer from '../components/Footer.vue'

    const slides = ref([
        { content: '完整股票資訊', imgSrc: 'src/assets/image/Index/stock.jpg' },
        { content: '開放的討論區', imgSrc: 'src/assets/image/Index/forum.jpg' },
        { content: '模擬交易系統', imgSrc: 'src/assets/image/Index/trade.jpg' },
    ]);

    const currentIndex = ref(0);
    let interval;

    const nextSlide = () => {
        currentIndex.value = (currentIndex.value + 1) % slides.value.length;
    };

    const prevSlide = () => {
        currentIndex.value = (currentIndex.value - 1 + slides.value.length) % slides.value.length;
    };

    onMounted(() => {
        interval = setInterval(nextSlide, 7000);
    });

    onUnmounted(() => {
        clearInterval(interval);
    });
</script>

<template>
    <div class="w-full mx-auto my-0 bg-white">
        <h1 class="text-black font-bold">歡迎來到股動未來！！！</h1>

        <!-- carousel 區域 -->
        <div class="carousel relative w-3/4 h-96 mx-auto">
            <div class="carousel-inner relative w-full h-full overflow-hidden">
            <div
                v-for="(slide, index) in slides"
                :key="index"
                class="carousel-item absolute inset-0 transition-opacity duration-1000 ease-in-out"
                :class="{ 'opacity-0': currentIndex !== index, 'opacity-100': currentIndex === index }"
            >
                <div class="h-full w-3/4 mx-auto">
                    <img :src="slide.imgSrc" class="w-80 h-80" alt="pic">
                    <h2 class="text-black text-3xl text-center">{{ slide.content }}</h2>
                    <p class="text-black">Lorem ipsum dolor, sit amet consectetur adipisicing elit. Assumenda illo asperiores quasi quidem velit ex facere minima suscipit nesciunt a?</p>
                </div>
            </div>
            </div>
            <!-- Navigation buttons -->
            <button @click="prevSlide" class="prev absolute left-0 top-1/2 transform -translate-y-1/2 w-10 h-10 ml-2 cursor-pointer text-3xl font-bold text-white bg-black rounded-full z-10 flex items-center justify-center">‹</button>
            <button @click="nextSlide" class="next absolute right-0 top-1/2 transform -translate-y-1/2 w-10 h-10 mr-2 cursor-pointer text-3xl font-bold text-white bg-black rounded-full z-10 flex items-center justify-center">›</button>
        </div>
        
        <!-- Footer區域 -->
        <Footer/>
    
    </div>

    
    


</template>

<style scoped>
    .carousel-item {
        opacity: 0;
        transition: opacity 1s ease-in-out;
    }
    .carousel-item.opacity-100 {
        opacity: 1;
    }
</style>
