//package subscription.converter;
//
//import com.liveperson.api.ams.aam.ExConversationChangeNotification;
//import com.liveperson.api.ams.aam.types.ExtendedConversationDetails;
//import com.liveperson.api.ams.cm.types.ConversationDetails;
//import com.liveperson.api.ams.types.ResultsetChange;
//import com.liveperson.messaging.async.types.cm.entities.Conversation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import subscription.api.SubscriptionConverter;
//import subscription.data.aam.AamConversationBuilder;
//import subscription.data.aam.EttrData;
//import subscription.data.aam.ExtendedConversation;
//import subscription.data.aam.ExtendedConversationBuilder;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//
///**
// * Created by eladw on 1/14/2016.
// */
//public class AamConverterOut implements SubscriptionConverter<ExtendedConversationDetails, ExConversationChangeNotification>{
//
//    private static final Logger logger = LoggerFactory.getLogger(AamConverterOut.class);
//
//    @Override
//    public ExConversationChangeNotification convert(String subscribeId, ExtendedConversationDetails inputEvent) {
//        try {
//
//            Collection<ResultsetChange<ExtendedConversationDetails>> collection = new ArrayList<>();
//            ResultsetChange<ExtendedConversationDetails> resultSetChange =
//                    new ResultsetChange<>(ResultsetChange.Type.UPSERT, inputEvent);
//            collection.add(resultSetChange);
//
//
//            final ExConversationChangeNotification exConvChangeNotification =
//                    new ExConversationChangeNotification(subscribeId, collection);
//
//            return exConvChangeNotification;
//
//        } catch (Exception e) {
//            logger.error("AAM convert out failed:", e);
//            return null;
//        }
//    }
//
//
//}
