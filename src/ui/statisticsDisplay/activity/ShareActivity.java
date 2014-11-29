package ui.statisticsDisplay.activity;

import java.util.Date;
import java.util.Random;

import ui.statisticsDisplay.viewModel.ShareviewModel;




import com.example.androidui_sample_demo.R;

import foundation.dataService.util.DataFormat;
import foundation.dataService.util.ScreenshotTools;




import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class ShareActivity  extends Activity{
	    
	 Context mContext;

     private  TextView tv_date,tv_startTime,tv_useTime,tv_distance,tv_avgHeartRate,tv_avgSpeed,tv_calouis;
     private ShareviewModel shareViewModal;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("分享 ");
		/**初始化*/
		init();
		/**接收从历史记录界面传过来的数据*/
		Bundle bundle=this.getIntent().getExtras();
		String date=bundle.getString("Date");
		String startTime=bundle.getString("startTime");
		String useTime=bundle.getString("useTime");
		double distance=bundle.getDouble("distance");
		int avgHeartRate=bundle.getInt("avgHeartRate");
		double avgSpeed=bundle.getDouble("avgSpeed");
		double calouis=bundle.getDouble("Calouis");
		/**得到viewModal*/
		shareViewModal=getViewModel(date, startTime, useTime, distance, avgHeartRate, avgSpeed, calouis);
		/**装载viewmodal到界面*/
		loadViewModal(shareViewModal);
		
		
	    
        
        mContext=this;
      
                 
	}
	public void init(){
		tv_date=(TextView) findViewById(R.id.tv_sportDate);
		tv_startTime=(TextView) findViewById(R.id.tv_startTime);
		tv_useTime=(TextView) findViewById(R.id.tv_useTime);
		tv_distance=(TextView) findViewById(R.id.tv_distance);
		tv_avgHeartRate=(TextView) findViewById(R.id.tv_avgHeartrate);
		tv_avgSpeed=(TextView) findViewById(R.id.tv_avgSpeed);
		tv_calouis=(TextView) findViewById(R.id.tv_calouis);
		
		
	}

	public ShareviewModel getViewModel(String date, String startTime, String useTime,
			double distance, int avgHeartRate, double avgSpeed, double calouis){
		 ShareviewModel shareViewModal=new ShareviewModel(date,startTime,useTime,distance,avgHeartRate,
				 avgSpeed,calouis);
		 return shareViewModal;
	}
	public void loadViewModal(ShareviewModel shareViewModal){
		tv_date.setText(shareViewModal.getDate());
		tv_startTime.setText(shareViewModal.getStartTime());
		tv_useTime.setText(shareViewModal.getUseTime());
		tv_distance.setText(String.valueOf(shareViewModal.getDistance()));
		tv_avgHeartRate.setText(String.valueOf(shareViewModal.getAvgHeartRate()));
		tv_avgSpeed.setText(String.valueOf(DataFormat.setDataFormat(shareViewModal.getAvgSpeed(),"0.00")));
		tv_calouis.setText(String.valueOf(DataFormat.setDataFormat(shareViewModal.getCalouis(),"0.00")));
	
	}
		

		
		

	
	
	

		@Override
		
		    public boolean onCreateOptionsMenu(Menu menu)
		
		    {
		
		        MenuInflater inflater = getMenuInflater();
	
		        inflater.inflate(R.menu.menu_share, menu);
		
		        return true;
		
		    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			//this.finish();
			Intent intent = new Intent(this, HistoryActivity.class);
			            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			            startActivity(intent);
			            return true;

		case R.id.Share:
			  ScreenshotTools.takeScreenShotToEmail(mContext, ShareActivity.this);  
			break;

		}
		return super.onOptionsItemSelected(item);
	}
}
