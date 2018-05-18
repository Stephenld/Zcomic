package ldh.com.zcomic.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import ldh.com.zcomic.R;

/**
 * Created by allen liu on 2018/5/14.
 */

public class ComicViewAdapter extends BasePagerAdapter<String>{
    private OnClickHolderInside mOnClickHolderInside;
     interface OnClickHolderInside {
        void onClicked(int position);
    }
    public void setOnClickHolderInside(OnClickHolderInside onClickHolderInside){
        mOnClickHolderInside = onClickHolderInside;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comic_view, parent, false);
        return new ComicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ComicViewHolder)holder).bindView(mDataList.get(position));
    }

    class ComicViewHolder extends BaseRvHolder{
        @BindView(R.id.sdv_pic)
        SimpleDraweeView mSdvPic;

        ComicViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(String s) {
            mSdvPic.setImageURI(Uri.parse(s));
            mSdvPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickHolderInside.onClicked(getLayoutPosition());
                }
            });
        }
    }
}
