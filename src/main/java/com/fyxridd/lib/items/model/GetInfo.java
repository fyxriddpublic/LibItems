package com.fyxridd.lib.items.model;

import java.util.List;

public class GetInfo {
	public static class GetItem {
		//插件名
		private String plugin;
		//文件名
		private String file;
		//物品类型名
		private String type;
		//ItemInfo的一个副本
		//目的是提高检测效率
		//缺点是加大内存使用
		private ItemInfo itemInfo;
		
		//1表示<all/single>:<min次数>-<max次数>
		//2表示<几率缩放目标值>/<最大几率>:<min数量>-<max数量>
		private int method;
		
		//method 1
		//true表示all,false表示single
		private boolean all;
		//min次数 max次数
		private int minTimes,maxTimes;
		
		//method 2
		//几率缩放目标值
		private int tarChance;
		//最大几率
		private int maxChance;
		//min数量 max数量
		private int minAmount,maxAmount;
		
		public GetItem(String plugin, String file, String type, int method,
				boolean all, int minTimes, int maxTimes, int tarChance,
				int maxChance, int minAmount, int maxAmount, ItemInfo itemInfo) {
			super();
			this.plugin = plugin;
			this.file = file;
			this.type = type;
			this.method = method;
			this.all = all;
			this.minTimes = minTimes;
			this.maxTimes = maxTimes;
			this.tarChance = tarChance;
			this.maxChance = maxChance;
			this.minAmount = minAmount;
			this.maxAmount = maxAmount;
			this.itemInfo = itemInfo;
			//计算更新几率
			if (method == 2) itemInfo.getItemList().updateTotalChance(tarChance);
		}

        public String getPlugin() {
            return plugin;
        }

        public String getFile() {
            return file;
        }

        public String getType() {
            return type;
        }

        public ItemInfo getItemInfo() {
            return itemInfo;
        }

        public int getMethod() {
            return method;
        }

        public boolean isAll() {
            return all;
        }

        public int getMinTimes() {
            return minTimes;
        }

        public int getMaxTimes() {
            return maxTimes;
        }

        public int getTarChance() {
            return tarChance;
        }

        public int getMaxChance() {
            return maxChance;
        }

        public int getMinAmount() {
            return minAmount;
        }

        public int getMaxAmount() {
            return maxAmount;
        }
	}
	
	//获取类型名
	private String name;
	private List<GetItem> list;
	public GetInfo(String name, List<GetItem> list) {
		super();
		this.name = name;
		this.list = list;
	}
	public String getName() {
		return name;
	}
	public List<GetItem> getList() {
		return list;
	}
}
