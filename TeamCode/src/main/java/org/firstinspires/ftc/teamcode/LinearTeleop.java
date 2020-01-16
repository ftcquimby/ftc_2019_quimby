package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

@TeleOp(name = "1-QuimbyManual")
public class LinearTeleop extends LinearOpMode {
    private OmegaBot robot;
    private ElapsedTime runtime;
    double maxSpeed = 1;
    boolean intakeOn = false;
    boolean ejectOn = false;
    private int armPosition = 0;
    private double handYPosition = .77;//hand_y position
    private int previousArmPositionRange = 0;
    private int curArmExtension = 0;
    private boolean button1xPressed = false;
    private boolean button1yPressed = false;
    private double curHandXPosition = 0;
    private double curHandYPosition = 0;
    private boolean button1aPressed = false;
    private boolean button1bPressed = false;

    private boolean dPadLeftPressed = false;
    private boolean dPadRightPressed = false;
    private boolean dPadUpPressed = false;
    private boolean dPadDownPressed = false;

    private boolean brickFound1 = false;
    private boolean brickFound2 = false;

    private boolean noStone = true;

    public void runOpMode(){
        robot = new OmegaBot(telemetry,hardwareMap);

        robot.arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);//MadanBellam
        robot.arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);//MadanBellam
        robot.arm.setTargetPosition((int)robot.HANDHANGPOSITION[robot.armCurStep][2]);
        robot.arm.setPower(.2);
        robot.arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);//MadanBellam
        sleep((long)robot.HANDHANGPOSITION[robot.armCurStep][6]);

        robot.extension.setMode(DcMotor.RunMode.RUN_USING_ENCODER);//MadanBellam
        robot.extension.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);//MadanBellam
        robot.extension.setTargetPosition(0);
        robot.extension.setPower(.2);
        robot.extension.setMode(DcMotor.RunMode.RUN_TO_POSITION);//MadanBellam

        robot.hand_x.setPosition(robot.HANDX_0DEGREES);
        robot.hand_y.setPosition(.85);
        robot.fingers.setPosition(robot.FINGERS_GRAB);
        sleep((long)robot.HANDHANGPOSITION[robot.armCurStep][5]);

        waitForStart();
        runtime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

        //kick out any stone that is in the intake bay
        //robot.leftIntake.setPower(1);
        //robot.rightIntake.setPower(-1);

        while(opModeIsActive()){
            //testMotors();


            //stoneFoundProcess();
            drivetrainProcess();
            //intakeProcessStop();
            //intakeProcessInOut();
            //foundationGrippers();


            pickupOnA();
            pickupOnB();
            pickupOnX();//Going away leftwards from the robot
            pickupOnY();//Going away rightwards from the robot
            armToHome(true);
            grabRelease();
            extensionProcess();
            armProcess3();
            handXManualProcess();
            handYManualProcess();

        }
    }

    public void pickupOnA(){

        if (gamepad2.a == true){
            //rotate arm up to get out of the way of the front motors
            robot.arm.setTargetPosition(300);
            robot.arm.setPower(.5);
            sleep(500);

            //extend arm out
            robot.extension.setTargetPosition(-2800);
            robot.extension.setPower(.5);
            sleep(1400);

            robot.hand_y.setPosition(.75);
            robot.fingers.setPosition(robot.FINGERS_OPEN);
            sleep(500);

            //rotate arm to be ready for pickup, Pickup happens at 200
            robot.arm.setTargetPosition(-100);
            robot.arm.setPower(.5);
            sleep(1000);

            robot.hand_y.setPosition(.75);
            robot.fingers.setPosition(robot.FINGERS_GRAB);
            sleep(1000);

            robot.arm.setTargetPosition(0);
            robot.arm.setPower(.5);
            sleep(500);

        }
    }

    public void pickupOnB(){

        if (gamepad2.b == true){
            //rotate arm up to get out of the way of the front motors
            robot.arm.setTargetPosition(300);
            robot.arm.setPower(.5);
            robot.hand_x.setPosition(robot.HANDX_90DEGREES);
            sleep(500);

            //extend arm out
            robot.extension.setTargetPosition(-2800);
            robot.extension.setPower(.8);
            sleep(1400);

            robot.hand_y.setPosition(.75);
            robot.fingers.setPosition(robot.FINGERS_OPEN);
            sleep(500);

            robot.arm.setTargetPosition(100);
            robot.arm.setPower(.5);
            sleep(1000);

            robot.arm.setTargetPosition(-100);
            robot.arm.setPower(.5);
            sleep(1000);

            robot.hand_y.setPosition(.75);
            robot.fingers.setPosition(robot.FINGERS_GRAB);
            sleep(1000);

            robot.arm.setTargetPosition(0);
            robot.arm.setPower(.5);
            sleep(500);

            robot.hand_x.setPosition(robot.HANDX_0DEGREES);


        }
    }

    public void pickupOnX(){

        if (gamepad2.x == true){
            //rotate arm up to get out of the way of the front motors
            robot.arm.setTargetPosition(300);
            robot.arm.setPower(.5);
            robot.hand_x.setPosition(robot.HANDX_45DEGREES);
            sleep(500);

            //extend arm out
            robot.extension.setTargetPosition(-2700);
            robot.extension.setPower(.8);
            sleep(1400);

            robot.hand_y.setPosition(.75);
            robot.fingers.setPosition(robot.FINGERS_OPEN);
            sleep(500);

            robot.arm.setTargetPosition(100);
            robot.arm.setPower(.5);
            sleep(1000);

            robot.arm.setTargetPosition(-100);
            robot.arm.setPower(.5);
            sleep(1000);

            robot.hand_y.setPosition(.75);
            robot.fingers.setPosition(robot.FINGERS_GRAB);
            sleep(1000);

            robot.arm.setTargetPosition(0);
            robot.arm.setPower(.5);
            sleep(500);

            robot.hand_x.setPosition(robot.HANDX_0DEGREES);

        }
    }

    public void pickupOnY(){

        if (gamepad2.y == true){
            //rotate arm up to get out of the way of the front motors
            robot.arm.setTargetPosition(300);
            robot.arm.setPower(.5);
            sleep(500);

            //extend arm out
            robot.extension.setTargetPosition(-2850);
            robot.extension.setPower(.8);
            sleep(1400);

            robot.hand_y.setPosition(.75);
            robot.hand_x.setPosition(robot.HANDX_135DEGREES);
            robot.fingers.setPosition(robot.FINGERS_OPEN);
            sleep(500);

            robot.arm.setTargetPosition(100);
            robot.arm.setPower(.5);
            sleep(1000);

            robot.arm.setTargetPosition(-100);
            robot.arm.setPower(.5);
            sleep(1000);

            robot.hand_y.setPosition(.75);
            robot.fingers.setPosition(robot.FINGERS_GRAB);
            sleep(1000);

            //extend arm out
            robot.extension.setTargetPosition(-3200);
            robot.extension.setPower(.8);
            sleep(1000);

            robot.arm.setTargetPosition(0);
            robot.arm.setPower(.5);
            sleep(1000);

            robot.hand_x.setPosition(robot.HANDX_0DEGREES);


        }
    }

    //If noStone = true, it will go back all the way inside the robot to the starting position
    //If noStone = false, it assumes it has a stone in its grasp and will stay extended out in front, holding the stone, but in a
    //                      position where it can go under the bridge.
    public void armToHome(boolean noStone){
        if (gamepad2.back == true){
            //rotate arm up to get out of the way of the front motors
            robot.arm.setTargetPosition(900);
            robot.arm.setPower(.5);
            sleep(500);

            robot.hand_x.setPosition(robot.HANDX_0DEGREES);
            robot.hand_y.setPosition(.85);
            robot.fingers.setPosition(robot.FINGERS_GRAB);//Close fingers

            //withdraw arm in
            robot.extension.setTargetPosition(0);
            robot.extension.setPower(.5);
            sleep(1000);

            //Rotate back inside the robot
            robot.arm.setTargetPosition(0);
            robot.arm.setPower(.5);

        }
    }


    public void extensionProcess(){
        int ePosition = robot.extension.getCurrentPosition();
        if (robot.logArmPosition){
            telemetry.addData("\r\nExtension Encoder Position: ", ePosition);
            telemetry.update();
        }

        if(gamepad2.right_stick_y < -.5){ //Extends the Arm
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
        }else if (gamepad2.right_stick_y > .5){ //Pulls in the Arm
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

    public void armProcess3(){
        int ePosition = robot.arm.getCurrentPosition();
        int armStepNow = 0;
        boolean done = false;
        boolean userUsingArm = false;

        if(gamepad2.left_stick_y < -.5){ //Rotate the Arm
            userUsingArm = true;
            if (ePosition < 3125) { //Never go above this number
                if (ePosition >= 3000) {
                    ePosition = 3125;
                }
                else {
                    ePosition = ePosition + 125;
                }
                robot.arm.setTargetPosition(ePosition);
                robot.arm.setPower(.5);
            }
        }else if (gamepad2.left_stick_y > .5){
            userUsingArm = true;
            if (ePosition > -100) { //Never go below -100
                if (ePosition <= 25) {
                    ePosition = -100;
                }
                else {
                    ePosition = ePosition - 125;
                }
                robot.arm.setTargetPosition(ePosition);
                robot.arm.setPower(.5);
            }
        }

        //Update the Arm Hang Position based on the arm rotation
        //Figure out which arm step we are on now
        if (userUsingArm) {
            ePosition = robot.arm.getCurrentPosition();
            while (!done) {
                if ((ePosition >= (robot.HANDHANGPOSITION[armStepNow][2] - 10)) && (ePosition <= (robot.HANDHANGPOSITION[armStepNow][2] + 10))) {
                    done = true;
                } else {
                    armStepNow++;
                    if (armStepNow > 27) {
                        done = true;
                    }
                }
            }
            if (armStepNow <= 27) {
                robot.hand_y.setPosition(robot.HANDHANGPOSITION[armStepNow][1]);
            }
        }

    }


    public void handXManualProcess(){
        double curHandXPosition;
        curHandXPosition = robot.hand_x.getPosition();
        if (gamepad2.dpad_left){ //DPad Left pressed
            if (dPadLeftPressed == false) {
                dPadLeftPressed = true;
                if (curHandXPosition <= .95) {
                    robot.hand_x.setPosition(curHandXPosition + .05);
                }
            } else {
                //We already saw this button press - do not do it again now
            }
        } else {
            dPadLeftPressed = false; //button is not pressed any more
        }

        if (gamepad2.dpad_right){ //DPad Left pressed
            if (dPadRightPressed == false) {
                dPadRightPressed = true;
                if (curHandXPosition >= .05) {
                    robot.hand_x.setPosition(curHandXPosition - .05);
                }
            } else {
                //We already saw this button press - do not do it again now
            }
        } else {
            dPadRightPressed = false; //button is not pressed any more
        }
    }

    public void handYManualProcess(){
        double curHandYPosition;
        curHandYPosition = robot.hand_y.getPosition();

        if (gamepad2.dpad_up){ //DPad Up pressed
            if (dPadUpPressed == false) {
                dPadUpPressed = true;
                if (curHandYPosition <= .95) {
                    robot.hand_x.setPosition(curHandYPosition + .05);
                }
            } else {
                //We already saw this button press - do not do it again now
            }
        } else {
            dPadUpPressed = false; //button is not pressed any more
        }

        if (gamepad2.dpad_down){ //DPad Down pressed
            if (dPadDownPressed == false) {
                dPadDownPressed = true;
                if (curHandYPosition >= .05) {
                    robot.hand_x.setPosition(curHandYPosition - .05);
                }
            } else {
                //We already saw this button press - do not do it again now
            }
        } else {
            dPadDownPressed = false; //button is not pressed any more
        }
    }

    //Test Motors
    public void testMotors(){
        if (gamepad2.a == true){
            robot.hand_x.setPosition(0);

        }
        if (gamepad2.b == true){
            robot.hand_x.setPosition(1);

        }


        if (gamepad1.a == true){
            robot.frontLeft.setPower(.2);
            robot.frontRight.setPower(0);
            robot.backLeft.setPower(0);
            robot.backRight.setPower(0);
        }else{
            robot.frontLeft.setPower(0);
        }
        if (gamepad1.b == true){
            robot.frontLeft.setPower(0);
            robot.frontRight.setPower(.2);
            robot.backLeft.setPower(0);
            robot.backRight.setPower(0);
        }else{
            robot.frontRight.setPower(0);
        }
        if (gamepad1.x == true){
            robot.frontLeft.setPower(0);
            robot.frontRight.setPower(0);
            robot.backLeft.setPower(.2);
            robot.backRight.setPower(0);
        }else{
            robot.backLeft.setPower(0);
        }
        if (gamepad1.y == true){
            robot.frontLeft.setPower(0);
            robot.frontRight.setPower(0);
            robot.backLeft.setPower(0);
            robot.backRight.setPower(0.2);
        }else{
            robot.backRight.setPower(0);
        }
    }

    //Extend the arm based on Gamepad1 x and y button presses. One extension per press
    public void processExtension(){
        if (gamepad1.x == true) { //Button 1x pressed
            if (button1xPressed == false) { //We are seeing it pressed for the first time
                button1xPressed = true;
                curArmExtension = curArmExtension - 100;
                robot.extension.setTargetPosition(curArmExtension);
                robot.extension.setPower(.1);
            } else {
                //We already saw this button press and moved the arm extension - do not do it again now
            }
        } else {
            button1xPressed = false; //Button 1x is not pressed any more
        }

        if (gamepad1.y == true) { //Button 1x pressed
            if (button1yPressed == false) { //We are seeing it pressed for the first time
                button1yPressed = true;
                curArmExtension = curArmExtension + 100;
                robot.extension.setTargetPosition(curArmExtension);
                robot.extension.setPower(.1);
            } else {
                //We already saw this button press and moved the arm extension - do not do it again now
            }
        } else {
            button1yPressed = false; //Button 1x is not pressed any more
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


    public void grabRelease(){

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


    public void armProcess2(){
        if(gamepad2.left_stick_y < -.5){
            if (robot.armCurStep > 0){
                robot.armCurStep--;
            }
        }else if (gamepad2.left_stick_y > .5){
            if (robot.armCurStep < 27){
                robot.armCurStep++;
            }
        }
        if (robot.armCurStep != robot.armPrevStep){

            if (robot.HANDHANGPOSITION[robot.armCurStep][2] != robot.HANDHANGPOSITION[robot.armPrevStep][2]){
                robot.arm.setTargetPosition((int)robot.HANDHANGPOSITION[robot.armCurStep][2]);
                robot.arm.setPower(.5);
            }

            robot.armPrevStep = robot.armCurStep;
        }

    }

    public void intakeProcessStop(){
        if (gamepad1.left_trigger > .5 && gamepad1.right_trigger > .5) {
            intakeOn = false;
            ejectOn = false;
            robot.leftIntake.setPower(0); //stop
            robot.rightIntake.setPower(0);
        }
    }

    public void intakeProcessInOut(){
        if (gamepad1.left_trigger > .5 && gamepad1.right_trigger > .5) {//ignore when both triggers are pressed. It is handled above.
        }else{
            if (gamepad1.left_trigger > .5){
                intakeOn = true;
                ejectOn = false;
            }
            if (gamepad1.right_trigger > .5){
                intakeOn = false;
                ejectOn = true;
            }

            if (intakeOn == true){
                robot.leftIntake.setPower(-.2);
                robot.rightIntake.setPower(.2);
            }
            if (ejectOn == true) {
                ejectOn = false;
                robot.leftIntake.setPower(0);
                robot.rightIntake.setPower(0);
                sleep(1000);//wait for intake to stop
                robot.leftIntake.setPower(.2); //reverse the intake
                robot.rightIntake.setPower(-.2);
                sleep(1500);//wait for the block to be kicked out
                robot.leftIntake.setPower(0);
                robot.rightIntake.setPower(0);
            }
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
