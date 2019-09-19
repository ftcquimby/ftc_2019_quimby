package org.firstinspires.ftc.teamcode;

public class MotionProfileGenerator {
    private double maxVel;
    private double maxAcc;
    private double distThreshold;
    private int accTime;

    /**
     * constructor
     * @param maxVel needs to be in units/millisecond
     * @param maxAcc needs to be in units/millisecond
     */
    public MotionProfileGenerator(double maxVel, double maxAcc){
        this.maxAcc = maxAcc;
        this.maxVel = maxVel;
        distThreshold = maxVel * maxVel / maxAcc;
        accTime = (int) (2*distThreshold/maxVel);
    }

    public double[] generateProfile(double distance){
        double maxVelTime = ((distance - distThreshold)/maxVel);
        int runTime = (int) (maxVelTime + 2 * accTime);
        double[] motionProfile = new double[runTime];
        if(distance > distThreshold){
            for(int ms = 0; ms < runTime; ms++){
                if(ms < accTime){
                    motionProfile[ms] = maxAcc * ms;
                }else if(ms < runTime - accTime) {
                    motionProfile[ms] = maxVel;
                }else if(ms < runTime){
                    motionProfile[ms] = maxVel - (ms - (runTime - accTime - maxVelTime)*maxAcc);
                }
            }
        }
        return motionProfile;
    }

    public double[] generateDistanceProfile(double[] motionProfile){
        double[] distanceProfile = new double[motionProfile.length];
        int prev = 0;
        for(int i = 0; i < motionProfile.length; i++){
            prev += motionProfile[i];
            distanceProfile[i] = prev;
        }
        return distanceProfile;
    }
}
