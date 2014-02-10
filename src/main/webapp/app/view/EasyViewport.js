Ext.define('app.view.EasyViewport', {
    extend: 'Ext.container.Viewport',

layout:'border',
defaults: {
    collapsible: true,
    split: true,
    bodyStyle: 'padding:15px'
},
items: [
{
    title: 'Navigation',
    region:'west',
    margins: '5 0 0 0',
    cmargins: '5 5 0 0',
    width: 300,
    minSize: 100,
    maxSize: 500
	,xtype: 'filetree'
},{xtype: 'tabpanel',
	region:'center',
	title:'tabs',
    margins: '5 0 0 0',
    flex: 1,
    collapsible: false,
    id:'maincontent',
	items:[
//	       {
//	    title: 'Main Content',
//	    //collapsible: false,
//	    //layout: 'fit',
//	    flex: 1,
//		xtype: 'filelist',
//		
//	}
	       ]
}]
//endof define
});