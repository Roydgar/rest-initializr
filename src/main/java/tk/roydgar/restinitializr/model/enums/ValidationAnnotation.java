package tk.roydgar.restinitializr.model.enums;

import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum ValidationAnnotation {

    ASSERT_FALSE("@AssertFalse", ImmutableList.of(), ""),
    ASSERT_TRUE("@AssertTrue", ImmutableList.of(), ""),
    DECIMAL_MAX("@DecimalMax", ImmutableList.of(new Parameter("value", ""), new Parameter("inclusive", "")), ""),
    DECIMAL_MIN("@DecimalMin", ImmutableList.of(new Parameter("value", ""), new Parameter("inclusive", "")), ""),
    DIGITS("@Digits", ImmutableList.of(new Parameter("integer", ""), new Parameter("fraction", "")), ""),
    FUTURE("@Future", ImmutableList.of(), ""),
    FUTURE_OR_PRESENT("@FutureOrPresent", ImmutableList.of(), ""),
    MAX("@Max", ImmutableList.of(new Parameter("value", "")), ""),
    MIN("@Min",  ImmutableList.of(new Parameter("value", "")), ""),
    NEGATIVE("@Negative", ImmutableList.of(), ""),
    NEGATIVE_OR_ZERO("@NegativeOrZero", ImmutableList.of(), ""),
    NOT_BLANK("@NotBlank", ImmutableList.of(), ""),
    NOT_EMPTY("@NotEmpty", ImmutableList.of(), ""),
    NULL("@Null", ImmutableList.of(), ""),
    PAST("@Past", ImmutableList.of(), ""),
    PAST_OR_PRESENT("@PastOrPresent", ImmutableList.of(), ""),
    PATTERN("@Pattern", ImmutableList.of(new Parameter("regexp", "")), ""),
    POSITIVE("@Positive", ImmutableList.of(), ""),
    POSITIVE_OR_ZERO("@PositiveOrZero", ImmutableList.of(), ""),
    SIZE("@Size", ImmutableList.of(new Parameter("min", ""), new Parameter("max", "")), "");
    
    private String label;
    private List<Parameter> parameters;
    private String description;

    @Getter
    @AllArgsConstructor
    public static class Parameter {
        private final String message = "message";
        private String parameterName;
        private String description;
    }

}
