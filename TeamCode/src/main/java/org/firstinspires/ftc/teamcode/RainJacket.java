package org.firstinspires.ftc.teamcode;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class RainJacket extends Jacket {
    public Telemetry telemetry;
    public String brand;

    public RainJacket() {
        brand = "Gucci";
    }

    public RainJacket(String brand1) {
        brand = brand1;
    }

    public RainJacket(Telemetry telemetry1, String size1, String color1, int zippers1, String brand1) {
        super(size1, color1, zippers1);
        telemetry = telemetry1;
        brand = brand1;
    }

    public void printJacket(){
        telemetry.addData("\n\rsize=", size);
        telemetry.addData("color=", color);
        telemetry.addData("zippers=", zippers);
        telemetry.update();
    }
}
