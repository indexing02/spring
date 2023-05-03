package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceTmpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceTmpl;

public class AppConfig {

    public MemberService memberService(){
        return new MemberServiceTmpl(getMemberRepository());
    }

    private static MemoryMemberRepository getMemberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService(){
        return new OrderServiceTmpl(getMemberRepository(), discountPolicy());
    }

    public DiscountPolicy discountPolicy(){
        return  new FixDiscountPolicy();
    }
}
