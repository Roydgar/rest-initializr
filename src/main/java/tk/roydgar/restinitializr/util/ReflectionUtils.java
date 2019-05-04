package tk.roydgar.restinitializr.util;

import lombok.experimental.UtilityClass;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.TypeNames;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ReflectionUtils {

    public static List<Integer> getClassFieldsAsInts(Class<?> clazz, Class<?> fieldClazz) {
        return Arrays.asList(clazz.getDeclaredFields())
                .stream()
                .filter(field -> field.getClass().equals(fieldClazz))
                .map(field -> {
                    try {
                        return field.getInt(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Cannot fetch field from class " + clazz +
                                "with type " + fieldClazz);
                    }
                })
                .collect(Collectors.toList());

    }

    public TypeNames fetchTypeNames(Dialect instance) {
        try {
            Field field = instance.getClass().getDeclaredField("typeNames"); //NoSuchFieldException
            field.setAccessible(true);
            return (TypeNames) field.get(instance); //IllegalAccessException
        } catch (Exception e) {
            throw new RuntimeException("Cannot fetch private field instance", e);
        }
    }
}
