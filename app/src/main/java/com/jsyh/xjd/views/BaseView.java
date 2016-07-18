package com.jsyh.xjd.views;

/**
 * 
 * @ClassName: BaseView
 * @Description: TODO(view 基类 )
 * @author yaodingding
 * @date 2015-9-24 上午10:28:56
 * 
 */
public interface BaseView {
	/**
	 * 对于请求错误的处理
	 * 
	 * @param msg
	 *            错误消息
	 * @param tag
	 *            错误tag
	 */
	public void error(String msg, Object tag);// 获取数据失败
}
