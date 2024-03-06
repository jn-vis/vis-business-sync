package com.ccp.jn.vis.sync.service;

import java.util.function.Function;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.db.dao.CcpGetEntityId;
import com.ccp.jn.vis.sync.business.VisProcessStatus;
import com.jn.vis.commons.entities.VisEntityBalance;
import com.jn.vis.commons.entities.VisEntityDeniedViewToCompany;
import com.jn.vis.commons.entities.VisEntityPosition;
import com.jn.vis.commons.entities.VisEntityPositionFeesToViewResume;
import com.jn.vis.commons.entities.VisEntityResume;
import com.jn.vis.commons.entities.VisEntityResumeComment;
import com.jn.vis.commons.entities.VisEntityResumeNegativeted;
import com.jn.vis.commons.entities.VisEntityResumeView;

public class SyncServiceVisResumeView {

	public CcpJsonRepresentation getResume(CcpJsonRepresentation values) {
		
		VisEntityResume visEntityResume = new VisEntityResume();
		VisEntityBalance visEntityBalance = new VisEntityBalance();
		VisEntityPosition visEntityPosition = new VisEntityPosition();
		VisEntityResumeView visEntityResumeView = new VisEntityResumeView();
		VisEntityResumeComment visEntityResumeComment = new VisEntityResumeComment();
		VisEntityResumeNegativeted visEntityResumeNegativeted = new VisEntityResumeNegativeted();
		VisEntityDeniedViewToCompany visEntityDeniedViewToCompany = new VisEntityDeniedViewToCompany();
		VisEntityPositionFeesToViewResume visEntityPositionFeesToViewResume = new VisEntityPositionFeesToViewResume();		
		
		Function<CcpJsonRepresentation, CcpJsonRepresentation> action = data -> {
			
			return null;
		};
		
		CcpJsonRepresentation result = new CcpGetEntityId(values)
		.toBeginProcedureAnd()
			.loadThisIdFromEntity(visEntityBalance).and()
			.loadThisIdFromEntity(visEntityResumeView).and()
			.loadThisIdFromEntity(visEntityResumeComment).and()
			.loadThisIdFromEntity(visEntityPositionFeesToViewResume).and()
			.ifThisIdIsNotPresentInEntity(visEntityResume).returnStatus(VisProcessStatus.inactiveResume).and()
			.ifThisIdIsNotPresentInEntity(visEntityPosition).returnStatus(VisProcessStatus.inactivePosition).and()
			.ifThisIdIsPresentInEntity(visEntityResumeNegativeted).returnStatus(VisProcessStatus.negativatedResume).and()
			.ifThisIdIsPresentInEntity(visEntityDeniedViewToCompany).returnStatus(VisProcessStatus.notAllowedRecruiter).and()
			.executeAction(action).andFinallyReturningThisFields()
		.endThisProcedureRetrievingTheResultingData();
		
		return result;
	}
}
