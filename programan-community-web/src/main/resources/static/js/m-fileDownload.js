
function open_file_download_list_page() {
	loadTabMenu('html/m-fileDownload.html', loadFileDownloadList)
}

function loadFileDownloadList() {
	$(function() {
		var table = $("#filedownloaddatatable").DataTable({
			"processing": true,
			"serverSide": true,
			'ajax': {
				'contentType': 'application/json',
				'url': callurl + "/fileDownload/tableList",
				'type': 'POST',
				'data': function(d) {
					return JSON.stringify(d);
				}
			},
			"aLengthMenu": [10, 30, 50, 100], //动态指定分页后每页显示的记录数。
			"searching": true, //禁用搜索
			"lengthChange": true, //是否启用改变每页显示多少条数据的控件
			"sort": "position", //是否开启列排序，对单独列的设置在每一列的bSortable选项中指定
			"deferRender": true, //延迟渲染
			"bStateSave": false, //在第三页刷新页面，会自动到第一页
			"iDisplayLength": 10, //默认每页显示多少条记录
			"iDisplayStart": 0,
			"ordering": true, //全局禁用排序
			//"dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
			"columns": [{
					"name": "id", // 指定的列
					"data": "id",
					"ordering": true, // 禁用排序
					"swidth": "5%",
					"render": function(data, type, full, meta) {
						return '<div class="checker"> <label> <input type="checkbox" class="checkboxes" value="' + data + '"> <span class="text"></span> </label> </div>';
					}
				}, {
					"name": "id", // 指定的列
					"data": "id",
					"ordering": true, // 禁用排序
					"swidth": "10%",
					//                  "render": function (data, type, full, meta) {
					//                      return '<div class="checker"> <label> <input type="checkbox" class="checkboxes" value="' + data + '"> <span class="text"></span> </label> </div>';
					//                  }
				}, {
					"name": "title", // 指定的列
					"data": "title",
					"ordering": false, // 禁用排序
//					"searchable": false,
					"width": "20%"
				}, {
					"name": "fileType.name", // 指定的列
					"data": "fileType.name",
					"ordering": false, // 禁用排序
					"width": "10%"
				},  {
					"name": "user.roleName", // 指定的列
					"data": "user.roleName",
					"ordering": false, // 禁用排序
					"width": "10%"
				},  {
					"name": "uploadDate", // 指定的列
					"data": "uploadDate",
					"ordering": false, // 禁用排序
					"width": "15%"
				},  {
					"name": "price", // 指定的列
					"data": "price",
					"ordering": false, // 禁用排序
					"width": "10%"
				},  {
					"name": "downloadNum", // 指定的列
					"data": "downloadNum",
					"ordering": false, // 禁用排序
					"width": "10%"
				}, {
					"name": "id",
					"data": "id",
					"ordering": false, // 禁用排序
					"width": "10%",
					"render": function(data, type, full, meta) { //render改变该列样式,4个参数，其中参数数量是可变的。
						return '<button name="fileDownloadDetail" class="btn btn-danger btn-sm btn-row" data-id=' + data + '>编 辑</button>';
					}
				}
				//data指该行获取到的该列数据
				//row指该行，可用row.name或row[2]获取第3列字段名为name的值
				//type调用数据类型，可用类型“filter”,"display","type","sort",具体用法还未研究
				//meta包含请求行索引，列索引，tables各参数等信息

			],
			"oLanguage": { // 国际化配置
				"sProcessing": "正在获取数据，请稍后...",
				"sLengthMenu": "显示 _MENU_ 条",
				"sZeroRecords": "没有找到数据",
				"sInfo": "从 _START_ 到  _END_ 条记录 总记录数为 _TOTAL_ 条",
				"sInfoEmpty": "记录数为0",
				"sInfoFiltered": "(全部记录数 _MAX_ 条)",
				"sInfoPostFix": "",
				"sSearch": "搜索",
				"sUrl": "",
				"oPaginate": {
					"sFirst": "第一页",
					"sPrevious": "上一页",
					"sNext": "下一页",
					"sLast": "最后一页"
				}
			},
			initComplete: initComplete,
			drawCallback: function(settings) {
				$('input[name=allChecked]')[0].checked = false; //取消全选状态
				initComplete();
			}
		});

		/**
		 * 表格加载渲染完毕后执行的方法
		 * @param data
		 */
		function initComplete(data) {
			initSelectedCheckbox();
			$('button[name="fileDownloadDetail"]').on('click', function() {
				var id = $(this).data("id");
				open_file_download_detail_page(id);
			});
		}
	});

	$('#purgeFileDownloadBtn').click(function() {
		if(window.confirm('你确定删除吗？')){
             //alert("确定");
            var checkedList = $("table tbody tr input[type=checkbox]:checked");
			if(checkedList.length === 0) {
				layer.msg("至少选一个");
			} else {
				var ids = checkedList.map(function() {
					return $(this)[0].value
				}).get().join(",");
				deleteFileDownload(ids);
			}
         }else{
             //alert("取消");
             return false;
         }
	});
	
	$('#addFileDownloadBtn').click(function(){
		open_file_download_detail_page();
	});

}

function deleteFileDownload(delid) {
	$.ajax({
		type: "post",
		url: callurl + "/fileDownload/delete/" + delid,
		async: true,
		dataType: 'json',
		contentType: 'application/json; charset=UTF-8',
		success: function(data) {
			handleAjaxResult(data, '删除成功');
			setTimeout(function(){
				open_file_download_list_page();
			},500)
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			layer.msg("失败");
		}
	});
}

/**
 * 打开详情页
 * @param name
 */
function open_file_download_detail_page(id) {
	loadFileTypeSelect();
    loadTabMenu('html/m-fileDownload-detail.html', function() {
        if (id) {
            getFileDownloadDetail(id);
        }
        /**
         * 保存分类
         */
        $("#saveFileDownloadBtnSubmit").on('click', function(){
            saveFileDownload();
        });

        /**
         * 取消保存
         */
        $("#cancelFileDownloadBtnSubmit").on('click', function () {
            open_file_download_list_page();
        });
    })
}

function getFileDownloadDetail(id) {
	$.ajax({
        type: "get",
        url: callurl + "/fileDownload/detail/" + id,
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
        		loadFileTypeSelect();
            var content = data.context;
            $('#id').val(content.id);
            $('#fileTitle').val(content.title);
            $('#fileType').val(content.fileType.id);
            $('#uploadUser').val(content.user.roleName);
            $('#uploadDate').val(content.uploadDate);
            $('#filePrice').val(content.price);
            $('#downloadNum').val(content.downloadNum);
            $('#downloadUrl').val(content.url);
            $('#fileIntroduction').html(content.introduction);
        }
    });
}

function saveFileDownload() {
    var role = {
        id: $('#id').val(),
        fileTitle: $('#fileTitle').val(),
        fileTypeId: $('#fileType').val(),
        price: $('#filePrice').val(),
        downloadNum: $('#downloadNum').val(),
        introduction: $('#fileIntroduction').val(),
        url: $('#downloadUrl').val()
    };
    $.ajax({
        type: "post",
        url: callurl + "/fileDownload/save",
        async: true,
        data: role,
        dataType: 'json',
        success: function(data) {
           if (handleAjaxResult(data, "保存成功")) {
               open_file_download_list_page();
           }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("失败");
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
