package com.tcs.sinanews.ui.viewHolder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tcs.sinanews.R;
import com.tcs.sinanews.bean.NewsList;

/**
 * Created by hzypf on 2017/3/14.
 */

public class NetworkImageHolderView implements Holder<NewsList> {
    private SimpleDraweeView imageView;
    @Override
    public View createView(Context context) {
        imageView = new SimpleDraweeView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, int position, NewsList data) {
        imageView.setImageResource(R.mipmap.ic_default_adimage);
        imageView.setImageURI(data.getPicUrl());
    }

}
