package ldh.com.zcomic.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ldh.com.zcomic.R;
import ldh.com.zcomic.bean.ChapterBean;

/**
 * Created by allen liu on 2018/5/14.
 */

public class ChapterAdapter extends BaseAdapter {
    private List<ChapterBean> chapterList;
    private Context mContext;

    public ChapterAdapter(List<ChapterBean> chapterList,Context mContext) {
        this.chapterList = chapterList;
        this.mContext = mContext;
    }
    @Override
    public int getCount() {
        return chapterList.size();
    }

    @Override
    public Object getItem(int position) {
        return chapterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_chapter, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String title = chapterList.get(position).getTitle();
        holder.tvChapter.setText(title);
        return convertView;
    }
    class ViewHolder{
        private View itemView;
        @BindView(R.id.tv_item_chapter_name)
        TextView tvChapter;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
