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

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> implements OnHistoryItemClickListener{

    ArrayList<NumberLine> items = new ArrayList<>();
    OnHistoryItemClickListener listener;
    String selectedNumber;

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.history_numbers_item,parent,false);
        return new ViewHolder(itemView, this);
    }

    public void setOnItemClickListener(OnHistoryItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position){
        if(listener != null){
            listener.onItemClick(holder,view, position);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
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


        public ViewHolder(@NonNull View itemview, final OnHistoryItemClickListener listener){
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

            String zero = "";

            season.setText( item.getSeason() );
            first.setText( item.getFirst() );
            second.setText(item.getSecond());
            third.setText( item.getThird() );
            fourth.setText(item.getFourth());
            fifth.setText( item.getFifth() );
            sixth.setText( item.getSixth() );

            if( item.getFirst().equals(zero) ){
                first.setBackgroundResource(R.drawable.history_ball_radius);
                first.setTextColor(Color.parseColor("#ffffff"));
            }else{
                first.setBackgroundResource(R.drawable.history_ball_radius_gray);
                first.setTextColor(Color.parseColor("#000000"));
            }
            if( item.getSecond().equals(zero) ){
                second.setBackgroundResource(R.drawable.history_ball_radius);
                second.setTextColor(Color.parseColor("#ffffff"));
            }else{
                second.setBackgroundResource(R.drawable.history_ball_radius_gray);
                second.setTextColor(Color.parseColor("#000000"));
            }
            if( item.getThird().equals(zero) ){
                third.setBackgroundResource(R.drawable.history_ball_radius);
                third.setTextColor(Color.parseColor("#ffffff"));
            }else{
                third.setBackgroundResource(R.drawable.history_ball_radius_gray);
                third.setTextColor(Color.parseColor("#000000"));
            }
            if( item.getFourth().equals(zero) ){
                fourth.setBackgroundResource(R.drawable.history_ball_radius);
                fourth.setTextColor(Color.parseColor("#ffffff"));
            }else{
                fourth.setBackgroundResource(R.drawable.history_ball_radius_gray);
                fourth.setTextColor(Color.parseColor("#000000"));
            }
            if( item.getFifth().equals(zero) ){
                fifth.setBackgroundResource(R.drawable.history_ball_radius);
                fifth.setTextColor(Color.parseColor("#ffffff"));
            }else{
                fifth.setBackgroundResource(R.drawable.history_ball_radius_gray);
                fifth.setTextColor(Color.parseColor("#000000"));
            }
            if( item.getSixth().equals(zero) ){
                sixth.setBackgroundResource(R.drawable.history_ball_radius);
                sixth.setTextColor(Color.parseColor("#ffffff"));
            }else{
                sixth.setBackgroundResource(R.drawable.history_ball_radius_gray);
                sixth.setTextColor(Color.parseColor("#000000"));
            }

        }

    }
}
