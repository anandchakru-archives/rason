package rason.app.util;

public class RasonConstant {
	//
	public static final String URI_BASE = "/";
	public static final String URI_BUCKET = "{bucket}";
	public static final String URI_SLUG = "{slug}";
	public static final String URI_API = "/api/" + URI_BUCKET;
	public static final String URI_API_KEYS = "/api/" + URI_BUCKET + "/keys";
	public static final String URI_API_WITH_KEY = "/api/" + URI_BUCKET + "/" + URI_SLUG;
	public static final String URI_EXISTS = "/api/exists/" + URI_BUCKET + "/" + URI_SLUG;
	public static final String URI_BU_MAP = "/api/bum/" + URI_BUCKET;
	public static final String URI_BU_LIST = "/api/bul/" + URI_BUCKET;
	public static final String URI_RANDOM = "/api/rnd/" + URI_BUCKET;
	//
	public static final String URI_HB = "/api/hb";
	public static final String URI_STATS = "/api/stats/" + URI_BUCKET;
	//
	public static final String HB_PREFIX = "beat_";
	public static final String NOT_FOUND = "notfound";
	public static final String BEAN_JSON_OBJECMAPPER = "jsonObjectMapper";
	public static final String BEAN_CACHE = "cacheService";
	public static final String BEAN_CORS_FILTER = "corsFilter";
	public static final String BEAN_UID_FILTER = "uidFilter";
	public static final String BEAN_PIR = "pir";
	//
	public static final Integer BUCKET_KEY_MIN_LENGTH = 3;
	public static final Integer BUCKET_KEY_MAX_LENGTH = 10;
	public static final Integer SLUG_KEY_MIN_LENGTH = 3;
	public static final Integer SLUG_KEY_MAX_LENGTH = 10;

	private RasonConstant() {
	}
}
