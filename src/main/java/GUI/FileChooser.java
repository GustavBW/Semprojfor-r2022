package GUI;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class FileChooser extends FuncButton {

    public FileChooser() {super("Choose File");}
    private String selectedFile;
    @Override
    public void onClicked(){
    JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setDialogTitle("CHOOSE FILE TO LOAD");

    int returnValue = chooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
        File selectedFile = chooser.getSelectedFile();

    }
        //return selectedFile;
    }
}
