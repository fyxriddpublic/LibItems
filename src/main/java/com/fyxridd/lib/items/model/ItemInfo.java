package com.fyxridd.lib.items.model;

import com.fyxridd.lib.core.api.hashList.ChanceHashList;
import com.fyxridd.lib.core.api.hashList.ChanceHashListImpl;
import com.fyxridd.lib.items.ItemsPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemInfo {
	public static class InheritItem {
		//插件名
		String plugin;
		//文件名
		String file;
		//类型名
		String type;
		//目标缩放值,可选,-1表示不缩放
		int tarChance = -1;
		
		public InheritItem(String plugin, String file, String type, int tarChance) {
			super();
			this.plugin = plugin;
			this.file = file;
			this.type = type;
			this.tarChance = tarChance;
		}

		@Override
		public InheritItem clone() throws CloneNotSupportedException {
			return new InheritItem(plugin, file, type, tarChance);
		}
	}

    //方式一
	//继承列表,不为null可为空
	private List<InheritItem> inherits;

    //方式二,三
    //几率
    private int chance;
	//物品
	private ItemWrapper iw;

    //通用
    //物品类型名
    private String name;
	//物品列表,不为null,为空说明未生成物品列表
	private ChanceHashList<ItemWrapper> itemList = new ChanceHashListImpl<>();
	//其它属性,不为null
	private Map<String, Object> properties = new HashMap<>();

    /**
     * 方式一
     */
	public ItemInfo(String name, List<InheritItem> inherits) {
		super();
		this.name = name;
		this.inherits = inherits;
	}

    /**
     * 方式二,三
     */
	public ItemInfo(String name, int chance, ItemWrapper iw) {
        this.inherits = new ArrayList<>();
		this.name = name;
		this.chance = chance;
		this.iw = iw;
	}

    /**
     * clone用
     */
	public ItemInfo(String name, List<InheritItem> inherits, int chance,
			ItemWrapper iw, ChanceHashList<ItemWrapper> itemList,
			Map<String, Object> properties) {
		this.name = name;
		this.inherits = inherits;
		this.chance = chance;
		this.iw = iw;
		this.itemList = itemList;
		this.properties = properties;
	}

	public String getName() {
		return name;
	}

	public List<InheritItem> getInherits() {
		return inherits;
	}

	public int getChance() {
		return chance;
	}

	public ItemWrapper getIw() {
		return iw;
	}

	public ChanceHashList<ItemWrapper> getItemList() {
		return itemList;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}
	
	/**
	 * 由当前物品配置生成物品列表<br>
	 * 读取物品配置时调用一次就可以了
	 */
	public void generateItemWrappers() {
		//继承
		for (InheritItem ii:inherits) {
            ItemInfo info = ItemsPlugin.instance.getItemsManager().getItemInfo(ii.plugin, ii.file, ii.type);
			if (info.getItemList().isEmpty()) info.generateItemWrappers();
			ChanceHashList<ItemWrapper> list = info.getItemList().clone();
            if (ii.tarChance != -1) list.updateTotalChance(ii.tarChance);
			this.itemList.convert(list, false);
		}
		//本身
		if (iw != null) this.itemList.addChance(iw, chance);
	}
	
	/**
	 * 复制的仅有itemList
	 * @return 异常返回null
	 */
	@Override
	public ItemInfo clone() {
		try {
			ChanceHashList<ItemWrapper> list = new ChanceHashListImpl<>();
			for (ItemWrapper i:itemList) list.addChance(i.clone(), itemList.getChance(i));
			return new ItemInfo(name, inherits, chance, iw, list, properties);
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
