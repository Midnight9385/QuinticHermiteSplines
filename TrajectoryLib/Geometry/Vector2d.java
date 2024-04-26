package TrajectoryLib.Geometry;

public class Vector2d{
    private double magnitude;
    private Rotation2d heading;

    public Vector2d(double dx, double dy){
        this.magnitude = Math.hypot(dx, dy);
        this.heading = new Rotation2d(dx, dy);
    }

    public Vector2d(double magnitude, Rotation2d heading){
        this.magnitude = magnitude;
        this.heading = heading;
    }

    public double getX(){
        return magnitude*Math.cos(heading.getRadians());
    }

    public double getY(){
        return magnitude*Math.sin(heading.getRadians());
    }

    public double getMagnitude(){
        return magnitude;
    }

    public Rotation2d getHeading(){
        return heading;
    }
}