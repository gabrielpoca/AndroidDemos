package com.gabrielpoca.android.movemario;

public class Coord {

	private int _x;
	private int _y;

	public Coord(int x, int y) {
		_x = x;
		_y = y;
	}

	public void setX(int x) {
		_x = x;
	}

	public void setY(int y) {
		_y = y;
	}

	public void setCoord(int x, int y) {
		_x = x;
		_y = y;
	}

	public int getX() {
		return _x;
	}

	public int getY() {
		return _y;
	}

}
