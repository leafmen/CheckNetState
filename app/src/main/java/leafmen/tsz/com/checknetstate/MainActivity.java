package leafmen.tsz.com.checknetstate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String newStateHint;
    private String newStateString;
    private TextView tvNewState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvNewState = (TextView) findViewById(R.id.tvNewState);
        newStateString = this.getResources().getString(R.string.newState);
        checkNetState(this);
        /*判断当前是否有可用的网络以及网络类型 0：无网络 1：WIFI 2：CMWAP 3：CMNET*/
        switch (CommonUtil.isNetworkAvailable(this)) {
            case 0:
                newStateHint = "当前无可用网络";
                break;
            case 1:
                newStateHint = "当前使用的是WIFI网络";
                break;
            case 2:
                newStateHint = "当前使用的是CMWAP网络";
                break;
            case 3:
                newStateHint = "当前使用的是CMNET网络";
                break;
            default:
                newStateHint = "当前无可用网络";
                break;
        }
        tvNewState.setText(newStateString + newStateHint);
        tvNewState.setTextColor(Color.BLUE);
        tvNewState.setAlpha(1f);
    }

    /**
     * 检查网络是否连接
     *
     * @param context
     */
    private void checkNetState(Context context) {
        if (!CommonUtil.isNetWork(context)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("网络状态提醒");
            builder.setMessage("当前网络不可用，是否打开网络设置?");
            builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (android.os.Build.VERSION.SDK_INT > 10) {
                        startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                    } else {
                        startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                    }
                }
            });
            builder.create().show();
        }
    }
}
