package com.qa.api.utils;

import io.restassured.path.xml.XmlPath;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class DockerFileFetch {

	public static void main(String[] args) {
		String containerId = "277e43d7abb7"; // Docker container ID
		String containerFilePath = "/opt/sonatype/nexus/lib"; // Path inside container
		String localFilePath = "/Users/naveenautomationlabs/Documents/MyFiles"; // Local path
		String dockerPath = "/usr/local/bin/docker"; // Absolute path to Docker

		try {
			String command = String.format("%s cp %s:%s %s", dockerPath, containerId, containerFilePath, localFilePath);
			System.out.println("Executing command: " + command);

			Process process = Runtime.getRuntime().exec(command);

			// Read standard output
			BufferedReader stdOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			while ((line = stdOutput.readLine()) != null) {
				System.out.println(line);
			}

			// Read error output
			BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			while ((line = stdError.readLine()) != null) {
				System.err.println("Error: " + line);
			}

			int exitCode = process.waitFor();
			if (exitCode == 0) {
				System.out.println("Files copied successfully.");
				validateXMLFiles(localFilePath);
			} else {
				System.err.println("Failed to copy files. Exit code: " + exitCode);
			}

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void validateXMLFiles(String directoryPath) {
		File sourceDir = new File(directoryPath);
		File processedDir = new File(directoryPath + "/ProcessedFiles");

		// Ensure processed directory exists
		if (!processedDir.exists()) {
			processedDir.mkdirs();
		}

		// Get list of XML files in the source directory
		File[] xmlFiles = sourceDir.listFiles((dir, name) -> name.endsWith(".xml"));

		// Check if the total number of XML files is exactly 10
		if (xmlFiles != null && xmlFiles.length == 10) {
			System.out.println("Found 10 XML files. Starting processing...");

			for (File file : xmlFiles) {
				System.out.println("Processing file: " + file.getName());

				try {
					// Move file to processed directory
					Files.move(file.toPath(), new File(processedDir, file.getName()).toPath(),
							StandardCopyOption.REPLACE_EXISTING);

					// Load XML with Rest Assured for validation
					XmlPath xmlPath = new XmlPath(new File(processedDir, file.getName()));

					// Perform validations
					// Example: Check if a specific node exists or has expected value
					String nodeValue = xmlPath.getString("root.node"); // Replace with actual XML path
					if ("expectedValue".equals(nodeValue)) {
						System.out.println("Validation successful for " + file.getName());
					} else {
						System.err.println("Validation failed for " + file.getName());
					}

				} catch (IOException e) {
					System.err.println("Error processing file " + file.getName());
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("The number of XML files is not 10. Found: " + (xmlFiles != null ? xmlFiles.length : 0));
		}
	}
}
