package com.hippo.nky.common.utils;
import java.io.IOException;  

import org.apache.activemq.broker.ConnectionContext;  
import org.apache.activemq.store.jdbc.JDBCPersistenceAdapter;  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  

/**
 * 持久化适配器类
 * @author Administrator
 *
 */
public class BrokenPersistenceAdapter  extends JDBCPersistenceAdapter {
	private final Logger LOG = LoggerFactory.getLogger(BrokenPersistenceAdapter.class);  
	  
    private boolean shouldBreak = false;  
  
    @Override  
    public void commitTransaction(ConnectionContext context) throws IOException {  
        if ( shouldBreak ) {  
            LOG.warn("Throwing exception on purpose");  
            throw new IOException("Breaking on purpose");  
        }  
        LOG.debug("in commitTransaction");  
        super.commitTransaction(context);  
    }  
  
    public void setShouldBreak(boolean shouldBreak) {  
        this.shouldBreak = shouldBreak;  
    }  
}
