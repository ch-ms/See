package chms.ru.see;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.webkit.WebSettings;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity {
	
	public final String USER_AGENT = "see/1.0";
	
	private WebView wv; 
	private EditText addressInput;
	private Button goButton;
	private Button backButton;
	private Button frwdButton;
	private Button forgetButton;
	private Button memorizeButton;
	
	private final String HTTP_PAT = "^https?://";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		addressInput   = (EditText)findViewById(R.id.addressInput);
		goButton       = (Button)findViewById(R.id.goButton);
		backButton     = (Button)findViewById(R.id.backButton);
		frwdButton     = (Button)findViewById(R.id.forwardButton);
		forgetButton   = (Button)findViewById(R.id.forget);
		memorizeButton = (Button)findViewById(R.id.memorize);
		
		wv = (WebView)findViewById(R.id.navigator);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.getSettings().setDomStorageEnabled(true);
		wv.getSettings().setBuiltInZoomControls(true);
		wv.getSettings().setUserAgentString(USER_AGENT);
		wv.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
		wv.setWebViewClient(new SeeWebViewClient(this));
		
		wv.loadUrl("file:///android_asset/about.html");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * Нажата кнопка go
	 * @param v
	 */
	public void onGoClick(View v){
		goToUrl(addressInput.getText().toString());
	}
	
	/**
	 * Нажата кнопка back
	 * @param v
	 */
	public void onBackClick(View v){
		wv.goBack();
	}
	
	/**
	 * Нажата кнопка forward
	 * @param v
	 */
	public void onFrwdButtonClick(View v){
		wv.goForward();
	}
	
	/**
	 * Перейти на урл
	 * @param url Адрес
	 */
	public void goToUrl(String url){	
		String urlForWv = url.trim();
		
		Matcher m = Pattern.compile(HTTP_PAT).matcher(url);
		if(!m.find()){
			urlForWv = "http://" + urlForWv;
		}
		
		wv.loadUrl(urlForWv);
	}
	
	/**
	 * Обновляет ui кнопок вперед назад.
	 */
	public void updateBackForwardUi(){
		frwdButton.setEnabled(wv.canGoForward());
		backButton.setEnabled(wv.canGoBack());
	}
	
	/**
	 * Обновляет адресную строку
	 */
	public void updateAddressInputUi(){
		updateAddressInputUi(wv.getUrl());
	}
	
	/**
	 * Обновляет адресную строку
	 * @param url
	 */
	public void updateAddressInputUi(String url){
		Matcher m = Pattern.compile("^file:///").matcher(url);
		if(m.find()){
			url = "";
		}else{
			url = url.replaceFirst("/$", "");
			url = url.replaceFirst(HTTP_PAT, "");
		}
		
		addressInput.setText(url);
	}

}
