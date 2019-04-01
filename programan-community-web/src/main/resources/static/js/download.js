function fill_download_page() {
    loadPage('html/download.html', loadDownload);
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

function loadDownload() {
	$('.tab-group').tabify();
}

