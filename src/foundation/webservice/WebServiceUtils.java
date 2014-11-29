package foundation.webservice;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.AttributeInfo;
import org.ksoap2.serialization.Marshal;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

public class WebServiceUtils {

	private final String namespace;
	private final String serviceURL;
	private final WebServiceDelegate delegate;

	public WebServiceUtils(String namespace, String serviceURL,
			WebServiceDelegate delegate) {
		super();
		this.namespace = namespace;
		this.serviceURL = serviceURL;
		this.delegate = delegate;
	}

	/**
	 * 
	 * @param namespace
	 * @param serviceURL
	 * @param args
	 * @param methodName
	 * @param resultType
	 */
	public <T> void callWebService(final String methodName,
			final HashMap<String, Object> args, final Class<T> resultType) {
		new AsyncTask<Void, Void, Object>() {

			@Override
			protected Object doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				return call(methodName, args, resultType);
			}

			@Override
			protected void onPostExecute(Object result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if (result instanceof Exception)
					delegate.handleException(result);
				else
					delegate.handleResultOfWebService(methodName, result);
			}

		}.execute();
	}

	private <T> Object call(String methodName, HashMap<String, Object> args,
			Class<T> resultType) {
		Object object = null;

		SoapObject requestSoapObject = new SoapObject(namespace, methodName);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapSerializationEnvelope.VER10); 
//		if(methodName.equals("test")){
//			UserHelper user=new UserHelper();
//			user=(UserHelper)args.get("userhelper");
//			PropertyInfo piInfo=new PropertyInfo();
//			piInfo.setName("userhelper");
//			piInfo.setValue(user);
//			piInfo.setType(user.getClass());
//			requestSoapObject.addProperty(piInfo);			
//		}
//		else{		
			if (args != null) {
				Set<String> set = args.keySet();
				for (String key : set) {
					requestSoapObject.addProperty(key, args.get(key));
				}
			}
//		}
		/**
		 * if the call methodName include some other type like byte[] and soon must 
		 * register for your self ,it not good 
		 */
		if (methodName.equals("uploadMemoDBFile")) {
			Marshal byteMarshal = new MarshalBase64();
			byteMarshal.register(envelope);
		}
		envelope.implicitTypes = true;
		envelope.dotNet = false;
		envelope.bodyOut = requestSoapObject;

		HttpTransportSE httpTransportSE = new HttpTransportSE(serviceURL);
		httpTransportSE.debug = true;
		try {
			httpTransportSE.call(null, envelope);
			Object retObj = envelope.getResponse();

			if (retObj instanceof SoapFault) {
				SoapFault fault = (SoapFault) retObj;
				Exception ex = new Exception(fault.faultstring);
				delegate.handleException(ex);
				System.out.println(ex);
			} else if (retObj instanceof SoapPrimitive) {
				SoapPrimitive soapPrimitive = (SoapPrimitive) retObj;
				object = unmarshalSoapPimitiveResponse(retObj, resultType);
				return object;
			} else if (retObj instanceof Vector) {
				object = (Object) retObj;
				return object;
			} else {
				SoapObject soapObject = (SoapObject) retObj;

				object = unmarshalSoapObjeResponse(soapObject, resultType);
				return object;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return e;

		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return e;
		}
		return null;
	}

	private static <T> Object unmarshalSoapObjeResponse(SoapObject soapObject,
			Class<T> resultType) {
		return soapObject;
	}

	@SuppressLint("SimpleDateFormat")
	private static <T> Object unmarshalSoapPimitiveResponse(Object retObj,
			Class<T> resultType) {
		Object object = null;
		SoapPrimitive j = (SoapPrimitive) retObj;
		try {
			if (resultType == int.class) {
				object = Integer.valueOf(j.toString());
			} else if (resultType == long.class) {
				object = Long.parseLong(j.toString());
			} else if (resultType == float.class) {
				object = Float.parseFloat(j.toString());
			} else if (resultType == double.class) {
				object = Double.parseDouble(j.toString());
			} else if (resultType == boolean.class) {
				object = Boolean.parseBoolean(j.toString());
			} else if (resultType == String.class) {
				object = j.toString();
			} else if (resultType == Date.class) {
				object = (new SimpleDateFormat("yyyy-MM-dd")).parse(j
						.toString());
			}else if(resultType == byte[].class){
				object = j.toString();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return object;
	}
}
