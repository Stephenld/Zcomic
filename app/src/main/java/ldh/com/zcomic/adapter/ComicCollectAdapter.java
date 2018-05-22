package ldh.com.zcomic.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import ldh.com.zcomic.R;
import ldh.com.zcomic.bean.ComicBean;

/**
 * Created by allen liu on 2018/5/15.
 */

public class ComicCollectAdapter extends BaseAdapter implements View.OnClickListener {

    private List<String> selected_comicId;
    private List<ComicBean> list;
    private LayoutInflater mInflater;
    private Context context;

    public ComicCollectAdapter(Context context, List<ComicBean> list) {
        this.list = list;
        this.context = context;
        mInflater = LayoutInflater.from(context);
        selected_comicId = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_collection, null);
        }
        ViewHolder holder = getViewHolder(convertView);
        ComicBean comic = list.get(position);
        holder.mIvImage.setImageURI(Uri.parse(comic.getImgUrl()));
        holder. mTvTitle.setText(comic.getTitle());
        holder.mTvAuthor.setText("作者：" + comic.getAuthor());
        holder. mTvAll.setText(comic.getCurrent());
        String desc = comic.getDesc();
        if (desc != null) {
            holder. mTvDesc.setText("简介：" + desc);
        } else {
            holder. mTvDesc.setText("简介：无");
        }
        holder.mIvCheck.setTag(position);
        holder.mIvCheck.setOnClickListener(this);
        if (selected_comicId.contains(comic.getComicId())) {
            holder.mIvCheck.setBackgroundResource(R.drawable.cart_mid_ic_check_on);
        } else {
            holder.mIvCheck.setBackgroundResource(R.drawable.cart_mid_ic_check_off);
        }
        return convertView;
    }

    private ViewHolder getViewHolder(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        return holder;
    }

    private class ViewHolder {
        private SimpleDraweeView mIvImage;
        private TextView mTvTitle, mTvAuthor, mTvAll, mTvDesc;
        private ImageView mIvCheck;
        ViewHolder(View view) {
            mIvImage = view.findViewById(R.id.item_collection_img);
            mTvTitle = view.findViewById(R.id.item_collection_title);
            mTvAuthor = view.findViewById(R.id.item_collection_author);
            mTvAll = view.findViewById(R.id.item_collection_total);
            mTvDesc = view.findViewById(R.id.item_collection_content);
            mIvCheck = view.findViewById(R.id.iv_check);
        }
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        if (v.getId() == R.id.iv_check) {
            checkAndSum(position, v);
        }
    }
    private void checkAndSum(int position, View v) {
        ComicBean comic = list.get(position);
        String objectId = comic.getComicId();
        if (selected_comicId.contains(objectId)) {
            selected_comicId.remove(objectId);
            v.setBackgroundResource(R.drawable.cart_mid_ic_check_off);
        } else {
            selected_comicId.add(objectId);
            v.setBackgroundResource(R.drawable.cart_mid_ic_check_on);
        }
    }
    public List<String> getSelected_comicId() {
        return selected_comicId;
    }
    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }
}

