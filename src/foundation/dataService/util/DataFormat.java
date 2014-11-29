package foundation.dataService.util;

import java.text.DecimalFormat;

public class DataFormat {
		//设置数据的显示格式,设为静态方法
		public static String setDataFormat(double x,String format){
			// 设置数据格式,format为要显示的数据格式,比如:0.00,0.000.......
			
			DecimalFormat df = new DecimalFormat(format);
			String num = df.format(x);
			return num;
		}
		/**
		 * 传入一个日期格式为”0000-00-00“的日期将其年份部分去掉
		 * @param date
		 * @return
		 */
		public static String setDateFormatForMonthAndDay(String date){
			String dateForMonthAndDay;
			dateForMonthAndDay=date.substring(5);
			return dateForMonthAndDay;
			
		}
}
