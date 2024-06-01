package com.ccp.jn.vis.sync.service;

import java.util.function.Function;

import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.crud.CcpCrud;
import com.ccp.especifications.db.crud.CcpGetEntityId;
import com.ccp.especifications.db.crud.CcpSelectUnionAll;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.jn.sync.mensageria.JnSyncMensageriaSender;
import com.ccp.jn.vis.sync.business.GetResumeContent;
import com.ccp.jn.vis.sync.business.VisProcessStatus;
import com.jn.vis.commons.entities.VisEntityBalance;
import com.jn.vis.commons.entities.VisEntityDeniedViewToCompany;
import com.jn.vis.commons.entities.VisEntityGroupResumesByPosition;
import com.jn.vis.commons.entities.VisEntityPosition;
import com.jn.vis.commons.entities.VisEntityResume;
import com.jn.vis.commons.entities.VisEntityScheduleSendingResumeFees;
import com.jn.vis.commons.entities.VisEntitySkill;
import com.jn.vis.commons.utils.VisAsyncBusiness;

public class SyncServiceVisPosition {
	
	private SyncServiceVisPosition() {}
	
	public static final SyncServiceVisPosition INSTANCE = new SyncServiceVisPosition();
	
	public CcpJsonRepresentation save(CcpJsonRepresentation json) {
		
		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.send(json, VisAsyncBusiness.positionSave);
		
		return result;
	}
	
	public CcpJsonRepresentation changeStatus(CcpJsonRepresentation json) {

		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.send(json, VisAsyncBusiness.positionStatusChange);
		
		return result;
	}
	
	public CcpJsonRepresentation getData(CcpJsonRepresentation json) {
		
		CcpCrud crud = CcpDependencyInjection.getDependency(CcpCrud.class);
		
		CcpEntity mirrorEntity = VisEntityPosition.INSTANCE.getMirrorEntity();
		CcpSelectUnionAll searchResults = crud.unionAll(json, VisEntityPosition.INSTANCE, mirrorEntity);
		
		boolean activeResume = VisEntityPosition.INSTANCE.isPresentInThisUnionAll(searchResults, json);
		
		if(activeResume) {
			CcpJsonRepresentation requiredEntityRow = VisEntityPosition.INSTANCE.getRequiredEntityRow(searchResults, json);
			CcpJsonRepresentation put = requiredEntityRow.put("activePosition", true);
			return put;
		}
		
		CcpJsonRepresentation requiredEntityRow = mirrorEntity.getRequiredEntityRow(searchResults, json);
		CcpJsonRepresentation put = requiredEntityRow.put("activePosition", false);
		return put;
	}

	public CcpJsonRepresentation getResumeList(CcpJsonRepresentation json) {
		
		CcpJsonRepresentation oneById = VisEntityGroupResumesByPosition.INSTANCE.fromCache().getOneById(json);
		
		return oneById;
	}
	
	//FIXME FALTANDO LOGICA DE EXTRAIR SKILLS DA VAGA
	public CcpJsonRepresentation getImportantSkillsFromText(CcpJsonRepresentation json) {
		
		CcpJsonRepresentation oneById = VisEntitySkill.INSTANCE.fromCache().getOneById(json);
		
		return oneById;
	}
	//TODO REGISTRAR VISUALIZAÇÃO POR DENTRO DA VAGAS
	public CcpJsonRepresentation getResumeContent(CcpJsonRepresentation json) {
		//TODO REGISTRAR OCORRENCIA
		Function<CcpJsonRepresentation, CcpJsonRepresentation> registerInactiveResume = CcpConstants.DO_NOTHING;
		
		CcpJsonRepresentation findById =  new CcpGetEntityId(json)
		.toBeginProcedureAnd()
			.ifThisIdIsNotPresentInEntity(VisEntityBalance.INSTANCE).returnStatus(VisProcessStatus.missingBalance).and()
			.ifThisIdIsNotPresentInEntity(VisEntityScheduleSendingResumeFees.INSTANCE).returnStatus(VisProcessStatus.missingFee).and()
			.ifThisIdIsPresentInEntity(VisEntityDeniedViewToCompany.INSTANCE).returnStatus(VisProcessStatus.notAllowedRecruiter).and()
			.ifThisIdIsPresentInEntity(VisEntityResume.INSTANCE.getMirrorEntity()).executeAction(registerInactiveResume).and()
			.ifThisIdIsNotPresentInEntity(VisEntityResume.INSTANCE).returnStatus(VisProcessStatus.inactiveResume).and()
			.ifThisIdIsPresentInEntity(VisEntityResume.INSTANCE).executeAction(GetResumeContent.INSTANCE).andFinallyReturningThisFields()
		.endThisProcedureRetrievingTheResultingData();
		
		return findById;
	}

	public CcpJsonRepresentation suggestNewSkills(CcpJsonRepresentation json) {
		
		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.send(json, VisAsyncBusiness.skillsSuggest);
		
		return result;
	}

}
