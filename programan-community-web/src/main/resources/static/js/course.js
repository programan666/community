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
	$('.slider1').bscSlider({
		duration: 3000,
		effect: 1,
		navigation      : true,
		effect_speed: 750,
		easing: 'easeOutQuad',
		height			: 400
	});
}

