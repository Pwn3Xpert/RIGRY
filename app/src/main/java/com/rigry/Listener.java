package com.rigry;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import java.util.ArrayList;

public class Listener {
    
	public SpeechRecognizer mSpeechRecognizer;
	public Intent mSpeechRecognizerIntent;
	
    public void startListen(Context c, final VoiceCallback callback){
		mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(c);
		mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, c.getPackageName());
		mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ka-GE");
		mSpeechRecognizer.setRecognitionListener(new RecognitionListener(){
				public void onReadyForSpeech(Bundle p1){}
				public void onBeginningOfSpeech(){}
				public void onRmsChanged(float p1){}
				public void onBufferReceived(byte[] p1){}
				public void onEndOfSpeech(){}
				public void onError(int p1){}
				public void onResults(Bundle p1){
					String SpeechedText = "";
					ArrayList<String> matches = p1.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
					for (String result : matches) {
						SpeechedText += result;
					}
					String res = SpeechedText.toString().trim();
					callback.onResult(res);
				}

				public void onPartialResults(Bundle p1){}
				public void onEvent(int p1, Bundle p2){}
			});
		mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
	}
	
	public void stopListen()
	{
		mSpeechRecognizer.stopListening();
	}
	
    
}
