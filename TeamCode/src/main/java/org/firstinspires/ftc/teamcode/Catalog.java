package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Catalog")
public class Catalog extends LinearOpMode {

    public void runOpMode() {
        PineTree pineTree1 = new PineTree(telemetry, 4, "brown", "sugar");
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
        }
    }
}
