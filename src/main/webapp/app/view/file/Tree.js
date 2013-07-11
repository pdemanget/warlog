Ext.define('app.view.file.Tree', {
	extend: 'Ext.tree.Panel',
    alias : 'widget.filetree',
    title : 'navigation tree',
    store: 'FileTree',
	rootVisible: false,
	columns: [
        {xtype:'treecolumn', header: 'Name',  dataIndex: 'name',  width:200}     
        ,{header: 'Length', dataIndex: 'length', flex: 1}
      ]
	
})

/*var store = Ext.create('Ext.data.TreeStore', {
    root: {
        expanded: true,
        children: [
            { text: "detention", leaf: true },
            { text: "homework", expanded: true, children: [
                { text: "book report", leaf: true },
                { text: "alegrbra", leaf: true}
            ] },
            { text: "buy lottery tickets", leaf: true }
        ]
    }
});

Ext.create('Ext.tree.Panel', {
    title: 'Simple Tree',
    width: 200,
    height: 150,
    store: store,
    rootVisible: false,
    renderTo: Ext.getBody()
});*/