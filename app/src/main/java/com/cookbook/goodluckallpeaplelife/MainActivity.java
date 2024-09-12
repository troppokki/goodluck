package com.cookbook.goodluckallpeaplelife;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class MainActivity extends AppCompatActivity {

    Button btnsave, btnList;
    TextView mnum, first, second, third, fourth, fifth, sixth, count, rank, near_one, near_two
            , near_three, moveleft, moveright, first_bar, second_bar, third_bar, fourth_bar
            , fifth_bar, sixth_bar, btnButtoomSliding
            , sixCount, fifCount, fourCount, threeCount, twoCount, moveup;
    String strmnum, strfirst, strsecond, strthird, strfourth, strfifth, strsixth;
    SeekBar selectbar;
    LinearLayout recnums;
    NumbersAdapter adapter;
    HistoryAdapter historyAdapter;
    RecyclerView recycvlerView,historyView;
    SQLiteDatabase db;
    String tName,selectQuery;
    String[] chooseNums = {"0","0","0","0","0","0"};

    StringBuffer query;

    List<Map<String, Object>> numList,nearNumList,historyNumList;
    Map<String,Object> numberCount,item,historyItem;

    Toast alim;

    int topNum = 1;

    int inputSeq = 0;

    String messageNum = "";


    boolean isPageOpen = false;
    boolean isBottomPageOpen = false;

    String dbName = "DB_LOTTO";

    Animation translateLeftAnim,translateRightAnim,translateLeftAnimBottom,translateRightAnimBottom;
    LinearLayout page, main_numbers, bottomPage, counting, srchSix, srchFive, srchFour, srchThree, srchTwo, con;

    NearNumbers n_layout;

    String type = "id";

    int viewId = 0;
    /*
    NotificationManager manager;

    private static String CHANNEL_ID = "channel1";
    private static String CHANNEL_NAME = "Channel1";
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        moveleft = findViewById(R.id.moveleft);
        moveright= findViewById(R.id.moveright);
        btnsave = findViewById(R.id.btnsave);
        btnList = findViewById(R.id.btnList);

        first  = findViewById(R.id.first);
        second = findViewById(R.id.second);
        third  = findViewById(R.id.third);
        fourth = findViewById(R.id.fourth);
        fifth  = findViewById(R.id.fifth);
        sixth  = findViewById(R.id.sixth);

        sixCount   = findViewById(R.id.sixCount);
        fifCount   = findViewById(R.id.fifCount);
        fourCount  = findViewById(R.id.fourCount);
        threeCount = findViewById(R.id.threeCount);
        twoCount   = findViewById(R.id.twoCount);

        first_bar  = findViewById(R.id.first_bar);
        second_bar = findViewById(R.id.second_bar);
        third_bar  = findViewById(R.id.third_bar);
        fourth_bar = findViewById(R.id.fourth_bar);
        fifth_bar  = findViewById(R.id.fifth_bar);
        sixth_bar  = findViewById(R.id.sixth_bar);

        srchSix    = findViewById(R.id.srchSix);
        srchFive   = findViewById(R.id.srchFive);
        srchFour   = findViewById(R.id.srchFour);
        srchThree  = findViewById(R.id.srchThree);
        srchTwo    = findViewById(R.id.srchTwo);

        moveup    = findViewById(R.id.moveup);

        recycvlerView = findViewById(R.id.recyclerView);
        historyView = findViewById(R.id.historyView);

        main_numbers = findViewById(R.id.main_numbers);

        page = findViewById(R.id.history_num);
        translateLeftAnim = AnimationUtils.loadAnimation(this, R.anim.translate_left);
        translateRightAnim = AnimationUtils.loadAnimation(this, R.anim.translate_right);

        SlideingPageAnimationListener animListener = new SlideingPageAnimationListener();
        translateLeftAnim.setAnimationListener(animListener);
        translateRightAnim.setAnimationListener(animListener);

        bottomPage = findViewById(R.id.bottomPage);
        translateLeftAnimBottom = AnimationUtils.loadAnimation(this, R.anim.translate_up);
        translateRightAnimBottom = AnimationUtils.loadAnimation(this, R.anim.translate_down);

        SlideingPageAnimationListener2 animListenerBottom = new SlideingPageAnimationListener2();
        translateLeftAnimBottom.setAnimationListener(animListenerBottom);
        translateRightAnimBottom.setAnimationListener(animListenerBottom);


        mnum   = findViewById(R.id.mnum);


        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topNum = 1;
                movePosTopNumber();
            }
        });
        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topNum = 2;
                movePosTopNumber();
            }
        });
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topNum = 3;
                movePosTopNumber();
            }
        });
        fourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topNum = 4;
                movePosTopNumber();
            }
        });
        fifth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topNum = 5;
                movePosTopNumber();
            }
        });
        sixth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topNum = 6;
                movePosTopNumber();
            }
        });

        readExcel();

        /**/
        historyItem = new HashMap<>();
        historyItem = selectLastNumber("DB_LOTTO");
/*
        first.setText(""+historyItem.get("NUM1"));
        second.setText(""+historyItem.get("NUM2"));
        third.setText(""+historyItem.get("NUM3"));
        fourth.setText(""+historyItem.get("NUM4"));
        fifth.setText(""+historyItem.get("NUM5"));
        sixth.setText(""+historyItem.get("NUM6"));

        chooseNums[0] = ""+historyItem.get("NUM1");
        chooseNums[1] = ""+historyItem.get("NUM2");
        chooseNums[2] = ""+historyItem.get("NUM3");
        chooseNums[3] = ""+historyItem.get("NUM4");
        chooseNums[4] = ""+historyItem.get("NUM5");
        chooseNums[5] = ""+historyItem.get("NUM6");
        */
        numberCount = selectNumberCount("DB_LOTTO",chooseNums);

        sixCount.setText(""+numberCount.get("NUM1"));
        fifCount.setText(""+numberCount.get("NUM2"));
        fourCount.setText(""+numberCount.get("NUM3"));
        threeCount.setText(""+numberCount.get("NUM4"));
        twoCount.setText(""+numberCount.get("NUM5"));


        historyNumList = new ArrayList<>();
        historyNumList = selectHistory("DB_LOTTO",chooseNums,"");

        historyAdapter =new HistoryAdapter();


        for(int n = 0 ; n < historyNumList.size(); n++) {
            historyItem = historyNumList.get(n);
            historyAdapter.addItem(new NumberLine(""+historyItem.get("SCORE"), ""+historyItem.get("RND")+"회",""+historyItem.get("NUM1"), ""+historyItem.get("NUM2"), ""+historyItem.get("NUM3"), ""+historyItem.get("NUM4"), ""+historyItem.get("NUM5"), ""+historyItem.get("NUM6")));
        }

        historyView.setAdapter(historyAdapter);

        @SuppressLint("ResourceType") Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.number_list_fade_in);
        historyView.startAnimation(anim);


        moveup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPageOpen){
                    page.startAnimation(translateRightAnim);

                    srchSix.setBackgroundResource(R.drawable.first_left_border);
                    srchFive.setBackgroundResource(R.drawable.left_border);
                    srchFour.setBackgroundResource(R.drawable.left_border);
                    srchThree.setBackgroundResource(R.drawable.left_border);
                    srchTwo.setBackgroundResource(R.drawable.left_border);
                }
            }
        });



        srchSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                srchSix.setBackgroundResource(R.drawable.first_left_border_black);
                srchFive.setBackgroundResource(R.drawable.left_border);
                srchFour.setBackgroundResource(R.drawable.left_border);
                srchThree.setBackgroundResource(R.drawable.left_border);
                srchTwo.setBackgroundResource(R.drawable.left_border);

                if(isPageOpen){
                    //page.startAnimation(translateRightAnim);
                }else{
                    page.setVisibility(View.VISIBLE);
                    page.startAnimation(translateLeftAnim);
                }

                chooseNums[0] = first.getText().toString();
                chooseNums[1] = second.getText().toString();
                chooseNums[2] = third.getText().toString();
                chooseNums[3] = fourth.getText().toString();
                chooseNums[4] = fifth.getText().toString();
                chooseNums[5] = sixth.getText().toString();

                //numberCount = selectNumberCount("DB_LOTTO",chooseNums);

                sixCount.setText(""+numberCount.get("NUM1"));
                fifCount.setText(""+numberCount.get("NUM2"));
                fourCount.setText(""+numberCount.get("NUM3"));
                threeCount.setText(""+numberCount.get("NUM4"));
                twoCount.setText(""+numberCount.get("NUM5"));

                historyNumList = new ArrayList<>();
                historyNumList = selectHistory("DB_LOTTO",chooseNums,"6");
                historyItem = new HashMap<>();

                historyAdapter =new HistoryAdapter();


                for(int n = 0 ; n < historyNumList.size(); n++) {
                    historyItem = historyNumList.get(n);
                    historyAdapter.addItem(new NumberLine(""+historyItem.get("SCORE"), ""+historyItem.get("RND")+"회",""+historyItem.get("NUM1"), ""+historyItem.get("NUM2"), ""+historyItem.get("NUM3"), ""+historyItem.get("NUM4"), ""+historyItem.get("NUM5"), ""+historyItem.get("NUM6")));
                }

                historyView.setAdapter(historyAdapter);

                @SuppressLint("ResourceType") Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.number_list_fade_in);
                historyView.startAnimation(anim);
            }
        });
        srchFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                srchSix.setBackgroundResource(R.drawable.first_left_border);
                srchFive.setBackgroundResource(R.drawable.left_border_black);
                srchFour.setBackgroundResource(R.drawable.left_border);
                srchThree.setBackgroundResource(R.drawable.left_border);
                srchTwo.setBackgroundResource(R.drawable.left_border);

                if(isPageOpen){
                    //page.startAnimation(translateRightAnim);
                }else{
                    page.setVisibility(View.VISIBLE);
                    page.startAnimation(translateLeftAnim);
                }

                chooseNums[0] = first.getText().toString();
                chooseNums[1] = second.getText().toString();
                chooseNums[2] = third.getText().toString();
                chooseNums[3] = fourth.getText().toString();
                chooseNums[4] = fifth.getText().toString();
                chooseNums[5] = sixth.getText().toString();

                //numberCount = selectNumberCount("DB_LOTTO",chooseNums);

                sixCount.setText(""+numberCount.get("NUM1"));
                fifCount.setText(""+numberCount.get("NUM2"));
                fourCount.setText(""+numberCount.get("NUM3"));
                threeCount.setText(""+numberCount.get("NUM4"));
                twoCount.setText(""+numberCount.get("NUM5"));

                historyNumList = new ArrayList<>();
                historyNumList = selectHistory("DB_LOTTO",chooseNums,"5");
                historyItem = new HashMap<>();

                historyAdapter =new HistoryAdapter();


                for(int n = 0 ; n < historyNumList.size(); n++) {
                    historyItem = historyNumList.get(n);
                    historyAdapter.addItem(new NumberLine(""+historyItem.get("SCORE"), ""+historyItem.get("RND")+"회",""+historyItem.get("NUM1"), ""+historyItem.get("NUM2"), ""+historyItem.get("NUM3"), ""+historyItem.get("NUM4"), ""+historyItem.get("NUM5"), ""+historyItem.get("NUM6")));
                }

                historyView.setAdapter(historyAdapter);

                @SuppressLint("ResourceType") Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.number_list_fade_in);
                historyView.startAnimation(anim);
            }
        });
        srchFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                srchSix.setBackgroundResource(R.drawable.first_left_border);
                srchFive.setBackgroundResource(R.drawable.left_border);
                srchFour.setBackgroundResource(R.drawable.left_border_black);
                srchThree.setBackgroundResource(R.drawable.left_border);
                srchTwo.setBackgroundResource(R.drawable.left_border);

                if(isPageOpen){
                    //page.startAnimation(translateRightAnim);
                }else{
                    page.setVisibility(View.VISIBLE);
                    page.startAnimation(translateLeftAnim);
                }

                chooseNums[0] = first.getText().toString();
                chooseNums[1] = second.getText().toString();
                chooseNums[2] = third.getText().toString();
                chooseNums[3] = fourth.getText().toString();
                chooseNums[4] = fifth.getText().toString();
                chooseNums[5] = sixth.getText().toString();

                numberCount = selectNumberCount("DB_LOTTO",chooseNums);

                sixCount.setText(""+numberCount.get("NUM1"));
                fifCount.setText(""+numberCount.get("NUM2"));
                fourCount.setText(""+numberCount.get("NUM3"));
                threeCount.setText(""+numberCount.get("NUM4"));
                twoCount.setText(""+numberCount.get("NUM5"));

                historyNumList = new ArrayList<>();
                historyNumList = selectHistory("DB_LOTTO",chooseNums,"4");
                historyItem = new HashMap<>();

                historyAdapter =new HistoryAdapter();


                for(int n = 0 ; n < historyNumList.size(); n++) {
                    historyItem = historyNumList.get(n);
                    historyAdapter.addItem(new NumberLine(""+historyItem.get("SCORE"), ""+historyItem.get("RND")+"회",""+historyItem.get("NUM1"), ""+historyItem.get("NUM2"), ""+historyItem.get("NUM3"), ""+historyItem.get("NUM4"), ""+historyItem.get("NUM5"), ""+historyItem.get("NUM6")));
                }

                historyView.setAdapter(historyAdapter);

                @SuppressLint("ResourceType") Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.number_list_fade_in);
                historyView.startAnimation(anim);
            }
        });
        srchThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                srchSix.setBackgroundResource(R.drawable.first_left_border);
                srchFive.setBackgroundResource(R.drawable.left_border);
                srchFour.setBackgroundResource(R.drawable.left_border);
                srchThree.setBackgroundResource(R.drawable.left_border_black);
                srchTwo.setBackgroundResource(R.drawable.left_border);

                if(isPageOpen){
                    //page.startAnimation(translateRightAnim);
                }else{
                    page.setVisibility(View.VISIBLE);
                    page.startAnimation(translateLeftAnim);
                }

                chooseNums[0] = first.getText().toString();
                chooseNums[1] = second.getText().toString();
                chooseNums[2] = third.getText().toString();
                chooseNums[3] = fourth.getText().toString();
                chooseNums[4] = fifth.getText().toString();
                chooseNums[5] = sixth.getText().toString();

                numberCount = selectNumberCount("DB_LOTTO",chooseNums);

                sixCount.setText(""+numberCount.get("NUM1"));
                fifCount.setText(""+numberCount.get("NUM2"));
                fourCount.setText(""+numberCount.get("NUM3"));
                threeCount.setText(""+numberCount.get("NUM4"));
                twoCount.setText(""+numberCount.get("NUM5"));

                historyNumList = new ArrayList<>();
                historyNumList = selectHistory("DB_LOTTO",chooseNums,"3");
                historyItem = new HashMap<>();

                historyAdapter =new HistoryAdapter();


                for(int n = 0 ; n < historyNumList.size(); n++) {
                    historyItem = historyNumList.get(n);
                    historyAdapter.addItem(new NumberLine(""+historyItem.get("SCORE"), ""+historyItem.get("RND")+"회",""+historyItem.get("NUM1"), ""+historyItem.get("NUM2"), ""+historyItem.get("NUM3"), ""+historyItem.get("NUM4"), ""+historyItem.get("NUM5"), ""+historyItem.get("NUM6")));
                }

                historyView.setAdapter(historyAdapter);

                @SuppressLint("ResourceType") Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.number_list_fade_in);
                historyView.startAnimation(anim);
            }
        });
        srchTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                srchSix.setBackgroundResource(R.drawable.first_left_border);
                srchFive.setBackgroundResource(R.drawable.left_border);
                srchFour.setBackgroundResource(R.drawable.left_border);
                srchThree.setBackgroundResource(R.drawable.left_border);
                srchTwo.setBackgroundResource(R.drawable.left_border_black);

                if(isPageOpen){
                    //page.startAnimation(translateRightAnim);
                }else{
                    page.setVisibility(View.VISIBLE);
                    page.startAnimation(translateLeftAnim);
                }

                chooseNums[0] = first.getText().toString();
                chooseNums[1] = second.getText().toString();
                chooseNums[2] = third.getText().toString();
                chooseNums[3] = fourth.getText().toString();
                chooseNums[4] = fifth.getText().toString();
                chooseNums[5] = sixth.getText().toString();

                numberCount = selectNumberCount("DB_LOTTO",chooseNums);

                sixCount.setText(""+numberCount.get("NUM1"));
                fifCount.setText(""+numberCount.get("NUM2"));
                fourCount.setText(""+numberCount.get("NUM3"));
                threeCount.setText(""+numberCount.get("NUM4"));
                twoCount.setText(""+numberCount.get("NUM5"));

                historyNumList = new ArrayList<>();
                historyNumList = selectHistory("DB_LOTTO",chooseNums,"2");
                historyItem = new HashMap<>();

                historyAdapter =new HistoryAdapter();


                for(int n = 0 ; n < historyNumList.size(); n++) {
                    historyItem = historyNumList.get(n);
                    historyAdapter.addItem(new NumberLine(""+historyItem.get("SCORE"), ""+historyItem.get("RND")+"회",""+historyItem.get("NUM1"), ""+historyItem.get("NUM2"), ""+historyItem.get("NUM3"), ""+historyItem.get("NUM4"), ""+historyItem.get("NUM5"), ""+historyItem.get("NUM6")));
                }

                historyView.setAdapter(historyAdapter);

                @SuppressLint("ResourceType") Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.number_list_fade_in);
                historyView.startAnimation(anim);
            }
        });

        counting = findViewById(R.id.counting);
        counting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isBottomPageOpen){
                    setNearNumbers();
                    bottomPage.setVisibility(View.VISIBLE);
                    bottomPage.startAnimation(translateLeftAnimBottom);

                }
            }
        });


        n_layout = new NearNumbers(getApplicationContext());
        con = (LinearLayout)findViewById(R.id.bottomPage);
        con.addView(n_layout);

        btnButtoomSliding = con.findViewById(R.id.btnButtoomSliding);
        btnButtoomSliding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBottomPageOpen){
                    con.startAnimation(translateRightAnimBottom);
                }
            }
        });

        moveleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(topNum > 1){
                    topNum--;
                }else{
                    topNum = 6;
                }

                movePosTopNumber();
            }
        });

        moveright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(topNum < 6) {
                    topNum++;
                }else{
                    topNum = 1;
                }

                movePosTopNumber();
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNumbers();
                Toast.makeText(getApplicationContext(),"저장되었습니다.",Toast.LENGTH_SHORT).show();
                //인텐트(이동할 Activity(페이지) 생성)
                Intent intent = new Intent(getApplicationContext(), GameNumberList.class);
                startActivity(intent);
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //인텐트(이동할 Activity(페이지) 생성)
                Intent intent = new Intent(getApplicationContext(), GameNumberList.class);
                startActivity(intent);
            }
        });



        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

         /*
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        */


        recycvlerView.setLayoutManager(layoutManager);

        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

         /*
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        */


        historyView.setLayoutManager(layoutManager2);



        mnum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strfirst  =  first.getText().toString();
                strsecond = second.getText().toString();
                strthird  =  third.getText().toString();
                strfourth = fourth.getText().toString();
                strfifth  =  fifth.getText().toString();
                strsixth  =  sixth.getText().toString();

                strmnum   = mnum.getText().toString();

                if(strmnum.equals(strfirst)){
                    messageNum = strfirst;
                } else if (strmnum.equals(strsecond)) {
                    messageNum = strsecond;
                    //Toast.makeText(getApplicationContext(),strsecond+"는 이미 선택 되었습니다.",Toast.LENGTH_SHORT).show();
                } else if (strmnum.equals(strthird)) {
                    messageNum = strthird;
                    //Toast.makeText(getApplicationContext(),strthird+"는 이미 선택 되었습니다.",Toast.LENGTH_SHORT).show();
                } else if (strmnum.equals(strfourth)) {
                    messageNum = strfourth;
                    //Toast.makeText(getApplicationContext(),strfourth+"는 이미 선택 되었습니다.",Toast.LENGTH_SHORT).show();
                } else if (strmnum.equals(strfifth)) {
                    messageNum = strfifth;
                    //Toast.makeText(getApplicationContext(),strfifth+"는 이미 선택 되었습니다.",Toast.LENGTH_SHORT).show();
                } else if (strmnum.equals(strsixth)) {
                    messageNum = strsixth;
                    //Toast.makeText(getApplicationContext(),strsixth+"는 이미 선택 되었습니다.",Toast.LENGTH_SHORT).show();
                } else {

                    inputSeq = topNum-1;

                    if( inputSeq < 1) {
                        first.setText(mnum.getText());
                    } else if(inputSeq < 2){
                        second.setText(mnum.getText());
                    } else if(inputSeq < 3){
                        third.setText(mnum.getText());
                    } else if(inputSeq < 4){
                        fourth.setText(mnum.getText());
                    } else if(inputSeq < 5){
                        fifth.setText(mnum.getText());
                    } else if(inputSeq < 6){
                        sixth.setText(mnum.getText());
                        inputSeq = -1;
                    }
                    inputSeq++;

                    movePosTopNumber();

                    /* 상단 선택된 숫자들의 추첨 통계 START*/
                    chooseNums[0] = first.getText().toString();
                    chooseNums[1] = second.getText().toString();
                    chooseNums[2] = third.getText().toString();
                    chooseNums[3] = fourth.getText().toString();
                    chooseNums[4] = fifth.getText().toString();
                    chooseNums[5] = sixth.getText().toString();

                    numberCount = selectNumberCount("DB_LOTTO",chooseNums);

                    sixCount.setText(""+numberCount.get("NUM1"));
                    fifCount.setText(""+numberCount.get("NUM2"));
                    fourCount.setText(""+numberCount.get("NUM3"));
                    threeCount.setText(""+numberCount.get("NUM4"));
                    twoCount.setText(""+numberCount.get("NUM5"));

                    historyNumList = new ArrayList<>();
                    historyNumList = selectHistory("DB_LOTTO",chooseNums,"");
                    historyItem = new HashMap<>();

                    historyAdapter =new HistoryAdapter();


                    for(int n = 0 ; n < historyNumList.size(); n++) {
                        historyItem = historyNumList.get(n);
                        historyAdapter.addItem(new NumberLine(""+historyItem.get("SCORE"), ""+historyItem.get("RND")+"회",""+historyItem.get("NUM1"), ""+historyItem.get("NUM2"), ""+historyItem.get("NUM3"), ""+historyItem.get("NUM4"), ""+historyItem.get("NUM5"), ""+historyItem.get("NUM6")));
                    }

                    historyView.setAdapter(historyAdapter);

                    @SuppressLint("ResourceType") Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.number_list_fade_in);
                    historyView.startAnimation(anim);



                    historyAdapter.setOnItemClickListener(new OnHistoryItemClickListener() {
                        @Override
                        public void onItemClick(HistoryAdapter.ViewHolder holder, View view, int position) {
                            NumberLine item = historyAdapter.getItem(position);
                            //Toast.makeText(getApplicationContext(),position+"번 아이템 선택됨", Toast.LENGTH_LONG).show();
                        }
                    });


                    /* 상단 선택된 숫자들의 추첨 통계 END */

                }



                if(!messageNum.equals("")){
                    alim = Toast.makeText(getApplicationContext(),messageNum+"는 이미 선택 되었습니다.",Toast.LENGTH_SHORT);
                    alim.setGravity(Gravity.TOP, 0, 100);
                    alim.show();
                    messageNum = "";
                }
            }
        });


        count      = findViewById(R.id.count);
        rank       = findViewById(R.id.rank);
        near_one   = findViewById(R.id.near_one);
        near_two   = findViewById(R.id.near_two);
        near_three = findViewById(R.id.near_three);

        selectbar = findViewById(R.id.selectbar);


        /* 1번으로 먼저 돌려줌 */
        mnum.setText("1");
        numList = new ArrayList<>();
        numList = selectNumberRank("DB_LOTTO","1");
        item = new HashMap<>();
        item = numList.get(0);
        count.setText(""+item.get("CNT"));
        rank.setText(""+item.get("RNK"));

        nearNumList = new ArrayList<>();
        nearNumList = selectNearRank("DB_LOTTO","1", "N");

        item = new HashMap<>();
        for(int n = 0 ; n < nearNumList.size(); n++) {
            item = nearNumList.get(n);
            if(n == 0){
                near_one.setText(""+item.get("NUM"));
            }else if(n == 1){
                near_two.setText(""+item.get("NUM"));
            }else if(n == 2){
                near_three.setText(""+item.get("NUM"));
            }
            //etText(nearnum.getText()+(n > 0?" ":"")+item.get("NUM"));
        }

        nearNumList = new ArrayList<>();
        nearNumList = selectSameNumRound("DB_LOTTO","1");
        item = new HashMap<>();

        adapter =new NumbersAdapter();


        for(int n = 0 ; n < nearNumList.size(); n++) {
            item = nearNumList.get(n);
            adapter.addItem(new NumberLine(""+item.get("RND")+"회",""+item.get("NUM1"), ""+item.get("NUM2"), ""+item.get("NUM3"), ""+item.get("NUM4"), ""+item.get("NUM5"), ""+item.get("NUM6"),"1", ""+near_one.getText(), ""+near_two.getText(), ""+near_three.getText()));

        }



        recycvlerView.setAdapter(adapter);

        @SuppressLint("ResourceType") Animation animm = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.number_list_fade_in);
        recycvlerView.startAnimation(animm);
        adapter.setOnItemClickListener(new OnNumberItemClickListener() {
            @Override
            public void onItemClick(NumbersAdapter.ViewHolder holder, View view, int position) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),((TextView)v.findViewById(R.id.numone)).getText(),Toast.LENGTH_SHORT).show();
                    }
                });
                //NumberLine item = adapter.getItem(position);
                //Toast.makeText(getApplicationContext(),position+"번 아이템 선택됨", Toast.LENGTH_LONG).show();
            }
        });



        selectbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mnum.setText(""+i);
                numList = new ArrayList<>();
                numList = selectNumberRank("DB_LOTTO",""+i);
                item = new HashMap<>();
                if(!numList.isEmpty()) {
                    item = numList.get(0);
                    count.setText("" + item.get("CNT"));
                    rank.setText("" + item.get("RNK"));
                }
                nearNumList = new ArrayList<>();
                nearNumList = selectNearRank("DB_LOTTO",""+i,"N");

                item = new HashMap<>();
                for(int n = 0 ; n < nearNumList.size(); n++) {
                    item = nearNumList.get(n);
                    if(n == 0){
                        near_one.setText(""+item.get("NUM"));
                    }else if(n == 1){
                        near_two.setText(""+item.get("NUM"));
                    }else if(n == 2){
                        near_three.setText(""+item.get("NUM"));
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                String i = mnum.getText().toString();
                nearNumList = new ArrayList<>();
                nearNumList = selectSameNumRound("DB_LOTTO",i);
                item = new HashMap<>();

                adapter =new NumbersAdapter();


                for(int n = 0 ; n < nearNumList.size(); n++) {
                    item = nearNumList.get(n);
                    adapter.addItem(new NumberLine(""+item.get("RND")+"회",""+item.get("NUM1"), ""+item.get("NUM2"), ""+item.get("NUM3"), ""+item.get("NUM4"), ""+item.get("NUM5"), ""+item.get("NUM6"),i, ""+near_one.getText(), ""+near_two.getText(), ""+near_three.getText()));
                }

                recycvlerView.setAdapter(adapter);

                @SuppressLint("ResourceType") Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.number_list_fade_in);
                recycvlerView.startAnimation(anim);
                adapter.setOnItemClickListener(new OnNumberItemClickListener() {
                    @Override
                    public void onItemClick(NumbersAdapter.ViewHolder holder, View view, int position) {
                        NumberLine item = adapter.getItem(position);
                        first.setText(item.getFirst());
                        second.setText(item.getSecond());
                        third.setText(item.getThird());
                        fourth.setText(item.getFourth());
                        fifth.setText(item.getFifth());
                        sixth.setText(item.getSixth());
                    }
                });
            }
        });
    }


    private class SlideingPageAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            if(isPageOpen){
                page.setVisibility(View.INVISIBLE);

                //main_numbers.setText("Open");
                isPageOpen = false;
            }else{
                //main_numbers.setText("Close");
                isPageOpen = true;
            }
        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    private class SlideingPageAnimationListener2 implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            if(isBottomPageOpen){
                bottomPage.setVisibility(View.INVISIBLE);

                //main_numbers.setText("Open");
                isBottomPageOpen = false;
            }else{
                //main_numbers.setText("Close");
                isBottomPageOpen = true;
            }
        }

        @Override
        public void onAnimationEnd(Animation animation) {

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }


    private void saveNumbers(){

        db = openOrCreateDatabase("DB_LOTTO", MODE_PRIVATE, null);

        strfirst  = first.getText().toString();
        strsecond = second.getText().toString();
        strthird  = third.getText().toString();
        strfourth = fourth.getText().toString();
        strfifth  = fifth.getText().toString();
        strsixth  = sixth.getText().toString();

        String[] numbers = {strfirst,strsecond,strthird,strfourth,strfifth,strsixth};
        Arrays.sort(numbers);

        String currentDate = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance(Locale.KOREA).getTime());

        query = new StringBuffer("INSERT INTO ")
        .append("TB_GAMES ")
        .append("( DATE, ROUND, NUMBER1, NUMBER2, NUMBER3, NUMBER4, NUMBER5, NUMBER6 ) \n")
        .append("VALUES \n")
        .append("( '"+currentDate+"'    \n")
        .append(",(SELECT MAX(ROUND)+1 \n")
        .append("   FROM  (          \n")
        .append("           SELECT CAST (ROUND AS integer) ROUND \n")
        .append("		     FROM TB_MINFO )) \n")
        .append(", '"+numbers[0]+"'\n")
        .append(", '"+numbers[1]+"'\n")
        .append(", '"+numbers[2]+"'\n")
        .append(", '"+numbers[3]+"'\n")
        .append(", '"+numbers[4]+"'\n")
        .append(", '"+numbers[5]+"'\n")
        .append(")");

        Log.d("게임 번호 저장 쿼리",query.toString());

        db.execSQL(query.toString());
        db.close();
    }


    private void movePosTopNumber(){

        first_bar.setBackgroundColor(Color.parseColor("#094c61"));
        second_bar.setBackgroundColor(Color.parseColor("#094c61"));
        third_bar.setBackgroundColor(Color.parseColor("#094c61"));
        fourth_bar.setBackgroundColor(Color.parseColor("#094c61"));
        fifth_bar.setBackgroundColor(Color.parseColor("#094c61"));
        sixth_bar.setBackgroundColor(Color.parseColor("#094c61"));

        first_bar.setBackgroundResource(R.drawable.bar_radius_empty);
        second_bar.setBackgroundResource(R.drawable.bar_radius_empty);
        third_bar.setBackgroundResource(R.drawable.bar_radius_empty);
        fourth_bar.setBackgroundResource(R.drawable.bar_radius_empty);
        fifth_bar.setBackgroundResource(R.drawable.bar_radius_empty);
        sixth_bar.setBackgroundResource(R.drawable.bar_radius_empty);

        if(topNum == 1){
            first_bar.setBackgroundResource(R.drawable.bar_radius);
        }else if(topNum == 2){
            second_bar.setBackgroundResource(R.drawable.bar_radius);
        }else if(topNum == 3){
            third_bar.setBackgroundResource(R.drawable.bar_radius);
        }else if(topNum == 4){
            fourth_bar.setBackgroundResource(R.drawable.bar_radius);
        }else if(topNum == 5){
            fifth_bar.setBackgroundResource(R.drawable.bar_radius);
        }else if(topNum == 6){
            sixth_bar.setBackgroundResource(R.drawable.bar_radius);
        }
    }

    public void readExcel(){

            //파일 읽기
            try {
                InputStream is = getBaseContext().getResources().getAssets().open("winnumber.xls");
                //엑셀파일
                Workbook wb = Workbook.getWorkbook(is);

                //엑셀 파일이 있다면
                if (wb != null) {

                    Sheet sheet = wb.getSheet(0);//시트 블러오기

                    int colTotal = sheet.getColumns(); //전체 컬럼
                    int rowIndexStart = 2; //row 인덱스 시작
                    int rowTotal = sheet.getColumn(colTotal - 1).length;

                    List<Map<String, Object>> list = new ArrayList<>();
                    Map<String, Object> rowinto;

                    for (int row = rowIndexStart; row < rowTotal; row++) {

                        rowinto = new HashMap<>();

                        for (int col = 0; col < colTotal; col++) {
                            String contents = sheet.getCell(col, row).getContents();
                            contents = contents.replaceAll("[,|ㄱ-힣|.]", "");
                            switch (col) {
                                case 0: //회차
                                    rowinto.put("ROUND", contents);
                                    break;
                                case 1: //추첨일
                                    rowinto.put("CHOOSEDATE", contents);
                                    break;
                                case 2: //1등 당첨자 수
                                    rowinto.put("COUNT1", contents);
                                    break;
                                case 3: //1등 당첨 금액
                                    rowinto.put("AMT1", contents);
                                    break;
                                case 4: //2등 당첨자 수
                                    rowinto.put("COUNT2", contents);
                                    break;
                                case 5: //2등 당첨 금액
                                    rowinto.put("AMT2", contents);
                                    break;
                                case 6: //3등 당첨자 수
                                    rowinto.put("COUNT3", contents);
                                    break;
                                case 7: //3등 당첨 금액
                                    rowinto.put("AMT3", contents);
                                    break;
                                case 8: //4등 당첨자 수
                                    rowinto.put("COUNT4", contents);
                                    break;
                                case 9: //4등 당첨 금액
                                    rowinto.put("AMT4", contents);
                                    break;
                                case 10: //5등 당첨자 수
                                    rowinto.put("COUNT5", contents);
                                    break;
                                case 11: //5등 당첨 금액
                                    rowinto.put("AMT5", contents);
                                    break;
                                case 12: //당첨번호 1
                                    rowinto.put("NUMBER1", contents);
                                    break;
                                case 13: //당첨번호 2
                                    rowinto.put("NUMBER2", contents);
                                    break;
                                case 14: //당첨번호 3
                                    rowinto.put("NUMBER3", contents);
                                    break;
                                case 15: //당첨번호 4
                                    rowinto.put("NUMBER4", contents);
                                    break;
                                case 16: //당첨번호 5
                                    rowinto.put("NUMBER5", contents);
                                    break;
                                case 17: //당첨번호 6
                                    rowinto.put("NUMBER6", contents);
                                    break;
                                case 18: //당첨번호 보너스
                                    rowinto.put("NUMBERB", contents);
                            }
                        } //내부 For
                        list.add(rowinto);
                        //textarea.append(rowinto.get("ROUND") + "번째: "  + rowinto.get("CHOOSEDATE")+"\n");
                    }//바깥 for
                    // }//if(sheet체크)
                    readyBaseDB();
                    buildBaseData(list);
                }//if(wb체크)
            } catch (IOException | BiffException e) {
                e.printStackTrace();
            }

        db.close();
    }



/*
    private void readyDB(String name, List<Map<String,Object>> list) {
        db = openOrCreateDatabase(name, MODE_PRIVATE, null);

        StringBuffer subSql = null;
        StringBuffer sql = new StringBuffer("CREATE TABLE IF NOT EXISTS ");
        sql.append("TB_MINFO");
        sql.append(" (_id integer PRIMARY KEY autoincrement");
        sql.append(", ROUND text");
        sql.append(", CHOOSEDATE text");
        sql.append(", COUNT1 text");
        sql.append(", AMT1 integer");
        sql.append(", COUNT2 text");
        sql.append(", AMT2 integer");
        sql.append(", COUNT3 text");
        sql.append(", AMT3 integer");
        sql.append(", COUNT4 text");
        sql.append(", AMT4 text");
        sql.append(", COUNT5 text");
        sql.append(", AMT5 text");
        sql.append(", NUMBER1 text");
        sql.append(", NUMBER2 text");
        sql.append(", NUMBER3 text");
        sql.append(", NUMBER4 text");
        sql.append(", NUMBER5 text");
        sql.append(", NUMBER6 text");
        sql.append(", NUMBERB text )");
        db.execSQL(sql.toString());
        Log.d("CREATE TABLE", "TB_MINFO가 생성되었습니다.");

        sql = new StringBuffer("CREATE TABLE IF NOT EXISTS ");
        sql.append("TB_NUMBERS");
        sql.append("(_id integer PRIMARY KEY autoincrement");
        sql.append(", ROUND text ");
        sql.append(", NUMBER text )");
        db.execSQL(sql.toString());
        Log.d("CREATE TABLE", "TB_NUMBERS가 생성되었습니다.");

        sql = new StringBuffer("CREATE TABLE IF NOT EXISTS ");
        sql.append("TB_GAMES ");
        sql.append("(_id integer PRIMARY KEY autoincrement");
        sql.append(", DATE text ");
        sql.append(", ROUND text ");
        sql.append(", NUMBER1 text ");
        sql.append(", NUMBER2 text ");
        sql.append(", NUMBER3 text ");
        sql.append(", NUMBER4 text ");
        sql.append(", NUMBER5 text ");
        sql.append(", NUMBER6 text ");
        sql.append(", NUMBERB text ");
        sql.append(", RANK text ");
        sql.append(", AMT integer )");
        db.execSQL(sql.toString());
        Log.d("CREATE TABLE", "TB_GAMES가 생성되었습니다.");


        if (db == null) {
            Log.d("CREATE TABLE", "데이터베이스가 NULL입니다.");
        }else {

            Log.d("CREATE TABLE", "데이터베이스 및 테이블이 준비되었습니다.");

            String deleteQuery = "DELETE FROM TB_MINFO";
            db.execSQL(deleteQuery.toString());
            deleteQuery = "DELETE FROM TB_NUMBERS";
            db.execSQL(deleteQuery.toString());

            Map<String, Object> item = null;
            sql = new StringBuffer();
            subSql = new StringBuffer("INSERT INTO TB_NUMBERS ( ROUND, NUMBER) VALUES ");
            sql.append("INSERT INTO TB_MINFO ")
                    .append("(ROUND, CHOOSEDATE, COUNT1, AMT1, COUNT2, AMT2, COUNT3, AMT3, COUNT4, AMT4, COUNT5, AMT5 ")
                    .append(", NUMBER1, NUMBER2, NUMBER3, NUMBER4, NUMBER5, NUMBER6, NUMBERB ) VALUES");
            for (int i = 0; i < list.size(); i++) {
                item = list.get(i);
                if (i > 0) {
                    sql.append(",");
                }

                sql.append("(")
                        .append(item.get("ROUND"))
                        .append(",")
                        .append(item.get("CHOOSEDATE"))
                        .append(",")
                        .append(item.get("COUNT1"))
                        .append(",")
                        .append(item.get("AMT1"))
                        .append(",")
                        .append(item.get("COUNT2"))
                        .append(",")
                        .append(item.get("AMT2"))
                        .append(",")
                        .append(item.get("COUNT3"))
                        .append(",")
                        .append(item.get("AMT3"))
                        .append(",")
                        .append(item.get("COUNT4"))
                        .append(",")
                        .append(item.get("AMT4"))
                        .append(",")
                        .append(item.get("COUNT5"))
                        .append(",")
                        .append(item.get("AMT5"))
                        .append(",")
                        .append(item.get("NUMBER1"))
                        .append(",")
                        .append(item.get("NUMBER2"))
                        .append(",")
                        .append(item.get("NUMBER3"))
                        .append(",")
                        .append(item.get("NUMBER4"))
                        .append(",")
                        .append(item.get("NUMBER5"))
                        .append(",")
                        .append(item.get("NUMBER6"))
                        .append(",")
                        .append(item.get("NUMBERB"))
                        .append(")");


                for (int n = 0; n < 6; n++) {
                    if (i == 0 && n == 0) {
                        subSql.append("");
                    } else {
                        subSql.append(",");
                    }
                    subSql.append("(")
                            .append(item.get("ROUND"))
                            .append(",")
                            .append(item.get("NUMBER" + (n + 1)))
                            .append(")");
                }
            }

            Log.d("INSERT QUERY", sql.toString());
            //textarea.setText(sql.toString());
            db.execSQL(sql.toString());
            db.execSQL(subSql.toString());
            Log.d("RESULT", "입력 성공, 어플 기초데이터 구축 완료");
            db.close();
        }
    }
    */


    //database : DB_LOTTO , table : TB_MINFO, TB_NUMBERS, TB_GAMES 생성
    private void readyBaseDB(){
        Log.d(" readyBaseDB","접근 시작");
        db = openOrCreateDatabase(dbName, MODE_PRIVATE, null);

        StringBuffer sql = new StringBuffer("CREATE TABLE IF NOT EXISTS ");
        sql.append("TB_MINFO");
        sql.append(" (_id integer PRIMARY KEY autoincrement");
        sql.append(", ROUND text");
        sql.append(", CHOOSEDATE text");
        sql.append(", COUNT1 text");
        sql.append(", AMT1 integer");
        sql.append(", COUNT2 text");
        sql.append(", AMT2 integer");
        sql.append(", COUNT3 text");
        sql.append(", AMT3 integer");
        sql.append(", COUNT4 text");
        sql.append(", AMT4 text");
        sql.append(", COUNT5 text");
        sql.append(", AMT5 text");
        sql.append(", NUMBER1 text");
        sql.append(", NUMBER2 text");
        sql.append(", NUMBER3 text");
        sql.append(", NUMBER4 text");
        sql.append(", NUMBER5 text");
        sql.append(", NUMBER6 text");
        sql.append(", NUMBERB text )");
        db.execSQL(sql.toString());
        Log.d("CREATE TABLE", "TB_MINFO가 생성되었습니다.");

        sql = new StringBuffer("CREATE TABLE IF NOT EXISTS ");
        sql.append("TB_NUMBERS");
        sql.append("(_id integer PRIMARY KEY autoincrement");
        sql.append(", ROUND text ");
        sql.append(", NUMBER text )");
        db.execSQL(sql.toString());
        Log.d("CREATE TABLE", "TB_NUMBERS가 생성되었습니다.");

        sql = new StringBuffer("CREATE TABLE IF NOT EXISTS ");
        sql.append("TB_GAMES ");
        sql.append("(_id integer PRIMARY KEY autoincrement");
        sql.append(", DATE text ");
        sql.append(", ROUND text ");
        sql.append(", NUMBER1 text ");
        sql.append(", NUMBER2 text ");
        sql.append(", NUMBER3 text ");
        sql.append(", NUMBER4 text ");
        sql.append(", NUMBER5 text ");
        sql.append(", NUMBER6 text ");
        sql.append(", NUMBERB text ");
        sql.append(", RANK text ");
        sql.append(", AMT integer )");
        db.execSQL(sql.toString());
        Log.d("CREATE TABLE", "TB_GAMES가 생성되었습니다.");

        db.close();
    }

    //통계를 위한 데이터 구축
    private void buildBaseData( List<Map<String,Object>> list ){
        Log.d(" buildBaseData","접근 시작");

        deleteTbMinfo();

        db = openOrCreateDatabase(dbName, MODE_PRIVATE, null);

        StringBuffer sql = new StringBuffer();
        StringBuffer subSql = new StringBuffer();;

        Map<String, Object> item = null;
        sql = new StringBuffer("INSERT INTO TB_MINFO ");
        sql.append("(ROUND, CHOOSEDATE, COUNT1, AMT1, COUNT2, AMT2, COUNT3, AMT3, COUNT4, AMT4, COUNT5, AMT5 ")
                .append(", NUMBER1, NUMBER2, NUMBER3, NUMBER4, NUMBER5, NUMBER6, NUMBERB ) VALUES");

        subSql = new StringBuffer("INSERT INTO TB_NUMBERS ( ROUND, NUMBER) VALUES ");

        for (int i = 0; i < list.size(); i++) {
            item = list.get(i);
            if (i > 0) {
                sql.append(",");
            }

            sql.append("('")
                    .append(item.get("ROUND"))
                    .append("','")
                    .append(item.get("CHOOSEDATE"))
                    .append("','")
                    .append(item.get("COUNT1"))
                    .append("','")
                    .append(item.get("AMT1"))
                    .append("','")
                    .append(item.get("COUNT2"))
                    .append("','")
                    .append(item.get("AMT2"))
                    .append("','")
                    .append(item.get("COUNT3"))
                    .append("','")
                    .append(item.get("AMT3"))
                    .append("','")
                    .append(item.get("COUNT4"))
                    .append("','")
                    .append(item.get("AMT4"))
                    .append("','")
                    .append(item.get("COUNT5"))
                    .append("','")
                    .append(item.get("AMT5"))
                    .append("','")
                    .append(item.get("NUMBER1"))
                    .append("','")
                    .append(item.get("NUMBER2"))
                    .append("','")
                    .append(item.get("NUMBER3"))
                    .append("','")
                    .append(item.get("NUMBER4"))
                    .append("','")
                    .append(item.get("NUMBER5"))
                    .append("','")
                    .append(item.get("NUMBER6"))
                    .append("','")
                    .append(item.get("NUMBERB"))
                    .append("')");


            for (int n = 0; n < 6; n++) {
                if (i == 0 && n == 0) {
                    subSql.append("");
                } else {
                    subSql.append(",");
                }
                subSql.append("(")
                        .append(item.get("ROUND"))
                        .append(",")
                        .append(item.get("NUMBER" + (n + 1)))
                        .append(")");
            }
        }

        Log.d("INSERT QUERY", sql.toString());
        Log.d("INSERT QUERY", subSql.toString());

        db.execSQL(sql.toString());
        db.execSQL(subSql.toString());

        Log.d("RESULT", "입력 성공, 어플 기초데이터 구축 완료");

        db.close();
    }


    /**
     * 현재 기본 데이터가 구축 되었는지 확인
     * @return result : TB_MINFO의 데이터 개수
     */
    private int selectDataExists(){
        db = openOrCreateDatabase(dbName,MODE_PRIVATE, null);
        Cursor cursor = null;
        int result    = 0;

        query = new StringBuffer("");
        query.append("SELECT count(*) FROM TB_MINFO");

        try {
            cursor = db.rawQuery(query.toString(), null);
            int recordCount = cursor.getCount();



            for (int i = 0; i < recordCount; i++) {
                cursor.moveToNext();
                result    = cursor.getInt(0);
            }

            cursor.close();
            db.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }


    private List<Map<String,Object>> selectNumberRank(String name, String paramNum){
        db = openOrCreateDatabase(name,MODE_PRIVATE, null);
        Cursor cursor = null;

        query = new StringBuffer("");
        query.append("SELECT NUMBER,CNT,RANKING                                \n")
             .append("  FROM (                                                 \n")
             .append("        SELECT NUMBER                                    \n")
             .append("             , CNT                                       \n")
             .append("             , RANK()OVER(ORDER BY CNT DESC) AS RANKING  \n")
             .append("          FROM (SELECT NUMBER, COUNT(*) CNT              \n")
             .append("                  FROM TB_NUMBERS                        \n")
             .append("                 GROUP BY NUMBER)                        \n")
             .append("       ) WHERE NUMBER = '"+paramNum+"'                   \n");


        Log.d("NumberRank",query.toString());

        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> rsItem;
        try {
            cursor = db.rawQuery(query.toString(), null);
            int recordCount = cursor.getCount();

            String num    = "";
            String cnt    = "";
            String rank   = "";

            for (int i = 0; i < recordCount; i++) {
                cursor.moveToNext();

                num    = cursor.getString(0);
                cnt    = cursor.getString(1);
                rank   = cursor.getString(2);



                rsItem = new HashMap<>();
                rsItem.put("NUM", num );
                rsItem.put("CNT", cnt );
                rsItem.put("RNK", rank);
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


    private List<Map<String,Object>> selectNearRank(String name, String paramNum, String allGbn){
        db = openOrCreateDatabase(name,MODE_PRIVATE, null);
        Cursor cursor = null;

        query = new StringBuffer();
        query.append("SELECT NUMBER,CNT                                      \n")
                .append("  FROM (                                            \n")
                .append("        SELECT NUMBER, COUNT(*) CNT                 \n")
                .append("		  FROM (                                     \n")
                .append("                SELECT *                            \n")
                .append("                  FROM TB_NUMBERS                   \n")
                .append("				 WHERE ROUND IN (                    \n")
                .append("								 SELECT ROUND CNT    \n")
                .append("								   FROM TB_NUMBERS   \n")
                .append("								  WHERE NUMBER = '"+paramNum+"' \n")
                .append("							    )                    \n")
                .append("				)                                    \n")
                .append("		  GROUP BY NUMBER                            \n")
                .append("		  ORDER BY CNT DESC                          \n")
                .append("		)                                            \n");

                if(allGbn.equals("N")) {
                    query.append("	LIMIT 1,3                                        \n");
                }

        Log.d("NearRank",query.toString());
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> rsItem;
        try {
            cursor = db.rawQuery(query.toString(), null);
            int recordCount = cursor.getCount();

            String num    = "";
            String cnt    = "";
            String rank   = "";

            for (int i = 0; i < recordCount; i++) {
                cursor.moveToNext();

                num    = cursor.getString(0);
                cnt    = cursor.getString(1);
                rsItem = new HashMap<>();
                rsItem.put("NUM", num );
                rsItem.put("CNT", cnt );
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



    private List<Map<String,Object>> selectSameNumRound(String name, String paramNum){
        db = openOrCreateDatabase(name,MODE_PRIVATE, null);
        Cursor cursor = null;

        query = new StringBuffer();
        query.append(" SELECT CAST(ROUND AS INTEGER) AS ROUND \n")
                .append("      , NUMBER1                                 \n")
                .append("      , NUMBER2                                 \n")
                .append("      , NUMBER3                                 \n")
                .append("      , NUMBER4                                 \n")
                .append("      , NUMBER5                                 \n")
                .append("      , NUMBER6                                 \n")
                .append("   FROM TB_MINFO                                \n")
                .append("  WHERE ROUND IN (                             \n")
                .append("                  SELECT ROUND                                 \n")
                .append("                    FROM TB_NUMBERS                           \n")
                .append("                   WHERE NUMBER = '"+paramNum+"'             \n")
                .append("                  ) AND NUMBERB NOT IN ('"+paramNum+"') ORDER BY ROUND DESC \n");

        Log.d("NOW",query.toString());

        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> rsItem;
        try {
            cursor = db.rawQuery(query.toString(), null);
            int recordCount = cursor.getCount();

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

    private List<Map<String,Object>> selectHistory(String name, String[] paramNum, String matchCount){
        db = openOrCreateDatabase(name,MODE_PRIVATE, null);
        Cursor cursor = null;

        query = new StringBuffer();
        query.append(" SELECT *                                                    \n")
                .append("   FROM (                                                    \n")
                .append(" SELECT CHECK1+CHECK2+CHECK3+CHECK4+CHECK5+CHECK6 SCORE      \n")
                .append("      , ROUND                                                \n")
                .append("      , CASE CHECK1 WHEN 1 THEN NUMBER1 ELSE '0' END NUMBER1 \n")
                .append(" 	 , CASE CHECK2 WHEN 1 THEN NUMBER2 ELSE '0' END NUMBER2   \n")
                .append(" 	 , CASE CHECK3 WHEN 1 THEN NUMBER3 ELSE '0' END NUMBER3   \n")
                .append(" 	 , CASE CHECK4 WHEN 1 THEN NUMBER4 ELSE '0' END NUMBER4   \n")
                .append(" 	 , CASE CHECK5 WHEN 1 THEN NUMBER5 ELSE '0' END NUMBER5   \n")
                .append(" 	 , CASE CHECK6 WHEN 1 THEN NUMBER6 ELSE '0' END NUMBER6   \n")
                .append(" FROM (                                                      \n")
                .append(" select round                                                \n")
                .append(" ,                                                           \n")
                .append("    --FIRST                                                  \n")
                .append("        CASE NUMBER1                                         \n")
                .append("        WHEN "+paramNum[0]+"                                 \n")
                .append(" 	   THEN 1                                                 \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 		  --DEEP1                                             \n")
                .append(" 		  CASE NUMBER1                                        \n")
                .append(" 		   WHEN "+paramNum[1]+"                                             \n")
                .append(" 		   THEN 1                                             \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 				--DEEP2                                       \n")
                .append(" 			  CASE NUMBER1                                    \n")
                .append(" 			   WHEN "+paramNum[2]+"                                         \n")
                .append(" 			   THEN 1                                         \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 					--DEEP3                                   \n")
                .append(" 				  CASE NUMBER1                                \n")
                .append(" 				   WHEN "+paramNum[3]+"                                     \n")
                .append(" 				   THEN 1                                     \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 						--DEEP4                               \n")
                .append(" 					  CASE NUMBER1                            \n")
                .append(" 					   WHEN "+paramNum[4]+"                                 \n")
                .append(" 					   THEN 1                                 \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 							--DEEP6                           \n")
                .append(" 							  CASE NUMBER1                    \n")
                .append(" 							   WHEN "+paramNum[5]+"                         \n")
                .append(" 							   THEN 1                         \n")
                .append(" 							   ELSE 0                         \n")
                .append(" 							    END                           \n")
                .append(" 					    END                                   \n")
                .append(" 				    END                                       \n")
                .append(" 			    END                                           \n")
                .append(" 		    END                                               \n")
                .append(" 	    END  CHECK1                                           \n")
                .append(" 	                                                          \n")
                .append(" 	   ,                                                      \n")
                .append(" 	   NUMBER1                                                \n")
                .append(" 	   ,                                                      \n")
                .append(" 	                                                          \n")
                .append(" 	  CASE NUMBER2                                            \n")
                .append("        WHEN "+paramNum[0]+"                                               \n")
                .append(" 	   THEN 1                                                 \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 		  --DEEP1                                             \n")
                .append(" 		  CASE NUMBER2                                        \n")
                .append(" 		   WHEN "+paramNum[1]+"                                             \n")
                .append(" 		   THEN 1                                             \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 				--DEEP2                                       \n")
                .append(" 			  CASE NUMBER2                                    \n")
                .append(" 			   WHEN "+paramNum[2]+"                                         \n")
                .append(" 			   THEN 1                                         \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 					--DEEP3                                   \n")
                .append(" 				  CASE NUMBER2                                \n")
                .append(" 				   WHEN "+paramNum[3]+"                                     \n")
                .append(" 				   THEN 1                                     \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 						--DEEP4                               \n")
                .append(" 					  CASE NUMBER2                            \n")
                .append(" 					   WHEN "+paramNum[4]+"                                 \n")
                .append(" 					   THEN 1                                 \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 							--DEEP6                           \n")
                .append(" 							  CASE NUMBER2                    \n")
                .append(" 							   WHEN "+paramNum[5]+"                         \n")
                .append(" 							   THEN 1                         \n")
                .append(" 							   ELSE 0                         \n")
                .append(" 							    END                           \n")
                .append(" 					    END                                   \n")
                .append(" 				    END                                       \n")
                .append(" 			    END                                           \n")
                .append(" 		    END                                               \n")
                .append(" 	    END  CHECK2                                           \n")
                .append(" 	                                                          \n")
                .append(" 	   ,                                                      \n")
                .append(" 	   NUMBER2                                                \n")
                .append(" 	   ,                                                      \n")
                .append(" 	                                                          \n")
                .append(" 	  CASE NUMBER3                                            \n")
                .append("        WHEN "+paramNum[0]+"                                               \n")
                .append(" 	   THEN 1                                                 \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 		  --DEEP1                                             \n")
                .append(" 		  CASE NUMBER3                                        \n")
                .append(" 		   WHEN "+paramNum[1]+"                                             \n")
                .append(" 		   THEN 1                                             \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 				--DEEP2                                       \n")
                .append(" 			  CASE NUMBER3                                    \n")
                .append(" 			   WHEN "+paramNum[2]+"                                         \n")
                .append(" 			   THEN 1                                         \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 					--DEEP3                                   \n")
                .append(" 				  CASE NUMBER3                                \n")
                .append(" 				   WHEN "+paramNum[3]+"                                     \n")
                .append(" 				   THEN 1                                     \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 						--DEEP4                               \n")
                .append(" 					  CASE NUMBER3                            \n")
                .append(" 					   WHEN "+paramNum[4]+"                                 \n")
                .append(" 					   THEN 1                                 \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 							--DEEP6                           \n")
                .append(" 							  CASE NUMBER3                    \n")
                .append(" 							   WHEN "+paramNum[5]+"                         \n")
                .append(" 							   THEN 1                         \n")
                .append(" 							   ELSE 0                         \n")
                .append(" 							    END                           \n")
                .append(" 					    END                                   \n")
                .append(" 				    END                                       \n")
                .append(" 			    END                                           \n")
                .append(" 		    END                                               \n")
                .append(" 	    END  CHECK3                                           \n")
                .append(" 	                                                          \n")
                .append(" 	   ,                                                      \n")
                .append(" 	   NUMBER3                                                \n")
                .append(" 	   ,                                                      \n")
                .append(" 	                                                          \n")
                .append(" 	  CASE NUMBER4                                            \n")
                .append("        WHEN "+paramNum[0]+"                                               \n")
                .append(" 	   THEN 1                                                 \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 		  --DEEP1                                             \n")
                .append(" 		  CASE NUMBER4                                        \n")
                .append(" 		   WHEN "+paramNum[1]+"                                             \n")
                .append(" 		   THEN 1                                             \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 				--DEEP2                                       \n")
                .append(" 			  CASE NUMBER4                                    \n")
                .append(" 			   WHEN "+paramNum[2]+"                                         \n")
                .append(" 			   THEN 1                                         \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 					--DEEP3                                   \n")
                .append(" 				  CASE NUMBER4                                \n")
                .append(" 				   WHEN "+paramNum[3]+"                                     \n")
                .append(" 				   THEN 1                                     \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 						--DEEP4                               \n")
                .append(" 					  CASE NUMBER4                            \n")
                .append(" 					   WHEN "+paramNum[4]+"                                 \n")
                .append(" 					   THEN 1                                 \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 							--DEEP6                           \n")
                .append(" 							  CASE NUMBER4                    \n")
                .append(" 							   WHEN "+paramNum[5]+"                         \n")
                .append(" 							   THEN 1                         \n")
                .append(" 							  ELSE 0                          \n")
                .append(" 							    END                           \n")
                .append(" 					    END                                   \n")
                .append(" 				    END                                       \n")
                .append(" 			    END                                           \n")
                .append(" 		    END                                               \n")
                .append(" 	    END  CHECK4 	                                      \n")
                .append(" 	                                                          \n")
                .append(" 	   ,                                                      \n")
                .append(" 	   NUMBER4                                                \n")
                .append(" 	   ,                                                      \n")
                .append(" 	   CASE NUMBER5                                           \n")
                .append("        WHEN "+paramNum[0]+"                                               \n")
                .append(" 	   THEN 1                                                 \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 		  --DEEP1                                             \n")
                .append(" 		  CASE NUMBER5                                        \n")
                .append(" 		   WHEN "+paramNum[1]+"                                            \n")
                .append(" 		   THEN 1                                             \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 				--DEEP2                                       \n")
                .append(" 			  CASE NUMBER5                                    \n")
                .append(" 			   WHEN "+paramNum[2]+"                                         \n")
                .append(" 			   THEN 1                                         \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 					--DEEP3                                   \n")
                .append(" 				  CASE NUMBER5                                \n")
                .append(" 				   WHEN "+paramNum[3]+"                                     \n")
                .append(" 				   THEN 1                                     \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 						--DEEP4                               \n")
                .append(" 					  CASE NUMBER5                            \n")
                .append(" 					   WHEN "+paramNum[4]+"                                 \n")
                .append(" 					   THEN 1                                 \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 							--DEEP6                           \n")
                .append(" 							  CASE NUMBER5                    \n")
                .append(" 							   WHEN "+paramNum[5]+"                         \n")
                .append(" 							   THEN 1                         \n")
                .append(" 							   ELSE 0                         \n")
                .append(" 							    END                           \n")
                .append(" 					    END                                   \n")
                .append(" 				    END                                       \n")
                .append(" 			    END                                           \n")
                .append(" 		    END                                               \n")
                .append(" 	    END  CHECK5                                           \n")
                .append(" 	                                                          \n")
                .append(" 	                                                          \n")
                .append(" 	   ,                                                      \n")
                .append(" 	   NUMBER5                                                \n")
                .append(" 	   ,                                                      \n")
                .append(" 	                                                          \n")
                .append(" 	   CASE NUMBER6                                           \n")
                .append("        WHEN "+paramNum[0]+"                                               \n")
                .append(" 	   THEN 1                                                 \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 		  --DEEP1                                             \n")
                .append(" 		  CASE NUMBER6                                        \n")
                .append(" 		   WHEN "+paramNum[1]+"                                             \n")
                .append(" 		   THEN 1                                             \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 				--DEEP2                                       \n")
                .append(" 			  CASE NUMBER6                                    \n")
                .append(" 			   WHEN "+paramNum[2]+"                                         \n")
                .append(" 			   THEN 1                                         \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 					--DEEP3                                   \n")
                .append(" 				  CASE NUMBER6                                \n")
                .append(" 				   WHEN "+paramNum[3]+"                                     \n")
                .append(" 				   THEN 1                                     \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 						--DEEP4                               \n")
                .append(" 					  CASE NUMBER6                            \n")
                .append(" 					   WHEN "+paramNum[4]+"                                 \n")
                .append(" 					   THEN 1                                 \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 							--DEEP6                           \n")
                .append(" 							  CASE NUMBER6                    \n")
                .append(" 							   WHEN "+paramNum[5]+"                         \n")
                .append(" 							   THEN 1                         \n")
                .append(" 							   ELSE 0                         \n")
                .append(" 							    END                           \n")
                .append(" 					    END                                   \n")
                .append(" 				    END                                       \n")
                .append(" 			    END                                           \n")
                .append(" 		    END                                               \n")
                .append(" 	    END  CHECK6                                           \n")
                .append(" 		,                                                     \n")
                .append(" 		NUMBER6	                                              \n")
                .append("   from tb_minfo                                             \n")
                .append("   ) MT                                                      \n");

        if(matchCount.equals("")) {
            query.append("  )MMT WHERE SCORE > 1 ORDER BY CAST(round  as integer) DESC       \n");
        }else{
            query.append("  )MMT WHERE SCORE = "+matchCount+" ORDER BY CAST(round  as integer) DESC       \n");
        }
        Log.d("NOW",query.toString());

        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> rsItem;
        try {
            cursor = db.rawQuery(query.toString(), null);
            int recordCount = cursor.getCount();
            String scr   = "";
            String rnd   = "";
            String num1  = "";
            String num2  = "";
            String num3  = "";
            String num4  = "";
            String num5  = "";
            String num6  = "";


            for (int i = 0; i < recordCount; i++) {
                cursor.moveToNext();
                scr  = cursor.getString(0);
                rnd  = cursor.getString(1);
                num1 = cursor.getString(2);
                num2 = cursor.getString(3);
                num3 = cursor.getString(4);
                num4 = cursor.getString(5);
                num5 = cursor.getString(6);
                num6 = cursor.getString(7);

                num1 = num1.equals("0")?"":num1;
                num2 = num2.equals("0")?"":num2;
                num3 = num3.equals("0")?"":num3;
                num4 = num4.equals("0")?"":num4;
                num5 = num5.equals("0")?"":num5;
                num6 = num6.equals("0")?"":num6;

                rsItem = new HashMap<>();
                rsItem.put("SCR", scr );
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


    private Map<String, Object> selectNumberCount(String name, String[] paramNum){
        db = openOrCreateDatabase(name,MODE_PRIVATE, null);
        Cursor cursor = null;

        query = new StringBuffer();
        query.append(" SELECT SUM(CASE SCORE WHEN 6 THEN 1 ELSE 0 END) SIX_COUND   \n")
                .append("      , SUM(CASE SCORE WHEN 5 THEN 1 ELSE 0 END) FIF_COUNT   \n")
                .append(" 	 , SUM(CASE SCORE WHEN 4 THEN 1 ELSE 0 END) FOUR_COUNT    \n")
                .append(" 	 , SUM(CASE SCORE WHEN 3 THEN 1 ELSE 0 END) THREE_COUNT   \n")
                .append(" 	 , SUM(CASE SCORE WHEN 2 THEN 1 ELSE 0 END) TWO_COUNT     \n")
                .append("   FROM (                                                    \n")
                .append(" SELECT CHECK1+CHECK2+CHECK3+CHECK4+CHECK5+CHECK6 SCORE      \n")
                .append("      , ROUND                                                \n")
                .append("      , CASE CHECK1 WHEN 1 THEN NUMBER1 ELSE '0' END NUMBER1 \n")
                .append(" 	 , CASE CHECK2 WHEN 1 THEN NUMBER2 ELSE '0' END NUMBER2   \n")
                .append(" 	 , CASE CHECK3 WHEN 1 THEN NUMBER3 ELSE '0' END NUMBER3   \n")
                .append(" 	 , CASE CHECK4 WHEN 1 THEN NUMBER4 ELSE '0' END NUMBER4   \n")
                .append(" 	 , CASE CHECK5 WHEN 1 THEN NUMBER5 ELSE '0' END NUMBER5   \n")
                .append(" 	 , CASE CHECK6 WHEN 1 THEN NUMBER6 ELSE '0' END NUMBER6   \n")
                .append(" FROM (                                                      \n")
                .append(" select round                                                \n")
                .append(" ,                                                           \n")
                .append("    --FIRST                                                  \n")
                .append("        CASE NUMBER1                                         \n")
                .append("        WHEN "+paramNum[0]+"                                 \n")
                .append(" 	   THEN 1                                                 \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 		  --DEEP1                                             \n")
                .append(" 		  CASE NUMBER1                                        \n")
                .append(" 		   WHEN "+paramNum[1]+"                                             \n")
                .append(" 		   THEN 1                                             \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 				--DEEP2                                       \n")
                .append(" 			  CASE NUMBER1                                    \n")
                .append(" 			   WHEN "+paramNum[2]+"                                         \n")
                .append(" 			   THEN 1                                         \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 					--DEEP3                                   \n")
                .append(" 				  CASE NUMBER1                                \n")
                .append(" 				   WHEN "+paramNum[3]+"                                     \n")
                .append(" 				   THEN 1                                     \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 						--DEEP4                               \n")
                .append(" 					  CASE NUMBER1                            \n")
                .append(" 					   WHEN "+paramNum[4]+"                                 \n")
                .append(" 					   THEN 1                                 \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 							--DEEP6                           \n")
                .append(" 							  CASE NUMBER1                    \n")
                .append(" 							   WHEN "+paramNum[5]+"                         \n")
                .append(" 							   THEN 1                         \n")
                .append(" 							   ELSE 0                         \n")
                .append(" 							    END                           \n")
                .append(" 					    END                                   \n")
                .append(" 				    END                                       \n")
                .append(" 			    END                                           \n")
                .append(" 		    END                                               \n")
                .append(" 	    END  CHECK1                                           \n")
                .append(" 	                                                          \n")
                .append(" 	   ,                                                      \n")
                .append(" 	   NUMBER1                                                \n")
                .append(" 	   ,                                                      \n")
                .append(" 	                                                          \n")
                .append(" 	  CASE NUMBER2                                            \n")
                .append("        WHEN "+paramNum[0]+"                                               \n")
                .append(" 	   THEN 1                                                 \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 		  --DEEP1                                             \n")
                .append(" 		  CASE NUMBER2                                        \n")
                .append(" 		   WHEN "+paramNum[1]+"                                             \n")
                .append(" 		   THEN 1                                             \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 				--DEEP2                                       \n")
                .append(" 			  CASE NUMBER2                                    \n")
                .append(" 			   WHEN "+paramNum[2]+"                                         \n")
                .append(" 			   THEN 1                                         \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 					--DEEP3                                   \n")
                .append(" 				  CASE NUMBER2                                \n")
                .append(" 				   WHEN "+paramNum[3]+"                                     \n")
                .append(" 				   THEN 1                                     \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 						--DEEP4                               \n")
                .append(" 					  CASE NUMBER2                            \n")
                .append(" 					   WHEN "+paramNum[4]+"                                 \n")
                .append(" 					   THEN 1                                 \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 							--DEEP6                           \n")
                .append(" 							  CASE NUMBER2                    \n")
                .append(" 							   WHEN "+paramNum[5]+"                         \n")
                .append(" 							   THEN 1                         \n")
                .append(" 							   ELSE 0                         \n")
                .append(" 							    END                           \n")
                .append(" 					    END                                   \n")
                .append(" 				    END                                       \n")
                .append(" 			    END                                           \n")
                .append(" 		    END                                               \n")
                .append(" 	    END  CHECK2                                           \n")
                .append(" 	                                                          \n")
                .append(" 	   ,                                                      \n")
                .append(" 	   NUMBER2                                                \n")
                .append(" 	   ,                                                      \n")
                .append(" 	                                                          \n")
                .append(" 	  CASE NUMBER3                                            \n")
                .append("        WHEN "+paramNum[0]+"                                               \n")
                .append(" 	   THEN 1                                                 \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 		  --DEEP1                                             \n")
                .append(" 		  CASE NUMBER3                                        \n")
                .append(" 		   WHEN "+paramNum[1]+"                                             \n")
                .append(" 		   THEN 1                                             \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 				--DEEP2                                       \n")
                .append(" 			  CASE NUMBER3                                    \n")
                .append(" 			   WHEN "+paramNum[2]+"                                         \n")
                .append(" 			   THEN 1                                         \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 					--DEEP3                                   \n")
                .append(" 				  CASE NUMBER3                                \n")
                .append(" 				   WHEN "+paramNum[3]+"                                     \n")
                .append(" 				   THEN 1                                     \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 						--DEEP4                               \n")
                .append(" 					  CASE NUMBER3                            \n")
                .append(" 					   WHEN "+paramNum[4]+"                                 \n")
                .append(" 					   THEN 1                                 \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 							--DEEP6                           \n")
                .append(" 							  CASE NUMBER3                    \n")
                .append(" 							   WHEN "+paramNum[5]+"                         \n")
                .append(" 							   THEN 1                         \n")
                .append(" 							   ELSE 0                         \n")
                .append(" 							    END                           \n")
                .append(" 					    END                                   \n")
                .append(" 				    END                                       \n")
                .append(" 			    END                                           \n")
                .append(" 		    END                                               \n")
                .append(" 	    END  CHECK3                                           \n")
                .append(" 	                                                          \n")
                .append(" 	   ,                                                      \n")
                .append(" 	   NUMBER3                                                \n")
                .append(" 	   ,                                                      \n")
                .append(" 	                                                          \n")
                .append(" 	  CASE NUMBER4                                            \n")
                .append("        WHEN "+paramNum[0]+"                                               \n")
                .append(" 	   THEN 1                                                 \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 		  --DEEP1                                             \n")
                .append(" 		  CASE NUMBER4                                        \n")
                .append(" 		   WHEN "+paramNum[1]+"                                             \n")
                .append(" 		   THEN 1                                             \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 				--DEEP2                                       \n")
                .append(" 			  CASE NUMBER4                                    \n")
                .append(" 			   WHEN "+paramNum[2]+"                                         \n")
                .append(" 			   THEN 1                                         \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 					--DEEP3                                   \n")
                .append(" 				  CASE NUMBER4                                \n")
                .append(" 				   WHEN "+paramNum[3]+"                                     \n")
                .append(" 				   THEN 1                                     \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 						--DEEP4                               \n")
                .append(" 					  CASE NUMBER4                            \n")
                .append(" 					   WHEN "+paramNum[4]+"                                 \n")
                .append(" 					   THEN 1                                 \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 							--DEEP6                           \n")
                .append(" 							  CASE NUMBER4                    \n")
                .append(" 							   WHEN "+paramNum[5]+"                         \n")
                .append(" 							   THEN 1                         \n")
                .append(" 							  ELSE 0                          \n")
                .append(" 							    END                           \n")
                .append(" 					    END                                   \n")
                .append(" 				    END                                       \n")
                .append(" 			    END                                           \n")
                .append(" 		    END                                               \n")
                .append(" 	    END  CHECK4 	                                      \n")
                .append(" 	                                                          \n")
                .append(" 	   ,                                                      \n")
                .append(" 	   NUMBER4                                                \n")
                .append(" 	   ,                                                      \n")
                .append(" 	   CASE NUMBER5                                           \n")
                .append("        WHEN "+paramNum[0]+"                                               \n")
                .append(" 	   THEN 1                                                 \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 		  --DEEP1                                             \n")
                .append(" 		  CASE NUMBER5                                        \n")
                .append(" 		   WHEN "+paramNum[1]+"                                            \n")
                .append(" 		   THEN 1                                             \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 				--DEEP2                                       \n")
                .append(" 			  CASE NUMBER5                                    \n")
                .append(" 			   WHEN "+paramNum[2]+"                                         \n")
                .append(" 			   THEN 1                                         \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 					--DEEP3                                   \n")
                .append(" 				  CASE NUMBER5                                \n")
                .append(" 				   WHEN "+paramNum[3]+"                                     \n")
                .append(" 				   THEN 1                                     \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 						--DEEP4                               \n")
                .append(" 					  CASE NUMBER5                            \n")
                .append(" 					   WHEN "+paramNum[4]+"                                 \n")
                .append(" 					   THEN 1                                 \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 							--DEEP6                           \n")
                .append(" 							  CASE NUMBER5                    \n")
                .append(" 							   WHEN "+paramNum[5]+"                         \n")
                .append(" 							   THEN 1                         \n")
                .append(" 							   ELSE 0                         \n")
                .append(" 							    END                           \n")
                .append(" 					    END                                   \n")
                .append(" 				    END                                       \n")
                .append(" 			    END                                           \n")
                .append(" 		    END                                               \n")
                .append(" 	    END  CHECK5                                           \n")
                .append(" 	                                                          \n")
                .append(" 	                                                          \n")
                .append(" 	   ,                                                      \n")
                .append(" 	   NUMBER5                                                \n")
                .append(" 	   ,                                                      \n")
                .append(" 	                                                          \n")
                .append(" 	   CASE NUMBER6                                           \n")
                .append("        WHEN "+paramNum[0]+"                                               \n")
                .append(" 	   THEN 1                                                 \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 		  --DEEP1                                             \n")
                .append(" 		  CASE NUMBER6                                        \n")
                .append(" 		   WHEN "+paramNum[1]+"                                             \n")
                .append(" 		   THEN 1                                             \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 				--DEEP2                                       \n")
                .append(" 			  CASE NUMBER6                                    \n")
                .append(" 			   WHEN "+paramNum[2]+"                                         \n")
                .append(" 			   THEN 1                                         \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 					--DEEP3                                   \n")
                .append(" 				  CASE NUMBER6                                \n")
                .append(" 				   WHEN "+paramNum[3]+"                                     \n")
                .append(" 				   THEN 1                                     \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 						--DEEP4                               \n")
                .append(" 					  CASE NUMBER6                            \n")
                .append(" 					   WHEN "+paramNum[4]+"                                 \n")
                .append(" 					   THEN 1                                 \n")
                .append(" 	   ELSE                                                   \n")
                .append(" 							--DEEP6                           \n")
                .append(" 							  CASE NUMBER6                    \n")
                .append(" 							   WHEN "+paramNum[5]+"                         \n")
                .append(" 							   THEN 1                         \n")
                .append(" 							   ELSE 0                         \n")
                .append(" 							    END                           \n")
                .append(" 					    END                                   \n")
                .append(" 				    END                                       \n")
                .append(" 			    END                                           \n")
                .append(" 		    END                                               \n")
                .append(" 	    END  CHECK6                                           \n")
                .append(" 		,                                                     \n")
                .append(" 		NUMBER6	                                              \n")
                .append("   from tb_minfo                                             \n")
                .append("   ) MT                                                      \n")
                .append("  )                                                          \n");

        Log.d("COUNT",query.toString());

        Map<String, Object> rsItem = new HashMap<>();;
        try {
            cursor = db.rawQuery(query.toString(), null);
            int recordCount = cursor.getCount();

            String num1  = "";
            String num2  = "";
            String num3  = "";
            String num4  = "";
            String num5  = "";

            cursor.moveToNext();
            num1 = cursor.getString(0);
            num2 = cursor.getString(1);
            num3 = cursor.getString(2);
            num4 = cursor.getString(3);
            num5 = cursor.getString(4);

            rsItem.put("NUM1", num1 );
            rsItem.put("NUM2", num2 );
            rsItem.put("NUM3", num3 );
            rsItem.put("NUM4", num4 );
            rsItem.put("NUM5", num5 );


            cursor.close();
            db.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return rsItem;
    }


    private Map<String, Object> selectLastNumber(String name){
        db = openOrCreateDatabase(name,MODE_PRIVATE, null);
        Cursor cursor = null;

        query = new StringBuffer();
        query.append(" SELECT NUMBER1                           \n")
                .append("    , NUMBER2                           \n")
                .append(" 	 , NUMBER3                             \n")
                .append(" 	 , NUMBER4                             \n")
                .append(" 	 , NUMBER5                             \n")
                .append(" 	 , NUMBER6                             \n")
                .append("   FROM TB_MINFO                          \n")
                .append("  ORDER BY CAST( ROUND AS INTEGER ) DESC  \n")
                .append("  LIMIT 0,1                               \n");

        Log.d("COUNT",query.toString());

        Map<String, Object> rsItem = new HashMap<>();;
        try {
            cursor = db.rawQuery(query.toString(), null);
            int recordCount = cursor.getCount();

            String num1  = "";
            String num2  = "";
            String num3  = "";
            String num4  = "";
            String num5  = "";
            String num6  = "";

            cursor.moveToNext();
            num1 = cursor.getString(0);
            num2 = cursor.getString(1);
            num3 = cursor.getString(2);
            num4 = cursor.getString(3);
            num5 = cursor.getString(4);
            num6 = cursor.getString(5);

            rsItem.put("NUM1", num1 );
            rsItem.put("NUM2", num2 );
            rsItem.put("NUM3", num3 );
            rsItem.put("NUM4", num4 );
            rsItem.put("NUM5", num5 );
            rsItem.put("NUM6", num6 );

            cursor.close();
            db.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return rsItem;
    }

    private void deleteTbMinfo(){
        db = openOrCreateDatabase(dbName,MODE_PRIVATE, null);
        query = new StringBuffer();
        query.append(" DELETE FROM TB_MINFO");
        db.execSQL(query.toString());
        query = new StringBuffer();
        query.append(" DELETE FROM TB_NUMBERS");
        db.execSQL(query.toString());
        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume",new SimpleDateFormat("yyyyMMdd kk:mm:ss").format(Calendar.getInstance(Locale.KOREA).getTime())); //new SimnpleDateFormat("yyyyMMdd kk:mm:ss").format(Calendar.getInstance(Locale.KOREA).getTime());
        updateNewGame();
    }

    private void updateNewGame(){
        new Thread(){
            @Override
            public void run() {
                Document doc = null;
                Elements elemens = null;
                Elements elemensBonus = null;

                int maxRound = selectMaxRound();

                String bunus = "";
                String round = "";
                int ordCount = 0;
                List<Map<String,Object>> list = new ArrayList();
                Map<String, Object> winNum;
                List<String> numbers;



                try {
                    Log.d("CHECK NEW ROUND","시작");
                    doc = Jsoup.connect("https://dhlottery.co.kr/gameResult.do?method=byWin").get();
                    Elements contents = doc.select(".win_result h4 strong");          //회차 id값 가져오기
                    round = contents.text().replaceAll("회","");
                    Log.d("CHECK NEW ROUND","조회된 회차");
                    ordCount = Integer.parseInt(round) - maxRound;

                    Log.d("CHECK NEW ROUND","비교차 : "+ordCount);

                    if(ordCount > 0){

                        for(int r = 0 ; r < ordCount; r++) {
                            winNum = new HashMap<>();
                            doc = Jsoup.connect("https://dhlottery.co.kr/gameResult.do?method=byWin&drwNo="+(maxRound+(r+1))).get();

                            Log.d("CHECK NEW ROUND","URL : https://dhlottery.co.kr/gameResult.do?method=byWin&drwNo="+(maxRound+(r+1)));

                            contents = doc.select(".win_result h4 strong");          //회차 id값 가져오기
                            round = contents.text().replaceAll("회", "");

                            Log.d("ROUND", selectMaxRound() + " : " + round);

                            elemens = doc.select(".win .ball_645");
                            elemensBonus = doc.select(".bonus .ball_645");

                            numbers = elemens.eachText();

                            for(int i = 1; i <= numbers.size(); i++ ){
                                winNum.put("NUMBER"+i, numbers.get(i-1));
                            }
                            winNum.put("NUMBERB", elemensBonus.text()) ;
                            winNum.put("ROUND", round) ;
                            winNum.put("CHOOSEDATE",new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance(Locale.KOREA).getTime()));
                            winNum.put("COUNT1","");
                            winNum.put("AMT1","");
                            winNum.put("COUNT2", "");
                            winNum.put("AMT2","");
                            winNum.put("COUNT3","");
                            winNum.put("AMT3","");
                            winNum.put("COUNT4","");
                            winNum.put("AMT4","");
                            winNum.put("COUNT5","");
                            winNum.put("AMT5","");

                            list.add(winNum);
                        }

                        buildBaseData(list);



                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }


    private void insertNewNumbers(List<String> numbers){
        db = openOrCreateDatabase("DB_LOTTO", MODE_PRIVATE, null);

        String currentDate = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance(Locale.KOREA).getTime());

        query = new StringBuffer("INSERT INTO ")
                .append("TB_MINFO ")
                .append("( ROUND	   \n")
                .append(", CHOOSEDATE  \n")
                .append(", COUNT1	   \n")
                .append(", AMT1	       \n")
                .append(", COUNT2	   \n")
                .append(", AMT2	       \n")
                .append(", COUNT3	   \n")
                .append(", AMT3	       \n")
                .append(", COUNT4	   \n")
                .append(", AMT4	       \n")
                .append(", COUNT5	   \n")
                .append(", AMT5	       \n")
                .append(", NUMBER1	   \n")
                .append(", NUMBER2	   \n")
                .append(", NUMBER3	   \n")
                .append(", NUMBER4	   \n")
                .append(", NUMBER5	   \n")
                .append(", NUMBER6	   \n")
                .append(", NUMBERB	  )\n")
                .append("VALUES \n")
                .append("( '"+numbers.get(7)+"'	   \n")
                .append(", '"+currentDate+"'  \n")
                .append(", ''	   \n")
                .append(", ''	       \n")
                .append(", ''	   \n")
                .append(", ''	       \n")
                .append(", ''	   \n")
                .append(", ''	       \n")
                .append(", ''	   \n")
                .append(", ''	       \n")
                .append(", ''	   \n")
                .append(", ''	       \n")
                .append(", '"+numbers.get(0)+"'\n")
                .append(", '"+numbers.get(1)+"'\n")
                .append(", '"+numbers.get(2)+"'\n")
                .append(", '"+numbers.get(3)+"'\n")
                .append(", '"+numbers.get(4)+"'\n")
                .append(", '"+numbers.get(5)+"'\n")
                .append(", '"+numbers.get(6)+"')");

        Log.d("게임 번호 저장 쿼리",query.toString());

        db.execSQL(query.toString());
        db.close();
    }

    private int selectMaxRound(){

        int num1  = 0;

        db = openOrCreateDatabase("DB_LOTTO",MODE_PRIVATE, null);
        Cursor cursor = null;

        query = new StringBuffer("SELECT");
        query.append(" MAX(CAST(ROUND as INTEGER)) ROUND  \n")
                .append("FROM TB_MINFO \n");

        Log.d("COUNT",query.toString());

        Map<String, Object> rsItem = new HashMap<>();;
        try {
            cursor = db.rawQuery(query.toString(), null);
            cursor.moveToNext();
            num1 = cursor.getInt(0);
            cursor.close();
            db.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return num1;
    }

    private void setNearNumbers(){

        /*
        100 % — FF
        95 % — F2
        90 % — E6
        85 % — D9
        80 % — CC
        75 % — BF
        70 % — B3
        65 % — A6
        60 % — 99
        55 % — 8C
        50 % — 80
        45 % — 73
        40 % — 66
        35 % — 59
        30 % — 4D
        25 % — 40
        20 % — 33
        15 % — 26
        10 % - 1A
        5 % — 0D
        0 % — 00
        */
        String[] opacity = {"1A","26","33","40","4D","59","66","73","80","8C","99","A6","B3","BF","CC","D9","E6","F2","FF"};


       // Toast.makeText(getApplicationContext(),"setNearNumbers 접근",Toast.LENGTH_SHORT).show();

        List<Map<String, Object>> nearNumAllList;
        nearNumAllList = selectNearRank("DB_LOTTO",""+mnum.getText(),"Y");
        TextView txtView;
        String pack = getPackageName();
        Map<String, Object> nearItemNum;
        for(int i = 1; i <= nearNumAllList.size(); i++){
            //item = new HashMap<>();
            nearItemNum = nearNumAllList.get(i-1);
            viewId = getResources().getIdentifier("near"+i, type, pack);
            Log.d("NEAR_NUM_ID"+i,""+viewId);
            txtView = con.findViewById(viewId);
            txtView.setText(""+nearItemNum.get("NUM"));


            String cnt = ""+nearItemNum.get("CNT");
            int rs = Integer.parseInt(cnt)/2;
            int opacityPos = (rs <= 18?rs:18);
            String opaColor = "#"+opacity[opacityPos]+"ebc334";

            txtView.setBackgroundColor(Color.parseColor(opaColor));
        }
    }
}