package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MegaBot {
    public DcMotor arm;
    public Servo hand_y;
    public Servo hand_x;
    public Servo fingers;

    public DcMotor extension;
    public DcMotor leftIntake;
    public DcMotor rightIntake;

    MegaBot(Telemetry telemetry, HardwareMap hardwareMap){
        extension = hardwareMap.get(DcMotor.class, "extension");
        hand_x = hardwareMap.get(Servo.class, "hand_x");
        hand_y = hardwareMap.get(Servo.class, "hand_y");
        fingers = hardwareMap.get(Servo.class, "fingers");
        leftIntake =  hardwareMap.get(DcMotor.class, "left_intake");
        rightIntake =  hardwareMap.get(DcMotor.class, "right_intake");
 /*       arm = hardwareMap.get(DcMotor.class, "arm");
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setTargetPosition(1);
        arm.setPower(0.2);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
*/

    }
}
