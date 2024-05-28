package com.ccp.jn.vis.sync.service;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.jn.sync.mensageria.JnSyncMensageriaSender;
import com.ccp.validation.CcpJsonFieldsValidations;
import com.jn.vis.commons.entities.VisEntityPosition;
import com.jn.vis.commons.entities.VisEntityPositionResumes;
import com.jn.vis.commons.entities.VisEntitySkill;
import com.jn.vis.commons.utils.VisAsyncBusiness;
import com.jn.vis.commons.utils.VisCommonsUtils;
import com.jn.vis.commons.validations.JsonFieldsValidationsVisPosition;

public class SyncServiceVisPosition {

	public CcpJsonRepresentation save(CcpJsonRepresentation json) {
		CcpJsonFieldsValidations.validate(JsonFieldsValidationsVisPosition.class, json.content, "savePosition");
		CcpJsonRepresentation position = new CcpJsonRepresentation(json);
		CcpJsonRepresentation createOrUpdate = VisEntityPosition.INSTANCE.createOrUpdate(position);

		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.send(createOrUpdate, VisAsyncBusiness.positionListingResumes);
		return result;
		
	}
	
	public CcpJsonRepresentation changeStatus(CcpJsonRepresentation json) {

		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.send(json, VisAsyncBusiness.positionChangeStatus);
		return result;
	}
	
	
	public CcpJsonRepresentation getData(CcpJsonRepresentation json) {
		CcpJsonRepresentation oneById = VisEntityPositionResumes.INSTANCE.getOneById(json);
		return oneById;
	}

	public CcpJsonRepresentation getResumeList(CcpJsonRepresentation json) {
		CcpJsonRepresentation oneById = VisEntityPositionResumes.INSTANCE.fromCache().getOneById(json);
		return oneById;
	}
	

	public CcpJsonRepresentation getImportantSkillsFromText(CcpJsonRepresentation json) {
		CcpJsonRepresentation oneById = VisEntitySkill.INSTANCE.fromCache().getOneById(json);
		return oneById;
	}

	public CcpJsonRepresentation getResumeContent(CcpJsonRepresentation json) {
		CcpJsonRepresentation result = VisCommonsUtils.getResume(json);
		return result;
	}

	public CcpJsonRepresentation sendResumesToEmail(CcpJsonRepresentation json) {
		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.send(json, VisAsyncBusiness.positionSendResumesToEmails);
		return result;
	}

	public CcpJsonRepresentation suggestNewSkills(CcpJsonRepresentation json) {
		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.send(json, VisAsyncBusiness.skillsSuggest);
		return result;
	}

}
