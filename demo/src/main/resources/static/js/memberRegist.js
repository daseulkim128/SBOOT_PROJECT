$(function(){
	var memberRegist = {
		init : function(){
			this.setEvent();
		},
		setEvent : function (){
			$('#registBtn').on('click', function(){
				meberRegist();
			});
			
			$('#id').on('change', function(){
				
				$.post('checkId', {MEM_ID : $('#id').val()} , function (data){
					
					var result = data.result['RESULT'];
					
					if(result == 1){
						alert('이미 등록된 아이디 입니다');
					}else{
						alert('사용가능한 아이디 입니다');
					}
				});
			});
		}
	};
	memberRegist.init();
});

function blankCheck(json) {
	
	var f = true;
	
	for(var k in json) {
		
		var v = $('#' + k).val();
		
		if(v == undefined || v == '') {
			alert(json[k]);
			
			f = false;
			return f;
		}
	}
	
	return f;
}

function pwdCheck(a, b){
	
	var f = true;
	
	if(a!=b){
		f = false;
		return alert('동일한 비밀번호를 입력해주세요');
	}
	
	return f;
}

function meberRegist(){
	
	var flag				= false;
	var type 	 		 	= $('#member_type').val();
	var id 					= $('#id').val();
	var password		 	= $('#password').val();
	var confirm_password 	= $('#confirm_password').val();
	
	$.post('checkEmail', param, function (data){
		
	});
	
	var json = {
		member_type			: '회원 구분을 확인해주세요',  
		id					: '이름을 확인해주세요',
		password			: '비밀번호를 확인해주세요',
		confirm_password	: '비밀번호를 확인해주세요',
	};
	
	if(blankCheck(json)){
		if(pwdCheck(password, confirm_password)){
			flag = true;
		}
	}
	
	if(flag){

		var param = {
				MEM_TYPE	: type ,
				MEM_ID 		: id ,
				MEM_EMAIL 	: email ,
				MEM_PWD 	: password ,
		}
		
		$.post('insertMember', param, function (data){
			alert('성공적으로 회원가입이 완료되었습니다!');
			location.href = "login";
		});
	}
}
