package service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FileService {
	
	
	public String login (String username, String password) throws IOException{
		String role = null;
		
		File input = new File("C:/Users/Abbès/Desktop/pjl/users.txt");
		
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(input));
			String line ="";
			while ((line = br.readLine()) != null){
				if (line.startsWith(username)){
					String[] s = line.split(" ");
					if (Objects.equals(password, s[1]))
						role = s[2];
				}
			}
		}finally{
			br.close();
		}
		
		return role;
	}

	public int incrementResource(String name) throws IOException {
		int valeur = 0;
		File input = new File("C:/Users/Abbès/Desktop/pjl/ressources.txt");
		File output = new File("C:/Users/Abbès/Desktop/pjl/temp.txt");

		BufferedReader br = null;
		BufferedWriter bw = null;
		try{
			br = new BufferedReader(new FileReader(input));
			bw = new BufferedWriter(new FileWriter(output));
			String line = "";
			while ((line = br.readLine()) != null) {
				if (line.startsWith(name)) {
					String[] s = line.split(" ");
					valeur = Integer.parseInt(s[1]);
					valeur++;
					bw.write(name + " " + valeur + "\r\n");
					bw.flush();
				} else {
					bw.write(line + "\r\n");
					bw.flush();
				}
			}
		} finally {
			bw.close();
			br.close();
		}		
		input.delete();
		output.renameTo(new File("C:/Users/Abbès/Desktop/pjl/ressources.txt"));
		return valeur;
	}

	public int decrementResource(String name) throws IOException {
		int valeur = 0;
		File input = new File("C:/Users/Abbès/Desktop/pjl/ressources.txt");
		File output = new File("C:/Users/Abbès/Desktop/pjl/temp.txt");

		BufferedReader br = null;
		BufferedWriter bw = null;
		try{
			br = new BufferedReader(new FileReader(input));
			bw = new BufferedWriter(new FileWriter(output));
			String line = "";
			while ((line = br.readLine()) != null) {
				if (line.startsWith(name)) {
					String[] s = line.split(" ");
					valeur = Integer.parseInt(s[1]);
					valeur--;
					bw.write(name + " " + valeur + "\r\n");
					bw.flush();
				} else {
					bw.write(line + "\r\n");
					bw.flush();
				}
			}
		} finally {
			bw.close();
			br.close();
		}		
		input.delete();
		output.renameTo(new File("C:/Users/Abbès/Desktop/pjl/ressources.txt"));
		return valeur;
	}
	

	public Map<String, Integer> getRessources() throws IOException {
		Map<String, Integer> result = new HashMap<String, Integer>();
		File entree = new File("C:/Users/Abbès/Desktop/pjl/ressources.txt");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(entree));
			String ligne = "";
			while ((ligne = br.readLine()) != null) {
				String[] s = ligne.split(" ");
				result.put(s[0], Integer.valueOf(s[1]));
			}
		} finally {
			if (br != null)
				br.close();
		}
		return result;
	}
	
	public void removeResource(String name) throws IOException {
		File input = new File("C:/Users/Abbès/Desktop/pjl/ressources.txt");
		File output = new File("C:/Users/Abbès/Desktop/pjl/temp.txt");

		BufferedReader br = null;
		BufferedWriter bw = null;
		try{
			br = new BufferedReader(new FileReader(input));
			bw = new BufferedWriter(new FileWriter(output));
			String line = "";
			while ((line = br.readLine()) != null) {
				if (line.startsWith(name)) {
				} else {
					bw.write(line + "\r\n");
					bw.flush();
				}
			}
		} finally {
			bw.close();
			br.close();
		}		
		input.delete();
		output.renameTo(new File("C:/Users/Abbès/Desktop/pjl/ressources.txt"));
	}
	
	public void addResource (String name, String value) throws IOException{
		File input = new File("C:/Users/Abbès/Desktop/pjl/ressources.txt");
		File output = new File("C:/Users/Abbès/Desktop/pjl/temp.txt");

		BufferedReader br = null;
		BufferedWriter bw = null;
		try{
			br = new BufferedReader(new FileReader(input));
			bw = new BufferedWriter(new FileWriter(output));
			String line = "";
			while ((line = br.readLine()) != null) {
					bw.write(line + "\r\n");
					bw.flush();
				}
			
			bw.write(name +" "+value+ "\r\n");
			bw.flush();
		}
		 finally {
			bw.close();
			br.close();
		}		
		input.delete();
		output.renameTo(new File("C:/Users/Abbès/Desktop/pjl/ressources.txt"));
	}

}
