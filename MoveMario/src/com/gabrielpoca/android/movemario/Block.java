package com.gabrielpoca.android.movemario;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Block {

	private Bitmap _bitmap;
	private int _x;
	private int _y;
	private int _width;
	private int _height;

	public Block(Bitmap bitmap, int x, int y, int width, int height) {
		_bitmap = bitmap;
		_x = x;
		_y = y;
		_width = width;
		_height = height;
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(_bitmap, _x - (_width / 2), _y - (_height / 2), null);
	}

	public void draw(Canvas canvas, int x, int y) {
		canvas.drawBitmap(_bitmap, x, y, null);
	}

}
