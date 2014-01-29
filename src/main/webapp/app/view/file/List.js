/**
 * List of line of a file.
 */
Ext.define('app.view.file.List' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.filelist',
    viewConfig: {
        emptyText: 'No records'
    },
    title : 'list ...',
    store: 'Lines',
    dockedItems: [{
        xtype: 'pagingtoolbar',
        dock: 'bottom',
        store: 'Lines',
        displayInfo: true},
        {
            xtype: 'toolbar',
            dock: 'top',
            items: [
                '->',
                {
                    text: 'catalina',
                    itemId: '1'
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
