/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities;

import co.sigess.entities.sec.AnalisisDesviacion;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 *
 * @author fmoreno
 */
public class ConvertidorDTO {

    public static <T, E> E toDTO(T entity, Class<E> dtoClass) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        E dtoInstance = dtoClass.newInstance();
        Class<T> clazz = (Class<T>) entity.getClass();
        for (Method method : clazz.getMethods()) {
            CampoDTO cdto = method.getAnnotation(CampoDTO.class);
            if (cdto != null) {
                Field dtoField = dtoClass.getDeclaredField(cdto.referencia());
                if (dtoField != null) {
                    dtoField.setAccessible(true);
                    dtoField.set(dtoInstance, method.invoke(entity));
                }
            }
        }
        return dtoInstance;
    }

    public static <E, T> T toEntity(E dtoInstance, Class<T> entityClass) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        T entity = entityClass.newInstance();
        Class<E> clazz = (Class<E>) dtoInstance.getClass();
        for (Method method : clazz.getMethods()) {
            CampoDTO cdto = method.getAnnotation(CampoDTO.class);
            if (cdto != null) {
                Field entityField = entityClass.getDeclaredField(cdto.referencia());
                if (entityField != null) {
                    entityField.setAccessible(true);
                    entityField.set(entity, method.invoke(dtoInstance));
                }
            }
        }
        return entity;
    }

}
