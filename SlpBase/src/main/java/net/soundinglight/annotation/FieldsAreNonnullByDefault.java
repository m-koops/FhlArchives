/*
 * (c) 2013 Soundinglight Publishing
 * All rights reserved.
 */

package net.soundinglight.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierDefault;

/**
 * This annotation can be applied to a package or class to indicate that the fields in that element
 * are non-null by default unless there is:
 * <ul>
 * <li>An explicit nullness annotation
 * <li>There is a default parameter annotation applied to a more tightly nested element.
 * </ul>
 * 
 * This annotation is copied from ParametersAreNonnullByDefault, since JSR-305 only defines that
 * one.
 */
@Documented
@Nonnull
@TypeQualifierDefault(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldsAreNonnullByDefault {
	// marker
}
