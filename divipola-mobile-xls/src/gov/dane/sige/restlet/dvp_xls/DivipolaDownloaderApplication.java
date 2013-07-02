package gov.dane.sige.restlet.dvp_xls;

import gov.dane.sige.restlet.dvp_xls.resources.DivipolaExcelListResource;
import gov.dane.sige.restlet.dvp_xls.resources.StaticExcelListResource;

import org.restlet.Application;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.Status;
import org.restlet.representation.StringRepresentation;
import org.restlet.routing.Router;

/**
 * Restlet Application class for DIVIPOLA download service.
 * 
 * @author mjlopezm
 */
public class DivipolaDownloaderApplication extends Application {

	@Override
	public Restlet createInboundRoot() {
		Restlet home = new Restlet(getContext()) {
			@Override
			public void handle(Request request, Response response) {
				String homeMsg = "Aplicación REST para consultas y descargas de archivos referentes a la DIVIPOLA";
				response.setStatus(Status.SUCCESS_OK);
				response.setEntity(new StringRepresentation(homeMsg));
			}
		};
		return prepareRouter(home);
	}

	private Restlet prepareRouter(Restlet home) {
		Router router = new Router(getContext());
		router.attachDefault(home);

		router.attach("/about/{tipo}/{anho}", StaticExcelListResource.class);
		router.attach("/dxls/", DivipolaExcelListResource.class);
		
		return router;
	}

}
