
function open_user_role_list_page() {
	loadTabMenu('html/m-user-role.html', loadUserRoleList)
}

function loadUserRoleList() {
	$(function() {
		var table = $("#userroledatatable").DataTable({
			"processing": true,
			"serverSide": true,
			'ajax': {
				'contentType': 'application/json',
				'url': callurl + "/userrole/tablelist",
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
					"swidth": "20%",
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
					"name": "user.userName", // 指定的列
					"data": "user.userName",
					"ordering": false, // 禁用排序
//					"searchable": false,
					"width": "25%"
				}, {
					"name": "role.name", // 指定的列
					"data": "role.name",
					"ordering": false, // 禁用排序
//					"searchable": false,
					"width": "25%"
				}, {
					"name": "id",
					"data": "id",
					"ordering": false, // 禁用排序
					"width": "20%",
					"render": function(data, type, full, meta) { //render改变该列样式,4个参数，其中参数数量是可变的。
						return '<button name="userRoleDetail" class="btn btn-danger btn-sm btn-row" data-id=' + data + '>编 辑</button>';
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
			$('button[name="userRoleDetail"]').on('click', function() {
				var id = $(this).data("id");
				open_user_role_detail_page(id);
			});
		}
	});

	$('#purgeUserRoleBtn').click(function() {
		if(window.confirm('你确定删除吗？')){
             //alert("确定");
            var checkedList = $("table tbody tr input[type=checkbox]:checked");
			if(checkedList.length === 0) {
				layer.msg("至少选一个");
			} else {
				var ids = checkedList.map(function() {
					return $(this)[0].value
				}).get().join(",");
				deleteUserRole(ids);
			}
         }else{
             //alert("取消");
             return false;
         }
	});
	
//	$('#addUserRoleBtn').click(function(){
//		open_user_role_detail_page();
//	});

}

function deleteUserRole(delid) {
	$.ajax({
		type: "post",
		url: callurl + "/userrole/delete/" + delid,
		async: true,
		dataType: 'json',
		contentType: 'application/json; charset=UTF-8',
		success: function(data) {
			handleAjaxResult(data, '删除成功');
			setTimeout(function(){
				open_user_role_list_page();
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
function open_user_role_detail_page(id) {
    loadTabMenu('html/m-user-role-detail.html', function() {
    		loadRoleSelect();
        if (id) {
            getUserRoleDetail(id);
        }
        /**
         * 保存分类
         */
        $("#saveUserRoleBtnSubmit").on('click', function(){
            saveUserRole();
        });

        /**
         * 取消保存
         */
        $("#cancelUserRoleBtnSubmit").on('click', function () {
            open_user_role_list_page();
        });
    })
}

function getUserRoleDetail(id) {
	$.ajax({
        type: "get",
        url: callurl + "/userrole/detail/" + id,
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
            var content = data.context;
            $('#id').val(content.id);
            $('#userName').val(content.user.userName);
            $('#role').val(content.role.id);
        }
    });
}

function saveUserRole() {
    var userRole = {
        id: $('#id').val(),
        userName: $('#userName').val(),
        roleId: $('#role').val()
    };
    $.ajax({
        type: "post",
        url: callurl + "/userrole/save",
        async: true,
        data: userRole,
        dataType: 'json',
        success: function(data) {
           if (handleAjaxResult(data, "保存成功")) {
               open_user_role_list_page();
           }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("失败");
        }
    });

}

function loadRoleSelect() {
	$.ajax({
        type: "get",
        url: callurl + "/role/list/",
        async: true,
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        success: function(data) {
            var content = data.context;
            $('#role').html("");
            $.each(content, function(inde, role) {
            		$('#role').append('<option value='+ role.id + '>' + role.name + '</option>')
            });
        }
    });
}
