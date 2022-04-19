package GUI;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class FileChooser { //This works just fine but won't compile in here.
    /*
    JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setDialogTitle("CHOOSE FILE TO LOAD");

    int returnValue = chooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
        File selectedFile = chooser.getSelectedFile();
        if (chooser.getSelectedFile().isFile()) {
            System.out.println("You chose the file: " + selectedFile.getAbsolutePath());
        }
        else {
            System.out.println("You chose a path: " + selectedFile.getAbsolutePath());
        }
    }
     */
}
