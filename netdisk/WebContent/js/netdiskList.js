$(function () {
    $("#jqGrid").jqGrid({
        url: '../getFileList',
        datatype: "json",
        mtype:"POST", 
        postData:{},
        colModel: [			
			{ label: '名称', name: 'name', index: 'name', width: 50, formatter:nameFmatter},
			{ label: '路径', name: 'absolutePath', index: 'absolutePath', width: 50, key: true },
			{ label: '文件长度', name: 'length', index: 'length', width: 80 ,sortable: false },
			{ label: '是否目录', name: 'isdir', index: 'isdir', width: 80 ,sortable: false },
			{ label: '备份因子', name: 'block_replication', index: 'block_replication', width: 80 ,sortable: false },
			{ label: '块大小', name: 'blocksize', index: 'blocksize', width: 80,sortable: false },
			{ label: '修改时间', name: 'modification_time', index: 'modification_time', width: 80 ,sortable: false },
        ],
		viewrecords: true,
        height: 385,
        autowidth:true,
        multiselect: true,
        jsonReader : {
            root: "fileList"  // json格式对应后台服务返回
        }, 
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        },
        ondblClickRow:function(rowId){
            var rowData = $("#jqGrid").getRowData(rowId);  //1.获取选中行的数据
            var isdir = rowData.isdir;                   //2.得到选中数据的某个属性

            if(isdir == "true"){
				vm.showList = true;
				
				vm.urls = rowId;
				$("#jqGrid").jqGrid('setGridParam',{postData:{path:rowId}}).trigger("reloadGrid");
            }else{
            	vm.readFile(rowId);
            }
        }
    });
});

//jqgrid列表字段 name 自定义
function nameFmatter(cellvalue, options, rowObjec){
    var isdir = rowObjec.isdir;                   //得到选中数据的某个属性
	if(isdir){
		return  "<img src='../STATIC/folder.png' />" + cellvalue;
	} else {
		return  "<img src='../STATIC/file.png' />" + cellvalue;
	}
}

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,// 页面切换
		title: null, // 标题
		urls:"",// 目录导航 变量
		content:{}  // 查看文件的变量
	},
	methods: {
		returnPage: function () { // 返回
			if(vm.urls == ""){
				return;
			}
			var url = vm.urls;
			var index = url.lastIndexOf("/");
			if(index == 0){
				vm.urls =  "/";
			}else{
				vm.urls = url.substring(0,index);
			}
			vm.reload();
		},
		readFile: function(rowId){ // 读文件
			vm.showList = false;
			vm.title = "查看";
			
			// 通过ajax获取文件内容：vm.content = "模拟传值";
			$.ajax({
				url : "../readFromHDFSFile",// 请求地址
				type : "POST",// 请求类型
				datatype : "json",// 返回类型
				data :{"filePath":rowId},
				success : function(data) {
					if(data.code === 200){
						vm.content = data.content;
					}else{
						alert(data.msg);
					}
				}
			});
			 
		},
		download:function(){
			var absolutePath = getSelectedRows();
			if(absolutePath == null){
				return;
			}
			if(absolutePath.length > 1){
				alert("只能选择一个文件删除！");
				return;
			}
			
			var rowData = $("#jqGrid").getRowData(absolutePath[0]);  //1.获取选中行的数据
	        var isdir = rowData.isdir;                   //2.得到选中数据的某个属性
	        if(isdir == "true"){
	        	alert("是目录，暂时不能打包下载！");
	        	return;
	        }
	        // 下载
			window.location.href = "../downloadFile?src=" + absolutePath[0];
	        
		},
		del: function (event) {
			var absolutePath = getSelectedRows();
			if(absolutePath == null){
				return;
			}
			if(absolutePath.length > 1){
				alert("只能选择一个文件删除！");
				return;
			}
			
			var rowData = $("#jqGrid").getRowData(absolutePath[0]);  //1.获取选中行的数据
	        var isdir = rowData.isdir;                   //2.得到选中数据的某个属性
	        if(isdir == "true"){
	        	alert("是目录，不允许删除！");
	        	return;
	        }
	        
	        //confirm('确定要删除选中的记录？', function(){
				$.ajax({
						url : "../delectFromHDFS",// 请求地址
						type : "POST",// 请求类型
						datatype : "json",// 返回类型
						data :{"path":absolutePath[0]},
						//async:false, // 同步执行代码
						success : function(data) {
							if (data.code == 200) {
								alert('操作成功', function(index){
									vm.reload();
								});
							} else {
								alert("操作失败")
							}
						}
				});
	        //});
		},
		reload: function (event) {
			vm.showList = true;
			rowId = vm.urls;
			if(rowId == ""){
				rowId = "/";
			}
			$("#jqGrid").jqGrid('setGridParam',{postData:{path:rowId}}).trigger("reloadGrid");
		}
	}
});

// 上传绑定事件
$(function(){
		var opts = {
	        'buttonText':'<a class="btn btn-danger">点击上传</a>',
	        'auto': true,
	        'uploadScript': '../uploadFile?path='+vm.urls,// 提交路径
	        'multi':false,
	        'removeCompleted':true,
	        'height': '50px',
	        'fileTypeExts':'*.zip; *.rar;*.doc;*.java',
	        'fileSizeLimit':'10MB',
	        'formData': {"path":"/user1"}, //提交给服务器端的参数
	        'onUploadComplete' : function(file, data) {
	            data = eval('('+data+')');debugger;
	            if(data.code == 200){
	            	$("#jqGrid").jqGrid('setGridParam',{postData:{path:"/"}}).trigger("reloadGrid");
	            }
	        }	
		};
		$("#fileUploadId").uploadifive(opts);
});