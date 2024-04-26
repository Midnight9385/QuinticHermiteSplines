package TrajectoryLib.Geometry;

public class Vector2D{
    private double magnitude;
    private Rotation2D heading;

    public Vector2D(double dx, double dy){
        this.magnitude = Math.hypot(dx, dy);
        this.heading = Rotation2D.fromRadians(Math.atan2(dy, dx));
    }

    public Vector2D(double magnitude, Rotation2D heading){
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

    public Rotation2D getHeading(){
        return heading;
    }
}