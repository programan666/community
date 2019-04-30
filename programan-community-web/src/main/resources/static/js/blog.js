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
	$('#bootstrap-touch-slider').bsTouchSlider();
//	loadAdvertisement();
	getAdvertisement();
	loadBlogData();
	
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

function loadAdvertisement(adData) {
	'use strict';
	var slider = $('#slider'),
	sliderList = $('<ul></ul>'),
	bulletList = $('<ul></ul'),
	
	sliderJSON = adData;
//	sliderJSON = [
//	{
//		"imagePath": "img/test/1.jpg",
//		"order": "2",
//		"url": "#",
//		"slideText": "Appreciate It!"
//	},
//	{
//		"imagePath": "img/test/2.jpg",
//		"order": "3",
//		"url": "#",
//		"slideText": "I really do!"
//	}, 
//	{
//		"imagePath": "img/test/3.jpg",
//		"order": "1",
//		"url": "#",
//		"slideText": "Thank you, Egor!"
//	},
//	{
//		"imagePath": "img/test/4.jpg",
//		"order": "4",
//		"url": "#",
//		"slideText": "eks dee"
//	},
//	{
//		"imagePath": "img/test/2.jpg",
//		"order": "5",
//		"url": "#",
//		"slideText": "eks dee"
//	}
//];
	//сортируем массив по order
	sliderJSON.sort(function(a, b) {
		return a.order - b.order;
	});

	//создаем слайды из json'a
	$.each(sliderJSON, function(index, element) {
		sliderList.append("<li><a href='"+ element.url +"'><img src='" + element.imagePath + "' alt=''></a>" +
			"<div class='content'>"+ element.slideText +"</div></li>");
		bulletList.append("<li id='bullet_"+ (index + 1) +"'></li>");
	});

	//добавляем классы к листам и добавляем их в DOM
	sliderList.addClass('sliderList');
	bulletList.addClass('bulletList');
	slider.append(sliderList);
	slider.append(bulletList);

	//Делаем первый буллет активным
	bulletList.find("li:first-child").addClass('bulletActive');

	var firstSlide = sliderList.find("li:first-child"),
	lastSlide = sliderList.find("li:last-child"),
	buttonPrev = $(".button-prev"),
	buttonNext = $(".button-next"),
	sliderCount = sliderList.children().length,
	sliderWidth = 100.0 / sliderCount,
	slideIndex = 0,
	intervalID;

	//Добавляем первый и последний слайды в начало и конец массива (для плавной анимации)
	lastSlide.clone().prependTo(sliderList);
	firstSlide.clone().appendTo(sliderList);

	//Рассчитываем ширину листа
	sliderList.css({"width": (100 * sliderCount) + "%"});
	sliderList.css({"margin-left": "-100%"});

	//Рассчитываем позицию слайдов
	sliderList.find("li").each(function(index) {
		var leftPercent = (sliderWidth * index) + "%";
		$(this).css({"left": leftPercent});
		$(this).css({"width": sliderWidth + "%"});
	});

	//А вот и обработчики подъехали
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

	//Запускаем таймер и обрабатываем его остановку
	startTimer();
	slider.on('mouseenter mouseleave', function(e){ 
    	var onMouEnt = (e.type === 'mouseenter') ?  
        clearInterval(intervalID) : startTimer();               
	});

	//Управляет анимацией, землей, небом и даже Аллахом
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

	//Нажимает на кнопку раз в 5 секунд
	function startTimer() {
		intervalID = setInterval(function() { buttonNext.click(); }, 5000);
		return intervalID;
	};
}

function getAdvertisement() {
	$.ajax({
        type: "get",
        url: callurl + "/advertisement/list",
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
            var content = data.context;
            loadAdvertisement(content);
        },
        error: function(){
        		return null;
        }
   });
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
            		$('#blogMenu').append('<a class="left-nav" href="#/articleTopic" onfocus="this.blur();" onclick="loadArticles(0,0)">推荐</a>');
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
	            html += '<a href="#">';
	            html += '<img src="' + article.user.headImgUrl + '"/>';
	            html += '</a>';
	            html += '</dt>';
	            html += '<dd>';
	            html += '<a href="#">' + article.user.roleName +'</a>';
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
            });
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
			$('#loading-article').css('display', 'none'); 
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