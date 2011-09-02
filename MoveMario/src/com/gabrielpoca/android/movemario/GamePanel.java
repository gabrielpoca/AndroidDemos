package com.gabrielpoca.android.movemario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = GamePanel.class.getSimpleName();

	private JumpMario mario;
	private Map map;
	private GameThread thread;

	public GamePanel(Context context) {
		super(context);
		getHolder().addCallback(this);

		thread = new GameThread(this, getHolder());
		// Insert Mario
		Bitmap[] move_bitmap = new Bitmap[3];
		move_bitmap[0] = BitmapFactory.decodeResource(getResources(),
				R.drawable.mariowalk1);
		move_bitmap[1] = BitmapFactory.decodeResource(getResources(),
				R.drawable.mariowalk2);
		move_bitmap[2] = BitmapFactory.decodeResource(getResources(),
				R.drawable.mariowalk3);

		Bitmap[] jump_bitmap = new Bitmap[1];
		jump_bitmap[0] = BitmapFactory.decodeResource(getResources(),
				R.drawable.mariojump);

		mario = new JumpMario(0, 0, move_bitmap, jump_bitmap, 3, 1, move_bitmap[0]
				.getWidth(), move_bitmap[0].getHeight(), 10);

		map = new Map(BitmapFactory.decodeResource(getResources(),
				R.drawable.mario_tile), getWidth(), getHeight());

		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		map.setDWith(width);
		map.setDHeight(height);
		Coord pos = map.getPlayerInitCoords();
		mario.setX(pos.getX());
		mario.setY(pos.getY());
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// TODO: handle exception
			}
		}
	}

	public boolean onTouchEvent(MotionEvent event) {
		Log.d(TAG, "Jump draw");
//		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			mario.init_jump(System.currentTimeMillis());
//		}
//		if (event.getAction() == MotionEvent.ACTION_UP) {
//
//		}
		return super.onTouchEvent(event);
	}

	protected void onDraw(Canvas canvas) {
//		Log.d(TAG, "Draw back...");
		canvas.drawColor(Color.BLUE);
//		Log.d(TAG, "Draw map...");
		map.draw(canvas);
//		Log.d(TAG, "Draw mario...");
		mario.draw(canvas);
	}

	public void update() {
		mario.update(System.currentTimeMillis());
		map.update();
	}

}
