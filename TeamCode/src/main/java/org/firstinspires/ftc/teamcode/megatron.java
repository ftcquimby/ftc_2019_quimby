package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Megatron")
public class megatron extends LinearOpMode {
    public void runOpMode(){
        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("I want to say", "Hello");
            telemetry.update();
        }
    }
}
