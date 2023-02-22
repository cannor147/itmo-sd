/*
 * Jakarta Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package com.github.cannor147.itmo.sd.lab10.annotation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import kotlin.reflect.KClass

/**
 * The annotated element must be a valid Russian mobile phone number
 * Supports only string type.
 *
 * @author Erofey Bashunov
 */
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.FIELD,
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.TYPE,
)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@JvmRepeatable(PhoneNumber.List::class)
@MustBeDocumented
@Constraint(validatedBy = [])
annotation class PhoneNumber(
    val message: String = "{com.github.cannor147.itmo.sd.lab10.annotation.PhoneNumber.message}",
) {
    /**
     * Defines several [PhoneNumber] annotations on the same element.
     *
     * @see PhoneNumber
     */
    @Target(
        AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER,
        AnnotationTarget.FIELD,
        AnnotationTarget.ANNOTATION_CLASS,
        AnnotationTarget.CONSTRUCTOR,
        AnnotationTarget.VALUE_PARAMETER,
        AnnotationTarget.TYPE,
    )
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    @MustBeDocumented
    annotation class List(vararg val value: PhoneNumber)
}