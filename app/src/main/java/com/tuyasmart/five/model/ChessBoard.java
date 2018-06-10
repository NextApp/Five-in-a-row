package com.tuyasmart.five.model;

/**
 * Created by letian on 2017/9/10.
 */

import com.tuyasmart.five.ai.CCAi;
import com.tuyasmart.five.bean.FiveNode;
import com.tuyasmart.five.layout.FiveLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 维护整盘棋局
 */
public class ChessBoard {
    public static final int WHITE_NODES = -1;
    public static final int BLACK_NODES = 1;
    public static final int STATUS_WIN = 1;
    private List<FiveNode> mNodes;
    private int[][] mChessBoardState;
    public static final int CHESS_NUM = FiveLayout.CHESS_LINE_NUM;
    private boolean mWhite = false;
    private ChessBoardListener mListener;

    private boolean mGameOver;
    public static final int[] Dx = new int[]{-1, 0, 1, 1, 1, 0, -1, -1};
    public static final int[] Dy = new int[]{1, 1, 1, 0, -1, -1, -1, 0};
    private CCAi mYuPengAi;

    public ChessBoard(boolean white, ChessBoardListener listener) {
        initBoard(white);
        mListener = listener;
    }

    private void initBoard(boolean white) {
        mWhite = white;
        mGameOver = false;
        mNodes = new ArrayList<>();
        mChessBoardState = new int[FiveLayout.CHESS_LINE_NUM][FiveLayout.CHESS_LINE_NUM];
    }


    public void addNote(int x, int y) {
        if (mGameOver) return;
        if (x >= CHESS_NUM || y >= CHESS_NUM) return;
        if (mChessBoardState[x][y] != 0) return;
        mChessBoardState[x][y] = mWhite ? WHITE_NODES : BLACK_NODES;
        mNodes.add(new FiveNode(x, y, mWhite));
        if (mListener != null) mListener.onChessBoardChanged(mNodes);
        if (isPlayerWin()) {
            mGameOver = true;
            mListener.onChessResult(mWhite, STATUS_WIN);
            return;
        }
        mWhite = !mWhite;
//        goNext();
//*for test
        mYuPengAi = new CCAi(mNodes, mChessBoardState, new CCAi.CCAiStepListener() {
            @Override
            public void onStep(int x, int y) {
                mNodes.add(new FiveNode(x, y, mWhite));
            }
        });
        mYuPengAi.calculateChessScore(x, y, mChessBoardState[x][y]);
    }

    private void goNext() {
        mYuPengAi = new CCAi(mNodes, mChessBoardState, new CCAi.CCAiStepListener() {
            @Override
            public void onStep(int x, int y) {
                mNodes.add(new FiveNode(x, y, mWhite));
            }
        });
        mYuPengAi.goNextStep();
    }

    private boolean check(int x, int y) {
        int M[] = new int[10];
        //查询8个方向
        for (int step = 0; step < 8; step++) {
            M[step] = 0;
            //查询4步
            for (int t = 1; t <= 4; t++) {
                int xx = x + Dx[step] * t, yy = y + Dy[step] * t;
                if (xx > CHESS_NUM || yy > CHESS_NUM || xx < 0 || yy < 0
                        || mChessBoardState[x][y] != mChessBoardState[xx][yy])
                    break;
                else
                    M[step]++;
            }
            for (int i = 0; i < 4; i++)
                if (M[i] + M[i + 4] + 1 >= 5)
                    return true;
        }
        return false;
    }

    private boolean isPlayerWin() {
        int size = mNodes.size();
        return check(mNodes.get(size - 1).getX(), mNodes.get(size - 1).getY());
    }


    public interface ChessBoardListener {
        void onChessBoardChanged(List<FiveNode> fiveNodes);

        void onChessResult(boolean whitePlayer, int state);
    }
}
