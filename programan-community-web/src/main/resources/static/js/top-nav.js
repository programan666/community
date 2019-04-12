var callurl = "http://127.0.0.1:9999";

function fill_top_nav_page() {
    loadNav('html/top-nav.html', loadMenu);
}

function loadNav(module_page, call_back) {
    $(document).ready(function() {
        var pageContent = $('.page-nav');
        pageContent.empty();
        pageContent.load(module_page, function() {
	        	if (!call_back) {
                return;
            }
            call_back();
        });
    });
}

function loadMenu() {
	$("div.navbar.fixed-top").autoHidingNavbar();
	$("#user-dropdown").hover(function() {
		$('#user-dropdown-show').css("display", "block");
	}, function() {
		$('#user-dropdown-show').css("display", "none");
	});
}

