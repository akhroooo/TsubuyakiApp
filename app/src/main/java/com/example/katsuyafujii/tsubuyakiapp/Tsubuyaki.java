package com.example.katsuyafujii.tsubuyakiapp;

import com.orm.SugarRecord;

/**
 * Created by katsuyafujii on 5/11/16.
 */
public class Tsubuyaki extends SugarRecord  {

    //ID
    public long id;

    //
    public String comment;

    public Tsubuyaki(){}

    public Tsubuyaki(long id,String comment){
        this.id = id;
        this.comment = comment;

    }
}
