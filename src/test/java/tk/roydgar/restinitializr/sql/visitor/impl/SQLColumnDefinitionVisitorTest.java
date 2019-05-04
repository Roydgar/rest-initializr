package tk.roydgar.restinitializr.sql.visitor.impl;

import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import tk.roydgar.restinitializr.sql.model.SQLColumn;
import tk.roydgar.restinitializr.sql.model.SQLEnum;
import tk.roydgar.restinitializr.sql.model.SQLTable;
import tk.roydgar.restinitializr.sql.model.enums.SQLDialect;
import tk.roydgar.restinitializr.sql.resolver.SQLColumnResolver;
import tk.roydgar.restinitializr.sql.resolver.SQLEnumResolver;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SQLColumnDefinitionVisitorTest {

    private static final SQLDialect SQL_DIALECT = SQLDialect.MY_SQL;
    @MockBean
    private SQLColumnResolver columnResolver;
    @MockBean
    private SQLEnumResolver enumResolver;

    private SQLColumnDefinitionVisitor subject;

    @Before
    public void setUp()  {
        subject = new SQLColumnDefinitionVisitor(columnResolver, enumResolver);
        subject.setTable(new SQLTable());
        subject.setSqlDialect(SQL_DIALECT);
    }

    @Test
    public void visit_delegatesToSQLColumnResolver() {
        SQLColumnDefinition sqlColumnDefinition = new SQLColumnDefinition();
        SQLColumn resolvedColumn = new SQLColumn();

        when(enumResolver.execute(any(SQLColumnDefinition.class))).thenReturn(Optional.empty());
        when(columnResolver.execute(any(SQLColumnDefinition.class), any(SQLDialect.class))).thenReturn(resolvedColumn);

        subject.visit(sqlColumnDefinition);
        SQLTable table = subject.getTable();

        assertThat(table.getEnums()).isEmpty();
        assertThat(table.getColumns()).hasSize(1);
        assertThat(table.getColumns().get(0)).isEqualTo(resolvedColumn);

        verify(columnResolver).execute(sqlColumnDefinition, SQL_DIALECT);
        verify(enumResolver).execute(sqlColumnDefinition);
    }

    @Test
    public void visit_delegatesToSQLColumnResolverAndEnumResolver() {
        SQLColumnDefinition sqlColumnDefinition = new SQLColumnDefinition();
        SQLColumn resolvedColumn = new SQLColumn();
        SQLEnum resolvedEnum = new SQLEnum();

        when(enumResolver.execute(any(SQLColumnDefinition.class))).thenReturn(Optional.of(resolvedEnum));
        when(columnResolver.execute(any(SQLColumnDefinition.class), any(SQLDialect.class))).thenReturn(resolvedColumn);

        subject.visit(sqlColumnDefinition);
        SQLTable table = subject.getTable();

        assertThat(table.getEnums()).hasSize(1);
        assertThat(table.getEnums().get(0)).isEqualTo(resolvedEnum);
        assertThat(table.getColumns()).hasSize(1);
        assertThat(table.getColumns().get(0)).isEqualTo(resolvedColumn);

        verify(columnResolver).execute(sqlColumnDefinition, SQL_DIALECT);
        verify(enumResolver).execute(sqlColumnDefinition);
    }
}