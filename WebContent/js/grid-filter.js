/*!
 * Ext JS Library 3.2.1
 * Copyright(c) 2006-2010 Ext JS, Inc.
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
Ext.onReady(function(){

    Ext.QuickTips.init();
    
    store = new Ext.data.JsonStore({
        // store configs
        autoDestroy: true,
        url: 'company/view.action',
        remoteSort: false,
        sortInfo: {
            field: 'company',
            direction: 'ASC'
        },
        storeId: 'myStore',
        
        // reader configs
        idProperty: 'id',
        root: 'data',
        totalProperty: 'total',
        fields: [{
            name: 'id'
        }, {
            name: 'company'
        }, {
            name: 'price',
            type: 'float'
        }, {
            name: 'date',
            type: 'date',
            dateFormat: 'm-d-Y'
        }, {
            name: 'visible',
            type: 'boolean'
        }, {
            name: 'size'
        }]
    });

    var filters = new Ext.ux.grid.GridFilters({
        // encode and local configuration options defined previously for easier reuse
        encode: true, // json encode the filter query
        local: false,   // defaults to false (remote filtering)
        filters: [{
            type: 'numeric',
            dataIndex: 'id'
        }, {
            type: 'string',
            dataIndex: 'company'
        }, {
            type: 'numeric',
            dataIndex: 'price'
        }, {
            type: 'date',
            dataIndex: 'date'
        }, {
            type: 'list',
            dataIndex: 'size',
            options: ['small', 'medium', 'large', 'extra large']
        }, {
            type: 'boolean',
            dataIndex: 'visible'
        }]
    });    
    
    var grid = new Ext.grid.GridPanel({
        border: false,
        store: store,
        columns: [{
            dataIndex: 'id',
            header: 'Id'
        }, {
            dataIndex: 'company',
            header: 'Company',
            id: 'company'
        }, {
            dataIndex: 'price',
            header: 'Price'
        }, {
            dataIndex: 'size',
            header: 'Size'
        }, {
            dataIndex: 'date',
            header: 'Date',
            renderer: Ext.util.Format.dateRenderer('m/d/Y')
        }, {
            dataIndex: 'visible',
            header: 'Visible'
        }],
        loadMask: true,
        plugins: [filters],
        autoExpandColumn: 'company',
        listeners: {
            render: {
                fn: function(){
                    store.load({
                        params: {
                            start: 0,
                            limit: 10
                        }
                    });
                }
            }
        },
        bbar: new Ext.PagingToolbar({
            store: store,
            pageSize: 10,
            displayInfo: true,
            displayMsg: 'Displaying {0} - {1} of {2}',
            plugins: [filters],
            items: [ // add some buttons to bottom toolbar just for demonstration purposes
                    '->',
                    {
                        text: 'Clear Filter Data',
                        handler: function () {
                            grid.filters.clearFilters();
                        } 
                    }    
                ]
        })
    });

    var win = new Ext.Window({
        title: 'ExtJS Grid Filters Example - Spring + Hibernate',
        height: 300,
        width: 700,
        layout: 'fit',
        items: grid
    }).show();
    
});