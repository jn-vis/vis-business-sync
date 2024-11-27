package com.ccp.jn.vis.sync.business;

import java.util.function.Function;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.exceptions.process.CcpFlow;
import com.ccp.jn.sync.mensageria.JnSyncMensageriaSender;
import com.jn.vis.commons.status.ViewResumeStatus;
import com.jn.vis.commons.utils.VisAsyncBusiness;
import com.jn.vis.commons.utils.VisCommonsUtils;
import com.vis.commons.entities.VisEntityBalance;
import com.vis.commons.entities.VisEntityFees;
import com.vis.commons.entities.VisEntityResumeViewFailed;

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
			throw new CcpFlow(json, ViewResumeStatus.insufficientFunds);
		}
		
		JnSyncMensageriaSender.INSTANCE.sendJsonToTopic(VisAsyncBusiness.resumeViewSave, json);
		//TODO IMPLEMENTAR LOGICA PARA FORMAR NOME DO ARQUIVO DO CURRICULO EM CASO DE SER RECRUTADOR BAIXANDO POR VAGA OU NAO
		CcpJsonRepresentation resume = VisCommonsUtils.getResumeFromBucket(json);
		
		return resume;
	}


}
