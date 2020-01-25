package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "ChaboyaParkFromRight")

public class ChaboyaParkBlueRight extends LinearOpMode{
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

                    //Go left for 2 feet
                    myRobot.frontLeft.setPower(+0.5);
                    myRobot.frontRight.setPower(-0.5);
                    myRobot.backLeft.setPower(-0.5);
                    myRobot.backRight.setPower(+0.5);
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
