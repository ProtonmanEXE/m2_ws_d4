package d4.protonmanexe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;

@Configuration
@Scope("singleton")
// @EnableConfigurationProperties(RedisProperties.class)
public class RedisInteraction { // cannot be the same name as RedisTemplate
       
    @Value("${spring.redis.host}") 
    private String redisHost;

    @Value("${spring.redis.port}") 
    private Optional<Integer> redisPort;

    @Value("${spring.redis.password}") 
    private String redisPassword;
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort.get());
        config.setPassword(redisPassword);

        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisFac = new JedisConnectionFactory(config, jedisClient); 
        jedisFac.afterPropertiesSet(); // allows the bean instance to perform validation of its 
                                       // overall configuration and final initialization when all 
                                       // bean properties have been set.
                    
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(jedisFac);

        template.setKeySerializer(new StringRedisSerializer()); 
        RedisSerializer<Object> serializer = 
            new JdkSerializationRedisSerializer(getClass().getClassLoader());
        template.setValueSerializer(serializer); // 

        return template;
    }
    
}
// RedisTemplate is the central class to interact with the Redis data. It performs automatic 
// serialization and deserialization between the given objects and binary data stored in Redis. 
// By default RedisTemplate uses Java serialization. This class is thread-safe once configured.