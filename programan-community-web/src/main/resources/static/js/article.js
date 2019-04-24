
function loadPage(module_page, call_back) {
    $(document).ready(function() {
        var pageContent = $('.page-content');
        pageContent.empty();
        pageContent.load(module_page, function() {
	        	if (!call_back) {
                return;
            }
            call_back();
        });
    });
}

function fill_article_page(id) {
    var pageContent = $('.page-content');
    pageContent.empty();
    pageContent.load('page/article', {articleId:id}, function(responseTxt,statusTxt,xhr) {

    });
    $('#savaArticleCommentBtn').click(function(){
    		savaArticleComment();
    });
}

function del_article(delid) {
	layer.msg('确定删除？', {
	  time: 0, //不自动关闭
	  btn: ['确定', '取消'],
	  yes: function(index){
		$.ajax({
			type: "post",
			url: callurl + "/article/delete/" + delid,
			async: true,
			dataType: 'json',
			contentType: 'application/json; charset=UTF-8',
			success: function(data) {
				handleAjaxResult(data, '删除成功');
				setTimeout(function(){
					fill_article_manage_page();
				},500)
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				layer.msg("失败");
			}
		});
	  }
	});
}

function reditArticle(id) {
	$.ajax({
        type: "get",
        url: callurl + "/article/detail/" + id,
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
            var content = data.context;
            $('#articleId').val(content.id);
            $('#articleTitle').val(content.title);
            $('#createType').val(content.createType.id);
            $('#topic').val(content.topic.id);
		    editor.txt.html(content.body);
        }
    });
}

function savaArticleComment(){
	var articleComment = {
		
	}
}
