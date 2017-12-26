package com.hippo.nky.common;
import java.io.File;
import java.io.Serializable;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.sql.DataSource;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.store.jdbc.adapter.OracleJDBCAdapter;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.PropertiesUtil;

import com.hippo.nky.common.utils.BrokenPersistenceAdapter;

/** 
 * 消息持久化到数据库 
 * @author longgangbai 
 * 
 */  
public class MessageProductor {
      private static Logger logger=LogManager.getLogger(MessageProductor.class);  
      private String username=ActiveMQConnectionFactory.DEFAULT_USER;
      private String password=ActiveMQConnectionFactory.DEFAULT_PASSWORD;
      private String url=ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL;
      private String queueName="mq_js_queue_standardlibrary";
      private BrokerService brokerService;  
      /*protected ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(  
                "tcp://localhost:61617?jms.prefetchPolicy.all=0&jms.redeliveryPolicy.maximumRedeliveries="+messagesExpected);*/
      protected ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
      		ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
 
    /** 
     * 启动BrokerService进程 
     * @throws Exception 
     */  
    public void init() throws Exception{  
        createBrokerService();  
        brokerService.start();  
    }  
    public BrokerService createBrokerService() throws Exception{  
        if(brokerService==null){  
            brokerService=createBroker();  
        }  
        return brokerService;  
    } 
    
    /*** 
     * 创建Broker服务对象 
     * @return 
     * @throws Exception 
     */  
    public BrokerService createBroker()throws Exception{  
            BrokerService  broker=new BrokerService();  
            BrokenPersistenceAdapter jdbc=createBrokenPersistenceAdapter();  
            broker.setPersistenceAdapter(jdbc);  
            jdbc.setDataDirectory(System.getProperty("user.dir")+File.separator+"data"+File.separator);  
            //jdbc.setAdapter(new MySqlJDBCAdapter());
            jdbc.setAdapter(new OracleJDBCAdapter());
            broker.setPersistent(true);  
            broker.addConnector("tcp://localhost:61617");  
            //broker.addConnector(ActiveMQConnectionFactory.DEFAULT_BROKER_BIND_URL);  
        return broker;  
    } 
    
    /** 
     * 创建Broken的持久化适配器 
     * @return 
     * @throws Exception 
     */  
    public BrokenPersistenceAdapter createBrokenPersistenceAdapter() throws Exception{  
        BrokenPersistenceAdapter jdbc=new BrokenPersistenceAdapter();  
        DataSource datasource=createDataSource();  
        jdbc.setDataSource(datasource);  
        jdbc.setUseDatabaseLock(false);  
        //jdbc.deleteAllMessages();  
        return jdbc;  
    } 
    
    /** 
     * 创建数据源 
     * @return 
     * @throws Exception 
     */  
    public DataSource createDataSource() throws Exception{
        Properties props=new Properties();  
        /*props.put("driverClassName", "com.mysql.jdbc.Driver");  
        props.put("url", "jdbc:mysql://localhost:3306/activemq");  
        props.put("username", "root");  
        props.put("password", "root");  */
        props.put("driverClassName", "oracle.jdbc.driver.OracleDriver");
        props.put("url", "jdbc:oracle:thin:@192.168.100.129:1521:orcl");  
        props.put("username", "activemq");  
        props.put("password", "activemq"); 
        DataSource datasource=BasicDataSourceFactory.createDataSource(props);  
        return datasource;  
    }
      
    public void sendMessage(Serializable object) throws JMSException{
    	PropertiesUtil prop = new PropertiesUtil();
    	String activemqSenderFlg  = prop.readProperty(queueName);
    	if("sender".equals(activemqSenderFlg)){
            Connection connection=connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queueName);
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            producer.send(session.createObjectMessage(object));
            logger.info("发送消息[消息列队："+queueName+"]"+"[发送条数："+1+"]"); 
            connection.close();
    	}else{
    		logger.info("消息列队："+queueName+"无发送权限。");
    	}
    }
    
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
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}  
}  
