package chms.ru.see;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Custom client for web view
 * @author chms
 *
 */

public class SeeWebViewClient extends WebViewClient {
	
	private MainActivity a;
	
	public SeeWebViewClient(MainActivity activity){
		a = activity;
	}
	
	@Override
	public boolean shouldOverrideUrlLoading(WebView wv, String url){
		a.goToUrl(url);
		return true;
	}
	
	@Override
	public void onLoadResource(WebView view, String url){
		a.updateBackForwardUi();
	}
	
	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon){
		a.updateAddressInputUi(url);
		a.updateBackForwardUi();
	}
	
	@Override
	public void onPageFinished(WebView view, String url){
		a.updateAddressInputUi(url);
		a.updateBackForwardUi();
	}
}
