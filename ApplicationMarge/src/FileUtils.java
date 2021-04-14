import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

	public <T> List<T> reader(String path) throws IOException {
		final List<T> list = new ArrayList<>();
		String line;
		var reader = new BufferedReader(new FileReader(path));
		try {
			while((line = reader.readLine()) !=null) {
					list.add((T) line);
			}
		}
		catch(IOException e){
			System.out.println("Неверно указан путь расположения файла");
		}
		finally {
	        if (reader != null) reader.close();
	    }
		return list;
	}
	
	public <T> void write(List<T> list, String nameFile, String dirFile) throws Exception {
		new File(dirFile).mkdir();
		var reader = new BufferedReader(new InputStreamReader(System.in));
		reader.close();
		var path = dirFile + "\\" + nameFile + ".txt";
		File file = new File(path);
		file.createNewFile();		
		var writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
		for(T print:list) {
			writer.write(print+"\n");
		}
		System.out.println("Ваш файл расположен в корне проекта по адресу " + dirFile);
		writer.close();
	}
	
}
