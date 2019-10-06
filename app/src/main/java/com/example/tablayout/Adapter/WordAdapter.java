package com.example.tablayout.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tablayout.DetailActivity;
import com.example.tablayout.R;
import com.example.tablayout.model.WordModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {

    private final ArrayList<WordModel> listWord = new ArrayList<>();
    private final Context context;
    private final ArrayList<WordModel> baseList = new ArrayList<>();

    public WordAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<WordModel> listWord){
        if (listWord.size() > 0){
            this.listWord.clear();
        }
        this.listWord.addAll(listWord);
        this.baseList.addAll(listWord);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_word, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        holder.txtWord.setText((listWord.get(holder.getAdapterPosition()).getWord()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = listWord.get(holder.getAdapterPosition()).getWord();
                String description = listWord.get(holder.getAdapterPosition()).getDescription();
                WordModel wordModel = new WordModel(word, description);
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("word", wordModel);
                context.startActivity(i);
            }
        });

    }

    public void filter(String text){
        listWord.clear();
        if (text.isEmpty()){
            listWord.addAll(baseList);
        }else {
            text = text.toLowerCase();
            for (WordModel item: baseList){
                if (item.getWord().toLowerCase().contains(text)){
                    listWord.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return listWord.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtWord;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtWord = itemView.findViewById(R.id.txt_word);
        }
    }
}
