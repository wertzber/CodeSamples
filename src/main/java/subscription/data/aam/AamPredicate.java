package subscription.data.aam;



import com.liveperson.api.ams.aam.SubscribeExConversations;
import com.liveperson.api.ams.cm.types.ConversationState;
import com.liveperson.api.ams.cm.types.ParticipantRole;

import java.util.Collection;
import java.util.EnumSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by eladw on 8/25/2015.
 * Wrapper for all predicate types.
 */
public class AamPredicate implements Predicate<ExtendedConversation> {

    private SubscribeExConversations queryRequest;

    private boolean predicateAgentIdResult;
    private boolean predicateConsumerResult;
    private boolean predicateMaxTimeResult;
    private boolean predicateMinTimeResult;
    private boolean predicateEttrResult;
    private boolean predicateConvStateResult;
    private boolean predicateBrandIdResult;

    public AamPredicate(SubscribeExConversations queryRequest) {
        this.queryRequest = queryRequest;
    }

    @Override
    public boolean test(ExtendedConversation extendedConversation) {
        init();
        return byAgentId(extendedConversation)
                .byBrandId(queryRequest.brandId, extendedConversation)
//                .byConsumerId(queryRequest.consumerId, extendedConversation)
                .byConversationStates(queryRequest.convState, extendedConversation)
//                .bymaxETTR(queryRequest.maxETTR, extendedConversation)
//                .byMaxLastUpdateTime(queryRequest.maxLastUpdatedTime, extendedConversation)
//                .byMinLastUpdateTime(queryRequest.minLastUpdatedTime, extendedConversation)
                .finalResult();

    }

    private void init() {
        predicateAgentIdResult = false;
        predicateConsumerResult = false;
        predicateMaxTimeResult = false;
        predicateMinTimeResult = false;
        predicateEttrResult = false;
        predicateConvStateResult = false;
        predicateBrandIdResult = false;
    }

    private AamPredicate byAgentId(ExtendedConversation extendedConversation) {
        Collection<String> wantedAgentIds = queryRequest.agentIds;
        if (wantedAgentIds == null) {
            predicateAgentIdResult = true;
            return this;
        }

        if (wantedAgentIds.size() == 1 && wantedAgentIds.iterator().next().isEmpty()) {

            predicateAgentIdResult = true; //one wants to query on all agents
            return this;
        }

        final  Collection<String> currentAgentIds = extendedConversation.getAamConversation().participants
                .entrySet()
                .stream()
                .filter(participantRoleCollectionEntry -> !participantRoleCollectionEntry.getKey().equals(ParticipantRole.CONSUMER)) //do not take consumer
                .flatMap(e->e.getValue().stream())
                .collect(Collectors.toList());

        //final Collection<String> currentAgentIds = extendedConversation.getAamConversation().participants.get(ParticipantRole.ASSIGNED_AGENT);
        if (currentAgentIds != null) {
            for (String wantedAgentId : wantedAgentIds) {
                //is wanted agent id is present in this conversation return true since
                if (currentAgentIds.contains(wantedAgentId)) {
                    predicateAgentIdResult = true;
                    return this;
                }
            }
            predicateAgentIdResult = false;
            return this;
        }

        predicateAgentIdResult = false;
        return this;
    }


    public AamPredicate byConsumerId(String requireConsumerId, ExtendedConversation extendedConversation) {

        if (requireConsumerId == null || requireConsumerId.isEmpty()) {
            predicateConsumerResult = true;
            return this;
        }

        final Collection<String> currentConsumers = extendedConversation.getAamConversation().participants.get(ParticipantRole.CONSUMER);
        if (currentConsumers != null && currentConsumers.size() > 0) {
            predicateConsumerResult = currentConsumers.stream()
                    .anyMatch(curConsumerId -> curConsumerId.equals(requireConsumerId));
        } else {
            predicateConsumerResult = false;
        }

        return this;
    }


    public AamPredicate byMaxLastUpdateTime(Long maxLastUpdatedTime, ExtendedConversation extendedConversation) {

        if (maxLastUpdatedTime == null) {
            predicateMaxTimeResult = true;
            return this;
        }
        //predicateMaxTimeResult = ExtendedConversationUtils.lastUpdateTime(extendedConversation) <= maxLastUpdatedTime;
        return this;
    }


    public AamPredicate byMinLastUpdateTime(Long minLastUpdatedTime, ExtendedConversation extendedConversation) {

        if (minLastUpdatedTime == null) {
            predicateMinTimeResult = true;
            return this;
        }
        //predicateMinTimeResult = ExtendedConversationUtils.lastUpdateTime(extendedConversation) >= minLastUpdatedTime;
        return this;
    }

    public AamPredicate byConversationStates(EnumSet<ConversationState> requestedConversationStates, ExtendedConversation extendedConversation) {
        predicateConvStateResult = requestedConversationStates.contains(extendedConversation.getAamConversation().state);
        return this;
    }

    public AamPredicate bymaxETTR(Long wantedEttr, ExtendedConversation extendedConversation) {

        if (wantedEttr == null) {
            predicateEttrResult = true;
            return this;
        }
        if (wantedEttr == Long.MAX_VALUE) {
            predicateEttrResult = extendedConversation.getEttrData().getLastEttr().longValue() == -1;
            return this;
        }

        long lastEttr = extendedConversation.getEttrData().getLastEttr().longValue();
        predicateEttrResult = wantedEttr > lastEttr && lastEttr != -1; //also check if last ettr is not due to agent reply since when agent reply there is no ettr
        return this;
    }


    public AamPredicate byBrandId(String brandId, ExtendedConversation extendedConversation) {
        if (brandId == null) {
            predicateBrandIdResult = true;
        } else {
            predicateBrandIdResult = extendedConversation.getAamConversation().brandId.equals(brandId);
        }
        return this;
    }


    private boolean finalResult() {

        return predicateConvStateResult
                && predicateBrandIdResult;
    }

    public SubscribeExConversations getQueryRequest() {
        return queryRequest;
    }

    public void setQueryRequest(SubscribeExConversations queryRequest) {
        this.queryRequest = queryRequest;
    }
}
