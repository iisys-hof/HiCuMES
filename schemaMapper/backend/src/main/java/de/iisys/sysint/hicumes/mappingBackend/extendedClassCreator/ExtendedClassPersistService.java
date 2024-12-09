package de.iisys.sysint.hicumes.mappingBackend.extendedClassCreator;

import de.iisys.sysint.hicumes.core.utils.exceptions.database.DatabaseQueryException;
import de.iisys.sysint.hicumes.core.utils.exceptions.fileSystem.FileSystemUtilsException;
import de.iisys.sysint.hicumes.core.utils.fileSystem.ZipFilesService;
import de.iisys.sysint.hicumes.mappingBackend.exceptions.fieldExtensionExceptions.FieldExtensionException;
import de.iisys.sysint.hicumes.mappingBackend.mapping.services.MappingPersistService;
import de.iisys.sysint.hicumes.mappingBackend.models.frontendFieldExtension.AllClassExtension;
import de.iisys.sysint.hicumes.mappingBackend.models.frontendFieldExtension.ClassExtension;
import de.iisys.sysint.hicumes.mappingBackend.models.frontendFieldExtension.MemberExtension;
import de.iisys.sysint.hicumes.mappingEngine.mappingEngine.MappingDependencyTreeGenerator;
import net.bytebuddy.dynamic.DynamicType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class ExtendedClassPersistService {

    @EJB
    ZipFilesService zipFilesService;

    @EJB
    MappingPersistService mappingPersistService;

    @Inject
    @ConfigProperty(name="MappingBackendDeploymentPath", defaultValue = "")
    private Optional<String> mappingBackendDeploymentPath;

    @Inject
    @ConfigProperty(name="ManufacturingTerminalBackendDeploymentPath", defaultValue = "")
    private Optional<String> manufacturingTerminalBackendDeploymentPath;

    public void saveExtended(AllClassExtension allClassExtension) throws FieldExtensionException, FileSystemUtilsException, DatabaseQueryException {

        //TODO path needs to be the deployment path of the war archive
        allClassExtension.setId(1);
        mappingPersistService.getPersistenceManager().merge(allClassExtension);
        var generatedExtendedClasses = new ExtendedClassCreatorService().generateExtendedClasses(allClassExtension);
        deleteColumns(allClassExtension);
        String jBossPath = System.getProperty("jboss.server.base.dir");

        var pathMappingBackendDeployment = mappingBackendDeploymentPath.orElse(jBossPath + "/deployments/mappingBackend.war");
        var pathManufacturingTerminalBackendDeployment = manufacturingTerminalBackendDeploymentPath.orElse(jBossPath + "/deployments/manufacturingTerminalBackend.war");

        System.out.println(pathMappingBackendDeployment);

        var pathTomee = Paths.get(pathMappingBackendDeployment).getParent().getParent().getParent();
        var tempJarFile = pathTomee.toString() + "/databaseEntities.jar";
        String relativeZipPath = "/WEB-INF/lib/entities-1.0-SNAPSHOT.jar";

        zipFilesService.copyFileFromZip(tempJarFile,pathMappingBackendDeployment,relativeZipPath);
        saveExtendedClasses(tempJarFile, generatedExtendedClasses);
        zipFilesService.copyFileIntoZip(tempJarFile,pathMappingBackendDeployment,relativeZipPath);
        zipFilesService.copyFileFromZip(tempJarFile,pathManufacturingTerminalBackendDeployment,relativeZipPath);
        saveExtendedClasses(tempJarFile, generatedExtendedClasses);
        zipFilesService.copyFileIntoZip(tempJarFile,pathManufacturingTerminalBackendDeployment,relativeZipPath);
    }


    public AllClassExtension getAllClasses() {
        var entityFilter = "de.iisys.sysint.hicumes.core.entities";
        var tree = new MappingDependencyTreeGenerator().generate(mappingPersistService.getPersistenceManager().getMetamodel(), entityFilter);

        List<ClassExtension> listClasses = new ArrayList<>();

        for (var className: tree.getTree().keySet()) {
            var classId = tree.getTree().get(className).getId();
            var fields = tree.getTree().get(className).getFields();
            var isCustomFields = tree.getTree().get(className).getIsCustomerField();

            var outputFields = new ArrayList<MemberExtension>();
            for (var fieldName: fields.keySet()) {
                var fieldType = fields.get(fieldName);
                var isCustomField = isCustomFields.get(fieldName);
                var outputField = new MemberExtension(fieldName,fieldType, isCustomField);
                outputFields.add(outputField);
            }


            var classExtension = new ClassExtension(className, classId,outputFields);
            listClasses.add(classExtension);
        }


        var allClassExtension = new AllClassExtension(listClasses);
        return allClassExtension;
    }

    private void deleteColumns(AllClassExtension allClassExtension) throws DatabaseQueryException {
        var oldClassExtension = getAllClasses();
        var deletedMembers = new ExtendedClassChangeService().getDeletedMembers(allClassExtension, oldClassExtension);

        for (var member: deletedMembers) {
            var idSplit = member.getClassId().split("\\.");

            mappingPersistService.getPersistenceManager().deleteColumn(idSplit[idSplit.length-1], member.getName());
        }
    }


    private void saveExtendedClasses(String outputFile, List<DynamicType.Unloaded<?>> generatedExtendedClasses) {
        File file = new File(outputFile);
        generatedExtendedClasses.forEach(generated -> {
            try {
                generated.inject(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
