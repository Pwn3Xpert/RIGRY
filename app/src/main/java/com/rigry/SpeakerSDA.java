package com.rigry;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SpeakerSDA
{
	private MediaPlayer mPlayer;
	public void say(String text, final Context c){
		mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		String req_url = "http://test-voice.sda.gov.ge/DesktopModules/Voice/VoiceService.asmx/Speak";
        JSONObject object = new JSONObject();
        try {
            object.put("argText", text.toString());
            object.put("argRate", "40");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, req_url, object,
			new Response.Listener<JSONObject>() {
				@Override
				public void onResponse(JSONObject response) {
					try {
						JSONArray audio_arr = response.getJSONArray("d");
						String audio = audio_arr.getString(0);
						String audio_url = "http://test-voice.sda.gov.ge/" + audio;
						try {
							mPlayer.setDataSource(audio_url);
							mPlayer.prepare();
							mPlayer.start();
						}
						catch (IOException e){}
						catch (IllegalStateException e){}
						catch (SecurityException e){}
						catch (IllegalArgumentException e){}
						mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
								@Override
								public void onCompletion(MediaPlayer mediaPlayer) {
									mPlayer.reset();
								}
							});
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					Toast.makeText(c, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
				}
			});
        RequestQueue requestQueue = Volley.newRequestQueue(c);
        requestQueue.add(jsonObjectRequest);
	}
}
