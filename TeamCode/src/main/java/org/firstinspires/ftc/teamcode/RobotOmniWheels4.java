package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RobotOmniWheels4 {
    public int noOfWheels = 2;
    public double myRobotSpeed;
    public DcMotor left;
    public DcMotor right;

    RobotOmniWheels(HardwareMap hardwareMap){
        right = hardwareMap.dcMotor.get("right");
        left = hardwareMap.dcMotor.get("left");
        right.setDirection(DcMotorSimple.Direction.REVERSE);
        myRobotSpeed = 0.5;
    }

    public void setRobotSpeed(long newRobotSpeed){
        myRobotSpeed = newRobotSpeed;
    }


    public void moveForward(){
        right.setPower(myRobotSpeed);
        left.setPower(myRobotSpeed);
    }

    public void moveBackward(){
        right.setPower(-myRobotSpeed);
        left.setPower(-myRobotSpeed);
    }

    public void stopMoving(){
        right.setPower(0);
        left.setPower(0);
    }

}
