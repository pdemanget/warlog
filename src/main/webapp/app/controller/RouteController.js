/**
 * Trap routes in Hash (#) portion of URL:
 * 1. on startup if the url already contains a Hash.
 * 2. on hashChange if user  use back/next buttons.
 * 3. on internal link if we want to open a new window.
 * 
 */
Ext.define('app.controller.RouteController', {
	extend : 'Ext.app.Controller',
	views: [],
	screenmap:{},
	defaultWidget: 'welcome',
	containerId: 'maincontent',

	init : function() {

		var me=this;
		if ('onhashchange' in window) {
			//2. onhashchange use back/next
			window.onhashchange = function(){me.internalUseHash()};
		}
		this.control({
            'viewport': {
				//1. do the startup stuff.
				//afterrender( this, eOpts )
                afterrender: this.internalUseHash
            }
        });
		
		
	},

	open : function(alias){
		window.location.hash = '#' + alias;
	},
	
	

	internalUseHash: function(){
		var token=this.defaultWidget;
		if (window.location.hash.length > 1) {
			token = window.location.hash.substr(1);
		}
		var mainContainer = Ext.getCmp(this.containerId);
		this.internalRoute(token);
	},

	internalRoute: function (token){
//		if(! this.screenmap[token]){
//			this.screenmap[token] = Ext.widget(token);
//			mainContainer.add(this.screenmap[token]);
//		}
//		mainContainer.getLayout().setActiveItem(mainContainer.items.indexOf(this.screenmap[token]));
		if(token!='')
		this.getController('FileController').treeClick('',{data:{path:token}});
	
	}
	
	

	                       
});