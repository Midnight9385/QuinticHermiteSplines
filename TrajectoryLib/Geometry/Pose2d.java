package TrajectoryLib.Geometry;

public class Pose2d {
    private double x, y;
    private Rotation2d theta;
    
    public Pose2d(double x, double y){
        this(x, y, Rotation2d.ZERO);
    }

    public Pose2d(double x, double y, Rotation2d theta){
        this.x = x;
        this.y = y;
        this.theta = theta;
    }

    public Pose2d(Pose2d pose){
        this(pose.getX(), pose.getY(), pose.getRotation());
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public Rotation2d getRotation(){
        return theta;
    }
}