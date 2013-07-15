Ext.define('app.store.Lines', {
    extend: 'Ext.data.Store',
    model: 'app.model.Line',
    autoLoad: false,
    
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