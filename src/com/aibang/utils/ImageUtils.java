package com.aibang.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class ImageUtils {
	/**
     * 将图片截取为圆角图片
     * @param bitmap 原图片
     * @param ratio 截取比例，如果是8，则圆角半径是宽高的1/8，如果是2，则是圆形图片
     * @return 圆角矩形图片
     */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float ratio) {
		final int w = bitmap.getWidth();
		final int h = bitmap.getHeight();
		//create canvas
		Bitmap output = Bitmap.createBitmap(w,
				h, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		
		// init canvas bg
		canvas.drawARGB(0, 0, 0, 0);
		
		//make a rectF
		
		//draw a round rect
		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		final Rect rect1 = new Rect(10, 10, w - 10, h - 10);
		final RectF rectF = new RectF(rect1);
		float rx = rectF.left + (rectF.right - rectF.left) / 2;
		float ry = rectF.top + (rectF.bottom - rectF.top) / 2;
		canvas.drawRoundRect(rectF, rx,
				ry , paint);

		//draw bitmap with Mode.SRC_IN
		final Rect rect = new Rect(0, 0, w, h);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		
		return output;
	}
}
