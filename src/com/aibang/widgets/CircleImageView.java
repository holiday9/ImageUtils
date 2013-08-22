package com.aibang.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircleImageView extends ImageView{
	private Bitmap mMaskBitmap;
	private Paint mPaint;
	private static final Xfermode MASK_XFERMODE = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);;
	
	public CircleImageView(Context context) {
		super(context);
	}

	public CircleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private Bitmap createMaskBitmap() {
		int w = getWidth();
		int h = getHeight();
		
		Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		
		// init canvas bg
		canvas.drawARGB(0, 0, 0, 0);
		
		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		final Rect rect1 = new Rect(0, 0, w, h);
		final RectF rectF = new RectF(rect1);
		float rx = w / 1;
		float ry = h / 1;
		canvas.drawRoundRect(rectF, rx, ry, paint);
		
		return output;
	}
	
	public Bitmap createMask() {
		int w = getWidth();
		int h = getHeight();
		
		Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bm);
		RectF localRectF = new RectF(0.0F, 0.0F, w, h);
		Paint paint = new Paint();
		canvas.drawOval(localRectF, paint);
		return bm;
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Drawable localDrawable = getDrawable();
		if (localDrawable == null) {
			return;
		}
		
		int i = canvas.saveLayer(0.0F, 0.0F, getWidth(), getHeight(), null,
				31); 
		localDrawable.setBounds(0, 0, getWidth(), getHeight());
		localDrawable.draw(canvas);
		
		if (mMaskBitmap == null || mMaskBitmap.isRecycled()) {
			mMaskBitmap = createMask();//createMaskBitmap();			
		}
		if (mPaint == null) {
			mPaint =  new Paint();
			mPaint.setFilterBitmap(false);
			mPaint.setXfermode(MASK_XFERMODE);
		}
		canvas.drawBitmap(mMaskBitmap, 0F, 0F, mPaint);
//		canvas.restoreToCount(i);     // 这句话是不起作用的，用了反而会报错。
	}
}
