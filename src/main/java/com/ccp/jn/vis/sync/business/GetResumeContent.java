package com.ccp.jn.vis.sync.business;

import java.util.function.Function;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.jn.sync.mensageria.JnSyncMensageriaSender;
import com.jn.vis.commons.utils.VisAsyncBusiness;
import com.jn.vis.commons.utils.VisCommonsUtils;

public class GetResumeContent implements Function<CcpJsonRepresentation, CcpJsonRepresentation>{
	
	private GetResumeContent() {}
	
	public static final GetResumeContent INSTANCE = new GetResumeContent();
	
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		JnSyncMensageriaSender.INSTANCE.send(json, VisAsyncBusiness.resumeViewAdd);
		String contentType = json.getAsString("contentType");
		String email = json.getAsString("email");
		String resumeContent = VisCommonsUtils.getResumeContent(email, contentType);
		CcpJsonRepresentation put = json.put("resumeContent", resumeContent);
		return put;
	}

}
