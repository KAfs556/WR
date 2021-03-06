package jp.ac.neec.myapplication;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by rakuishi on 6/22/14.
 */
public class HueAdapter_TimeLine2 extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    public String[] mHueArray = {
            "KURANOSUKE", "sara", "まっちゃん", "たり",
            "おさむ", "takada", "あたらし", "上地"
    };
    public Integer[] mHueIdArray = {
            R.drawable.sample0, R.drawable.sample1,
            R.drawable.sample2, R.drawable.sample3,
            R.drawable.sample4, R.drawable.sample5,
            R.drawable.sample6, R.drawable.sample7,
    };
    public Integer[] mHueIdThamArray = {
            R.drawable.tham_sample0, R.drawable.tham_sample1,
            R.drawable.tham_sample2, R.drawable.tham_sample3,
            R.drawable.tham_sample4, R.drawable.tham_sample5,
            R.drawable.tham_sample6, R.drawable.tham_sample7,
    };
    public Integer[] mHueIcArray = {
            R.drawable.icon_1, R.drawable.icon_8,
            R.drawable.icon_2, R.drawable.icon_6,
            R.drawable.icon_4, R.drawable.icon_3,
            R.drawable.icon_5, R.drawable.icon_7,
    };
    private static class ViewHolder {
        public ImageView hueImageView;
        public TextView  hueTextView;
        public ImageView hueIconView;
        public Button hueFavView;
    }
    public int[] mHueFavArray = {
            316,529,109,628,
            232,328,13,223
    };
    public String[] mHueSinArray = {
            "172cm", "155cm","168cm", "180cm",
            "178cm", "149cm", "171cm", "193cm"
    };
    public String[] mHueComeArray = {
            "0","3","11","9",
            "13","2","5","1"
    };

    public HueAdapter_TimeLine2(Context context) {
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
            convertView = mLayoutInflater.inflate(R.layout.grid_item_hue_timeline2, null);
            holder = new ViewHolder();
            holder.hueImageView = (ImageView)convertView.findViewById(R.id.hue_imageview);
            holder.hueTextView = (TextView)convertView.findViewById(R.id.hue_textview);
            holder.hueIconView = (ImageView)convertView.findViewById(R.id.hue_circle_imageview);
            holder.hueFavView = (Button)convertView.findViewById(R.id.button2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.hueImageView.setImageResource(mHueIdArray[position]);
        holder.hueTextView.setText(mHueArray[position]);
        holder.hueIconView.setImageResource(mHueIcArray[position]);
        holder.hueFavView.setText(String.valueOf(mHueFavArray[position]));

        return convertView;
    }
}