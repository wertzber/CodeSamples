package subscription.data.cm;

import com.liveperson.api.ReqBody;
import com.liveperson.api.ams.cm.SubscribeConversations;
import com.liveperson.api.ams.cm.types.ParticipantRole;
import com.liveperson.messaging.async.types.cm.entities.Conversation;

import java.util.function.Predicate;

/**
 * Created by anastasiam on 11/19/2015.
 */
public class ConversationPredicate implements Predicate<Conversation> {

    private SubscribeConversations queryRequest;

    private boolean predicateLastUpdatedTsResult;
    private boolean predicateStateResult;
    private boolean predicateAgentIdResult;
    private boolean predicateConsumerIdResult;

    public ConversationPredicate(ReqBody queryRequest) {
        this.queryRequest = (SubscribeConversations)queryRequest;
    }

    private void init(){
        predicateLastUpdatedTsResult = false;
        predicateStateResult = false;
        predicateAgentIdResult = false;
        predicateConsumerIdResult = false;
    }

    @Override
    public boolean test(Conversation conversation) {
        init();
        return byAgentId(conversation)
                .byConsumerId(conversation)
                .byLastUpdatedTs(conversation)
                .byState(conversation)
                .finalResult();
    }

    private ConversationPredicate byLastUpdatedTs(Conversation conversation) {
        if(queryRequest.minLastUpdatedTs == null || queryRequest.minLastUpdatedTs <= conversation.metaDataLastUpdateTs){
            predicateLastUpdatedTsResult = true;
        }
        return this;
    }

    private ConversationPredicate byState(Conversation conversation) {
        if(queryRequest.state == null || queryRequest.state.equals(conversation.state)){
            predicateStateResult = true;
        }
        return this;
    }

    private ConversationPredicate byConsumerId(Conversation conversation) {
        if(queryRequest.consumerId == null || queryRequest.consumerId.isEmpty()){
            predicateConsumerIdResult = true;
            return this;
        }
        String coonsumerId = conversation.getParticipant(ParticipantRole.CONSUMER);
        if(queryRequest.consumerId.equals(coonsumerId)){
            predicateConsumerIdResult = true;
            return this;
        }
        return this;
    }

    private ConversationPredicate byAgentId(Conversation conversation) {
        if(queryRequest.agentId == null || queryRequest.agentId.isEmpty()){
            predicateAgentIdResult = true;
            return this;
        }
        String agentId = conversation.getParticipant(ParticipantRole.ASSIGNED_AGENT);
        if(queryRequest.agentId.equals(agentId)){
            predicateAgentIdResult = true;
            return this;
        }
        return this;
    }

    private boolean finalResult() {

        return predicateAgentIdResult
                && predicateConsumerIdResult
                && predicateStateResult
                && predicateLastUpdatedTsResult;
    }
}
