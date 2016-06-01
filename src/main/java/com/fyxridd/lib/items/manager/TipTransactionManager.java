package com.fyxridd.lib.items.manager;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

import com.fyxridd.lib.core.api.event.ReloadConfigEvent;
import com.fyxridd.lib.items.ItemsPlugin;
import com.fyxridd.lib.tiptransaction.api.TransactionApi;

public class TipTransactionManager {
    public TipTransactionManager() {
        //注册事件
        {
            //请求重载配置
            Bukkit.getPluginManager().registerEvent(ReloadConfigEvent.class, ItemsPlugin.instance, EventPriority.HIGHEST, new EventExecutor() {
                @Override
                public void execute(Listener listener, Event e) throws EventException {
                    if (e instanceof ReloadConfigEvent) {
                        ReloadConfigEvent event = (ReloadConfigEvent) e;
                        if (event.getPlugin().equals(ItemsPlugin.instance.pn)) TransactionApi.reloadTips(ItemsPlugin.instance.pn);
                    }
                }
            }, ItemsPlugin.instance);
        }
        //重新读取提示
        Bukkit.getScheduler().scheduleSyncDelayedTask(ItemsPlugin.instance, new Runnable() {
            @Override
            public void run() {
                TransactionApi.reloadTips(ItemsPlugin.instance.pn);
            }
        });
    }
}
