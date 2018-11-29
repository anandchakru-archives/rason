package rason.app.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class CacheStatsResponse implements Serializable {
	private Long used;
	private Long max;
	private Long maxLifeMinutes;
}
