package TrajectoryLib.util;

public class MathUtil {
    
    /**
     * clamps the value of a number to between a min and max
     * @param a value to be clamped
     * @param min min value
     * @param max max value
     * @return the clamped value
     */
    public static double clamp(double a, double min, double max){
        if(a>max){
            return max;
        }

        if(a<min) {
            return min;
        }

        return a;
    }
}
