package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "SDJavaMoveRobot")
public class SDJavaMoveRobot6 extends LinearOpMode {

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        RobotOmniWheels5 myRobot = new RobotOmniWheels5(hardwareMap);

        // Put initialization blocks here.
        waitForStart();

        //code will continue to run until you hit stop in the app
        while(opModeIsActive()) {

            double leftStickYValue = gamepad1.left_stick_y;
            if (leftStickYValue < -.1) {
                //Set the robotspeed to the stick value
                //As Stick Y value is negative when pressed up, use negative in front
                myRobot.setRobotSpeed(-leftStickYValue);
                myRobot.move();
            } else {
                if (leftStickYValue > .1){
                    //Set the robotspeed to the stick value
                    //As Stick Y value is positive when pressed down, use negative in front to move back
                    myRobot.setRobotSpeed(-leftStickYValue);
                    myRobot.move();
                }
                else {
                    myRobot.stopMoving();
                }
            }
        }
    }
}

