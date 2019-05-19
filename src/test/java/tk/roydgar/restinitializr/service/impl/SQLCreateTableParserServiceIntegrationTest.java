package tk.roydgar.restinitializr.service.impl;

import com.alibaba.druid.sql.parser.ParserException;
import com.alibaba.druid.util.JdbcConstants;
import org.assertj.core.api.JUnitSoftAssertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.roydgar.restinitializr.sql.model.SQLColumn;
import tk.roydgar.restinitializr.sql.model.SQLEnum;
import tk.roydgar.restinitializr.sql.model.SQLTable;
import tk.roydgar.restinitializr.sql.model.enums.SQLDialect;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
public class SQLCreateTableParserServiceIntegrationTest {

    private static final SQLDialect SQL_DIALECT = SQLDialect.MY_SQL;
    @Autowired
    private SQLCreateTableParserService subject;

    @Rule
    public JUnitSoftAssertions softly = new JUnitSoftAssertions();
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private final static String DATABASE_TYPE = JdbcConstants.MYSQL;

    @Test
    public void parseCreateQueries_parsesColumnWithNameAndType() {
        String query = "CREATE TABLE table_name (column_name VARCHAR)";

        SQLTable sqlTable = subject.parseCreateQuery(query, SQL_DIALECT);

        assertThat(sqlTable.getColumns()).hasSize(1);
        SQLColumn sqlColumn = sqlTable.getColumns().get(0);

        softly.assertThat(sqlTable.getName()).isEqualTo("table_name");
        softly.assertThat(sqlTable.getEnums()).isEmpty();
        softly.assertThat(sqlColumn.getName()).isEqualTo("column_name");
        softly.assertThat(sqlColumn.getDataType()).isEqualTo("varchar");
        softly.assertThat(sqlColumn.getDefaultValue()).isNull();
        softly.assertThat(sqlColumn.getLength()).isNull();
        softly.assertThat(sqlColumn.getPrecision()).isNull();
        softly.assertThat(sqlColumn.getScale()).isNull();
        softly.assertThat(sqlColumn.isNullable()).isEqualTo(true);
        softly.assertThat(sqlColumn.isAutoIncremental()).isEqualTo(false);
        softly.assertThat(sqlColumn.isUnique()).isEqualTo(false);
        softly.assertThat(sqlColumn.isPrimaryKey()).isEqualTo(false);
    }

    @Test
    public void parseCreateQueries_parsesNotNullConstraint() {
        String query = "CREATE TABLE table_name (column_name VARCHAR NOT NULL)";

        SQLTable sqlTable = subject.parseCreateQuery(query, SQL_DIALECT);

        assertThat(sqlTable.getColumns()).hasSize(1);
        SQLColumn sqlColumn = sqlTable.getColumns().get(0);

        softly.assertThat(sqlTable.getName()).isEqualTo("table_name");
        softly.assertThat(sqlTable.getEnums()).isEmpty();
        softly.assertThat(sqlColumn.getName()).isEqualTo("column_name");
        softly.assertThat(sqlColumn.getDataType()).isEqualTo("varchar");
        softly.assertThat(sqlColumn.getDefaultValue()).isNull();
        softly.assertThat(sqlColumn.getLength()).isNull();
        softly.assertThat(sqlColumn.getPrecision()).isNull();
        softly.assertThat(sqlColumn.getScale()).isNull();
        softly.assertThat(sqlColumn.isNullable()).isEqualTo(false);
        softly.assertThat(sqlColumn.isAutoIncremental()).isEqualTo(false);
        softly.assertThat(sqlColumn.isUnique()).isEqualTo(false);
        softly.assertThat(sqlColumn.isPrimaryKey()).isEqualTo(false);
    }

    @Test
    public void parseCreateQueries_parsesAutoIncrementConstraint() {
        String query = "CREATE TABLE table_name (column_name VARCHAR AUTO_INCREMENT)";

        SQLTable sqlTable = subject.parseCreateQuery(query, SQL_DIALECT);

        assertThat(sqlTable.getColumns()).hasSize(1);
        SQLColumn sqlColumn = sqlTable.getColumns().get(0);

        softly.assertThat(sqlTable.getName()).isEqualTo("table_name");
        softly.assertThat(sqlTable.getEnums()).isEmpty();
        softly.assertThat(sqlColumn.getName()).isEqualTo("column_name");
        softly.assertThat(sqlColumn.getDataType()).isEqualTo("varchar");
        softly.assertThat(sqlColumn.getDefaultValue()).isNull();
        softly.assertThat(sqlColumn.getLength()).isNull();
        softly.assertThat(sqlColumn.getPrecision()).isNull();
        softly.assertThat(sqlColumn.getScale()).isNull();
        softly.assertThat(sqlColumn.isNullable()).isEqualTo(true);
        softly.assertThat(sqlColumn.isAutoIncremental()).isEqualTo(true);
        softly.assertThat(sqlColumn.isUnique()).isEqualTo(false);
        softly.assertThat(sqlColumn.isPrimaryKey()).isEqualTo(false);
    }

    @Test
    public void parseCreateQueries_parsesUniqueConstraint() {
        String query = "CREATE TABLE table_name (column_name VARCHAR UNIQUE)";

        SQLTable sqlTable = subject.parseCreateQuery(query, SQL_DIALECT);

        assertThat(sqlTable.getColumns()).hasSize(1);
        SQLColumn sqlColumn = sqlTable.getColumns().get(0);

        softly.assertThat(sqlTable.getName()).isEqualTo("table_name");
        softly.assertThat(sqlTable.getEnums()).isEmpty();
        softly.assertThat(sqlColumn.getName()).isEqualTo("column_name");
        softly.assertThat(sqlColumn.getDataType()).isEqualTo("varchar");
        softly.assertThat(sqlColumn.getDefaultValue()).isNull();
        softly.assertThat(sqlColumn.getLength()).isNull();
        softly.assertThat(sqlColumn.getPrecision()).isNull();
        softly.assertThat(sqlColumn.getScale()).isNull();
        softly.assertThat(sqlColumn.isNullable()).isEqualTo(true);
        softly.assertThat(sqlColumn.isAutoIncremental()).isEqualTo(false);
        softly.assertThat(sqlColumn.isUnique()).isEqualTo(true);
        softly.assertThat(sqlColumn.isPrimaryKey()).isEqualTo(false);
    }

    @Test
    public void parseCreateQueries_parsesPrimaryKeyConstraint() {
        String query = "CREATE TABLE table_name (column_name VARCHAR PRIMARY KEY)";

        SQLTable sqlTable = subject.parseCreateQuery(query, SQL_DIALECT);

        assertThat(sqlTable.getColumns()).hasSize(1);
        SQLColumn sqlColumn = sqlTable.getColumns().get(0);

        softly.assertThat(sqlTable.getName()).isEqualTo("table_name");
        softly.assertThat(sqlTable.getEnums()).isEmpty();
        softly.assertThat(sqlColumn.getName()).isEqualTo("column_name");
        softly.assertThat(sqlColumn.getDataType()).isEqualTo("varchar");
        softly.assertThat(sqlColumn.getDefaultValue()).isNull();
        softly.assertThat(sqlColumn.getLength()).isNull();
        softly.assertThat(sqlColumn.getPrecision()).isNull();
        softly.assertThat(sqlColumn.getScale()).isNull();
        softly.assertThat(sqlColumn.isNullable()).isEqualTo(true);
        softly.assertThat(sqlColumn.isAutoIncremental()).isEqualTo(false);
        softly.assertThat(sqlColumn.isUnique()).isEqualTo(false);
        softly.assertThat(sqlColumn.isPrimaryKey()).isEqualTo(true);
    }

    @Test
    public void parseCreateQueries_parsesDefaultValueConstraint() {
        String query = "CREATE TABLE table_name (column_name VARCHAR DEFAULT 'default_value')";

        SQLTable sqlTable = subject.parseCreateQuery(query, SQL_DIALECT);

        assertThat(sqlTable.getColumns()).hasSize(1);
        SQLColumn sqlColumn = sqlTable.getColumns().get(0);

        softly.assertThat(sqlTable.getName()).isEqualTo("table_name");
        softly.assertThat(sqlTable.getEnums()).isEmpty();
        softly.assertThat(sqlColumn.getName()).isEqualTo("column_name");
        softly.assertThat(sqlColumn.getDataType()).isEqualTo("varchar");
        softly.assertThat(sqlColumn.getDefaultValue()).isEqualTo("default_value");
        softly.assertThat(sqlColumn.getLength()).isNull();
        softly.assertThat(sqlColumn.getPrecision()).isNull();
        softly.assertThat(sqlColumn.getScale()).isNull();
        softly.assertThat(sqlColumn.isNullable()).isEqualTo(true);
        softly.assertThat(sqlColumn.isAutoIncremental()).isEqualTo(false);
        softly.assertThat(sqlColumn.isUnique()).isEqualTo(false);
        softly.assertThat(sqlColumn.isPrimaryKey()).isEqualTo(false);
    }

    @Test
    public void parseCreateQuery_parsesSeparateConstrains() {
        String query = "\tCREATE TABLE table_name (\n" +
                "    column_name VARCHAR,\n" +
                "    UNIQUE (column_name),\n" +
                "    PRIMARY KEY (column_name)\n" +
                ");";

        SQLTable sqlTable = subject.parseCreateQuery(query, SQL_DIALECT);

        assertThat(sqlTable.getColumns()).hasSize(1);
        SQLColumn sqlColumn = sqlTable.getColumns().get(0);

        softly.assertThat(sqlTable.getName()).isEqualTo("table_name");
        softly.assertThat(sqlTable.getEnums()).isEmpty();
        softly.assertThat(sqlColumn.getName()).isEqualTo("column_name");
        softly.assertThat(sqlColumn.getDataType()).isEqualTo("varchar");
        softly.assertThat(sqlColumn.getDefaultValue()).isNull();
        softly.assertThat(sqlColumn.getLength()).isNull();
        softly.assertThat(sqlColumn.getPrecision()).isNull();
        softly.assertThat(sqlColumn.getScale()).isNull();
        softly.assertThat(sqlColumn.isNullable()).isEqualTo(true);
        softly.assertThat(sqlColumn.isAutoIncremental()).isEqualTo(false);
        softly.assertThat(sqlColumn.isUnique()).isEqualTo(true);
        softly.assertThat(sqlColumn.isPrimaryKey()).isEqualTo(true);
    }

    @Test
    public void parseCreateQuery_parsesColumnLength() {
        String query = "CREATE TABLE table_name (column_name VARCHAR(10))";

        SQLTable sqlTable = subject.parseCreateQuery(query, SQL_DIALECT);

        assertThat(sqlTable.getColumns()).hasSize(1);
        SQLColumn sqlColumn = sqlTable.getColumns().get(0);

        softly.assertThat(sqlTable.getName()).isEqualTo("table_name");
        softly.assertThat(sqlTable.getEnums()).isEmpty();
        softly.assertThat(sqlColumn.getName()).isEqualTo("column_name");
        softly.assertThat(sqlColumn.getDataType()).isEqualTo("varchar");
        softly.assertThat(sqlColumn.getDefaultValue()).isNull();
        softly.assertThat(sqlColumn.getLength()).isEqualTo(10);
        softly.assertThat(sqlColumn.getPrecision()).isNull();
        softly.assertThat(sqlColumn.getScale()).isNull();
        softly.assertThat(sqlColumn.isNullable()).isEqualTo(true);
        softly.assertThat(sqlColumn.isAutoIncremental()).isEqualTo(false);
        softly.assertThat(sqlColumn.isUnique()).isEqualTo(false);
        softly.assertThat(sqlColumn.isPrimaryKey()).isEqualTo(false);
    }

    @Test
    public void parseCreateQuery_parsesColumnPrecision() {
        String query = "CREATE TABLE table_name (column_name DOUBLE(10))";

        SQLTable sqlTable = subject.parseCreateQuery(query, SQL_DIALECT);

        assertThat(sqlTable.getColumns()).hasSize(1);
        SQLColumn sqlColumn = sqlTable.getColumns().get(0);

        softly.assertThat(sqlTable.getName()).isEqualTo("table_name");
        softly.assertThat(sqlTable.getEnums()).isEmpty();
        softly.assertThat(sqlColumn.getName()).isEqualTo("column_name");
        softly.assertThat(sqlColumn.getDataType()).isEqualTo("double");
        softly.assertThat(sqlColumn.getDefaultValue()).isNull();
        softly.assertThat(sqlColumn.getLength()).isNull();
        softly.assertThat(sqlColumn.getPrecision()).isEqualTo(10);
        softly.assertThat(sqlColumn.getScale()).isNull();
        softly.assertThat(sqlColumn.isNullable()).isEqualTo(true);
        softly.assertThat(sqlColumn.isAutoIncremental()).isEqualTo(false);
        softly.assertThat(sqlColumn.isUnique()).isEqualTo(false);
        softly.assertThat(sqlColumn.isPrimaryKey()).isEqualTo(false);
    }

    @Test
    public void parseCreateQuery_truncatesQuotes() {
        String query = "CREATE TABLE 'table_name' (`column_name` DOUBLE(10))";

        SQLTable sqlTable = subject.parseCreateQuery(query, SQL_DIALECT);

        assertThat(sqlTable.getColumns()).hasSize(1);
        SQLColumn sqlColumn = sqlTable.getColumns().get(0);

        softly.assertThat(sqlTable.getName()).isEqualTo("table_name");
        softly.assertThat(sqlTable.getEnums()).isEmpty();
        softly.assertThat(sqlColumn.getName()).isEqualTo("column_name");
        softly.assertThat(sqlColumn.getDataType()).isEqualTo("double");
        softly.assertThat(sqlColumn.getDefaultValue()).isNull();
        softly.assertThat(sqlColumn.getLength()).isNull();
        softly.assertThat(sqlColumn.getPrecision()).isEqualTo(10);
        softly.assertThat(sqlColumn.getScale()).isNull();
        softly.assertThat(sqlColumn.isNullable()).isEqualTo(true);
        softly.assertThat(sqlColumn.isAutoIncremental()).isEqualTo(false);
        softly.assertThat(sqlColumn.isUnique()).isEqualTo(false);
        softly.assertThat(sqlColumn.isPrimaryKey()).isEqualTo(false);
    }

    @Test
    public void parseCreateQuery_parsesColumnPrecisionAndScale() {
        String query = "CREATE TABLE table_name (column_name DOUBLE(10, 10))";

        SQLTable sqlTable = subject.parseCreateQuery(query, SQL_DIALECT);

        assertThat(sqlTable.getColumns()).hasSize(1);
        SQLColumn sqlColumn = sqlTable.getColumns().get(0);

        softly.assertThat(sqlTable.getName()).isEqualTo("table_name");
        softly.assertThat(sqlTable.getEnums()).isEmpty();
        softly.assertThat(sqlColumn.getName()).isEqualTo("column_name");
        softly.assertThat(sqlColumn.getDataType()).isEqualTo("double");
        softly.assertThat(sqlColumn.getDefaultValue()).isNull();
        softly.assertThat(sqlColumn.getLength()).isNull();
        softly.assertThat(sqlColumn.getPrecision()).isEqualTo(10);
        softly.assertThat(sqlColumn.getScale()).isEqualTo(10);
        softly.assertThat(sqlColumn.isNullable()).isEqualTo(true);
        softly.assertThat(sqlColumn.isAutoIncremental()).isEqualTo(false);
        softly.assertThat(sqlColumn.isUnique()).isEqualTo(false);
    }

    @Test
    public void parseCreateQuery_parsesEnumDefinitions() {
        String query = "CREATE TABLE table_name (column_name ENUM('value_1', 'value_2'))";

        SQLTable sqlTable = subject.parseCreateQuery(query, SQL_DIALECT);
        assertThat(sqlTable.getColumns()).hasSize(1);
        assertThat(sqlTable.getEnums()).hasSize(1);

        SQLColumn sqlColumn = sqlTable.getColumns().get(0);
        SQLEnum sqlEnum = sqlTable.getEnums().get(0);
        List<String> sqlEnumValues = sqlEnum.getValues();

        assertThat(sqlEnumValues).isNotNull();
        assertThat(sqlEnumValues).hasSize(2);

        softly.assertThat(sqlTable.getName()).isEqualTo("table_name");
        softly.assertThat(sqlColumn.getName()).isEqualTo("column_name");
        softly.assertThat(sqlColumn.getDataType()).isEqualTo("enum");
        softly.assertThat(sqlColumn.getDefaultValue()).isNull();
        softly.assertThat(sqlColumn.getLength()).isNull();
        softly.assertThat(sqlColumn.getPrecision()).isNull();
        softly.assertThat(sqlColumn.getScale()).isNull();
        softly.assertThat(sqlColumn.isNullable()).isEqualTo(true);
        softly.assertThat(sqlColumn.isAutoIncremental()).isEqualTo(false);
        softly.assertThat(sqlColumn.isUnique()).isEqualTo(false);

        softly.assertThat(sqlEnum.getName()).isEqualTo("column_name");
        softly.assertThat(sqlEnumValues.get(0)).isEqualTo("value_1");
        softly.assertThat(sqlEnumValues.get(1)).isEqualTo("value_2");
    }

    @Test
    public void parseCreateQuery_whenSqlCreateQueryIsNotValid_throwsCannotParseSqlQueryException () {
        String incorrectQuery = "CREATE TABLE test id INT, name VARCHAR)";

        expectedException.expect(ParserException.class);
        subject.parseCreateQuery(incorrectQuery, SQL_DIALECT);
    }
}