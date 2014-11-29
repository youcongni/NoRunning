package ui.statisticsDisplay.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ui.statisticsDisplay.activity.MainActivity;
import ui.statisticsDisplay.activity.achartEngine.ComprehensiveChart;
import ui.statisticsDisplay.activity.achartEngine.HeartRareChart;
import ui.statisticsDisplay.activity.achartEngine.IDemoChart;
import ui.statisticsDisplay.activity.achartEngine.SpeedControlChart;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.androidui_sample_demo.R;

import foundation.dataService.util.ListSort;
import foundation.webservice.help.MinuteSportData;
import foundation.webservice.help.Model;
import foundation.webservice.help.OneSport;
import foundation.webservice.help.RungeKutta;

public class RunningSchemeFragment extends Fragment {
	private IDemoChart chart;
	private Spinner sp_select_time;
	private Context context;
	private TextView txt_speed_control, txt_heartrate_chart, txt_comprehensive,
			txt_title;
	private LinearLayout line_chart;
	private double[] speed,speedCopy;
	private double[] heartRate,heartRateCopy;
	private double[] hsData;
	private double[][] comprehensive;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		context = (MainActivity) getActivity();
		Bundle bundle = this.getArguments();
		String s=null;
		s = bundle.getString("model");// 接受menuFragment信息
		Toast.makeText(getActivity(), "方案更新中...", Toast.LENGTH_SHORT).show();
		if (!s.equals("null")) {
			Model model = JSON.parseObject(s, Model.class);
			List<Double> encodes = new ArrayList<Double>();
			encodes.add(model.getParameter().getA1());
			encodes.add(model.getParameter().getA2());
			encodes.add(model.getParameter().getA3());
			encodes.add(model.getParameter().getA4());
			encodes.add(model.getParameter().getA5());
			speed = new double[model.getSchemes().size()+1];
			speedCopy = new double[speed.length];
			speed[0]=0;
			speedCopy[0]=0;
			for (int i = 0; i < speed.length-1; i++) {
				speed[i+1] = model.getSchemes().get(i).getBestSpeed();
				speedCopy[i+1] = speed[i+1];
			}
			heartRate = RungeKutta.calcuFitness(encodes, speed);
			heartRateCopy = new double[heartRate.length];
			for (int i = 0; i < heartRate.length; i++) {
				heartRate[i] += 90;
				heartRateCopy[i] = heartRate[i];
			}
			calModel();
			Toast.makeText(getActivity(), "加载成功", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
			speed = new double[] { 0 };
			heartRate = new double[] { 0};
			hsData = new double[]{0};
		}
	}
	public void calModel(){
		for (int i = speed.length - 1; i > 0; --i) {
			for (int j = 0; j < i; ++j) {
				if (speedCopy[j + 1] < speedCopy[j]) {
					double temp = speedCopy[j];
					speedCopy[j] = speedCopy[j + 1];
					speedCopy[j + 1] = temp;
					double temp1 =heartRateCopy[j];
					heartRateCopy[j]=heartRateCopy[j+1];
					heartRateCopy[j+1]=temp1;
				}
			}
		}
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_runningscheme, null);
		getActivity().setTitle("运动方案");
		initView(view);
		return view;
	}

	public void initView(View view) {
		txt_speed_control = (TextView) view
				.findViewById(R.id.txt_speed_control);
		txt_heartrate_chart = (TextView) view
				.findViewById(R.id.txt_heartrate_chart);
		txt_comprehensive = (TextView) view
				.findViewById(R.id.txt_comprehensive);
		txt_speed_control.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				line_chart.removeAllViews();
				txt_speed_control.setBackgroundDrawable(context.getResources()
						.getDrawable(R.drawable.text_bg_press));
				txt_heartrate_chart.setBackgroundDrawable(context
						.getResources().getDrawable(R.drawable.text_bg));
				txt_comprehensive.setBackgroundDrawable(context.getResources()
						.getDrawable(R.drawable.text_bg));
				txt_title.setText("速度控制图");
				chart = new SpeedControlChart(context);

				View viewChart = chart.initView(speed);
				line_chart.addView(viewChart);
			}
		});
		txt_heartrate_chart.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				line_chart.removeAllViews();
				txt_speed_control.setBackgroundDrawable(context.getResources()
						.getDrawable(R.drawable.text_bg));
				txt_heartrate_chart.setBackgroundDrawable(context
						.getResources().getDrawable(R.drawable.text_bg_press));
				txt_comprehensive.setBackgroundDrawable(context.getResources()
						.getDrawable(R.drawable.text_bg));
				txt_title.setText("心率图");
				chart = new HeartRareChart(context);
				View viewChart = chart.initView(heartRate);
				line_chart.addView(viewChart);
			}
		});

		txt_comprehensive.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				line_chart.removeAllViews();
				txt_speed_control.setBackgroundDrawable(context.getResources()
						.getDrawable(R.drawable.text_bg));
				txt_heartrate_chart.setBackgroundDrawable(context
						.getResources().getDrawable(R.drawable.text_bg));
				txt_comprehensive.setBackgroundDrawable(context.getResources()
						.getDrawable(R.drawable.text_bg_press));
				txt_title.setText("综合分析");
				chart = new ComprehensiveChart(context);
				View viewChart = chart.initView(heartRateCopy,speedCopy);
				line_chart.addView(viewChart);
			}
		});

		txt_title = (TextView) view.findViewById(R.id.txt_title);
		// // sp_select_time=(Spinner) view.findViewById(R.id.sp_select_time);
		String[] mItems = getResources().getStringArray(
				R.array.select_time_array);
		@SuppressWarnings("unused")
		ArrayAdapter<String> adapt_select_time = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, mItems);
		// sp_select_time.setAdapter(adapt_select_time);
		line_chart = (LinearLayout) view.findViewById(R.id.line_chart);
		chart = new SpeedControlChart(context);
		View viewChart = chart.initView(speed);
		line_chart.addView(viewChart);
	}

//	@Override
//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//		menu.clear();
//		inflater = getActivity().getMenuInflater();
//		inflater.inflate(R.menu.menu_main, menu);
//		super.onCreateOptionsMenu(menu, inflater);
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case R.id.setting:
//			Intent intent = new Intent(getActivity(), SettingActivity.class);
//			startActivity(intent);
//			RunningSchemeFragment.this.onDestroy();
//			break;
//		}
//		return super.onOptionsItemSelected(item);
//	}
}
