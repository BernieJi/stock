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
  <div class="w-full mx-auto my-0 border-b border-white">
    <nav class="w-full flex justify-between items-center bg-[#D1CCC4]">
      <!-- 首頁小圖 -->
      <div class="m-5">
        <RouterLink to="/about" class="flex items-center hover:bg-[#FFFFE0]">
          <img src="../assets/image/NavBar/logo.png" alt="logo" class="w-10 h-10 m-2" />
          <span class="text-black hover:font-bold">StockSphere</span>
        </RouterLink>
        
      </div>

      <!-- 導覽列(手機版狀態下隱藏) -->
      <ul class="hidden md:flex justify-between items-center">
        <li class="m-5">
          <RouterLink to="/about" class="text-black hover:bg-[#FFFFE0] hover:font-bold">平台介紹</RouterLink>
        </li>

        <li class="m-5">
          <RouterLink to="/stock" class="text-black hover:bg-[#FFFFE0] hover:font-bold">股票資訊</RouterLink>
        </li>

        <li class="m-5">
          <RouterLink to="/forum" class="text-black hover:bg-[#FFFFE0] hover:font-bold">討論區</RouterLink>
        </li>

        <li v-if="isMember" class="m-5">
          <RouterLink to="/watchlist" class="text-black hover:bg-[#FFFFE0] hover:font-bold">我的追蹤</RouterLink>
        </li>
        
        <li v-if="isMember" class="m-5">
          <RouterLink to="/logout" class="text-black hover:bg-[#FFFFE0] hover:font-bold">登出</RouterLink>
        </li>

        <li v-else class="m-5">
          <RouterLink to="/login" class="text-black hover:bg-[#FFFFE0] hover:font-bold">登入/註冊</RouterLink>
        </li>
        <li v-if="isMember" class="m-5">
          <RouterLink to="/info">
            <img src="../assets/image/NavBar/user.png" alt="user" class="w-10 h-10 hover:bg-[#FFFFE0]" />
          </RouterLink>
        </li>
       
      </ul>

      <!-- 手機版功能按鈕 -->
      <div class="m-5 md:hidden">
        <button v-if="!isMenuOpen" @click="clickMenu">
          <img src="../assets/image/NavBar/list.png" alt="list" />
        </button>

        <!-- 關閉手機版功能頁 -->
        <button v-else @click="clickMenu">
          <img
            src="../assets/image/NavBar/close.png"
            alt="close"
            style="width: 30px; height: 30px"
          />
        </button>
      </div>
    </nav>

    <!-- 手機版才會有的向下展開清單列 -->
    <div class="w-auto h-auto">

      <!-- 遮罩 -->
      <div
        v-if="isMenuOpen"
        class="bg-black opacity-20 fixed top-1/5 left-0 w-2/3 h-screen z-50"
      ></div>

        <div
          v-if="isMenuOpen"
          class="bg-[#D1CCC4] fixed top-1/5 right-0 w-1/3 h-screen z-50 md:hidden"
        >
          <ul class="h-full">
            <li
              v-if="!isMember"
              class="m-5 flex text-center cursor-pointer hover:bg-[#FFFFE0]"
            >
              <RouterLink to="/login" class="text-black text-center">登入/註冊</RouterLink>
            </li>
            
            <li class="m-5 text-center cursor-pointer hover:bg-[#FFFFE0]">
              <RouterLink to="/about" class="text-black hover:bg-[#FFFFE0]"> 平台介紹 </RouterLink>
            </li>

            <li class="m-5 text-center cursor-pointer hover:bg-[#FFFFE0]">
              <RouterLink to="/" class="text-black hover:bg-[#FFFFE0]"> 股票資訊 </RouterLink>
            </li>

            <li class="m-5 text-center cursor-pointer hover:bg-[#FFFFE0]">
              <RouterLink to="/article" class="text-black hover:bg-[#FFFFE0]"> 討論區 </RouterLink>
            </li>

            <li v-if="isMember" class="m-5 text-center cursor-pointer hover:bg-[#FFFFE0]">
              <RouterLink to="/record" class="text-black hover:bg-[#FFFFE0]"> 我的追蹤 </RouterLink>
            </li>
            
            <li class="m-5 text-center cursor-pointer hover:bg-[#FFFFE0]">
              <RouterLink to="/info" class="text-black hover:bg-[#FFFFE0]"> 個人資料 </RouterLink>
            </li>
            
            <li v-if="isMember" class="m-5 text-center cursor-pointer hover:bg-[#FFFFE0]">
              <RouterLink to="/logout" class="text-black hover:bg-[#FFFFE0]"> 登出 </RouterLink>
            </li>
          </ul>
        </div>

    </div>
  </div>
</template>

<style scoped>
</style>
