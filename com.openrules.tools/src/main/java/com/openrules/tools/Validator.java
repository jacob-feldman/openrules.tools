package com.openrules.tools;

/*
 * Copyright (c) 2005-2007 OpenRules, Inc. 	
 */

import java.util.Locale;

import org.apache.commons.validator.GenericValidator;

public class Validator {

	static public boolean isCreditCardValid(String card) {
		return GenericValidator.isCreditCard(card);
	}

	static public boolean isEmailValid(String email) {
		return GenericValidator.isEmail(email);
	}

	static public boolean isDateValid(String value) {
		return isDateValid(value, Locale.getDefault());
	}

	static public boolean isDateValid(String value, Locale locale) {
		return GenericValidator.isDate(value, locale);
	}

	static public boolean isDateValid(String value, String pattern,
			boolean strict) {
		return GenericValidator.isDate(value, pattern, strict);
	}

	static public boolean isRegExpValid(String value, String pattern) {
		return GenericValidator.matchRegexp(value, pattern);
	}

	static public boolean isIntegerValid(String value) {
		return GenericValidator.isInt(value);
	}

	static public boolean isDoubleValid(String value) {
		return GenericValidator.isDouble(value);
	}

	static public boolean isUrlValid(String value) {
		return GenericValidator.isUrl(value);
	}
}