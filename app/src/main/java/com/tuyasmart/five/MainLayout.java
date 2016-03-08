package com.tuyasmart.five;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainLayout extends View {
    private static final String TAG = "MainLayout";
    private int x, y;
    boolean isVisible = false;
    float x0 = 8, w = 35, y0 = 30;
    private float f13, f23, f22;
    private int ok = -1;
    private int color = -1;
    private int w_line = 15, l_line = 15;
    private int dx[] = new int[10];
    private int dy[] = new int[10];
    private int wo = 0, mo = 0;
    String line[] = new String[4];
    AI ai = new AI();
    int CCC = 0;
    int count = 0;
    int vis[][] = new int[20][20];
    private Chessman chess = new Chessman();
    private Nouser my = new Nouser();
    Canvas bg;
    private int AI_id[] = new int[12];
    private int minpixels;
    private float f5;
    private boolean gameover;
    Context context;

    public MainLayout(Context context, int widthpixels, int heightpixels) {

        super(context);
        this.context = context;
        minpixels = widthpixels > heightpixels ? heightpixels : widthpixels;
        x0 = (float) (minpixels / 600.0 * 8);
        y0 = (float) (minpixels / 600.0 * 30);
        w = (float) (minpixels / 600.0 * 35);
        f13 = (float) (minpixels / 600.0 * 13);
        f23 = (float) (minpixels / 600.0 * 23);
        f22 = (float) (minpixels / 600.0 * 22);
        f5 = (float) (minpixels / 600.0 * 5);
        gameover = false;
        my.addT(7, 6, 5);
        dx[0] = -1;
        dx[1] = 0;
        dx[2] = 1;
        dx[3] = 1;
        dx[4] = 1;
        dx[5] = 0;
        dx[6] = -1;
        dx[7] = -1;
        dy[0] = 1;
        dy[1] = 1;
        dy[2] = 1;
        dy[3] = 0;
        dy[4] = -1;
        dy[5] = -1;
        dy[6] = -1;
        dy[7] = 0;
        read();

        // TODO Auto-generated constructor stub

    }

    private void addnouser(int x, int y) {
        for (int m = 0; m < 8; m++) {
            for (int t = 1; t <= 2; t++) {
                int xx = x + dx[m] * t, yy = y + dy[m] * t;
                if (xx >= w_line || yy >= l_line || xx < 0 || yy < 0
                        || vis[xx][yy] != 0)
                    continue;
                if (my.vis[xx][yy] == 0)
                    my.addT(xx, yy, 0);
            }
        }
    }

    protected void onDraw(Canvas canvas) {


        super.onDraw(canvas);
        bg = canvas;
        if (!gameover) update();
    }

    protected boolean IS_WIN() {
        boolean ok = false;
        for (int i = 0; i < chess.len; i++) {
            int x = chess.x[i], y = chess.y[i];
            if (check(x, y) == true)
                return true;
        }
        return ok;
    }

    public void update() {
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setAntiAlias(true);
        bg.drawRect(new Rect(0, 0, minpixels, minpixels), paint);
        paint.setColor(Color.BLACK);
        //
        for (int i = 1; i <= w_line; i++) {
            //if(i==1)System.out.println(w*i+x0+","+(w+f23+x0));
            bg.drawLine(w * i + x0, x0 + w + f23, w * i + x0, w * 15 + y0, paint);
            bg.drawLine(y0 + f13, w * i + y0, w * 15 + x0, w * i + y0, paint);
        }
        float s = w;
        s -= f5;
        for (int i = 1; i <= w_line; i++)
            for (int j = 1; j <= l_line; j++) {
                if (vis[i - 1][j - 1] == 1) {
                    paint.setColor(Color.WHITE);

                    float xx = x0 - s / 2 + w * i;
                    float yy = x0 + f22 - s / 2 + w * j;

                    bg.drawOval(new RectF(xx, yy, xx + s,
                            yy + s), paint);

                } else if (vis[i - 1][j - 1] == -1) {
                    float xx = x0 - s / 2 + w * i;
                    float yy = x0 + f22 - s / 2 + w * j;
                    paint.setColor(Color.BLACK);
                    //	bg.drawCircle(x0 - s / 2 + w * i,x0 + f22 - s / 2 + w * j,s,paint);
                    bg.drawOval(new RectF(xx, yy, xx + s,
                            yy + s), paint);

                }
            }

        //repaint();
        if (!WIN() && ok == color)
            next_point(mo);
        // System.out.println(count);


    }


    protected int calculate(int x, int y) {
        int sum = 0;
        String M[] = new String[8];
        String a;
        for (int m = 0; m < 8; m++) {
            M[m] = "";
            for (int t = 1; t < 6; t++) {
                int xx = x + dx[m] * t, yy = y + dy[m] * t;
                if (xx < w_line && yy < l_line && xx >= 0 && yy >= 0) {
                    if (vis[xx][yy] == 0) {
                        if (m > 3)
                            M[m] += "A";
                        else
                            M[m] = "A" + M[m];
                    } else {
                        if (vis[xx][yy] == color)
                            a = "B";
                        else
                            a = "W";
                        if (m > 3)
                            M[m] += a;
                        else
                            M[m] = a + M[m];
                        if (vis[xx][yy] != vis[x][y])
                            break;
                    }
                } else {
                    if (m > 3)
                        M[m] += "E";
                    else
                        M[m] = "E" + M[m];
                    break;
                }
            }
        }
        for (int t = 0; t < 4; t++) {
            line[t] = M[t] + "B" + M[t + 4];
            int id = compute(t);
            AI_id[ai.id[id]]++;
            sum += ai.score[id];
        }
        return sum;
    }

    private int compute(int t) {
        // TODO Auto-generated method stub
        int mm = 0;
        int id = 0;
        int len = line[t].length();
        for (int j = 0; j <= len - 5; j++)
            for (int k = 5 + j; k <= len; k++) {
                int ss = search(line[t].substring(j, k));
                if (ss >= 0 && mm < ai.score[ss]) {
                    id = ss;
                    mm = ai.score[ss];
                }
            }

        return id;
    }

    private int search(String substring) {
        // TODO Auto-generated method stub
        int s = 0, e = ai.len - 1;
        while (s <= e) {
            count++;
            int k = (s + e) / 2;
            int o = substring.compareTo(ai.string[k]);
            if (o == 0)
                return k;
            else if (o < 0)
                e = k - 1;
            else
                s = k + 1;
        }
        return -1;
    }

    protected int next_point(int move) {

        // TODO Auto-generated method stub
        Nouser st = new Nouser();
        int bx = -1, by = -1, score = -1;
        int v[] = new int[12];
        int OK = -1;
        P p = new P();
        for (int t = 1; t < my.len; t++) {
            int i = my.a[t].x;
            int j = my.a[t].y;
            if (vis[i][j] == 0) {
                vis[i][j] = color;
                for (int x = 0; x <= 10; x++) AI_id[x] = 0;
                int s = calculate(i, j);
                vis[i][j] = -color;
                s += calculate(i, j);
                if (s > score) {
                    bx = i;
                    by = j;
                    score = s;
                }
                vis[i][j] = 0;
                if (s >= 100) {
                    for (int x = 0; x <= 10; x++)
                        v[x] += AI_id[x];
                    p.add(i, j, s, AI_id);

                    if (v[10] >= 1) {
                        if (move != mo)
                            return 1;
                        else {
                            bx = i;
                            by = j;
                            OK = 1;
                            break;
                        }
                    }
                }
            }
        }
        if (move != mo) {
            if (v[9] >= 2)
                return -1;
            if (v[9] >= 1 && v[6] >= 1) return -1;
            if (v[8] >= 1)
                return 1;
        }
        for (int i = 0; i < p.len && OK == -1; i++) {
            if (v[9] >= 1) {
                if (p.a[i].id[9] >= 1) {
                    st.add(p.a[i]);
                    break;
                }
            } else if (v[8] >= 1) {
                if (p.a[i].id[8] >= 1) {
                    st.add(p.a[i]);
                    break;
                }
            } else if (v[6] >= 1) {
                if (p.a[i].id[6] >= 1 || p.a[i].id[7] >= 1) {
                    st.add(p.a[i]);
                }
            } else st.add(p.a[i]);
        }

        if (score == -1)
            return 0;
        else {
            if (move == mo)
                System.out.println(st.len);
            if (OK == -1) {
                int len = st.len;
                if (len == 1)
                    st.addT(bx, by, score);
                for (int i = 1; i < len && i <= wo + 1; i++) {
                    T t = st.delete(1);
                    int x = t.x;
                    int y = t.y;
                    vis[x][y] = color;
                    color = -color;
                    if (my.vis[x][y] != 0)
                        my.delete(my.vis[x][y]);
                    addnouser(x, y);
                    int w = 0;
                    if (move >= 1)
                        w = next_point(move - 1);
                    if (move == mo) {
                        System.out.println(x + " " + y + " " + w + ">" + color);
                    }
                    my.addT(x, y, 0);
                    vis[x][y] = 0;
                    color = -color;
                    if (w == -1) {
                        if (move == mo) System.out.println(x + "*" + y);
                        OK = 1;
                        bx = x;
                        by = y;
                        break;
                    } else if (w == 0 && OK == -1) {
                        bx = x;
                        by = y;
                        OK = 0;
                    }
                }
            }
            if (move == mo) {
                vis[bx][by] = color;
                addnouser(bx, by);
                if (my.vis[bx][by] != 0)
                    my.delete(my.vis[bx][by]);
                chess.add(bx, by);
                ok = -ok;
                //bg = buf.getGraphics();
                update();
            }
        }
        return OK;
    }

    protected boolean check(int x, int y) {
        int M[] = new int[10];
        for (int m = 0; m < 8; m++) {
            M[m] = 0;
            for (int t = 1; t < 5; t++) {
                int xx = x + dx[m] * t, yy = y + dy[m] * t;
                if (xx > w_line || yy > l_line || xx < 0 || yy < 0
                        || vis[x][y] != vis[xx][yy])
                    break;
                else
                    M[m]++;
            }
            for (int i = 0; i < 4; i++)
                if (M[i] + M[i + 4] + 1 >= 5)
                    return true;
        }
        return false;
    }

    protected boolean WIN() {
        for (int t = 2; t >= 1; t--)
            if (chess.len >= t
                    && check(chess.x[chess.len - t], chess.y[chess.len - t])) {
                if (vis[chess.x[chess.len - t]][chess.y[chess.len - t]] == 1) {
                    gameover = true;
                    Toast.makeText(context, " 白棋胜利", Toast.LENGTH_SHORT).show();
                    //JOptionPane.showMessageDialog(null, );
                    return true;
                } else {
                    gameover = true;
                    Toast.makeText(context, "黑棋胜利", Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        return false;

    }

    public void read() {
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().openRawResource(R.raw.ai));
            BufferedReader f = new BufferedReader(inputReader);
            int len = 173;
            for (int i = 0; i < len; i++) {
                String readLine = f.readLine();
                if (readLine.length() == 0) {
                    i--;
                    continue;
                }

                for (int j = 0; j < readLine.length(); j++) {
                    if (readLine.charAt(j) == ' ') {
                        ai.string[i] = readLine.substring(0, j);
                        while (readLine.charAt(j) == ' ')
                            j++;
                        int k = j;
                        while (readLine.charAt(k) != ' ')
                            k++;
                        ai.id[i] = Integer.parseInt(readLine.substring(j, k));
                        while (readLine.charAt(k) == ' ')
                            k++;
                        ai.score[i] = Integer.parseInt(readLine.substring(k,
                                readLine.length()));
                        break;
                    }
                }
                // System.out.println(ai.string[i]+" "+ai.id[i]+" "+ai.score[i]);
            }
            ai.len = len;
            f.close();
        } catch (IOException e) {
            Log.d(TAG, e.toString());
        }

    }

    public void touch(float x, float y) {
        // TODO Auto-generated method stub
        if (gameover) return;
        //	System.out.println(x+","+y+","+w);
        x -= w + x0;
        y -= f23 + w + x0;
        //	System.out.println(x+","+y+","+w);
        //if(x<-0.0001||y<-0.0001)return ;
        int eok = 0;
        float xx = x - x / w * w;
        float yy = y - y / w * w;
        x /= w;
        y /= w;
        int xxx = (int) (x + 0.5);
        int yyy = (int) (y + 0.5);
        if (!(xxx >= 0 && xxx < 15 && yyy >= 0 && yyy < 15)) return;
        //System.out.println(xxx+","+yyy+","+x+"x"+y);
        if (xx <= w / 2 && yy <= w / 2) {
            if (vis[xxx][yyy] == 0)
                eok = 1;
        } else if (xx > w / 2 && yy <= w / 2) {
            if (vis[xxx + 1][yyy] == 0) {
                x = x + 1;
                eok = 1;
            }
        } else if (xx <= w / 2 && yy > w / 2) {
            if (vis[xxx][yyy + 1] == 0) {
                y = y + 1;
                eok = 1;
            }
        } else {
            if (vis[xxx + 1][yyy + 1] == 0) {
                x = x + 1;
                y = y + 1;
                eok = 1;
            }
        }
        isVisible = true;

        if (eok == 1) {
            vis[xxx][yyy] = ok;
            if (my.vis[xxx][yyy] != 0)
                my.delete(my.vis[xxx][yyy]);
            addnouser(xxx, yyy);
            chess.add(xxx, yyy);
            ok = -ok;
            this.postInvalidate();
        }
    }
}
