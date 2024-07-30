<script setup>
import { RouterLink, RouterView } from 'vue-router'
import HelloWorld from './components/HelloWorld.vue'
import axios from 'axios';
import { reactive } from 'vue';

let jwt = ''
const data = reactive({});
const getInfo = (()=>{
  axios.get('/api/api/v1/user/rawdata/boin',{
					headers:{
						'Authorization' : jwt
					}
				})
					.then(res =>{
            Object.assign(data, res.data.data);
					})
					.catch(
						console.log('axios 發生錯誤惹...')
					)	
})


</script>

<template>
  <header>

    <button class="border-2 text-red-700 p-2" @click="getInfo">按一下</button>
    
    <img alt="Vue logo" class="logo" src="@/assets/logo.svg" width="125" height="125" />

    <div class="wrapper">
      <HelloWorld msg="You did it!" />

      <nav>
        <RouterLink to="/">Home</RouterLink>
        <RouterLink to="/about">About</RouterLink>
      </nav>
    </div>
  </header>

</template>

<style scoped>
</style>
