package tk.roydgar.restinitializr.config;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import tk.roydgar.restinitializr.ui.gui.*;
import tk.roydgar.restinitializr.ui.gui.action.CheckConnectionAction;
import tk.roydgar.restinitializr.ui.gui.action.GenerateAction;
import tk.roydgar.restinitializr.ui.gui.action.NextPanelAction;
import tk.roydgar.restinitializr.ui.gui.action.ParseQueriesAction;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Profile("gui")
public class GUIConfig {

    private final SQLQueriesPanel sqlQueriesPanel;
    private final DataSourcePanel dataSourcePanel;
    private final OutputDirectoryPanel outputDirectoryPanel;
    private final InputPanel inputPanel;
    private final GenerateAction generateAction;
    private final ParseQueriesAction parseQueriesAction;
    private final CheckConnectionAction checkConnectionAction;
    private final Session session;
    private final GUIFrame guiFrame;

    @Bean
    public Map<GUIPanel, GUIPanel> currentPanelToNextPanelPagingMap () {
        return ImmutableMap.of(
                sqlQueriesPanel, inputPanel,
                inputPanel, dataSourcePanel,
                dataSourcePanel, outputDirectoryPanel
        );
    }

    @PostConstruct
    public void setJOptionPageWindowSize() {
        UIManager.put("OptionPane.minimumSize",new Dimension(300,100));
    }

    @PostConstruct
    public void configureActionListeners() {
        Map<GUIPanel, GUIPanel> currentPanelToNextPanelPagingMap = currentPanelToNextPanelPagingMap();
        checkConnectionAction.setExecutionCondition(s -> s.getValidationResult().isSuccess());
        parseQueriesAction.setExecutionCondition(s -> s.getValidationResult().isSuccess());
        generateAction.setFinishAction(() -> guiFrame.dispatchEvent(new WindowEvent(guiFrame, WindowEvent.WINDOW_CLOSING)));

        outputDirectoryPanel.addGenerateButtonActionListener(generateAction);
        dataSourcePanel.addCheckConnectionButtonActionListener(checkConnectionAction);
        sqlQueriesPanel.addNextButtonActionListener(parseQueriesAction, 1);

        sqlQueriesPanel.addNextButtonActionListener(
                new NextPanelAction(sqlQueriesPanel, currentPanelToNextPanelPagingMap.get(sqlQueriesPanel),
                session, guiFrame::setContentPane, s -> s.getSqlTables() != null && s.getValidationResult().isSuccess()), 0);
        sqlQueriesPanel.addNextButtonActionListener(s -> sqlQueriesPanel.validate(), Integer.MAX_VALUE);

        inputPanel.addNextButtonActionListener(
                new NextPanelAction(inputPanel, currentPanelToNextPanelPagingMap.get(inputPanel),
                session, guiFrame::setContentPane, s -> s.getValidationResult().isSuccess()), 0);
        inputPanel.addNextButtonActionListener(e -> inputPanel.validate(), Integer.MAX_VALUE);

        dataSourcePanel.addNextButtonActionListener(
                new NextPanelAction(dataSourcePanel, currentPanelToNextPanelPagingMap.get(dataSourcePanel),
                session, guiFrame::setContentPane, s -> s.getValidationResult().isSuccess()), 0);
        dataSourcePanel.addNextButtonActionListener(e -> dataSourcePanel.validate(), Integer.MAX_VALUE);
    }

}
