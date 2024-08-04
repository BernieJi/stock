$(document).ready(function(){	

	Vue.createApp({
		data:function(){
			return {
				lastStockDate:'',
				totalStockCount:0,
				totalPages:0,
				stockList:[],
				currentPage: 1,
				pageSize: 100 
			}
		},
		methods :{
			// 切換到上一頁
			prevPage:function() {
				if (this.currentPage > 1) {
					this.currentPage--;
				}
			},
			// 切換到下一頁
			nextPage:function() {
				if(this.currentPage < this.totalStockPages)
				this.currentPage++;
			},
			queryLastDate:function(){					
				let serviceURL = '/api/v1/stock/rawdata/date';
				axios.get(serviceURL,{
					headers:{
						'Authorization' : sessionStorage.getItem("Authorization")
					}
				})
					.then((response)=>{
						this.lastStockDate = response.data.data
					}
				)	
			},
			queryAll:function(currentPage, pageSize){					
				let serviceURL = '/api/v1/stock/rawdata?currentPage='+ this.currentPage + '&pageSize='+ this.pageSize;
				axios.get(serviceURL,{
					headers:{
						'Authorization' : sessionStorage.getItem("Authorization")
					}
				})
					.then((response)=>{
					// console.log(response);
					this.stockList = response.data.data
					}
				)	
			},
			queryTotalCount:function(){
				let serviceURL = '/api/v1/stock/rawdata/count';
				axios.get(serviceURL,{
					headers:{
						'Authorization' : sessionStorage.getItem("Authorization")
					}
				})
					.then((response)=>{
					// console.log('股票總數為:' + response);
					this.totalStockCount = response.data.data
					}
				)		
			},
			query:function(){
				let code = $("#myInput").val();
				console.log('輸入的值為:' + code);
				// 若為空值 直接返回查詢前的頁數
				if(!code){
					this.queryAll();
				}
				var serviceURL = '/api/v1/stock/rawdata/'+ code;
				axios.get(serviceURL,
					{
						headers:{
							'Authorization' : sessionStorage.getItem("Authorization")	
						}
					})
				.then((response)=>{
					console.log(response);
					if(response.status === 200){
						this.stockList.splice(0, this.stockList.length);
						this.stockList.push(response.data.data);
					} else {
						console.log('查詢錯誤 請聯絡管理員')
					}
				})
				.catch(function (error) {
					console.log(error);
				});
			}
		},
		computed:{
			totalStockPages:{
				get(){
					return Math.ceil(this.totalStockCount / this.pageSize);	
				}	
			}	
		},
		mounted:function(){
			this.queryLastDate();
			this.queryAll(1, 100);	
			this.queryTotalCount();
		}
	}).mount('#app');

});