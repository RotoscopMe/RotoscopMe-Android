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
    private Paint paintRubber;
    private Paint currentPaint;
    private Paint bitmapPaint;
    private Paint backgroundPaint;
    private Path path;

    private static final float TOLERANCE = 5;

    int width;
    int height;

    private float mX;
    private float mY;

    DrawActivity parent;

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

    public void setParent(DrawActivity parent)
    {
        this.parent = parent;
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
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(12);

        paintRubber = new Paint();
        paintRubber.setAntiAlias(true);
        paintRubber.setDither(true);
        paintRubber.setColor(Color.WHITE);
        paintRubber.setStyle(Paint.Style.STROKE);
        paintRubber.setStrokeJoin(Paint.Join.ROUND);
        paintRubber.setStrokeCap(Paint.Cap.ROUND);
        paintRubber.setStrokeWidth(12);
        //paintRubber.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        currentPaint = paint;

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
        c.drawPath(path, currentPaint);
    }

    public void touchStart(float x, float y) {
        path.moveTo(x, y);
        mX = x;
        mY = y;
    }
    public void touchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            path.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }
    public void touchEnd() {
        path.lineTo(mX, mY);

        canvas.drawPath(path, currentPaint);
// kill this so we don't double draw
        path.reset();
    }

    public void touchUndo()
    {
        path.reset();
    }

    public void clearCanvas()
    {
        createCanvas();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        parent.handleTouch(event);
        /*float x = event.getX();
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
        }*/
        return true;
    }

    public void setSizePen(float size)
    {
        paint.setStrokeWidth(size);
    }

    public void setSizeRubber(float size)
    {
        paintRubber.setStrokeWidth(size);
    }

    public void getPen()
    {
        currentPaint = paint;
    }

    public void getRubber()
    {
        currentPaint = paintRubber;
    }

    public void setBlack()
    {
        paint.setColor(Color.BLACK);
    }

    public void setRed()
    {
        paint.setColor(Color.RED);
    }

    public void setBlue()
    {
        paint.setColor(Color.BLUE);
    }

    public void setGreen()
    {
        paint.setColor(Color.GREEN);
    }
}

