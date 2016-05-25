package com.fyxridd.lib.items;

import com.fyxridd.lib.core.api.SqlApi;
import com.fyxridd.lib.core.api.plugin.SimplePlugin;
import com.fyxridd.lib.info.manager.DaoManager;
import com.fyxridd.lib.info.manager.InfoManager;

import java.io.File;

public class ItemsPlugin extends SimplePlugin{
    public static InfoPlugin instance;

    private InfoManager infoManager;
    private DaoManager daoManager;

    @Override
    public void onEnable() {
        instance = this;

        //注册Mapper文件
        SqlApi.registerMapperXml(new File(dataPath, "InfoUserMapper.xml"));

        infoManager = new InfoManager();
        daoManager = new DaoManager();

        super.onEnable();
    }

    public InfoManager getInfoManager() {
        return infoManager;
    }

    public DaoManager getDaoManager() {
        return daoManager;
    }
}