var E = window.wangEditor;
var editor = new E('#editor');
function open_article_list_page() {
	loadTabMenu('html/m-article.html', loadArticleList)
}

function loadArticleList() {
	$(function() {
		var table = $("#articledatatable").DataTable({
			"processing": true,
			"serverSide": true,
			'ajax': {
				'contentType': 'application/json',
				'url': callurl + "/article/tableList",
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
					"swidth": "7%",
					//                  "render": function (data, type, full, meta) {
					//                      return '<div class="checker"> <label> <input type="checkbox" class="checkboxes" value="' + data + '"> <span class="text"></span> </label> </div>';
					//                  }
				}, {
					"name": "title", // 指定的列
					"data": "title",
					"ordering": false, // 禁用排序
//					"searchable": false,
					"width": "18%"
				}, {
					"name": "readNum", // 指定的列
					"data": "readNum",
					"ordering": false, // 禁用排序
					"width": "7%"
				},  {
					"name": "createTime", // 指定的列
					"data": "createTime",
					"ordering": false, // 禁用排序
					"width": "17%"
				},  {
					"name": "topic.name", // 指定的列
					"data": "topic.name",
					"ordering": false, // 禁用排序
					"width": "10%"
				},  {
					"name": "createType.name", // 指定的列
					"data": "createType.name",
					"ordering": false, // 禁用排序
					"width": "10%"
				},   {
					"name": "user.roleName", // 指定的列
					"data": "user.roleName",
					"ordering": false, // 禁用排序
					"width": "13%"
				}, {
					"name": "id",
					"data": "id",
					"ordering": false, // 禁用排序
					"width": "13%",
					"render": function(data, type, full, meta) { //render改变该列样式,4个参数，其中参数数量是可变的。
						var btnHtml = '<button name="articleDetail" class="btn btn-danger btn-sm btn-row" data-id=' + data + '>编 辑</button>';
						btnHtml += '<button name="addRecommendedArticle" class="btn btn-info btn-sm btn-row" data-id=' + data + ' style="margin-left:5px">推 荐</button>';
						return btnHtml;
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
			$('button[name="articleDetail"]').on('click', function() {
				var id = $(this).data("id");
				open_article_detail_page(id);
			});
			$('button[name="addRecommendedArticle"]').on('click', function() {
				var id = $(this).data("id");
				open_add_recommended_detail_page(id);
			});
		}
	});

	$('#purgeArticleBtn').click(function() {
		if(window.confirm('你确定删除吗？')){
             //alert("确定");
            var checkedList = $("table tbody tr input[type=checkbox]:checked");
			if(checkedList.length === 0) {
				layer.msg("至少选一个");
			} else {
				var ids = checkedList.map(function() {
					return $(this)[0].value
				}).get().join(",");
				deleteArticle(ids);
			}
         }else{
             //alert("取消");
             return false;
         }
	});
	
//	$('#addRoleBtn').click(function(){
//		open_role_detail_page();
//	});

}

function deleteArticle(delid) {
	$.ajax({
		type: "post",
		url: callurl + "/article/delete/" + delid,
		async: true,
		dataType: 'json',
		contentType: 'application/json; charset=UTF-8',
		success: function(data) {
			handleAjaxResult(data, '删除成功');
			setTimeout(function(){
				open_article_list_page();
			},500)
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			layer.msg("失败");
		}
	});
}

function open_add_recommended_detail_page(id) {
    loadTabMenu('html/m-recommended-detail.html', function() {
        $('#articleId').val(id);
        $('#articleId').attr('readonly', 'true');
        /**
         * 保存分类
         */
        $("#saveRecommendedBtnSubmit").on('click', function(){
            saveRecommended();
        });

        /**
         * 取消保存
         */
        $("#cancelRecommendedBtnSubmit").on('click', function () {
            open_article_list_page();
        });
    })
}

/**
 * 打开详情页
 * @param name
 */
function open_article_detail_page(id) {
    loadTabMenu('html/m-article-detail.html', function() {
        if (id) {
            getArticleDetail(id);
        }
        /**
         * 保存分类
         */
        $("#saveArticleBtnSubmit").on('click', function(){
            saveArticle();
        });

        /**
         * 取消保存
         */
        $("#cancelArticleBtnSubmit").on('click', function () {
            open_article_list_page();
        });
    })
}

function getArticleDetail(id) {
	$.ajax({
        type: "get",
        url: callurl + "/article/detail/" + id,
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
        		getCreateTypeList();
        		getTopicList();
            var content = data.context;
            $('#articleId').val(content.id);
            $('#articleTitle').val(content.title);
            $('#readNum').val(content.readNum);
            $('#createTime').val(content.createTime);
            $('#topic').val(content.topic.id);
            $('#createType').val(content.createType.id);
            $('#articleAuth').val(content.user.roleName);
		    editor.create();
		    $('.w-e-menu').click(function(){
				if($('.w-e-active').html().toString().indexOf('网络图片') == -1) {
					return false;
				} else {
					setTimeout(function(){
						if($('.w-e-button-container').html().toString().length < 200) {
							var html = '<div style="width: 100%"><div class="head-img" style="width:100px;height:100px">';
							html += '<img src="" id="imgs" style="width:100px;height:100px">';
							html += '</div>';
							html += '文件：<input onchange="get_img(this)" type="file" name="fileUpload6" id="fileUpload6"/>';
							html += '<div class="progress-bar" id="progress-bar" style="float: none;"></div>';
							html += '<input class="model-btn" type="button" value="上传" onclick="mArticleUploadFile(\'editor .w-e-panel-tab-content input\', \'articleImgDir\', \'fileUpload6\')" style="margin-top: 10px;"/></div>';
							$('.w-e-button-container').append(html);
						}
					},1000);
				}
			});
			setTimeout(function(){
				editor.txt.html(content.body);
			}, 500);
        }
    });
    $('#updateArticleBtn').click(function(){
    		updateArticle();
    });
}

function getCreateTypeList() {
	$.ajax({
        type: "get",
        url: callurl + "/createType/list",
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
            var content = data.context;
            $('#createType').html("");
            $.each(content, function(index, createType) {
            		$('#createType').append('<option value='+ createType.id + '>' + createType.name + '</option>')
            });
        }
    });
}

function getTopicList() {
	$.ajax({
        type: "get",
        url: callurl + "/topic/list",
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
            var content = data.context;
            $('#topic').html("");
            $.each(content, function(index, topic) {
            		$('#topic').append('<option value='+ topic.id + '>' + topic.name + '</option>')
            });
        }
    });
}

function updateArticle() {
	var articleInfo = {
		id: $('#articleId').val(),
		articleTitle: $('#articleTitle').val(),
		readNum: $('#readNum').val(),
		articleBody: editor.txt.html(),
		createTypeId: $('#createType').val(),
		topicId: $('#topic').val()
	}
	$.ajax({
        type: "post",
        url: callurl + "/article/mSave",
        async: true,
        data: articleInfo,
        dataType: 'json',
        success: function(data) {
           if (handleAjaxResult(data, "保存成功")) {
               open_article_list_page();
           }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("失败");
        }
    });
}