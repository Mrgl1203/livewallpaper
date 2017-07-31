package wallpaper.gl.com.videowallpaper;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity {
    public static final int PERMISSION_CAMERA = 1000;
    public static final int PERMISSION_BACK_SETTING = 1001;

    @BindView(R.id.but_choosepaper)
    Button butChoosepaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }


    public void choosePaper() {
        Intent intent = new Intent(Intent.ACTION_SET_WALLPAPER);
        Intent choose = Intent.createChooser(intent, getResources().getString(R.string.choosepaper));
        startActivity(choose);
    }

    @OnClick(R.id.but_choosepaper)
    public void checkSelfPremission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA);
        } else {
            choosePaper();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CAMERA:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        choosePaper();
                    } else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                            showAlert(this, "我们需要摄像头权限来设置透明壁纸", "准了", "残忍拒绝", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    checkSelfPremission();
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                        } else {
                            showAlert(this, "请去设置界面打开权限", "起驾", "拖出去砍了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent =
                                            new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                                    startActivityForResult(intent, PERMISSION_BACK_SETTING);
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                        }
                    }
                }

                break;

        }
    }

    public void showAlert(Context context, String msg,
                          String positivemsg, String negativemsg,
                          DialogInterface.OnClickListener positivelistener, DialogInterface.OnClickListener negativelistener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false)
                .setMessage(msg)
                .setPositiveButton(positivemsg, positivelistener)
                .setNegativeButton(negativemsg, negativelistener)
                .create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PERMISSION_BACK_SETTING:
                choosePaper();
                break;
        }
    }
}
