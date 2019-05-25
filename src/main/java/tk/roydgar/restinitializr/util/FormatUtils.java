package tk.roydgar.restinitializr.util;

import com.google.common.base.CaseFormat;
import lombok.experimental.UtilityClass;
import tk.roydgar.restinitializr.model.validation.ValidationParameterEntity;
import tk.roydgar.restinitializr.sql.model.SQLColumn;

import java.util.List;

@UtilityClass
public class FormatUtils {

    private final static String TO_REMOVE_REGEX = "[`'\"]";

    public static String deleteQuotes(String target) {
        return target.replaceAll(TO_REMOVE_REGEX, "");
    }

    public static String snakeToCamelCase(String target) {
        String cased = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, target);
        return Character.toLowerCase(cased.charAt(0)) + cased.substring(1);
    }

    public static String formatArtifactIdToProjectPackage(String artifactId) {
        return artifactId.replaceAll("-", "");
    }

    public static String formatValidationParameters(List<ValidationParameterEntity> validationParameters) {
        StringBuilder formattedParameters = new StringBuilder();
        for (ValidationParameterEntity validationParameter: validationParameters) {
            formattedParameters
                    .append(formatParameter(validationParameter.getParameter().getParameterName(),
                            validationParameter.getValue()));
        }
        String result = formattedParameters.toString();
        return result.substring(0, result.length() - 2);
    }

    public static String formatColumnParameters(SQLColumn sqlColumn) {
        StringBuilder formattedParameters = new StringBuilder();

        if (sqlColumn.isUnique()) {
            formattedParameters.append(formatParameter("unique", true));
        }
        if (!sqlColumn.isNullable()) {
            formattedParameters.append(formatParameter("nullable", false));
        }
        if (sqlColumn.getLength() != null) {
            formattedParameters.append(formatParameter("length", sqlColumn.getLength()));
        }
        if (sqlColumn.getPrecision() != null) {
            formattedParameters.append(formatParameter("precision", sqlColumn.getPrecision()));
        }
        if (sqlColumn.getScale() != null) {
            formattedParameters.append(formatParameter("scale", sqlColumn.getScale()));
        }

        String result = formattedParameters.toString();
        return result.isEmpty() ? "" : result.substring(0, result.length() - 2);
    }

    private static String formatParameter(String name, Object value) {
        return name + " = " + value + ", ";
    }
}
