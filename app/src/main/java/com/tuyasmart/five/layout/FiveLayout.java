package com.tuyasmart.five.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.tuyasmart.five.bean.FiveNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by letian on 2017/9/9.
 */

public class FiveLayout extends View {

    private static final String TAG = "FiveLayout";
    private final int CHESS_WIDTH = 800;
    public static final int CHESS_LINE_NUM = 15;
    private final Paint mBackGroundPaint;
    private final Paint mLinePaint;
    private final Paint mBlackChessPaint;
    private final Paint mWhiteChessPaint;

    private int mChessPadding;
    private float mWidthLineInterval;
    private float mHeightLineInterval;
    private int mWidth = -1;
    private int mHeight = -1;

    private List<FiveNode> mFiveNodes;

    public FiveLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mBackGroundPaint = new Paint();
        mLinePaint = new Paint();
        mLinePaint.setColor(Color.BLACK);
        mLinePaint.setAntiAlias(true);
        mBlackChessPaint = new Paint();
        mBlackChessPaint.setColor(Color.BLACK);
        mBlackChessPaint.setAntiAlias(true);
        mWhiteChessPaint = new Paint();
        mWhiteChessPaint.setColor(Color.WHITE);
        mWhiteChessPaint.setAntiAlias(true);
        mBackGroundPaint.setColor(Color.YELLOW);
        mBackGroundPaint.setAntiAlias(true);
        mFiveNodes = new ArrayList<>();
//        mFiveNodes.add(new FiveNode(0, 0, true));
//        mFiveNodes.add(new FiveNode(0, 1, true));
//        mFiveNodes.add(new FiveNode(1, 0, false));
//        mFiveNodes.add(new FiveNode(1, 1, false));
//        int width = getWidth();
//        Log.d(TAG, "width: " + width + " messag" + getMeasuredWidth());
//
    }

    private void countLineInterval() {
        mChessPadding = mWidth / (CHESS_LINE_NUM + 1);
        mWidthLineInterval = (mWidth - mChessPadding * 2) / CHESS_LINE_NUM;
        mHeightLineInterval = (mHeight - mChessPadding * 2) / CHESS_LINE_NUM;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawLine(canvas);
        drawChess(canvas);
    }

    private Paint mTempPaint = null;
    private RectF mTempRectF = new RectF();

    private void drawChess(Canvas canvas) {
        for (FiveNode fiveNode : mFiveNodes) {
            mTempPaint = fiveNode.isWhite() ? mWhiteChessPaint : mBlackChessPaint;
            mTempRectF = getRectF(fiveNode.getX(), fiveNode.getY());
            canvas.drawOval(mTempRectF, mTempPaint);
        }
    }

    private RectF getRectF(int x, int y) {
        float px = x * mWidthLineInterval - mWidthLineInterval / 2 + mChessPadding;
        float py = y * mHeightLineInterval - mHeightLineInterval / 2 + mChessPadding;
        mTempRectF.set(px, py, px + mWidthLineInterval, py + mHeightLineInterval);
        return mTempRectF;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        if (mWidth == -1 && mHeight == -1) {
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        Log.d(TAG, "width: " + mWidth + " height: " + mHeight);
        countLineInterval();
//        }
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawRect(new Rect(getPaddingLeft(), getPaddingTop(), mWidth, mHeight), mBackGroundPaint);
    }

    private void drawLine(Canvas canvas) {
        for (int index = 0; index <= CHESS_LINE_NUM; index++) {
            canvas.drawLine(mChessPadding, mChessPadding + index * mHeightLineInterval, CHESS_LINE_NUM * mHeightLineInterval + mChessPadding, mChessPadding + index * mHeightLineInterval, mLinePaint);
            canvas.drawLine(mChessPadding + index * mWidthLineInterval, mChessPadding, mChessPadding + index * mWidthLineInterval, CHESS_LINE_NUM * mWidthLineInterval + mChessPadding, mLinePaint);
        }
    }

    public int getTouchNodeXY(float px, float py) {
        float startX = px - getPaddingLeft() - mChessPadding + mWidthLineInterval / 2;
        float startY = py - getPaddingTop() - mChessPadding + mHeightLineInterval / 2;
        Log.d(TAG, "px: " + startX + " py: " + startY);
        if (startX < 0 || startY < 0) return -1;
        int x = (int) (startX / mWidthLineInterval);
        int y = (int) (startY / mHeightLineInterval);
        Log.d(TAG, "x: " + x + " y: " + y);
        if (x > CHESS_LINE_NUM || y > CHESS_LINE_NUM) return -1;
        return x * CHESS_LINE_NUM + y;
    }

    public void updateChess(List<FiveNode> fiveNodes) {
        mFiveNodes.clear();
        mFiveNodes.addAll(fiveNodes);
        invalidate();
    }
}
