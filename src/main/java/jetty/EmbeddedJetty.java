//package jetty;
//
//
//
//import java.io.IOException;
//
//public class EmbeddedJetty {
//
//    private static final int DEFAULT_PORT = 8080;
//    private static final String CONTEXT_PATH = "/";
//    private static final String CONFIG_LOCATION = "eu.kielczewski.example.config";
//    private static final String MAPPING_URL = "/*";
//    private static final String DEFAULT_PROFILE = "dev";
//
//    public static void main(String[] args) throws Exception {
//        new EmbeddedJetty().startJetty(getPortFromArgs(args));
//    }
//
//    private static int getPortFromArgs(String[] args) {
//        if (args.length > 0) {
//            try {
//                return Integer.valueOf(args[0]);
//            } catch (NumberFormatException ignore) {
//            }
//        }
//        return DEFAULT_PORT;
//    }
//
//    private void startJetty(int port) throws Exception {
//        Server server = new Server(port);
//        server.setHandler(getServletContextHandler(getContext()));
//        server.start();
//        server.join();
//    }
//
//    private static ServletContextHandler getServletContextHandler(WebApplicationContext context) throws IOException {
//        ServletContextHandler contextHandler = new ServletContextHandler();
//        contextHandler.setErrorHandler(null);
//        contextHandler.setContextPath(CONTEXT_PATH);
//        contextHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), MAPPING_URL);
//        contextHandler.addEventListener(new ContextLoaderListener(context));
//        contextHandler.setResourceBase(new ClassPathResource("webapp").getURI().toString());
//        return contextHandler;
//    }
//
//    private static WebApplicationContext getContext() {
//        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//        context.setConfigLocation(CONFIG_LOCATION);
//        context.getEnvironment().setDefaultProfiles(DEFAULT_PROFILE);
//        return context;
//    }
//}
