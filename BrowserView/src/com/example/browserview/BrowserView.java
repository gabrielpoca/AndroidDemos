package com.example.browserview;


import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class BrowserView extends Activity {
	private WebView webView;
	private TextView urlText;
	private Button goButton;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		webView = (WebView) findViewById(R.id.web_view);
		urlText = (TextView) findViewById(R.id.url_field);
		goButton = (Button) findViewById(R.id.go_button);

		goButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				openBrowser();

			}
		});

		urlText.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					openBrowser();
					return true;
				}
				return false;
			}
		});
	}

	private void openBrowser() {
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(urlText.getText().toString());
	}
}