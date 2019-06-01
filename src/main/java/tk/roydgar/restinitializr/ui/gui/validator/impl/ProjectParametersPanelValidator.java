package tk.roydgar.restinitializr.ui.gui.validator.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.config.properties.GUIProperties;
import tk.roydgar.restinitializr.ui.gui.InputPanel;
import tk.roydgar.restinitializr.ui.gui.validator.entity.ValidationResult;

import javax.annotation.PostConstruct;
import java.util.regex.Pattern;

import static java.lang.String.format;
import static tk.roydgar.restinitializr.util.ValidationUtils.emptyOrLessThenSize;
import static tk.roydgar.restinitializr.util.ValidationUtils.emptyOrMatchesPattern;

@Component
@RequiredArgsConstructor
public class ProjectParametersPanelValidator extends CommonPanelInputValidator {

    private final InputPanel inputPanel;
    private final GUIProperties guiProperties;

    @PostConstruct
    public void initValidators() {
        int maxLength = guiProperties.getMaxStringInputLength();
        int defaultLength = guiProperties.getDefaultStringInputLength();
        String emptyOrTooLongFormat = guiProperties.getEmptyOrTooLongFormat();
        String javaVersionPattern = guiProperties.getJavaVersionPattern();

        validators.add(() -> new ValidationResult(emptyOrLessThenSize(inputPanel.getArtifactId(), defaultLength),
                format(emptyOrTooLongFormat, "Artifact id", defaultLength)));
        validators.add(() -> new ValidationResult(emptyOrLessThenSize(inputPanel.getGroupId(), defaultLength),
                format(emptyOrTooLongFormat, "Group id", defaultLength)));
        validators.add(() -> new ValidationResult(emptyOrLessThenSize(inputPanel.getServerPort(), defaultLength),
                format(emptyOrTooLongFormat, "Server port", defaultLength)));
        validators.add(() -> new ValidationResult(emptyOrMatchesPattern(inputPanel.getJavaVersion(), Pattern.compile(javaVersionPattern)),
                format(emptyOrTooLongFormat, "Java version", javaVersionPattern)));
        validators.add(() -> new ValidationResult(emptyOrLessThenSize(inputPanel.getDescription(), maxLength),
                format(emptyOrTooLongFormat, "Description", maxLength)));
    }

}
