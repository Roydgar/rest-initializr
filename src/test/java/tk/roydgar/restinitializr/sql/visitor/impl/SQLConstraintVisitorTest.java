package tk.roydgar.restinitializr.sql.visitor.impl;

import com.alibaba.druid.sql.ast.statement.SQLColumnPrimaryKey;
import com.alibaba.druid.sql.ast.statement.SQLColumnUniqueKey;
import com.alibaba.druid.sql.ast.statement.SQLNotNullConstraint;
import com.alibaba.druid.sql.ast.statement.SQLNullConstraint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class SQLConstraintVisitorTest {

    private SQLConstraintVisitor subject;
    @Before
    public void setUp() {
        subject = new SQLConstraintVisitor();
    }

    @Test
    public void visitSQLNotNullConstraint() {
        subject.visit(new SQLNotNullConstraint());
        assertThat(subject.isNullable()).isFalse();
    }

    @Test
    public void visitSQLNullConstraint() {
        subject.visit(new SQLNullConstraint());
        assertThat(subject.isNullable()).isTrue();
    }

    @Test
    public void visitSQLColumnUniqueKey() {
        subject.visit(new SQLColumnUniqueKey());
        assertThat(subject.isUnique()).isTrue();
    }

    @Test
    public void visitSQLColumnPrimaryKey() {
        subject.visit(new SQLColumnPrimaryKey());
        assertThat(subject.isPrimaryKey()).isTrue();
    }
}