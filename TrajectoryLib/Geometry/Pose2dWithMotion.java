package TrajectoryLib.Geometry;

public class Pose2dWithMotion extends Pose2d{
    private Vector2d velocity;
    
    public Pose2dWithMotion(double x, double y, double dx, double dy){
        this(x, y, Rotation2d.ZERO, dx, dy);
    }

    public Pose2dWithMotion(double x, double y, Rotation2d theta, double dx, double dy){
        super(x, y, theta);
        this.velocity = new Vector2d(dx, dy);
    }

    public Pose2dWithMotion(double x, double y, double velocity, Rotation2d heading){
        this(x, y, Rotation2d.ZERO, velocity, heading);
    }

    public Pose2dWithMotion(double x, double y, Rotation2d theta, double velocity, Rotation2d heading){
        super(x, y, theta);
        this.velocity = new Vector2d(velocity, heading);
    }

    public Pose2dWithMotion(double x, double y, Vector2d velocity){
        this(x, y, Rotation2d.ZERO, velocity);
    }

    public Pose2dWithMotion(double x, double y, Rotation2d theta, Vector2d velocity){
        super(x, y, theta);
        this.velocity = velocity;
    }

    public Pose2dWithMotion(Pose2d pose, Vector2d velocity){
        super(pose);
        this.velocity = velocity;
    }

    public Vector2d getVelocity(){
        return velocity;
    }
}