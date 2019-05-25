package tk.roydgar.restinitializr.model.enums;

import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

import static tk.roydgar.restinitializr.model.enums.ValidationAnnotation.*;

@AllArgsConstructor
@Getter
public enum JavaType {

    BOOLEAN("Boolean", "java.lang.Boolean", ImmutableList.of(ASSERT_FALSE, ASSERT_TRUE, NULL)),

    INTEGER("Integer", "java.lang.Integer", ImmutableList.of(DECIMAL_MAX, DECIMAL_MIN, DIGITS,
            MAX, MIN, NEGATIVE, POSITIVE, POSITIVE_OR_ZERO, NEGATIVE_OR_ZERO, NULL)),

    LONG("Long", "java.lang.Long", ImmutableList.of(DECIMAL_MAX, DECIMAL_MIN, DIGITS, DIGITS, MAX, MIN,
            NEGATIVE, POSITIVE, POSITIVE_OR_ZERO, NEGATIVE_OR_ZERO, NULL)),

    BIG_DECIMAL("BigDecimal", "java.math.BigDecimal", ImmutableList.of(DECIMAL_MAX, DECIMAL_MIN, DIGITS, MAX, MIN,
            NEGATIVE, POSITIVE, POSITIVE_OR_ZERO, NEGATIVE_OR_ZERO, NULL)),

    DOUBLE("Double", "java.lang.Double", ImmutableList.of(NEGATIVE, POSITIVE, POSITIVE_OR_ZERO,
            NEGATIVE_OR_ZERO, NULL)),

    LOCAL_DATE("LocalDate", "java.time.LocalDate",
            ImmutableList.of(PAST, PAST_OR_PRESENT, FUTURE, FUTURE_OR_PRESENT, NULL)),

    LOCAL_TIME("LocalTime", "java.time.LocalTime",
            ImmutableList.of(PAST, PAST_OR_PRESENT, FUTURE, FUTURE_OR_PRESENT, NULL)),

    LOCAL_DATE_TIME("LocalDateTime", "java.time.LocalDateTime",
            ImmutableList.of(PAST, PAST_OR_PRESENT, FUTURE, FUTURE_OR_PRESENT, NULL)),

    STRING("String", "java.lang.String", ImmutableList.of(DECIMAL_MAX, DECIMAL_MIN, DIGITS, NOT_BLANK,
            NOT_EMPTY, PATTERN, SIZE, NULL)),
    BYTE_ARR("byte[]", "", ImmutableList.of(NOT_EMPTY, NULL)),
    OBJECT("Object", "java.lang.Object", ImmutableList.of(NULL));

    private String label;
    private String importPath;
    private List<ValidationAnnotation> validations;

    public boolean isImportNeeded() {
        return importPath != null && !importPath.isEmpty() && !importPath.contains(".lang.");
    }

}
