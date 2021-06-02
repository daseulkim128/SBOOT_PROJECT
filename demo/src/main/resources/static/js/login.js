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
	
	$.post('checkId', {MEM_ID : id} , function (data){
		
		var result = data.result['RESULT'];
		
		if(result == 1){
			
			$.post('loginMember', param, function(data){
				var result2 = data['RESULT'];
				
				if(result2 == 1){
					location.href="home";
				}else{
					alert('비밀번호를 일치하지 않습니다');
				}
			});
		}else{
			alert('등록되지 않은 아이디 입니다');
		}
	});
}
