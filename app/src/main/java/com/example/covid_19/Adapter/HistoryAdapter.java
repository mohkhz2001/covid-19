package com.example.covid_19.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19.R;
import com.example.covid_19.model.History;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.viewHolder> {

    ArrayList<History> histories;
    Context context;

    public HistoryAdapter(Context context, ArrayList<History> histories) {
        this.context = context;
        this.histories = histories;
    }

    @NonNull
    @Override
    public HistoryAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_view, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.viewHolder holder, int position) {
        holder.newCase.setText(histories.get(position).getNewCases());
        holder.newDeath.setText(histories.get(position).getNewDeath());
        holder.newRecovered.setText(histories.get(position).getNewRecovered());
        holder.totalCases.setText(histories.get(position).getCases());
        holder.totalDeath.setText(histories.get(position).getDeaths());
        holder.totalRecovered.setText(histories.get(position).getRecovered());
        holder.date.setText(histories.get(position).getDate());

        boolean expended = histories.get(position).isExpended();
        holder.expendable.setVisibility(expended ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView newCase, newDeath, newRecovered, totalCases, totalDeath, totalRecovered, date;
        CardView expendable;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            newCase = itemView.findViewById(R.id.newCase);
            newDeath = itemView.findViewById(R.id.newDeath);
            newRecovered = itemView.findViewById(R.id.newRecovered);
            totalCases = itemView.findViewById(R.id.totalCases);
            totalDeath = itemView.findViewById(R.id.totalDeath);
            totalRecovered = itemView.findViewById(R.id.totalRecovered);
            date = itemView.findViewById(R.id.date);
            expendable = itemView.findViewById(R.id.expendable);

            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    History history = histories.get(getAdapterPosition());
                    history.setExpended(!history.isExpended());
                    notifyItemChanged(getAdapterPosition());
                }
            });


        }
    }

}
