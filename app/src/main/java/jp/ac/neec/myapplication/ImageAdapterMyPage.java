package jp.ac.neec.myapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapterMyPage extends BaseAdapter {
    private Context mContext;

    // Constructor
    public ImageAdapterMyPage(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(405,500));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(4, 4, 4, 4);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumThambIds[position]);
        return imageView;
    }

    public Integer[] mThumbIds = {
            R.drawable.sample0, R.drawable.sample1,
            R.drawable.sample2, R.drawable.sample3,
            R.drawable.sample4, R.drawable.sample5,
            R.drawable.sample6, R.drawable.sample7,
            R.drawable.sample8, R.drawable.sample9,
            R.drawable.sample10, R.drawable.sample11
    };
    public Integer[] mThumThambIds = {
            R.drawable.tham_sample0, R.drawable.tham_sample1,
            R.drawable.tham_sample2, R.drawable.tham_sample3,
            R.drawable.tham_sample4, R.drawable.tham_sample5,
            R.drawable.tham_sample6, R.drawable.tham_sample7,
            R.drawable.tham_sample8, R.drawable.tham_sample9,
            R.drawable.tham_sample10, R.drawable.tham_sample11
    };
}