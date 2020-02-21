package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "SDJavaMoveRobot")
public class SDJavaMoveRobot7 extends LinearOpMode {

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        RobotOmniWheels7 myRobot = new RobotOmniWheels7(hardwareMap);

        // Put initialization blocks here.
        waitForStart();

        //code will continue to run until you hit stop in the app
        while(opModeIsActive()) {

            double leftStickYValue = gamepad1.left_stick_y;
            double rightStickYValue = gamepad1.right_stick_y;

            if (leftStickYValue < -.1) {
                //Set the robotspeed to the stick value
                //As Stick Y value is negative when pressed up, use negative in front
                myRobot.setRobotLeftSpeed(-leftStickYValue);
                myRobot.moveLeftWheel();
            } else {
                if (leftStickYValue > .1){
                    //Set the robotspeed to the stick value
                    //As Stick Y value is positive when pressed down, use negative in front to move back
                    myRobot.setRobotLeftSpeed(-leftStickYValue);
                    myRobot.moveLeftWheel();
                }
                else {
                    myRobot.stopLeftWheelMoving();
                }
            }

            if (rightStickYValue < -.1) {
                //Set the robotspeed to the stick value
                //As Stick Y value is negative when pressed up, use negative in front
                myRobot.setRobotRightSpeed(-rightStickYValue);
                myRobot.moveRightWheel();
            } else {
                if (leftStickYValue > .1){
                    //Set the robotspeed to the stick value
                    //As Stick Y value is positive when pressed down, use negative in front to move back
                    myRobot.setRobotRightSpeed(-rightStickYValue);
                    myRobot.moveRightWheel();
                }
                else {
                    myRobot.stopRightWheelMoving();
                }
            }
        }
    }
}
