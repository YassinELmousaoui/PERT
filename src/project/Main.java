package project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.StringTokenizer;

import graph.Activity;
import graph.Project;

public class Main {
	

		/**
		 * Main method for executing the file parsing and PERT solution using the 
		 * Graph class.
		 * 
		 * @param args
		 *            the file name of the file that holds the graph information
		 * @throws IOException
		 */
		public static void main(String[] args) throws IOException {
			
				Project project = new Project();
				URL path = Main.class.getResource("input.txt");
				FileReader file2 = new FileReader(path.getPath());
				BufferedReader read = new BufferedReader(file2);
				while(read.ready()){
					String line = read.readLine();
					StringTokenizer tokens = new StringTokenizer(line);
					String name = tokens.nextToken();
					

					// Scrape the cost value
					int dure = new Integer(tokens.nextToken()).intValue(); 
					Activity a = new Activity(Activity.uniqueID.getAndIncrement(), name, dure);
					project.activities.add(a);
					//System.out.println(a);
					// Add all the dependencies as edges
				}
				
				read.close();
				file2.close();
				FileReader file = new FileReader(path.getPath());
				BufferedReader rd = new BufferedReader(file);
				while(rd.ready()){
					String line = rd.readLine();
					StringTokenizer tokens = new StringTokenizer(line);
					String name = tokens.nextToken();
					

					// Scrape the cost value
					tokens.nextToken();
					
					//System.out.println(cost);
					Activity a = project.getActivityByName(name);
					// Add all the dependencies as edges
					while(tokens.hasMoreTokens()){
						String s =tokens.nextToken();
						//System.out.println(s);
						a.getPredecessors().add(project.getActivityByName(s));
					}
				}
				for(Activity a : project.activities ) {
					a.setSus(project.activities);
				}
				rd.close();
				file.close();
				project.updateStats();
				
				
				for (Activity a : project.activities) {
					if(a != null && !a.getPredecessors().isEmpty()) {
						System.out.println(a);
					}
		        }
				
				System.out.println(project.getCriticalStiring());
				System.out.println(project.maxEndDate());
				
			}
		
			


}
