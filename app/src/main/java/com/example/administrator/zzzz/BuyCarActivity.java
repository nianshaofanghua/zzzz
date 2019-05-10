package com.example.administrator.zzzz;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

public class BuyCarActivity extends AppCompatActivity {

    private RecyclerView rv;
    private RvAdapter mRvAdapter;
    private ArrayList<RvModel> mList;
    private RelativeLayout rl;
private ImageView iv_buy_car;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_car);

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
// 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (height / density);// 屏幕高度(dp)
        Log.e("width=======>",screenWidth+"");
        Log.e("height=======>",screenHeight+"");


        TextView tv_message = findViewById(R.id.tv_message);
        tv_message.setText(screenWidth+"----------"+screenHeight);














        rl = findViewById(R.id.rl);
        iv_buy_car = findViewById(R.id.iv_buy_car);
        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            RvModel model = new RvModel();
            model.setTitle("名储层");
            model.setPicUrl("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1316704868,2175920806&fm=26&gp=0.jpg");
            mList.add(model);
        }
        rv = findViewById(R.id.rv);
        mRvAdapter = new RvAdapter(R.layout.rv_item, mList, this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mRvAdapter);
        mRvAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                ImageView iv_pic = view.findViewById(R.id.iv_pic);
                AnimationUtils.AddToShopingCart((ImageView) iv_pic, iv_buy_car, BuyCarActivity.this, rl, 1);

            }
        });
    }
}
