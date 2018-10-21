package com.example.administrator.myapplication.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.net.Uri;

import com.example.administrator.myapplication.ui.viewmodel.BlankViewModel;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.ui.activity.BookReadActivity;
import com.example.administrator.myapplication.util.ConnUtil;
import com.example.administrator.myapplication.util.MemoryUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class BlankFragment extends Fragment {


    private static final String TAG = "BlankFragment";
    private BlankViewModel mViewModel;
    private Button mButton;
    private Button mFileTest;
    private ConnUtil connUtil;

    private ImageView iv;

    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public static BlankFragment newInstance() {
        return new BlankFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.blank_fragment, container, false);
        mButton = view.findViewById(R.id.mButtonTest);
        mFileTest = view.findViewById(R.id.blank_btn_fileTest);

        iv = view.findViewById(R.id.blank_iv_test);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BlankViewModel.class);

        // TODO: Use the ViewModel

       /* new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: 链接中...");
                connUtil = new ConnUtil();
                connUtil.Conn2Server();
            }
        }).start();*/

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URL picUrl = null;
                try {
                    picUrl = new URL("http://www.biquge.com.tw/files/article/image/14/14055/14055s.jpg");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                new MyAsyncTask().execute(picUrl);
            }
        });

        mFileTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = context.getExternalFilesDir(null).getPath() + "/kong1337.txt";
                File file = new File(path);
                try {
                    if (!file.exists()) {
                        file.createNewFile();
                        FileOutputStream fos = new FileOutputStream(path);
                        fos.write("你好".getBytes());
                        fos.flush();
                        fos.close();

                        Log.d(TAG, "onClick: 创建成功！");
                    } else {
                        FileOutputStream fos = new FileOutputStream(path);
                        fos.write("你好".getBytes());
                        fos.flush();
                        fos.close();
                        Log.d(TAG, "onClick: you wen jian ");
                        Toast.makeText(context, "有文件了", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    class MyAsyncTask extends AsyncTask<URL,Void,Bitmap>{
        @Override
        protected Bitmap doInBackground(URL... urls) {
            Bitmap pngBM = null;
            try {
                Log.d(TAG, "doInBackground: 正在请求网页？");
                Log.d(TAG, "doInBackground: " + urls[0]);
                pngBM = BitmapFactory.decodeStream(urls[0].openStream());
                Log.d(TAG, "doInBackground: 已经获取到图片");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return pngBM;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            iv.setImageBitmap(bitmap);
        }
    }

}
