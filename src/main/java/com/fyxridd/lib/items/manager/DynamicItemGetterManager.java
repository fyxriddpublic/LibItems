package com.fyxridd.lib.items.manager;

import org.bukkit.inventory.ItemStack;

import com.fyxridd.lib.items.api.DynamicItemGetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态物品获取管理器
 */
public class DynamicItemGetterManager {
    //插件名 类型名 获取器
    private Map<String, Map<String, DynamicItemGetter>> getters = new HashMap<>();

    /**
     * @see com.fyxridd.lib.items.api.ItemsApi#register(String, String, DynamicItemGetter)
     */
    public void register(String plugin, String type, DynamicItemGetter dynamicItemGetter) {
        if (plugin == null || type == null) return;

        Map<String, DynamicItemGetter> getHandlers = getters.get(plugin);
        if (getHandlers == null) {
            getHandlers = new HashMap<>();
            getters.put(plugin, getHandlers);
        }
        getHandlers.put(type, dynamicItemGetter);
    }

    /**
     * @see com.fyxridd.lib.items.api.ItemsApi#getItems(String, String, String)
     */
    public List<ItemStack> getItems(String plugin, String type, String arg) {
        Map<String, DynamicItemGetter> getHandlers = getters.get(plugin);
        if (getHandlers != null) {
            DynamicItemGetter getItemsHandler = getHandlers.get(type);
            if (getItemsHandler != null) return getItemsHandler.get(arg);
        }
        return new ArrayList<>();
    }
}
