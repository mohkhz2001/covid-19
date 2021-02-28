package com.example.covid_19.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19.R;
import com.example.covid_19.model.History;
import com.example.covid_19.model.News;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.viewHolder> {

    ArrayList<News> news;
    Context context;

    public NewsAdapter(Context context, ArrayList<News> news) {
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public NewsAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_layout, parent, false);
        return new NewsAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.viewHolder holder, int position) {
        holder.title.setText(news.get(position).getTitle());
        holder.description.setText(news.get(position).getDescription());
        Picasso.get().load(news.get(position).getImage()).into(holder.img_url);

        boolean expended = news.get(position).isExpended();
        holder.description.setVisibility(expended ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView img_url;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            img_url = itemView.findViewById(R.id.img_url);

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    News news1 = news.get(getAdapterPosition());
                    news1.setExpended(!news1.isExpended());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            img_url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    News news1 = news.get(getAdapterPosition());
                    news1.setExpended(!news1.isExpended());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
