package com.jsyh.shopping.uilibrary.uiutils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Base64;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {

    public static Bitmap CreatImage(String imgStr) {
        byte[] byteIcon = Base64.decode(imgStr, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteIcon, 0, byteIcon.length);
        return bitmap;
    }

    /**
     * 压缩指定路径的图片，并得到图片对象
     *
     * @param path    bitmap source path
     * @return Bitmap {@link Bitmap}
     */
    public final static Bitmap compressBitmap(String path, int rqsW, int rqsH) {
        File file = new File(path);
        if (file.exists()) {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            options.inSampleSize = caculateInSampleSize(options, rqsW, rqsH);
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            /**
             * 获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转
             */
            int degree = ImageUtils.readPictureDegree(file.getAbsolutePath());
            bitmap = ImageUtils.rotaingImageView(degree, bitmap);
            return bitmap;
        } else
            return null;
    }

    /**
     * caculate the bitmap sampleSize
     *
     * @return
     */
    public final static int caculateInSampleSize(BitmapFactory.Options options, int rqsW, int rqsH) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (rqsW == 0 || rqsH == 0)
            return 1;
        if (height > rqsH || width > rqsW) {
            final int heightRatio = Math.round((float) height / (float) rqsH);
            final int widthRatio = Math.round((float) width / (float) rqsW);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 从Assets中读取图片
     */
    public static Bitmap getImageFromAssetsFile(Context context, String fileName) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 从Assets中读取图片
     */
    public static Bitmap getImageFromSD(Context context, String fileName) {
        File mFile = new File(fileName);
        // 若该文件存在
        if (mFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(fileName);
            int bmpWidth = bitmap.getWidth();
            int bmpHeight = bitmap.getHeight();
            int screenWidth = Utils.getScreenWidth(context) - Utils.dip2px(context, 20);
            if (bmpWidth > screenWidth) {
                /* 设置图片缩小的比例 */
                float scale = (float) (screenWidth * 1.0 / bmpWidth);
                //LogUtil.d("屏幕宽度："+screenWidth+"图片宽度"+bmpWidth+"缩放比例"+scale);
                /* 计算出这次要缩小的比例 */
//		        float scaleWidth=(float) (bmpWidth*scale);   
//		        float scaleHeight=(float) (bmpHeight*scale); 
		        /* 产生reSize后的Bitmap对象 */
                Matrix matrix = new Matrix();
                matrix.postScale(scale, scale);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bmpWidth,
                        bmpHeight, matrix, true);
            }
            return bitmap;
        }
        return null;
    }

    /**
     * 从Assets中读取图片原图
     */
    public static Bitmap getBigImageFromSD(Context context, String fileName) {
        File mFile = new File(fileName);
        // 若该文件存在
        if (mFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(fileName);
            return bitmap;
        }
        return null;
    }
}
