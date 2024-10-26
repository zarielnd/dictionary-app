package com.PRM391.dictionaryapp;

import android.app.ProgressDialog;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
    SearchView searchView;
    TextView word, meaning;
    RecyclerView recyclerView_phonetics, recyclerView_meanings;
    ProgressDialog progressDialog;
    PhoneticAdapter phoneticAdapter;
    MeaningAdapter meaningAdapter;
    private void bindingView(){
        searchView = findViewById(R.id.search_view);
        word = findViewById(R.id.textView_word);
        meaning = findViewById(R.id.meanings);
        recyclerView_phonetics = findViewById(R.id.RVPhonetics);
        recyclerView_meanings = findViewById(R.id.RVMeanings);
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
        word.setText("Word:- "+apiResponse.getWord());
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
        bindingView();
        bindingAction();
    }
}