package com.PRM391.dictionaryapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.PRM391.dictionaryapp.Model.SavedWord;
import com.PRM391.dictionaryapp.R;

import java.util.List;

public class SavedWordsAdapter extends RecyclerView.Adapter<SavedWordsAdapter.WordViewHolder> {
    private List<SavedWord> savedWords;
    private OnWordClickListener listener;

    public interface OnWordClickListener {
        void onWordClick(SavedWord word);
        void onDeleteClick(SavedWord word, int position);
    }

    public SavedWordsAdapter(List<SavedWord> savedWords, OnWordClickListener listener) {
        this.savedWords = savedWords;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_saved_word, parent, false);
        return new WordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
        SavedWord word = savedWords.get(position);
        holder.bind(word, listener);
    }

    @Override
    public int getItemCount() {
        return savedWords.size();
    }

    static class WordViewHolder extends RecyclerView.ViewHolder {
        TextView englishWordText;
        TextView vietnameseWordText;
        ImageButton deleteButton;

        WordViewHolder(@NonNull View itemView) {
            super(itemView);
            englishWordText = itemView.findViewById(R.id.textViewEnglishWord);
            vietnameseWordText = itemView.findViewById(R.id.textViewVietnameseWord);
            deleteButton = itemView.findViewById(R.id.buttonDelete);
        }

        void bind(SavedWord word, OnWordClickListener listener) {
            englishWordText.setText(word.getEnglishWord());
            vietnameseWordText.setText(word.getVietnameseWord());

            itemView.setOnClickListener(v -> listener.onWordClick(word));
            deleteButton.setOnClickListener(v -> listener.onDeleteClick(word, getAdapterPosition()));
        }
    }
}
