Ext.application({
    name: 'app',
    // automatically create an instance of AM.view.Viewport
//    autoCreateViewport: true,
    launch: function() {
    	Ext.create('app.view.EasyViewport');
    },
    controllers: [
        'FileController'
    ],
//	views: ['file.List','file.Tree']
});
