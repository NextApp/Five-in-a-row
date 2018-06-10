package com;

/**
 * Created by letian on 2017/3/6.
 */

public interface Task {
    void start();

    void stop();

    void getTag();

    void setTag(String tag);

    void setPriority(int priority);

    int getPriority(int priority);

    void onSuccess();
}
