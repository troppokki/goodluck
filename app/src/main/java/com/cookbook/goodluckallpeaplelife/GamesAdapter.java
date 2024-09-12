package com.cookbook.goodluckallpeaplelife;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder> implements OnGamesItemClickListener {

    ArrayList<NumberLine> items = new ArrayList<>();
    OnGamesItemClickListener listener;
    String selectedNumber;

    @NonNull
    @Override
    public GamesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.numbers_item,parent,false);
        return new ViewHolder(itemView, this);
    }

    public void setOnItemClickListener(OnGamesItemClickListener listener){
        this.listener =  listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position){
        if(listener != null){
            listener.onItemClick(holder,view, position);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull GamesAdapter.ViewHolder holder, int position) {
        NumberLine item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void addItem(NumberLine item) {
        items.add(item);
    }

    public void setItems(ArrayList<NumberLine> items){
        this.items = items;
    }

    //
    public NumberLine getItem(int position){
        return items.get(position);
    }

    //ArrayList 순서 지정 세팅
    public void setItem(int position, NumberLine item){
        items.set(position, item);
    }

    public void setSelectedNumber(String num){
        selectedNumber = num;
    }

    public String getSelectedNumber(){
        return selectedNumber;
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView season,first, second, third, fourth, fifth, sixth;


        public ViewHolder(@NonNull View itemview, final OnGamesItemClickListener listener){
            super(itemview);
            season = itemview.findViewById(R.id.season);
            first  = itemview.findViewById(R.id.numone);
            second = itemview.findViewById(R.id.numtwo);
            third  = itemview.findViewById(R.id.numthree);
            fourth = itemview.findViewById(R.id.numfour);
            fifth  = itemview.findViewById(R.id.numfive);
            sixth  = itemview.findViewById(R.id.numsix);


            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if(listener != null){
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(NumberLine item){

            Log.d("ITEM",item.getFifth());

            season.setText( item.getSeason() );
            first.setText( item.getFirst() );
            second.setText(item.getSecond());
            third.setText( item.getThird() );
            fourth.setText(item.getFourth());
            fifth.setText( item.getFifth() );
            sixth.setText( item.getSixth() );
        }

    }
}
