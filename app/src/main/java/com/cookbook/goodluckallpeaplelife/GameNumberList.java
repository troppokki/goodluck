package com.cookbook.goodluckallpeaplelife;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameNumberList extends AppCompatActivity {

    TextView btnback;
    GamesAdapter adapter;
    RecyclerView recycvlerView;
    SQLiteDatabase db;
    StringBuffer query;
    List<Map<String, Object>> nearNumList;
    Map<String, Object> item;
    int limit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_number_list);

        btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        recycvlerView = findViewById(R.id.gameNumberList);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recycvlerView.setLayoutManager(layoutManager);

        limit = 100;
        selectList();

        @SuppressLint("ResourceType") Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.number_list_fade_in);
        recycvlerView.startAnimation(anim);
        adapter.setOnItemClickListener(new OnGamesItemClickListener() {
            @Override
            public void onItemClick(GamesAdapter.ViewHolder holder, View view, int position) {
                //NumberLine item = adapter.getItem(position);
                //Toast.makeText(getApplicationContext(),position+"번 아이템 선택됨", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        selectList();
    }

    private void selectList(){
        nearNumList = new ArrayList<>();
        nearNumList = selectGames("DB_LOTTO",100);
        item = new HashMap<>();

        adapter = new GamesAdapter();


        for(int n = 0 ; n < nearNumList.size(); n++) {
            item = nearNumList.get(n);
            adapter.addItem(new NumberLine(""+item.get("RND")+"회",""+item.get("NUM1"), ""+item.get("NUM2"), ""+item.get("NUM3"), ""+item.get("NUM4"), ""+item.get("NUM5"), ""+item.get("NUM6"),"0","","",""));
        }


        recycvlerView.setAdapter(adapter);
    }

    private List<Map<String,Object>> selectGames(String name, int limit){
        db = openOrCreateDatabase(name,MODE_PRIVATE, null);
        Cursor cursor = null;

        query = new StringBuffer();
        query.append(" SELECT CAST(ROUND AS INTEGER) AS ROUND \n")
                .append("      , NUMBER1    \n")
                .append("      , NUMBER2    \n")
                .append("      , NUMBER3    \n")
                .append("      , NUMBER4    \n")
                .append("      , NUMBER5    \n")
                .append("      , NUMBER6    \n")
                .append("   FROM TB_GAMES   \n")
                .append("   ORDER BY _id DESC  \n")
                .append("   LIMIT "+(limit-100)+","+limit+"   \n");

        Log.d("GAME QUERY",query.toString());

        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> rsItem;
        try {
            cursor = db.rawQuery(query.toString(), null);
            int recordCount = cursor.getCount();

            Log.d("COUNT",":"+recordCount);

            String rnd    = "";
            String num1    = "";
            String num2    = "";
            String num3    = "";
            String num4    = "";
            String num5    = "";
            String num6    = "";


            for (int i = 0; i < recordCount; i++) {
                cursor.moveToNext();

                rnd    = cursor.getString(0);
                num1    = cursor.getString(1);
                num2   = cursor.getString(2);
                num3    = cursor.getString(3);
                num4    = cursor.getString(4);
                num5    = cursor.getString(5);
                num6    = cursor.getString(6);

                rsItem = new HashMap<>();
                rsItem.put("RND", rnd );
                rsItem.put("NUM1", num1 );
                rsItem.put("NUM2", num2 );
                rsItem.put("NUM3", num3 );
                rsItem.put("NUM4", num4 );
                rsItem.put("NUM5", num5 );
                rsItem.put("NUM6", num6 );
                resultList.add(rsItem);
                //textarea.append(num +"-"+ cnt +"-"+ rank +"\n");
            }

            cursor.close();
            db.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return resultList;
    }
}