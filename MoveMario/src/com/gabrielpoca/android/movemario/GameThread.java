package com.gabrielpoca.android.movemario;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
	
	private static final String TAG = GameThread.class.getSimpleName();
	
	private GamePanel gamePanel;
	private SurfaceHolder surfaceHolder;
	
	private boolean running;
	
	public GameThread(GamePanel _gamePanel, SurfaceHolder _surfaceHolder) {
		super();
		gamePanel = _gamePanel;
		surfaceHolder = _surfaceHolder;
	}
	
	public void setRunning(boolean _running) {
		running = _running;
	}
	
	public void run() {
		Canvas canvas;
		Log.d(TAG, "Starting game loop");
		while(running) {
			canvas = null;
			try {
				canvas = surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					gamePanel.update();
//					Log.d(TAG, "Drawing...");
					gamePanel.onDraw(canvas);
				}
			} finally {
				if(canvas != null)
					surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}
		Log.d(TAG, "End game loop");
	}
}
