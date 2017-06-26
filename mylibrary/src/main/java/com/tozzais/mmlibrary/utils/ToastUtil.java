package com.tozzais.mmlibrary.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tozzais.mmlibrary.R;


/**
 * 自定义toast
 * 
 * @author dongsy
 * @version 创建时间：2015年10月23日 下午12:08:52
 */
public class ToastUtil {

	private static ToastUtil instance;

	private Toast toast;

	private ToastUtil() {
	}

	public static ToastUtil getInstance() {
		if (instance == null) {
			instance = new ToastUtil();
		}
		return instance;
	}

	/**
	 * 显示Toast
	 * 
	 * @param context
	 * @param tvString
	 */

	public void show(Context context, String tvString,int gravity,int duration) {
		View layout = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
		TextView text = (TextView) layout.findViewById(R.id.toast_des);
		text.setText(tvString);
		if (toast == null) {
			//context.getApplicationContext()取代context也是为了防止内存泄露
			toast = new Toast(context.getApplicationContext());
			toast.setGravity((gravity == -1)? Gravity.CENTER:gravity, 0, 0);
			toast.setDuration((duration == -1)?Toast.LENGTH_SHORT:duration);
			toast.setView(layout);
			toast.show();
		} else {
			toast.setView(layout);
			toast.show();
		}

	}

	/**
	 * 同名函数 可以设置位置
	 * @param context
	 * @param tvString
	 * @param gravity
	 */
	public void show(Context context, String tvString,int gravity) {
		show(context,tvString,gravity,-1);

	}

	/**
	 * 同名函数 默认情况
	 * @param context
	 * @param tvString
	 */
	public void show(Context context, String tvString) {
		show(context,tvString,-1,-1);

	}


}
