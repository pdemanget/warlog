/** a link */
Ext.define('app.model.Link', {
    extend: 'Ext.data.Model',
    requires: ['Ext.data.SequentialIdGenerator'],
    idgen: 'sequential',
    fields: ['url','id'],
//    idProperty: 'url'
});