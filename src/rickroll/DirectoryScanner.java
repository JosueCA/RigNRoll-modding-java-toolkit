package rickroll;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import rickroll.log.RickRollLog;

public class DirectoryScanner {

	public String encodedExtension = "no_extension";
	public File directory = null;
	public ArrayList<String> encodedFiles = new ArrayList<String>();
	
	public DirectoryScanner(File directory, String encodedExtension) {
		this.directory = directory;
		this.encodedExtension = encodedExtension;
	}
	public void recursiveScan() {
		File[] files = this.directory.listFiles();
	    for (File file : files) {
	    	if (file.isFile()) {
                this.recursiveScanProcessFile(file);
            }
	    	if(file.isDirectory()) {
	    		this.recursiveScan(file);
            }
	    }
	    
	    RickRollLog.log("DirectoryScanner recursive scan ended with a total: " + this.encodedFiles.size());
    }
	
	public void recursiveScan(File directory) {
		File[] files = directory.listFiles();
	    for (File file : files) {
	    	if (file.isFile()) {
                this.recursiveScanProcessFile(file);
            }
	    	if(file.isDirectory()) {
	    		this.recursiveScan(file);
            }
	    }
    }

	public void recursiveScanProcessFile(File file) {
		if (file.isFile() && file.getName().endsWith(this.encodedExtension)) {
			RickRollLog.log("DirectoryScanner found file: " + file.getAbsolutePath());
			encodedFiles.add(file.getAbsolutePath());
        }
    }
}
