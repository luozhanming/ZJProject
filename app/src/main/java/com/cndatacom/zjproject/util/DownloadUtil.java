package com.cndatacom.zjproject.util;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;

/**
 * Created by cdc4512 on 2018/1/5.
 */

public class DownloadUtil {


    /**
     * 下载apk
     *
     * @param context
     * @param uri         apk下载地址
     * @param dstFile     apk下载后的文件名
     * @param title       下载标题
     * @param description 下载描述
     */
    public static long downloadApk(Context context, String uri
            , String dstFile, String title
            , String description) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request req = new DownloadManager.Request(Uri.parse(uri));
        req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        req.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, dstFile);
        req.setTitle(title);
        req.setDescription(description);
        req.setMimeType("application/vnd.android.package-archive");
        return downloadManager.enqueue(req);
    }
}
