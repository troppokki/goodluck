package com.cookbook.goodluckallpeaplelife;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NumbersAdapter extends RecyclerView.Adapter<NumbersAdapter.ViewHolder> implements OnNumberItemClickListener {

    ArrayList<NumberLine> items = new ArrayList<>();
    OnNumberItemClickListener listener;
    String selectedNumber;

    @NonNull
    @Override
    public NumbersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.numbers_item,parent,false);
        return new ViewHolder(itemView, this);
    }

    public void setOnItemClickListener(OnNumberItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position){
        if(listener != null){
            listener.onItemClick(holder,view, position);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull NumbersAdapter.ViewHolder holder, int position) {
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


        public ViewHolder(@NonNull View itemview, final OnNumberItemClickListener listener){
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

            if(!item.getCnum().equals("0")){

                if( item.getFirst().equals(item.getCnum()) ){
                    first.setBackgroundResource(R.drawable.mball_radius);
                    first.setTextColor(Color.parseColor("#FFFFFF"));
                }else if(item.getFirst().equals(item.getNear_one()) || item.getFirst().equals(item.getNear_two()) || item.getFirst().equals(item.getNear_three())){
                    first.setBackgroundResource(R.drawable.ball_radius_yellow);
                    first.setTextColor(Color.parseColor("#000000"));
                }else{
                    first.setBackgroundResource(R.drawable.ball_radius);
                    first.setTextColor(Color.parseColor("#000000"));
                }
                if( item.getSecond().equals(item.getCnum()) ){
                    second.setBackgroundResource(R.drawable.mball_radius);
                    second.setTextColor(Color.parseColor("#FFFFFF"));
                }else if(item.getSecond().equals(item.getNear_one()) || item.getSecond().equals(item.getNear_two()) || item.getSecond().equals(item.getNear_three())){
                    second.setBackgroundResource(R.drawable.ball_radius_yellow);
                    second.setTextColor(Color.parseColor("#000000"));
                }else{
                    second.setBackgroundResource(R.drawable.ball_radius);
                    second.setTextColor(Color.parseColor("#000000"));
                }
                if( item.getThird().equals(item.getCnum()) ){
                    third.setBackgroundResource(R.drawable.mball_radius);
                    third.setTextColor(Color.parseColor("#FFFFFF"));
                }else if(item.getThird().equals(item.getNear_one()) || item.getThird().equals(item.getNear_two()) || item.getThird().equals(item.getNear_three())){
                    third.setBackgroundResource(R.drawable.ball_radius_yellow);
                    third.setTextColor(Color.parseColor("#000000"));
                }else{
                    third.setBackgroundResource(R.drawable.ball_radius);
                    third.setTextColor(Color.parseColor("#000000"));
                }
                if( item.getFourth().equals(item.getCnum()) ){
                    fourth.setBackgroundResource(R.drawable.mball_radius);
                    fourth.setTextColor(Color.parseColor("#FFFFFF"));
                }else if(item.getFourth().equals(item.getNear_one()) || item.getFourth().equals(item.getNear_two()) || item.getFourth().equals(item.getNear_three())){
                    fourth.setBackgroundResource(R.drawable.ball_radius_yellow);
                    fourth.setTextColor(Color.parseColor("#000000"));
                }else{
                    fourth.setBackgroundResource(R.drawable.ball_radius);
                    fourth.setTextColor(Color.parseColor("#000000"));
                }
                if( item.getFifth().equals(item.getCnum()) ){
                    fifth.setBackgroundResource(R.drawable.mball_radius);
                    fifth.setTextColor(Color.parseColor("#FFFFFF"));
                }else if(item.getFifth().equals(item.getNear_one()) || item.getFifth().equals(item.getNear_two()) || item.getFifth().equals(item.getNear_three())){
                    fifth.setBackgroundResource(R.drawable.ball_radius_yellow);
                    fifth.setTextColor(Color.parseColor("#000000"));
                }else{
                    fifth.setBackgroundResource(R.drawable.ball_radius);
                    fifth.setTextColor(Color.parseColor("#000000"));
                }
                if( item.getSixth().equals(item.getCnum()) ){
                    sixth.setBackgroundResource(R.drawable.mball_radius);
                    sixth.setTextColor(Color.parseColor("#FFFFFF"));
                }else if(item.getSixth().equals(item.getNear_one()) || item.getSixth().equals(item.getNear_two()) || item.getSixth().equals(item.getNear_three())){
                    sixth.setBackgroundResource(R.drawable.ball_radius_yellow);
                    sixth.setTextColor(Color.parseColor("#000000"));
                }else{
                    sixth.setBackgroundResource(R.drawable.ball_radius);
                    sixth.setTextColor(Color.parseColor("#000000"));
                }
            }
        }

    }
}
