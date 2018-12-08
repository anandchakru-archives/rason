package rason.app.util;

public class RasonConstant {
	//
	public static final String URI_BASE = "/";
	public static final String URI_API = "/api";
	public static final String URI_API_KEYS = "/api/keys";
	public static final String URI_API_WITH_KEY = "/api/{key}";
	public static final String URI_CHECK_SLUG = "/cs/{key}";
	public static final String URI_BU_MAP = "/bum";
	public static final String URI_BU_LIST = "/bul";
	//
	public static final String URI_HB = "/hb";
	public static final String URI_STATS = "/stats";
	//
	public static final String DEFAULT_KEY = "rnd";
	public static final String HB_PREFIX = "beat_";
	public static final String NOT_FOUND = "notfound";
	public static final String BEAN_CACHE = "cacheService";
	public static final String BEAN_JSON_OBJECMAPPER = "jsonObjectMapper";
	public static final String BEAN_SLUGGER = "sluggerService";
	public static final String BEAN_CORS_FILTER = "corsFilter";
	public static final String BEAN_UID_FILTER = "uidFilter";
	public static final String BEAN_PIR = "pir";
	public static final String SESSION_CACHE = "cacheInSession";
	public static final String SESSION_FLUX = "fluxInSession";

	//
	private RasonConstant() {
	}
}
