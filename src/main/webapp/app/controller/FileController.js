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
        this.control({
        	'filetree dataview': {
                itemdblclick: this.treeClick
            },
        	
            'viewport > filelist dataview': {
                itemdblclick: this.edit
            },
            'fileedit button[action=save]': {
                click: this.updateUser
            },
            'filelist toolbar button': {
            	click: this.tbbutton
            },
            'form textfield': {
            	specialkey: this.specialkey
            }
        });
    },
    
    treeClick: function(e, f) {
		var path = f.data.path;
		var route= this.getController('RouteController');
		route.open(f.data.path+"?"+(f.data.folder?"Folder":"File"));
	},

	/**
	 * inderictly called from treeclick.
	 */
	open: function(path){
		console.log("load path "+ path);
		var store=this.getStore("Lines");
		this.params={path:path};
		store.getProxy().extraParams=this.params ;
		store.on('exception',function( store, records, options ){
			console.log('EXCEPTION !!!');
		});
		store.loadPage(1);
		this.waitEvent(path);
		//Synchronize with package view
		this.getController('FolderController').open(path);
		
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
    },
    
    waitEvent: function waitEvent(eventName){
        //stop last one
        if (this.request){
            Ext.Ajax.abort( this.request);
        }
        //start new one
        this.waitEventIntern(eventName);
    },
    
    waitEventIntern: function waitEventItern(eventName){
        

        this.request=Ext.Ajax.request({
            url: 'push',
            timeout : 600*1000,
            method: "GET",
            params: {
                event: eventName
            },
            scope: this,
            success: function(response){
                var text = response.responseText;
                Ext.log("LOAD "+eventName);
                var store=this.getStore("Lines");
                store.load();
            },
            callback: function(){
                this.waitEventIntern(eventName);
            }
        });
    },
    
    tbbutton: function(){
    	this.getController('FolderController').
    		open('D:/batsh/apache-tomcat-7.0.41/log');
    },
    
    specialkey: function(field, e){
        // e.HOME, e.END, e.PAGE_UP, e.PAGE_DOWN,
        // e.TAB, e.ESC, arrow keys: e.LEFT, e.RIGHT, e.UP, e.DOWN
        if (e.getKey() == e.ENTER) {
            //var form = field.up('form').getForm();
            //form.submit();
        	var pattern=field.getValue();
        	var store=this.getStore("Lines");
        	this.params.pattern=pattern;
        	store.getProxy().extraParams=this.params ;
    		store.loadPage(1);
        }
    }
});
