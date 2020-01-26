package com.lk.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    private Button but;//
    private EditText et1;
    private EditText et2;
    private TextView TVstatus;
    private String xm;
    private String ccdm;
    private String slot1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        but=(Button)findViewById(R.id.button1);
        et1=(EditText)findViewById(R.id.editText1);
        et2=(EditText)findViewById(R.id.editText2);
        TVstatus=(TextView)findViewById(R.id.TVstatus);
        xm="test";
        ccdm="test";
        slot1="test";
        but.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                //测试使用System.out.println("hello..........");
                //要成功进行跳转到intent这个对象
                //第一个参数是原来的类，出发站以.this结尾
                //第二个参数是要跳转的类，结束站以class结尾
                //Intent in=new Intent(MainActivity.this,SecActivity.class);
                //startActivity(in);
                TVstatus.setText("Logging");
                Toast.makeText(Main2Activity.this, "Logging", Toast.LENGTH_SHORT).show();
                //new Thread(networkTask).start();
//                String tmp=TVstatus.getText().toString();
                Intent in = new Intent(Main2Activity.this, Recog.class);
                in.putExtra("ccdm",ccdm);
                in.putExtra("xm",xm);
                in.putExtra("slot1",slot1);
                startActivity(in);
//                Login_ButtonClick(username, pwd);
            }



        });

    }
}
