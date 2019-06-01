package tk.roydgar.restinitializr.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

@UtilityClass
public class ValidationUtils {

    public static boolean emptyOrLessThenSize(String target, int size) {
        return !StringUtils.isEmpty(target) && target.length() <= size;
    }

    public static boolean emptyOrMatchesPattern(String target, Pattern pattern) {
        return !StringUtils.isEmpty(target) && pattern.matcher(target).matches();
    }

}
