package com.mohammadKZ.CoronaVirus_COVID19_statistics.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19.R;
import com.mohammadKZ.CoronaVirus_COVID19_statistics.model.History;

import java.text.DecimalFormat;
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
        DecimalFormat decimalFormat = new DecimalFormat("###,###");// set the decimal format

        holder.newCase.setText(decimalFormat.format(Integer.valueOf(histories.get(position).getNewCases())));
        holder.newDeath.setText(decimalFormat.format(Integer.valueOf(histories.get(position).getNewDeath())));
        holder.newRecovered.setText(decimalFormat.format(Integer.valueOf(histories.get(position).getNewRecovered())));
        holder.totalCases.setText(decimalFormat.format(Integer.valueOf(histories.get(position).getCases())));
        holder.totalDeath.setText(decimalFormat.format(Integer.valueOf(histories.get(position).getDeaths())));
        holder.totalRecovered.setText(decimalFormat.format(Integer.valueOf(histories.get(position).getRecovered())));
        holder.date.setText(histories.get(position).getDate());

        boolean expended = histories.get(position).isExpended();
        if (expended) {
            holder.down_up.setImageResource(R.drawable.up);
            holder.new_info_card.setVisibility(View.VISIBLE);
            holder.total_info_card.setVisibility(View.VISIBLE);
            holder.diver.setVisibility(View.VISIBLE);
        } else {
            holder.down_up.setImageResource(R.drawable.down);
            holder.new_info_card.setVisibility(View.GONE);
            holder.total_info_card.setVisibility(View.GONE);
            holder.diver.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView newCase, newDeath, newRecovered, totalCases, totalDeath, totalRecovered, date;
        CardView new_info_card, total_info_card;
        ImageView down_up;
        View diver;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            newCase = itemView.findViewById(R.id.newCase);
            newDeath = itemView.findViewById(R.id.newDeath);
            newRecovered = itemView.findViewById(R.id.newRecovered);
            totalCases = itemView.findViewById(R.id.totalCases);
            totalDeath = itemView.findViewById(R.id.totalDeath);
            totalRecovered = itemView.findViewById(R.id.totalRecovered);
            date = itemView.findViewById(R.id.date);
            new_info_card = itemView.findViewById(R.id.new_info);
            total_info_card = itemView.findViewById(R.id.total_info);
            down_up = itemView.findViewById(R.id.down_up);
            diver = itemView.findViewById(R.id.diver);

            down_up.setOnClickListener(new View.OnClickListener() {
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
