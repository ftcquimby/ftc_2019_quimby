package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class Chaboya2019Base {
    public boolean logArmPosition;
    public boolean logColorData;
    public Telemetry telemetry;
    public HardwareMap hardwareMap;
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    public DcMotor arm;
    public DcMotor extension;
    public DcMotor leftIntake;
    public DcMotor rightIntake;

    public Servo hand_y; //cable 2
    public Servo hand_x; //cable 1
    public Servo fingers; //cable 3
    public Servo leftGripper;
    public Servo rightGripper;

    public DistanceSensor distanceSensor1;
    public DistanceSensor distanceSensor2;

    //If true Runs in steps syncd with HandY position as Hang Down with Pad2 A/B control. If false arm runs independent of handY
    //with same A/B control but without syncing with HandY for hangdown
    public boolean armRunStepMode;
    public int armCurStep;//Goes from -2 to 25. Starts with 0
    public int armPrevStep;

    DcMotor.RunMode myRunMode = DcMotor.RunMode.RUN_USING_ENCODER;
    public OmegaDriveTrain drivetrain;

    //CONSTANTS
    public double HANDX_MINUS20DEGREES = .2;//This is wrong - fix it
    public double HANDX_0DEGREES = 0.1;
    public double HANDX_90DEGREES = .43;
    public double FINGERS_GRAB = .13;
    public double FINGERS_OPEN = .21;
    public double LEFT_FOUNDATION_GRIPPER_GRAB = .2;
    public double LEFT_FOUNDATION_GRIPPER_RELEASE = .6;
    public double RIGHT_FOUNDATION_GRIPPER_GRAB = .0;
    public double RIGHT_FOUNDATION_GRIPPER_RELEASE = .4;

    //The array tells us how to move the arm and hand so that the hand is always in a hanging position
    //This hanging position lets us pickup and carry something in a stable fashion with the hand
    //It also lets us place an object properly at different heights based on this array
    //Starting position is ROW 2
    //This array has [StepNo][HandY Position][Arm Rotation Position][Extension][HandX Position][SleepAfterHandChanges][SleepAfterArmExtChanges]
    double HANDY_STARTPOSITION = .77;
    public double[][] HANDHANGPOSITION = {
            {0,.87, -125,0,HANDX_0DEGREES,0,0},
            {1,0.87,-125,0,HANDX_0DEGREES,0,0},
            {2,0.87,0,0,HANDX_0DEGREES,500,0},
            {3,0.85,125,0,HANDX_0DEGREES,500,0},
            {4,0.83,250,0,HANDX_0DEGREES,0,0},
            {5,0.81,375,0,HANDX_0DEGREES,0,0},
            {6,0.79,500,0,HANDX_0DEGREES,0,0},
            {7,0.74,625,0,HANDX_0DEGREES,0,0},
            {8,0.52,750,0,HANDX_0DEGREES,0,0},
            {9,0.52,875,0,HANDX_0DEGREES,0,500},
            {10,0.52,1000,-1000,HANDX_0DEGREES,0,500},
            {11,0.55,1125,-1000,HANDX_0DEGREES,0,0},
            {12,0.53,1250,-1000,HANDX_0DEGREES,0,0},
            {13,0.49,1375,-1000,HANDX_0DEGREES,0,0},
            {14,0.44,1500,-1000,HANDX_0DEGREES,0,0},
            {15,0.39,1625,-1000,HANDX_0DEGREES,0,0},
            {16,0.39,1750,-1000,HANDX_0DEGREES,0,0},
            {17,0.39,1875,-1000,HANDX_0DEGREES,0,0},
            {18,0.37,1950,-1000,HANDX_0DEGREES,0,0},
            {19,0.35,2025,-1000,HANDX_0DEGREES,0,0},
            {20,0.33,2100,-1000,HANDX_0DEGREES,0,0},
            {21,0.31,2200,-1000,HANDX_0DEGREES,0,0},
            {22,0.29,2300,-1000,HANDX_0DEGREES,0,0},
            {23,0.27,2400,-1000,HANDX_0DEGREES,0,0},
            {24,0.25,2550,-1000,HANDX_0DEGREES,0,500},
            {25,0.23,2675,-1000,HANDX_0DEGREES,0,0},
            {26,0.21,2800,-1000,HANDX_0DEGREES,0,500},
            {27,0.21,2900,-1000,HANDX_0DEGREES,0,0}
    };

    //3.937-inch diameter wheels, 1 wheel rotations per 1 motor rotation; all Yellow Jacket 19.2:1 motors for wheels (537.6 ticks per rev for 1:1); 27 inch turning diameter
    final double ticksPerInch = (537.6 / 1.0) / (3.937 * Math.PI);
    final double ticksPerDegree = ticksPerInch * 27 * Math.PI / 360.0 * (2.0 / 3); //2.0 / 3 is random scale factor
    final double turnTolerance = 2; //2 degrees error tolerance
    final double driveTolerance = 8;
    final double turnTimeLimit = 2.5;
    final double driveTimeLimitPer1Foot = 0.80; //1.5 sec per 12 inches
    Orientation lastAngles = new Orientation();
    BNO055IMU imu;
    public OmegaPID turnPID;
    public OmegaPID drivePID;
    double globalAngle, power = .30, correction;

    double MOVE_CORRECTION_ADDENDUM = 0;
    double AUTO_GOLD_RADIUS = 110;

    Chaboya2019Base(Telemetry telemetry, HardwareMap hardwareMap) {
        this.logArmPosition = false;
        this.armRunStepMode = true;
        this.telemetry = telemetry;
        this.hardwareMap = hardwareMap;

        frontLeft = hardwareMap.get(DcMotor.class, "front_left");
        frontRight = hardwareMap.get(DcMotor.class, "front_right");
        backLeft = hardwareMap.get(DcMotor.class, "back_left");
        backRight = hardwareMap.get(DcMotor.class, "back_right");
        arm = hardwareMap.get(DcMotor.class, "arm");
        extension = hardwareMap.get(DcMotor.class, "extension");
        leftIntake = hardwareMap.get(DcMotor.class, "left_intake");
        rightIntake = hardwareMap.get(DcMotor.class, "right_intake");

        telemetry.clearAll();

        hand_x = hardwareMap.get(Servo.class, "hand_x");
        hand_y = hardwareMap.get(Servo.class, "hand_y");
        hand_y.setDirection(Servo.Direction.REVERSE);
        fingers = hardwareMap.get(Servo.class, "fingers");

        leftGripper = hardwareMap.get(Servo.class, "left_gripper");
        leftGripper.setDirection(Servo.Direction.REVERSE);
        rightGripper = hardwareMap.get(Servo.class, "right_gripper");
        rightGripper.setDirection(Servo.Direction.REVERSE);
        leftGripper.setPosition(LEFT_FOUNDATION_GRIPPER_RELEASE);
        rightGripper.setPosition(RIGHT_FOUNDATION_GRIPPER_RELEASE);


        distanceSensor1 = hardwareMap.get(DistanceSensor.class, "distance_sensor1");
        distanceSensor2 = hardwareMap.get(DistanceSensor.class, "distance_sensor2");

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu1".
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        imu.initialize(parameters);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        armCurStep = 2;
        armPrevStep = 2;
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);//MadanBellam
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);//MadanBellam
        arm.setTargetPosition(1);
        arm.setPower(.2);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);//MadanBellam

        extension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);//MadanBellam
        extension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);//MadanBellam
        hand_x.setPosition(HANDX_0DEGREES);
        hand_y.setPosition(HANDHANGPOSITION[2][1]);
        //hand_y.setPosition(.87);
        fingers.setPosition(FINGERS_OPEN);
        extension.setTargetPosition(1);
        extension.setPower(.2);
        extension.setMode(DcMotor.RunMode.RUN_TO_POSITION);//MadanBellam

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeft.setDirection(DcMotor.Direction.FORWARD); //When motor turns right wheel goes forward, so we keep it as FORWARD
        frontRight.setDirection(DcMotor.Direction.REVERSE);//When motor turns right wheel goes forward, so we keep it as FORWARD
        backLeft.setDirection(DcMotor.Direction.FORWARD); //When motor turns right wheel goes backward, so we change it to REVERSE
        backRight.setDirection(DcMotor.Direction.FORWARD);//When motor turns right wheel goes backward, so we change it to REVERSE

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);

        drivetrain = new OmegaDriveTrain(frontLeft, frontRight, backLeft, backRight);
        drivetrain.setRunMode(myRunMode);
        turnPID = new OmegaPID(0.25, 0.000008, 0.36, turnTolerance); //0.015, 0.00008, 0.05 work for robotSpeed = 0.6. now tuning for 1.0
        drivePID = new OmegaPID(0.45, 0.0001, 0.395, driveTolerance);//.25, .0001, .08 has some jitters
    }//.25,.00008,.5

    public void movePID(double inches, double velocity) {
        double target = ticksPerInch * inches + drivetrain.getAvgEncoderValueOfFrontWheels();
        DcMotor.RunMode originalMode = frontLeft.getMode(); //Assume that all wheels have the same runmode
        drivetrain.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int count = 0;
        ElapsedTime runtime = new ElapsedTime();
        while (runtime.seconds() < driveTimeLimitPer1Foot * (inches / 12.0)) {
            drivetrain.setVelocity(drivePID.calculatePower(drivetrain.getAvgEncoderValueOfFrontWheels(), target, -velocity, velocity));
            telemetry.addData("Count", count);
            telemetry.update();
        }
        drivetrain.setVelocity(0);
        drivetrain.setRunMode(originalMode);
    }

    /**
     * This method makes the robot turn counterclockwise based on gyro values and PID
     * Velocity is always positive. Set neg degrees for clockwise turn
     * pwr in setPower(pwr) is a fraction [-1.0, 1.0] of 12V
     *
     * @param degrees  desired angle in deg
     * @param velocity max velocity
     */
    public void turnUsingPIDVoltage(double degrees, double velocity) {
        DcMotor.RunMode original = frontLeft.getMode(); //assume all drive motors r the same runmode
        drivetrain.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double max = 12.0 * velocity;
        double targetHeading = getAngle() + degrees;
        int count = 0;
        ElapsedTime runtime = new ElapsedTime();
        while (runtime.seconds() < turnTimeLimit) {
            velocity = (turnPID.calculatePower(getAngle(), targetHeading, -max, max) / 12.0); //turnPID.calculatePower() used here will return a voltage
            telemetry.addData("Count", count);
            telemetry.addData("Calculated velocity [-1.0, 1/0]", turnPID.getDiagnosticCalculatedPower() / 12.0);
            telemetry.addData("PID power [-1.0, 1.0]", velocity);
            telemetry.update();
            frontLeft.setPower(-velocity);
            backLeft.setPower(-velocity);
            frontRight.setPower(velocity);
            backRight.setPower(velocity);
            count++;
        }
        drivetrain.setVelocity(0);
        drivetrain.setRunMode(original);
    }


    /**
     * Resets the cumulative angle tracking to zero.
     */
    public void resetAngle() {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        globalAngle = 0;
    }

    /**
     * Get current cumulative angle rotation from last reset.
     *
     * @return Angle in degrees. + = left, - = right.
     */
    public double getAngle() {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }

//    /**
//     * Get the real heading {0, 360}
//     *
//     * @return the heading of the robot {0, 360}
//     */
//    public double getAngleReadable() {
//        double a = getAngle() % 360;
//        if (a < 0) {
//            a = 360 + a;
//        }
//        return a;
//    }
//
//    /**
//     * See if we are moving in a straight line and if not return a power correction value.
//     *
//     * @return Power adjustment, + is adjust left - is adjust right.
//     */
//    public double checkDirection() {
//        // The gain value determines how sensitive the correction is to direction changes.
//        // You will have to experiment with your robot to get small smooth direction changes
//        // to stay on a straight line.
//        double correction, angle, gain = .10;
//
//        angle = getAngle();
//
//        if (angle == 0)
//            correction = 0;             // no adjustment.
//        else
//            correction = -angle;        // reverse sign of angle for correction.
//
//        correction = correction * gain;
//
//        return correction;
//    }

    public double getMOVE_CORRECTION_ADDENDUM() {
        return MOVE_CORRECTION_ADDENDUM;
    }


    public double getTicksPerInch() {
        return ticksPerInch;
    }

    public double getTurnTolerance() {
        return turnTolerance;
    }
}
