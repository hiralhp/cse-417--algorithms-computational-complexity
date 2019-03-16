import java.util.Comparator;
public class Point implements Comparator{

    public final double x;
    public final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int compare(Object obj1, Object obj2) {
        Point p1 = ((Point) obj1);
        Point p2 = ((Point) obj2);

        if (p1.getX() > p2.getX()) {
            return 1;
        } else if (p1.getX() < p2.getX()){
            return -1;
        } else {
            return 0;
        }
    }
}
