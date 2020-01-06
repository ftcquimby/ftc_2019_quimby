package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class PineTree extends Tree {

    public Telemetry telemetry;
    public String leafType;

    public PineTree(){
        leafType = "sugar";
    }

    public PineTree(String leafType1){
        leafType = leafType1;
    }

    public PineTree(Telemetry telemetry1, int height1, String color1, String leafType1){
        super(height1, color1);
        leafType = leafType1;
        telemetry = telemetry1;
    }
    public void printTree(){
            int realHeight1;
            realHeight1 = getHeight();

            telemetry.addData("\n\rheight=", realHeight1);
            telemetry.addData("color=", color);
            telemetry.addData("leaf type=", leafType);
            telemetry.update();

    }
}
