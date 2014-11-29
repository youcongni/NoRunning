package foundation.webservice;

public class MemoWebPara {
	/**
	 * URL为wsdl的地址
	 * 这里我使用我去年的一个比赛的一个项目做个解释
	 * 在浏览器里访问http://test.ahhxgq.com:8686/SmartMemoWeb
	 * 将会看到所有的webservice对外提供的接口，分三块是根据不同的模块划分的注意看不同模块的命名空间不同
	 * 后面有个wsdl的超链接可以点击查看wsdl描述，这个描述包含了调用相应方法时的方法名以及参数名和参数类型
	 * 还有返回值类型。xxxResponse描述的就是相应方法的返回类型信息
	 * 
	 * 下面的这个SM_URL连接是不包含后面的?wsdl的切记
	 * Namespace的连接后面有个/反斜杠不要丢了
	 */
	public static final String Model_URL = "http://icoolrunapp.duapp.com/N.O.Running/webservice/sportModelServiceImpl";
	public static final String USER_URL = "http://icoolrunapp.duapp.com/N.O.Running/webservice/userServiceImpl";
	public static final String OneSport_URL = "http://icoolrunapp.duapp.com/N.O.Running/webservice/oneSportServiceImpl";
	
	//public static final String SM_URL = "http://192.168.1.107:8080/N.O.Running/webservice/userServiceImpl";
	public static final String NAMESPACE ="http://service.fjnu.com/";
}
