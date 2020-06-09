package com.guestbook.config;

import org.modelmapper.AbstractCondition;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import javax.jms.ConnectionFactory;
import java.util.Calendar;

@Configuration
public class AppConfig {
   @Bean
   public RestTemplate restTemplate() {
      RestTemplate restTemplate = new RestTemplate();
      restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
      restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
      return restTemplate;
   }

   @Bean
   public ModelMapper modelMapper() {

      ModelMapper modelMapper = new ModelMapper();
      modelMapper.getConfiguration()
//              .setSkipNullEnabled(true)
              .setAmbiguityIgnored(true)
              .setPropertyCondition(isStringBlank);
      return modelMapper;
   }

   // string blank condition
   Condition<?, ?> isStringBlank = new AbstractCondition<Object, Object>() {
      @Override
      public boolean applies(MappingContext<Object, Object> context) {
         if (context.getSource() instanceof String) {
            return null != context.getSource() && !"".equals(context.getSource());
         } else {
            return context.getSource() != null;
         }
      }
   };

   @Bean
   Calendar calendar() {
      return Calendar.getInstance();
   }

   @Bean
   BCryptPasswordEncoder bCryptPasswordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
                                                   DefaultJmsListenerContainerFactoryConfigurer configurer) {
      DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
      // This provides all boot's default to this factory, including the message converter
      configurer.configure(factory, connectionFactory);
      // You could still override some of Boot's default if necessary.
      return factory;
   }

   @Bean // Serialize message content to json using TextMessage
   public MessageConverter jacksonJmsMessageConverter() {
      MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
      converter.setTargetType(MessageType.TEXT);
      converter.setTypeIdPropertyName("_type");
      return converter;
   }
}
