package com.example.administrator.zzzz;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by zxb on 2018/3/3.
 */
public class SystemUtils {

    /**
     * 获取android系统版本 如：Android5.1.1
     */
    public static String getOSVersion() {
        String release = Build.VERSION.RELEASE; // android系统版本号
        release = "Android " + release;
        return release;
    }


    /**
     * 获取Gridle配置里边 app的版本号   如 ：versionCode 1
     *
     * @return
     */
    public static String getVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        int version = 0;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);

            version = info.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version + "";

    }


    /**
     * 获取手机型号  如：vivo X6S A
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }


    /**
     * 获取Gridle配置里边 app版本名字    如: versionName 2.0
     *
     * @return
     */
    public static String getVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            //第二个参数代表额外的信息，例如获取当前应用中的所有的Activity
            PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES
            );
            ActivityInfo[] activities = packageInfo.activities;
            //showActivities(activities);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void showActivities(ActivityInfo[] activities) {
        for (ActivityInfo activity : activities) {
            //Log.i("activity=========", activity.name);
        }
    }

    /**
     * 获取系统序列号  ，手机的唯一标识
     */
    public static String getAndroidId(Context context) {
        String pValue = null;
        try {
            pValue = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } catch (Exception e) {

        }
        return pValue;
    }


//    /**
//     * 修改UserAgent
//     *
//     * @param context
//     * @return
//     */
//    public static String getUserAgent(Context context) {
//        String data = "KeGuanDiChan," + PackageUtils.getVersionName(context) + ";Android," +
//                Build.BRAND + " " + Build.MODEL + "," + Build.VERSION.RELEASE;
////       "KeGuanDiChan,1.0.0;Android,Xiaomi MIX2,8.0.0"
////        Log.e("aaaaa",data);
//        return data;
//    }

    /**
     * 这是使用adb shell命令来获取mac地址的方式
     *
     * @return
     */
    public static String getMac() {
        String macSerial = null;
        String str = "";

        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }
        return macSerial;
    }

//    //右侧进入显示View
//    public static void showImgAnimation(Context context, View view) {
//        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_right_in);
//        view.startAnimation(animation);
//    }
//
//    //右侧退出显示View
//    public static void hideImgAnimation(Context context, View view) {
//        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_right_out);
//        view.startAnimation(animation);
//    }

    //隐藏image的动画一种方式
    public static void HiddenView(ImageView imageView) {
        ScaleAnimation animation = new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(2000);
        imageView.startAnimation(animation);
        imageView.setVisibility(View.GONE);
    }

    /**
     * 跳转到权限设置界面
     */
    public static void getAppDetailSettingIntent(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(intent);
    }

    /**
     * 获取屏幕宽度
     *
     * @param ctx
     * @return
     */
    public static int getPhoneScreenWidth(Activity ctx) {
        WindowManager manager = ctx.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        return width;
    }

    /**
     * 获取屏幕高度
     *
     * @param ctx
     * @return
     */
    public static int getPhoneScreenHight(Activity ctx) {
        WindowManager manager = ctx.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int height = outMetrics.heightPixels;
        return height;
    }

    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');  //这边+'0'，不是拼接，在Java和C#中是8+0的ASCII码得到8在ASCII中的编码值，然后通过(char)转成字符'8'
    }

    /**
     * 调用拨号界面,用户决定是否拨打
     *
     * @param phone 电话号码
     */
    public static void call(String phone, Context context) {
        Intent intent = null;
        if (phone != null && !phone.equals("")) {
            if (phone.contains("tel:")) {
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse(phone));
            } else {
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "暂无电话号码!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 直接拨打电话
     *
     * @param phone
     * @param context
     */
    public static void callPhone(String phone, Context context) {
        Intent intent = null;
        if (phone != null && !phone.equals("")) {
            if (phone.contains("tel:")) {
                intent = new Intent(Intent.ACTION_CALL, Uri.parse(phone));
            } else {
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
            }
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "暂无电话号码!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 调用系统邮箱发送邮件(无附件发送)
     *
     * @param email   接收人邮箱
     * @param title   邮件标题
     * @param content 邮件内容
     * @param context 上下文
     */
    public static void sendEmail(String email, String title, String content, Context context) {
        if (email.equals("")) {
            Toast.makeText(context, "没有接收人邮箱,不能发送邮件", Toast.LENGTH_SHORT).show();
        } else {
            if (title == null || title.equals("")) {
                title = "标题";
            } else if (content == null || content.equals("")) {
                content = "请编辑内容";
            }
            Intent data = new Intent((Intent.ACTION_SENDTO));
            data.setData(Uri.parse("mailto:" + email));
            data.putExtra(Intent.EXTRA_SUBJECT, title);
            data.putExtra(Intent.EXTRA_TEXT, content);
            context.startActivity(data);
        }
    }

    /**
     * 返回分享的平台名称
     *
     * @param share_media
     * @return
     */
    public static String ShareTypeName(String share_media) {

        if (share_media != null && !share_media.toString().equals("") && share_media.toString() != null) {
            if (share_media.equals("QQ")) {
                return "QQ";
            } else if (share_media.equals("QZONE")) {
                return "QQ空间";
            } else if (share_media.equals("WEIXIN")) {
                return "微信";
            } else if (share_media.equals("WEIXIN_CIRCLE")) {
                return "微信朋友圈";
            } else if (share_media.equals("WEIXIN_FAVORITE")) {
                return "微信收藏";
            } else if (share_media.equals("SINA")) {
                return "新浪微博";
            } else if (share_media.equals("SMS")) {
                return "短信";
            } else if (share_media.equals("EMAIL")) {
                return "邮箱";
            } else {
                return "";
            }
        } else {
            return null;
        }

    }

    public static String getAsciiChar(int ascllValue) {
        char c = (char) ascllValue;
        return c + "";
    }

    public static <T> boolean notEmpty(List<T> list) {
        return !isEmpty(list);
    }

    public static <T> boolean isEmpty(List<T> list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }

    // 将px值转换为dip或dp值
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    // 将dip或dp值转换为px值
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    // 将px值转换为sp值
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    // 将sp值转换为px值
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    // 屏幕宽度（像素）
    public static int getWindowWidth(Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    // 屏幕高度（像素）
    public static int getWindowHeight(Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;
    }

    // 根据Unicode编码判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    // 判断中文汉字和符号
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
     */
    public static boolean isEmpty(String value) {
        if (value != null && !"".equalsIgnoreCase(value.trim()) && !"null".equalsIgnoreCase(value.trim())) {
            return false;
        } else {
            return true;
        }
    }

//    public static Bitmap createQRcodeImage(String url) {
//        int w = 150, h = 150;
//        Bitmap bitmap = null;
//        try {
//            //判断URL合法性
//            if (url == null || "".equals(url) || url.length() < 1) {
//                return null;
//            }
//            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
//            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//            hints.put(EncodeHintType.MARGIN, 1 + "");
//            //图像数据转换，使用了矩阵转换
//            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, w, h, hints);
//            int[] pixels = new int[w * h];
//            //下面这里按照二维码的算法，逐个生成二维码的图片，
//            //两个for循环是图片横列扫描的结果
//            for (int y = 0; y < h; y++) {
//                for (int x = 0; x < w; x++) {
//                    if (bitMatrix.get(x, y)) {
//                        pixels[y * w + x] = 0xff000000;
//                    } else {
//                        pixels[y * w + x] = 0xffffffff;
//                    }
//                }
//            }
//            //生成二维码图片的格式，使用ARGB_8888
//            bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//            bitmap.setPixels(pixels, 0, w, 0, 0, w, h);
//            //显示到我们的ImageView上面
//
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//        return bitmap;
//    }


    public static String openDoor(int type, String door_position) {
        String order = "";
        String[] temp = {"57", "4B", "4C", "59", "21", "00", "82", "01", "30", "30", "30", "30", "30", "30", "30", "30", "30", "30", "30", "30", "30", "30", "30", "30", "30", "30", "30", "30", "30", "30", "30", "30"};
        StringBuilder builder = new StringBuilder();
        switch (type) {
            case 0:
                if (door_position.substring(0, 1).equals("0")) {
                    temp[7] = door_position + "";
                } else {
                    String hexTemp = Integer.toHexString(16);
                    hexTemp = hexTemp.toUpperCase();
                    if (hexTemp.length() == 1) {

                        hexTemp = "0" + hexTemp;
                    }
                    temp[7] = hexTemp;
                }

                for (String tempString : temp) {
                    builder.append(tempString);
                    builder.append(" ");
                }
                builder.append(xor(temp));
                break;
            case 1:

                break;
            case 2:

                break;
        }
        return builder.toString();
    }

    public static String xor(String[] a) {

        int temp = 0;
        for (int i = 0; i < a.length; i++) {
            temp ^= Integer.parseInt(a[i], 16);
        }
        return Integer.toHexString(temp).toUpperCase() + "";
    }
}
