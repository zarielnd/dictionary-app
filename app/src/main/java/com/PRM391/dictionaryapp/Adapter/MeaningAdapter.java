package com.PRM391.dictionaryapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.PRM391.dictionaryapp.Model.Meanings;
import com.PRM391.dictionaryapp.R;

import java.util.List;

public class MeaningAdapter extends RecyclerView.Adapter<MeaningAdapter.MeaningViewHolder> {
    Context context;
    List<Meanings> meaningsList;

    public MeaningAdapter(Context context, List<Meanings> meaningsList) {
        this.context = context;
        this.meaningsList = meaningsList;
    }

    @NonNull
    @Override
    public MeaningAdapter.MeaningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.meaning_list_view,parent,false);
        return new MeaningAdapter.MeaningViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeaningAdapter.MeaningViewHolder holder, int position) {
        holder.textView_phonetics.setText("Part of speech:- "+meaningsList.get(position).getPartOfSpeech());
        holder.recyclerView_defitions.setHasFixedSize(true);
        holder.recyclerView_defitions.setLayoutManager(new GridLayoutManager(context,1));
        DefitionsAdapter defitionsAdapter = new DefitionsAdapter(context,meaningsList.get(position).getDefinitions());
        holder.recyclerView_defitions.setAdapter(defitionsAdapter);
    }

    @Override
    public int getItemCount() {
        return meaningsList.size();
    }

    public class MeaningViewHolder extends RecyclerView.ViewHolder{
        TextView textView_phonetics;
        RecyclerView recyclerView_defitions;
        private void bindingView(){
            textView_phonetics = itemView.findViewById(R.id.textView_phonetic);
            recyclerView_defitions = itemView.findViewById(R.id.recycleview_definitions);
        }
        public MeaningViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingView();
        }
    }
}
