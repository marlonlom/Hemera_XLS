import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

public class FormClientTiester {

	protected static byte[] readStreamBytes(InputStream inputStream)
			throws Exception {

		int len;
		int size = 1024;
		byte[] buf;

		if (inputStream instanceof ByteArrayInputStream) {
			size = inputStream.available();
			buf = new byte[size];
			len = inputStream.read(buf, 0, size);
		} else {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			buf = new byte[size];
			while ((len = inputStream.read(buf, 0, size)) != -1)
				bos.write(buf, 0, len);
			buf = bos.toByteArray();
		}
		return buf;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		final Form requestData = new Form();
		int test = 51;
		prepareTestsData(requestData, test);

		ClientResource client = new ClientResource(
				"http://localhost:8080/divipola-mobile-xls/dxls/");
		Representation post = client.post(requestData);
		if (MediaType.APPLICATION_EXCEL.isCompatible(post.getMediaType())) {
			HSSFWorkbook workbook = new HSSFWorkbook(post.getStream());
			FileOutputStream out = 
			        new FileOutputStream(new File("c:\\marlonlom-dev\\test-"+System.currentTimeMillis()+".xls"));
			    workbook.write(out);
			    out.close();
		}
		client.release();
	}

	private static void prepareTestsData(Form requestData, int nro)
			throws Exception {
		switch (nro) {
		case 1:
			requestData.set("root02", "none");
			requestData.set("root01", "none");
			requestData.set("level", "1");
			requestData.set("items","05;08;11;13;15;17;18;19;20;23;25;27;41;44;47;50;52;54;63;66;68;70;73;76;81;85;86;88;91;94;95;97;99");
			break;
		case 2:
			requestData.set("root02", "none");
			requestData.set("root01", "none");
			requestData.set("level", "2");
			requestData.set("items", "am001;am002;am003;am004;am005;am006");
			break;
		case 3:
			requestData.set("root02", "none");
			requestData.set("root01", "none");
			requestData.set("level", "3");
			requestData.set("items", "dstr001;dstr002;dstr003;dstr004;dstr005");
			break;
		case 4:
			requestData.set("root02", "none");
			requestData.set("root01", "areasmetrop/am003");
			requestData.set("level", "4");
			requestData.set("items", "08001;08296;08433;08573;08758");
			break;
		case 41:
			requestData.set("root02", "none");
			requestData.set("root01", "distritos/dstr005");
			requestData.set("level", "4");
			requestData.set("items", "76109");
			break;
		case 42:
			requestData.set("root02", "none");
			requestData.set("root01", "departamentos/17");
			requestData.set("level", "4");
			requestData.set("items", "17001;17013;17042;17050;17088;17174;17272;17380;17388;17433;17442;17444;17446;17486;17495;17513;17524;17541;17614;17616;17653;17662;17665;17777;17867;17873;17877");
			break;
		case 5:
			requestData.set("root02", "47001");
			requestData.set("root01", "distritos/dstr004");
			requestData.set("level", "5");
			requestData.set("items", "47001000;47001001;47001036;47001038");
			break;
		case 51:
			requestData.set("root02", "54405");
			requestData.set("root01","areasmetrop/am004");
			requestData.set("level", "5");
			requestData.set("items", "54405000;54405001;54405003;54405004;54405006");
			break;
		case 52:
			requestData.set("root02", "08078");
			requestData.set("root01","departamentos/08");
			requestData.set("level", "5");
			requestData.set("items","08078000;08078001;08078002;08078003");
			break;
		}
	}
}
