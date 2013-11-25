package jax.java.jaxdemo.events;

public class UploadDataEvent {

	private String dataFileName;

	public UploadDataEvent(String dataFileName) {
		this.dataFileName = dataFileName;
	}

	public String getDataFileName() {
		return dataFileName;
	}

}
