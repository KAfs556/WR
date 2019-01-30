package jp.ac.neec.myapplication;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.preference.PreferenceActivity;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PreferencePicSelect extends PreferenceActivity {
    private static final int SELECT_PICTURE = 1;

    //アイコンをタップ時に画像の変更を行う
    protected void onClick() {
        Intent pickPhotoIntent = new Intent()
                .setType("image/*")
                .setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(pickPhotoIntent,
                "Select Picture"), SELECT_PICTURE);
    }

    //選択した画像をセット
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Bitmap image = null;
                if (data.getExtras() != null && data.getExtras().get("data") != null) {
                    image = (Bitmap) data.getExtras().get("data");
                    //mImageView.setImageBitmap(image);
                } else {
                    try {
                        InputStream stream = getContentResolver().openInputStream(data.getData());
                        image = BitmapFactory.decodeStream(stream);
                       // mImageView.setImageBitmap(image);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}