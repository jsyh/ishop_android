package com.jsyh.shopping.uilibrary.uiutils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;
import android.util.DisplayMetrics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sks on 2015/9/16.
 */
public class Utils {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale - 0.5f);
    }

    /** * 根据手机的分辨率从 px(像素) 的单位 转成为 dp */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    // 获取屏幕的宽度
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        return width;
    }

    /**
     * 删除单个文件
     *
     * @param filePath
     *            被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    public static boolean fileIsExist(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return true;
        }
        return false;
    }

    /**
     * 删除文件夹以及目录下的文件
     *
     * @param filePath
     *            被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String filePath) {
        boolean flag = false;
        // 如果filePath不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        // 遍历删除文件夹下的所有文件(包括子目录)
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                // 删除子文件
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            } else {
                // 删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag)
            return false;
        // 删除当前空目录
        return dirFile.delete();
    }

    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *
     * @param filePath
     *            要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public boolean DeleteFolder(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        } else {
            if (file.isFile()) {
                // 为文件时调用删除文件方法
                return deleteFile(filePath);
            } else {
                // 为目录时调用删除目录方法
                return deleteDirectory(filePath);
            }
        }
    }

    /**
     *
     * @param b
     *            Bitmap
     * @return 图片存储的位置
     * @throws FileNotFoundException
     */
    public static String saveImg(String path, Bitmap b, String name) {
        File mediaFile = new File(path + File.separator + name);
        if (mediaFile.exists()) {
            mediaFile.delete();
        }
        if (!new File(path).exists()) {
            new File(path).mkdirs();
        }
        try {
            mediaFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(mediaFile);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            b.recycle();
            b = null;
            System.gc();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mediaFile.getPath();
    }

    /**
     * 复制单个文件
     *
     * @param oldPath
     *            String 原文件路径 如：c:/fqf.txt
     * @param newPath
     *            String 复制后路径 如：f:/fqf.txt
     * @return boolean
     *//*
    public static void copyFile(String oldPath, String newPath) {
        File mediaFile = new File(newPath);
        if (mediaFile.exists()) {
            mediaFile.delete();
        }
        if (!new File(Settings.imagefilepath).exists()) {
            new File(Settings.imagefilepath).mkdirs();
        }
        try {
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { // 文件存在时
                InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }*/

    public static void creatFolder(String path) {
        File dbFolder = new File(path);
        // 目录不存在则自动创建目录
        if (!dbFolder.exists()) {
            dbFolder.mkdirs();
        }
    }

    // 校验Tag Alias 只能是数字,英文字母和中文
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]{0,}$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    public static int getVersionName(Context context) {
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo;
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            String version = packInfo.versionName;
            version = version.replace(".", "").trim();
            return Integer.parseInt(version);
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean ExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }

    public static long getSDFreeSize() {
        // 取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        // 获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        // 空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        return (freeBlocks * blockSize) / 1024 / 1024; // 单位MB
    }
    public static long getSDAllSize(){
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //获取所有数据块数
        long allBlocks = sf.getBlockCount();
        return (allBlocks * blockSize)/1024/1024; //单位MB
    }
    public static List<String> getImageFilePaths(String path) {
        File folder = new File(path);
        List<String> imageFilePath=new ArrayList<String>();
        if (folder.exists()) {
            File[] files =folder.listFiles();
            for(int i=0;i<files.length;i++){
                String fileName = files[i].getName().toLowerCase();
//					 .jpg .jpeg .png
                if (fileName.endsWith(".jpg")||fileName.endsWith(".jpeg")||fileName.endsWith(".png")) {
                    imageFilePath.add(files[i].getPath());
                }
            }
        }
        return imageFilePath;
    }
}
