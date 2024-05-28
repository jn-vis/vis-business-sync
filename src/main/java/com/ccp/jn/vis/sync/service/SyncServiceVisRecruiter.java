package com.ccp.jn.vis.sync.service;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.crud.CcpCrud;
import com.ccp.especifications.db.crud.CcpSelectUnionAll;
import com.ccp.jn.sync.mensageria.JnSyncMensageriaSender;
import com.jn.vis.commons.entities.VisEntityPositionsByRecruiter;
import com.jn.vis.commons.entities.VisEntityResumesSeen;
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
		
		CcpSelectUnionAll unionAll = dependency.unionAll(json, VisEntityResumesSeen.INSTANCE);
		
		CcpJsonRepresentation requiredEntityRow = unionAll.getRequiredEntityRow(VisEntityResumesSeen.INSTANCE, json);
		
		return requiredEntityRow;
	}

	
	public CcpJsonRepresentation getPositionsFromThisRecruiter(CcpJsonRepresentation json) {
		CcpJsonRepresentation oneById = VisEntityPositionsByRecruiter.INSTANCE.fromCache().getOneById(json);
		return oneById;
	}
	

}
