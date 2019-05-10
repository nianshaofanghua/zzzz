package com.example.administrator.zzzz;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2019/4/29.
 */

public class RvAdapter extends BaseQuickAdapter<RvModel> {
    private Context context;


    public RvAdapter(int layoutResId, List<RvModel> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, RvModel optionAnswer) {
        final int position = baseViewHolder.getLayoutPosition() - getHeaderLayoutCount();
        ImageView iv_pic = baseViewHolder.getView(R.id.iv_pic);
        TextView tv_option = baseViewHolder.getView(R.id.tv_title);
        Glide.with(mContext).load(optionAnswer.getPicUrl()).into(iv_pic);
        tv_option.setText(optionAnswer.getTitle());
    }
}
