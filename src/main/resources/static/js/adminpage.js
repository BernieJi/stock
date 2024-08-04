$(document).ready(function(){	

	// 取得用戶資訊
	let jwt = sessionStorage.getItem("Authorization");

	Vue.createApp({
		data:function(){
			return {
				userList:[],
			}
		},
		methods :{
			getAllUsersInfo(){
				axios.get('/api/v1/admin/user/rawdata/all',{
					headers:{
						'Authorization' : jwt
					}
				})
					.then(res =>{
						this.userList = res.data.data
					})
					.catch(
						console.log('axios 發生錯誤惹...')
					)	
			},
			authorizeUser(username){
				axios.put('/api/v1/admin/'+ username +'/authority/give',{
					headers:{
						'Authorization' : jwt
					}
				})
					.then(res =>{
						alert('賦予權限成功');
						this.getAllUsersInfo();
					})
					.catch(
						console.log('axios 發生錯誤惹...')
					)	
			},
			deleteUser(username){
				axios.delete('/api/v1/admin/'+ username +'/delete',{
					headers:{
						'Authorization' : jwt
					}
				})
					.then(res =>{
						alert('刪除使用者成功');
						this.getAllUsersInfo();
					})
					.catch(
						console.log('axios 發生錯誤惹...')
					)		
			}							
		},
		mounted:function(){
			this.getAllUsersInfo();
			
		}
	}).mount('#app');

});