package gov.dane.sige.restlet.dvp_xls.utils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Singleton Utility class for handling divipola information retrieval.
 * @author mjlopezm
 */
public final class DivipolaSearchUtil {
	private static final DivipolaSearchUtil instance = new DivipolaSearchUtil();

	private static final String NONE = "none";

	private static final String KEY_COD = "cod";

	public static final String KEY_DEPTO = "depto";

	public static final String KEY_AMTRP = "armtrp";

	public static final String KEY_DSTRT = "dstrt";

	public static final String KEY_MPIO = "mpio";

	private static final String LIST_DEPTOS = "departamentos";

	private static final String LIST_DISTRTS = "distritos";

	private static final String LIST_AMTRPS = "areasmetrop";

	private static final String LIST_MPIOS = "municipios";

	private static final String LIST_CPOBS = "cpoblados";

	public static DivipolaSearchUtil getInstance() {
		return instance;
	}

	JSONObject divipola = new JSONObject();

	/**
	 * Lookup for an item from Department, Metropolitan area, District,
	 * Municipality or TownCenter levels.
	 * 
	 * @param level
	 *            code for searching
	 * @param code
	 *            key of item for lookup
	 * @return a json item with found data, or null
	 * @throws Exception
	 *             if returned item is null or if a list was not prepared for
	 *             search
	 */
	private JSONObject findLevelItem(String level, String code)
			throws Exception {
		JSONObject levelItem = null;
		final JSONArray deptos = divipola.getJSONArray(level);
		if (!deptos.equals(null) && deptos.length() > 0) {
			for (int i = 0; i < deptos.length(); i++) {
				levelItem = deptos.getJSONObject(i);
				if (code.equalsIgnoreCase(levelItem.getString(KEY_COD))) {
					break;
				}
			}
		}
		return levelItem;
	}

	public boolean haveDepartment(JSONObject item) throws Exception {
		return item.has(KEY_DEPTO)
				&& item.getString(KEY_DEPTO).equalsIgnoreCase(NONE);
	}
	public boolean haveDistrict(JSONObject item) throws Exception {
		return item.has(KEY_DSTRT)
				&& item.getString(KEY_DSTRT).equalsIgnoreCase(NONE);
	}
	public boolean haveMetropolitanArea(JSONObject item) throws Exception {
		return item.has(KEY_AMTRP)
				&& item.getString(KEY_AMTRP).equalsIgnoreCase(NONE);
	}
	public boolean haveMunicipality(JSONObject item) throws Exception {
		return item.has(KEY_MPIO)
				&& item.getString(KEY_MPIO).equalsIgnoreCase(NONE);
	}

	public JSONObject lookupDepartment(String code) throws Exception {
		return findLevelItem(LIST_DEPTOS, code);
	}

	public JSONObject lookupDistrict(String code) throws Exception {
		return findLevelItem(LIST_DISTRTS, code);
	}

	public JSONObject lookupMetropolitanArea(String code) throws Exception {
		return findLevelItem(LIST_AMTRPS, code);
	}

	public JSONObject lookupMunicipality(String code) throws Exception {
		return findLevelItem(LIST_MPIOS, code);
	}

	public JSONObject lookupTownCenter(String code) throws Exception {
		return findLevelItem(LIST_CPOBS, code);
	}

	public void setSearchableItems(JSONObject data) {
		this.divipola = data;
	}
}
