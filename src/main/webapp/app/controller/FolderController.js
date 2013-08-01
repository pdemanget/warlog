/**
 * Controller that manage links.
 */
Ext.define('app.controller.FolderController', {
    extend: 'Ext.app.Controller',

    stores: ['FileTree'],

    models: ['File'],

    views: ['file.List', 'file.Tree'],

    requires: ['app.util.Ajax'],

    init: function() {
        this.control({
        	
        });
    },
    

    lookupNode: function(){
        
    },
    
	/**
	 * called after hash load-reload
	 */
	open: function(path){
		console.log("load folder path "+ path);
		var store=this.getStore("FileTree");
		var node = store.getRootNode();
		//basic parse should be tested
		var folders = path.split('/');
		var relativePath="";
		for(var i in folders){
		    if(! node.isExpanded()){ node.expand(
		            function(){this.open(path);},this);
		    }else{
    		    relativePath+="/"+folders[i];
    		    //node.findChild("path",relativePath);
    		    node = node.findChildBy(
    		            function(anode){
    		                console.log(anode.data.path+" "+relativePath);
    		                return anode.data.name.replace('/','')===folders[i];
    		    });
		    }
		    
		}
		node.set('checked', true); 
	}

});
