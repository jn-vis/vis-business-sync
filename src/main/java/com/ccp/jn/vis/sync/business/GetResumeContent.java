package com.ccp.jn.vis.sync.business;

import java.util.function.Function;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.exceptions.process.CcpFlow;
import com.ccp.jn.sync.mensageria.JnSyncMensageriaSender;
import com.jn.vis.commons.entities.VisEntityBalance;
import com.jn.vis.commons.entities.VisEntityFees;
import com.jn.vis.commons.entities.VisEntityResumeViewFailed;
import com.jn.vis.commons.status.ViewResumeStatus;
import com.jn.vis.commons.utils.VisAsyncBusiness;
import com.jn.vis.commons.utils.VisCommonsUtils;

public class GetResumeContent implements Function<CcpJsonRepresentation, CcpJsonRepresentation>{
	
	private GetResumeContent() {}
	
	public static final GetResumeContent INSTANCE = new GetResumeContent();
	
	public CcpJsonRepresentation apply(CcpJsonRepresentation json) {
		/*
		 * VisEntityResume
		 * VisEntityFees
		 * VisEntityResumeView
		 * VisEntityResumeOpinion.INSTANCE.getMirrorEntity()
		 * VisEntityPosition
		 */
		CcpJsonRepresentation balance =  VisEntityBalance.INSTANCE.getInnerJsonFromMainAndMirrorEntities(json);
		CcpJsonRepresentation fee =  VisEntityFees.INSTANCE.getInnerJsonFromMainAndMirrorEntities(json);
		
		boolean insufficientFunds = VisCommonsUtils.isInsufficientFunds(1, fee, balance);
		
		if(insufficientFunds) {
			CcpJsonRepresentation put = json.put("status", ViewResumeStatus.insufficientFunds);
			VisEntityResumeViewFailed.INSTANCE.createOrUpdate(put);
			throw new CcpFlow(json, ViewResumeStatus.insufficientFunds.status());
		}
		
		JnSyncMensageriaSender.INSTANCE.whenSendMessage(VisAsyncBusiness.resumeViewSave).apply(json);
	
		String contentType = json.getAsString("contentType");
		String email = json.getAsString("email");
		
		String resumeContent = VisCommonsUtils.getResumeContent(email, contentType);
		CcpJsonRepresentation put = json.put("resumeContent", resumeContent);
		return put;
	}

}
