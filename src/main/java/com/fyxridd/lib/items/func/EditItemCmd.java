package com.fyxridd.lib.items.func;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fyxridd.lib.func.api.func.Default;
import com.fyxridd.lib.func.api.func.Extend;
import com.fyxridd.lib.func.api.func.Func;
import com.fyxridd.lib.func.api.func.FuncType;
import com.fyxridd.lib.items.ItemsPlugin;

@FuncType("cmd")
public class EditItemCmd {
    @Func("open")
    public void open(CommandSender sender, boolean reset) {
        if (!(sender instanceof Player)) return;
        Player p = (Player) sender;
        ItemsPlugin.instance.getEditItemManager().open(p, reset);
    }

    @Func("save")
    public void save(CommandSender sender, String type, boolean force) {
        if (!(sender instanceof Player)) return;
        Player p = (Player) sender;
        ItemsPlugin.instance.getEditItemManager().save(p, type, force);
    }

    @Func("getItems")
    public void getItems(CommandSender sender, String plugin, String getType) {
        if (!(sender instanceof Player)) return;
        Player p = (Player) sender;
        ItemsPlugin.instance.getEditItemManager().getItems(p, plugin, getType);
    }

    @Func("getStaticItems")
    public void getStaticItems(CommandSender sender, String plugin, String fileName, String itemType) {
        if (!(sender instanceof Player)) return;
        Player p = (Player) sender;
        ItemsPlugin.instance.getEditItemManager().getStaticItems(p, plugin, fileName, itemType);
    }

    @Func("getDynamicItems")
    public void getDynamicItems(CommandSender sender, String plugin, String type, @Default("") @Extend String arg) {
        if (!(sender instanceof Player)) return;
        Player p = (Player) sender;
        ItemsPlugin.instance.getEditItemManager().getDynamicItems(p, plugin, type, arg);
    }
}
