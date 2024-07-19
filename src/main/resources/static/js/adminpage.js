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
			
		},
		computed:{
			
		},
		mounted:function(){
			this.queryLastDate();
		}
	}).mount('#app');

});