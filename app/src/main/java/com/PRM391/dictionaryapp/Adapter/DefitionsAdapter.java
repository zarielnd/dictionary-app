package com.PRM391.dictionaryapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.PRM391.dictionaryapp.Model.Definitions;
import com.PRM391.dictionaryapp.R;

import java.util.List;

public class DefitionsAdapter extends RecyclerView.Adapter<DefitionsAdapter.DefitionViewHolder> {
    Context context;
    List<Definitions> definitionsList;

    public DefitionsAdapter(Context context, List<Definitions> definitionsList) {
        this.context = context;
        this.definitionsList = definitionsList;
    }

    @NonNull
    @Override
    public DefitionsAdapter.DefitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.defitions_list_view,parent,false);
        return new DefitionsAdapter.DefitionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DefitionsAdapter.DefitionViewHolder holder, int position) {
        holder.defitions.setText("Defintions :- "+definitionsList.get(position).getDefinition());
        if (definitionsList.get(position).getExample()==null||definitionsList.get(position).getExample().isEmpty()){
            holder.example.setText("Example :- No Example avaliable");
        }else{
            holder.example.setText("Example: "+definitionsList.get(position).getExample());
        }

        StringBuilder synonyms = new StringBuilder();
        StringBuilder antonyms = new StringBuilder();
        synonyms.append(definitionsList.get(position).getSynonyms());
        antonyms.append(definitionsList.get(position).getAntonyms());

        holder.synonyms.setText(synonyms);
        holder.antonyms.setText(antonyms);
        holder.synonyms.setSelected(true);
        holder.antonyms.setSelected(true);

    }

    @Override
    public int getItemCount() {
        return definitionsList.size();
    }

    public class DefitionViewHolder extends RecyclerView.ViewHolder{
        TextView defitions, example, synonyms, antonyms;
        private void bindingView(){
            defitions = itemView.findViewById(R.id.textView_defitions);
            example = itemView.findViewById(R.id.textView_example);
            synonyms = itemView.findViewById(R.id.textView_synonyns);
            antonyms = itemView.findViewById(R.id.textView_antonyms);
        }
        public DefitionViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingView();
        }
    }
}
