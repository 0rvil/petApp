package com.example.petapp;
import android.graphics.drawable.Drawable;


public class plant {

    private int water;
    private int fertilizer;
    private boolean alive;
    private float Life;
    private int evolutionStatus;
    private Drawable drawable;

    public plant() {
        Life = (fertilizer + water)/200;
        alive = true;
        evolutionStatus = 1;
        fertilizer = (int) (Math.random()*90)+10;;
        water = (int) (Math.random()*90)+10;
    }

     plant(Drawable avatar) {
        Life = (fertilizer + water)/200;
        alive = true;
        evolutionStatus = 0;
        fertilizer = (int) (Math.random()*90)+10;
        water = (int) (Math.random()*90)+10;
        drawable = avatar;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getFertilizer() {
        return fertilizer;
    }

    public void setFertilizer(int fertilizer) {
        this.fertilizer = fertilizer;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public double getLife() {
        return Life;
    }

    public void setLife(float life) {
        Life = life;
    }

    public int getEvolutionStatus() {
        return evolutionStatus;
    }

    public void setEvolutionStatus(int evolutionStatus) {
        this.evolutionStatus = evolutionStatus;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }
}