package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class RobotMecanumWheels {
    public int noOfWheels = 4;
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    RobotMecanumWheels(HardwareMap hardwareMap){
        frontRight = hardwareMap.dcMotor.get("front_right");
        frontLeft = hardwareMap.dcMotor.get("front_left");
        backRight = hardwareMap.dcMotor.get("back_right");
        backLeft = hardwareMap.dcMotor.get("back_left");
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void moveForward(){
        frontRight.setPower(.5);
        backRight.setPower(.5);
        frontLeft.setPower(.5);
        backLeft.setPower(.5);
    }

    public void stopMoving(){
        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }


}

