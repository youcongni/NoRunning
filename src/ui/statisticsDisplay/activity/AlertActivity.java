package ui.statisticsDisplay.activity;

import com.example.androidui_sample_demo.R;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/*
 * 目标时间到的提醒界面
 */
public class AlertActivity extends Activity{
	
	protected Button button;
	
	@Override
	  public void onCreate(Bundle savedInstanceState) 
	  {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.alert);
	    
	    button=(Button)findViewById(R.id.brew_confirm);
	    button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//RunningActivity.mediaPlayer.stop();
				Session session=Session.getSession();
				MediaPlayer mediaPlayer=(MediaPlayer)session.get("player");
				mediaPlayer.stop();
				session.remove(mediaPlayer);
				session.cleanUpSession();
//				Intent intent=new Intent(AlertActivity.this,StartrunningActivity.class);
//				startActivity(intent);
				AlertActivity.this.finish();
				
			}
		});
	  }

}
