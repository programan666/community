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
	loadFileTypeSelect();
	loadFileDownload();
}

function loadFileDownload(){
	$.ajax({
        type: "get",
        url: callurl + "/fileDownload/list/",
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
            var content = data.context;
            $('#downloadTotal').html('');
            $.each(content, function(index, fileDownload) {
            		var html = '';
            		html += '<dl><dt><a href="#"><img src="' + fileDownload.fileType.imgUrl + '"/></a></dt><dd>';
            		html += '<a href="javascript:void(0)"  onfocus="this.blur();" onclick="downloadFile('+ fileDownload.id +')">' + fileDownload.title + '</a>';
            		html += '<div class="download-msg"><label><span>上传者：</span>';
            		html += '<em>' + fileDownload.user.roleName + '</em>';
            		html += '</label><label><span>上传时间：</span>';
            		html += '<em>' + fileDownload.uploadDate + '</em>';
            		html += '</label><label style="float: right;"><span>积分/P豆：</span>';
            		html += '<em class="download-p">' + fileDownload.price + '</em>';
            		html += '</label></div></dd></dl>';
            		$('#downloadTotal').append(html);
            });
        }
    });
}

function loadFileTypeSelect() {
	$.ajax({
        type: "get",
        url: callurl + "/fileType/list",
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
            var content = data.context;
            $('#fileType').html("");
            $.each(content, function(inde, fileType) {
            		$('#fileType').append('<option value='+ fileType.id + '>' + fileType.name + '</option>')
            });
        }
    });
}

function selectFileDownload() {
	var selectData = {
		typeId : $('#fileType').val(),
		keyWord : $('#downloadTitle').val()
	};

	$.ajax({
        type: "get",
        url: callurl + "/fileDownload/listByTypeAndKeyWord",
        async: true,
        dataType: 'json',
        data: selectData,
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
            var content = data.context;
            $('#downloadTotal').html('');
            $.each(content, function(index, fileDownload) {
            		var html = '';
            		html += '<dl><dt><a href="#"><img src="' + fileDownload.fileType.imgUrl + '"/></a></dt><dd>';
            		html += '<a href="javascript:void(0)"  onfocus="this.blur();" onclick="downloadFile('+ fileDownload.id +')">' + fileDownload.title + '</a>';
            		html += '<div class="download-msg"><label><span>上传者：</span>';
            		html += '<em>' + fileDownload.user.roleName + '</em>';
            		html += '</label><label><span>上传时间：</span>';
            		html += '<em>' + fileDownload.uploadDate + '</em>';
            		html += '</label><label style="float: right;"><span>积分/P豆：</span>';
            		html += '<em class="download-p">' + fileDownload.price + '</em>';
            		html += '</label></div></dd></dl>';
            		$('#downloadTotal').append(html);
            });
        }
  });
}

function downloadFile(id) {
	if(!$('#susername').html()){
		layer.msg("请先登录");
		$('#login-btn').click();
	} else {
		$.ajax({
	        type: "get",
	        url: callurl + "/fileDownload/detail/" + id,
	        async: true,
			dataType: 'json',
			contentType: 'application/json; charset=UTF-8',
	        success: function(data) {
	           var content = data.context;
	           if(confirm('下载该资源会扣除 ' + content.price +' P豆，确定要下载？')) {
					showDownloadModel(content.price, content.url);
				}
	        },
	        error: function(XMLHttpRequest, textStatus, errorThrown) {
	            layer.msg("请求错误");
	        }
	    });
		
	}
}

function updatePNumd() {
	$.ajax({
        type: "post",
        url: callurl + "/user/updatePNum",
        async: true,
        data: {pNum: $('#downloadFilePrice').val()},
        dataType: 'json',
        success: function(data) {
           if (data.status == 'ok') {
           	
           } else {
           		layer.msg(data.errorMsg);
           }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("请求错误");
        }
    });
}

function showDownloadModel(dprice, durl){
	$.ajax({
        type: "get",
        url: callurl + "/user/getNowPNum",
        async: true,
		dataType: 'json',
		contentType: 'application/json; charset=UTF-8',
        success: function(data) {
           var content = data.context;
           if(Number(content) > Number(dprice)) {
           		$('#downloadFile').modal('show');
				$('#downloadFilePrice').val(dprice)
				$('#downloadFileUrl').attr('href', durl);
           } else {
           	    layer.msg('P豆余额不足，请充值');
           }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("请求错误");
        }
    });
	
}
