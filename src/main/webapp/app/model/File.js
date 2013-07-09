Ext.define('app.model.File', {
    extend: 'Ext.data.Model',
    fields: ['id','path', 'name', 'length', 'folder']
    // , idProperty:'path'
});