package com.ccp.jn.vis.sync.service;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.validation.CcpJsonFieldsValidations;
import com.jn.vis.commons.entities.VisEntityPosition;
import com.jn.vis.commons.validations.JsonFieldsValidationsVisPosition;

public class SyncServiceVisPosition {

	public CcpJsonRepresentation save(CcpJsonRepresentation json) {
		CcpJsonFieldsValidations.validate(JsonFieldsValidationsVisPosition.class, json.content, "savePosition");
		CcpJsonRepresentation position = new CcpJsonRepresentation(json);
		VisEntityPosition.INSTANCE.createOrUpdate(position);
		return position;
	}
	
	public CcpJsonRepresentation changeStatus(CcpJsonRepresentation json) {
		return json;
	}
	
	
	public CcpJsonRepresentation getData(CcpJsonRepresentation json) {
		return json;
	}

	public CcpJsonRepresentation getResumeList(CcpJsonRepresentation json) {
		return json;
	}
	

	public CcpJsonRepresentation getImportantWordsFromText(CcpJsonRepresentation json) {
		return json;
	}


	public CcpJsonRepresentation getResumeContent(CcpJsonRepresentation json) {
		return json;
	}

	public CcpJsonRepresentation sendResumesToEmail(CcpJsonRepresentation json) {
		return json;
	}

	public CcpJsonRepresentation suggestNewWords(CcpJsonRepresentation json) {
		return json;
	}

}
