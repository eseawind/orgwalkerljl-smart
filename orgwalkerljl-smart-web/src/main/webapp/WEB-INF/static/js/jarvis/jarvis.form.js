/**
 * JARVIS.FORM 组件
 * 
 * @author lijunlin
 */
JARVIS.registerNamespace("JARVIS.FORM");
(function(){
	
	/**
	 * ajax方式提交
	 */
	JARVIS.FORM.postAjax = function(formObj, callback) {
		$(formObj).validate( {
	        submitHandler : function(form) {
	        	JARVIS.FORM.postAjaxForm($(formObj), callback);  
	        }    
	    });
		$(formObj).submit();
	};
	
	/**
	 * ajax提交表单
	 */
	JARVIS.FORM.postAjaxForm = function(form, callback) {
		JARVIS.mask();
		var href = $(form).attr("action");
		$.ajax({
			url : href + ".json",
			type : $(form).attr("method"),
			data : $(form).serialize(),
			dataType : "json",
			success : function(response) {
				JARVIS.unmask();
				alert(response[JARVIS.RESPONSE_MESSAGE_KEY]);
				if (typeof(callback) == "function") {
					callback(response);
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				JARVIS.unmask();
				alert("操作失败");
			}
		});
	};
})();