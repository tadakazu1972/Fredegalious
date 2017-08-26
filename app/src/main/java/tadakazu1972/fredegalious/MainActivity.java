package tadakazu1972.fredegalious;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

public class MainActivity extends AppCompatActivity implements android.view.View.OnTouchListener, android.view.View.OnLongClickListener {

    protected SurfaceView surfaceview; //activity_main.xmlの中のSurfaceView領域保持用
    protected MainSurfaceView mainSurfaceView; //クラス保持用
    //解像度拡大用
    protected int deviceWidth;
    protected int deviceHeight;
    //メンバクラス
    protected MyChara mMyChara;
    protected Map[] mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //メンバ変数初期化
        initMyChara();
        initMap();

        //xmlをセット。こちらを先に設定しないと後でSurfaceViewを捕獲できずNull Pointer Exceptionになる
        setContentView(R.layout.activity_main);

        //端末の画面サイズ取得
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        deviceWidth = size.x;
        deviceHeight = size.y;

        //SurfaceView生成
        surfaceview = (SurfaceView)findViewById(R.id.MainSurfaceView);
        mainSurfaceView = new MainSurfaceView(this, surfaceview, deviceWidth, deviceHeight);

        //ボタン初期化
        initButtons();

    }

    private void initMyChara(){
        //初期化
        mMyChara = new MyChara(this);
    }


    private void initMap(){
        //初期化
        mMap = new Map[2];
        //csv読み込み
        for(int i=0;i<2;i++){
            mMap[i] = new Map(i);
            //csvファイルからマップデータを読み込み
            String filename = String.format("map%d.csv", i);
            mMap[i].loadCSV(this, filename);
        }
        //next
        mMap[0].setNext(0,0,1,0);
        mMap[1].setNext(0,0,0,0);
    }

    private void initButtons(){
        //ボタンにonTouchイベント設定
        findViewById(R.id.btnUp).setOnTouchListener(this);
        findViewById(R.id.btnRight).setOnTouchListener(this);
        findViewById(R.id.btnDown).setOnTouchListener(this);
        findViewById(R.id.btnLeft).setOnTouchListener(this);
        //ボタンにonLongClickイベント設定
        findViewById(R.id.btnUp).setOnLongClickListener(this);
        findViewById(R.id.btnRight).setOnLongClickListener(this);
        findViewById(R.id.btnDown).setOnLongClickListener(this);
        findViewById(R.id.btnLeft).setOnLongClickListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event){
        //onTouchは画面を押した時と離した時の両方のイベントを取得する
        int action = event.getAction();
        switch(action){
            //ボタンから指が離れた時
            case MotionEvent.ACTION_UP:
                //連続イベントフラグをfalse
                //repeatFlg = false;
                //ゲームスタート
                //if (mainSurfaceView.gs==0) mainSurfaceView.gs=1;
                break;
            case MotionEvent.ACTION_DOWN:
                switch(v.getId()){
                    case R.id.btnUp:
                        mMyChara.up(1);
                        break;
                    case R.id.btnRight:
                        mMyChara.right(1);
                        break;
                    case R.id.btnDown:
                        mMyChara.down(1);
                        break;
                    case R.id.btnLeft:
                        mMyChara.left(1);
                        break;
                }
        }
        return false;
    }

    //長押しすると３倍速で移動
    @Override
    public boolean onLongClick(View v){
        switch(v.getId()) {
            case R.id.btnUp:
                //連続イベントフラグをtrue
                //repeatFlg = true;
                mMyChara.up(3);
                break;
            case R.id.btnRight:
                mMyChara.right(3);
                break;
            case R.id.btnDown:
                mMyChara.down(3);
                break;
            case R.id.btnLeft:
                mMyChara.left(3);
                break;
        }
        return true;
    }
}
