<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical"
    tools:context=".beforelogin.HomeFragment">

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="10sp"
            android:layout_marginRight="10sp"
            android:layout_marginTop="30sp"
            android:layout_marginBottom="30sp">

            <androidx.cardview.widget.CardView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                app:cardCornerRadius="10sp">

                <RelativeLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:padding="10sp">

                    <ImageView
                        android:id="@+id/logoIV"
                        android:layout_width="80sp"
                        android:layout_height="80sp"
                        android:src="@drawable/logo"/>

                    <LinearLayout
                        android:layout_toEndOf="@id/logoIV"
                        android:layout_width="match_parent"
                        android:layout_height="wrap_content"
                        android:orientation="horizontal"
                        android:weightSum="1">

                        <TextView
                            android:layout_width="0dp"
                            android:layout_height="80sp"
                            android:text="Mahesh Vandana"
                            android:fontFamily="@font/pms_fontregular"
                            android:textSize="18sp"
                            android:textColor="@color/black"
                            android:gravity="start|center_vertical"
          