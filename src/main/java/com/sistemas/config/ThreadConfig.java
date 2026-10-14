package com.sistemas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;
@Configuration 
public class ThreadConfig {

    @Bean("apiExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);     // hilos mínimos 
        executor.setMaxPoolSize(10);     // hilos máximos 
        executor.setQueueCapacity(50);   // cola de tareas 
        executor.setThreadNamePrefix("API-Hilo-"); // 
        executor.initialize();
        return executor;
    }
    
}
