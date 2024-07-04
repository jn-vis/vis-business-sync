package com.ccp.jn.vis.sync.service;

import java.util.function.Function;

import com.ccp.constantes.CcpConstants;
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

		Function<CcpJsonRepresentation, CcpJsonRepresentation> whenSendMessage = JnSyncMensageriaSender.INSTANCE
				.whenSendMessage(VisAsyncBusiness.resumeSave);
		CcpJsonRepresentation sendResultFromSaveResume = whenSendMessage.apply(resume);

		CcpJsonRepresentation sendResultFromSaveResumeFile = JnSyncMensageriaSender.INSTANCE
				.whenSendMessage(VisAsyncBusiness.resumeBucketSave).apply(resume);

		VisCommonsUtils.removeFromCache(resume, "text", "file");

		CcpJsonRepresentation put = CcpConstants.EMPTY_JSON
				.putAll(sendResultFromSaveResumeFile)
				.putAll(sendResultFromSaveResume)
				;
		return  put;
	}

	public CcpJsonRepresentation delete(CcpJsonRepresentation sessionValues) {

		VisCommonsUtils.removeFromCache(sessionValues, "text", "file");
		
		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.whenSendMessage(VisAsyncBusiness.resumeDelete).apply(sessionValues);

		return result;
	}

	public CcpJsonRepresentation changeStatus(CcpJsonRepresentation sessionValues) {

		VisCommonsUtils.removeFromCache(sessionValues, "text", "file");

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
	public CcpJsonRepresentation getResumeFile(CcpJsonRepresentation sessionValues) {
		
		String contentType = sessionValues.getAsString("contentType");
		String email = sessionValues.getAsString("email");
		
		
		String resumeContent = VisCommonsUtils.getResumeContent(email, contentType);
		
		CcpJsonRepresentation put = CcpConstants.EMPTY_JSON
				.put("content", resumeContent)
				.put("type", contentType);
		return put;
	}


}
