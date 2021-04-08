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