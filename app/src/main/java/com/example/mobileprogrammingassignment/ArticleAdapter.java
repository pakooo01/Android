package com.example.mobileprogrammingassignment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobileprogrammingassignment.model.Article;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private List<Article> articles; // Lista di articoli da visualizzare

    public ArticleAdapter(List<Article> articles) {
        this.articles = articles;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articles.get(position);

        holder.titleTextView.setText(article.getTitle());
        holder.abstractTextView.setText(article.getDescription());
        holder.categoryTextView.setText(article.getCategory());

        if (article.getUrlToImage() != null && !article.getUrlToImage().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(article.getUrlToImage())
                    .into(holder.imageView);
            holder.imageView.setVisibility(View.VISIBLE);
        } else {
            holder.imageView.setVisibility(View.GONE);
        }

        // Imposta il click listener per aprire i dettagli
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), ArticleDetailActivity.class);
            intent.putExtra("title", article.getTitle());
            intent.putExtra("subtitle", article.getDescription()); // Usato come sottotitolo
            intent.putExtra("body", article.getBody()); // Aggiungi il corpo dell'articolo
            intent.putExtra("category", article.getCategory());
            intent.putExtra("imageUrl", article.getUrlToImage());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, abstractTextView, categoryTextView;
        ImageView imageView; Button detailButton;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.articleTitle);
            imageView = itemView.findViewById(R.id.articleImage);
            abstractTextView = itemView.findViewById(R.id.articleAbstract);
            categoryTextView = itemView.findViewById(R.id.articleCategory);
            detailButton = itemView.findViewById(R.id.detailButton);
        }
    }
}
