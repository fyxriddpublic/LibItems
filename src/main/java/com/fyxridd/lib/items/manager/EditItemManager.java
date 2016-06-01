package com.fyxridd.lib.items.manager;

import com.fyxridd.lib.core.api.*;
import com.fyxridd.lib.core.api.config.ConfigApi;
import com.fyxridd.lib.core.api.config.Setter;
import com.fyxridd.lib.core.api.fancymessage.FancyMessage;
import com.fyxridd.lib.func.api.FuncApi;
import com.fyxridd.lib.items.ItemsPlugin;
import com.fyxridd.lib.items.api.ItemsApi;
import com.fyxridd.lib.items.config.EditConfig;
import com.fyxridd.lib.items.config.LangConfig;
import com.fyxridd.lib.items.func.EditItemCmd;
import com.fyxridd.lib.items.model.ItemInfo;
import com.fyxridd.lib.items.model.ItemWrapper;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 编辑物品管理器
 */
public class EditItemManager {
    //物品编辑框大小
    private static final int SLOT = 36;

    private String savePath;
    private LangConfig langConfig;
    private EditConfig editConfig;
    
    //缓存
    //玩家名 物品编辑框
	private static Map<String, Inventory> invHash = new HashMap<>();

	public EditItemManager() {
        savePath = ItemsPlugin.instance.dataPath+File.separator+"saveItems";
	    //添加配置监听
	    ConfigApi.addListener(ItemsPlugin.instance.pn, LangConfig.class, new Setter<LangConfig>() {
            @Override
            public void set(LangConfig value) {
                langConfig = value;
            }
        });
        ConfigApi.addListener(ItemsPlugin.instance.pn, EditConfig.class, new Setter<EditConfig>() {
            @Override
            public void set(EditConfig value) {
                editConfig = value;
            }
        });
        //注册功能
        FuncApi.register(ItemsPlugin.instance.pn, new EditItemCmd());
	}
	
    /**
     * @see com.fyxridd.lib.items.api.ItemsApi#getInv(String, boolean)
	 */
	public Inventory getInv(String name, boolean create) {
        if (name == null) return null;
        //目标玩家存在性检测
        name = PlayerApi.getRealName(null, name);
        if (name == null) return null;
        //
        if (!invHash.containsKey(name) && create) invHash.put(name, Bukkit.createInventory(null, SLOT, get(name, 40, name).getText()));
		return invHash.get(name);
	}

	/**
	 * 玩家请求打开物品编辑框
	 * @param p 玩家,不为null
	 * @param reset 是否重置物品编辑框
	 */
	public void open(Player p, boolean reset) {
        //检测权限
        if (!PerApi.checkHasPer(p.getName(), editConfig.getEditPer())) return;
		//检测新建物品编辑框
		String name = p.getName();
		if (!invHash.containsKey(name) || reset) invHash.put(name, Bukkit.createInventory(null, SLOT, get(p.getName(), 40, name).getText()));
		//打开
		Inventory inv = invHash.get(name);
		p.openInventory(inv);
	}

	/**
	 * 玩家请求保存类型
	 * @param p 玩家,不为null
	 * @param type 类型名,不为null
	 * @param force true表示强制保存(如果原来有文件则覆盖)
	 */
	public void save(Player p, String type, boolean force) {
        //检测权限
        if (!PerApi.checkHasPer(p.getName(), editConfig.getEditPer())) return;
		//物品编辑框里没有物品
		String name = p.getName();
		if (!invHash.containsKey(name) || ItemApi.getEmptySlots(invHash.get(name)) == invHash.get(name).getSize()) {
		    MessageApi.send(p, get(p.getName(), 50), true);
			return;
		}
		//类型已经存在
		String saveFilePath = savePath+File.separator+type+".yml";
		if (!force && new File(saveFilePath).exists()) {
            MessageApi.send(p, get(p.getName(), 55, type), true);
			return;
		}
		//保存
        File file = new File(saveFilePath);
        file.getParentFile().mkdirs();
		YamlConfiguration saveConfig = new YamlConfiguration();
		Inventory inv = invHash.get(name);
		for (int i=0;i<inv.getSize();i++) {
            ItemStack is = inv.getItem(i);
			if (is != null &&
                    !is.getType().equals(Material.AIR) &&
                    is.getAmount() > 0) {//保存物品信息
                ItemsApi.saveItemStack(saveConfig, "" + i, is);
			}
		}
        if (UtilApi.saveConfigByUTF8(saveConfig, file)) MessageApi.send(p, get(p.getName(), 60), true);
        else MessageApi.send(p, get(p.getName(), 65), true);
	}

    public void getItems(Player p, String plugin, String getType) {
        //检测权限
        if (!PerApi.checkHasPer(p.getName(), editConfig.getEditPer())) return;

        //获取物品列表
        List<ItemStack> list = ItemsApi.getItems(plugin, getType);

        //添加物品
        p.getInventory().addItem(list.toArray(new ItemStack[list.size()]));

        //延时更新背包
        PlayerApi.updateInventoryDelay(p);

        //提示
        MessageApi.send(p, get(p.getName(), 120), true);
    }

    public void getDynamicItems(Player p, String plugin, String type, String arg) {
        //检测权限
        if (!PerApi.checkHasPer(p.getName(), editConfig.getEditPer())) return;

        //获取物品列表
        List<ItemStack> list = ItemsApi.getItems(plugin, type, arg);

        //添加物品
        p.getInventory().addItem(list.toArray(new ItemStack[list.size()]));

        //延时更新背包
        PlayerApi.updateInventoryDelay(p);

        //提示
        MessageApi.send(p, get(p.getName(), 120), true);
    }

    public void getStaticItems(Player p, String plugin, String file, String type) {
        //检测权限
        if (!PerApi.checkHasPer(p.getName(), editConfig.getEditPer())) return;

        //获取物品列表
        List<ItemStack> list = new ArrayList<>();
        ItemInfo itemInfo = ItemsPlugin.instance.getItemsManager().getItemInfo(plugin, file, type);
        if (itemInfo != null) {
            for (ItemWrapper iw:itemInfo.getItemList()) {
                ItemStack is = iw.getItem();
                if (is != null) list.add(is);
            }
        }

        //添加物品
        p.getInventory().addItem(list.toArray(new ItemStack[list.size()]));

        //延时更新背包
        PlayerApi.updateInventoryDelay(p);

        //提示
        MessageApi.send(p, get(p.getName(), 120), true);
    }

    private FancyMessage get(String player, int id, Object... args) {
        return langConfig.getLang().get(player, id, args);
    }
}
