$(document).ready(function(){
	
	// 首頁側邊欄動畫效果
	$("#collapse").on("click",function(){
		$("#sidebar").toggleClass("active");
		$(".fa-align-left").toggleClass("fa-chevrons-right");
	})

	// 取得用戶資訊
	let jwt = sessionStorage.getItem("Authorization");
	let username = sessionStorage.getItem("username")

	axios.get('/api/v1/user/rawdata/' + username,{
		headers:{
			'Authorization' : jwt
		}
	})
		.then(res =>{
			let userId = res.data.data.id;
			sessionStorage.setItem("userId",userId);
			let nameSpanTag = document.getElementsByTagName("span")[0];
			let authoritySpanTag = document.getElementsByTagName("span")[1];
			let timeSpan = document.getElementsByTagName("span")[2];
			nameSpanTag.innerHTML = res.data.data.username;
			authoritySpanTag.innerHTML = res.data.data.role;
			timeSpan.innerHTML = new Date().toLocaleString();
		})
		.catch(
			console.log('axios 發生錯誤惹...')
		)
})