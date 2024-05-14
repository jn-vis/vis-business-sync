package com.ccp.jn.vis.sync.service;

import java.util.Map;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.jn.sync.commons.JnSyncMensageriaSender;
import com.ccp.validation.CcpJsonFieldsValidations;
import com.ccp.vis.sync.validations.JsonFieldsValidationsVisResume;
import com.jn.vis.commons.entities.VisEntityResume;
import com.jn.vis.commons.utils.VisAsyncBusiness;

public class SyncServiceVisResume {

	public Map<String, Object> save(CcpJsonRepresentation resume) {

		CcpJsonFieldsValidations.validate(JsonFieldsValidationsVisResume.class, resume.content, "saveResume");

		CcpJsonRepresentation sendResultFromSaveResumeFile = JnSyncMensageriaSender.INSTANCE.send(resume, VisAsyncBusiness.saveResumeFile);

		VisEntityResume.INSTANCE.createOrUpdate(resume);
		CcpJsonRepresentation sendResultFromSaveResume = JnSyncMensageriaSender.INSTANCE.send(resume, VisAsyncBusiness.sendResumeToRecruiters);

		CcpJsonRepresentation put = CcpConstants.EMPTY_JSON
				.put("sendResumeToRecruiters", sendResultFromSaveResume)
				.put("saveResumeFile", sendResultFromSaveResumeFile)
				;
		
		return  put.content;
	}

	public Map<String, Object> delete(CcpJsonRepresentation sessionValues) {
		// TODO Auto-generated method stub
		return null;
	}

}
