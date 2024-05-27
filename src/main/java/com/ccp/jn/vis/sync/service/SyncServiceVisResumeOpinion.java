package com.ccp.jn.vis.sync.service;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.jn.sync.commons.JnSyncMensageriaSender;
import com.jn.vis.commons.utils.VisAsyncBusiness;
import com.jn.vis.commons.utils.VisCommonsUtils;

public class SyncServiceVisResumeOpinion {

	public CcpJsonRepresentation getResumeContent(CcpJsonRepresentation json) {
		CcpJsonRepresentation result = VisCommonsUtils.getResume(json);
		return result;
	}

	public CcpJsonRepresentation saveOpinionAboutThisResume(CcpJsonRepresentation json) {
		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.send(json, VisAsyncBusiness.resumeSaveOpinion);
		CcpJsonRepresentation put = CcpConstants.EMPTY_JSON.put(VisAsyncBusiness.resumeSaveOpinion.name(), result);
		return put;
	}

	public CcpJsonRepresentation changeOpinionAboutThisResume(CcpJsonRepresentation json) {
		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.send(json, VisAsyncBusiness.resumeChangeOpinion);
		CcpJsonRepresentation put = CcpConstants.EMPTY_JSON.put(VisAsyncBusiness.resumeChangeOpinion.name(), result);
		return put;
	}
}
