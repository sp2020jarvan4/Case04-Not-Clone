public class App extends Application {
    private ObjectGraph objectGraph;

    private ClientService clientService;
    private SessionService sessionService;
    private PersonService personService;
    private EconomicActivityService economicActivityService;
    private BranchService branchService;
    private CityService cityService;
    private CustomFieldService customFieldService;
    private JobService jobService;

    @Inject
    EventBus bus;

    @Override
    public MuleEvent filter(MuleEvent event)
    {
        String origin = event.getMessage().getInboundProperty(HttpHeaders.Names.ORIGIN);
        if (StringUtils.isEmpty(origin))
        {
            logger.debug("Request is not a CORS request.");
            return event;
        }
        String method = event.getMessage().getInboundProperty(HttpConstants.RequestProperties.HTTP_METHOD_PROPERTY);
        if (shouldInvokeFlow(origin, method, publicResource))
        {
            return event;
        }
        event.getMessage().setPayload(null);
        event.getMessage().setInvocationProperty(Constants.CORS_STOP_PROCESSING_FLAG, true);
        return event;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        bus.unregister(clientService);
        bus.unregister(sessionService);
        bus.unregister(personService);
        bus.unregister(economicActivityService);
        bus.unregister(branchService);
        bus.unregister(cityService);
        bus.unregister(customFieldService);
        bus.unregister(jobService);
    }

}