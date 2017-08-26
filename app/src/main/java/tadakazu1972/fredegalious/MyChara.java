package tadakazu1972.fredegalious;

/**
 * Created by tadakazu on 2017/08/26.
 */

public class MyChara {
    public float x, y, vx, vy;
    public float l, r, t, b;
    public int base_index; //アニメーション用 0:上 1:右 2:下 3:左
    public int index; //アニメーション用カウンタ

    public MyChara(){
        x = 4*32.0f;
        y = 7*32.0f;
        vx = 0.0f;
        vy = 0.0f;
        l = x;
        r = x+31.0f;
        t = y;
        b = y+31.0f;
        base_index = 4;
        index = 0;
    }

    public void move(){
        //座標更新
        x = x + vx;
        y = y + vy;
        //アニメーションインデックス変更処理
        index++;
        if (index > 19) index = 0;
    }

    public void up(int i){
        if (i<2) i=1;
        vx =  0.0f;
        vy = -1.0f * i;
        base_index= 0;
    }

    public void right(int i){
        if (i<2) i=1;
        vx =  1.0f * i;
        vy =  0.0f;
        base_index= 2;
    }

    public void down(int i){
        if (i<2) i=1;
        vx =  0.0f;
        vy =  1.0f * i;
        base_index= 4;
    }

    public void left(int i){
        if (i<2) i=1;
        vx = -1.0f * i;
        vy =  0.0f;
        base_index= 6;
    }
}
