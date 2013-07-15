/**
 * Controller that manage links.
 */
Ext.define('app.controller.FileController', {
    extend: 'Ext.app.Controller',

    stores: ['Lines','FileTree'],

    models: ['File','Line'],

    views: ['file.List', 'file.Tree'],
    
    requires: ['app.util.Ajax'],

    init: function() {
    	//alert('PLEASE SAY SOMETHING');
        this.control({
        	'filetree dataview': {
                itemdblclick: this.treeClick
            },
        	
            'viewport > filelist dataview': {
                itemdblclick: this.edit
            },
            'fileedit button[action=save]': {
                click: this.updateUser
            }
        });
    },
    
    treeClick: function(e, f) {
		var path = f.data.path;
		console.log("load path "+ path);
//		this.getStore("Lines").load({params:{path:path}});
		var store=this.getStore("Lines");
		store.getProxy().extraParams= {path:path};
		store.on('exception',function( store, records, options ){
			console.log('EXCEPTION !!!');
		});
		store.loadPage(1);

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
