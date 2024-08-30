package com.personalize.common.configuration;

import com.personalize.common.utils.id.SnowflakeIdGenerate;
import com.personalize.common.utils.id.IdGenerate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * Mybatis 常用重用拦截器
 *
 */
public abstract class BaseMybatisConfiguration {

    /**
     * id生成 机器码， 单机配置1即可。 集群部署，每个实例自增1即可。
     *
     * @param machineCode
     * @return
     */
    @Bean("snowflakeIdGenerate")
    public IdGenerate getIdGenerate(@Value("${id-generator.machine-code:1}") Long machineCode) {
        return new SnowflakeIdGenerate(machineCode);
    }

}
