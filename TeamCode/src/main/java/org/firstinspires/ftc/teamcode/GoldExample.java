package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.skystone.SkystoneDetector;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.opencv.core.Rect;


@TeleOp(name="Gold Example", group="DogeCV")

public class GoldExample extends OpMode
{
    // Detector object
    private SkystoneDetector detector;


    @Override
    public void init() {
        telemetry.addData("Status", "DogeCV 2019.1 - Gold Example");
        // Set up detector
        detector = new SkystoneDetector(); // Create detector
        detector.useDefaults(); // Set detector to use default settings
    }

    /*
     * Code to run REPEATEDLY when the driver hits INIT
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {

    }

    /*
     * Code to run REPEATEDLY when the driver hits PLAY
     */
    @Override
    public void loop() {
        telemetry.addData("IsFound: ", detector.isDetected());
        Rect rect = detector.foundRectangle();
        if(detector.isDetected()) telemetry.addData("Location: ", Integer.toString((int) (rect.x + rect.width*0.5)) + ", " + Integer.toString((int) (rect.y+0.5*rect.height)));
        telemetry.update();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        // Disable the detector
        //if(detector != null) detector.disable();
    }

}