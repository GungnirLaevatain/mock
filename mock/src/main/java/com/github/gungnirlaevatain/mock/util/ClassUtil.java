package com.github.gungnirlaevatain.mock.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * The class Class util.
 * 针对class进行部分操作
 *
 * @author gungnirlaevatain
 * @version 2019 -06-24 15:48:27
 * @since 1.0
 */
public class ClassUtil {

    /**
     * Is base class.
     * 判断是否是基本类型或包装类
     *
     * @param cls the cls
     * @return the boolean
     * @author gungnirlaevatain
     */
    public static boolean isBaseClass(Class cls) {
        return isPrimitiveClass(cls) || isPackagingClass(cls);
    }

    /**
     * Is primitive class.
     * 判断是否是基本类型
     *
     * @param cls the cls
     * @return the boolean
     * @author gungnirlaevatain
     */
    public static boolean isPrimitiveClass(Class<?> cls) {
        return cls.isPrimitive();
    }

    /**
     * Is packaging class.
     * 判断是否是包装类
     *
     * @param o the o
     *          源对象
     * @return the boolean
     * 是返回true，反之返回false
     */
    public static boolean isPackagingClass(Object o) {
        return isPackagingClass(o.getClass());
    }

    /**
     * Is packaging class.
     * 判断是否是包装类
     *
     * @param cls the cls
     *            源类
     * @return the boolean
     * 是返回true，反之返回false
     */
    public static boolean isPackagingClass(Class<?> cls) {
        if (cls != null && !cls.isArray() && !cls.isEnum()) {
            try {
                return ((Class) cls.getField("TYPE").get(null)).isPrimitive();
            } catch (IllegalAccessException | NoSuchFieldException ignored) {
            }
        }
        return false;
    }

    /**
     * Is java class.
     * 判断是否是java原生的类,如Integer,String等
     *
     * @param o the o
     *          源对象
     * @return the boolean
     * 是返回true，反之返回false
     */
    public static boolean isJavaClass(Object o) {
        return o != null && isJavaClass(o.getClass());
    }

    /**
     * Is java class.
     * 判断是否是java原生的类,如Integer,String等
     *
     * @param cls the cls
     *            源类
     * @return the boolean
     * 是返回true，反之返回false
     */
    public static boolean isJavaClass(Class<?> cls) {
        return cls != null && cls.getClassLoader() == null;
    }

    /**
     * Is enum.
     * 判断是否是枚举类型
     *
     * @param o the o
     *          源对象
     * @return the boolean
     * 是返回true，反之返回false
     */
    public static boolean isEnum(Object o) {
        return o.getClass().isEnum();
    }

    /**
     * Is array.
     * 判断是否是数组类型
     *
     * @param o the o
     *          源对象
     * @return the boolean
     * 是返回true，反之返回false
     */
    public static boolean isArray(Object o) {
        return o.getClass().isArray();
    }

    /**
     * Class for name.
     * 根据class名称加载并实例化
     *
     * @param className the class name
     *                  class名称，如com.a.b.c.d
     * @return the object
     * 对应类的实例化对象
     * @throws ClassNotFoundException the class not found exception
     * @throws IllegalAccessException the illegal access exception
     * @throws InstantiationException the instantiation exception
     */
    public static Object classForName(String className) throws ClassNotFoundException, IllegalAccessException,
            InstantiationException {
        return Class.forName(className).newInstance();
    }

    /**
     * Gets generic paradigm.
     * 获取该对象的所有的泛型类型
     *
     * @param o the o
     *          源对象
     * @return the generic paradigm
     */
    public static Set<Type> getGenericParadigm(Object o) {
        return getGenericParadigm(o.getClass());
    }

    /**
     * Gets generic paradigm.
     * 获取该类所有的泛型类型
     *
     * @param cls the cls
     *            源类
     * @return the generic paradigm
     */
    public static Set<Type> getGenericParadigm(Class<?> cls) {
        Set<Type> classSet = new HashSet<>();
        Type superClass = cls.getGenericSuperclass();

        if (ParameterizedType.class.isAssignableFrom(superClass.getClass())) {
            classSet.addAll(Arrays.asList(((ParameterizedType) superClass).getActualTypeArguments()));
        }

        Type[] interfaceClass = cls.getGenericInterfaces();

        for (Type type : interfaceClass) {
            if (ParameterizedType.class.isAssignableFrom(type.getClass())) {
                classSet.addAll(Arrays.asList(((ParameterizedType) type).getActualTypeArguments()));
            }
        }
        return classSet;
    }

    @SuppressWarnings("unchecked")
    public static <T> T convertBaseValue(Class<T> type, Object obj) {

        if (obj == null) {
            return null;
        }
        String source = String.valueOf(obj);
        if (type.isAssignableFrom(String.class)) {
            return type.cast(source);
        } else if (type == Boolean.class || type == boolean.class) {
            return (T) Boolean.valueOf(source);
        } else if (type == Character.class || type == char.class) {
            return (T) Character.valueOf(source.charAt(0));
        } else if (type == Byte.class || type == byte.class) {
            return (T) Byte.valueOf(source);
        } else if (type == Short.class || type == short.class) {
            return (T) Short.valueOf(source);
        } else if (type == Integer.class || type == int.class) {
            return (T) Integer.valueOf(source);
        } else if (type == Long.class || type == long.class) {
            return (T) Long.valueOf(source);
        } else if (type == Float.class || type == float.class) {
            return (T) Float.valueOf(source);
        } else if (type == Double.class || type == double.class) {
            return (T) Double.valueOf(source);
        } else {
            return null;
        }
    }

}