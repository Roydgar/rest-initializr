package tk.roydgar.restinitializr.sql.visitor.impl;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.statement.SQLCreateTableStatement;
import com.alibaba.druid.util.JdbcConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import tk.roydgar.restinitializr.sql.model.SQLTable;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class SQLCreateTableVisitorTest {

    private SQLCreateTableVisitor subject;

    private final static String sqlType = JdbcConstants.MYSQL;

    @Before
    public void setUp() {
        subject = new SQLCreateTableVisitor();
        subject.setTable(new SQLTable());

    }

    @Test
    public void visit_setsTableName() {
        SQLCreateTableStatement sqlStatement = (SQLCreateTableStatement)SQLUtils
                .parseSingleStatement("CREATE TABLE table_name (age INT)", sqlType);

        subject.visit(sqlStatement);

        assertThat(subject.getTable().getName()).isEqualTo("table_name");
    }

    @Test
    public void visit_setsTableNameAndTruncatesQuotes() {
        SQLCreateTableStatement sqlStatement = (SQLCreateTableStatement)SQLUtils
                .parseSingleStatement("CREATE TABLE `table_name` (age INT)", sqlType);

        subject.visit(sqlStatement);

        assertThat(subject.getTable().getName()).isEqualTo("table_name");
    }

}