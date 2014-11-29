package foundation.dataService.base;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.androidui_sample_demo.R;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.DatabaseTableConfigLoader;
import com.j256.ormlite.table.TableUtils;

/**
 * 
 * @author 福建师范大学软件学院   陈贝、刘大刚
 * 
 */
public class DataHelper extends OrmLiteSqliteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private Context context;

	public DataHelper(Context context,String dataFileName) {
		super(context, dataFileName, null, DATABASE_VERSION,
				R.raw.ormlite_config);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		// create database
		this.createTable(db, connectionSource);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int arg2, int arg3) {
		// update database
		this.updateTable(db, connectionSource);

	}

	@Override
	public void onOpen(SQLiteDatabase db) {

		super.onOpen(db);
		Log.d("DataHelper", "database is opened");
	}

	@Override
	public void close() {
		super.close();
		Log.d("DataHelper", "database is closed");

	}

	private void createTable(SQLiteDatabase db,ConnectionSource connectionSource) {

		try {
			Log.d("DataHelper", "create database");
			InputStream is = this.context.getResources().openRawResource(
					R.raw.ormlite_config);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader reader = new BufferedReader(isr, 4096);
			List<DatabaseTableConfig<?>> tableConfigs = DatabaseTableConfigLoader
					.loadDatabaseConfigFromReader(reader);
			for (DatabaseTableConfig<?> databaseTableConfig : tableConfigs) {
				TableUtils.createTableIfNotExists(connectionSource,
						databaseTableConfig);
			}
			is.close();
			isr.close();
			reader.close();

		} catch (Exception e) {
			Log.e(DataHelper.class.getName(), "创建数据库失败" + e.getCause());
			e.printStackTrace();
		}
	}

	private void updateTable(SQLiteDatabase db, ConnectionSource connectionSource) {

		try {
			Log.d("DataHelper", "Update Database");
			InputStream is = this.context.getResources().openRawResource(
					R.raw.ormlite_config);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader reader = new BufferedReader(isr, 4096);
			List<DatabaseTableConfig<?>> tableConfigs = DatabaseTableConfigLoader
					.loadDatabaseConfigFromReader(reader);

			for (DatabaseTableConfig<?> databaseTableConfig : tableConfigs) {
				TableUtils.dropTable(connectionSource, databaseTableConfig,
						true);
			}
			is.close();
			isr.close();
			reader.close();

			onCreate(db, connectionSource);
		} catch (Exception e) {
			Log.e(DataHelper.class.getName(), "更新数据库失败" + e.getMessage());
			e.printStackTrace();
		}
	}

}