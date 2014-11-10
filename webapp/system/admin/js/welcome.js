/**
 * 欢迎页面
 * 
 */
Ext.onReady(function() {
			new Ext.ux.TipWindow({
						title : '<span class=commoncss>提示</span>',
						html : '您有[0]条未读信息.',
						iconCls : 'commentsIcon'
					}).show(Ext.getBody());
		});

Ext.onReady(function() {

	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

	var tools = [{
				id : 'maximize',
				handler : function(e, target, panel) {
				}
			}];

	var my_height1 = document.body.clientHeight - 35;
	var my_height = document.body.clientHeight - 65;
	var my_sina = '<div style="margin:10px"></div>';
	var viewport = new Ext.Viewport({
		layout : 'border',
		items : [{
			xtype : 'portal',
			region : 'center',
			margins : '3 3 3 3',
			items : [{
						columnWidth : .6,
						style : 'padding:8px 0px 8px 8px',
						items : [{
									title : '信息栏',
									layout : 'fit',
									// tools : tools,
									height : my_height1,
									html : my_sina
								}]
					}, {
						columnWidth : .4,
						style : 'padding:8px 8px 8px 8px',
						items : [{
							title : '联系方式',
							// tools : tools,
							html : '<div style=height:60px;line-height:25px class=commoncss></div>'
						}, {
							title : '舍我其谁',
							// tools : tools,
							html : '<div style=height:155px;line-height:25px class=commoncss></div>'
						}]
					}]
		}]
	});
});
