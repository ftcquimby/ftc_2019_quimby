package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "SDJavaMoveRobot")
public class SDJavaMoveRobot4 extends LinearOpMode {

    /**
     * This function is executed when this Op Mode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        RobotOmniWheels4 myRobot = new RobotOmniWheels4(hardwareMap);

        // Put initialization blocks here.
        waitForStart();

        //code will continue to run untill you hit stop in the app
        while(opModeIsActive()) {

            //will go slowly for one second
            myRobot.setRobotSpeed(0.2);
            myRobot.moveForward();
            sleep(1000);

            //will move back for one second
            myRobot.moveBackward();
            sleep(1000);

            //will go faster for one second
            myRobot.setRobotSpeed(0.5);
            myRobot.moveForward();
            sleep(1000);

            //will move back to original position
            myRobot.moveBackward();
            sleep(1000);

            myRobot.stopMoving();
        }
    }
}
