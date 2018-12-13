package rason.app.model;

import java.io.Serializable;
import java.util.Map;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class BulkUploadRsp implements Serializable {
	private Map<String, StringKey> payload = Maps.newHashMap();

	public void add(String reqKey, StringKey rspKey) {
		payload.put(reqKey, rspKey);
	}
}
