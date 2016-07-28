package com.example.flux.android.views.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.flux.android.R;

/**
 * 徽标绘制在右上角，BadgeView 的中心和原FrameLayout的右上角重合
 * 简单起见，绘制一个小红点即可。
 * Created by ntop on 16/7/27.
 */
public class BadgeLayout extends FrameLayout {
    private Paint paint = null;
    private int badgeColor = 0;
    private int badgeWith = 0;
    private int badgeHeight = 0;

    private int badgeX;
    private int badgeY;

    private boolean isBadgeVisible = false;

    public BadgeLayout(Context context) {
        this(context, null);
    }

    public BadgeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        parseAttr(context, attrs);
    }

    private void parseAttr(Context context, AttributeSet attrs) {
        if (attrs == null) return;

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BadgeLayout,
                0, 0);
        try {
            badgeWith = (int)a.getDimension(R.styleable.BadgeLayout_badgeWidth, 4);
            badgeHeight = (int)a.getDimension(R.styleable.BadgeLayout_badgeHeight, 4);
            badgeColor = a.getColor(R.styleable.BadgeLayout_badgeColor, Color.RED);
            paint.setColor(badgeColor);
        } finally {
            a.recycle();
        }

    }

    public void setBadgeWidth(int width) {
        this.badgeWith = width;
        requestLayout();
    }

    public void setBadgeHeight(int height) {
        this.badgeHeight = height;
        requestLayout();
    }

    public void setBadgeVisibility(boolean visible) {
        isBadgeVisible = visible;
        invalidate();
    }

    /**
     * 需要把徽标绘制在右上角，BadgeView 的中心和原FrameLayout的右上角重合
     * 大小扩大BadgeView的1/2高和1/2宽
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = getMeasuredWidth();
        int heightSize = getMeasuredHeight();

        widthSize += badgeWith/2;
        heightSize += badgeHeight/2;

        setMeasuredDimension(widthSize, heightSize);
    }



    /**
     * 需要把徽标绘制在右上角，BadgeView 的中心和原FrameLayout的右上角重合
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // 子View往左下角偏移
        View child = getChildAt(0);
        int width = child.getMeasuredWidth();
        int height = child.getMeasuredHeight();
        // BadgeView 的圆心
        badgeX = width;
        badgeY = badgeHeight/2;

        child.layout(0, badgeHeight/2, width, height + badgeHeight/2);
    }

    /**
     * 绘制
     * @param canvas
     */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isBadgeVisible) {
            canvas.drawCircle(badgeX, badgeY, badgeWith/2, paint);
        }
    }
}
