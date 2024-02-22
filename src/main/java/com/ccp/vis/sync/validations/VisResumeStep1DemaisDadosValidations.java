package com.ccp.vis.sync.validations;

import com.ccp.validation.annotations.Year;
import com.ccp.validation.annotations.ObjectNumbers;
import com.ccp.validation.annotations.SimpleObject;
import com.ccp.validation.annotations.ObjectTextSize;
import com.ccp.validation.annotations.ValidationRules;
import com.ccp.validation.enums.YearValidations;
import com.ccp.validation.enums.ObjectNumberValidations;
import com.ccp.validation.enums.ObjectTextSizeValidations;
import com.ccp.validation.enums.SimpleObjectValidations;

@ValidationRules(
		objectTextSize = {
				@ObjectTextSize(rule = ObjectTextSizeValidations.equalsOrLessThan, 
						fields = {"desiredJob", "lastJob"}, bound = 100)
		}
		
		//TODO validação do ano atual contra o passado
		,year = {
				@Year(rule = YearValidations.equalsOrLessThan, fields = {"experience"}, bound = 70),
				@Year(rule = YearValidations.equalsOrGreaterThan, fields = {"experience"}, bound = 0),
		},
				
		simpleObject = {
				@SimpleObject(rule = SimpleObjectValidations.requiredAtLeastOne, fields = {"btc", "clt", "pj"})
		},
		
		objectNumbers = {
				@ObjectNumbers(rule=ObjectNumberValidations.equalsOrGreaterThan, bound = 1000, 
						fields = {"btc", "clt", "pj"} ),
				@ObjectNumbers(rule=ObjectNumberValidations.equalsOrLessThan, bound = 100000, 
				fields = {"btc", "clt", "pj"} )
		}
		
		
		)
public class VisResumeStep1DemaisDadosValidations {

}
