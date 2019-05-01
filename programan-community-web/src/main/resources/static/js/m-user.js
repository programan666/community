function formatDateTime (date) {
     var time = new Date(Date.parse(date));
     time.setTime(time.setHours(time.getHours() + 8));
     var Y = time.getFullYear() + '-';
     var  M = this.addZero(time.getMonth() + 1) + '-';
     var D = this.addZero(time.getDate()) + ' ';
     var h = this.addZero(time.getHours()) + ':';
     var m = this.addZero(time.getMinutes()) + ':';
     var  s = this.addZero(time.getSeconds());
     return Y + M + D + h + m + s;
}

function  addZero(num) {
    return num < 10 ? '0' + num : num;
} 

function loadTabMenu(module_page, call_back1) {
	$(document).ready(function() {
		var pageContent = $('.page-content');
		pageContent.empty();
		pageContent.load(module_page, function() {
			if(!call_back1) {
				return;
			}
			call_back1();
		});
	});
}

function open_user_list_page() {
	loadTabMenu('html/m-user.html', loadUserList)
}

function handleAjaxResult(data, success_message) {
	var status = data.status;
	if(status === '0' || status === 'ok' || status === 0) {
		layer.msg(success_message);
		return true;
	} else {
		layer.msg("错误: " + data.errorMsg);
		return false;
	}
}

function initSelectedCheckbox() {
	/**
	 * 全选
	 */
	$('input[name=allChecked] input[type=checkbox]').change(function() {
		var set = $("table tbody tr input[type=checkbox]");
		var checked = $(this).is(":checked");
		$(set).each(function() {
			if(checked) {
				$(this).prop("checked", true);
				$(this).parents('tr').addClass("active");
			} else {
				$(this).prop("checked", false);
				$(this).parents('tr').removeClass("active");
			}
		});
	});

	/**
	 * 单个选
	 */
	$('table tbody tr input[type=checkbox]').change(function() {
		$(this).parents('tr').toggleClass("active");
	});
}

function loadUserList() {
	//  init_column_tool_bar();
	$(function() {
		var table = $("#userdatatable").DataTable({
			"processing": true,
			"serverSide": true,
			'ajax': {
				'contentType': 'application/json',
				'url': callurl + "/user/list",
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
					"swidth": "2%",
					"render": function(data, type, full, meta) {
						return '<div class="checker"> <label> <input type="checkbox" class="checkboxes" value="' + data + '"> <span class="text"></span> </label> </div>';
					}
				}, {
					"name": "id", // 指定的列
					"data": "id",
					"ordering": true, // 禁用排序
					"swidth": "6%",
					//                  "render": function (data, type, full, meta) {
					//                      return '<div class="checker"> <label> <input type="checkbox" class="checkboxes" value="' + data + '"> <span class="text"></span> </label> </div>';
					//                  }
				}, {
					"name": "userName", // 指定的列
					"data": "userName",
					"ordering": false, // 禁用排序
//					"searchable": false,
					"width": "10%"
				}, {
					"name": "roleName", // 指定的列
					"data": "roleName",
					"ordering": false, // 禁用排序
					"width": "10%"
				}, {
					"name": "realName", // 指定的列
					"data": "realName",
					"ordering": false, // 禁用排序
					"width": "7%"
				}, {
					"name": "sex", // 指定的列
					"data": "sex",
					"ordering": false, // 禁用排序
					"width": "5%"
				}, {
					"name": "birthday", // 指定的列
					"data": "birthday",
					"ordering": false, // 禁用排序
					"width": "10%"
				}, {
					"name": "area", // 指定的列
					"data": "area",
					"ordering": false, // 禁用排序
					"width": "15%"
				}, {
					"name": "industry.name", // 指定的列
					"data": "industry.name",
					"ordering": false, // 禁用排序
					"width": "10%"
				}, {
					"name": "jobName",
					"data": "jobName",
					"ordering": false, // 禁用排序
					"width": "10%",
					//                  "render": function (data, type, full, meta) {  //render改变该列样式,4个参数，其中参数数量是可变的。
					//                      return '<button name="editColumn" class="btn btn-danger btn-sm btn-row" data-id=' + data + '>编 辑</button>';
					//                  }
				}, {
					"name": "pnum", // 指定的列
					"data": "pnum",
					"ordering": false, // 禁用排序
					"width": "5%"
				}, {
					"name": "id",
					"data": "id",
					"ordering": false, // 禁用排序
					"width": "8%",
					"render": function(data, type, full, meta) { //render改变该列样式,4个参数，其中参数数量是可变的。
						return '<button name="userDetail" class="btn btn-danger btn-sm btn-row" data-id=' + data + '>编 辑</button>';
					}
				}
				//data指该行获取到的该列数据
				//row指该行，可用row.name或row[2]获取第3列字段名为name的值
				//type调用数据类型，可用类型“filter”,"display","type","sort",具体用法还未研究
				//meta包含请求行索引，列索引，tables各参数等信息

			],
			"columnDefs": [{
				"targets": 8,
				"createdCell": function(td, cellData, rowData, row, col) {
					if(rowData['canEmpty'] || rowData['invalidCount'] === 0) {
						$(td).addClass('normal');
					} else {
						$(td).addClass('error');
					}
				}
			}],
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
			$('button[name="userDetail"]').on('click', function() {
				var id = $(this).data("id");
				open_user_detail_page(id);
			});

			$('button[name="attributeColumn"]').on('click', function() {
				var name = $(this).data("id");
				//              open_attribute_list_page('column', name);
			});

			$('button[name="columnDistribution"]').on('click', function() {
				var name = $(this).data("id");
				//              open_column_distribution_list_page(name);
			});
		}
	});

	$('#purgeUserBtn').click(function() {
		if(window.confirm('你确定删除吗？')){
             //alert("确定");
            var checkedList = $("table tbody tr input[type=checkbox]:checked");
			if(checkedList.length === 0) {
				layer.msg("至少选一个");
			} else {
				var ids = checkedList.map(function() {
					return $(this)[0].value
				}).get().join(",");
				deleteUser(ids);
			}
         }else{
             //alert("取消");
             return false;
         }
	});
	
	$('#addUserBtn').click(function(){
		open_user_detail_page();
	});

}

function deleteUser(delid) {
	$.ajax({
		type: "post",
		url: callurl + "/user/delete/" + delid,
		async: true,
		dataType: 'json',
		contentType: 'application/json; charset=UTF-8',
		success: function(data) {
			handleAjaxResult(data, '删除成功');
			setTimeout(function(){
				open_user_list_page();
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
function open_user_detail_page(id) {
    loadTabMenu('html/m-user-detail.html', function() {
    		loadIndustrySelect();
        if (id) {
            getUserDetail(id);
        }
        /**
         * 保存分类
         */
        $("#saveUserBtnSubmit").on('click', function(){
            saveUser();
        });

        /**
         * 取消保存
         */
        $("#cancelUserBtnSubmit").on('click', function () {
            open_user_list_page();
        });
    })
}

function getUserDetail(id) {
	$.ajax({
        type: "get",
        url: callurl + "/user/detail/" + id,
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
            var content = data.context;
            $('#id').val(content.id);
            $('#userName').val(content.userName);
            $('#pwd').val(content.pwd);
            $('#roleName').val(content.roleName);
            $('#realName').val(content.realName);
            $('#sex').val(content.sex);
            $('#birthday').val(content.birthday);
            $('#phone').val(content.phone);
            $('#area').val(content.area);
            $('#industry').val(content.industry.id);
            $('#jobName').val(content.jobName);
            $('#headImgUrl').val(content.headImgUrl);
            $('#pNum').val(content.pnum);
            $('#introduction').val(content.introduction);
        }
    });
}

function loadIndustrySelect() {
	$('.datepicker').datepicker({
		language: 'zh-CN',
		format: 'yyyy-mm-dd'
	});
	$.ajax({
        type: "get",
        url: callurl + "/industry/list/",
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
            var content = data.context;
            $('#industry').html("");
            $.each(content, function(inde, industry) {
            		$('#industry').append('<option value='+ industry.id + '>' + industry.name + '</option>')
            });
        }
    });
}

function saveUser() {
    var user = {
        id: $('#id').val(),
        userName: $('#userName').val(),
        pwd: $('#pwd').val(),
        roleName: $('#roleName').val(),
        realName: $('#realName').val(),
        sex: $('#sex').val(),
        birthday: $('#birthday').val(),
		phone: $('#phone').val(),
        area: $('#area').val(),
        industryId: $('#industry').val(),
        jobName: $('#jobName').val(),
        introduction: $('#introduction').val(),
        headImgUrl: $('#headImgUrl').val(),
        pNum: $('#pNum').val()
    };
    $.ajax({
        type: "post",
        url: callurl + "/user/save",
        async: true,
        data: user,
        dataType: 'json',
//      contentType: 'application/json; charset=UTF-8',
        success: function(data) {
           if (handleAjaxResult(data, "保存成功")) {
               open_user_list_page();
           }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("失败");
        }
    });

}
//
//function mUpdateImg(showInputId, filePath) {
//	var formData = new FormData();
//	formData.append("file", $("#fileUpload")[0].files[0]);
//	formData.append("path", filePath);
//	$.ajax({
//		url: callurl + '/file/upload',
//		data: formData,
//		type: 'POST',
//		cache: false,
//		dataType: "json",
//		mimeType: "multipart/form-data",
//		processData: false,
//		contentType: false,
//		success: function(data) {
//			if(handleAjaxResult(data, "上传成功")) {
//				$('#' + showInputId).val(data.context);
//			}
//			$('#uploadFile').modal('hide');
//		},
//		error: function() {
//			console.log("上传出现异常");
//		},
//	});
//}

//参数说明：showInputId:返回的url显示的控件id， filePath：上传路径  modelId:上传完需要关闭的model的id  inputFile: 输入文件id
function mUploadFile(showInputId, filePath, modelId, inputFile) {
	var formData = new FormData();
	formData.append("file", $("#" + inputFile)[0].files[0]);
	formData.append("path", filePath);
	$.ajax({
		url: callurl + '/file/upload',
		data: formData,
		type: 'POST',
		cache: false,
		dataType: "json",
		mimeType: "multipart/form-data",
		processData: false,
		contentType: false,
		success: function(data) {
			if(handleAjaxResult(data, "上传成功")) {
				$('#' + showInputId).val(data.context);
			}
			$('#' + modelId).modal('hide');
		},
		error: function() {
			console.log("上传出现异常");
		},
		xhr: function () {
            myXhr = $.ajaxSettings.xhr();
            if (myXhr.upload) {
                //绑定progress事件的回调函数  
                myXhr.upload.addEventListener('progress', progressHandlingFunction, false);
            }
            return myXhr; //xhr对象返回给jQuery使用
        }
	});
}

function progressHandlingFunction(event) {
    var loaded = Math.floor(100 * (event.loaded / event.total)); //已经上传的百分比
    $("#progress-bar").html(loaded + "%").css("width", loaded + "%");
}
