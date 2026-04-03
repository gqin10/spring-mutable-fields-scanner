package com.qode.mutablescanner.scanner;

import com.qode.mutablescanner.annotation.AllowMutable;
import com.qode.mutablescanner.exception.MutableFieldNotAllowedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FieldScanner {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldScanner.class);

    private static final List<Class<?>> ALLOWED_ANNOTATIONS = Arrays.asList(
            Autowired.class,
            Value.class,

            AllowMutable.class
    );

    public static List<String> scan(Class<?> clazz, ConfigurableListableBeanFactory beanFactory) {
        Field[] fields = clazz.getDeclaredFields();
        List<String> mutableFields = new ArrayList<>();

        for (Field field : fields) {
            boolean isSingletonBean = isSingletonBean(field, beanFactory);
            boolean isAllowedModifier = isAllowedModifier(field);
            boolean isAllowedAnnotations = isAllowedAnnotation(field.getDeclaredAnnotations());

            if (isSingletonBean || isAllowedModifier || isAllowedAnnotations) {
                continue;
            }

            mutableFields.add(field.getName());
        }
        return mutableFields;
    }

    private static boolean isAllowedModifier(Field field) {
        int modifier = field.getModifiers();
        return Modifier.isStatic(modifier) || Modifier.isFinal(modifier);
    }

    private static boolean isAllowedAnnotation(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (ALLOWED_ANNOTATIONS.contains(annotation.annotationType())) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSingletonBean(Field field, ConfigurableListableBeanFactory beanFactory) {
        String[] beanNamesOfType = beanFactory.getBeanNamesForType(field.getType());
        if (beanNamesOfType.length > 0) {
            // to cater multiple beans exist but some are not singleton
            boolean isSingleton = true;
            for (String s : beanNamesOfType) {
                isSingleton = isSingleton && beanFactory.getBeanDefinition(s).isSingleton();
            }
            return isSingleton;
        }
        return false;
    }

}
