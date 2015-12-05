JARVIS.registerNamespace("JARVIS.WINDOW");
(function(){
	JARVIS.WINDOW.queue = new Array();
	
	/**
	 * 打开一个模态窗口
	 * @param url 窗口请求路径
	 * @param params 请求参数{a:'',b:''}
	 * @param title: 窗口标题
	 * @param method: 请求方式(get,post)
	 * @param conf: 配置信息(buttons:[],width:number,height:number,closeable:true/false)
	 */
	JARVIS.WINDOW.open = function(url, params, title, conf, method) {
		JARVIS.mask();
		var def_opts = {buttons:[], width : 'auto', height : 'auto', closeable : true};
		var options = $.extend(true, {}, def_opts, conf || {});
		var callback = function(data) {
			try {
				if (data != null && !JARVIS.validateText(data[JARVIS.RESPONSE_STATUS_KEY])) {
					alert(data[JARVIS.RESPONSE_MESSAGE_KEY]);
				}
			} catch(e) {}
			var modal = JARVIS.WINDOW.openDialog(title, data, options);
			modal.attr("data-url", url).attr("data-params", params).attr("data-method", method).css({width:options.width,height:options.height});
			JARVIS.unmask();
		};
		if ("get" == method) {//get请求
			jQuery.get(url, params, callback);
		} else {//post请求
			jQuery.post(url, params, callback);
		}
	};
	
	JARVIS.WINDOW.close = function(){
		var length = JARVIS.WINDOW.queue.length;
		if (length > 0) {
			JARVIS.WINDOW.queue[length-1].find('DIV.modal-header>A.close').click();
		}
	};

	JARVIS.WINDOW.openDialog = function(title, content, options) {
		var def_opts = {buttons:[], width : 'auto', height : 'auto', closeable : true};
		options = $.extend(true, {}, def_opts, options || {});
		var modal = $("<div id='modalhr' class='modal hide window'></div>");
		var modalHeader = $("<div class='modal-header'></div>");
		if (options.closeable) {
			var closeBtn = $("<a class='close' title='键盘按ESC也可以关闭我哟!'>×</a>");
			closeBtn.bind("click", function() {
				modal.remove(); 
				backdrop.remove(); 
				JARVIS.WINDOW.queue.pop();
				}
			);
			modalHeader.append(closeBtn);
		}
		var modalBody = $("<div class='modal-body' style='padding:10px'></div>");
		modalBody.append(content);
		var windowHtmlTitle = modalBody.find("#windowName").text();
		if (JARVIS.validateText(windowHtmlTitle) == false) {
			title = windowHtmlTitle;
		}
		modalHeader.append("<h3>"+(JARVIS.validateText(title) ? JARVIS.systemName : title)+"</h3>");
		var modalFooter = "";
		if (options.buttons == null) { 
			options.buttons = new Array();
		}
		modalBody.find("DIV.bottomBar>button,DIV.bottomBar>A").each(function(i,o) {
			options.buttons.push(o);
		});
		if(options.buttons != null && options.buttons.length > 0) {
			modalFooter = $("<div class='modal-footer' style='padding:10px'></div>");
			for (var btn in options.buttons) {
				modalFooter.append(options.buttons[btn]);
			}
		}
		JARVIS.WINDOW.queue.push(modal);
		var zIndex = 1050 + JARVIS.WINDOW.queue.length;
		var backdrop = $('<div class="modal-backdrop"/>').css("z-index", zIndex);
		modal.append(modalHeader).append(modalBody).append(modalFooter).appendTo(document.body);
		modal.before(backdrop).draggable({handle : modalHeader, containment: "body"});
		modal.css({"z-index":zIndex+1,"width":options.width,"height":options.height});
		setTimeout(function() {
			var left = ($(window).width() - modal.width()) / 2;
			var top = ($(window).height() - modal.height()) / 2 - 50;
			modal.css({"left" : left, "top" : top, "margin-left" : 0}).fadeIn("slow");
			JARVIS.initPage();
		}, 50);
		if(options.resizable){modal.resizable({minHeight: 200, minWidth: 300});}
		
		return modal;
	};
})();