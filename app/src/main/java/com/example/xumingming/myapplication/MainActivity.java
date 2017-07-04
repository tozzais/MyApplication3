package com.example.xumingming.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.tozzais.mmlibrary.utils.ToastUtil;

import java.io.File;

/**
 * http://blog.csdn.net/lmj623565791/article/details/39761281
 * @author zhy
 *
 */
public class MainActivity extends AppCompatActivity {

	private ImageView image;
	private CountDownView countDownView;





	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		image = (ImageView) findViewById(R.id.image);
		countDownView = (CountDownView) findViewById(R.id.countDownView);
		countDownView.startTime(1*60*60+10);
		countDownView.setListener(new CountDownView.OnFinishListener() {

			@Override
			public void onFinish(boolean isFinish) {
				ToastUtil.getInstance().show(MainActivity.this,"结束了");
			}
		});


	}


	public static final int PHOTOZOOM = 0;
	public static final int PHOTOTAKE = 1;
	public static final int IMAGE_COMPLETE = 2;
	private String photoPath;


	public void onclick(View view){

//		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//		photoPath = Constant.ROOT_PATH+"/"+String.valueOf(System.currentTimeMillis()) + ".png";
//		File file = new File(photoPath);
//		Uri imageUri = Uri.fromFile(file);
//		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//		startActivityForResult(intent, PHOTOTAKE);


		//这里必须用 getExternalFilesDir(null).getAbsolutePath() 用Constant.ROOT_PATH在小米3上运用不了
		//明明点击了 确定 resultCode 返回的不是OK 是CANCEL  具体原因不知道
		photoPath = String.valueOf(System.currentTimeMillis()) + ".png";
		Uri imageUri = null;
		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		imageUri = Uri.fromFile(new File(getExternalFilesDir(null).getAbsolutePath(), photoPath));
		openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
		openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(openCameraIntent, PHOTOTAKE);



	}
	public void onclick2(View view){
//		Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
//		openAlbumIntent.setType("image/*");
//		startActivityForResult(openAlbumIntent, PHOTOZOOM);

		Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, PHOTOZOOM);
//		startCrop("");

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		Log.e("-------",requestCode+""+resultCode);
		if(resultCode == RESULT_OK){
			switch (requestCode){
				//照相机
				case PHOTOTAKE:
					startCrop(getExternalFilesDir(null).getAbsolutePath()+"/"+photoPath);
					break;

				//相册
				case PHOTOZOOM:
					Uri uri = data.getData();
					Cursor cursor = getContentResolver().query(uri, null, null, null,null);
					if (cursor != null && cursor.moveToFirst()) {
						String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
						startCrop(path);
					}
					break;

				//裁剪完
				case IMAGE_COMPLETE:

//					byte[] b = data.getByteArrayExtra("bitmap");
//					Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
//					if (bitmap != null)
//					{
						image.setImageBitmap(BitmapFactory.decodeFile(data.getStringExtra("path")));
//					}

					break;
			}
		}

		super.onActivityResult(requestCode, resultCode, data);

	}

	public void startCrop(String path){
//		String path1 = "/storage/emulated/0/Android/data/com.example.xumingming.myapplication/files/1498486068851.png";
		Log.e("-------",path);
		Intent intent = new Intent(MainActivity.this,CropActivity.class);
		intent.putExtra("path",path);
		startActivityForResult(intent,IMAGE_COMPLETE);
	}
}
