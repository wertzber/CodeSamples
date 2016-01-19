package subscription.converter;

import com.liveperson.api.ams.aam.ExConversationChangeNotification;
import com.liveperson.api.ams.aam.types.ExtendedConversationDetails;
import com.liveperson.api.ams.cm.ConversationChangeNotification;
import com.liveperson.api.ams.cm.types.ConversationDetails;
import com.liveperson.api.ams.types.ResultsetChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import subscription.api.SubscriptionConverter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by eladw on 1/14/2016.
 */
public class CmConverterOut implements SubscriptionConverter<ConversationDetails, ConversationChangeNotification>{

    private static final Logger logger = LoggerFactory.getLogger(CmConverterOut.class);

    @Override
    public ConversationChangeNotification convert(String subscribeId, ConversationDetails inputEvent) {
        try {

            Collection<ResultsetChange<ConversationDetails>> collection = new ArrayList<>();
            ResultsetChange<ConversationDetails> resultSetChange =
                    new ResultsetChange<>(ResultsetChange.Type.UPSERT, inputEvent);
            collection.add(resultSetChange);


            final ConversationChangeNotification exConvChangeNotification =
                    new ConversationChangeNotification(subscribeId, collection);

            return exConvChangeNotification;

        } catch (Exception e) {
            logger.error("AAM convert out failed:", e);
            return null;
        }
    }


}
