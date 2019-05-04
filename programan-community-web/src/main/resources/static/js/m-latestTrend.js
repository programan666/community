
function open_latest_trend() {
//	loadTabMenu('html/m-latestTrend.html', loadLatestTrend)
	var pageContent = $('.page-content');
	pageContent.html('加载中...');
//	$('#loading-latest-page').css("display", "block");
	pageContent.load('/page/latest/trend', {}, function(responseTxt,statusTxt,xhr) {
//		$('#loading-latest-page').css("display", "none");
		$('#latesttrendatatable').DataTable({
			"order": [[ 0, "desc" ]]
		});
	});	
}



//function loadLatestTrend() {
//		$('#latesttrendatatable').DataTable({
//			"processing": true,
//			"serverSide": true,
//			'ajax': {
////				'contentType': 'application/json',
//				'url': callurl + "/page/latest/trend",
//				'type': 'get',
//				"dataType":"json",
//			},
//			"aLengthMenu": [10, 30, 50, 100], //动态指定分页后每页显示的记录数。
//			"searching": true, //禁用搜索
//			"lengthChange": true, //是否启用改变每页显示多少条数据的控件
//			"sort": "position", //是否开启列排序，对单独列的设置在每一列的bSortable选项中指定
//			"deferRender": true, //延迟渲染
//			"bStateSave": false, //在第三页刷新页面，会自动到第一页
//			"iDisplayLength": 10, //默认每页显示多少条记录
//			"iDisplayStart": 0,
//			"ordering": true, //全局禁用排序
//			//"dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
//			"columns": [{
//					"data": "visitDate",
//					"ordering": true, // 禁用排序
//					"swidth": "15%"
//			}, {
//					"data": "visitArea",
//					"ordering": true, // 禁用排序
//					"swidth": "10%"
//			}, {
//					"data": "visitIP",
//					"ordering": true, // 禁用排序
//					"swidth": "15%",
//					//                  "render": function (data, type, full, meta) {
//					//                      return '<div class="checker"> <label> <input type="checkbox" class="checkboxes" value="' + data + '"> <span class="text"></span> </label> </div>';
//					//                  }
//				}, {
//					"data": "os",
//					"ordering": true, // 禁用排序
//					"swidth": "10%",
//					//                  "render": function (data, type, full, meta) {
//					//                      return '<div class="checker"> <label> <input type="checkbox" class="checkboxes" value="' + data + '"> <span class="text"></span> </label> </div>';
//					//                  }
//				}, {
//					"data": "visitorType",
//					"ordering": true, // 禁用排序
//					"swidth": "10%",
//					//                  "render": function (data, type, full, meta) {
//					//                      return '<div class="checker"> <label> <input type="checkbox" class="checkboxes" value="' + data + '"> <span class="text"></span> </label> </div>';
//					//                  }
//				}, {
//					"data": "resolution",
//					"ordering": false, // 禁用排序
////					"searchable": false,
//					"width": "15%"
//				}, {
//					"data": "browser",
//					"ordering": false, // 禁用排序
//					"width": "15%"
//				}, {
//					"data": "visitTime",
//					"ordering": false, // 禁用排序
//					"width": "10%",
//				}
//				//data指该行获取到的该列数据
//				//row指该行，可用row.name或row[2]获取第3列字段名为name的值
//				//type调用数据类型，可用类型“filter”,"display","type","sort",具体用法还未研究
//				//meta包含请求行索引，列索引，tables各参数等信息
//
//			],
//			"oLanguage": { // 国际化配置
//				"sProcessing": "正在获取数据，请稍后...",
//				"sLengthMenu": "显示 _MENU_ 条",
//				"sZeroRecords": "没有找到数据",
//				"sInfo": "从 _START_ 到  _END_ 条记录 总记录数为 _TOTAL_ 条",
//				"sInfoEmpty": "记录数为0",
//				"sInfoFiltered": "(全部记录数 _MAX_ 条)",
//				"sInfoPostFix": "",
//				"sSearch": "搜索",
//				"sUrl": "",
//				"oPaginate": {
//					"sFirst": "第一页",
//					"sPrevious": "上一页",
//					"sNext": "下一页",
//					"sLast": "最后一页"
//				}
//			},
//		});
//
//}
