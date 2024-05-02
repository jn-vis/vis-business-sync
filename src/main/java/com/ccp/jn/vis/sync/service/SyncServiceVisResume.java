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
	// O Projeto contem business no nome porque trata de lógica de negócio, contem sync no nome por serem regras a 
	// 	serem executadas ao vivo (imediatamente).
	public Map<String, Object> save(String email, Map<String, Object> json) {
		// A linha abaixo trata-se de validações do campo do json; se quiser saber quais regras são essas, 
		// 	entre na classe que termina com .class no final.
		CcpJsonFieldsValidations.validate(JsonFieldsValidationsVisResume.class, json, "saveResume");
		// Dados vem na URL e dados vem no JSON, a linha abaixo junta os dois grupos de informação.
		CcpJsonRepresentation resume = new CcpJsonRepresentation(json).put("email", email);
		//TODO caça as bruxas contra a palavra "new"
		// As duas linhas abaixo colocam os dados para processar em processos paralelos (isso só é possível porque esses 
		//  processos não têm interdependênica entre si).
		CcpJsonRepresentation sendResultFromSaveResumeFile = JnSyncMensageriaSender.INSTANCE.send(resume, VisAsyncBusiness.saveResumeFile);
		// Vis.1.1
		VisEntityResume.INSTANCE.createOrUpdate(resume);
		CcpJsonRepresentation sendResultFromSaveResume = JnSyncMensageriaSender.INSTANCE.send(resume, VisAsyncBusiness.sendResumeToRecruiters);
		// Nas linhas abaixo é iniciado um JSON vazio que concatena os dados de feedback dos dois processos paralelos acima.
		// Esses dados são importantes por se referirem a tickets que servirão para o frontend verificar status do processo
		//	paralelo.
		CcpJsonRepresentation put = CcpConstants.EMPTY_JSON
				.put("sendResumeToRecruiters", sendResultFromSaveResume)
				.put("saveResumeFile", sendResultFromSaveResumeFile)
				;
		
		return  put.content;
	}

}
