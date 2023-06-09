package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class statefulServiceTest {

    @Test
    void statefulServiceSingleton(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        statefulService statefulService1 = ac.getBean(statefulService.class);
        statefulService statefulService2 = ac.getBean(statefulService.class);

        //ThreadA : A 사용자 10,000원 주문
        int UserAPrice = statefulService1.order("UserA",10000);
        //ThreadB : B 사용자 20,000원 주문
        int UserBPrice = statefulService2.order("UserB",20000);

        //ThreadA : 사용자 A 주문 금액 조회
        //int price = statefulService1.getPrice();
        System.out.println("price = "+UserAPrice );

       // Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

    static class TestConfig{

        @Bean
        public statefulService statefulService(){
            return new statefulService();
        }
    }
}