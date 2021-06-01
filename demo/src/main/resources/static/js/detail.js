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

function guid() {
	return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = (Math.random() * 16) | 0;
        var v = (c === 'x') ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

function searchUser(){

	$.post('searchUser', {"PERSON_ID" : p_id}, function(data){
		
		var detail	= data.detail;
		var files	= data.files.list;
		var fileArr = '';
		
		for(var i = 0; i<files.length; i++){
			
			var file			= files[i];
			var FILE_ID			= file['FILE_ID'];
			var ORN_FILE_NM		= file['OGN_FILE_NM'];
			var url 			= "fileDownload/" + p_id +"/"+FILE_ID+"______"+ORN_FILE_NM;
			var uuid 			= guid();

			fileArr	+='<div class="col-4" delId="' + uuid + '">'
					+ '	<div class="card card-default mb-1">'
					+ '		<div class="card-body row">'
					+ '			<div class="col-9">'
					+ '				<a href="'+url+'">' + ORN_FILE_NM + '</a>'
					+ '			</div>'
					+ '			<div class="col-2" float="right">'
					+ '				<a class="text-right" delId="' + uuid + '" name="fileDelBtn" href="javascript:;">삭제</a>'
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
	
	$.post('updateUser', param, function(data){
		
		var result = data.result;
		
		if(result > 0){
			alert('수정이 완료되었습니다!');
		}else{
			alert('수정 실패!');
		}
	});
}

function deleteUser(){
	
	$.post('deleteUser', {"PERSON_ID" : p_id}, function(data){
		
		var result = data.result;
		
		if(result > 0){
			location.href ="main";
			alert('삭제 완료');
		}else{
			alert('삭제 실패!');
		}
	});
}
