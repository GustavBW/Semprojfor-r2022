package dk.sdu.se_f22.productmodule.management.GUI;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class FileChooser {
    private JFileChooser chooser;

    public FileChooser() {
        chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setDialogTitle("CHOOSE FILE TO LOAD");
    }
    private String selectedFile;

    public void open(){
        int returnValue = chooser.showOpenDialog(null);
        chooser.requestFocus();
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            System.out.println(filePath);
            forwardResult(filePath);
        }
    }
    public void forwardResult(String filepath){
        App.loadFileFrom(filepath);
    }
}
