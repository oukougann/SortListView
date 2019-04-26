package com.example.wanghong.sortlistview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.wanghong.sortlistview.R;

public class SideBar extends View {

    public static String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};

    private int choose = -1;

    private TextView textViewDialog;

    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;

    /**
     * 为SideBar显示字母的TextView
     *
     * @param textViewDialog
     */
    public void setTextView(TextView textViewDialog) {
        this.textViewDialog = textViewDialog;
    }

    public SideBar(Context context) {
        super(context);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     *
     * 重写的onDraw的方法
     *
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        int singleHeight = height/alphabet.length; //获取每一个字母的高度
        Paint paint = new Paint();
        for (int i = 0; i < alphabet.length; i++) {
            paint.setColor(Color.rgb(33, 65, 98));
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(20);

            //选中的状态
            if (i == choose) {
                paint.setColor(Color.parseColor("#3399ff"));
                paint.setFakeBoldText(true); //设置是否为粗体文字
            }

            //x坐标等于=中间-字符串宽度的一半
            float posX = width/2 - paint.measureText(alphabet[i])/2;
            float posY = singleHeight*i + singleHeight;
            canvas.drawText(alphabet[i], posX, posY, paint);
            paint.reset(); //重置画笔
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY(); //点击y坐标
        int oldChoose = choose;

        OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        int c = (int)(y/getHeight()*alphabet.length);

        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackgroundDrawable(new ColorDrawable(0x00000000));
                choose = -1;
                invalidate();
                if (textViewDialog != null) {
                    textViewDialog.setVisibility(INVISIBLE);
                }
                break;

            default:
                setBackgroundResource(R.drawable.sidebar_background);
                if (oldChoose != c) {
                    if (c > 0 && c < alphabet.length) {
                        if (listener != null) {
                            listener.onTouchingLetterChangedListener(alphabet[c]);
                        }
                        if (textViewDialog != null) {
                            textViewDialog.setText(alphabet[c]);
                            textViewDialog.setVisibility(VISIBLE);
                        }
                        choose = c;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    /**
     * 向外松开的方法
     *
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener {
        public void onTouchingLetterChangedListener(String str);
    }
}
