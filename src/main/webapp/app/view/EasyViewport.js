Ext.define('app.view.EasyViewport', {
    extend: 'Ext.container.Viewport',
//    constructor:function(){
//    	alert('PLEASE SAY SOMETHING viewport');
//    },
layout:'border',
defaults: {
    collapsible: true,
    split: true,
    bodyStyle: 'padding:15px'
},
items: [
{
    title: 'Menu',
    region: 'north',
    height: 50,
    minSize: 25,
    maxSize: 250,
    cmargins: '5 0 0 0'
}
//]	
	,{
    title: 'Footer',
    region: 'south',
    height: 50,
    minSize: 25,
    maxSize: 250,
    cmargins: '5 0 0 0'
},{
    title: 'Navigation',
    region:'west',
    margins: '5 0 0 0',
    cmargins: '5 5 0 0',
    width: 250,
    minSize: 100,
    maxSize: 500
	,xtype: 'filetree'
},{
    title: 'Main Content',
    collapsible: false,
    region:'center',
    margins: '5 0 0 0',
	xtype: 'filelist'
}]	

});