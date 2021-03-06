/**
 *
 */
package com.notsocomplex.core;

import java.lang.reflect.Method;
import java.util.Set;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils.MethodFilter;
import org.springframework.web.method.HandlerMethodSelector;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.google.common.collect.Sets;
import com.notsocomplex.core.annotation.OverrideRequestMapping;


/**
 * @author Jasmin Larouche for Not So Complex
 *
 *         This customRequestMappingHandlerMapping will allow to override request mapping using @OverrideRequestMapping
 *
 */
public class CustomRequestMappingHandlerMapping<T> extends RequestMappingHandlerMapping
{

	@Override
	protected void detectHandlerMethods(final Object handler)
	{
		final Class<?> userType = getUserType(handler);
		final Set<Method> methods = selectMethods(userType);
		final Set<RequestMappingInfo> overrideMethodMapping = Sets.newHashSet();

		for (final Method method : methods)
		{
			final OverrideRequestMapping overrideAnnotation = AnnotationUtils.findAnnotation(method, OverrideRequestMapping.class);
			if (overrideAnnotation != null)
			{
				final RequestMappingInfo mapping = getMappingForMethod(method, userType);
				overrideMethodMapping.add(mapping);
			}
		}

		for (final Method method : methods)
		{
			final RequestMappingInfo mapping = getMappingForMethod(method, userType);

			final OverrideRequestMapping overrideAnnotation = AnnotationUtils.findAnnotation(method, OverrideRequestMapping.class);
			if (overrideAnnotation == null && overrideMethodMapping.contains(mapping) || getHandlerMethods().get(mapping) != null)
			{
				continue;
			}
			registerHandlerMethod(handler, method, mapping);
		}
	}

	/**
	 * @param handler
	 * @return userType
	 */
	protected Class<?> getUserType(final Object handler)
	{
		final Class<?> handlerType = (handler instanceof String) ? getApplicationContext().getType((String) handler) : handler
				.getClass();

		return ClassUtils.getUserClass(handlerType);
	}

	protected Set<Method> selectMethods(final Class<?> userType)
	{
		return HandlerMethodSelector.selectMethods(userType, new MethodFilter()
		{
			@Override
			public boolean matches(final Method method)
			{
				return getMappingForMethod(method, userType) != null;
			}
		});
	}
}
