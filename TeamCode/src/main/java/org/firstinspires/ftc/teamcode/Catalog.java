package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Catalog")
public class Catalog extends LinearOpMode {

    public void runOpMode() {
        RainJacket rainJacket1 = new RainJacket (telemetry, "M", "blue", 1, "Nike");
        RainJacket rainJacket2 = new RainJacket (telemetry, "XXS", "pink", 10, "Prada");
        RainJacket rainJacket3 = new RainJacket (telemetry, "XL", "orange", 0, "Thrifted");
        CurlyHair curlyHair1 = new CurlyHair (telemetry, 4, "blonde", "crunchy");
        CurlyHair curlyHair2 = new CurlyHair (telemetry, 7, "brunette", "smooth");
        CurlyHair curlyHair3 = new CurlyHair (telemetry, 2, "magenta", "course");
        waitForStart();
        while (opModeIsActive()) {
            rainJacket1.printJacket();
            rainJacket2.printJacket();
            rainJacket3.printJacket();
            curlyHair1.printHair();
            curlyHair2.printHair();
            curlyHair3.printHair();
        }

        /*
        PineTree pineTree2 = new PineTree(telemetry, 8, "green", "aleppo");
        PineTree pineTree3 = new PineTree(telemetry, 7, "purple", "pinus");
        Element element1 = new Element(telemetry, "green", 2003, 200000,2);
        Element element2 = new Element(telemetry, "black", 2008, 130000,2);
        Element element3 = new Element(telemetry, "red", 2009, 5,2);
        waitForStart();
        while (opModeIsActive()) {
            pineTree1.printTree();
            pineTree2.printTree();
            pineTree3.printTree();
            element1.printElement();
            element2.printElement();
            element3.printElement();
        }*/
    }
}
