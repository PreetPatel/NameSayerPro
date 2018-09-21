package app;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

public class Name {

    private List<File> _files;
    private String _fullName;
    private String _name;
    private JFXButton _button;

    public Name(File file){
        _name = getFileName(file);
        _files = getAllFilesOfName(new File(NameSayer.creationsPath));
    }

    public Name(String name){
        _name = name;
        _files = getAllFilesOfName(new File(NameSayer.creationsPath));
    }

    private String getFileName(File file) {
        String displayName = file.getName();
        _fullName = displayName;
        displayName = displayName.replaceAll("^[^_]*_[^_]*_[^_]*_", "");
        displayName = displayName.replaceAll("[.][^.]+$", "");
        return displayName;
    }

    /**
     * checks if the file provided for the name is a valid .wav file
     * @return true if the file is valid
     */
    public boolean isValid() {

        if (!FilenameUtils.getExtension(_fullName).equals("wav")) {
            return false;
        }
        return true;
    }

    public HashMap<String, File> getVersions() {
        HashMap<String, File> returnVersions= new HashMap<>();
        int i = 1;
        for (File file : _files) {
            returnVersions.put("Version "+ i, file);
            i++;
        }
        return returnVersions;
    }

    public List<File> getAllFilesOfName(File dir){
        //File dir = new File(NameSayer.creationsPath);
        FileFilter filter = new WildcardFileFilter("*_"+_name+".wav");
        File[] files = dir.listFiles(filter);
        return Arrays.asList(files);

    }

    public File get(int i) {
        return _files.get(i);
    }

    public int size(){
        return _files.size();
    }

    public String getName(){
        return _name;
    }

    public List<File> getFiles() {
        return _files;
    }

    public JFXButton generateButton(List<JFXButton> selectedButtonsList){
        //create a new button to represent the item
        JFXButton button = new JFXButton();
        button.setMnemonicParsing(false);
        button.setText(this.getName());
        button.setId(this.getName());
        button.setStyle("-fx-background-color: #03b5aa; -fx-text-fill: white; -fx-font-family: 'Lato Medium'; -fx-font-size: 25;");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!selectedButtonsList.contains(button)) {
                    button.setStyle("-fx-background-color: #256961; -fx-text-fill: white; -fx-font-family: 'Lato Medium'; -fx-font-size: 25;");
                    selectedButtonsList.add(button);
                } else {
                    button.setStyle("-fx-background-color: #03b5aa; -fx-text-fill: white; -fx-font-family: 'Lato Medium'; -fx-font-size: 25;");
                    selectedButtonsList.remove(button);
                }
            }
        });

        _button = button;
        return button;
    }

    public JFXButton getButton() {
        return _button;
    }

    public void destroy() {
        _files.clear();
    }
}
