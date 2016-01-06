package subscription.data.aam;

import com.liveperson.api.ams.cm.types.*;
import com.liveperson.api.ams.types.Delay;
import com.liveperson.api.ams.types.TTR;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by eladw on 8/5/2015.
 * AAM internal Dao model for Conversation
 */
public class AamConversation {

    @NotNull
    public final String convId;
    public final String skillId;
    @Size(max = 16)
    public final Map<ParticipantRole, Collection<String>> participants;
    @NotNull
    public final String brandId;
    public final ConversationState state;
    public final CloseReason closeReason;
    public final Long startTs;
    public final Long endTs;
    public final Long metaDataLastUpdateTs;
    public final Boolean firstConversation;
    @Max(5L)
    @Min(1L)
    public final Integer csatRate;
    public final Boolean csatResolutionConfirmation;
    public final TTR ttr;
    public final Delay delay;
    public final Long manualEttr;
    public final String topic;
    public final String note;

    @GeneratePojoBuilder(withCopyMethod = true)
    public AamConversation(String convId, String skillId, Map<ParticipantRole, Collection<String>> participants, String brandId,
                           ConversationState state, CloseReason closeReason, Long startTs, Long endTs, Long metaDataLastUpdateTs,
                           Boolean firstConversation, Integer csatRate, TTR ttr, Delay delay, Long manualEttr, String topic, String note,
                           Boolean csatResolutionConfirmation) {
        this.convId = convId;
        this.skillId = skillId;
        this.participants = participants;
        this.brandId = brandId;
        this.state = state;
        this.closeReason = closeReason;
        this.startTs = startTs;
        this.endTs = endTs;
        this.metaDataLastUpdateTs = metaDataLastUpdateTs;
        this.firstConversation = firstConversation;
        this.csatRate = csatRate;
        this.ttr = ttr;
        this.delay = delay;
        this.manualEttr = manualEttr;
        this.topic = topic;
        this.note = note;
        this.csatResolutionConfirmation = csatResolutionConfirmation;
    }

    public AamConversation() {
        this(null, null, new ConcurrentHashMap<>(), null, null, null, null, null, null, null, null, null, null, null, null, null, null);
    }


    public static AamConversationBuilder defaultAmmConversationBuldier() {
        return new AamConversationBuilder().withParticipants(new ConcurrentHashMap<>());
    }

    @Override
    public String toString() {
        return "AamConversation{" +
                "convId='" + convId + '\'' +
                ", skillId='" + skillId + '\'' +
                ", participants=" + participants +
                ", brandId='" + brandId + '\'' +
                ", state=" + state +
                ", closeReason=" + closeReason +
                ", startTs=" + startTs +
                ", endTs=" + endTs +
                ", metaDataLastUpdateTs=" + metaDataLastUpdateTs +
                ", firstConversation=" + firstConversation +
                ", csatRate=" + csatRate +
                ", csatResolutionConfirmation=" + csatResolutionConfirmation +
                ", ttr=" + ttr +
                ", delay=" + delay +
                ", manualEttr=" + manualEttr +
                ", topic='" + topic + '\'' +
                ", note='" + note + '\'' +
                '}';
    }

    public static AamConversation toAamConversation(ConversationDetails cd) {
        return new AamConversationBuilder()
                .withBrandId(cd.brandId)
                .withCloseReason(cd.closeReason)
                .withConvId(cd.convId)
                .withCsatRate(cd.csatRate)
                .withCsatResolutionConfirmation(cd.csatResolutionConfirmation)
                .withDelay(cd.delay)
                .withParticipants(cd.participants)
                .withFirstConversation(cd.firstConversation)
                .withMetaDataLastUpdateTs(cd.metaDataLastUpdateTs)
                .withNote(cd.note)
                .withSkillId(cd.skillId)
                .withStartTs(cd.startTs)
                .withState(cd.state)
                .withEndTs(cd.endTs)
                .withTtr(cd.ttr)
                .withManualEttr(cd.manualETTR)
                .withTopic(cd.topic)
                .build();
    }

    public static ConversationDetails toConversationDetails(AamConversation aamConv) {
        return new ConversationDetailsBuilder()
                .withBrandId(aamConv.brandId)
                .withCloseReason(aamConv.closeReason)
                .withConvId(aamConv.convId)
                .withCsatRate(aamConv.csatRate)
                .withCsatResolutionConfirmation(aamConv.csatResolutionConfirmation)
                .withDelay(aamConv.delay)
                .withFirstConversation(aamConv.firstConversation)
                .withMetaDataLastUpdateTs(aamConv.metaDataLastUpdateTs)
                .withNote(aamConv.note)
                .withParticipants(aamConv.participants)
                .withSkillId(aamConv.skillId)
                .withStartTs(aamConv.startTs)
                .withEndTs(aamConv.endTs)
                .withState(aamConv.state)
                .withTopic(aamConv.topic)
                .withTtr(aamConv.ttr)
                .withManualETTR(aamConv.manualEttr)
                .build();
    }

    public ParticipantRole getRole(String userId) {
        ParticipantRole returnRole = null;
        final Set<Map.Entry<ParticipantRole, Collection<String>>> entries = participants.entrySet();
        for (Map.Entry<ParticipantRole, Collection<String>> entry : entries) {
            for (String innerUserId : entry.getValue()) {
                if (innerUserId.equals(userId)) {
                    returnRole = entry.getKey();
                    break;
                }
            }
            if (returnRole != null) break;
        }
        return returnRole;
    }


    // chekc if there is a key in aamConversation's participantMap is null
    public void cleanNullParticipant() {

        Iterator<ParticipantRole> participantRolesIterator = this.participants.keySet().iterator();
        while (participantRolesIterator.hasNext()) {
            ParticipantRole participantRole = participantRolesIterator.next();
            if (this.participants.get(participantRole) != null) {
                Collection<String> ids = this.participants.get(participantRole);
                Iterator<String> iterator = ids.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next() == null) {
                        iterator.remove();
                    }
                }
            }
        }
    }
}
