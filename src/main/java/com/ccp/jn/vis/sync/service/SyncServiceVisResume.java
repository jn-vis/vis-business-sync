package com.ccp.jn.vis.sync.service;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.exceptions.process.CcpAsyncProcess;
import com.jn.commons.entities.JnEntityAsyncTask;
import com.jn.vis.commons.utils.VisTopics;

public class SyncServiceVisResume {
	
	public CcpJsonRepresentation save(String email, CcpJsonRepresentation json) {

		String name = VisTopics.saveResume.name();
		JnEntityAsyncTask entity = new JnEntityAsyncTask();
		CcpJsonRepresentation put = json.put("email", email);
		CcpAsyncProcess ccpAsyncProcess = new CcpAsyncProcess();
		
		CcpJsonRepresentation send = ccpAsyncProcess.send(put, name, entity);
		
		return send;
	}

}
