package com.ccp.vis.sync.validations.positions.steps;

import com.ccp.validation.annotations.Day;
import com.ccp.validation.annotations.ObjectRules;
import com.ccp.validation.annotations.ObjectText;
import com.ccp.validation.annotations.ValidationRules;
import com.ccp.validation.enums.DayValidations;
import com.ccp.validation.enums.ObjectTextSizeValidations;
import com.ccp.validation.enums.ObjectValidations;

@ValidationRules(
		simpleObjectRules = {
				@ObjectRules(rule= ObjectValidations.requiredFields, fields = 
					{"title", "description", "contactChannel", "expireDate"}),
				
		}
		,
		objectTextsValidations = {
				@ObjectText(rule=ObjectTextSizeValidations.equalsOrGreaterThan, fields = {"title"}, bound = 3),
				@ObjectText(rule=ObjectTextSizeValidations.equalsOrLessThan, fields = {"title"}, bound = 100),
				@ObjectText(rule=ObjectTextSizeValidations.equalsOrGreaterThan, fields = {"description"}, bound = 200),
				@ObjectText(rule=ObjectTextSizeValidations.equalsOrLessThan, fields = {"description"}, bound = 10000),
				@ObjectText(rule=ObjectTextSizeValidations.equalsOrGreaterThan, fields = {"contactChannel"}, bound = 3),
				@ObjectText(rule=ObjectTextSizeValidations.equalsOrLessThan, fields = {"contactChannel"}, bound = 100),
		}
		, dayValidations = {
				@Day(rule= DayValidations.equalsOrGreaterThan, fields = {"expireDate"}, bound = 0),
				@Day(rule= DayValidations.equalsOrLessThan, fields = {"expireDate"}, bound = 365),
		}
		
		)
public class VisPositionStep0DadosBasicos {

}
