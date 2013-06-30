Ext.define('app.view.file.List' ,{
    extend: 'Ext.grid.Panel',
    alias : 'widget.filelist',

    title : 'list ...',
    store: 'Lines',

    columns: [
        {header: 'A',  dataIndex: 'col1',  flex: 1},
        {header: 'B', dataIndex: 'col2', flex: 1},
        {header: 'C', dataIndex: 'col2', flex: 1}
    ]
});
