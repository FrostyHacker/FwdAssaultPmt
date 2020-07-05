package com.frosty.fwdassaultpmt;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.core.view.MotionEventCompat;

import java.io.File;

public class MenuActivity extends Service {
    public static boolean isNowReady;
    public static boolean landscapegame;
    public static boolean resetAvailable;
    private static int[] tSize;
    private int DPI;
    private AlertDialog alertDialog;
    private String borderColor;
    private String buttonBackgroundColor;
    private String buttonTextColor;
    private String creditsText;
    private int height;
    private RelativeLayout menuHolder;
    private int menuStyle;
    private LinearLayout modHolder;
    private String nonToggleTextColor;
    private int overlay;
    private int scrollHeight;
    private int switchColorOff;
    private int switchColorOn;
    /* access modifiers changed from: private */
    public String switchOffTextColor;
    /* access modifiers changed from: private */
    public String switchOnTextColor;
    private String titleText;
    private RelativeLayout toggleHolder;
    /* access modifiers changed from: private */
    public String[] weaponList;
    private int width;

    /*public static native void bunny();

    public static native void damage(int i);

    public static native void fieldofview(int i);

    public static native void flash();

    public static native void flinch();

    public static native void fly();

    public static native void lowgravity(int i);

    public static native void minimap();

    public static native void nameban();

    public static native void nogravity();

    public static native void norecoil();

    public static native void nospread();

    public static native void rapidfire(int i);

    public static native void refillammo();

    public static native void spamchat();

    public static native void spamflash();

    public static native void spamgrenade();

    public static native void spamsmoke();

    public static native void submitInfo(int i);

    public static native void superjump();

    public static native void trigger();

    public static native void weaponselected(int i);

    public static native void youdontexist();*/

    WindowManager windowManager;
    WindowManager.LayoutParams layoutParams;
    RelativeLayout holder;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        isNowReady = true;
        resetAvailable = false;
        CustomizeMenu();
        MenuSystem();
    }

    private void CustomizeMenu() {
        this.switchOnTextColor = "#00FF00";
        this.switchOffTextColor = "#FF0000";
        this.borderColor = "#FFFFFF";
        this.switchColorOn = MotionEventCompat.ACTION_POINTER_INDEX_MASK;
        this.switchColorOff = 16711680;
        this.buttonTextColor = "#FF0000";
        this.buttonBackgroundColor = "#00FF00";
        this.nonToggleTextColor = "#FFFFFF";
        this.menuStyle = 1;
        this.titleText = "Fmods - Forward Assault";
        this.creditsText = "Source code by Frosty Hacker";
        landscapegame = true;
    }

    private void MenuSystem() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= 26) {
            this.overlay = 2038;
        } else {
            this.overlay = 2002;
        }
        this.width = windowManager.getDefaultDisplay().getWidth();
        this.height = windowManager.getDefaultDisplay().getHeight();
        this.DPI = getResources().getDisplayMetrics().densityDpi;
        //submitInfo(this.DPI);
        layoutParams = new WindowManager.LayoutParams(-2, -2, this.overlay, 8, -3);
        layoutParams.gravity = 17;
        layoutParams.x = 0;
        layoutParams.y = 0;
        holder = new RelativeLayout(this);
        holder.setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        windowManager.addView(holder, layoutParams);
        this.menuHolder = holder;
        this.menuHolder.setVisibility(View.VISIBLE);
        SetupNewMenu();
        holder.setOnTouchListener(new View.OnTouchListener() {
            private float initialTouchX;
            private float initialTouchY;
            private int initialX;
            private int initialY;

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case 0:
                        this.initialX = layoutParams.x;
                        this.initialY = layoutParams.y;
                        this.initialTouchX = event.getRawX();
                        this.initialTouchY = event.getRawY();
                        return true;
                    case 1:
                        int Xdiff = (int) (event.getRawX() - this.initialTouchX);
                        int Ydiff = (int) (event.getRawY() - this.initialTouchY);
                        if (Xdiff < 10 && Ydiff < 10) {
                            MenuActivity.this.DisplayMenu();
                            Toast.makeText(MenuActivity.this, "Source code by Frosty Hacker", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    case 2:
                        layoutParams.x = this.initialX + ((int) (event.getRawX() - this.initialTouchX));
                        layoutParams.y = this.initialY + ((int) (event.getRawY() - this.initialTouchY));
                        windowManager.updateViewLayout(holder, layoutParams);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void SetupNewMenu() {
        RelativeLayout iconView = new RelativeLayout(this);
        ImageView pmtIcon = new ImageView(this);
        ScrollView scrollView = new ScrollView(this);
        RelativeLayout togglesHolder = new RelativeLayout(this);
        LinearLayout modsHolder = new LinearLayout(this);
        this.modHolder = modsHolder;
        this.toggleHolder = togglesHolder;
        if (landscapegame) {
            double d = (double) this.height;
            Double.isNaN(d);
            this.scrollHeight = (int) Math.round(d / 2.0d);
        } else {
            double d2 = (double) this.height;
            Double.isNaN(d2);
            this.scrollHeight = (int) Math.round(d2 / 3.25d);
        }
        RelativeLayout.LayoutParams iconViewParam = new RelativeLayout.LayoutParams(-2, -2);
        ViewGroup.LayoutParams pmtIconParam = new ViewGroup.LayoutParams(this.width / 7, this.height / 6);
        LinearLayout.LayoutParams scrollViewParam = new LinearLayout.LayoutParams(-1, this.scrollHeight);
        RelativeLayout.LayoutParams togglesHolderParam = new RelativeLayout.LayoutParams(-1, -2);
        iconView.setLayoutParams(iconViewParam);
        pmtIcon.setLayoutParams(pmtIconParam);
        togglesHolder.setLayoutParams(togglesHolderParam);
        scrollView.setLayoutParams(scrollViewParam);
        this.modHolder.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        this.modHolder.setOrientation(LinearLayout.VERTICAL);
        this.menuHolder.addView(iconView);
        iconView.addView(pmtIcon);
        togglesHolder.addView(scrollView);
        scrollView.addView(modsHolder);
        File myFile = new File(Environment.getExternalStorageDirectory().getPath() + "/pmt/images/pmt_icon_round.png");
        File myFile2 = new File(Environment.getExternalStorageDirectory().getPath() + "/pmt/images/pmt_icon_round_menu.png");
        Bitmap bmp = BitmapFactory.decodeFile(myFile.getAbsolutePath());
        Bitmap bmp2 = BitmapFactory.decodeFile(myFile2.getAbsolutePath());
        pmtIcon.setImageBitmap(bmp);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bmp2);
        this.alertDialog = new AlertDialog.Builder(this, this.menuStyle).setIcon(bitmapDrawable).setTitle(this.titleText).setMessage(this.creditsText).setView(pmtIcon).create();
        this.alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor(this.borderColor)));
        DisplayMenu();
        AddModifications();
    }

    private void AddModifications() {
        AddToggle("Name Spoof / Ban Help", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton cb, boolean toggled) {
                if (toggled) {
                    Toast.makeText(MenuActivity.this, "Dev Name Activated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MenuActivity.this, "Dev Name Deactivated", Toast.LENGTH_SHORT).show();
                }
                //MenuActivity.nameban();
            }
        });
        AddToggle("Fly Mode", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton cb, boolean toggled) {
                if (toggled) {
                    Toast.makeText(MenuActivity.this, "Fly Mode Activated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MenuActivity.this, "Fly Mode Deactivated", Toast.LENGTH_SHORT).show();
                }
                //MenuActivity.fly();
            }
        });
        AddToggle("Triggerbot", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton cb, boolean toggled) {
                if (toggled) {
                    Toast.makeText(MenuActivity.this, "Triggerbot Activated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MenuActivity.this, "Triggerbot Deactivated", Toast.LENGTH_SHORT).show();
                }
                //MenuActivity.trigger();
            }
        });
        AddToggle("Bunny Hop", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton cb, boolean toggled) {
                if (toggled) {
                    Toast.makeText(MenuActivity.this, "Bunny Hop Activated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MenuActivity.this, "Bunny Hop Deactivated", Toast.LENGTH_SHORT).show();
                }
                //MenuActivity.bunny();
            }
        });
        AddToggle("Radar", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton cb, boolean toggled) {
                if (toggled) {
                    Toast.makeText(MenuActivity.this, "Radar Activated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MenuActivity.this, "Radar Deactivated", Toast.LENGTH_SHORT).show();
                }
               // MenuActivity.minimap();
            }
        });
        AddToggle("Infinite Ammo", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton cb, boolean toggled) {
                if (toggled) {
                    Toast.makeText(MenuActivity.this, "Infinite Ammo Activated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MenuActivity.this, "Infinite Ammo Deactivated", Toast.LENGTH_SHORT).show();
                }
                //MenuActivity.refillammo();
            }
        });
        AddToggle("No Spread", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton cb, boolean toggled) {
                if (toggled) {
                    Toast.makeText(MenuActivity.this, "No Spread Activated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MenuActivity.this, "No Spread Deactivated", Toast.LENGTH_SHORT).show();
                }
                //MenuActivity.nospread();
            }
        });
        AddSeekBar("Damage Modifier", 100, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i == 0) {
                    //MenuActivity.damage(1337);
                    Toast.makeText(MenuActivity.this, "Damage Set To Normal", Toast.LENGTH_SHORT).show();
                    return;
                }
                MenuActivity menuActivity = MenuActivity.this;
                Toast.makeText(menuActivity, "Damage Added: " + String.valueOf(i), Toast.LENGTH_SHORT).show();
                //MenuActivity.damage(i);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
       CreateWeaponList();
        AddSpinner("Change Weapon", this.weaponList, new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MenuActivity menuActivity = MenuActivity.this;
                Toast.makeText(menuActivity, "Weapon Selected: " + MenuActivity.this.weaponList[i], Toast.LENGTH_SHORT).show();
                //MenuActivity.weaponselected(i);
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.parseColor(MenuActivity.this.switchOnTextColor));
                if (i == 0 || i == 7 || i == 11 || i == 17 || i == 18) {
                    ((TextView) adapterView.getChildAt(0)).setTextColor(Color.parseColor(MenuActivity.this.switchOffTextColor));
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        AddToggle("No Recoil", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton cb, boolean toggled) {
                if (toggled) {
                    Toast.makeText(MenuActivity.this, "No Recoil Activated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MenuActivity.this, "No Recoil Deactivated", Toast.LENGTH_SHORT).show();
                }
                //MenuActivity.norecoil();
            }
        });
        AddToggle("Spam Chat", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton cb, boolean toggled) {
                if (toggled) {
                    Toast.makeText(MenuActivity.this, "Spam Chat Activated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MenuActivity.this, "Spam Chat Deactivated", Toast.LENGTH_SHORT).show();
                }
                //MenuActivity.spamchat();
            }
        });
        AddToggle("No Flinch", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton cb, boolean toggled) {
                if (toggled) {
                    Toast.makeText(MenuActivity.this, "No Flinch Activated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MenuActivity.this, "No Flinch Deactivated", Toast.LENGTH_SHORT).show();
                }
                //MenuActivity.flinch();
            }
        });
        AddToggle("Spam Grenade", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton cb, boolean toggled) {
                if (toggled) {
                    Toast.makeText(MenuActivity.this, "Spam Grenade Activated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MenuActivity.this, "Spam Grenade Deactivated", Toast.LENGTH_SHORT).show();
                }
                //MenuActivity.spamgrenade();
            }
        });
        AddToggle("Spam Smoke", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton cb, boolean toggled) {
                if (toggled) {
                    Toast.makeText(MenuActivity.this, "Spam Smoke Activated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MenuActivity.this, "Spam Smoke Deactivated", Toast.LENGTH_SHORT).show();
                }
                //MenuActivity.spamsmoke();
            }
        });
        AddToggle("Spam Flash", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton cb, boolean toggled) {
                if (toggled) {
                    Toast.makeText(MenuActivity.this, "Spam Flash Activated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MenuActivity.this, "Spam Flash Deactivated", Toast.LENGTH_SHORT).show();
                }
                //MenuActivity.spamflash();
            }
        });
        AddToggle("No Flash", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton cb, boolean toggled) {
                if (toggled) {
                    Toast.makeText(MenuActivity.this, "No Flash Activated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MenuActivity.this, "No Flash Deactivated", Toast.LENGTH_SHORT).show();
                }
                //MenuActivity.flash();
            }
        });
        AddToggle("No Gravity", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton cb, boolean toggled) {
                if (toggled) {
                    Toast.makeText(MenuActivity.this, "No Gravity Activated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MenuActivity.this, "No Gravity Deactivated", Toast.LENGTH_SHORT).show();
                }
                //MenuActivity.nogravity();
            }
        });
        AddSeekBar("Field of View Modifier", 4, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                MenuActivity menuActivity = MenuActivity.this;
                Toast.makeText(menuActivity, "Field of View Set To x" + String.valueOf(i), Toast.LENGTH_SHORT).show();
                //MenuActivity.fieldofview(i);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        AddSeekBar("Jump Gravity Modifier", 4, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                MenuActivity menuActivity = MenuActivity.this;
                Toast.makeText(menuActivity, "Jump Gravity Set To x" + String.valueOf(i), Toast.LENGTH_SHORT).show();
                //MenuActivity.lowgravity(i);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        AddSeekBar("Fire Rate Modifier", 4, new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                MenuActivity menuActivity = MenuActivity.this;
                Toast.makeText(menuActivity, "Fire Rate Set To x" + String.valueOf(i), Toast.LENGTH_SHORT).show();
                //MenuActivity.rapidfire(i);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        AddToggle("Crouch Height", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton cb, boolean toggled) {
                if (toggled) {
                    Toast.makeText(MenuActivity.this, "Crouch Height Activated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MenuActivity.this, "Crouch Height Deactivated", Toast.LENGTH_SHORT).show();
                }
               // MenuActivity.superjump();
            }
        });
        AddToggle("You Don't Exist", new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton cb, boolean toggled) {
                if (toggled) {
                    Toast.makeText(MenuActivity.this, "You Don't Exist Activated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MenuActivity.this, "You Don't Exist Deactivated", Toast.LENGTH_SHORT).show();
                }
                //MenuActivity.youdontexist();
            }
        });
    }

    private void CreateWeaponList() {
        this.weaponList = new String[33];
        String[] strArr = this.weaponList;
        strArr[0] = "None";
        strArr[1] = "AK47";
        strArr[2] = "Vector";
        strArr[3] = "DesertEagle";
        strArr[4] = "FAMAS";
        strArr[5] = "M4A1";
        strArr[6] = "M40";
        strArr[7] = "FragGrenade";
        strArr[8] = "FiveSeven";
        strArr[9] = "Spas12";
        strArr[10] = "MP7";
        strArr[11] = "C4";
        strArr[12] = "P250";
        strArr[13] = "M98";
        strArr[14] = "Knife";
        strArr[15] = "CX70";
        strArr[16] = "A91";
        strArr[17] = "SmokeGrenade";
        strArr[18] = "Flashbang";
        strArr[19] = "PP2000";
        strArr[20] = "MP5K";
        strArr[21] = "Karambit";
        strArr[22] = "ButterflyKnife";
        strArr[23] = "Tec9";
        strArr[24] = "M1014";
        strArr[25] = "RFB";
        strArr[26] = "Glock";
        strArr[27] = "Uzi";
        strArr[28] = "FAL";
        strArr[29] = "Hatchet";
        strArr[30] = "MP9";
        strArr[31] = "P90";
        strArr[32] = "AWP";
    }

    /* access modifiers changed from: private */
    public void DisplayMenu() {
        this.alertDialog.getWindow().setType(this.overlay);
        this.alertDialog.setView(this.toggleHolder);
        this.alertDialog.show();
    }

    private void AddSeekBar(String text, int movement, SeekBar.OnSeekBarChangeListener listener) {
        TextView textV = new TextView(this);
        textV.setText(text);
        textV.setTextColor(Color.parseColor(this.nonToggleTextColor));
        textV.setTextSize((float) 13);
        RelativeLayout.LayoutParams textVP = new RelativeLayout.LayoutParams(-2, -2);
        textV.setLayoutParams(textVP);
        textVP.setMargins(this.width / 100, 0, 0, 0);
        this.modHolder.addView(textV);
        SeekBar seeker = new SeekBar(this);
        seeker.setMax(movement);
        seeker.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
        seeker.setOnSeekBarChangeListener(listener);
        this.modHolder.addView(seeker);
    }

    private void AddToggle(String text, CompoundButton.OnCheckedChangeListener listener) {
        final Switch toggle = new Switch(this);
        toggle.setText(text);
        toggle.setTextSize(13);
        toggle.setTextColor(Color.parseColor(this.switchOffTextColor));
        toggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (toggle.isChecked()) {
                    toggle.setTextColor(Color.parseColor(MenuActivity.this.switchOnTextColor));
                } else {
                    toggle.setTextColor(Color.parseColor(MenuActivity.this.switchOffTextColor));
                }
            }
        });
        final StateListDrawable thumbDrawable = new StateListDrawable();
        thumbDrawable.addState(new int[] { 16842912 },new ColorDrawable(this.switchColorOn));
        thumbDrawable.addState(new int[0], new ColorDrawable(this.switchColorOff));
        toggle.setThumbDrawable(thumbDrawable);
        toggle.setOnCheckedChangeListener(listener);
        RelativeLayout.LayoutParams toggleP = new RelativeLayout.LayoutParams(-1, -2);
        toggleP.setMargins(this.width / 100, 0, 0, 0);
        toggle.setLayoutParams(toggleP);
        this.modHolder.addView(toggle);
    }

    private void AddSpinner(String name, String[] list, AdapterView.OnItemSelectedListener listener) {
        TextView textV = new TextView(this);
        textV.setText(name);
        textV.setTextSize((float) 13);
        textV.setTextColor(Color.parseColor(this.nonToggleTextColor));
        RelativeLayout.LayoutParams textVP = new RelativeLayout.LayoutParams(-1, -2);
        textVP.setMargins(this.width / 100, 0, 0, 0);
        textV.setLayoutParams(textVP);
        Spinner sp = new Spinner(this);
        sp.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(dataAdapter);
        sp.setOnItemSelectedListener(listener);
        this.modHolder.addView(textV);
        this.modHolder.addView(sp);
    }

    private void AddButton(String text, View.OnClickListener listener) {
        Button button = new ToggleButton(this);
        button.setText(text);
        button.setTextSize((float) 13);
        button.setOnClickListener(listener);
        button.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
        button.setScaleX(0.75f);
        button.setScaleY(0.75f);
        this.modHolder.addView(button);
    }

    private void AddMultipliers(String text, String multiplied, String divided, View.OnClickListener listener, View.OnClickListener listener2) {
        LinearLayout multi = new LinearLayout(this);
        multi.setOrientation(LinearLayout.HORIZONTAL);
        multi.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        this.modHolder.addView(multi);
        TextView textV = new TextView(this);
        textV.setText(text);
        textV.setTextColor(Color.parseColor(this.nonToggleTextColor));
        textV.setTextSize((float) 13);
        RelativeLayout.LayoutParams textVP = new RelativeLayout.LayoutParams(-2, -2);
        textV.setLayoutParams(textVP);
        textVP.setMargins(this.width / 100, 0, 0, 0);
        multi.addView(textV);
        Button button = new Button(this);
        button.setText(multiplied);
        button.setTextSize((float) 13);
        button.setTextColor(Color.parseColor(this.buttonTextColor));
        button.setBackgroundColor(Color.parseColor(this.buttonBackgroundColor));
        button.setOnClickListener(listener);
        button.setScaleX(0.75f);
        button.setScaleY(0.75f);
        RelativeLayout.LayoutParams buttonP = new RelativeLayout.LayoutParams(-2, -2);
        buttonP.setMargins(this.width / 125, 0, 0, 0);
        button.setLayoutParams(buttonP);
        multi.addView(button);
        Button button2 = new Button(this);
        button2.setText(divided);
        button2.setTextSize((float) 13);
        button2.setTextColor(Color.parseColor(this.buttonBackgroundColor));
        button2.setBackgroundColor(Color.parseColor(this.buttonTextColor));
        button2.setOnClickListener(listener2);
        button2.setScaleX(0.75f);
        button2.setScaleY(0.75f);
        button2.setLayoutParams(buttonP);
        multi.addView(button2);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        stopSelf();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (holder != null){
            windowManager.removeView(holder);
            alertDialog.cancel();
        }
    }
}