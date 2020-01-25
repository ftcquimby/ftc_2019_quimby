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

@Autonomous(name = "1-QuimbyAutoBluePartnerAtWall")

public class QuimbyAuto extends LinearOpMode {
    private OmegaBot myRobot;
    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";

    private static final String VUFORIA_KEY =
            "AW6hudP/////AAABmSb5kt3O+UP1ibJMJ1u2q7V6YYmXB6QpH/noQlDby0O8n1lxPZmdNY8fugUNak9gnyB20NnTzMVsxgb/2QkA5jOlfn8Wtt37C5ASc1BrvxwL1zrL0cDpSkFyhvYKkIVFK/lfSPaYW0rfLPJxdeNV7qXeVo3B7PPkJqHaIhPgajcJCLZSBxtRw2/2EarD+ELqjE+AvvcngBT9TThyUyzSLOh9wZHNmAGtVDvzINOSOcojy0gpywbjfR8E32ZX8rpZP9+RaDUNYkDH2J13SiqehkNx3sUm4RYX9/ifdInyljRKi6m1DRwQprk7fYYS9ygb06ElevQxNmq0DSg7mxYe8gziX7v/UqmEw50cVX/QXie9";

    private VuforiaLocalizer vuforia;

    private TFObjectDetector tfod;

    public int ourSkystone = 0;

    @Override
    public void runOpMode() {
        myRobot = new OmegaBot(telemetry, hardwareMap);
        boolean finishedAuto = false;

        /*
        myRobot.leftGripper.setPosition(myRobot.LEFT_FOUNDATION_GRIPPER_RELEASE);
        myRobot.rightGripper.setPosition(myRobot.RIGHT_FOUNDATION_GRIPPER_RELEASE);

        sleep(2000);
        myRobot.leftGripper.setPosition(myRobot.LEFT_FOUNDATION_GRIPPER_GRAB);
        myRobot.rightGripper.setPosition(myRobot.RIGHT_FOUNDATION_GRIPPER_GRAB);

        sleep(2000);

        myRobot.leftGripper.setPosition(myRobot.LEFT_FOUNDATION_GRIPPER_RELEASE);
        myRobot.rightGripper.setPosition(myRobot.RIGHT_FOUNDATION_GRIPPER_RELEASE);

        sleep(2000);
*/
        myRobot.arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);//MadanBellam
        myRobot.arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);//MadanBellam
        myRobot.arm.setTargetPosition(0);
        myRobot.arm.setPower(.2);
        myRobot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);//MadanBellam

        myRobot.extension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);//MadanBellam
        myRobot.extension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);//MadanBellam
        myRobot.extension.setTargetPosition(0);
        myRobot.extension.setPower(.2);
        myRobot.extension.setMode(DcMotor.RunMode.RUN_TO_POSITION);//MadanBellam
        sleep(200);

        initializeVuforia();
        initializeTfod();

        if (tfod != null) {
            tfod.activate();
        }
        waitForStart();

        if (opModeIsActive()) {

            myRobot.arm.setTargetPosition(200);
            myRobot.arm.setPower(.5);
            sleep(500);

            while (opModeIsActive()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        //telemetry.addData("# Object Detected", updatedRecognitions.size());
                        // step through the list of recognitions and display boundary info.
                        int i = 0;
                        for (Recognition recognition : updatedRecognitions) {
                            if (recognition.getLabel().equals(LABEL_SECOND_ELEMENT)) {

                                if ((recognition.getRight() - recognition.getLeft()) < 270) {

                                    if ((recognition.getLeft() > 250) && (recognition.getLeft() < 450)) {
                                        ourSkystone = 3;

                                    }
                                    if ((recognition.getLeft() > 100) && (recognition.getLeft() <= 250)) {
                                        ourSkystone = 2;

                                    }
                                    if ((recognition.getLeft() > 0) && (recognition.getLeft() <= 100)) {
                                        ourSkystone = 1;

                                    }

                                }
                                telemetry.addData("Found Skystone: ", ourSkystone);
                                telemetry.addData("\n\rLeft Value=", recognition.getLeft());
                                telemetry.addData("\n\rRight Value=", recognition.getRight());

                            }

                           /* telemetry.addData(String.format("  left,top (%  d)", i), "%.03f , %.03f",
                                    recognition.getLeft(), recognition.getTop());
                            telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                    recognition.getRight(), recognition.getBottom());

                            */

                        }
                        telemetry.update();
                    }
                }

                ourSkystone = 1;

                if ((finishedAuto == false) && (ourSkystone > 0)) {
                    finishedAuto = true;

                    if (ourSkystone == 3) {
                        myRobot.frontLeft.setPower(-0.2);
                        myRobot.frontRight.setPower(-0.2);
                        myRobot.backLeft.setPower(-0.2);
                        myRobot.backRight.setPower(-0.2);
                        sleep(4000);
                        myRobot.frontLeft.setPower(0);
                        myRobot.frontRight.setPower(0);
                        myRobot.backLeft.setPower(0);
                        myRobot.backRight.setPower(0);

                        myRobot.frontLeft.setPower(-0.2);
                        myRobot.frontRight.setPower(+0.2);
                        myRobot.backLeft.setPower(+0.2);
                        myRobot.backRight.setPower(-0.2);
                        sleep(1000);
                        myRobot.frontLeft.setPower(0);
                        myRobot.frontRight.setPower(0);
                        myRobot.backLeft.setPower(0);
                        myRobot.backRight.setPower(0);

                        sleep(10000);

                        myRobot.frontLeft.setPower(+0.2);
                        myRobot.frontRight.setPower(-0.2);
                        myRobot.backLeft.setPower(-0.2);
                        myRobot.backRight.setPower(+0.2);
                        sleep(1000);
                        myRobot.frontLeft.setPower(0);
                        myRobot.frontRight.setPower(0);
                        myRobot.backLeft.setPower(0);
                        myRobot.backRight.setPower(0);

                        myRobot.frontLeft.setPower(0.2);
                        myRobot.frontRight.setPower(0.2);
                        myRobot.backLeft.setPower(0.2);
                        myRobot.backRight.setPower(0.2);
                        sleep(4000);
                        myRobot.frontLeft.setPower(0);
                        myRobot.frontRight.setPower(0);
                        myRobot.backLeft.setPower(0);
                        myRobot.backRight.setPower(0);

                        myRobot.arm.setTargetPosition(0);
                        myRobot.arm.setPower(0);
                        sleep(500);
                    }

                    if (ourSkystone == 2) {
                        myRobot.frontLeft.setPower(-0.2);
                        myRobot.frontRight.setPower(-0.2);
                        myRobot.backLeft.setPower(-0.2);
                        myRobot.backRight.setPower(-0.2);
                        sleep(4000);
                        myRobot.frontLeft.setPower(0);
                        myRobot.frontRight.setPower(0);
                        myRobot.backLeft.setPower(0);
                        myRobot.backRight.setPower(0);

                        sleep(10000);

                        myRobot.frontLeft.setPower(0.2);
                        myRobot.frontRight.setPower(0.2);
                        myRobot.backLeft.setPower(0.2);
                        myRobot.backRight.setPower(0.2);
                        sleep(4000);
                        myRobot.frontLeft.setPower(0);
                        myRobot.frontRight.setPower(0);
                        myRobot.backLeft.setPower(0);
                        myRobot.backRight.setPower(0);

                        myRobot.arm.setTargetPosition(0);
                        myRobot.arm.setPower(0);
                        sleep(500);

                    }

                    if (ourSkystone == 1) {
                        //We are trying to go 25 inches
                        myRobot.frontLeft.setPower(-0.5);
                        myRobot.frontRight.setPower(-0.5);
                        myRobot.backLeft.setPower(-0.5);
                        myRobot.backRight.setPower(-0.5);
                        sleep(1310);
                        myRobot.frontLeft.setPower(0);
                        myRobot.frontRight.setPower(0);
                        myRobot.backLeft.setPower(0);
                        myRobot.backRight.setPower(0);

                        //Go left for 9 inches
                        myRobot.frontLeft.setPower(+0.5);
                        myRobot.frontRight.setPower(-0.5);
                        myRobot.backLeft.setPower(-0.5);
                        myRobot.backRight.setPower(+0.5);
                        sleep(675);
                        myRobot.frontLeft.setPower(0);
                        myRobot.frontRight.setPower(0);
                        myRobot.backLeft.setPower(0);
                        myRobot.backRight.setPower(0);

                        //Pick up 1st stone
                        myRobot.arm.setTargetPosition(1100);
                        myRobot.arm.setPower(.8);
                        sleep(1000);
                        //extend arm out
                        myRobot.extension.setTargetPosition(-2800);
                        myRobot.extension.setPower(.8);
                        sleep(2500);

                        myRobot.hand_y.setPosition(.85);
                        myRobot.hand_x.setPosition(myRobot.HANDX_90DEGREES);
                        myRobot.fingers.setPosition(myRobot.FINGERS_OPENWIDE);
                        sleep(500);

                        myRobot.arm.setTargetPosition(100);
                        myRobot.arm.setPower(.8);
                        sleep(1500);

                        myRobot.hand_y.setPosition(.85);
                        myRobot.fingers.setPosition(myRobot.FINGERS_GRAB);
                        sleep(200);

                        myRobot.arm.setTargetPosition(300);
                        myRobot.arm.setPower(.8);
                        sleep(200);

                        //Move Back a little
                        myRobot.frontLeft.setPower(+0.5);
                        myRobot.frontRight.setPower(+0.5);
                        myRobot.backLeft.setPower(+0.5);
                        myRobot.backRight.setPower(+0.5);
                        sleep(400);
                        myRobot.frontLeft.setPower(0);
                        myRobot.frontRight.setPower(0);
                        myRobot.backLeft.setPower(0);
                        myRobot.backRight.setPower(0);

                        //turn stone and pull it in
                        myRobot.hand_x.setPosition(myRobot.HANDX_0DEGREES);
                        myRobot.extension.setTargetPosition(-500);
                        myRobot.extension.setPower(.8);
                        sleep(1500);

                        //Move Forward a little
                        myRobot.frontLeft.setPower(-0.5);
                        myRobot.frontRight.setPower(-0.5);
                        myRobot.backLeft.setPower(-0.5);
                        myRobot.backRight.setPower(-0.5);
                        sleep(250);
                        myRobot.frontLeft.setPower(0);
                        myRobot.frontRight.setPower(0);
                        myRobot.backLeft.setPower(0);
                        myRobot.backRight.setPower(0);

                        myRobot.frontLeft.setPower(+0.5);
                        myRobot.frontRight.setPower(-0.5);
                        myRobot.backLeft.setPower(-0.5);
                        myRobot.backRight.setPower(+0.5);
                        sleep(4400);
                        myRobot.frontLeft.setPower(0);
                        myRobot.frontRight.setPower(0);
                        myRobot.backLeft.setPower(0);
                        myRobot.backRight.setPower(0);

                        myRobot.hand_y.setPosition(.81);
                        myRobot.arm.setTargetPosition(800);
                        myRobot.arm.setPower(.8);
                        sleep(1000);
                        myRobot.extension.setTargetPosition(-4000);
                        myRobot.arm.setPower(.8);
                        sleep(2000);

                        //drop the stone
                        myRobot.fingers.setPosition(myRobot.FINGERS_OPEN);

                        //Withdraw the arm
                        myRobot.arm.setTargetPosition(300);
                        myRobot.arm.setPower(.8);
                        myRobot.extension.setTargetPosition(-500);
                        myRobot.extension.setPower(.8);
                        sleep(1500);

                        //Come back and park
                        myRobot.frontLeft.setPower(-0.5);
                        myRobot.frontRight.setPower(+0.5);
                        myRobot.backLeft.setPower(+0.5);
                        myRobot.backRight.setPower(-0.5);
                        sleep(3000);
                        myRobot.frontLeft.setPower(0);
                        myRobot.frontRight.setPower(0);
                        myRobot.backLeft.setPower(0);
                        myRobot.backRight.setPower(0);

                        myRobot.arm.setTargetPosition(0);
                        myRobot.arm.setPower(.8);
                        myRobot.extension.setTargetPosition(0);
                        myRobot.extension.setPower(.8);
                        sleep(1500);



                    }
                }

            }

        }

        if (tfod != null) {
            tfod.shutdown();
        }


    }

    private void initializeVuforia(){
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    private void initializeTfod(){
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);

        tfodParameters.minimumConfidence = 0.8;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }
}
