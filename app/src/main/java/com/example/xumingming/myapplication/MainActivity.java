package com.example.xumingming.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tozzais.mmlibrary.utils.address.CityUtils;
import com.tozzais.mmlibrary.utils.address.OnAddressSelectListener;
import com.tozzais.mmlibrary.utils.address.bean.CityBean;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CityUtils.init(this);

    }


    public void onclick(View view){


        CityUtils.getInstance().showAddressDialog(this, new OnAddressSelectListener() {
            @Override
            public void onFinish(CityBean province, CityBean city, CityBean county) {

            }
        });

    }
}
