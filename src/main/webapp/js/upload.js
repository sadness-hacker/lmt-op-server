function _autoUploadSubmit(id,callback,extra,allowType){
	var options = {
	   success : function(data){
		   callback(data,extra);
		   $('#auto-upload-form-id-' + id).remove();
	   },
	   timeout: 30000,               //限制请求的时间，当请求大于30秒后，跳出请求
	   error : function(){
		   console.log("文件上传出错啦！");
	   }
	};
	if(allowType != null && allowType != ''){
		var path = document.getElementById("auto-upload-field-id-" + id).value;
		var fileName = getFileName(path);
		if(!isAllowType(fileName,allowType)){
			alert("不允许的文件类型，请上传" + allowType + "类型的文件！");
			return;
		}
	}
	$('#auto-upload-form-id-' + id).ajaxSubmit(options);   
}

function _buildForm(id,url,fieldName){
	var html = '<form id="auto-upload-form-id-' + id + '" action="' + url + '" method="post" enctype="multipart/form-data" style="display:none;">'
				+ '<input type="file" name="' + fieldName + '" id="auto-upload-field-id-' + id + '" />'
				+ '</form>';
	$('body').append(html);
}

function autoUpload(fieldName,url,callback,extraParam,allowType){
	var id = new Date().getTime();
	_buildForm(id,url,fieldName);
	$('#auto-upload-field-id-' + id).on('change', function(){
		_autoUploadSubmit(id,callback,extraParam,allowType);
	});
	$("#auto-upload-field-id-" + id).click();
}

/**
 * 判断是否是允许的文件类型
 * @param path
 * @param allowType
 * @returns {Boolean}
 */
function isAllowType(path,allowType){
	var i = path.lastIndexOf(".");
	if(i < 0){
		return false;
	}else{
		var suffix = path.substring(i,path.length);
		i = allowType.indexOf(suffix);
		if(i > -1){
			return true;
		}
	}
}
/**
 * 获取表单file域文件名
 * @param path
 * @returns
 */
function getFileName(path){
	var pos1 = path.lastIndexOf('/');
	var pos2 = path.lastIndexOf('\\');
	var pos  = Math.max(pos1, pos2);
	if( pos<0 ){
		return path;
	}else{
		return path.substring(pos+1);
	}
}