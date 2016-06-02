package com.shanghai.jingzhi.fragment;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.shanghai.jingzhi.R;
import com.shanghai.jingzhi.jingzhiutils.Default_P;
import com.shanghai.jingzhi.jingzhiutils.Log;
import com.shanghai.jingzhi.jingzhiutils.Utils;
import com.shanghai.jingzhi.model.RespData;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.xxutils.alerterview.OnItemClickListener;
import cn.com.xxutils.alerterview.XXAlertView;
import cn.com.xxutils.interfac.OnStartRequestListener;
import cn.com.xxutils.progress.XXSVProgressHUD;
import cn.com.xxutils.util.XXUtils;
import cn.com.xxutils.view.XXTouchImageView;


/**
 * Created by zhaowenyun on 16/5/30.
 */
public class F_L_C extends Fragment implements View.OnClickListener {
    private View view_main;
    private ImageView iv_touch;
    private ImageView iv_c;
    private boolean isShow = true;
    private ImageView iv_more;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view_main = inflater.inflate(R.layout.f_l_c, null);
        Log.d("F_L_C-onCreateView,view-" + view_main);
        initView();
        return view_main;
    }

    private void initView() {
        iv_c = (ImageView) view_main.findViewById(R.id.iv_c);
        iv_touch = (ImageView) view_main.findViewById(R.id.iv_touch);
        iv_touch.setOnTouchListener(new MulitPointTouchListener());
        showViwe();
    }

    private void showViwe() {
        new XXAlertView("提示", "请选择图片来源", "取消", null, new String[]{"相册", "拍照"}, getActivity(), XXAlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                Log.d("possionL:" + position);
                switch (position) {
                    case -1:
                        //取消
                        Utils.replaceFragment(getActivity(), new F_Home());
                        break;

                    case 1:
                        //拍照
                        Intent cameraintent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraintent, 0x03);
                        break;
                    case 0:
                        //相册
                        Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(picture, 0x04);
                        break;
                }
            }
        }).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            showViwe();
            return;
        }
        Bitmap bitmap_phone = null;
        if (requestCode == 0x03) {
            //来源为相机
            Bundle bundle = data.getExtras();
            //获取相机返回的数据，并转换为图片格式
            bitmap_phone = (Bitmap) bundle.get("data");
        } else if (requestCode == 0x04) {
            //来源为图库
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getActivity().getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            bitmap_phone = BitmapFactory.decodeFile(picturePath);
        }
        if (bitmap_phone == null) {
            Utils.Toast(getActivity(), "系统异常,请稍后再试");
            Utils.replaceFragment(getActivity(), new F_Home());
            return;
        }
        iv_touch.setImageBitmap(Default_P.bitmap);
        iv_c.setImageBitmap(bitmap_phone);
        Utils.Toast(getActivity(), "请对定位的图片进行缩放");
        iv_more = (ImageView) getActivity().findViewById(R.id.iv_more);
        iv_more.setVisibility(View.VISIBLE);
        iv_more.setOnClickListener(this);
//        if (this.isVisible())
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    while (isShow) {
//                        try {
//                            Thread.sleep(5000);
//                        } catch (InterruptedException e) {
//                            Log.d("休眠抛出异常");
//                        }
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                new XXAlertView("提示", "是否发表该图片", "再调整调整", new String[]{"发表"}, null, getActivity(), XXAlertView.Style.Alert, new OnItemClickListener() {
//                                    @Override
//                                    public void onItemClick(Object o, int position) {
//                                        Log.d("p:" + position);
//                                        if (position == 0) {
//                                            isShow = false;
//
//                                        }
//                                    }
//                                }).show();
//                            }
//                        });
//                    }
//                }
//            }).start();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_more:
                Utils.Toast(getActivity(), "上传图片");
//                获取指定view的截图
                Log.d("getViewBitmap(view):" + getViewBitmap(view_main));
                //发表图片
                Map<String, String> map = new HashMap<>();
                String username = Utils.getNewSharedObject(Default_P.FileName_UserState).get(getActivity(), Default_P.Key_UserName, "").toString();
                String sessionid = Utils.getNewSharedObject(Default_P.FileName_UserState).get(getActivity(), Default_P.Key_Sessionid, "").toString();

                map.put("reqtype", "10");
                map.put("username", username);
                map.put("sessionid", sessionid);
                map.put("lat", Default_P.lat);
                map.put("lnt", Default_P.lnt);
                map.put("city", Default_P.city);
                map.put("image", XXUtils.bitmapToBase64(getViewBitmap(view_main)));
                XXSVProgressHUD.showWithStatus(getActivity(), "正在发表您的图片");
                XXUtils.StartRequest(getActivity(), map, Default_P.URL, "uploadimage", new OnStartRequestListener() {
                    @Override
                    public void Succ(String data) {
                        Utils.dissmiss(getActivity());
                        Log.d("back to upload image," + data);
                        RespData respData = new Gson().fromJson(data, RespData.class);
                        if (respData.getCode() == 9000) {
                            Utils.replaceFragment(getActivity(), new F_Home());
                            Utils.Toast(getActivity(), "发表成功");
                            iv_more.setVisibility(View.GONE);
                            Default_P.bitmap = null;
                            Default_P.city = null;
                            Default_P.lat = null;
                            Default_P.lnt = null;
                        } else {
                            Utils.Toast(getActivity(), "发表失败," + respData.getRespMsg());
                        }
                    }

                    @Override
                    public void Error(String errorMsg) {
                        Utils.dissmiss(getActivity());
                        XXSVProgressHUD.showErrorWithStatus(getActivity(), "上传失败," + errorMsg);
                    }
                });
                break;
        }
    }

    public class MulitPointTouchListener implements View.OnTouchListener {

        private static final String TAG = "Touch";
        // These matrices will be used to move and zoom image
        Matrix matrix = new Matrix();
        Matrix savedMatrix = new Matrix();

        // We can be in one of these 3 states
        static final int NONE = 0;
        static final int DRAG = 1;
        static final int ZOOM = 2;
        int mode = NONE;

        // Remember some things for zooming
        PointF start = new PointF();
        PointF mid = new PointF();
        float oldDist = 1f;

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            ImageView view = (ImageView) v;
            // Log.e("view_width",
            // view.getImageMatrix()..toString()+"*"+v.getWidth());
            // Dump touch event to log
            dumpEvent(event);

            // Handle touch events here...
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:

                    matrix.set(view.getImageMatrix());
                    savedMatrix.set(matrix);
                    start.set(event.getX(), event.getY());
                    //Log.d(TAG, "mode=DRAG");
                    mode = DRAG;


                    //Log.d(TAG, "mode=NONE");
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    oldDist = spacing(event);
                    //Log.d(TAG, "oldDist=" + oldDist);
                    if (oldDist > 10f) {
                        savedMatrix.set(matrix);
                        midPoint(mid, event);
                        mode = ZOOM;
                        //Log.d(TAG, "mode=ZOOM");
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    mode = NONE;
                    //Log.e("view.getWidth", view.getWidth() + "");
                    //Log.e("view.getHeight", view.getHeight() + "");

                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mode == DRAG) {
                        // ...
                        matrix.set(savedMatrix);
                        matrix.postTranslate(event.getX() - start.x, event.getY()
                                - start.y);
                    } else if (mode == ZOOM) {
                        float newDist = spacing(event);
                        //Log.d(TAG, "newDist=" + newDist);
                        if (newDist > 10f) {
                            matrix.set(savedMatrix);
                            float scale = newDist / oldDist;
                            matrix.postScale(scale, scale, mid.x, mid.y);
                        }
                    }
                    break;
            }

            view.setImageMatrix(matrix);
            return true; // indicate event was handled
        }

        private void dumpEvent(MotionEvent event) {
            String names[] = {"DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
                    "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?"};
            StringBuilder sb = new StringBuilder();
            int action = event.getAction();
            int actionCode = action & MotionEvent.ACTION_MASK;
            sb.append("event ACTION_").append(names[actionCode]);
            if (actionCode == MotionEvent.ACTION_POINTER_DOWN
                    || actionCode == MotionEvent.ACTION_POINTER_UP) {
                sb.append("(pid ").append(
                        action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
                sb.append(")");
            }
            sb.append("[");
            for (int i = 0; i < event.getPointerCount(); i++) {
                sb.append("#").append(i);
                sb.append("(pid ").append(event.getPointerId(i));
                sb.append(")=").append((int) event.getX(i));
                sb.append(",").append((int) event.getY(i));
                if (i + 1 < event.getPointerCount())
                    sb.append(";");
            }
            sb.append("]");
            //Log.d(TAG, sb.toString());
        }


        private float spacing(MotionEvent event) {
            float x = event.getX(0) - event.getX(1);
            float y = event.getY(0) - event.getY(1);
            return (float) Math.sqrt(x * x + y * y);
        }


        private void midPoint(PointF point, MotionEvent event) {
            float x = event.getX(0) + event.getX(1);
            float y = event.getY(0) + event.getY(1);
            point.set(x / 2, y / 2);
        }


    }

    private Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            Log.e("failed getViewBitmap(" + v + ")");
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }
}
