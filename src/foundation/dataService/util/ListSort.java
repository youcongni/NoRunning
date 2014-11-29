/**
 * 
 */
package foundation.dataService.util;

import java.util.Comparator;

import foundation.webservice.help.MinuteSportData;

/**
 * @author ÁÖÇï
 * 
 */
public class ListSort implements Comparator {

	public int compare(Object o1, Object o2) {
		MinuteSportData m1 = (MinuteSportData) o1;
		MinuteSportData m2 = (MinuteSportData) o2;
		if (m1.getSpeed()>m2.getSpeed())
			return 1;
		else if(m1.getSpeed()<m2.getSpeed()){
			return -1;
		}
		else{
			return 0;
		}
	}
}
