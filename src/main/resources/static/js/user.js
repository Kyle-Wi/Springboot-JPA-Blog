let index = {
    init: function(){
        $("#btn-save").on("click",()=>{
            this.save();
        }); // id가 btn-save인 부분을 찾아서 click을 해주면 
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
        $.ajax({
            // 회원가입 요청
            type: "POST",
            url: "/api/user", // 뒤에 join 안해도 post이고 user라는 것을 보면 insert라는 것을 알기 때문에.
            data: JSON.stringify(data),  // http body 데이터
            contentType:"application/json; charset=utf-8", // body 데이터가 어떤 type인지
            dataType: "json" // response
        }).done(function(){
            // 성공하면 실행하는 함수
        }).fail(function(){
            // 실패하면 실행하는 함수
        }); // ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청을 할 것.
    }

    
}


index.init()