/**
 * List of line of a file.
 */
Ext.define('app.view.file.Raw' ,{
    extend: 'Ext.panel.Panel',
    alias : 'widget.fileraw',
    layout: 'fit',
    items: [{
        xtype     : 'textareafield',
        grow      : true,
        name      : 'message',
        anchor    : '100%',
        flex : 1
    }],
    store: 'Lines',
    dockedItems: [{
	        xtype: 'pagingtoolbar',
	        dock: 'bottom',
	        store: 'Lines',
	        displayInfo: true
	    },{
            xtype: 'toolbar',
            dock: 'top',
            items: [
				{
					xtype: 'checkbox',
				    name: 'linenumbers',
				    fieldLabel: 'line numbers ',
				    checked: true,
				    margin:10,
				    labelWidth: 70
				},
                {
                	xtype: 'textfield',
                    name: 'pattern',
        	        fieldLabel: 'Filter ',
        	        labelWidth: 40,
        	        width: 300,
        	        margin: '20px 0 0 0'
                },
                '->',
                {
                    text: 'display as list',
                    itemId: '1'
                }
            ]
        }
        ],
//--
//--        
        
    columns: [
         {header: 'line',  dataIndex: 'id',  width:50}
        ,{header: 'A',  dataIndex: 'col1',  flex: 1, renderer: 'htmlEncode'}
//        ,{header: 'B', dataIndex: 'col2', flex: 1}
//        ,{header: 'C', dataIndex: 'col3', flex: 1}
    ],
    setRawValue: function(text){
    	this.down('textareafield').setRawValue(text);
    },
    getStore: function(){
    	return this.storeImpl;
    },
    initComponent:function(){
    	this.callParent();
    	var SAMESTORE=true;
    	if(SAMESTORE){
    		this.storeImpl=Ext.getStore(this.store);
    	}else{
    		this.storeImpl=Ext.create('app.store.Lines');
    		this.storeImpl.load();
    		
    	}
    	var area=this.down('textareafield[name=message]');
    	var me=this;
    	this.down('checkbox').on('change',function(){
    		me.storeImpl.reload();
    	});
    	this.storeImpl.on('load', function(st,models){
    		var ck =me.down('checkbox');
    		var linenumbers=ck&&ck.getValue();
    		console.log("linenumbers "+linenumbers);
			var txt='';
			var lab='';
			for(var i=0;i<models.length;i++){
				var model=models[i];
				if (linenumbers) txt+=model.getId()+': ';
				txt+=model.get('col1')+'\n';
			}
			area.setRawValue(txt);
		});
    }
});
