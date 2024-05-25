package com.ccp.jn.vis.sync.service;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.crud.CcpCrud;
import com.ccp.especifications.db.crud.CcpSelectUnionAll;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.jn.sync.commons.JnSyncMensageriaSender;
import com.ccp.validation.CcpJsonFieldsValidations;
import com.jn.vis.commons.entities.VisEntityResume;
import com.jn.vis.commons.utils.VisAsyncBusiness;
import com.jn.vis.commons.utils.VisCommonsUtils;
import com.jn.vis.commons.validations.JsonFieldsValidationsVisResume;

public class SyncServiceVisResume {
	

	public CcpJsonRepresentation save(CcpJsonRepresentation resume) {

		CcpJsonFieldsValidations.validate(JsonFieldsValidationsVisResume.class, resume.content, "saveResume");
	
		VisCommonsUtils.removeFromCache(resume, "text", "file");

		CcpJsonRepresentation sendResultFromSaveResume = JnSyncMensageriaSender.INSTANCE.send(resume, VisAsyncBusiness.sendResumeToRecruiters);

		CcpJsonRepresentation sendResultFromSaveResumeFile = JnSyncMensageriaSender.INSTANCE.send(resume, VisAsyncBusiness.saveResumeFile);

		CcpJsonRepresentation put = CcpConstants.EMPTY_JSON
				.put("sendResumeToRecruiters", sendResultFromSaveResume)
				.put("saveResumeFile", sendResultFromSaveResumeFile)
				;
		
		return  put;
	}

	public CcpJsonRepresentation delete(CcpJsonRepresentation sessionValues) {

		VisCommonsUtils.removeFromCache(sessionValues, "text", "file");
		
		CcpJsonRepresentation deleteResume = JnSyncMensageriaSender.INSTANCE.send(sessionValues, VisAsyncBusiness.deleteResume);

		CcpJsonRepresentation put = CcpConstants.EMPTY_JSON.put("deleteResume", deleteResume);

		return put;
	}

	public CcpJsonRepresentation changeStatus(CcpJsonRepresentation sessionValues) {

		VisCommonsUtils.removeFromCache(sessionValues, "text", "file");

		CcpJsonRepresentation changeResumeStatus = JnSyncMensageriaSender.INSTANCE.send(sessionValues, VisAsyncBusiness.changeResumeStatus);

		CcpJsonRepresentation put = CcpConstants.EMPTY_JSON.put("changeResumeStatus", changeResumeStatus);

		return put;
	}
	
	
	public CcpJsonRepresentation getData(CcpJsonRepresentation sessionValues) {
		
		CcpCrud crud = CcpDependencyInjection.getDependency(CcpCrud.class);
		
		CcpEntity mirrorEntity = VisEntityResume.INSTANCE.getMirrorEntity();
		CcpSelectUnionAll unionAll = crud.unionAll(sessionValues, VisEntityResume.INSTANCE, mirrorEntity);
		
		boolean activeResume = VisEntityResume.INSTANCE.isPresentInThisUnionAll(unionAll, sessionValues);
		
		if(activeResume) {
			CcpJsonRepresentation requiredEntityRow = unionAll.getRequiredEntityRow(VisEntityResume.INSTANCE, sessionValues);
			CcpJsonRepresentation put = requiredEntityRow.put("activeResume", true);
			return put;
		}
		
		CcpJsonRepresentation requiredEntityRow = unionAll.getRequiredEntityRow(mirrorEntity, sessionValues);
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
