package com.gabrielpoca.android.movemario;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Map {

	private int _dWidth;
	private int _dHeight;

	private Bitmap _block;
	private int _blockSize;

	private int _offset;
	
	private Block _bone;

	public Map(Bitmap block, int dWidth, int dHeight) {
		_block = block;
		_blockSize = _block.getWidth();
		
		_offset = 0;

		_bone = new Block(_block, 50, 50, _block.getWidth(), _block.getHeight());
		_dWidth = dWidth;
		_dHeight = dHeight;
	}

	public void setDWith(int dWidth) {
		_dWidth = dWidth;
	}

	public void setDHeight(int dHeight) {
		_dHeight = dHeight;
	}
	
	public Coord getPlayerInitCoords() {
		return new Coord(_dWidth / 2, _dHeight - _blockSize);
	}

	public void draw(Canvas canvas) {
		for (int i = - _offset; i < _dWidth; i += _blockSize) {
			_bone.draw(canvas, i, _dHeight
					- _block.getHeight());
		}
	}
	
	public void update() {
		if(_offset >= _blockSize)
			_offset = 0;
		else _offset++;
	}
}
