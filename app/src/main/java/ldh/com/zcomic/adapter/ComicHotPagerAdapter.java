package ldh.com.zcomic.adapter;

import android.net.Uri;
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

public class ComicHotPagerAdapter extends BasePagerAdapter<ComicBean> {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comic_hot, parent, false);
        return new ComicHotHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ComicHotHolder)holder).bindView(mDataList.get(position));
    }
    class ComicHotHolder extends BaseRvHolder {
        @BindView(R.id.sdv_hot_image)
        SimpleDraweeView mSdvImage;
        @BindView(R.id.tv_hot_title)
        TextView mTvTitle;
        @BindView(R.id.tv_current)
        TextView mTvCurrent;
        @BindView(R.id.tv_popular)
        TextView mTvPopular;
        ComicHotHolder(View itemView) {
            super(itemView);
        }
        @Override
        protected void bindView(ComicBean comicBean) {
            mSdvImage.setImageURI(Uri.parse(comicBean.getImgUrl()));
            mTvTitle.setText(comicBean.getTitle());
            mTvCurrent.setText(comicBean.getCurrent());
            mTvPopular.setText("人气："+ comicBean.getPopularity());
        }
    }
}
