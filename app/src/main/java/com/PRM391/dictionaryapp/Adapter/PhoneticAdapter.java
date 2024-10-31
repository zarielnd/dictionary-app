package com.PRM391.dictionaryapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.PRM391.dictionaryapp.Model.Phonetics;
import com.PRM391.dictionaryapp.R;

import java.util.List;

public class PhoneticAdapter extends RecyclerView.Adapter<PhoneticAdapter.PhoneticViewHolder> {
    Context context;
    List<Phonetics> phoneticsList;

    public PhoneticAdapter(Context context, List<Phonetics> phoneticsList) {
        this.context = context;
        this.phoneticsList = phoneticsList;
    }

    @NonNull
    @Override
    public PhoneticAdapter.PhoneticViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.phontics_list_view,parent,false);
        return new PhoneticViewHolder(view);
    }
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull PhoneticAdapter.PhoneticViewHolder holder,int position) {
        if (phoneticsList.get(position).getText()==null||phoneticsList.get(position).getText().isEmpty()){
            holder.phonetic.setText("No text");
        }else {
            holder.phonetic.setText(phoneticsList.get(position).getText());
        }
        holder.audiobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer player = new MediaPlayer();
                try{
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    if (phoneticsList.get(position).getAudio()==null||phoneticsList.get(position).getAudio().isEmpty()){
                        Toast.makeText(context,"audio is not avaliable",Toast.LENGTH_SHORT).show();
                    }else{
                        player.setDataSource(phoneticsList.get(position).getAudio());
                        player.prepare();
                        player.start();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(context,"Couldn't play audio",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return phoneticsList.size();
    }

    public class PhoneticViewHolder extends RecyclerView.ViewHolder {
        TextView phonetic;
        ImageView audiobtn;
        private void bindingView(){
            phonetic = itemView.findViewById(R.id.textView_phonetic);
            audiobtn = itemView.findViewById(R.id.audioBtn);
        }
        public PhoneticViewHolder(@NonNull View itemView){
            super(itemView);
            bindingView();
        }
    }
}
