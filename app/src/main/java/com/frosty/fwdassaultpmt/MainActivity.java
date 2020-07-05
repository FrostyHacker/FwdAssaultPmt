package com.frosty.fwdassaultpmt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class MainActivity extends Activity {

    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2002;
    public String iconColor = "Cyan";
    private String greetingMessage = "Mod menu made by Jasmur";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, this.greetingMessage, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Source code by Frosty Hacker", Toast.LENGTH_SHORT).show();
        new File(Environment.getExternalStorageDirectory().toString() + "/pmt/images").mkdirs();
        new Thread(new Runnable() {
            public void run() {
                try {
                    FileWriter writer = new FileWriter(Environment.getExternalStorageDirectory().getPath() + "/pmt/images/.nomedia");
                    writer.write("");
                    writer.close();
                    InputStream in = new BufferedInputStream(new URL(MainActivity.this.GrabIcon(MainActivity.this.iconColor)).openStream());
                    OutputStream out = new BufferedOutputStream(new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/pmt/images/pmt_icon_round.png"));
                    while (true) {
                        int read = in.read();
                        int i = read;
                        if (read == -1) {
                            break;
                        }
                        out.write(i);
                    }
                    in.close();
                    out.close();
                    InputStream in2 = new BufferedInputStream(new URL("https://i.ibb.co/m0YtPJN/rsz-111548062311009.png").openStream());
                    OutputStream out2 = new BufferedOutputStream(new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/pmt/images/pmt_icon_round_menu.png"));
                    while (true) {
                        int read2 = in2.read();
                        int i2 = read2;
                        if (read2 != -1) {
                            out2.write(i2);
                        } else {
                            in2.close();
                            out2.close();
                            return;
                        }
                    }
                } catch (Exception e) {
                }
            }
        }).start();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Start();
            }
        }, 5000);

        //Start();
    }

    private String GrabIcon(final String s) {
        if (s == "Cyan") {
            return "https://image.ibb.co/jawP3V/1541544806255.png";
        }
        if (s == "Blue") {
            return "https://i.ibb.co/QjPY3k9/1548062479496.png";
        }
        if (s == "Green") {
            return "https://i.ibb.co/bRcm1JH/1548062576329.png";
        }
        if (s == "Yellow") {
            return "https://i.ibb.co/z2DvYPc/1548062748884.png";
        }
        if (s == "Orange") {
            return "https://i.ibb.co/SJdXKyG/1548062536315.png";
        }
        if (s == "Pink") {
            return "https://i.ibb.co/1nZVzms/1548062383462.png";
        }
        if (s == "Red") {
            return "https://i.ibb.co/FHSDD74/1548062311009.png";
        }
        if (s == "Black") {
            return "https://i.ibb.co/Xjwh11P/1548062604921.png";
        }
        if (s == "White") {
            return "https://i.ibb.co/09nq6g1/1548062711383.png";
        }
        return "";
    }
    public void Start() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        } else {
            startService(new Intent(MainActivity.this, MenuActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            if (resultCode == RESULT_OK) {
                startService(new Intent(MainActivity.this, MenuActivity.class));
            } else {
                Toast.makeText(this, "Draw over other app permission not available. Closing the application", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}