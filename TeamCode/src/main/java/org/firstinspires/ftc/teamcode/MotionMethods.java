package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MotionMethods {
    OmegaBot robot;
    Telemetry telemetry;
    LinearOpMode opMode;

    public MotionMethods(OmegaBot robot, Telemetry telemetry, LinearOpMode opMode){
        this.robot = robot;
        this.telemetry = telemetry;
        this.opMode = opMode;
    }

    public void moveMotionProfile(double inches, double power){//power is between 0 and 1
        if(inches == 0){
            return;
        }
        double maxVel = 312 * 3.937 / 60000; // 312 is the rotations per minute, 3.937 is the inches per rotation (based on wheel circumference), 60000 is the number of milliseconds in a minute
        double macAcc = maxVel / 1300; //1300 is the number of milliseconds it takes to accelerate to full speed
        MotionProfileGenerator generator = new MotionProfileGenerator(maxVel * power, macAcc);//multiply by power cuz its a number between 0 and 1 so it scales
        double[] motionProfile = generator.generateProfile(inches);
        double[] distanceProfile = generator.generateDistanceProfile(motionProfile);
        ElapsedTime runtime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        runtime.reset();
        double heading = robot.getAngle();
        while(runtime.milliseconds() < motionProfile.length && opMode.opModeIsActive()){
            int ms = (int) runtime.milliseconds();
            if (ms < motionProfile.length) {
                double adjust = 0;//0.04 * (robot.getAngle()-heading);
                robot.frontLeft.setPower(motionProfile[ms] / maxVel + adjust);
                robot.backLeft.setPower(motionProfile[ms] / maxVel + adjust);
                robot.frontRight.setPower(motionProfile[ms] / maxVel - adjust);
                robot.backRight.setPower(motionProfile[ms] / maxVel - adjust);
                //robot.drivetrain.setVelocity(motionProfile[(int) runtime.milliseconds()] / maxVel);//TODO: use the distance profile + encoders to pid up in dis bicth
            }
        }
        robot.drivetrain.setVelocity(0);
    }

    public void movePID(double inches, double velocity) {
        DcMotor.RunMode originalMode = robot.frontLeft.getMode(); //Assume that all wheels have the same runmode
        robot.drivetrain.setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        double target = robot.ticksPerInch * inches;
        robot.drivetrain.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int count = 0;
        ElapsedTime runtime = new ElapsedTime();
        while (opMode.opModeIsActive() && runtime.seconds() < robot.driveTimeLimitPer1Foot * inches / 12.0) {
            robot.drivetrain.setVelocity(robot.drivePID.calculatePower(robot.drivetrain.getAvgEncoderValueOfFrontWheels(), target, -velocity, velocity));
            telemetry.addData("Count", count);
            telemetry.update();
        }
        robot.drivetrain.setVelocity(0);
        robot.drivetrain.setRunMode(originalMode);
    }

    public void turnUsingPIDVoltage(double degrees, double velocity) {
        DcMotor.RunMode original = robot.frontLeft.getMode(); //assume all drive motors r the same runmode
        robot.drivetrain.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double max = 12.0 * velocity;
        double targetHeading = robot.getAngle() + degrees;
        int count = 0;
        ElapsedTime runtime = new ElapsedTime();
        while (opMode.opModeIsActive() && runtime.seconds() < robot.turnTimeLimit) {
            velocity = (robot.turnPID.calculatePower(robot.getAngle(), targetHeading, -max, max) / 12.0); //turnPID.calculatePower() used here will return a voltage
            telemetry.addData("Count", count);
            telemetry.addData("Calculated velocity [-1.0, 1/0]", robot.turnPID.getDiagnosticCalculatedPower() / 12.0);
            telemetry.addData("PID power [-1.0, 1.0]", velocity);
            telemetry.update();
            robot.frontLeft.setPower(-velocity);
            robot.backLeft.setPower(-velocity);
            robot.frontRight.setPower(velocity);
            robot.backRight.setPower(velocity);
            count++;
        }
        robot.drivetrain.setVelocity(0);
        robot.drivetrain.setRunMode(original);
    }

    public void turnUsingPIDVoltageFieldCentric(double degrees, double velocity) {
        DcMotor.RunMode original = robot.frontLeft.getMode(); //assume all drive motors r the same runmode
        robot.drivetrain.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        double max = 12.0 * velocity;
        double targetHeading = degrees;
        int count = 0;
        ElapsedTime runtime = new ElapsedTime();
        while (opMode.opModeIsActive() && runtime.seconds() < robot.turnTimeLimit) {
            velocity = (robot.turnPID.calculatePower(robot.getAngle(), targetHeading, -max, max) / 12.0); //turnPID.calculatePower() used here will return a voltage
            telemetry.addData("Count", count);
            telemetry.addData("Calculated velocity [-1.0, 1/0]", robot.turnPID.getDiagnosticCalculatedPower() / 12.0);
            telemetry.addData("PID power [-1.0, 1.0]", velocity);
            telemetry.update();
            robot.frontLeft.setPower(-velocity);
            robot.backLeft.setPower(-velocity);
            robot.frontRight.setPower(velocity);
            robot.backRight.setPower(velocity);
            count++;
        }
        robot.drivetrain.setVelocity(0);
        robot.drivetrain.setRunMode(original);
    }

    public void strafe(double heading, double distance, double velocity){
        double moveGain = .02;
        double turnGain = .01;
        double right = Math.cos(Math.toRadians(heading));
        double forward = Math.sin(Math.toRadians(heading));
        telemetry.addData("heading", heading);
        telemetry.update();
        double robotHeading = robot.getAngle();
        int[] encoderCounts = {robot.frontLeft.getCurrentPosition(),robot.frontRight.getCurrentPosition(),robot.backLeft.getCurrentPosition(),robot.backRight.getCurrentPosition()};
        ElapsedTime runtime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        double currTime = runtime.milliseconds();
        while (opMode.opModeIsActive() && distance > 0){
            double timeChange = runtime.milliseconds() - currTime;
            currTime = runtime.milliseconds();
            distance--;//find a better way to do this w encoder counts
            double clockwise =  robot.getAngle() - robotHeading;
            clockwise *= turnGain;
            /**
            double temp = forward * Math.cos(Math.toRadians(robotHeading)) - right * Math.sin(Math.toRadians(robotHeading));
            right = forward * Math.sin(Math.toRadians(robotHeading)) + right * Math.cos(Math.toRadians(robotHeading));
            forward = temp * moveGain * distance;
            right = right * moveGain * distance;
             **/

            right  = right * moveGain * distance;
            forward = forward * moveGain * distance;

            double front_left = forward + clockwise + right;
            double front_right = forward - clockwise -right;
            double rear_left = forward + clockwise - right;
            double rear_right = forward - clockwise + right;

            double max = Math.abs(front_left);
            if(Math.abs(front_right) > max) max = Math.abs(front_right);
            if(Math.abs(rear_left) > max) max = Math.abs(rear_left);
            if(Math.abs(rear_right) > max) max = Math.abs(rear_right);

            if(max>velocity){
                front_left /= max;
                front_left *= velocity;
                front_right /= max;
                front_right *= velocity;
                rear_left /= max;
                rear_left *= velocity;
                rear_right /= max;
                rear_right *= velocity;
            }

            robot.frontLeft.setPower(front_left);
            robot.frontRight.setPower(front_right);
            robot.backLeft.setPower(rear_left);
            robot.backRight.setPower(rear_right);
        }
    }
}
