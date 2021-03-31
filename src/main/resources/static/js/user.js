let index = {
    init: function(){
        $("#btn-save").on("click",()=>{
            this.save();
        });
        $("#btn-update").on("click",()=>{
            this.update();
        });
        // $("#btn-login").on("click",()=>{
        //     this.login();
        // }); // id가 btn-save인 부분을 찾아서 click을 해주면 
    },

    save: function(){
        // alert("user save함수 호출");
    
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };
        // console.log(data)  잘 작동 됨
    

        // ajax는 기본적으로 비동기 호출
        // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
        // ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해줌.
        $.ajax({
            // 회원가입 요청
            type: "POST",
            url: "/auth/joinProc/", // 뒤에 join 안해도 post이고 user라는 것을 보면 insert라는 것을 알기 때문에.
            data: JSON.stringify(data),  // http body 데이터
            contentType:"application/json; charset=utf-8", // body 데이터가 어떤 type인지
            dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 buffer로 오기 때문에 string(생긴게 json이라면 ->javascript오브젝트로 변경해줌)
        }).done(function(resp){
            // 성공하면 실행하는 함수
            alert("회원가입이 완료되었습니다.");
            console.log(resp)
            location.href="/"; // 반응하면 바로 어디로 이동할지 설정해주는 것
        }).fail(function(error){
            // 실패하면 실행하는 함수
            alert(JSON.stringify(error)); // error 가 json으로 날라온다.
        }); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청을 할 것.
    },
    update: function(){
        // alert("user save함수 호출");
    
        let data = {
            id: $("#id").val(),
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        };
        // console.log(data)  잘 작동 됨
    

        // ajax는 기본적으로 비동기 호출
        // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
        // ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해줌.
        $.ajax({
            // 회원가입 요청
            type: "PUT",
            url: "/user", // 뒤에 join 안해도 post이고 user라는 것을 보면 insert라는 것을 알기 때문에.
            data: JSON.stringify(data),  // http body 데이터
            contentType:"application/json; charset=utf-8", // body 데이터가 어떤 type인지
            dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 buffer로 오기 때문에 string(생긴게 json이라면 ->javascript오브젝트로 변경해줌)
        }).done(function(resp){
            // 성공하면 실행하는 함수
            alert("회원정보가 수정되었습니다.");
            console.log(resp)
            location.href="/"; // 반응하면 바로 어디로 이동할지 설정해주는 것
        }).fail(function(error){
            // 실패하면 실행하는 함수
            alert(JSON.stringify(error)); // error 가 json으로 날라온다.
        }); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청을 할 것.
    },


    // login: function(){
    //     // alert("user save함수 호출");
    
    //     let data = {
    //         username: $("#username").val(),
    //         password: $("#password").val()
    //     };
        
    //     $.ajax({
    //         // 회원가입 요청
    //         type: "POST",
    //         url: "api/user/login", // 뒤에 join 안해도 post이고 user라는 것을 보면 insert라는 것을 알기 때문에.
    //         data: JSON.stringify(data),  // http body 데이터
    //         contentType:"application/json; charset=utf-8", // body 데이터가 어떤 type인지
    //         dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 buffer로 오기 때문에 string(생긴게 json이라면 ->javascript오브젝트로 변경해줌)
    //     }).done(function(resp){
    //         // 성공하면 실행하는 함수
    //         alert("로그인이 완료되었습니다.");
    //         console.log(resp);
    //         location.href="/"; // 반응하면 바로 어디로 이동할지 설정해주는 것
    //     }).fail(function(error){
    //         // 실패하면 실행하는 함수
    //         alert(JSON.stringify(error)); // error 가 json으로 날라온다.
    //     }); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청을 할 것.
    
    
}


index.init()