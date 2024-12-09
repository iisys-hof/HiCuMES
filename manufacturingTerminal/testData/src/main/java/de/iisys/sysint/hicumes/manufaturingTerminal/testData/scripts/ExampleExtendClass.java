package de.iisys.sysint.hicumes.manufaturingTerminal.testData.scripts;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.iisys.sysint.hicumes.core.utils.fileSystem.ZipFilesService;
import de.iisys.sysint.hicumes.mappingBackend.extendedClassCreator.ExtendedClassCreatorService;
import de.iisys.sysint.hicumes.mappingBackend.models.frontendFieldExtension.AllClassExtension;
import de.iisys.sysint.hicumes.mappingBackend.models.frontendFieldExtension.ClassExtension;
import net.bytebuddy.dynamic.DynamicType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class ExampleExtendClass {
    public static void main(String[] args) throws Exception {
        //TODO change path to local one.
        String inputFile = "core/entities/target/hicumes-entities.jar";
        String outputFile = "manufacturingTerminal/testData/target/entities-1.0-SNAPSHOT.jar";
        String targetJarMT = "manufacturingTerminal/backend/target/manufacturingTerminalBackend";
        String targetJarMA = "schemaMapper/backend/target/mappingBackend";
        String targetJarMTwar = "manufacturingTerminal/backend/target/manufacturingTerminalBackend.war";
        String targetJarMAwar = "schemaMapper/backend/target/mappingBackend.war";
        String relativeZipPath = "/WEB-INF/lib/entities-1.0-SNAPSHOT.jar";
        String updateClassJson = "manufacturingTerminal/testData/exampleExtendedClass/exampleExtendedClass.json";


        if(args.length == 6)
        {
            inputFile = args[1];
            outputFile = args[2];
            targetJarMT = args[3];
            targetJarMA = args[4];
            updateClassJson = args[5];
        }
        else
        {
            System.out.println(String.format("Wrong number of arguments... Expected is 6, provided is %d", args.length));
            for(int i = 0; i< args.length; i++) {
                System.out.println(String.format("Command Line Argument %d is %s", i, args[i]));
            }
        }

        System.out.println(String.format("Input File is %s", inputFile));
        System.out.println(String.format("Output File is %s", outputFile));
        System.out.println(String.format("MFT War is %s", targetJarMT));
        System.out.println(String.format("Mapper War is %s", targetJarMA));
        System.out.println(String.format("Class Json is %s", updateClassJson));

        Files.copy(new File(inputFile).toPath(), new File(outputFile).toPath(), StandardCopyOption.REPLACE_EXISTING);

        var allClassExtension = loadExtensionConfiguration(updateClassJson);
        var generatedExtendedClasses = new ExtendedClassCreatorService().generateExtendedClasses(allClassExtension);
        saveExtendedClasses(outputFile, generatedExtendedClasses);
        new ZipFilesService().copyFileIntoZip(outputFile, targetJarMT, relativeZipPath);
        new ZipFilesService().copyFileIntoZip(outputFile, targetJarMA, relativeZipPath);
        new ZipFilesService().copyFileIntoZip(outputFile, targetJarMTwar, relativeZipPath);
        new ZipFilesService().copyFileIntoZip(outputFile, targetJarMAwar, relativeZipPath);

    }

    private static void saveExtendedClasses(String outputFile, List<DynamicType.Unloaded<?>> generatedExtendedClasses) {
        File file = new File(outputFile);
        generatedExtendedClasses.forEach(generated -> {
            try {
                generated.inject(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static AllClassExtension loadExtensionConfiguration(String updateClassJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File jsonFile = new File(updateClassJson);
        ClassExtension[] classExtensions = mapper.readValue(jsonFile, ClassExtension[].class);

        return new AllClassExtension(List.of(classExtensions));
    }

}
