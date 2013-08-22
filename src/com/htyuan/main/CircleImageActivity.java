package com.htyuan.main;

import java.io.InputStream;

import com.aibang.utils.ImageUtils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CircleImageActivity extends Activity implements View.OnClickListener{
	private ImageView mImageView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circleimage);
		
		initView();
		
		setImageView();
	}

	private void setImageView() {
		InputStream is = getResources().openRawResource(R.drawable.icon);  
		Bitmap srbm = BitmapFactory.decodeStream(is); 
		Bitmap dsbm = ImageUtils.getRoundedCornerBitmap(srbm, 2);
		mImageView.setImageBitmap(dsbm);
	}

	private void initView() {
		mImageView = (ImageView) findViewById(R.id.img);
	}

	@Override
	public void onClick(View v) {
		
	}
}
