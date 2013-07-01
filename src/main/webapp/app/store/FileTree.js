Ext.define('app.store.FileTree', {
    extend: 'Ext.data.TreeStore',
    model: 'app.model.File',
    autoLoad: true,
    proxy: {
        type: 'ajax',
        //url: 'data/files.json',
        url: 'folder',
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        }
    }
	/* root: {
        expanded: true,
        children: [
            { name: "detention", leaf: true },
            { name: "homework", expanded: true, children: [
                { name: "book report", leaf: true },
                { name: "alegrbra", leaf: true}
            ] },
            { name: "buy lottery tickets", leaf: true }
        ]
    }*/

});

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
*/