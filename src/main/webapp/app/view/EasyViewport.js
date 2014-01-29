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
    title: 'Filter',
    region: 'north',
    height: 75,
    minSize: 25,
    maxSize: 250,
    cmargins: '0 0 0 0',
    padding: 2,
    collapsed: true,	
    xtype: 'form',
	items : 
		[ {
			xtype : 'textfield',
	        name: 'pattern',
	        fieldLabel: 'Regex pattern to filter lines of the file',
	        padding: 2
		} ]
	},
// ,{
// title: 'Footer',
// region: 'south',
//    height: 50,
//    minSize: 25,
//    maxSize: 250,
//    cmargins: '5 0 0 0'
//},
//http://www.gnu.org/graphics/gplv3-88x31.png
{
    title: 'Navigation',
    region:'west',
    margins: '5 0 0 0',
    cmargins: '5 5 0 0',
    width: 300,
    minSize: 100,
    maxSize: 500
	,xtype: 'filetree'
},{
    title: 'Main Content',
    collapsible: false,
    region:'center',
    margins: '5 0 0 0',
    flex: 1,
	xtype: 'filelist',
	id:'maincontent'
}]	

});