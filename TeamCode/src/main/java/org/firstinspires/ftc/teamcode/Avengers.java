package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Avengers extends Movie {

    public Telemetry telemetry;
    public  int numberOfMovies;

    public Avengers(){
     numberOfMovies = 4;
    }

    public Avengers(int numberOfMovies1) {
        numberOfMovies = numberOfMovies1;
    }
    public Avengers(Telemetry telemetry1, int time1, int cost1, int numberOfMovies1) {
        super(time1, cost1);
        numberOfMovies = numberOfMovies1;
        telemetry = telemetry1;
    }

    public void printMovie() {
        int realTime1;
        realTime1 = getTime();

        telemetry.addData("\n\rtime=", realTime1);
        telemetry.addData("cost=", cost );
        telemetry.addData("number of movies=", numberOfMovies);
        telemetry.update();
    }

}
