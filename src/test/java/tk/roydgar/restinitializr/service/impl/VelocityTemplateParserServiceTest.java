package tk.roydgar.restinitializr.service.impl;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import tk.roydgar.restinitializr.config.properties.TemplateProperties;
import tk.roydgar.restinitializr.exception.CannotParseTemplateException;
import tk.roydgar.restinitializr.model.enums.template.TemplateKey;
import tk.roydgar.restinitializr.model.enums.template.TemplateType;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@SuppressWarnings("unchecked")
public class VelocityTemplateParserServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();
    @Captor
    private ArgumentCaptor<VelocityContext> velocityContextCaptor;
    @Captor
    private ArgumentCaptor<String> stringCaptor;


    @MockBean
    private VelocityEngine velocityEngine;
    @Autowired
    private TemplateProperties templateProperties;

    private VelocityTemplateParserService subject;

    @Before
    public void setUp() {
        this.subject = new VelocityTemplateParserService(velocityEngine, templateProperties);
    }

    @Test
    public void parseTemplate_whenTemplateIsPresentAndCorrect_returnsTemplateMergingResult() throws IOException{
        Map<String, Object> contextContent = Collections.singletonMap("entity", "test");
        String expectedResult = "testResult";

        Template template = mock(Template.class);
        when(velocityEngine.getTemplate(anyString())).thenReturn(template);
        doAnswer((InvocationOnMock invocation) -> {
            StringWriter writer = invocation.getArgument(1);
            writer.append(expectedResult);
            return null;
        }).when(template).merge(ArgumentMatchers.any(VelocityContext.class), ArgumentMatchers.any(Writer.class));

        InputStream actual = subject.parseTemplate(TemplateType.SERVICE, contextContent);
        String actualAsString = IOUtils.toString(actual, Charset.defaultCharset());

        verify(template).merge(ArgumentMatchers.any(VelocityContext.class), ArgumentMatchers.any(StringWriter.class));
        assertThat(actualAsString).isEqualTo(expectedResult);
    }

    @Test
    public void parseTemplate_whenTemplateIsPresentAndCorrect_preparesContext() {
        Map<String, Object> contextContent = Collections.singletonMap("entity", "test");
        String entityKey = templateProperties.getTemplateKeyToNameMap().get(TemplateKey.ENTITY);

        Template template = mock(Template.class);
        when(velocityEngine.getTemplate(anyString())).thenReturn(template);
        doNothing().when(template).merge(ArgumentMatchers.any(VelocityContext.class), ArgumentMatchers.any(Writer.class));

        subject.parseTemplate(TemplateType.SERVICE, contextContent);

        verify(template).merge(velocityContextCaptor.capture(), ArgumentMatchers.any(StringWriter.class));
        VelocityContext actualContext = velocityContextCaptor.getValue();

        softly.assertThat(actualContext.get(entityKey)).isEqualTo("test");
    }

    @Test
    public void parseTemplate_preparesTemplatePath() {
        Template template = mock(Template.class);
        when(velocityEngine.getTemplate(anyString())).thenReturn(template);
        doNothing().when(template).merge(ArgumentMatchers.any(VelocityContext.class), ArgumentMatchers.any(Writer.class));

        subject.parseTemplate(TemplateType.SERVICE, Collections.emptyMap());

        verify(template).merge(ArgumentMatchers.any(VelocityContext.class), ArgumentMatchers.any(StringWriter.class));
        verify(velocityEngine).getTemplate(stringCaptor.capture());

        assertThat(stringCaptor.getValue()).isEqualTo(
                File.separator
                + templateProperties.getTemplatesRelativePath()
                + File.separator
                + templateProperties.getTemplateTypeToFileNameMap().get(TemplateType.SERVICE)
                + "."
                + templateProperties.getTemplateExtension()
        );
    }

    @Test
    public void parseTemplate_whenTemplateIsNull_throwsCannotParseTemplateException() {
        when(velocityEngine.getTemplate(anyString())).thenReturn(null);

        expectedException.expect(CannotParseTemplateException.class);
        expectedException.expectMessage(" Cannot parse or find template in path: \\templates\\null.template");

        subject.parseTemplate(TemplateType.SERVICE, Collections.emptyMap());
    }

}