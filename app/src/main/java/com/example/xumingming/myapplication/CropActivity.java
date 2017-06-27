package com.example.xumingming.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tozzais.mmlibrary.utils.BitmapUtil;
import com.tozzais.mmlibrary.widget.crop.ClipImageLayout;

/**
 * http://blog.csdn.net/lmj623565791/article/details/39761281
 * @author zhy
 *
 */
public class CropActivity extends Activity {
	private ClipImageLayout mClipImageLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crop);

		mClipImageLayout = (ClipImageLayout) findViewById(R.id.id_clipImageLayout);

		String path = getIntent().getStringExtra("path");

		//这种方法压缩很慢
		BitmapUtils.getInstance().compactPic(this, path, new BitmapUtils.CallBack() {
			@Override
			public void resultPath(String path) {
				Log.e("---jiancjie----",path+"");
				mClipImageLayout.getmZoomImageView().setImageBitmap(BitmapFactory.decodeFile(path));
			}
		});

//		Bitmap bm = BitmapFactory.decodeFile(path);
//		if (bm == null) {
//			ToastUtil.getInstance().show(this, "图片为null");
//			return;
//		}
//		Bitmap bmp = null;
//		DisplayMetrics dm = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(dm);
//		int screenWidth = dm.widthPixels;
//		if (bm.getWidth() <= screenWidth) {
//			//图片很小就不压缩
//			bmp = bm;
//		} else {
//			//图片很大就压缩 小米2手机上测试 压缩了18倍
//			bmp = Bitmap.createScaledBitmap(bm, screenWidth, bm.getHeight() * screenWidth / bm.getWidth(), false);
////			Log.e("---之后的大小----", bmp.getByteCount() + "");
//		}
//		mClipImageLayout.getmZoomImageView().setImageBitmap(bmp);


	}


	public void onclick(View view) {
		Bitmap bitmap = mClipImageLayout.clip();

		String path = getExternalFilesDir(null).getAbsolutePath() + "/" + String.valueOf(System.currentTimeMillis()) + ".png";
		BitmapUtil.savePhotoToSDCard(bitmap, path);
		Log.e("保存后的大小", "" + bitmap.getByteCount() + path);
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//		byte[] datas = baos.toByteArray();
		Intent intent = new Intent();
//		intent.putExtra("bitmap", datas);
		intent.putExtra("path", path);
		setResult(RESULT_OK, intent);
		finish();

	}

}
