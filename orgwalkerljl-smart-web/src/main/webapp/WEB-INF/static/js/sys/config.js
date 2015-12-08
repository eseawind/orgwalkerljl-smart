/**
 * config.js
 * 
 * @author lijunlin
 */
Namespace.register("sys.config");
(function(){
	
	sys.config.init = function() {
		//初始化dataTable
		sys.config.initDataTable();
	};
	
	/**
	 * 初始化dataTable
	 */
	sys.config.initDataTable = function() {
		JARVIS.TABLE.dataTable(JARVIS.CONTROLLER.datatableIdentifer, {
			sPaginationType : JARVIS.TABLE.sPaginationType,
			sDom : JARVIS.TABLE.sDom,
			bProcessing : false,
			bSort : false,
			bServerSide : true,
			bFilter : false,
			fnServerParams : function(aoData) { 
				
			}, 
			sAjaxSource : JARVIS.CONTROLLER.URL.selectJSONPage,
			aoColumns: [
			    {"mData" : "id", mRender : function(data, type, row) {return JARVIS.TABLE.getIdColumn(data);}},
                {"mData" : "id"},
                {"mData" : "name"},
                {"mData" : "key"},
                {"mData" : "value"},
                {"mData" : "modifier"}
			]
		}, JARVIS.TABLE.dataKey);
	};
})();