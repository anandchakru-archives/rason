package rason.app.model;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = true)
public class BucketSlugs extends BucketSlug {
	private Map<String, String> slugs = Maps.newHashMap();

	public void add(String reqKey, String rspKey) {
		slugs.put(reqKey, rspKey);
	}
	public void add(String slug) {
		slugs.put(slug, slug);
	}
	public void setSlugsFromSet(Set<String> slugs) {
		this.slugs = slugs.stream().collect(Collectors.toMap(slug -> slug, slug -> slug));
	}
}