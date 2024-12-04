package com.searchEngine.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.indexingAndSearching.Indexer;
import com.indexingAndSearching.MainSolution;

@RestController
public class SearchEngineController {

	public SearchEngineController() throws IOException {
		Indexer.index();
	}

	@GetMapping("/hello")
	public Map<String, String> hello() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("msg", "Hello World");
		map.put("name", "abc");
		return map;
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/search")
	public List<Map<String, Object>> search(@RequestBody String inputString) throws IOException, InterruptedException {
		String[] parts = inputString.replaceAll("[\\[\\]\"]", "").split(",");
		String indexPath = "src/main/resources/Index/";
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		try {
			results = MainSolution.searchQuery(indexPath, parts[0], Integer.parseInt(parts[1]));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("src/main/resources/searchResult.json");
		try {
			// Serialize Java object info JSON file.
			mapper.writeValue(file, results);
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<Map<String, Object>> clusters = new ArrayList<Map<String, Object>>();
		String[] commands = new String[2];
		String command = "src/main/resources/clustering.py";
//		commands.add(command);
		commands[0] = "python";
		commands[1] = command;


		Process p = Runtime.getRuntime().exec(commands);
		p.waitFor();
		int clusterNumber = 1;
		List<List<Map<String, Object>>> clusterResults = new ArrayList<List<Map<String, Object>>>();
		File clusterFile = new File("src/main/resources/clusters.json");
        try {
            // Deserialize JSON file into Java object.
        	clusterResults = mapper.readValue(clusterFile, List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        for (int i = 0; i < clusterResults.size(); i++) {
        	List<Map<String, Object>> clusterElements = clusterResults.get(i);
        	Map<String, Object> cluster = new HashMap<String, Object>();
        	List<Map<String, Object>> clusterElementsList = new ArrayList<Map<String, Object>>();
        	for (int j = 0; j < clusterElements.size(); j++) {
        		Map<String, Object> clusterElement = new HashMap<String, Object>();
				clusterElement.put("name", clusterElements.get(j).get("title"));
				clusterElement.put("value", clusterElements.get(j).get("score"));
				clusterElement.put("content", clusterElements.get(j).get("content"));
				clusterElement.put("x", clusterElements.get(j).get("coord1"));
				clusterElement.put("y", clusterElements.get(j).get("coord2"));
				clusterElementsList.add(clusterElement);
        	}
			cluster.put("name", "Cluster " + Integer.toString(clusterNumber));
			cluster.put("data", clusterElementsList);
			clusters.add(cluster);
			clusterNumber++;
        }
		return clusters;
	}

}