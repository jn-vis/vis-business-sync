package com.ccp.jn.vis.sync.service;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.exceptions.process.CcpAsyncProcess;
import com.jn.commons.entities.JnEntityAsyncTask;
import com.jn.vis.commons.utils.VisTopics;

public class SyncServiceVisPosition {

	public CcpJsonRepresentation save(String email,CcpJsonRepresentation json) {
		CcpJsonRepresentation send = new CcpAsyncProcess().send(json.put("email", email), VisTopics.savePosition.name(), new JnEntityAsyncTask());
		return send;
	}
	
}
