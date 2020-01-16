package org.firstinspires.ftc.teamcode;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class CurlyHair extends Hair {
    public Telemetry telemetry;
    public String texture;

    public CurlyHair () {
        texture = "smooth";
    }

    public CurlyHair (String texture1) {
        texture = texture1;
    }

    public CurlyHair (Telemetry telemetry1, int length1, String color1, String texture1) {
        super (length1, color1);
        telemetry = telemetry1;
        texture = texture1;
    }

    public void printHair() {
        telemetry.addData("\n\rlength=", length);
        telemetry.addData("color=", color);
        telemetry.addData("texture=", texture);
        telemetry.update();
    }

}
