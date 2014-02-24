Ext.define('app.store.BufferedLines', {
    extend: 'Ext.data.Store',
    model: 'app.model.Line',
    autoLoad: false,
    pageSize: 100,
    buffered: true,
    
    proxy: {
        type: 'appajax',
        api: {
        	read: 'file'
            //read: 'data/lines.json'
            //,update: 'data/none.json'
        },
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        }
    }
});