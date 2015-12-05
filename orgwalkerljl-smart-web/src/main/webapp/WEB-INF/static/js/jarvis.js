/**
 * JARVIS组件核心
 * @author cdlijunlin
 */
//定义JS命名空间，防止js冲突
var Namespace = Namespace||new Object();
Namespace.register = function(path){var arr = path.split("."),ns = "";;for(var i=0;i<arr.length;i++){if(i>0){ns += ".";}ns += arr[i];eval("if(typeof(" + ns + ")=='undefined'){" + ns + " = new Object();}");}};
Namespace.register("JARVIS");
(function(){
	
	JARVIS.registerNamespace = function(namespace) {
		Namespace.register(namespace);
	};
	
	JARVIS.systemName = "统一身份系统";
	JARVIS.contextPath = "";
	JARVIS.currentUrl = "";
	JARVIS.objectIdentifer = "";
	JARVIS.mainContent = "main-content";
	JARVIS.RESPONSE_STATUS_KEY = "REQ_FLAG";
	JARVIS.RESPONSE_MESSAGE_KEY = "REQ_MSG";
	JARVIS.RESPONSE_DATA_KEY = "REQ_DATA";
	
	/** 启用状态*/
	JARVIS.STATUS_ENABLED = "1";
	/** 停用状态*/
	JARVIS.STATUS_DISABLED = "2";
	/** 删除状态*/
	JARVIS.STATUS_DELETED = "3";
	
	//开启遮罩
	JARVIS.mask = function(info) {
		if($("#winModal,#loadInfo").length == 0) {var msg = (info != null && info.trim() != "") ? info:"系统正在为您处理数据,请稍候...";$("body").append("<div id='winModal'></div><div id='loadInfo'>"+msg+"</div>");}
	},
	
	//关闭遮罩
	JARVIS.unmask = function() {
		$("#winModal,#loadInfo").remove();
	},
	
	//动态加载页面
	JARVIS.loadPage = function(url, data, target) {
		JARVIS.mask();
		$.post(url, data, function(html) {
			$("#"+target).html(html);
			JARVIS.unmask();
		});
	};
	
	/**
	 * 重新加载页面(默认加载到主框架)
	 */
	JARVIS.loadPageToMainFrame = function(url, params) {
		JARVIS.loadPageToContainer(JARVIS.mainContent, url, params);
	};
	
	/**
	 * 重新加载容器内容
	 */
	JARVIS.loadPageToContainer = function(container, url, params){
		JARVIS.mask();
		jQuery.post(url, params, function(response) {
			try {
				$("#"+container).html(response);
			} catch(e) {
				
			}
			JARVIS.unmask();
			JARVIS.initPage();
		});
	};
	
	//刷新页面
	JARVIS.replace = function(url) {
		window.location.replace(url);
	};
	
	JARVIS.validateText = function(text) {
		return text == null || text == "undefined" || text == "" || text.length == 0;
	};
	
	JARVIS.isValidText = function(text) {
		return !JARVIS.validateText(text);
	};
	
	JARVIS.isInvalidText = function(text) {
		return JARVIS.validateText(text);
	};
	
	JARVIS.stringIsEmpty = function(string) {
		return JARVIS.validateText(string);
	};
	
	JARVIS.stringIsNotEmpty = function(string) {
		return !JARVIS.stringIsEmpty(string);
	};
	
	JARVIS.objectIsNull = function(obj) {
		return obj == null || obj == "undefined";
	};
	
	//ajax提交表单
	JARVIS.postAjaxForm = function(form, callback) {
		JARVIS.mask();
		var href = $(form).attr("action");
		$.ajax({
			url : href+".json",
			type : $(form).attr("method"),
			data : $(form).serialize(),
			dataType : "json",
			success : function(response) {
				JARVIS.unmask();
				if (typeof(callback) == "function") {
					callback(response);
				} else {
					alert(response[JARVIS.RESPONSE_MESSAGE_KEY]);
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				JARVIS.unmask();
				alert("操作失败");
			}
		});
	};
	
	/**
	 * ajax请求数据
	 * @param url:请求路径(注意url不要带后缀:比如.json或 .htm)
	 * @param data:请求参数({key:val,key1:val1})
	 * @param isMask:是否开启遮罩(true:开启,false:不开启)
	 * @param callBack:回调函数(参数data:服务器返回的数据JSON格式)
	 */
	JARVIS.reqAjax = function(url, data, isMask, callBack) {
		if(isMask){
			JARVIS.mask();
		}
		$.ajax({url : url+".json", type : "POST", data : data, dataType : "json",
			success : function(response) {
				JARVIS.unmask();
				if (typeof(callBack) == "function") {
					callBack(response);
				} else {
					alert(response[JARVIS.RESPONSE_MESSAGE_KEY]);
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				JARVIS.unmask();
				alert("操作失败");
			}
		});
	};
	
	//全选反选
	JARVIS.checkAll = function(chk, key) {
		if ($(chk).attr("checked") == "checked") {
			$("input[name="+key+"]").attr("checked", "checked");
		} else {
			$("input[name="+key+"]").removeAttr("checked");
		}
	};
	
	//获取所有选中checkbox的值
	JARVIS.getCheckedValues = function(chk) {
		var selects = $("input[name="+chk+"]:checked");
		var len = selects.length;
		if (selects == null || len == 0) {
			return "";
		}
		var ids = "";
		if (len > 0) {
			ids += $(selects[0]).val();
		}
		for (var i = 1; i < len; i++) {
			ids += "," + $(selects[i]).val();
		}
		return ids;
	};
	
	/**
	 * 获取指定Input对象选中的值
	 */
	JARVIS.getInputCheckedValues = function(inputs) {
		if (inputs == null || inputs.length == 0) {
			return "";
		}
		var len = inputs.length;
		var checkedValues = "";
		if (len > 0) {
			checkedValues += $(inputs[0]).val();
		}
		for (var i = 1; i < len; i++) {
			checkedValues += "," + $(inputs[i]).val();
		}
		return checkedValues;
	};
	
	//初始化页面
	JARVIS.initPage = function() {
		try {
			JARVIS.CONTROLLER.init();
			JARVIS.CONTROLLER.URL.init();
			//初始化执行通过span定义的函数;
			$("SPAN.script").each(function(i,obj) {
				eval($(obj).attr("page-load"));
				if ($(obj).attr("firstInit") != "false") {
					$(obj).remove();
				}
			});
		} catch(e) {
			window.alert("页面初始化失败,详细:" + e);
		}
	};
})();

//页面初始化
$(document).ready(function() {
	JARVIS.contextPath = $("#contextPath").val();
	JARVIS.currentUrl = $("#currentUrl").val();
	JARVIS.objectIdentifer = $("#objectIdentifer").val();
	JARVIS.initPage();
});