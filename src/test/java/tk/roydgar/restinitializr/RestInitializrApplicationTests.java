package tk.roydgar.restinitializr;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import org.hibernate.dialect.MySQL8Dialect;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.roydgar.restinitializr.client.SpringInitializrClient;
import tk.roydgar.restinitializr.model.SpringInitializrParameters;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RestInitializrApplicationTests {

	@Autowired
	private SpringInitializrClient client;

	@Test
	public void anotherTest(){
		//https://dev.mysql.com/doc/refman/8.0/en/string-types.html
		//https://stackoverflow.com/questions/939673/how-is-a-blob-column-annotated-in-hibernate
		//to lower case?
		String sql = "create table `phone_numbers` (\n" +
				"  `id` int unsigned not null auto_increment primary key,\n" +
				"  `user_id` int unsigned not NULL DEFAULT 5 UNIQUE,\n" +
				"  `phone_number` decimal(4,2)  not null,\n" +
				"  index pn_user_index(`user_id`),\n" +
				"size ENUM('x-small', 'small', 'medium', 'large', 'x-large'), unique(id), \n" +
				"UNIQUE(id), \n" +
				"  foreign key (`user_id`, `user_name`) references users(`id`, `name`) on delete cascade,\n" +
				"  primary key(`id`)\n" +
				");";

		SQLStatement statement = SQLUtils.parseSingleMysqlStatement(sql);
		System.out.println(statement);
	}

	@Test
	@Ignore
	public void testCall() {
		SpringInitializrParameters parameters = new SpringInitializrParameters();
		parameters.setGroupId("tk.roydgar");
		parameters.setArtifactId("myapp");
		parameters.setBootVersion("2.1.4.RELEASE");
		parameters.setJavaVersion("1.8");
		parameters.setLanguage("java");
		parameters.setPackaging("jar");
		parameters.setType("maven-project");

		client.getSpringStarterContent(parameters);

	}

	@Test
	public void name() {
		MySQL8Dialect mySQLDialect = new MySQL8Dialect();
		System.out.println(mySQLDialect);
	}
}

