package com.vain.manager.log;

import org.apache.commons.lang.ArrayUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author vain
 * @date： 2017/11/3 11:12
 * @description：
 */
public class FieldsUtil {

    /**
     * 获取反射属性及属性值
     *
     * @param model
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Map<String, String> getFieldsByReflect(Object model) throws IllegalArgumentException, IllegalAccessException {
        Map<String, String> map = new HashMap<>();
        // 获取实体类的所有属性，返回Field数组
        Field[] fields = model.getClass().getDeclaredFields();
        // 递归获取父类所有属性
        Field[] field = getBeanFields(model.getClass(), fields);
        // 遍历所有属性
        for (Field data : field) {
            // 将字段的访问权限设为true：即去除private修饰符的影响
            data.setAccessible(true);
            map.put(data.getName(), data.get(model) == null ? "" : data.get(model).toString());
        }
        return map;
    }

    /**
     * 递归获取父类
     *
     * @param cls
     * @param fs
     * @return
     */
    @SuppressWarnings("rawtypes")
    private static Field[] getBeanFields(Class cls, Field[] fs) {
        fs = (Field[]) ArrayUtils.addAll(fs, cls.getDeclaredFields());
        if (cls.getSuperclass() != null) {
            Class clsSup = cls.getSuperclass();
            fs = getBeanFields(clsSup, fs);
        }
        return fs;
    }

    /**
     * 通过判断类型获取反射属性及值
     *
     * @param model
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws SecurityException
     * @throws NoSuchFieldException
     */
    public static Map<String, String> getReflectFieldByType(Object model) throws NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException, NoSuchFieldException {
        Map<String, String> map = new HashMap<>();
        // 获取实体类的所有属性，返回Field数组
        Field[] fields = model.getClass().getDeclaredFields();
        // 递归获取父类所有属性
        Field[] field = getBeanFields(model.getClass(), fields);
        // 遍历所有属性
        for (int j = 0; j < field.length; j++) {
            // 将字段的访问权限设为true：即去除private修饰符的影响
            field[j].setAccessible(true);
            // 获得其属性的修饰
            String descriptor = Modifier.toString(field[j].getModifiers());
            if (descriptor.contains("final")) {
                map.put(field[j].getName(), field[j].get(model) == null ? "" : field[j].get(model).toString());
                continue; // 静态常量不往下判断
            }
            // 获取属性名
            String name = field[j].getName();
            String _name = name.substring(0, 1).toUpperCase() + name.substring(1);
            // 获取属性的类型
            String type = field[j].getGenericType().toString();
            // 如果type是类类型，则前面包含"class "，后面跟类名
            if (type.equals("class java.lang.String")) {
                Method m = model.getClass().getMethod("get" + _name);
                // 调用getter方法获取属性值
                String value = (String) m.invoke(model);
                if (value != null) {
                    map.put(name, value);
                }
            }
            if (type.equals("class java.lang.Integer")) {
                Method m = model.getClass().getMethod("get" + _name);
                Integer value = (Integer) m.invoke(model);
                if (value != null) {
                    map.put(name, value.toString());
                }
            }
            if (type.equals("class java.lang.Long")) {
                Method m = model.getClass().getMethod("get" + _name);
                Long value = (Long) m.invoke(model);
                if (value != null) {
                    map.put(name, value.toString());
                }
            }
            if (type.equals("class java.lang.Short")) {
                Method m = model.getClass().getMethod("get" + _name);
                Short value = (Short) m.invoke(model);
                if (value != null) {
                    map.put(name, value.toString());
                }
            }
            if (type.equals("class java.lang.Double")) {
                Method m = model.getClass().getMethod("get" + _name);
                Double value = (Double) m.invoke(model);
                if (value != null) {
                    map.put(name, value.toString());
                }
            }
            if (type.equals("class java.lang.Boolean")) {
                Method m = model.getClass().getMethod("get" + _name);
                Boolean value = (Boolean) m.invoke(model);
                if (value != null) {
                    map.put(name, value.toString());
                }
            }
            if (type.equals("class java.util.Date")) {
                Method m = model.getClass().getMethod("get" + _name);
                Date value = (Date) m.invoke(model);
                if (value != null) {
                    map.put(name, value.toString());
                }
            }

        }
        return map;
    }
}
