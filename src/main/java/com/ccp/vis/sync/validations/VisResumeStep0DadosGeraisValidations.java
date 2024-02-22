package com.ccp.vis.sync.validations;

import com.ccp.validation.annotations.AllowedValues;
import com.ccp.validation.annotations.SimpleArray;
import com.ccp.validation.annotations.SimpleObject;
import com.ccp.validation.annotations.ObjectTextSize;
import com.ccp.validation.annotations.ValidationRules;
import com.ccp.validation.enums.AllowedValuesValidations;
import com.ccp.validation.enums.SimpleArrayValidations;
import com.ccp.validation.enums.ObjectTextSizeValidations;
import com.ccp.validation.enums.SimpleObjectValidations;

@ValidationRules(simpleObject = {
		@SimpleObject(rule = SimpleObjectValidations.requiredFields, fields = {"onlyHomeOffice", "ddds", "disabilities", "companiesNotAllowed", 
			"disponibility", "observations", "resumeBase64", "resumeWords", "resumeText"}),
		@SimpleObject(rule = SimpleObjectValidations.nonRepeatedLists, fields = {"ddds","disabilities", "companiesNotAllowed", "resumeWords"}),
		@SimpleObject(rule = SimpleObjectValidations.integerFields, fields = {"disponibility"}),
		@SimpleObject(rule = SimpleObjectValidations.booleanFields, fields = {"onlyHomeOffice"}),
	},allowedValues = {
			@AllowedValues(rule = AllowedValuesValidations.arrayWithAllowedNumbers , fields =  {"ddd"}, allowedValues = {"10", "61", "62", "64", "65", "66", "67", "82", "71", "73", "74", "75", "77", "85", "88", "98", "99", "83", "81", "87", "86", "89", "84", "79", "68", "96", "92", "97", "91", "93", "94", "69", "95", "63", "27", "28", "31", "32", "33", "34", "35", "37", "38", "21", "22", "24", "11", "12", "13", "14", "15", "16", "17", "18", "19", "41", "42", "43", "44", "45", "46", "51", "53", "54	", "55", "47", "48", "49"}),
			@AllowedValues(rule = AllowedValuesValidations.arrayWithAllowedTexts ,fields ={"disabilities"}, allowedValues = {"física/motora", "intelectual/mental", "visual", "auditiva"})
	},objectTextSize =  {
			@ObjectTextSize(rule = ObjectTextSizeValidations.equalsOrLessThan, fields = {"observations"}, bound = 500),
			@ObjectTextSize(rule = ObjectTextSizeValidations.equalsOrLessThan, fields = {"resumeBase64"}, bound = 4_200_000),
			@ObjectTextSize(rule = ObjectTextSizeValidations.equalsOrLessThan, fields = {"resumeText"}, bound = 2_100_000),
			@ObjectTextSize(rule = ObjectTextSizeValidations.equalsOrGreaterThan, fields = {"resumeBase64"}, bound = 512),
			@ObjectTextSize(rule = ObjectTextSizeValidations.equalsOrGreaterThan, fields = {"resumeText"}, bound = 512),
	},
	simpleArray = {
			@SimpleArray(rule = SimpleArrayValidations.notEmptyArray, fields = {"ddds", "resumeWords"}),
			@SimpleArray(rule = SimpleArrayValidations.integerItems, fields = {"ddds"}),
	}
	
	


	
)
public class VisResumeStep0DadosGeraisValidations {

}
