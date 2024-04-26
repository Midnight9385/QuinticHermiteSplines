package TrajectoryLib.Geometry;

public class Rotation2D implements interpolable<Rotation2D>{
    public static Rotation2D ZERO = new Rotation2D(0);

    private double theta;

    private Rotation2D(double radians){
        this.theta = radians;
    }

    public static Rotation2D fromRadians(double theta){
        return new Rotation2D(theta);
    }

    public static Rotation2D fromDegrees(double theta){
        return new Rotation2D(Math.toRadians(theta));
    }

    public double getDegrees(){
        return Math.toDegrees(theta);
    }

    public double getRadians(){
        return theta;
    }

    public void rotateBy(Rotation2D other){
        theta+=other.theta;
    }

    @Override
    public Rotation2D interpolate(Rotation2D other, double t){
        boolean cc = 
        return Rotation2D.fromRadians()
    }
}
