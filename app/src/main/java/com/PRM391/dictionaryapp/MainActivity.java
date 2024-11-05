package com.PRM391.dictionaryapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.LocaleList;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.PRM391.dictionaryapp.Adapter.MeaningAdapter;
import com.PRM391.dictionaryapp.Adapter.PhoneticAdapter;
import com.PRM391.dictionaryapp.Model.APIResponse;
import com.PRM391.dictionaryapp.Model.SavedWord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private static final String PREF_SAVED_WORDS = "saved_words";
    private final Gson gson = new Gson();
    private FirebaseAuth mAuth;

    private String currentWord;
    private String translatedWord;

    SearchView searchView;
    TextView word, meaning;
    RecyclerView recyclerView_phonetics, recyclerView_meanings;
    ProgressDialog progressDialog;
    PhoneticAdapter phoneticAdapter;
    MeaningAdapter meaningAdapter;
    private void bindingView() {
        searchView = findViewById(R.id.search_view);
        word = findViewById(R.id.textView_word);
        meaning = findViewById(R.id.meanings);
        recyclerView_phonetics = findViewById(R.id.RVPhonetics);
        recyclerView_meanings = findViewById(R.id.RVMeanings);
        Button saveButton = findViewById(R.id.btn_save_word);

        saveButton.setOnClickListener(v -> {
            String englishWord = currentWord; // Your current word
            String vietnameseWord = translatedWord; // Your translated word
            if (englishWord != null && !englishWord.isEmpty() &&
                    vietnameseWord != null && !vietnameseWord.isEmpty()) {
                saveWordPair(englishWord, vietnameseWord);
            }
        });
    }
    private void bindingAction(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading....");
        progressDialog.show();
        RequestManager requestManager = new RequestManager(MainActivity.this);
        requestManager.getWordMeanings(listner,"hello");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressDialog.setTitle("Fetching data for " + query);
                progressDialog.show();
                RequestManager requestManager = new RequestManager(MainActivity.this);
                requestManager.getWordMeanings(listner,query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    private final OnFetchDataListener listner = new OnFetchDataListener() {
        @Override
        public void onFetchData(APIResponse apiResponse, String message) {
            progressDialog.dismiss();
            if(apiResponse==null){
                Toast.makeText(MainActivity.this,"No Data Found !!",Toast.LENGTH_SHORT).show();
                return;
            }
            showData(apiResponse);
        }

        @Override
        public void onError(String message) {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
        }
    };
    private void showData(APIResponse apiResponse){
        word.setText(R.string.word);
        word.append(" "+apiResponse.getWord());

        currentWord = apiResponse.getWord();
        // For now, set translated word to the same value - you'll need to implement actual translation
        translatedWord = TranslateAPI.getTranslatedWord(currentWord, "vi");

        recyclerView_phonetics.setHasFixedSize(true);
        recyclerView_phonetics.setLayoutManager(new GridLayoutManager(this,1));
        phoneticAdapter = new PhoneticAdapter(this,apiResponse.getPhonetics());
        recyclerView_phonetics.setAdapter(phoneticAdapter);

        recyclerView_meanings.setHasFixedSize(true);
        recyclerView_meanings.setLayoutManager(new GridLayoutManager(this,1));
        meaningAdapter = new MeaningAdapter(this,apiResponse.getMeanings());
        recyclerView_meanings.setAdapter(meaningAdapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        bindingView();
        bindingAction();
    }
    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // User not signed in, redirect to login
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_saved_words) {
            Intent intent = new Intent(this, SavedWordsActivity.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId()==R.id.menu_language){
            Toast.makeText(MainActivity.this,"select language",Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId()==R.id.menu_language_english){
            Toast.makeText(MainActivity.this,"select english",Toast.LENGTH_SHORT).show();
            setLocal("en");
            return true;
        }
        if (item.getItemId()==R.id.menu_language_vietnamese){
            Toast.makeText(MainActivity.this,"select vietnamese",Toast.LENGTH_SHORT).show();
            setLocal("vi");
            return true;
        }
        if (item.getItemId() == R.id.menu_sign_out) {
            signOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
//        switch (item.getItemId()) {
//            case R.id.menu_language:
//                Toast.makeText(MainActivity.this,"select langage",Toast.LENGTH_SHORT);
//                return true;
//            case R.id.menu_language_english:
//                Toast.makeText(MainActivity.this,"select langage",Toast.LENGTH_SHORT);
//                return true;
//            case R.id.menu_language_vietnamese:
//                Toast.makeText(MainActivity.this,"select langage",Toast.LENGTH_SHORT);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
    }

    private void signOut() {
        mAuth.signOut();
        Toast.makeText(MainActivity.this, "Signed out successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    private void setLocal(String langCode){
        Locale locale = new Locale(langCode);
        LocaleList localeList = new LocaleList(locale);
        LocaleList.setDefault(localeList);
        Configuration config = new Configuration();
        config.setLocales(localeList);
        getBaseContext().createConfigurationContext(config);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        getSharedPreferences("Settings", MODE_PRIVATE)
                .edit()
                .putString("Language", langCode)
                .apply();

        recreate();
    }

    private void saveWordPair(String englishWord, String vietnameseWord) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        SavedWord word = new SavedWord(englishWord, vietnameseWord);

        db.collection("users").document(userId)
                .collection("saved_words")
                .add(word)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Word saved successfully!", Toast.LENGTH_SHORT).show();
                });
    }
}