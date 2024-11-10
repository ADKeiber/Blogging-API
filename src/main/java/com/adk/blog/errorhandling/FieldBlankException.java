package com.adk.blog.errorhandling;

import org.springframework.util.StringUtils;

public class FieldBlankException extends RuntimeException {
	
	private static final long serialVersionUID = -8104227912438844281L;

	public FieldBlankException(Class<?> clazz, String missingField, String valueType) {
        super(FieldBlankException.generateMessage(clazz.getSimpleName(), missingField, valueType));
    }

    private static String generateMessage(String clazz, String missingField, String valueType) {
        return StringUtils.capitalize(clazz) + " was missing value of field '" + missingField + "' which is of " + valueType ;
    }
}