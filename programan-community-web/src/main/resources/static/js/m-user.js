
function loadTabMenu(module_page, call_back1) {
    $(document).ready(function() {
        var pageContent = $('.page-content');
        pageContent.empty();
        pageContent.load(module_page, function() {
            if (!call_back1) {
                return;
            }
            call_back1();
        });
    });
}

function open_column_list_page() {
    loadTabMenu('html/m-user.html', loadColumnList)
}
function loadColumnList() {
    init_column_tool_bar();
    $(function () {
        var table = $("#userdatatable").DataTable({
            "processing": true,
            "serverSide": true,
            'ajax': {
                'contentType': 'application/json',
                'url': callurl + "/user/list",
                'type': 'POST',
                'data': function (d) {
                    return JSON.stringify(d);
                }
            },
            "aLengthMenu": [30, 50, 100, 150], //动态指定分页后每页显示的记录数。
            "searching": true,//禁用搜索
            "lengthChange": true, //是否启用改变每页显示多少条数据的控件
            "sort": "position",  //是否开启列排序，对单独列的设置在每一列的bSortable选项中指定
            "deferRender": true,//延迟渲染
            "bStateSave": false, //在第三页刷新页面，会自动到第一页
            "iDisplayLength": 30,  //默认每页显示多少条记录
            "iDisplayStart": 0,
            "ordering": true,//全局禁用排序
            //"dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
            "columns": [
                {
                    "name": "id", // 指定的列
                    "data": "id",
                    "ordering": true, // 禁用排序
                    "swidth": "2%",
                    "render": function (data, type, full, meta) {
                        return '<div class="checker"> <label> <input type="checkbox" class="checkboxes" value="' + data + '"> <span class="text"></span> </label> </div>';
                    }
                }, {
                    "name": "userName", // 指定的列
                    "data": "userName",
                    "ordering": false, // 禁用排序
                    "searchable": false,
                    "width": "10%"
                }, {
                    "name": "pwd", // 指定的列
                    "data": "pwd",
                    "ordering": false, // 禁用排序
                    "width": "10%",
                    "render": function (data, type, full, meta) {
                        return '<span> ' + format(data, true) + ' </span>';
                    }
                }, {
                    "name": "roleName", // 指定的列
                    "data": "roleName",
                    "ordering": false, // 禁用排序
                    "width": "10%"
                }, {
                    "name": "realName", // 指定的列
                    "data": "realName",
                    "ordering": false, // 禁用排序
                    "width": "10%"
                }, {
                    "name": "sex", // 指定的列
                    "data": "sex",
                    "ordering": false, // 禁用排序
                    "width": "10%"
                }, {
                    "name": "birthday", // 指定的列
                    "data": "birthday",
                    "ordering": false, // 禁用排序
                    "width": "10%"
                }, {
                    "name": "area", // 指定的列
                    "data": "area",
                    "ordering": false, // 禁用排序
                    "width": "10%"
                }, {
                    "name": "industry.name", // 指定的列
                    "data": "industry.name",
                    "ordering": false, // 禁用排序
                    "width": "10%"
                }, {
                    "name": "jobName",
                    "data": "jobName",
                    "ordering": true, // 禁用排序
                    "width": "10%",
//                  "render": function (data, type, full, meta) {  //render改变该列样式,4个参数，其中参数数量是可变的。
//                      return '<button name="editColumn" class="btn btn-danger btn-sm btn-row" data-id=' + data + '>编 辑</button>';
//                  }
                }, {
                    "name": "PNum",
                    "data": "PNum",
                    "ordering": true, // 禁用排序
                    "width": "10%",
//                  "render": function (data, type, full, meta) {  //render改变该列样式,4个参数，其中参数数量是可变的。
//                      return '<button name="attributeColumn" class="btn btn-danger btn-sm btn-row" data-id=' + data + '>属 性</button>';
//                  }
                }, {
                    "name": "name",
                    "data": "name",
                    "ordering": true, // 禁用排序
                    "width": "10%",
                    "render": function (data, type, full, meta) {  //render改变该列样式,4个参数，其中参数数量是可变的。
                        return '<button name="columnDistribution" class="btn btn-danger btn-sm btn-row" data-id=' + data + '>分 布</button>';
                    }
                }
                //data指该行获取到的该列数据
                //row指该行，可用row.name或row[2]获取第3列字段名为name的值
                //type调用数据类型，可用类型“filter”,"display","type","sort",具体用法还未研究
                //meta包含请求行索引，列索引，tables各参数等信息

            ],
            "columnDefs": [
                {
                    "targets": 8,
                    "createdCell": function (td, cellData, rowData, row, col) {
                        if (rowData['canEmpty'] || rowData['invalidCount'] === 0) {
                            $(td).addClass('normal');
                        } else {
                            $(td).addClass('error');
                        }
                    }
                }
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
            drawCallback: function (settings) {
                $('input[name=allChecked]')[0].checked = false;//取消全选状态
                initComplete();
            }
        });

        /**
         * 表格加载渲染完毕后执行的方法
         * @param data
         */
//      function initComplete(data) {
//          initSelectedCheckbox();
//
//          $('button[name="editColumn"]').on('click', function () {
//              var name = $(this).data("id");
//             open_column_detail_page(name);
//          });
//
//          $('button[name="attributeColumn"]').on('click', function () {
//              var name = $(this).data("id");
//              open_attribute_list_page('column', name);
//          });
//
//
//          $('button[name="columnDistribution"]').on('click', function () {
//              var name = $(this).data("id");
//              open_column_distribution_list_page(name);
//          });
//      }
    });
}