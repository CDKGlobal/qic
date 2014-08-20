package com.testing123.dataObjects;

import java.util.Arrays;
import java.util.List;

public class ConvertPath {

	final private String originalInput;
	final private List<String> path;
	final private boolean originallySonar;

	public ConvertPath(String inputPath) {
		if (inputPath == null) {
			originalInput = null;
			path = null;
			originallySonar = false;
		} else {
			this.originalInput = inputPath.trim();
			String[] split = originalInput.split(":");
			if (split.length > 2) {
				originallySonar = true;
				this.path = Arrays.asList(split[2].split("[/\\.]"));
			} else {
				originallySonar = false;
				this.path = Arrays.asList(inputPath.split("[/\\.]"));
			}
		}
	}

	public String getOriginalPath() {
		return originalInput;
	}

	public String getSonarPath() {
		if (originallySonar) {
			return originalInput;
		}
		StringBuffer buff = getPath('.');
		int length = buff.length();
		return getPath('.').substring(0, length - 5).toString();
	}

	public String getFisheyePath() {
		if (!originallySonar) {
			return originalInput;
		}
		StringBuffer buff = getPath('/');
		int lastSlashIndex = buff.lastIndexOf("/");
		int lastJavaIndex = buff.lastIndexOf("java");
		if (lastJavaIndex == lastSlashIndex + 1) {
			buff.replace(lastSlashIndex, lastSlashIndex + 1, ".");
		} else {
			buff.append(".java");
		}
		return buff.toString();
	}

	private StringBuffer getPath(char c) {
		StringBuffer buff = new StringBuffer();
		for (String s : path) {
			buff.append(s);
			buff.append(c);
		}
		buff.deleteCharAt(buff.length() - 1);
		return buff;
	}

	public boolean equals(String string) {
		return equals(new ConvertPath(string));
	}

	public boolean equals(ConvertPath other) {
		if (other == null)
			return false;
		if(path == null || other.path==null)
			return false;
		if (path.containsAll(other.path) || other.path.containsAll(path)) {
			return true;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

}
