package com.ccp.vis.sync.validations.resumes.steps;

import com.ccp.validation.annotations.CurrentYear;
import com.ccp.validation.annotations.ObjectText;
import com.ccp.validation.annotations.ValidationRules;
import com.ccp.validation.enums.CurrentYearValidations;
import com.ccp.validation.enums.ObjectTextSizeValidations;

@ValidationRules(
		objectTextsValidations = {
				@ObjectText(rule = ObjectTextSizeValidations.equalsOrLessThan, 
						fields = {"desiredJob", "lastJob"}, bound = 100)
		}
		
		//TODO validação do ano atual contra o passado
		,currentYearValidations = {
				@CurrentYear(rule = CurrentYearValidations.equalsOrLessThan, fields = {"experience"}, bound = 70),
				@CurrentYear(rule = CurrentYearValidations.equalsOrGreaterThan, fields = {"experience"}, bound = 0),
		}
		
		)
public class VisResumeStep1DadosDaCarreiraValidations {

}
