/*
package com.szh.util.common.proxy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ExceptionIgnoredProxyFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionIgnoredProxyFactory.class);
    private static final ExceptionIgnoredProxyFactory INSTANCE = new ExceptionIgnoredProxyFactory();
    private final DefaultValueMapper defaultValueMapper;

    public static ExceptionIgnoredProxyFactory getInstance() {
        LOGGER.info("get instance of ExceptionIgnoredProxyFactory");
        return INSTANCE;
    }

    public ExceptionIgnoredProxyFactory(DefaultValueMapper defaultValueMapper) {
        this.defaultValueMapper = defaultValueMapper;
    }

    private ExceptionIgnoredProxyFactory() {
        this(new DefaultValueMapper());
    }

    public synchronized <T> T create(T t) {
        if(t == null) {
            return t;
        } else {
            Object result = this.createProxy(t);
            if(result == null) {
                LOGGER.warn("failed to create proxy for {}.", t);
            }

            LOGGER.info("success to create proxy {} for {}.", result, t);
            return result;
        }
    }

    private <T> T createProxy(T t) {
        Class targetCls = t.getClass();
        Object result = null;

        try {
            result = this.createProxyUseSubClass(targetCls, t);
        } catch (Exception var5) {
            LOGGER.info("failed to create proxy use subclass, error is {}", var5.getMessage());
        }

        if(result != null) {
            LOGGER.info("success to create proxy {} as subclass for {}.", result, t);
            return result;
        } else {
            result = this.createProxyUseInterface(targetCls, t);
            if(result == null) {
                LOGGER.info("failed to create proxy using interface for {}.", t);
                return null;
            } else {
                LOGGER.info("success to create proxy {} using interface for {}.", result, t);
                return result;
            }
        }
    }

    private <T> T createProxyUseInterface(Class targetCls, T t) {
        LOGGER.info("create proxy use interfaces.");
        Class[] interfaceClses = this.getInterfaces(targetCls);
        if(interfaceClses != null && interfaceClses.length != 0) {
            Enhancer enhancer = new Enhancer();
            enhancer.setInterfaces(interfaceClses);
            enhancer.setCallback(new ExceptionIgnoredProxyFactory.ExceptionIgnoredCallback(t));
            return enhancer.create();
        } else {
            LOGGER.warn("the object {} have no interface, can not create proxy, use self .", t);
            return t;
        }
    }

    private Class[] getInterfaces(Class targetCls) {
        if(targetCls == null) {
            return null;
        } else {
            HashSet allClses = new HashSet();

            for(Class temp = targetCls; temp != null; temp = temp.getSuperclass()) {
                allClses.add(temp);
            }

            HashSet allInterfaces = new HashSet();
            Iterator var5 = allClses.iterator();

            while(var5.hasNext()) {
                Class cls = (Class)var5.next();
                allInterfaces.addAll(Arrays.asList(cls.getInterfaces()));
            }

            return (Class[])allInterfaces.toArray(new Class[allInterfaces.size()]);
        }
    }

    private <T> T createProxyUseSubClass(Class targetCls, T t) {
        LOGGER.info("create proxy use interfaces.");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetCls);
        enhancer.setCallback(new ExceptionIgnoredProxyFactory.ExceptionIgnoredCallback(t));
        return enhancer.create();
    }

    private class ExceptionIgnoredCallback implements MethodInterceptor {
        private final Object targetObject;

        private ExceptionIgnoredCallback(Object targetObject) {
            this.targetObject = targetObject;
        }

        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            try {
                return method.invoke(this.targetObject, objects);
            } catch (Throwable var6) {
                ExceptionIgnoredProxyFactory.LOGGER.error("failed to run method {} using args {}.", new Object[]{method, Arrays.toString(objects), var6});
                return ExceptionIgnoredProxyFactory.this.defaultValueMapper.getDefaultValueOfClass(method.getReturnType());
            }
        }
    }
}
*/
