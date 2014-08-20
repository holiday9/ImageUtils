package com.aibang.abbus.util;

import android.content.Context;
import android.widget.Toast;


/**
 * 界面工具类;
 * 
 * @author zgli;
 * 
 */
public class UIUtils {
	public static Toast mToast;
	public static final String TOKEN = "##";

	/**
	 * 显示提示消息框;
	 * 
	 * @param context
	 *            : 上下文;
	 * @param info
	 *            : 消息内容;
	 */
	public static void showShortToast(Context context, String info) {
		if (mToast == null) {
			mToast = Toast.makeText(context, info, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(info);
		}
		mToast.show();
	}

}
