/*
 * Created by dannyl on 1/26/2015.
 */
package jackson2;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.deser.std.StdDeserializer;
import org.codehaus.jackson.node.ObjectNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MsDalDtoSerializer extends StdDeserializer<BaseMsDalDto> {

    private Map<String, Class<? extends BaseMsDalDto>> registry =  new HashMap<String, Class<? extends BaseMsDalDto>>();

    public MsDalDtoSerializer() {
        super(BaseMsDalDto.class);
    }

    public void registerDto(String uniqueAttribute,
                        Class<? extends BaseMsDalDto> payloadDtoClass)
    {
        registry.put(uniqueAttribute, payloadDtoClass);
    }

    @Override
    public BaseMsDalDto deserialize(JsonParser jp, DeserializationContext deserializationContext) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        ObjectNode root = (ObjectNode) mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL).readTree(jp);
        Class<? extends BaseMsDalDto> payloadDtoClass = null;
        Iterator<Map.Entry<String, JsonNode>> elementsIterator =
                root.getFields();
        while (elementsIterator.hasNext())
        {
            Map.Entry<String, JsonNode> element=elementsIterator.next();
            String name = element.getValue().asText();
            if (registry.containsKey(name))
            {
                payloadDtoClass = registry.get(name);
                break;
            }
        }
        if (payloadDtoClass == null) return null;
        return mapper.readValue(root, payloadDtoClass);
    }

}
