package com.avivasa.maf.service.faxrobot.processor;

import com.avivasa.logging.Logger;
import com.avivasa.logging.LoggerFactory;
import com.avivasa.maf.util.MafUtils;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author Taner YILDIRIM
 */

@Component
public class FaxRobotRoute extends RouteBuilder {
    private Logger logger = LoggerFactory.getLogger(FaxRobotRoute.class);

    public static final String ROUTE_PROCESS_DOCUMENT = "direct:FaxRobotRoute.processDocuments";
    private static final String DIRECTORY_ROUTE_PARAMETERS = "?delay=60000&move=done/${date:now:ddMMyyyy}/${file:name}&moveFailed=error/${date:now:ddMMyyyy}/${file:name}";

    @Autowired
    private Environment env;

    @Autowired
    private FromDirectoryProcessor fromDirectoryProcessor;

    @Autowired
    private FaxRobotDocumentProcessor documentProcessor;

    @Override
    public void configure() throws Exception {

        String ROUTE_VIA_EVRAK_GONDERIM_APP = "timer://viaEvrakGonderim?fixedRate=true&period=60000";
        String ROUTE_VIA_TRANSFER_DIRECTORY = env.getProperty("faxrobot.route.transfer") + DIRECTORY_ROUTE_PARAMETERS;
        String ROUTE_VIA_FAX_DIRECTORY = env.getProperty("faxrobot.route.fax") + DIRECTORY_ROUTE_PARAMETERS;

        logger.info("ROUTE_VIA_TRANSFER_DIRECTORY : {}", ROUTE_VIA_TRANSFER_DIRECTORY);
        logger.info("ROUTE_VIA_FAX_DIRECTORY : {}", ROUTE_VIA_FAX_DIRECTORY);

        onException(Exception.class).process(exchange -> {
            Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
            StringBuilder msg = MafUtils.getExceptionTraceLog(exception,"FaxRobotRoute exception occured.\n");
            logger.error(msg.toString());
        });

        from(ROUTE_VIA_TRANSFER_DIRECTORY)
                .routeId("FaxRobotRoute.routeViaTransferDirectory")
                .process(fromDirectoryProcessor)
                .to(ROUTE_PROCESS_DOCUMENT);

        from(ROUTE_VIA_FAX_DIRECTORY)
                .routeId("FaxRobotRoute.routeViaFaxDirectory")
                .process(fromDirectoryProcessor)
                .to(ROUTE_PROCESS_DOCUMENT);

        from(ROUTE_PROCESS_DOCUMENT)
                .routeId("FaxRobotRoute.processDocuments")
                .process(documentProcessor);
    }
}