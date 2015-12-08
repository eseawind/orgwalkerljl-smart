/**
 * JARVIS组件核心
 * 
 * @author lijunlin
 */
//定义JS命名空间，防止js冲突
var Namespace = Namespace||new Object();
Namespace.register = function(path){var arr = path.split("."),ns = "";;for(var i=0;i<arr.length;i++){if(i>0){ns += ".";}ns += arr[i];eval("if(typeof(" + ns + ")=='undefined'){" + ns + " = new Object();}");}};
Namespace.register("JARVIS");
(function(){
	
	JARVIS.registerNamespace = function(namespace) {
		Namespace.register(namespace);
	};
	
	JARVIS.systemName = "";
	JARVIS.contextPath = "";
	JARVIS.currentUrl = "";
	JARVIS.objectIdentifer = "";
	JARVIS.mainContent = "main-content";
	JARVIS.RESPONSE_STATUS_KEY = "status";
	JARVIS.RESPONSE_MESSAGE_KEY = "msg";
	JARVIS.RESPONSE_DATA_KEY = "body";
	JARVIS.ssoLoginAddress;
	
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
	JARVIS.loadPage = function(url, params, target) {
		JARVIS.mask();
		$.post(url, params, function(html) {
			$("#" + target).html(html);
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
	
	JARVIS.post = function(url, data, successCallback) {
		$.ajax({
			type : 'POST',
			url : url + ".json",
			data : data,
			dataType : 'json',
			success : function(response) {
				if (response[JARVIS.RESPONSE_DATA_KEY] == "notLogin") {
					window.location.href = JARVIS.ssoLoginAddress;
				} else {
					alert(response[JARVIS.RESPONSE_MESSAGE_KEY]);
					if (typeof(successCallback) == "function") {
						successCallback(response);
					}
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("操作失败");
			}
		});
	};
	
	JARVIS.commonMvcSuccessCallback = function(response, callback) {
		alert(JARVIS.RESPONSE_MESSAGE_KEY);
		if (response[JARVIS.RESPONSE_STATUS_KEY]) {
			callback;
		}
	};
	
	JARVIS.ajax = function(type, url, data, dataType, successCallback, errorCallback) {
		$.ajax({
			type: type,
			url: url,
			data: data,
			success: callback,
			dataType: dataType,
			error : errorCallback
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
	
	
	/**
	 * 初始化参数
	 */
	JARVIS.initParams = function() {
		JARVIS.contextPath = $("#contextPath").val();
		JARVIS.currentUrl = $("#currentUrl").val();
		JARVIS.objectIdentifer = $("#objectIdentifer").val();
		JARVIS.systemName = $("#appName").val();
		JARVIS.ssoLoginAddress = $("#ssoLoginAddress").val();
	};
	
	/**
	 * 初始化页面
	 */
	JARVIS.initPage = function() {
		try {
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