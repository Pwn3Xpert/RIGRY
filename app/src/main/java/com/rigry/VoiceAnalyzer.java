package com.rigry;


import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.concurrent.ThreadLocalRandom;

public class VoiceAnalyzer {
    
	private Context context;
	public static TextToSpeech TTS;
	private InfoBase info = new InfoBase();
	private Boolean engineInitialized = false;
	
	public void setContext(Context c)
	{
		context = c;
	}
	
	
    public void run(String text)
	{
		
		if (text.contains("რა არის "))
		{
			String[] splited = text.split("რა არის ");
			info.wikipedia(splited[1], context, new InfoBaseCallback(){
				@Override
				public void onSuccess(String result)
				{
					Speak(result);
				}
			});
		}
		else
		{
			String response = geeciPasuxi(text);
            Speak(response);
		}
	}
    
	public void launch(String pkg){
		Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(pkg);
		if (launchIntent != null) { 
			context.startActivity(launchIntent);
		}
	}
	
	public void Initialize()
	{
		TTS = new TextToSpeech(context, new TextToSpeech.OnInitListener(){
			@Override
			public void onInit(int p1) 
			{
				if (p1 != TTS.ERROR)
				{
					TTS.setLanguage(Locale.UK);
					TTS.setEngineByPackageName("com.github.olga_yakovleva.rhvoice.android");
					engineInitialized = true;
				}
			}	
		});
	}
	
	public void Speak(final String text){
		if (engineInitialized)
		{
			TTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
		}
		else
		{
			System.out.println("საჭიროა ინიციალიზაცია");
		}
	}
    
    @Override
    public JSONObject jsonObj;
    public JSONObject getData()
    {
        try
        {
            InputStream is = context.getAssets().open("data.json");
            
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            
            String jsonStr = new String(buffer, "UTF-8");
            
            try
            {
                jsonObj = new JSONObject(jsonStr);
            } catch (JSONException e) 
            {
                e.printStackTrace();
            }
            
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        
        return jsonObj;
    }
    
    @Override
    public JSONObject data;
    public String answer;
    public String geeciPasuxi(String question)
    {
        data = getData();
        if (data != null)
        {
            if (data.has(question))
            {
                try 
                {
                    // answer = data.getString(question);
                    Object obj = data.get(question);
                    if (obj instanceof JSONArray)
                    {
                        JSONArray dataArr = (JSONArray) obj;
                        int randIndex = ThreadLocalRandom.current().nextInt(0,dataArr.length());
                        answer = (String) dataArr.get(randIndex);
                    }
                    else
                    {
                        answer = obj.toString();
                    }
                    
                } catch (JSONException e) 
                {
                    e.printStackTrace();
                }
            } 
            else
            {
                answer = "უკაცრავად";
            }
        }
        
        return answer;
    }
    
    
    
}


