import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Audience {

    //@Pointcut("execution(* *.*(..))")  //定义命令的切点
    @Pointcut("within(com.run..*)")
    public void performance(){
        System.out.println("========================performance");
    }

    @Before("performance()")    //在表演之前
    public void silenceCellPhones(){
        System.out.println("=========================After");
    }

}
