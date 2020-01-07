package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Element extends Car {
    public int doors;
    public Telemetry telemetry;

    public Element(){
        doors = 2;
    }

    public Element(int doors1){
        doors = doors1;
    }

    public Element (Telemetry telemetry1, String color1, int year1, int mileage1, int doors1){
        super(color1, year1, mileage1);
        doors = doors1;
        telemetry = telemetry1;
    }

    public void printElement(){
        telemetry.addData("color=", color);
        telemetry.addData("year=", year);
        telemetry.addData("mileage=", mileage);
        telemetry.addData("doors=", doors);
        telemetry.update();
    }
}
