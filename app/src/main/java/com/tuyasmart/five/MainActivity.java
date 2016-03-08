package com.tuyasmart.five;


import android.os.Bundle;
import android.app.Activity;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class MainActivity extends Activity {

    private int widthpixels;
    private int heightpixels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindowSize();
        final MainLayout mainlayout = new MainLayout(this, widthpixels, heightpixels);
        mainlayout.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        setContentView(mainlayout);
        mainlayout.postInvalidate();
        mainlayout.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                float x = event.getX();
                float y = event.getY();
                mainlayout.touch(x, y);
                return false;
            }
        });

    }


    private void getWindowSize() {
        Rect rect = new Rect();
        MainActivity.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //  View view = MainActivity.this.getWindow().findViewById(Window.ID_ANDROID_CONTENT);

        // TODO Auto-generated method stub
        DisplayMetrics dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);
        widthpixels = dm.widthPixels;
        heightpixels = dm.heightPixels - rect.top;
        ;
        System.out.println(widthpixels + " f " + heightpixels + " " + rect.top);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
