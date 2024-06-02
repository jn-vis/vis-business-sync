package com.ccp.jn.vis.sync.service;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.crud.CcpCrud;
import com.ccp.especifications.db.crud.CcpSelectUnionAll;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.jn.sync.mensageria.JnSyncMensageriaSender;
import com.jn.vis.commons.entities.VisEntityResume;
import com.jn.vis.commons.utils.VisAsyncBusiness;
import com.jn.vis.commons.utils.VisCommonsUtils;

public class SyncServiceVisResume {
	
	private SyncServiceVisResume() {}
	
	public static final SyncServiceVisResume INSTANCE = new SyncServiceVisResume();
	
	// Recebe as informações do currículo no formato de JSON 
	public CcpJsonRepresentation save(CcpJsonRepresentation resume) {
		// Realiza o envio do currículo inserido aos recrutadores
		CcpJsonRepresentation sendResultFromSaveResume = JnSyncMensageriaSender.INSTANCE.whenSendMessage(VisAsyncBusiness.resumeSave).apply(resume);
		// Guarda em forma de arquivo o currículo inserido
		CcpJsonRepresentation sendResultFromSaveResumeFile = JnSyncMensageriaSender.INSTANCE.whenSendMessage(VisAsyncBusiness.resumeBucketSave).apply(resume);
		// Remove do cache a informação equivalente ao currículo que está entrando aqui
		VisCommonsUtils.removeFromCache(resume, "text", "file");
		// Cria uma variável do tipo JSON vazia 
		CcpJsonRepresentation put = CcpConstants.EMPTY_JSON
				.putAll(sendResultFromSaveResumeFile)
				.putAll(sendResultFromSaveResume)
				;
		// Retorna essa variável de JSON com todoas as informações necessárias do currículo 
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
