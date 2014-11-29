package ui.statisticsDisplay.activity;

import ui.statisticsDisplay.activity.achartEngine.IDemoChart;
import ui.statisticsDisplay.activity.achartEngine.LineChart;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class TestActivity extends Activity{
    private IDemoChart lineChart;
    private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		lineChart=new LineChart(this);
		View viewChart=lineChart.initView();
		setContentView(viewChart);
		context=this;
		//LinearLayout linearLayout =(LinearLayout) findViewById(R.id.line_chart);
		
	}

}
