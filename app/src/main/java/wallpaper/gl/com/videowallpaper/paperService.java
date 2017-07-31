package wallpaper.gl.com.videowallpaper;

import android.hardware.Camera;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Created by gl152 on 2017/7/31.
 */

public class paperService extends WallpaperService {
    @Override
    public Engine onCreateEngine() {
        return new CameraEngine();
    }

    class CameraEngine extends Engine implements Camera.PreviewCallback {
        private Camera camera;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            startPreview();
            // 设置处理触摸事件
            setTouchEventsEnabled(true);

        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
            // 时间处理:点击拍照,长按拍照
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            stopPreview();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (visible) {
                startPreview();
            } else {
                stopPreview();
            }
        }

        @Override//setPreviewCallback
        public void onPreviewFrame(byte[] bytes, Camera camera) {
            camera.addCallbackBuffer(bytes);
        }

        public void startPreview() {
            camera = Camera.open();
            camera.setDisplayOrientation(90);
            try {
                camera.setPreviewDisplay(getSurfaceHolder());
            } catch (IOException e) {
                e.printStackTrace();
            }

            camera.startPreview();
        }

        public void stopPreview() {
            if (camera != null) {
                camera.stopPreview();
                camera.setPreviewCallback(null);
                camera.release();
                camera = null;
            }


        }
    }
}
