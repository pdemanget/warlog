var SERVER_PUSH_ENABLED=true;

/**
 * Controller that manage links.
 */
Ext.define('app.controller.FileController', {
    extend: 'Ext.app.Controller',

    stores: ['Lines','FileTree', 'Links'],

    models: ['File','Line'],

    views: ['file.List', 'file.Tree'],
    
    requires: ['app.util.Ajax'],

    init: function() {
    	var me=this;
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
            'filelist textfield': {
            	specialkey: this.specialkey
            },
            'tab': {
            	focus: this.tabfocus,
            	close: this.tabclose,
            	activate: function(){
            		console.log('activate');
            	}
            },
            'tabpanel':{
            	afterrender: this.loadTabs
            },
            'filelist':{
            	 afterrender: this.loadTab
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
		if(!path) return;
		//TODO remove id
		var tabpanel = Ext.getCmp('maincontent');
		this.tabs=this.tabs||[];
		if(!this.tabs[path]){
			this.tabs[path]=tabpanel.add({
			    title: path,
			    path: path,
			    layout: 'fit',
				xtype: 'filelist',
				closable: true
			});
			var link=app.model.Link.create({
				url: path
			});
			this.tabs[path].link = link;
			this.getStore('Links').add(link);
			this.getStore('Links').sync();
		}
		tabpanel.setActiveTab(this.tabs[path]);
		
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
        	console.log("stop wait");
            Ext.Ajax.abort( this.request);
        }
        //start new one
        this.waitEventIntern(eventName);
    },
    
    waitEventIntern: function waitEventItern(eventName){
    	if(!SERVER_PUSH_ENABLED) return;
    	console.log('waitEventIntern');
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
            callback: function(response, opts){
            	//don't relaunch on abort!
            	if(opts){
            		console.log('wait callback '+response.status);
            		this.waitEventIntern(eventName);
            	}
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
    },
    
    tabfocus: function(tab){
    	console.log("tabfocus");
    	this.treeClick(null,{data:{path:tab.text}});
    	//this.open(tab.text);
    },
    tabclose: function(tab){
    	console.log("tabclose");
    	if(this.tabs[tab.text]){
    		this.getStore('Links').remove(this.tabs[tab.text].link);
    		this.tabs[tab.text]=null;
    	}
    	this.getStore('Links').sync();
    	this.fireReload=true;
    },
    
    loadTabs: function(){
    	var me=this;
    	var linkStore = this.getStore('Links');
    	linkStore.load();
    	var l = linkStore.getRange();
    	linkStore.getProxy().clear();
		
		linkStore.sync();
		for(var i=0;i<l.length;i++){
			me.open(l[i].get('url'));	
		}
		
		console.log("localstorage "+l.length);
    },
    loadTab: function(filelist){
    	var me=this;
    	if (this.fireReload){
    		fireReload=false;
    		
    	}
    }
});
