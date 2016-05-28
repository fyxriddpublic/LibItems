package com.fyxridd.lib.items.api;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.fyxridd.lib.items.ItemsPlugin;

import java.util.List;

public class ItemsApi{
    /**
     * 将物品保存为字符串(包括保存Attributes)
     * @param is 物品,可为null(null时返回null)
     * @return 异常返回null
     */
    public static String saveItem(ItemStack is) {
        if (is == null) return null;

        try {
            YamlConfiguration config = new YamlConfiguration();
            saveItemStack(config, "item", is);
            return config.saveToString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从字符串中读取物品(包括读取Attributes)
     * @param data 字符串,可为null(null时返回null)
     * @return 异常返回null
     */
    public static ItemStack loadItem(String data) {
        if (data == null) return null;

        try {
            YamlConfiguration config = new YamlConfiguration();
            config.loadFromString(data);
            return loadItemStack((ConfigurationSection) config.get("item"));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 保存物品信息(包括保存Attributes)
     * @param cs 物品信息将被保存在这里,可为null(null时无效果)
     * @param type 物品类型名,可为null(null时无效果)
     * @param is 物品,可为null(null时无效果)
     */
    public static void saveItemStack(ConfigurationSection cs, String type, ItemStack is) {
        if (cs == null || type == null || is == null) return;

        if (!is.getType().equals(Material.AIR) && is.getAmount() > 0) cs.createSection(type, is.serialize());
    }

    /**
     * 获取物品(包括读取Attributes)
     * @param cs 可为null(null时返回null)
     * @return 物品信息,异常返回null
     */
    public static ItemStack loadItemStack(ConfigurationSection cs) {
        if (cs == null) return null;

        try {
            return ItemStack.deserialize(cs.getValues(true));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * (动态)
     * 注册物品获取器
     * @param plugin 插件(null时无效果)
     * @param type (物品获取器)类型(null时无效果)
     * @param dynamicItemGetter 物品获取器
     */
    public static void register(String plugin, String type, DynamicItemGetter dynamicItemGetter) {
        ItemsPlugin.instance.getDynamicItemGetterManager().register(plugin, type, dynamicItemGetter);
    }

    /**
     * (动态)
     * 获取物品
     * @param plugin 插件(null时返回空列表)
     * @param type (物品获取器)类型(null时返回空列表)
     * @param arg 变量,可为null
     * @return 物品列表,不为null
     */
    public static List<ItemStack> getItems(String plugin, String type, String arg) {
        return ItemsPlugin.instance.getDynamicItemGetterManager().getItems(plugin, type, arg);
    }

    /**
     * 获取检测成功的物品列表
     * @param plugin 插件名,可为null(null时返回空列表)
     * @param type 获取类型,可为null(null时返回空列表)
     * @return 检测成功的物品列表,出错返回空列表
     */
    public static List<ItemStack> getItems(String plugin, String type) {
        return ItemsPlugin.instance.getItemsManager().getItems(plugin, type);
    }

    /**
     * 重新读取指定插件的物品配置,包括:<br>
     *   - 物品类型: 保存在plugins/<b>plugin</b>/items文件夹下,文件名xxx.yml的都是<br>
     *   - 获取类型: 保存在传进来的<b>ms</b>中
     * @param plugin 插件名,可为null(null时无效果)
     * @param cs 配置,可为null(null时无效果)
     */
    public static void reloadItems(String plugin, ConfigurationSection cs) {
        ItemsPlugin.instance.getItemsManager().reloadItems(plugin, cs);
    }

    /**
     * 获取玩家的物品编辑框
     * @param name 玩家名,可为null(null时返回null)
     * @param create 如果没有是否创建
     * @return 玩家的物品编辑框,没有则返回null
     */
    public static Inventory getInv(String name, boolean create) {
        return ItemsPlugin.instance.getEditItemManager().getInv(name, create);
    }
}
