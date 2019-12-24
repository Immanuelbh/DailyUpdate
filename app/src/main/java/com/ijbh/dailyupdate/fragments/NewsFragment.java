package com.ijbh.dailyupdate.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ijbh.dailyupdate.R;
import com.ijbh.dailyupdate.articles.Article;
import com.ijbh.dailyupdate.articles.ArticleAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    final String TAG = "NewsFragment";

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
        RecyclerView recyclerView = rootView.findViewById(R.id.news_recycler);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        List<Article> articlesList = new ArrayList<>();
        articlesList.add(new Article("Hello World!", "This is the first cell to be created in this project!"));
        articlesList.add(new Article("Second Test", "Second attempt at creating a cell inside a fragment."));

        ArticleAdapter articleAdapter = new ArticleAdapter(articlesList);

        recyclerView.setAdapter(articleAdapter);

        return rootView;
    }
}
