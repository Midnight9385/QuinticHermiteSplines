package TrajectoryLib.Geometry;

public class Pose2D {
    private double x, y;
    private Rotation2D theta;
    
    public Pose2D(double x, double y){
        this(x, y, Rotation2D.ZERO);
    }

    public Pose2D(double x, double y, Rotation2D theta){
        this.x = x;
        this.y = y;
        this.theta = theta;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public Rotation2D getRotation(){
        return theta;
    }
}