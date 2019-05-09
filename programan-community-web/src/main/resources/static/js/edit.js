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

function fill_other_user_info_page(pageNum, userId) {
	if(pageNum == 0){
		loadPage('/page/userInfoShow', function(){
			showUserInfo(pageNum);
		});
	} else {
		showUserInfo(pageNum);
	}
	setTimeout(function(){
		if(pageNum == 0) {
			clickUserInfoById(userId);
		}
		if(pageNum == 1) {
			loadUserFollow(userId);
		}
		if(pageNum == 2) {
			loadUserFans(userId);
		}
		if(pageNum == 3) {
			loadCreateTypeSelect(1);
			loadTopicSelect(1);
			selectUserArticle(userId);
			$('#selectArticleBtn').click(function() {
				selectUserArticle(userId);
			});
		}
		if(pageNum == 4) {
			loadUserCourse(userId);
		}
		
	},500)
}

function clickUserInfoById(userId) {
	$.ajax({
		type: "get",
		url: callurl + "/user/usdetailById/" + userId,
		async: true,
		dataType: 'json',
		contentType: 'application/json; charset=UTF-8',
		success: function(data) {
			var content = data.context;
			$('#userShowInfoId').val(content.id);
			$('#userHeaderImg').attr('src', content.headImgUrl);
			$('#user-info-rolename').html('昵称： ' + content.roleName);
			$('#user-info-realname').html('实名： ' + content.realName);
			$('#user-info-sex').html('性别： ' + content.sex);
			$('#user-info-birthday').html('生日： ' + content.birthday);
			$('#user-info-area').html('地区： ' + content.area);
			$('#user-info-industry').html('行业： ' + content.industry.name);
			$('#user-info-job').html('职位： ' + content.jobName);
			$('#user-info-introduction').html('简介： ' + content.introduction);
			
			$('#userShowInfoBtn').attr('onclick', 'fill_other_user_info_page(0,' + content.id + ')');
			$('#userShowFollowBtn').attr('onclick', 'fill_other_user_info_page(1,' + content.id + ')');
			$('#userShowFansBtn').attr('onclick', 'fill_other_user_info_page(2,' + content.id + ')');
			$('#userShowBlogBtn').attr('onclick', 'fill_other_user_info_page(3,' + content.id + ')');
			$('#userShowCourseBtn').attr('onclick', 'fill_other_user_info_page(4,' + content.id + ')');
		}
	});
	
	$.ajax({
		type: "get",
		url: callurl + "/user/userInfoFollow/" + userId,
		async: true,
		dataType: 'json',
		contentType: 'application/json; charset=UTF-8',
		success: function(data) {
			var content = data.context;
			if(content == 'no'){
				$('#UserFollowSpan').html('<input type="button" class="submit-input user-info-follow-btn" id="followBtn" value="关注" onclick="UserInfoFollowUser(' + userId + ')"/>');
			} else {
				$('#UserFollowSpan').html('<input type="button" class="submit-input user-info-follow-btn" id="unFollowBtn" value="取消关注" onclick="unUserInfoFollowUser(' + userId + ')"/>');
			}
		}
	});

	$.ajax({
		type: "get",
		url: callurl + "/userFollow/getUserFollowCountById/" + userId,
		async: true,
		dataType: 'json',
		contentType: 'application/json; charset=UTF-8',
		success: function(data) {
			var content = data.context;
			$('#my-focus-count').html('关注 ' + content.focusNum);
			$('#my-fans-count').html(content.fansNum);
		}
	});

}

function loadEditBlogRight() {
	$(document).ready(function() {
		var pageContent = $('.edit-right');
		pageContent.empty();
		pageContent.load('/page/editBlog', function() {

			editor.create();
			
			$('#saveArticleBtn').click(function() {
				saveArticle(editor.txt.html());
			});
			$('.w-e-menu').click(function(){
				if($('.w-e-active').html().toString().indexOf('网络图片') == -1) {
					return false;
				} else {
					setTimeout(function(){
						if($('.w-e-button-container').html().toString().length < 200) {
							var html = '<div class="head-img" style="width:100px;height:100px">';
							html += '<img src="" id="imgs" style="width:100px;height:100px">';
							html += '</div>';
							html += '文件：<input onchange="get_img(this)" type="file" name="fileUpload6" id="fileUpload6"/>';
							html += '<div class="progress-bar" id="progress-bar" style="float: none;"></div>';
							html += '<input class="model-btn" type="button" value="上传" onclick="mArticleUploadFile(\'editor .w-e-panel-tab-content input\', \'articleImgDir\', \'fileUpload6\')" style="margin-top: 10px;"/>';
							$('.w-e-button-container').append(html);
						}
					},1000);
				}
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
		pageContent.load('/page/articleManage', {
			username: $('#susername').html()
		}, function(responseTxt, statusTxt, xhr) {
			var E = window.wangEditor;
			var editor = new E('#editor');
			editor.create();
			loadCreateTypeSelect(1);
			loadTopicSelect(1);
			$('#selectArticleBtn').click(function() {
				selectArticle(0);
			});
		});
	});
}

//控制userupdate的界面，修改不同信息
function updateUserBaseInfo(index) {
	var all_update_page = ['updateUserpwdDiv', 'updateUserPhoneDiv', 'updateUserEmailDiv'];
	$.each(all_update_page, function(index, page) {
		$('#' + page).css('display', 'none');
	});
	$('#' + all_update_page[index]).css('display', 'block');
}

//控制userupdate的界面，修改不同信息
function updateUserInfo(index) {
	var all_update_page = ['userInfoDiv', 'userFollowDiv', 'userFansDiv', 'userCourseDiv'];
	$.each(all_update_page, function(index, page) {
		$('#' + page).css('display', 'none');
	});
	$('#' + all_update_page[index]).css('display', 'block');
}

function showUserInfo(index) {
	var all_update_page = ['userInfoDiv', 'userFollowDiv', 'userFansDiv', 'userBlogDiv', 'userCourseDiv'];
	$.each(all_update_page, function(index, page) {
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
			$('#userHeaderImg').attr('src', content.headImgUrl);
			$('#user-info-rolename').html('昵称： ' + content.roleName);
			$('#user-info-realname').html('实名： ' + content.realName);
			$('#user-info-sex').html('性别： ' + content.sex);
			$('#user-info-birthday').html('生日： ' + content.birthday);
			$('#user-info-area').html('地区： ' + content.area);
			$('#user-info-industry').html('行业： ' + content.industry.name);
			$('#user-info-job').html('职位： ' + content.jobName);
			$('#user-info-pnum').html('P豆 ' + content.pnum);
			$('#user-info-introduction').html('简介： ' + content.introduction);

			$('#roleName').val(content.roleName);
			$('#realName').val(content.realName);
			$('#jobName').val(content.jobName);
			$('#sex').val(content.sex);
			$('#birthday').val(content.birthday);
			$('#phone').val(content.phone);
			$('#industry').val(content.industry.id);
			$('#area').val(content.area);
			$('#introduction').val(content.introduction);
		}
	});

	$.ajax({
		type: "get",
		url: callurl + "/userFollow/getUserFollowCount",
		async: true,
		dataType: 'json',
		contentType: 'application/json; charset=UTF-8',
		success: function(data) {
			var content = data.context;
			$('#my-focus-count').html('关注 ' + content.focusNum);
			$('#my-fans-count').html('粉丝 ' + content.fansNum);
		}
	});

}

$.History.bind('/user/follow', function(state) {
	updateUserInfo(1);
	setTimeout(function() {
		loadMyFollow();
	},500)
	
});

$.History.bind('/user/funs', function(state) {
	updateUserInfo(2);
	setTimeout(function() {
		loadMyFans();
	},500)
});

$.History.bind('/user/course', function(state) {
	updateUserInfo(3);
	setTimeout(function() {
		loadMyCourse();
	},500)
});

$.History.bind('/article/manage', function(state) {
	fill_article_manage_page();
});

$.History.bind('/edit/blog', function(state) {
	fill_edit_blog_page()
});

$.History.bind('/article/managel', function(state) {
	fill_article_manage_page();
	loadArticleManage();
});

$.History.bind('/user/info', function(state) {
	fill_user_info_page();
	setTimeout(function(){
		loadIndustrySelect();
		clickUserInfo();
	},500);
});

function saveUpdateUserInfo() {
	var user = {
		userName: $('#susername').html(),
		roleName: $('#roleName').val(),
		realName: $('#realName').val(),
		sex: $('#sex').val(),
		birthday: $('#birthday').val(),
		phone: $('#phone').val(),
		area: $('#area').val(),
		industryId: $('#industry').val(),
		jobName: $('#jobName').val(),
		introduction: $('#introduction').val()
	};
	judgeNull(['roleName', 'phone']);
	$.ajax({
		type: "post",
		url: callurl + "/user/update",
		async: true,
		data: user,
		dataType: 'json',
		success: function(data) {
			if(handleAjaxResult(data, "保存成功")) {

			}
			clickUserInfo();
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			layer.msg("失败");
		}
	});
}

function updateUserHeadImg() {
	$.ajax({
		url: callurl + '/user/getPhoneNum',
		data: formData, //将表单数据序列化，格式为name=value
		type: 'POST',
		cache: false,
		dataType: "json",
		mimeType: "multipart/form-data",
		processData: false,
		contentType: false,
		success: function(data) {
			if(handleAjaxResult(data, "上传成功")) {
				$('#uploadHeadImgResultUrl').val(data.context);
				$('#updateUserHeadImgBtn').css('display', 'block');
			}
		},
		error: function() {
			console.log("上传出现异常");
		},
	});
}

function updateHeaderImg() {
	var headImgData = {
		headImgUrl: $('#uploadHeadImgResultUrl').val()
	};
	$.ajax({
		type: "post",
		url: callurl + "/user/updateHeaderImg",
		async: true,
		data: headImgData,
		dataType: 'json',
		success: function(data) {
			if(handleAjaxResult(data, "修改成功")) {
				$('#updateUserHeadImgBtn').css('display', 'none');
				$('#userHeaderImg').attr('src',data.context); 
				$('#user-dropdown .user-img img').attr('src',data.context); 
			}
		},
		error: function() {
			console.log("上传出现异常");
		},
	});
}

function get_img(f) {
	var rd = new FileReader(); //创建文件读取对象
	var files = f.files[0]; //获取file组件中的文件
	rd.readAsDataURL(files); //文件读取装换为base64类型
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
			if(index == 1) {
				$('#createType').append('<option value=0>所有</option>')
			}
			$.each(content, function(index, createType) {
				$('#createType').append('<option value=' + createType.id + '>' + createType.name + '</option>')
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
			if(index == 1) {
				$('#topic').append('<option value=0>所有</option>')
			}
			$.each(content, function(index, topic) {
				$('#topic').append('<option value=' + topic.id + '>' + topic.name + '</option>')
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
	judgeNull(['articleTitle']);
	if(htmlText.toString().length < 20) {
		layer.msg("文章内容过少");
		return false;
	}
	$.ajax({
		type: "post",
		url: callurl + "/article/save",
		async: true,
		data: articleInfo,
		dataType: 'json',
		success: function(data) {
			if(handleAjaxResult(data, "保存成功")) {
				document.getElementById('article-manager-btn').click();
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			layer.msg("失败");
		}
	});
}

function selectArticle(userId) {
	var articleCon = {
		userId: userId,
		createTypeId: $('#createType').val(),
		topicId: $('#topic').val(),
		articleTitle: $('#articleTitle').val(),
	}
	var pageContent = $('.edit-right');
	pageContent.empty();
	pageContent.load('/article/select', articleCon, function(responseTxt, statusTxt, xhr) {
		$('#selectArticleBtn').click(function() {
			selectArticle(userId);
		});
	});
}

function selectUserArticle(userId) {
	var articleCon = {
		userId: userId,
		createTypeId: $('#createType').val(),
		topicId: $('#topic').val(),
		articleTitle: $('#articleTitle').val(),
	}
	var pageContent = $('#my-blog');
	pageContent.empty();
	pageContent.load('/article/select', articleCon, function(responseTxt, statusTxt, xhr) {
		$('#selectArticleBtn').click(function() {
			selectUserArticle(userId);
		});
	});
}

function loadMyFollow() {
	$.ajax({
		type: "get",
		url: callurl + "/userFollow/listByFans/" + 0,
		async: true,
		dataType: 'json',
		contentType: 'application/json; charset=UTF-8',
		success: function(data) {
			var content = data.context;
			$('#my-follow').html('');
			$.each(content, function(index, userFollow) {
				var html = '';
				html += '<div class="follow-box">';
				html += '<span><a href="#"  onclick="fill_other_user_info_page(0,' + userFollow.focusUser.id + ')">';
				html += '<img src="' + userFollow.focusUser.headImgUrl +'"/>';
				html += '</a></span>';
				html += '<span><a href="#"  onclick="fill_other_user_info_page(0,' + userFollow.focusUser.id + ')">';
				html += userFollow.focusUser.roleName;
				html += '</a></span>';
				html += '<span class="float-right">';
				html += '<input type="button" class="follow" value="取消关注" onclick="unFollowUser(' + userFollow.focusUser.id + ',1)"/>';
				html += '</span>';
				html += '</div>';
				$('#my-follow').append(html);
			});
		}
	});
}

function loadMyFans() {
	$.ajax({
		type: "get",
		url: callurl + "/userFollow/listByFocus/" + 0,
		async: true,
		dataType: 'json',
		contentType: 'application/json; charset=UTF-8',
		success: function(data) {
			var content = data.context;
			$('#my-fans').html('');
			$.each(content, function(index, userFollow) {
				var id = userFollow.fansUser.id;
				var html = '';
				html += '<div class="follow-box">';
				html += '<span><a href="#"  onclick="fill_other_user_info_page(0,' + userFollow.fansUser.id + ')">';
				html += '<img src="' + userFollow.fansUser.headImgUrl + '"/>';
				html += '</a></span>';
				html += '<span><a href="#"  onclick="fill_other_user_info_page(0,' + userFollow.fansUser.id + ')">';
				html += userFollow.fansUser.roleName;
				html += '</a></span>';
				html += '<span class="float-right">';
				//          		html += '<input type="button" class="follow" value="关注" onclick="followUser(' + id + ',1)"/>';
				html += '</span>';
				html += '</div>';
				$('#my-fans').append(html);
			});
		}
	});
}

function loadMyCourse() {
	$.ajax({
		type: "get",
		url: callurl + "/userCourse/listByUser/",
		async: true,
		dataType: 'json',
		contentType: 'application/json; charset=UTF-8',
		success: function(data) {
			var content = data.context;
			$('#my-course').html('');
			$.each(content, function(index, userCourse) {
				var html = '';
            		html += '<div class="course-box">';
            		html += '<div class="course-img">';
            		html += '<a href="javascript:void(0);" onclick="learnCourse(' + userCourse.course.id + ')"><img src="' + userCourse.course.imgUrl +'" alt="" /></a>';
            		html += '</div>';
            		html += '<div class="course-info">';
            		html += '<a href="javascript:void(0);" onclick="learnCourse(' + userCourse.course.id + ')" onfocus="this.blur();">' + userCourse.course.title + '</a>';
            		html += '<p>' + userCourse.course.teacherName + '·' + userCourse.course.teacherJob + '</p>';
            		html += '<div class="skill">';
            		html += userCourse.course.introduction;
            		html += '</div></div>';
            		html += '<div class="course-footer">';
            		html += '<h3>' + userCourse.course.price + 'P豆</h3>';
            		html += '</div></div>';
				$('#my-course').append(html);
			});
		}
	});
}

function loadUserFollow(userId) {
	$.ajax({
		type: "get",
		url: callurl + "/userFollow/listByFans/" + userId,
		async: true,
		dataType: 'json',
		contentType: 'application/json; charset=UTF-8',
		success: function(data) {
			var content = data.context;
			$('#my-follow').html('');
			$.each(content, function(index, userFollow) {
				var html = '';
				html += '<div class="follow-box">';
				html += '<span>';
				html += '<a href="#" onclick="fill_other_user_info_page(0,' + userFollow.focusUser.id + ')">';
				html += '<img src="' + userFollow.focusUser.headImgUrl +'"/>';
				html += '</a>';
				html += '</span>';
				html += '<span>';
				html += '<a href="#" onclick="fill_other_user_info_page(0,' + userFollow.focusUser.id + ')">';
				html += userFollow.focusUser.roleName;
				html += '</a>';
				html += '</span>';
				html += '</div>';
				$('#my-follow').append(html);
			});
		}
	});
}

function loadUserFans(userId) {
	$.ajax({
		type: "get",
		url: callurl + "/userFollow/listByFocus/" + userId,
		async: true,
		dataType: 'json',
		contentType: 'application/json; charset=UTF-8',
		success: function(data) {
			var content = data.context;
			$('#my-fans').html('');
			$.each(content, function(index, userFollow) {
				var id = userFollow.fansUser.id;
				var html = '';
				html += '<div class="follow-box">';
				html += '<span>';
				html += '<a href="#" onclick="fill_other_user_info_page(0,' + id + ')">';
				html += '<img src="' + userFollow.fansUser.headImgUrl + '"/>';
				html += '</a>';
				html += '</span>';
				html += '<span>';
				html += '<a href="#" onclick="fill_other_user_info_page(0,' + id + ')">';
				html += userFollow.fansUser.roleName;
				html += '</a>';
				html += '</span>';
				html += '</div>';
				$('#my-fans').append(html);
			});
		}
	});
}

function loadUserCourse(userId) {
	$.ajax({
		type: "get",
		url: callurl + "/userCourse/listByUserId/" + userId,
		async: true,
		dataType: 'json',
		contentType: 'application/json; charset=UTF-8',
		success: function(data) {
			var content = data.context;
			$('#my-course').html('');
			$.each(content, function(index, userCourse) {
				var html = '';
            		html += '<div class="course-box">';
            		html += '<div class="course-img">';
            		html += '<a href="javascript:void(0);" onclick="learnCourse(' + userCourse.course.id + ')"><img src="' + userCourse.course.imgUrl +'" alt="" /></a>';
            		html += '</div>';
            		html += '<div class="course-info">';
            		html += '<a href="javascript:void(0);" onclick="learnCourse(' + userCourse.course.id + ')" onfocus="this.blur();">' + userCourse.course.title + '</a>';
            		html += '<p>' + userCourse.course.teacherName + '·' + userCourse.course.teacherJob + '</p>';
            		html += '<div class="skill">';
            		html += userCourse.course.introduction;
            		html += '</div></div>';
            		html += '<div class="course-footer">';
            		html += '<h3>' + userCourse.course.price + 'P豆</h3>';
            		html += '</div></div>';
				$('#my-course').append(html);
			});
		}
	});
}

function updatePNum() {
	if(Number($('#updatePNum').val()) < 1) {
		layer.msg('输入不合法');
		return false;
	}
	$.ajax({
		type: "get",
		url: callurl + "/user/updatePNum/" + $('#updatePNum').val(),
		async: true,
		dataType: 'json',
		contentType: 'application/json; charset=UTF-8',
		success: function(data) {
			if(handleAjaxResult(data, "充值成功")) {

			}
			clickUserInfo();
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			layer.msg("充值失败");
		}
	});
}

function selectArticleByTitle() {
	var titleKeyi = $('#selectIndexArticleInput').val();
	if(titleKeyi.length < 1) {
		return false;
	}
	$.ajax({
		type: "get",
		url: callurl + "/article/listByTitle/",
		async: true,
		dataType: 'json',
		data: {
			titleKey: titleKeyi
		},
		contentType: 'application/json; charset=UTF-8',
		success: function(data) {
			$('#article-main').html('');
			if(data.status == 'error') {
				$('#loading-article').css('display', 'none');
				return;
			}
			var content = data.context;
			$.each(content, function(index, article) {
				var html = '';
				html += '<li>';
				html += '<div class="article-box">';
				html += '<div class="article-box-head">';
				html += '<h2>';
				html += '<a href="#" onclick="fill_article_page(' + article.id + ')">' + article.title + '</a>';
				html += '</h2>';
				html += '</div>';
				html += '<div class="article-box-body">';
				html += article.body.toString().split('<br>')[0];
				html += '</div>';
				html += '<dl class="article-box-foot">';
				html += '<dt>';
				html += '<a href="#">';
				html += '<img src="' + article.user.headImgUrl + '"/>';
				html += '</a>';
				html += '</dt>';
				html += '<dd>';
				html += '<a href="#">' + article.user.roleName + '</a>';
				html += '</dd>';
				html += '<div class="interval"></div>';
				html += '<dd>' + article.topic.name + '</dd>';
				html += '<div class="interval"></div>';
				html += '<dd>' + formatDateTime(article.createTime) + '</dd>';
				html += '<div class="article-box-foot-right">';
				html += '<dd>';
				html += '<span>阅读数&nbsp;</span><span style="color: rgb(116,238,91);">' + article.readNum + '</span>';
				html += '</dd>';
				html += '<div class="interval"></div>';
				html += '<dd>';
				html += '<a href="#">评论</a>';
				html += '</dd></div></dl></div></li>';
				$('#article-main').append(html);
				$('#loading-article').css('display', 'none');
				stop = true;
			})
		}
	});
}

function updatePwd() {
	var newPwd = $('#newPwd').val();
	var rnewPwd = $('#rnewPwd').val();
	var phoneNumber = $('#phoneNumber').val();
	var smsCode = $('#smsCode').val();
	if(newPwd != rnewPwd){
		layer.msg("两次密码输入不一致");
		return false;
	}
	var updateData = {
		newPwd: newPwd,
		phoneNumber: phoneNumber,
		smsCode:smsCode
	};
	$.ajax({
		type: "post",
		url: callurl + "/user/updatePwd",
		async: true,
		data: updateData,
		dataType: 'json',
		success: function(data) {
			if(handleAjaxResult(data, "更改成功")) {

			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			layer.msg("失败");
		}
	});
}

function updatePhoneNumberBtn() {
	var newPhoneNumber = $('#newPhoneNumber').val();
	var updateData = {
		newPhoneNumber: newPhoneNumber,
		smsCode: $('#smsCode1').val()
	};
	$.ajax({
		type: "post",
		url: callurl + "/user/updatePhoneNumber",
		async: true,
		data: updateData,
		dataType: 'json',
		success: function(data) {
			if(handleAjaxResult(data, "更改成功")) {

			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			layer.msg("失败");
		}
	});
}

function forgetPwd() {
	var username = $('#username').val();
	var newPwd = $('#newPwd').val();
	var rnewPwd = $('#rnewPwd').val();
	var phoneNumber = $('#phoneNumber').val();
	var smsCode = $('#smsCode').val();
	if(newPwd != rnewPwd) {
		layer.msg("两次输入的密码不一致");
		return false;
	}
	var forgetData = {
		username: username,
		newPwd: newPwd,
		phoneNumber: phoneNumber,
		smsCode:smsCode
	};
	$.ajax({
		type: "post",
		url: callurl + "/user/forgetPwd",
		async: true,
		data: forgetData,
		dataType: 'json',
		success: function(data) {
			if(handleAjaxResult(data, "密码重置成功")) {
				setTimeout(function(){
					$('#login-btn').click();
				},1000);
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			layer.msg("失败");
		}
	});
	
}
