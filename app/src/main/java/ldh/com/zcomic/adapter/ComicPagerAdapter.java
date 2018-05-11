package ldh.com.zcomic.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import ldh.com.zcomic.R;
import ldh.com.zcomic.bean.ComicBean;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by allen liu on 2018/5/8.
 */

public class ComicPagerAdapter extends BasePagerAdapter<ComicBean> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.classify_comic__pager, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder)holder).bindView(mDataList.get(position));
    }

    class MyViewHolder extends BaseRvHolder {
        @BindView(R.id.iv_comic)
         ImageView iv_comic;
        @BindView(R.id.tv_update)
        TextView tv_update;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_desc)
        TextView tv_desc;
        public MyViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(ComicBean comicBean) {
            Glide.with(getApplicationContext()).load(comicBean.getImgUrl()).into(iv_comic);
            //这里用全局的Context(),否则闪退报错：You cannot start a load on a not yet attached View or a Fragment where getActivity() returns null
            // (which usually occurs when getActivity() is called before the Fragment is attached or after the Fragment is destroyed).
           tv_title.setText(comicBean.getTitle());
           tv_update.setText(comicBean.getCurrent());
            if (comicBean.getDesc() != null){
                tv_desc.setText(comicBean.getDesc());
            }else {
                tv_desc.setVisibility(View.GONE);
            }
        }
    }
}
