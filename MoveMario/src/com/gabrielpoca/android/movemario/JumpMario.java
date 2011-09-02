package com.gabrielpoca.android.movemario;

import java.lang.Math;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class JumpMario extends AnimatedModel {

	private static final String TAG = JumpMario.class.getSimpleName();
	private final long _period_jump = 1000;

	private Bitmap[] _bitmap;
	private int _n_frames;

	private boolean _in_jump;
	private int _y_jump;
	private long _time_jump;

	public JumpMario(int x, int y, Bitmap[] move_bitmap, Bitmap[] jump_bitmap,
			int n_move, int n_jump, int sprite_width, int sprite_height, int fps) {
		super(x, y, move_bitmap, n_move, sprite_width, sprite_height, fps);
		_in_jump = false;
		_bitmap = jump_bitmap;
		_n_frames = n_jump;
	}

	public void init_jump(long gameTime) {
		_in_jump = true;
		_current_frame = 0;
		_y_jump = this.getY();
		_time_jump = gameTime;
	}

	public void end_jump() {
		_in_jump = false;
		_current_frame = 0;
	}

	public void draw(Canvas canvas) {
		if (!_in_jump)
			super.draw(canvas);
		else {
			canvas.drawBitmap(this._bitmap[_current_frame], getX()
					- (_sprite_width / 2), _y_jump - _sprite_height, null);
		}
	}

	public void update(long gameTime) {
		if (!_in_jump) {
			super.update(gameTime);
		} else {
			// if (gameTime - _time_jump > _period_jump) {

			/* Update mario animation */
			_last_frame = gameTime;
			// increment the frame
			_current_frame++;
			if (_current_frame >= this._n_frames) {
				_current_frame = 0;
			}
			/* Update position */
			_y_jump = (int) getJumpY(gameTime);
			Log.d(TAG, "Jump Y: " + _y_jump);
			if (gameTime - _time_jump > _period_jump) {
				end_jump();
			}
		}
	}

	public double getJumpY(long gameTime) {
		return getY() - _sprite_height
				+ Math.pow(((gameTime - _time_jump) / 100) - 5, 2);
	}

}
