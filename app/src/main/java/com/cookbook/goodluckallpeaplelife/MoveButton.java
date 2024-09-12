package com.cookbook.goodluckallpeaplelife;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MoveButton extends View {

    Paint paint;
    public MoveButton(Context context){
        super(context);
        init(context);
    }

    public MoveButton(Context context, AttributeSet attrs){
        super(context, attrs);

        init(context);
    }

    private void init(Context context){
        Paint paint = new Paint();
        paint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(100,100,200,200, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        return super.onTouchEvent(event);
    }
}
