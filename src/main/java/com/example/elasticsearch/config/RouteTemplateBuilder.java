package com.example.elasticsearch.config;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.apache.camel.spring.spi.TransactionErrorHandlerBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.example.elasticsearch.common.dto.RouteProperties;



/**
 * RouteTemplate의 추상 클래스. 
 * EndpointRouteBuilder에 SpringRouteBuilder의 lookup method를 사용하기 위해 구현
 */
public abstract class RouteTemplateBuilder extends EndpointRouteBuilder implements ApplicationContextAware {
	
	private ApplicationContext applicationContext;

	/**
     * Looks up the bean with the given name in the application context and
     * returns it, or throws an exception if the bean is not present or is not
     * of the given type
     *
     * @param beanName the name of the bean in the application context
     * @param type the type of the bean
     * @return the bean
     */
    public <T> T lookup(String beanName, Class<T> type) {
        ApplicationContext context = getApplicationContext();
        return context.getBean(beanName, type);
    }

    /**
     * Looks up the bean with the given type in the application context and
     * returns it, or throws an exception if the bean is not present or there
     * are multiple possible beans to choose from for the given type
     *
     * @param type the type of the bean
     * @return the bean
     */
    public <T> T lookup(Class<T> type) {
        ApplicationContext context = getApplicationContext();
        return context.getBean(type);
    }

    /**
     * Returns the application context which has been configured via the
     * {@link #setApplicationContext(ApplicationContext)} method or from the
     * underlying {@link SpringCamelContext}
     */
    public ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            CamelContext camelContext = getContext();
            if (camelContext instanceof SpringCamelContext) {
                SpringCamelContext springCamelContext = (SpringCamelContext)camelContext;
                return springCamelContext.getApplicationContext();
            } else {
                throw new IllegalArgumentException("This SpringBuilder is not being used with a SpringCamelContext and there is no applicationContext property configured");
            }
        }
        return applicationContext;
    }

    /**
     * Sets the application context to use to lookup beans
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Creates a transaction error handler that will lookup in application context for
     * an exiting transaction manager.
     *
     * @return the created error handler
     */
    public TransactionErrorHandlerBuilder transactionErrorHandler() {
        return new TransactionErrorHandlerBuilder();
    }

    /**
     * Creates a transaction error handler.
     *
     * @param policy   using this transaction policy (eg: required, supports, ...)
     * @return the created error handler
     */
    public TransactionErrorHandlerBuilder transactionErrorHandler(SpringTransactionPolicy policy) {
        return transactionErrorHandler(policy.getTransactionTemplate());
    }

    /**
     * Creates a transaction error handler.
     *
     * @param template the spring transaction template
     * @return the created error handler
     */
    public TransactionErrorHandlerBuilder transactionErrorHandler(TransactionTemplate template) {
        TransactionErrorHandlerBuilder answer = new TransactionErrorHandlerBuilder();
        answer.setTransactionTemplate(template);
        return answer;
    }

    /**
     * Creates a transaction error handler.
     *
     * @param transactionManager the spring transaction manager
     * @return the created error handler
     */
    public TransactionErrorHandlerBuilder transactionErrorHandler(PlatformTransactionManager transactionManager) {
        TransactionTemplate template = new TransactionTemplate(transactionManager);
        return transactionErrorHandler(template);
    }
    
    public RouteDefinition fromDirect(RouteProperties props) {
    	return from(direct(props.getRouteId())
    			.advanced().synchronous(true))
    			.routeId(props.getRouteId())
    			.autoStartup(props.isUsed());
    }
    
}
