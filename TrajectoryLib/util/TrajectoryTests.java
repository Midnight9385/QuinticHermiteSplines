package TrajectoryLib.util;

import TrajectoryLib.Geometry.Pose2dWithMotion;
import TrajectoryLib.Geometry.Rotation2d;
import TrajectoryLib.Splines.Spline2d;

public class TrajectoryTests {
    public static void main(String[] args) {
        splineGenerationTest();
    }

    public static double[] splineGenerationTestPyX(){
        //these come from a predefined path that has been calculated outside the code
        //the intent is to test that the math in the code matches expectations
        Pose2dWithMotion start = new Pose2dWithMotion(5.14, 3.77, Rotation2d.ZERO, 0.0, 0.0);
        Pose2dWithMotion end = new Pose2dWithMotion(8.9, 2.64, Rotation2d.ZERO, 1, -1.8);
        Spline2d spline = new Spline2d(start, end);

        double[] x = new double[20];

        for(int i=0; i<x.length; i++){
            x[i] = spline.getPose(i*0.05).getX();
        }

        return x;
    }

    public static double[] splineGenerationTestPyY(){
        //these come from a predefined path that has been calculated outside the code
        //the intent is to test that the math in the code matches expectations
        Pose2dWithMotion start = new Pose2dWithMotion(5.14, 3.77, Rotation2d.ZERO, 0.0, 0.0);
        Pose2dWithMotion end = new Pose2dWithMotion(8.9, 2.64, Rotation2d.ZERO, 1, -1.8);
        Spline2d spline = new Spline2d(start, end);

        double[] y = new double[20];

        for(int i=0; i<y.length; i++){
            y[i] = spline.getPose(i*0.05).getX();
        }

        return y;
    }

    public static void splineGenerationTest(){
        //these come from a predefined path that has been calculated outside the code
        //the intent is to test that the math in the code matches expectations
        Pose2dWithMotion start = new Pose2dWithMotion(5.14, 3.77, Rotation2d.ZERO, 0.0, 0.0);
        Pose2dWithMotion end = new Pose2dWithMotion(8.9, 2.64, Rotation2d.ZERO, 1, -1.8);
        Spline2d spline = new Spline2d(start, end);

        spline.printXCoefficients();
        System.out.println(String.format("c0: %.2f, c1: %.2f, c2: %.2f, c3: %.2f, c4: %.2f, c5: %.2f", 5.14, 0.0, 0.0, 33.6, -49.4, 19.65));
        System.out.println();
        spline.printYCoefficients();
        System.out.println(String.format("c0: %.2f, c1: %.2f, c2: %.2f, c3: %.2f, c4: %.2f, c5: %.2f", 3.77, 0.0, 0.0, -4.1, 4.35, -1.38));
    }

    public static void rotationInterpTest(){
        Rotation2d start = Rotation2d.fromDegrees(Math.random()*180*(Math.random()>0.5?-1:1));
        Rotation2d end = Rotation2d.fromDegrees(Math.random()*180*(Math.random()>0.5?-1:1));

        System.out.println(start);
        System.out.println(end);
        System.out.println();

        for(double t = 0.0; t<=1.0; t+=0.2){
            System.out.println(start.interpolate(end, t));
        }

        end = end.unaryMinus();
        System.out.println();

        for(double t = 0.0; t<=1.0; t+=0.2){
            System.out.println(start.interpolate(end, t));
        }

        System.out.println("\nend\n");
    }
}
