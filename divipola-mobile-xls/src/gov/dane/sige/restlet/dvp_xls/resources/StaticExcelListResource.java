package gov.dane.sige.restlet.dvp_xls.resources;

import gov.dane.sige.restlet.dvp_xls.config.ExcelListConfig;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

import org.restlet.data.Disposition;
import org.restlet.data.MediaType;
import org.restlet.representation.OutputRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;

/**
 * Server resource for handling excel static files download.
 * @author mjlopezm
 */
public class StaticExcelListResource extends ExcelListingServerResource {

	@Get
	public Representation retrieveExcelDocument() {
		try {
			final String _anho = (String) getRequestAttributes().get("anho");
			final String _tipo = (String) getRequestAttributes().get("tipo");

			if (!_anho.equals(null) && !_tipo.equals(null)) {
				final String xlsPath = ExcelListConfig.getInstance()
						.getStaticXlsPath(_tipo, _anho);

				InputStream xlsInStream = this.serveResourceAsStream(xlsPath);

				final ByteArrayOutputStream baos = new ByteArrayOutputStream();

				this.writeInputStreamToOutputStream(xlsInStream, baos);

				Representation representation = new OutputRepresentation(
						MediaType.APPLICATION_EXCEL, baos.size()) {

					@Override
					public void write(OutputStream outputStream)
							throws IOException {
						baos.writeTo(outputStream);
					}
				};

				Disposition disposition = new Disposition(
						Disposition.TYPE_ATTACHMENT);
				final String filename = _tipo + "" + _anho + ".xls";
				disposition.setFilename(filename);
				disposition.setType(MediaType.APPLICATION_EXCEL.getName());
				representation.setDisposition(disposition);
				return representation;
			}
		} catch (Exception e) {
			getContext().getLogger().log(Level.SEVERE, e.getMessage());
		}
		return new StringRepresentation("No hay archivo XLS para descargar");
	}

}
