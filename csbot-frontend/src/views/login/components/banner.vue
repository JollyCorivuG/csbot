<template>
    <div class="banner">
        <div class="banner-inner">
            <a-carousel class="carousel" animation-name="fade">
                <a-carousel-item v-for="(item, index) in carouselItem" :key="index">
                    <div :key="index" class="carousel-item">
                        <img class="carousel-image" :src="item.image"  alt="img"/>
                    </div>
                </a-carousel-item>
            </a-carousel>
        </div>
    </div>
</template>

<script setup lang="ts" >
import { computed } from 'vue';
import bannerImage from '@/assets/images/login-banner.png';

const carouselItem = computed(() => [
    {
        image: bannerImage,
    },
    {
        image: bannerImage,
    },
    {
        image: bannerImage,
    },
]);
</script>

<style lang="scss" scoped>
.banner {
    display: flex;
    align-items: center;
    justify-content: center;

    .banner-inner {
        flex: 1;
        height: 100%;
    }
}

.carousel {
    height: 100%;

    .carousel-item {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        height: 100%;
    }

    .carousel-image {
        width: calc(45%);
        margin-top: 30px;
    }
}
</style>