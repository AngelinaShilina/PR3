package main.java;
public class Square extends Rectangle {
    private double side;
    public Square(){
        super();
    }
    public Square(double side){
        super(side,side);
    }
    public  Square(double side, String color, boolean filled)
    {
        super(side,side,color,filled);
    }
    public double getSide(){
        return this.length;
    }
    public void setSide(double side){
        this.length = side;
        this.width = side;
    }
    @Override
    public String toString() {
        return "Square{" +
                "color='" + color + '\'' +
                ", filled=" + filled +
                ", side=" + side +
                '}';
    }
}

