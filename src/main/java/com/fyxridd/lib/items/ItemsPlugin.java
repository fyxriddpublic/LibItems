package com.fyxridd.lib.items;

import com.fyxridd.lib.core.api.SqlApi;
import com.fyxridd.lib.core.api.config.ConfigApi;
import com.fyxridd.lib.core.api.plugin.SimplePlugin;
import com.fyxridd.lib.items.config.EditConfig;
import com.fyxridd.lib.items.config.LangConfig;
import com.fyxridd.lib.items.manager.DynamicItemGetterManager;
import com.fyxridd.lib.items.manager.EditItemManager;
import com.fyxridd.lib.items.manager.ItemsManager;

import java.io.File;

public class ItemsPlugin extends SimplePlugin{
    public static ItemsPlugin instance;

    private ItemsManager itemsManager;
    private DynamicItemGetterManager dynamicItemGetterManager;
    private EditItemManager editItemManager;
    
    @Override
    public void onEnable() {
        instance = this;

        //注册配置
        ConfigApi.register(pn, LangConfig.class);
        ConfigApi.register(pn, EditConfig.class);
        
        itemsManager = new ItemsManager();
        dynamicItemGetterManager = new DynamicItemGetterManager();
        editItemManager = new EditItemManager();
        
        super.onEnable();
    }

    public ItemsManager getItemsManager() {
        return itemsManager;
    }

    public DynamicItemGetterManager getDynamicItemGetterManager() {
        return dynamicItemGetterManager;
    }

    public EditItemManager getEditItemManager() {
        return editItemManager;
    }
}