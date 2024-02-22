package com.ccp.vis.sync.validations;

import com.ccp.validation.annotations.Day;
import com.ccp.validation.annotations.SimpleObject;
import com.ccp.validation.annotations.ObjectTextSize;
import com.ccp.validation.annotations.ValidationRules;
import com.ccp.validation.enums.DayValidations;
import com.ccp.validation.enums.ObjectTextSizeValidations;
import com.ccp.validation.enums.SimpleObjectValidations;

@ValidationRules(
		simpleObject = {
				@SimpleObject(rule= SimpleObjectValidations.requiredFields, fields = 
					{"title", "description", "contactChannel", "expireDate"}),
				
		}
		,
		objectTextSize = {
				@ObjectTextSize(rule=ObjectTextSizeValidations.equalsOrGreaterThan, fields = {"title"}, bound = 3),
				@ObjectTextSize(rule=ObjectTextSizeValidations.equalsOrLessThan, fields = {"title"}, bound = 100),
				@ObjectTextSize(rule=ObjectTextSizeValidations.equalsOrGreaterThan, fields = {"description"}, bound = 200),
				@ObjectTextSize(rule=ObjectTextSizeValidations.equalsOrLessThan, fields = {"description"}, bound = 10000),
				@ObjectTextSize(rule=ObjectTextSizeValidations.equalsOrGreaterThan, fields = {"contactChannel"}, bound = 3),
				@ObjectTextSize(rule=ObjectTextSizeValidations.equalsOrLessThan, fields = {"contactChannel"}, bound = 100),
		}
		, day = {
				@Day(rule= DayValidations.equalsOrGreaterThan, fields = {"expireDate"}, bound = 0),
				@Day(rule= DayValidations.equalsOrLessThan, fields = {"expireDate"}, bound = 365),
		}
		
		)
public class VisPositionStep0DadosBasicosValidations {

}
