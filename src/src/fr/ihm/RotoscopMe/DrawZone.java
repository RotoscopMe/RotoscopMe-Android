package fr.ihm.RotoscopMe;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class DrawZone extends View {

    Context context;
    private Canvas canvas;
    private Bitmap bitmap;
    private Paint paint;
    private Paint bitmapPaint;
    private Paint backgroundPaint;
    private Path path;

    private static final float TOLERANCE = 5;

    int width;
    int height;

    private float mX;
    private float mY;

    public DrawZone(Context c)
    {
        super(c);
        context=c;
        createPainters();
    }

    public DrawZone(Context c, AttributeSet attr)
    {
        super(c, attr);
        context=c;
        createPainters();
    }

    public DrawZone(Context c, AttributeSet attr, int defStyle)
    {
        super(c, attr, defStyle);
        context=c;
        createPainters();
    }

    public void createCanvas()
    {
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    public void createPainters()
    {
        path = new Path();
        bitmapPaint = new Paint(Paint.DITHER_FLAG);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(12);

        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.WHITE);
        backgroundPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);



        canvas.drawPaint(backgroundPaint);
    }

    @Override
    protected void onDraw(Canvas c)
    {
        super.onDraw(c);
        c.drawBitmap(bitmap,0,0,bitmapPaint);
        c.drawPath(path, paint);
    }

    private void touchStart(float x, float y) {
        path.moveTo(x, y);
        mX = x;
        mY = y;
    }
    private void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            path.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }
    private void touchEnd() {
        path.lineTo(mX, mY);

        canvas.drawPath(path, paint);
// kill this so we don't double draw
        path.reset();
    }

    public void clearCanvas()
    {
        createCanvas();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchEnd();
                invalidate();
                break;
        }
        return true;
    }
}

