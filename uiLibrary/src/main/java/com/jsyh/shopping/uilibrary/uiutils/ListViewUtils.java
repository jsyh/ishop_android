package com.jsyh.shopping.uilibrary.uiutils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Liang on 2015/9/18.
 *
 *  修改listview的高度为包裹内容的长度 或者，限定 内容。
 */
public class ListViewUtils {


    public static boolean setListViewHeightBasedOnItems(ListView listView) {
        ListAdapter listAdapter =  listView.getAdapter();
        if (listAdapter != null) {

            return setListViewHeightBasedOnItems(listView, listAdapter, listView.getAdapter().getCount());

        } else {
            return false;
        }

    }

    public static boolean setListViewHeightBasedOnItems(ListView listView ,ListAdapter listAdapter, int limitShowNumber) {

//        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            if (limitShowNumber < 0 || limitShowNumber > numberOfItems) {
                return false;
            }

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < limitShowNumber;itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() * limitShowNumber;

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }


}
