Ext.define('app.model.File', {
    extend: 'Ext.data.Model',
    fields: ['path', 'name', 'length', 'folder'],
    idProperty:'path'
});