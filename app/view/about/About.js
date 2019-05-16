Ext.define('App.view.about.About', {
    extend: 'Ext.Panel',

    requires: [
        'App.view.about.AboutModel',
		'App.view.about.AboutController'
    ],


    xtype: 'about',

    viewModel: {
        type: 'about'
    },

    controller: 'about',


    config: {
        route: null
    },

    minHeight: 568,
    minWidth: 320,

    eventedConfig: {
        /**
         * Make the config trigger an event on change to allow the controller to monitor it.
         * https://www.sencha.com/blog/using-sencha-ext-config/
         */
        route: null
    },

    scrollable: 'y',
    autoSize: true,

    defaults: {
        autoSize: true
    },
    items: [
        {
            xtype: 'container',
            flex: 1,
            minHeight: 15,
            bind: {
                tpl: [
                    '<div class="about-block"><p>Application version: <span class="about-val">{apkVersion}</span></p></div>'
                ]
            }

        }
    ]
});