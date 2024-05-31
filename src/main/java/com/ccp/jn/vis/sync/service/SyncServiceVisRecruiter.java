package com.ccp.jn.vis.sync.service;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.crud.CcpCrud;
import com.ccp.especifications.db.crud.CcpSelectUnionAll;
import com.ccp.exceptions.process.CcpFlow;
import com.ccp.jn.sync.mensageria.JnSyncMensageriaSender;
import com.jn.vis.commons.entities.VisEntityGroupPositionsByRecruiter;
import com.jn.vis.commons.entities.VisEntityPosition;
import com.jn.vis.commons.entities.VisEntityResumeOpinion;
import com.jn.vis.commons.entities.VisEntityResumeView;
import com.jn.vis.commons.utils.VisAsyncBusiness;

public class SyncServiceVisRecruiter {
	
	private SyncServiceVisRecruiter() {}

	public static SyncServiceVisRecruiter INSTANCE = new SyncServiceVisRecruiter();
	
	public CcpJsonRepresentation sendResumesToEmail(CcpJsonRepresentation json) {

		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.send(json, VisAsyncBusiness.recruiterReceivingResumes);
		return result;
	}

	public CcpJsonRepresentation getAlreadySeenResumes(CcpJsonRepresentation json) {
		
		CcpCrud dependency = CcpDependencyInjection.getDependency(CcpCrud.class);
		
		CcpSelectUnionAll unionAll = dependency.unionAll(json, VisEntityResumeView.INSTANCE);
		
		CcpJsonRepresentation requiredEntityRow = unionAll.getRequiredEntityRow(VisEntityResumeView.INSTANCE, json);
		
		return requiredEntityRow;
	}

	//FIXME CACHE QUANDO ESTIVER EM COMPUTE ENGINE
	public CcpJsonRepresentation getPositionsFromThisRecruiter(CcpJsonRepresentation json) {

		CcpJsonRepresentation oneById = VisEntityGroupPositionsByRecruiter.INSTANCE.fromCache().getOneById(json);

		boolean isActive = VisEntityPosition.INSTANCE.fromCache().exists(json);
		
		if(isActive) {
			return oneById;
		}

		boolean isInactive = VisEntityPosition.INSTANCE.getMirrorEntity().fromCache().exists(json);
		
		if(isInactive) {
			return oneById;
		}
		
		throw new CcpFlow(json, 404);
	}
	
	public CcpJsonRepresentation saveOpinionAboutThisResume(CcpJsonRepresentation json) {
		VisEntityResumeOpinion.INSTANCE.createOrUpdate(json);
		return json;
	}

	public CcpJsonRepresentation changeOpinionAboutThisResume(CcpJsonRepresentation json) {
		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.send(json, VisAsyncBusiness.resumeOpinionChange);
		return result;
	}

}
