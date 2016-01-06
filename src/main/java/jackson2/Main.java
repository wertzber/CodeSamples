package jackson2;

import com.couchbase.client.deps.com.fasterxml.jackson.core.JsonProcessingException;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by eladw on 3/9/2015.
 */
public class Main {

    public static void main(String[] args) throws Exception {
//        MsgPublishPayLoad msgPublishPayLoad = new MsgPublishPayLoad();
//        msgPublishPayLoad.setMsgType(MCCAEnums.MessageType.MsgPublish.getType());
//        msgPublishPayLoad.setContentType("text/plain");
//        msgPublishPayLoad.setEndpointTimestamp(new Date().getTime());
//        msgPublishPayLoad.setMessage("elad 1");

        MsgAcceptStatusPublishPayload msgAccpet = new MsgAcceptStatusPublishPayload();
        msgAccpet.setMsgType(MCCAEnums.MessageType.MsgAcceptStatusPublish.getType());
        msgAccpet.setStatus(MCCAEnums.Status.accept);
        List<Long> seqList = new ArrayList<>();
        seqList.add(1L);
        seqList.add(2L);
        seqList.add(3L);



        msgAccpet.setSequenceList(seqList);

        MsgDistributePayload msgDist = new MsgDistributePayload();
        msgDist.setDistributeType(MCCAEnums.DistributeType.query);
        //msgDist.setOrigPayload(msgPublishPayLoad);
        msgDist.setOrigPayload(msgAccpet);
        msgDist.setMsgType(MCCAEnums.MessageType.MsgDistribute.getType());
        msgDist.setSequence(1);
        msgDist.setOriginatorId("123");

        MessageMetadataDto msgMeta = new MessageMetadataDto(MessageMetadataDto.MessageType.DATA, "text/plain", new Date().getTime(), "123");
        MessageDto messageDto = new MessageDto("con1",msgMeta, msgDist, 1);


        ObjectMapper om  = new ObjectMapper();
        String asStr = om.writeValueAsString(messageDto);
        System.out.println("Convert to String:" +  asStr);

        MessageDto messageDto1 = om.readValue(asStr, MessageDto.class);
        System.out.println("Convert to Object:" +  messageDto1);



    }
}
