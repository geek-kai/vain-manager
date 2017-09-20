import com.vain.manager.shiro.authenticator.DefaultAccountSubject;
import com.vain.manager.shiro.exception.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

public class ShiroTest {

    public static void main(String[] args) {
        UsernamePasswordToken token = new UsernamePasswordToken();
        token.setUsername("admin");
        token.setPassword("vain123".toCharArray());
        DefaultAccountSubject accountSubject = new DefaultAccountSubject();
        try {
            accountSubject.login(token);
        } catch (AuthenticationException authenticationException) {

            System.out.println("登录失败");
        }
    }

    public static void logout() {

        DefaultAccountSubject accountSubject = new DefaultAccountSubject();
        accountSubject.logout();

    }
}
