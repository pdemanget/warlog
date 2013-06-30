Ext.define('app.controller.FileController', {
    extend: 'Ext.app.Controller',

    stores: ['Lines','FileTree'],

    models: ['File','Line'],

    views: ['file.List', 'file.Tree'],

//    constructor:function(){
//    	alert('PLEASE SAY SOMETHING');
//    },
    init: function() {
    	alert('PLEASE SAY SOMETHING');
        this.control({
            'viewport > filelist dataview': {
                itemdblclick: this.editUser
            },
            'fileedit button[action=save]': {
                click: this.updateUser
            }
        });
    },

    edit: function(grid, record) {
        var edit = Ext.widget('fileedit').show();

        edit.down('form').loadRecord(record);
    },

    update: function(button) {
        var win    = button.up('window'),
            form   = win.down('form'),
            record = form.getRecord(),
            values = form.getValues();

        record.set(values);
        win.close();
        this.getFilesStore().sync();
    }
});
