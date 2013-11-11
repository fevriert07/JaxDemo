package jaxdemo.events;

public class UploadDataEvent implements Event {

	private String dataFileName;

	public UploadDataEvent(String dataFileName) {
		this.dataFileName = dataFileName;
	}

	public String getDataFileName() {
		return dataFileName;
	}

}
