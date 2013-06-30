Ext.define('app.store.Lines', {
    extend: 'Ext.data.Store',
    model: 'app.model.Line',
    autoLoad: true,
    
    proxy: {
        type: 'ajax',
        api: {
            read: 'data/lines.json',
            update: 'data/none.json'
        },
        reader: {
            type: 'json',
            root: 'data',
            successProperty: 'success'
        }
    }
});