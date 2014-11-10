errorAlias = {
	errCode : "错误代码",
	clientId : "客户端ID",
	clientName : "客户端",
	errType : "错误类型",
	errDetail : "错误内容",
	createTime : "发生时间",
	queryNum : "查询次数",
	queryTime : "最近查询时间"
};
errorAliasName = "日志管理";
errorBaseUrl = 'error.do';
errorAction = {
	list : errorBaseUrl + "?reqCode=list",
	save : errorBaseUrl + "?reqCode=save",
	update : errorBaseUrl + "?reqCode=update",
	del : errorBaseUrl + "?reqCode=delete",
	batchSql : errorBaseUrl + "?reqCode=batchSql"
};

Ext.onReady(function() {
	var qForm = new Ext.form.FormPanel({
				region : 'north',
				margins : '3 3 3 3',
				title : '<span class="commoncss">查询条件<span>',
				collapsible : true,
				border : true,
				labelWidth : 50, // 标签宽度
				frame : false, // 是否渲染表单面板背景色
				labelAlign : 'right', // 标签对齐方式
				bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
				buttonAlign : 'center',
				height : 125,
				items : [{
							layout : 'column',
							border : false,
							items : [{
										columnWidth : .33,
										layout : 'form',
										labelWidth : 60, // 标签宽度
										defaultType : 'textfield',
										border : false,
										items : [{
													xtype : 'textfield',
													anchor : '100%',
													fieldLabel : errorAlias.errCode,
													name : 'errCode'
												}]
									}, {
										columnWidth : .33,
										layout : 'form',
										labelWidth : 60, // 标签宽度
										defaultType : 'textfield',
										border : false,
										items : [{
													xtype : 'textfield',
													anchor : '100%',
													fieldLabel : errorAlias.clientName,
													name : 'clientName'
												}]
									}, {
										columnWidth : .33,
										layout : 'form',
										labelWidth : 60, // 标签宽度
										defaultType : 'textfield',
										border : false,
										items : [new Ext.form.ComboBox({
													hiddenName : 'errType',
													fieldLabel : errorAlias.errType,
													emptyText : '请选择',
													triggerAction : 'all',
													store : ERRORTYPEStore,
													displayField : 'text',
													valueField : 'value',
													mode : 'local',
													forceSelection : false, // 选中内容必须为下拉列表的子项
													editable : false,
													typeAhead : true,
													resizable : true,
													anchor : '100%'
												})]
									}]
						}],
				buttons : [{
							text : '查询',
							iconCls : 'previewIcon',
							handler : function() {
								Ext.getCmp('tbi_edit').disable();
								Ext.getCmp('tbi_del').disable();
								queryDatas();
							}
						}, {
							text : '重置',
							iconCls : 'tbar_synchronizeIcon',
							handler : function() {
								qForm.getForm().reset();
							}
						}]
			});

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'NO',
				width : 28
			});

	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum, {
				header : errorAlias.errCode,
				width : 100,
				sortable : true,
				dataIndex : 'errCode'
			}, {
				header : errorAlias.clientName,
				width : 100,
				sortable : true,
				dataIndex : 'clientName'
			}, {
				header : errorAlias.errType,
				sortable : true,
				renderer : ERRORTYPERender,
				dataIndex : 'errType'
			}, {
				header : errorAlias.errDetail,
				width : 100,
				sortable : true,
				dataIndex : 'errDetail'
			}, {
				header : errorAlias.createTime,
				width : 100,
				sortable : true,
				dataIndex : 'createTime'
			}, {
				header : errorAlias.queryNum,
				width : 100,
				sortable : true,
				dataIndex : 'queryNum'
			}, {
				header : errorAlias.queryTime,
				width : 100,
				sortable : true,
				dataIndex : 'queryTime'
			}]);

	/**
	 * 数据存储
	 */
	var store = new Ext.data.Store({
				// 获取数据的方式
				proxy : new Ext.data.HttpProxy({
							url : errorAction.list
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'TOTALCOUNT', // 记录总数
							root : 'ROOT' // Json中的列表数据根节点
						}, ['errCode', 'clientId', 'errType', 'errDetail',
								'createTime', 'queryNum', 'queryTime',
								'clientName'])
			});

	/**
	 * 翻页排序时候的参数传递
	 */
	// 翻页排序时带上查询条件
	store.on('beforeload', function() {
				this.baseParams = qForm.getForm().getValues();
			});
	// 每页显示条数下拉选择框
	var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
							fields : ['value', 'text'],
							data : [[10, '10条/页'], [20, '20条/页'],
									[50, '50条/页'], [100, '100条/页'],
									[250, '250条/页'], [500, '500条/页']]
						}),
				valueField : 'value',
				displayField : 'text',
				value : '20',
				editable : false,
				width : 85
			});
	var number = parseInt(pagesize_combo.getValue());
	// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
				bbar.pageSize = parseInt(comboBox.getValue());
				number = parseInt(comboBox.getValue());
				store.reload({
							params : {
								start : 0,
								limit : bbar.pageSize
							}
						});
			});

	// 分页工具栏
	var bbar = new Ext.PagingToolbar({
				pageSize : number,
				store : store,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
				emptyMsg : "没有符合条件的记录",
				items : ['-', '&nbsp;&nbsp;', pagesize_combo]
			});

	// 表格工具栏
	var tbar = new Ext.Toolbar({
				items : [{
							text : '查看',
							id : 'tbi_edit',
							iconCls : 'edit1Icon',
							disabled : true,
							handler : function() {
								viewCatalogItem();
							}
						}, {
							text : '删除',
							id : 'tbi_del',
							iconCls : 'deleteIcon',
							disabled : true,
							handler : function() {
								deleteCatalogItem();
							}
						}, '->', {
							text : '刷新',
							iconCls : 'arrow_refreshIcon',
							handler : function() {
								store.reload();
							}
						}]
			});

	// 表格实例
	var grid = new Ext.grid.GridPanel({
				// 表格面板标题,默认为粗体，我不喜欢粗体，这里设置样式将其格式为正常字体
				title : '<span class="commoncss">' + errorAliasName + '</span>',
				height : 500,
				id : 'id_grid_sfxm',
				autoScroll : true,
				frame : false,
				region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				margins : '3 3 3 3',
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				tbar : tbar, // 表格工具栏
				bbar : bbar,// 分页工具栏
				viewConfig : {
					forceFit : true
				},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});

	// 监听行选中事件
	grid.on('rowclick', function(pGrid, rowIndex, event) {
				Ext.getCmp('tbi_edit').enable();
				Ext.getCmp('tbi_del').enable();
			});

	grid.on('rowdblclick', function(grid, rowIndex, event) {
				viewCatalogItem();
			});

	var viewForm = new Ext.form.FormPanel({
		collapsible : false,
		border : true,
		labelWidth : 60, // 标签宽度
		// frame : true, //是否渲染表单面板背景色
		labelAlign : 'right', // 标签对齐方式
		bodyStyle : 'padding:5 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		items : [{
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .33,
								layout : 'form',
								labelWidth : 60, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : errorAlias.errCode,
											name : 'errCode',
											fieldClass : 'x-custom-field-disabled',
											xtype : 'textfield', // 设置为数字输入框类型
											anchor : '100%'
										}, {
											fieldLabel : errorAlias.createTime,
											name : 'createTime',
											allowBlank : false,
											maxLength : 100,
											anchor : '100%'
										}, new Ext.form.ComboBox({
													hiddenName : 'errType',
													fieldLabel : errorAlias.errType,
													emptyText : '请选择',
													triggerAction : 'all',
													store : ERRORTYPEStore,
													displayField : 'text',
													valueField : 'value',
													mode : 'local',
													forceSelection : false, // 选中内容必须为下拉列表的子项
													editable : false,
													typeAhead : true,
													resizable : true,
													allowBlank : false,
													labelStyle : 'color:blue;',
													anchor : '100%'
												})]
							}, {
								columnWidth : .67,
								layout : 'form',
								labelWidth : 60, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : errorAlias.clientName,
											name : 'clientName',
											maxLength : 100,
											allowBlank : false,
											anchor : '100%'
										}, {
											maxLength : 100,
											allowBlank : false,
											anchor : '100%',
											fieldLabel : errorAlias.queryTime,
											name : 'queryTime'
										}, {
											fieldLabel : errorAlias.queryNum,
											name : 'queryNum',
											maxLength : 100,
											allowBlank : false,
											anchor : '100%'
										}]
							}]
				}, {
					fieldLabel : errorAlias.errDetail,
					name : 'errDetail',
					xtype : 'textarea',
					maxLength : 200,
					anchor : '100%'
				}]
	});
	var viewWindow = new Ext.Window({
				title : '<span class="commoncss">查看' + errorAliasName
						+ '<span>', // 窗口标题
				layout : 'fit', // 设置窗口布局模式
				width : 600, // 窗口宽度
				height : 400, // 窗口高度
				closable : false, // 是否可关闭
				collapsible : true, // 是否可收缩
				maximizable : true, // 设置是否可以最大化
				border : false, // 边框线设置
				constrain : true, // 设置窗口是否可以溢出父容器
				animateTarget : Ext.getBody(),
				pageY : 20, // 页面定位Y坐标
				pageX : document.body.clientWidth / 2 - 600 / 2, // 页面定位X坐标
				items : [viewForm], // 嵌入的表单面板
				buttons : [{
							text : '关闭',
							iconCls : 'deleteIcon',
							handler : function() {
								viewWindow.hide();
							}
						}]
			});

	// 布局
	// 如果把form作为center区域的话,其Height属性将失效。
	var viewport = new Ext.Viewport({
				layout : 'border',
				items : [qForm, grid]
			});

	/**
	 * 查询项目列表
	 */
	function queryDatas() {
		var params = qForm.getForm().getValues();
		params.start = 0;
		params.limit = bbar.pageSize;
		store.load({
					params : params
				});
	}

	queryDatas();

	/**
	 * 修改项目
	 */
	function viewCatalogItem() {
		var record = grid.getSelectionModel().getSelected();
		if (Ext.isEmpty(record)) {
			Ext.Msg.alert('提示:', '请先选中数据');
			return;
		}
		viewForm.getForm().loadRecord(record);
		viewWindow.show(); // 显示窗口
	}

});