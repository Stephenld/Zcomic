package ldh.com.zcomic.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import ldh.com.zcomic.R;
import ldh.com.zcomic.bean.ComicBean;

/**
 * Created by allen liu on 2018/5/12.
 */

public class ComicNewsPagerAdapter extends  BasePagerAdapter<ComicBean> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comic_news, parent, false);
        return new ComicNewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ComicNewsHolder)holder).bindView(mDataList.get(position));
    }
    class ComicNewsHolder extends BaseRvHolder {
        @BindView(R.id.sdv_image)
        SimpleDraweeView mSdvImage;
        @BindView(R.id.tv_comic_title)
        TextView mTvTitle;
        @BindView(R.id.tv_comic_desc)
        TextView mTvDesc;
        @BindView(R.id.tv_comic_time)
        TextView mTvCurrent;
        ComicNewsHolder(View itemView) {
            super(itemView);
        }
        @Override
        protected void bindView(ComicBean comicBean) {
            mSdvImage.setImageURI(comicBean.getImgUrl());
            mTvTitle.setText(comicBean.getTitle());
            mTvCurrent.setText(comicBean.getCurrent());
            mTvDesc.setText(comicBean.getDesc());
        }
    }
}
