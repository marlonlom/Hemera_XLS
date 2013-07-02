package gov.dane.sige.restlet.dvp_xls.resources;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;

import org.restlet.resource.ServerResource;

public abstract class ExcelListingServerResource extends ServerResource {

	private static final String ORG_RESTLET_EXT_SERVLET_SERVLET_CONTEXT = "org.restlet.ext.servlet.ServletContext";

	protected InputStream serveResourceAsStream(String path) throws Exception {
		return getServletContext().getResourceAsStream(path);
	}

	protected byte[] readStreamBytes(InputStream inputStream) throws Exception {

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
	
	protected void writeInputStreamToOutputStream(InputStream in, OutputStream os)
			throws Exception {
		int nextChar;
		while ((nextChar = in.read()) != -1) {
			os.write(nextChar);
		}
		os.flush();
		os.close();
	}

	protected ServletContext getServletContext() throws Exception {
		ServletContext context = null;
		context = (ServletContext) getContext().getServerDispatcher()
				.getContext().getAttributes()
				.get(ORG_RESTLET_EXT_SERVLET_SERVLET_CONTEXT);
		return context;
	}
}
