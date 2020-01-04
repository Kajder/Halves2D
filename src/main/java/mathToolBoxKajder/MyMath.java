package mathToolBoxKajder;

import java.awt.Point;

public class MyMath {

    public static double angleFromComponents(double componentX, double componentY) {
        if ((Math.signum(componentX) > 0) && (Math.signum(componentY) > 0))
            return Math.atan(componentY / componentX) - Math.PI / 2;
        if ((Math.signum(componentX) < 0) && (Math.signum(componentY) > 0))
            return Math.atan(componentY / componentX) + Math.PI / 2;
        if ((Math.signum(componentX) < 0) && (Math.signum(componentY) < 0))
            return Math.atan(componentY / componentX) + Math.PI / 2;
        if ((Math.signum(componentX) > 0) && (Math.signum(componentY) < 0))
            return Math.atan(componentY / componentX) - Math.PI / 2;
        if ((Math.signum(componentX) == 0) && (Math.signum(componentY) > 0)) return 0;
        if ((Math.signum(componentX) == 0) && (Math.signum(componentY) < 0)) return Math.PI;
        if ((Math.signum(componentX) < 0) && (Math.signum(componentY) == 0)) return Math.PI / 2;
        if ((Math.signum(componentX) > 0) && (Math.signum(componentY) == 0)) return -Math.PI / 2;
        return 0;
    }

    public static doublePair lineOnTwoPoints(doublePair P1, doublePair P2) {
        double a = (P2.second - P1.second) / (P2.first - P1.first);
        double b = P1.second - a * P1.first;
        doublePair parameters = new doublePair(a, b);

        return parameters;
    }

    public static doublePair lineOnTwoPoints(double x1, double x2, double y1, double y2) {
        double a = (y2 - y1) / (x2 - x1);
        double b = y1 - a * x1;
        doublePair parameters = new doublePair(a, b);
        return parameters;
    }

    public static doublePair interOfLines(doublePair line1, doublePair line2) {
        doublePair interPoint = new doublePair(0, 0);
        interPoint.first = (line2.second - line1.second) / (line1.first - line2.first);
        interPoint.second = line1.first * interPoint.first + line1.second;

        return interPoint;
    }

    public static doublePair interOfLines(double a1, double b1, double a2, double b2) {
        doublePair interPoint = new doublePair(0, 0);
        interPoint.first = (b2 - b1) / (a1 - a2);
        interPoint.second = a1 * interPoint.first + b1;

        return interPoint;
    }

    public static doublePair perpendicularToLineOnPoint(doublePair line, doublePair point) {
        double a = -1 / line.first;
        double b = point.second - a * point.first;
        doublePair newLine = new doublePair(a, b);

        return newLine;
    }

    public static double distanceTwoPoints(doublePair P1, doublePair P2) {
        double distX = P1.first - P2.first;
        double distY = P1.second - P2.second;
        return Math.sqrt(distX * distX + distY * distY);
    }

    public static double distanceTwoPoints(double x1, double y1, double x2, double y2) {
        double distX = x1 - x2;
        double distY = y1 - y2;
        return Math.sqrt(distX * distX + distY * distY);
    }

    public static boolean isPointBetweenPoints(doublePair P, doublePair P1, doublePair P2) {
        //P has to lie on a line connecting P1 and P2
        boolean flag = false;
        if ((distanceTwoPoints(P1, P2) > distanceTwoPoints(P, P1)) && (distanceTwoPoints(P1, P2) > distanceTwoPoints(P, P2)))
            flag = true;

        return flag;
    }

    public static doublePair[] interOfLineAndCircle(doublePair line, doublePair center, double radius) {
        //quadratic equation
        double f1 = line.first * line.first + 1; //factor next to x^2
        double f2 = 2 * (line.first * line.second - center.first - line.first * center.second); //factor next to x
        double f3 = center.first * center.first + center.second * center.second + line.second * line.second
                - 2 * line.second * center.second - radius * radius; //free factor
        double deltaSq = Math.sqrt(f2 * f2 - 4 * f1 * f3);
        double x1 = (-f2 - deltaSq) / (2 * f1);
        double x2 = (-f2 + deltaSq) / (2 * f1);
        double y1 = line.first * x1 + line.second;
        double y2 = line.first * x2 + line.second;
		/*System.out.println("f1 = "+f1+", f2 = "+f2+", f3 = "+f3+", delta = "+(f2*f2 - 4*f1*f3));
		System.out.println("x1 = "+x1+", y1 = "+y1);
		System.out.println("x2 = "+x2+", y2 = "+y2);	
		System.out.println("normal line");
		*/
        doublePair P1 = new doublePair(x1, y1);
        doublePair P2 = new doublePair(x2, y2);
        doublePair[] interPoints = new doublePair[2];
        interPoints[0] = P1;
        interPoints[1] = P2;
        return interPoints;
    }

    public static doublePair[] interOfVerticalLineAndCircle(double lineX, doublePair center, double radius) {
        double f1 = 1; //factor next to x^2
        double f2 = -2 * center.second; //factor next to x
        double f3 = (center.second * center.second) + Math.pow(lineX - center.first, 2) - radius * radius;
        double deltaSq = Math.sqrt(f2 * f2 - 4 * f1 * f3);
        double y1 = (-f2 - deltaSq) / (2 * f1);
        double y2 = (-f2 + deltaSq) / (2 * f1);
        double x1 = lineX;
        double x2 = lineX;
		/*System.out.println("f1 = "+f1+", f2 = "+f2+", f3 = "+f3+", delta = "+(f2*f2 - 4*f1*f3));
		System.out.println("x1 = "+x1+", y1 = "+y1);
		System.out.println("x2 = "+x2+", y2 = "+y2);
		System.out.println("vertical line");
		*/
        doublePair P1 = new doublePair(x1, y1);
        doublePair P2 = new doublePair(x2, y2);
        doublePair[] interPoints = new doublePair[2];
        interPoints[0] = P1;
        interPoints[1] = P2;
        return interPoints;
    }

}