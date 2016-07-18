package com.jsyh.shopping.uilibrary.popuwindow;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.jsyh.shopping.uilibrary.R;
import com.jsyh.shopping.uilibrary.uiutils.ListViewUtils;

import java.util.List;

/**
 * 综合筛选
 */
public class FilterMultiplePopuwindow extends BackgoudDimPopuwindow {


    private View            contentView;

    private ListView        mMenuListView;

    private int             dataCount;

    private BaseAdapter     mAdapter;

    // Menu 展开的list 显示数量
    private int             showCount;


    public FilterMultiplePopuwindow(Context context, int dataCount) {

        super(context);
        this.dataCount = dataCount;

    }

    @Override
    View getContent(Context context) {

        contentView = inflater.inflate(R.layout.goods_multiple_layout, null);

        mMenuListView = (ListView) contentView.findViewById(R.id.lvMenu);

        return contentView;
    }

    public void setShowCount(int showCount) {
        this.showCount = showCount;
    }

    public void setAdapter(BaseAdapter adapter) {
        mMenuListView.setAdapter(adapter);

        ListViewUtils.setListViewHeightBasedOnItems(mMenuListView,adapter,showCount);

    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {

        if (onItemClickListener != null) {
            mMenuListView.setOnItemClickListener(onItemClickListener);
        }
    }


    /**
     * 清空所有选择
     */
    public void clearAllChecked(){



    }


   public static class MultipleFilterModel{
        public String      name;
        public boolean     checked;



       public MultipleFilterModel(String name, boolean checked) {
            this.name = name;
            this.checked = checked;
        }
    }

}
