package subscription.test;

import com.liveperson.api.server.Remote;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import subscription.impl.SubscriptionServerAamImpl;

/**
 * Created by eladw on 1/6/2016.
 */
@RunWith(PowerMockRunner.class)
public class AamServerSubscriptionTest {

    private Remote remote;

    @Before
    public void setupMock() {
        remote = Mockito.mock(Remote.class);
    }

    @Test
    public void aamSubsriptionTest(){
        SubscriptionServerAamImpl aamServerSubscriber = new SubscriptionServerAamImpl();
        //PowerMockito.when(remote.send())
        //aamServerSubscriber.
    }
}
