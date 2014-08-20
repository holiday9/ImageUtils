package com.aibang.abbus.util;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

public class CameraTools {
	/*
	 * ���ͼƬ ͼƬ�߶� ���maxH
	 * 
	 * @param imagePath ͼƬ·��
	 */

	public static Bitmap getImageByPath(String imgPath, int maxH) {
		Bitmap bitmap = null;
		try {

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			// ��ȡ���ͼƬ�Ŀ�͸�
			bitmap = BitmapFactory.decodeFile(imgPath, options); // ��ʱ����bmΪ��
			// �������ű�
			int maxwh = Math.max(options.outHeight, options.outWidth);
			int be = (int) (maxwh / (float) maxH);
			int ys = maxwh % maxH;// ������
			float fe = ys / (float) maxH;
			if (fe >= 0.5)
				be = be + 1;
			if (be <= 0)
				be = 1;
			options.inSampleSize = be;

			// ���¶���ͼƬ��ע�����Ҫ��options.inJustDecodeBounds ��Ϊ false
			options.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeFile(imgPath, options);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;

	}
	
	public static String getImagePath(int requestCode, Intent intent,
			Context context) {
		String path = null;
		if (requestCode == PHOTO_PICKED_WITH_DATA) {
			try {
				Uri selectedImage = intent.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
	
				Cursor cursor = context.getContentResolver().query(
						selectedImage, filePathColumn, null, null, null);
				cursor.moveToFirst();
	
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				path = cursor.getString(columnIndex);
				cursor.close();
			} catch (Exception e) {
				System.err.println("δ��ȡͼƬ���");
			}
	
		} else if (requestCode == CAMERA_WITH_DATA) {
			path = IMAGE_PATH;
		}
	
		return path;
	}

	/**
	 * ��BitmapתΪ����
	 * 
	 * @param bitmap
	 * @return
	 */
	public static byte[] bitMapToBytes(Bitmap bitmap) { // TODO refactor move
		if (bitmap == null) {
			return null;
		}
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			int quality = 70;
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
			byte[] data = baos.toByteArray();
			baos.close();
			return data;
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return null;
	}

	public static boolean createPath(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return file.mkdir();
		}
		return true;
	}

	/**
	 * ���洢���Ƿ����
	 * 
	 * @return
	 */
	public static boolean isHasSdcard() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static final String SDCARD_ROOT_PATH = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath();// ·��
	public static final String SAVE_PATH_IN_SDCARD = "/bi.data/";
	public static final String IMAGE_CAPTURE_NAME = "cameraTmp.jpg"; // ��Ƭ���
	public static final String IMAGE_PATH = SDCARD_ROOT_PATH
			+ SAVE_PATH_IN_SDCARD + IMAGE_CAPTURE_NAME;
	public static final String CROP_IMAGE_PATH = SDCARD_ROOT_PATH
			+ SAVE_PATH_IN_SDCARD + "crop.jpg";
	public static final int CAMERA_WITH_DATA = 100;
	public static final int PHOTO_PICKED_WITH_DATA = 200;
	public static final int CAMERA_WITH_CROP = 300;
}
