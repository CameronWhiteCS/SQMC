package squeeze.theorem.entity;

public interface EyeTracking {

	public default int getTrackingDistance() {
		return 10;
	}
	
}
