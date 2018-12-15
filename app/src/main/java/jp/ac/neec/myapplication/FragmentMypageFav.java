package jp.ac.neec.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

public class FragmentMypageFav extends Fragment {

    private static final int SELECT_PICTURE = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mypage_fav, container, false);

        //お気に入り編集ボタンを作成
        Button button_code = (Button) view.findViewById(R.id.button_fav);
        button_code.setOnClickListener(new View.OnClickListener() {
            //写真選択画面へ遷移
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FavEditActivity.class);
                startActivity(intent);
            }
        });

        GridView gridview = (GridView) view.findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapterMyPage(getActivity()));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                // Send intent to SingleViewActivity_Timeline
                Intent intent = new Intent(getActivity(), SingleViewActivity_Timeline.class);
                // Pass image index
                intent.putExtra("id", position);
                startActivity(intent);
            }
        });
        return view;
    }
}