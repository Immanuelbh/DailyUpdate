package com.ijbh.dailyupdate.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.*;

import com.ijbh.dailyupdate.R;
import com.ijbh.dailyupdate.models.Article;
import com.ijbh.dailyupdate.adapters.ArticleAdapter;
import com.ijbh.dailyupdate.viewmodel.ArticleViewModel;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    final String TAG = "NewsFragment";

    ArticleAdapter adapter;

    public interface OnNewsFragmentListener{

    }

    OnNewsFragmentListener callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            callback = (OnNewsFragmentListener) context;
        }catch (ClassCastException cce){
            throw new ClassCastException("The activity must implement OnNewsFragmentListener interface");
        }
    }

    public static NewsFragment newInstance(){
        NewsFragment newsFragment = new NewsFragment();

        return newsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_fragment, container, false);

        //recyclerview
        final RecyclerView recyclerView = rootView.findViewById(R.id.news_recycler);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        ArticleViewModel model = ViewModelProviders.of(this).get(ArticleViewModel.class);

        model.getArticleList().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                adapter = new ArticleAdapter(NewsFragment.this.getContext(), articles);

                //not working
                adapter.setListener(new ArticleAdapter.ArticleListener() {
                    @Override
                    public void onArticleClicked(int position, View view) {

                        //Toast.makeText(getContext(), "Article " + position, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getContext(), ArticleActivity.class);
                        intent.putExtra("current_article", position);
                        startActivity(intent);

                    }
                });

                recyclerView.setAdapter(adapter);

                if(articles == null)
                    Log.e("err","articles list is empty");
            }
        });

        return rootView;
    }
}
