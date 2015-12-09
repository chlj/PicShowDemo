package com.pic.demo;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * 图片,宽高定死 ，等比例显示
 * 
 * @author Administrator
 * 
 */
public class Pic2Activity extends Activity {

	private ImageView imgs, imgs2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pic2);

		imgs = (ImageView) findViewById(R.id.imgs);
		imgs2 = (ImageView) findViewById(R.id.imgs2);

		String str = "http://www.3d414.com//Tpl//default//upload//wshop//index//5666321c6c92e_small.jpg";
		//
		new MyTask2().execute(new Object[] { str, imgs });

		str = "http://www.3d414.com//Tpl//default//upload//wshop//index//566541c1091b7_small.jpg";

		new MyTask2().execute(new Object[] { str, imgs2 });

	}

	public class MyTask2 extends AsyncTask<Object, Integer, Void> {
		@Override
		protected Void doInBackground(Object... params) {
			InputStream inputStream;
			try {
				inputStream = HttpUtils.getImageViewInputStream(String.valueOf(params[0]));
				Bitmap drawable = BitmapFactory.decodeStream(inputStream);

				Message msg = Message.obtain();
				msg.what = 1;
				msg.obj = new Object[] { params[1], drawable };
				mHandler.sendMessage(msg);

				if (drawable == null) {
					Log.i("xx", "==========is null");
				} else {
					Log.i("xx", "==========is not null");
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				Object[] obj = (Object[]) msg.obj;
				ImageView img = (ImageView) obj[0];
				Bitmap bmp = (Bitmap) obj[1];

				final int h = dp2px(Pic2Activity.this, 220); // 220 为xml 中写好的宽度
				Bitmap bp = zoomImg(bmp, getScreenWidth(Pic2Activity.this), h);
				img.setImageBitmap(bp);

			}
		}

	};

	public static int dp2px(Context context, float dpValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	public static int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

	/**
	 * 处理图片
	 * 
	 * @param bm
	 *            �?要转换的bitmap
	 * @param newWidth新的�?
	 * @param newHeight新的�?
	 * @return 指定宽高的bitmap
	 */
	public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
		// 获得图片的宽�?
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片
		Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
		return newbm;
	}

}
