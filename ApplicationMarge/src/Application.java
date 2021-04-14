import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Application {

	public static void main(String[] args) throws Exception {
		var reader = new BufferedReader(new InputStreamReader(System.in));
		List<String> inDirectoryFile = new ArrayList<>();
		String sortMode = "",dataTipe="",outName="",outDirectory="";
		
		System.out.println("Спасибо, что решили вопосльзоваться нашим сервисом сортировки слянием файлов,\nследуйте алгоритму и всё получиться :)");
		System.out.println();
		System.out.println("Введите команду желаемой сортировки -a (по возростанию) -d (по убыванию)");
		sortMode = juxtapose(reader.readLine(), "sortMode");
		System.out.println("Укажите тип данных сортировки -s(строковые значения) -i(целочисленные значения)");
		dataTipe=juxtapose(reader.readLine(), "dataTipe");
		System.out.println("Введите название выходного файла");
		outName=juxtapose(reader.readLine(), "outName");
		System.out.println("Введите директорию вывода выходного файла в формате 'X:\\xxxxx\\' ");
		outDirectory=checkDirectory(reader.readLine());
		System.out.println("Веедите путь до расположения файла в формате 'X:\\xxxxx\\xxx.txt' ");
		inDirectoryFile = add();
		//System.out.println(sortMode+" *** "+dataTipe+" *** "+outName+" *** "+outDirectory+" *** "+inDirectoryFile);
		compareTo(inDirectoryFile, sortMode, outName, outDirectory);
		reader.close();

	}
	
	private static String juxtapose(String command, String tipe) throws IOException{
		var reader = new BufferedReader(new InputStreamReader(System.in));
	    if(tipe.equals("sortMode")) {
	    	while (!command.equals("-a") && !command.equals("-d")){
	        System.out.println("Вы указали неверную команду, повторите ввод");
	        command = reader.readLine();
	    	}
	    }
	    if(tipe.equals("dataTipe")) {
		    while (!command.equals("-s") && !command.equals("-i")){
		        System.out.println("Вы указали неверную команду, повторите ввод");
		        command = reader.readLine();
		    }
	    }
	    if(tipe.equals("outName")) {
	    	while(command.contains("\\")||command.contains("|")||command.contains("/")||command.contains(":")||command.contains("*")
					||command.contains("?")||command.contains("\"")||command.contains("<")||command.contains(">")) {
				System.out.println("Название файла содержит недопустимые символы (\\/:*?\"<>|), введите другое имя файла");
				command = reader.readLine();
			}
	    }
	    return command;
	    }
	
	private static String checkDirectory(String directory) throws IOException{
		var reader = new BufferedReader(new InputStreamReader(System.in));
			while (!Files.isDirectory(Paths.get(directory))) {
	            System.out.println("Такого пути к файлу не существует, повторите ввод");
	            directory = reader.readLine();
	       }
	    return directory;
	    }
	
	private static boolean checkFile(String directory) throws Exception {
		boolean t;
		File f = new File(directory);
			if(f.exists() && !f.isDirectory()) { 
	            t = true;
	        }
	        else{
	             t = false;
	        }
		return t;
	}
	
	private static String checkDirFile(String directory) throws Exception {
		var reader = new BufferedReader(new InputStreamReader(System.in));
		
      while(!checkFile(directory)) {
				System.out.println("Файла по указанному пути не существует, повторите ввод");
	            directory = reader.readLine();
		}
		return directory;
	}
	
	private static List<String> add() throws Exception {
		String command = "";
		var reader = new BufferedReader(new InputStreamReader(System.in));
		final List<String> list = new ArrayList<>();
		list.add(checkDirFile(reader.readLine()));
		System.out.println("Введите путь до расположения второго файла");
		list.add(checkDirFile(reader.readLine()));
		System.out.println("Если хотите добавить ещё файл введите -d, для завершения нажмите Enter");
		command=reader.readLine();
		while(command.equals("-d")) {
			System.out.println("Введите путь до расположения файла");
			list.add(checkDirFile(reader.readLine()));
			System.out.println("Если хотите добавить ещё файл введите -d, для завершения нажмите Enter");
			command = reader.readLine();
		}
		return list;
	}
	
	private static void compareTo (List<String> inDirectoryFile, String sortMode, String outName, String outDirectory) throws Exception {
		List<Integer> sortedList = new ArrayList<Integer>();
		var ms = new MergerSorter();
		var fu = new FileUtils();
		if(sortMode.equals("-a")) {
			for(int i = 0; i<inDirectoryFile.size(); i++) {
				if(i<=1) {
					sortedList = ms.merge(fu.reader(inDirectoryFile.get(0)), fu.reader(inDirectoryFile.get(1)), Comparator.<Integer>naturalOrder());
				}
				else {
					sortedList = ms.merge(sortedList, fu.reader(inDirectoryFile.get(i)), Comparator.<Integer>naturalOrder());
					}		
			}
		}
		else {
			for(int i = 0; i<inDirectoryFile.size(); i++) {
				if(i<=1) {
					sortedList = ms.merge(fu.reader(inDirectoryFile.get(0)), fu.reader(inDirectoryFile.get(1)), Comparator.<Integer>naturalOrder().reversed());
				}
				else {
					sortedList = ms.merge(sortedList, fu.reader(inDirectoryFile.get(i)), Comparator.<Integer>naturalOrder().reversed());
					}		
			}
		}
		
		fu.write(sortedList, outName, outDirectory);
	}

}
