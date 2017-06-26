package com.tozzais.mmlibrary.utils.address;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tozzais.mmlibrary.Constant;
import com.tozzais.mmlibrary.R;
import com.tozzais.mmlibrary.utils.address.bean.CityBean;
import com.tozzais.mmlibrary.utils.address.bean.CityDao;
import com.tozzais.mmlibrary.widget.wheel.OnWheelChangedListener;
import com.tozzais.mmlibrary.widget.wheel.WheelView;
import com.tozzais.mmlibrary.widget.wheel.adapters.AbstractWheelTextAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * 这是一个从地址选择器
 *
 */
public class CityUtils implements OnWheelChangedListener {

	/**
	 * 这是 城市的级数 1表示只到省 2表示只到市 3表示只到区
	 */
	public static final int LEVEL_ONE = 1 ,LEVEL_TWO = 2 , DEFAULT = 3;

	private static CityUtils cityUtils;

	public static CityUtils getInstance() {
		if (cityUtils == null) {
			synchronized (CityUtils.class) {
				if (cityUtils == null) {
					cityUtils = new CityUtils();
				}
			}
		}
		return cityUtils;
	}

	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewCounty;

	private TextView mBtnConfirm;
	private Context context;
	private CityDao cityDao;
	private CityWheelAdapter provinceAdapter;
	private CityWheelAdapter cityAdapter;
	private Dialog cityDialog;

	/**
	 *
	 * @param context
	 * @param number
	 * @param listener
	 */
	public void showAddressDialog(final Context context,int number ,final OnAddressSelectListener listener) {
		cityDao = new CityDao(context);
		this.context = context;
		View view = View.inflate(context, R.layout.city_choose_dialog, null);
		mViewProvince = (WheelView) view.findViewById(R.id.wv_country);
		mViewCity = (WheelView) view.findViewById(R.id.wv_city);
		mViewCounty = (WheelView) view.findViewById(R.id.wv_county);
		if(LEVEL_ONE == number){
			mViewCity.setVisibility(View.GONE);
			mViewCounty.setVisibility(View.GONE);
		}else if (LEVEL_TWO == number){
			mViewCounty.setVisibility(View.GONE);
		}

		mBtnConfirm = (TextView) view.findViewById(R.id.tv_commit);
		TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
		mBtnConfirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cityDialog.dismiss();
				CityBean provinceBean = cityDao.getProvince().get(mViewProvince.getCurrentItem());
				List<CityBean> citys = cityDao.getCity(provinceBean.id);
				CityBean city = citys.get(mViewCity.getCurrentItem());
				
				List<CityBean> countrys = cityDao.getCity(city.id);
				CityBean county = countrys.get(mViewCounty.getCurrentItem());
				if (listener != null) {
					listener.onFinish(provinceBean, city,county);
				}
			}
		});
		tv_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cityDialog.dismiss();
			}
		});

		setUpListener();
		setUpData();

		cityDialog = new Dialog(context,R.style.transparentFrameWindowStyle);
		cityDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		cityDialog.setContentView(view);
		Window window = cityDialog.getWindow();
		window.setWindowAnimations(R.style.PopupAnimation);

		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = ((Activity) context).getWindowManager().getDefaultDisplay().getHeight();
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

		cityDialog.getWindow().setAttributes(wl);

		cityDialog.setCanceledOnTouchOutside(true);
		cityDialog.show();
	}

	/**
	 *
	 * @param context
	 * @param listener
	 */
	public void showAddressDialog(final Context context,final OnAddressSelectListener listener) {
		showAddressDialog(context,DEFAULT,listener);
	}



	/**
	 * 设置数据
	 */
	private void setUpData() {
		List<CityBean> province = cityDao.getProvince();
		provinceAdapter = new CityWheelAdapter(context, province);
		mViewProvince.setViewAdapter(provinceAdapter);
		
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
		mViewCounty.setVisibleItems(7);
		updateCities();
		updateCounty();
	}

	private void setUpListener() {
		mViewProvince.addChangingListener(this);
		mViewCity.addChangingListener(this);
		mViewCounty.addChangingListener(this);
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		if (wheel == mViewProvince) {
			updateCities();
			updateCounty();
		}else if(wheel == mViewCity){
			updateCounty();
		}
	}

	private void updateCities() {

		List<CityBean> city =  cityDao.getCity(provinceAdapter.getCityId(mViewProvince.getCurrentItem()));
		cityAdapter = new CityWheelAdapter(context, city);

		mViewCity.setViewAdapter(cityAdapter);
		mViewCity.setCurrentItem(0);
	}
	private void updateCounty(){
		mViewCounty.setViewAdapter(new CityWheelAdapter(context,
				cityDao.getCity(cityAdapter.getCityId(mViewCity.getCurrentItem()))));
		mViewCounty.setCurrentItem(0);
	}

	class CityWheelAdapter extends AbstractWheelTextAdapter {
		List<CityBean> list;

		public CityWheelAdapter(Context context, List<CityBean> list) {
			super(context);
			this.list = list;
		}

		@Override
		public int getItemsCount() {
			return list == null ? 0 : list.size();
		}

		public String getCityId(int position) {
			return list.get(position).id;
		}

		@Override
		protected CharSequence getItemText(int index) {
			return list.get(index).name;
		}

	}

	/**
	 * 初始化
	 */
	public static final String FILE_PATH = "static.db";
	public static void init(final Context mContext){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					File RootPath = new File(Constant.ROOT_PATH);
					if (!RootPath.exists()) {
						RootPath.mkdirs();
					}else {
						RootPath.delete();
					}
					File DBFile = new File(RootPath + "/static.dll");
					if (!DBFile.exists()) {
						AssetManager assetManager = mContext.getAssets();
						InputStream fis = null;
						FileOutputStream fos = null;
						try {
							fis = assetManager.open(FILE_PATH);
							fos = new FileOutputStream(DBFile);
							byte[] buffer = new byte[1024 * 10];
							int len = 0;
							while ((len = fis.read(buffer)) != -1) {
								fos.write(buffer, 0, len);
							}
							fos.flush();

						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							if (fis != null) {
								try {
									fis.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}

							if (fos != null) {
								try {
									fos.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}


}
