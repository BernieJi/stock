$(document).ready(function(){
	
	// 取得股票代碼 ?code=0050
	let code = window.location.search.substring(6); 

	Vue.createApp({
		data:function(){
			return {
				lastStockDate:'',
				stock:{},
				historyDataList:[]
			}
		},
		methods:{
			query:function(){					
				let serviceURL = '/api/v1/stock/rawdata/' + code;
				axios.get(serviceURL,{
					headers:{
						'Authorization' : sessionStorage.getItem("Authorization")
					}
				})
					.then((response)=>{
					// console.log(response);
					this.stock = response.data.data
					}
				)	
			},
			queryHistory:function(){					
				let serviceURL = '/api/v1/stock/rawdata/history/' + code;
				axios.get(serviceURL,{
					headers:{
						'Authorization' : sessionStorage.getItem("Authorization")
					}
				})
					.then((response)=>{
					// console.log(response);
					this.historyDataList = response.data.data
					this.drawChart();
					}
				)	
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
			addStockToWatchlist:function(){					
				let serviceURL = '/api/v1/stock/addtowatchlist';
				axios.post(serviceURL,
					{
						userId: sessionStorage.getItem("userId"),
						// 這裡要再做修改
						watchlistName: '追蹤表1',
						code: code,
					},
					{
						headers:{
							'Authorization' : sessionStorage.getItem("Authorization")
						}
					}	
				)
					.then((response)=>{
					alert('加入追蹤成功！')
					console.log(response);
					console.log('加入追蹤成功！')
					}
				)	
			},
			drawChart: function() {
				google.charts.load('current', {'packages':['corechart']});
				var historyData = this.historyDataList;
				var stocksData = [];
				for(var i = 0; i < historyData.length ; i++){
					var secondStockData = [
							historyData[i].date.split(" ")[0],
							Number(historyData[i].lowestPrice),
							Number(historyData[i].openingPrice),
							Number(historyData[i].closingPrice),
							Number(historyData[i].highestPrice)
					];
					stocksData[historyData.length - i - 1] = secondStockData;
				}
				  google.charts.setOnLoadCallback(function(){
					var data = google.visualization.arrayToDataTable(stocksData, true);
					// chart setting
					var options = {
						legend: 'none',
						title: '走勢圖',	
						bar: { groupWidth: 50 },
						backgroundColor: '#FFEEDD',
						colors:['#000000','#004411'],
						chartArea:{
							backgroundColor:'#BEBEBE'
						},	
						candlestick: {
							hollowIsRising: true,
							fallingColor: {
								fill: '#a52714',
								stroke: '#000000',
								strokeWidth: 0
							},
							risingColor: { 
								fill: '#0f9d58',
								stroke: '#000000',
								strokeWidth: 0 
							}
						}
					};

					var chart = new google.visualization.CandlestickChart(document.getElementById('chart_div'));
					chart.draw(data, options);	
				});		
			}
		},
		mounted:function(){
			this.query();
			this.queryHistory();	
			this.queryLastDate();
		}
	}).mount('#app');

});		
			

		
