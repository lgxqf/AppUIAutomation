import com.run.Performance;
import com.run.Theatre;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy //启用Aspectj自动代理
public class ConcertConfig {

    @Bean   //声明了一个切面bean
    public Audience audience() {

        System.out.println("audience");
        return new Audience();
    }

    @Bean
    public Performance theatre(){
        System.out.println("theatre");
        return new Theatre();
    }

}
