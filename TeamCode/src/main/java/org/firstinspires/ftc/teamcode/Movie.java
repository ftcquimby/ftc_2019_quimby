package org.firstinspires.ftc.teamcode;

public class Movie {
    public int time;
    public int cost;

    public Movie(){
        time = 2;
        cost= 10;
    }
    public Movie (int time1) {time = time1};
    public Movie (int time1, int cost1){
        time1= time;
        cost1 =cost;
        }
        public int getTime(){
        int realTime = time + 15;
        return realTime;
        }


}
