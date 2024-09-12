package com.cookbook.goodluckallpeaplelife;

import android.util.Log;

public class NumberLine {
    String score,season, first, second, third, fourth, fifth, sixth, cnum, near_one, near_two,near_three;
    int limit;
    public NumberLine(String season,String first, String second, String third, String fourth, String fifth, String sixth, String cnum, String near_one, String near_two, String near_three){
        this.season = season;
        this.first  = first ;
        this.second = second;
        this.third  = third ;
        this.fourth = fourth;
        this.fifth  = fifth ;
        this.sixth  = sixth ;
        this.cnum   = cnum;
        this.near_one = near_one;
        this.near_two = near_two;
        this.near_three = near_three;
    }

    public NumberLine(String score,String season,String first, String second, String third, String fourth, String fifth, String sixth){
        this.score = score;
        this.season = season;
        this.first  = first ;
        this.second = second;
        this.third  = third ;
        this.fourth = fourth;
        this.fifth  = fifth ;
        this.sixth  = sixth ;
        this.cnum   = "0";
        this.near_one = "0";
        this.near_two = "0";
        this.near_three = "0";
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getNear_one() {
        return near_one;
    }

    public void setNear_one(String near_one) {
        this.near_one = near_one;
    }

    public String getNear_two() {
        return near_two;
    }

    public void setNear_two(String near_two) {
        this.near_two = near_two;
    }

    public String getNear_three() {
        return near_three;
    }

    public void setNear_three(String near_three) {
        this.near_three = near_three;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getThird() {
        return third;
    }

    public void setThird(String third) {
        this.third = third;
    }

    public String getFourth() {
        return fourth;
    }

    public void setFourth(String fourth) {
        this.fourth = fourth;
    }

    public String getFifth() {
        return fifth;
    }

    public void setFifth(String fifth) {
        this.fifth = fifth;
    }

    public String getSixth() {
        return sixth;
    }

    public void setSixth(String sixth) {
        this.sixth = sixth;
    }

    public String getCnum() {
        return cnum;
    }

    public void setCnum(String cnum) {
        this.cnum = cnum;
    }
}
