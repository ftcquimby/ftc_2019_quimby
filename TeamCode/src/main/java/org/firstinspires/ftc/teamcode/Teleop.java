package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class Teleop extends OpMode {
    OmegaBot robot;

    public void init(){
        robot = new OmegaBot(telemetry, hardwareMap);
    }

    public void loop(){
        double forward = gamepad1.left_stick_y;
        double right = gamepad1.left_stick_x
        double clockwise = gamepad1.right_stick_x;
        double temp = forward * Math.cos(robot.getAngle()) + right * Math.sin(robot.getAngle());
        right = -1 * forward * Math.sin(robot.getAngle()) + right * Math.sin(robot.getAngle());

        double front_left = forward + clockwise + right;
        double front_right = forward - clockwise -right;
        double rear_left = forward + clockwise - right;
        double rear_right = forward - clockwise + right;

        double max = Math.abs(front_left);
        if(Math.abs(front_right) > max) max = Math.abs(front_right);
        if(Math.abs(rear_left) > max) max = Math.abs(rear_left);
        if(Math.abs(rear_right) > max) max = Math.abs(rear_right);

        if(max>1){
            front_left /= max;
            front_right /= max;
            rear_left /= max;
            rear_right /= max;
        }

        robot.frontLeft.setPower(front_left);
        robot.frontRight.setPower(front_right);
        robot.backLeft.setPower(rear_left);
        robot.backRight.setPower(rear_right);
    }
}
