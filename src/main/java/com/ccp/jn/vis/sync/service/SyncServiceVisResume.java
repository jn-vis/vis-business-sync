package com.ccp.jn.vis.sync.service;

import java.util.Map;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.jn.sync.business.utils.JnSyncMensageriaSender;
import com.ccp.validation.CcpJsonFieldsValidations;
import com.ccp.vis.sync.validations.JsonFieldsValidationsVisResume;
import com.jn.vis.commons.utils.VisTopics;

public class SyncServiceVisResume {
	
	public Map<String, Object> save(String email, Map<String, Object> json) {

		CcpJsonFieldsValidations.validate(JsonFieldsValidationsVisResume.class, json);
		CcpJsonRepresentation resume = new CcpJsonRepresentation(json).put("email", email);
		JnSyncMensageriaSender ccpAsyncProcess = new JnSyncMensageriaSender();

		CcpJsonRepresentation sendResultFromSaveResumeFile = ccpAsyncProcess.send(resume, VisTopics.saveResumeFile);
		CcpJsonRepresentation sendResultFromSaveResume = ccpAsyncProcess.send(resume, VisTopics.saveResume);
		
		CcpJsonRepresentation put = CcpConstants.EMPTY_JSON
				.put("saveResumeFile", sendResultFromSaveResumeFile)
				.put("saveResume", sendResultFromSaveResume);
		
		return  put.content;
	}

}
