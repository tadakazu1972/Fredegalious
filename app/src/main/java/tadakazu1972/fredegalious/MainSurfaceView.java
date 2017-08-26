package tadakazu1972.fredegalious;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by tadakazu on 2017/08/16.
 */

public class MainSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private MainActivity ac;
    private SurfaceHolder holder;
    private Thread thread;
    private float scaleX;
    private float scaleY;
    private float scale;
    private boolean isSurfaceStandby = false;
    //画像 sはspriteのs
    private Bitmap[] sMyChara;
    private Bitmap[] sMap;

    public MainSurfaceView(Context context, SurfaceView _surfaceview, int _deviceWidth, int _deviceHeight){
        super(context);

        //MainActivity保存。あとでマップCSV読み込みの時にassetsフォルダをアクセスする時に必要
        ac = (MainActivity)context;

        //画像読み込み
        initBitmap();

        //SurfaceHolder取得
        holder = _surfaceview.getHolder(); //MainActivityで確保したのを受け取る必要あり
        holder.addCallback(this);
        holder.setFixedSize(getWidth(), getHeight());

        //端末の画面サイズをもとに画像拡大倍率計算
        scaleX = _deviceWidth / 320.0f; //32pxx32pxの画像を10x10並べているので
        scaleY = _deviceHeight / 320.0f;
        scale = scaleX > scaleY ? scaleY : scaleX;

        //フォーカスをあてる
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        //毎回この後にsurfaceChangedが呼ばれるからそちらで記述している
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        thread = null;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
        //準備完了、描画スレッド起動
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run(){
        while (thread !=null){

            //描画
            draw(holder);

            //移動処理
            ac.mMyChara.move();

        }
    }

    public void draw(SurfaceHolder holder){
        //ホルダーからキャンパス取得
        Canvas canvas = holder.lockCanvas();
        if (canvas !=null){
            //端末の画面サイズにあわせて拡大
            canvas.scale(scale, scale);
            //現在の状態を保存
            canvas.save();

            //背景を塗る
            canvas.drawColor(Color.WHITE);

            //画像描画
            drawMap(canvas);
            drawMyChara(canvas);

            //現在の状態の変更
            canvas.restore();

            //描画内容確定
            holder.unlockCanvasAndPost(canvas);
        }

    }

    private void drawMyChara(Canvas canvas){
        int i = ac.mMyChara.base_index + ac.mMyChara.index / 10;
        if ( i > 7) i = 0;
        canvas.drawBitmap(sMyChara[i], ac.mMyChara.x, ac.mMyChara.y, null);
    }

    private void drawMap(Canvas canvas){
        int mapIndex=1;
        int mapId=0;
        for (int y=0; y<10; y++){
            for (int x=0; x<10; x++){
                mapId = ac.mMap[mapIndex].mapData[y][x];
                canvas.drawBitmap(sMap[mapId], x*32.0f, y*32.0f, null);
            }
        }
    }

    //画像初期化
    private void initBitmap(){
        Resources res = getResources();
        //自キャラ
        sMyChara = new Bitmap[8];
        sMyChara[0] = BitmapFactory.decodeResource(res, R.drawable.arthur07);
        sMyChara[1] = BitmapFactory.decodeResource(res, R.drawable.arthur08);
        sMyChara[2] = BitmapFactory.decodeResource(res, R.drawable.arthur03);
        sMyChara[3] = BitmapFactory.decodeResource(res, R.drawable.arthur04);
        sMyChara[4] = BitmapFactory.decodeResource(res, R.drawable.arthur01);
        sMyChara[5] = BitmapFactory.decodeResource(res, R.drawable.arthur02);
        sMyChara[6] = BitmapFactory.decodeResource(res, R.drawable.arthur05);
        sMyChara[7] = BitmapFactory.decodeResource(res, R.drawable.arthur06);
        //Map
        sMap = new Bitmap[2];
        sMap[0] = BitmapFactory.decodeResource(res, R.drawable.greenfield);
        sMap[1] = BitmapFactory.decodeResource(res, R.drawable.tree);
    }

}
