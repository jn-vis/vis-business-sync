package com.ccp.jn.vis.sync.service;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.exceptions.process.CcpAsyncProcess;
import com.jn.commons.entities.JnEntityAsyncTask;
import com.jn.vis.commons.utils.VisTopics;

public class SyncServiceVisResume {
	public CcpJsonRepresentation save(String email, CcpJsonRepresentation json) {
		CcpJsonRepresentation send = new CcpAsyncProcess().send(json.put("email", email), VisTopics.saveResume.name(), new JnEntityAsyncTask());
		return send;
	}

}
