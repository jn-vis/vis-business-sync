package com.ccp.vis.sync.validations.resumes.steps;

import com.ccp.validation.annotations.AllowedValues;
import com.ccp.validation.annotations.ArrayRules;
import com.ccp.validation.annotations.ObjectRules;
import com.ccp.validation.annotations.ObjectText;
import com.ccp.validation.annotations.ValidationRules;
import com.ccp.validation.enums.AllowedValuesValidations;
import com.ccp.validation.enums.ArrayValidations;
import com.ccp.validation.enums.ObjectTextSizeValidations;
import com.ccp.validation.enums.ObjectValidations;

@ValidationRules(simpleObjectRules = {
		@ObjectRules(rule = ObjectValidations.requiredFields, fields = {"onlyHomeOffice", "ddds", "disabilities", "companiesNotAllowed", 
			"disponibility", "observations", "resumeBase64", "resumeWords", "resumeText"}),
		@ObjectRules(rule = ObjectValidations.nonRepeatedLists, fields = {"ddds","disabilities", "companiesNotAllowed", "resumeWords"}),
		@ObjectRules(rule = ObjectValidations.integerFields, fields = {"ddds","disponibility"}),
		@ObjectRules(rule = ObjectValidations.booleanFields, fields = {"onlyHomeOffice"}),
	},allowedValues = {
			@AllowedValues(rule = AllowedValuesValidations.arrayWithAllowedNumbers , fields =  {"ddd"}, allowedValues = {"10", "61", "62", "64", "65", "66", "67", "82", "71", "73", "74", "75", "77", "85", "88", "98", "99", "83", "81", "87", "86", "89", "84", "79", "68", "96", "92", "97", "91", "93", "94", "69", "95", "63", "27", "28", "31", "32", "33", "34", "35", "37", "38", "21", "22", "24", "11", "12", "13", "14", "15", "16", "17", "18", "19", "41", "42", "43", "44", "45", "46", "51", "53", "54	", "55", "47", "48", "49"}),
			@AllowedValues(rule=AllowedValuesValidations.arrayWithAllowedTexts ,fields ={"disabilities"}, allowedValues = {"física/motora", "intelectual/mental", "visual", "auditiva"})
	},objectTextsValidations =  {
			@ObjectText(rule = ObjectTextSizeValidations.equalsOrLessThan, fields = {"observations"}, bound = 500),
			@ObjectText(rule = ObjectTextSizeValidations.equalsOrLessThan, fields = {"resumeBase64"}, bound = 4_200_000),
			@ObjectText(rule = ObjectTextSizeValidations.equalsOrLessThan, fields = {"resumeText"}, bound = 2_100_000),
			@ObjectText(rule = ObjectTextSizeValidations.equalsOrGreaterThan, fields = {"resumeBase64"}, bound = 512),
			@ObjectText(rule = ObjectTextSizeValidations.equalsOrGreaterThan, fields = {"resumeText"}, bound = 512),
	},
	simpleArrayRules = {
			@ArrayRules(rule = ArrayValidations.notEmptyArray, fields = {"ddds", "resumeWords"}),
	}
	
)
public class VisResumeStep0DadosGeraisValidations {

}
