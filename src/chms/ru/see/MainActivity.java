package chms.ru.see;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity {
	
	public final String USER_AGENT = "see/1.0";
	
	private WebView wv; 
	private EditText addressInput;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		addressInput = (EditText)findViewById(R.id.addressInput);
		
		wv = (WebView)findViewById(R.id.navigator);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.getSettings().setDomStorageEnabled(true);
		wv.getSettings().setBuiltInZoomControls(true);
		wv.getSettings().setUserAgentString(USER_AGENT);
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
	 * Перейти на урл
	 * @param {String} url Адрес
	 */
	public void goToUrl(String url){
		url = url.trim();
		url = url.replaceFirst("/$", "");		
		
		String urlForWv = url;
		String urlForAddress = url;
		
		Pattern httpPat = Pattern.compile("^https?://");
		Matcher m = httpPat.matcher(url);
		if(m.find()){
			urlForAddress = m.replaceFirst("");
		}else{
			urlForWv = "http://" + urlForWv;
		}
		
		addressInput.setText(urlForAddress);
		wv.loadUrl(urlForWv);
	}

}
