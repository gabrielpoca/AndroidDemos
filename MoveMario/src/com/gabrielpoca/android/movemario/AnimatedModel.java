package com.gabrielpoca.android.movemario;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class AnimatedModel {

	protected Coord _coord;
	protected Bitmap[] _bitmap;
	protected int _n_frames;
	protected int _sprite_width;
	protected int _sprite_height;
	protected int _period;

	protected long _last_frame;
	protected int _current_frame;

	public AnimatedModel(int x, int y, Bitmap[] bitmap, int n_frames,
			int sprite_width, int sprite_height, int fps) {
		_coord = new Coord(x, y);
		_bitmap = bitmap;
		_n_frames = n_frames;
		_sprite_height = sprite_height;
		_sprite_width = sprite_width;
		_period = 1000 / fps;
		_last_frame = 0;
		_current_frame = 0;
	}

	public int getX() {
		return _coord.getX();
	}

	public int getY() {
		return _coord.getY();
	}

	public void setCoord(int x, int y) {
		_coord.setCoord(x, y);
	}

	public void setX(int x) {
		_coord.setX(x);
	}

	public void setY(int y) {
		_coord.setY(y);
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(_bitmap[_current_frame], _coord.getX()
				- (_sprite_width / 2), _coord.getY() - _sprite_height, null);
	}

	public void update(long gameTime) {
		/* Update antimation frame */
		if (gameTime > _last_frame + _period) {
			_last_frame = gameTime;
			// increment the frame
			_current_frame++;
			if (_current_frame >= _n_frames) {
				_current_frame = 0;
			}
		}
	}

}
