package com.jsyh.xjd.model;

/**
 * Created by Su on 2016/1/21.
 */
public class MenuItemModel {

    private String name;//每个item名称

    private String iconNormal;//正常状态下icon图标
    private String iconPress;//按下状态的icon图标

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconNormal() {
        return iconNormal;
    }

    public void setIconNormal(String iconNormal) {
        this.iconNormal = iconNormal;
    }

    public String getIconPress() {
        return iconPress;
    }

    public void setIconPress(String iconPress) {
        this.iconPress = iconPress;
    }
}
