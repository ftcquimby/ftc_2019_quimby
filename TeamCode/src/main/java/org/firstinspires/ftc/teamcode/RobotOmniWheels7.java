package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RobotOmniWheels7 {
    public int noOfWheels = 2;
    public double myRobotLeftSpeed;
    public double myRobotRightSpeed;
    public DcMotor left;
    public DcMotor right;

    RobotOmniWheels(HardwareMap hardwareMap){
        right = hardwareMap.dcMotor.get("right");
        left = hardwareMap.dcMotor.get("left");
        right.setDirection(DcMotorSimple.Direction.REVERSE);
        myRobotLeftSpeed = 0;
        myRobotRightSpeed = 0;
    }

    public void setRobotLeftSpeed(double newRobotSpeed){
        myRobotLeftSpeed = newRobotSpeed;
    }

    public void setRobotRightSpeed(double newRobotSpeed){
        myRobotRightSpeed = newRobotSpeed;
    }


    public void moveLeftWheel(){
        left.setPower(myRobotLeftSpeed);
    }

    public void moveRightWheel(){
        right.setPower(myRobotRightSpeed);
    }

    public void stopLeftWheelMoving(){
        left.setPower(0);
    }

    public void stopRightWheelMoving(){
        right.setPower(0);
    }
}