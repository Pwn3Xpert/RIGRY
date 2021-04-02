package com.rigry;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends Activity 
{
    @Override
	private Button listenButton;
	private TextView outputText;
	private InfoBase info;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		info = new InfoBase();
		final Listener stt = new Listener();
		final VoiceAnalyzer analyzer = new VoiceAnalyzer();
		listenButton = findViewById(R.id.listenButton);
		outputText = findViewById(R.id.outputText);
		analyzer.setContext(getApplicationContext());
		analyzer.Initialize();
		
		
		
		listenButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view){
				
				if (analyzer.TTS != null) {
					if (analyzer.TTS.isSpeaking()) {
						analyzer.TTS.stop();
					}
				}
				
				stt.startListen(getApplicationContext(), new VoiceCallback(){
					@Override
					public void onResult(String result)
					{
						
						analyzer.run(result);
						outputText.setText(result);
						
					}
				});
			}
		});
    }
	
	
	public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
	
}
