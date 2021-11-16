package main.java;

public class MovableCircle implements t{
    private int radius;
    private MovablePoint center;
    public MovableCircle(int x, int y, int xSpeed, int ySpeed, int radius){
        this.radius=radius;
        center = new MovablePoint(x,y,xSpeed,ySpeed);
    }
    public void moveUp() {
        this.center.y+=this.center.ySpeed;
    }
    @Override
    public void moveDown() {
        this.center.y-=this.center.ySpeed;
    }
    @Override
    public void moveLeft() {
        this.center.x-=this.center.xSpeed;
    }
    @Override
    public void moveRight() {
        this.center.x+=this.center.xSpeed;
    }
    @Override
    public String toString() {
        return (this.center.toString() + "радиус круга: " + this.radius);
    }
}

