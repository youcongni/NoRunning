package domain.systemManaConfig;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import domain.dataCollect.OneSport;
import foundation.dataService.SystemManagerDataService;

import foundation.dataService.base.DataContext;
import foundation.dataService.util.DateService;

public class SystemManageService {
	private DataContext dataContext;
	private  User currentLoginedUser;
	private Friends friend;
	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	
	


	

	public SystemManageService() {
		dataContext=new DataContext();
		
	}
//根据user表里面的islogin字段来查找当前登录的用户
	public User getCurrentLoginedUser() {
		List<User> users = null;
		try {
			/*
			 * 构造查询生成器
			 */
			QueryBuilder<User, String> queryBuilder = dataContext.getDao(
					User.class,String.class).queryBuilder();
			// 查询日期为date的所有OneSport
			queryBuilder.where().eq(User.ISLOGIN, true);//显示为true的为当前登录的用户
			// prepare our sql statement
			PreparedQuery<User> query = queryBuilder.prepare();
			users =dataContext.query(User.class,String.class, query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return users.get(0);
	}
//根据好友的email和其属于哪个用户来找到好友
	public Friends gerFriend(String email,User user){
		List<Friends> friends = null;

		try {
			/*
			 * 构造查询生成器
			 */
			QueryBuilder<Friends, Integer> queryBuilder = dataContext.getQueryBuilder(
					Friends.class, Integer.class);
			// 查询日期为date,第count次的OneSport
			queryBuilder.where().eq(Friends.EMAIL, email).and()
					.eq(Friends.ONEANDMOREUSER, user);

			// prepare our sql statement
			PreparedQuery<Friends> query = queryBuilder.prepare();
			friends = dataContext.query(Friends.class, Integer.class, query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(friends.size()!=0){
		return friends.get(0);
		}else{
		return null;
		}
	}


	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void register() {
		// begin-user-code
		// TODO 自动生成的方法存根

		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void logout() {
		// begin-user-code
		// TODO 自动生成的方法存根

		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void alter() {
		// begin-user-code
		// TODO 自动生成的方法存根

		// end-user-code
	}

	/** 
	 * <!-- begin-UML-doc -->
	 * <!-- end-UML-doc -->
	 * @generated "UML 至 Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void login() {
		// begin-user-code
		// TODO 自动生成的方法存根

		// end-user-code
	}
	/**
	 * 根据好友名或者好友备注来查找到该好友的id
	 * @param nameType
	 * @param name
	 * @return
	 */
	public int getFriendIdByName(String nameType,String name){
		String sql = null;
		if(nameType.equals("friendName")){
			sql= "select id from T_Friends where friendName='"+name+"'";
		}else if(nameType.equals("anotherName")){
			sql= "select id from T_Friends where anotherName='"+name+"'";
		}
		int id = 0;
		try {
		
			List<String[]> ID=dataContext.queryBySql(Friends.class, Integer.class,sql);
			if(ID.size()==0){
			}	
			else{
				id=Integer.parseInt(ID.get(0)[0]);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	/**
	 * 根据好友的id号删除好友
	 * @param id
	 */
	public void deleteFriendById(int id){
		try {
			dataContext.deleteById(id, Friends.class,Integer.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO 自动生成的方法存根

	}

}
