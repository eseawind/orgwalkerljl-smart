/**
 * JARVIS.FORM 组件
 * @author cdlijunlin
 */
JARVIS.registerNamespace("JARVIS.FORM");
(function(){
	//ajax方式提交
	JARVIS.FORM.postAjax = function(formObj, callback) {
		$(formObj).validate( {
	        submitHandler : function(form) {
	        	JARVIS.postAjaxForm($(formObj), callback);  
	        }    
	    });
		$(formObj).submit();
	};
})();