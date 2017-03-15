package com.tcs.sinanews.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.tcs.sinanews.R;
import com.tcs.sinanews.bean.NewsList;
import com.tcs.sinanews.ui.viewHolder.BannerViewHolder;
import com.tcs.sinanews.ui.viewHolder.NetworkImageHolderView;
import com.tcs.sinanews.ui.viewHolder.NewsViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzypf on 2017/3/15.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnItemClickListener
{
    private int BANNER_TYPE = 0;
    private int NORMAL_TYPE = 1;
    //头部固定为5张图片
    private static final int NUM_IMAGE = 5;
    private Context mContext;
    private List<NewsList> mNewsLists;
    //是否下来刷新
    private boolean refresh=false;
    private LayoutInflater mInflater;
    private IBannerClick mClick;
    public NewsAdapter(Context context, List<NewsList> news,IBannerClick bannerClick) {
        mContext = context;
        mNewsLists = news;
        mClick = bannerClick;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == NORMAL_TYPE) {
            view = mInflater.inflate(R.layout.item_news, null);
            return new NewsViewHolder(view);
        }
        if (viewType == BANNER_TYPE) {
            view = mInflater.inflate(R.layout.itembanner, null);
            return new BannerViewHolder(view);
        }
        throw new RuntimeException("没有对应类型哦");

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsViewHolder) {
            NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
            //由于header放置了一个图片轮播器占据了第0,1,2.3,4   5个位置，所以position从5开始
            NewsList newsList = mNewsLists.get(position + NUM_IMAGE - 1);
            newsViewHolder.mSimpleDraweeView.setImageURI(newsList.getPicUrl());
            newsViewHolder.mMTv.setText(newsList.getTitle());
        } else if (holder instanceof BannerViewHolder) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            NewsList newsList = mNewsLists.get(position);
            List<NewsList> mBannerList = new ArrayList<>();
            mBannerList.addAll(mNewsLists.subList(0,NUM_IMAGE));

                bannerViewHolder.mBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, mBannerList).setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                        .setOnItemClickListener(this);

                mClick.getBanner(bannerViewHolder.mBanner);
                refresh =true;

        }
    }

    @Override
    public int getItemCount() {
        return mNewsLists.size() - NUM_IMAGE + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position ==0)
            return BANNER_TYPE;
        else
            return NORMAL_TYPE;
    }

    @Override
    public void onItemClick(int position) {
        mClick.onBannerClick(position);
    }

    public void replaceAll(List<NewsList> items) {
        mNewsLists.clear();
        mNewsLists.addAll(items);
        notifyDataSetChanged();
    }
}
