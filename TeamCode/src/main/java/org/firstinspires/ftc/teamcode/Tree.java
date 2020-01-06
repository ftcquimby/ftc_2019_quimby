package org.firstinspires.ftc.teamcode;

public class Tree {
    public int height;
    public String color;

    public Tree(){
        height = 7;
        color = "green";
    }
    public Tree(int height1){
        height = height1;
    }
    public Tree(int height1, String color1){
       height = height1;
       color = color1;
    }
    public int getHeight(){
        int realHeight;
        realHeight = height + 1;
        return (realHeight);
    }
}
