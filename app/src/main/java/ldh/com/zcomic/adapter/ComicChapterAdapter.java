package ldh.com.zcomic.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import ldh.com.zcomic.R;
import ldh.com.zcomic.bean.ChapterBean;

/**
 * Created by allen liu on 2018/5/14.
 */

public class ComicChapterAdapter extends BasePagerAdapter<ChapterBean> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter, parent, false);
        return new ComicHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ComicHolder)holder).bindView(mDataList.get(position));
    }

    class ComicHolder extends BaseRvHolder{
        @BindView(R.id.tv_item_chapter_name)
        TextView mTvChapter;

        ComicHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView(ChapterBean chapterBean) {
            mTvChapter.setText(chapterBean.getTitle());
        }
    }
}
