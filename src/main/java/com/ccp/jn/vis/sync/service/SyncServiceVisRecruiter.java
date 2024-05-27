package com.ccp.jn.vis.sync.service;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.crud.CcpCrud;
import com.ccp.especifications.db.crud.CcpSelectUnionAll;
import com.ccp.jn.sync.commons.JnSyncMensageriaSender;
import com.jn.vis.commons.entities.VisEntityPositionsByRecruiter;
import com.jn.vis.commons.entities.VisEntityResumesSeen;
import com.jn.vis.commons.utils.VisAsyncBusiness;

public class SyncServiceVisRecruiter {
	
	private SyncServiceVisRecruiter() {}

	public static SyncServiceVisRecruiter INSTANCE = new SyncServiceVisRecruiter();
	
	public CcpJsonRepresentation sendResumesToEmail(CcpJsonRepresentation json) {

		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.send(json, VisAsyncBusiness.sendResumesToEmails);
		CcpJsonRepresentation put = CcpConstants.EMPTY_JSON.put(VisAsyncBusiness.sendResumesToEmails.name(), result);
		return put;
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
