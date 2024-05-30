package com.ccp.jn.vis.sync.service;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.jn.sync.mensageria.JnSyncMensageriaSender;
import com.jn.vis.commons.entities.VisEntityPositionResumes;
import com.jn.vis.commons.entities.VisEntitySkill;
import com.jn.vis.commons.utils.VisAsyncBusiness;
import com.jn.vis.commons.utils.VisCommonsUtils;

public class SyncServiceVisPosition {

	public CcpJsonRepresentation save(CcpJsonRepresentation json) {
		
		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.send(json, VisAsyncBusiness.positionSave);
		
		return result;
		
	}
	
	public CcpJsonRepresentation changeStatus(CcpJsonRepresentation json) {

		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.send(json, VisAsyncBusiness.positionStatusChange);
		
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

	public CcpJsonRepresentation suggestNewSkills(CcpJsonRepresentation json) {
		
		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.send(json, VisAsyncBusiness.skillsSuggest);
		
		return result;
	}

}
