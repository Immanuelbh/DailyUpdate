package com.ijbh.dailyupdate.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ijbh.dailyupdate.R;
import com.ijbh.dailyupdate.models.Article;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private Context aCtx;
    private List<Article> articles;
    private ArticleListener listener;

    //final String ARTICLE_IMAGE_URL = "";

    interface ArticleListener{
        void onArticleClicked(int position);
    }

    public void setListener(ArticleListener listener){
        this.listener = listener;
    }

    public ArticleAdapter(Context aCtx, List<Article> articleList){
        this.aCtx = aCtx;
        this.articles = articleList;
    }

    public ArticleAdapter(List<Article> articles){
        this.articles = articles;
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder{
        TextView titleTv;
        TextView descTv;

        public ArticleViewHolder(View itemView){
            super(itemView);

            titleTv = itemView.findViewById(R.id.title_tv);
            descTv = itemView.findViewById(R.id.desc_tv);

        }
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(aCtx).inflate(R.layout.article_cell, parent, false);
        //ArticleViewHolder articleViewHolder = new ArticleViewHolder(view);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.titleTv.setText(article.getTitle());
        holder.descTv.setText(article.getDesc());
        //TODO add glide for article image
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
