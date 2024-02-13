package com.ccp.vis.sync.validations.resumes.step0;

import com.ccp.fields.validations.annotations.AllowedValues;
import com.ccp.fields.validations.annotations.ObjectRules;
import com.ccp.fields.validations.annotations.ObjectText;
import com.ccp.fields.validations.annotations.ValidationRules;
import com.ccp.fields.validations.enums.AllowedValuesValidations;
import com.ccp.fields.validations.enums.ObjectTextSizeValidations;
import com.ccp.fields.validations.enums.ObjectValidations;

@ValidationRules(simpleObjectRules = {
		@ObjectRules(rule = ObjectValidations.requiredFields, fields = {"onlyHomeOffice", "ddds", "pcd", "disabilities", "companiesNotAllowed", "disponibility", "observations"}),
		@ObjectRules(rule = ObjectValidations.nonRepeatedLists, fields = {"ddds","disabilities", "companiesNotAllowed"}),
		@ObjectRules(rule = ObjectValidations.listFields, fields = {"ddds", "disabilities", "companiesNotAllowed"}),
		@ObjectRules(rule = ObjectValidations.integerFields, fields = {"ddds","disponibility"}),
		@ObjectRules(rule = ObjectValidations.booleanFields, fields = {"onlyHomeOffice","pcd"}),
	},allowedValues = {
			@AllowedValues(rule = AllowedValuesValidations.arrayWithAllowedNumbers , fields =  {"ddd"}, allowedValues = {"61", "62", "64", "65", "66", "67", "82", "71", "73", "74", "75", "77", "85", "88", "98", "99", "83", "81", "87", "86", "89", "84", "79", "68", "96", "92", "97", "91", "93", "94", "69", "95", "63", "27", "28", "31", "32", "33", "34", "35", "37", "38", "21", "22", "24", "11", "12", "13", "14", "15", "16", "17", "18", "19", "41", "42", "43", "44", "45", "46", "51", "53", "54	", "55", "47", "48", "49"}),
			@AllowedValues(rule=AllowedValuesValidations.arrayWithAllowedTexts ,fields ={"disabilities"}, allowedValues = {"física/motora", "intelectual/mental", "visual", "auditiva"})
	},objectTextsValidations =  {
			@ObjectText(rule = ObjectTextSizeValidations.equalsOrLessThan, fields = {"observations"}, bound = 500)
	}
)
public class VisResumeStep0DadosGeraisValidations {

}
