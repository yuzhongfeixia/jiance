package com.hippo.nky.service.impl.webservice.pad;

import javax.jws.WebService;

import com.hippo.nky.service.webservice.WBTest;

@WebService(endpointInterface = "com.hippo.nky.service.webservice.WBTest")  
public class WBTestImpl implements WBTest {

	@Override
	public int test() {
		return 0;
	}

}
