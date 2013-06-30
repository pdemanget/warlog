Ext.define('app.model.File', {
    extend: 'Ext.data.Model',
    fields: ['path', 'name', 'folder'],
    idProperty:'path'
});