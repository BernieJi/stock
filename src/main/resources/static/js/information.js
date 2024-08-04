$(document).ready(function(){

   
    Vue.createApp({
        data:function(){
            return {
                user:{id:'', username:'', password:'', email:'', imageUrl:'', authority:''},
                previewImageUrl:'',
                uploadImageUrl:'',
                selectedFile: null,
                isFileSelected: false,
            }
        },
        methods :{
            query(){
                let username = sessionStorage.getItem("username");
                var serviceURL = '/api/v1/user/rawdata/'+ username;
                axios.get(serviceURL,{
                    headers:{
                        'Authorization' : sessionStorage.getItem("Authorization")
                    }
                })
                    .then((response) => {
                    console.log(response)
                    this.user.id = response.data.data.id;
                    this.user.username = response.data.data.username;
                    this.user.password = response.data.data.password;
                    this.user.email = response.data.data.email;
                    this.user.imageUrl = "https://storage.cloud.google.com/" + response.data.data.imageUrl;
                    }
                )	
            },

            async update(){
                // 有未上傳的圖片須先上傳
                if (this.isFileSelected) {
                    await this.uploadFile();
                }
                var serviceURL = '/api/v1/user/update/'+ this.user.username;
                
                let updateDTO = {
                    email: this.user.email,
                    uploadFileUrl: this.uploadImageUrl,
                }
                axios.put(serviceURL, updateDTO, {
                        headers:{
                            'Authorization' : sessionStorage.getItem("Authorization"),
                            'Content-Type': 'multipart/form-data',	
                        }
                    })
                .then((response) => {
                    alert('編輯成功！');
                    this.query();
                     
                })
                .catch((error) => {
                    console.log(error);
                });
            },

            previewImage(event) {
                const file = event.target.files[0];
                if (file) {
                  this.previewImageUrl = URL.createObjectURL(file);
                  console.log('預覽的url為',this.previewImageUrl)
                  this.selectedFile = file;
                  this.isFileSelected = true;
                }
            },

            async uploadFile() {
                if (this.selectedFile) {
                    const formData = new FormData();
                    formData.append('file', this.selectedFile);
                    formData.append('folder', 'image/person');
            
                    await axios.post('/upload', formData, {
                        headers: {
                            'Authorization': sessionStorage.getItem("Authorization"),
                            'Content-Type': 'multipart/form-data',
                        }
                    })
                    .then((res) => {
                        this.uploadImageUrl = res.data.message;

                    })
                    .catch(error => {
                        console.error('Error:', error);
                        alert('File upload failed.');
                    });
                }
            }
        },
        mounted:function(){
            this.query();	
        }
    }).mount('#app');

})