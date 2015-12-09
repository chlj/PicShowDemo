package com.pic.demo;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 宽指定，高不确定。图片按比例显示
 * 
 * @author Administrator
 * 
 */
public class Pic1Activity extends Activity {

	private ImageView imgs, imgs2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pic1);

		imgs = (ImageView) findViewById(R.id.imgs);
		imgs2 = (ImageView) findViewById(R.id.imgs2);

		String str = "http://www.3d414.com/Tpl/default/upload/25685_detail-1_big.jpg";
		//
		new MyTask2().execute(new Object[] { str, imgs });

		str = "http://www.3d414.com/Tpl/default/upload/wshop/huodong/5666320175db2_small.jpg";

		new MyTask2().execute(new Object[] { str, imgs2 });

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
				Log.i("xx", "w1=" + bmp.getWidth() + ",h=" + bmp.getHeight()); // 750*2880
																				// ，640*340

				int w = getScreenWidth(getApplicationContext());
				int h = getScreenHeight(getApplicationContext());

				double imgHeight = ArithUtil.div(ArithUtil.mul(bmp.getHeight(), w), bmp.getWidth());

				// (y *w) /x =h

				LinearLayout.LayoutParams params = (android.widget.LinearLayout.LayoutParams) img.getLayoutParams();
				params.width = w;
				params.height = (int) imgHeight;
				img.setLayoutParams(params);
				img.setImageBitmap(bmp);

			}
		}

	};

}
