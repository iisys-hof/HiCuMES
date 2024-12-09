package de.iisys.sysint.hicumes.mappingEngine.mappingEngine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.iisys.sysint.hicumes.core.entities.jsonViews.JsonViews;
import de.iisys.sysint.hicumes.core.utils.exceptions.fileSystem.FileSystemUtilsException;
import de.iisys.sysint.hicumes.core.utils.fileSystem.ZipFilesService;
import de.iisys.sysint.hicumes.core.utils.json.JsonTransformer;
import de.iisys.sysint.hicumes.core.utils.logger.Logger;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.mappingException.ExecuteMappingException;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.mappingException.LoadMappingException;
import de.iisys.sysint.hicumes.mappingEngine.exceptions.mappingException.MappingException;
import de.iisys.sysint.hicumes.mappingEngine.models.MappingWorkflowResult;
import de.iisys.sysint.hicumes.mappingEngine.models.mapping.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapperEngine {

    ZipFilesService zipFilesService = new ZipFilesService();
    ScriptEngine scriptEngine;

    private final Logger logger = Logger.getInstance(this.getClass().getName(), this.getClass().getSimpleName());

    public MappingWorkflowResult doMappingJS(MappingDependencyTree tree, MappingInput inputData, MappingConfiguration mapping) throws MappingException {

        if (scriptEngine == null) {
            scriptEngine = initScriptEngine();
        }
        Invocable invocable = (Invocable) scriptEngine;
        Object result = null;
        MappingLogging mappingLogging = new MappingLogging();

        try {
            var treeJson = tree.toJson();
            JsonNode inputDataJson = inputData.getJsonNode();
            String mappingJson = mapping.toJson();

            result = invocable.invokeFunction("doMapping", treeJson, inputDataJson, mappingJson, mappingLogging);

        } catch (ScriptException e) {
            throw new ExecuteMappingException("Failed to execute mapping.", e);
        } catch (NoSuchMethodException e) {
            throw new ExecuteMappingException("No method to execute for mapping.", e);
        } catch (JsonProcessingException e) {
            throw new ExecuteMappingException("Failed to parse input data.", e);
        }
        var mappingOutput = new MappingOutput((HashMap<String, Object>) result);


        var mappingWorkflowResult =   new MappingWorkflowResult(inputData, mappingOutput, mappingLogging, tree, null);
        return mappingWorkflowResult;
    }

    //TODO make code more efficient and divide into smaller functions
    public MappingWorkflowResult doMappingXSLT(MappingDependencyTree tree, MappingInput inputData, MappingConfiguration mapping) throws MappingException {

        Object result = null;
        MappingLogging mappingLogging = new MappingLogging();

        ObjectMapper xmlMapper = new XmlMapper();
        String xml = null;
        try {
            xml = xmlMapper.writeValueAsString(inputData.getJsonNode());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        //Remove all ':' in tags and replace them with '_'
        Pattern pattern = Pattern.compile("\\</?[^\\<\\>]+\\>");
        Matcher matcher = pattern.matcher(xml);

        while (matcher.find())
        {
            var match = matcher.group();
            if(match.contains(":"))
            {
                xml = xml.replace(match, match.replace(":", "_"));
            }
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        var hashmap = new HashMap<String, Object>();
        try
        {
            builder = factory.newDocumentBuilder();

            //logger.logMessage("XML Input: ", "-", xml);
            Document doc = builder.parse( new InputSource( new StringReader( xml ) ) );

            Transformer transformer = new net.sf.saxon.BasicTransformerFactory().newTransformer(
                    new StreamSource(new StringReader(mapping.getXsltRules())));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMResult output = new DOMResult();
            transformer.transform(new DOMSource(doc), output);

            Document document = (Document) output.getNode();

            /*Transformer dings = TransformerFactory.newInstance().newTransformer();
            StringWriter bums = new StringWriter();
            dings.transform(new DOMSource(document), new StreamResult(bums));
            String xmlDoc = bums.toString();

            logger.logMessage("XML Document: ", "-", xmlDoc);
            */
            XPath xPath = XPathFactory.newInstance().newXPath();

            NodeList nodeListExportJson = (NodeList) xPath.compile("/output/" + "EXPORT_JSON").evaluate(document, XPathConstants.NODESET);

            if(nodeListExportJson != null && nodeListExportJson.getLength() > 0)
            {
                Transformer transf = TransformerFactory.newInstance().newTransformer();
                transf.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                ArrayList<String> xmlExportList = new ArrayList<>();
                for(int i = 0; i < nodeListExportJson.getLength(); ++i)
                {
                    StringWriter stringWriter = new StringWriter();
                    transf.transform(new DOMSource(nodeListExportJson.item(i)), new StreamResult(stringWriter));
                    xmlExportList.add(stringWriter.toString());
                }
                //System.out.println(nodeListExportJson);

                try {
                    JsonTransformer jsonTransformer = new JsonTransformer(JsonViews.SchemaMapperDefault.class);
                    ArrayNode outputNode = xmlMapper.createArrayNode();
                    for (String xmlExport: xmlExportList) {
                        outputNode.add(xmlMapper.readTree(xmlExport.getBytes()));
                        //logger.logMessage("XML Export: ", "-", xmlExport);
                    }
                    hashmap.put("EXPORT_JSON", jsonTransformer.writeValueAsString(outputNode));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for (var className: tree.getTree().keySet()) {
                Class<?> clazz = Class.forName(tree.getTree().get(className).getId());
                NodeList nodeList = (NodeList) xPath.compile("/output/" + className).evaluate(document, XPathConstants.NODESET);
                ArrayList<Object> list = new ArrayList<>(nodeList.getLength());
                for(int i = 0; i < nodeList.getLength(); ++i)
                {
                    NodeList exportJson = (NodeList) xPath.compile(".//EXPORT_JSON").evaluate(nodeList.item(i), XPathConstants.NODESET);
                    if(exportJson != null && exportJson.getLength() > 0)
                    {
                        Transformer transf = TransformerFactory.newInstance().newTransformer();
                        transf.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");


                        for(int j = 0; j < exportJson.getLength(); ++j)
                        {
                            StringWriter stringWriter = new StringWriter();
                            transf.transform(new DOMSource(exportJson.item(j)), new StreamResult(stringWriter));

                            String xmlExport = stringWriter.toString();


                            JsonTransformer jsonTransformer = new JsonTransformer(JsonViews.SchemaMapperDefault.class);
                            JsonNode outputNode = xmlMapper.readTree(xmlExport.getBytes());
                            String jsonString = jsonTransformer.writeValueAsString(outputNode);

                            exportJson.item(j).getParentNode().setTextContent(jsonString);
                        }
                    }

                    StringWriter sw = new StringWriter();
                    Transformer serializer = TransformerFactory.newInstance().newTransformer();
                    serializer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                    serializer.transform(new DOMSource(nodeList.item(i)), new StreamResult(sw));
                    String xmlString = sw.toString();

                    //logger.logMessage("XML Output: ", "-", xmlString);
                    JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();



                    Object object = jaxbUnmarshaller.unmarshal(new StringReader(xmlString));
                    list.add(object);
                }
                hashmap.put(className, list);
            }

            //TODO Error handling
        } catch (Exception e) {
            e.printStackTrace();
        }

        var mappingOutput = new MappingOutput(hashmap);

        var mappingWorkflowResult =   new MappingWorkflowResult(inputData, mappingOutput, mappingLogging, tree, null);
        return mappingWorkflowResult;
    }

    private ScriptEngine initScriptEngine() throws MappingException {
        ScriptEngineManager scriptEngineManager= new ScriptEngineManager();
        var scriptEngine = scriptEngineManager.getEngineByName("nashorn");
        var lodashExternalLibrary = loadScript("/lodash.min.js");
        var script = loadScript("/mapper.js");

        try {
            scriptEngine.eval(lodashExternalLibrary);
            scriptEngine.eval(script);
        } catch (ScriptException e) {
            throw new ExecuteMappingException("Could not load script files", e);
        }
        return scriptEngine;
    }

    private String loadScript(String path) throws MappingException {
        var fullPath = getPathForFilesystemLoad(path);
        try {
            return zipFilesService.loadFileInsideZip(fullPath);
        } catch (FileSystemUtilsException e) {
            throw new MappingException("Failed to load script path: " + path + " FullPath: " +fullPath,e );
        }
    }

    private String getPathForFilesystemLoad(String path) throws LoadMappingException {
        try {
            URI uri = getClass().getResource(path).toURI();

            var osName = System.getProperty("os.name");

            logger.logMessage("MapperEngine URI Path: " +  uri.toString(), "~");

            // Wrong path wildfly/deployments/; correct: wildfly/standalone/deployments at least in windows environments
            // TODO: Check path in other OSes
            var outputPath = uri.toString().replace("/content/", "/deployments/").replace("file:/","");
            System.out.println("URI_PATH       = " + uri.toString());
            System.out.println("OUTPUT_PATH    = " + outputPath);
            System.out.println("OS NAME = " + osName);
            if (osName.contains("Windows")) {
                // Replaced to get correct path in Windows
                outputPath = outputPath.replace("vfs:/", "").replace("/bin", "/standalone");
            }
            if (osName.toLowerCase().contains("mac")) {
                outputPath = System.getProperty("jboss.server.base.dir") + outputPath.replace("vfs:", "");
            }
            if (osName.toLowerCase().contains("linux")) {
                outputPath = System.getProperty("jboss.server.base.dir") + outputPath.replace("vfs:", "");
            }
            System.out.println("jboss.server.base.dir = " + System.getProperty("jboss.server.base.dir"));
            System.out.println("OUTPUT_PATH:EDIT = " + outputPath);
            return outputPath;
//
//            System.out.println("OUTPUT_PATH WITH REPLACE = " + outputPath);
//            String jBossPath = System.getProperty("jboss.server.base.dir");
//            var pathWarFile = jBossPath + "/deployments/mappingBackend";
//
//            var relativePath = uri.toString().split("mappingBackend")[1];
//            System.out.println("OUTPUT_PATH COMBI = " + pathWarFile + relativePath);
//            System.out.println("jboss.server.base.dir = " + System.getProperty("jboss.server.base.dir"));
//            return pathWarFile + relativePath;
//



        } catch (URISyntaxException e) {
            throw new LoadMappingException("Could not prepare load path:  " + path, e);
        }
    }
}
