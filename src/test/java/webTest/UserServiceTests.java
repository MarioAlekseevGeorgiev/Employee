package webTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import tech.getarrays.api.web.entity.UserWeb;
import tech.getarrays.api.web.impl.UserWebServiceImpl;
import tech.getarrays.api.web.repository.UserWebRepository;
import tech.getarrays.api.web.service.UserWebService;

public class UserServiceTests {
    private UserWeb           testuser;
    private UserWebRepository mockedUserRepository;

    @Before
    public void onInit() {
        this.testuser = new UserWeb() {{
            setId(84651435L);
            setUsername("Pesho");
            setPassword("123456");
        }};

        this.mockedUserRepository = Mockito.mock(UserWebRepository.class);
    }

    @Test
    public void userWebService_GetUserWithCorrectUserName() {
        //Arrange
        Mockito.when(this.mockedUserRepository
               .findUserWebByName("Pesho"))
               .thenReturn(this.testuser);

        UserWebService userWebService = new UserWebServiceImpl(this.mockedUserRepository);
        UserWeb        expected       = this.testuser;

        //Actual
        UserWeb actual = userWebService.findUserWebByName("Pesho");

        //Assert
        Assert.assertEquals("Broken...", expected.getId(), actual.getId());
        Assert.assertEquals("Broken...", expected.getUsername(), actual.getUsername());
        Assert.assertEquals("Broken...", expected.getPassword(), actual.getPassword());
    }


}
