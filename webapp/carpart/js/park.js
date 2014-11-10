parkAlias = {
	parkId : "ID",
	parkName : "名字",
	address : "地址",
	city : "城市",
	officeTime : "营业时间",
	feeRules : "计费规则",
	rulesDesc : "计费规则说明",
	mapLb : "经纬度",
	memo : "备注信息",
	status : "状态"
};
parkAliasName = "停车场";
parkBaseUrl = 'park.do';
parkAction = {
	list : parkBaseUrl + "?reqCode=list",
	save : parkBaseUrl + "?reqCode=save",
	update : parkBaseUrl + "?reqCode=update",
	del : parkBaseUrl + "?reqCode=delete",
	batchSql : parkBaseUrl + "?reqCode=batchSql"
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
													fieldLabel : parkAlias.parkName,
													name : 'parkName'
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
													fieldLabel : parkAlias.address,
													name : 'address'
												}]
									}, {
										columnWidth : .33,
										layout : 'form',
										labelWidth : 60, // 标签宽度
										defaultType : 'textfield',
										border : false,
										items : [new Ext.form.ComboBox({
													hiddenName : 'status',
													fieldLabel : parkAlias.status,
													emptyText : '请选择',
													triggerAction : 'all',
													store : QYBZStore,
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
		header : parkAlias.parkId,
		dataIndex : 'parkId', // 数据索引:和Store模型对应
		hidden : true
		}, {
		header : parkAlias.parkName,
		width : 100,
		sortable : true,
		dataIndex : 'parkName'
	}, {
		header : parkAlias.address,
		width : 100,
		sortable : true,
		dataIndex : 'address'
	}, {
		header : parkAlias.city,
		width : 100,
		sortable : true,
		renderer : CITYCODERender,
		dataIndex : 'city'
	}, {
		header : parkAlias.officeTime,
		width : 100,
		sortable : true,
		dataIndex : 'officeTime'
	}, {
		header : parkAlias.rulesDesc,
		width : 100,
		sortable : true,
		dataIndex : 'rulesDesc'
	}, {
		header : parkAlias.mapLb,
		width : 100,
		sortable : true,
		dataIndex : 'mapLb'
	}, {
		header : parkAlias.status,
		sortable : true,
		renderer : QYBZRender,
		dataIndex : 'status'
	}]);

	/**
	 * 数据存储
	 */
	var store = new Ext.data.Store({
				// 获取数据的方式
				proxy : new Ext.data.HttpProxy({
							url : parkAction.list
						}),
				reader : new Ext.data.JsonReader({
							totalProperty : 'TOTALCOUNT', // 记录总数
							root : 'ROOT' // Json中的列表数据根节点
						}, ['parkId', 'parkName', 'address', 'city',
								'officeTime', 'feeRules', 'rulesDesc', 'mapLb',
								'memo', 'status'])
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
							text : '新增',
							iconCls : 'addIcon',
							id : 'id_tbi_add',
							handler : function() {
								addCatalogItem();
							}
						}, {
							text : '修改',
							id : 'tbi_edit',
							iconCls : 'edit1Icon',
							disabled : true,
							handler : function() {
								updateCatalogItem();
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
				title : '<span class="commoncss">' + parkAliasName + '</span>',
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
				updateCatalogItem();
			});

	var myForm = new Ext.form.FormPanel({
		collapsible : false,
		border : true,
		labelWidth : 60, // 标签宽度
		// frame : true, //是否渲染表单面板背景色
		labelAlign : 'right', // 标签对齐方式
		bodyStyle : 'padding:5 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		height : 280,
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
											fieldLabel : parkAlias.parkId,
											name : 'partId',
											disabled : true,
											fieldClass : 'x-custom-field-disabled',
											xtype : 'textfield', // 设置为数字输入框类型
											anchor : '100%'
										}, {
											fieldLabel : parkAlias.parkName,
											name : 'parkName',
											allowBlank : false,
											maxLength : 100,
											anchor : '100%'
										}, new Ext.form.ComboBox({
													hiddenName : 'city',
													fieldLabel : parkAlias.city,
													emptyText : '请选择',
													triggerAction : 'all',
													store : CITYCODEStore,
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
												}), new Ext.form.ComboBox({
													hiddenName : 'status',
													fieldLabel : parkAlias.status,
													emptyText : '请选择',
													triggerAction : 'all',
													store : QYBZStore,
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
											fieldLabel : parkAlias.address,
											name : 'address',
											maxLength : 100,
											allowBlank : false,
											anchor : '100%'
										}, {
											maxLength : 100,
											allowBlank : false,
											anchor : '100%',
											fieldLabel : parkAlias.mapLb,
											name : 'mapLb'
										}, {
											fieldLabel : parkAlias.officeTime,
											name : 'officeTime',
											maxLength : 100,
											allowBlank : false,
											anchor : '100%'
										}]
							}]
				}, {
					fieldLabel : parkAlias.rulesDesc,
					name : 'rulesDesc',
					xtype : 'textarea',
					maxLength : 100,
					anchor : '99%'
				}, {
					fieldLabel : parkAlias.feeRules,
					name : 'feeRules',
					xtype : 'textarea',
					maxLength : 100,
					anchor : '99%'
				}, {
					fieldLabel : parkAlias.memo,
					name : 'memo',
					xtype : 'textarea',
					maxLength : 100,
					anchor : '99%'
				}]
	});

	var firstWindow = new Ext.Window({
				title : '<span class="commoncss">录入' + parkAliasName + '<span>', // 窗口标题
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
				items : [myForm], // 嵌入的表单面板
				buttons : [{
							text : '保存',
							iconCls : 'acceptIcon',
							handler : function() {
								submitTheForm();
							}
						}, {
							text : '重置',
							iconCls : 'tbar_synchronizeIcon',
							handler : function() {
								myForm.getForm().reset();
							}
						}, {
							text : '关闭',
							iconCls : 'deleteIcon',
							handler : function() {
								firstWindow.hide();
							}
						}]
			});

	var updateForm = new Ext.form.FormPanel({
		collapsible : false,
		border : true,
		labelWidth : 60, // 标签宽度
		// frame : true, //是否渲染表单面板背景色
		labelAlign : 'right', // 标签对齐方式
		bodyStyle : 'padding:5 5 0', // 表单元素和表单面板的边距
		buttonAlign : 'center',
		height : 280,
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
											fieldLabel : parkAlias.parkId,
											name : 'parkId',
											readOnly : true,
											fieldClass : 'x-custom-field-disabled',
											xtype : 'textfield', // 设置为数字输入框类型
											anchor : '100%'
										}, {
											fieldLabel : parkAlias.parkName,
											name : 'parkName',
											allowBlank : false,
											maxLength : 100,
											anchor : '100%'
										}, new Ext.form.ComboBox({
													hiddenName : 'city',
													fieldLabel : parkAlias.city,
													emptyText : '请选择',
													triggerAction : 'all',
													store : CITYCODEStore,
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
												}), new Ext.form.ComboBox({
													hiddenName : 'status',
													fieldLabel : parkAlias.status,
													emptyText : '请选择',
													triggerAction : 'all',
													store : QYBZStore,
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
											fieldLabel : parkAlias.address,
											name : 'address',
											maxLength : 100,
											allowBlank : false,
											anchor : '100%'
										}, {
											maxLength : 100,
											allowBlank : false,
											anchor : '100%',
											fieldLabel : parkAlias.mapLb,
											name : 'mapLb'
										}, {
											fieldLabel : parkAlias.officeTime,
											name : 'officeTime',
											maxLength : 100,
											allowBlank : false,
											anchor : '100%'
										}]
							}]
				}, {
					fieldLabel : parkAlias.rulesDesc,
					name : 'rulesDesc',
					xtype : 'textarea',
					maxLength : 100,
					anchor : '99%'
				}, {
					fieldLabel : parkAlias.feeRules,
					name : 'feeRules',
					xtype : 'textarea',
					maxLength : 100,
					anchor : '99%'
				}, {
					fieldLabel : parkAlias.memo,
					name : 'memo',
					xtype : 'textarea',
					maxLength : 100,
					anchor : '99%'
				}]
	});
	var updateWindow = new Ext.Window({
				title : '<span class="commoncss">修改' + parkAliasName + '<span>', // 窗口标题
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
				items : [updateForm], // 嵌入的表单面板
				buttons : [{
							text : '保存',
							iconCls : 'acceptIcon',
							handler : function() {
								updateTheForm();
							}
						}, {
							text : '关闭',
							iconCls : 'deleteIcon',
							handler : function() {
								updateWindow.hide();
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
	 * 表单提交(以Batch方式批量执行SQL语句)
	 */
	function submitTheFormBasedBatch() {
		if (!myForm.getForm().isValid())
			return;
		myForm.form.submit({
					url : parkAction.batchSql,
					waitTitle : '提示',
					method : 'POST',
					waitMsg : '正在处理数据,请稍候...',
					success : function(form, action) {
						Ext.MessageBox.alert('提示', action.result.msg);
					},
					failure : function(form, action) {
						Ext.Msg
								.alert('提示', '数据查询失败,错误类型:'
												+ action.failureType);
					}
				});
	}

	/**
	 * 新增项目
	 */
	function addCatalogItem() {
		firstWindow.show(); // 显示窗口
	}

	/**
	 * 表单提交(表单自带Ajax提交)
	 */
	function submitTheForm() {
		if (!myForm.getForm().isValid())
			return;
		myForm.form.submit({
					url : parkAction.save,
					waitTitle : '提示',
					method : 'POST',
					waitMsg : '正在处理数据,请稍候...',
					success : function(form, action) { // 回调函数有2个参数
						Ext.Msg.confirm('请确认', '新增成功,您要继续新增吗?', function(btn,
										text) {
									if (btn == 'yes') {
										myForm.getForm().reset();
									} else {
										firstWindow.hide();
										store.reload();
									}
								});
					},
					failure : function(form, action) {
						Ext.MessageBox.alert('提示', '数据保存失败');
					}
				});
	}

	/**
	 * 修改项目
	 */
	function updateCatalogItem() {
		var record = grid.getSelectionModel().getSelected();
		if (Ext.isEmpty(record)) {
			Ext.Msg.alert('提示:', '请先选中数据');
			return;
		}
		updateForm.getForm().loadRecord(record);
		updateWindow.show(); // 显示窗口
	}

	/**
	 * 表单提交(表单自带Ajax提交)
	 */
	function updateTheForm() {
		if (!updateForm.getForm().isValid())
			return;
		updateForm.form.submit({
					url : parkAction.update,
					waitTitle : '提示',
					method : 'POST',
					waitMsg : '正在处理数据,请稍候...',
					success : function(form, action) { // 回调函数有2个参数
						Ext.MessageBox.alert('提示', action.result.msg);
						updateWindow.hide();
						store.reload();
					},
					failure : function(form, action) {
						Ext.Msg
								.alert('提示', '数据保存失败,错误类型:'
												+ action.failureType);
					}
				});
	}

	/**
	 * 删除项目
	 */
	function deleteCatalogItem() {
		var record = grid.getSelectionModel().getSelected();
		if (Ext.isEmpty(record)) {
			Ext.Msg.alert('提示:', '请先选中数据');
			return;
		}
		Ext.MessageBox.confirm('请确认', '确认删除吗?', function(btn, text) {
					if (btn == 'yes') {
						showWaitMsg();
						Ext.Ajax.request({
									url : parkAction.del,
									success : function(response) { // 回调函数有1个参数
										var resultArray = Ext.util.JSON
												.decode(response.responseText);
										Ext.Msg.alert('提示', resultArray.msg);
										store.reload();
									},
									failure : function(response) {
										Ext.MessageBox.alert('提示', '数据删除失败');
									},
									params : {
										parkId : record.data.parkId
									}
								});
					}
				});
	}

});