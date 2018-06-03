package com.example.vudou.weatherapp;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by vudou on 10/18/2017.
 */


public class CustomDialog extends Dialog implements android.view.View.OnClickListener {
    private Activity activity;
    private Button btnConfirm, btnCancel;
    private EditText edtCity;
    private TextView txtMess;
//    OnMyDialogResult myDialogResult;


    public CustomDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    //Hàm khởi tạo
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set layout
        setContentView(R.layout.custom_dialog);
        //Ánh xạ đến custom_dialog layout
        btnConfirm = (Button) findViewById(R.id.dialog_confirm);
        btnCancel = (Button) findViewById(R.id.dialog_cancel);
        edtCity = (EditText) findViewById(R.id.dialog_editext);
        txtMess = (TextView) findViewById(R.id.dialog_mess);
        //Gắn sự kiện
        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    //Hàm xử lý luồng sự kiện
    public void onClick(View view) {
        switch (view.getId()) {
            //Khi bấm nút Xác nhận
            case R.id.dialog_confirm:
                doConfirm();
                break;
            //Khi bấm nút Hủy
            case R.id.dialog_cancel:
                dismiss();
                break;
            default:
                break;
        }
    }

    //Hàm xử lý khi bấm nút xác nhận
    public void doConfirm() {
        //Lấy string email từ edittext
        if(edtCity.getText().equals("")) {
            txtMess.setText("Please enter city's name");
        } else {
            String name = edtCity.getText().toString();
            //Kiểm tra edittext email có trống không
            Intent intent = new Intent("cityAdd");
            intent.putExtra("cityName", edtCity.getText().toString());

        }

    }

//    public void setDialogResult(OnMyDialogResult dialogResult) {
//        myDialogResult = dialogResult;
//    }
//
//    public interface OnMyDialogResult {
//        public void finish(String result);
//    }

}