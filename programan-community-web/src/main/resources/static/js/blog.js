var topic = 0;
var fromIndex = 0;
var pageNum = 10;
var stop=true;

function fill_blog_page() {
    loadPage('/page/blog', loadBlog);
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

function loadBlog() {

	$('#sidebar').stickySidebar({
		sidebarTopMargin: 60,
		footerThreshold: 100
	});
	
	getAdvertisement();
	loadBlogData();
	loadRecommended();
	
	var totalHeight = $(document).height();//整个文档高度
    var seeHeight = $(window).height();//浏览器可视窗口高度
    var thisBodyHeight = $(document.body).height();//浏览器当前窗口文档body的高度
    var totalBodyHeight = $(document.body).outerHeight(true);//浏览器当前窗口文档body的总高度 包括border padding margin
    var thisWidth = $(window).width(); //浏览器当前窗口可视区域宽度
    var thisDocumentWidth = $(document).width();//浏览器当前窗口文档对象宽度
    var thisBodyWidth = $(document.body).width();//浏览器当前窗口文档body的宽度
    var totalBodyWidth = $(document.body).outerWidth(true);//浏览器当前窗口文档body的总宽度 包括border padding margin
    var scrollTop = $(window).scrollTop();//浏览器可视窗口顶端距离网页顶端的高度（垂直偏移）
    //添加滚动事件
    
    $(window).scroll(function(){
        scrollTop = $(window).scrollTop();
        totalHeight = $(document).height();
       // console.log(scrollTop,seeHeight,totalHeight)
        if(scrollTop+seeHeight+50>totalHeight && stop){
        	 	stop = false;
            	loadArticles(topic, 1);
        }
    });
	
}

function loadAdvertisement(elementId, adData, changeTime) {
	'use strict';
	var slider = $('#' + elementId),
	sliderList = $('<ul></ul>'),
	bulletList = $('<ul></ul'),
	
	sliderJSON = adData;
//	sliderJSON = [
//	{
//		"imagePath": "img/test/1.jpg",
//		"order": "2",
//		"url": "#",
//		"slideText": "Appreciate It!"
//	}
//];
	//сортируем массив по order
	sliderJSON.sort(function(a, b) {
		return a.order - b.order;
	});

	$.each(sliderJSON, function(index, element) {
		sliderList.append("<li><a href='"+ element.url +"' target='_blank'><img src='" + element.imagePath + "' alt=''></a>" +
			"<div class='content'>"+ element.slideText +"</div></li>");
		bulletList.append("<li id='bullet_"+ (index + 1) +"'></li>");
	});

	sliderList.addClass('sliderList');
	bulletList.addClass('bulletList');
	slider.append(sliderList);
	slider.append(bulletList);

	bulletList.find("li:first-child").addClass('bulletActive');

	var firstSlide = sliderList.find("li:first-child"),
	lastSlide = sliderList.find("li:last-child"),
	buttonPrev = $(".button-prev"),
	buttonNext = $(".button-next"),
	sliderCount = sliderList.children().length,
	sliderWidth = 100.0 / sliderCount,
	slideIndex = 0,
	intervalID;

	lastSlide.clone().prependTo(sliderList);
	firstSlide.clone().appendTo(sliderList);

	sliderList.css({"width": (100 * sliderCount) + "%"});
	sliderList.css({"margin-left": "-100%"});

	sliderList.find("li").each(function(index) {
		var leftPercent = (sliderWidth * index) + "%";
		$(this).css({"left": leftPercent});
		$(this).css({"width": sliderWidth + "%"});
	});

	buttonPrev.on('click', function() {
		slide(slideIndex - 1);
	});
	buttonNext.on('click', function() {
		slide(slideIndex + 1);
	});
	$('.bulletList li').on('click', function() {
		var id = ($(this).attr('id').split('_')[1]) - 1;
		slide(id);
	});

	startTimer();
	slider.on('mouseenter mouseleave', function(e){ 
    	var onMouEnt = (e.type === 'mouseenter') ?  
        clearInterval(intervalID) : startTimer();               
	});

	function slide(newSlideIndex) {

		var marginLeft = (newSlideIndex * (-100) - 100) + "%";
		sliderList.animate({"margin-left": marginLeft}, 400, function() {
			if ( newSlideIndex < 0 ) {
				$(".bulletActive").removeClass('bulletActive');
				bulletList.find("li:last-child").addClass("bulletActive");
				sliderList.css({"margin-left": ((sliderCount) * (-100)) + "%"});
        		newSlideIndex = sliderCount - 1;
        		slideIndex = newSlideIndex;
        		return;
			} else if ( newSlideIndex >= sliderCount ) {
				$(".bulletActive").removeClass('bulletActive');
				bulletList.find("li:first-child").addClass("bulletActive");
				sliderList.css({"margin-left": "-100%"});
				newSlideIndex = 0;
				slideIndex = newSlideIndex;
				return;
			}
			$(".bulletActive").removeClass('bulletActive');
			bulletList.find('li:nth-child('+ (newSlideIndex + 1) +')').addClass('bulletActive');
			slideIndex = newSlideIndex;
		});
	};

	function startTimer() {
		intervalID = setInterval(function() { buttonNext.click(); }, changeTime);
		return intervalID;
	};
}

function getAdvertisement() {
	$.ajax({
        type: "get",
        url: callurl + "/advertisement/listByLocation/blog-right",
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
            var content = data.context;
            loadAdvertisement('slider', content, 5000);
        },
        error: function(){
        		return null;
        }
   });
   
   $.ajax({
        type: "get",
        url: callurl + "/advertisement2/listByLocation/blog-middle",
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
            var content = data.context;
            loadAdvertisement2('bootstrap-touch-slider', content);
        },
        error: function(){
        		return null;
        }
   });
}

function loadAdvertisement2(elementId, adData) {
	var hcss = ['zoomInRight', 'flipInX', 'zoomInLeft'];
	var pcss = ['fadeInLeft', 'lightSpeedIn', 'fadeInRight'];
	var liele = $('#' + elementId + ' .carousel-indicators');
	var bodyele = $('#' + elementId + ' .carousel-inner');
	liele.html('');
	bodyele.html('');
	$.each(adData, function(index, advertisement2) {
//		alert(index);
//		alert(advertisement2.imgUrl);
		if(index == 0) {
			var html = '';
			html += '<div class="item active">';
			html += '<img src="' + advertisement2.imgUrl + '"  class="slide-image"/>';
			html += '<div class="bs-slider-overlay"></div>';
			html += '<div class="slide-text slide_style_center">';
			html += '<h3 data-animation="animated ' + hcss[index%hcss.length] + '">' + advertisement2.textH + '</h3>';
			html += '<p data-animation="animated ' + pcss[index%pcss.length] +'">' + advertisement2.textP + '</p>';
			html += '<a href="' + advertisement2.url + '" target="_blank"  class="btn btn-primary" data-animation="animated fadeInDown">了解更多</a>';
			html += '</div></div>';
			liele.append('<li data-target="#bootstrap-touch-slider" data-slide-to="' + index +'" class="active"></li>');
			bodyele.append(html);
		} else {
			var html = '';
			html += '<div class="item">';
			html += '<img src="' + advertisement2.imgUrl + '"  class="slide-image"/>';
			html += '<div class="bs-slider-overlay"></div>';
			html += '<div class="slide-text slide_style_center">';
			html += '<h3 data-animation="animated ' + hcss[index%hcss.length] + '">' + advertisement2.textH + '</h3>';
			html += '<p data-animation="animated ' + pcss[index%pcss.length] +'">' + advertisement2.textP + '</p>';
			html += '<a href="' + advertisement2.url + '" target="_blank"  class="btn btn-primary" data-animation="animated fadeInDown">了解更多</a>';
			html += '</div></div>';
			liele.append('<li data-target="#bootstrap-touch-slider" data-slide-to="' + index +'"></li>');
			bodyele.append(html);
		}
	});
	$('#' + elementId).bsTouchSlider();
}

function loadBlogData(){
	loadBlogMenu(1);
	loadArticles(topic);
}

function loadBlogMenu(index){
	$.ajax({
        type: "get",
        url: callurl + "/topic/list",
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
            var content = data.context;
            $('#blogMenu').html("");
            if(index  == 1) {
            		$('#blogMenu').append('<a class="left-nav" href="#/articleTopic" onfocus="this.blur();" onclick="loadArticles(0,0)">最新</a>');
            }
            $.each(content, function(index, topic) {
            		$('#blogMenu').append('<a class="left-nav" href="#/articleTopic" onfocus="this.blur();" onclick="loadArticles(' + topic.id + ',0)">' + topic.name + '</a>');
            });
        }
    });
}
//javascritp:void(0)
function loadArticles(topicId, increment) {
	$('#loading-article').css('display', 'block'); 
	if(increment != 1) {
    		$('#article-main').html('');
    		fromIndex = 0;
    }
	topic = topicId;
//	console.info(topicId);
	$.ajax({
        type: "get",
        url: callurl + "/article/listByTopic/" + topicId + '/' + fromIndex + '/' + pageNum,
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
        		if(data.status == 'error') {
        			$('#loading-article').css('display', 'none');
//      			layer.msg('已经是最底部啦');
        			return;
        		}
        		fromIndex = fromIndex + pageNum;
        		topic = topicId;
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
	            html += '<a href="#" onclick="fill_other_user_info_page(0,' + article.user.id + ')">';
	            html += '<img src="' + article.user.headImgUrl + '"/>';
	            html += '</a>';
	            html += '</dt>';
	            html += '<dd>';
	            html += '<a href="#" onclick="fill_other_user_info_page(0,' + article.user.id + ')">' + article.user.roleName +'</a>';
	            html += '</dd>';
	            html += '<div class="interval"></div>';
	            html += '<dd>' + article.topic.name + '</dd>';
	            html += '<div class="interval"></div>';
	            html += '<dd>' + formatDateTime(article.createTime) + '</dd>';
	            html += '<div class="article-box-foot-right">';
	            html += '<dd>';
	            html += '<span>阅读数&nbsp;</span><span style="color: rgb(54,128,243);">' + article.readNum + '</span>';
	            html += '</dd>';
	            html += '<div class="interval"></div>';
	            html += '<dd>';
	            html += '<a href="#" onclick="fill_article_page(' + article.id + ', true)">评论</a>';
	            html += '</dd></div></dl></div></li>';
	            $('#article-main').append(html);
				$('#loading-article').css('display', 'none'); 
	            stop = true;
            });
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
			$('#loading-article').css('display', 'none'); 
        }
       
  });
}

function loadRecommended() {
	$.ajax({
        type: "get",
        url: callurl + "/recommended/list",
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
            var content = data.context;
            $('#recommended-box').html("");
            $.each(content, function(index, recommended) {
            		var html = '';
            		html += '<li><a href="#" onfocus="this.blur();" onclick="fill_article_page(' + recommended.article.id + ')" class="right-box-img">';
            		html += '<img src="' + recommended.imgUrl + '"/>';
            		html += '</a><a href="#" onfocus="this.blur();" onclick="fill_article_page(' + recommended.article.id + ')" class="right-box-word">';
            		html += recommended.article.title;
            		html += '</a></li>';
            		html += '';
            		html += '';
            		$('#recommended-box').append(html);
            });
        }
    });
}

$.History.bind('/index/blog',function(state){
	fill_blog_page();
});

$.History.bind('/articleTopic',function(state){
	fill_blog_page();
});

$.History.bind('/index/course',function(state){
	fill_course_page();
});

$.History.bind('/index/download',function(state){
	fill_download_page();
});