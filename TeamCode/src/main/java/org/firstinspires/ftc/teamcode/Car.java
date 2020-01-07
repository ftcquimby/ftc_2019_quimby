package org.firstinspires.ftc.teamcode;

public class Car {
    public String color;
    public int year;
    public int mileage;

    public Car(){
        color = "green";
        year = 2003;
        mileage = 200000;
    }

    public Car(String color1, int year1, int mileage1){
        color = color1;
        year = year1;
        mileage= mileage1;
    }
}
