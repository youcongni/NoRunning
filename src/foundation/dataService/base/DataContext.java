package foundation.dataService.base;

import java.sql.SQLException;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import app.MyApp;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;



public class DataContext implements IDataContext {

	private ConnectionSource connectionSource;//数据库连接
	private SQLiteDatabase db; // 事务管理

	public DataContext() {
		connectionSource = MyApp.DATAHELPER.getConnectionSource();
		db = MyApp.DATAHELPER.getWritableDatabase();
	}

	// 增加
	public <T, ID> void add(T item, Class<T> dataClass, Class<ID> idClass)
			throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		dao.create(item);
	}

	// 删除
	public <T, ID> void delete(T item, Class<T> dataClass, Class<ID> idClass)
			throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		dao.delete(item);
	}
	
	//删除所有记录
	public <T, ID> void deleteAll(Class<T> dataClass, Class<ID> idClass)
			throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		List<T> list=dao.queryForAll();
		dao.delete(list);
	}

	// 按ID删除
	public <T, ID, K extends ID> void deleteById(ID id, Class<T> dataClass,
			Class<K> idClass) throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		dao.deleteById(id);
	}
	
	//删除BUilder
		public <T, ID> DeleteBuilder<T, ID> getDeleteBuilder(Class<T> dataClass,
				Class<ID> idClass) throws SQLException {
			Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
			return dao.deleteBuilder();
		}
		
		
		//通用删除
		public <T, ID> void delete(Class<T> dataClass, Class<ID> idClass,
				PreparedDelete<T> preparedDelete) throws SQLException {
			Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
			dao.delete(preparedDelete);
			
		}
		
	// 更新
	public <T, ID> void update(T item, Class<T> dataClass, Class<ID> idClass)
			throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		dao.update(item);
	}

	// 根据ID查询
	public <T, ID, K extends ID> T queryById(Class<T> dataClass,
			Class<K> idClass, ID id) throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		return dao.queryForId(id);
	}

	// 查询所有记录
	public <T, ID> List<T> queryForAll(Class<T> dataClass, Class<ID> idClass)
			throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		return dao.queryForAll();
	}

	// 根据SQL语句查询
	public <T, ID> List<String[]> queryBySql(Class<T> dataClass,
			Class<ID> idClass, String sql) throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		GenericRawResults<String[]> rawResults = dao.queryRaw(sql);
		List<String[]> list = rawResults.getResults();
		return list;
	}

//	// 通用查询
//	public <T, ID> List<T> query(Class<T> dataClass, Class<ID> idClass,
//			PreparedQuery<T> query) throws SQLException {
//		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
//		
//		return dao.query(query);
//	}
	// 通用查询
	public <T, ID> List<T> query(Class<T> dataClass, Class<ID> idClass,
			PreparedQuery<T> query) throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);

		return dao.query(query);
	}
	//获取dao,构造查询生成器要用到
	public <T, ID> Dao getDao(Class<T> dataClass, Class<ID> idClass) throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		return dao;
	}

	// 获取所有记录数
	public <T, ID> long countof(Class<T> dataClass, Class<ID> idClass)
			throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		return dao.countOf();
	}

	// 获取指定条件的记录数
	public <T, ID> long countof(Class<T> dataClass, Class<ID> idClass,
			PreparedQuery<T> query) throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		return dao.countOf(query);
	}

	// 获取QueryBuilder
	public <T, ID> QueryBuilder<T, ID> getQueryBuilder(Class<T> dataClass,
			Class<ID> idClass) throws SQLException {
		Dao<T, ID> dao = DaoManager.createDao(connectionSource, dataClass);
		return dao.queryBuilder();
	}

	// 开始事务
	public void beginTransaction() {
		db.beginTransaction();
	}

	// 提交事务
	public void commit() {
		db.setTransactionSuccessful();
	}

	// 回滚事务
	public void rollback() {
		db.endTransaction();
	}

	

	
}
