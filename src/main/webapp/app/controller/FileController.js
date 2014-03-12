var SERVER_PUSH_ENABLED=true;

/**
 * Controller that manage links.
 * 
 * Hierarchy call:
 * RouteController hash : #path?controller -> controller.open(path)
 * open(path) ->
 *  lazyCreateTabs
 *  store links : sync
 *  store ines: setproxy to new path
 *  storelines load.
 *  FolderController.open path -> file highlight in tree
 */
Ext.define('app.controller.FileController', {
    extend: 'Ext.app.Controller',

    stores: ['BufferedLines', 'Lines','FileTree', 'Links'],

    models: ['File','Line'],

    views: ['file.List', 'file.Tree', 'file.Raw'],
    
    requires: ['app.util.Ajax'],
    
    raw: true, 

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
            'button[itemId=switch]': {
            	click: this.tbbutton
            },
            'filelist textfield': {
            	specialkey: this.specialkey
            },
            'fileraw textfield': {
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
            	activate: this.loadTab,
            	afterrender: this.loadTabs
            },
            'filelist':{
//            	 afterrender: this.loadTab
            }

        });
    },
    
    treeClick: function(e, f) {
		var path = f.data.path;
		var route= this.getController('RouteController');
		route.open(f.data.path+"?"+(f.data.folder?"Folder":"File"));
	},
	
	lazyCreate:function(map,key,factory_key){
		if(!map[key]) map[key]=factory_key(key);
		return map[key];
	},

	/**
	 * inderictly called from treeclick.
	 */
	open: function(path){
		console.log("load path "+ path);
		if(!path) return;
		//TODO remove id
		//var tabpanel = Ext.getCmp('maincontent');
		var tabpanel=this.tabpanel;
		this.tabs=this.tabs||[];
		var key='';//this.raw?"raw_":"list_";
		key += path;
		var me=this;
		var tab = this.lazyCreate(this.tabs,key, function(key){
			var newtab;
			if (me.raw){
				newtab=tabpanel.add({
				    title    : path,
				    path     : path,
				    closable : true,
			        xtype    : 'fileraw',
			        key      : key
				});
			}else{
				newtab=tabpanel.add({
				    title    : path,
				    path     : path,
				    layout   : 'fit',
					xtype    : 'filelist',
					closable : true,
					key      : key
				});
			}
			var link=app.model.Link.create({
				url: path
			});
			newtab.link = link;
			me.getStore('Links').add(link);
			me.getStore('Links').sync();
			return newtab;
		});
		tabpanel.setActiveTab(tab);
		
		var store=this.getStore("Lines");
		this.params={path:path};
		store.getProxy().extraParams=this.params ;
		store.on('exception',function( store, records, options ){
			console.log('EXCEPTION !!!');
		});
		var me = this;
		//store.loadPage(1);
		store.load();
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
            callback: function(a,opts){
            	if(opts) {
            		this.waitEventIntern(eventName);
            	}
            }
        });
    },
    
    /**
     * open as raw/list.
     * toggle option the close/reopen.
     */
    tbbutton: function(but, evt, opt){
    	this.raw=!this.raw;
    	var tab = but.up('panel');
    	var path=tab.path;
    	
    	this.tabclose(tab);//remove if bug

    	this.tabs[tab.key]=null;
    	
    	this.open(path);//remove if bug

    	tab.close();
    	
    	//this.treeClick(null,{data:{path:path}});
//    	this.getController('FolderController').
//    		open('D:/batsh/apache-tomcat-7.0.41/log');
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
    	if(this.tabs[tab.key]){
    		this.getStore('Links').remove(this.tabs[tab.key].link);
    		this.tabs[tab.key]=null;
    	}
    	this.getStore('Links').sync();
    	this.fireReload=true;
    },
    
    /**
     * init tabs from localtorage
     */
    loadTabs: function(tabpanel){
    	this.loadTab(tabpanel);
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
    loadTab: function(tabpanel){
    	console.log("tabpanel"+tabpanel);
    	this.tabpanel=tabpanel;
    }
});
