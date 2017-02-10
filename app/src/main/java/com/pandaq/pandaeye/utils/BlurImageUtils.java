package com.pandaq.pandaeye.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.pandaq.pandaqlib.imagerblur.FastBlur;

/**
 * Created by PandaQ on 2016/9/7.
 * email : 767807368@qq.com
 * 模糊处理图像的工具类
 */
public class BlurImageUtils {

    /**
     * @param imageView   待处理的imageView
     * @param scaleFactor 模糊处理之前的进行采样缩小被处理图片大小的系数
     * @param blurf        模糊系数
     * @return 处理后的模糊图像
     */
    public static Bitmap blur(ImageView imageView, float scaleFactor, int blurf) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        //创建一个长宽等比缩小的Bitmap
        Bitmap overlay = Bitmap.createBitmap((int) (width / scaleFactor), (int) (height / scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        //将canvas按照bitmap等量缩放，模糊处理的图片才能显示正常
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        //对采样后的bitmap进行模糊处理，缩放采样后的图片处理比原图处理要省很多时间和内存开销
        overlay = FastBlur.doBlur(overlay, blurf, false);
        return overlay;
    }
}
