package com.tozzais.mmlibrary.utils.address;

import com.tozzais.mmlibrary.utils.address.bean.CityBean;

/**
 * Created by xumingming on 2017/6/26.
 * 城市选择的接口
 */

public interface OnAddressSelectListener {

    void onFinish(CityBean province, CityBean city, CityBean county);

}
