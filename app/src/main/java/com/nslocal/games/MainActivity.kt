<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/root_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:background="@color/bg_dark"
    android:padding="20dp"
    android:gravity="center_horizontal"
    android:scrollbars="vertical">

    <TextView android:layout_width="wrap_content" android:layout_height="wrap_content"
        android:text="NSlocal Games" android:textSize="28sp" android:textColor="@color/primary"
        android:textStyle="bold" android:layout_marginTop="16dp" android:layout_marginBottom="4dp"/>
    <TextView android:layout_width="wrap_content" android:layout_height="wrap_content"
        android:text="Performance • Cooling • Overlay" android:textSize="13sp" android:textColor="@color/text_gray"
        android:layout_marginBottom="24dp"/>

    <LinearLayout android:id="@+id/card_status" android:layout_width="match_parent" android:layout_height="wrap_content"
        android:orientation="vertical" android:background="@drawable/glass_card" android:padding="20dp"
        android:layout_marginBottom="16dp" android:gravity="center_horizontal">
        <TextView android:id="@+id/tvDeviceInfo" android:layout_width="match_parent" android:layout_height="wrap_content"
            android:text="Loading..." android:textColor="@color/text_white" android:textSize="14sp" android:gravity="center"/>
        <LinearLayout android:layout_width="match_parent" android:layout_height="wrap_content"
            android:orientation="horizontal" android:layout_marginTop="16dp" android:gravity="center">
            <LinearLayout android:layout_width="0dp" android:layout_height="wrap_content" android:layout_weight="1" android:gravity="center">
                <TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="FPS" android:textColor="@color/text_gray" android:textSize="12sp"/>
                <TextView android:id="@+id/tvFps" android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="--" android:textColor="@color/fps_green" android:textSize="18sp" android:textStyle="bold"/>
            </LinearLayout>
            <LinearLayout android:layout_width="0dp" android:layout_height="wrap_content" android:layout_weight="1" android:gravity="center">
                <TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="CPU" android:textColor="@color/text_gray" android:textSize="12sp"/>
                <TextView android:id="@+id/tvCpu" android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="--" android:textColor="@color/cpu_white" android:textSize="18sp" android:textStyle="bold"/>
            </LinearLayout>
            <LinearLayout android:layout_width="0dp" android:layout_height="wrap_content" android:layout_weight="1" android:gravity="center">
                <TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="BATT" android:textColor="@color/text_gray" android:textSize="12sp"/>
                <TextView android:id="@+id/tvBat" android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="--" android:textColor="@color/battery_red" android:textSize="18sp" android:textStyle="bold"/>
            </LinearLayout>
            <LinearLayout android:layout_width="0dp" android:layout_height="wrap_content" android:layout_weight="1" android:gravity="center">
                <TextView android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="TEMP" android:textColor="@color/text_gray" android:textSize="12sp"/>
                <TextView android:id="@+id/tvTemp" android:layout_width="wrap_content" android:layout_height="wrap_content" android:text="--" android:textColor="@color/temp_yellow" android:textSize="18sp" android:textStyle="bold"/>
            </LinearLayout>
        </LinearLayout>
    </LinearLayout>

    <Button android:id="@+id/btnPerm" android:layout_width="match_parent" android:layout_height="52dp"
        android:text="Allow Overlay Permission" android:backgroundTint="@color/accent" android:textColor="@color/text_white"
        android:textSize="15sp" android:layout_marginBottom="12dp" android:layout_marginTop="8dp"/>
    <Button android:id="@+id/btnStart" android:layout_width="match_parent" android:layout_height="56dp"
        android:text="Start All Features" android:backgroundTint="@color/primary" android:textColor="@color/text_white"
        android:textSize="16sp" android:textStyle="bold" android:layout_marginBottom="12dp"/>
    <Button android:id="@+id/btnStop" android:layout_width="match_parent" android:layout_height="52dp"
        android:text="Stop & Reset" android:backgroundTint="@color/bg_card" android:textColor="@color/text_white"
        android:textSize="15sp" android:layout_marginBottom="16dp"/>

    <TextView android:layout_width="wrap_content" android:layout_height="wrap_content"
        android:text="Overlay: FPS • CPU • Battery • Temp — Drag to move" android:textSize="12sp"
        android:textColor="@color/text_gray" android:gravity="center" android:layout_marginTop="8dp"/>
</LinearLayout>
