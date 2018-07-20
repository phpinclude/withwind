package kr.pe.withwind.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Service;

import kr.pe.withwind.spring.TestClass;

/**
 * aop 테스트용 샘플 클래스
 * 참고 URL : http://private.tistory.com/43?category=655784
 * 		<dependency>
 *			<groupId>cglib</groupId>
 *			<artifactId>cglib</artifactId>
 *			<version>2.2</version>
 *		</dependency>
 *	에 대한 걸 추가해야 한다.
 * aop는 Spring bean으로 등록된 클래스에 대해서만 작동한다.
 * 
 * 
 * servlet-context.xml에 다음과 같이 적는다.
 * <aop:aspectj-autoproxy/> -> Aspect 어노테이션이 붙은 것을 자동으로 등록시킨다.
 * 
 * aop 구현 클래스도 bean으로 등록되어 있어야 한다. xml 이든 어노테이션이든
 * 
 * within(kr.pe.withwind.spring.*) 의 예시처럼 패키지 단위 또는 특정 클래스만을 관리하도록 할수 있다.
 * 
 * @author smpark
 *
 */


@Aspect
@Service
public class AopTestByXml {
	
	@Around("within(kr.pe.withwind.spring.*)")
	public Object loggerAop(ProceedingJoinPoint joinpoint) throws Throwable{
		// 공통 기능이 적용되는 메서드가 어떤 메서드인지 출력하기 위해 메서드명을 얻어옴
		String signatureStr = joinpoint.getSignature().toShortString();
		System.out.println(signatureStr + "시작"); // 메서드 실행
		// 공통기능
		System.out.println("핵심 기능 전에 실행 할 공통 기능입니다. " + System.currentTimeMillis());
		
		Object target = joinpoint.getTarget();

		try {
			Object obj = joinpoint.proceed(); // 핵심 기능 실행
			return obj;
		} finally {
			// 공통기능
			System.out.println("핵심 기능 후에 실행 할 공통 기능입니다. " + System.currentTimeMillis());

			System.out.println(signatureStr + "끝");
			
			((TestClass)target).aaa();
		}
		
		
	}
	
	@Before("within(kr.pe.withwind.spring.*)")
	public void before() throws Throwable{
		System.out.println("전처리");
	}
	
	@After("within(kr.pe.withwind.spring.*)")
	public void after() throws Throwable{
		System.out.println("후처리");
	}
}