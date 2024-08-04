<script setup>
import { ref } from 'vue'
import { RouterLink, RouterView } from 'vue-router'

// 先查詢是否已登入狀態 TODO
const isMember = ref(true)

// 手機版頁面是否展開
const isMenuOpen = ref(false)

const clickMenu = () => {
  isMenuOpen.value = !isMenuOpen.value
}
</script>

<template>
  <div class="w-full mx-auto my-0 border-b border-amber-400">
    <nav class="w-full bg-custom-color flex justify-between items-center">
      <div class="m-5">
        <RouterLink to="/about">
          <img src="../assets/image/NavBar/logo.png" alt="logo" class="w-10 h-10" />
        </RouterLink>
      </div>
      <ul class="hidden md:flex justify-between items-center">
        <li class="m-5">
          <RouterLink to="/about" class="text-[#6F6D55] font-bold">平台介紹</RouterLink>
        </li>
        <li v-if="isMember" class="m-5">
          <RouterLink to="/record" class="text-[#6F6D55]">我的紀錄</RouterLink>
        </li>
        <li class="m-5">
          <RouterLink to="/randomfood" class="text-[#6F6D55]">今天吃什麼</RouterLink>
        </li>
        <li class="m-5">
          <RouterLink to="/article" class="text-[#6F6D55]">美食文章</RouterLink>
        </li>
        <li v-if="isMember" class="m-5">
          <RouterLink to="/" class="text-[#6F6D55]">登出</RouterLink>
        </li>
        <li v-else class="m-5">
          <RouterLink to="/login" class="text-[#6F6D55]">登入/註冊</RouterLink>
        </li>
        <li v-if="isMember" class="m-5">
          <RouterLink to="/friendlist">
            <img src="../assets/images/NavBar/Users.png" alt="users" />
          </RouterLink>
        </li>
        <li v-if="isMember" class="m-5">
          <RouterLink to="/">
            <img src="../assets/images/NavBar/UserCircle.png" alt="personal" />
          </RouterLink>
        </li>
        <li v-if="isNotice" class="m-5 h-auto">
          <RouterLink to="/friend">
            <img src="../assets/images/NavBar/BellRinging.png" alt="alert" />
          </RouterLink>
        </li>
      </ul>
      <div class="m-5 md:hidden">
        <button v-if="!isMenuOpen" @click="clickMenu">
          <img src="../assets/images/NavBar/list.png" alt="list" />
        </button>

        <button v-else @click="clickMenu">
          <img
            src="../assets/images/NavBar/close.png"
            alt="close"
            style="width: 30px; height: 30px"
          />
        </button>
      </div>
    </nav>

    <div class="w-auto h-auto">
      <!-- 手機版才會有的清單列 -->
      <!-- 遮罩 -->
      <div
        v-if="isMenuOpen"
        class="bg-[#000000] opacity-20 fixed top-1/5 left-0 w-2/3 h-screen z-50"
      ></div>

      <transition name="sidebar">
        <div
          v-if="isMenuOpen"
          class="bg-custom-color fixed top-1/5 right-0 w-1/3 h-screen z-50 md:hidden"
        >
          <ul class="h-full">
            <li
              v-if="!isMember"
              class="m-5 flex text-center cursor-pointer hover:bg-custom-hover-color"
            >
              <RouterLink to="/login" class="text-[#6F6D55] text-center">登入/註冊</RouterLink>
            </li>
            <li v-if="isNotice" class="m-5 text-center bg-custom-alert-color">
              <RouterLink to="/friend" class="text-[#6F6D55]">
                好友送美食來囉
                <img src="../assets/images/NavBar/Cheers.png" class="inline" alt="cheer" />
              </RouterLink>
            </li>
            <li class="m-5 text-center cursor-pointer hover:bg-custom-hover-color">
              <RouterLink to="/about" class="text-[#6F6D55] transition-none"> 平台介紹 </RouterLink>
            </li>
            <li v-if="isMember" class="m-5 text-center cursor-pointer hover:bg-custom-hover-color">
              <RouterLink to="/record" class="text-[#6F6D55]"> 我的紀錄 </RouterLink>
            </li>
            <li class="m-5 text-center cursor-pointer hover:bg-custom-hover-color">
              <RouterLink to="/" class="text-[#6F6D55]"> 今天吃什麼 </RouterLink>
            </li>
            <li class="m-5 text-center cursor-pointer hover:bg-custom-hover-color">
              <RouterLink to="/article" class="text-[#6F6D55]"> 美食文章 </RouterLink>
            </li>
            <li class="m-5 text-center cursor-pointer hover:bg-custom-hover-color">
              <RouterLink to="/friendlist" class="text-[#6F6D55]"> 我的好友 </RouterLink>
            </li>
            <li class="m-5 text-center cursor-pointer hover:bg-custom-hover-color">
              <RouterLink to="/info" class="text-[#6F6D55]"> 個人資料 </RouterLink>
            </li>
            <li v-if="isMember" class="m-5 text-center cursor-pointer hover:bg-custom-hover-color">
              <RouterLink to="/logout" class="text-[#6F6D55]"> 登出 </RouterLink>
            </li>
          </ul>
        </div>
      </transition>
    </div>
  </div>
</template>

<style scoped>
</style>
