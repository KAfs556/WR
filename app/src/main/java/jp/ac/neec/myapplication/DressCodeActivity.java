package jp.ac.neec.myapplication;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.IOException;

public class DressCodeActivity extends AppCompatActivity{

    private static final int RESULT_PICK_IMAGEFILE = 1000;
    private ImageView imageback;
    private ImageView imageManekin;
    private ImageView imageHat;
    private ImageView imageGlasse;
    //private ImageView imageOuter;
    private ImageView imageTops;
    private ImageView imageBottoms;
    private DialogFragment dialogFragment;
    private FragmentManager flagmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dress_code);

        imageback = (ImageView) findViewById(R.id.imageback);
        imageManekin = (ImageView) findViewById(R.id.imageView1);
        imageHat = (ImageView) findViewById(R.id.imagehat);
        imageGlasse = (ImageView) findViewById(R.id.imageglasse);
        //imageOuter = (ImageView) findViewById(R.id.imageouter);
        imageTops = (ImageView) findViewById(R.id.imagetops);
        imageBottoms = (ImageView) findViewById(R.id.imagebottoms);

        DragViewListener listener = new DragViewListener(imageHat);
        imageHat.setOnTouchListener(listener);
        DragViewListener listener2 = new DragViewListener(imageGlasse);
        imageGlasse.setOnTouchListener(listener2);
        //DragViewListener listener5 = new DragViewListener(imageOuter);
        //imageOuter.setOnTouchListener(listener5);
        DragViewListener listener3 = new DragViewListener(imageTops);
        imageTops.setOnTouchListener(listener3);
        DragViewListener listener4 = new DragViewListener(imageBottoms);
        imageBottoms.setOnTouchListener(listener4);

        findViewById(R.id.imageback_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, RESULT_PICK_IMAGEFILE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == RESULT_PICK_IMAGEFILE && resultCode == RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();

                try {
                    Bitmap bmp = getBitmapFromUri(uri);
                    imageback.setImageBitmap(bmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    public void onClickBack(View view){
        finish();
    }

    //マネキンを選択するダイアログフラグメント
    public void onClickSilhouette(View view){
        flagmentManager = getSupportFragmentManager();

        dialogFragment = new AlertDialogFragment1();
        dialogFragment.show(flagmentManager, "test alert dialog");
    }
    public void setSelection(String str) {
        if (str.equals("manekin1 clicked")) {
            imageManekin.setImageResource(R.drawable.manekin_bj);
        } else if (str.equals("manekin2 clicked")) {
            imageManekin.setImageResource(R.drawable.manekin_bk);
        }/* else if (str.equals("manekin3 clicked")) {
            imageManekin.setImageResource(R.drawable.manekin_men_bj);
        }*/
        else if(str.equals("delete")){imageManekin.setImageDrawable(null);}
    }
    public static class AlertDialogFragment1 extends DialogFragment {

        private AlertDialog dialog ;
        private AlertDialog.Builder alert;
        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            alert = new AlertDialog.Builder(getActivity());
            //alert.setTitle("マネキンを選択してください");

            // カスタムレイアウトの生成
            View alertView = getActivity().getLayoutInflater().inflate(R.layout.select_dialog_manekin, null);

            ImageView manekin1 = (ImageView) alertView.findViewById(R.id.manekin1);
            manekin1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","manekin1 clicked");
                    setMassage("manekin1 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });

            ImageView manekin2 = (ImageView) alertView.findViewById(R.id.manekin2);
            manekin2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","manekin2 clicked");
                    setMassage("manekin2 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });/*
            ImageView manekin3 = (ImageView) alertView.findViewById(R.id.manekin3);
            manekin3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","manekin3 clicked");
                    setMassage("manekin3 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });*/
            alert.setPositiveButton("マネキンを削除", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setMassage("delete");
                }
            }).setNegativeButton("キャンセル", null);

            // ViewをAlertDialog.Builderに追加
            alert.setView(alertView);

            // ViewをAlertDialog.Builderに追加
            alert.setView(alertView);

            // Dialogを生成
            dialog = alert.create();
            dialog.show();

            return dialog;
        }
        private void setMassage(String message) {
            DressCodeActivity doresscodeActivity = (DressCodeActivity) getActivity();
            doresscodeActivity.setSelection(message);
        }
    }

    //背景を選択するダイアログフラグメント
    public void onClickBackGround(View view) {
        flagmentManager = getSupportFragmentManager();
        dialogFragment = new AlertDialogFragment2();
        dialogFragment.show(flagmentManager, "test alert dialog");
    }
    public void setSelection2(String str){
        if(str.equals("back3 clicked")){imageback.setImageResource(R.drawable.back3);}
        else if(str.equals("back4 clicked")){imageback.setImageResource(R.drawable.back4);}
        else if(str.equals("back5 clicked")){imageback.setImageResource(R.drawable.back5);}
        else if(str.equals("back6 clicked")){imageback.setImageResource(R.drawable.back6);}
        else if(str.equals("delete")){imageback.setImageDrawable(null);}
    }
    public static class AlertDialogFragment2 extends DialogFragment {

        private AlertDialog dialog ;
        private AlertDialog.Builder alert;

        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            alert = new AlertDialog.Builder(getActivity());
            //alert.setTitle("マネキンを選択してください");

            // カスタムレイアウトの生成
            View alertView = getActivity().getLayoutInflater().inflate(R.layout.select_dialog_back, null);



            ImageView back3 = (ImageView) alertView.findViewById(R.id.back3);
            back3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","back3 clicked");
                    setMassage("back3 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView back4 = (ImageView) alertView.findViewById(R.id.back4);
            back4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","back4 clicked");
                    setMassage("back4 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });

            ImageView back5 = (ImageView) alertView.findViewById(R.id.back5);
            back5.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","back5 clicked");
                    setMassage("back5 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView back6 = (ImageView) alertView.findViewById(R.id.back6);
            back6.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","back6 clicked");
                    setMassage("back6 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });

            alert.setPositiveButton("背景を削除", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setMassage("delete");
                }
            }).setNegativeButton("キャンセル", null);

            // ViewをAlertDialog.Builderに追加
            alert.setView(alertView);

            // Dialogを生成
            dialog = alert.create();
            dialog.show();

            return dialog;
        }
        private void setMassage(String message) {
            DressCodeActivity doresscodeActivity = (DressCodeActivity) getActivity();
            doresscodeActivity.setSelection2(message);
        }
    }

    //帽子を選択するダイアログフラグメント
    public void onClickHat(View view){
        flagmentManager = getSupportFragmentManager();

        dialogFragment = new AlertDialogFragment3();
        dialogFragment.show(flagmentManager, "test alert dialog");
    }
    public void setSelection3(String str) {
        if (str.equals("hat1 clicked")) {
            imageHat.setImageResource(R.drawable.hat1);
        } else if (str.equals("hat2 clicked")) {
            imageHat.setImageResource(R.drawable.hat2);
        } else if (str.equals("hat3 clicked")) {
            imageHat.setImageResource(R.drawable.hat3);
        } else if (str.equals("hat4 clicked")) {
            imageHat.setImageResource(R.drawable.hat4);
        } else if (str.equals("hat5 clicked")) {
            imageHat.setImageResource(R.drawable.hat5);
        } else if (str.equals("hat6 clicked")) {
            imageHat.setImageResource(R.drawable.hat6);
        } else if (str.equals("hat7 clicked")) {
            imageHat.setImageResource(R.drawable.hat7);
        } else if (str.equals("hat8 clicked")) {
            imageHat.setImageResource(R.drawable.hat8);
        } else if (str.equals("hat9 clicked")) {
            imageHat.setImageResource(R.drawable.hat9);
        } else if (str.equals("hat10 clicked")) {
            imageHat.setImageResource(R.drawable.hat10);
        } else if (str.equals("hat11 clicked")) {
            imageHat.setImageResource(R.drawable.hat11);
        } else if (str.equals("hat12 clicked")) {
            imageHat.setImageResource(R.drawable.hat12);
        }
        else if(str.equals("delete")){imageHat.setImageDrawable(null);}
    }
    public static class AlertDialogFragment3 extends DialogFragment {

        private AlertDialog dialog ;
        private AlertDialog.Builder alert;
        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            alert = new AlertDialog.Builder(getActivity());
            //alert.setTitle("帽子を選択してください");

            // カスタムレイアウトの生成
            View alertView = getActivity().getLayoutInflater().inflate(R.layout.select_dialog_hat, null);

            ImageView hat1 = (ImageView) alertView.findViewById(R.id.hat1);
            hat1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","hat1 clicked");
                    setMassage("hat1 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });

            ImageView hat2 = (ImageView) alertView.findViewById(R.id.hat2);
            hat2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","hat2 clicked");
                    setMassage("hat2 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView hat3 = (ImageView) alertView.findViewById(R.id.hat3);
            hat3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","hat3 clicked");
                    setMassage("hat3 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView hat4 = (ImageView) alertView.findViewById(R.id.hat4);
            hat4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","hat4 clicked");
                    setMassage("hat4 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView hat5 = (ImageView) alertView.findViewById(R.id.hat5);
            hat5.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","hat5 clicked");
                    setMassage("hat5 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView hat6 = (ImageView) alertView.findViewById(R.id.hat6);
            hat6.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","hat6 clicked");
                    setMassage("hat6 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView hat7 = (ImageView) alertView.findViewById(R.id.hat7);
            hat7.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","hat7 clicked");
                    setMassage("hat7 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView hat8 = (ImageView) alertView.findViewById(R.id.hat8);
            hat8.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","hat8 clicked");
                    setMassage("hat8 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView hat9 = (ImageView) alertView.findViewById(R.id.hat9);
            hat9.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","hat9 clicked");
                    setMassage("hat9 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView hat10 = (ImageView) alertView.findViewById(R.id.hat10);
            hat10.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","hat10 clicked");
                    setMassage("hat10 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView hat11 = (ImageView) alertView.findViewById(R.id.hat11);
            hat11.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","hat11 clicked");
                    setMassage("hat11 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView hat12 = (ImageView) alertView.findViewById(R.id.hat12);
            hat12.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","hat12 clicked");
                    setMassage("hat12 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            alert.setPositiveButton("帽子を削除", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setMassage("delete");
                }
            }).setNegativeButton("キャンセル", null);

            // ViewをAlertDialog.Builderに追加
            alert.setView(alertView);

            // ViewをAlertDialog.Builderに追加
            alert.setView(alertView);

            // Dialogを生成
            dialog = alert.create();
            dialog.show();

            return dialog;
        }
        private void setMassage(String message) {
            DressCodeActivity doresscodeActivity = (DressCodeActivity) getActivity();
            doresscodeActivity.setSelection3(message);
        }
    }

    //眼鏡を選択するダイアログフラグメント
    public void onClickGlasse(View view){
        flagmentManager = getSupportFragmentManager();

        dialogFragment = new AlertDialogFragment4();
        dialogFragment.show(flagmentManager, "test alert dialog");
    }
    public void setSelection4(String str) {
        if (str.equals("glasse1 clicked")) {
            imageGlasse.setImageResource(R.drawable.glasse1);
        } else if (str.equals("glasse2 clicked")) {
            imageGlasse.setImageResource(R.drawable.glasse2);
        } else if (str.equals("glasse3 clicked")) {
            imageGlasse.setImageResource(R.drawable.glasse3);
        } else if (str.equals("glasse4 clicked")) {
            imageGlasse.setImageResource(R.drawable.glasse4);
        } else if (str.equals("glasse5 clicked")) {
            imageGlasse.setImageResource(R.drawable.glasse5);
        } else if (str.equals("glasse6 clicked")) {
            imageGlasse.setImageResource(R.drawable.glasse6);
        } else if (str.equals("glasse7 clicked")) {
            imageGlasse.setImageResource(R.drawable.glasse7);
        } else if (str.equals("glasse8 clicked")) {
            imageGlasse.setImageResource(R.drawable.glasse8);
        } else if (str.equals("glasse9 clicked")) {
            imageGlasse.setImageResource(R.drawable.glasse9);
        } else if (str.equals("glasse10 clicked")) {
            imageGlasse.setImageResource(R.drawable.glasse10);
        } else if (str.equals("glasse11 clicked")) {
            imageGlasse.setImageResource(R.drawable.glasse11);
        } else if (str.equals("glasse12 clicked")) {
            imageGlasse.setImageResource(R.drawable.glasse12);
        }
        else if(str.equals("delete")){imageGlasse.setImageDrawable(null);}
    }
    public static class AlertDialogFragment4 extends DialogFragment {

        private AlertDialog dialog ;
        private AlertDialog.Builder alert;
        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            alert = new AlertDialog.Builder(getActivity());
            //alert.setTitle("眼鏡を選択してください");

            // カスタムレイアウトの生成
            View alertView = getActivity().getLayoutInflater().inflate(R.layout.select_dialog_glasse, null);

            ImageView glasse1 = (ImageView) alertView.findViewById(R.id.glasse1);
            glasse1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","glasse1 clicked");
                    setMassage("glasse1 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });

            ImageView glasse2 = (ImageView) alertView.findViewById(R.id.glasse2);
            glasse2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","glasse2 clicked");
                    setMassage("glasse2 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView glasse3 = (ImageView) alertView.findViewById(R.id.glasse3);
            glasse3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","glasse3 clicked");
                    setMassage("glasse3 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView glasse4 = (ImageView) alertView.findViewById(R.id.glasse4);
            glasse4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","glasse4 clicked");
                    setMassage("glasse4 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView glasse5 = (ImageView) alertView.findViewById(R.id.glasse5);
            glasse5.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","glasse5 clicked");
                    setMassage("glasse5 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView glasse6 = (ImageView) alertView.findViewById(R.id.glasse6);
            glasse6.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","glasse6 clicked");
                    setMassage("glasse6 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView glasse7 = (ImageView) alertView.findViewById(R.id.glasse7);
            glasse7.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","glasse7 clicked");
                    setMassage("glasse7 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });

            ImageView glasse8 = (ImageView) alertView.findViewById(R.id.glasse8);
            glasse8.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","glasse8 clicked");
                    setMassage("glasse8 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView glasse9 = (ImageView) alertView.findViewById(R.id.glasse9);
            glasse9.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","glasse9 clicked");
                    setMassage("glasse9 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView glasse10 = (ImageView) alertView.findViewById(R.id.glasse10);
            glasse10.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","glasse10 clicked");
                    setMassage("glasse10 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView glasse11 = (ImageView) alertView.findViewById(R.id.glasse11);
            glasse11.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","glasse11 clicked");
                    setMassage("glasse11 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView glasse12 = (ImageView) alertView.findViewById(R.id.glasse12);
            glasse12.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","glasse12 clicked");
                    setMassage("glasse12 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            alert.setPositiveButton("眼鏡を削除", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setMassage("delete");
                }
            }).setNegativeButton("キャンセル", null);

            // ViewをAlertDialog.Builderに追加
            alert.setView(alertView);

            // ViewをAlertDialog.Builderに追加
            alert.setView(alertView);

            // Dialogを生成
            dialog = alert.create();
            dialog.show();

            return dialog;
        }
        private void setMassage(String message) {
            DressCodeActivity doresscodeActivity = (DressCodeActivity) getActivity();
            doresscodeActivity.setSelection4(message);
        }
    }
/*
    //ドレス、アウターを選択するダイアログフラグメント
    public void onClickOuter(View view){
        flagmentManager = getSupportFragmentManager();

        dialogFragment = new AlertDialogFragment7();
        dialogFragment.show(flagmentManager, "test alert dialog");
    }
    public void setSelection7(String str) {
        if (str.equals("outer1 clicked")) {
            imageOuter.setImageResource(R.drawable.outer1);
        } else if (str.equals("outer2 clicked")) {
            imageOuter.setImageResource(R.drawable.outer2);
        } else if (str.equals("outer3 clicked")) {
            imageOuter.setImageResource(R.drawable.outer3);
        }
        else if(str.equals("delete")){imageOuter.setImageDrawable(null);}
    }
    public static class AlertDialogFragment7 extends DialogFragment {

        private AlertDialog dialog ;
        private AlertDialog.Builder alert;
        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            alert = new AlertDialog.Builder(getActivity());
            //alert.setTitle("アウターを選択してください");

            // カスタムレイアウトの生成
            View alertView = getActivity().getLayoutInflater().inflate(R.layout.select_dialog_outer, null);

            ImageView outer1 = (ImageView) alertView.findViewById(R.id.outer1);
            outer1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","outer1 clicked");
                    setMassage("outer1 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });

            ImageView outer2 = (ImageView) alertView.findViewById(R.id.outer2);
            outer2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","outer2 clicked");
                    setMassage("outer2 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView outer3 = (ImageView) alertView.findViewById(R.id.outer3);
            outer3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","outer3 clicked");
                    setMassage("outer3 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            alert.setPositiveButton("帽子を削除", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setMassage("delete");
                }
            }).setNegativeButton("キャンセル", null);

            // ViewをAlertDialog.Builderに追加
            alert.setView(alertView);

            // ViewをAlertDialog.Builderに追加
            alert.setView(alertView);

            // Dialogを生成
            dialog = alert.create();
            dialog.show();

            return dialog;
        }
        private void setMassage(String message) {
            DressCodeActivity doresscodeActivity = (DressCodeActivity) getActivity();
            doresscodeActivity.setSelection7(message);
        }
    }
*/
    //トップスを選択するダイアログフラグメント
    public void onClickTops(View view){
        flagmentManager = getSupportFragmentManager();

        dialogFragment = new AlertDialogFragment5();
        dialogFragment.show(flagmentManager, "test alert dialog");
    }
    public void setSelection5(String str) {
        if (str.equals("tops1 clicked")) {
            imageTops.setImageResource(R.drawable.tops1);
        } else if (str.equals("tops2 clicked")) {
            imageTops.setImageResource(R.drawable.tops2);
        } else if (str.equals("tops3 clicked")) {
            imageTops.setImageResource(R.drawable.tops3);
        } else if (str.equals("tops4 clicked")) {
            imageTops.setImageResource(R.drawable.tops4);
        } else if (str.equals("tops5 clicked")) {
            imageTops.setImageResource(R.drawable.tops5);
        } else if (str.equals("tops6 clicked")) {
            imageTops.setImageResource(R.drawable.tops6);
        } else if (str.equals("tops7 clicked")) {
            imageTops.setImageResource(R.drawable.tops7);
        } else if (str.equals("tops8 clicked")) {
            imageTops.setImageResource(R.drawable.tops8);
        } else if (str.equals("tops9 clicked")) {
            imageTops.setImageResource(R.drawable.tops9);
        } else if (str.equals("tops10 clicked")) {
            imageTops.setImageResource(R.drawable.tops10);
        } else if (str.equals("tops11 clicked")) {
            imageTops.setImageResource(R.drawable.tops11);
        } else if (str.equals("tops12 clicked")) {
            imageTops.setImageResource(R.drawable.tops12);
        } else if (str.equals("tops13 clicked")) {
            imageTops.setImageResource(R.drawable.tops13);
        } else if (str.equals("tops14 clicked")) {
            imageTops.setImageResource(R.drawable.tops14);
        } else if (str.equals("tops15 clicked")) {
            imageTops.setImageResource(R.drawable.tops15);
        } else if (str.equals("tops16 clicked")) {
            imageTops.setImageResource(R.drawable.tops16);
        } else if (str.equals("tops17 clicked")) {
            imageTops.setImageResource(R.drawable.tops17);
        } else if (str.equals("tops18 clicked")) {
            imageTops.setImageResource(R.drawable.tops18);
        } else if (str.equals("tops19 clicked")) {
            imageTops.setImageResource(R.drawable.tops19);
        } else if (str.equals("tops20 clicked")) {
            imageTops.setImageResource(R.drawable.tops20);
        }/* else if (str.equals("tops21 clicked")) {
            imageTops.setImageResource(R.drawable.tops21);
        } else if (str.equals("tops22 clicked")) {
            imageTops.setImageResource(R.drawable.tops22);
        } else if (str.equals("tops23 clicked")) {
            imageTops.setImageResource(R.drawable.tops23);
        } else if (str.equals("tops24 clicked")) {
            imageTops.setImageResource(R.drawable.tops24);
        }*/
        else if(str.equals("delete")){imageTops.setImageDrawable(null);}
    }
    public static class AlertDialogFragment5 extends DialogFragment {

        private AlertDialog dialog ;
        private AlertDialog.Builder alert;
        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            alert = new AlertDialog.Builder(getActivity());
            //alert.setTitle("トップスを選択してください");

            // カスタムレイアウトの生成
            View alertView = getActivity().getLayoutInflater().inflate(R.layout.select_dialog_tops, null);

            ImageView tops1 = (ImageView) alertView.findViewById(R.id.tops1);
            tops1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","tops1 clicked");
                    setMassage("tops1 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });

            ImageView tops2 = (ImageView) alertView.findViewById(R.id.tops2);
            tops2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","tops2 clicked");
                    setMassage("tops2 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView tops3 = (ImageView) alertView.findViewById(R.id.tops3);
            tops3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","tops3 clicked");
                    setMassage("tops3 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView tops4 = (ImageView) alertView.findViewById(R.id.tops4);
            tops4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","tops4 clicked");
                    setMassage("tops4 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView tops5 = (ImageView) alertView.findViewById(R.id.tops5);
            tops5.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","tops5 clicked");
                    setMassage("tops5 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView tops6 = (ImageView) alertView.findViewById(R.id.tops6);
            tops6.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","tops6 clicked");
                    setMassage("tops6 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView tops7 = (ImageView) alertView.findViewById(R.id.tops7);
            tops7.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","tops7 clicked");
                    setMassage("tops7 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView tops8 = (ImageView) alertView.findViewById(R.id.tops8);
            tops8.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","tops8 clicked");
                    setMassage("tops8 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView tops9 = (ImageView) alertView.findViewById(R.id.tops9);
            tops9.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","tops9 clicked");
                    setMassage("tops9 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView tops10 = (ImageView) alertView.findViewById(R.id.tops10);
            tops10.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","tops10 clicked");
                    setMassage("tops10 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView tops11 = (ImageView) alertView.findViewById(R.id.tops11);
            tops11.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","tops11 clicked");
                    setMassage("tops11 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView tops12 = (ImageView) alertView.findViewById(R.id.tops12);
            tops12.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","tops12 clicked");
                    setMassage("tops12 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView tops13 = (ImageView) alertView.findViewById(R.id.tops13);
            tops13.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","tops13 clicked");
                    setMassage("tops13 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView tops14 = (ImageView) alertView.findViewById(R.id.tops14);
            tops14.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","tops14 clicked");
                    setMassage("tops14 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView tops15 = (ImageView) alertView.findViewById(R.id.tops15);
            tops15.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","tops15 clicked");
                    setMassage("tops15 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView tops16 = (ImageView) alertView.findViewById(R.id.tops16);
            tops16.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","tops16 clicked");
                    setMassage("tops16 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView tops17 = (ImageView) alertView.findViewById(R.id.tops17);
            tops17.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","tops17 clicked");
                    setMassage("tops17 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView tops18 = (ImageView) alertView.findViewById(R.id.tops18);
            tops18.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","tops18 clicked");
                    setMassage("tops18 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView tops19 = (ImageView) alertView.findViewById(R.id.tops19);
            tops19.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","tops19 clicked");
                    setMassage("tops19 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView tops20 = (ImageView) alertView.findViewById(R.id.tops20);
            tops20.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","tops20 clicked");
                    setMassage("tops20 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });/*
            ImageView tops21 = (ImageView) alertView.findViewById(R.id.tops21);
            tops21.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","tops21 clicked");
                    setMassage("tops21 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView tops22 = (ImageView) alertView.findViewById(R.id.tops22);
            tops22.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","tops22 clicked");
                    setMassage("tops22 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView tops23 = (ImageView) alertView.findViewById(R.id.tops23);
            tops23.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","tops23 clicked");
                    setMassage("tops23 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView tops24 = (ImageView) alertView.findViewById(R.id.tops24);
            tops24.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","tops24 clicked");
                    setMassage("tops24 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });*/
            alert.setPositiveButton("トップスを削除", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setMassage("delete");
                }
            }).setNegativeButton("キャンセル", null);

            // ViewをAlertDialog.Builderに追加
            alert.setView(alertView);

            // ViewをAlertDialog.Builderに追加
            alert.setView(alertView);

            // Dialogを生成
            dialog = alert.create();
            dialog.show();

            return dialog;
        }
        private void setMassage(String message) {
            DressCodeActivity doresscodeActivity = (DressCodeActivity) getActivity();
            doresscodeActivity.setSelection5(message);
        }
    }

    //ボトムを選択するダイアログフラグメント
    public void onClickBottoms(View view){
        flagmentManager = getSupportFragmentManager();

        dialogFragment = new AlertDialogFragment6();
        dialogFragment.show(flagmentManager, "test alert dialog");
    }
    public void setSelection6(String str) {
        if (str.equals("bottoms1 clicked")) {
            imageBottoms.setImageResource(R.drawable.bottoms1);
        } else if (str.equals("bottoms2 clicked")) {
            imageBottoms.setImageResource(R.drawable.bottoms2);
        } else if (str.equals("bottoms3 clicked")) {
            imageBottoms.setImageResource(R.drawable.bottoms3);
        } else if (str.equals("bottoms4 clicked")) {
            imageBottoms.setImageResource(R.drawable.bottoms4);
        } else if (str.equals("bottoms5 clicked")) {
            imageBottoms.setImageResource(R.drawable.bottoms5);
        } else if (str.equals("bottoms6 clicked")) {
            imageBottoms.setImageResource(R.drawable.bottoms6);
        } else if (str.equals("bottoms7 clicked")) {
            imageBottoms.setImageResource(R.drawable.bottoms7);
        } else if (str.equals("bottoms8 clicked")) {
            imageBottoms.setImageResource(R.drawable.bottoms8);
        } else if (str.equals("bottoms9 clicked")) {
            imageBottoms.setImageResource(R.drawable.bottoms9);
        } else if (str.equals("bottoms10 clicked")) {
            imageBottoms.setImageResource(R.drawable.bottoms10);
        } else if (str.equals("bottoms11 clicked")) {
            imageBottoms.setImageResource(R.drawable.bottoms11);
        } else if (str.equals("bottoms12 clicked")) {
            imageBottoms.setImageResource(R.drawable.bottoms12);
        } else if (str.equals("bottoms13 clicked")) {
            imageBottoms.setImageResource(R.drawable.bottoms13);
        } else if (str.equals("bottoms14 clicked")) {
            imageBottoms.setImageResource(R.drawable.bottoms14);
        } else if (str.equals("bottoms15 clicked")) {
            imageBottoms.setImageResource(R.drawable.bottoms15);
        } else if (str.equals("bottoms16 clicked")) {
            imageBottoms.setImageResource(R.drawable.bottoms16);
        } else if (str.equals("bottoms17 clicked")) {
            imageBottoms.setImageResource(R.drawable.bottoms17);
        } else if (str.equals("bottoms18 clicked")) {
            imageBottoms.setImageResource(R.drawable.bottoms18);
        } else if (str.equals("bottoms19 clicked")) {
            imageBottoms.setImageResource(R.drawable.bottoms19);
        } else if (str.equals("bottoms20 clicked")) {
            imageBottoms.setImageResource(R.drawable.bottoms20);
        }
        else if(str.equals("delete")){imageBottoms.setImageDrawable(null);}
    }
    public static class AlertDialogFragment6 extends DialogFragment {

        private AlertDialog dialog ;
        private AlertDialog.Builder alert;
        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            alert = new AlertDialog.Builder(getActivity());
            //alert.setTitle("ボトムを選択してください");

            // カスタムレイアウトの生成
            View alertView = getActivity().getLayoutInflater().inflate(R.layout.select_dialog_bottoms, null);

            ImageView bottoms1 = (ImageView) alertView.findViewById(R.id.bottoms1);
            bottoms1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","bottoms1 clicked");
                    setMassage("bottoms1 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });

            ImageView bottoms2 = (ImageView) alertView.findViewById(R.id.bottoms2);
            bottoms2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","bottoms2 clicked");
                    setMassage("bottoms2 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView bottoms3 = (ImageView) alertView.findViewById(R.id.bottoms3);
            bottoms3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","bottoms3 clicked");
                    setMassage("bottoms3 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView bottoms4 = (ImageView) alertView.findViewById(R.id.bottoms4);
            bottoms4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","bottoms4 clicked");
                    setMassage("bottoms4 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView bottoms5 = (ImageView) alertView.findViewById(R.id.bottoms5);
            bottoms5.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","bottoms5 clicked");
                    setMassage("bottoms5 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView bottoms6 = (ImageView) alertView.findViewById(R.id.bottoms6);
            bottoms6.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","bottoms6 clicked");
                    setMassage("bottoms6 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView bottoms7 = (ImageView) alertView.findViewById(R.id.bottoms7);
            bottoms7.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","bottoms7 clicked");
                    setMassage("bottoms7 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView bottoms8 = (ImageView) alertView.findViewById(R.id.bottoms8);
            bottoms8.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","bottoms8 clicked");
                    setMassage("bottoms8 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView bottoms9 = (ImageView) alertView.findViewById(R.id.bottoms9);
            bottoms9.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","bottoms9 clicked");
                    setMassage("bottoms9 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView bottoms10 = (ImageView) alertView.findViewById(R.id.bottoms10);
            bottoms10.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","bottoms10 clicked");
                    setMassage("bottoms10 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView bottoms11 = (ImageView) alertView.findViewById(R.id.bottoms11);
            bottoms11.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","bottoms11 clicked");
                    setMassage("bottoms11 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView bottoms12 = (ImageView) alertView.findViewById(R.id.bottoms12);
            bottoms12.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","bottoms12 clicked");
                    setMassage("bottoms12 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView bottoms13 = (ImageView) alertView.findViewById(R.id.bottoms13);
            bottoms13.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","bottoms13 clicked");
                    setMassage("bottoms13 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView bottoms14 = (ImageView) alertView.findViewById(R.id.bottoms14);
            bottoms14.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","bottoms14 clicked");
                    setMassage("bottoms14 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView bottoms15 = (ImageView) alertView.findViewById(R.id.bottoms15);
            bottoms15.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","bottoms15 clicked");
                    setMassage("bottoms15 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView bottoms16 = (ImageView) alertView.findViewById(R.id.bottoms16);
            bottoms16.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","bottoms16 clicked");
                    setMassage("bottoms16 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView bottoms17 = (ImageView) alertView.findViewById(R.id.bottoms17);
            bottoms17.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","bottoms17 clicked");
                    setMassage("bottoms17 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView bottoms18 = (ImageView) alertView.findViewById(R.id.bottoms18);
            bottoms18.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","bottoms18 clicked");
                    setMassage("bottoms18 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView bottoms19 = (ImageView) alertView.findViewById(R.id.bottoms19);
            bottoms19.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","bottoms19 clicked");
                    setMassage("bottoms19 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            ImageView bottoms20 = (ImageView) alertView.findViewById(R.id.bottoms20);
            bottoms20.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("debug","bottoms20 clicked");
                    setMassage("bottoms20 clicked");
                    // Dialogを消す
                    getDialog().dismiss();
                }
            });
            alert.setPositiveButton("ボトムを削除", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    setMassage("delete");
                }
            }).setNegativeButton("キャンセル", null);

            // ViewをAlertDialog.Builderに追加
            alert.setView(alertView);

            // ViewをAlertDialog.Builderに追加
            alert.setView(alertView);

            // Dialogを生成
            dialog = alert.create();
            dialog.show();

            return dialog;
        }
        private void setMassage(String message) {
            DressCodeActivity doresscodeActivity = (DressCodeActivity) getActivity();
            doresscodeActivity.setSelection6(message);
        }
    }
}
