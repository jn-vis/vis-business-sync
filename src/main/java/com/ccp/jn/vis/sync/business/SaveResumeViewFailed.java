package com.ccp.jn.vis.sync.business;

import java.util.function.Function;

import com.ccp.decorators.CcpJsonRepresentation;
import com.jn.vis.commons.entities.VisEntityResumeViewFailed;

public class SaveResumeViewFailed implements Function<CcpJsonRepresentation, CcpJsonRepresentation> {

	private SaveResumeViewFailed() {}
	
	public static final SaveResumeViewFailed INSTANCE = new SaveResumeViewFailed();
	
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		String status = json.getValueFromPath("", "errorDetails", "status");
		CcpJsonRepresentation put = json.put("status", status);
		VisEntityResumeViewFailed.INSTANCE.createOrUpdate(put);
		return json;
	}

}
