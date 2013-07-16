package fr.warlog.bus;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;

/**
 * Get the hook on modified files 
 * @author ABC-OBJECTIF\philippe.demanget
 */
public class FileHookMgt {
	
	public FileHookMgt(){
	}
	
	
	public static void trackFile() throws IOException, InterruptedException{
//		Path path = FileSystems.getDefault().getPath("/var/log/");
		Path path = FileSystems.getDefault().getPath("/");
		WatchService watcher = FileSystems.getDefault().newWatchService();
		path.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY );
		WatchKey poll = watcher.take();
		System.out.println( poll.pollEvents().get(0) );
	}
	
	public static void main(String[] args) {
		try {
			while (true){
				trackFile();
			}
		} catch (IOException  | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
