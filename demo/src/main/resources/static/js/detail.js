var params	= location.search.substr(location.search.indexOf("?") + 1);
var p_id	= params.split("=")[1];		

$(function() {
	var detail = {
		init : function() {
			this.setEvent();
			
			searchUser();
		},
		setEvent : function() {
			
			$('#updateBtn').on('click', function(){
				updateUser();
			});
			
			$('#deleteBtn').on('click', function(){
				deleteUser();
			});
		}
	};
	detail.init();
});

function searchUser(){

	$.post('/searchUser', {"PERSON_ID" : p_id}, function(data){
		
		var detail	= data.detail;
		var files	= data.files.list;
		var fileArr = '';
		
		for(var i = 0; i<files.length; i++){
			
			var file			= files[i];
			var FILE_ID			= file['FILE_ID'];
			var ORN_FILE_NM		= file['OGN_FILE_NM'];
			var url 			= "fileDownload/" + FILE_ID;

			fileArr	+='<div class="col-4">'
					+ '	<div class="card card-default mb-1">'
					+ '		<div class="card-body row">'
					+ '			<div class="col-9">'
					+ '				<a href="'+url+'">' + ORN_FILE_NM + '</a>'
					+ '			</div>'
					+ '		</div>'
					+ '	</div>'
					+ '</div>'
		}
		
		$('#file-append').show();
		$('#file-append').append(fileArr);
		$('#LAST_NAME').val(detail['LAST_NAME']);
		$('#FIRST_NAME').val(detail['FIRST_NAME']);
		$('#ADDRESS').val(detail['ADDRESS']);
		$('#AGE').val(detail['AGE']);
		$('#HEIGHT').val(detail['HEIGHT']);
		$('#CITY').val(detail['CITY']);
	});
}



function updateUser(){
	
	var param = {
		"PERSON_ID"		: p_id,
		"LAST_NAME" 	: $('#LAST_NAME').val(),
		"FIRST_NAME" 	: $('#FIRST_NAME').val(),
		"ADDRESS" 		: $('#ADDRESS').val(),
		"AGE" 			: $('#AGE').val(),
		"HEIGHT" 		: $('#HEIGHT').val(),
		"CITY" 			: $('#CITY').val() 
	}
	
	$.post('/updateUser', param, function(data){
		
		var result = data.result;
		
		if(result > 0){
			alert('수정이 완료되었습니다!');
		}else{
			alert('수정 실패!');
		}
	});
}

function deleteUser(){
	
	$.post('/deleteUser', {"PERSON_ID" : p_id}, function(data){
		
		var result = data.result;
		
		if(result > 0){
			location.href ="http://localhost:8888/main";
			alert('삭제 완료');
		}else{
			alert('삭제 실패!');
		}
	});
}
