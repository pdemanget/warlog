/**
 * Controller that manage links.
 */
Ext.define('app.controller.FileController', {
    extend: 'Ext.app.Controller',

    stores: ['Lines','FileTree'],

    models: ['File','Line'],

    views: ['file.List', 'file.Tree'],

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
            }
        });
        
     // to capture ALL events use:
//        Ext.util.Observable.prototype.fireEvent = 
//            Ext.util.Observable.prototype.fireEvent.createInterceptor(function() {
//                console.log(this.name);
//                console.log(arguments);
//                return true;
//        });
         
        // to capture events for a particular component:
//        Ext.util.Observable.capture(
//            Ext.getCmp('my-comp'),
//            function(e) {
//                console.info(e);
//            }
//        );
        
		/*
		 * trappe toutes les erreurs ajax pour les afficher, ou délogger si 401
		 */
//		Ext.override(Ext.data.proxy.Server,
//		{
//			constructor : function(config) {
//				this.callOverridden([ config ]);
//				this.addListener(
//				"exception",
//				function(
//						proxy,
//						response,
//						operation) {
//					console.log("HTTP  "+ response.status);
//					if (response.status == 401) {
//						
//					}
//					// else if
//					// (response.responseText
//					// != null)
//					else {
//						if (response.status == 0) {
//							Ext.Msg.alert(
//											'HTTP 0',
//											"serverUnavailable");
//
//						} else if (response.status == 200) {
//							var error = response.statusText;
//							try {
//								var res = JSON.parse(response.responseText);
//								error = res.error;
//							} catch (e) {
//
//							}
//							if (typeof  error !== "undefined")
//								Ext.Msg	.alert('Error Server',	error);
//						} else if (response.status == -1) {
//							// transaction aborted: just ignore it.
//							
//						}else {
//							Ext.Msg.alert('Error ','HTTP'+ response.status+ "/"+ response.statusText);
//						}
//					}
//				});
//			}
//		});


        
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
//		this.getStore("Lines").load({params:{path:path}});
		var store=this.getStore("Lines");
		store.getProxy().extraParams= {path:path};
		store.on('exception',function( store, records, options ){
			console.log('EXCEPTION !!!');
		});
		store.loadPage(1);

//		store.load({
//			    page: 1,
//			    start: 0
//			},function(records, operation, success){
//			if(success){
//				var page=Math.floor(this.getTotalCount()/this.pageSize);
//				console.log("page.total"+page);
//				//store.loadPage(page+1);
//			}
//		});
	},

    edit: function(grid, record) {
    	//FFU not used yet
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
