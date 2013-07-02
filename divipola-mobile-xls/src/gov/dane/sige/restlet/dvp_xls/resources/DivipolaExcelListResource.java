package gov.dane.sige.restlet.dvp_xls.resources;

import gov.dane.sige.restlet.dvp_xls.config.ExcelListConfig;
import gov.dane.sige.restlet.dvp_xls.utils.DivipolaSearchUtil;
import gov.dane.sige.restlet.dvp_xls.utils.ExcelReportingUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.data.Disposition;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.OutputRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;

/**
 * @author mjlopezm
 */
public class DivipolaExcelListResource extends ExcelListingServerResource {

	final Integer LEVEL_DEPT = 1;
	final Integer LEVEL_AMTP = 2;
	final Integer LEVEL_DSTR = 3;
	final Integer LEVEL_MPIO = 4;
	final Integer LEVEL_CPOB = 5;
	final String KEY_COD = "cod";

	@Post
	public Representation retrieveExcelDocument(Representation entity)
			throws Exception {
		String _level = "";
		String _keys = "";
		String _root01 = "";
		String _root02 = "";

		if (MediaType.APPLICATION_WWW_FORM.isCompatible(entity.getMediaType())) {
			final Form form = new Form(entity);
			_level = form.getFirstValue("level");
			_keys = form.getFirstValue("items");
			_root01 = form.getFirstValue("root01");
			_root02 = form.getFirstValue("root02");
		} else if (MediaType.APPLICATION_JSON.isCompatible(entity
				.getMediaType())) {
			final JSONObject json = new JsonRepresentation(entity)
					.getJsonObject();
			_level = json.getString("level");
			_keys = json.getString("items");
			_root01 = json.getString("root01");
			_root02 = json.getString("root02");
		}

		final JSONObject divipolaItems = prepareDivipolaContents();
		final DivipolaSearchUtil searchUtil = DivipolaSearchUtil.getInstance();
		searchUtil.setSearchableItems(divipolaItems);

		JSONArray levelList = new JSONArray();
		JSONArray foundItms = new JSONArray();
		JSONObject xlsParams = new JSONObject();
		String listKey = "";
		if (!divipolaItems.equals(null)) {
			xlsParams.put("level", Integer.valueOf(_level));
			if (Integer.valueOf(_level).equals(LEVEL_DEPT)) {
				listKey = "departamentos";
			} else if (Integer.valueOf(_level).equals(LEVEL_AMTP)) {
				listKey = "areasmetrop";
			} else if (Integer.valueOf(_level).equals(LEVEL_DSTR)) {
				listKey = "distritos";
			} else if (Integer.valueOf(_level).equals(LEVEL_MPIO)) {
				listKey = "municipios";
			} else if (Integer.valueOf(_level).equals(LEVEL_CPOB)) {
				listKey = "cpoblados";
			}
			xlsParams.put("listField", listKey);
			levelList = divipolaItems.getJSONArray(listKey);
			int countKeys = 0;
			if (!levelList.equals(null) && levelList.length() > 0) {
				for (int i = 0; i < levelList.length(); i++) {
					final JSONObject foundItm = levelList.getJSONObject(i);
					if (_keys.indexOf(foundItm.getString(KEY_COD)) >= 0) {
						foundItms.put(foundItm);
						countKeys++;
					}
					if (countKeys == _keys.split(";").length) {
						break;
					}
				}
			}
			xlsParams.put(listKey, foundItms);
			if (!foundItms.equals(null) && foundItms.length() > 0) {
				if (listKey.equalsIgnoreCase("municipios")) {
					JSONObject itm = foundItms.getJSONObject(0);
					xlsParams.put(
							"departamentos",
							selectDivipolaItem(divipolaItems, new String(
									"departamentos/" + itm.getString("depto")
											+ "").split("/")));
				}
				if (!_root01.equals(null) && !_root01.equalsIgnoreCase("none")
						&& !_root01.startsWith("municipios/")) {
					xlsParams.put(
							_root01.split("/")[0],
							selectDivipolaItem(divipolaItems,
									_root01.split("/")));
				}
				if (!_root02.equals(null) && !_root02.equalsIgnoreCase("none")) {
					xlsParams.put(
							"municipios",
							selectDivipolaItem(divipolaItems, new String(
									"municipios/" + _root02 + "").split("/")));
					JSONObject depto = xlsParams.getJSONObject("municipios");
					xlsParams.put(
							"departamentos",
							selectDivipolaItem(divipolaItems, new String(
									"departamentos/" + depto.getString("depto")
											+ "").split("/")));
				}
			}
		}

		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ExcelReportingUtil.getInstance().prepareReport(xlsParams, searchUtil,
				baos);

		Representation representation = new OutputRepresentation(
				MediaType.APPLICATION_EXCEL, baos.size()) {

			@Override
			public void write(OutputStream outputStream) throws IOException {
				baos.writeTo(outputStream);
			}
		};

		Disposition disposition = new Disposition(Disposition.TYPE_ATTACHMENT);
		disposition.setFilename("reporte.xls");
		disposition.setType(MediaType.APPLICATION_EXCEL.getName());
		representation.setDisposition(disposition);
		return representation;
	}

	private JSONObject selectDivipolaItem(JSONObject divipolaItems,
			String[] level01Data) throws Exception {
		JSONObject item = null;
		if (divipolaItems.has(level01Data[0])) {
			JSONArray items = divipolaItems.getJSONArray(level01Data[0]);
			for (int i = 0; i < items.length(); i++) {
				item = items.getJSONObject(i);
				if (item.get(KEY_COD).equals(level01Data[1])) {
					break;
				}
			}
		}

		return item;
	}

	private JSONObject prepareDivipolaContents() throws Exception {
		final String dataPath = ExcelListConfig.getInstance().getDataPath();
		InputStream inputStream = this.serveResourceAsStream(dataPath);
		byte[] streamBytes = this.readStreamBytes(inputStream);
		String divipolaData = new String(streamBytes);
		return new JSONObject(divipolaData.substring(1));
	}
}
