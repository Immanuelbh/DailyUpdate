package com.ijbh.dailyupdate.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ijbh.dailyupdate.R;
import com.ijbh.dailyupdate.models.Article;
import com.ijbh.dailyupdate.ui.ArticleActivity;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private Context aCtx;
    private List<Article> articles;
    private ArticleListener listener;

    public interface ArticleListener{
        void onArticleClicked(int position, View view);
    }

    public void setListener(ArticleListener listener){
        this.listener = listener;
    }

    public ArticleAdapter(Context aCtx, List<Article> articleList){
        this.aCtx = aCtx;
        this.articles = articleList;
    }

    //public ArticleAdapter(){}

    public ArticleAdapter(List<Article> articles){
        this.articles = articles;
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder{
        ImageView imageIv;
        TextView titleTv;
        TextView descTv;
        LinearLayout articleLl;


        public ArticleViewHolder(View itemView){
            super(itemView);

            imageIv = itemView.findViewById(R.id.article_img_iv);
            titleTv = itemView.findViewById(R.id.title_tv);
            descTv = itemView.findViewById(R.id.desc_tv);
            articleLl = itemView.findViewById(R.id.article_ll);
        }
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(aCtx).inflate(R.layout.article_cell, parent, false);
        //ArticleViewHolder articleViewHolder = new ArticleViewHolder(view);

        //working
        final ArticleViewHolder avh = new ArticleViewHolder(view);
        avh.articleLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(aCtx, "Article num: " + String.valueOf(avh.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(parent.getContext(), ArticleActivity.class);
                intent.putExtra("current_article", avh.getAdapterPosition());
                aCtx.startActivity(intent);
            }
        });

        return avh;//new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.titleTv.setText(article.getTitle());
        holder.descTv.setText(article.getDesc());
        //TODO add glide for article image
        if(article.getImageUrl() == null){
            Glide.with(aCtx)
                    .load(R.drawable.no_image_available_comp)
                    .into(holder.imageIv);
        }
        else{
            Glide.with(aCtx)
                    .load(article.getImageUrl()+"")
                    .into(holder.imageIv);
        }

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
