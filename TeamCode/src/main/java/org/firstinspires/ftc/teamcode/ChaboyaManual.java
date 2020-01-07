package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "ChaboyaManual")
public class ChaboyaManual extends LinearOpMode{
    private ChaboyaBot robot;

    public void runOpMode(){
        robot = new ChaboyaBot(telemetry, hardwareMap);
        waitForStart();
        while(opModeIsActive()){
            if (gamepad1.a){
                robot.fingers.setPosition(.12);
            }
            if (gamepad1.b){
                robot.fingers.setPosition(.5);
            }
            if (gamepad2.a){
                robot.hand_x.setPosition(.12);
            }
            if (gamepad2.b){
                robot.hand_x.setPosition(.5);
            }
            if (gamepad2.x){
                robot.hand_y.setPosition(.12);
            }
            if (gamepad2.y){
                robot.hand_y.setPosition(.5);
            }

        }
    }
}
