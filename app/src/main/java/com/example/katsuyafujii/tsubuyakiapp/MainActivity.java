package com.example.katsuyafujii.tsubuyakiapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemLongClickListener{

    //つぶやき表示用リストビュー
    private ListView tsubuyakiLV;

    //つぶやき入力欄
    private EditText commentEtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        Tsubuyaki t = new Tsubuyaki(1,"つぶやきテスト");
//        t.save();//テーブルにデータ保存
//
//
//        List<Tsubuyaki> list = Tsubuyaki.listAll(Tsubuyaki.class);
//        for(Tsubuyaki tsubuyaki: list){
//        Log.d("データベーステスト",tsubuyaki.comment) ;
//        }
        //レイアウトより，つぶやくボタンの情報を取得
        Button commitBtn = (Button) findViewById(R.id.main_commit_btn);
        commitBtn.setOnClickListener(this);


        //レイアウトより，リストビューの情報を取得
        tsubuyakiLV = (ListView) findViewById(R.id.main_tsubuyaki_lv);
        tsubuyakiLV.setOnItemLongClickListener(this);


       // リストビューの内容を更新する
        updateListView();
    }

    @Override
    public void onClick(View v) {
        //レイアウトより，入力欄の情報を取得
        commentEtx = (EditText) findViewById(R.id.main_comment_etx);

        Tsubuyaki tsubuyaki = new Tsubuyaki();
        tsubuyaki.id = 1;
        tsubuyaki.comment = commentEtx.getText().toString();
        tsubuyaki.save(); //テーブルに保存

        updateListView();
        commentEtx.setText("");//入力蘭を空ににする
    }

    //リストビューの内容を更新する
    private void updateListView(){

        //テーブルから全てのデータを取得
        List<Tsubuyaki> list = Tsubuyaki.listAll(Tsubuyaki.class);
        //降順(ASC)大きい順
        //昇順(DESC)小さい順
        list = Tsubuyaki.listAll(Tsubuyaki.class,"ID DESC");

        //リストビューにデータをセット
        //Adapter:特定のデータをひとまとめにしてビューに渡すときに利用する
        AdapterListTsubuyaki adapter =
                new AdapterListTsubuyaki(this,R.layout.list_tsubuyaki,list);

        tsubuyakiLV.setAdapter(adapter);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long l) {
        //長押しされたリストビューの項目を取得
        ListView list =(ListView) parent;
        //項目の情報を取得
        final Tsubuyaki selectedItem = (Tsubuyaki) list.getItemAtPosition(i);

        //警告ダイアログを出す
        new AlertDialog.Builder(this)
                .setTitle("警告")
                .setMessage("削除してもよろしいですか？")
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        //OK button pressed
                        //テーブルから該当するIDの項目の削除
                        Tsubuyaki tsubuyaki = Tsubuyaki.findById(Tsubuyaki.class, selectedItem.getId());
                        tsubuyaki.delete();

                        //削除を反映させるためのリストビュー更新
                        updateListView();
                    }
                })
                .setNegativeButton("Cancel",null)
                .show();

        return false;
    }
}
