package foundation.webservice;

public interface WebServiceDelegate {
	public void handleException(Object ex);
	public void handleResultOfWebService(String methodName,Object result);
}
