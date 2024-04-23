package com.ccp.jn.vis.sync.service;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.jn.sync.business.utils.JnSyncMensageriaSender;
import com.jn.vis.commons.utils.VisTopics;

public class SyncServiceVisPosition {

	public CcpJsonRepresentation save(String email,CcpJsonRepresentation json) {
		JnSyncMensageriaSender jnMensageria = new JnSyncMensageriaSender();
		CcpJsonRepresentation put = json.put("email", email);
		CcpJsonRepresentation send = jnMensageria.send(put, VisTopics.savePosition);
		return send;
	}
	
}
