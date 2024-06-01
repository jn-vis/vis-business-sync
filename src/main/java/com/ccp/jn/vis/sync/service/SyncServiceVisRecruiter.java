package com.ccp.jn.vis.sync.service;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.jn.sync.mensageria.JnSyncMensageriaSender;
import com.jn.vis.commons.entities.VisEntityGroupPositionsByRecruiter;
import com.jn.vis.commons.entities.VisEntityGroupResumesOpinionsByRecruiter;
import com.jn.vis.commons.utils.VisAsyncBusiness;

public class SyncServiceVisRecruiter {
	
	private SyncServiceVisRecruiter() {}

	public static SyncServiceVisRecruiter INSTANCE = new SyncServiceVisRecruiter();
	
	public CcpJsonRepresentation sendResumesToEmail(CcpJsonRepresentation json) {

		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.send(json, VisAsyncBusiness.recruiterReceivingResumes);
		return result;
	}

	public CcpJsonRepresentation getAlreadySeenResumes(CcpJsonRepresentation json) {

		CcpJsonRepresentation oneByIdFromMirrorOrFromCache = VisEntityGroupResumesOpinionsByRecruiter.INSTANCE.getOneByIdFromMirrorOrFromCache(json);
		return oneByIdFromMirrorOrFromCache;
	}

	//FIXME CACHE QUANDO ESTIVER EM COMPUTE ENGINE
	public CcpJsonRepresentation getPositionsFromThisRecruiter(CcpJsonRepresentation json) {

		CcpJsonRepresentation oneByIdFromMirrorOrFromCache = VisEntityGroupPositionsByRecruiter.INSTANCE.getOneByIdFromMirrorOrFromCache(json);
		return oneByIdFromMirrorOrFromCache;
	}

	public CcpJsonRepresentation saveOpinionAboutThisResume(CcpJsonRepresentation json) {
		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.send(json, VisAsyncBusiness.resumeOpinionSave);
		return result;
	}

	public CcpJsonRepresentation changeOpinionAboutThisResume(CcpJsonRepresentation json) {
		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.send(json, VisAsyncBusiness.resumeOpinionChange);
		return result;
	}

}
