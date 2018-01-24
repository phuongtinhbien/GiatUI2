package com.example.vuphu.giatui2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRCodeScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView qrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_scan);
        qrcode = findViewById(R.id.qrcode);
        qrcode.setResultHandler(this);
        qrcode.startCamera();
    }

    @Override
    public void handleResult(Result result) {

        Toast.makeText(this, result.getText(), Toast.LENGTH_SHORT).show();
        qrcode.resumeCameraPreview(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        qrcode.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrcode.stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        qrcode.stopCamera();
    }

    public void exit(View view) {
        finish();
    }
}
