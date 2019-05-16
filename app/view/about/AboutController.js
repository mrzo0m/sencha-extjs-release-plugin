Ext.define('App.view.about.AboutController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.about',

    /**
     * Called when the view is created
     */
    init: function() {
        this.getViewModel().set('apkVersion', Ext.manifest.version);
    }
});