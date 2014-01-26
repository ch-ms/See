package chms.ru.see;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * ������ ��� ��� ���
 * @author chms
 *
 */

public class SeeWebViewClient extends WebViewClient {
	
	MainActivity a;
	
	public SeeWebViewClient(MainActivity activity){
		a = activity;
	}
	
	@Override
	public boolean shouldOverrideUrlLoading(WebView wv, String url){
		a.goToUrl(url);
		return true;
	}
}
