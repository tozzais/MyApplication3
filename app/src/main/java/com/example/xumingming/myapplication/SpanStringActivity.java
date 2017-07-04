package com.example.xumingming.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.widget.TextView;

/**
 * http://blog.csdn.net/lmj623565791/article/details/39761281
 * @author zhy
 *
 *
 * 实现android文本的第一行 加一个图片
 *
 */
public class SpanStringActivity extends AppCompatActivity {



	private TextView text;



	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		text = (TextView) findViewById(R.id.text);



		SpannableString ss = new SpannableString("0 这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据这是测试数据");
		ImageSpan span = new MyIm(this, R.mipmap.selete_fill);
		ss.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		text.setText(ss);

//		setLeftImage(this,text,"这时候测试数据这时候测试数据这时候测试数据这时候测试数据这时候测试数据这时候测试数据"
//				,R.mipmap.selete_fill);


//		SpannableStringBuilder ssb = new SpannableStringBuilder("这时候测试数据这时候测试数据这时候测试数据这时候测试数据这时候测试数据这时候测试数据");
//		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.selete_fill);
//		ssb.setSpan(new IconMarginSpan(bitmap, 60), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//		text.setText(ssb);

//		SpannableStringBuilder ssb = new SpannableStringBuilder("这时候测试数据这时候测试数据这时候测试数据这时候测试数据这时候测试数据这时候测试数据");
//		ssb.setSpan(new ImageSpan(this, R.mipmap.selete_fill, ImageSpan.ALIGN_BASELINE), 0, 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//		text.setText(ssb);
	}

	private void setLeftImage(final Context mContext, final TextView text, String str, final Integer imageResoures){
		text.setText(str);
		SpannableString ss8 = new SpannableString("0 "+text.getText());
		ss8.setSpan(new DynamicDrawableSpan(DynamicDrawableSpan.ALIGN_BOTTOM) {

			@Override
			public Drawable getDrawable() {
				Drawable d = mContext.getResources().getDrawable(
						imageResoures);
//				d.setBounds(0,1 , 80,100);
				Log.e("----",text.getTop()+""+text.getLineSpacingExtra());
				d.setBounds(0, (int) -text.getLineSpacingExtra(),100, (int) (50 -text.getLineSpacingExtra()));
//				d.setBounds(0, -(int) text.getLineSpacingExtra(),100,50-(int) text.getLineSpacingExtra());
				return d;
			}
		}, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// 这里的参数0，1表示将“设”字替换为图片
		text.setText(ss8);
	}

	public class MyIm extends ImageSpan
	{
		public MyIm(Context arg0, int arg1) {
			super(arg0, arg1);
		}


		public int getSize(Paint paint, CharSequence text, int
				start, int end,
						   Paint.FontMetricsInt fm) {
			Drawable d = getDrawable();
			Rect rect = d.getBounds();
			if (fm != null) {
				Paint.FontMetricsInt fmPaint=paint.getFontMetricsInt();
				int fontHeight = fmPaint.bottom - fmPaint.top;
				int drHeight=rect.bottom-rect.top;
				int top= drHeight/2 - fontHeight/4;
				int bottom=drHeight/2 + fontHeight/4;
				fm.ascent=-bottom;
				fm.top=-bottom;
				fm.bottom=top;
				fm.descent=top;

				Log.e("----",top+"::::"+bottom+":"+":"+":"+":");
			}
			return rect.right;
		}
		@Override
		public void draw(Canvas canvas, CharSequence text, int start, int end,
						 float x, int top, int y, int bottom, Paint paint) {
			Log.e("----",start+":"+end+":"+top+":"+bottom+":");
			Drawable b = getDrawable();
			canvas.save();
			int transY = 0;
			transY = ((bottom-top) - b.getBounds().bottom)/2+top;
			canvas.translate(x, transY);
			b.draw(canvas);
			canvas.restore();
		}
	}






}
