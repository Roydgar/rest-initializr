package tk.roydgar.restinitializr.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FormatUtils {

    private final static String TO_REMOVE_REGEX = "[`'\"]";

    public static String deleteQuotes(String target) {
        return target.replaceAll(TO_REMOVE_REGEX, "");
    }

    public static String formatArtifactIdToProjectPackage(String artifactId) {
        return artifactId.replaceAll("-", "");
    }
}
