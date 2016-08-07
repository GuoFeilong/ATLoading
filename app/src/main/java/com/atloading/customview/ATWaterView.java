package com.atloading.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.atloading.R;

/**
 * Created by jsion on 16/8/7.
 */
public class ATWaterView extends View {
    private static final float WAVEY_SCALE = 7 / 8.F;
    private static final float CONTROLY_SCALE = 17 / 16.F;
    private Bitmap mBitmap;
    private int defW, defH;
    private float controlX, controlY;
    private float waveY;
    private Paint paint;
    private int waterColor;
    private PorterDuffXfermode porterDuffXfermode;
    private Path path;
    private Canvas mCanvas;
    private Bitmap bg;
    private boolean isIncrease;
    private boolean isReflesh = true;


    public ATWaterView(Context context) {
        this(context, null);
    }

    public ATWaterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ATWaterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ATWaterView, defStyleAttr, R.style.def_waterview);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.ATWaterView_water_bottle:
                    mBitmap = BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(attr, 0));
                    defW = mBitmap.getWidth();
                    defH = mBitmap.getHeight();
                    break;
                case R.styleable.ATWaterView_water_color:
                    waterColor = typedArray.getColor(attr, Color.BLACK);
                    break;
            }
        }
        typedArray.recycle();
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width, height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize + getPaddingLeft() + getPaddingRight();
        } else {
            width = this.defW + getPaddingLeft() + getPaddingRight();
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(width, widthSize);
            }

        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize + getPaddingTop() + getPaddingBottom();
        } else {
            height = this.defH + getPaddingTop() + getPaddingBottom();

            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, heightSize);
            }

        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTargetBitmap();
        canvas.drawBitmap(bg, getPaddingLeft(), getPaddingTop(), null);
        if (isReflesh) {
            invalidate();
        }
    }


    private Paint creatPaint(int paintColor, int textSize, Paint.Style style) {
        Paint paint = new Paint();
        paint.setColor(paintColor);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setTextSize(textSize);
        paint.setStyle(style);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        return paint;
    }


    private void init() {
        paint = creatPaint(waterColor, 0, Paint.Style.FILL);
        waveY = WAVEY_SCALE * defH;
        controlY = CONTROLY_SCALE * defH;
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        path = new Path();
        mCanvas = new Canvas();
        bg = Bitmap.createBitmap(defW, defH, Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(bg);
    }

    private void drawTargetBitmap() {
        path.reset();
        bg.eraseColor(getResources().getColor(android.R.color.transparent));
        // 当控制点的x坐标大于或等于终点x坐标时更改标识值
        if (controlX >= defW + 1 / 2 * defW) {
            isIncrease = false;
        }
        // 当控制点的x坐标小于或等于起点x坐标时更改标识值
        else if (controlX <= -1 / 2 * defW) {
            isIncrease = true;
        }

        // 根据标识值判断当前的控制点x坐标是该加还是减
        controlX = isIncrease ? controlX + 10 : controlX - 10;
        if (controlY >= 0) {
            // 波浪上移
            controlY -= 1;
            waveY -= 1;
        } else {
            // 超出则重置位置
            waveY = WAVEY_SCALE * defH;
            controlY = CONTROLY_SCALE * defH;
        }

        // 贝塞尔曲线的生成
        path.moveTo(0, waveY);
        // 两个控制点通过controlX，controlY生成
        path.cubicTo(controlX / 2, waveY - (controlY - waveY), (controlX + defW) / 2, controlY, defW, waveY);
        // 与下下边界闭合
        path.lineTo(defW, defH);
        path.lineTo(0, defH);
        // 进行闭合
        path.close();
        mCanvas.drawBitmap(mBitmap, 0, 0, paint);
        paint.setXfermode(porterDuffXfermode);
        mCanvas.drawPath(path, paint);
        paint.setXfermode(null);
    }


    public boolean isReflesh() {
        return isReflesh;
    }


    public void setReflesh(boolean isReflesh) {
        this.isReflesh = isReflesh;
        postInvalidate();
    }


}
