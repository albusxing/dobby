package com.albusxing.dobby.core.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * Mybatis-Plus 配置
 * @author liguoqing
 */
@Slf4j
@Configuration
@ConditionalOnClass(MetaObjectHandler.class)
@MapperScan(basePackages = "com.albusxing.dobby.domain.mapper")
public class MybatisPlusConfig {

	/**
	 * 配置分页插件
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
		interceptor.addInnerInterceptor(paginationInnerInterceptor);
		return interceptor;
	}

	@Bean
	public MetaObjectHandler metaObjectHandler() {
		return new MetaObjectHandler() {
			@Override
			public void insertFill(MetaObject metaObject) {
				log.info("start insert fill ....");
				this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
				this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
			}

			@Override
			public void updateFill(MetaObject metaObject) {
				log.info("start update fill ....");
				this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
			}
		};
	}

}
