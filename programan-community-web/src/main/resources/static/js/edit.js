function fill_edit_blog_page() {
    loadPage('html/edit.html', loadEditBlogRight);
}

function fill_article_manage_page() {
    loadPage('html/edit.html', loadArticleManage);
}

function fill_user_base_info_page() {
    loadPage('html/userBaseEdit.html', loadEditUserBaseInfo);
}

function fill_user_info_page() {
    loadPage('html/userInfoEdit.html', loadEditUserInfo);
}

function loadEditBlogRight() {
	$(document).ready(function() {
        var pageContent = $('.edit-right');
        pageContent.empty();
        pageContent.load('html/editBlog.html', function() {
			var E = window.wangEditor;
		    var editor = new E('#editor');
		    editor.create();
        });
    });
}

function loadEditUserBaseInfo() {
	updateUserBaseInfo(0);
}

function loadEditUserInfo() {
	updateUserInfo(0);
}

function loadArticleManage() {
	$(document).ready(function() {
        var pageContent = $('.edit-right');
        pageContent.empty();
        pageContent.load('html/articleManage.html', function() {
			var E = window.wangEditor;
		    var editor = new E('#editor');
		    editor.create();
        });
    });
}

//控制userupdate的界面，修改不同信息
function updateUserBaseInfo(index) {
	var all_update_page = ['updateUserpwdDiv', 'updateUserPhoneDiv', 'updateUserEmailDiv'];
	$.each(all_update_page, function(index,page) {
		$('#' + page).css('display', 'none');
	});
	$('#' + all_update_page[index]).css('display', 'block');
}

//控制userupdate的界面，修改不同信息
function updateUserInfo(index) {
	var all_update_page = ['userInfoDiv', 'userFollowDiv', 'userFansDiv'];
	$.each(all_update_page, function(index,page) {
		$('#' + page).css('display', 'none');
	});
	$('#' + all_update_page[index]).css('display', 'block');
}

$('#showUpdateUserInfoBtn').click(function() {
	$('.datepicker').datepicker({
		language: 'zh-CN'
	});
})
