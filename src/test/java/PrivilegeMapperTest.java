import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.simple.domain.SysPrivilege;
import tk.mybatis.simple.mapper.PrivilegeMapper;

public class PrivilegeMapperTest extends BaseMapperTest {

    @Test
    public void testSelectById() {
        testSession(new ITest<PrivilegeMapper>() {
            @Override
            public void test(PrivilegeMapper mapper) {
                SysPrivilege privilege = mapper.selectById(1L);

                System.out.println(privilege.getPrivilegeName());
                System.out.println(privilege.getId());
                System.out.println(privilege.getPrivilegeUrl());

                Assert.assertNotNull(privilege);
//                Assert.assertEquals("用户管理", privilege.getPrivilegeName());
            }
        }, PrivilegeMapper.class);
    }

}
