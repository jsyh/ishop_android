package com.jsyh.xjd.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * @desc管理Activtiy
 * 
 */

public class AppManager {

	private static Stack<Activity> activityStack;
	private static AppManager instance;

	private AppManager() {
	}

	/**
	 * 单一实例
	 * 
	 * @return
	 */
	public static AppManager getAppManager() {
		if (instance == null)
			instance = new AppManager();
		return instance;

	}

	/**
	 * 添加Activity到堆栈
	 */

	public void addActivity(Activity activity) {
		if (activityStack == null)
			activityStack = new Stack<Activity>();
		activityStack.add(activity);
	}

	/**
	 * 获取当前的Activity(最后一个压入的)
	 */

	public Activity currentActivity() {
		return activityStack.lastElement();
	}

	/**
	 * 结束当前的Activity ()
	 */

	public void finishActivity() {
		finishActivity(activityStack.lastElement());

	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}

	}

	/**
	 * 结束指定类名的Activity
	 */

	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}

	}

	/**
	 * 结束所有的Activity
	 */
	public void finishAllActivity() {

		for (Activity activity : activityStack) {
			if (activity != null) {
				activity.finish();
			}
		}
		activityStack.clear();

	}

	/**
	 * 退出应用程序
	 */
	public void appExit(Context context) {
		try {

			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
		}

	}

}
