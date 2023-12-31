package m.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "entityManageFactory",
        basePackages = "m.user.repo")
public class UserDBconfig {
    @Primary
    @Bean(name = "datasource")
    @ConfigurationProperties(prefix = "spring.user.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean
    localContainerEntityManagerFactoryBean(EntityManagerFactoryBuilder builder,
                                           @Qualifier("datasource") DataSource dataSource) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MYSQL5Dialect");
        return
                builder
                        .dataSource(dataSource)
                        .properties(properties)
                        .packages("m.entity.user")
                        .persistenceUnit("User")
                        .build();
    }

    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return null;
    }


}
