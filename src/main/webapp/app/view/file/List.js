/**
 * List of line of a file.
 * 
 * adding infinite scroll: 
    'Ext.grid.PagingScroller' + verticalScrollerType: 'paginggridscroller',
 */
Ext.define('app.view.file.List' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.filelist',
    viewConfig: {
        emptyText: 'No records'
    },
    title : 'list ...',
    bodyStyle: 'padding: 0px; margin: 0px',
    store: 'Lines',
    focusOnToFront: false,
    dockedItems: [{
	        xtype: 'pagingtoolbar',
	        dock: 'bottom',
	        store: 'Lines',
	        displayInfo: true
	    },{
            xtype: 'toolbar',
            dock: 'top',
            items: [
                {
                	xtype: 'textfield',
                    name: 'pattern',
        	        fieldLabel: 'Filter ',
        	
                },
                '->',
                {
                    text: 'display as raw text',
                    itemId: 'switch'
                }
            ]
        }
        ],
//--
//--        
        
    columns: [
         {header: 'line',  dataIndex: 'id',  width:50}
        ,{header: 'A',  dataIndex: 'col1',  flex: 1, renderer: 'htmlEncode'}
//        ,{header: 'B', dataIndex: 'col2', flex: 1}
//        ,{header: 'C', dataIndex: 'col3', flex: 1}
    ]
});
