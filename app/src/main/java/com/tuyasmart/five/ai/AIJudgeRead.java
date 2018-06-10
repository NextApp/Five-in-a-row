package com.tuyasmart.five.ai;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.tuyasmart.five.R;
import com.tuyasmart.five.bean.JudgeBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by letian on 2017/9/10.
 */

public class AIJudgeRead {

    private static final String TAG = "ChessAi";

    public List<JudgeBean> getJudge() {
        return mJudge;
    }

    private List<JudgeBean> mJudge;

    private final Context mContext;

    private AIJudgeRead(Context context) {
        mContext = context;
        mJudge = new ArrayList<>();
        read();
    }

    private static AIJudgeRead judgeRead;

    public static AIJudgeRead getInstance() {
        if (judgeRead == null) {
            judgeRead = new AIJudgeRead(getSystemApp());
        }
        return judgeRead;
    }


    private void read() {
        try {
            InputStreamReader inputReader = new InputStreamReader(mContext.getResources().openRawResource(R.raw.ai));
            BufferedReader f = new BufferedReader(inputReader);
            int len = 173;
            for (int i = 0; i < len; i++) {
                String readLine = f.readLine();
                if (readLine.length() == 0) {
                    i--;
                    continue;
                }
                JudgeBean aiJudge = new JudgeBean();
                for (int j = 0; j < readLine.length(); j++) {
                    if (readLine.charAt(j) == ' ') {
                        aiJudge.judgeData = readLine.substring(0, j);
                        while (readLine.charAt(j) == ' ')
                            j++;
                        int k = j;
                        while (readLine.charAt(k) != ' ')
                            k++;
                        aiJudge.id = Integer.parseInt(readLine.substring(j, k));
                        while (readLine.charAt(k) == ' ')
                            k++;
                        aiJudge.score = Integer.parseInt(readLine.substring(k,
                                readLine.length()));
                        break;
                    }
                }
                mJudge.add(aiJudge);
            }
            f.close();
        } catch (IOException e) {
            Log.d(TAG, e.toString());
        }

    }

    private static Application getSystemApp() {
        try {
            Class<?> activitythread = Class.forName("android.app.ActivityThread");

            Method m_currentActivityThread = activitythread
                    .getDeclaredMethod("currentActivityThread");
            Field f_mInitialApplication = activitythread.getDeclaredField("mInitialApplication");
            f_mInitialApplication.setAccessible(true);
            Object current = m_currentActivityThread.invoke(null);

            Object app = f_mInitialApplication.get(current);

            return (Application) app;

        } catch (Exception e) {

        }
        return null;
    }

}
