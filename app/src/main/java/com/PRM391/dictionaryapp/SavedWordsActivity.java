package com.PRM391.dictionaryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.PRM391.dictionaryapp.Adapter.SavedWordsAdapter;
import com.PRM391.dictionaryapp.Model.SavedWord;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SavedWordsActivity extends AppCompatActivity implements SavedWordsAdapter.OnWordClickListener {
    private RecyclerView recyclerView;
    private SavedWordsAdapter adapter;
    private ArrayList<SavedWord> savedWords;
    private final Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_words);

        // Set title and back button using the default ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Saved Words");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.recyclerViewSavedWords);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadSavedWords();

    }
    private void loadSavedWords() {
        SharedPreferences prefs = getSharedPreferences("saved_words", MODE_PRIVATE);
        String wordsJson = prefs.getString("word_pairs", "[]");
        Type type = new TypeToken<ArrayList<SavedWord>>(){}.getType();
        savedWords = gson.fromJson(wordsJson, type);

        adapter = new SavedWordsAdapter(savedWords, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onWordClick(SavedWord word) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("SEARCH_WORD", word.getEnglishWord());
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(SavedWord word, int position) {
        savedWords.remove(position);
        adapter.notifyItemRemoved(position);

        // Save updated list
        SharedPreferences prefs = getSharedPreferences("saved_words", MODE_PRIVATE);
        String updatedJson = gson.toJson(savedWords);
        prefs.edit().putString("word_pairs", updatedJson).apply();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}