package com.PRM391.dictionaryapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.PRM391.dictionaryapp.Model.Definitions;
import com.PRM391.dictionaryapp.R;
import com.PRM391.dictionaryapp.TranslateAPI;

import java.util.List;
import java.util.Locale;

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
        String currentLanguage = Locale.getDefault().getLanguage();

        // Handle definition text
        String definition = definitionsList.get(position).getDefinition();
        if (currentLanguage.equals("vi")) {
            translateAndSetText(definition, holder.defitions);
        } else {
            holder.defitions.append(" "+definition);
        }
        if (definitionsList.get(position).getExample()==null||definitionsList.get(position).getExample().isEmpty())
            holder.example.append(" "+context.getString(R.string.no_example_avaliable));
        else{
            holder.example.append(" "+definitionsList.get(position).getExample());
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

    private void translateAndSetText(String text, final TextView textView) {
        TranslateAPI.translate(text, "vi", new TranslateAPI.TranslationCallback() {
            @Override
            public void onSuccess(String translatedText) {
                ((Activity) context).runOnUiThread(() -> {
                    textView.append(" "+translatedText);
                });
            }

            @Override
            public void onError(String error) {
                ((Activity) context).runOnUiThread(() -> {
                    textView.append(" "+text); // Fallback to original text
                });
            }
        });
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
