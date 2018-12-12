package rason.app.util;

public class RasonConstant {
	//
	public static final String URI_BASE = "/";
	public static final String URI_API = "/api/{bucketId}";
	public static final String URI_API_KEYS = "/api/{bucketId}/keys";
	public static final String URI_API_WITH_KEY = "/api/{bucketId}/{key}";
	public static final String URI_CHECK_SLUG = "/api/exists/{bucketId}/{key}";
	public static final String URI_BU_MAP = "/api/bum/{bucketId}";
	public static final String URI_BU_LIST = "/api/bul/{bucketId}";
	//
	public static final String URI_HB = "/api/hb";
	public static final String URI_STATS = "/api/stats/{bucketId}";
	//
	public static final String DEFAULT_KEY = "rnd";
	public static final String HB_PREFIX = "beat_";
	public static final String NOT_FOUND = "notfound";
	public static final String BEAN_JSON_OBJECMAPPER = "jsonObjectMapper";
	public static final String BEAN_SLUGGER = "sluggerService";
	public static final String BEAN_CORS_FILTER = "corsFilter";
	public static final String BEAN_UID_FILTER = "uidFilter";
	public static final String BEAN_PIR = "pir";

	//
	private RasonConstant() {
	}
}
