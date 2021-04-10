public class AppServiceManager
{
	public static final Logger logger = LogWriter.createLogger(AppServiceManager.class, "appServiceManager");
	
	private ClientSessionManager sessionManager;
	private SensorManager sensorManager;
	private HashMap<ISession, SessionServiceInstance> serviceMap;
	private Observer<SessionEvent> sessionObserver;
	
	public AppServiceManager(ClientSessionManager sessionManager, SensorManager sensorManager)
	{
		this.sessionManager = sessionManager;
		this.sensorManager = sensorManager;
		this.serviceMap = new HashMap<ISession, SessionServiceInstance>();
		this.sessionObserver = this::sessionObserver;
	}

	public int parseArguments(Parameters parameters) throws CmdLineException {
        String arg = parameters.getParameter(0);
        String[] tokens = arg.split(":");
        if(tokens.length!=3)
            throw new CmdLineException(owner, "Illegal port forwarding specification: "+arg);
        try {
            setter.addValue(new PortSpec(
                    Integer.parseInt(tokens[0]),tokens[1],Integer.parseInt(tokens[2])));
            return 1;
        } catch (NumberFormatException e) {
            throw new CmdLineException(owner, "Illegal port forwarding specification: "+arg);
        }
    }

    public boolean startModule()
	{
		this.sessionManager.addObserver(this.sessionObserver);
		logger.log(Level.INFO, "앱 서비스 매니저 시작 완료");
		return true;
	}
	
	public void stopModule()
	{
		this.sessionManager.removeObserver(this.sessionObserver);
		for(SessionServiceInstance inst : this.serviceMap.values())
		{
			inst.destroy();
		}
		this.serviceMap.clear();
		logger.log(Level.INFO, "앱 서비스 매니저 종료 완료");
	}

}