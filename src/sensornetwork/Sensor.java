package sensornetwork;

import java.awt.Point;

public class Sensor {
	private int radius;
	private Point position;
	private int isJoinS;
	private int isJoinT;

	public Sensor(int x, int y, int radius) {
		super();
		this.radius = radius;
		this.position = new Point(x,y);
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public int getIsJoinS() {
		return isJoinS;
	}

	public void setIsJoinS(int isJoinS) {
		this.isJoinS = isJoinS;
	}

	public int getIsJoinT() {
		return isJoinT;
	}

	public void setIsJoinT(int isJoinT) {
		this.isJoinT = isJoinT;
	}
	
	public int getX() {
		return (int) this.getPosition().getX();
	}
	
	public int getY() {
		return (int) this.getPosition().getY();
	}

	
}
