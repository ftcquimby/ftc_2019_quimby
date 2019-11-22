package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "StraightTest")
@Disabled
public class StraightTest extends LinearOpMode {
    OmegaBot robot;
    MotionMethods motionMethods;

    public void runOpMode(){
        robot = new OmegaBot(telemetry, hardwareMap);
        motionMethods = new MotionMethods(robot, telemetry, this);

        robot.drivetrain.setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.drivetrain.setRunMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        motionMethods.moveMotionProfile(5, 1);
    }
}
