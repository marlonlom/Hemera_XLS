package gov.dane.sige.restlet.dvp_xls.config;

import org.json.JSONObject;

/**
 * Configuration class for handlig excel resources for static download.
 * 
 * @author mjlopezm
 */
public final class ExcelListConfig {
	private static final JSONObject configData = new JSONObject();
	private static final ExcelListConfig instance = new ExcelListConfig();
	private static final String LIST_TYPE = "listado";
	private static final String RESUME_TYPE = "resumen";
	private static final String NUEVOS_TYPE = "nuevos";
	private static final String JSON_DIPOLA = "divipola";

	static {
		prepareConfigs();
	}

	public static ExcelListConfig getInstance() {
		return instance;
	}

	private static void prepareConfigs() {
		try {
			configData.put(JSON_DIPOLA, "/WEB-INF/json/dvpm.data.json");

			configData.put(NUEVOS_TYPE, new JSONObject());

			configData.getJSONObject(NUEVOS_TYPE).put("1993",
					"/WEB-INF/xls/new_mpios/Nuevos_municipios_desde_1993.xls");

			configData.put(RESUME_TYPE, new JSONObject());

			configData.getJSONObject(RESUME_TYPE).put("1967",
					"/WEB-INF/xls/resumen/Resumen_1967.xls");

			configData.getJSONObject(RESUME_TYPE).put("1970",
					"/WEB-INF/xls/resumen/Resumen_1970.xls");

			configData.getJSONObject(RESUME_TYPE).put("2000",
					"/WEB-INF/xls/resumen/Resumen_2000.xls");

			configData.getJSONObject(RESUME_TYPE).put("2002",
					"/WEB-INF/xls/resumen/Resumen_2002.xls");

			configData.getJSONObject(RESUME_TYPE).put("2008",
					"/WEB-INF/xls/resumen/Resumen_2008.xls");

			configData.getJSONObject(RESUME_TYPE).put("2009",
					"/WEB-INF/xls/resumen/Resumen_2009.xls");

			configData.getJSONObject(RESUME_TYPE).put("2010",
					"/WEB-INF/xls/resumen/Resumen_2010.xls");

			configData.getJSONObject(RESUME_TYPE).put("2011",
					"/WEB-INF/xls/resumen/Resumen_2011.xls");

			configData.getJSONObject(RESUME_TYPE).put("2012",
					"/WEB-INF/xls/resumen/Resumen_2012.xls");

			configData.put(LIST_TYPE, new JSONObject());

			configData.getJSONObject(LIST_TYPE).put("2008",
					"/WEB-INF/xls/listados/Listado_2008.xls");

			configData.getJSONObject(LIST_TYPE).put("2009",
					"/WEB-INF/xls/listados/Listado_2009.xls");

			configData.getJSONObject(LIST_TYPE).put("2010",
					"/WEB-INF/xls/listados/Listado_2010.xls");

			configData.getJSONObject(LIST_TYPE).put("2011",
					"/WEB-INF/xls/listados/Listado_2011.xls");

			configData.getJSONObject(LIST_TYPE).put("2012",
					"/WEB-INF/xls/listados/Listado_2012.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getDataPath() throws Exception {
		return configData.getString(JSON_DIPOLA);
	}

	public String getNewMunicipalitiesXlsPath(String year) throws Exception {
		return configData.getJSONObject(NUEVOS_TYPE).getString(year);
	}

	public String getStaticXlsPath(String type, String year) throws Exception {
		return configData.getJSONObject(type).getString(year);
	}
}
