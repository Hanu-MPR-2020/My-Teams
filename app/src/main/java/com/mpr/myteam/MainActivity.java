package com.mpr.myteam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private int id = 1;
    private final String PROFILE = "https://jsonplaceholder.typicode.com/users/";

    private Bundle data;
    private FragmentManager manager;
    private Fragment fragment;

    private float x1, x2, y1, y2;
    private static int MIN_DISTANCE =150;
    private GestureDetector gestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getSupportFragmentManager();
        fragment = new ProfileFragment();

        //initilize gesture detector
        this.gestureDetector = new GestureDetector(MainActivity.this, this);

        //Set image and text on create activity
        String profileUrl = PROFILE + id;
        String imgUrl = String.format("https://robohash.org/%d?set=set2", id);
        data = new Bundle();
        data.putString("PROFILE", profileUrl);
        data.putString("AVATAR", imgUrl);
        fragment.setArguments(data);
        manager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment, null)
                .addToBackStack(null)
                .commit();
    }

    //override OnTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);

        switch (event.getAction()){
                //starting to swipe time gesture
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1  = event.getY();
                break;

                //ending swipe gesture
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();

                //getting value for horizontal click
                float valueX = x2  -x1;

                //getting value for vertical click
                float valueY = y2 - y1;

                if(Math.abs(valueX) > MIN_DISTANCE){

                    //detect left to right swipe
                    if(x2 > x1){
                        if(id > 1){
                            id--;
                            Toast.makeText(MainActivity.this, "ID : " +id, Toast.LENGTH_SHORT).show();
                            String profileUrl = PROFILE + id;
                            String imgUrl = String.format("https://robohash.org/%d?set=set2", id);
                            data = new Bundle();
                            data.putString("PROFILE", profileUrl);
                            data.putString("AVATAR", imgUrl);
                            fragment.setArguments(data);
                            manager.beginTransaction()
                                    .detach(fragment)
                                    .attach(fragment)
                                    .commit();
                        }

                    }else{ //detect right to left
                        if(id < 10){
                            id++;
                            Toast.makeText(MainActivity.this, "ID : " +id, Toast.LENGTH_SHORT).show();
                            String profileUrl = PROFILE + id;
                            String imgUrl = String.format("https://robohash.org/%d?set=set2", id);
                            data = new Bundle();
                            data.putString("PROFILE", profileUrl);
                            data.putString("AVATAR", imgUrl);
                            fragment.setArguments(data);
                            manager.beginTransaction()
                                    .detach(fragment)
                                    .attach(fragment)
                                    .commit();
                        }
                    }
                }
                break;

        }


        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}