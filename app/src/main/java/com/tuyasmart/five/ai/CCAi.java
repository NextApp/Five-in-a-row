package com.tuyasmart.five.ai;

import android.util.Log;

import com.tuyasmart.five.MainActivity;
import com.tuyasmart.five.bean.FiveNode;
import com.tuyasmart.five.bean.JudgeBean;
import com.tuyasmart.five.model.ChessBoard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by letian on 2017/9/10.
 */

public class CCAi {
    private static final String TAG = "CCAi";
    private static final int INF = 1 >> 31;
    private final List<FiveNode> mNodes;
    private final int[][] mChessBoardState;
    private final boolean mWhite;
    private CCAiStepListener mListener;
    private int mChessState;
    private List<JudgeBean> judge = AIJudgeRead.getInstance().getJudge();

    public CCAi(List<FiveNode> nodes, int[][] chessBoardState, CCAiStepListener listener) {
        mNodes = new ArrayList<>();
        mNodes.addAll(nodes);
        mChessBoardState = chessBoardState;
        this.mListener = listener;
        mWhite = nodes.get(nodes.size() - 1).isWhite();
        mChessState = mWhite ? ChessBoard.WHITE_NODES : ChessBoard.BLACK_NODES;
    }


    /**
     * 计算当前棋子在棋局中的分数
     *
     * @param x
     * @param y
     * @param state
     * @return
     */
    public int calculateChessScore(int x, int y, int state) {
        String fourDirectionChessState[] = new String[4];
        String eightDirectionChessState[] = new String[8];
        //八个方向
        for (int i = 0; i < 8; i++) {
            eightDirectionChessState[i] = "";
            //走四步
            for (int t = 1; t <= 4; t++) {
                char chess = 'A';
                int xx = x + ChessBoard.Dx[i] * t, yy = y + ChessBoard.Dy[i] * t;
                if (xx < ChessBoard.CHESS_NUM && yy < ChessBoard.CHESS_NUM && xx >= 0 && yy >= 0) {
                    if (mChessBoardState[xx][yy] != 0)
                        chess = mChessBoardState[xx][yy] == state ? 'W' : 'B';
                } else {
                    chess = 'B';
                }
                if (i > 3)
                    eightDirectionChessState[i] += chess;
                else
                    eightDirectionChessState[i] = chess + eightDirectionChessState[i];
                if (chess == 'B') break;
            }
        }
        int sumScore = 0;
        for (int t = 0; t < 4; t++) {
            fourDirectionChessState[t] = eightDirectionChessState[t] + "W" + eightDirectionChessState[t + 4];
            Integer integer = MainActivity.mJudgeScoreMap.get(fourDirectionChessState[t]);
            if (integer != null) {
                sumScore += integer;
                Log.d(TAG, fourDirectionChessState[t] + " " + integer);
            } else {
                Log.e(TAG, fourDirectionChessState[t] + " null");
            }
        }
        Log.d(TAG, "score:" + sumScore);
        return sumScore;
    }


    public void goNextStep() {
        if (mListener != null) {
            mListener.onStep(0, 0);
        }
    }

    public int chooseNext(int state, int deep) {
        ChooseChess theMostScoreChess = getTheMostScoreChess(state);
        mChessBoardState[theMostScoreChess.x][theMostScoreChess.y] = state;
        int myScore = theMostScoreChess.sum;
        int enemyScore = chooseNext(-state, deep - 1);
        mChessBoardState[theMostScoreChess.x][theMostScoreChess.y] = 0;
        return 0;
    }

    public void findChess(int state) {
        List<ChooseChess> points = new ArrayList<>(16);
        for (int i = 0; i < ChessBoard.CHESS_NUM; i++) {
            for (int j = 0; j < ChessBoard.CHESS_NUM; j++) {
                if (mChessBoardState[i][j] == 0) {
                    //我方得分
                    int myScore = calculateChessScore(i, j, state);
                    //敌方得分
                    int enemyScore = calculateChessScore(i, j, -state);
                    int sourceScore = myScore - enemyScore;
                    if (sourceScore > 0) {
                        points.add(new ChooseChess(i, j));
                    }
                }
            }
        }
    }


    private ChooseChess getTheMostScoreChess(int state) {
        ChooseChess chooseChess = new ChooseChess(0, 0);

        for (int i = 0; i < ChessBoard.CHESS_NUM; i++) {
            for (int j = 0; j < ChessBoard.CHESS_NUM; j++) {
                if (mChessBoardState[i][j] == 0) {
                    //我方得分
                    int myScore = calculateChessScore(i, j, state);
                    //敌方得分
                    int enemyScore = calculateChessScore(i, j, -state);
                    int sourceScore = myScore - enemyScore;
                    if (sourceScore > chooseChess.sum) {
                        chooseChess.x = i;
                        chooseChess.y = j;
                        chooseChess.sum = sourceScore;
                    }
                }
            }
        }
        return chooseChess;
    }

    private class ChooseChess {
        private int x = -1;
        private int y = -1;
        private int sum = -INF;

        public ChooseChess(int i, int j) {
            x = i;
            y = j;
        }
    }

    public interface CCAiStepListener {
        void onStep(int x, int y);
    }
}
