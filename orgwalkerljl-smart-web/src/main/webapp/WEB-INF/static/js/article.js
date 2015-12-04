/**
 * Article
 * 
 * @author lijunlin<walkerljl@qq.com>
 */
JARVIS.registerNamespace("blog.article");
(function(){
	
	/**
	 * 创建文章
	 */
	blog.article.add = function() {
		window.location.href = JARVIS.contextPath + "/article/add";
	};
	
	/**
	 * 删除文章
	 */
	blog.article.del = function(ids) {
		if (confirm("确认要删除此文章吗？")) {
			JARVIS.post(JARVIS.contextPath + "/article/delete?ids=" + ids, {}, function(response){
				if (response[JARVIS.RESPONSE_STATUS_KEY]) {
					window.location.href = JARVIS.contextPath + "/article";
				}
			});
		}
	};
	
	/**
	 * 编辑文章
	 */
	blog.article.edit = function(id) {
		window.location.href = JARVIS.contextPath + "/article/edit-" + id;
	};
	
	/**
	 * 保存文章
	 */
	blog.article.save = function(formObj, editorObj) {
		editorObj.sync();
     	JARVIS.FORM.postAjaxForm($(formObj), function(response) {
     		window.location.href = JARVIS.contextPath + "/article";
     	});
     };
     
     /**
 	 * 评论
 	 */
 	blog.article.comment = function(articleId, formObj, editorObj) {
 		editorObj.sync();
 		var contentObj = $(formObj).find("#content");
 		if (contentObj.val() == "") {
 			alert("评论内容不能为空并且字符长度不能超过512个字符");
 			contentObj.focus();
 			return;
 		}
      	JARVIS.FORM.postAjaxForm($(formObj), function(response) {
      		window.location.href = JARVIS.contextPath + "/article/otherRead-" + articleId;
      	});
      };
	
	/**
	 * 阅读
	 */
	blog.article.read = function(id) {
		window.location.href = JARVIS.contextPath + "/article/view-" + id;
	};
	
	/**
	 * 其他人阅读
	 */
	blog.article.otherRead = function(id) {
		window.location.href = JARVIS.contextPath + "/article/otherRead-" + id;
	};
	
	/**
	 * 点赞
	 */
	blog.article.praise = function(articleId, refreshUrl) {
		JARVIS.post(JARVIS.contextPath + "/article/praise.json", {id:articleId}, function(response){
			if (response[JARVIS.RESPONSE_STATUS_KEY]) {
				window.location.href = refreshUrl;
			}
		});
	};	
	
	/**
	 * 收藏
	 */
	blog.article.collect = function(articleId, refreshUrl) {
		JARVIS.post(JARVIS.contextPath + "/article/collect", {id:articleId}, function(response){
			if (response[JARVIS.RESPONSE_STATUS_KEY]) {
				window.location.href = refreshUrl;
			}
		});
	};	
})();