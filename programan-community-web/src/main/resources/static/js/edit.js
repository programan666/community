var E = window.wangEditor;
var editor = new E('#editor');
function fill_edit_blog_page() {
    loadPage('/page/edit', loadEditBlogRight);
}

function fill_article_manage_page() {
    loadPage('/page/edit', loadArticleManage);
}

function fill_user_base_info_page() {
    loadPage('/page/userBaseEdit', loadEditUserBaseInfo);
}

function fill_user_info_page() {
    loadPage('/page/userInfoEdit', loadEditUserInfo);
}

function loadEditBlogRight() {
	$(document).ready(function() {
        var pageContent = $('.edit-right');
        pageContent.empty();
        pageContent.load('/page/editBlog', function() {
		
		    editor.create();
		    $('#saveArticleBtn').click(function(){
		    		saveArticle(editor.txt.html());
		    });
		    loadCreateTypeSelect();
		    loadTopicSelect();
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
        pageContent.load('/page/articleManage',{username: $('#susername').html()}, function(responseTxt,statusTxt,xhr) {
			var E = window.wangEditor;
		    var editor = new E('#editor');
		    editor.create();
		    loadCreateTypeSelect(1);
		    loadTopicSelect(1);
			$('#selectArticleBtn').click(function(){
				selectArticle();
			});
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
		language: 'zh-CN',
		format: 'yyyy-mm-dd'
	});
})

$.History.bind('/user/info',function(state){
	fill_user_info_page();
	loadIndustrySelect();
	clickUserInfo();
});

function clickUserInfo() {
	var username = $('#susername').html();
	$.ajax({
        type: "get",
        url: callurl + "/user/usdetail/" + username,
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
            var content = data.context;
            $('#user-info-id').html('ID: ' + content.id);
            $('#user-info-rolename').html('昵称： ' + content.roleName);
            $('#user-info-realname').html('实名： ' + content.realName);
            $('#user-info-sex').html('性别： ' + content.sex);
            $('#user-info-birthday').html('生日： ' + content.birthday);
            $('#user-info-area').html('地区： ' + content.area);
            $('#user-info-industry').html('行业： ' + content.industry.name);
            $('#user-info-job').html('职位： ' + content.jobName);
//          $('#user-info-id').html('实名: ' + content.headImgUrl);
            $('#user-info-pnum').html('P豆 ' + content.pnum);
            $('#user-info-introduction').html('简介： ' +content.introduction);
            
            $('#roleName').val(content.roleName);
            $('#realName').val(content.realName);
            $('#jobName').val(content.jobName);
            $('#sex').val(content.sex);
            $('#birthday').val(content.birthday);
            $('#industry').val(content.industry.id);
            $('#area').val(content.area);
            $('#introduction').val(content.introduction);
        }
  });
   
}

$.History.bind('/user/follow',function(state){
	updateUserInfo(1);
});

$.History.bind('/user/funs',function(state){
	updateUserInfo(2);
});

$.History.bind('/article/manage',function(state){
	fill_article_manage_page();
});

$.History.bind('/edit/blog',function(state){
	fill_edit_blog_page()
});

$.History.bind('/article/managel',function(state){
	fill_article_manage_page();
	loadArticleManage();
});

function saveUpdateUserInfo(){
	var user = {
        userName: $('#susername').html(),
        roleName: $('#roleName').val(),
        realName: $('#realName').val(),
        sex: $('#sex').val(),
        birthday: $('#birthday').val(),
        area: $('#area').val(),
        industryId: $('#industry').val(),
        jobName: $('#jobName').val(),
        introduction: $('#introduction').val()
    };
    $.ajax({
        type: "post",
        url: callurl + "/user/update",
        async: true,
        data: user,
        dataType: 'json',
        success: function(data) {
           if (handleAjaxResult(data, "保存成功")) {
               
           }
           clickUserInfo();
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("失败");
        }
    });
}

function uploadFile(){
	var formData = new FormData();
    formData.append("file", $("#fileUpload")[0].files[0]);
	$.ajax({
		url: callurl + '/file/upload',
		data: formData, //将表单数据序列化，格式为name=value
		type: 'POST',
		cache: false,
		dataType:"json",
		mimeType:"multipart/form-data",
		processData: false,
    		contentType: false,
		success:function(data){
			if (handleAjaxResult(data, "上传成功")) {
               
            }
		},
		error:function(){
			console.log("上传出现异常");
		},
	});

}

function get_img(f) {
	var rd = new FileReader();//创建文件读取对象
	var files = f.files[0];//获取file组件中的文件
	rd.readAsDataURL(files);//文件读取装换为base64类型
	rd.onloadend = function(e) {
		document.getElementById("imgs").src = this.result;
	}
}

function loadCreateTypeSelect(index) {
	$.ajax({
        type: "get",
        url: callurl + "/createType/list/",
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
            var content = data.context;
            $('#createType').html("");
            if(index  == 1) {
            		$('#createType').append('<option value=0>所有</option>')
            }
            $.each(content, function(index, createType) {
            		$('#createType').append('<option value='+ createType.id + '>' + createType.name + '</option>')
            });
        }
    });
}

function loadTopicSelect(index) {
	$.ajax({
        type: "get",
        url: callurl + "/topic/list/",
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
            var content = data.context;
            $('#topic').html("");
            if(index  == 1) {
            		$('#topic').append('<option value=0>所有</option>')
            }
            $.each(content, function(index, topic) {
            		$('#topic').append('<option value='+ topic.id + '>' + topic.name + '</option>')
            });
        }
    });
}

function saveArticle(htmlText) {
	var articleInfo = {
		id: $('#articleId').val(),
		articleTitle: $('#articleTitle').val(),
		articleBody: htmlText,
		createTypeId: $('#createType').val(),
		topicId: $('#topic').val(),
		username: $('#susername').html()
	}
	$.ajax({
        type: "post",
        url: callurl + "/article/save",
        async: true,
        data: articleInfo,
        dataType: 'json',
        success: function(data) {
           if (handleAjaxResult(data, "保存成功")) {
               
           }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("失败");
        }
    });
}

function selectArticle() {
	var articleCon = {
		createTypeId : $('#createType').val(),
		topicId : $('#topic').val(),
		articleTitle : $('#articleTitle').val(),
	}
	var pageContent = $('.edit-right');
    pageContent.empty();
	pageContent.load('/article/select',articleCon, function(responseTxt,statusTxt,xhr) {
		$('#selectArticleBtn').click(function(){
			selectArticle();
		});
   });
}


