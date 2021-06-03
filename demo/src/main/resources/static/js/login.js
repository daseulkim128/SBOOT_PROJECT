$(function(){
	var login = {
		init : function(){
			this.setEvent();
		},
		setEvent : function (){
			$('#loginBtn').on('click', function(){
				loginMember();
			});
		}
	};
	login.init();
});

function loginMember(){
	
	var id 			= $('#id').val();
	var password	= $('#password').val();
	
	var param = {
		MEM_ID	: id,
		MEM_PWD	: password
	}
	
	$.post('loginMember', param, function(data){
		var result = data['RESULT'];
		
		if(result == 1){
			location.href="home";
		}else{
			alert('아이디나 비밀번호가 일치하지 않습니다');
		}
	});
}



