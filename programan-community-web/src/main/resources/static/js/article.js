
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

function fill_article_page(id, gotocomment) {
    var pageContent = $('.page-content');
    pageContent.empty();
    $('#loading-article-page').css('display', 'block'); 
	pageContent.load('page/article', {articleId:id}, function(responseTxt,statusTxt,xhr) {
		loadComment();
		$('#savaArticleCommentBtn').click(function(){
	    		savaArticleComment();
	    });
	    if(gotocomment) {
	    		document.getElementById('comment-btn').click();
	    }
	    $('#loading-article-page').css('display', 'none'); 
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
	setTimeout(function(){
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
	}, 700);
}

function savaArticleComment(){
	var articleComment = {
		id: '0',
		commentInfo: $('#articleComment').val(),
		articleId: $('#articleId').val()
	}
	$.ajax({
        type: "post",
        url: callurl + "/articleComment/save",
        async: true,
        data: articleComment,
        dataType: 'json',
        success: function(data) {
           if (handleAjaxResult(data, "保存成功")) {
           		loadComment();
           		$('#totalCommentNum').html(Number($('#totalCommentNum').html()) + 1);
           }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
        		if(XMLHttpRequest.status == 200) {
        			$('#login-btn').click();
        		}
            layer.msg("失败");
        }
    });
}

function loadComment() {
	$.ajax({
        type: "post",
        url: callurl + "/articleComment/list/" + $('#articleId').val(),
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
            var content = data.context;
            $('#article-comment').html("");
            $.each(content, function(index, articleComment) {
            		var html = '';
            		html += '<div class="article-comment-box">';
            		html += '<div class="top">';
            		html += '<a href="#"><img src="' + articleComment.user.headImgUrl + '"/></a>';
            		html += '<a href="#">' + articleComment.user.roleName + ':</a>';
            		html += '<span>' + articleComment.info + '</span>';
            		html += '</div>';
            		html += '<div class="bot">';
            		html += formatDateTime(articleComment.createTime);
            		html += '</div>';
            		html += '</div>';
            		$('#article-comment').append(html);
            });
        }
    });
}

function followUser(focusIdi, reload){
	var followData = {
		id: '0',
		focusId: focusIdi
	}
	$.ajax({
        type: "post",
        url: callurl + "/userFollow/save",
        async: true,
        data: followData,
        dataType: 'json',
        success: function(data) {
           if (handleAjaxResult(data, "关注成功")) {
           		if(reload == 1) {
	        			loadMyFans();
	        		} else {
	        			$('#followBtn').val('取消关注');
	           		$('#followBtn').attr("onclick","unFollowUser(" + focusIdi + ",0);");
	           		$('#unFollowBtn').val('取消关注');
	           		$('#unFollowBtn').attr("onclick","unFollowUser(" + focusIdi + ",0);");
	           		$('#totalFansNum').html(Number($('#totalFansNum').html()) + 1);
	        		}
           }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
        		if(XMLHttpRequest.status == 200) {
        			$('#login-btn').click();
        		}
            layer.msg("请先登录");
        }
    });
}

function unFollowUser(focusIdi, reload) {
	$.ajax({
        type: "post",
        url: callurl + "/userFollow/deleteByBoth/"+focusIdi,
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
           if (handleAjaxResult(data, "取消关注成功")) {
	           	if(reload == 1) {
			    		loadMyFollow();
			    } else {
			    		$('#followBtn').val('关注');
	           		$('#followBtn').attr("onclick","followUser(" + focusIdi + ",0);");
	           		$('#unFollowBtn').val('关注');
	           		$('#unFollowBtn').attr("onclick","followUser(" + focusIdi + ",0);");
	           		$('#totalFansNum').html(Number($('#totalFansNum').html()) - 1);
			    }
           }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("失败");
        }
    });
    
}

function UserInfoFollowUser(focusIdi, reload){
	var followData = {
		id: '0',
		focusId: focusIdi
	}
	$.ajax({
        type: "post",
        url: callurl + "/userFollow/save",
        async: true,
        data: followData,
        dataType: 'json',
        success: function(data) {
           if (handleAjaxResult(data, "关注成功")) {
           		if(reload == 1) {
	        			loadMyFans();
	        		} else {
	        			$('#followBtn').val('取消关注');
	           		$('#followBtn').attr("onclick","unUserInfoFollowUser(" + focusIdi + ",0);");
	           		$('#unFollowBtn').val('取消关注');
	           		$('#unFollowBtn').attr("onclick","unUserInfoFollowUser(" + focusIdi + ",0);");
	           		$('#my-fans-count').html(Number($('#my-fans-count').html()) + 1);
	        		}
           }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
        		if(XMLHttpRequest.status == 200) {
        			$('#login-btn').click();
        		}
            layer.msg("请先登录");
        }
    });
}

function unUserInfoFollowUser(focusIdi, reload) {
	$.ajax({
        type: "post",
        url: callurl + "/userFollow/deleteByBoth/"+focusIdi,
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
           if (handleAjaxResult(data, "取消关注成功")) {
	           	if(reload == 1) {
			    		loadMyFollow();
			    } else {
			    		$('#followBtn').val('关注');
	           		$('#followBtn').attr("onclick","UserInfoFollowUser(" + focusIdi + ",0);");
	           		$('#unFollowBtn').val('关注');
	           		$('#unFollowBtn').attr("onclick","UserInfoFollowUser(" + focusIdi + ",0);");
	           		$('#my-fans-count').html(Number($('#my-fans-count').html()) - 1);
			    }
           }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("失败");
        }
    });
    
}

function likeThisArticle(articleIdi){
	var articleLike = {
		id: '0',
		articleId: articleIdi
	}
	$.ajax({
        type: "post",
        url: callurl + "/articleLike/save",
        async: true,
        data: articleLike,
        dataType: 'json',
        success: function(data) {
           if (handleAjaxResult(data, "点赞成功")) {
           		$('#thumds').removeClass("fa-thumbs-up");
           		$('#thumds').addClass("fa-thumbs-o-up");
           		$('#article-like-btn').css('background-color', 'rgba(106,206,238,0.3)');
           		$('#article-like-btn').attr("onclick","unLikeThisArticle(" + articleIdi + ");");
           		$('#likeNum').html(Number($('#likeNum').html()) + 1);
           		$('#totalLikeNum').html(Number($('#totalLikeNum').html()) + 1);
           }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
        		if(XMLHttpRequest.status == 200) {
        			$('#login-btn').click();
        		}
            layer.msg("请先登录");
        }
    });
}

function unLikeThisArticle(articleIdi) {
	$.ajax({
        type: "post",
        url: callurl + "/articleLike/deleteByBoth/"+articleIdi,
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
           if (handleAjaxResult(data, "取消点赞成功")) {
           		$('#thumds').removeClass("fa-thumbs-o-up");
           		$('#thumds').addClass("fa-thumbs-up");
           		$('#article-like-btn').css('background-color', '#FFFFFF');
           		$('#article-like-btn').attr("onclick","likeThisArticle(" + articleIdi + ");");
           		$('#likeNum').html(Number($('#likeNum').html()) - 1);
           		$('#totalLikeNum').html(Number($('#totalLikeNum').html()) - 1);
           }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("失败");
        }
    });
}

