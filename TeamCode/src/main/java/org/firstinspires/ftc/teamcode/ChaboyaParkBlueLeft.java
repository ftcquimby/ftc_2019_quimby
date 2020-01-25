package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Autonomous(name = "ChaboyaParkFromLeft")

public class ChaboyaParkBlueLeft extends LinearOpMode {

    private Chaboya2019Base myRobot;
    private boolean autoDone = false;

    @Override
    public void runOpMode() {
        myRobot = new Chaboya2019Base(telemetry, hardwareMap);

        myRobot.arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);//MadanBellam
        myRobot.arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);//MadanBellam
        myRobot.arm.setTargetPosition(0);
        myRobot.arm.setPower(.2);
        myRobot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);//MadanBellam


        waitForStart();

        if (opModeIsActive()) {

            while (opModeIsActive()) {
                if (autoDone == false){
                    autoDone = true;
                        //GO forward 1 inch
                    myRobot.frontLeft.setPower(-0.2);
                    myRobot.frontRight.setPower(-0.2);
                    myRobot.backLeft.setPower(-0.2);
                    myRobot.backRight.setPower(-0.2);
                    sleep(200);
                    myRobot.frontLeft.setPower(0);
                    myRobot.frontRight.setPower(0);
                    myRobot.backLeft.setPower(0);
                    myRobot.backRight.setPower(0);

                    //Go right for 2 feet
                    myRobot.frontLeft.setPower(-0.5);
                    myRobot.frontRight.setPower(+0.5);
                    myRobot.backLeft.setPower(+0.5);
                    myRobot.backRight.setPower(-0.5);
                    sleep(1000);
                    myRobot.frontLeft.setPower(0);
                    myRobot.frontRight.setPower(0);
                    myRobot.backLeft.setPower(0);
                    myRobot.backRight.setPower(0);


                }

            }

        }



    }

}
