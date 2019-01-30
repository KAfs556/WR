package jp.ac.neec.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import static android.app.Activity.RESULT_OK;

public class FragmentMypageCode extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mypage_code, container, false);

        //コーデ登録ボタン作成
        Button button_code = (Button) view.findViewById(R.id.button);
        button_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PicAppDateActivity.class);
                startActivity(intent);
            }
        });

        GridView gridview = (GridView) view.findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapterMyPage(getActivity()));

        //グリッドビューアイテム押下時
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