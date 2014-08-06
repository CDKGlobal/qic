package com.testing123.downloader;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.testing123.dataObjects.ConvertProject;
import com.testing123.vaadin.DatabaseInterface;

public class MockDBI implements DatabaseInterface {

	@Override
	public Map<String, Integer> getMapToID(int projectID) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		switch (projectID) {
		case 0:
			System.out.println("got map for 0");
			map.put("this.is.one.type.of.path", 18);
			map.put("this/is/the/other/type.java", 21);
			break;
		default:
			break;
		}
		return map;
	}

	@Override
	public List<ConvertProject> getAvailableProjects() {
		System.out.println("Got list of Projects");
		List<ConvertProject> list = new LinkedList<ConvertProject>();
		list.add(new ConvertProject("Unused", "alsoUnused", 0, "/QIC/Mock"));
		list.add(new ConvertProject("NoNameHere", "NoKeyEvenNeeded", 42542, "/QIC/MockEmpty"));
		return list;
	}

}
