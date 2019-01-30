package jp.ac.neec.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class FragmentOtherFav extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_other_fav, container, false);

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