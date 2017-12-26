package com.hippo.nky.activemq;

import java.util.List;

import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;

import com.hippo.nky.common.MessageCustomer;

public class StandardLibraryDataSynchronization extends  MessageCustomer{

	@Override
	protected int execute(MessageConsumer consumer) throws Exception {
		boolean receive = true; 
		ObjectMessage message = null;
		int count = 0;
		while (receive) {
			message = (ObjectMessage) consumer.receive(5000);
			if (null != message) {
				count = iterativeInsert(message.getObject(),count);
		    }else{
				receive = false;
			}
		}
		return count;
	}
	
	/**
	 * 迭代插入
	 * @param object
	 */
	public int iterativeInsert(Object object,int count){
		if(object != null){
			if(object instanceof List){
				for(Object subObject : (List)object){
					if(subObject != null){
						if(subObject instanceof List){
							count = iterativeInsert(subObject,count);
						}else{
							this.getCommonService().save(subObject);
							count ++;
						}	
					}
				}
			}else{
				this.getCommonService().save(object);
				count ++;
			}
		}
		return count;
	}
}
