package io.melakuera.fileuploaddownloaddemo;

import java.util.Objects;

public class Attachment {
	
	private String fileName;
	private String url;

	@Override
	public String toString() {
		return "Attachment{" +
				"fileName='" + fileName + '\'' +
				", url='" + url + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Attachment that = (Attachment) o;
		return Objects.equals(fileName, that.fileName) && Objects.equals(url, that.url);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fileName, url);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Attachment(String fileName, String url) {
		this.fileName = fileName;
		this.url = url;
	}

	public Attachment() {}
}
