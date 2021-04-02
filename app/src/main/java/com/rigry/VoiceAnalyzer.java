package com.rigry;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import java.util.Locale;

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
		
		if (text.contains("ინფორმაცია "))
		{
			String[] splited = text.split("ინფორმაცია ");
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
			if (text.equals("გამარჯობა"))
			{
				Speak("გაგიმარჯოს");
			}
			else if (text.equals("მესენჯერი"))
			{
				launch("com.facebook.orca");
			}
			else
			{
				Speak("უკაცრავად");
			}
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
}


