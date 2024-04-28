package com.ccp.jn.vis.sync.service;

import java.util.Map;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.validation.CcpJsonFieldsValidations;
import com.ccp.vis.sync.validations.JsonFieldsValidationsVisPosition;
import com.jn.vis.commons.entities.VisEntityPosition;

public class SyncServiceVisPosition {

	public Map<String, Object> save(String email, Map<String, Object> json) {
		CcpJsonFieldsValidations.validate(JsonFieldsValidationsVisPosition.class, json, "savePosition");
		CcpJsonRepresentation position = new CcpJsonRepresentation(json).put("email", email);
		VisEntityPosition.INSTANCE.createOrUpdate(position);
		return position.content;
	}

}
