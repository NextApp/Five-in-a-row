package com.tuyasmart.five;


import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tuyasmart.five.bean.FiveNode;
import com.tuyasmart.five.layout.FiveLayout;
import com.tuyasmart.five.model.ChessBoard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private FiveLayout mFiveLayout;
    private ChessBoard mChessBoard;
    private int widthpixels;
    private int heightpixels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        oldChess();
        readFileByLines();
        newChess();
    }

    private void newChess() {
        setContentView(R.layout.five_main);
        mChessBoard = new ChessBoard(false, new ChessBoard.ChessBoardListener() {
            @Override
            public void onChessBoardChanged(List<FiveNode> fiveNodes) {
                mFiveLayout.updateChess(fiveNodes);
            }

            @Override
            public void onChessResult(boolean whitePlayer, int state) {
                if (state == ChessBoard.STATUS_WIN) {
                    if (whitePlayer) {
                        Toast.makeText(MainActivity.this, " 白棋胜利", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, " 黑棋胜利", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        mFiveLayout = (FiveLayout) findViewById(R.id.fiveLayout);
        mFiveLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();
                touch(x, y);
                return false;
            }
        });
    }

    private void oldChess() {
        getWindowSize();
        final MainLayout mainlayout = new MainLayout(this, widthpixels, heightpixels);
        mainlayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
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

    public void touch(float px, float py) {
        // TODO Auto-generated method stub
        int touchNodeXY = mFiveLayout.getTouchNodeXY(px, py);
        if (touchNodeXY == -1) return;
        else {
            int x = touchNodeXY / FiveLayout.CHESS_LINE_NUM;
            int y = touchNodeXY % FiveLayout.CHESS_LINE_NUM;
            mChessBoard.addNote(x, y);
        }
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

    public static Map<String, Integer> mJudgeScoreMap = new HashMap<>(2048);

    public void readFileByLines() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.ai2)));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                Log.d(TAG, tempString);
                if (!TextUtils.isEmpty(tempString)) {
                    String[] split = tempString.split(" ");
                    mJudgeScoreMap.put(split[0], Integer.valueOf(split[1]));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }


}
