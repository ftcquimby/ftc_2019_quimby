package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "ChaboyaTesting")
public class ChaboyaManual extends LinearOpMode{
    private OmegaBot robot;
    private int curArmExtension = 0;
    private ElapsedTime runtime;


    public void runOpMode(){
        robot = new OmegaBot(telemetry, hardwareMap);
        /* robot.extension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.extension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.extension.setTargetPosition(1);
        robot.extension.setPower(0.2);
        robot.extension.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        */
        waitForStart();
        runtime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        
        while(opModeIsActive()){
            processExtension();
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
            if (gamepad2.left_trigger > .5){
                robot.leftIntake.setPower(-.2);
                robot.rightIntake.setPower(.2);
            }
            if (gamepad2.right_trigger > .5){
                robot.leftIntake.setPower(0);
                robot.rightIntake.setPower(0);
            }

        }
    }

    public void processExtension(){
        if (gamepad1.x){
            curArmExtension = curArmExtension - 100;
            robot.extension.setTargetPosition(curArmExtension);
            robot.extension.setPower(.1);
        }
        if (gamepad1.y){
            curArmExtension = curArmExtension + 100;
            robot.extension.setTargetPosition(curArmExtension);
            robot.extension.setPower(.1);
        }
    }
}
