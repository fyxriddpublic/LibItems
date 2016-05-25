package com.fyxridd.lib.items.api;

import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * 物品获取器
 */
public interface DynamicItemGetter {
    /**
     * 获取物品时调用
     * @param arg 变量,可为null
     * @return 不为null
     */
    public List<ItemStack> get(String arg);
}
