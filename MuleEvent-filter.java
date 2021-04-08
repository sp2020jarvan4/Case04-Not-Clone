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