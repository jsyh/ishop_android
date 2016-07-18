package com.jsyh.xjd.model;

import java.util.List;

/**
 * Created by Su on 2016/1/21.
 */
public class MenuModel {


    private String textNormalColor;

    public String getTextNormalColor() {
        return textNormalColor;
    }

    public void setTextNormalColor(String textNormalColor) {
        this.textNormalColor = textNormalColor;
    }

    public String getTextPressColor() {
        return textPressColor;
    }

    public void setTextPressColor(String textPressColor) {
        this.textPressColor = textPressColor;
    }

    public String getItemNormalColor() {
        return itemNormalColor;
    }

    public void setItemNormalColor(String itemNormalColor) {
        this.itemNormalColor = itemNormalColor;
    }

    public String getItemPressColor() {
        return itemPressColor;
    }

    public void setItemPressColor(String itemPressColor) {
        this.itemPressColor = itemPressColor;
    }

    public List<MenuItemModel> getItems() {
        return items;
    }

    public void setItems(List<MenuItemModel> items) {
        this.items = items;
    }

    private String textPressColor;

    private String itemNormalColor;
    private String itemPressColor;

    /**
     * 菜单
     */
    private List<MenuItemModel> items;


    /**
     * 服务器，接口请求地址
     */
    //首页请求地址
    private String mainUrlAddress;

    public String getMainUrlAddress() {
        return mainUrlAddress;
    }

    public void setMainUrlAddress(String mainUrlAddress) {
        this.mainUrlAddress = mainUrlAddress;
    }

}
