package tk.roydgar.restinitializr.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;

@Slf4j
@UtilityClass
public class UrlUtils {

    public static String createUrl(SpringInitializrParameters properties) {
        log.debug("Creating spring initializr request for properties {}", properties);
        URIBuilder uriBuilder = new URIBuilder()
                .setScheme("https")
                .setHost("start.spring.io")
                .setPath("/starter.zip")
                .addParameter("type", properties.getType())
                .addParameter("language", properties.getLanguage())
                .addParameter("bootVersion", properties.getBootVersion())
                .addParameter("baseDir", properties.getArtifactId())
                .addParameter("groupId", properties.getGroupId())
                .addParameter("artifactId", properties.getArtifactId())
                .addParameter("name", properties.getArtifactId())
                .addParameter("description", properties.getDescription())
                .addParameter("packageName", properties.getGroupId() + "." + properties.getArtifactId())
                .addParameter("packaging", properties.getPackaging())
                .addParameter("javaVersion", properties.getJavaVersion());

        properties.getDependencies().forEach(dependency -> uriBuilder.addParameter("style", dependency));
        return uriBuilder.toString();
    }
}
