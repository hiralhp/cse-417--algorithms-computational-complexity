import java.util.*;
import java.io.*;

public class closestPairs {
    public static Map<Double, List<Point>> map = new HashMap<Double, List<Point>>();
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("enter file path: ");
        Scanner sc1 = new Scanner(System.in);
        String path = sc1.nextLine();
        Scanner sc = new Scanner(new File(path));
        List<Point> list = new ArrayList<Point>();
        while (sc.hasNextDouble()) {
            Double x = sc.nextDouble();
            if (sc.hasNextDouble()) {
                Double y = sc.nextDouble();
                Point curr = new Point(x, y);
                list.add(curr);
            }
        }
        if(list.size()<2){
            System.out.println("ERROR you must have at least 2 points. You entered "+list.size()+ " points");
            return;
        }
        long start = System.currentTimeMillis();

        Double p1 = first(list);
        long end = System.currentTimeMillis();
        long time = end - start;

        System.out.println("Version 1, number of points = " +list.size() +
                " Points (" + map.get(p1).get(0).getX() +", " +map.get(p1).get(0).getY()+") " + "and ("
                + map.get(p1).get(1).getX() +", " +map.get(p1).get(1).getY()+") Distance: " + p1
                + " Time: "+time+" milliseconds");
        map.clear();

         start = System.currentTimeMillis();
        p1=second(list);
         end = System.currentTimeMillis();
         time = end - start;
        System.out.println("Version 2, number of points = " +list.size() +
                " Points (" + map.get(p1).get(0).getX() +", " +map.get(p1).get(0).getY()+") " + "and ("
                + map.get(p1).get(1).getX() +", " +map.get(p1).get(1).getY()+") Distance: " + p1
                + " Time: "+time +" milliseconds");
        map.clear();
        start = System.currentTimeMillis();
        Collections.sort(list, new Comparator<Point>() {
            public int compare(final Point obj1, final Point obj2) {
                Point p1 = ((Point) obj1);
                Point p2 = ((Point) obj2);

                if (p1.getX() > p2.getX()) {
                    return 1;
                } else if (p1.getX() < p2.getX()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        List<Point> xSorted = list;
        Collections.sort(list, new Comparator<Point>() {
            public int compare(final Point obj1, final Point obj2) {
                Point p1 = ((Point) obj1);
                Point p2 = ((Point) obj2);

                if (p1.getY() > p2.getY()) {
                    return 1;
                } else if (p1.getY() < p2.getY()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        List<Point> ySorted = list;

        p1=third(xSorted, ySorted);
        end = System.currentTimeMillis();
         time = end - start;
        System.out.println("Version 3, number of points = " +list.size() +
                " Points (" + map.get(p1).get(0).getX() +", " +map.get(p1).get(0).getY()+") " + "and ("
                + map.get(p1).get(1).getX() +", " +map.get(p1).get(1).getY()+") Distance: " + p1
                + " Time: "+time+" milliseconds");

        map.clear();
    }

    public static double first(List<Point> list) {

        double min = Integer.MAX_VALUE;
        Point minPt1 = new Point(0, 0);
        Point minPt2 = new Point(0, 0);
        for (int i = 0; i < list.size(); i++) {
            Point pt1 = list.get(i);
            for (int j = i + 1; j < list.size(); j++) {
                Point pt2 = list.get(j);
                double xDif = Math.abs(pt2.getX() - pt1.getX());
                double yDif = Math.abs(pt2.getY() - pt1.getY());
                double xSquared = Math.pow(xDif, 2);
                double ySquared = Math.pow(yDif, 2);
                double added = ySquared + xSquared;
                double z = Math.sqrt(added);
                if (z < min) {
                    min = z;
                    minPt1 = pt1;
                    minPt2 = pt2;
                    List<Point> curr = new ArrayList<Point>();
                    curr.add(minPt1);
                    curr.add(minPt2);
                    map.put(z, curr);
                }
            }
        }
        return min;
    }

    public static double second(List<Point> list) {
        if (list.size() < 4) {
            return first(list);
        } else {
            Collections.sort(list, new Comparator<Point>() {
                public int compare(final Point obj1, final Point obj2) {
                    Point p1 = ((Point) obj1);
                    Point p2 = ((Point) obj2);

                    if (p1.getX() > p2.getX()) {
                        return 1;
                    } else if (p1.getX() < p2.getX()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        }
        double split = list.get(list.size() / 2).getX() - list.get(list.size() / 2 - 1).getX();
        List<Point> list1 = list.subList(0, list.size() / 2);
        List<Point> list2 = list.subList(list.size() / 2, list.size());
        double l1 = second(list1);
        double l2 = second(list2);
        double min = Math.min(l1, l2);
        List<Point> l3 = new ArrayList<Point>();
        for (int i = 0; i < list.size(); i++) {
            if (Math.abs(split - list.get(i).getX()) < min) {
                l3.add(list.get(i));
            }
        }
        Collections.sort(l3, new Comparator<Point>() {
            @Override
            public int compare(final Point obj1, final Point obj2) {
                Point p1 = ((Point) obj1);
                Point p2 = ((Point) obj2);

                if (p1.getY() > p2.getY()) {
                    return 1;
                } else if (p1.getY() < p2.getY()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        double l3Min = first(l3);
        min = Math.min(min, l3Min);
        return min;
    }

    public static double third(List<Point> xSorted, List<Point> ySorted) {
        if (xSorted.size() < 4) {
            return first(xSorted);
        } else {

            double split = xSorted.get(xSorted.size() / 2).getX() - xSorted.get(xSorted.size() / 2 - 1).getX();
            List<Point> list1x = xSorted.subList(0, xSorted.size() / 2);
            List<Point> list2x = xSorted.subList(xSorted.size() / 2, xSorted.size());
            List<Point> list1y = ySorted.subList(0, ySorted.size() / 2);
            List<Point> list2y = ySorted.subList(ySorted.size() / 2, ySorted.size());

            double l1 = third(list1x, list1y);
            double l2 = third(list2x, list2y);
            double min = Math.min(l1, l2);
            List<Point> l3 = new ArrayList<Point>();
            for (int i = 0; i < ySorted.size(); i++) {
                if (Math.abs(split - ySorted.get(i).getX()) < min) {
                    l3.add(ySorted.get(i));
                }
            }
            double l3Min = first(l3);
            min = Math.min(min, l3Min);
            return min;
        }
    }
}

