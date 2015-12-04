/**
 * JARVIS.TABLE 组件
 * @author cdlijunlin
 */
JARVIS.registerNamespace("JARVIS.TABLE");
(function() {
	
	JARVIS.TABLE.dataTableObject;
	JARVIS.TABLE.dataKey = "data";
	JARVIS.TABLE.sPaginationType = "bootstrap";
	JARVIS.TABLE.sDom = '<"row-fluid"<"span6"l><"span6"f>r>t<"row-fluid"<"span6"i><"span6"p>>';
	
	/**
	 * 使用dataTables插件展示表格数据
	 * @param domId DOM节点ID
	 * @param params 参数(详细查看dataTables配置)
	 * @param datakey 要显示数据的key rr
	 */
	JARVIS.TABLE.dataTable = function(domId, params, dataKey) {
		//默认配置
		var def_opts = {
			fnServerData : function(sSource, aoData, fnCallback, oSettings) {
				JARVIS.TABLE.fnServerData(sSource, aoData, fnCallback, oSettings, dataKey);
			},
			oLanguage : {
				"sLengthMenu" : "每页显示 _MENU_ 条记录",
				"sLengthMenu" : "每页显示 _MENU_ 条记录",
				"sZeroRecords" : "抱歉， 没有找到要查询的数据！",
				"sInfo" : "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
				"sInfoEmpty" : "没有数据",
				"sInfoFiltered" : "(从 _MAX_ 条数据中检索)",
				"oPaginate" : {"sSearch": "搜索:"}
			},
			sPaginationType : "bootstrap",
			bProcessing : false,
			bServerSide :true,
			bFilter : false,
			bSort : false,
			aLengthMenu : [25, 50, 100],
			iDisplayLength : 25
		};
		var options = $.extend(true, {}, def_opts, params || {});
		var table = $('#'+domId).dataTable(options);
		JARVIS.TABLE.dataTableObject = table;
		return table;
	};

	/**
	 * @param url:请求url
	 * @param data:发送到服务器的数据
	 * @param fnCallback 请求完成之后回调此函数将返回的数据绘制到页面上
	 * @param oSettings dataTable设置对象
	 * @param dataKey 返回数据Key
	 */
	JARVIS.TABLE.fnServerData = function(url, data, fnCallback, oSettings, dataKey) {
		JARVIS.mask();
		$.ajax({ 
	        dataType : 'json', 
	        contentType : "application/x-www-form-urlencoded; charset=utf-8", 
	        type : 'POST', 
	        cache : false, 
	        url : url+".json", 
	        data : data, 
	        success : function(data) {
	        	try {
	        		if (data[JARVIS.RESPONSE_STATUS_KEY] == false){//服务出现异常
		        		alert(data[JARVIS.RESPONSE_MESSAGE_KEY]);
		        		JARVIS.unmask();
		        		return ;
		        	}
		        	if (dataKey == null) {
		        		oSettings.sAjaxDataProp = JARVIS.RESPONSE_DATA_KEY;
		        		fnCallback(data); 
		        	} else {
		        		oSettings.sAjaxDataProp = dataKey;
		        		fnCallback(data[JARVIS.RESPONSE_DATA_KEY]); 
		        	}
		            JARVIS.unmask();
	        	} catch (e) {
	        		window.alert('请求数据异常');
	        		JARVIS.unmask();
	        	}
	        },
	        error : function(jqXHR, textStatus, errorThrown) {
				JARVIS.unmask();
				alert(jqXHR.responseText+"数据加载失败!");
			}
	    }); 
	};

	//重新绘制表格
	JARVIS.TABLE.resetTable = function(oTable, flag) {
		if (flag) {
			var oSettings = oTable.fnSettings();
			oSettings._iDisplayStart = 0;
		}
	    oTable.fnClearTable(0);
	    oTable.fnDraw();
	};
	
	JARVIS.TABLE.getIdColumn = function(data) {
		var string = 
				"<label>" +
					"<input id='"+JARVIS.CONTROLLER.checkboxItemIdentifer +
					"' value='"+data+"' name='"+JARVIS.CONTROLLER.checkboxItemIdentifer +"' type='checkbox'/>" +
					"<span class='lbl'></span>" +
				"</label>";
		return string;
	};
})();