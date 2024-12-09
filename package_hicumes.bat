title Package HiCuMES
@echo off
SETLOCAL 

set hicumes_path="D:\iisys\HiCuMES\HiCuMES_Release"
set mft_frontend_path="D:\iisys\HiCuMES\HiCuMES_Release\manufacturingTerminal\frontendManufacturingTerminal"
set mapper_frontend_path="D:\iisys\HiCuMES\HiCuMES_Release\schemaMapper\frontendSchemaMapper"
set dbeditor_frontend_path="D:\iisys\HiCuMES\HiCuMES_Release\schemaMapper\frontendDatabaseEditor"
set camunda_servicebus_path="D:\iisys\HiCuMES\HiCuMES_Release\camunda\camunda-servicebus-extension"
set camunda_delegates_path="D:\iisys\HiCuMES\HiCuMES_Release\camunda\hicumes-backend-camunda-delegates"

set mft_build_path="D:\iisys\HiCuMES\HiCuMES_Release\manufacturingTerminal\backend\target\manufacturingTerminalBackend.war"
set mft_frontend_build_path="D:\iisys\HiCuMES\HiCuMES_Release\manufacturingTerminal\frontendManufacturingTerminal\dist\frontendManufacturingTerminal"
set mapper_build_path="D:\iisys\HiCuMES\HiCuMES_Release\schemaMapper\backend\target\mappingBackend.war"
set mapper_frontend_build_path="D:\iisys\HiCuMES\HiCuMES_Release\schemaMapper\frontendSchemaMapper\dist\frontendSchemaMapper"
set dbeditor_frontend_build_path="D:\iisys\HiCuMES\HiCuMES_Release\schemaMapper\frontendDatabaseEditor\dist\frontendDatabaseEditor"
set plugin_build_path="D:\iisys\HiCuMES\HiCuMES_Release\schemaMapper\mapperPlugins\target\plugins"
set data_path_mapping="D:\iisys\HiCuMES\HiCuMES_Release\manufacturingTerminal\testData\demoCompany\Mappings"
set data_path_data="D:\iisys\HiCuMES\HiCuMES_Release\manufacturingTerminal\testData\demoCompany\Data"
set data_path_bpmn="D:\iisys\HiCuMES\HiCuMES_Release\manufacturingTerminal\testData\demoCompany\BPMN"
set camunda_servicebus_build_path="D:\iisys\HiCuMES\HiCuMES_Release\camunda\camunda-servicebus-extension\target\camunda-servicebus-extension-7.15.0-SNAPSHOT-jar-with-dependencies.jar"
set camunda_delegates_build_path="D:\iisys\HiCuMES\HiCuMES_Release\camunda\hicumes-backend-camunda-delegates\target\hicumes-backend-camunda-delegates-1.0.0-SNAPSHOT-jar-with-dependencies.jar"

set output_path="D:\iisys\HiCuMES\HiCuMES_Release\docker_compose_build"
set output_path_camunda="D:\iisys\HiCuMES\HiCuMES_Release\docker_compose_build\tomcat\files\lib"
set output_path_fe_mapper="D:\iisys\HiCuMES\HiCuMES_Release\docker_compose_build\SchemaMapper\Content\frontendSchemaMapper"
set output_path_fe_mft="D:\iisys\HiCuMES\HiCuMES_Release\docker_compose_build\MFT\Content\frontendManufacturingTerminal"
set output_path_fe_dbeditor="D:\iisys\HiCuMES\HiCuMES_Release\docker_compose_build\DBEditor\Content\frontendDatabaseEditor"
set output_path_deployments="D:\iisys\HiCuMES\HiCuMES_Release\docker_compose_build\wildfly\deployments\"
set output_path_plugins="D:\iisys\HiCuMES\HiCuMES_Release\docker_compose_build\wildfly\wildfly-plugins"
set output_path_testdata_mapping="D:\iisys\HiCuMES\HiCuMES_Release\docker_compose_build\testData\Deployment\Mapping"
set output_path_testdata_data="D:\iisys\HiCuMES\HiCuMES_Release\docker_compose_build\testData\Deployment\Data"
set output_path_testdata_bpmn="D:\iisys\HiCuMES\HiCuMES_Release\docker_compose_build\testData\Deployment\BPMN"

echo Build HiCuMES and Camunda
cd %hicumes_path%
call mvn clean install -P !hicumes-frontend -P !hicumes-lite -DskipTests

echo Build MFT Frontend
cd %mft_frontend_path%
call ng build

echo Build Mapper
cd %mapper_frontend_path%
call ng build

echo Build DBEditor
cd %dbeditor_frontend_path%
call ng build

del %output_path%

mkdir -p %output_path%
mkdir -p %output_path_camunda%
mkdir -p %output_path_fe_mapper%
mkdir -p %output_path_fe_mft%
mkdir -p %output_path_fe_dbeditor%
mkdir -p %output_path_deployments%
mkdir -p %output_path_plugins%
mkdir -p %output_path_testdata_mapping%
mkdir -p %output_path_testdata_data%
mkdir -p %output_path_testdata_bpmn%

cd %output_path%

echo Copying files
xcopy /f /s /y %camunda_servicebus_build_path% %output_path_camunda%
xcopy /f /s /y %camunda_delegates_build_path% %output_path_camunda%
xcopy /f /s /e /y %mapper_frontend_build_path% %output_path_fe_mapper% 
xcopy /f /s /e /y %mft_frontend_build_path% %output_path_fe_mft% 
xcopy /f /s /e /y %dbeditor_frontend_build_path% %output_path_fe_dbeditor%
xcopy /f /s /y %mft_build_path% %output_path_deployments%
xcopy /f /s /y %mapper_build_path% %output_path_deployments%
xcopy /f /s /e /y %plugin_build_path% %output_path_plugins% 
xcopy /f /s /e /y %data_path_mapping% %output_path_testdata_mapping% 
xcopy /f /s /e /y %data_path_data% %output_path_testdata_data% 
xcopy /f /s /e /y %data_path_bpmn% %output_path_testdata_bpmn% 

echo DONE - %output_path%
explorer %output_path%