package de.iisys.sysint.hicumes.core.utils.fileSystem;


import de.iisys.sysint.hicumes.core.utils.exceptions.fileSystem.FileSystemUtilsException;

import javax.ejb.Singleton;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

@Singleton
public class ZipFilesService {
    public void copyFileIntoZip(String fileToCopy, String zipFile, String relativeZipPath) throws FileSystemUtilsException {
        Path fileToCopyPath = Paths.get(fileToCopy);
        Path zipFilePath = Paths.get(zipFile);
        if(zipFile.endsWith(".war")) {
            try (FileSystem fs = FileSystems.newFileSystem(zipFilePath, null)) {
                Path fileInsideZipPath = fs.getPath(relativeZipPath);
                Files.copy(fileToCopyPath, fileInsideZipPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new FileSystemUtilsException("Failed to copy to zip:" + "fileToCopy: " + fileToCopy + " zipFile: " + zipFile + " relativeZipPath:" + relativeZipPath, e);
            }
        }
        else
        {
            try {
                Path fullPath = Paths.get(zipFile, relativeZipPath);
                Files.copy(fileToCopyPath, fullPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new FileSystemUtilsException("Failed to copy to folder:" + "fileToCopy: " + fileToCopy + " folder: " + zipFile + " relativePath:" + relativeZipPath, e);
            }
        }
    }

    public void copyFileFromZip(String fileToCopy, String zipFile, String relativeZipPath) throws FileSystemUtilsException {
        Path fileToCopyPath = Paths.get(fileToCopy);
        Path zipFilePath = Paths.get(zipFile);
        if(zipFile.endsWith(".war")) {
            try (FileSystem fs = FileSystems.newFileSystem(zipFilePath, null)) {
                Path fileInsideZipPath = fs.getPath(relativeZipPath);
                Files.copy(fileInsideZipPath, fileToCopyPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new FileSystemUtilsException("Failed to copy from zip:" + "fileToCopy: " + fileToCopy + " zipFile: " + zipFile + " relativeZipPath:" + relativeZipPath, e);
            }
        }
        else
        {
            try {
                Path fullPath = Paths.get(zipFile, relativeZipPath);
                Files.copy(fullPath, fileToCopyPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new FileSystemUtilsException("Failed to copy from folder:" + "fileToCopy: " + fileToCopy + " folder: " + zipFile + " relativePath:" + relativeZipPath, e);
            }
        }
    }

    public boolean doesFileExistInsideZip(String zipFile, String relativeZipPath) throws FileSystemUtilsException {
        Path zipFilePath = Paths.get(zipFile);
        try (FileSystem fs = FileSystems.newFileSystem(zipFilePath, null)) {
            Path fileInsideZipPath = fs.getPath(relativeZipPath);
            var doesExist = Files.exists(fileInsideZipPath);
            return doesExist;
        }
        catch (IOException e) {
            throw new FileSystemUtilsException("Failed to check if file Exists: zipFile: " + zipFile + " relativeZipPath:" + relativeZipPath,e);
        }
    }

    public String loadFileInsideZip (String zipPath) throws FileSystemUtilsException {
        var splitMarker = "#####";
        var extensions = new String[]{".war", ".jar", ".zip"};
        for (var fileEnding: extensions) {
            zipPath = zipPath.replace(fileEnding + "/", fileEnding +splitMarker +  "/" );
        }

        var split = zipPath.split(splitMarker);
        LinkedList<String> linkedList= new LinkedList(Arrays.asList(split));

        var first = linkedList.pollFirst();

        Path currentPath = Path.of(first);
        for (var link: linkedList) {
            FileSystem fs = getFileSystem(currentPath);
            currentPath = fs.getPath(link);
        }

        var file = getBufferedReader(currentPath);
        var fileContent = file.lines().collect(Collectors.joining(System.lineSeparator()));
        return fileContent;

    }

    private FileSystem getFileSystem(Path currentPath) throws FileSystemUtilsException {
        try {
            return FileSystems.newFileSystem(currentPath, null);
        } catch (IOException e) {
            throw new FileSystemUtilsException("Could not open filesystem:  " + currentPath, e);
        }
    }

    public BufferedReader getBufferedReader(Path scriptPath) throws FileSystemUtilsException {
        try {
            return  Files.newBufferedReader(scriptPath);
        } catch (IOException e) {
            throw new FileSystemUtilsException("Could not open script file:  " + scriptPath, e);
        }
    }
}
