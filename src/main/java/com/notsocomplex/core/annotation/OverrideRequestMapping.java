/**
 *
 */
package com.notsocomplex.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.Mapping;


/**
 * @author Jasmin Larouche for Not So Complex
 *
 */
@Target(
{ java.lang.annotation.ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface OverrideRequestMapping
{
	// Override request mapping
}
