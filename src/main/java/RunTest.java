import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.TestNG;
import org.testng.collections.Lists;
import java.util.List;

public class RunTest {
    public static Logger log = LoggerFactory.getLogger(RunTest.class);

    public static void main(String args[]){

        TestNG testng = new TestNG();
        List<String> suites = Lists.newArrayList();

        suites.add(args[0]);
        testng.setTestSuites(suites);
        //ITestNGListener tla = new TestListener();
        //testng.addListener(tla);
        testng.run();
    }
}
