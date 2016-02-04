//package subscription.converter;
//
//import com.liveperson.messaging.async.types.cm.entities.Conversation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import subscription.api.SubscriptionConverter;
//import subscription.data.aam.*;
//
///**
// * Created by eladw on 1/14/2016.
// */
//public class AamConverterIn implements SubscriptionConverter<Conversation, ExtendedConversation>{
//
//    private static final Logger logger = LoggerFactory.getLogger(AamConverterIn.class);
//
//    public AamConverterIn() {
//    }
//
//    @Override
//    public ExtendedConversation convert(Conversation inputEvent) {
//        try {
//            if (inputEvent == null) {
//                logger.error("recv event null in AamInternalApiCmHandler");
//                return null;
//            }
//
//            // prepare old extended conversation object
//            ExtendedConversation exConv = new ExtendedConversationBuilder()
//                    .withAamConversation(new AamConversationBuilder()
//                            .withBrandId(inputEvent.brandId)
//                            .withConvId(inputEvent.convId)
//                            .withStartTs(inputEvent.startTs)
//                            .withState(inputEvent.state)
//                            .build())
//                    .build();
//
//            EttrData ettrDataNew = new EttrData();
//            ettrDataNew.setBrandId(inputEvent.brandId);
//            //fill in exConv
//            exConv.setEttrData(ettrDataNew);
//            return exConv;
//
//        } catch (Exception e) {
//            logger.error("AamInternalApiCmHandler sendEventToAam exception", e);  //TODO:remove try catch
//            return null;
//        }
//    }
//
//}
