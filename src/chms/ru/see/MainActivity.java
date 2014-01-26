package chms.ru.see;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
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
	
	private String rememberedUrl;
	
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
		
		forgetRememberedUrl();
		
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
	 * Go to a given url
	 * @param url URL
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
	 * Remember current url
	 */
	private void rememberCurrentUrl(){
		rememberedUrl = wv.getUrl();
		updateMemorizeButtons();
	}
	
	/**
	 * Forget remembered url
	 */
	private void forgetRememberedUrl(){
		rememberedUrl = "";
		updateMemorizeButtons();
	}
	
	/**
	 * Navigate to remembered url
	 * @return True if we got remembered url, otherwise false
	 */
	private boolean goToRememberedUrl(){
		String url = getRememberedUrl();
		if(url!=""){
			wv.loadUrl(url);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Get remembered url
	 * @return Remembered url
	 */
	private String getRememberedUrl(){
		return rememberedUrl;
	}
	
	/**
	 * Go button pressed
	 * @param v
	 */
	public void onGoClick(View v){
		goToUrl(addressInput.getText().toString());
	}
	
	/**
	 * Back button pressed
	 * @param v
	 */
	public void onBackClick(View v){
		wv.goBack();
	}
	
	/**
	 * Memorize button pressed
	 * @param v
	 */
	public void onMemorizeButtonClick(View v){
		if(!goToRememberedUrl()){
			rememberCurrentUrl();	
		}
	}
	
	/**
	 * Forget button pressed
	 */
	public void onForgetButtonClick(View v){
		forgetRememberedUrl();
	}
	
	/**
	 * Forward button pressed
	 * @param v
	 */
	public void onFrwdButtonClick(View v){
		wv.goForward();
	}
	
	/**
	 * Refresh back and forward buttons states
	 */
	public void updateBackForwardUi(){
		frwdButton.setEnabled(wv.canGoForward());
		backButton.setEnabled(wv.canGoBack());
	}
	
	/**
	 * Refresh URL value in a address input
	 */
	public void updateAddressInputUi(){
		updateAddressInputUi(wv.getUrl());
	}
	
	/**
	 * Refresh URL value in a address input
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
	
	/**
	 * Refresh UI of memorize buttons
	 */
	public void updateMemorizeButtons(){
		if(getRememberedUrl()==""){
			memorizeButton.setTextColor(Color.BLACK);
			forgetButton.setEnabled(false);
		}else{
			memorizeButton.setTextColor(Color.RED);
			forgetButton.setEnabled(true);
		}
	}

}
