package com.rigry;
import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import java.util.ArrayList;
import java.util.List;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Iterator;
import android.widget.Toast;

public class InfoBase {
	
	@Override
	private RequestQueue reqQueue;
    public void wikipedia(String title, final Context context, final InfoBaseCallback callback)
	{
		String URL = "https://ka.wikipedia.org/w/api.php?action=query&format=json&titles=" + title + "&prop=extracts&exintro&explaintext";
		reqQueue = Volley.newRequestQueue(context);
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>(){
				
			@Override
			public void onResponse(JSONObject obj) {
				try {
					JSONObject objQuery = obj.getJSONObject("query");
					JSONObject objPages = objQuery.getJSONObject("pages");
					Iterator<String> keys = objPages.keys();
					String key = null;
					
					if (keys.hasNext())
					{
						key = (String) keys.next();
					}
					
					if (key != null)
					{
						JSONObject currentPage = objPages.getJSONObject(key);
						String information = currentPage.getString("extract").toString();
						// InfoBaseCallback.onSuccess(information);
						callback.onSuccess(information);
						//Toast.makeText(context, information, Toast.LENGTH_LONG).show();
					}
					
				} catch (JSONException e) {
					System.out.println(e.toString());
				}
			}
			
		}, new Response.ErrorListener(){
			@Override
			public void onErrorResponse(VolleyError p1) {
				System.out.println(p1.toString());
			}
		});
		
		reqQueue.add(jsonObjReq);
		
	}
    
}
