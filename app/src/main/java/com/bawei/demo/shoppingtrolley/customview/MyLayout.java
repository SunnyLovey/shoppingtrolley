package com.bawei.demo.shoppingtrolley.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bawei.demo.shoppingtrolley.R;

import java.util.Random;

public class MyLayout extends RelativeLayout {
    Random random;
    Drawable blue,red,yellow;
    LayoutParams lp;
    int dWidth,dHeight;
    int mWidth,mHeight;

    Drawable[] drawables = new Drawable[3];

    public MyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    private void init() {

        dWidth = BitmapFactory.decodeResource(getResources(), R.drawable.pl_blue).getWidth();
        dHeight = BitmapFactory.decodeResource(getResources(), R.drawable.pl_blue).getHeight();

        random = new Random();
        blue = getResources().getDrawable(R.drawable.pl_blue);
        red = getResources().getDrawable(R.drawable.pl_red);
        yellow = getResources().getDrawable(R.drawable.pl_yellow);

        drawables[0] = blue;
        drawables[1] = red;
        drawables[2] = yellow;

        lp = new LayoutParams(dWidth, dHeight);
        lp.addRule(CENTER_HORIZONTAL, TRUE); // 这里的TRUE 要注意 不是true
        lp.addRule(ALIGN_PARENT_BOTTOM, TRUE);
    }
    public void addLayout(){
        ImageView iv =new ImageView(getContext());
        iv.setLayoutParams(lp);
        addView(iv);
        iv.setImageDrawable(drawables[random.nextInt(3)]);
        getAnimator(iv);
    }


    private void getAnimator(final View iv){
        ValueAnimator va = ValueAnimator.ofObject(new BezierEvaluator(new PointF(mWidth/2-dWidth/2,mHeight-dHeight), getPointF()), new PointF(
                        (mWidth - dWidth) / 2, mHeight - dHeight),
                new PointF(random.nextInt(getWidth()), 0));// 随机
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                iv.setX(pointF.x);
                iv.setY(pointF.y);

                // 这里偷个懒,顺便做一个alpha动画,这样alpha渐变也完成啦
                iv.setAlpha(1 - animation.getAnimatedFraction());
            }
        });



        ObjectAnimator alpha = ObjectAnimator.ofFloat(iv, "alpha", 0.2f,1.0f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(iv, "scaleX", 0.2f,1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(iv, "scaleY", 0.2f,1.0f);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(3000);
        set.playTogether(alpha,scaleX,scaleY);
        set.play(va).after(alpha);
        set.setTarget(iv);
        set.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
              //  System.out.println("结束");
                removeView(iv);
            }
        });
        set.start();
    }

    private PointF getPointF(){

        PointF p = new PointF();
        p.x = random.nextInt(mWidth);
        p.y = 50;
        return p;
    }

}
