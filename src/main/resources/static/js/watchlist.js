$(document).ready(function(){
	

	Vue.createApp({
		data:function(){
			return {				
				followedStock:[],
			}
		},
		methods:{
			getFollowedStocks:function(){				
				let userId = sessionStorage.getItem("userId")	
				let serviceURL = '/api/v1/watchlist/rawdata/' + userId
				axios.get(serviceURL,{
					headers:{
						'Authorization' : sessionStorage.getItem("Authorization")
					}
				})
				.then((response)=>{
				console.log(response.data.data);
				this.followedStock = response.data.data;
				}
				)	
			},
			deleteStockFromWatchlist(code){	
				console.log('code is',code);
				let serviceURL = '/api/v1/watchlist/rawdata/delete'
				axios.delete(serviceURL,{
					data:{
						userId: sessionStorage.getItem("userId"),
						stockCode: code,
					},
					headers:{
						'Authorization' : sessionStorage.getItem("Authorization")
					}
				})
					.then((response)=>{
						console.log(response.data.data);
						this.getFollowedStocks();
					}
				)	
			},
			
			
		},
		mounted:function(){
			this.getFollowedStocks();
		}
	}).mount('#app');

});		
			

		
