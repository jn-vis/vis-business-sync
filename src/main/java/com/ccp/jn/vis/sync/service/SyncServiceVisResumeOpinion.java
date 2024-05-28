package com.ccp.jn.vis.sync.service;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.jn.sync.mensageria.JnSyncMensageriaSender;
import com.jn.vis.commons.utils.VisAsyncBusiness;
import com.jn.vis.commons.utils.VisCommonsUtils;

public class SyncServiceVisResumeOpinion {

	public CcpJsonRepresentation getResumeContent(CcpJsonRepresentation json) {
		CcpJsonRepresentation result = VisCommonsUtils.getResume(json);
		return result;
	}

	public CcpJsonRepresentation saveOpinionAboutThisResume(CcpJsonRepresentation json) {
		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.send(json, VisAsyncBusiness.resumeSaveOpinion);
		return result;
	}

	public CcpJsonRepresentation changeOpinionAboutThisResume(CcpJsonRepresentation json) {
		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.send(json, VisAsyncBusiness.resumeChangeOpinion);
		return result;
	}
}
