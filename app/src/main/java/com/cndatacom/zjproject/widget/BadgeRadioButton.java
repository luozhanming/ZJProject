package com.cndatacom.zjproject.widget;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PathMeasure;
import android.util.AttributeSet;

/**
 * 加Bagde的RadioGroup
 * Created by cdc4512 on 2018/1/8.
 */

public class BadgeRadioButton extends android.support.v7.widget.AppCompatRadioButton {

    private int num = 0;
    private Paint paint;

    public BadgeRadioButton(Context context) {
        super(context);
    }

    public BadgeRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(30);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (num != 0) {
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            paint.setColor(Color.RED);
            canvas.drawCircle(width / 2 + 36, height / 2 - 64, 30, paint);
            paint.setColor(Color.WHITE);
            canvas.drawText(num + "", width / 2 + 36, height / 2 - 64 + 13, paint);
        }

    }


    public void setBadgeNum(int num) {
        this.num = num;
        invalidate();
    }

    public int getBadgeNum() {
        return this.num;
    }


}
