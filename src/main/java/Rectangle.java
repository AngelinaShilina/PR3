package main.java;

public class Rectangle extends Shape{
    protected double width;
    protected double length;
    public Rectangle(){
        super();
    }
    public Rectangle(double width, double length){
        this.length = length;
        this.width = width;
    }
    public Rectangle(double width, double length, String color, boolean filled){
        super(color,filled);
        this.width=width;
        this.length=length;
    }
    public double getWidth(){
        return this.width;
    }
    public double getLength(){
        return this.length;
    }
    public void setWidth(double width){
        this.width = width;
    }
    public void setLength(double length){
        this.length = length;
    }

    @Override
    public double getArea(){
        double S = width*length;
        return S;
    }
    @Override
    public  double getPerimeter(){
        double P = 2*(width+length);
        return P;
    }
    @Override
    public String toString() {
        return "Reсtangle{" +
                "width=" + width +
                ", length=" + length +
                ", color='" + color + '\'' +
                ", filled=" + filled +
                '}';
    }
}
