package TrajectoryLib.Geometry;

public class Pose2DWithMotion extends Pose2D{
    private Vector2D velocity;
    
    public Pose2DWithMotion(double x, double y, double dx, double dy){
        this(x, y, Rotation2D.ZERO, dx, dy);
    }

    public Pose2DWithMotion(double x, double y, Rotation2D theta, double dx, double dy){
        super(x, y, theta);
        this.velocity = new Vector2D(dx, dy);
    }

    public Pose2DWithMotion(double x, double y, double velocity, Rotation2D heading){
        this(x, y, Rotation2D.ZERO, velocity, heading);
    }

    public Pose2DWithMotion(double x, double y, Rotation2D theta, double velocity, Rotation2D heading){
        super(x, y, theta);
        this.velocity = new Vector2D(velocity, heading);
    }

    public Vector2D getVelocity(){
        return velocity;
    }
}