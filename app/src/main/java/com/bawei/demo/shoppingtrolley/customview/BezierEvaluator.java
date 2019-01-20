package com.bawei.demo.shoppingtrolley.customview;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

public class BezierEvaluator implements TypeEvaluator<PointF> {
    private PointF pointF1;//途径的两个点
    private PointF pointF2;
    public BezierEvaluator(PointF pointF1,PointF pointF2){
        this.pointF1 = pointF1;
        this.pointF2 = pointF2;
        System.out.println("p1="+pointF1);
        System.out.println("p2="+pointF2);
    }
    @Override
    public PointF evaluate(float time, PointF startValue,
                           PointF endValue) {

        float timeLeft = 1.0f - time;
        PointF point = new PointF();//结果

        PointF point0 = (PointF)startValue;//起点

        PointF point3 = (PointF)endValue;//终点
        System.out.println("time"+time);
        System.out.println("p0="+point0);
        System.out.println("p3="+point3);
        //代入公式
        point.x = timeLeft * timeLeft * timeLeft * (point0.x)
                + 3 * timeLeft * timeLeft * time * (pointF1.x)
                + 3 * timeLeft * time * time * (pointF2.x)
                + time * time * time * (point3.x);

        point.y = timeLeft * timeLeft * timeLeft * (point0.y)
                + 3 * timeLeft * timeLeft * time * (pointF1.y)
                + 3 * timeLeft * time * time * (pointF2.y)
                + time * time * time * (point3.y);
        return point;
    }
}
