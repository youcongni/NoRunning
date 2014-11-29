package foundation.ble;

import java.util.Date;
import java.util.List;

public interface ITimeout {

	public void onTimeout(Date collectTime,List<Integer> heartRates);
}
