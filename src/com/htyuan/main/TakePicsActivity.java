package com.htyuan.main;

import java.io.File;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.aibang.abbus.util.CameraTools;
import com.aibang.abbus.util.UIUtils;

public class TakePicsActivity extends Activity implements View.OnClickListener {
	private ImageView mImageView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_pics);

		initView();
	}

	private void initView() {
		mImageView = (ImageView) findViewById(R.id.img);
	}

	@Override
	public void onClick(View v) {
		doTakePhoto();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode != Activity.RESULT_OK) {
			return ;
		}
		
		// 拍照
		if (requestCode == CameraTools.CAMERA_WITH_DATA) {
			// 设置文件保存路径这里放在跟目录下
			File picture = new File(Environment.getExternalStorageDirectory()
					+ "/temp.jpg");
			startPhotoZoom(Uri.fromFile(picture));
		}
		if (requestCode == CameraTools.CAMERA_WITH_CROP) {
			Bitmap bmp = data.getParcelableExtra("data");
			if (bmp != null)
				mImageView.setImageBitmap(bmp);
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 160);
		intent.putExtra("outputY", 160);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CameraTools.CAMERA_WITH_CROP);
	}

	private void doPickPhotoFromGallery() {
		try {
			// Launch picker to choose photo for selected contact
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			startActivityForResult(intent, CameraTools.PHOTO_PICKED_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			UIUtils.showShortToast(this, "图片未取到");
		}
	}

	private void doTakePhoto() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
				Environment.getExternalStorageDirectory(), "temp.jpg")));
		startActivityForResult(intent, CameraTools.CAMERA_WITH_DATA);
	}
}
