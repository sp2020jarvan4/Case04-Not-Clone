public class Service{

	/**
     * Dialog to show an error message associated with bluetooth requirement.
     */
    public static class ShowBtErrorFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Dialog d = new AlertDialog.Builder(getActivity())
                    .setTitle("Bluetooth required")
                    .setNeutralButton("OK", new Dialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getActivity().finish();
                        }
                    })
                    .setCancelable(false)
                    .create();
            d.setCanceledOnTouchOutside(false);
            return d;
        }
    }

	public abstract class ServiceInstance
{
	protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss/SSS");
	
	public final String key;
	public final IChannel channel;
	private Observer<ChannelDataEvent> onDataRecieve;
	private Consumer<ServiceInstance> destroyCallback;
	
	public ServiceInstance(String key, IChannel channel)
	{
		this.key = key;
		this.channel = channel;
		this.onDataRecieve = this::onDataReceive;
	}
	
	public final void startService(Consumer<ServiceInstance> destroyCallback)
	{
		this.destroyCallback = destroyCallback;
		this.channel.addDataReceiveObserver(this.onDataRecieve);
		this.onStartService();
	}
	
	protected abstract void onStartService();
	
	public final void destroy()
	{
		this.channel.removeDataReceiveObserver(this.onDataRecieve);
		this.onDestroy();
		if(this.destroyCallback != null) this.destroyCallback.accept(this);
	}
	
	protected abstract void onDestroy();
	
	protected abstract void onDataReceive(ChannelDataEvent event);
}

private void updateFragment() {
	Fragment frag = null;
	switch (mGameProgression) {
		case ROLE_SELECTION:
			Log.d(TAG, "updateFragment: role selection");
			frag = mRoleSelectionFragment;
			break;
		case SETTING_UP_BT:
			Log.d(TAG, "updateFragment: setup");
			frag = (mIsServer) ? mSetupServerFragment : mSetupClientFragment;
			break;
		case GAME_IN_PROGRESS:
			Log.d(TAG, "updateFragment: game in progress");
			frag = (mIsServer) ? mServerFragment : mClientFragment;
			break;
		default:
			Log.w(TAG, "game progression not recognized: " + mGameProgression);
			break;
	}

	if (frag != null) {
		getFragmentManager().beginTransaction()
				.replace(R.id.fragment_container, frag)
				.commit();
	}

	// Refresh menu
	invalidateOptionsMenu();
}
}