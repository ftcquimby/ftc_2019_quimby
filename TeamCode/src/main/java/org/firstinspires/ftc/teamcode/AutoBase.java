package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="autobase")
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

        robot.leftIntake.setPower(-1);
        robot.rightIntake.setPower(1);
        sleep(1000);
        robot.drivetrain.reverseDirection();
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
        robot.leftIntake.setPower(1);
        robot.rightIntake.setPower(-1);
        sleep(1000);
        motionMethods.strafe(0,47,.5);
        sleep(1000);
        motionMethods.strafe(180,97,.5);



        //GYRO SETUP
        runtime.reset();
    }
}
