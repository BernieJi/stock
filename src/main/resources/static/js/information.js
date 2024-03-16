$(document).ready(function(){

    Vue.createApp({
        data:function(){
            return {
                user:{id:'',username:'',password:'',email:'',authority:''}
            }
        },
        methods :{
            query:function(){
                let username = sessionStorage.getItem("username");
                var serviceURL = '/api/v1/user/rawdata/'+ username;
                axios.get(serviceURL,{
                    headers:{
                        'Authorization' : sessionStorage.getItem("Authorization")
                    }
                })
                    .then((response) => {
                    // console.log(response);
                    this.user.id = response.data.id;
                    this.user.username = response.data.username;
                    this.user.password = response.data.password;
                    this.user.email = response.data.email;
                    }
                )	
            },

            update:function(){
                var serviceURL = '/api/v1/user/update/'+ this.user.username;
                axios.put(serviceURL,
                    {email:this.user.email},
                    {
                        headers:{
                            'Authorization' : sessionStorage.getItem("Authorization")	
                        }
                    })
                .then(function (response) {
                     console.log(response);
                     if(response.status === 200){
                        alert('編輯成功！');
                     } else {
                        alert('編輯錯誤 請聯絡管理員')
                     }
                })
                .catch(function (error) {
                    console.log(error);
                });
            }
        },
        mounted:function(){
            this.query();	
        }
    }).mount('#app');

})