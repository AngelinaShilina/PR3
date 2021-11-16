package main.java;

public abstract class Shape {
    protected String color;
    protected boolean filled;
    public Shape(){
        this.filled = false;
        this.color = "default";
    }
    public Shape(String color, boolean filled){
        this.color = color;
        this.filled = filled;
    }
    public String getColor() {
        return this.color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public boolean isFilled() {
        return this.filled;
    }
    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public String toString() {
        return "Shape{" +
                "color='" + color + '\'' +
                ", filled=" + filled +
                '}';
    }
    public abstract double getArea();
    public abstract double getPerimeter();
}

