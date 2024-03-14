$(document).ready(function(){

    // 登入
    let loginbButton = document.getElementById("login");
    loginbButton.addEventListener('click', function(event) {
        // 讓表單不要提交資料
        event.preventDefault();
        let username = document.getElementById("login_username").value;
        let password = document.getElementById("login_password").value;

        axios.post('/api/v1/auth/login', {
            username:username,
            password:password,
        })
            .then(function (response) {
                if (response.data.code === "200") {
                    let jwt = "Bearer " + response.data.token;
                    // 將jwt 儲存在localStorage
                    sessionStorage.setItem("Authorization",jwt);
                    sessionStorage.setItem("username",username);
                    alert("登入成功，將為您導入首頁！")
                    window.location.href = "/index";
                } else {
                    alert("登入失敗，請重新輸入帳密！")
                    window.location.href = "/loginpage";
                }
            })
            .catch(function (error) {
                // console.log("發生錯誤！" + JSON.stringify(error));
            });

    });

    // 註冊帳號
    let registerButton = document.getElementById("register");
    registerButton.addEventListener('click', function(event) {
        // 讓表單不要提交資料
        event.preventDefault();
        let username = document.getElementById("register_name").value;
        let password = document.getElementById("register_password").value;
        let email = document.getElementById("register_email").value;

        axios.post('/api/v1/auth/register', {
            id:1,
            username:username,
            password:password,
            email:email,
            role:"user"
        })
            .then(function (response) {
                if (response.data.code === "200") {
                    alert("註冊成功，請輸入帳號密碼登入！")
                    let jwt = "Bearer " + response.data.token;
                    // 將jwt 儲存
                    sessionStorage.setItem("Authorization",jwt);
                    sessionStorage.setItem("username", username);
                    // 畫面導回登入頁面
                    window.location.href = "/loginpage";
                } else {
                    alert("註冊失敗，請重新註冊！")
                    window.location.href = "/loginpage";
                }
            })
            .catch(function (error) {
                // console.log("發生錯誤！！" + JSON.stringify(error));
            });

    });

    // 檢查是否已有相同的username
    let input = document.getElementById("register_name");
    input.addEventListener('blur',function(){
        let username = input.value;
        axios.get('/check?username=' + username)
            .then(function (response) {
                let show = document.getElementById("check_name");
                if (response.data.code === "200") {
                    show.style.display = "inline";
                    show.textContent = "帳號可建立";
                } else {
                    show.style.display = "inline";
                    show.textContent = "帳號已存在，請嘗試其他帳號";
                }
            })
            .catch(function (error) {
                // console.log(error);
            });
    });

})