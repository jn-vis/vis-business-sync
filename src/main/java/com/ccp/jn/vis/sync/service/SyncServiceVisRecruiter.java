package com.ccp.jn.vis.sync.service;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.jn.sync.mensageria.JnSyncMensageriaSender;
import com.jn.vis.commons.utils.VisAsyncBusiness;
import com.vis.commons.entities.VisEntityGroupPositionsByRecruiter;
import com.vis.commons.entities.VisEntityGroupResumesPerceptionsByRecruiter;

public class SyncServiceVisRecruiter {
	
	private SyncServiceVisRecruiter() {}

	public static SyncServiceVisRecruiter INSTANCE = new SyncServiceVisRecruiter();
	
	public CcpJsonRepresentation sendResumesToEmail(CcpJsonRepresentation json) {

		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.whenSendMessage(VisAsyncBusiness.recruiterReceivingResumes).apply(json);
		return result;
	}

	public CcpJsonRepresentation getAlreadySeenResumes(CcpJsonRepresentation json) {

		CcpJsonRepresentation oneByIdFromMirrorOrFromCache = VisEntityGroupResumesPerceptionsByRecruiter.INSTANCE.getOneByIdFromMirrorOrFromCache(json);
		return oneByIdFromMirrorOrFromCache;
	}

	//FIXME CACHE LOCAL NO COMPUTE ENGINE
	public CcpJsonRepresentation getPositionsFromThisRecruiter(CcpJsonRepresentation json) {

		CcpJsonRepresentation oneByIdFromMirrorOrFromCache = VisEntityGroupPositionsByRecruiter.INSTANCE.getOneByIdFromMirrorOrFromCache(json);
		return oneByIdFromMirrorOrFromCache;
	}

	public CcpJsonRepresentation saveOpinionAboutThisResume(CcpJsonRepresentation json) {
		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.whenSendMessage(VisAsyncBusiness.resumeOpinionSave).apply(json);
		return result;
	}

	public CcpJsonRepresentation changeOpinionAboutThisResume(CcpJsonRepresentation json) {
		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.whenSendMessage(VisAsyncBusiness.resumeOpinionChange).apply(json);
		return result;
	}

}
