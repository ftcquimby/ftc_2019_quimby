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

    public void movePID(double inches, double velocity) {
        double target = robot.ticksPerInch * inches + robot.drivetrain.getAvgEncoderValueOfFrontWheels();
        DcMotor.RunMode originalMode = robot.frontLeft.getMode(); //Assume that all wheels have the same runmode
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

    public void strafe(double direction, double distance, double velocity){
    
    }
}
