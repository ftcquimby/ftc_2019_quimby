package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="pullwaffle2")
public class AutoBase extends LinearOpMode{
    private ElapsedTime runtime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    public double robotSpeed = 0.45;
    OmegaBot robot;
    OmegaPID drivePID;
    MotionMethods motionMethods;

    public void runOpMode() {
        robot = new OmegaBot(telemetry, hardwareMap);
        drivePID = robot.drivePID;
        motionMethods = new MotionMethods(robot, telemetry, this);
        /*
         */
        robot.drivetrain.setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.drivetrain.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        robot.rightGripper.setPosition(.33);
        robot.leftGripper.setPosition(.6);

        motionMethods.turnUsingPIDVoltageFieldCentric(90,.5);

            robot.drivetrain.reverseDirection();
        motionMethods.moveMotionProfile(9,1);
            robot.drivetrain.reverseDirection();

        motionMethods.turnUsingPIDVoltageFieldCentric(90,.5);

            robot.drivetrain.reverseDirection();
        motionMethods.moveMotionProfile(10,1);
            robot.drivetrain.reverseDirection();

        motionMethods.turnUsingPIDVoltageFieldCentric(180,.5);

            robot.drivetrain.reverseDirection();
        motionMethods.movePID(24,.25);
            robot.drivetrain.reverseDirection();

        robot.rightGripper.setPosition(1);
        robot.leftGripper.setPosition(0);
        sleep(1000);
        motionMethods.moveMotionProfile(8,1);


        /*robot.frontRight.setPower(1);
        sleep(3000);
        robot.frontRight.setPower(0);
        robot.frontLeft.setPower(1);
        sleep(3000);
        robot.frontLeft.setPower(0);
        robot.backLeft.setPower(1);
        sleep(3000);
        robot.backLeft.setPower(0);
        robot.backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.backRight.setPower(1);
        sleep(3000);
        robot.backRight.setPower(0);
        robot.frontRight.setPower(-1);
        sleep(3000);
        robot.frontRight.setPower(0);
        robot.frontLeft.setPower(-1);
        sleep(3000);
        robot.frontLeft.setPower(0);
        robot.backLeft.setPower(-1);
        sleep(3000);
        robot.backLeft.setPower(0);
        robot.backRight.setPower(-1);
        sleep(3000);
        robot.backRight.setPower(0);*/


/*
        //robot.leftIntake.setPower(-1);
        //robot.rightIntake.setPower(1);
        //robot.drivetrain.reverseDirection();
        motionMethods.movePID(32,.5);
        sleep(1000);
        robot.drivetrain.reverseDirection();
        motionMethods.turnUsingPIDVoltageFieldCentric(0,.5);
        motionMethods.movePID(18,.5);
        sleep(1000);
        motionMethods.turnUsingPIDVoltageFieldCentric(-90, .5);
        sleep(500);
        robot.drivetrain.reverseDirection();
        motionMethods.movePID(48,.5);
        robot.drivetrain.reverseDirection();
        motionMethods.turnUsingPIDVoltageFieldCentric(-90,.5);
        //robot.leftIntake.setPower(1);
        //robot.rightIntake.setPower(-1);
        sleep(1000);
        motionMethods.strafe(0,47,.5);
        sleep(1000);
        motionMethods.strafe(180,97,.5);
*/



        //GYRO SETUP
        runtime.reset();
    }
}
