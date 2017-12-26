package jeecg.webservice;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.hippo.nky.service.webservice.WBTest;

public class WebServiceClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean(); 
		factory.setServiceClass(WBTest.class); 
		
		factory.setAddress("http://localhost:8080/framework3.3.2/webservice/mytest?wsdl");
		
		WBTest service = (WBTest) factory.create();
		
		int rt;
		try {
			rt = service.test();
			System.out.println(rt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
