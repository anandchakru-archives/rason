package rason.app.model;

import java.io.Serializable;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class BulkUploadReq implements Serializable {
	private Map<String, JsonNode> payload = Maps.newHashMap();
}
