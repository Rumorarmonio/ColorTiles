package com.example.colortilescanvas;

public class Tile { //класс плитки с двумя её диагональными точками, цветом и множеством сеттеров/геттеров

    short[] pointFirst;
    short[] pointSecond;
    int color;

    public Tile() {
    }

    public Tile(int color, short[] pointFirst, short[] pointSecond) {
        this.color = color;
        this.pointFirst = pointFirst;
        this.pointSecond = pointSecond;
    }

    public short[] getPointFirst() {
        return pointFirst;
    }

    public short getPointFirstX() {
        return this.pointFirst[0];
    }

    public short getPointFirstY() {
        return this.pointFirst[1];
    }

    public short[] getPointSecond() {
        return pointSecond;
    }

    public short getPointSecondX() {
        return this.pointSecond[0];
    }

    public short getPointSecondY() {
        return this.pointSecond[1];
    }

    public void getPoints(short[] pointFirst, short[] pointSecond) {
        this.pointFirst = pointFirst;
        this.pointSecond = pointSecond;
    }

    public int getColor() {
        return color;
    }

    public void setPointFirst(short[] pointFirst) {
        this.pointFirst = pointFirst;
    }

    public void setPointFirst(short pointFirstX, short pointFirstY) {
        this.pointFirst[0] = pointFirstX;
        this.pointFirst[1] = pointFirstY;
    }

    public void setPointFirstX(short pointFirst) {
        this.pointFirst[0] = pointFirst;
    }

    public void setPointFirstY(short pointFirst) {
        this.pointFirst[1] = pointFirst;
    }

    public void setPointSecond(short[] pointSecond) {
        this.pointSecond = pointSecond;
    }

    public void setPointSecond(short pointSecondX, short pointSecondY) {
        this.pointSecond[0] = pointSecondX;
        this.pointSecond[1] = pointSecondY;
    }

    public void setPointSecondX(short pointSecond) {
        this.pointSecond[0] = pointSecond;
    }

    public void setPointSecondY(short pointSecond) {
        this.pointSecond[1] = pointSecond;
    }

    public void setPoints(short[] pointFirst, short[] pointSecond) {
        this.pointFirst = pointFirst;
        this.pointSecond = pointSecond;
    }

    public void setPoints(short pointFirstX, short pointFirstY, short pointSecondX, short pointSecondY) {
        this.pointFirst[0] = pointFirstX;
        this.pointFirst[1] = pointFirstY;
        this.pointSecond[0] = pointSecondX;
        this.pointSecond[1] = pointSecondY;
    }

    public void setXes(short pointFirstX, short pointSecondX) {
        this.pointFirst[0] = pointFirstX;
        this.pointSecond[0] = pointSecondX;
    }

    public void setYs(short pointFirstY, short pointSecondY) {
        this.pointFirst[1] = pointFirstY;
        this.pointSecond[1] = pointSecondY;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
