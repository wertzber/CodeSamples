/*
 * Created by dannyl on 1/27/2015.
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

public class MsDalEntitySerializer extends StdDeserializer<Entity> {

	private Map<String, Class<? extends Entity>> registry = new HashMap<String, Class<? extends Entity>>();

	public MsDalEntitySerializer() {
		super(Entity.class);
	}

	public void registerEntity(String uniqueAttribute, Class<? extends Entity> entityClass) {
		registry.put(uniqueAttribute, entityClass);
	}

	@Override
	public Entity deserialize(JsonParser jp,
			DeserializationContext deserializationContext) throws IOException {
		ObjectMapper mapper = (ObjectMapper) jp.getCodec();
		ObjectNode root = (ObjectNode) mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL).readTree(jp);
		Class<? extends Entity> dtoClass = null;
		Iterator<Map.Entry<String, JsonNode>> elementsIterator = root
				.getFields();
		while (elementsIterator.hasNext()) {
			Map.Entry<String, JsonNode> element = elementsIterator.next();
			String name = element.getValue().asText();
			if (registry.containsKey(name)) {
				dtoClass = registry.get(name);
				break;
			}

            String value = element.getValue().asText();
            if (registry.containsKey(value)) {
                dtoClass = registry.get(value);
                break;
            }
		}

		if (dtoClass == null)
			return null;

		return mapper.readValue(root, dtoClass);
	}

}
