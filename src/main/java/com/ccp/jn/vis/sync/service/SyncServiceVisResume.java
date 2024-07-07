package com.ccp.jn.vis.sync.service;

import java.util.function.Function;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.crud.CcpCrud;
import com.ccp.especifications.db.crud.CcpSelectUnionAll;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.jn.sync.mensageria.JnSyncMensageriaSender;
import com.jn.vis.commons.utils.VisAsyncBusiness;
import com.jn.vis.commons.utils.VisCommonsUtils;
import com.vis.commons.entities.VisEntityResume;

public class SyncServiceVisResume {
	
	private SyncServiceVisResume() {}
	
	public static final SyncServiceVisResume INSTANCE = new SyncServiceVisResume();
	
	public CcpJsonRepresentation save(CcpJsonRepresentation resume) {

		Function<CcpJsonRepresentation, CcpJsonRepresentation> resumeBucketSave =
				JnSyncMensageriaSender.INSTANCE.whenSendMessage(VisAsyncBusiness.resume);

		CcpJsonRepresentation sendResultFromSaveResumeFile = resumeBucketSave.apply(resume);

		return  sendResultFromSaveResumeFile;
	}

	public CcpJsonRepresentation delete(CcpJsonRepresentation sessionValues) {

		CcpJsonRepresentation result = JnSyncMensageriaSender
				.INSTANCE.whenSendMessage(VisAsyncBusiness.resumeDelete).apply(sessionValues);

		return result;
	}

	public CcpJsonRepresentation changeStatus(CcpJsonRepresentation sessionValues) {

		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.whenSendMessage(VisAsyncBusiness.resumeStatusChange).apply(sessionValues);

		return result;
	}
	
	
	public CcpJsonRepresentation getData(CcpJsonRepresentation sessionValues) {
		
		CcpCrud crud = CcpDependencyInjection.getDependency(CcpCrud.class);
		
		CcpEntity mirrorEntity = VisEntityResume.INSTANCE.getMirrorEntity();
		CcpSelectUnionAll searchResults = crud.unionAll(sessionValues, VisEntityResume.INSTANCE, mirrorEntity);
		
		boolean activeResume = VisEntityResume.INSTANCE.isPresentInThisUnionAll(searchResults, sessionValues);
		
		if(activeResume) {
			CcpJsonRepresentation requiredEntityRow = VisEntityResume.INSTANCE.getRequiredEntityRow(searchResults, sessionValues);
			CcpJsonRepresentation put = requiredEntityRow.put("activeResume", true);
			return put;
		}
		
		CcpJsonRepresentation requiredEntityRow = mirrorEntity.getRequiredEntityRow(searchResults, sessionValues);
		CcpJsonRepresentation put = requiredEntityRow.put("activeResume", false);
		return put;
	}
	public CcpJsonRepresentation getResumeFile(CcpJsonRepresentation json) {
		
		CcpJsonRepresentation resume = VisCommonsUtils.getResumeFromBucket(json);

		return resume;
	}
}
