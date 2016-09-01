//package subscription.modifier;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import subscription.api.SubscriptionResultModifier;
//import subscription.data.aam.ExtendedConversation;
//import subscription.utils.SubscriptionConsts;
//
//import java.util.Map;
//import java.util.function.BiFunction;
//import java.util.function.Predicate;
//
///**
// * Created with IntelliJ IDEA.
// * User: eladw
// * Date: 1/14/2016
// * Time: 1:33 PM
// */
//public class AamModifierNoteOut implements SubscriptionResultModifier<ExtendedConversation, ExtendedConversation>{
//
//    private static final Logger logger = LoggerFactory.getLogger(AamModifierNoteOut.class);
//
//    private Predicate<String> predicate;
//    private String value;
//    private BiFunction<ExtendedConversation, String, ExtendedConversation> action;
//
//    public AamModifierNoteOut(Predicate<String> predicate, String value, BiFunction<ExtendedConversation, String, ExtendedConversation> action) {
//        this.predicate = predicate;
//        this.value = value;
//        this.action = action;
//    }
//
//    @Override
//    public ExtendedConversation modify(ExtendedConversation inputEvent, Map<String, Object> params) {
//        String role = (String) params.get(SubscriptionConsts.ROLE);
//        if(predicate.test(role)){
//            action.apply(inputEvent, value);
//        }
//        return inputEvent;
//
//
//    }
//}