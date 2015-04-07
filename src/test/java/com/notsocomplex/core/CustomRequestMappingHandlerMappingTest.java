/**
 *
 */
package com.notsocomplex.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.notsocomplex.controller.HomePageController;
import com.notsocomplex.newcontroller.NewHomePageController;


/**
 *  @author Jasmin Larouche for Not So Complex
 *
 */
public class CustomRequestMappingHandlerMappingTest<T>
{

	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
	}

	// Should register one method
	@Test
	public void shouldRegisterOneMethod() throws NoSuchMethodException, SecurityException
	{
		final List<Method> registedMethods = Lists.newArrayList();
		final Set<Method> methods = Sets.newHashSet();

		final Method method1 = HomePageController.class.getMethod("home");

		methods.add(method1);

		final CustomRequestMappingHandlerMapping<T> handler = createCustomRequestMappingHandlerMapping(registedMethods, methods);

		handler.detectHandlerMethods("handler");

		assertEquals(registedMethods.size(), 1);
		assertEquals(registedMethods.get(0), method1);
	}


	// Should register only one method in case we override it
	@Test
	public void shouldRegisterOnlyOneMethodAfterOverride() throws NoSuchMethodException, SecurityException
	{
		final List<Method> registedMethods = Lists.newArrayList();
		final Set<Method> methods = Sets.newHashSet();

		final Method method1 = NewHomePageController.class.getMethod("home");

		final Method method2 = HomePageController.class.getMethod("home");
		final Method method3 = NewHomePageController.class.getMethod("equals", Object.class);

		methods.add(method1);
		methods.add(method2);
		methods.add(method3);

		final CustomRequestMappingHandlerMapping<T> handler = createCustomRequestMappingHandlerMapping(registedMethods, methods);

		handler.detectHandlerMethods("handler");

		assertEquals(2, registedMethods.size());
		assertTrue(registedMethods.contains(method1));
		assertTrue(registedMethods.contains(method3));
	}


	/**
	 * @param registedMethods
	 * @param methods
	 * @return requestMappingHandlerMapping
	 */
	private CustomRequestMappingHandlerMapping<T> createCustomRequestMappingHandlerMapping(final List<Method> registedMethods,
			final Set<Method> methods)
	{
		final CustomRequestMappingHandlerMapping<T> handler = new CustomRequestMappingHandlerMapping<T>()
		{
			@Override
			protected Class<?> getUserType(final Object handler)
			{
				return NewHomePageController.class;
			}

			@Override
			protected Set<Method> selectMethods(final Class<?> userType)
			{
				return methods;
			}

			@Override
			protected void registerHandlerMethod(final Object handler, final Method method, final RequestMappingInfo mapping)
			{
				registedMethods.add(method);
			}
		};
		return handler;
	}
}
