package ui.statisticsDisplay.viewModel;

import java.util.List;

import com.example.androidui_sample_demo.R;

import domain.statisticsDisplay.RunningRecord;











import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RunningRecordAdapt  extends BaseAdapter {
	private List<RunningRecord> record_list;
	private Context context;
    private RelativeLayout item;
    ChildView childView = null;
	public RunningRecordAdapt(Context context, List<RunningRecord> record_list) {
		this.record_list = record_list;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return (record_list == null) ? 0 : record_list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return record_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public class ChildView {
		TextView txt_date;
		TextView txt_run;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final RunningRecord record = (RunningRecord) getItem(position);
		Log.d("MyBaseAdapter", "ÐÂ½¨convertView,position=" + position);
		convertView = LayoutInflater.from(context).inflate(R.layout.list_personal_data_item,
				null);

		childView = new ChildView();
		
		childView.txt_run = (TextView) convertView
				.findViewById(R.id.txt_run);
		childView.txt_date = (TextView) convertView.findViewById(R.id.txt_date);
		item=(RelativeLayout)convertView.findViewById(R.id.rl_record_item);
		convertView.setTag(childView);
				
		childView.txt_date.setText(record.getStart_time());
		childView.txt_run.setText(record.getTotal_running());
		
		return convertView;
	}

}
