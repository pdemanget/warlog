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
        	'filetree': {
                beforerender: this.activate
            }
        });
    },
    

    lookupNode: function(){
        
    },
    
	/**
	 * called after hash load-reload
	 */
	open: function(path){
		//alert("load folder path "+ path);
		var store=this.getStore("FileTree");
		var node = store.getRootNode();
		//basic parse should be tested
		var folders = path.split('/');
		var relativePath="";
		for(var i in folders){
		    if(! node.isExpanded()){
		    	//alert('expand '+node.data.name );
		    	node.expand(
		    		false,
		            function(){this.open(path);},
		            this);
		    }else{    		    
		    	relativePath+="/"+folders[i];
    		    //node.findChild("path",relativePath);
    		    node = node.findChildBy(
    		            function(anode){
    		            	//console.log(anode.data.path+" "+relativePath);
    		                console.log("anode.name:" + anode.data.name+" ,folder:"+folders[i]);
    		                var name=anode.data.name;
    		                if(name[name.length-1]=='/') name=name.substring(0,name.length-1);
    		                return name===folders[i];
    		                //return anode.data.name.replace('/','')===folders[i];
    		    });
		    }
		    
		}
		if(node)
			this.fileTree.getSelectionModel().select(node);
	},
	activate: function (widget){
		this.fileTree=widget;
	}

});
