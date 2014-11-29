package foundation.ble;

import java.util.ArrayList;
import java.util.List;

public class HeartList {
	private List<Integer> heartRates=new ArrayList<Integer>();
	
	public List<Integer> getHeartRates() {
		return heartRates;
	}
	//往心率集合里面添加心率数据	
	public synchronized void add(Integer heartRate){
		heartRates.add(heartRate);
	}
	//清楚心率集合的数据
	public synchronized void clear(){
		heartRates.clear();
	}
	
	
}
