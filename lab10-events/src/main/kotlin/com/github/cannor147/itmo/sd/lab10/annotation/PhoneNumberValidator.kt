package com.github.cannor147.itmo.sd.lab10.annotation

import jakarta.validation.ConstraintValidatorContext
import org.hibernate.validator.internal.constraintvalidators.AbstractEmailValidator
import java.util.regex.Pattern

class PhoneNumberValidator : AbstractEmailValidator<PhoneNumber>() {
    override fun isValid(value: CharSequence?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) {
            return true
        } else if (!super.isValid(value, context)) {
            return false
        }

        val matcher = PHONE_NUMBER_PATTERN.matcher(value.trim())
        return matcher.matches()
    }

    companion object {
        val PHONE_NUMBER_PATTERN: Pattern = Pattern.compile("^(8|\\+7)?([\\- ]?\\(?\\d{3}\\)?)([\\- ]?\\d){7}$")
    }
}