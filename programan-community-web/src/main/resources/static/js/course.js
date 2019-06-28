function fill_course_page() {
    loadPage('html/course.html', loadCourse);
}

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

function loadCourse() {
//	$('.slider1').bscSlider({
//		duration: 3000,
//		effect: 1,
//		navigation: true,
//		effect_speed: 750,
//		easing: 'easeOutQuad',
//		height: 400
//	});
	loadCourseTopic();
	loadCourseByTopic(0);
	loadCourseAdvertisement();
}

function loadCourseAdvertisement(){
	$.ajax({
        type: "get",
        url: callurl + "/advertisement/listByLocation/course-top",
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
            var content = data.context;
            loadAdvertisement('slider-course-top', content, 5000);
        },
        error: function(){
        		return null;
        }
   });
}

function loadCourseTopic(){
	$.ajax({
        type: "get",
        url: callurl + "/topic/list/",
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
            var content = data.context;
            $('#courseTopic').html('');
            $.each(content, function(index, topic) {
            		var html = '';
            		html += '<a href="#" onfocus="this.blur();" onclick=loadCourseByTopic(' + topic.id + ')>' + topic.name + '</a>';
            		$('#courseTopic').append(html);
            });
        }
    });
}

function loadCourseByTopic(topicId) {
	var LiCallUrl;
	if(topicId == 0) {
		LiCallUrl = callurl + "/course/list";
	} else {
		LiCallUrl = callurl + "/course/listByTopic/" + topicId;
	}
	$.ajax({
        type: "get",
        url: LiCallUrl,
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
            var content = data.context;
            $('#course-body').html('');
            $.each(content, function(index, course) {
            		var html = '';
            		html += '<div class="course-box">';
            		html += '<div class="course-img">';
            		html += '<a href="javascript:void(0);" onclick="learnCourse(' + course.id + ')"><img src="' + course.imgUrl +'" alt="" /></a>';
            		html += '</div>';
            		html += '<div class="course-info">';
            		html += '<a href="javascript:void(0);" onclick="learnCourse(' + course.id + ')" onfocus="this.blur();">' + course.title + '</a>';
            		html += '<p>' + course.teacherName + '·' + course.teacherJob + '</p>';
            		html += '<div class="skill">';
            		html += course.introduction;
            		html += '</div></div>';
            		html += '<div class="course-footer">';
            		if(course.price < 0) {
            			html += '<h3>已购买</h3>';
            		} else {
            			html += '<h3>' + course.price + 'P豆</h3>';
            		}
            		html += '</div></div>';
            		$('#course-body').append(html);
            });
        }
    });
}

function learnCourse(courseId) {
	$.ajax({
        type: "get",
        url: callurl + "/userCourse/getByBoth/" + courseId,
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
            var status = data.status;
			if(status === '0' || status === 'ok' || status === 0) {
				window.open('/getVideo/' + courseId);
			} else if(data.context == "noUser") {
				$('#login-btn').click();
				layer.msg("请先登录");
			} else {
				buyCourse(courseId);
			}
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("请求失败");
        }
    });
}

function buyCourse(courseIdi) {
	layer.msg('确定要购买该课程？', {
	  time: 0, //不自动关闭
	  btn: ['确定', '取消'],
	  yes: function(index){
	  	
	  	var userCourse = {
	        id: "0",
	        userId: "0",
	        courseId: courseIdi
	    };
	    $.ajax({
	        type: "post",
	        url: callurl + "/userCourse/save",
	        async: true,
	        data: userCourse,
	        dataType: 'json',
	        success: function(data) {
	           if (handleAjaxResult(data, "购买成功")) {

	           }
	        },
	        error: function(XMLHttpRequest, textStatus, errorThrown) {
	            layer.msg("失败");
	        }
	    });
	  	
	  }
	});
}
