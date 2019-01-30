package jp.ac.neec.myapplication;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by rakuishi on 6/22/14.
 */
public class HueAdapter_Search extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    public String[] mHueArray = {
            "KURANOSUKE", "sara", "まっちゃん", "たり",
            "おさむ", "takada", "あたらし", "上地","神山"
    };
    public String[] mHueSinArray = {
            "172cm", "155cm","168cm", "180cm",
            "178cm", "149cm", "171cm", "193cm",
            "168cm"
    };
    public Integer[] mHueIdArray = {
            R.drawable.sample14, R.drawable.sample1,
            R.drawable.sample2, R.drawable.sample3,
            R.drawable.sample4, R.drawable.sample5,
            R.drawable.sample6, R.drawable.sample7,
            R.drawable.sample8,
    };
    public Integer[] mHueIdThamArray = {
            R.drawable.tham_sample14, R.drawable.tham_sample1,
            R.drawable.tham_sample2, R.drawable.tham_sample3,
            R.drawable.tham_sample4, R.drawable.tham_sample5,
            R.drawable.tham_sample6, R.drawable.tham_sample7,
            R.drawable.tham_sample8,
    };
    public Integer[] mHueIcArray = {
            R.drawable.icon_1, R.drawable.icon_8,
            R.drawable.icon_2, R.drawable.icon_6,
            R.drawable.icon_4, R.drawable.icon_3,
            R.drawable.icon_5, R.drawable.icon_7,
            R.drawable.icon_8,
    };
    public String[] mHueComeArray = {
            "0","3","11","9",
            "13","2","5","1","5"
    };
    public int[] mHueFavArray = {
            316,529,109,628,
            232,328,13,223,12
    };

    private static class ViewHolder {
        public ImageView hueImageView;
        public TextView  hueTextView;
    }

    public HueAdapter_Search(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return mHueArray.length;
    }

    public Object getItem(int position) {
        return mHueArray[position];
    }

    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.grid_item_hue_search, null);
            holder = new ViewHolder();
            holder.hueImageView = (ImageView)convertView.findViewById(R.id.hue_imageview);
            holder.hueTextView = (TextView)convertView.findViewById(R.id.hue_textview);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.hueImageView.setImageResource(mHueIdThamArray[position]);
        holder.hueTextView.setText(mHueArray[position]);

        return convertView;
    }
}