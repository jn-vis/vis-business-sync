package com.ccp.jn.vis.sync.service;

import java.util.Map;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.jn.sync.business.utils.JnSyncMensageriaSender;
import com.ccp.validation.CcpJsonFieldsValidations;
import com.ccp.vis.sync.validations.JsonFieldsValidationsVisPosition;
import com.jn.vis.commons.utils.VisTopics;

public class SyncServiceVisPosition {

	public Map<String, Object> save(String email, Map<String, Object> json) {
		CcpJsonFieldsValidations.validate(JsonFieldsValidationsVisPosition.class, json);
		CcpJsonRepresentation map = new CcpJsonRepresentation(json).put("email", email);
		JnSyncMensageriaSender jnMensageria = new JnSyncMensageriaSender();
		CcpJsonRepresentation send = jnMensageria.send(map, VisTopics.savePosition);
		return send.content;
	}

}
