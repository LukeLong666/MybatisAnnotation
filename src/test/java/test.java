import com.dao.IAccountDao;
import com.dao.IUserDao;
import com.domain.Account;
import com.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class test {

    private static SqlSessionFactory factory;
    private static SqlSession session;
    private static IUserDao userDao;
    private static IAccountDao accountDao;
    private InputStream inputStream;

    @Before
    public void before() throws Exception{
        //1.获取字节输入流
        inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.构建SqlSessionFactory
        factory = new SqlSessionFactoryBuilder().build(inputStream);
        //3.生产一个SqlSession
        session = factory.openSession();
        //4.使用sqlsession获取dao代理对象
        userDao = session.getMapper(IUserDao.class);
        accountDao = session.getMapper(IAccountDao.class);
    }

    @After
    public void after() throws Exception{
        session.commit();
        session.close();
        inputStream.close();
    }

    @Test
    public void testFindById() {
        User user = userDao.findById(55);
        System.out.println(user);
    }

    @Test
    public void testFindUserByName() {
        List<User> users = userDao.findUserByName("%m%");
        System.out.println(users);
    }

    @Test
    public void findAll() {
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println("---------------");
            System.out.println(user);
        }

    }

    @Test
    public void aFindAll() {
        List<Account> accounts = accountDao.findAll();
        for (Account account : accounts) {
            System.out.println("-------------");
            System.out.println(account);
            System.out.println(account.getUser());
        }
    }
}
