<script setup>
  import NavBar from './components/NavBar.vue'
  import axios from 'axios';
  import { reactive } from 'vue';

  // let jwt = ''
  // const data = reactive({});
  // const getInfo = (()=>{
  //   axios.get('/api/api/v1/user/rawdata/boin',{
  // 					headers:{
  // 						'Authorization' : jwt
  // 					}
  // 				})
  // 					.then(res =>{
  //             Object.assign(data, res.data.data);
  // 					})
  // 					.catch(
  // 						console.log('axios 發生錯誤惹...')
  // 					)	
  // })


</script>

<template>

  <div>
    <NavBar/>
    <RouterView />
  </div>
  
</template>

<style scoped>
</style>
