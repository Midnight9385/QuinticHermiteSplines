package TrajectoryLib.Path;

import java.util.ArrayList;
import java.util.List;

import TrajectoryLib.Geometry.GeometryUtil;
import TrajectoryLib.Geometry.Pose2d;
import TrajectoryLib.Geometry.Rotation2d;
import TrajectoryLib.Geometry.Vector2d;

public class Trajectory {
    private final List<State> states;

    public Trajectory(
            Path path, Vector2d startingSpeeds, Rotation2d startingRotation) {
        this.states = generateStates(path, startingSpeeds, startingRotation);
    }

    public static List<State> generateStates(Path path, Vector2d startingSpeeds, Rotation2d startingRotation) {
        List<State> states = new ArrayList<>();
        PathConstraints constraints = path.getConstraints();

        // initial pass: creates states and handles accel
        for (int i = 0; i < path.numPoints(); i++) {
            State state = new State();

            state.targetPose = path.getPoint(i).position.getPose();
            state.targetVelocity = path.getPoint(i).position.getVelocity();
            state.constraints = path.getConstraints();

            if (i == path.numPoints() - 1) {
                state.targetVelocity = path.getGoalEndState().getVelocity();
                state.deltaPos = path.getPoint(i).distanceAlongPath - path.getPoint(i - 1).distanceAlongPath;
            } else if (i == 0) {
                state.targetVelocity = startingSpeeds;
                state.deltaPos = 0;
            } else {
                state.deltaPos = path.getPoint(i + 1).distanceAlongPath - path.getPoint(i).distanceAlongPath;

                double v0 = states.get(states.size() - 1).targetVelocity.getMagnitude();
                double vMax = Math.sqrt(
                        Math.abs(
                                Math.pow(v0, 2)
                                        + (2 * constraints.getMaxAccelerationMpsSq() * state.deltaPos)));
                state.targetVelocity.adjustMagnitude(Math.min(vMax, path.getPoint(i).maxV));
            }

            states.add(state);
        }

        // second pass: handles deccel
        for (int i = states.size() - 2; i > 1; i--) {
            constraints = states.get(i).constraints;

            double v0 = states.get(i + 1).targetVelocity.getMagnitude();

            double vMax = Math.sqrt(
                    Math.abs(
                            Math.pow(v0, 2)
                                    + (2 * constraints.getMaxAccelerationMpsSq() * states.get(i + 1).deltaPos)));
            states.get(i).targetVelocity.adjustMagnitude(Math.min(vMax, states.get(i).targetVelocity.getMagnitude()));
        }

        double time = 0;
        states.get(0).time = 0;

        //final pass: calculates time
        for (int i = 1; i < states.size(); i++) {
            double v0 = states.get(i - 1).targetVelocity.getMagnitude();
            double v = states.get(i).targetVelocity.getMagnitude();
            double dt = (2 * states.get(i).deltaPos) / (v + v0);

            time += dt;
            states.get(i).time = time;
        }

        return states;
    }

    /**
     * Get the total run time of the trajectory
     *
     * @return Total run time in seconds
     */
    public double getTotaltime() {
        return getEndState().time;
    }

    /**
     * Get the goal state at the given index
     *
     * @param index Index of the state to get
     * @return The state at the given index
     */
    public State getState(int index) {
        return getStates().get(index);
    }

    /**
     * Get the initial state of the trajectory
     *
     * @return The initial state
     */
    public State getInitialState() {
        return getState(0);
    }

    /**
     * Get the end state of the trajectory
     *
     * @return The end state
     */
    public State getEndState() {
        return getState(getStates().size() - 1);
    }

    /**
     * Get the initial target pose for a holonomic drivetrain NOTE: This is a
     * "target" pose, meaning
     * the rotation will be the value of the next rotation target along the path,
     * not what the
     * rotation should be at the start of the path
     *
     * @return The initial target pose
     */
    public Pose2d getInitialTargetPose() {
        return getInitialState().getTargetPose();
    }

    /**
     * Get the target state at the given point in time along the trajectory
     *
     * @param time The time to sample the trajectory at in seconds
     * @return The target state
     */
    public State sample(double time) {
        if (time <= getInitialState().time)
            return getInitialState();
        if (time >= getTotaltime())
            return getEndState();

        int low = 1;
        int high = getStates().size() - 1;

        while (low != high) {
            int mid = (low + high) / 2;
            if (getState(mid).time < time) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        State sample = getState(low);
        State prevSample = getState(low - 1);

        if (Math.abs(sample.time - prevSample.time) < 1E-3)
            return sample;

        return prevSample.interpolate(
                sample, (time - prevSample.time) / (sample.time - prevSample.time));
    }

    /**
     * Get all of the pre-generated states in the trajectory
     *
     * @return List of all states
     */
    public List<State> getStates() {
        return states;
    }

    public static class State {
        private double time;
        private Pose2d targetPose;
        private Vector2d targetVelocity;
        private PathConstraints constraints;

        private double deltaPos;

        /**
         * Interpolate between this state and the given state
         *
         * @param endVal State to interpolate with
         * @param t      Interpolation factor (0.0-1.0)
         * @return Interpolated state
         */
        public State interpolate(State endVal, double t) {
            State lerpedState = new State();

            lerpedState.time = GeometryUtil.doubleLerp(time, endVal.time, t);
            double deltaT = lerpedState.time - time;

            if (deltaT < 0) {
                return endVal.interpolate(this, 1 - t);
            }

            lerpedState.targetVelocity = targetVelocity.interpolate(endVal.targetVelocity, t);
            lerpedState.targetPose = targetPose.interpolate(endVal.targetPose, t);

            if (t < 0.5) {
                lerpedState.constraints = constraints;
            } else {
                lerpedState.constraints = endVal.constraints;
            }

            return lerpedState;
        }

        /**
         * Get the target pose for a holonomic drivetrain NOTE: This is a "target" pose,
         * meaning the
         * rotation will be the value of the next rotation target along the path, not
         * what the rotation
         * should be at the start of the path
         *
         * @return The target pose
         */
        public Pose2d getTargetPose() {
            return targetPose;
        }
    }
}
