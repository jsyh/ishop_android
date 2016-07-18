package com.jsyh.xjd.qrzxing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.jsyh.xjd.R;

import java.io.UnsupportedEncodingException;

/**
 * Created by sks on 2015/10/14.
 * 生成二维码
 */
public class CreateQRCode {

    //图片的宽度
    private static final int IMAGE_HALFWIDTH = 30;
    private static Bitmap mBitmap = null;
    //传过来的信息最好做一个加密，暂时先用MD5吧
    public static Bitmap getQRCode(Context context, String content){

        // 构造需要插入的图片对象
        mBitmap = ((BitmapDrawable) context.getResources().getDrawable(
                R.mipmap.ic_launcher)).getBitmap();
        // 缩放图片
        Matrix m = new Matrix();
        float sx = (float) 2 * IMAGE_HALFWIDTH / mBitmap.getWidth();
        float sy = (float) 2 * IMAGE_HALFWIDTH / mBitmap.getHeight();
        m.setScale(sx, sy);
        // 重新构造一个40*40的图片
        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(),
                mBitmap.getHeight(), m, false);
        try {
            String s = new String(content.getBytes(),"ISO-8859-1");
            return cretaeBitmap(s);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static Bitmap cretaeBitmap(String str) throws WriterException {
        // 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(str,
                BarcodeFormat.QR_CODE, 500, 500);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        // 二维矩阵转为一维像素数组,也就是一直横着排了
        int halfW = width / 2;
        int halfH = height / 2;
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
                        && y > halfH - IMAGE_HALFWIDTH
                        && y < halfH + IMAGE_HALFWIDTH) {
                    pixels[y * width + x] = mBitmap.getPixel(x - halfW
                            + IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);
                } else {
                    if (matrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    }
                }

            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

        return bitmap;
    }
}
