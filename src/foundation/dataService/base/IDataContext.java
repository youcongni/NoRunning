package foundation.dataService.base;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
/**
 * 
 * @author 福建师范大学软件学院   陈贝、刘大刚
 *
 */
public interface IDataContext {

	// 增加
	public abstract <T, ID> void add(T item, Class<T> dataClass,
			Class<ID> idClass) throws SQLException;

	// 删除
	public abstract <T, ID> void delete(T item, Class<T> dataClass,
			Class<ID> idClass) throws SQLException;
	
	//通用删除
	public abstract <T,ID> void delete(Class<T> dataClass,
			Class<ID> idClass,PreparedDelete<T> preparedDelete) throws SQLException;
	
	// 更新
	public abstract <T, ID> void update(T item, Class<T> dataClass,
			Class<ID> idClass) throws SQLException;

	// 根据ID查询
	public abstract <T, ID, K extends ID> T queryById(Class<T> dataClass,
			Class<K> idClass, ID id) throws SQLException;

	// 查询全部
	public abstract <T, ID> List<T> queryForAll(Class<T> dataClass,
			Class<ID> idClass) throws SQLException;

	// 根据条件查询
	public abstract <T, ID> List<String[]> queryBySql(Class<T> dataClass,
			Class<ID> idClass, String query) throws SQLException;

	// 获取全部记录数
	public abstract <T, ID> long countof(Class<T> dataClass, Class<ID> idClass)
			throws SQLException;

	// 获取符合条件的记录数
	public abstract <T, ID> long countof(Class<T> dataClass, Class<ID> idClass,
			PreparedQuery<T> query) throws SQLException;
	
	// 按id删除记录
	public abstract <T, ID, K extends ID> void deleteById(ID id, Class<T> dataClass,
			Class<K> idClass) throws SQLException;
	
	//删除所有记录
	public abstract <T, ID> void deleteAll(Class<T> dataClass, Class<ID> idClass)
			throws SQLException ;

	/**
	 * 通用查询
	 * 
	 * @param <T>
	 * @param <ID>
	 * @param dataClass
	 * @param idClass
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public abstract <T, ID> List<T> query(Class<T> dataClass,
			Class<ID> idClass, PreparedQuery<T> query) throws SQLException;

	/**
	 * 获取QueryBuilder
	 * 
	 * @param <T>
	 * @param <ID>
	 * @param dataClass
	 * @param idClass
	 * @return
	 * @throws SQLException
	 */
	public abstract <T, ID> QueryBuilder<T, ID> getQueryBuilder(
			Class<T> dataClass, Class<ID> idClass) throws SQLException;
	/**
	 * 获取DeleteBuilder
	 * @param dataClass
	 * @param idClass
	 * @return
	 * @throws SQLException
	 */
	
	public abstract <T, ID> DeleteBuilder<T, ID> getDeleteBuilder(
			Class<T> dataClass, Class<ID> idClass) throws SQLException;

	/**
	 * 开始事务
	 */
	public abstract void beginTransaction();

	// 提交事务
	public abstract void commit();

	// 回滚事务
	public abstract void rollback();

}