package ui.community;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.androidui_sample_demo.R;

public class FriendNewsActivity extends Activity {

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend_news);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

	}	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			return true;

		}
		return super.onOptionsItemSelected(item);
	}
}