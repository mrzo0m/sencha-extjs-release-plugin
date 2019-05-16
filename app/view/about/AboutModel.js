Ext.define('App.view.about.AboutModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.about',

    stores: {
        /*
        A declaration of Ext.data.Store configurations that are first processed as binds to produce an effective
        store configuration. For example:

        users: {
            model: 'About',
            autoLoad: true
        }
        */
    },

    data	: {
        apkVersion: null
    }
});