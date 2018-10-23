package com.example.administrator.myapplication.ui.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.bean.Constant;
import com.example.administrator.myapplication.bean.Novel;
import com.example.administrator.myapplication.util.SpiderUtil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BookDetailFragment extends Fragment {

    private static final String TAG = "BookDetailFragment";

    public static final String ARG_ITEM_ID = "3";
    private Novel novel;

    @BindView(R.id.detail_fragment_author)
    TextView author;
    @BindView(R.id.detail_fragment_introduce)
    TextView introduce;
    @BindView(R.id.detail_fragment_state)
    TextView statement;
    @BindView(R.id.detail_fragment_time)
    TextView time;
    @BindView(R.id.detail_fragment_chapter)
    TextView chapter;
    @BindView(R.id.detail_fragment_name)
    TextView bookName;

    public BookDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            novel = (Novel) getArguments().getSerializable(ARG_ITEM_ID);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_detail,container,false);
        ButterKnife.bind(this,view);

        new BookDetailAsync().execute(novel);

        return view;
    }

    class BookDetailAsync extends AsyncTask<Novel,Void,List>{
        
        @Override
        protected List doInBackground(Novel... novels) {
            String url = novels[0].getUrl();
            List<String> details = SpiderUtil.getNovelDetails(url);
            return details;
        }

        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);

            bookName.append(list.get(0).toString());
            introduce.append(list.get(1).toString());
            author.append(list.get(3).toString());
            statement.append(list.get(4).toString());
            time.append(list.get(5).toString());
            chapter.append(list.get(6).toString());
        }
    }
}
