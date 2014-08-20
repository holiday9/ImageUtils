package com.htyuan.main;

import java.io.File;
import java.net.URI;
import java.util.List;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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

	//    /storage/emulated/0/DCIM/Camera/20140818_184744.jpg
	@Override
	public void onClick(View v) {
//		doTakePhoto();
		doPickPhotoFromGallery();
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) {
			return;
		}

		// 拍照
		if (requestCode == CameraTools.CAMERA_WITH_DATA) {
			// 设置文件保存路径这里放在跟目录下
			File picture = new File(Environment.getExternalStorageDirectory()
					+ "/temp.jpg");
			startPhotoZoom(Uri.fromFile(picture));
		}

		if (requestCode == CameraTools.PHOTO_PICKED_WITH_DATA) {
			String path = CameraTools.getImagePath(requestCode, data, this);
			System.out.println(path);
			
			File file = new File(path);
			if (file.exists()) {
				System.out.println("图片存在");
			} else {
				System.out.println("图片不存在");
			}
			startPhotoZoom(Uri.fromFile(file));  // Uri.parse(path)会报错．原因：查看Uri.fromFile(file)的注释．
		}

		if (requestCode == CameraTools.CAMERA_WITH_CROP) {
			Bitmap bmp = data.getParcelableExtra("data");
			setImageView(bmp);
		}
	}

	protected void setImageView(Bitmap bmp) {
		if (bmp != null)
			mImageView.setImageBitmap(bmp);
	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("return-data", true);
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 160);
		intent.putExtra("outputY", 160);
		startActivityForResult(intent, CameraTools.CAMERA_WITH_CROP);
	}

	protected Uri getTempUri() {
		return Uri.fromFile(getTempFile());
	}

	private String camera_crop_temp_file = "corp.jpg";

	protected File getTempFile() {
		File f = new File(Environment.getExternalStorageDirectory(),
				camera_crop_temp_file);
		try {
			f.createNewFile();
		} catch (Exception e) {
			Toast.makeText(this, "SD临时文件读取错误！", Toast.LENGTH_LONG).show();
		}
		return f;
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
