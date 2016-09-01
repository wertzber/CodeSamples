//package subscription.converter;
//
//import com.liveperson.api.ams.cm.types.*;
//import com.liveperson.messaging.async.types.cm.entities.Conversation;
//import subscription.api.SubscriptionConverter;
//
//import java.util.*;
//
///**
// * Created by eladw on 1/17/2016.
// */
//public class CmConverterIn implements SubscriptionConverter<Conversation, ConversationDetails> {
//
//
//    @Override
//    public ConversationDetails convert(Conversation input) {
//
//        Map<ParticipantRole, Collection<String>> participants =  new HashMap<>(2);
//        Collection<String> dialogParticipants = new ArrayList<>(2);
//        input.participants.forEach((participantRole, participantIds) -> {
//            dialogParticipants.addAll(participantIds);
//            participants.put(participantRole, participantIds);
//        });
//        return new ConversationDetailsBuilder()
//                .withConvId(input.convId)
//                .withSkillId(input.skillId)
//                .withParticipants(participants)
//                .withDialogs(Collections.singletonList(new DialogDetails(
//                        input.convId,
//                        dialogParticipants,
//                        DialogType.MAIN,
//                        ChannelType.MESSAGING,
//                        DialogState.OPEN,
//                        input.startTs,
//                        input.metaDataLastUpdateTs,
//                        null)))
//                .withBrandId(input.brandId)
//                .withState(input.state)
//                .withCloseReason(input.closeReason)
//                .withStartTs(input.startTs)
//                .withMetaDataLastUpdateTs(input.metaDataLastUpdateTs)
//                .withFirstConversation(input.firstConversation)
//                .withCsatRate(input.csatRate)
//                .withTtr(input.ttr)
//                .withDelay(input.delay)
//                .withTopic(input.topic)
//                .withNote(input.note)
//                .build();
//    }
//}
