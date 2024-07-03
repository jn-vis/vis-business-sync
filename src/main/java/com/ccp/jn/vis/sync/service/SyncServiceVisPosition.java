package com.ccp.jn.vis.sync.service;


import com.ccp.constantes.CcpConstants;
import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.especifications.db.crud.CcpCrud;
import com.ccp.especifications.db.crud.CcpGetEntityId;
import com.ccp.especifications.db.crud.CcpSelectUnionAll;
import com.ccp.especifications.db.utils.CcpEntity;
import com.ccp.jn.sync.mensageria.JnSyncMensageriaSender;
import com.ccp.jn.vis.sync.business.GetResumeContent;
import com.ccp.jn.vis.sync.business.ResumeSaveViewFailed;
import com.jn.vis.commons.entities.VisEntityBalance;
import com.jn.vis.commons.entities.VisEntityDeniedViewToCompany;
import com.jn.vis.commons.entities.VisEntityFees;
import com.jn.vis.commons.entities.VisEntityGroupResumesByPosition;
import com.jn.vis.commons.entities.VisEntityPosition;
import com.jn.vis.commons.entities.VisEntityResume;
import com.jn.vis.commons.entities.VisEntityResumeFreeView;
import com.jn.vis.commons.entities.VisEntityResumeLastView;
import com.jn.vis.commons.entities.VisEntityResumePerception;
import com.jn.vis.commons.entities.VisEntitySkill;
import com.jn.vis.commons.entities.VisEntitySkillApproved;
import com.jn.vis.commons.entities.VisEntitySkillRejected;
import com.jn.vis.commons.status.SuggestNewSkillStatus;
import com.jn.vis.commons.status.ViewResumeStatus;
import com.jn.vis.commons.utils.VisAsyncBusiness;
import com.jn.vis.commons.utils.VisCommonsUtils;

public class SyncServiceVisPosition {
	
	private SyncServiceVisPosition() {}
	
	public static final SyncServiceVisPosition INSTANCE = new SyncServiceVisPosition();
	
	public CcpJsonRepresentation save(CcpJsonRepresentation json) {
		
		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.whenSendMessage(VisAsyncBusiness.positionSave).apply(json);
		
		return result;
	}
	
	public CcpJsonRepresentation changeStatus(CcpJsonRepresentation json) {

		CcpJsonRepresentation result = JnSyncMensageriaSender.INSTANCE.whenSendMessage(VisAsyncBusiness.positionStatusChange).apply(json);
		
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
	
	public CcpJsonRepresentation getImportantSkillsFromText(CcpJsonRepresentation json) {
		
		CcpJsonRepresentation oneById = VisEntitySkill.INSTANCE.fromCache().getOneById(json);
		
		CcpJsonRepresentation jsonWithSkills = VisCommonsUtils.getJsonWithSkills(
				oneById
				, VisEntityPosition.Fields.description.name()
				, VisEntityPosition.Fields.mandatorySkill.name()
				);
		
		return jsonWithSkills;
	}

	public CcpJsonRepresentation getResumeContent(CcpJsonRepresentation json) {

		CcpJsonRepresentation findById =  new CcpGetEntityId(json)
		.toBeginProcedureAnd()
			.loadThisIdFromEntity(VisEntityPosition.INSTANCE).and()
			.loadThisIdFromEntity(VisEntityResumePerception.INSTANCE).and()
			.loadThisIdFromEntity(VisEntityResumeFreeView.INSTANCE).and()
			.loadThisIdFromEntity(VisEntityResumeLastView.INSTANCE).and()
			.loadThisIdFromEntity(VisEntityPosition.INSTANCE.getMirrorEntity()).and()
			.loadThisIdFromEntity(VisEntityResumePerception.INSTANCE.getMirrorEntity()).and()
			.ifThisIdIsNotPresentInEntity(VisEntityBalance.INSTANCE).returnStatus(ViewResumeStatus.missingBalance).and()
			.ifThisIdIsNotPresentInEntity(VisEntityFees.INSTANCE).returnStatus(ViewResumeStatus.missingFee).and()
			.ifThisIdIsPresentInEntity(VisEntityDeniedViewToCompany.INSTANCE).returnStatus(ViewResumeStatus.notAllowedRecruiter).and()
			.ifThisIdIsPresentInEntity(VisEntityResume.INSTANCE.getMirrorEntity()).returnStatus(ViewResumeStatus.inactiveResume).and()
			.ifThisIdIsNotPresentInEntity(VisEntityResume.INSTANCE).returnStatus(ViewResumeStatus.resumeNotFound).and()
			.ifThisIdIsPresentInEntity(VisEntityResume.INSTANCE).executeAction(GetResumeContent.INSTANCE).andFinallyReturningThisFields()
		.endThisProcedureRetrievingTheResultingData(ResumeSaveViewFailed.INSTANCE);
		
		return findById;
	}

	public CcpJsonRepresentation suggestNewSkills(CcpJsonRepresentation json) {
		
		CcpJsonRepresentation findById =  new CcpGetEntityId(json)
		.toBeginProcedureAnd()
			.ifThisIdIsPresentInEntity(VisEntitySkill.INSTANCE).returnStatus(SuggestNewSkillStatus.alreadyExists).and()
			.ifThisIdIsPresentInEntity(VisEntitySkillApproved.INSTANCE).returnStatus(SuggestNewSkillStatus.approvedSkill).and()
			.ifThisIdIsPresentInEntity(VisEntitySkillRejected.INSTANCE).returnStatus(SuggestNewSkillStatus.rejectedSkill).and()
			.ifThisIdIsPresentInEntity(VisEntitySkillApproved.INSTANCE.getMirrorEntity()).returnStatus(SuggestNewSkillStatus.pendingSkill).and()
			.ifThisIdIsNotPresentInEntity(VisEntitySkill.INSTANCE).executeAction(JnSyncMensageriaSender.INSTANCE.whenSendMessage(VisAsyncBusiness.skillsSuggest))
			.andFinallyReturningThisFields()
		.endThisProcedureRetrievingTheResultingData(CcpConstants.DO_NOTHING);
		
		return findById;
	}

}
