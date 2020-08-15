package com.seata.demo.config;

import com.alibaba.druid.proxy.jdbc.DataSourceProxy;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.seata.demo.mapper.goodsdb", sqlSessionTemplateRef = "goodsdbSqlSessionTemplate")
public class GoodsdbSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.druid.goodsdb")
    public DataSource goodsdbDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public SqlSessionFactory goodsdbSqlSessionFactory(@Qualifier("goodsdbDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //bean.setDataSource();
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/goodsdb/*.xml"));
        return bean.getObject();
    }

    @Bean
    @Primary
    public DataSourceTransactionManager goodsdbTransactionManager(@Qualifier("goodsdbDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Primary
    public SqlSessionTemplate goodsdbSqlSessionTemplate(@Qualifier("goodsdbSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}