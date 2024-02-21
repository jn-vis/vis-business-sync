package com.ccp.vis.sync.validations.resumes.steps;

import com.ccp.validation.annotations.CurrentYear;
import com.ccp.validation.annotations.ObjectNumbers;
import com.ccp.validation.annotations.ObjectRules;
import com.ccp.validation.annotations.ObjectText;
import com.ccp.validation.annotations.ValidationRules;
import com.ccp.validation.enums.CurrentYearValidations;
import com.ccp.validation.enums.ObjectNumberValidations;
import com.ccp.validation.enums.ObjectTextSizeValidations;
import com.ccp.validation.enums.ObjectValidations;

@ValidationRules(
		objectTextsValidations = {
				@ObjectText(rule = ObjectTextSizeValidations.equalsOrLessThan, 
						fields = {"desiredJob", "lastJob"}, bound = 100)
		}
		
		//TODO validação do ano atual contra o passado
		,currentYearValidations = {
				@CurrentYear(rule = CurrentYearValidations.equalsOrLessThan, fields = {"experience"}, bound = 70),
				@CurrentYear(rule = CurrentYearValidations.equalsOrGreaterThan, fields = {"experience"}, bound = 0),
		},
				
		simpleObjectRules = {
				@ObjectRules(rule = ObjectValidations.requiredAtLeastOne, fields = {"btc", "clt", "pj"})
		},
		
		objectNumbersValidations = {
				@ObjectNumbers(rule=ObjectNumberValidations.equalsOrGreaterThan, bound = 1000, 
						fields = {"btc", "clt", "pj"} ),
				@ObjectNumbers(rule=ObjectNumberValidations.equalsOrLessThan, bound = 100000, 
				fields = {"btc", "clt", "pj"} )
		}
		
		
		)
public class VisResumeStep1DemaisDadosValidations {

}
