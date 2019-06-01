package tk.roydgar.restinitializr.ui.gui.action;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import tk.roydgar.restinitializr.model.SQLInputQueries;
import tk.roydgar.restinitializr.service.Generator;
import tk.roydgar.restinitializr.sql.model.SQLTable;
import tk.roydgar.restinitializr.ui.gui.Session;
import tk.roydgar.restinitializr.ui.gui.composer.UserInputComposer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class ParseQueriesAction implements ActionListener {

    private final UserInputComposer userInputComposer;
    private final Generator generator;
    private final Session session;
    @Setter
    private Predicate<Session> executionCondition;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!executionCondition.test(session)) {
            return;
        }
        SQLInputQueries sqlInputQueries = userInputComposer.composeSQLCreateTableQueries();
        try {
            List<SQLTable> sqlTables = generator.parseQueries(sqlInputQueries);
            session.setSqlTables(sqlTables);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null, exception.toString(), "Paring error"
                    , JOptionPane.ERROR_MESSAGE);

        }
    }

}
