Ext.define('app.store.Links', {
    extend: 'Ext.data.Store',
    model: 'app.model.Link',
    autoLoad: false,
       
    proxy: {
        type: 'localstorage',
        id: 'warlog1'
    }
});