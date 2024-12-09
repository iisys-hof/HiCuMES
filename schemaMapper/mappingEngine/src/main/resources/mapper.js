//@ sourceURL=D:/iisys/HiCuMES/HiCuMES/schemaMapper/mappingEngine/src/main/resources/mapper.js
var debug = false;

//global input, to have the input in every function available
var currentInput;
//global input, to have the input in every function available
var loggingPersistence;
//global input, to have the input in every function available
var globalClassTree;
//global input, to have the input in every function available
var globalMappingRule;
//global input, to have the input in every function available
var globalOutput;

function doMapping(classTreeString, inputString, mappingConfigString, logFile) {
    loggingPersistence = logFile;

    internalLogging("Parsing inputs");
    debugger
    var classTree = JSON.parse(classTreeString);
    var input = JSON.parse(inputString);

    currentInput = input;
    var mappingConfig = JSON.parse(mappingConfigString);
    internalLogging("Init return classes");
    debugLog("classTree = ")
    debugLog(classTree)
    var output = initAllClasses(classTree);

    internalLogging("Start mapping");

    //Save variables for global usage to simplify function call in ui (e.g. parseJson()-function)
    globalClassTree = classTree;
    globalOutput = output;

    doLoopWithOutput(mappingConfig, classTree, output,input);
    internalLogging("Finished mapping");

    debugLog("output = ");
    debugLog(output);
    return output;
}

function doLoopWithOutput(mappingConfig, classTree, output,input) {

    debugger
    debugLog("doLoopWithOutput()")
    debugLog("mappingConfig = ")
    debugLog(mappingConfig)
    debugLog("classTree = ")
    debugLog(classTree)
    debugLog("output = ")
    debugLog(output)
    debugLog("input = ")
    debugLog(input)

    for (var loopIndex in mappingConfig.loops) {


        debugLog("for()")
        debugLog("loopIndex = ")
        debugLog(loopIndex)

        var loopConfig = mappingConfig.loops[loopIndex];
        var loopInput = eval(loopConfig.inputSelector);


        debugLog("loopConfig = ")
        debugLog(loopConfig)
        debugLog("loopInput = ")
        debugLog(loopInput)

        if(loopConfig.outputSelector === 'output') {
            if(Array.isArray(loopInput)) {
                mapDataElementsLoopWithOutput(loopConfig, loopInput, classTree, output);
            } else {
                mapDataElementsLoopWithOutputAndNoInputLoop(loopConfig, loopInput, classTree, output);
            }
        } else {
            var className = getClassNameFromMappingRule(loopConfig.outputSelector);
            var loopClassTree = classTree[className].arrays;

            debugLog("className = ")
            debugLog(className)
            debugLog("loopClassTree = ")
            debugLog(loopClassTree)

            if (output[className].size()==0) {
                var outputClass = generateJavaClass(classTree[className].id);
                output[className].add(outputClass);
            }
            loopConfig.outputSelector = loopConfig.outputSelector.replace(className + ".", "");

            debugLog("loopConfig = ")
            debugLog(loopConfig)

            doLoop({ loops:[loopConfig]}, loopClassTree, output[className].get(0),input);
        }
    }
}

function mapDataElementsLoopWithOutputAndNoInputLoop(loopConfig, input, classTree, outerOutput) {

    debugger
    debugLog("mapDataElementsLoopWithOutputAndNoInputLoop()")
        debugLog("loopConfig = ")
        debugLog(loopConfig)
        debugLog("input = ")
        debugLog(input)
        debugLog("classTree = ")
        debugLog(classTree)
        debugLog("outerOutput = ")
        debugLog(outerOutput)

        for (var mappingIndex in loopConfig.mappings) {

            debugLog("for()")
            debugLog("mappingIndex = ")
            debugLog(mappingIndex)

            var __mappingRule =  loopConfig.mappings[mappingIndex];

            //Check, if mapping rule contains conditional operator - string.includes() is not supported in nashorn engine
            if(__mappingRule.rule.split('?').length > 1)
            {
                internalLogging("MappingRule has if-clause in mapDataElementsLoopWithOutputAndNoInputLoop(): " + __mappingRule.rule)
            }
            var className = getClassNameFromMappingRule(__mappingRule.rule);
            if (outerOutput[className].size()===0) {
                var outputClass = generateJavaClass(classTree[className].id);
                outerOutput[className].add(outputClass);
            }

            var output = {};
            output[className] = outerOutput[className].get(0);

            var subClassName = checkForSubclass(__mappingRule.rule)
            if(subClassName != null && output[className][subClassName] == null)
            {
                //if subclass is coredata object, the java classname starts with cD_ but not the member variable
                var subClass = generateJavaClass(getJavaClassName(subClassName));
                output[className][subClassName] = subClass
            }

            debugLog("__mappingRule = ")
            debugLog(__mappingRule)
            debugLog("className = ")
            debugLog(className)
            debugLog("output = ")
            debugLog(output)

            try {
                internalLogging("run: " +  __mappingRule.rule);
                //Save variables for global usage to simplify function call in ui (e.g. parseJson()-function)
                globalMappingRule = __mappingRule;
                eval(__mappingRule.rule);
                internalLogging(' - ' + eval(__mappingRule.inputSelector) +  " ---> " +  eval(__mappingRule.outputSelector));
            } catch (e) {
                internalError("Exception by executing Rule: " + __mappingRule.rule,e);
            }

        }
        doLoopWithOutput(loopConfig, classTree, outerOutput,input);
}

function mapDataElementsLoop(loopConfig, outerInput, classTree) {

    debugger
    debugLog("mapDataElementsLoop()")
    debugLog("loopConfig = ")
    debugLog(loopConfig)
    debugLog("outerInput = ")
    debugLog(outerInput)
    debugLog("classTree = ")
    debugLog(classTree)

    if(!Array.isArray(outerInput)) {
        outerInput = [outerInput];
    }
    var outputArray = [];
    for (var inputIndex in outerInput) {

        debugLog("for()")
        debugLog("inputIndex = ")
        debugLog(inputIndex)

        var input = outerInput[inputIndex];
        currentInput = input;

        var className = getClassNameFromSelector(loopConfig.outputSelector);
        var outputs = [];

        debugLog("input = ")
        debugLog(input)
        debugLog("currentInput = ")
        debugLog(currentInput)
        debugLog("className = ")
        debugLog(className)
        debugLog("outputs = ")
        debugLog(outputs)

            for (var mappingIndex in loopConfig.mappings) {

                var output = generateJavaClass(classTree[className].id);

                debugLog("for()")
                debugLog("mappingIndex = ")
                debugLog(mappingIndex)

                debugLog("classTree[className].id = ")
                debugLog(classTree[className].id)
                debugLog("output = ")
                debugLog(output)

                var __mappingRule = loopConfig.mappings[mappingIndex];

                //Check, if mapping rule contains conditional operator - string.includes() is not supported in nashorn engine
                if(__mappingRule.rule.split('?').length > 1)
                {
                    internalLogging("MappingRule has if-clause in mapDataElementsLoop(): " + __mappingRule.rule)
                }

                var subClassName = checkForSubclass(__mappingRule.rule)
                if(subClassName != null && output[subClassName] == null)
                {
                    //if subclass is coredata object, the java classname starts with cD_ but not the member variable
                    var subClass = generateJavaClass(getJavaClassName(subClassName));
                    output[subClassName] = subClass
                }

                try {
                    internalLogging("run: " + __mappingRule.rule);
                    eval(__mappingRule.rule);
                    internalLogging(' - ' + eval(__mappingRule.inputSelector) + " ---> " + eval(__mappingRule.outputSelector));
                } catch (e) {
                    internalError("Exception by executing Rule: " + __mappingRule.rule, e);
                }

                debugLog("__mappingRule = ")
                debugLog(__mappingRule)

                outputs.push(output);

            }
            var loopClassTree = classTree[className].arrays;

            debugLog("loopClassTree = ")
            debugLog(loopClassTree)

            doLoop(loopConfig, loopClassTree, output, input);
            for (var outputIndex in outputs) {
                outputArray.push(outputs[outputIndex]);
            }
    }

    debugLog("outputArray = ")
    debugLog(outputArray)

    return outputArray;
}

function mapDataElementsLoopWithOutput(loopConfig, outerInput, classTree, outerOutput) {

    debugger
    debugLog("mapDataElementsLoopWithOutput()")
    debugLog("loopConfig = ")
    debugLog(loopConfig)
    debugLog("outerInput = ")
    debugLog(outerInput)
    debugLog("classTree = ")
    debugLog(classTree)
    debugLog("outerOutput = ")
    debugLog(outerOutput)

    if(!Array.isArray(outerInput)) {
        outerInput = [outerInput];
    }
    for (var inputIndex in outerInput) {

        debugLog("for()")
        debugLog("inputIndex = ")
        debugLog(inputIndex)

        var input = outerInput[inputIndex];
        currentInput = input;
        var output = {};

        debugLog("input = ")
        debugLog(input)

        for (var mappingIndex in loopConfig.mappings) {

            debugLog("for()")
            debugLog("mappingIndex = ")
            debugLog(mappingIndex)

            var __mappingRule = _.cloneDeep(loopConfig.mappings[mappingIndex]);

            var createNewObject = false;

            if(__mappingRule.rule.split('*!').length > 1)
            {
                createNewObject = true;
                __mappingRule.rule = __mappingRule.rule.replace('*!', '');
            }


            debugLog("__mappingRule = ")
            debugLog(__mappingRule)

            try {
                var className = getClassNameFromMappingRule(loopConfig.mappings[mappingIndex].rule);
                classTree[className].id
            } catch (e) {
                internalError("Exception by extracting classname from Rule: " + __mappingRule.rule,e);
                continue;
            }

            //Check, if mapping rule contains conditional operator - string.includes() is not supported in nashorn engine
            if(__mappingRule.rule.split('?').length > 1 && _.isNil(eval(__mappingRule.rule.split("=")[1])))
            {
                internalLogging("MappingRule has if-clause in mapDataElementsLoopWithOutput() where the input is null, continuing with next loop: " + __mappingRule.rule)
                continue;
            }
            if (_.isNil(output[className])) {
                output[className] = generateJavaClass(classTree[className].id);
            }
            else if(createNewObject)
            {
                //Add the previous created object to our output
                outerOutput[className].add(output[className]);
                //And create a new one
                output[className] = generateJavaClass(classTree[className].id);
            }

            var subClassName = checkForSubclass(__mappingRule.rule)
            if(subClassName != null && output[className][subClassName] == null)
            {
                //if subclass is coredata object, the java classname starts with cD_ but not the member variable
                var subClass = generateJavaClass(getJavaClassName(subClassName));
                output[className][subClassName] = subClass
            }

            try {
                internalLogging("run: " +  __mappingRule.rule);
                eval(__mappingRule.rule);
                internalLogging(' - ' + eval(__mappingRule.inputSelector) +  " ---> " +  eval(__mappingRule.outputSelector));
            } catch (e) {
                internalError("Exception by executing Rule: " + __mappingRule.rule,e);
            }
            debugLog(__mappingRule)
        }
        for (var className in output) {

            debugLog("for()")
            debugLog("className = ")
            debugLog(className)

            doLoopWithOutput(loopConfig, classTree, outerOutput,input);
            outerOutput[className].add(output[className]);

            debugLog("outerOutput[className] = ")
            debugLog(outerOutput[className])
        }
    }

    debugLog("outerOutput = ")
    debugLog(outerOutput)
    return outerOutput;
}

function doLoop(mappingConfig, classTree, output,input){

    debugger
    debugLog("doLoop()")
    debugLog("mappingConfig = ")
    debugLog(mappingConfig)
    debugLog("classTree = ")
    debugLog(classTree)
    debugLog("output = ")
    debugLog(output)
    debugLog("input = ")
    debugLog(input)

    for (var loopIndex in mappingConfig.loops) {

        debugLog("for()")
        debugLog("loopIndex = ")
        debugLog(loopIndex)

        var loopConfig = mappingConfig.loops[loopIndex];

        var loopInput = eval(loopConfig.inputSelector);

        var resultArray = mapDataElementsLoop(loopConfig, loopInput, classTree);

        debugLog("loopConfig = ")
        debugLog(loopConfig)
        debugLog("loopInput = ")
        debugLog(loopInput)
        debugLog("resultArray = ")
        debugLog(resultArray)

        for (var index in resultArray) {

            debugLog("for()")
            debugLog("index = ")
            debugLog(index)
            debugLog("loopConfig.outputSelector = ")
            debugLog(loopConfig.outputSelector)
            try {
                eval(loopConfig.outputSelector + 'Add(resultArray[index])');
            } catch (e) {
                internalError("Function not implemented: " + loopConfig.outputSelector + 'Add(resultArray[index])',e);
            }

        }
    }
}

function generateJavaClass(classPath) {
    var classType = Java.type(classPath)
    var classInstance = new classType();
    return classInstance;
}


function getClassNameFromMappingRule(rule) {
    var lastLine =  rule.split('\n').pop();

    var className = lastLine.split('.')[1].split(' ')[0].split('=')[0]
    return className
}

function checkForSubclass(rule) {
    var lastLine =  rule.split('\n').pop();
    //"output.productionOrder.product.externalId = input.d.ActualReleaseDate" -> lastLine => "output.productionOrder.product.externalId = input.d.ActualReleaseDate"
    // replaceAll(' ','') => "output.productionOrder.product.externalId=input.d.ActualReleaseDate"
    // split('=') => ["output.productionOrder.product.externalId", "input.d.ActualReleaseDate"]
    // [0].split('.') => ["output", "productionOrder", "product", "externalId"]
    var fields = lastLine.replaceAll(' ', '').split('=')[0].split('.')
    // => length of fields <= 3 means, that there is no subclass -> output.class.member
    // => length > 3 means, that there is a subclass -> output.class.subclass.member
    if(fields.length > 3) {
        return fields[fields.length - 2]
    }
    else
    {
        return null
    }
}

function getJavaClassName(subClassName)
{
    var cd_subClassName = 'cD_' + subClassName.charAt(0).toUpperCase() + subClassName.slice(1);
    if(globalClassTree[subClassName] !== undefined)
    {
        return globalClassTree[subClassName].id
    }
    else
    {
        return globalClassTree[cd_subClassName].id
    }

}

function  getClassNameFromSelector(selector) {
    var className = selector.split('.').pop();
    return className;
}

function  initAllClasses( classTree) {
    var output = generateJavaClass('java.util.HashMap')
    for (var className in classTree) {
        //TODO filter somewhere
        if (className === 'unit' || className === 'entitySuperClass') {
            continue;
        }
        output[className] = generateJavaClass("java.util.ArrayList");
    }
    return output;
}

function parseJson(json)
{
    debugLog("parseJson()")
    debugLog("json = ")
    debugLog(json)
    var input = json;
    if(typeof json === "string")
    {
        input = JSON.parse(json);
    }
    var outputArray = [];
    for(var index in input)
    {
        var obj = input[index]
        debugLog("for()")
        debugLog("obj = ")
        debugLog(obj)

        //Check, if json contains className, if not we can't do anything
        if(obj.className != null)
        {
            //Generate Java Class based on the classname in the json
            var loopObject = generateJavaClass(globalClassTree[obj.className].id);

            //Iterate through every property in the json object and set its value in the JavaClass
            Object.keys(obj).forEach(function(key,index) {
                if(key !== "className" && loopObject[key] !== undefined)
                {
                    loopObject[key] = obj[key]
                }
                debugLog("obj[key] = ")
                debugLog(obj[key])
                debugLog("loopObject[key] = ")
                debugLog(loopObject[key])
            });
            //Use ...Add function of Subproductionstep (e.g. auxiliaryMaterialsAdd(), productionNumbersAdd(), qualityManagementsAdd(), setUpsAdd()) to attach the new object to the subproductionstep
            var output = globalOutput;
            eval(globalMappingRule.outputSelector + 'Add(loopObject)');
            outputArray.push(loopObject);
            debugLog("outputArray = ")
            debugLog(outputArray)
        }
    }
    return outputArray;
}

function parseQualityControl(input)
{
    debugLog("parseQualityControl()")
    debugLog("input = ")
    debugLog(input)
    if(typeof input === "string")
    {
        input = JSON.parse(input);
    }

    var outputArray = [];
    for(var index in input)
    {
        var obj = input[index]
        debugLog("for()")
        debugLog("obj = ")
        debugLog(obj)

        //Generate Java Class for qualityManagement
        var qualityManagement = generateJavaClass(globalClassTree["qualityManagement"].id);

        //Iterate through every property from the input object and set its value in the JavaClass
        Object.keys(obj).forEach(function(key,index) {
            if(key !== "className" && qualityManagement[key] !== undefined)
            {
                if(key === "qualityControlFeature")
                {
                    var feature = generateJavaClass(globalClassTree["cD_QualityControlFeature"].id);
                    Object.keys(obj[key]).forEach(function(innerkey,index) {
                        feature[innerkey] = obj[key][innerkey];
                    });
                    eval("feature.qualityManagementsAdd(qualityManagement)");
                }
                else
                {
                    qualityManagement[key] = obj[key]
                }
            }
            debugLog("obj[key] = ")
            debugLog(obj[key])
            debugLog("loopObject[key] = ")
            debugLog(qualityManagement[key])
        });
            //Use ...Add function of Subproductionstep (e.g. auxiliaryMaterialsAdd(), productionNumbersAdd(), qualityManagementsAdd(), setUpsAdd()) to attach the new object to the subproductionstep
            var output = globalOutput;
            eval(globalMappingRule.outputSelector + 'Add(qualityManagement)');
            outputArray.push(qualityManagement);
            debugLog("outputArray = ")
            debugLog(outputArray)

    }
    return outputArray;
}

function parseLocalDateTime(dateString, format) {
        var formatter = Java.type('java.time.format.DateTimeFormatter').ofPattern(format).withZone(Java.type('java.time.ZoneId').systemDefault());
        var dateTime = Java.type('java.time.ZonedDateTime').parse(dateString, formatter).toLocalDateTime();
        return dateTime;
}

function fromMillisecondsLocalDateTime(millis) {
    var instant = Java.type('java.time.Instant').ofEpochMilli(millis);
    var dateTime = instant.atZone(Java.type('java.time.ZoneId').systemDefault()).toLocalDateTime();
    return dateTime;
}

function fromMillisecondsDuration(millis) {
    var instant = Java.type('java.time.Duration').ofMillis(millis);
    return instant;
}

function fromSecondsDuration(millis) {
    var instant = Java.type('java.time.Duration').ofSeconds(millis);
    return instant;
}

function logging(data) {
    var logMessage = 'UserLog: '
    if (typeof data === 'object') {
        logMessage = logMessage + JSON.stringify(data, null, 2);
    } else {
        logMessage = logMessage +data;
    }
    loggingPersistence.getLog().add(logMessage);
    return data;
}

function nullIfError(selectorString) {
    var input = currentInput;
    try {
        return eval(selectorString);
    } catch (e) {
        return null;
    }
}

function internalError(data, exception) {
    var exeptionString = 'Exception: ' + exception.name  + ': ' +  exception.message
    print('mapper error: ' + data);
    print(exeptionString)
    internalLogging(data);
    internalLogging(exeptionString);

    loggingPersistence.getError().add(data);
    loggingPersistence.getError().add(exeptionString);

}

function internalLogging(data) {
    if (typeof data === 'object') {
        data = JSON.stringify(data, null, 2);
    }
    print('mapper info: ' + data);
    loggingPersistence.getLog().add(data);
}

function debugLog(data) {
    if(debug) {
        if (typeof data === 'object') {
            data = JSON.stringify(data, null, 2);
        }
        print('mapper debug: ' + data);
    }
}
