package helper;


import javafx.util.Pair;
import objects.DraggableCircle;
import objects.DraggableLine;

public class MathHelper {
    public static double distance(DraggableCircle x, DraggableCircle y){
        double res = 0;
        try {res = Math.sqrt(Math.pow((x.getCenterX() - y.getCenterX()),2) +
                Math.pow((x.getCenterY() - y.getCenterY()),2));}
        catch (NullPointerException e){
            e.fillInStackTrace();
        }
        return res;
    }

    public static Pair<Double,Double> getPinionLocation(DraggableCircle center, double length, int degree) {
        double x = center.getCenterX() + length*Math.cos(Math.toRadians(degree));
        double y = center.getCenterY() + length*Math.sin(Math.toRadians(degree));
        return new Pair<>(x, y);
    }

    public static Pair<Double, Double> getJoint(DraggableLine firstRod, DraggableLine secondRod,
                                                DraggableCircle firstPin, DraggableCircle secondPin, int side){
        double a = firstRod.getLength();
        double b = secondRod.getLength();
        double l = MathHelper.distance(firstPin, secondPin);
        double x1 = 0 , y1 = 0;
        try {
            x1 = firstPin.getCenterX();
            y1 = firstPin.getCenterY();
        }
        catch (NullPointerException e){ e.fillInStackTrace();}
        double x2 = secondPin.getCenterX();
        double y2 = secondPin.getCenterY();
        double h = b * b - Math.pow(((l * l + b * b - a * a) / 2 / l), 2);
        h = Math.sqrt(h);
        double disFromFirstToH = Math.sqrt(a * a - h * h);
        double xh = x1 + disFromFirstToH * (x2 - x1) / l;
        double yh = y1 + disFromFirstToH * (y2 - y1) / l;
        double t = -1 * (y1 - yh) / (x1 - xh);
        double t2 = side * Math.sqrt(h * h / (t * t + 1));
        double yj = t2 + yh;
        double xj = xh + t * t2;
        if (side==1){
            double t3 = -1*t2;
            double xj2 = xh + t*t3;
            if (xj2 < xj){
                xj = xj2;
                yj = t3 + yh;
            }
        }
        if (!Double.isNaN(xj) && !Double.isNaN(yj)) {
            return new Pair<Double,Double>(xj,yj);
        }
        else{
            return null;
        }
    }

    public static Pair<Double,Double> getMainPin(DraggableCircle pin , DraggableCircle jointPin,
                                                 DraggableLine lenToTheJoint, DraggableLine lenAfterJoint){
        double x1 = pin.getCenterX();
        double y1 = pin.getCenterY();
        double xj = jointPin.getCenterX();
        double yj = jointPin.getCenterY();
        double xm = x1 + (xj - x1) * (lenToTheJoint.getLength()+lenAfterJoint.getLength())
                / lenToTheJoint.getLength();
        double ym = y1 + (yj - y1) * (lenToTheJoint.getLength()+lenAfterJoint.getLength())
                / lenToTheJoint.getLength();
        if (!Double.isNaN(xm) && !Double.isNaN(ym)) {
            return new Pair<>(xm,ym);
        }
        else return null;
    }

    public static int getAngle(DraggableCircle center, DraggableCircle pinionCircle) {
        double x = pinionCircle.getCenterX() - center.getCenterX(),
        y = pinionCircle.getCenterY() - center.getCenterY(), ratio;
        try{ratio = y/x;}
        catch (ArithmeticException ignored) {ratio = 0;}

        int result = (int) Math.toDegrees(Math.atan(ratio));
        if(x<0 & y>=0){
            result += 180;
        }
        else if(x<0 & y<0){
            result += 180;
        }
        else if(x>0 & y<0){
            result += 360;
        }
        else if(x==0 & y<0){
            result = 270;// this is not working;
        }
        else if(x==0 & y>0){
            result = 90; // this is not working;
        }
        result -= 360;
        return result;
    }
}
