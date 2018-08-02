package testcase.xes;

import framework.BaseTest;
import org.testng.annotations.Test;
import page.xes.XESLoginPage;

@Test
public class XESLoginTest extends BaseTest{

    public void login(){
        XESLoginPage.verify()
                .inputPhoneNo("13988887771")
                .inputPassword("887771")
                .clickLoginButton();
    }
}
