package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

@TeleOp(name = "Chaboya2019TeleOp")
public class Chaboya2019TeleOp extends LinearOpMode {
    private Chaboya2019Base robot;
    private ElapsedTime runtime;
    double maxSpeed = 1;
    boolean intakeOn = false;
    private int armPosition = 0;
    private double handYPosition = .77;//hand_y position
    private int previousArmPositionRange = 0;

    private boolean brickFound1 = false;
    private boolean brickFound2 = false;

    public void runOpMode(){
        robot = new Chaboya2019Base(telemetry,hardwareMap);
        waitForStart();
        runtime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        robot.leftIntake.setPower(1);
        robot.rightIntake.setPower(-1);

        while(opModeIsActive()){
            stoneFoundProcess();
            drivetrainProcess();
            armProcess();
            extensionProcess();
            servoProcess();
            intakeProcessIn();
            intakeProcessOut();
            foundationGrippers();

            //telemetry.addData("\n\rhand_x position: ", robot.hand_x.getPosition());
            //telemetry.addData("\n\rleft Foundation Gripper position: ", robot.leftGripper.getPosition());
            //telemetry.addData("\n\rright Foundation Gripper position: ", robot.rightGripper.getPosition());
            //telemetry.update();
        }
    }

    //Using the Rev Color Sensor V3 for Distance Sensing using the Infra Red Range Sensor in it
    //  - 5cm to 25cm
    //  - - Avoid other Infra Red Light - like sunlight
    //Color Sensor Parameters
    //  - Keep the Color Sensor about 2cm from the color you are trying to sense, the white light should reflect back into it
    //

    public void stoneFoundProcess() {
        getDistanceData();


    }

    public void getDistanceData(){
        brickFound1 = false;
        brickFound2 = false;

        double distance1 = robot.distanceSensor1.getDistance(DistanceUnit.CM);
        double distance2 = robot.distanceSensor2.getDistance(DistanceUnit.CM);

        //telemetry.addData(",\r\n distance1: ", distance1);
        //telemetry.addData(",\r\n distance2: ", distance2);
        //telemetry.update();

    }

    public void moveArm(double degrees){
        double maxVel = 15 * 360 / 60000; // 15 is the rotations per minute, 360 is the degrees per rotation, 60000 is the number of milliseconds in a minute
        double macAcc = maxVel / 1300; //1300 is the number of milliseconds it takes to accelerate to full speed
        MotionProfileGenerator generator = new MotionProfileGenerator(maxVel, macAcc);
        double[] motionProfile = generator.generateProfile(degrees);
        double[] distanceProfile = generator.generateDistanceProfile(motionProfile);
        runtime.reset();
        while(runtime.milliseconds() < motionProfile.length && opModeIsActive()){
            robot.arm.setPower(motionProfile[(int)runtime.milliseconds()]/maxVel);//TODO: use the distance profile + encoders to pid up in dis bicth
        }
    }


    public void handXManualProcess(){
        if (gamepad1.x){
            robot.hand_x.setPosition(robot.HANDX_0DEGREES);
        }else if (gamepad1.y) {
            robot.hand_x.setPosition(robot.HANDX_90DEGREES);
        }
    }

    public void handYManualProcess(){
        if (gamepad1.a){
            robot.hand_y.setPosition(.77);
        }else if (gamepad1.b) {
            robot.hand_y.setPosition(.55);
        }
        telemetry.addData("\r\nHand Y Position =", handYPosition);
        telemetry.update();
    }

    public void servoProcess(){
        //handXManualProcess();
        //handYManualProcess();

        if(gamepad2.right_bumper){
            robot.fingers.setPosition(robot.FINGERS_OPEN);
        }else if(gamepad2.left_bumper){
            robot.fingers.setPosition(robot.FINGERS_GRAB);
        }
    }

    public void OLDservoProcess(){
        //handXManualProcess();
        //handYManualProcess();
        int armPosition = robot.arm.getCurrentPosition();
        //Always hang the block carrier vertically down

        if (armPosition < -240 && armPosition >= -260 && previousArmPositionRange !=-2){
            previousArmPositionRange = -2;
            handYPosition = robot.HANDY_STARTPOSITION+.01;
            robot.hand_y.setPosition(handYPosition);
        }
        if (armPosition < -120 && armPosition >= -130 && previousArmPositionRange !=-1){
            previousArmPositionRange = -1;
            handYPosition = robot.HANDY_STARTPOSITION+.01;
            robot.hand_y.setPosition(handYPosition);
        }
        if (armPosition < -5 && armPosition >= 5 && previousArmPositionRange !=0){
            previousArmPositionRange = 0;
            handYPosition = robot.HANDY_STARTPOSITION; //.77
            robot.hand_y.setPosition(handYPosition);
        }
        if (armPosition > 120 && armPosition <= 140 && previousArmPositionRange !=1){
            previousArmPositionRange = 1;
            handYPosition = robot.HANDY_STARTPOSITION-.02;
            robot.hand_y.setPosition(handYPosition);
        }
        if (armPosition > 240 && armPosition <= 260 && previousArmPositionRange !=2){
            previousArmPositionRange = 2;
            handYPosition = robot.HANDY_STARTPOSITION-.04;
            robot.hand_y.setPosition(handYPosition);
        }
        if (armPosition > 365 && armPosition <= 385 && previousArmPositionRange !=3){
            previousArmPositionRange = 3;//We are free of rollers if block is horizontal
            handYPosition = robot.HANDY_STARTPOSITION-.06;
            robot.hand_y.setPosition(handYPosition);
        }
        if (armPosition > 450 && armPosition <= 510 && previousArmPositionRange !=4){
            previousArmPositionRange = 4;
            handYPosition = robot.HANDY_STARTPOSITION-.08;
            robot.hand_y.setPosition(handYPosition);
        }
        if (armPosition > 665 && armPosition <= 685 && previousArmPositionRange !=5){
            previousArmPositionRange = 5;
            handYPosition = robot.HANDY_STARTPOSITION-.10;
            robot.hand_y.setPosition(handYPosition);
        }
        if (armPosition > 790 && armPosition <= 810 && previousArmPositionRange !=6){
            previousArmPositionRange = 6;
            handYPosition = robot.HANDY_STARTPOSITION-.25;
            robot.hand_y.setPosition(handYPosition);
            if (robot.extension.getCurrentPosition() !=0) {//Only retract if needed - this ensures we do not waste 1 sec if already at 0
                robot.extension.setTargetPosition(0);
                robot.extension.setPower(.5);
                sleep(1000);
            }
        }
        if (armPosition > 800 && armPosition <= 925 && previousArmPositionRange !=8){
            previousArmPositionRange = 8;
            handYPosition = .52;
            robot.hand_y.setPosition(handYPosition);
        }
        if (armPosition > 925 && armPosition <= 1050 && previousArmPositionRange !=9){
            previousArmPositionRange = 9;
            handYPosition = .52;
            robot.hand_y.setPosition(handYPosition);
        }
        if (armPosition > 1050 && armPosition <= 1175 && previousArmPositionRange !=10){
            previousArmPositionRange = 10;
            handYPosition = .52;
            robot.hand_y.setPosition(handYPosition);
            robot.extension.setTargetPosition(-1500);
            robot.extension.setPower(.5);
        }
        if (armPosition > 1175 && armPosition <= 1300 && previousArmPositionRange !=11){
            previousArmPositionRange = 11;
            handYPosition = .55;
            robot.hand_y.setPosition(handYPosition);
        }
        if (armPosition > 1300 && armPosition <= 1425 && previousArmPositionRange !=12){
            previousArmPositionRange = 12;
            handYPosition = .53;
            robot.hand_y.setPosition(handYPosition);
        }
        if (armPosition > 1425 && armPosition <= 1550 && previousArmPositionRange !=13){
            previousArmPositionRange = 13;
            handYPosition = .49;
            robot.hand_y.setPosition(handYPosition);
        }
        if (armPosition > 1550 && armPosition <= 1675 && previousArmPositionRange !=14){
            previousArmPositionRange = 14;
            handYPosition = .44;
            robot.hand_y.setPosition(handYPosition);
        }
        if (armPosition > 1675 && armPosition <= 1800 && previousArmPositionRange !=15){
            previousArmPositionRange = 15;
            handYPosition = .39;
            robot.hand_y.setPosition(handYPosition);
        }
        if (armPosition > 1800 && armPosition <= 1925 && previousArmPositionRange !=16){
            previousArmPositionRange = 16;
            handYPosition = .32;
            robot.hand_y.setPosition(handYPosition);
        }
        if (armPosition > 1925 && armPosition <= 2050 && previousArmPositionRange !=17){
            previousArmPositionRange = 17;
            handYPosition = .28;
            robot.hand_y.setPosition(handYPosition);
        }
        if (armPosition > 2050 && armPosition <= 2175 && previousArmPositionRange !=18){
            previousArmPositionRange = 18;
            handYPosition = .25;
            robot.hand_y.setPosition(handYPosition);
        }
        if (armPosition > 2175 && armPosition <= 2300 && previousArmPositionRange !=19){
            previousArmPositionRange = 19;
            handYPosition = .19;
            robot.hand_y.setPosition(handYPosition);
        }
        if (armPosition > 2300 && armPosition <= 2425 && previousArmPositionRange !=20){
            previousArmPositionRange = 20;
            handYPosition = .13;
            robot.hand_y.setPosition(handYPosition);
        }
        if (armPosition > 2425 && armPosition <= 2550 && previousArmPositionRange !=21){
            previousArmPositionRange = 21;
            handYPosition = .10;
            robot.hand_y.setPosition(handYPosition);
        }
        if (armPosition > 2550 && armPosition <= 2675 && previousArmPositionRange !=22){
            previousArmPositionRange = 22;
            handYPosition = .10;
            robot.hand_y.setPosition(handYPosition);
        }
        if (armPosition > 2675 && armPosition <= 2800 && previousArmPositionRange !=23){
            previousArmPositionRange = 23;
            handYPosition = .10;
            robot.hand_y.setPosition(handYPosition);
        }
        if (armPosition > 2800 && armPosition <= 2925 && previousArmPositionRange !=24){
            previousArmPositionRange = 24;
            handYPosition = .08;
            robot.hand_y.setPosition(handYPosition);
        }
        if (armPosition > 2925 && armPosition <= 3050 && previousArmPositionRange !=25){
            previousArmPositionRange = 25;
            handYPosition = .08;
            robot.hand_y.setPosition(handYPosition);
        }

        if (robot.logArmPosition){
            telemetry.addData("\r\nHand Y Position =", handYPosition);
            telemetry.addData("\r\nArm Position =", armPosition);
            telemetry.addData("\r\nPrev Arm Position Range =", previousArmPositionRange);
            telemetry.update();
        }

        robot.hand_y.setPosition(handYPosition);
        if(gamepad2.right_bumper){
            robot.fingers.setPosition(robot.FINGERS_OPEN);
        }else if(gamepad2.left_bumper){
            robot.fingers.setPosition(robot.FINGERS_GRAB);
        }
    }

    public void foundationGrippers(){
        if(gamepad1.left_bumper){
            robot.leftGripper.setPosition(robot.LEFT_FOUNDATION_GRIPPER_GRAB);
            robot.rightGripper.setPosition(robot.RIGHT_FOUNDATION_GRIPPER_GRAB);
        }
        else if (gamepad1.right_bumper){
            robot.leftGripper.setPosition(robot.LEFT_FOUNDATION_GRIPPER_RELEASE);
            robot.rightGripper.setPosition(robot.RIGHT_FOUNDATION_GRIPPER_RELEASE);
        }
    }

    public void armProcess(){
        if(gamepad2.a){
            if (robot.armCurStep > 0){
                robot.armCurStep--;
            }
        }else if (gamepad2.b){
            if (robot.armCurStep < 27){
                robot.armCurStep++;
            }
        }
        if (robot.armCurStep != robot.armPrevStep){
            if (robot.HANDHANGPOSITION[robot.armCurStep][1] != robot.HANDHANGPOSITION[robot.armPrevStep][1]){
                robot.hand_y.setPosition(robot.HANDHANGPOSITION[robot.armCurStep][1]);
            }
            if (robot.HANDHANGPOSITION[robot.armCurStep][4] != robot.HANDHANGPOSITION[robot.armPrevStep][4]){
                robot.hand_x.setPosition(robot.HANDHANGPOSITION[robot.armCurStep][4]);
            }

            //Give enough time for the hand position to change before changing the arm
            //This is needed so that the fingers do not crash into the compliant wheels while coming down quickly
            if (robot.HANDHANGPOSITION[robot.armCurStep][1] != robot.HANDHANGPOSITION[robot.armPrevStep][1]){
                if (robot.HANDHANGPOSITION[robot.armCurStep][5] !=0) {
                    sleep((long) robot.HANDHANGPOSITION[robot.armCurStep][5]);
                }
            }

            if (robot.HANDHANGPOSITION[robot.armCurStep][2] != robot.HANDHANGPOSITION[robot.armPrevStep][2]){
                robot.arm.setTargetPosition((int)robot.HANDHANGPOSITION[robot.armCurStep][2]);
                robot.arm.setPower(.5);
            }
            if (robot.HANDHANGPOSITION[robot.armCurStep][3] != robot.HANDHANGPOSITION[robot.armPrevStep][3]) {
                robot.extension.setTargetPosition((int)robot.HANDHANGPOSITION[robot.armCurStep][3]);
                robot.extension.setPower(.5);
            }

            //Give enough time for the arm position to extend or withdraw
            //This is needed so that the arm does not crash into the front surgical band while coming down quickly
            if (robot.HANDHANGPOSITION[robot.armCurStep][3] != robot.HANDHANGPOSITION[robot.armPrevStep][3]){
                if (robot.HANDHANGPOSITION[robot.armCurStep][6] !=0) {
                    sleep((long) robot.HANDHANGPOSITION[robot.armCurStep][6]);
                }
            }
            robot.armPrevStep = robot.armCurStep;
        }

    }

    public void OLDarmProcess(){
        int position = robot.arm.getCurrentPosition();
        //telemetry.addData("Arm Encoder Position", position);
        //telemetry.update();
        if(gamepad2.a){
            //robot.arm.setPower(.75);
            //moveArm(-30);
            if (position > -400) { //Never go below -120
                if (position <= -125) {
                    position = -125;
                }
                else {
                    position = position - 125;
                }
                robot.arm.setTargetPosition(position);
                robot.arm.setPower(.5);
            }
            position = robot.arm.getCurrentPosition();
            telemetry.addData("Arm Encoder Position A=", position);
            telemetry.update();
        }else if (gamepad2.b){
            //moveArm(30);
            //robot.arm.setPower(-.75);
            if (position < 3000) { //Never go above 1000
                if (position >= 2875) {
                    position = 3000;
                }
                else {
                    position = position + 125;
                }
                robot.arm.setTargetPosition(position);
                robot.arm.setPower(.5);
            }
            position = robot.arm.getCurrentPosition();
            telemetry.addData("Arm Encoder PositionB=", position);
            telemetry.update();
        }
    }

    public void extensionProcess(){
        int ePosition = robot.extension.getCurrentPosition();
        if (robot.logArmPosition){
            telemetry.addData("\r\nExtension Encoder Position: ", ePosition);
            telemetry.update();
        }

        if(gamepad2.y){ //Extends the Arm
            //robot.arm.setPower(.75);
            //moveArm(-30);
            if (ePosition >= -3800) { //Never go below -1000
                if (ePosition <= -3720) {
                    ePosition = -3800;
                }
                else {
                    ePosition = ePosition - 80;
                }
                robot.extension.setTargetPosition(ePosition);
                robot.extension.setPower(.5);
            }
            ePosition = robot.extension.getCurrentPosition();
            //telemetry.addData("Extension Encoder Position Y", ePosition);
            //telemetry.update();
        }else if (gamepad2.x){ //Pulls in the Arm
            //moveArm(30);
            //robot.arm.setPower(-.75);
            if (ePosition <= 0) { //Never go above 0
                if (ePosition >= -80) {
                    ePosition = 0;
                }
                else {
                    ePosition = ePosition + 80;
                }
                robot.extension.setTargetPosition(ePosition);
                robot.extension.setPower(.5);
            }
            ePosition = robot.extension.getCurrentPosition();
            //telemetry.addData("Extension Encoder Position X", ePosition);
            //telemetry.update();
        }
    }

    //Press both triggers together to reverse intake
    public void intakeProcessOut(){
        if (gamepad2.left_trigger > .5 && gamepad2.right_trigger > .5) {
            intakeOn = false;
            robot.leftIntake.setPower(0);
            robot.rightIntake.setPower(0);
            sleep(1000);//wait for intake to stop
            robot.leftIntake.setPower(.2); //reverse the intake
            robot.rightIntake.setPower(-.2);
            sleep(2000);//wait for the block to be kicked out
            robot.leftIntake.setPower(0); //stop
            robot.rightIntake.setPower(0);
        }
    }

    public void intakeProcessIn(){
        if (gamepad2.left_trigger > .5 && gamepad2.right_trigger > .5) {//ignore when both triggers are pressed. It is handled above.
        }else{
            if (gamepad2.left_trigger > .5){
                intakeOn = true;
            }
            if (gamepad2.right_trigger > .5){
                intakeOn = false;
            }

            if (intakeOn == true){
                robot.leftIntake.setPower(-.2);
                robot.rightIntake.setPower(.2);
            }
            else {
                robot.leftIntake.setPower(0);
                robot.rightIntake.setPower(0);
            }
        }
    }

    public void intakeProcessOutOld(){
        if(gamepad2.right_trigger > .5) {
            if (gamepad2.left_trigger > .5 && gamepad2.right_trigger > .5){
                robot.leftIntake.setPower(0);
                robot.rightIntake.setPower(0);
            }
            else {
                robot.leftIntake.setPower(.2);
                robot.rightIntake.setPower(-.2);
            }

        }

        else {
            robot.leftIntake.setPower(0);
            robot.rightIntake.setPower(0);
        }
    }
    public void intakeProcessInOld(){
        if (gamepad2.left_trigger > .5){
            if (gamepad2.left_trigger > .5 && gamepad2.right_trigger > .5){
                robot.leftIntake.setPower(0);
                robot.rightIntake.setPower(0);
            }
            else {
                robot.leftIntake.setPower(-.2);
                robot.rightIntake.setPower(.2);
            }

        }

        else {
            robot.leftIntake.setPower(0);
            robot.rightIntake.setPower(0);
        }

    }

    public void drivetrainProcess(){
        double forward = gamepad1.left_stick_y;
        double right = -gamepad1.left_stick_x;
        double clockwise = -gamepad1.right_stick_x *.75;
        //double temp = forward * Math.cos(Math.toRadians(robot.getAngle())) - right * Math.sin(Math.toRadians(robot.getAngle()));
        //right = forward * Math.sin(Math.toRadians(robot.getAngle())) + right * Math.cos(Math.toRadians(robot.getAngle()));
        //forward = temp;

        double front_left = forward + clockwise + right;
        double front_right = forward - clockwise - right;
        double rear_left = forward + clockwise - right;
        double rear_right = forward - clockwise + right;

        //double FrontLeftVal = gamepad1.left_stick_y - (gamepad1.left_stick_x) + -gamepad1.right_stick_x;
        //double FrontRightVal = gamepad1.left_stick_y + (gamepad1.left_stick_x) - -gamepad1.right_stick_x;
        //double BackLeftVal = gamepad1.left_stick_y + (gamepad1.left_stick_x) + -gamepad1.right_stick_x;
        //double BackRightVal = gamepad1.left_stick_y - (gamepad1.left_stick_x) - -gamepad1.right_stick_x;

        double max = Math.abs(front_left);
        if (Math.abs(front_right) > max) max = Math.abs(front_right);
        if (Math.abs(rear_left) > max) max = Math.abs(rear_left);
        if (Math.abs(rear_right) > max) max = Math.abs(rear_right);

        if (max > maxSpeed) {
            front_left /= (max/maxSpeed);
            front_right /= (max/maxSpeed);
            rear_left /= (max/maxSpeed);
            rear_right /= (max/maxSpeed);
        }

        robot.frontLeft.setPower(front_left);
        robot.frontRight.setPower(front_right);
        robot.backLeft.setPower(rear_left);
        robot.backRight.setPower(rear_right);
    }
}
