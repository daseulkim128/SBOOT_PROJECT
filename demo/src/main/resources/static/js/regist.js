var bufferFiles = [];

$(function() {
	var regist = {
		init : function() {
			this.setEvent();
		},
		setEvent : function() {
			
			$('#registBtn').on('click', function(){
				registUser();
			});
			
			$('#fileBtn').on('click', function(){
				$('#testFile').trigger('click');
			});
			
			$('#testFile').on('change', function(){
				inputFileChange();
			});
		}
	};
	regist.init();
});

function guid() {
	return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = (Math.random() * 16) | 0;
        var v = (c === 'x') ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

function inputFileChange(){
	
	var selectFiles	= $('#testFile')[0].files;
	
	var fileInfo 	= '';
	   
	for(var i = 0; i < selectFiles.length; i++){
		var file = selectFiles[i];
		var name = file.name;
		var uuid = guid();
				
		fileInfo +='<div class="col-4" delId="' + uuid + '">'
				+ '	<div class="card card-default mb-1">'
				+ '		<div class="card-body row">'
				+ '			<div class="col-8">'
				+ '				<a href="javascript:;">' + name + '</a>'
				+ '			</div>'
				+ '			<div class="col-2" float="right">'
				+ '				<a class="text-right" name="fileDelBtn" href="javascript:;">삭제</a>'
				+ '			</div>'
				+ '		</div>'
				+ '	</div>'
				+ '</div>'		
		 		
		file['uuid']  = uuid;
		bufferFiles.push(file);
	}
	
	$('#file-append').show();
	$('#file-append').append(fileInfo);
}

function registUser(){
	
	var LAST_NAME	= $('#LAST_NAME').val();
	var FIRST_NAME	= $('#FIRST_NAME').val();
	var ADDRESS		= $('#ADDRESS').val();
	var HEIGHT		= $('#HEIGHT').val();
	var AGE			= $('#AGE').val();
	var CITY		= $('#CITY').val();
	var formData 	= new FormData();
	
	for(var i = 0; i < bufferFiles.length ; i++){
		var file = bufferFiles[i];
		
		formData.append('files',file);
	}
	
	formData.append('LAST_NAME'	, LAST_NAME);
	formData.append('FIRST_NAME', FIRST_NAME);
	formData.append('ADDRESS'	, ADDRESS);
	formData.append('HEIGHT'	, HEIGHT);
	formData.append('AGE'		, AGE);
	formData.append('CITY'		, CITY);
	
	$.ajax({
		url			: 'registUser',
		data		: formData,
		dataType	: 'JSON',
		processData	: false,
		contentType	: false,
		type		: 'POST',
		success		: function(data, textStatus, request){
			if(data.result > 0){
				alert('성공적으로 등록되었습니다.');
				location.href ="detail?p_id="+data.PERSON_ID;
			}
		},
		error		: function(jqXHR, textStatus, errorThrown) {
			alert("실패");
		} 
	});
}



