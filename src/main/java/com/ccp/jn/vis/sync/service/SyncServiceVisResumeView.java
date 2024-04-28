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
		
		Function<CcpJsonRepresentation, CcpJsonRepresentation> action = data -> {
			
			return data;
		};
		
		CcpJsonRepresentation result = new CcpGetEntityId(values)
		.toBeginProcedureAnd()
			.loadThisIdFromEntity(VisEntityBalance.INSTANCE).and()
			.loadThisIdFromEntity(VisEntityResumeView.INSTANCE).and()
			.loadThisIdFromEntity(VisEntityResumeComment.INSTANCE).and()
			.loadThisIdFromEntity(VisEntityPositionFeesToViewResume.INSTANCE).and()
			.ifThisIdIsNotPresentInEntity(VisEntityResume.INSTANCE).returnStatus(VisProcessStatus.inactiveResume).and()
			.ifThisIdIsNotPresentInEntity(VisEntityPosition.INSTANCE).returnStatus(VisProcessStatus.inactivePosition).and()
			.ifThisIdIsPresentInEntity(VisEntityResumeNegativeted.INSTANCE).returnStatus(VisProcessStatus.negativatedResume).and()
			.ifThisIdIsPresentInEntity(VisEntityDeniedViewToCompany.INSTANCE).returnStatus(VisProcessStatus.notAllowedRecruiter).and()
			.executeAction(action).andFinallyReturningThisFields()
		.endThisProcedureRetrievingTheResultingData();
		
		return result;
	}
}
