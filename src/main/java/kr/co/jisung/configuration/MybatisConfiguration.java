package kr.co.jisung.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * mybatis 는 application.properties 에 설정 불가)
 * Mybatis 설정
 */
@Configuration
//@MapperScan 아노테이션은 지정된 베이스 패키지에서 DAO(Mapper) 를 검색하여 등록합니다
@MapperScan(basePackages = "kr.co.jisung.mvc.repository")
public class MybatisConfiguration {
	
	/*
	 * 마이바티스에서는 SqlSession를 생성하기 위해 SqlSessionFactory를 사용한다.
	 * SqlSessionFactory는 데이터베이스와의 연결과 SQL의 실행에 대한 모든 것을 가진 가장 중요한 객체이다.
	 * SqlSessionFactory를 생성해주는 특별한 객체를 먼저 설정해주어야하는데, 
	 * 이때 스프링-마이바티스에서는 SqlSessionFactoryBean이라는 클래스를 사용한다
	 */
	@Bean																/*DatabaseConfig에서 만든 DataSource주입*/
	public SqlSessionFactory sqlSessionFactory(@Autowired DataSource dataSource,ApplicationContext applicationContext) throws Exception{
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource);
		/*마이바티스 XML 파일의 위치*/
		factoryBean.setMapperLocations(applicationContext.getResources("classpath:mysql/*.xml"));
		SqlSessionFactory factory = factoryBean.getObject();
		factory.getConfiguration().setMapUnderscoreToCamelCase(true);
		return factoryBean.getObject();
	}
	
	//SqlSessionTemplate은 SqlSession을 구현하고 코드에서 SqlSession을 대체하는 역활
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(@Autowired SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
