package com.hippo.nky.common;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.core.util.PropertiesUtil;
/*** 
 * 消息持久化到数据库 
 *  @author longgangbai 
 */  
public abstract class MessageCustomer {
	
	private CommonService commonService;
    private static Logger logger=LogManager.getLogger(MessageProductor.class);  
    private String username=ActiveMQConnectionFactory.DEFAULT_USER;
    private String password=ActiveMQConnectionFactory.DEFAULT_PASSWORD;
    private String url=ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL;
    private String queueName = "mq_js_queue_standardlibrary";
      /*protected ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(  
              url+"?jms.prefetchPolicy.all=0&jms.redeliveryPolicy.maximumRedeliveries="+messagesExpected);*/  
    protected ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
      		ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
        
    /** 
     * 启动BrokerService进程 
     * @throws Exception 
     */  
    public void init() throws Exception{  
        BrokerService brokerService=createBroker();  
        brokerService.start();  
    }
    /*** 
     * 创建Broker服务对象 
     * @return 
     * @throws Exception 
     */  
    public BrokerService createBroker()throws Exception{  
        BrokerService  broker=new BrokerService();  
        broker.addConnector(url);  
        return broker;  
    }
    /** 
     * 接收的信息 
     * @return 
     * @throws JMSException 
     * @throws Exception 
     */  
    public int receiveMessage() throws JMSException{
    	Connection connection = null;
    	Session session = null;
    	Destination destination = null;
    	MessageConsumer consumer = null;
    	int count = -1;
    	
    	PropertiesUtil prop = new PropertiesUtil();
    	String activemqSenderFlg  = prop.readProperty(queueName);
    	if("receiver".equals(activemqSenderFlg)){
    		try {
            	connection=connectionFactory.createConnection();
                connection.start();  
                session = connection.createSession(true, Session.SESSION_TRANSACTED);
            	destination = session.createQueue(queueName);
                consumer = session.createConsumer(destination);
                count = execute(consumer);
                if (count > 0) {
                	session.commit();
                }
            } catch (Exception e) {  
                logger.debug("receiveMessage exception " + e);  
                session.rollback();
            } finally {
                if (consumer != null) {
                    consumer.close(); 
                }
                if (consumer != null) {
                	connection.close(); 
                }
            }
    	}else{
    		logger.info("消息列队："+queueName+"无接收权限。");
    	}
        
        return count;
    }
  
    /** 
     * 实现execute方法来往数据库插入数据
     * @param messagesExpected 
     * @param session 
     * @return 
     * @throws Exception 
     */  
    protected abstract int execute(MessageConsumer consumer) throws Exception;
  
    public String getPassword() {  
        return password;  
    }  
    public void setPassword(String password) {  
        this.password = password;  
    }  
    public String getUrl() {  
        return url;  
    }  
    public void setUrl(String url) {  
        this.url = url;  
    }  
    public String getUsername() {  
        return username;  
    }  
    public void setUsername(String username) {  
        this.username = username;  
    }
	public CommonService getCommonService() {
		return commonService;
	}
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
}  
