-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: mysql
-- Erstellungszeit: 25. Nov 2024 um 12:48
-- Server-Version: 8.4.3
-- PHP-Version: 8.2.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `hicumes`
--

-- --------------------------------------------------------
DROP TABLE IF EXISTS `AllClassExtension`, `AllClassExtension_ClassExtension`, `AuxiliaryMaterials`, `BookingEntry`, `CamundaMachineOccupation`, `CamundaSubProductionStep`, `CD_Department`, `CD_Machine`, `CD_MachineType`, `CD_MachineType_CD_Department`, `CD_OverheadCostCenter`, `CD_Product`, `CD_ProductionStep`, `CD_ProductionStep_CD_ToolSettingParameter`, `CD_ProductRelationship`, `CD_QualityControlFeature`, `CD_Tool`, `CD_ToolSettingParameter`, `CD_ToolType`, `ClassExtension`, `ClassExtension_MemberExtension`, `CsvReaderParserConfig`, `CustomerOrder`, `DataReader`, `DataWriter`, `hibernate_sequence`, `HicumesSettings`, `KeyValueMapProductionOrder`, `KeyValueMapSubProductionStep`, `MachineOccupation`, `MachineOccupation_ActiveToolSetting`, `MachineOccupation_CD_ProductionStep`, `MachineOccupation_CD_Tool`, `MachineOccupation_MachineOccupation`, `MachineStatus`, `MachineStatusHistory`, `MappingAndDataSource`, `MappingConfiguration`, `MappingConfiguration_MappingConfiguration`, `MappingConfiguration_MappingRule`, `MappingRule`, `Note`, `OverheadCost`, `ProductionNumbers`, `ProductionOrder`, `ProductionOrder_Note`, `ProductionOrder_ProductionOrder`, `ProductionStepInfo`, `QualityManagement`, `ReaderResult`, `SetUp`, `SingleFileReaderConfig`, `SubProductionStep`, `SuspensionType`, `TimeRecord`, `TimeRecordType`, `Time_Durations`, `Time_DurationsStep`, `ToolSetting`, `User`, `UserOccupation`, `UserOccupation_ActiveUsers`, `UserOccupation_InactiveUsers`, `UserOccupation_TimeRecords`;

--
-- Tabellenstruktur für Tabelle `AllClassExtension`
--

CREATE TABLE `AllClassExtension` (
  `id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `AllClassExtension_ClassExtension`
--

CREATE TABLE `AllClassExtension_ClassExtension` (
  `AllClassExtension_id` bigint NOT NULL,
  `classes_fieldId` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `AuxiliaryMaterials`
--

CREATE TABLE `AuxiliaryMaterials` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `amount` double NOT NULL,
  `unitString` varchar(255) DEFAULT NULL,
  `subProductionStep_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `BookingEntry`
--

CREATE TABLE `BookingEntry` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `bookingState` int DEFAULT NULL,
  `isStepTime` bit(1) NOT NULL,
  `message` longtext,
  `response` longtext,
  `machineOccupation_id` bigint DEFAULT NULL,
  `overheadCost_id` bigint DEFAULT NULL,
  `subProductionStep_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `BookingEntry`
--

INSERT INTO `BookingEntry` (`id`, `createDateTime`, `externalId`, `updateDateTime`, `versionNr`, `bookingState`, `isStepTime`, `message`, `response`, `machineOccupation_id`, `overheadCost_id`, `subProductionStep_id`, `user_id`) VALUES
(230, '2024-11-25 13:21:47.219941', NULL, '2024-11-25 13:21:47.219964', NULL, 0, b'1', '{\"initAction\":{\"_type\":\"OpenEditor\",\"tableName\":\"9:2\",\"editAction\":\"DONE\"},\"actions\":[{\"_type\":\"SetFieldValue\",\"fieldName\":\"barmex\",\"value\":\"Insti/MO/00014100\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"gutmge\",\"value\":\"NaN\",\"rowSpec\":\"$,artikel=Spitze Kunststoff\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"bzeit\",\"value\":\",01\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"mzeit\",\"value\":\",01\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"ma\",\"value\":\"004\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"rzbuchen\",\"value\":\"true\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"yerfzeit\",\"value\":\"25.11.2024_13:21\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"yerfdat\",\"value\":\"20241125\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"bem\",\"value\":\"25.11.2024 004 llustig;BDE\"}]}', NULL, 142, NULL, 222, 4),
(233, '2024-11-25 13:22:15.225373', NULL, '2024-11-25 13:22:15.225397', NULL, 0, b'1', '{\"initAction\":{\"_type\":\"OpenEditor\",\"tableName\":\"9:2\",\"editAction\":\"DONE\"},\"actions\":[{\"_type\":\"SetFieldValue\",\"fieldName\":\"barmex\",\"value\":\"Insti/MO/00014100\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"gutmge\",\"value\":\"5\",\"rowSpec\":\"$,artikel=Spitze Kunststoff\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"bzeit\",\"value\":\",01\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"mzeit\",\"value\":\",01\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"ma\",\"value\":\"004\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"rzbuchen\",\"value\":\"true\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"yerfzeit\",\"value\":\"25.11.2024_13:22\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"yerfdat\",\"value\":\"20241125\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"bem\",\"value\":\"25.11.2024 004 llustig;BDE\"}]}', NULL, 142, NULL, 226, 4),
(247, '2024-11-25 13:24:12.003431', NULL, '2024-11-25 13:24:12.003451', NULL, 0, b'1', '{\"initAction\":{\"_type\":\"OpenEditor\",\"tableName\":\"9:2\",\"editAction\":\"DONE\"},\"actions\":[{\"_type\":\"SetFieldValue\",\"fieldName\":\"barmex\",\"value\":\"Insti/MO/00017100\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"gutmge\",\"value\":\"NaN\",\"rowSpec\":\"$,artikel=Griffrohr B blau\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"bzeit\",\"value\":\",00\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"mzeit\",\"value\":\",00\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"ma\",\"value\":\"003\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"rzbuchen\",\"value\":\"true\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"yerfzeit\",\"value\":\"25.11.2024_13:24\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"yerfdat\",\"value\":\"20241125\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"bem\",\"value\":\"25.11.2024 003 ffunke;BDE\"}]}', NULL, 145, NULL, 239, 3),
(249, '2024-11-25 13:25:05.518321', NULL, '2024-11-25 13:25:05.518344', NULL, 0, b'1', '{\"initAction\":{\"_type\":\"OpenEditor\",\"tableName\":\"9:2\",\"editAction\":\"DONE\"},\"actions\":[{\"_type\":\"SetFieldValue\",\"fieldName\":\"barmex\",\"value\":\"Insti/MO/00014100\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"gutmge\",\"value\":\"15\",\"rowSpec\":\"$,artikel=Spitze Kunststoff\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"bzeit\",\"value\":\",05\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"mzeit\",\"value\":\",05\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"ma\",\"value\":\"004\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"rzbuchen\",\"value\":\"true\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"yerfzeit\",\"value\":\"25.11.2024_13:25\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"yerfdat\",\"value\":\"20241125\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"bem\",\"value\":\"25.11.2024 004 llustig;BDE\"}]}', NULL, 142, NULL, 234, 4),
(255, '2024-11-25 13:28:57.626685', NULL, '2024-11-25 13:28:57.626703', NULL, 0, b'1', '{\"initAction\":{\"_type\":\"OpenEditor\",\"tableName\":\"9:2\",\"editAction\":\"DONE\"},\"actions\":[{\"_type\":\"SetFieldValue\",\"fieldName\":\"barmex\",\"value\":\"Insti/MO/00017100\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"gutmge\",\"value\":\"50\",\"rowSpec\":\"$,artikel=Griffrohr B blau\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"bzeit\",\"value\":\",08\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"mzeit\",\"value\":\",08\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"ma\",\"value\":\"003\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"rzbuchen\",\"value\":\"true\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"yerfzeit\",\"value\":\"25.11.2024_13:28\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"yerfdat\",\"value\":\"20241125\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"bem\",\"value\":\"25.11.2024 003 ffunke;BDE\"}]}', NULL, 145, NULL, 243, 3),
(257, '2024-11-25 13:29:16.096238', NULL, '2024-11-25 13:29:16.096256', NULL, 0, b'1', '{\"initAction\":{\"_type\":\"OpenEditor\",\"tableName\":\"9:2\",\"editAction\":\"DONE\"},\"actions\":[{\"_type\":\"SetFieldValue\",\"fieldName\":\"barmex\",\"value\":\"Insti/MO/00014100\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"gutmge\",\"value\":\"20\",\"rowSpec\":\"$,artikel=Spitze Kunststoff\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"bzeit\",\"value\":\",07\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"mzeit\",\"value\":\",07\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"ma\",\"value\":\"004\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"rzbuchen\",\"value\":\"true\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"yerfzeit\",\"value\":\"25.11.2024_13:29\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"yerfdat\",\"value\":\"20241125\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"bem\",\"value\":\"25.11.2024 004 llustig;BDE\"}]}', NULL, 142, NULL, 250, 4),
(263, '2024-11-25 13:32:37.283066', NULL, '2024-11-25 13:32:37.283084', NULL, 0, b'1', '{\"initAction\":{\"_type\":\"OpenEditor\",\"tableName\":\"9:2\",\"editAction\":\"DONE\"},\"actions\":[{\"_type\":\"SetFieldValue\",\"fieldName\":\"barmex\",\"value\":\"Insti/MO/00014100\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"gutmge\",\"value\":\"10\",\"rowSpec\":\"$,artikel=Spitze Kunststoff\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"bzeit\",\"value\":\",06\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"mzeit\",\"value\":\",06\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"ma\",\"value\":\"004\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"rzbuchen\",\"value\":\"true\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"yerfzeit\",\"value\":\"25.11.2024_13:32\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"yerfdat\",\"value\":\"20241125\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"bem\",\"value\":\"25.11.2024 004 llustig;BDE\"}]}', NULL, 142, NULL, 258, 4),
(269, '2024-11-25 13:33:01.960083', NULL, '2024-11-25 13:33:01.960099', NULL, 0, b'1', '{\"initAction\":{\"_type\":\"OpenEditor\",\"tableName\":\"9:2\",\"editAction\":\"DONE\"},\"actions\":[{\"_type\":\"SetFieldValue\",\"fieldName\":\"barmex\",\"value\":\"Insti/MO/00015100\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"gutmge\",\"value\":\"NaN\",\"rowSpec\":\"$,artikel=Druckfeder Metall\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"bzeit\",\"value\":\",00\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"mzeit\",\"value\":\",00\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"ma\",\"value\":\"004\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"rzbuchen\",\"value\":\"true\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"yerfzeit\",\"value\":\"25.11.2024_13:33\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"yerfdat\",\"value\":\"20241125\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"bem\",\"value\":\"25.11.2024 004 llustig;BDE\"}]}', NULL, 148, NULL, 265, 4),
(275, '2024-11-25 13:33:09.513037', NULL, '2024-11-25 13:33:09.513053', NULL, 0, b'1', '{\"initAction\":{\"_type\":\"OpenEditor\",\"tableName\":\"9:2\",\"editAction\":\"DONE\"},\"actions\":[{\"_type\":\"SetFieldValue\",\"fieldName\":\"barmex\",\"value\":\"Insti/MO/00015100\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"gutmge\",\"value\":\"50\",\"rowSpec\":\"$,artikel=Druckfeder Metall\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"bzeit\",\"value\":\",00\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"mzeit\",\"value\":\",00\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"ma\",\"value\":\"004\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"rzbuchen\",\"value\":\"true\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"yerfzeit\",\"value\":\"25.11.2024_13:33\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"yerfdat\",\"value\":\"20241125\"},{\"_type\":\"SetFieldValue\",\"fieldName\":\"bem\",\"value\":\"25.11.2024 004 llustig;BDE\"}]}', NULL, 148, NULL, 270, 4);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `CamundaMachineOccupation`
--

CREATE TABLE `CamundaMachineOccupation` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `businessKey` varchar(255) DEFAULT NULL,
  `camundaProcessInstanceId` varchar(255) DEFAULT NULL,
  `isSubMachineOccupation` bit(1) NOT NULL,
  `activeProductionStep_id` bigint DEFAULT NULL,
  `machineOccupation_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `CamundaMachineOccupation`
--

INSERT INTO `CamundaMachineOccupation` (`id`, `createDateTime`, `externalId`, `updateDateTime`, `versionNr`, `businessKey`, `camundaProcessInstanceId`, `isSubMachineOccupation`, `activeProductionStep_id`, `machineOccupation_id`) VALUES
(141, '2024-11-25 13:17:58.040828', NULL, '2024-11-25 13:19:23.720754', NULL, 'Process_Montage#Insti/MO/00004/20#-1717049187', '815ca005-ab27-11ef-93e1-0242ac1d0004', b'0', 109, 139),
(144, '2024-11-25 13:17:58.094170', NULL, '2024-11-25 13:21:10.195418', NULL, 'Process_Basis#Insti/MO/00014/23#223833442', 'c0ddcc29-ab27-11ef-93e1-0242ac1d0004', b'0', 110, 142),
(147, '2024-11-25 13:17:58.138464', NULL, '2024-11-25 13:24:06.837576', NULL, 'Process_Basis#Insti/MO/00017/26#174878868', '2a3bd378-ab28-11ef-93e1-0242ac1d0004', b'0', 111, 145),
(150, '2024-11-25 13:17:58.183520', NULL, '2024-11-25 13:32:56.033516', NULL, 'Process_Basis#Insti/MO/00015/24#1248360068', '65ad003d-ab29-11ef-93e1-0242ac1d0004', b'0', 112, 148),
(153, '2024-11-25 13:17:58.230645', NULL, '2024-11-25 13:17:58.230665', NULL, 'Process_Basis#Insti/MO/00028/61#329458424', NULL, b'0', 113, 151),
(156, '2024-11-25 13:17:58.277785', NULL, '2024-11-25 13:17:58.277801', NULL, 'Process_Basis#Insti/MO/00028/62#-1122795367', NULL, b'0', 114, 154),
(159, '2024-11-25 13:17:58.325902', NULL, '2024-11-25 13:17:58.325918', NULL, 'Process_Basis#Insti/MO/00028/63#-1302562097', NULL, b'0', 115, 157),
(162, '2024-11-25 13:17:58.372474', NULL, '2024-11-25 13:17:58.372492', NULL, 'Process_Basis#Insti/MO/00028/64#-1402386612', NULL, b'0', 116, 160),
(165, '2024-11-25 13:17:58.419726', NULL, '2024-11-25 13:17:58.419746', NULL, 'Process_Basis#Insti/MO/00028/65#803458979', NULL, b'0', 117, 163),
(168, '2024-11-25 13:17:58.470165', NULL, '2024-11-25 13:17:58.470182', NULL, 'Process_Montage#Insti/MO/00028/66#156757709', NULL, b'0', 118, 166),
(171, '2024-11-25 13:17:58.519261', NULL, '2024-11-25 13:17:58.519279', NULL, 'Process_Montage#Insti/MO/00028/67#550291604', NULL, b'0', 119, 169),
(174, '2024-11-25 13:17:58.568776', NULL, '2024-11-25 13:17:58.568795', NULL, 'Process_Check#Insti/MO/00028/68#399792381', NULL, b'0', 120, 172),
(177, '2024-11-25 13:17:58.619245', NULL, '2024-11-25 13:17:58.619263', NULL, 'Process_Verpackung#Insti/MO/00028/69#1236916408', NULL, b'0', 121, 175),
(180, '2024-11-25 13:17:58.668292', NULL, '2024-11-25 13:17:58.668325', NULL, 'Process_Versand#Insti/MO/00028/70#-2117071510', NULL, b'0', 122, 178),
(183, '2024-11-25 13:17:58.713591', NULL, '2024-11-25 13:17:58.713612', NULL, 'Process_Basis#Insti/MO/00027/51#-865042788', NULL, b'0', 123, 181),
(186, '2024-11-25 13:17:58.771142', NULL, '2024-11-25 13:17:58.771163', NULL, 'Process_Basis#Insti/MO/00027/52#-1219789926', NULL, b'0', 124, 184),
(189, '2024-11-25 13:17:58.816807', NULL, '2024-11-25 13:17:58.816826', NULL, 'Process_Basis#Insti/MO/00027/53#-1220140771', NULL, b'0', 125, 187),
(192, '2024-11-25 13:17:58.858746', NULL, '2024-11-25 13:17:58.858760', NULL, 'Process_Basis#Insti/MO/00027/54#1080744861', NULL, b'0', 126, 190),
(195, '2024-11-25 13:17:58.902173', NULL, '2024-11-25 13:17:58.902192', NULL, 'Process_Montage#Insti/MO/00027/56#879866151', NULL, b'0', 127, 193),
(198, '2024-11-25 13:17:58.948929', NULL, '2024-11-25 13:17:58.948949', NULL, 'Process_Montage#Insti/MO/00027/57#424105211', NULL, b'0', 128, 196),
(201, '2024-11-25 13:17:58.992270', NULL, '2024-11-25 13:17:58.992289', NULL, 'Process_Check#Insti/MO/00027/58#667692417', NULL, b'0', 129, 199),
(204, '2024-11-25 13:17:59.037419', NULL, '2024-11-25 13:17:59.037440', NULL, 'Process_Verpackung#Insti/MO/00027/59#1195765749', NULL, b'0', 130, 202),
(207, '2024-11-25 13:17:59.082670', NULL, '2024-11-25 13:17:59.082690', NULL, 'Process_Versand#Insti/MO/00027/60#1631028168', NULL, b'0', 131, 205),
(210, '2024-11-25 13:17:59.128238', NULL, '2024-11-25 13:17:59.128256', NULL, 'Process_Basis#Insti/MO/00027/55#1947415522', NULL, b'0', 132, 208);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `CamundaSubProductionStep`
--

CREATE TABLE `CamundaSubProductionStep` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `camundaProcessVariables` text,
  `filledFormField` text,
  `formField` text,
  `formKey` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `taskDefinitionKey` varchar(255) DEFAULT NULL,
  `taskId` varchar(255) DEFAULT NULL,
  `camundaMachineOccupation_id` bigint DEFAULT NULL,
  `subProductionStep_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `CamundaSubProductionStep`
--

INSERT INTO `CamundaSubProductionStep` (`id`, `createDateTime`, `externalId`, `updateDateTime`, `versionNr`, `camundaProcessVariables`, `filledFormField`, `formField`, `formKey`, `name`, `taskDefinitionKey`, `taskId`, `camundaMachineOccupation_id`, `subProductionStep_id`) VALUES
(215, '2024-11-25 13:19:24.235919', NULL, '2024-11-25 13:19:24.236055', NULL, '{\"ff_PartsOK\":null,\"ff_Note_2_1\":null,\"varTotalNOK\":null,\"businessKey\":\"Process_Montage#Insti/MO/00004/20#-1717049187\",\"varTotalOK\":null,\"ff_PartsNOK\":null,\"userName\":\"admin\",\"key\":\"Process_Montage\",\"varProdAmount\":150.0}', NULL, '[{\"key\":\"info_TotalOK\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Bisherige i.O.\",\"value\":\"0.0\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"9\\\",\\\"col-width\\\":\\\"2\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":0},{\"key\":\"info_TotalNOK\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Bisherige n.i.O.\",\"value\":\"0.0\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"11\\\",\\\"col-width\\\":\\\"2\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":1},{\"key\":\"info_RemainingParts\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Verbleibende i.O.\",\"value\":\"150.0\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"7\\\",\\\"col-width\\\":\\\"2\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":2},{\"key\":\"ff_Note\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Bemerkung\",\"properties\":\"{\\\"col-width\\\":\\\"6\\\",\\\"col-start\\\":\\\"7\\\",\\\"row-height\\\":\\\"1\\\",\\\"row-start\\\":\\\"4\\\"}\",\"order\":3},{\"key\":\"ff_PartsOK\",\"controlType\":\"textbox\",\"type\":\"number\",\"label\":\"Teile i.O.\",\"validationConstraints\":\"[{\\\"name\\\":\\\"required\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"7\\\",\\\"col-width\\\":\\\"3\\\",\\\"row-height\\\":\\\"1\\\",\\\"row-start\\\":\\\"2\\\"}\",\"order\":4},{\"key\":\"ff_PartsNOK\",\"controlType\":\"textbox\",\"type\":\"number\",\"label\":\"Teile n.i.O.\",\"validationConstraints\":\"[{\\\"name\\\":\\\"required\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"10\\\",\\\"col-width\\\":\\\"3\\\",\\\"row-start\\\":\\\"2\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":5},{\"key\":\"ff_ContinueWithLess\",\"controlType\":\"checkbox\",\"type\":\"boolean\",\"label\":\"Arbeitsgang mit reduzierter Fertigungsstückzahl abschließen\",\"value\":\"false\",\"properties\":\"{\\\"col-width\\\":\\\"6\\\",\\\"col-start\\\":\\\"7\\\",\\\"row-start\\\":\\\"3\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":6},{\"key\":\"ff_ManufacturingProblem\",\"controlType\":\"dropdown\",\"type\":\"enum\",\"label\":\"Fertigungsproblem\",\"properties\":\"{\\\"col-start\\\":\\\"7\\\",\\\"col-width\\\":\\\"6\\\",\\\"row-start\\\":\\\"6\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":7,\"options\":[{\"key\":\"Maschinenstoerung\",\"value\":\"Maschinenstörung\"},{\"key\":\"Werkzeugstoerung\",\"value\":\"Werkzeugstörung\"},{\"key\":\"Mitarbeitereinarbeitung\",\"value\":\"Ungenügende Einarbeitung von Mitarbeitern\"},{\"key\":\"AenderungArtikelArbeitsprozess\",\"value\":\"Änderung von Artikel / Arbeitsprozess\"}]},{\"key\":\"info_Video\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Montageanleitung\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"type\\\":\\\"video\\\",\\\"col-start\\\":\\\"1\\\",\\\"col-width\\\":\\\"6\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"6\\\"}\",\"order\":8}]', 'ConfirmProductionTask', 'Einfache Rückmeldung', 'Activity_0s5lqmy', '8162baa8-ab27-11ef-93e1-0242ac1d0004', 141, 213),
(224, '2024-11-25 13:21:10.588202', NULL, '2024-11-25 13:21:45.686208', NULL, '{\"varTotalNOK\":null,\"businessKey\":\"Process_Basis#Insti/MO/00014/23#223833442\",\"varTotalOK\":null,\"userName\":\"llustig\",\"key\":\"Process_Basis\",\"varProdAmount\":50.0}', '{\"info_WorkorderText\":\"x:120, y:10, ...\",\"ff_Checked\":true,\"ff_Note_1_1\":\"Vorgezogen\"}', '[{\"key\":\"info_WorkorderText\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Einstellparameter\",\"value\":\"x:120, y:10, ...\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"type\\\":\\\"data\\\",\\\"col-width\\\":\\\"12\\\",\\\"col-start\\\":\\\"1\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":0},{\"key\":\"ff_Checked\",\"controlType\":\"checkbox\",\"type\":\"boolean\",\"label\":\"Maschine eingerüstet\",\"validationConstraints\":\"[{\\\"name\\\":\\\"required\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-width\\\":\\\"4\\\",\\\"col-start\\\":\\\"1\\\",\\\"row-start\\\":\\\"2\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":1},{\"key\":\"ff_Note_1_1\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Bemerkung\",\"properties\":\"{\\\"col-width\\\":\\\"8\\\",\\\"col-start\\\":\\\"5\\\",\\\"row-height\\\":\\\"1\\\",\\\"row-start\\\":\\\"2\\\"}\",\"order\":2}]', 'SetParameterTask', 'Maschine Rüsten', 'Activity_0279ebn', 'c0df52e3-ab27-11ef-93e1-0242ac1d0004', 144, 222),
(228, '2024-11-25 13:21:46.868058', NULL, '2024-11-25 13:22:14.536903', NULL, '{\"varTotalNOK\":null,\"ff_Checked\":true,\"varTotalOK\":null,\"ff_PartsNOK\":null,\"userName\":\"llustig\",\"ff_Note_1_1\":\"Vorgezogen\",\"ff_PartsOK\":null,\"ff_Note_2_1\":null,\"businessKey\":\"Process_Basis#Insti/MO/00014/23#223833442\",\"varSubProdStepId\":222,\"info_WorkorderText\":\"x:120, y:10, ...\",\"key\":\"Process_Basis\",\"varProdAmount\":50.0}', '{\"info_TotalOK\":\"0.0\",\"info_TotalNOK\":\"0.0\",\"info_RemainingParts\":\"50.0\",\"ff_ContinueWithLess\":false,\"info_Drawing\":\"assets/demoComp.pdf\",\"ff_PartsOK\":5,\"ff_PartsNOK\":0}', '[{\"key\":\"info_TotalOK\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Bisherige i.O.\",\"value\":\"0.0\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"9\\\",\\\"col-width\\\":\\\"2\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":0},{\"key\":\"info_TotalNOK\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Bisherige n.i.O.\",\"value\":\"0.0\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"11\\\",\\\"col-width\\\":\\\"2\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":1},{\"key\":\"info_RemainingParts\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Verbleibende i.O.\",\"value\":\"50.0\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"7\\\",\\\"col-width\\\":\\\"2\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":2},{\"key\":\"ff_Note\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Bemerkung\",\"properties\":\"{\\\"col-width\\\":\\\"6\\\",\\\"col-start\\\":\\\"7\\\",\\\"row-height\\\":\\\"1\\\",\\\"row-start\\\":\\\"5\\\"}\",\"order\":3},{\"key\":\"ff_PartsOK\",\"controlType\":\"textbox\",\"type\":\"number\",\"label\":\"Teile i.O.\",\"validationConstraints\":\"[{\\\"name\\\":\\\"required\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"7\\\",\\\"col-width\\\":\\\"3\\\",\\\"row-height\\\":\\\"1\\\",\\\"row-start\\\":\\\"2\\\"}\",\"order\":4},{\"key\":\"ff_PartsNOK\",\"controlType\":\"textbox\",\"type\":\"number\",\"label\":\"Teile n.i.O.\",\"validationConstraints\":\"[{\\\"name\\\":\\\"required\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"10\\\",\\\"col-width\\\":\\\"3\\\",\\\"row-start\\\":\\\"2\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":5},{\"key\":\"ff_ContinueWithLess\",\"controlType\":\"checkbox\",\"type\":\"boolean\",\"label\":\"Arbeitsgang mit reduzierter Fertigungsstückzahl abschließen\",\"value\":\"false\",\"properties\":\"{\\\"col-width\\\":\\\"6\\\",\\\"col-start\\\":\\\"7\\\",\\\"row-start\\\":\\\"4\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":6},{\"key\":\"ff_ManufacturingProblem\",\"controlType\":\"dropdown\",\"type\":\"enum\",\"label\":\"Fertigungsproblem\",\"properties\":\"{\\\"col-start\\\":\\\"7\\\",\\\"col-width\\\":\\\"6\\\",\\\"row-start\\\":\\\"6\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":7,\"options\":[{\"key\":\"Maschinenstoerung\",\"value\":\"Maschinenstörung\"},{\"key\":\"Werkzeugstoerung\",\"value\":\"Werkzeugstörung\"},{\"key\":\"Mitarbeitereinarbeitung\",\"value\":\"Ungenügende Einarbeitung von Mitarbeitern\"},{\"key\":\"AenderungArtikelArbeitsprozess\",\"value\":\"Änderung von Artikel / Arbeitsprozess\"}]},{\"key\":\"info_Drawing\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Zeichnung\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"type\\\":\\\"pdf\\\",\\\"col-start\\\":\\\"1\\\",\\\"col-width\\\":\\\"6\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"7\\\"}\",\"order\":8}]', 'ConfirmProductionTask', 'Einfache Rückmeldung', 'Activity_WZB_Rueckmeldung_Basis', 'd69333dd-ab27-11ef-93e1-0242ac1d0004', 144, 226),
(236, '2024-11-25 13:22:15.664642', NULL, '2024-11-25 13:25:04.969470', NULL, '{\"info_RemainingParts\":\"45.0\",\"varTotalNOK\":0.0,\"ff_Checked\":true,\"varSubProductionStepId\":226,\"info_TotalOK\":\"5.0\",\"varTotalOK\":5.0,\"ff_PartsNOK\":null,\"userName\":\"llustig\",\"ff_Note_1_1\":\"Vorgezogen\",\"ff_PartsOK\":null,\"ff_Note_2_1\":null,\"info_TotalNOK\":\"0.0\",\"businessKey\":\"Process_Basis#Insti/MO/00014/23#223833442\",\"varSubProdStepId\":222,\"info_WorkorderText\":\"x:120, y:10, ...\",\"ff_ContinueWithLess\":false,\"key\":\"Process_Basis\",\"varProdAmount\":50.0}', '{\"info_TotalOK\":\"5.0\",\"info_TotalNOK\":\"0.0\",\"info_RemainingParts\":\"45.0\",\"ff_ContinueWithLess\":false,\"info_Drawing\":\"assets/demoComp.pdf\",\"ff_PartsOK\":15,\"ff_PartsNOK\":0}', '[{\"key\":\"info_TotalOK\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Bisherige i.O.\",\"value\":\"5.0\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"9\\\",\\\"col-width\\\":\\\"2\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":0},{\"key\":\"info_TotalNOK\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Bisherige n.i.O.\",\"value\":\"0.0\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"11\\\",\\\"col-width\\\":\\\"2\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":1},{\"key\":\"info_RemainingParts\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Verbleibende i.O.\",\"value\":\"45.0\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"7\\\",\\\"col-width\\\":\\\"2\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":2},{\"key\":\"ff_Note\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Bemerkung\",\"properties\":\"{\\\"col-width\\\":\\\"6\\\",\\\"col-start\\\":\\\"7\\\",\\\"row-height\\\":\\\"1\\\",\\\"row-start\\\":\\\"5\\\"}\",\"order\":3},{\"key\":\"ff_PartsOK\",\"controlType\":\"textbox\",\"type\":\"number\",\"label\":\"Teile i.O.\",\"validationConstraints\":\"[{\\\"name\\\":\\\"required\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"7\\\",\\\"col-width\\\":\\\"3\\\",\\\"row-height\\\":\\\"1\\\",\\\"row-start\\\":\\\"2\\\"}\",\"order\":4},{\"key\":\"ff_PartsNOK\",\"controlType\":\"textbox\",\"type\":\"number\",\"label\":\"Teile n.i.O.\",\"validationConstraints\":\"[{\\\"name\\\":\\\"required\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"10\\\",\\\"col-width\\\":\\\"3\\\",\\\"row-start\\\":\\\"2\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":5},{\"key\":\"ff_ContinueWithLess\",\"controlType\":\"checkbox\",\"type\":\"boolean\",\"label\":\"Arbeitsgang mit reduzierter Fertigungsstückzahl abschließen\",\"value\":\"false\",\"properties\":\"{\\\"col-width\\\":\\\"6\\\",\\\"col-start\\\":\\\"7\\\",\\\"row-start\\\":\\\"4\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":6},{\"key\":\"ff_ManufacturingProblem\",\"controlType\":\"dropdown\",\"type\":\"enum\",\"label\":\"Fertigungsproblem\",\"properties\":\"{\\\"col-start\\\":\\\"7\\\",\\\"col-width\\\":\\\"6\\\",\\\"row-start\\\":\\\"6\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":7,\"options\":[{\"key\":\"Maschinenstoerung\",\"value\":\"Maschinenstörung\"},{\"key\":\"Werkzeugstoerung\",\"value\":\"Werkzeugstörung\"},{\"key\":\"Mitarbeitereinarbeitung\",\"value\":\"Ungenügende Einarbeitung von Mitarbeitern\"},{\"key\":\"AenderungArtikelArbeitsprozess\",\"value\":\"Änderung von Artikel / Arbeitsprozess\"}]},{\"key\":\"info_Drawing\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Zeichnung\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"type\\\":\\\"pdf\\\",\\\"col-start\\\":\\\"1\\\",\\\"col-width\\\":\\\"6\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"7\\\"}\",\"order\":8}]', 'ConfirmProductionTask', 'Einfache Rückmeldung', 'Activity_WZB_Rueckmeldung_Basis', 'e770ac93-ab27-11ef-93e1-0242ac1d0004', 144, 234),
(241, '2024-11-25 13:24:07.084143', NULL, '2024-11-25 13:24:11.205135', NULL, '{\"varTotalNOK\":null,\"businessKey\":\"Process_Basis#Insti/MO/00017/26#174878868\",\"varTotalOK\":null,\"userName\":\"ffunke\",\"key\":\"Process_Basis\",\"varProdAmount\":50.0}', '{\"info_WorkorderText\":\"x:120, y:10, ...\",\"ff_Checked\":true}', '[{\"key\":\"info_WorkorderText\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Einstellparameter\",\"value\":\"x:120, y:10, ...\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"type\\\":\\\"data\\\",\\\"col-width\\\":\\\"12\\\",\\\"col-start\\\":\\\"1\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":0},{\"key\":\"ff_Checked\",\"controlType\":\"checkbox\",\"type\":\"boolean\",\"label\":\"Maschine eingerüstet\",\"validationConstraints\":\"[{\\\"name\\\":\\\"required\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-width\\\":\\\"4\\\",\\\"col-start\\\":\\\"1\\\",\\\"row-start\\\":\\\"2\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":1},{\"key\":\"ff_Note_1_1\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Bemerkung\",\"properties\":\"{\\\"col-width\\\":\\\"8\\\",\\\"col-start\\\":\\\"5\\\",\\\"row-height\\\":\\\"1\\\",\\\"row-start\\\":\\\"2\\\"}\",\"order\":2}]', 'SetParameterTask', 'Maschine Rüsten', 'Activity_0279ebn', '2a3d3322-ab28-11ef-93e1-0242ac1d0004', 147, 239),
(245, '2024-11-25 13:24:11.916555', NULL, '2024-11-25 13:28:57.084806', NULL, '{\"varTotalNOK\":null,\"ff_Checked\":true,\"varTotalOK\":null,\"ff_PartsNOK\":null,\"userName\":\"ffunke\",\"ff_PartsOK\":null,\"ff_Note_2_1\":null,\"businessKey\":\"Process_Basis#Insti/MO/00017/26#174878868\",\"varSubProdStepId\":239,\"info_WorkorderText\":\"x:120, y:10, ...\",\"key\":\"Process_Basis\",\"varProdAmount\":50.0}', '{\"info_TotalOK\":\"0.0\",\"info_TotalNOK\":\"0.0\",\"info_RemainingParts\":\"50.0\",\"ff_ContinueWithLess\":false,\"info_Drawing\":\"assets/demoComp.pdf\",\"ff_PartsOK\":50,\"ff_PartsNOK\":0}', '[{\"key\":\"info_TotalOK\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Bisherige i.O.\",\"value\":\"0.0\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"9\\\",\\\"col-width\\\":\\\"2\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":0},{\"key\":\"info_TotalNOK\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Bisherige n.i.O.\",\"value\":\"0.0\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"11\\\",\\\"col-width\\\":\\\"2\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":1},{\"key\":\"info_RemainingParts\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Verbleibende i.O.\",\"value\":\"50.0\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"7\\\",\\\"col-width\\\":\\\"2\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":2},{\"key\":\"ff_Note\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Bemerkung\",\"properties\":\"{\\\"col-width\\\":\\\"6\\\",\\\"col-start\\\":\\\"7\\\",\\\"row-height\\\":\\\"1\\\",\\\"row-start\\\":\\\"5\\\"}\",\"order\":3},{\"key\":\"ff_PartsOK\",\"controlType\":\"textbox\",\"type\":\"number\",\"label\":\"Teile i.O.\",\"validationConstraints\":\"[{\\\"name\\\":\\\"required\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"7\\\",\\\"col-width\\\":\\\"3\\\",\\\"row-height\\\":\\\"1\\\",\\\"row-start\\\":\\\"2\\\"}\",\"order\":4},{\"key\":\"ff_PartsNOK\",\"controlType\":\"textbox\",\"type\":\"number\",\"label\":\"Teile n.i.O.\",\"validationConstraints\":\"[{\\\"name\\\":\\\"required\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"10\\\",\\\"col-width\\\":\\\"3\\\",\\\"row-start\\\":\\\"2\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":5},{\"key\":\"ff_ContinueWithLess\",\"controlType\":\"checkbox\",\"type\":\"boolean\",\"label\":\"Arbeitsgang mit reduzierter Fertigungsstückzahl abschließen\",\"value\":\"false\",\"properties\":\"{\\\"col-width\\\":\\\"6\\\",\\\"col-start\\\":\\\"7\\\",\\\"row-start\\\":\\\"4\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":6},{\"key\":\"ff_ManufacturingProblem\",\"controlType\":\"dropdown\",\"type\":\"enum\",\"label\":\"Fertigungsproblem\",\"properties\":\"{\\\"col-start\\\":\\\"7\\\",\\\"col-width\\\":\\\"6\\\",\\\"row-start\\\":\\\"6\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":7,\"options\":[{\"key\":\"Maschinenstoerung\",\"value\":\"Maschinenstörung\"},{\"key\":\"Werkzeugstoerung\",\"value\":\"Werkzeugstörung\"},{\"key\":\"Mitarbeitereinarbeitung\",\"value\":\"Ungenügende Einarbeitung von Mitarbeitern\"},{\"key\":\"AenderungArtikelArbeitsprozess\",\"value\":\"Änderung von Artikel / Arbeitsprozess\"}]},{\"key\":\"info_Drawing\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Zeichnung\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"type\\\":\\\"pdf\\\",\\\"col-start\\\":\\\"1\\\",\\\"col-width\\\":\\\"6\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"7\\\"}\",\"order\":8}]', 'ConfirmProductionTask', 'Einfache Rückmeldung', 'Activity_WZB_Rueckmeldung_Basis', '2cfbde2d-ab28-11ef-93e1-0242ac1d0004', 147, 243),
(252, '2024-11-25 13:25:05.663945', NULL, '2024-11-25 13:29:15.565471', NULL, '{\"info_RemainingParts\":\"30.0\",\"varTotalNOK\":0.0,\"ff_Checked\":true,\"varSubProductionStepId\":234,\"info_TotalOK\":\"20.0\",\"varTotalOK\":20.0,\"ff_PartsNOK\":null,\"userName\":\"llustig\",\"ff_Note_1_1\":\"Vorgezogen\",\"ff_PartsOK\":null,\"ff_Note_2_1\":null,\"info_TotalNOK\":\"0.0\",\"businessKey\":\"Process_Basis#Insti/MO/00014/23#223833442\",\"varSubProdStepId\":222,\"info_WorkorderText\":\"x:120, y:10, ...\",\"ff_ContinueWithLess\":false,\"key\":\"Process_Basis\",\"varProdAmount\":50.0}', '{\"info_TotalOK\":\"20.0\",\"info_TotalNOK\":\"0.0\",\"info_RemainingParts\":\"30.0\",\"ff_ContinueWithLess\":false,\"info_Drawing\":\"assets/demoComp.pdf\",\"ff_PartsOK\":20,\"ff_PartsNOK\":0}', '[{\"key\":\"info_TotalOK\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Bisherige i.O.\",\"value\":\"20.0\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"9\\\",\\\"col-width\\\":\\\"2\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":0},{\"key\":\"info_TotalNOK\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Bisherige n.i.O.\",\"value\":\"0.0\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"11\\\",\\\"col-width\\\":\\\"2\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":1},{\"key\":\"info_RemainingParts\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Verbleibende i.O.\",\"value\":\"30.0\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"7\\\",\\\"col-width\\\":\\\"2\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":2},{\"key\":\"ff_Note\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Bemerkung\",\"properties\":\"{\\\"col-width\\\":\\\"6\\\",\\\"col-start\\\":\\\"7\\\",\\\"row-height\\\":\\\"1\\\",\\\"row-start\\\":\\\"5\\\"}\",\"order\":3},{\"key\":\"ff_PartsOK\",\"controlType\":\"textbox\",\"type\":\"number\",\"label\":\"Teile i.O.\",\"validationConstraints\":\"[{\\\"name\\\":\\\"required\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"7\\\",\\\"col-width\\\":\\\"3\\\",\\\"row-height\\\":\\\"1\\\",\\\"row-start\\\":\\\"2\\\"}\",\"order\":4},{\"key\":\"ff_PartsNOK\",\"controlType\":\"textbox\",\"type\":\"number\",\"label\":\"Teile n.i.O.\",\"validationConstraints\":\"[{\\\"name\\\":\\\"required\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"10\\\",\\\"col-width\\\":\\\"3\\\",\\\"row-start\\\":\\\"2\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":5},{\"key\":\"ff_ContinueWithLess\",\"controlType\":\"checkbox\",\"type\":\"boolean\",\"label\":\"Arbeitsgang mit reduzierter Fertigungsstückzahl abschließen\",\"value\":\"false\",\"properties\":\"{\\\"col-width\\\":\\\"6\\\",\\\"col-start\\\":\\\"7\\\",\\\"row-start\\\":\\\"4\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":6},{\"key\":\"ff_ManufacturingProblem\",\"controlType\":\"dropdown\",\"type\":\"enum\",\"label\":\"Fertigungsproblem\",\"properties\":\"{\\\"col-start\\\":\\\"7\\\",\\\"col-width\\\":\\\"6\\\",\\\"row-start\\\":\\\"6\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":7,\"options\":[{\"key\":\"Maschinenstoerung\",\"value\":\"Maschinenstörung\"},{\"key\":\"Werkzeugstoerung\",\"value\":\"Werkzeugstörung\"},{\"key\":\"Mitarbeitereinarbeitung\",\"value\":\"Ungenügende Einarbeitung von Mitarbeitern\"},{\"key\":\"AenderungArtikelArbeitsprozess\",\"value\":\"Änderung von Artikel / Arbeitsprozess\"}]},{\"key\":\"info_Drawing\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Zeichnung\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"type\\\":\\\"pdf\\\",\\\"col-start\\\":\\\"1\\\",\\\"col-width\\\":\\\"6\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"7\\\"}\",\"order\":8}]', 'ConfirmProductionTask', 'Einfache Rückmeldung', 'Activity_WZB_Rueckmeldung_Basis', '4d07e8ce-ab28-11ef-93e1-0242ac1d0004', 144, 250),
(260, '2024-11-25 13:29:16.336385', NULL, '2024-11-25 13:32:36.924718', NULL, '{\"info_RemainingParts\":\"10.0\",\"varTotalNOK\":0.0,\"ff_Checked\":true,\"varSubProductionStepId\":250,\"info_TotalOK\":\"40.0\",\"varTotalOK\":40.0,\"ff_PartsNOK\":null,\"userName\":\"llustig\",\"ff_Note_1_1\":\"Vorgezogen\",\"ff_PartsOK\":null,\"ff_Note_2_1\":null,\"info_TotalNOK\":\"0.0\",\"businessKey\":\"Process_Basis#Insti/MO/00014/23#223833442\",\"varSubProdStepId\":222,\"info_WorkorderText\":\"x:120, y:10, ...\",\"ff_ContinueWithLess\":false,\"key\":\"Process_Basis\",\"varProdAmount\":50.0}', '{\"info_TotalOK\":\"40.0\",\"info_TotalNOK\":\"0.0\",\"info_RemainingParts\":\"10.0\",\"ff_ContinueWithLess\":false,\"info_Drawing\":\"assets/demoComp.pdf\",\"ff_PartsOK\":10,\"ff_PartsNOK\":0}', '[{\"key\":\"info_TotalOK\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Bisherige i.O.\",\"value\":\"40.0\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"9\\\",\\\"col-width\\\":\\\"2\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":0},{\"key\":\"info_TotalNOK\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Bisherige n.i.O.\",\"value\":\"0.0\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"11\\\",\\\"col-width\\\":\\\"2\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":1},{\"key\":\"info_RemainingParts\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Verbleibende i.O.\",\"value\":\"10.0\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"7\\\",\\\"col-width\\\":\\\"2\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":2},{\"key\":\"ff_Note\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Bemerkung\",\"properties\":\"{\\\"col-width\\\":\\\"6\\\",\\\"col-start\\\":\\\"7\\\",\\\"row-height\\\":\\\"1\\\",\\\"row-start\\\":\\\"5\\\"}\",\"order\":3},{\"key\":\"ff_PartsOK\",\"controlType\":\"textbox\",\"type\":\"number\",\"label\":\"Teile i.O.\",\"validationConstraints\":\"[{\\\"name\\\":\\\"required\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"7\\\",\\\"col-width\\\":\\\"3\\\",\\\"row-height\\\":\\\"1\\\",\\\"row-start\\\":\\\"2\\\"}\",\"order\":4},{\"key\":\"ff_PartsNOK\",\"controlType\":\"textbox\",\"type\":\"number\",\"label\":\"Teile n.i.O.\",\"validationConstraints\":\"[{\\\"name\\\":\\\"required\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"10\\\",\\\"col-width\\\":\\\"3\\\",\\\"row-start\\\":\\\"2\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":5},{\"key\":\"ff_ContinueWithLess\",\"controlType\":\"checkbox\",\"type\":\"boolean\",\"label\":\"Arbeitsgang mit reduzierter Fertigungsstückzahl abschließen\",\"value\":\"false\",\"properties\":\"{\\\"col-width\\\":\\\"6\\\",\\\"col-start\\\":\\\"7\\\",\\\"row-start\\\":\\\"4\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":6},{\"key\":\"ff_ManufacturingProblem\",\"controlType\":\"dropdown\",\"type\":\"enum\",\"label\":\"Fertigungsproblem\",\"properties\":\"{\\\"col-start\\\":\\\"7\\\",\\\"col-width\\\":\\\"6\\\",\\\"row-start\\\":\\\"6\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":7,\"options\":[{\"key\":\"Maschinenstoerung\",\"value\":\"Maschinenstörung\"},{\"key\":\"Werkzeugstoerung\",\"value\":\"Werkzeugstörung\"},{\"key\":\"Mitarbeitereinarbeitung\",\"value\":\"Ungenügende Einarbeitung von Mitarbeitern\"},{\"key\":\"AenderungArtikelArbeitsprozess\",\"value\":\"Änderung von Artikel / Arbeitsprozess\"}]},{\"key\":\"info_Drawing\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Zeichnung\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"type\\\":\\\"pdf\\\",\\\"col-start\\\":\\\"1\\\",\\\"col-width\\\":\\\"6\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"7\\\"}\",\"order\":8}]', 'ConfirmProductionTask', 'Einfache Rückmeldung', 'Activity_WZB_Rueckmeldung_Basis', 'e26472ee-ab28-11ef-93e1-0242ac1d0004', 144, 258),
(267, '2024-11-25 13:32:56.230622', NULL, '2024-11-25 13:33:01.533913', NULL, '{\"varTotalNOK\":null,\"businessKey\":\"Process_Basis#Insti/MO/00015/24#1248360068\",\"varTotalOK\":null,\"userName\":\"llustig\",\"key\":\"Process_Basis\",\"varProdAmount\":50.0}', '{\"info_WorkorderText\":\"x:120, y:10, ...\",\"ff_Checked\":true}', '[{\"key\":\"info_WorkorderText\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Einstellparameter\",\"value\":\"x:120, y:10, ...\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"type\\\":\\\"data\\\",\\\"col-width\\\":\\\"12\\\",\\\"col-start\\\":\\\"1\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":0},{\"key\":\"ff_Checked\",\"controlType\":\"checkbox\",\"type\":\"boolean\",\"label\":\"Maschine eingerüstet\",\"validationConstraints\":\"[{\\\"name\\\":\\\"required\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-width\\\":\\\"4\\\",\\\"col-start\\\":\\\"1\\\",\\\"row-start\\\":\\\"2\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":1},{\"key\":\"ff_Note_1_1\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Bemerkung\",\"properties\":\"{\\\"col-width\\\":\\\"8\\\",\\\"col-start\\\":\\\"5\\\",\\\"row-height\\\":\\\"1\\\",\\\"row-start\\\":\\\"2\\\"}\",\"order\":2}]', 'SetParameterTask', 'Maschine Rüsten', 'Activity_0279ebn', '65adc3a7-ab29-11ef-93e1-0242ac1d0004', 150, 265),
(272, '2024-11-25 13:33:02.140178', NULL, '2024-11-25 13:33:09.235991', NULL, '{\"varTotalNOK\":null,\"ff_Checked\":true,\"varTotalOK\":null,\"ff_PartsNOK\":null,\"userName\":\"llustig\",\"ff_PartsOK\":null,\"ff_Note_2_1\":null,\"businessKey\":\"Process_Basis#Insti/MO/00015/24#1248360068\",\"varSubProdStepId\":265,\"info_WorkorderText\":\"x:120, y:10, ...\",\"key\":\"Process_Basis\",\"varProdAmount\":50.0}', '{\"info_TotalOK\":\"0.0\",\"info_TotalNOK\":\"0.0\",\"info_RemainingParts\":\"50.0\",\"ff_ContinueWithLess\":false,\"info_Drawing\":\"assets/demoComp.pdf\",\"ff_PartsOK\":50,\"ff_PartsNOK\":0}', '[{\"key\":\"info_TotalOK\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Bisherige i.O.\",\"value\":\"0.0\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"9\\\",\\\"col-width\\\":\\\"2\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":0},{\"key\":\"info_TotalNOK\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Bisherige n.i.O.\",\"value\":\"0.0\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"11\\\",\\\"col-width\\\":\\\"2\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":1},{\"key\":\"info_RemainingParts\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Verbleibende i.O.\",\"value\":\"50.0\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"7\\\",\\\"col-width\\\":\\\"2\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":2},{\"key\":\"ff_Note\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Bemerkung\",\"properties\":\"{\\\"col-width\\\":\\\"6\\\",\\\"col-start\\\":\\\"7\\\",\\\"row-height\\\":\\\"1\\\",\\\"row-start\\\":\\\"5\\\"}\",\"order\":3},{\"key\":\"ff_PartsOK\",\"controlType\":\"textbox\",\"type\":\"number\",\"label\":\"Teile i.O.\",\"validationConstraints\":\"[{\\\"name\\\":\\\"required\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"7\\\",\\\"col-width\\\":\\\"3\\\",\\\"row-height\\\":\\\"1\\\",\\\"row-start\\\":\\\"2\\\"}\",\"order\":4},{\"key\":\"ff_PartsNOK\",\"controlType\":\"textbox\",\"type\":\"number\",\"label\":\"Teile n.i.O.\",\"validationConstraints\":\"[{\\\"name\\\":\\\"required\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"col-start\\\":\\\"10\\\",\\\"col-width\\\":\\\"3\\\",\\\"row-start\\\":\\\"2\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":5},{\"key\":\"ff_ContinueWithLess\",\"controlType\":\"checkbox\",\"type\":\"boolean\",\"label\":\"Arbeitsgang mit reduzierter Fertigungsstückzahl abschließen\",\"value\":\"false\",\"properties\":\"{\\\"col-width\\\":\\\"6\\\",\\\"col-start\\\":\\\"7\\\",\\\"row-start\\\":\\\"4\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":6},{\"key\":\"ff_ManufacturingProblem\",\"controlType\":\"dropdown\",\"type\":\"enum\",\"label\":\"Fertigungsproblem\",\"properties\":\"{\\\"col-start\\\":\\\"7\\\",\\\"col-width\\\":\\\"6\\\",\\\"row-start\\\":\\\"6\\\",\\\"row-height\\\":\\\"1\\\"}\",\"order\":7,\"options\":[{\"key\":\"Maschinenstoerung\",\"value\":\"Maschinenstörung\"},{\"key\":\"Werkzeugstoerung\",\"value\":\"Werkzeugstörung\"},{\"key\":\"Mitarbeitereinarbeitung\",\"value\":\"Ungenügende Einarbeitung von Mitarbeitern\"},{\"key\":\"AenderungArtikelArbeitsprozess\",\"value\":\"Änderung von Artikel / Arbeitsprozess\"}]},{\"key\":\"info_Drawing\",\"controlType\":\"textbox\",\"type\":\"string\",\"label\":\"Zeichnung\",\"validationConstraints\":\"[{\\\"name\\\":\\\"readonly\\\",\\\"configuration\\\":\\\"true\\\"}]\",\"properties\":\"{\\\"type\\\":\\\"pdf\\\",\\\"col-start\\\":\\\"1\\\",\\\"col-width\\\":\\\"6\\\",\\\"row-start\\\":\\\"1\\\",\\\"row-height\\\":\\\"7\\\"}\",\"order\":8}]', 'ConfirmProductionTask', 'Einfache Rückmeldung', 'Activity_WZB_Rueckmeldung_Basis', '690616a2-ab29-11ef-93e1-0242ac1d0004', 150, 270);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `CD_Department`
--

CREATE TABLE `CD_Department` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `CD_Department`
--

INSERT INTO `CD_Department` (`id`, `createDateTime`, `externalId`, `updateDateTime`, `versionNr`, `name`) VALUES
(1, NULL, '2', '2024-11-25 13:17:55.598195', '', 'Institut für Informationssysteme der Hochschule Hof - iisys');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `CD_Machine`
--

CREATE TABLE `CD_Machine` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `machineType_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `CD_Machine`
--

INSERT INTO `CD_Machine` (`id`, `createDateTime`, `externalId`, `updateDateTime`, `versionNr`, `name`, `machineType_id`) VALUES
(22, NULL, '10', '2024-11-25 13:17:56.686331', '', 'Spritzgießen A', 10),
(25, NULL, '11', '2024-11-25 13:17:56.674011', '', 'Spritzgießen B', 10),
(28, NULL, '12', '2024-11-25 13:17:56.789161', '', 'Wickeln', 11),
(29, NULL, '9', '2024-11-25 13:17:56.870468', '', 'Tiefziehen', 12),
(30, NULL, '14', '2024-11-25 13:17:56.802014', '', 'Handmontage', 13),
(31, NULL, '15', '2024-11-25 13:17:56.815457', '', 'Endmontage', 14),
(32, NULL, '20', '2024-11-25 13:17:56.832298', '', 'QS Prüfung', 17),
(35, NULL, '18', '2024-11-25 13:17:56.845564', '', 'Verpackung', 20),
(38, NULL, '19', '2024-11-25 13:17:56.858563', '', 'Versand', 21);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `CD_MachineType`
--

CREATE TABLE `CD_MachineType` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `CD_MachineType`
--

INSERT INTO `CD_MachineType` (`id`, `createDateTime`, `externalId`, `updateDateTime`, `versionNr`, `name`) VALUES
(10, NULL, '001', '2024-11-25 13:17:56.394276', '', 'Spritzgießen'),
(11, NULL, '002', '2024-11-25 13:17:56.430836', '', 'Wickeln'),
(12, NULL, '003', '2024-11-25 13:17:56.607739', '', 'Tiefziehen'),
(13, NULL, '004', '2024-11-25 13:17:56.461137', '', 'Handmontage'),
(14, NULL, '005', '2024-11-25 13:17:56.489752', '', 'Endmontage'),
(17, NULL, '006', '2024-11-25 13:17:56.519925', '', 'QS Prüfung'),
(20, NULL, '007', '2024-11-25 13:17:56.553168', '', 'Verpackung'),
(21, NULL, '008', '2024-11-25 13:17:56.580486', '', 'Versand');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `CD_MachineType_CD_Department`
--

CREATE TABLE `CD_MachineType_CD_Department` (
  `machineType` bigint NOT NULL,
  `department` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `CD_MachineType_CD_Department`
--

INSERT INTO `CD_MachineType_CD_Department` (`machineType`, `department`) VALUES
(10, 1),
(11, 1),
(13, 1),
(14, 1),
(17, 1),
(20, 1),
(21, 1),
(12, 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `CD_OverheadCostCenter`
--

CREATE TABLE `CD_OverheadCostCenter` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `CD_Product`
--

CREATE TABLE `CD_Product` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `unitType` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `CD_Product`
--

INSERT INTO `CD_Product` (`id`, `createDateTime`, `externalId`, `updateDateTime`, `versionNr`, `name`, `unitType`) VALUES
(103, '2024-11-25 13:17:55.653274', 'Insti/MO/00004_84', '2024-11-25 13:17:55.653312', '', 'Griffrohr Holz', NULL),
(104, '2024-11-25 13:17:55.671839', 'Insti/MO/00014_69', '2024-11-25 13:17:55.671868', '', 'Spitze Kunststoff', NULL),
(105, '2024-11-25 13:17:55.691735', 'Insti/MO/00017_71', '2024-11-25 13:17:55.691824', '', 'Griffrohr B blau', NULL),
(106, '2024-11-25 13:17:55.709849', 'Insti/MO/00015_63', '2024-11-25 13:17:55.709877', '', 'Druckfeder Metall', NULL),
(107, NULL, 'Insti/MO/00028_106', '2024-11-25 13:17:55.833897', '', 'Kugelschreiber blau/rot, Kunststoff', NULL),
(108, NULL, 'Insti/MO/00027_106', '2024-11-25 13:17:55.954534', '', 'Kugelschreiber blau/rot, Kunststoff', NULL);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `CD_ProductionStep`
--

CREATE TABLE `CD_ProductionStep` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `camundaProcessName` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `productionDuration` bigint DEFAULT NULL,
  `sequence` int NOT NULL,
  `setupTime` bigint DEFAULT NULL,
  `machineType_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `productionStepInfo_id` bigint DEFAULT NULL,
  `toolType_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `CD_ProductionStep`
--

INSERT INTO `CD_ProductionStep` (`id`, `createDateTime`, `externalId`, `updateDateTime`, `versionNr`, `camundaProcessName`, `name`, `productionDuration`, `sequence`, `setupTime`, `machineType_id`, `product_id`, `productionStepInfo_id`, `toolType_id`) VALUES
(109, '2024-11-25 13:17:56.939403', 'Insti/MO/00004/20', '2024-11-25 13:17:56.939437', '', 'Subprocess_Montage', 'Montage Griff', 362, 100, 0, 13, 103, NULL, NULL),
(110, '2024-11-25 13:17:56.965427', 'Insti/MO/00014/23', '2024-11-25 13:17:56.965456', '', 'Subprocess_Basis', 'Spritzgießen Spitze', 30, 100, 0, 10, 104, NULL, NULL),
(111, '2024-11-25 13:17:56.986058', 'Insti/MO/00017/26', '2024-11-25 13:17:56.986086', '', 'Subprocess_Basis', 'Spritzgießen Griffrohr A', 24, 100, 0, 10, 105, NULL, NULL),
(112, '2024-11-25 13:17:57.009758', 'Insti/MO/00015/24', '2024-11-25 13:17:57.009786', '', 'Subprocess_Basis', 'Wickeln', 1809, 100, 0, 11, 106, NULL, NULL),
(113, '2024-11-25 13:17:57.036339', 'Insti/MO/00028/61', '2024-11-25 13:17:57.036367', '', 'Subprocess_Basis', 'Spritzgießen Griffrohr A', 2, 100, 0, 10, 107, NULL, NULL),
(114, '2024-11-25 13:17:57.064622', 'Insti/MO/00028/62', '2024-11-25 13:17:57.064646', '', 'Subprocess_Basis', 'Spritzgießen Griffrohr B', 3, 101, 0, 10, 107, NULL, NULL),
(115, '2024-11-25 13:17:57.099007', 'Insti/MO/00028/63', '2024-11-25 13:17:57.099032', '', 'Subprocess_Basis', 'Spritzgießen Druckhülse', 6, 102, 0, 10, 107, NULL, NULL),
(116, '2024-11-25 13:17:57.132570', 'Insti/MO/00028/64', '2024-11-25 13:17:57.132599', '', 'Subprocess_Basis', 'Wickeln', 5, 103, 0, 11, 107, NULL, NULL),
(117, '2024-11-25 13:17:57.174854', 'Insti/MO/00028/65', '2024-11-25 13:17:57.174880', '', 'Subprocess_Basis', 'Tiefziehen', 9, 104, 0, 12, 107, NULL, NULL),
(118, '2024-11-25 13:17:57.213751', 'Insti/MO/00028/66', '2024-11-25 13:17:57.213775', '', 'Subprocess_Montage', 'Montage Griff', 45, 105, 0, 13, 107, NULL, NULL),
(119, '2024-11-25 13:17:57.240519', 'Insti/MO/00028/67', '2024-11-25 13:17:57.240546', '', 'Subprocess_Montage', 'Montage Kugelschreiber', 60, 106, 0, 14, 107, NULL, NULL),
(120, '2024-11-25 13:17:57.276478', 'Insti/MO/00028/68', '2024-11-25 13:17:57.276503', '', 'Subprocess_QS', 'QS Stiftprüfung', 9, 107, 0, 17, 107, NULL, NULL),
(121, '2024-11-25 13:17:57.298811', 'Insti/MO/00028/69', '2024-11-25 13:17:57.298853', '', 'Subprocess_Verpackung', 'Verpacken', 30, 108, 0, 20, 107, NULL, NULL),
(122, '2024-11-25 13:17:57.320847', 'Insti/MO/00028/70', '2024-11-25 13:17:57.320872', '', 'Subprocess_Versand', 'Versand', 30, 109, 0, 21, 107, NULL, NULL),
(123, '2024-11-25 13:17:57.340970', 'Insti/MO/00027/51', '2024-11-25 13:17:57.340994', '', 'Subprocess_Basis', 'Spritzgießen Griffrohr A', 2, 100, 0, 10, 108, NULL, NULL),
(124, '2024-11-25 13:17:57.368576', 'Insti/MO/00027/52', '2024-11-25 13:17:57.368601', '', 'Subprocess_Basis', 'Spritzgießen Griffrohr B', 3, 101, 0, 10, 108, NULL, NULL),
(125, '2024-11-25 13:17:57.391001', 'Insti/MO/00027/53', '2024-11-25 13:17:57.391025', '', 'Subprocess_Basis', 'Spritzgießen Druckhülse', 7, 102, 0, 10, 108, NULL, NULL),
(126, '2024-11-25 13:17:57.413046', 'Insti/MO/00027/54', '2024-11-25 13:17:57.413071', '', 'Subprocess_Basis', 'Wickeln', 5, 103, 0, 11, 108, NULL, NULL),
(127, '2024-11-25 13:17:57.434946', 'Insti/MO/00027/56', '2024-11-25 13:17:57.434970', '', 'Subprocess_Montage', 'Montage Griff', 45, 105, 0, 13, 108, NULL, NULL),
(128, '2024-11-25 13:17:57.465655', 'Insti/MO/00027/57', '2024-11-25 13:17:57.465680', '', 'Subprocess_Montage', 'Montage Kugelschreiber', 60, 106, 0, 14, 108, NULL, NULL),
(129, '2024-11-25 13:17:57.486196', 'Insti/MO/00027/58', '2024-11-25 13:17:57.486220', '', 'Subprocess_QS', 'QS Stiftprüfung', 10, 107, 0, 17, 108, NULL, NULL),
(130, '2024-11-25 13:17:57.507678', 'Insti/MO/00027/59', '2024-11-25 13:17:57.507703', '', 'Subprocess_Verpackung', 'Verpacken', 30, 108, 0, 20, 108, NULL, NULL),
(131, '2024-11-25 13:17:57.529194', 'Insti/MO/00027/60', '2024-11-25 13:17:57.529281', '', 'Subprocess_Versand', 'Versand', 30, 109, 0, 21, 108, NULL, NULL),
(132, '2024-11-25 13:17:57.549564', 'Insti/MO/00027/55', '2024-11-25 13:17:57.549590', '', 'Subprocess_Basis', 'Tiefziehen', 10, 104, 0, 12, 108, NULL, NULL);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `CD_ProductionStep_CD_ToolSettingParameter`
--

CREATE TABLE `CD_ProductionStep_CD_ToolSettingParameter` (
  `productionStep` bigint NOT NULL,
  `toolSettingParameter` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `CD_ProductRelationship`
--

CREATE TABLE `CD_ProductRelationship` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `extIdProductionOrder` varchar(255) DEFAULT NULL,
  `amount` double NOT NULL,
  `unitString` varchar(255) DEFAULT NULL,
  `part_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `CD_QualityControlFeature`
--

CREATE TABLE `CD_QualityControlFeature` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `tolerance` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `product_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `CD_Tool`
--

CREATE TABLE `CD_Tool` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `toolType_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `CD_ToolSettingParameter`
--

CREATE TABLE `CD_ToolSettingParameter` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `unitType` varchar(255) DEFAULT NULL,
  `machineType_id` bigint DEFAULT NULL,
  `toolType_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `CD_ToolType`
--

CREATE TABLE `CD_ToolType` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `number` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ClassExtension`
--

CREATE TABLE `ClassExtension` (
  `fieldId` bigint NOT NULL,
  `id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ClassExtension_MemberExtension`
--

CREATE TABLE `ClassExtension_MemberExtension` (
  `ClassExtension_fieldId` bigint NOT NULL,
  `members_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `CsvReaderParserConfig`
--

CREATE TABLE `CsvReaderParserConfig` (
  `id` bigint NOT NULL,
  `separatorChar` char(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `CustomerOrder`
--

CREATE TABLE `CustomerOrder` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `customerName` varchar(255) DEFAULT NULL,
  `deadline` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `priority` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `DataReader`
--

CREATE TABLE `DataReader` (
  `id` bigint NOT NULL,
  `ParserConfigID` bigint DEFAULT NULL,
  `parserID` varchar(255) DEFAULT NULL,
  `parserKeyValueConfigs` longtext,
  `ReaderConfigID` bigint DEFAULT NULL,
  `readerID` varchar(255) DEFAULT NULL,
  `readerKeyValueConfigs` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `DataReader`
--

INSERT INTO `DataReader` (`id`, `ParserConfigID`, `parserID`, `parserKeyValueConfigs`, `ReaderConfigID`, `readerID`, `readerKeyValueConfigs`) VALUES
(63, 0, 'parserPlugin-JSON', '[]', 0, 'inputPlugin-DatabaseReader', '[{\"configKey\":\"CLASSNAME\",\"configValue\":\"MachineOccupation\"},{\"configKey\":\"SEARCH_COLUMN\",\"configValue\":\"externalId\"},{\"configKey\":\"SEARCH_VALUE\",\"configValue\":\"364914011_1625\"},{\"configKey\":\"LOOP_IF_NOT_FINISHED\",\"configValue\":\"true\"},{\"configKey\":\"ADDITIONAL\",\"configValue\":\"29120\"}]'),
(67, 0, 'parserPlugin-JSON', '[]', 0, 'inputPlugin-FPDatabaseReader', '[{\"configKey\":\"JDBC_URL\",\"configValue\":\"jdbc:mysql://mysql:3306/hicumesFP\"},{\"configKey\":\"USER\",\"configValue\":\"hicumes\"},{\"configKey\":\"PASSWORD\",\"configValue\":\"d8h_cpn!si1fn\"},{\"configKey\":\"DRIVER\",\"configValue\":\"com.mysql.cj.jdbc.Driver\"},{\"configKey\":\"ADDITIONAL\",\"configValue\":\"null\"}]'),
(71, 0, 'parserPlugin-JSON', '[]', 0, 'local-Camunda', '[]'),
(75, 0, 'parserPlugin-JSON', '[]', 0, 'local-Camunda', '[]'),
(79, 0, 'parserPlugin-JSON', '[]', 0, 'local-Camunda', '[]'),
(83, 0, 'parserPlugin-JSON', '[]', 0, 'local-Camunda', '[]'),
(87, 0, 'parserPlugin-JSON', '[]', 0, 'local-Camunda', '[]'),
(91, 0, 'parserPlugin-JSON', '[]', 0, 'inputPlugin-DatabaseReader', '[{\"configKey\":\"CLASSNAME\",\"configValue\":\"MachineOccupation\"},{\"configKey\":\"SEARCH_COLUMN\",\"configValue\":\"externalId\"},{\"configKey\":\"SEARCH_VALUE\",\"configValue\":\"363846005_1130\"},{\"configKey\":\"LOOP_IF_NOT_FINISHED\",\"configValue\":\"true\"},{\"configKey\":\"ADDITIONAL\",\"configValue\":\"27934\"}]'),
(95, 0, 'parserPlugin-JSON', '[]', 0, 'inputPlugin-SQLDatabase', '[{\"configKey\":\"DATABASE_TYPE\",\"configValue\":\"postgresql\"},{\"configKey\":\"URL\",\"configValue\":\"192.168.91.41\"},{\"configKey\":\"PORT\",\"configValue\":\"5432\"},{\"configKey\":\"DATABASE\",\"configValue\":\"odoo\"},{\"configKey\":\"USER\",\"configValue\":\"hicumes\"},{\"configKey\":\"PASSWORD\",\"configValue\":\"hicumes\"},{\"configKey\":\"QUERY\",\"configValue\":\"SELECT\\n\\tprod.id as cd_Product_extId,\\n    prod.name as cd_Product_name,\\n  \\n    pr.name as productionOrder_extId,\\n    pr.name as productionOrder_name,\\n    pr.date_finished as productionOrder_deadline,\\n    pr.product_qty as productionOrder_amount,\\n    \\n    comp.id as cD_Department_extId,\\n    comp.name as cD_Department_name,\\n  \\n    wc.id as cD_MachineType_extId,\\n    wc.name as cD_MachineType_name,\\n  \\n    wc.id as cD_Machine_extId,\\n    wc.name as cD_Machine_name,\\n  \\n    wo.barcode as cD_ProductionStep_extId,\\n    wo.name as cD_ProductionStep_name,\\n    wo.duration_expected as cD_ProductionStep_duration,  \\n\\trwc.sequence as cd_productionstep_number,\\n\\t\\n    wo.barcode as machineOccupation_extId,\\n    wo.name as machineOccupation_name,\\n    wo.production_date as machineOccupation_plannedStart\\n    \\n\\t\\nFROM public.mrp_production pr\\nJOIN public.res_company comp ON comp.id = pr.company_id\\nJOIN public.mrp_bom bo ON bo.id = pr.bom_id\\nJOIN public.product_template prod ON prod.id = bo.product_tmpl_id\\nJOIN public.mrp_workorder wo ON pr.id = wo.production_id\\nJOIN public.mrp_workcenter wc ON wc.id = wo.workcenter_id\\nJOIN public.mrp_routing_workcenter rwc ON rwc.bom_id = pr.bom_id and rwc.workcenter_id = wc.id and rwc.name = wo.name\\n\\t\\nwhere comp.id = 2 and pr.state = \'confirmed\' order by pr.name, rwc.sequence;\"}]'),
(99, 0, 'parserPlugin-JSON', '[]', 0, 'local-Camunda', '[]');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `DataWriter`
--

CREATE TABLE `DataWriter` (
  `id` bigint NOT NULL,
  `ParserConfigID` bigint DEFAULT NULL,
  `parserID` varchar(255) DEFAULT NULL,
  `parserKeyValueConfigs` longtext,
  `WriterConfigID` bigint DEFAULT NULL,
  `writerID` varchar(255) DEFAULT NULL,
  `writerKeyValueConfigs` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `DataWriter`
--

INSERT INTO `DataWriter` (`id`, `ParserConfigID`, `parserID`, `parserKeyValueConfigs`, `WriterConfigID`, `writerID`, `writerKeyValueConfigs`) VALUES
(64, 0, '', '[]', 0, 'outputPlugin-NewEntityEventWriter', '[{\"configKey\":\"ENTITY_FILTER\",\"configValue\":\"de.iisys.sysint.hicumes.core.entities\"},{\"configKey\":\"FORCE_UPDATE\",\"configValue\":\"false\"}]'),
(68, 0, '', '[]', 0, 'outputPlugin-NewEntityEventWriter', '[{\"configKey\":\"ENTITY_FILTER\",\"configValue\":\"de.iisys.sysint.hicumes.core.entities\"},{\"configKey\":\"FORCE_UPDATE\",\"configValue\":\"true\"}]'),
(72, 0, NULL, '[]', 0, 'outputPlugin-DatabaseWriter', '[{\"configKey\":\"DATABASE_NAME\",\"configValue\":\"hicumes\"},{\"configKey\":\"ENTITY_FILTER\",\"configValue\":\"de.iisys.sysint.hicumes.core.entities\"}]'),
(76, 0, NULL, '[]', 0, 'outputPlugin-DatabaseWriter', '[{\"configKey\":\"DATABASE_NAME\",\"configValue\":\"hicumes\"},{\"configKey\":\"ENTITY_FILTER\",\"configValue\":\"de.iisys.sysint.hicumes.core.entities\"}]'),
(80, 0, NULL, '[]', 0, 'outputPlugin-DatabaseWriter', '[{\"configKey\":\"DATABASE_NAME\",\"configValue\":\"hicumes\"},{\"configKey\":\"ENTITY_FILTER\",\"configValue\":\"de.iisys.sysint.hicumes.core.entities\"}]'),
(84, 0, NULL, '[]', 0, 'outputPlugin-DatabaseWriter', '[{\"configKey\":\"DATABASE_NAME\",\"configValue\":\"hicumes\"},{\"configKey\":\"ENTITY_FILTER\",\"configValue\":\"de.iisys.sysint.hicumes.core.entities\"}]'),
(88, 0, NULL, '[]', 0, 'outputPlugin-DatabaseWriter', '[{\"configKey\":\"DATABASE_NAME\",\"configValue\":\"hicumes\"},{\"configKey\":\"ENTITY_FILTER\",\"configValue\":\"de.iisys.sysint.hicumes.core.entities\"}]'),
(92, 0, '', '[]', 0, 'outputPlugin-NewEntityEventWriter', '[{\"configKey\":\"ENTITY_FILTER\",\"configValue\":\"de.iisys.sysint.hicumes.core.entities\"},{\"configKey\":\"FORCE_UPDATE\",\"configValue\":\"false\"}]'),
(96, 0, '', '[]', 0, 'outputPlugin-FPDatabaseWriter', '[{\"configKey\":\"JDBC_URL\",\"configValue\":\"jdbc:mysql://mysql:3306/hicumesFP\"},{\"configKey\":\"USER\",\"configValue\":\"hicumes\"},{\"configKey\":\"PASSWORD\",\"configValue\":\"d8h_cpn!si1fn\"},{\"configKey\":\"DRIVER\",\"configValue\":\"com.mysql.cj.jdbc.Driver\"},{\"configKey\":\"ADDITIONAL\",\"configValue\":\"null\"}]'),
(100, 0, NULL, '[]', 0, 'outputPlugin-DatabaseWriter', '[{\"configKey\":\"DATABASE_NAME\",\"configValue\":\"hicumes\"},{\"configKey\":\"ENTITY_FILTER\",\"configValue\":\"de.iisys.sysint.hicumes.core.entities\"}]');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276),
(276);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `HicumesSettings`
--

CREATE TABLE `HicumesSettings` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `disableBooking` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `KeyValueMapProductionOrder`
--

CREATE TABLE `KeyValueMapProductionOrder` (
  `productionOrder` bigint NOT NULL,
  `valueString` varchar(255) DEFAULT NULL,
  `keyString` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `KeyValueMapSubProductionStep`
--

CREATE TABLE `KeyValueMapSubProductionStep` (
  `subProductionStep` bigint NOT NULL,
  `valueString` varchar(255) DEFAULT NULL,
  `keyString` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `MachineOccupation`
--

CREATE TABLE `MachineOccupation` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `actualEndDateTime` datetime(6) DEFAULT NULL,
  `actualStartDateTime` datetime(6) DEFAULT NULL,
  `camundaProcessName` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `plannedEndDateTime` datetime(6) DEFAULT NULL,
  `plannedStartDateTime` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `department_id` bigint DEFAULT NULL,
  `machine_id` bigint DEFAULT NULL,
  `parentMachineOccupation_id` bigint DEFAULT NULL,
  `productionOrder_id` bigint DEFAULT NULL,
  `tool_id` bigint DEFAULT NULL,
  `totalProductionNumbers_id` bigint DEFAULT NULL,
  `userOccupation_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `MachineOccupation`
--

INSERT INTO `MachineOccupation` (`id`, `createDateTime`, `externalId`, `updateDateTime`, `versionNr`, `actualEndDateTime`, `actualStartDateTime`, `camundaProcessName`, `name`, `plannedEndDateTime`, `plannedStartDateTime`, `status`, `department_id`, `machine_id`, `parentMachineOccupation_id`, `productionOrder_id`, `tool_id`, `totalProductionNumbers_id`, `userOccupation_id`) VALUES
(139, '2024-11-25 13:17:58.021439', 'Insti/MO/00004/20', '2024-11-25 13:20:05.828765', '', NULL, '2024-11-25 13:19:23.676888', 'Process_Montage', '100', '2024-11-20 23:06:10.000000', '2024-11-20 08:01:10.000000', 'PAUSED', 1, 30, NULL, 133, NULL, 140, 211),
(142, '2024-11-25 13:17:58.076006', 'Insti/MO/00014/23', '2024-11-25 13:32:37.306220', '', '2024-11-25 13:32:37.248642', '2024-11-25 13:21:10.157014', 'Process_Basis', '100', '2024-11-20 07:26:45.000000', '2024-11-20 07:01:45.000000', 'FINISHED', 1, 22, NULL, 134, NULL, 143, 220),
(145, '2024-11-25 13:17:58.122721', 'Insti/MO/00017/26', '2024-11-25 13:28:57.450566', '', '2024-11-25 13:28:57.424279', '2024-11-25 13:24:06.821715', 'Process_Basis', '100', '2024-11-20 07:22:44.000000', '2024-11-20 07:02:44.000000', 'FINISHED', 1, 25, NULL, 135, NULL, 146, 238),
(148, '2024-11-25 13:17:58.168418', 'Insti/MO/00015/24', '2024-11-25 13:33:09.477977', '', '2024-11-25 13:33:09.454105', '2024-11-25 13:32:56.019738', 'Process_Basis', '100', '2024-11-21 09:08:11.000000', '2024-11-20 08:00:41.000000', 'FINISHED', 1, 28, NULL, 136, NULL, 149, 264),
(151, '2024-11-25 13:17:58.213957', 'Insti/MO/00028/61', '2024-11-25 13:17:58.213978', '', NULL, NULL, 'Process_Basis', '100', '2024-11-22 07:01:46.000000', '2024-11-22 07:00:06.000000', 'READY_TO_START', 1, 22, NULL, 137, NULL, 152, NULL),
(154, '2024-11-25 13:17:58.261899', 'Insti/MO/00028/62', '2024-11-25 13:17:58.261910', '', NULL, NULL, 'Process_Basis', '101', '2024-11-22 07:03:02.000000', '2024-11-22 07:00:32.000000', 'READY_TO_START', 1, 25, NULL, 137, NULL, 155, NULL),
(157, '2024-11-25 13:17:58.309481', 'Insti/MO/00028/63', '2024-11-25 13:17:58.309493', '', NULL, NULL, 'Process_Basis', '102', '2024-11-22 07:06:58.000000', '2024-11-22 07:01:58.000000', 'READY_TO_START', 1, 22, NULL, 137, NULL, 158, NULL),
(160, '2024-11-25 13:17:58.355769', 'Insti/MO/00028/64', '2024-11-25 13:17:58.355873', '', NULL, NULL, 'Process_Basis', '103', '2024-11-22 07:16:08.000000', '2024-11-22 07:11:58.000000', 'READY_TO_START', 1, 28, NULL, 137, NULL, 161, NULL),
(163, '2024-11-25 13:17:58.404425', 'Insti/MO/00028/65', '2024-11-25 13:17:58.404440', '', NULL, NULL, 'Process_Basis', '104', '2024-11-22 07:28:38.000000', '2024-11-22 07:21:08.000000', 'READY_TO_START', 1, 29, NULL, 137, NULL, 164, NULL),
(166, '2024-11-25 13:17:58.452318', 'Insti/MO/00028/66', '2024-11-25 13:17:58.452329', '', NULL, NULL, 'Process_Montage', '105', '2024-11-22 08:11:08.000000', '2024-11-22 07:33:38.000000', 'READY_TO_START', 1, 30, NULL, 137, NULL, 167, NULL),
(169, '2024-11-25 13:17:58.503052', 'Insti/MO/00028/67', '2024-11-25 13:17:58.503081', '', NULL, NULL, 'Process_Montage', '106', '2024-11-22 09:06:08.000000', '2024-11-22 08:16:08.000000', 'READY_TO_START', 1, 31, NULL, 137, NULL, 170, NULL),
(172, '2024-11-25 13:17:58.551851', 'Insti/MO/00028/68', '2024-11-25 13:17:58.551863', '', NULL, NULL, 'Process_Check', '107', '2024-11-22 09:18:38.000000', '2024-11-22 09:11:08.000000', 'READY_TO_START', 1, 32, NULL, 137, NULL, 173, NULL),
(175, '2024-11-25 13:17:58.602691', 'Insti/MO/00028/69', '2024-11-25 13:17:58.602701', '', NULL, NULL, 'Process_Verpackung', '108', '2024-11-22 09:48:38.000000', '2024-11-22 09:23:38.000000', 'READY_TO_START', 1, 35, NULL, 137, NULL, 176, NULL),
(178, '2024-11-25 13:17:58.652113', 'Insti/MO/00028/70', '2024-11-25 13:17:58.652125', '', NULL, NULL, 'Process_Versand', '109', '2024-11-22 10:18:38.000000', '2024-11-22 09:53:38.000000', 'READY_TO_START', 1, 38, NULL, 137, NULL, 179, NULL),
(181, '2024-11-25 13:17:58.697279', 'Insti/MO/00027/51', '2024-11-25 13:17:58.697297', '', NULL, NULL, 'Process_Basis', '100', '2024-11-26 08:15:00.000000', '2024-11-26 08:10:00.000000', 'READY_TO_START', 1, NULL, NULL, 138, NULL, 182, NULL),
(184, '2024-11-25 13:17:58.754517', 'Insti/MO/00027/52', '2024-11-25 13:17:58.754529', '', NULL, NULL, 'Process_Basis', '101', '2024-11-26 08:18:53.000000', '2024-11-26 08:11:23.000000', 'READY_TO_START', 1, NULL, NULL, 138, NULL, 185, NULL),
(187, '2024-11-25 13:17:58.800811', 'Insti/MO/00027/53', '2024-11-25 13:17:58.800823', '', NULL, NULL, 'Process_Basis', '102', '2024-11-26 08:30:05.000000', '2024-11-26 08:12:35.000000', 'READY_TO_START', 1, NULL, NULL, 138, NULL, 188, NULL),
(190, '2024-11-25 13:17:58.843799', 'Insti/MO/00027/54', '2024-11-25 13:17:58.843811', '', NULL, NULL, 'Process_Basis', '103', '2024-11-26 08:47:35.000000', '2024-11-26 08:35:05.000000', 'READY_TO_START', 1, 28, NULL, 138, NULL, 191, NULL),
(193, '2024-11-25 13:17:58.887544', 'Insti/MO/00027/56', '2024-11-25 13:17:58.887555', '', NULL, NULL, 'Process_Montage', '105', '2024-11-26 10:55:08.000000', '2024-11-26 09:02:38.000000', 'READY_TO_START', 1, 30, NULL, 138, NULL, 194, NULL),
(196, '2024-11-25 13:17:58.932984', 'Insti/MO/00027/57', '2024-11-25 13:17:58.932995', '', NULL, NULL, 'Process_Montage', '106', '2024-11-26 13:25:23.000000', '2024-11-26 10:55:23.000000', 'READY_TO_START', 1, 31, NULL, 138, NULL, 197, NULL),
(199, '2024-11-25 13:17:58.977655', 'Insti/MO/00027/58', '2024-11-25 13:17:58.977665', '', NULL, NULL, 'Process_Check', '107', '2024-11-26 12:04:16.000000', '2024-11-26 11:39:16.000000', 'READY_TO_START', 1, 32, NULL, 138, NULL, 200, NULL),
(202, '2024-11-25 13:17:59.021757', 'Insti/MO/00027/59', '2024-11-25 13:17:59.021770', '', NULL, NULL, 'Process_Verpackung', '108', '2024-11-26 15:00:11.000000', '2024-11-26 13:45:11.000000', 'READY_TO_START', 1, 35, NULL, 138, NULL, 203, NULL),
(205, '2024-11-25 13:17:59.066459', 'Insti/MO/00027/60', '2024-11-25 13:17:59.066475', '', NULL, NULL, 'Process_Versand', '109', '2024-11-26 16:26:50.000000', '2024-11-26 15:11:50.000000', 'READY_TO_START', 1, 38, NULL, 138, NULL, 206, NULL),
(208, '2024-11-25 13:17:59.112563', 'Insti/MO/00027/55', '2024-11-25 13:17:59.112580', '', NULL, NULL, 'Process_Basis', '104', '2024-11-26 09:02:56.000000', '2024-11-26 08:37:56.000000', 'READY_TO_START', 1, 29, NULL, 138, NULL, 209, NULL);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `MachineOccupation_ActiveToolSetting`
--

CREATE TABLE `MachineOccupation_ActiveToolSetting` (
  `machineOccupation` bigint NOT NULL,
  `toolSetting` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `MachineOccupation_CD_ProductionStep`
--

CREATE TABLE `MachineOccupation_CD_ProductionStep` (
  `machineOccupation` bigint NOT NULL,
  `productionStep` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `MachineOccupation_CD_ProductionStep`
--

INSERT INTO `MachineOccupation_CD_ProductionStep` (`machineOccupation`, `productionStep`) VALUES
(139, 109),
(142, 110),
(145, 111),
(148, 112),
(151, 113),
(154, 114),
(157, 115),
(160, 116),
(163, 117),
(166, 118),
(169, 119),
(172, 120),
(175, 121),
(178, 122),
(181, 123),
(184, 124),
(187, 125),
(190, 126),
(193, 127),
(196, 128),
(199, 129),
(202, 130),
(205, 131),
(208, 132);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `MachineOccupation_CD_Tool`
--

CREATE TABLE `MachineOccupation_CD_Tool` (
  `machineOccupation` bigint NOT NULL,
  `tool` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `MachineOccupation_MachineOccupation`
--

CREATE TABLE `MachineOccupation_MachineOccupation` (
  `MachineOccupation_id` bigint NOT NULL,
  `subMachineOccupations_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `MachineStatus`
--

CREATE TABLE `MachineStatus` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `MachineStatusHistory`
--

CREATE TABLE `MachineStatusHistory` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `startDateTime` datetime(6) DEFAULT NULL,
  `machine_id` bigint DEFAULT NULL,
  `machineStatus_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `MappingAndDataSource`
--

CREATE TABLE `MappingAndDataSource` (
  `id` bigint NOT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `dataReader_id` bigint NOT NULL,
  `dataWriter_id` bigint NOT NULL,
  `mappingConfiguration_id` bigint NOT NULL,
  `readerResult_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `MappingAndDataSource`
--

INSERT INTO `MappingAndDataSource` (`id`, `externalId`, `name`, `dataReader_id`, `dataWriter_id`, `mappingConfiguration_id`, `readerResult_id`) VALUES
(156204, 'Activity_0flm6mm', 'Collaboration_Schmeier_WZB > QS > QS Prüfung > Stiftprüfung (QS)', 75, 76, 77, 78),
(430741, 'Odoo_to_FP', 'Odoo_to_FP', 95, 96, 97, 98),
(467196, 'Activity_WZB_Rueckmeldung_Basis', 'Collaboration_Schmeier_WZB > Generische MGR Produktion > Basis AG Rückmelden > Einfache Rückmeldung', 83, 84, 85, 86),
(475982, 'GenerateReport', 'GenerateReport', 63, 64, 65, 66),
(478819, 'Activity_0279ebn', 'Collaboration_Schmeier_WZB > Generische MGR Produktion > Basis AG Rückmelden > Maschine Rüsten', 71, 72, 73, 74),
(506371, 'FP_to_HiCuMES', 'FP_to_HiCuMES', 67, 68, 69, 70),
(543989, 'Activity_11bo43c', 'Collaboration_Schmeier_WZB > Generische MGR Verpackung > Basis AG Verpackung > Einfache Rückmeldung', 87, 88, 89, 90),
(838732, 'Activity_0s5lqmy', 'Collaboration_Schmeier_WZB > Generische MGR Montage > Basis AG Montage > Einfache Rückmeldung', 99, 100, 101, 102),
(997839, 'Activity_14jppin', 'Collaboration_Schmeier_WZB > Versand > Versandtätigkeit > Vorbereiten zum Versand', 79, 80, 81, 82),
(47598353, 'GenerateReport_Partial', 'GenerateReport_Partial', 91, 92, 93, 94);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `MappingConfiguration`
--

CREATE TABLE `MappingConfiguration` (
  `id` bigint NOT NULL,
  `inputSelector` varchar(255) DEFAULT NULL,
  `outputSelector` varchar(255) DEFAULT NULL,
  `xsltRules` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `MappingConfiguration`
--

INSERT INTO `MappingConfiguration` (`id`, `inputSelector`, `outputSelector`, `xsltRules`) VALUES
(65, NULL, NULL, '<xsl:stylesheet version=\"2.0\"\n    xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"\n    xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n  <xsl:decimal-format name=\"d\" decimal-separator=\",\" grouping-separator=\".\"/>\n  <xsl:template match=\"/\">\n    <output>  \n        <xsl:choose>\n            <xsl:when test=\"/ObjectNode/result/subMachineOccupations\">\n                <xsl:for-each\n                    select=\"/ObjectNode/result/subMachineOccupations\">    \n\n                    <!-- Assuming $parentValue contains the parent value obtained during iteration -->\n                    <xsl:variable name=\"parentValue\" select=\"./id\" />\n\n                    <!-- Use $parentValue to construct the XPath expression -->\n                    <xsl:variable name=\"manufacturingSetParameterTaskValue\" select=\"xs:double(/ObjectNode/additional/timeDistribution[id = $parentValue]/timeDurations/Manufacturing_SetParameterTask)\" />\n                    <xsl:variable name=\"manufacturingConfirmProductionTaskValue\" select=\"xs:double(/ObjectNode/additional/timeDistribution[id = $parentValue]/timeDurations/Manufacturing_ConfirmProductionTask)\" />\n\n                    <xsl:variable name=\"rawSeconds\" select=\"$manufacturingSetParameterTaskValue + $manufacturingConfirmProductionTaskValue\"/>\n                    <xsl:variable name=\"seconds\">\n                        <xsl:choose>\n                            <xsl:when test=\"string($rawSeconds) = \'\' or not(xs:double($rawSeconds))\">\n                                <!-- Set $seconds to 0 when empty or erroneous -->\n                                <xsl:value-of select=\"0\"/>\n                            </xsl:when>\n                            <xsl:otherwise>\n                                <!-- Otherwise, set $seconds to the parsed double value -->\n                                <xsl:value-of select=\"format-number(xs:double($rawSeconds), \'#.##\')\"/>\n                            </xsl:otherwise>\n                        </xsl:choose>\n                    </xsl:variable>\n                    <xsl:variable name=\"export\"> \n                        <xsl:call-template name=\"export_json\">                        \n                            <xsl:with-param name=\"ba\"><xsl:value-of select=\"substring-before(./externalId, \'_\')\" /></xsl:with-param>\n                            <xsl:with-param name=\"gutmge\"><xsl:value-of select=\"number(./totalProductionNumbers/acceptedMeasurement/amount)\" /></xsl:with-param>\n                            <xsl:with-param name=\"article\"><xsl:value-of select=\"concat(\'$,artikel=\', ./productionOrder/product/name)\" /></xsl:with-param>\n                            <xsl:with-param name=\"bzeit\"><xsl:value-of select=\"format-number(xs:dayTimeDuration(concat(\'PT\', $seconds, \'S\')) div xs:dayTimeDuration(\'PT1H\') , \'#.###,00\', \'d\')\" /></xsl:with-param>\n                            <xsl:with-param name=\"mzeit\"><xsl:value-of select=\"format-number(xs:dayTimeDuration(concat(\'PT\', $seconds, \'S\')) div xs:dayTimeDuration(\'PT1H\') , \'#.###,00\', \'d\')\" /></xsl:with-param>\n                            <xsl:with-param name=\"ma\"><xsl:value-of select=\"/ObjectNode/result/subProductionSteps[last()]/timeRecords[last()]/responseUser/externalId\" /></xsl:with-param>\n                            <xsl:with-param name=\"erfzeit\"><xsl:value-of select=\"format-dateTime(current-dateTime(),\'[D01].[M01].[Y0001]_[H01]:[m01]\')\" /></xsl:with-param>\n                            <xsl:with-param name=\"erfdat\"><xsl:value-of select=\"format-dateTime(current-dateTime(),\'[Y0001][M01][D01]\')\" /></xsl:with-param>\n                            <xsl:with-param name=\"bem\"><xsl:value-of select=\"concat(format-dateTime(current-dateTime(),\'[D01].[M01].[Y0001]\'), \' \', /ObjectNode/result/subProductionSteps[last()]/timeRecords[last()]/responseUser/externalId, \' \', /ObjectNode/result/subProductionSteps[last()]/timeRecords[last()]/responseUser/userName, \';BDE\')\" /></xsl:with-param>\n                        </xsl:call-template>\n                    </xsl:variable>\n\n                    <bookingEntry>\n                        <machineOccupation>\n                            <externalId>\n                                <xsl:value-of select=\"/ObjectNode/result/externalId\"/>\n                            </externalId>\n                        </machineOccupation>\n                        <subProductionStep>\n                            <externalId>\n                                <xsl:value-of select=\"/ObjectNode/result/subProductionSteps[last()]/externalId\"/>\n                            </externalId>\n                        </subProductionStep>\n                        <bookingState>NEW</bookingState>\n                        <xsl:if test=\"/ObjectNode/result/subProductionSteps[last()]/timeRecords[last()]/responseUser/externalId != \'\'\">\n                            <user>\n                                <externalId>\n                                    <xsl:value-of select=\"/ObjectNode/result/subProductionSteps[last()]/timeRecords[last()]/responseUser/externalId\"/>\n                                </externalId>\n                            </user>\n                        </xsl:if>                       \n                        <message>\n                            <xsl:copy-of select=\"$export\"/>\n                        </message>\n                    </bookingEntry>          \n                </xsl:for-each>\n            </xsl:when>\n            <xsl:when test=\"/ObjectNode/subMachineOccupations\">\n                <xsl:for-each\n                    select=\"/ObjectNode/subMachineOccupations\">    \n\n                    <!-- Assuming $parentValue contains the parent value obtained during iteration -->\n                    <xsl:variable name=\"parentValue\" select=\"./id\" />\n\n                    <!-- Use $parentValue to construct the XPath expression -->\n                    <xsl:variable name=\"manufacturingSetParameterTaskValue\" select=\"xs:double(/ObjectNode/additional/timeDistribution[id = $parentValue]/timeDurations/Manufacturing_SetParameterTask)\" />\n                    <xsl:variable name=\"manufacturingConfirmProductionTaskValue\" select=\"xs:double(/ObjectNode/additional/timeDistribution[id = $parentValue]/timeDurations/Manufacturing_ConfirmProductionTask)\" />\n\n                    <xsl:variable name=\"rawSeconds\" select=\"$manufacturingSetParameterTaskValue + $manufacturingConfirmProductionTaskValue\"/>\n                    <xsl:variable name=\"seconds\">\n                        <xsl:choose>\n                            <xsl:when test=\"string($rawSeconds) = \'\' or not(xs:double($rawSeconds))\">\n                                <!-- Set $seconds to 0 when empty or erroneous -->\n                                <xsl:value-of select=\"0\"/>\n                            </xsl:when>\n                            <xsl:otherwise>\n                                <!-- Otherwise, set $seconds to the parsed double value -->\n                                <xsl:value-of select=\"format-number(xs:double($rawSeconds), \'#.##\')\"/>\n                            </xsl:otherwise>\n                        </xsl:choose>\n                    </xsl:variable>\n                    <xsl:variable name=\"export\"> \n                        <xsl:call-template name=\"export_json\">                        \n                            <xsl:with-param name=\"ba\"><xsl:value-of select=\"substring-before(./externalId, \'_\')\" /></xsl:with-param>\n                            <xsl:with-param name=\"gutmge\"><xsl:value-of select=\"number(./totalProductionNumbers/acceptedMeasurement/amount)\" /></xsl:with-param>\n                            <xsl:with-param name=\"article\"><xsl:value-of select=\"concat(\'$,artikel=\', ./productionOrder/product/name)\" /></xsl:with-param>\n                            <xsl:with-param name=\"bzeit\"><xsl:value-of select=\"format-number(xs:dayTimeDuration(concat(\'PT\', $seconds, \'S\')) div xs:dayTimeDuration(\'PT1H\') , \'#.###,00\', \'d\')\" /></xsl:with-param>\n                            <xsl:with-param name=\"mzeit\"><xsl:value-of select=\"format-number(xs:dayTimeDuration(concat(\'PT\', $seconds, \'S\')) div xs:dayTimeDuration(\'PT1H\') , \'#.###,00\', \'d\')\" /></xsl:with-param>\n                            <xsl:with-param name=\"ma\"><xsl:value-of select=\"/ObjectNode/subProductionSteps[last()]/timeRecords[last()]/responseUser/externalId\" /></xsl:with-param>\n                            <xsl:with-param name=\"erfzeit\"><xsl:value-of select=\"format-dateTime(current-dateTime(),\'[D01].[M01].[Y0001]_[H01]:[m01]\')\" /></xsl:with-param>\n                            <xsl:with-param name=\"erfdat\"><xsl:value-of select=\"format-dateTime(current-dateTime(),\'[Y0001][M01][D01]\')\" /></xsl:with-param>\n                            <xsl:with-param name=\"bem\"><xsl:value-of select=\"concat(format-dateTime(current-dateTime(),\'[D01].[M01].[Y0001]\'), \' \', /ObjectNode/subProductionSteps[last()]/timeRecords[last()]/responseUser/externalId, \' \', /ObjectNode/result/subProductionSteps[last()]/timeRecords[last()]/responseUser/userName, \';BDE\')\" /></xsl:with-param>\n                        </xsl:call-template>\n                    </xsl:variable>\n\n                    <bookingEntry>\n                        <machineOccupation>\n                            <externalId>\n                                <xsl:value-of select=\"/ObjectNode/externalId\"/>\n                            </externalId>\n                        </machineOccupation>\n                        <subProductionStep>\n                            <externalId>\n                                <xsl:value-of select=\"/ObjectNode/subProductionSteps[last()]/externalId\"/>\n                            </externalId>\n                        </subProductionStep>\n                        <bookingState>NEW</bookingState>\n                        <xsl:if test=\"/ObjectNode/subProductionSteps[last()]/timeRecords[last()]/responseUser/externalId != \'\'\">\n                            <user>\n                                <externalId>\n                                    <xsl:value-of select=\"/ObjectNode/subProductionSteps[last()]/timeRecords[last()]/responseUser/externalId\"/>\n                                </externalId>\n                            </user>\n                        </xsl:if>                       \n                        <message>\n                            <xsl:copy-of select=\"$export\"/>\n                        </message>\n                    </bookingEntry>          \n                </xsl:for-each>\n            </xsl:when>\n            <xsl:when test=\"/ObjectNode/result\">\n\n                <xsl:variable name=\"rawSeconds\" select=\"xs:double(/ObjectNode/result/timeDurations/work)\"/>\n                <xsl:variable name=\"seconds\">\n                    <xsl:choose>\n                        <xsl:when test=\"string($rawSeconds) = \'\' or not(xs:double($rawSeconds))\">\n                            <!-- Set $seconds to 0 when empty or erroneous -->\n                            <xsl:value-of select=\"0\"/>\n                        </xsl:when>\n                        <xsl:otherwise>\n                            <!-- Otherwise, set $seconds to the parsed double value -->\n                            <xsl:value-of select=\"format-number(xs:double($rawSeconds), \'#.##\')\"/>\n                        </xsl:otherwise>\n                    </xsl:choose>\n                </xsl:variable>\n\n                <bookingEntry>\n                    <machineOccupation>\n                        <externalId>\n                            <xsl:value-of select=\"/ObjectNode/result/externalId\"/>\n                        </externalId>\n                    </machineOccupation>\n                    <subProductionStep>\n                        <externalId>\n                            <xsl:value-of select=\"/ObjectNode/result/subProductionSteps[last()]/externalId\"/>\n                        </externalId>\n                    </subProductionStep>\n                    <bookingState>NEW</bookingState>\n                    <xsl:if test=\"/ObjectNode/result/subProductionSteps[last()]/timeRecords[last()]/responseUser/externalId != \'\'\">\n                        <user>\n                            <externalId>\n                                <xsl:value-of select=\"/ObjectNode/result/subProductionSteps[last()]/timeRecords[last()]/responseUser/externalId\"/>\n                            </externalId>\n                        </user>\n                    </xsl:if>\n                    <message>                \n                        <xsl:call-template name=\"export_json\">\n                                <xsl:with-param name=\"ba\"><xsl:value-of select=\"concat(/ObjectNode/result/productionOrder/externalId, /ObjectNode/result/name)\" /></xsl:with-param>\n                                <xsl:with-param name=\"gutmge\"><xsl:value-of select=\"number(/ObjectNode/result/totalProductionNumbers/acceptedMeasurement/amount)\" /></xsl:with-param>\n                                <xsl:with-param name=\"article\"><xsl:value-of select=\"concat(\'$,artikel=\', /ObjectNode/result/productionOrder/product/name)\" /></xsl:with-param>\n                                <xsl:with-param name=\"bzeit\"><xsl:value-of select=\"format-number(xs:dayTimeDuration(concat(\'PT\', $seconds, \'S\')) div xs:dayTimeDuration(\'PT1H\') , \'#.###,00\', \'d\')\" /></xsl:with-param>\n                                <xsl:with-param name=\"mzeit\"><xsl:value-of select=\"format-number(xs:dayTimeDuration(concat(\'PT\', $seconds, \'S\')) div xs:dayTimeDuration(\'PT1H\') , \'#.###,00\', \'d\')\" /></xsl:with-param>\n                                <xsl:with-param name=\"ma\"><xsl:value-of select=\"/ObjectNode/result/subProductionSteps[last()]/timeRecords[last()]/responseUser/externalId\" /></xsl:with-param>\n                                <xsl:with-param name=\"erfzeit\"><xsl:value-of select=\"format-dateTime(current-dateTime(),\'[D01].[M01].[Y0001]_[H01]:[m01]\')\" /></xsl:with-param>\n                                <xsl:with-param name=\"erfdat\"><xsl:value-of select=\"format-dateTime(current-dateTime(),\'[Y0001][M01][D01]\')\" /></xsl:with-param>\n                                <xsl:with-param name=\"bem\"><xsl:value-of select=\"concat(format-dateTime(current-dateTime(),\'[D01].[M01].[Y0001]\'), \' \', /ObjectNode/result/subProductionSteps[last()]/timeRecords[last()]/responseUser/externalId, \' \', /ObjectNode/result/subProductionSteps[last()]/timeRecords[last()]/responseUser/userName, \';BDE\')\" /></xsl:with-param>\n                        </xsl:call-template> \n                    </message>\n                </bookingEntry>     \n            </xsl:when>\n            <xsl:otherwise>\n                \n                <xsl:variable name=\"rawSeconds\" select=\"xs:double(/ObjectNode/timeDurations/work)\"/>\n                <xsl:variable name=\"seconds\">\n                    <xsl:choose>\n                        <xsl:when test=\"string($rawSeconds) = \'\' or not(xs:double($rawSeconds))\">\n                            <!-- Set $seconds to 0 when empty or erroneous -->\n                            <xsl:value-of select=\"0\"/>\n                        </xsl:when>\n                        <xsl:otherwise>\n                            <!-- Otherwise, set $seconds to the parsed double value -->\n                            <xsl:value-of select=\"format-number(xs:double($rawSeconds), \'#.##\')\"/>\n                        </xsl:otherwise>\n                    </xsl:choose>\n                </xsl:variable>\n\n                <bookingEntry>\n                    <machineOccupation>\n                        <externalId>\n                            <xsl:value-of select=\"/ObjectNode/externalId\"/>\n                        </externalId>\n                    </machineOccupation>                    \n                    <subProductionStep>\n                        <externalId>\n                            <xsl:value-of select=\"/ObjectNode/subProductionSteps[last()]/externalId\"/>\n                        </externalId>\n                    </subProductionStep>\n                    <bookingState>NEW</bookingState>\n                    <xsl:if test=\"/ObjectNode/subProductionSteps[last()]/timeRecords[last()]/responseUser/externalId != \'\'\">\n                        <user>\n                            <externalId>\n                                <xsl:value-of select=\"/ObjectNode/subProductionSteps[last()]/timeRecords[last()]/responseUser/externalId\"/>\n                            </externalId>\n                        </user>\n                    </xsl:if>\n                    <message>        \n                        <xsl:call-template name=\"export_json\">\n                            <xsl:with-param name=\"ba\"><xsl:value-of select=\"concat(/ObjectNode/productionOrder/externalId, /ObjectNode/name)\" /></xsl:with-param>\n                            <xsl:with-param name=\"gutmge\"><xsl:value-of select=\"number(/ObjectNode/totalProductionNumbers/acceptedMeasurement/amount)\" /></xsl:with-param>\n                            <xsl:with-param name=\"article\"><xsl:value-of select=\"concat(\'$,artikel=\', /ObjectNode/productionOrder/product/name)\" /></xsl:with-param>\n                            <xsl:with-param name=\"bzeit\"><xsl:value-of select=\"format-number(xs:dayTimeDuration(concat(\'PT\', $seconds, \'S\')) div xs:dayTimeDuration(\'PT1H\') , \'#.###,00\', \'d\')\" /></xsl:with-param>\n                            <xsl:with-param name=\"mzeit\"><xsl:value-of select=\"format-number(xs:dayTimeDuration(concat(\'PT\', $seconds, \'S\')) div xs:dayTimeDuration(\'PT1H\') , \'#.###,00\', \'d\')\" /></xsl:with-param>\n                            <xsl:with-param name=\"ma\"><xsl:value-of select=\"/ObjectNode/subProductionSteps[last()]/timeRecords[last()]/responseUser/externalId\" /></xsl:with-param>\n                            <xsl:with-param name=\"erfzeit\"><xsl:value-of select=\"format-dateTime(current-dateTime(),\'[D01].[M01].[Y0001]_[H01]:[m01]\')\" /></xsl:with-param>\n                            <xsl:with-param name=\"erfdat\"><xsl:value-of select=\"format-dateTime(current-dateTime(),\'[Y0001][M01][D01]\')\" /></xsl:with-param>\n                            <xsl:with-param name=\"bem\"><xsl:value-of select=\"concat(format-dateTime(current-dateTime(),\'[D01].[M01].[Y0001]\'), \' \', /ObjectNode/subProductionSteps[last()]/timeRecords[last()]/responseUser/externalId, \' \', /ObjectNode/subProductionSteps[last()]/timeRecords[last()]/responseUser/userName, \';BDE\')\" /></xsl:with-param>\n                        </xsl:call-template>   \n                    </message>\n                </bookingEntry>                                \n            </xsl:otherwise>\n        </xsl:choose>\n    </output>\n  </xsl:template>\n\n\n  <xsl:template name=\"export_json\">\n        <xsl:param name=\"ba\" />\n        <xsl:param name=\"gutmge\" />\n        <xsl:param name=\"article\" />\n        <xsl:param name=\"bzeit\" />\n        <xsl:param name=\"mzeit\" />\n        <xsl:param name=\"ma\" />\n        <xsl:param name=\"erfzeit\" />\n        <xsl:param name=\"erfdat\" />\n        <xsl:param name=\"bem\" />\n        <EXPORT_JSON>\n        <initAction>\n                <_type>OpenEditor</_type>\n                <tableName>9:2</tableName>\n                <editAction>DONE</editAction>\n            </initAction>\n            <actions>\n                <_type>SetFieldValue</_type>\n                <fieldName>barmex</fieldName>\n                <value>\n                    <xsl:value-of select=\"$ba\" />\n                </value>\n            </actions>\n            <actions>\n                <_type>SetFieldValue</_type>\n                <fieldName>gutmge</fieldName>\n                <value>            \n                    <xsl:value-of select=\"$gutmge\" />\n                </value>\n                <rowSpec>\n                    <xsl:value-of select=\"$article\" />\n                </rowSpec>\n            </actions>\n            <actions>\n                <_type>SetFieldValue</_type>\n                <fieldName>bzeit</fieldName>\n                <value>\n                    <xsl:value-of select=\"$bzeit\" />\n                </value>\n            </actions>\n            <actions>\n                <_type>SetFieldValue</_type>\n                <fieldName>mzeit</fieldName>\n                <value>\n                    <xsl:value-of select=\"$mzeit\" />\n                </value>\n            </actions>\n            <actions>\n                <_type>SetFieldValue</_type>\n                <fieldName>ma</fieldName>\n                <value>\n                    <xsl:value-of select=\"$ma\" />\n                </value>\n            </actions>\n            <actions>\n                <_type>SetFieldValue</_type>\n                <fieldName>rzbuchen</fieldName>\n                <value>true</value>\n            </actions>\n            <actions>\n                <_type>SetFieldValue</_type>\n                <fieldName>yerfzeit</fieldName>\n                <value>\n                <xsl:value-of select=\"$erfzeit\"/>\n                </value>\n            </actions>\n            <actions>\n                <_type>SetFieldValue</_type>\n                <fieldName>yerfdat</fieldName>\n                <value>\n                    <xsl:value-of select=\"$erfdat\"/>\n                </value>\n            </actions>\n            <actions>\n                <_type>SetFieldValue</_type>\n                <fieldName>bem</fieldName>\n                <value>\n                    <xsl:value-of select=\"$bem\" />\n                </value>\n            </actions>\n        </EXPORT_JSON>\n    </xsl:template>\n</xsl:stylesheet>'),
(69, NULL, NULL, '<xsl:stylesheet version=\"2.0\"\n  xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"\n  xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n  <xsl:decimal-format name=\"d\" decimal-separator=\",\" grouping-separator=\".\" />\n  <xsl:template match=\"/\">\n    <output>\n      <xsl:for-each select=\"/ObjectNode/result\">\n        <xsl:variable name=\"product\" select=\"./productionOrder/product/externalId\"/>\n        <xsl:if test=\"./department/externalId != \'\'\">\n          <cD_Department>\n            <externalId>\n              <xsl:value-of select=\"./department/externalId\" />\n            </externalId>\n            <versionNr>\n              <xsl:value-of select=\"./department/versionNr\" />\n            </versionNr>\n            <name>\n              <xsl:value-of select=\"./department/name\" />\n            </name>\n          </cD_Department>\n        </xsl:if>\n\n        <xsl:for-each\n          select=\"./machine/machineType/departments\">\n\n          <xsl:if test=\"./externalId != \'\'\">\n            <cD_Department>\n              <externalId>\n                <xsl:value-of select=\"./externalId\" />\n              </externalId>\n              <versionNr>\n                <xsl:value-of select=\"./versionNr\" />\n              </versionNr>\n              <name>\n                <xsl:value-of select=\"./name\" />\n              </name>\n            </cD_Department>\n          </xsl:if>\n        </xsl:for-each>\n        \n        <xsl:if\n          test=\"./machine/machineType/externalId != \'\'\">\n          <cD_MachineType>\n            <externalId>\n              <xsl:value-of select=\"./machine/machineType/externalId\" />\n            </externalId>\n            <versionNr>\n              <xsl:value-of select=\"./machine/machineType/versionNr\" />\n            </versionNr>\n            <name>\n              <xsl:value-of select=\"./machine/machineType/name\" />\n            </name>\n            <xsl:for-each select=\"./machine/machineType/departments\">\n              <departments>\n                <externalId>\n                  <xsl:value-of select=\"./externalId\" />\n                </externalId>\n              </departments>\n            </xsl:for-each>\n          </cD_MachineType>\n        </xsl:if>\n        \n        <xsl:if\n          test=\"./machine/externalId != \'\'\">\n          <cD_Machine>\n            <externalId>\n              <xsl:value-of select=\"./machine/externalId\" />\n            </externalId>\n            <versionNr>\n              <xsl:value-of select=\"./machine/versionNr\" />\n            </versionNr>\n            <machineType>\n              <externalId>\n                <xsl:value-of select=\"./machine/machineType/externalId\" />\n              </externalId>\n            </machineType>\n            <name>\n              <xsl:value-of select=\"./machine/name\" />\n            </name>\n          </cD_Machine>\n        </xsl:if>\n\n        \n        <xsl:if\n          test=\"./tool/toolType/externalId != \'\'\">\n          <cD_ToolType>\n            <externalId>\n              <xsl:value-of select=\"./tool/toolType/externalId\" />\n            </externalId>\n            <versionNr>\n              <xsl:value-of select=\"./tool/toolType/versionNr\" />\n            </versionNr>\n            <number>\n              <xsl:value-of select=\"./tool/toolType/number\" />\n            </number>\n            <name>\n              <xsl:value-of select=\"./tool/toolType/name\" />\n            </name>\n          </cD_ToolType>\n        </xsl:if>\n            \n        <xsl:if\n          test=\"./tool/externalId != \'\'\">\n          <cD_Tool>\n            <externalId>\n              <xsl:value-of select=\"./tool/externalId\" />\n            </externalId>\n            <versionNr>\n              <xsl:value-of select=\"./tool/versionNr\" />\n            </versionNr>\n            <name>\n              <xsl:value-of select=\"./tool/name\" />\n            </name>\n            <toolType>\n              <externalId>\n                <xsl:value-of select=\"./tool/toolType/externalId\" />\n              </externalId>\n            </toolType>\n          </cD_Tool>\n        </xsl:if>\n   \n   <xsl:if\n          test=\"./productionOrder/product/externalId != \'\'\">\n          <cD_Product>\n            <externalId>\n              <xsl:value-of select=\"./productionOrder/product/externalId\" />\n            </externalId>\n            <versionNr>\n              <xsl:value-of select=\"./productionOrder/product/versionNr\" />\n            </versionNr>\n            <name>\n              <xsl:value-of select=\"./productionOrder/product/name\" />\n            </name>\n            <unitType>\n              <xsl:value-of select=\"./productionOrder/product/unitType\" />\n            </unitType>\n          </cD_Product>\n        </xsl:if>\n\n<xsl:if\n          test=\"./productionOrder/customerOrder/externalId != \'\'\">\n          <customerOrder>\n            <externalId>\n              <xsl:value-of select=\"./productionOrder/customerOrder/externalId\" />\n            </externalId>\n            <versionNr>\n              <xsl:value-of select=\"./productionOrder/customerOrder/versionNr\" />\n            </versionNr>\n            <name>\n              <xsl:value-of select=\"./productionOrder/customerOrder/name\" />\n            </name>\n            <customerName>\n              <xsl:value-of select=\"./productionOrder/customerOrder/customerName\" />\n            </customerName>\n            <priority>\n              <xsl:value-of select=\"./productionOrder/customerOrder/priority\" />\n            </priority>\n            <deadline>\n              <xsl:value-of select=\"./productionOrder/customerOrder/deadline\" />\n            </deadline>\n          </customerOrder>\n        </xsl:if>\n\n<xsl:if\n          test=\"./productionOrder/externalId != \'\'\">\n          <productionOrder>\n            <externalId>\n              <xsl:value-of select=\"./productionOrder/externalId\" />\n            </externalId>\n            <versionNr>\n              <xsl:value-of select=\"./productionOrder/versionNr\" />\n            </versionNr>\n            <measurement>\n              <amount>\n                <xsl:value-of select=\"./productionOrder/measurement/amount\" />\n              </amount>\n              <unitString>\n                <xsl:value-of select=\"./productionOrder/measurement/unitString\" />\n              </unitString>\n            </measurement>\n            <customerOrder>\n              <externalId>\n                <xsl:value-of select=\"./productionOrder/customerOrder/externalId\" />\n              </externalId>\n            </customerOrder>\n            <product>\n              <externalId>\n                <xsl:value-of select=\"./productionOrder/product/externalId\" />\n              </externalId>\n            </product>\n            <name>\n              <xsl:value-of select=\"./productionOrder/name\" />\n            </name>\n            <priority>\n              <xsl:value-of select=\"./productionOrder/priority\" />\n            </priority>\n            <deadline>              \n              <xsl:if test=\"substring-before(./productionOrder/deadline, \'.\') != \'\'\">\n                <xsl:value-of select=\"substring-before(./productionOrder/deadline, \'.\')\" />\n              </xsl:if>\n              <xsl:if test=\"substring-before(./productionOrder/deadline, \'.\') = \'\'\">\n                <xsl:value-of select=\"./productionOrder/deadline\" />\n              </xsl:if>\n            </deadline>\n            <status>\n              <xsl:value-of select=\"./productionOrder/status\" />\n            </status>\n            <keyValueMap>\n              <xsl:value-of select=\"./productionOrder/keyValueMap\" />\n            </keyValueMap>\n          </productionOrder>\n        </xsl:if>\n\n        <xsl:for-each\n          select=\"./productionSteps\">\n          <xsl:for-each select=\"./machineType/departments\">\n\n            <xsl:if test=\"./externalId != \'\'\">\n              <cD_Department>\n                <externalId>\n                  <xsl:value-of select=\"./externalId\" />\n                </externalId>\n                <versionNr>\n                  <xsl:value-of select=\"./versionNr\" />\n                </versionNr>\n                <name>\n                  <xsl:value-of select=\"./name\" />\n                </name>\n              </cD_Department>\n            </xsl:if>\n          </xsl:for-each>\n\n          <xsl:if\n            test=\"./machineType/externalId != \'\'\">\n            <cD_MachineType>\n              <externalId>\n                <xsl:value-of select=\"./machineType/externalId\" />\n              </externalId>\n              <versionNr>\n                <xsl:value-of select=\"./machineType/versionNr\" />\n              </versionNr>\n              <name>\n                <xsl:value-of select=\"./machineType/name\" />\n              </name>\n              <xsl:for-each select=\"./machineType/departments\">\n                <departments>\n                  <externalId>\n                    <xsl:value-of select=\"./externalId\" />\n                  </externalId>\n                </departments>\n              </xsl:for-each>\n            </cD_MachineType>\n          </xsl:if>\n\n          <xsl:if\n            test=\"./productionStepInfo/externalId != \'\'\">\n            <productionStepInfo>\n              <externalId>\n                <xsl:value-of select=\"./productionStepInfo/externalId\" />\n              </externalId>\n              <versionNr>\n                <xsl:value-of select=\"./productionStepInfo/versionNr\" />\n              </versionNr>\n              <stepGroup>\n                <xsl:value-of select=\"./productionStepInfo/stepGroup\" />\n              </stepGroup>\n              <stepIdent>\n                <xsl:value-of select=\"./productionStepInfo/stepIdent\" />\n              </stepIdent>\n              <stepType>\n                <xsl:value-of select=\"./productionStepInfo/stepType\" />\n              </stepType>\n              <stepNr>\n                <xsl:value-of select=\"./productionStepInfo/stepNr\" />\n              </stepNr>\n            </productionStepInfo>\n          </xsl:if>\n\n          <xsl:if\n            test=\"./externalId != \'\'\">\n            <cD_ProductionStep>\n              <externalId>\n                <xsl:value-of select=\"./externalId\" />\n              </externalId>\n              <versionNr>\n                <xsl:value-of select=\"./versionNr\" />\n              </versionNr>\n              <camundaProcessName>\n                <xsl:value-of select=\"./camundaProcessName\" />\n              </camundaProcessName>\n              <machineType>\n                <externalId>\n                  <xsl:value-of select=\"./machineType/externalId\" />\n                </externalId>\n              </machineType>\n              <toolType>\n                <externalId>\n                  <xsl:value-of select=\"./toolType/externalId\" />\n                </externalId>\n              </toolType>\n              <product>\n                <externalId>\n                  <xsl:value-of select=\"./product/externalId\" />\n                </externalId>\n              </product>\n              <name>\n                <xsl:value-of select=\"./name\" />\n              </name>\n              <productionDuration>\n                <xsl:value-of select=\"./productionDuration\" />\n              </productionDuration>\n              <setupTime>\n                <xsl:value-of select=\"./setupTime\" />\n              </setupTime>\n              <sequence>\n                <xsl:value-of select=\"./sequence\" />\n              </sequence>\n              <productionStepInfo>\n                <externalId>\n                  <xsl:value-of select=\"./productionStepInfo/externalId\" />\n                </externalId>\n              </productionStepInfo>\n            </cD_ProductionStep>\n          </xsl:if>\n        </xsl:for-each>\n\n        <xsl:if\n          test=\"./productionOrder/externalId != \'\'\">          \n          <xsl:variable name=\"prodStep\" select=\"./productionSteps[1]\"/>\n          <machineOccupation>\n            <externalId>\n              <xsl:value-of select=\"$prodStep/externalId\" />\n            </externalId>\n            <versionNr>\n              <xsl:value-of select=\"./versionNr\" />\n            </versionNr>\n\n            <xsl:for-each select=\"./productionSteps\">\n              <productionSteps>\n                <externalId>\n                  <xsl:value-of select=\"./externalId\" />\n                </externalId>\n              </productionSteps>\n            </xsl:for-each>\n            <machine>\n              <externalId>\n                <xsl:value-of select=\"./machine/externalId\" />\n              </externalId>\n            </machine>\n            <tool>\n              <externalId>\n                <xsl:value-of select=\"./tool/externalId\" />\n              </externalId>\n            </tool>\n            <productionOrder>\n              <externalId>\n                <xsl:value-of select=\"./productionOrder/externalId\" />\n              </externalId>\n            </productionOrder>\n            <plannedStartDateTime>\n              <xsl:if test=\"substring-before(./plannedStartDateTime, \'.\') != \'\'\">\n                <xsl:value-of select=\"substring-before(./plannedStartDateTime, \'.\')\" />\n              </xsl:if>\n              <xsl:if test=\"substring-before(./plannedStartDateTime, \'.\') = \'\'\">\n                <xsl:value-of select=\"./plannedStartDateTime\" />\n              </xsl:if>\n            </plannedStartDateTime>\n            <plannedEndDateTime>\n              <xsl:if test=\"substring-before(./plannedEndDateTime, \'.\') != \'\'\">\n                <xsl:value-of select=\"substring-before(./plannedEndDateTime, \'.\')\" />\n              </xsl:if>\n              <xsl:if test=\"substring-before(./plannedEndDateTime, \'.\') = \'\'\">\n                <xsl:value-of select=\"./plannedEndDateTime\" />\n              </xsl:if>\n            </plannedEndDateTime>\n            <actualStartDateTime>\n              <xsl:if test=\"substring-before(./actualStartDateTime, \'.\') != \'\'\">\n                <xsl:value-of select=\"substring-before(./actualStartDateTime, \'.\')\" />\n              </xsl:if>\n              <xsl:if test=\"substring-before(./actualStartDateTime, \'.\') = \'\'\">\n                <xsl:value-of select=\"./actualStartDateTime\" />\n              </xsl:if>\n            </actualStartDateTime>\n            <actualEndDateTime>\n              <xsl:if test=\"substring-before(./actualEndDateTime, \'.\') != \'\'\">\n                <xsl:value-of select=\"substring-before(./actualEndDateTime, \'.\')\" />\n              </xsl:if>\n              <xsl:if test=\"substring-before(./actualEndDateTime, \'.\') = \'\'\">\n                <xsl:value-of select=\"./actualEndDateTime\" />\n              </xsl:if>\n            </actualEndDateTime>\n            <xsl:choose>\n                <xsl:when\n                    test=\"$prodStep/machineType/name = \'Spritzgießen A\' or $prodStep/machineType/name = \'Spritzgießen B\' or $prodStep/machineType/name = \'Wickeln\' or $prodStep/machineType/name = \'Tiefziehen\'\">\n                    <camundaProcessName>Process_Basis</camundaProcessName>\n                </xsl:when>\n                <xsl:when \n                    test=\"$prodStep/machineType/name = \'Handmontage\' or $prodStep/machineType/name = \'Endmontage\'\">\n                    <camundaProcessName>Process_Montage</camundaProcessName>\n                </xsl:when>\n                <xsl:when \n                    test=\"$prodStep/machineType/name = \'QS Prüfung\'\">\n                    <camundaProcessName>Process_Check</camundaProcessName>\n                </xsl:when>\n                <xsl:when \n                    test=\"$prodStep/machineType/name = \'Verpackung\'\">\n                    <camundaProcessName>Process_Verpackung</camundaProcessName>\n                </xsl:when>\n                <xsl:when \n                    test=\"$prodStep/machineType/name = \'Versand\'\">\n                    <camundaProcessName>Process_Versand</camundaProcessName>\n                </xsl:when>\n                <xsl:otherwise>\n                    <camundaProcessName>Process_Basis</camundaProcessName>\n                </xsl:otherwise>\n            </xsl:choose>\n            <name>\n              <xsl:value-of select=\"$prodStep/sequence\" />\n            </name>\n            <status>READY_TO_START</status>\n            <timeDurations>\n              <xsl:value-of select=\"./timeDurations\" />\n            </timeDurations>\n            <department>\n              <externalId>\n                <xsl:value-of select=\"$prodStep/machineType/departments[1]/externalId\" />\n              </externalId>\n            </department>\n          </machineOccupation>\n        </xsl:if>\n      </xsl:for-each>\n    </output>\n  </xsl:template>\n</xsl:stylesheet>'),
(73, NULL, NULL, '<xsl:stylesheet version=\"2.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n  <xsl:template match=\"/\">\n    <output>\n        <subProductionStep>\n          <note>\n            <xsl:value-of select=\"ObjectNode/ff_Note_1_1\" />\n          </note>\n        </subProductionStep>\n    </output>\n  </xsl:template>\n</xsl:stylesheet>'),
(77, NULL, NULL, '<xsl:stylesheet version=\"2.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n  <xsl:template match=\"/\">\n    <output>\n        <subProductionStep>\n          <productionNumbers>\n            <acceptedMeasurement>\n              <amount>\n                  <xsl:value-of select=\"ObjectNode/ff_PartsOK\" />\n              </amount>\n            </acceptedMeasurement>\n            <rejectedMeasurement>\n              <amount>\n                  <xsl:value-of select=\"ObjectNode/ff_PartsNOK\" />\n              </amount>\n            </rejectedMeasurement>\n          </productionNumbers>\n        </subProductionStep>\n    </output>\n  </xsl:template>\n</xsl:stylesheet>'),
(81, NULL, NULL, '<xsl:stylesheet version=\"2.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n  <xsl:template match=\"/\">\n    <output>\n        <subProductionStep>\n          <productionNumbers>\n            <acceptedMeasurement>\n              <amount>\n                  <xsl:value-of select=\"ObjectNode/ff_PartsOK\" />\n              </amount>\n            </acceptedMeasurement>\n            <rejectedMeasurement>\n              <amount>\n                  <xsl:value-of select=\"ObjectNode/ff_PartsNOK\" />\n              </amount>\n            </rejectedMeasurement>\n          </productionNumbers>\n        </subProductionStep>\n    </output>\n  </xsl:template>\n</xsl:stylesheet>'),
(85, NULL, NULL, '<xsl:stylesheet version=\"2.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n  <xsl:template match=\"/\">\n    <output>\n        <subProductionStep>\n          <productionNumbers>\n            <acceptedMeasurement>\n              <amount>\n                  <xsl:value-of select=\"ObjectNode/ff_PartsOK\" />\n              </amount>\n            </acceptedMeasurement>\n            <rejectedMeasurement>\n              <amount>\n                  <xsl:value-of select=\"ObjectNode/ff_PartsNOK\" />\n              </amount>\n            </rejectedMeasurement>\n          </productionNumbers>\n        </subProductionStep>\n    </output>\n  </xsl:template>\n</xsl:stylesheet>'),
(89, NULL, NULL, '<xsl:stylesheet version=\"2.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n  <xsl:template match=\"/\">\n    <output>\n        <subProductionStep>\n          <productionNumbers>\n            <acceptedMeasurement>\n              <amount>\n                  <xsl:value-of select=\"ObjectNode/ff_PartsOK\" />\n              </amount>\n            </acceptedMeasurement>\n            <rejectedMeasurement>\n              <amount>\n                  <xsl:value-of select=\"ObjectNode/ff_PartsNOK\" />\n              </amount>\n            </rejectedMeasurement>\n          </productionNumbers>\n        </subProductionStep>\n    </output>\n  </xsl:template>\n</xsl:stylesheet>');
INSERT INTO `MappingConfiguration` (`id`, `inputSelector`, `outputSelector`, `xsltRules`) VALUES
(93, NULL, NULL, '<xsl:stylesheet version=\"2.0\"\n    xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"\n    xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n  <xsl:decimal-format name=\"d\" decimal-separator=\",\" grouping-separator=\".\"/>\n  <xsl:template match=\"/\">\n    <output>\n\n        <xsl:variable name=\"export\"> \n            <EXPORT_JSON>\n                <initAction>\n                    <_type>OpenEditor</_type>\n                    <tableName>9:2</tableName>\n                    <editAction>DONE</editAction>\n                </initAction>\n                <actions>\n                    <_type>SetFieldValue</_type>\n                    <fieldName>barmex</fieldName>\n                    <value>\n                        <xsl:value-of select=\"concat(/ObjectNode/result/productionOrder/externalId, /ObjectNode/result/name)\" />\n                    </value>\n                </actions>\n                <actions>\n                    <_type>SetFieldValue</_type>\n                    <fieldName>gutmge</fieldName>\n                    <value>            \n                        <xsl:value-of select=\"number(/ObjectNode/result/subProductionSteps[id = /ObjectNode/additional]/productionNumbers[last()]/acceptedMeasurement/amount)\" />\n                    </value>\n                    <rowSpec>\n                        <xsl:value-of select=\"concat(\'$,artikel=\', /ObjectNode/result/productionOrder/product/name)\" />\n                    </rowSpec>\n                </actions>\n                <actions>\n                    <_type>SetFieldValue</_type>\n                    <fieldName>bzeit</fieldName>\n                    <value>\n                        <!--<xsl:value-of select=\"format-number((xs:dateTime(/ObjectNode/result/subProductionSteps[id = /ObjectNode/additional]/timeRecords[last()]/endDateTime) - xs:dateTime(/ObjectNode/result/subProductionSteps[id = /ObjectNode/additional]/timeRecords[1]/startDateTime)) div xs:dayTimeDuration(\'PT1H\') , \'#.###,00\', \'d\')\" />-->\n                        <xsl:variable name=\"rawSeconds\" select=\"xs:double(/ObjectNode/result/subProductionSteps[id = /ObjectNode/additional]/timeDurations/work)\"/>\n                        <xsl:variable name=\"work_seconds\">\n                            <xsl:choose>\n                                    <xsl:when test=\"string($rawSeconds) = \'\' or not(xs:double($rawSeconds))\">\n                                        <!-- Set $seconds to 0 when empty or erroneous -->\n                                        <xsl:value-of select=\"0\"/>\n                                    </xsl:when>\n                                    <xsl:otherwise>\n                                        <!-- Otherwise, set $seconds to the parsed double value -->\n                                        <xsl:value-of select=\"format-number($rawSeconds, \'#.##\')\"/>\n                                    </xsl:otherwise>\n                                </xsl:choose> \n                        </xsl:variable>\n                        \n                        <xsl:value-of select=\"format-number(xs:dayTimeDuration(concat(\'PT\', $work_seconds, \'S\')) div xs:dayTimeDuration(\'PT1H\') , \'#.###,00\', \'d\')\" />\n                    </value>\n                </actions>\n                <actions>\n                    <_type>SetFieldValue</_type>\n                    <fieldName>mzeit</fieldName>\n                    <value>\n                        <!--<xsl:value-of select=\"format-number((xs:dateTime(/ObjectNode/result/subProductionSteps[id = /ObjectNode/additional]/timeRecords[last()]/endDateTime) - xs:dateTime(/ObjectNode/result/subProductionSteps[id = /ObjectNode/additional]/timeRecords[1]/startDateTime)) div xs:dayTimeDuration(\'PT1H\') , \'#.###,00\', \'d\')\" />-->\n                        <xsl:variable name=\"rawSeconds\" select=\"xs:double(/ObjectNode/result/subProductionSteps[id = /ObjectNode/additional]/timeDurations/work)\"/>\n                        <xsl:variable name=\"work_seconds\">\n                            <xsl:choose>\n                                    <xsl:when test=\"string($rawSeconds) = \'\' or not(xs:double($rawSeconds))\">\n                                        <!-- Set $seconds to 0 when empty or erroneous -->\n                                        <xsl:value-of select=\"0\"/>\n                                    </xsl:when>\n                                    <xsl:otherwise>\n                                        <!-- Otherwise, set $seconds to the parsed double value -->\n                                        <xsl:value-of select=\"format-number($rawSeconds, \'#.##\')\"/>\n                                    </xsl:otherwise>\n                                </xsl:choose> \n                        </xsl:variable>\n                        \n                        <xsl:value-of select=\"format-number(xs:dayTimeDuration(concat(\'PT\', $work_seconds, \'S\')) div xs:dayTimeDuration(\'PT1H\') , \'#.###,00\', \'d\')\" />\n                    </value>\n                </actions>\n                <actions>\n                    <_type>SetFieldValue</_type>\n                    <fieldName>ma</fieldName>\n                    <value>\n                        <xsl:value-of select=\"/ObjectNode/result/subProductionSteps[id = /ObjectNode/additional]/timeRecords[last()]/responseUser/externalId\" />\n                    </value>\n                </actions>\n                <actions>\n                    <_type>SetFieldValue</_type>\n                    <fieldName>rzbuchen</fieldName>\n                    <value>true</value>\n                </actions>\n                <actions>\n                    <_type>SetFieldValue</_type>\n                    <fieldName>yerfzeit</fieldName>\n                    <value>\n                    <xsl:value-of select=\"format-dateTime(current-dateTime(),\'[D01].[M01].[Y0001]_[H01]:[m01]\')\"/>\n                    </value>\n                </actions>\n                <actions>\n                    <_type>SetFieldValue</_type>\n                    <fieldName>yerfdat</fieldName>\n                    <value>\n                        <xsl:value-of select=\"format-dateTime(current-dateTime(),\'[Y0001][M01][D01]\')\"/>\n                    </value>\n                </actions>\n                <actions>\n                    <_type>SetFieldValue</_type>\n                    <fieldName>bem</fieldName>\n                    <value>\n                        <xsl:value-of select=\"concat(format-dateTime(current-dateTime(),\'[D01].[M01].[Y0001]\'), \' \', /ObjectNode/result/subProductionSteps[id = /ObjectNode/additional]/timeRecords[last()]/responseUser/externalId, \' \', /ObjectNode/result/subProductionSteps[id = /ObjectNode/additional]/timeRecords[last()]/responseUser/userName, \';BDE\')\" />\n                    </value>\n                </actions>\n            </EXPORT_JSON>        \n        </xsl:variable>\n\n        <bookingEntry>\n            <machineOccupation>\n                <externalId>\n                    <xsl:value-of select=\"/ObjectNode/result/externalId\"/>\n                </externalId>\n            </machineOccupation>            \n            <subProductionStep>\n                <externalId>\n                    <xsl:value-of select=\"/ObjectNode/result/subProductionSteps[id = /ObjectNode/additional]/externalId\"/>\n                </externalId>\n            </subProductionStep>\n            <isStepTime>true</isStepTime>\n            <bookingState>NEW</bookingState>\n            <xsl:if test=\"/ObjectNode/result/subProductionSteps[id = /ObjectNode/additional]/timeRecords[last()]/responseUser/externalId != \'\'\">\n                <user>\n                    <externalId>\n                        <xsl:value-of select=\"/ObjectNode/result/subProductionSteps[id = /ObjectNode/additional]/timeRecords[last()]/responseUser/externalId\"/>\n                    </externalId>\n                </user>\n            </xsl:if>                       \n            <message>\n                <xsl:copy-of select=\"$export\"/>\n            </message>\n        </bookingEntry> \n\n    </output>\n  </xsl:template>\n</xsl:stylesheet>'),
(97, NULL, NULL, '<xsl:stylesheet version=\"2.0\" \nxmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"\nxmlns:map=\"http://www.w3.org/2005/xpath-functions/map\"\nxmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n    <xsl:template match=\"/\">\n        <output>\n            \n            <!-- Loop over Table array -->\n            <xsl:for-each\n                select=\"/ObjectNode/queryResult\">\n            <xsl:variable name=\"customerOrder_extid\" select=\"/ObjectNode/result/content/data/head/fields/verw/value\" />\n            <xsl:variable name=\"cd_product_extid\" select=\"concat(./productionorder_extid, \'_\' , ./cd_product_extid)\" />\n            <xsl:variable name=\"productionorder_extid\" select=\"./productionorder_extid\" />\n            <xsl:variable name=\"cd_department_extid\" select=\"./cd_department_extid\" />\n            <xsl:variable name=\"cd_machinetype_extid_db\" select=\"./cd_machinetype_extid\" />\n            <xsl:variable name=\"cd_machine_extid\" select=\"./cd_machine_extid\" />\n            <xsl:variable name=\"cd_productionstep_extid\" select=\"./cd_productionstep_extid\" />\n            <xsl:variable name=\"cD_ToolType_extid\" select=\"concat(/ObjectNode/result/content/data/head/fields/banummer/text, \'_\', ./fields/elemnum/value)\" />\n            <xsl:variable name=\"machineoccupation_extid\" select=\"./machineoccupation_extid\" />\n            <xsl:variable name=\"cD_Tool_extid\" select=\"concat(/ObjectNode/result/content/data/head/fields/banummer/text, \'_\', ./fields/elemnum/value)\" />\n            <xsl:variable name=\"cD_ProductRelationship_extid\" select=\"concat(/ObjectNode/result/content/data/head/fields/banummer/text, \'_REL_\', /ObjectNode/result/content/data/head/fields/artnum/value, \'_\', ./fields/elemnum/value)\" />\n            <xsl:variable name=\"cd_machinetype_extid\">\n                <xsl:choose>\n                    <xsl:when test=\"$cd_machinetype_extid_db = 10 or $cd_machinetype_extid_db = 11\">\n                        <xsl:value-of select=\"\'001\'\"/>\n                    </xsl:when>\n                    <xsl:when test=\"$cd_machinetype_extid_db = 12\">\n                        <xsl:value-of select=\"\'002\'\"/>\n                    </xsl:when>\n                    <xsl:when test=\"$cd_machinetype_extid_db = 9\">\n                        <xsl:value-of select=\"\'003\'\"/>\n                    </xsl:when>\n                    <xsl:when test=\"$cd_machinetype_extid_db = 14\">\n                        <xsl:value-of select=\"\'004\'\"/>\n                    </xsl:when>\n                    <xsl:when test=\"$cd_machinetype_extid_db = 15\">\n                        <xsl:value-of select=\"\'005\'\"/>\n                    </xsl:when>\n                    <xsl:when test=\"$cd_machinetype_extid_db = 20\">\n                        <xsl:value-of select=\"\'006\'\"/>\n                    </xsl:when>\n                    <xsl:when test=\"$cd_machinetype_extid_db = 18\">\n                        <xsl:value-of select=\"\'007\'\"/>\n                    </xsl:when>\n                    <xsl:when test=\"$cd_machinetype_extid_db = 19\">\n                        <xsl:value-of select=\"\'008\'\"/>\n                    </xsl:when>\n                    <xsl:otherwise>\n                        <xsl:value-of select=\"$cd_machinetype_extid_db\"/>\n                    </xsl:otherwise>\n                </xsl:choose>\n            </xsl:variable>\n            \n            <!-- Customer Order -->\n            <customerOrder>\n                <externalId>\n                    <xsl:value-of select=\"$customerOrder_extid\" />\n                </externalId>\n                <customerName>\n                    <xsl:value-of select=\"/ObjectNode/result/content/data/head/fields/ykunde/value\" />\n                </customerName>\n                <deadline>\n                    <xsl:value-of select=\"concat(/ObjectNode/result/content/data/head/fields/tterm/value, \'T23:59:00\')\" />\n                </deadline>\n                <name>\n                    <xsl:value-of select=\"/ObjectNode/result/content/data/head/fields/verw/value\" />\n                </name>\n            </customerOrder>\n\n            <!-- Product -->\n            <cD_Product>\n                <externalId>\n                    <xsl:value-of select=\"$cd_product_extid\" />\n                </externalId>\n                \n                <name>\n                    <xsl:value-of select=\"parse-json(./cd_product_name/value)?de_DE\"/>\n                </name>\n            </cD_Product>\n\n            <!-- Production Order -->\n            <productionOrder>\n                <externalId>\n                    <xsl:value-of select=\"$productionorder_extid\" />\n                </externalId>\n                <name>\n                    <xsl:value-of select=\"./productionorder_name\" />\n                </name>\n                <versionNr>\n                    <xsl:value-of select=\"/ObjectNode/additional/versionNr\" />\n                </versionNr>\n                <measurement>\n                    <amount>\n                        <xsl:value-of select=\"number(./productionorder_amount)\" />\n                    </amount>\n                    <unitString>pcs</unitString>\n                </measurement>\n                <deadline>\n                    <xsl:call-template name=\"timeConversion\">\n                        <xsl:with-param name=\"epochTime\"><xsl:value-of select=\"./productionorder_deadline\" /></xsl:with-param>\n                    </xsl:call-template>\n                </deadline>\n\n                <!-- References -->\n                <customerOrder>\n                    <externalId>\n                        <xsl:value-of select=\"$customerOrder_extid\" />\n                    </externalId>\n                </customerOrder>\n                <product>\n                    <externalId>\n                        <xsl:value-of select=\"$cd_product_extid\" />\n                    </externalId>\n                </product>\n            </productionOrder>\n            \n            <cD_Department>\n                <externalId>\n                    <xsl:value-of select=\"$cd_department_extid\" />\n                </externalId>\n                <name>\n                    <xsl:value-of select=\"./cd_department_name\" />\n                </name>\n            </cD_Department>\n\n            <xsl:choose>\n                <xsl:when\n                    test=\"./cd_machinetype_name = \'Spritzgießen A\' or ./cd_machinetype_name = \'Spritzgießen B\'\">\n                    <!-- MachineType -->\n                    <cD_MachineType>\n                        <externalId>\n                            <xsl:value-of select=\"$cd_machinetype_extid\" />\n                        </externalId>\n                        <name>Spritzgießen</name>                        \n                        <departments>\n                            <externalId>\n                                <xsl:value-of select=\"$cd_department_extid\" />\n                            </externalId>\n                        </departments>\n                    </cD_MachineType>\n                   \n                </xsl:when>\n                <xsl:otherwise>\n                    <!-- MachineType -->\n                    <cD_MachineType>\n                        <externalId>\n                            <xsl:value-of select=\"$cd_machinetype_extid\" />\n                        </externalId>\n                        <name>\n                            <xsl:value-of select=\"./cd_machinetype_name\" />\n                        </name>                        \n                        <departments>\n                            <externalId>\n                                <xsl:value-of select=\"$cd_department_extid\" />\n                            </externalId>\n                        </departments>\n                    </cD_MachineType>\n                \n                </xsl:otherwise>\n            </xsl:choose>\n\n                    <!-- Machine -->\n                    <cD_Machine>\n                        <externalId>\n                            <xsl:value-of select=\"$cd_machine_extid\" />\n                        </externalId>\n                        <name>\n                            <xsl:value-of select=\"./cd_machine_name\" />\n                        </name>\n                        <machineType>\n                            <externalId>\n                                <xsl:value-of select=\"$cd_machinetype_extid\" />\n                            </externalId>\n                        </machineType>\n                    </cD_Machine>\n\n            <!-- ProductionStep -->\n            <cD_ProductionStep>\n                <name>\n                    <xsl:value-of select=\"./cd_productionstep_name\" />\n                </name>\n                <productionDuration>\n                    <xsl:value-of select=\"number(./cd_productionstep_duration)*60 div number(./productionorder_amount)\" />\n                </productionDuration>\n                <setupTime>\n                    <xsl:value-of select=\"number(0)\" />\n                </setupTime>\n                <externalId>\n                    <xsl:value-of select=\"$cd_productionstep_extid\" />\n                </externalId>\n                <machineType>\n                    <externalId>\n                        <xsl:value-of select=\"$cd_machinetype_extid\" />\n                    </externalId>\n                </machineType>\n                <sequence>\n                    <xsl:value-of select=\"./cd_productionstep_number\" />\n                </sequence>\n\n                <!-- Check for ArbGang ID and select first id of camunda process -->\n                <xsl:choose>\n                    <xsl:when\n                        test=\"./cd_machinetype_name = \'Spritzgießen A\' or ./cd_machinetype_name = \'Spritzgießen B\' or ./cd_machinetype_name = \'Wickeln\' or ./cd_machinetype_name = \'Tiefziehen\'\">\n                        <camundaProcessName>Subprocess_Basis</camundaProcessName>\n                    </xsl:when>\n                    <xsl:when \n                        test=\"./cd_machinetype_name = \'Handmontage\' or ./cd_machinetype_name = \'Endmontage\'\">\n                        <camundaProcessName>Subprocess_Montage</camundaProcessName>\n                    </xsl:when>\n                    <xsl:when \n                        test=\"./cd_machinetype_name = \'QS Prüfung\'\">\n                        <camundaProcessName>Subprocess_QS</camundaProcessName>\n                    </xsl:when>\n                    <xsl:when \n                        test=\"./cd_machinetype_name = \'Verpackung\'\">\n                        <camundaProcessName>Subprocess_Verpackung</camundaProcessName>\n                    </xsl:when>\n                    <xsl:when \n                        test=\"./cd_machinetype_name = \'Versand\'\">\n                        <camundaProcessName>Subprocess_Versand</camundaProcessName>\n                    </xsl:when>\n                    <xsl:otherwise>\n                        <camundaProcessName>Subprocess_Basis</camundaProcessName>\n                    </xsl:otherwise>\n                </xsl:choose>\n                \n                <product>\n                    <externalId>\n                        <xsl:value-of select=\"$cd_product_extid\" />\n                    </externalId>\n                </product>\n            </cD_ProductionStep>\n                <!--\n                    <machineOccupation>\n                        <externalId>\n                            <xsl:value-of select=\"$machineoccupation_extid\" />\n                        </externalId>\n                        <name>\n                            <xsl:value-of select=\"./cd_productionstep_number\" />\n                        </name>\n                        <plannedStartDateTime>                            \n                            <xsl:call-template name=\"timeConversion\">\n                                <xsl:with-param name=\"epochTime\"><xsl:value-of select=\"./machineoccupation_plannedstart\" /></xsl:with-param>\n                            </xsl:call-template>\n                        </plannedStartDateTime>\n                        <plannedEndDateTime>\n                            <xsl:call-template name=\"timeConversion\">\n                                <xsl:with-param name=\"epochTime\"><xsl:value-of select=\"./machineoccupation_plannedstart\" /></xsl:with-param>\n                            </xsl:call-template>\n                        </plannedEndDateTime>\n                        <productionOrder>\n                            <externalId>\n                                <xsl:value-of select=\"$productionorder_extid\" />\n                            </externalId>\n                        </productionOrder>\n                        <department>\n                            <externalId>\n                                <xsl:value-of select=\"$cd_department_extid\" />\n                            </externalId>\n                        </department>\n                        <status>READY_TO_START</status>\n\n                        <productionSteps>\n                            <externalId>\n                                <xsl:value-of select=\"$cd_productionstep_extid\" />\n                            </externalId>\n                        </productionSteps>\n\n                        <xsl:choose>\n                            <xsl:when\n                                test=\"./cd_machinetype_name = \'Spritzgießen A\' or ./cd_machinetype_name = \'Spritzgießen B\' or ./cd_machinetype_name = \'Wickeln\' or ./cd_machinetype_name = \'Tiefziehen\'\">\n                                <camundaProcessName>Process_Basis</camundaProcessName>\n                            </xsl:when>\n                            <xsl:when \n                                test=\"./cd_machinetype_name = \'Handmontage\' or ./cd_machinetype_name = \'Endmontage\'\">\n                                <camundaProcessName>Process_Montage</camundaProcessName>\n                            </xsl:when>\n                            <xsl:when \n                                test=\"./cd_machinetype_name = \'QS Prüfung\'\">\n                                <camundaProcessName>Process_Check</camundaProcessName>\n                            </xsl:when>\n                            <xsl:when \n                                test=\"./cd_machinetype_name = \'Verpackung\'\">\n                                <camundaProcessName>Process_Verpackung</camundaProcessName>\n                            </xsl:when>\n                            <xsl:when \n                                test=\"./cd_machinetype_name = \'Versand\'\">\n                                <camundaProcessName>Process_Versand</camundaProcessName>\n                            </xsl:when>\n                            <xsl:otherwise>\n                                <camundaProcessName>Process_Basis</camundaProcessName>\n                            </xsl:otherwise>\n                        </xsl:choose>\n                        <machine>\n                            <externalId>\n                                <xsl:value-of select=\"$cd_machine_extid\" />\n                            </externalId>\n                        </machine>\n\n                    </machineOccupation>-->\n\n                <!-- Produkt/Material -->\n                <xsl:if\n                    test=\"./fields/elart/value = \'(Product)\'\">\n                    <cD_Product>\n                        <name>\n                            <xsl:value-of select=\"json-to-xml(data)/map:map/map:string[@name=\'de_DE\']\"/>\n                        </name>\n                        <externalId>\n                            <xsl:value-of select=\"$cd_product_extid\" />\n                        </externalId>\n                    </cD_Product>\n                    <cD_ProductRelationship>\n                        <externalId>\n                            <xsl:value-of select=\"$cD_ProductRelationship_extid\" />\n                        </externalId>\n                        <product>\n                            <externalId>\n                                <xsl:value-of select=\"$cd_product_extid\" />\n                            </externalId>\n                            <unitString>pcs</unitString>\n                        </product>\n                        <part>\n                            <externalId>\n                                <xsl:value-of select=\"$cd_product_extid\" />\n                            </externalId>\n                            <unitString>pcs</unitString>\n                        </part>\n                        <extIdProductionOrder>\n\n                            <xsl:value-of select=\"$productionorder_extid\" />\n                        </extIdProductionOrder>\n                        <xsl:call-template name=\"measurement\">\n                            <xsl:with-param name=\"amount\"><xsl:value-of select=\"number(./fields/anzahl/value)\" /></xsl:with-param>\n                            <xsl:with-param name=\"unitString\"><xsl:value-of select=\"./fields/elemle/value\" /></xsl:with-param>\n                        </xsl:call-template>\n                    </cD_ProductRelationship>\n                </xsl:if>\n            </xsl:for-each>\n        </output>\n\n    </xsl:template>\n\n    <!-- Templates -->\n    <xsl:template name=\"toolSetting\">\n        <xsl:param name=\"externalId\" />\n        <xsl:param name=\"parameterId\" />\n        <xsl:param name=\"value\" />\n        <toolSetting>\n            <externalId><xsl:value-of select=\"$externalId\" />_<xsl:value-of select=\"$parameterId\" /></externalId>\n            <settingsValue>\n                <xsl:value-of select=\"$value\" />\n            </settingsValue>\n            <toolSettingParameter>\n                <externalId>\n                    <xsl:value-of select=\"$parameterId\" />\n                </externalId>\n            </toolSettingParameter>\n            <measurement>\n                <amount>0</amount>\n                <unitString></unitString>\n            </measurement>\n        </toolSetting>\n    </xsl:template>\n\n    <xsl:template name=\"measurement\">\n        <xsl:param name=\"unitString\" />\n        <xsl:param name=\"amount\" />\n        <measurement>\n            <amount>\n                <xsl:value-of select=\"number($amount)\" />\n            </amount>\n            <xsl:if\n                test=\"$unitString = \'(G)\'\">\n                <unitString>g</unitString>\n            </xsl:if>\n            <xsl:if\n                test=\"$unitString = \'(PCE)\'\">\n                <unitString>pcs</unitString>\n            </xsl:if>\n        </measurement>\n    </xsl:template>    \n    <xsl:template name=\"timeConversion\">\n        <xsl:param name=\"epochTime\" />\n        <xsl:variable name=\"milliseconds\" select=\"number(./productionorder_deadline)\"/>\n        <xsl:variable name=\"seconds\" select=\"floor($milliseconds div 1000)\"/>\n        <xsl:variable name=\"days\" select=\"floor($seconds div 86400)\"/>\n        <xsl:variable name=\"remainingSeconds\" select=\"$seconds mod 86400\"/>\n        <!-- Handle days and remaining seconds separately to avoid large duration issues -->\n        <xsl:variable name=\"epochDate\" select=\"xs:dateTime(\'1970-01-01T00:00:00\')\"/>\n        <xsl:variable name=\"dateTimeDays\" select=\"$epochDate + xs:dayTimeDuration(concat(\'P\', $days, \'D\'))\"/>\n        <xsl:variable name=\"dateTime\" select=\"$dateTimeDays + xs:dayTimeDuration(concat(\'PT\', $remainingSeconds, \'S\'))\"/>\n\n        <xsl:value-of select=\"format-dateTime($dateTime, \'[Y0001]-[M01]-[D01]T[H01]:[m01]:[s01]\')\"/>\n    </xsl:template>\n</xsl:stylesheet>'),
(101, NULL, NULL, '<xsl:stylesheet version=\"2.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">\n  <xsl:template match=\"/\">\n    <output>\n        <subProductionStep>\n          <productionNumbers>\n            <acceptedMeasurement>\n              <amount>\n                  <xsl:value-of select=\"ObjectNode/ff_PartsOK\" />\n              </amount>\n            </acceptedMeasurement>\n            <rejectedMeasurement>\n              <amount>\n                  <xsl:value-of select=\"ObjectNode/ff_PartsNOK\" />\n              </amount>\n            </rejectedMeasurement>\n          </productionNumbers>\n          <note>\n            <xsl:value-of select=\"ObjectNode/ff_Note\" />\n          </note>\n        </subProductionStep>\n    </output>\n  </xsl:template>\n</xsl:stylesheet>');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `MappingConfiguration_MappingConfiguration`
--

CREATE TABLE `MappingConfiguration_MappingConfiguration` (
  `MappingConfiguration_id` bigint NOT NULL,
  `loops_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `MappingConfiguration_MappingRule`
--

CREATE TABLE `MappingConfiguration_MappingRule` (
  `MappingConfiguration_id` bigint NOT NULL,
  `mappings_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `MappingRule`
--

CREATE TABLE `MappingRule` (
  `id` bigint NOT NULL,
  `inputSelector` varchar(255) DEFAULT NULL,
  `outputSelector` varchar(255) DEFAULT NULL,
  `rule` varchar(1020) DEFAULT NULL,
  `uiId` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Note`
--

CREATE TABLE `Note` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `creationDateTime` datetime(6) DEFAULT NULL,
  `noteString` varchar(255) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `Note`
--

INSERT INTO `Note` (`id`, `createDateTime`, `externalId`, `updateDateTime`, `versionNr`, `creationDateTime`, `noteString`, `userName`) VALUES
(231, '2024-11-25 13:22:06.036914', NULL, '2024-11-25 13:22:06.036933', NULL, '2024-11-25 13:22:06.024529', '20 stk aus Lager', 'llustig');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `OverheadCost`
--

CREATE TABLE `OverheadCost` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `timeDuration` bigint DEFAULT NULL,
  `overheadCostCenter_id` bigint DEFAULT NULL,
  `timeRecord_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ProductionNumbers`
--

CREATE TABLE `ProductionNumbers` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `acceptedAmount` double DEFAULT NULL,
  `acceptedUnitString` varchar(255) DEFAULT NULL,
  `rejectedAmount` double DEFAULT NULL,
  `rejectedUnitString` varchar(255) DEFAULT NULL,
  `subProductionStep_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `ProductionNumbers`
--

INSERT INTO `ProductionNumbers` (`id`, `createDateTime`, `externalId`, `updateDateTime`, `versionNr`, `acceptedAmount`, `acceptedUnitString`, `rejectedAmount`, `rejectedUnitString`, `subProductionStep_id`) VALUES
(140, '2024-11-25 13:17:58.016775', NULL, '2024-11-25 13:17:58.016797', NULL, 0, '', 0, '', NULL),
(143, '2024-11-25 13:17:58.074193', NULL, '2024-11-25 13:32:36.923727', NULL, 50, '', 0, '', NULL),
(146, '2024-11-25 13:17:58.121186', NULL, '2024-11-25 13:28:57.083597', NULL, 50, '', 0, '', NULL),
(149, '2024-11-25 13:17:58.166958', NULL, '2024-11-25 13:33:09.239460', NULL, 50, '', 0, '', NULL),
(152, '2024-11-25 13:17:58.211979', NULL, '2024-11-25 13:17:58.211996', NULL, 0, '', 0, '', NULL),
(155, '2024-11-25 13:17:58.260107', NULL, '2024-11-25 13:17:58.260128', NULL, 0, '', 0, '', NULL),
(158, '2024-11-25 13:17:58.307769', NULL, '2024-11-25 13:17:58.307787', NULL, 0, '', 0, '', NULL),
(161, '2024-11-25 13:17:58.354101', NULL, '2024-11-25 13:17:58.354120', NULL, 0, '', 0, '', NULL),
(164, '2024-11-25 13:17:58.402713', NULL, '2024-11-25 13:17:58.402734', NULL, 0, '', 0, '', NULL),
(167, '2024-11-25 13:17:58.450501', NULL, '2024-11-25 13:17:58.450520', NULL, 0, '', 0, '', NULL),
(170, '2024-11-25 13:17:58.501082', NULL, '2024-11-25 13:17:58.501132', NULL, 0, '', 0, '', NULL),
(173, '2024-11-25 13:17:58.549881', NULL, '2024-11-25 13:17:58.549901', NULL, 0, '', 0, '', NULL),
(176, '2024-11-25 13:17:58.600684', NULL, '2024-11-25 13:17:58.600702', NULL, 0, '', 0, '', NULL),
(179, '2024-11-25 13:17:58.650132', NULL, '2024-11-25 13:17:58.650154', NULL, 0, '', 0, '', NULL),
(182, '2024-11-25 13:17:58.695577', NULL, '2024-11-25 13:17:58.695600', NULL, 0, '', 0, '', NULL),
(185, '2024-11-25 13:17:58.752784', NULL, '2024-11-25 13:17:58.752803', NULL, 0, '', 0, '', NULL),
(188, '2024-11-25 13:17:58.799387', NULL, '2024-11-25 13:17:58.799408', NULL, 0, '', 0, '', NULL),
(191, '2024-11-25 13:17:58.842060', NULL, '2024-11-25 13:17:58.842081', NULL, 0, '', 0, '', NULL),
(194, '2024-11-25 13:17:58.886017', NULL, '2024-11-25 13:17:58.886037', NULL, 0, '', 0, '', NULL),
(197, '2024-11-25 13:17:58.931421', NULL, '2024-11-25 13:17:58.931439', NULL, 0, '', 0, '', NULL),
(200, '2024-11-25 13:17:58.976048', NULL, '2024-11-25 13:17:58.976065', NULL, 0, '', 0, '', NULL),
(203, '2024-11-25 13:17:59.019939', NULL, '2024-11-25 13:17:59.019957', NULL, 0, '', 0, '', NULL),
(206, '2024-11-25 13:17:59.064755', NULL, '2024-11-25 13:17:59.064776', NULL, 0, '', 0, '', NULL),
(209, '2024-11-25 13:17:59.110976', NULL, '2024-11-25 13:17:59.110996', NULL, 0, '', 0, '', NULL),
(232, '2024-11-25 13:22:14.535276', NULL, '2024-11-25 13:22:14.535304', NULL, 5, '', 0, '', 226),
(248, '2024-11-25 13:25:04.966417', NULL, '2024-11-25 13:25:04.966436', NULL, 15, '', 0, '', 234),
(254, '2024-11-25 13:28:57.081870', NULL, '2024-11-25 13:28:57.081888', NULL, 50, '', 0, '', 243),
(256, '2024-11-25 13:29:15.563083', NULL, '2024-11-25 13:29:15.563099', NULL, 20, '', 0, '', 250),
(262, '2024-11-25 13:32:36.922486', NULL, '2024-11-25 13:32:36.922503', NULL, 10, '', 0, '', 258),
(274, '2024-11-25 13:33:09.234583', NULL, '2024-11-25 13:33:09.234598', NULL, 50, '', 0, '', 270);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ProductionOrder`
--

CREATE TABLE `ProductionOrder` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `deadline` datetime(6) DEFAULT NULL,
  `amount` double NOT NULL,
  `unitString` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `priority` int NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `customerOrder_id` bigint DEFAULT NULL,
  `parentProductionOrder_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `ProductionOrder`
--

INSERT INTO `ProductionOrder` (`id`, `createDateTime`, `externalId`, `updateDateTime`, `versionNr`, `deadline`, `amount`, `unitString`, `name`, `priority`, `status`, `customerOrder_id`, `parentProductionOrder_id`, `product_id`) VALUES
(133, '2024-11-25 13:17:57.576758', 'Insti/MO/00004', '2024-11-25 13:17:57.576783', '', '2024-05-24 22:06:00.000000', 150, 'pcs', 'Insti/MO/00004', 0, 'PLANNED', NULL, NULL, 103),
(134, '2024-11-25 13:17:57.596007', 'Insti/MO/00014', '2024-11-25 13:17:57.596030', '', '2024-05-27 10:47:47.000000', 50, 'pcs', 'Insti/MO/00014', 0, 'PLANNED', NULL, NULL, 104),
(135, '2024-11-25 13:17:57.618222', 'Insti/MO/00017', '2024-11-25 13:17:57.618245', '', '2024-05-27 10:16:19.000000', 50, 'pcs', 'Insti/MO/00017', 0, 'PLANNED', NULL, NULL, 105),
(136, '2024-11-25 13:17:57.639394', 'Insti/MO/00015', '2024-11-25 13:17:57.639443', '', '2024-05-28 11:02:52.000000', 50, 'pcs', 'Insti/MO/00015', 0, 'PLANNED', NULL, NULL, 106),
(137, NULL, 'Insti/MO/00028', '2024-11-25 13:17:57.808445', '', '2024-05-31 12:39:41.000000', 50, 'pcs', 'Insti/MO/00028', 0, 'PLANNED', NULL, NULL, 107),
(138, NULL, 'Insti/MO/00027', '2024-11-25 13:17:57.981746', '', '2024-05-31 15:37:59.000000', 150, 'pcs', 'Insti/MO/00027', 0, 'PLANNED', NULL, NULL, 108);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ProductionOrder_Note`
--

CREATE TABLE `ProductionOrder_Note` (
  `ProductionOrder_id` bigint NOT NULL,
  `notes_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `ProductionOrder_Note`
--

INSERT INTO `ProductionOrder_Note` (`ProductionOrder_id`, `notes_id`) VALUES
(134, 231);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ProductionOrder_ProductionOrder`
--

CREATE TABLE `ProductionOrder_ProductionOrder` (
  `ProductionOrder_id` bigint NOT NULL,
  `subProductionOrders_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ProductionStepInfo`
--

CREATE TABLE `ProductionStepInfo` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `stepGroup` varchar(255) DEFAULT NULL,
  `stepIdent` varchar(255) DEFAULT NULL,
  `stepNr` int NOT NULL,
  `stepType` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `QualityManagement`
--

CREATE TABLE `QualityManagement` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `qualityOk` bit(1) NOT NULL,
  `qualityControlFeature_id` bigint DEFAULT NULL,
  `subProductionStep_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ReaderResult`
--

CREATE TABLE `ReaderResult` (
  `id` bigint NOT NULL,
  `additionalData` longtext,
  `result` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `ReaderResult`
--

INSERT INTO `ReaderResult` (`id`, `additionalData`, `result`) VALUES
(66, '29120', '{\"id\":74338,\"externalId\":\"364914011_1625\",\"versionNr\":null,\"subMachineOccupations\":[],\"productionSteps\":[{\"id\":74277,\"externalId\":\"364914_1625\",\"versionNr\":null,\"camundaProcessName\":\"Subprocess_AbdrehenCBN_1625_1635\",\"machineType\":{\"id\":809,\"externalId\":\"625\",\"versionNr\":null,\"name\":\"Abdrehen CBN CNC 625\"},\"toolType\":null,\"product\":null,\"name\":\"Abdrehen CBN CNC\",\"productionDuration\":1.700000000,\"setupTime\":0.0,\"sequence\":0,\"productionStepInfo\":{\"id\":17,\"externalId\":\"625_cnc_turn\",\"versionNr\":null,\"stepGroup\":\"625_cnc\",\"stepIdent\":\"turn\",\"stepType\":\"ALTERNATIVE\",\"stepNr\":1},\"mgrnr\":\"625\",\"arbgangNr\":\"1625\"}],\"machine\":{\"id\":817,\"externalId\":\"625\",\"versionNr\":null,\"machineType\":{\"id\":809,\"externalId\":\"625\",\"versionNr\":null,\"name\":\"Abdrehen CBN CNC 625\"},\"name\":\"Abdrehen CBN CNC\"},\"tool\":null,\"availableTools\":[],\"productionOrder\":{\"id\":74282,\"externalId\":\"364914\",\"versionNr\":\"2023101309152800000000\",\"measurement\":{\"amount\":55.0,\"unitString\":\"pcs\"},\"customerOrder\":{\"id\":74281,\"externalId\":\"2074278_2\",\"versionNr\":null,\"name\":\"2074278_2\",\"customerName\":\"53000\",\"priority\":0,\"deadline\":\"2023-11-14T23:59:00\",\"note\":\"Mat-Nr. 036893080-0000\\n\",\"ysbvertr\":\"Ss/Gm\"},\"product\":{\"id\":625,\"externalId\":\"60995\",\"versionNr\":\"2023101419141300000102\",\"name\":\"60995\",\"unitType\":null,\"qualityControlFeatures\":[],\"elemsuch\":\"SLSBOB32B0046\",\"elemname\":\"8,3-0,3 X 20 X 5,5\",\"platzsuch\":null,\"ysm1\":\"32B\",\"ykorn1\":\"46\",\"yhart1\":\"P\",\"ygef1\":\"7\",\"ybind1\":\"V164\",\"ybind2\":\"PK\",\"ynachb\":\"\",\"yspezgew\":0.0,\"ymgamma\":2.26,\"yagamma\":0.0,\"ykonz\":192.0,\"yform\":\"Leer\",\"yrand\":\"A\",\"ytxbs\":\"SIEHE75628\",\"ytabm\":\"\",\"ydurch1\":8.3,\"ydtol\":\"-0,3\",\"ybreit1\":20.0,\"ybtol\":\"+0,30/-0,10\",\"ybs\":5.5,\"ystol\":\"\",\"yausd1\":0.0,\"yausb1\":0.0,\"yausd2\":0.0,\"yausb2\":0.0,\"ykennz1\":\"\",\"ykennz2\":\"\",\"ykennz3\":\"\",\"ygetext\":\"\",\"ybem\":\"Mach Klärung Sj/Ra/Hg wurde die Breite mit +0,3/-0,10 toleriert - \\nMail Sj 02.05.17 - ew\\n\\n\\n\\nauf Anweisung Pö 15.09.15 wurde der Freigabehaken im Artikel \\nwieder gesetzt - ew \\n\",\"yvms\":\"63\",\"ybrand\":\"\",\"ycode\":\"\",\"yfarbkoerper\":\"\",\"yzeichnung\":\"assets/drawing/Zeichnungen/60995_ZG(1)8864_20230427.pdf\",\"yd1tol\":\"\",\"yb1tol\":\"\",\"yd2tol\":\"\",\"yb2tol\":\"\"},\"name\":\"364914\",\"priority\":0,\"deadline\":\"2023-11-14T23:59:00\",\"status\":\"PLANNED\",\"notes\":[],\"keyValueMap\":{},\"orderAmount\":500.0,\"orderType\":\"A\",\"startDateTime\":\"2023-10-25T00:00:00\",\"sumSteps\":14},\"plannedStartDateTime\":\"2023-11-09T00:00:00\",\"plannedEndDateTime\":\"2023-11-09T23:59:00\",\"actualStartDateTime\":null,\"actualEndDateTime\":null,\"camundaProcessName\":\"Process_unknown\",\"name\":\"011\",\"status\":\"PLANNED\",\"subProductionSteps\":[],\"activeToolSettings\":[],\"totalProductionNumbers\":{\"id\":74339,\"externalId\":null,\"versionNr\":null,\"acceptedMeasurement\":{\"amount\":0.0,\"unitString\":\"\"},\"rejectedMeasurement\":{\"amount\":0.0,\"unitString\":\"\"}},\"timeDurations\":{},\"ptext\":\"Achtung! aufgrund Reklamation 3524 bitte\\nmit der Abdrehaufnahme 5,53+0,01\\nabdrehen - Ra/ew 23.05.2012\\n\\nfür Zeitaufnahme Ma verständigen\",\"datasheet\":null,\"drawing\":null,\"image\":null,\"instruction\":null,\"manual\":null,\"document\":null,\"misc\":null}'),
(70, 'null', '[{\"id\":1,\"externalId\":null,\"versionNr\":null,\"subMachineOccupations\":[],\"productionSteps\":[{\"id\":47,\"externalId\":\"Insti/MO/00027/51\",\"versionNr\":null,\"camundaProcessName\":\"Subprocess_Basis\",\"machineType\":{\"id\":15,\"externalId\":\"001\",\"versionNr\":null,\"name\":\"Spritzgießen\",\"departments\":[{\"id\":1,\"externalId\":\"2\",\"versionNr\":null,\"name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"}]},\"toolType\":null,\"product\":{\"id\":8,\"externalId\":\"Insti/MO/00027_106\",\"versionNr\":null,\"name\":\"Kugelschreiber blau/rot, Kunststoff\",\"unitType\":null,\"qualityControlFeatures\":[]},\"name\":\"Spritzgießen Griffrohr A\",\"productionDuration\":2.000000000,\"setupTime\":0.0,\"sequence\":100,\"productionStepInfo\":null}],\"machine\":null,\"tool\":null,\"availableTools\":[],\"productionOrder\":{\"id\":84,\"externalId\":\"Insti/MO/00027\",\"versionNr\":\"\",\"measurement\":{\"amount\":150.0,\"unitString\":\"pcs\"},\"customerOrder\":{\"id\":76,\"externalId\":\"\",\"versionNr\":null,\"name\":\"\",\"customerName\":\"\",\"priority\":0,\"deadline\":null},\"product\":{\"id\":8,\"externalId\":\"Insti/MO/00027_106\",\"versionNr\":null,\"name\":\"Kugelschreiber blau/rot, Kunststoff\",\"unitType\":null,\"qualityControlFeatures\":[]},\"name\":\"Insti/MO/00027\",\"priority\":0,\"deadline\":\"2024-05-31T15:37:59\",\"status\":\"PLANNED\",\"notes\":[],\"keyValueMap\":{}},\"plannedStartDateTime\":\"2024-11-21T00:00:00.142\",\"plannedEndDateTime\":\"2024-11-21T00:05:00.142\",\"actualStartDateTime\":null,\"actualEndDateTime\":null,\"camundaProcessName\":null,\"name\":\"Insti/MO/00027\",\"status\":\"PLANNED\",\"subProductionSteps\":[],\"activeToolSettings\":[],\"totalProductionNumbers\":null,\"timeDurations\":{},\"department\":null},{\"id\":2,\"externalId\":null,\"versionNr\":null,\"subMachineOccupations\":[],\"productionSteps\":[{\"id\":48,\"externalId\":\"Insti/MO/00027/52\",\"versionNr\":null,\"camundaProcessName\":\"Subprocess_Basis\",\"machineType\":{\"id\":15,\"externalId\":\"001\",\"versionNr\":null,\"name\":\"Spritzgießen\",\"departments\":[{\"id\":1,\"externalId\":\"2\",\"versionNr\":null,\"name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"}]},\"toolType\":null,\"product\":{\"id\":8,\"externalId\":\"Insti/MO/00027_106\",\"versionNr\":null,\"name\":\"Kugelschreiber blau/rot, Kunststoff\",\"unitType\":null,\"qualityControlFeatures\":[]},\"name\":\"Spritzgießen Griffrohr B\",\"productionDuration\":3.000000000,\"setupTime\":0.0,\"sequence\":101,\"productionStepInfo\":null}],\"machine\":{\"id\":25,\"externalId\":\"11\",\"versionNr\":null,\"machineType\":{\"id\":15,\"externalId\":\"001\",\"versionNr\":null,\"name\":\"Spritzgießen\",\"departments\":[{\"id\":1,\"externalId\":\"2\",\"versionNr\":null,\"name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"}]},\"name\":\"Spritzgießen B\"},\"tool\":null,\"availableTools\":[],\"productionOrder\":{\"id\":84,\"externalId\":\"Insti/MO/00027\",\"versionNr\":\"\",\"measurement\":{\"amount\":150.0,\"unitString\":\"pcs\"},\"customerOrder\":{\"id\":76,\"externalId\":\"\",\"versionNr\":null,\"name\":\"\",\"customerName\":\"\",\"priority\":0,\"deadline\":null},\"product\":{\"id\":8,\"externalId\":\"Insti/MO/00027_106\",\"versionNr\":null,\"name\":\"Kugelschreiber blau/rot, Kunststoff\",\"unitType\":null,\"qualityControlFeatures\":[]},\"name\":\"Insti/MO/00027\",\"priority\":0,\"deadline\":\"2024-05-31T15:37:59\",\"status\":\"PLANNED\",\"notes\":[],\"keyValueMap\":{}},\"plannedStartDateTime\":\"2024-11-21T00:10:00.142\",\"plannedEndDateTime\":\"2024-11-21T00:17:30.142\",\"actualStartDateTime\":null,\"actualEndDateTime\":null,\"camundaProcessName\":null,\"name\":\"Insti/MO/00027\",\"status\":\"PLANNED\",\"subProductionSteps\":[],\"activeToolSettings\":[],\"totalProductionNumbers\":null,\"timeDurations\":{},\"department\":null},{\"id\":3,\"externalId\":null,\"versionNr\":null,\"subMachineOccupations\":[],\"productionSteps\":[{\"id\":49,\"externalId\":\"Insti/MO/00027/53\",\"versionNr\":null,\"camundaProcessName\":\"Subprocess_Basis\",\"machineType\":{\"id\":15,\"externalId\":\"001\",\"versionNr\":null,\"name\":\"Spritzgießen\",\"departments\":[{\"id\":1,\"externalId\":\"2\",\"versionNr\":null,\"name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"}]},\"toolType\":null,\"product\":{\"id\":8,\"externalId\":\"Insti/MO/00027_106\",\"versionNr\":null,\"name\":\"Kugelschreiber blau/rot, Kunststoff\",\"unitType\":null,\"qualityControlFeatures\":[]},\"name\":\"Spritzgießen Druckhülse\",\"productionDuration\":7.000000000,\"setupTime\":0.0,\"sequence\":102,\"productionStepInfo\":null}],\"machine\":{\"id\":23,\"externalId\":\"10\",\"versionNr\":null,\"machineType\":{\"id\":15,\"externalId\":\"001\",\"versionNr\":null,\"name\":\"Spritzgießen\",\"departments\":[{\"id\":1,\"externalId\":\"2\",\"versionNr\":null,\"name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"}]},\"name\":\"Spritzgießen A\"},\"tool\":null,\"availableTools\":[],\"productionOrder\":{\"id\":84,\"externalId\":\"Insti/MO/00027\",\"versionNr\":\"\",\"measurement\":{\"amount\":150.0,\"unitString\":\"pcs\"},\"customerOrder\":{\"id\":76,\"externalId\":\"\",\"versionNr\":null,\"name\":\"\",\"customerName\":\"\",\"priority\":0,\"deadline\":null},\"product\":{\"id\":8,\"externalId\":\"Insti/MO/00027_106\",\"versionNr\":null,\"name\":\"Kugelschreiber blau/rot, Kunststoff\",\"unitType\":null,\"qualityControlFeatures\":[]},\"name\":\"Insti/MO/00027\",\"priority\":0,\"deadline\":\"2024-05-31T15:37:59\",\"status\":\"PLANNED\",\"notes\":[],\"keyValueMap\":{}},\"plannedStartDateTime\":\"2024-11-21T00:22:30.142\",\"plannedEndDateTime\":\"2024-11-21T00:40:00.142\",\"actualStartDateTime\":null,\"actualEndDateTime\":null,\"camundaProcessName\":null,\"name\":\"Insti/MO/00027\",\"status\":\"PLANNED\",\"subProductionSteps\":[],\"activeToolSettings\":[],\"totalProductionNumbers\":null,\"timeDurations\":{},\"department\":null},{\"id\":4,\"externalId\":null,\"versionNr\":null,\"subMachineOccupations\":[],\"productionSteps\":[{\"id\":50,\"externalId\":\"Insti/MO/00027/54\",\"versionNr\":null,\"camundaProcessName\":\"Subprocess_Basis\",\"machineType\":{\"id\":16,\"externalId\":\"002\",\"versionNr\":null,\"name\":\"Wickeln\",\"departments\":[{\"id\":1,\"externalId\":\"2\",\"versionNr\":null,\"name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"}]},\"toolType\":null,\"product\":{\"id\":8,\"externalId\":\"Insti/MO/00027_106\",\"versionNr\":null,\"name\":\"Kugelschreiber blau/rot, Kunststoff\",\"unitType\":null,\"qualityControlFeatures\":[]},\"name\":\"Wickeln\",\"productionDuration\":5.000000000,\"setupTime\":0.0,\"sequence\":103,\"productionStepInfo\":null}],\"machine\":null,\"tool\":null,\"availableTools\":[],\"productionOrder\":{\"id\":84,\"externalId\":\"Insti/MO/00027\",\"versionNr\":\"\",\"measurement\":{\"amount\":150.0,\"unitString\":\"pcs\"},\"customerOrder\":{\"id\":76,\"externalId\":\"\",\"versionNr\":null,\"name\":\"\",\"customerName\":\"\",\"priority\":0,\"deadline\":null},\"product\":{\"id\":8,\"externalId\":\"Insti/MO/00027_106\",\"versionNr\":null,\"name\":\"Kugelschreiber blau/rot, Kunststoff\",\"unitType\":null,\"qualityControlFeatures\":[]},\"name\":\"Insti/MO/00027\",\"priority\":0,\"deadline\":\"2024-05-31T15:37:59\",\"status\":\"PLANNED\",\"notes\":[],\"keyValueMap\":{}},\"plannedStartDateTime\":\"2024-11-21T00:45:00.142\",\"plannedEndDateTime\":\"2024-11-21T00:57:30.142\",\"actualStartDateTime\":null,\"actualEndDateTime\":null,\"camundaProcessName\":null,\"name\":\"Insti/MO/00027\",\"status\":\"PLANNED\",\"subProductionSteps\":[],\"activeToolSettings\":[],\"totalProductionNumbers\":null,\"timeDurations\":{},\"department\":null},{\"id\":5,\"externalId\":null,\"versionNr\":null,\"subMachineOccupations\":[],\"productionSteps\":[{\"id\":51,\"externalId\":\"Insti/MO/00027/55\",\"versionNr\":null,\"camundaProcessName\":\"Subprocess_Basis\",\"machineType\":{\"id\":17,\"externalId\":\"003\",\"versionNr\":null,\"name\":\"Tiefziehen\",\"departments\":[{\"id\":1,\"externalId\":\"2\",\"versionNr\":null,\"name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"}]},\"toolType\":null,\"product\":{\"id\":8,\"externalId\":\"Insti/MO/00027_106\",\"versionNr\":null,\"name\":\"Kugelschreiber blau/rot, Kunststoff\",\"unitType\":null,\"qualityControlFeatures\":[]},\"name\":\"Tiefziehen\",\"productionDuration\":10.000000000,\"setupTime\":0.0,\"sequence\":104,\"productionStepInfo\":null}],\"machine\":null,\"tool\":null,\"availableTools\":[],\"productionOrder\":{\"id\":84,\"externalId\":\"Insti/MO/00027\",\"versionNr\":\"\",\"measurement\":{\"amount\":150.0,\"unitString\":\"pcs\"},\"customerOrder\":{\"id\":76,\"externalId\":\"\",\"versionNr\":null,\"name\":\"\",\"customerName\":\"\",\"priority\":0,\"deadline\":null},\"product\":{\"id\":8,\"externalId\":\"Insti/MO/00027_106\",\"versionNr\":null,\"name\":\"Kugelschreiber blau/rot, Kunststoff\",\"unitType\":null,\"qualityControlFeatures\":[]},\"name\":\"Insti/MO/00027\",\"priority\":0,\"deadline\":\"2024-05-31T15:37:59\",\"status\":\"PLANNED\",\"notes\":[],\"keyValueMap\":{}},\"plannedStartDateTime\":\"2024-11-21T01:02:30.142\",\"plannedEndDateTime\":\"2024-11-21T01:27:30.142\",\"actualStartDateTime\":null,\"actualEndDateTime\":null,\"camundaProcessName\":null,\"name\":\"Insti/MO/00027\",\"status\":\"PLANNED\",\"subProductionSteps\":[],\"activeToolSettings\":[],\"totalProductionNumbers\":null,\"timeDurations\":{},\"department\":null},{\"id\":6,\"externalId\":null,\"versionNr\":null,\"subMachineOccupations\":[],\"productionSteps\":[{\"id\":52,\"externalId\":\"Insti/MO/00027/56\",\"versionNr\":null,\"camundaProcessName\":\"Subprocess_Montage\",\"machineType\":{\"id\":14,\"externalId\":\"004\",\"versionNr\":null,\"name\":\"Handmontage\",\"departments\":[{\"id\":1,\"externalId\":\"2\",\"versionNr\":null,\"name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"}]},\"toolType\":null,\"product\":{\"id\":8,\"externalId\":\"Insti/MO/00027_106\",\"versionNr\":null,\"name\":\"Kugelschreiber blau/rot, Kunststoff\",\"unitType\":null,\"qualityControlFeatures\":[]},\"name\":\"Montage Griff\",\"productionDuration\":45.000000000,\"setupTime\":0.0,\"sequence\":105,\"productionStepInfo\":null}],\"machine\":null,\"tool\":null,\"availableTools\":[],\"productionOrder\":{\"id\":84,\"externalId\":\"Insti/MO/00027\",\"versionNr\":\"\",\"measurement\":{\"amount\":150.0,\"unitString\":\"pcs\"},\"customerOrder\":{\"id\":76,\"externalId\":\"\",\"versionNr\":null,\"name\":\"\",\"customerName\":\"\",\"priority\":0,\"deadline\":null},\"product\":{\"id\":8,\"externalId\":\"Insti/MO/00027_106\",\"versionNr\":null,\"name\":\"Kugelschreiber blau/rot, Kunststoff\",\"unitType\":null,\"qualityControlFeatures\":[]},\"name\":\"Insti/MO/00027\",\"priority\":0,\"deadline\":\"2024-05-31T15:37:59\",\"status\":\"PLANNED\",\"notes\":[],\"keyValueMap\":{}},\"plannedStartDateTime\":\"2024-11-21T01:32:30.142\",\"plannedEndDateTime\":\"2024-11-21T03:25:00.142\",\"actualStartDateTime\":null,\"actualEndDateTime\":null,\"camundaProcessName\":null,\"name\":\"Insti/MO/00027\",\"status\":\"PLANNED\",\"subProductionSteps\":[],\"activeToolSettings\":[],\"totalProductionNumbers\":null,\"timeDurations\":{},\"department\":null},{\"id\":7,\"externalId\":null,\"versionNr\":null,\"subMachineOccupations\":[],\"productionSteps\":[{\"id\":53,\"externalId\":\"Insti/MO/00027/57\",\"versionNr\":null,\"camundaProcessName\":\"Subprocess_Montage\",\"machineType\":{\"id\":18,\"externalId\":\"005\",\"versionNr\":null,\"name\":\"Endmontage\",\"departments\":[{\"id\":1,\"externalId\":\"2\",\"versionNr\":null,\"name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"}]},\"toolType\":null,\"product\":{\"id\":8,\"externalId\":\"Insti/MO/00027_106\",\"versionNr\":null,\"name\":\"Kugelschreiber blau/rot, Kunststoff\",\"unitType\":null,\"qualityControlFeatures\":[]},\"name\":\"Montage Kugelschreiber\",\"productionDuration\":60.000000000,\"setupTime\":0.0,\"sequence\":106,\"productionStepInfo\":null}],\"machine\":{\"id\":27,\"externalId\":\"15\",\"versionNr\":null,\"machineType\":{\"id\":18,\"externalId\":\"005\",\"versionNr\":null,\"name\":\"Endmontage\",\"departments\":[{\"id\":1,\"externalId\":\"2\",\"versionNr\":null,\"name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"}]},\"name\":\"Endmontage\"},\"tool\":null,\"availableTools\":[],\"productionOrder\":{\"id\":84,\"externalId\":\"Insti/MO/00027\",\"versionNr\":\"\",\"measurement\":{\"amount\":150.0,\"unitString\":\"pcs\"},\"customerOrder\":{\"id\":76,\"externalId\":\"\",\"versionNr\":null,\"name\":\"\",\"customerName\":\"\",\"priority\":0,\"deadline\":null},\"product\":{\"id\":8,\"externalId\":\"Insti/MO/00027_106\",\"versionNr\":null,\"name\":\"Kugelschreiber blau/rot, Kunststoff\",\"unitType\":null,\"qualityControlFeatures\":[]},\"name\":\"Insti/MO/00027\",\"priority\":0,\"deadline\":\"2024-05-31T15:37:59\",\"status\":\"PLANNED\",\"notes\":[],\"keyValueMap\":{}},\"plannedStartDateTime\":\"2024-11-21T03:34:05.126\",\"plannedEndDateTime\":\"2024-11-21T06:04:05.126\",\"actualStartDateTime\":null,\"actualEndDateTime\":null,\"camundaProcessName\":null,\"name\":\"Insti/MO/00027\",\"status\":\"PLANNED\",\"subProductionSteps\":[],\"activeToolSettings\":[],\"totalProductionNumbers\":null,\"timeDurations\":{},\"department\":null},{\"id\":8,\"externalId\":null,\"versionNr\":null,\"subMachineOccupations\":[],\"productionSteps\":[{\"id\":54,\"externalId\":\"Insti/MO/00027/58\",\"versionNr\":null,\"camundaProcessName\":\"Subprocess_QS\",\"machineType\":{\"id\":19,\"externalId\":\"006\",\"versionNr\":null,\"name\":\"QS Prüfung\",\"departments\":[{\"id\":1,\"externalId\":\"2\",\"versionNr\":null,\"name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"}]},\"toolType\":null,\"product\":{\"id\":8,\"externalId\":\"Insti/MO/00027_106\",\"versionNr\":null,\"name\":\"Kugelschreiber blau/rot, Kunststoff\",\"unitType\":null,\"qualityControlFeatures\":[]},\"name\":\"QS Stiftprüfung\",\"productionDuration\":10.000000000,\"setupTime\":0.0,\"sequence\":107,\"productionStepInfo\":null}],\"machine\":{\"id\":28,\"externalId\":\"20\",\"versionNr\":null,\"machineType\":{\"id\":19,\"externalId\":\"006\",\"versionNr\":null,\"name\":\"QS Prüfung\",\"departments\":[{\"id\":1,\"externalId\":\"2\",\"versionNr\":null,\"name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"}]},\"name\":\"QS Prüfung\"},\"tool\":null,\"availableTools\":[],\"productionOrder\":{\"id\":84,\"externalId\":\"Insti/MO/00027\",\"versionNr\":\"\",\"measurement\":{\"amount\":150.0,\"unitString\":\"pcs\"},\"customerOrder\":{\"id\":76,\"externalId\":\"\",\"versionNr\":null,\"name\":\"\",\"customerName\":\"\",\"priority\":0,\"deadline\":null},\"product\":{\"id\":8,\"externalId\":\"Insti/MO/00027_106\",\"versionNr\":null,\"name\":\"Kugelschreiber blau/rot, Kunststoff\",\"unitType\":null,\"qualityControlFeatures\":[]},\"name\":\"Insti/MO/00027\",\"priority\":0,\"deadline\":\"2024-05-31T15:37:59\",\"status\":\"PLANNED\",\"notes\":[],\"keyValueMap\":{}},\"plannedStartDateTime\":\"2024-11-21T06:05:00.142\",\"plannedEndDateTime\":\"2024-11-21T06:30:00.142\",\"actualStartDateTime\":null,\"actualEndDateTime\":null,\"camundaProcessName\":null,\"name\":\"Insti/MO/00027\",\"status\":\"PLANNED\",\"subProductionSteps\":[],\"activeToolSettings\":[],\"totalProductionNumbers\":null,\"timeDurations\":{},\"department\":null},{\"id\":9,\"externalId\":null,\"versionNr\":null,\"subMachineOccupations\":[],\"productionSteps\":[{\"id\":55,\"externalId\":\"Insti/MO/00027/59\",\"versionNr\":null,\"camundaProcessName\":\"Subprocess_Verpackung\",\"machineType\":{\"id\":20,\"externalId\":\"007\",\"versionNr\":null,\"name\":\"Verpackung\",\"departments\":[{\"id\":1,\"externalId\":\"2\",\"versionNr\":null,\"name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"}]},\"toolType\":null,\"product\":{\"id\":8,\"externalId\":\"Insti/MO/00027_106\",\"versionNr\":null,\"name\":\"Kugelschreiber blau/rot, Kunststoff\",\"unitType\":null,\"qualityControlFeatures\":[]},\"name\":\"Verpacken\",\"productionDuration\":30.000000000,\"setupTime\":0.0,\"sequence\":108,\"productionStepInfo\":null}],\"machine\":{\"id\":29,\"externalId\":\"18\",\"versionNr\":null,\"machineType\":{\"id\":20,\"externalId\":\"007\",\"versionNr\":null,\"name\":\"Verpackung\",\"departments\":[{\"id\":1,\"externalId\":\"2\",\"versionNr\":null,\"name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"}]},\"name\":\"Verpackung\"},\"tool\":null,\"availableTools\":[],\"productionOrder\":{\"id\":84,\"externalId\":\"Insti/MO/00027\",\"versionNr\":\"\",\"measurement\":{\"amount\":150.0,\"unitString\":\"pcs\"},\"customerOrder\":{\"id\":76,\"externalId\":\"\",\"versionNr\":null,\"name\":\"\",\"customerName\":\"\",\"priority\":0,\"deadline\":null},\"product\":{\"id\":8,\"externalId\":\"Insti/MO/00027_106\",\"versionNr\":null,\"name\":\"Kugelschreiber blau/rot, Kunststoff\",\"unitType\":null,\"qualityControlFeatures\":[]},\"name\":\"Insti/MO/00027\",\"priority\":0,\"deadline\":\"2024-05-31T15:37:59\",\"status\":\"PLANNED\",\"notes\":[],\"keyValueMap\":{}},\"plannedStartDateTime\":\"2024-11-21T06:35:00.142\",\"plannedEndDateTime\":\"2024-11-21T07:50:00.142\",\"actualStartDateTime\":null,\"actualEndDateTime\":null,\"camundaProcessName\":null,\"name\":\"Insti/MO/00027\",\"status\":\"PLANNED\",\"subProductionSteps\":[],\"activeToolSettings\":[],\"totalProductionNumbers\":null,\"timeDurations\":{},\"department\":null},{\"id\":10,\"externalId\":null,\"versionNr\":null,\"subMachineOccupations\":[],\"productionSteps\":[{\"id\":56,\"externalId\":\"Insti/MO/00027/60\",\"versionNr\":null,\"camundaProcessName\":\"Subprocess_Versand\",\"machineType\":{\"id\":21,\"externalId\":\"008\",\"versionNr\":null,\"name\":\"Versand\",\"departments\":[{\"id\":1,\"externalId\":\"2\",\"versionNr\":null,\"name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"}]},\"toolType\":null,\"product\":{\"id\":8,\"externalId\":\"Insti/MO/00027_106\",\"versionNr\":null,\"name\":\"Kugelschreiber blau/rot, Kunststoff\",\"unitType\":null,\"qualityControlFeatures\":[]},\"name\":\"Versand\",\"productionDuration\":30.000000000,\"setupTime\":0.0,\"sequence\":109,\"productionStepInfo\":null}],\"machine\":{\"id\":30,\"externalId\":\"19\",\"versionNr\":null,\"machineType\":{\"id\":21,\"externalId\":\"008\",\"versionNr\":null,\"name\":\"Versand\",\"departments\":[{\"id\":1,\"externalId\":\"2\",\"versionNr\":null,\"name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"}]},\"name\":\"Versand\"},\"tool\":null,\"availableTools\":[],\"productionOrder\":{\"id\":84,\"externalId\":\"Insti/MO/00027\",\"versionNr\":\"\",\"measurement\":{\"amount\":150.0,\"unitString\":\"pcs\"},\"customerOrder\":{\"id\":76,\"externalId\":\"\",\"versionNr\":null,\"name\":\"\",\"customerName\":\"\",\"priority\":0,\"deadline\":null},\"product\":{\"id\":8,\"externalId\":\"Insti/MO/00027_106\",\"versionNr\":null,\"name\":\"Kugelschreiber blau/rot, Kunststoff\",\"unitType\":null,\"qualityControlFeatures\":[]},\"name\":\"Insti/MO/00027\",\"priority\":0,\"deadline\":\"2024-05-31T15:37:59\",\"status\":\"PLANNED\",\"notes\":[],\"keyValueMap\":{}},\"plannedStartDateTime\":\"2024-11-21T07:55:00.142\",\"plannedEndDateTime\":\"2024-11-21T09:10:00.142\",\"actualStartDateTime\":null,\"actualEndDateTime\":null,\"camundaProcessName\":null,\"name\":\"Insti/MO/00027\",\"status\":\"PLANNED\",\"subProductionSteps\":[],\"activeToolSettings\":[],\"totalProductionNumbers\":null,\"timeDurations\":{},\"department\":null}]'),
(74, NULL, '{\"processVariables\":{\"varSubProdStepId\":\"Beispiel Eingabetext\"},\"info_WorkorderText\":\"x:120, y:10, ...\",\"ff_Checked\":false,\"ff_Note_1_1\":\"Beispiel Eingabetext\"}'),
(78, NULL, '{\"processVariables\":{},\"info_WorkorderText\":\"Beispiel Eingabetext\",\"ff_Checked\":false,\"ff_Note_1_1\":\"Beispiel Eingabetext\",\"ff_PartsOK\":1234,\"ff_PartsNOK\":1234}'),
(82, NULL, '{\"processVariables\":{},\"ff_PartsOK\":1234,\"ff_PartsNOK\":1234,\"ff_RejectionReason\":\"Enum Eingabetext\",\"ff_DisturbanceReason\":\"Enum Eingabetext\",\"ff_Note_2_1\":\"Beispiel Eingabetext\"}'),
(86, NULL, '{\"processVariables\":{\"varSubProductionStepId\":\"Beispiel Eingabetext\",\"varTotalOK\":\"Beispiel Eingabetext\",\"varTotalNOK\":\"Beispiel Eingabetext\"},\"info_TotalOK\":\"${machineOccupation.totalProductionNumbers.acceptedMeasurement.amount}\",\"info_TotalNOK\":\"${machineOccupation.totalProductionNumbers.rejectedMeasurement.amount}\",\"info_RemainingParts\":\"${machineOccupation.productionOrder.measurement.amount - machineOccupation.totalProductionNumbers.acceptedMeasurement.amount}\",\"ff_Note\":\"Beispiel Eingabetext\",\"ff_PartsOK\":1234,\"ff_PartsNOK\":1234,\"ff_ContinueWithLess\":false,\"ff_ManufacturingProblem\":\"7\",\"info_Drawing\":\"Beispiel Eingabetext\"}'),
(90, NULL, '{\"processVariables\":{\"varSubProductionStepId\":\"Beispiel Eingabetext\",\"varTotalOK\":\"Beispiel Eingabetext\",\"varTotalNOK\":\"Beispiel Eingabetext\"},\"info_WorkorderText\":\"Beispiel Eingabetext\",\"info_TotalOK\":\"${machineOccupation.totalProductionNumbers.acceptedMeasurement.amount}\",\"info_TotalNOK\":\"${machineOccupation.totalProductionNumbers.rejectedMeasurement.amount}\",\"info_RemainingParts\":\"${machineOccupation.productionOrder.measurement.amount - machineOccupation.totalProductionNumbers.acceptedMeasurement.amount}\",\"ff_Note\":\"Beispiel Eingabetext\",\"ff_PartsOK\":1234,\"ff_PartsNOK\":1234,\"ff_ManufacturingProblem\":\"7\",\"ff_ContinueWithLess\":false,\"info_Drawing\":\"Beispiel Eingabetext\"}');
INSERT INTO `ReaderResult` (`id`, `additionalData`, `result`) VALUES
(94, '27934', '{\"id\":38885,\"externalId\":\"363846005_1130\",\"versionNr\":null,\"subMachineOccupations\":[],\"productionSteps\":[{\"id\":38835,\"externalId\":\"363846_1130\",\"versionNr\":null,\"camundaProcessName\":\"Subprocess_Pressen_1130_1131_1133\",\"machineType\":{\"id\":29,\"externalId\":\"130\",\"versionNr\":null,\"name\":\"CBN Pressen 130\"},\"toolType\":null,\"product\":null,\"name\":\"CBN Pressen\",\"productionDuration\":4.200000000,\"setupTime\":0.0,\"sequence\":0,\"productionStepInfo\":{\"id\":7,\"externalId\":\"130_press_press\",\"versionNr\":null,\"stepGroup\":\"130_press\",\"stepIdent\":\"press\",\"stepType\":\"ALTERNATIVE\",\"stepNr\":0},\"mgrnr\":\"130\",\"arbgangNr\":\"1130\"}],\"machine\":null,\"tool\":null,\"availableTools\":[],\"productionOrder\":{\"id\":38847,\"externalId\":\"363846\",\"versionNr\":\"2023101014043200000069\",\"measurement\":{\"amount\":520.0,\"unitString\":\"pcs\"},\"customerOrder\":{\"id\":38846,\"externalId\":\"2073944_2\",\"versionNr\":null,\"name\":\"2073944_2\",\"customerName\":\"51801\",\"priority\":0,\"deadline\":\"2023-10-18T23:59:00\",\"note\":\"Sach-Nr. 51-24843\\n\",\"ysbvertr\":\"Ss/Gm\"},\"product\":{\"id\":663,\"externalId\":\"58018\",\"versionNr\":\"2023101419125800000153\",\"name\":\"58018\",\"unitType\":null,\"qualityControlFeatures\":[],\"elemsuch\":\"SLSBMB1B0025\",\"elemname\":\"1,65 X 5,4  auf Gew.Sch. M2/2,1 SW2,5\",\"platzsuch\":null,\"ysm1\":\"1B\",\"ykorn1\":\"25\",\"yhart1\":\"N\",\"ygef1\":\"9\",\"ybind1\":\"VX164\",\"ybind2\":\"PM\",\"ynachb\":\"\",\"yspezgew\":2.0,\"ymgamma\":2.09,\"yagamma\":0.0,\"ykonz\":168.0,\"yform\":\"Sonderform\",\"yrand\":\"SDFNZ\",\"ytxbs\":\"G.S.M2/2,1SW2,5\",\"ytabm\":\"\",\"ydurch1\":1.65,\"ydtol\":\"+0,05\",\"ybreit1\":5.4,\"ybtol\":\"\",\"ybs\":0.0,\"ystol\":\"\",\"yausd1\":0.0,\"yausb1\":0.0,\"yausd2\":0.0,\"yausb2\":0.0,\"ykennz1\":\"\",\"ykennz2\":\"\",\"ykennz3\":\"\",\"ygetext\":\"\",\"ybem\":\"\",\"yvms\":\"35\",\"ybrand\":\"\",\"ycode\":\"\",\"yfarbkoerper\":\"\",\"yzeichnung\":\"assets/drawing/Zeichnungen/58018_ZG(3)6950_20160923.pdf\",\"yd1tol\":\"\",\"yb1tol\":\"\",\"yd2tol\":\"\",\"yb2tol\":\"\"},\"name\":\"363846\",\"priority\":0,\"deadline\":\"2023-10-18T23:59:00\",\"status\":\"PLANNED\",\"notes\":[],\"keyValueMap\":{},\"orderAmount\":500.0,\"orderType\":\"A\",\"startDateTime\":\"2023-09-15T00:00:00\",\"sumSteps\":16},\"plannedStartDateTime\":\"2023-09-21T00:00:00\",\"plannedEndDateTime\":\"2023-09-28T23:59:00\",\"actualStartDateTime\":null,\"actualEndDateTime\":null,\"camundaProcessName\":\"Process_Presse_CBN_pressen\",\"name\":\"005\",\"status\":\"RUNNING\",\"subProductionSteps\":[{\"id\":42537,\"externalId\":\"Activity_1811cd7_04f35dd1-5147-11ee-9446-0242c0a8e007_363846005_1130\",\"versionNr\":null,\"type\":null,\"note\":null,\"auxiliaryMaterials\":[],\"productionNumbers\":[{\"id\":42789,\"externalId\":null,\"versionNr\":null,\"acceptedMeasurement\":{\"amount\":60.0,\"unitString\":\"\"},\"rejectedMeasurement\":{\"amount\":6.0,\"unitString\":\"\"}}],\"qualityManagements\":[],\"setUps\":[],\"timeRecords\":[{\"id\":42538,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-12T10:33:20.021359\",\"endDateTime\":\"2023-09-12T12:03:55.415804\"},{\"id\":42591,\"externalId\":null,\"versionNr\":null,\"suspensionType\":{\"id\":28145,\"externalId\":null,\"versionNr\":null,\"name\":\"BREAK_DEFAULT\",\"description\":\"Generated break type for BREAK_DEFAULT\"},\"timeRecordType\":null,\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-12T12:03:55.415832\",\"endDateTime\":\"2023-09-12T12:29:34.266199\"},{\"id\":42592,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-12T12:29:34.266226\",\"endDateTime\":\"2023-09-12T15:55:51.697509\"}],\"timeDurations\":{},\"keyValueMap\":{}},{\"id\":42790,\"externalId\":\"Activity_1811cd7_15bd2eb3-5174-11ee-9446-0242c0a8e007_363846005_1130\",\"versionNr\":null,\"type\":null,\"note\":null,\"auxiliaryMaterials\":[],\"productionNumbers\":[{\"id\":44819,\"externalId\":null,\"versionNr\":null,\"acceptedMeasurement\":{\"amount\":65.0,\"unitString\":\"\"},\"rejectedMeasurement\":{\"amount\":4.0,\"unitString\":\"\"}}],\"qualityManagements\":[],\"setUps\":[],\"timeRecords\":[{\"id\":42791,\"externalId\":null,\"versionNr\":null,\"suspensionType\":{\"id\":28981,\"externalId\":null,\"versionNr\":null,\"name\":\"BREAK_ShiftChange\",\"description\":\"Generated break type for BREAK_ShiftChange\"},\"timeRecordType\":null,\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-12T15:56:03.11856\",\"endDateTime\":\"2023-09-13T07:09:01.295716\"},{\"id\":43043,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-13T07:09:01.295739\",\"endDateTime\":\"2023-09-13T08:25:45.18772\"},{\"id\":43079,\"externalId\":null,\"versionNr\":null,\"suspensionType\":{\"id\":28798,\"externalId\":null,\"versionNr\":null,\"name\":\"BREAK_OrderChange\",\"description\":\"Generated break type for BREAK_OrderChange\"},\"timeRecordType\":null,\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-13T08:25:45.18776\",\"endDateTime\":\"2023-09-13T09:49:26.741018\"},{\"id\":43346,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-13T09:49:26.74105\",\"endDateTime\":\"2023-09-13T11:42:24.118043\"},{\"id\":43929,\"externalId\":null,\"versionNr\":null,\"suspensionType\":{\"id\":28798,\"externalId\":null,\"versionNr\":null,\"name\":\"BREAK_OrderChange\",\"description\":\"Generated break type for BREAK_OrderChange\"},\"timeRecordType\":null,\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-13T11:42:24.118074\",\"endDateTime\":\"2023-09-13T13:33:44.159367\"},{\"id\":44198,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-13T13:33:44.159398\",\"endDateTime\":\"2023-09-13T13:38:11.525178\"},{\"id\":44338,\"externalId\":null,\"versionNr\":null,\"suspensionType\":{\"id\":30374,\"externalId\":null,\"versionNr\":null,\"name\":\"BREAK_GeneralExpense\",\"description\":\"Generated break type for BREAK_GeneralExpense\"},\"timeRecordType\":null,\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-13T13:38:11.525212\",\"endDateTime\":\"2023-09-13T14:24:59.055406\"},{\"id\":44649,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-13T14:24:59.055432\",\"endDateTime\":\"2023-09-13T15:54:32.435675\"}],\"timeDurations\":{},\"keyValueMap\":{}},{\"id\":44820,\"externalId\":\"Activity_1811cd7_10eacd57-523d-11ee-9446-0242c0a8e007_363846005_1130\",\"versionNr\":null,\"type\":null,\"note\":null,\"auxiliaryMaterials\":[],\"productionNumbers\":[{\"id\":46011,\"externalId\":null,\"versionNr\":null,\"acceptedMeasurement\":{\"amount\":95.0,\"unitString\":\"\"},\"rejectedMeasurement\":{\"amount\":10.0,\"unitString\":\"\"}}],\"qualityManagements\":[],\"setUps\":[],\"timeRecords\":[{\"id\":44821,\"externalId\":null,\"versionNr\":null,\"suspensionType\":{\"id\":28981,\"externalId\":null,\"versionNr\":null,\"name\":\"BREAK_ShiftChange\",\"description\":\"Generated break type for BREAK_ShiftChange\"},\"timeRecordType\":null,\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-13T15:54:43.749424\",\"endDateTime\":\"2023-09-14T07:06:54.133645\"},{\"id\":45057,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-14T07:06:54.133671\",\"endDateTime\":\"2023-09-14T09:00:37.927886\"},{\"id\":45278,\"externalId\":null,\"versionNr\":null,\"suspensionType\":{\"id\":28145,\"externalId\":null,\"versionNr\":null,\"name\":\"BREAK_DEFAULT\",\"description\":\"Generated break type for BREAK_DEFAULT\"},\"timeRecordType\":null,\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-14T09:00:37.927918\",\"endDateTime\":\"2023-09-14T09:16:03.58815\"},{\"id\":45284,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-14T09:16:03.588177\",\"endDateTime\":\"2023-09-14T12:00:05.020078\"},{\"id\":45541,\"externalId\":null,\"versionNr\":null,\"suspensionType\":{\"id\":28145,\"externalId\":null,\"versionNr\":null,\"name\":\"BREAK_DEFAULT\",\"description\":\"Generated break type for BREAK_DEFAULT\"},\"timeRecordType\":null,\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-14T12:00:05.02011\",\"endDateTime\":\"2023-09-14T12:30:06.540443\"},{\"id\":45557,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-14T12:30:06.540475\",\"endDateTime\":\"2023-09-14T15:27:18.294842\"}],\"timeDurations\":{},\"keyValueMap\":{}},{\"id\":46012,\"externalId\":\"Activity_1811cd7_6d53752d-5302-11ee-9446-0242c0a8e007_363846005_1130\",\"versionNr\":null,\"type\":null,\"note\":null,\"auxiliaryMaterials\":[],\"productionNumbers\":[{\"id\":47159,\"externalId\":null,\"versionNr\":null,\"acceptedMeasurement\":{\"amount\":40.0,\"unitString\":\"\"},\"rejectedMeasurement\":{\"amount\":2.0,\"unitString\":\"\"}}],\"qualityManagements\":[],\"setUps\":[],\"timeRecords\":[{\"id\":46013,\"externalId\":null,\"versionNr\":null,\"suspensionType\":{\"id\":28981,\"externalId\":null,\"versionNr\":null,\"name\":\"BREAK_ShiftChange\",\"description\":\"Generated break type for BREAK_ShiftChange\"},\"timeRecordType\":null,\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-14T15:27:32.920548\",\"endDateTime\":\"2023-09-15T07:02:02.324779\"},{\"id\":46429,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-15T07:02:02.324805\",\"endDateTime\":\"2023-09-15T09:00:18.480646\"},{\"id\":46652,\"externalId\":null,\"versionNr\":null,\"suspensionType\":{\"id\":28145,\"externalId\":null,\"versionNr\":null,\"name\":\"BREAK_DEFAULT\",\"description\":\"Generated break type for BREAK_DEFAULT\"},\"timeRecordType\":null,\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-15T09:00:18.480707\",\"endDateTime\":\"2023-09-15T09:15:03.998476\"},{\"id\":46658,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-15T09:15:03.998508\",\"endDateTime\":\"2023-09-15T11:13:41.425959\"}],\"timeDurations\":{},\"keyValueMap\":{}},{\"id\":47160,\"externalId\":\"Activity_1811cd7_29cd37a1-53a8-11ee-9446-0242c0a8e007_363846005_1130\",\"versionNr\":null,\"type\":null,\"note\":null,\"auxiliaryMaterials\":[],\"productionNumbers\":[{\"id\":50142,\"externalId\":null,\"versionNr\":null,\"acceptedMeasurement\":{\"amount\":50.0,\"unitString\":\"\"},\"rejectedMeasurement\":{\"amount\":0.0,\"unitString\":\"\"}}],\"qualityManagements\":[],\"setUps\":[],\"timeRecords\":[{\"id\":47161,\"externalId\":null,\"versionNr\":null,\"suspensionType\":{\"id\":28798,\"externalId\":null,\"versionNr\":null,\"name\":\"BREAK_OrderChange\",\"description\":\"Generated break type for BREAK_OrderChange\"},\"timeRecordType\":null,\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-15T11:14:06.256305\",\"endDateTime\":\"2023-09-15T12:46:03.145403\"},{\"id\":47264,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-15T12:46:03.145432\",\"endDateTime\":\"2023-09-15T13:03:28.946032\"},{\"id\":47266,\"externalId\":null,\"versionNr\":null,\"suspensionType\":{\"id\":28798,\"externalId\":null,\"versionNr\":null,\"name\":\"BREAK_OrderChange\",\"description\":\"Generated break type for BREAK_OrderChange\"},\"timeRecordType\":null,\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-15T13:03:28.946063\",\"endDateTime\":\"2023-09-18T09:19:31.588032\"},{\"id\":47948,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-18T09:19:31.588064\",\"endDateTime\":\"2023-09-18T11:59:08.295329\"},{\"id\":49614,\"externalId\":null,\"versionNr\":null,\"suspensionType\":{\"id\":28798,\"externalId\":null,\"versionNr\":null,\"name\":\"BREAK_OrderChange\",\"description\":\"Generated break type for BREAK_OrderChange\"},\"timeRecordType\":null,\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-18T11:59:08.295359\",\"endDateTime\":\"2023-09-18T14:35:10.572067\"},{\"id\":49961,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-18T14:35:10.572109\",\"endDateTime\":\"2023-09-18T15:50:43.299432\"}],\"timeDurations\":{\"break\":255124.918676314,\"work\":14109.434588000},\"keyValueMap\":{}},{\"id\":50143,\"externalId\":\"Activity_1811cd7_5e145541-562a-11ee-8705-0242ac170008_363846005_1130\",\"versionNr\":null,\"type\":null,\"note\":null,\"auxiliaryMaterials\":[],\"productionNumbers\":[{\"id\":61115,\"externalId\":null,\"versionNr\":null,\"acceptedMeasurement\":{\"amount\":97.0,\"unitString\":\"\"},\"rejectedMeasurement\":{\"amount\":3.0,\"unitString\":\"\"}}],\"qualityManagements\":[],\"setUps\":[],\"timeRecords\":[{\"id\":50144,\"externalId\":null,\"versionNr\":null,\"suspensionType\":{\"id\":28798,\"externalId\":null,\"versionNr\":null,\"name\":\"BREAK_OrderChange\",\"description\":\"Generated break type for BREAK_OrderChange\"},\"timeRecordType\":null,\"responseUser\":{\"id\":28226,\"externalId\":\"509\",\"versionNr\":null,\"userName\":\"sl\",\"fullUserName\":null},\"startDateTime\":\"2023-09-18T15:51:11.733445\",\"endDateTime\":\"2023-09-25T06:11:22.798407\"},{\"id\":60528,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":34590,\"externalId\":\"262\",\"versionNr\":null,\"userName\":\"sihe\",\"fullUserName\":null},\"startDateTime\":\"2023-09-25T06:11:22.798454\",\"endDateTime\":\"2023-09-25T09:00:20.652539\"},{\"id\":60686,\"externalId\":null,\"versionNr\":null,\"suspensionType\":{\"id\":28145,\"externalId\":null,\"versionNr\":null,\"name\":\"BREAK_DEFAULT\",\"description\":\"Generated break type for BREAK_DEFAULT\"},\"timeRecordType\":null,\"responseUser\":{\"id\":34590,\"externalId\":\"262\",\"versionNr\":null,\"userName\":\"sihe\",\"fullUserName\":null},\"startDateTime\":\"2023-09-25T09:00:20.652572\",\"endDateTime\":\"2023-09-25T09:22:23.95937\"},{\"id\":60690,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":34590,\"externalId\":\"262\",\"versionNr\":null,\"userName\":\"sihe\",\"fullUserName\":null},\"startDateTime\":\"2023-09-25T09:22:23.959403\",\"endDateTime\":\"2023-09-25T12:00:10.171557\"},{\"id\":60961,\"externalId\":null,\"versionNr\":null,\"suspensionType\":{\"id\":28145,\"externalId\":null,\"versionNr\":null,\"name\":\"BREAK_DEFAULT\",\"description\":\"Generated break type for BREAK_DEFAULT\"},\"timeRecordType\":null,\"responseUser\":{\"id\":34590,\"externalId\":\"262\",\"versionNr\":null,\"userName\":\"sihe\",\"fullUserName\":null},\"startDateTime\":\"2023-09-25T12:00:10.171591\",\"endDateTime\":\"2023-09-25T12:29:11.295037\"},{\"id\":60977,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":34590,\"externalId\":\"262\",\"versionNr\":null,\"userName\":\"sihe\",\"fullUserName\":null},\"startDateTime\":\"2023-09-25T12:29:11.295077\",\"endDateTime\":\"2023-09-25T14:56:40.392363\"}],\"timeDurations\":{\"break\":573075.495206489,\"work\":28453.163525452},\"keyValueMap\":{}},{\"id\":61116,\"externalId\":\"Activity_1811cd7_f8621115-5ba2-11ee-b1ac-0242c0a87006_363846005_1130\",\"versionNr\":null,\"type\":null,\"note\":null,\"auxiliaryMaterials\":[],\"productionNumbers\":[{\"id\":61534,\"externalId\":null,\"versionNr\":null,\"acceptedMeasurement\":{\"amount\":53.0,\"unitString\":\"\"},\"rejectedMeasurement\":{\"amount\":0.0,\"unitString\":\"\"}}],\"qualityManagements\":[],\"setUps\":[],\"timeRecords\":[{\"id\":61117,\"externalId\":null,\"versionNr\":null,\"suspensionType\":{\"id\":28981,\"externalId\":null,\"versionNr\":null,\"name\":\"BREAK_ShiftChange\",\"description\":\"Generated break type for BREAK_ShiftChange\"},\"timeRecordType\":null,\"responseUser\":{\"id\":34590,\"externalId\":\"262\",\"versionNr\":null,\"userName\":\"sihe\",\"fullUserName\":null},\"startDateTime\":\"2023-09-25T14:57:06.720402\",\"endDateTime\":\"2023-09-26T05:57:16.367516\"},{\"id\":61327,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":34590,\"externalId\":\"262\",\"versionNr\":null,\"userName\":\"sihe\",\"fullUserName\":null},\"startDateTime\":\"2023-09-26T05:57:16.367548\",\"endDateTime\":\"2023-09-26T09:00:14.888881\"},{\"id\":61401,\"externalId\":null,\"versionNr\":null,\"suspensionType\":{\"id\":28145,\"externalId\":null,\"versionNr\":null,\"name\":\"BREAK_DEFAULT\",\"description\":\"Generated break type for BREAK_DEFAULT\"},\"timeRecordType\":null,\"responseUser\":{\"id\":34590,\"externalId\":\"262\",\"versionNr\":null,\"userName\":\"sihe\",\"fullUserName\":null},\"startDateTime\":\"2023-09-26T09:00:14.888913\",\"endDateTime\":\"2023-09-26T09:18:37.145998\"},{\"id\":61405,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":34590,\"externalId\":\"262\",\"versionNr\":null,\"userName\":\"sihe\",\"fullUserName\":null},\"startDateTime\":\"2023-09-26T09:18:37.146033\",\"endDateTime\":\"2023-09-26T11:07:52.756811\"}],\"timeDurations\":{\"break\":55111.904199209,\"work\":17534.132110178},\"keyValueMap\":{}},{\"id\":61535,\"externalId\":\"Activity_1811cd7_2c804e77-5c4c-11ee-b1ac-0242c0a87006_363846005_1130\",\"versionNr\":null,\"type\":null,\"note\":null,\"auxiliaryMaterials\":[],\"productionNumbers\":[{\"id\":62721,\"externalId\":null,\"versionNr\":null,\"acceptedMeasurement\":{\"amount\":60.0,\"unitString\":\"\"},\"rejectedMeasurement\":{\"amount\":0.0,\"unitString\":\"\"}}],\"qualityManagements\":[],\"setUps\":[],\"timeRecords\":[{\"id\":61536,\"externalId\":null,\"versionNr\":null,\"suspensionType\":{\"id\":28981,\"externalId\":null,\"versionNr\":null,\"name\":\"BREAK_ShiftChange\",\"description\":\"Generated break type for BREAK_ShiftChange\"},\"timeRecordType\":null,\"responseUser\":{\"id\":34590,\"externalId\":\"262\",\"versionNr\":null,\"userName\":\"sihe\",\"fullUserName\":null},\"startDateTime\":\"2023-09-26T11:08:20.956613\",\"endDateTime\":\"2023-09-28T07:14:16.540912\"},{\"id\":62690,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":28223,\"externalId\":\"159\",\"versionNr\":null,\"userName\":\"al\",\"fullUserName\":null},\"startDateTime\":\"2023-09-28T07:14:16.540943\",\"endDateTime\":\"2023-09-28T09:00:10.629333\"},{\"id\":62717,\"externalId\":null,\"versionNr\":null,\"suspensionType\":{\"id\":28145,\"externalId\":null,\"versionNr\":null,\"name\":\"BREAK_DEFAULT\",\"description\":\"Generated break type for BREAK_DEFAULT\"},\"timeRecordType\":null,\"responseUser\":{\"id\":28223,\"externalId\":\"159\",\"versionNr\":null,\"userName\":\"al\",\"fullUserName\":null},\"startDateTime\":\"2023-09-28T09:00:10.629366\",\"endDateTime\":\"2023-09-28T09:15:06.122361\"},{\"id\":62718,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":28223,\"externalId\":\"159\",\"versionNr\":null,\"userName\":\"al\",\"fullUserName\":null},\"startDateTime\":\"2023-09-28T09:15:06.122397\",\"endDateTime\":\"2023-09-28T10:44:33.397768\"}],\"timeDurations\":{\"break\":159651.077294573,\"work\":11721.363761422},\"keyValueMap\":{}},{\"id\":62722,\"externalId\":\"Activity_1811cd7_3f41c60d-5ddb-11ee-b1ac-0242c0a87006_363846005_1130\",\"versionNr\":null,\"type\":null,\"note\":null,\"auxiliaryMaterials\":[],\"productionNumbers\":[{\"id\":62980,\"externalId\":null,\"versionNr\":null,\"acceptedMeasurement\":{\"amount\":0.0,\"unitString\":\"\"},\"rejectedMeasurement\":{\"amount\":0.0,\"unitString\":\"\"}}],\"qualityManagements\":[],\"setUps\":[],\"timeRecords\":[{\"id\":62723,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":28223,\"externalId\":\"159\",\"versionNr\":null,\"userName\":\"al\",\"fullUserName\":null},\"startDateTime\":\"2023-09-28T10:45:08.388083\",\"endDateTime\":\"2023-09-29T09:00:55.972431\"},{\"id\":62953,\"externalId\":null,\"versionNr\":null,\"suspensionType\":{\"id\":28145,\"externalId\":null,\"versionNr\":null,\"name\":\"BREAK_DEFAULT\",\"description\":\"Generated break type for BREAK_DEFAULT\"},\"timeRecordType\":null,\"responseUser\":{\"id\":28224,\"externalId\":\"176\",\"versionNr\":null,\"userName\":\"fh\",\"fullUserName\":null},\"startDateTime\":\"2023-09-29T09:00:55.972461\",\"endDateTime\":\"2023-09-29T09:16:18.651911\"},{\"id\":62958,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":28224,\"externalId\":\"176\",\"versionNr\":null,\"userName\":\"fh\",\"fullUserName\":null},\"startDateTime\":\"2023-09-29T09:16:18.651945\",\"endDateTime\":\"2023-09-29T10:37:41.387547\"}],\"timeDurations\":{\"break\":922.679450226,\"work\":85030.319950751},\"keyValueMap\":{}},{\"id\":62982,\"externalId\":\"Activity_1811cd7_75fa1e1e-5ea3-11ee-b1ac-0242c0a87006_363846005_1130\",\"versionNr\":null,\"type\":null,\"note\":null,\"auxiliaryMaterials\":[],\"productionNumbers\":[{\"id\":75321,\"externalId\":null,\"versionNr\":null,\"acceptedMeasurement\":{\"amount\":2.0,\"unitString\":\"\"},\"rejectedMeasurement\":{\"amount\":0.0,\"unitString\":\"\"}}],\"qualityManagements\":[],\"setUps\":[],\"timeRecords\":[{\"id\":62983,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":28224,\"externalId\":\"176\",\"versionNr\":null,\"userName\":\"fh\",\"fullUserName\":null},\"startDateTime\":\"2023-09-29T10:38:50.196249\",\"endDateTime\":\"2023-10-06T13:19:03.050368\"},{\"id\":68459,\"externalId\":null,\"versionNr\":null,\"suspensionType\":{\"id\":28798,\"externalId\":null,\"versionNr\":null,\"name\":\"BREAK_OrderChange\",\"description\":\"Generated break type for BREAK_OrderChange\"},\"timeRecordType\":null,\"responseUser\":{\"id\":10341,\"externalId\":\"169\",\"versionNr\":\"\",\"userName\":\"mf\",\"fullUserName\":\"\"},\"startDateTime\":\"2023-10-06T13:19:03.050399\",\"endDateTime\":\"2023-10-31T15:57:02.852634\"},{\"id\":75320,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":75270,\"externalId\":null,\"versionNr\":null,\"userName\":\"admin\",\"fullUserName\":null},\"startDateTime\":\"2023-10-31T15:57:02.853669\",\"endDateTime\":\"2023-10-31T15:57:12.724743\"}],\"timeDurations\":{\"break\":2169479.802235300,\"work\":614422.725192772},\"keyValueMap\":{}},{\"id\":75322,\"externalId\":null,\"versionNr\":null,\"type\":null,\"note\":null,\"auxiliaryMaterials\":[],\"productionNumbers\":[],\"qualityManagements\":[],\"setUps\":[],\"timeRecords\":[{\"id\":75323,\"externalId\":null,\"versionNr\":null,\"suspensionType\":null,\"timeRecordType\":{\"id\":28149,\"externalId\":null,\"versionNr\":null,\"name\":\"Manufacturing_ConfirmProductionTask\",\"description\":\"Default manufacturing type for ConfirmProductionTask\"},\"responseUser\":{\"id\":75270,\"externalId\":null,\"versionNr\":null,\"userName\":\"admin\",\"fullUserName\":null},\"startDateTime\":\"2023-10-31T15:57:15.89627\",\"endDateTime\":null}],\"timeDurations\":{},\"keyValueMap\":{}}],\"activeToolSettings\":[{\"id\":38848,\"externalId\":\"363846_cbn_wzb_130_ytform\",\"versionNr\":null,\"machine\":null,\"toolSettingParameter\":{\"id\":76,\"externalId\":\"cbn_wzb_130_ytform\",\"versionNr\":null,\"unitType\":null,\"name\":\"Form\"},\"measurement\":{\"amount\":0.0,\"unitString\":\"\"},\"settingsValue\":\"\"},{\"id\":38849,\"externalId\":\"363846_cbn_wzb_130_ytdupress\",\"versionNr\":null,\"machine\":null,\"toolSettingParameter\":{\"id\":79,\"externalId\":\"cbn_wzb_130_ytdupress\",\"versionNr\":null,\"unitType\":null,\"name\":\"Durchm./Länge\"},\"measurement\":{\"amount\":0.0,\"unitString\":\"\"},\"settingsValue\":\"2,08\"},{\"id\":38850,\"externalId\":\"363846_cbn_wzb_130_ytbrpress\",\"versionNr\":null,\"machine\":null,\"toolSettingParameter\":{\"id\":86,\"externalId\":\"cbn_wzb_130_ytbrpress\",\"versionNr\":null,\"unitType\":null,\"name\":\"Breite\"},\"measurement\":{\"amount\":0.0,\"unitString\":\"\"},\"settingsValue\":\"5,80\"},{\"id\":38851,\"externalId\":\"363846_cbn_wzb_130_ytbopress\",\"versionNr\":null,\"machine\":null,\"toolSettingParameter\":{\"id\":87,\"externalId\":\"cbn_wzb_130_ytbopress\",\"versionNr\":null,\"unitType\":null,\"name\":\"Bohrung/Höhe\"},\"measurement\":{\"amount\":0.0,\"unitString\":\"\"},\"settingsValue\":\"0,90\"},{\"id\":38852,\"externalId\":\"363846_cbn_wzb_130_ytsltpress\",\"versionNr\":null,\"machine\":null,\"toolSettingParameter\":{\"id\":88,\"externalId\":\"cbn_wzb_130_ytsltpress\",\"versionNr\":null,\"unitType\":null,\"name\":\"Sackloch Tiefe\"},\"measurement\":{\"amount\":0.0,\"unitString\":\"\"},\"settingsValue\":\"2,60\"},{\"id\":38853,\"externalId\":\"363846_cbn_wzb_130_ytaupress\",\"versionNr\":null,\"machine\":null,\"toolSettingParameter\":{\"id\":89,\"externalId\":\"cbn_wzb_130_ytaupress\",\"versionNr\":null,\"unitType\":null,\"name\":\"Info Pressen\"},\"measurement\":{\"amount\":0.0,\"unitString\":\"\"},\"settingsValue\":\"\"},{\"id\":38854,\"externalId\":\"363846_cbn_wzb_130_ytausdpress1\",\"versionNr\":null,\"machine\":null,\"toolSettingParameter\":{\"id\":91,\"externalId\":\"cbn_wzb_130_ytausdpress1\",\"versionNr\":null,\"unitType\":null,\"name\":\"Aussparung D 1\"},\"measurement\":{\"amount\":0.0,\"unitString\":\"\"},\"settingsValue\":\"0,00\"},{\"id\":38855,\"externalId\":\"363846_cbn_wzb_130_ytausdpress2\",\"versionNr\":null,\"machine\":null,\"toolSettingParameter\":{\"id\":95,\"externalId\":\"cbn_wzb_130_ytausdpress2\",\"versionNr\":null,\"unitType\":null,\"name\":\"Aussparung D 2\"},\"measurement\":{\"amount\":0.0,\"unitString\":\"\"},\"settingsValue\":\"0,00\"},{\"id\":38856,\"externalId\":\"363846_cbn_wzb_130_ytausbpress1\",\"versionNr\":null,\"machine\":null,\"toolSettingParameter\":{\"id\":96,\"externalId\":\"cbn_wzb_130_ytausbpress1\",\"versionNr\":null,\"unitType\":null,\"name\":\"Aussparung B 1\"},\"measurement\":{\"amount\":0.0,\"unitString\":\"\"},\"settingsValue\":\"0,00\"},{\"id\":38857,\"externalId\":\"363846_cbn_wzb_130_ytausbpress2\",\"versionNr\":null,\"machine\":null,\"toolSettingParameter\":{\"id\":97,\"externalId\":\"cbn_wzb_130_ytausbpress2\",\"versionNr\":null,\"unitType\":null,\"name\":\"Aussparung B 2\"},\"measurement\":{\"amount\":0.0,\"unitString\":\"\"},\"settingsValue\":\"0,00\"},{\"id\":38858,\"externalId\":\"363846_cbn_wzb_130_ytpressart\",\"versionNr\":null,\"machine\":null,\"toolSettingParameter\":{\"id\":98,\"externalId\":\"cbn_wzb_130_ytpressart\",\"versionNr\":null,\"unitType\":null,\"name\":\"Pressart\"},\"measurement\":{\"amount\":0.0,\"unitString\":\"\"},\"settingsValue\":\"GLATT\"},{\"id\":38859,\"externalId\":\"363846_cbn_wzb_130_yteprv\",\"versionNr\":null,\"machine\":null,\"toolSettingParameter\":{\"id\":102,\"externalId\":\"cbn_wzb_130_yteprv\",\"versionNr\":null,\"unitType\":null,\"name\":\"Pressvolumen\"},\"measurement\":{\"amount\":0.0,\"unitString\":\"\"},\"settingsValue\":\"0,0180\"},{\"id\":38860,\"externalId\":\"363846_cbn_wzb_130_yteprg\",\"versionNr\":null,\"machine\":null,\"toolSettingParameter\":{\"id\":104,\"externalId\":\"cbn_wzb_130_yteprg\",\"versionNr\":null,\"unitType\":null,\"name\":\"Pressgewicht\"},\"measurement\":{\"amount\":0.0,\"unitString\":\"\"},\"settingsValue\":\"0,0376\"}],\"totalProductionNumbers\":{\"id\":47272,\"externalId\":null,\"versionNr\":null,\"acceptedMeasurement\":{\"amount\":262.0,\"unitString\":\"\"},\"rejectedMeasurement\":{\"amount\":3.0,\"unitString\":\"\"}},\"timeDurations\":{\"break\":2958240.958385797,\"BREAK_DEFAULT\":5984.859774789,\"work\":757161.704540575,\"BREAK_ShiftChange\":212765.231413491,\"Manufacturing_ConfirmProductionTask\":771271.139128575,\"BREAK_OrderChange\":2994615.785873831},\"ptext\":\"für Zeitaufnahme Ma vertändigen\\n\\nauf Engoplatte nicht einstreuen/ AE\\nAuf rechtwinklige Einspannung des SL-\\nDorns achten, wegen Winkelfehler / 04.09.12Hg\\n\",\"datasheet\":null,\"drawing\":null,\"image\":null,\"instruction\":null,\"manual\":null,\"document\":null,\"misc\":null}');
INSERT INTO `ReaderResult` (`id`, `additionalData`, `result`) VALUES
(98, NULL, '{\"queryResult\": [{\"productionorder_amount\":150.00,\"machineoccupation_plannedstart\":1716534000000,\"cd_product_extid\":84,\"cd_machine_name\":\"Handmontage\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Griffrohr Holz\\\", \\\"en_US\\\": \\\"Griffrohr Holz\\\"}\",\"null\":false},\"productionorder_deadline\":1716588360000,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Handmontage\",\"cd_productionstep_number\":100,\"productionorder_extid\":\"Insti/MO/00004\",\"machineoccupation_name\":\"Montage Griff\",\"cd_productionstep_name\":\"Montage Griff\",\"cd_productionstep_extid\":\"Insti/MO/00004/20\",\"productionorder_name\":\"Insti/MO/00004\",\"cd_machinetype_extid\":14,\"cd_productionstep_duration\":906.00,\"machineoccupation_extid\":\"Insti/MO/00004/20\",\"cd_machine_extid\":14,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":50.00,\"machineoccupation_plannedstart\":1716805333532,\"cd_product_extid\":69,\"cd_machine_name\":\"Spritzgießen A\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Spitze Kunststoff\\\", \\\"en_US\\\": \\\"Spitze Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1716806867132,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Spritzgießen A\",\"cd_productionstep_number\":100,\"productionorder_extid\":\"Insti/MO/00014\",\"machineoccupation_name\":\"Spritzgießen Spitze\",\"cd_productionstep_name\":\"Spritzgießen Spitze\",\"cd_productionstep_extid\":\"Insti/MO/00014/23\",\"productionorder_name\":\"Insti/MO/00014\",\"cd_machinetype_extid\":10,\"cd_productionstep_duration\":25.56,\"machineoccupation_extid\":\"Insti/MO/00014/23\",\"cd_machine_extid\":10,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":50.00,\"machineoccupation_plannedstart\":1716803692000,\"cd_product_extid\":63,\"cd_machine_name\":\"Wickeln\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Druckfeder Metall\\\", \\\"en_US\\\": \\\"Druckfeder Metall\\\"}\",\"null\":false},\"productionorder_deadline\":1716894172000,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Wickeln\",\"cd_productionstep_number\":100,\"productionorder_extid\":\"Insti/MO/00015\",\"machineoccupation_name\":\"Wickeln\",\"cd_productionstep_name\":\"Wickeln\",\"cd_productionstep_extid\":\"Insti/MO/00015/24\",\"productionorder_name\":\"Insti/MO/00015\",\"cd_machinetype_extid\":12,\"cd_productionstep_duration\":1508.00,\"machineoccupation_extid\":\"Insti/MO/00015/24\",\"cd_machine_extid\":12,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":50.00,\"machineoccupation_plannedstart\":1716803774000,\"cd_product_extid\":71,\"cd_machine_name\":\"Spritzgießen A\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Griffrohr B blau\\\", \\\"en_US\\\": \\\"Griffrohr A blau\\\"}\",\"null\":false},\"productionorder_deadline\":1716804979400,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Spritzgießen A\",\"cd_productionstep_number\":100,\"productionorder_extid\":\"Insti/MO/00017\",\"machineoccupation_name\":\"Spritzgießen Griffrohr A\",\"cd_productionstep_name\":\"Spritzgießen Griffrohr A\",\"cd_productionstep_extid\":\"Insti/MO/00017/26\",\"productionorder_name\":\"Insti/MO/00017\",\"cd_machinetype_extid\":10,\"cd_productionstep_duration\":20.09,\"machineoccupation_extid\":\"Insti/MO/00017/26\",\"cd_machine_extid\":10,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":50.00,\"machineoccupation_plannedstart\":1716803851000,\"cd_product_extid\":87,\"cd_machine_name\":\"Handmontage\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Griffrohr blau, Clip Kunststoff\\\", \\\"en_US\\\": \\\"Griffrohr blau, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1716810211000,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Handmontage\",\"cd_productionstep_number\":100,\"productionorder_extid\":\"Insti/MO/00018\",\"machineoccupation_name\":\"Montage Griff\",\"cd_productionstep_name\":\"Montage Griff\",\"cd_productionstep_extid\":\"Insti/MO/00018/27\",\"productionorder_name\":\"Insti/MO/00018\",\"cd_machinetype_extid\":14,\"cd_productionstep_duration\":106.00,\"machineoccupation_extid\":\"Insti/MO/00018/27\",\"cd_machine_extid\":14,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":50.00,\"machineoccupation_plannedstart\":1716803891000,\"cd_product_extid\":72,\"cd_machine_name\":\"Spritzgießen A\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Griffrohr B blau\\\", \\\"en_US\\\": \\\"Griffrohr B blau\\\"}\",\"null\":false},\"productionorder_deadline\":1716825090800,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Spritzgießen A\",\"cd_productionstep_number\":100,\"productionorder_extid\":\"Insti/MO/00019\",\"machineoccupation_name\":\"Spritzgießen Griffrohr B\",\"cd_productionstep_name\":\"Spritzgießen Griffrohr B\",\"cd_productionstep_extid\":\"Insti/MO/00019/28\",\"productionorder_name\":\"Insti/MO/00019\",\"cd_machinetype_extid\":10,\"cd_productionstep_duration\":353.33,\"machineoccupation_extid\":\"Insti/MO/00019/28\",\"cd_machine_extid\":10,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":500.00,\"machineoccupation_plannedstart\":1716977784000,\"cd_product_extid\":106,\"cd_machine_name\":\"Spritzgießen A\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717059945600,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Spritzgießen A\",\"cd_productionstep_number\":100,\"productionorder_extid\":\"Insti/MO/00025\",\"machineoccupation_name\":\"Spritzgießen Griffrohr A\",\"cd_productionstep_name\":\"Spritzgießen Griffrohr A\",\"cd_productionstep_extid\":\"Insti/MO/00025/41\",\"productionorder_name\":\"Insti/MO/00025\",\"cd_machinetype_extid\":10,\"cd_productionstep_duration\":21.85,\"machineoccupation_extid\":\"Insti/MO/00025/41\",\"cd_machine_extid\":10,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":500.00,\"machineoccupation_plannedstart\":1716977784000,\"cd_product_extid\":106,\"cd_machine_name\":\"Spritzgießen B\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717059945600,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Spritzgießen B\",\"cd_productionstep_number\":101,\"productionorder_extid\":\"Insti/MO/00025\",\"machineoccupation_name\":\"Spritzgießen Griffrohr B\",\"cd_productionstep_name\":\"Spritzgießen Griffrohr B\",\"cd_productionstep_extid\":\"Insti/MO/00025/42\",\"productionorder_name\":\"Insti/MO/00025\",\"cd_machinetype_extid\":11,\"cd_productionstep_duration\":17.63,\"machineoccupation_extid\":\"Insti/MO/00025/42\",\"cd_machine_extid\":11,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":500.00,\"machineoccupation_plannedstart\":1716977784000,\"cd_product_extid\":106,\"cd_machine_name\":\"Spritzgießen B\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717059945600,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Spritzgießen B\",\"cd_productionstep_number\":102,\"productionorder_extid\":\"Insti/MO/00025\",\"machineoccupation_name\":\"Spritzgießen Druckhülse\",\"cd_productionstep_name\":\"Spritzgießen Druckhülse\",\"cd_productionstep_extid\":\"Insti/MO/00025/43\",\"productionorder_name\":\"Insti/MO/00025\",\"cd_machinetype_extid\":11,\"cd_productionstep_duration\":21.14,\"machineoccupation_extid\":\"Insti/MO/00025/43\",\"cd_machine_extid\":11,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":500.00,\"machineoccupation_plannedstart\":1716977784000,\"cd_product_extid\":106,\"cd_machine_name\":\"Wickeln\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717059945600,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Wickeln\",\"cd_productionstep_number\":103,\"productionorder_extid\":\"Insti/MO/00025\",\"machineoccupation_name\":\"Wickeln\",\"cd_productionstep_name\":\"Wickeln\",\"cd_productionstep_extid\":\"Insti/MO/00025/44\",\"productionorder_name\":\"Insti/MO/00025\",\"cd_machinetype_extid\":12,\"cd_productionstep_duration\":49.67,\"machineoccupation_extid\":\"Insti/MO/00025/44\",\"cd_machine_extid\":12,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":500.00,\"machineoccupation_plannedstart\":1716977784000,\"cd_product_extid\":106,\"cd_machine_name\":\"Tiefziehen\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717059945600,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Tiefziehen\",\"cd_productionstep_number\":104,\"productionorder_extid\":\"Insti/MO/00025\",\"machineoccupation_name\":\"Tiefziehen\",\"cd_productionstep_name\":\"Tiefziehen\",\"cd_productionstep_extid\":\"Insti/MO/00025/45\",\"productionorder_name\":\"Insti/MO/00025\",\"cd_machinetype_extid\":9,\"cd_productionstep_duration\":24.74,\"machineoccupation_extid\":\"Insti/MO/00025/45\",\"cd_machine_extid\":9,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":500.00,\"machineoccupation_plannedstart\":1716977784000,\"cd_product_extid\":106,\"cd_machine_name\":\"Handmontage\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717059945600,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Handmontage\",\"cd_productionstep_number\":105,\"productionorder_extid\":\"Insti/MO/00025\",\"machineoccupation_name\":\"Montage Griff\",\"cd_productionstep_name\":\"Montage Griff\",\"cd_productionstep_extid\":\"Insti/MO/00025/46\",\"productionorder_name\":\"Insti/MO/00025\",\"cd_machinetype_extid\":14,\"cd_productionstep_duration\":381.00,\"machineoccupation_extid\":\"Insti/MO/00025/46\",\"cd_machine_extid\":14,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":500.00,\"machineoccupation_plannedstart\":1716977784000,\"cd_product_extid\":106,\"cd_machine_name\":\"Endmontage\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717059945600,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Endmontage\",\"cd_productionstep_number\":106,\"productionorder_extid\":\"Insti/MO/00025\",\"machineoccupation_name\":\"Montage Kugelschreiber\",\"cd_productionstep_name\":\"Montage Kugelschreiber\",\"cd_productionstep_extid\":\"Insti/MO/00025/47\",\"productionorder_name\":\"Insti/MO/00025\",\"cd_machinetype_extid\":15,\"cd_productionstep_duration\":506.00,\"machineoccupation_extid\":\"Insti/MO/00025/47\",\"cd_machine_extid\":15,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":500.00,\"machineoccupation_plannedstart\":1716977784000,\"cd_product_extid\":106,\"cd_machine_name\":\"QS Prüfung\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717059945600,\"cd_department_extid\":2,\"cd_machinetype_name\":\"QS Prüfung\",\"cd_productionstep_number\":107,\"productionorder_extid\":\"Insti/MO/00025\",\"machineoccupation_name\":\"QS Stiftprüfung\",\"cd_productionstep_name\":\"QS Stiftprüfung\",\"cd_productionstep_extid\":\"Insti/MO/00025/48\",\"productionorder_name\":\"Insti/MO/00025\",\"cd_machinetype_extid\":20,\"cd_productionstep_duration\":83.33,\"machineoccupation_extid\":\"Insti/MO/00025/48\",\"cd_machine_extid\":20,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":500.00,\"machineoccupation_plannedstart\":1716977784000,\"cd_product_extid\":106,\"cd_machine_name\":\"Verpackung\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717059945600,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Verpackung\",\"cd_productionstep_number\":108,\"productionorder_extid\":\"Insti/MO/00025\",\"machineoccupation_name\":\"Verpacken\",\"cd_productionstep_name\":\"Verpacken\",\"cd_productionstep_extid\":\"Insti/MO/00025/49\",\"productionorder_name\":\"Insti/MO/00025\",\"cd_machinetype_extid\":18,\"cd_productionstep_duration\":14.00,\"machineoccupation_extid\":\"Insti/MO/00025/49\",\"cd_machine_extid\":18,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":500.00,\"machineoccupation_plannedstart\":1716977784000,\"cd_product_extid\":106,\"cd_machine_name\":\"Versand\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717059945600,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Versand\",\"cd_productionstep_number\":109,\"productionorder_extid\":\"Insti/MO/00025\",\"machineoccupation_name\":\"Versand\",\"cd_productionstep_name\":\"Versand\",\"cd_productionstep_extid\":\"Insti/MO/00025/50\",\"productionorder_name\":\"Insti/MO/00025\",\"cd_machinetype_extid\":19,\"cd_productionstep_duration\":250.00,\"machineoccupation_extid\":\"Insti/MO/00025/50\",\"cd_machine_extid\":19,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":150.00,\"machineoccupation_plannedstart\":1717139579000,\"cd_product_extid\":106,\"cd_machine_name\":\"Spritzgießen A\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717169879000,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Spritzgießen A\",\"cd_productionstep_number\":100,\"productionorder_extid\":\"Insti/MO/00027\",\"machineoccupation_name\":\"Spritzgießen Griffrohr A\",\"cd_productionstep_name\":\"Spritzgießen Griffrohr A\",\"cd_productionstep_extid\":\"Insti/MO/00027/51\",\"productionorder_name\":\"Insti/MO/00027\",\"cd_machinetype_extid\":10,\"cd_productionstep_duration\":5.00,\"machineoccupation_extid\":\"Insti/MO/00027/51\",\"cd_machine_extid\":10,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":150.00,\"machineoccupation_plannedstart\":1717139579000,\"cd_product_extid\":106,\"cd_machine_name\":\"Spritzgießen B\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717169879000,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Spritzgießen B\",\"cd_productionstep_number\":101,\"productionorder_extid\":\"Insti/MO/00027\",\"machineoccupation_name\":\"Spritzgießen Griffrohr B\",\"cd_productionstep_name\":\"Spritzgießen Griffrohr B\",\"cd_productionstep_extid\":\"Insti/MO/00027/52\",\"productionorder_name\":\"Insti/MO/00027\",\"cd_machinetype_extid\":11,\"cd_productionstep_duration\":7.50,\"machineoccupation_extid\":\"Insti/MO/00027/52\",\"cd_machine_extid\":11,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":150.00,\"machineoccupation_plannedstart\":1717139579000,\"cd_product_extid\":106,\"cd_machine_name\":\"Spritzgießen B\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717169879000,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Spritzgießen B\",\"cd_productionstep_number\":102,\"productionorder_extid\":\"Insti/MO/00027\",\"machineoccupation_name\":\"Spritzgießen Druckhülse\",\"cd_productionstep_name\":\"Spritzgießen Druckhülse\",\"cd_productionstep_extid\":\"Insti/MO/00027/53\",\"productionorder_name\":\"Insti/MO/00027\",\"cd_machinetype_extid\":11,\"cd_productionstep_duration\":17.50,\"machineoccupation_extid\":\"Insti/MO/00027/53\",\"cd_machine_extid\":11,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":150.00,\"machineoccupation_plannedstart\":1717139579000,\"cd_product_extid\":106,\"cd_machine_name\":\"Wickeln\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717169879000,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Wickeln\",\"cd_productionstep_number\":103,\"productionorder_extid\":\"Insti/MO/00027\",\"machineoccupation_name\":\"Wickeln\",\"cd_productionstep_name\":\"Wickeln\",\"cd_productionstep_extid\":\"Insti/MO/00027/54\",\"productionorder_name\":\"Insti/MO/00027\",\"cd_machinetype_extid\":12,\"cd_productionstep_duration\":12.50,\"machineoccupation_extid\":\"Insti/MO/00027/54\",\"cd_machine_extid\":12,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":150.00,\"machineoccupation_plannedstart\":1717139579000,\"cd_product_extid\":106,\"cd_machine_name\":\"Tiefziehen\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717169879000,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Tiefziehen\",\"cd_productionstep_number\":104,\"productionorder_extid\":\"Insti/MO/00027\",\"machineoccupation_name\":\"Tiefziehen\",\"cd_productionstep_name\":\"Tiefziehen\",\"cd_productionstep_extid\":\"Insti/MO/00027/55\",\"productionorder_name\":\"Insti/MO/00027\",\"cd_machinetype_extid\":9,\"cd_productionstep_duration\":25.00,\"machineoccupation_extid\":\"Insti/MO/00027/55\",\"cd_machine_extid\":9,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":150.00,\"machineoccupation_plannedstart\":1717139579000,\"cd_product_extid\":106,\"cd_machine_name\":\"Handmontage\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717169879000,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Handmontage\",\"cd_productionstep_number\":105,\"productionorder_extid\":\"Insti/MO/00027\",\"machineoccupation_name\":\"Montage Griff\",\"cd_productionstep_name\":\"Montage Griff\",\"cd_productionstep_extid\":\"Insti/MO/00027/56\",\"productionorder_name\":\"Insti/MO/00027\",\"cd_machinetype_extid\":14,\"cd_productionstep_duration\":112.50,\"machineoccupation_extid\":\"Insti/MO/00027/56\",\"cd_machine_extid\":14,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":150.00,\"machineoccupation_plannedstart\":1717139579000,\"cd_product_extid\":106,\"cd_machine_name\":\"Endmontage\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717169879000,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Endmontage\",\"cd_productionstep_number\":106,\"productionorder_extid\":\"Insti/MO/00027\",\"machineoccupation_name\":\"Montage Kugelschreiber\",\"cd_productionstep_name\":\"Montage Kugelschreiber\",\"cd_productionstep_extid\":\"Insti/MO/00027/57\",\"productionorder_name\":\"Insti/MO/00027\",\"cd_machinetype_extid\":15,\"cd_productionstep_duration\":150.00,\"machineoccupation_extid\":\"Insti/MO/00027/57\",\"cd_machine_extid\":15,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":150.00,\"machineoccupation_plannedstart\":1717139579000,\"cd_product_extid\":106,\"cd_machine_name\":\"QS Prüfung\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717169879000,\"cd_department_extid\":2,\"cd_machinetype_name\":\"QS Prüfung\",\"cd_productionstep_number\":107,\"productionorder_extid\":\"Insti/MO/00027\",\"machineoccupation_name\":\"QS Stiftprüfung\",\"cd_productionstep_name\":\"QS Stiftprüfung\",\"cd_productionstep_extid\":\"Insti/MO/00027/58\",\"productionorder_name\":\"Insti/MO/00027\",\"cd_machinetype_extid\":20,\"cd_productionstep_duration\":25.00,\"machineoccupation_extid\":\"Insti/MO/00027/58\",\"cd_machine_extid\":20,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":150.00,\"machineoccupation_plannedstart\":1717139579000,\"cd_product_extid\":106,\"cd_machine_name\":\"Verpackung\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717169879000,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Verpackung\",\"cd_productionstep_number\":108,\"productionorder_extid\":\"Insti/MO/00027\",\"machineoccupation_name\":\"Verpacken\",\"cd_productionstep_name\":\"Verpacken\",\"cd_productionstep_extid\":\"Insti/MO/00027/59\",\"productionorder_name\":\"Insti/MO/00027\",\"cd_machinetype_extid\":18,\"cd_productionstep_duration\":75.00,\"machineoccupation_extid\":\"Insti/MO/00027/59\",\"cd_machine_extid\":18,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":150.00,\"machineoccupation_plannedstart\":1717139579000,\"cd_product_extid\":106,\"cd_machine_name\":\"Versand\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717169879000,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Versand\",\"cd_productionstep_number\":109,\"productionorder_extid\":\"Insti/MO/00027\",\"machineoccupation_name\":\"Versand\",\"cd_productionstep_name\":\"Versand\",\"cd_productionstep_extid\":\"Insti/MO/00027/60\",\"productionorder_name\":\"Insti/MO/00027\",\"cd_machinetype_extid\":19,\"cd_productionstep_duration\":75.00,\"machineoccupation_extid\":\"Insti/MO/00027/60\",\"cd_machine_extid\":19,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":50.00,\"machineoccupation_plannedstart\":1717149082000,\"cd_product_extid\":106,\"cd_machine_name\":\"Spritzgießen A\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717159181800,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Spritzgießen A\",\"cd_productionstep_number\":100,\"productionorder_extid\":\"Insti/MO/00028\",\"machineoccupation_name\":\"Spritzgießen Griffrohr A\",\"cd_productionstep_name\":\"Spritzgießen Griffrohr A\",\"cd_productionstep_extid\":\"Insti/MO/00028/61\",\"productionorder_name\":\"Insti/MO/00028\",\"cd_machinetype_extid\":10,\"cd_productionstep_duration\":1.67,\"machineoccupation_extid\":\"Insti/MO/00028/61\",\"cd_machine_extid\":10,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":50.00,\"machineoccupation_plannedstart\":1717149082000,\"cd_product_extid\":106,\"cd_machine_name\":\"Spritzgießen B\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717159181800,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Spritzgießen B\",\"cd_productionstep_number\":101,\"productionorder_extid\":\"Insti/MO/00028\",\"machineoccupation_name\":\"Spritzgießen Griffrohr B\",\"cd_productionstep_name\":\"Spritzgießen Griffrohr B\",\"cd_productionstep_extid\":\"Insti/MO/00028/62\",\"productionorder_name\":\"Insti/MO/00028\",\"cd_machinetype_extid\":11,\"cd_productionstep_duration\":2.50,\"machineoccupation_extid\":\"Insti/MO/00028/62\",\"cd_machine_extid\":11,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":50.00,\"machineoccupation_plannedstart\":1717149082000,\"cd_product_extid\":106,\"cd_machine_name\":\"Spritzgießen B\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717159181800,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Spritzgießen B\",\"cd_productionstep_number\":102,\"productionorder_extid\":\"Insti/MO/00028\",\"machineoccupation_name\":\"Spritzgießen Druckhülse\",\"cd_productionstep_name\":\"Spritzgießen Druckhülse\",\"cd_productionstep_extid\":\"Insti/MO/00028/63\",\"productionorder_name\":\"Insti/MO/00028\",\"cd_machinetype_extid\":11,\"cd_productionstep_duration\":5.83,\"machineoccupation_extid\":\"Insti/MO/00028/63\",\"cd_machine_extid\":11,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":50.00,\"machineoccupation_plannedstart\":1717149082000,\"cd_product_extid\":106,\"cd_machine_name\":\"Wickeln\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717159181800,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Wickeln\",\"cd_productionstep_number\":103,\"productionorder_extid\":\"Insti/MO/00028\",\"machineoccupation_name\":\"Wickeln\",\"cd_productionstep_name\":\"Wickeln\",\"cd_productionstep_extid\":\"Insti/MO/00028/64\",\"productionorder_name\":\"Insti/MO/00028\",\"cd_machinetype_extid\":12,\"cd_productionstep_duration\":4.17,\"machineoccupation_extid\":\"Insti/MO/00028/64\",\"cd_machine_extid\":12,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":50.00,\"machineoccupation_plannedstart\":1717149082000,\"cd_product_extid\":106,\"cd_machine_name\":\"Tiefziehen\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717159181800,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Tiefziehen\",\"cd_productionstep_number\":104,\"productionorder_extid\":\"Insti/MO/00028\",\"machineoccupation_name\":\"Tiefziehen\",\"cd_productionstep_name\":\"Tiefziehen\",\"cd_productionstep_extid\":\"Insti/MO/00028/65\",\"productionorder_name\":\"Insti/MO/00028\",\"cd_machinetype_extid\":9,\"cd_productionstep_duration\":8.33,\"machineoccupation_extid\":\"Insti/MO/00028/65\",\"cd_machine_extid\":9,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":50.00,\"machineoccupation_plannedstart\":1717149082000,\"cd_product_extid\":106,\"cd_machine_name\":\"Handmontage\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717159181800,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Handmontage\",\"cd_productionstep_number\":105,\"productionorder_extid\":\"Insti/MO/00028\",\"machineoccupation_name\":\"Montage Griff\",\"cd_productionstep_name\":\"Montage Griff\",\"cd_productionstep_extid\":\"Insti/MO/00028/66\",\"productionorder_name\":\"Insti/MO/00028\",\"cd_machinetype_extid\":14,\"cd_productionstep_duration\":37.50,\"machineoccupation_extid\":\"Insti/MO/00028/66\",\"cd_machine_extid\":14,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":50.00,\"machineoccupation_plannedstart\":1717149082000,\"cd_product_extid\":106,\"cd_machine_name\":\"Endmontage\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717159181800,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Endmontage\",\"cd_productionstep_number\":106,\"productionorder_extid\":\"Insti/MO/00028\",\"machineoccupation_name\":\"Montage Kugelschreiber\",\"cd_productionstep_name\":\"Montage Kugelschreiber\",\"cd_productionstep_extid\":\"Insti/MO/00028/67\",\"productionorder_name\":\"Insti/MO/00028\",\"cd_machinetype_extid\":15,\"cd_productionstep_duration\":50.00,\"machineoccupation_extid\":\"Insti/MO/00028/67\",\"cd_machine_extid\":15,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":50.00,\"machineoccupation_plannedstart\":1717149082000,\"cd_product_extid\":106,\"cd_machine_name\":\"QS Prüfung\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717159181800,\"cd_department_extid\":2,\"cd_machinetype_name\":\"QS Prüfung\",\"cd_productionstep_number\":107,\"productionorder_extid\":\"Insti/MO/00028\",\"machineoccupation_name\":\"QS Stiftprüfung\",\"cd_productionstep_name\":\"QS Stiftprüfung\",\"cd_productionstep_extid\":\"Insti/MO/00028/68\",\"productionorder_name\":\"Insti/MO/00028\",\"cd_machinetype_extid\":20,\"cd_productionstep_duration\":8.33,\"machineoccupation_extid\":\"Insti/MO/00028/68\",\"cd_machine_extid\":20,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":50.00,\"machineoccupation_plannedstart\":1717149082000,\"cd_product_extid\":106,\"cd_machine_name\":\"Verpackung\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717159181800,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Verpackung\",\"cd_productionstep_number\":108,\"productionorder_extid\":\"Insti/MO/00028\",\"machineoccupation_name\":\"Verpacken\",\"cd_productionstep_name\":\"Verpacken\",\"cd_productionstep_extid\":\"Insti/MO/00028/69\",\"productionorder_name\":\"Insti/MO/00028\",\"cd_machinetype_extid\":18,\"cd_productionstep_duration\":25.00,\"machineoccupation_extid\":\"Insti/MO/00028/69\",\"cd_machine_extid\":18,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":50.00,\"machineoccupation_plannedstart\":1717149082000,\"cd_product_extid\":106,\"cd_machine_name\":\"Versand\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber blau/rot, Kunststoff\\\", \\\"en_US\\\": \\\"Kugelschreiber grün, Kunststoff\\\"}\",\"null\":false},\"productionorder_deadline\":1717159181800,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Versand\",\"cd_productionstep_number\":109,\"productionorder_extid\":\"Insti/MO/00028\",\"machineoccupation_name\":\"Versand\",\"cd_productionstep_name\":\"Versand\",\"cd_productionstep_extid\":\"Insti/MO/00028/70\",\"productionorder_name\":\"Insti/MO/00028\",\"cd_machinetype_extid\":19,\"cd_productionstep_duration\":25.00,\"machineoccupation_extid\":\"Insti/MO/00028/70\",\"cd_machine_extid\":19,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":5.00,\"machineoccupation_plannedstart\":1720429200000,\"cd_product_extid\":107,\"cd_machine_name\":\"Spritzgießen A\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber grau (FT)\\\", \\\"en_US\\\": \\\"Kugelschreiber grau\\\"}\",\"null\":false},\"productionorder_deadline\":1720432418400,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Spritzgießen A\",\"cd_productionstep_number\":103,\"productionorder_extid\":\"Insti/MO/00046\",\"machineoccupation_name\":\"Spritzgießen Schaft grau\",\"cd_productionstep_name\":\"Spritzgießen Schaft grau\",\"cd_productionstep_extid\":\"Insti/MO/00046/139\",\"productionorder_name\":\"Insti/MO/00046\",\"cd_machinetype_extid\":10,\"cd_productionstep_duration\":20.06,\"machineoccupation_extid\":\"Insti/MO/00046/139\",\"cd_machine_extid\":10,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":5.00,\"machineoccupation_plannedstart\":1720429200000,\"cd_product_extid\":107,\"cd_machine_name\":\"Handmontage\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber grau (FT)\\\", \\\"en_US\\\": \\\"Kugelschreiber grau\\\"}\",\"null\":false},\"productionorder_deadline\":1720432418400,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Handmontage\",\"cd_productionstep_number\":105,\"productionorder_extid\":\"Insti/MO/00046\",\"machineoccupation_name\":\"Montage Griff\",\"cd_productionstep_name\":\"Montage Griff\",\"cd_productionstep_extid\":\"Insti/MO/00046/140\",\"productionorder_name\":\"Insti/MO/00046\",\"cd_machinetype_extid\":14,\"cd_productionstep_duration\":9.75,\"machineoccupation_extid\":\"Insti/MO/00046/140\",\"cd_machine_extid\":14,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":5.00,\"machineoccupation_plannedstart\":1720429200000,\"cd_product_extid\":107,\"cd_machine_name\":\"Endmontage\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber grau (FT)\\\", \\\"en_US\\\": \\\"Kugelschreiber grau\\\"}\",\"null\":false},\"productionorder_deadline\":1720432418400,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Endmontage\",\"cd_productionstep_number\":106,\"productionorder_extid\":\"Insti/MO/00046\",\"machineoccupation_name\":\"Montage Kugelschreiber gesamt\",\"cd_productionstep_name\":\"Montage Kugelschreiber gesamt\",\"cd_productionstep_extid\":\"Insti/MO/00046/141\",\"productionorder_name\":\"Insti/MO/00046\",\"cd_machinetype_extid\":15,\"cd_productionstep_duration\":11.00,\"machineoccupation_extid\":\"Insti/MO/00046/141\",\"cd_machine_extid\":15,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":5.00,\"machineoccupation_plannedstart\":1720429200000,\"cd_product_extid\":107,\"cd_machine_name\":\"QS Prüfung\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber grau (FT)\\\", \\\"en_US\\\": \\\"Kugelschreiber grau\\\"}\",\"null\":false},\"productionorder_deadline\":1720432418400,\"cd_department_extid\":2,\"cd_machinetype_name\":\"QS Prüfung\",\"cd_productionstep_number\":107,\"productionorder_extid\":\"Insti/MO/00046\",\"machineoccupation_name\":\"QS Stiftprüfung\",\"cd_productionstep_name\":\"QS Stiftprüfung\",\"cd_productionstep_extid\":\"Insti/MO/00046/142\",\"productionorder_name\":\"Insti/MO/00046\",\"cd_machinetype_extid\":20,\"cd_productionstep_duration\":0.83,\"machineoccupation_extid\":\"Insti/MO/00046/142\",\"cd_machine_extid\":20,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":5.00,\"machineoccupation_plannedstart\":1720429200000,\"cd_product_extid\":107,\"cd_machine_name\":\"Verpackung\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber grau (FT)\\\", \\\"en_US\\\": \\\"Kugelschreiber grau\\\"}\",\"null\":false},\"productionorder_deadline\":1720432418400,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Verpackung\",\"cd_productionstep_number\":108,\"productionorder_extid\":\"Insti/MO/00046\",\"machineoccupation_name\":\"Verpacken\",\"cd_productionstep_name\":\"Verpacken\",\"cd_productionstep_extid\":\"Insti/MO/00046/143\",\"productionorder_name\":\"Insti/MO/00046\",\"cd_machinetype_extid\":18,\"cd_productionstep_duration\":9.50,\"machineoccupation_extid\":\"Insti/MO/00046/143\",\"cd_machine_extid\":18,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":5.00,\"machineoccupation_plannedstart\":1720429200000,\"cd_product_extid\":107,\"cd_machine_name\":\"Versand\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Kugelschreiber grau (FT)\\\", \\\"en_US\\\": \\\"Kugelschreiber grau\\\"}\",\"null\":false},\"productionorder_deadline\":1720432418400,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Versand\",\"cd_productionstep_number\":109,\"productionorder_extid\":\"Insti/MO/00046\",\"machineoccupation_name\":\"Versand\",\"cd_productionstep_name\":\"Versand\",\"cd_productionstep_extid\":\"Insti/MO/00046/144\",\"productionorder_name\":\"Insti/MO/00046\",\"cd_machinetype_extid\":19,\"cd_productionstep_duration\":2.50,\"machineoccupation_extid\":\"Insti/MO/00046/144\",\"cd_machine_extid\":19,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":5.00,\"machineoccupation_plannedstart\":1719471600000,\"cd_product_extid\":111,\"cd_machine_name\":\"Spritzgießen B\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Druckhülse weiß\\\", \\\"en_US\\\": \\\"Druckhülse weiß\\\"}\",\"null\":false},\"productionorder_deadline\":1719472501200,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Spritzgießen B\",\"cd_productionstep_number\":100,\"productionorder_extid\":\"Insti/MO/00047\",\"machineoccupation_name\":\"Spritzgießen Druckhülse weiß\",\"cd_productionstep_name\":\"Spritzgießen Druckhülse weiß\",\"cd_productionstep_extid\":\"Insti/MO/00047/145\",\"productionorder_name\":\"Insti/MO/00047\",\"cd_machinetype_extid\":11,\"cd_productionstep_duration\":15.02,\"machineoccupation_extid\":\"Insti/MO/00047/145\",\"cd_machine_extid\":11,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":688.00,\"machineoccupation_plannedstart\":1732179600000,\"cd_product_extid\":63,\"cd_machine_name\":\"Wickeln\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Druckfeder Metall\\\", \\\"en_US\\\": \\\"Druckfeder Metall\\\"}\",\"null\":false},\"productionorder_deadline\":1733418480000,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Wickeln\",\"cd_productionstep_number\":100,\"productionorder_extid\":\"Insti/MO/00049\",\"machineoccupation_name\":\"Wickeln\",\"cd_productionstep_name\":\"Wickeln\",\"cd_productionstep_extid\":\"Insti/MO/00049/146\",\"productionorder_name\":\"Insti/MO/00049\",\"cd_machinetype_extid\":12,\"cd_productionstep_duration\":20648.00,\"machineoccupation_extid\":\"Insti/MO/00049/146\",\"cd_machine_extid\":12,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"},{\"productionorder_amount\":700.00,\"machineoccupation_plannedstart\":1732179600000,\"cd_product_extid\":64,\"cd_machine_name\":\"Tiefziehen\",\"cd_product_name\":{\"type\":\"jsonb\",\"value\":\"{\\\"de_DE\\\": \\\"Spitze Metall\\\", \\\"en_US\\\": \\\"Spitze Metall\\\"}\",\"null\":false},\"productionorder_deadline\":1732623568200,\"cd_department_extid\":2,\"cd_machinetype_name\":\"Tiefziehen\",\"cd_productionstep_number\":100,\"productionorder_extid\":\"Insti/MO/00050\",\"machineoccupation_name\":\"Tiefziehen\",\"cd_productionstep_name\":\"Tiefziehen\",\"cd_productionstep_extid\":\"Insti/MO/00050/147\",\"productionorder_name\":\"Insti/MO/00050\",\"cd_machinetype_extid\":9,\"cd_productionstep_duration\":7399.47,\"machineoccupation_extid\":\"Insti/MO/00050/147\",\"cd_machine_extid\":9,\"cd_department_name\":\"Institut für Informationssysteme der Hochschule Hof - iisys\"}]}'),
(102, NULL, '{\"processVariables\":{\"varSubProductionStepId\":\"Beispiel Eingabetext\",\"varTotalOK\":\"Beispiel Eingabetext\",\"varTotalNOK\":\"Beispiel Eingabetext\"},\"info_TotalOK\":\"${machineOccupation.totalProductionNumbers.acceptedMeasurement.amount}\",\"info_TotalNOK\":\"${machineOccupation.totalProductionNumbers.rejectedMeasurement.amount}\",\"info_RemainingParts\":\"${machineOccupation.productionOrder.measurement.amount - machineOccupation.totalProductionNumbers.acceptedMeasurement.amount}\",\"ff_Note\":\"Beispiel Eingabetext\",\"ff_PartsOK\":1234,\"ff_PartsNOK\":1234,\"ff_ContinueWithLess\":false,\"ff_ManufacturingProblem\":\"7\",\"info_Video\":\"Beispiel Eingabetext\"}');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `SetUp`
--

CREATE TABLE `SetUp` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `subProductionStep_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `SingleFileReaderConfig`
--

CREATE TABLE `SingleFileReaderConfig` (
  `id` bigint NOT NULL,
  `path` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `SubProductionStep`
--

CREATE TABLE `SubProductionStep` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `submitType` int DEFAULT NULL,
  `type` int DEFAULT NULL,
  `machineOccupation_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `SubProductionStep`
--

INSERT INTO `SubProductionStep` (`id`, `createDateTime`, `externalId`, `updateDateTime`, `versionNr`, `note`, `submitType`, `type`, `machineOccupation_id`) VALUES
(213, '2024-11-25 13:19:24.223195', 'Activity_0s5lqmy_8162baa8-ab27-11ef-93e1-0242ac1d0004_Insti/MO/00004/20', '2024-11-25 13:19:24.223213', NULL, NULL, NULL, NULL, 139),
(222, '2024-11-25 13:21:10.585563', 'Activity_0279ebn_c0df52e3-ab27-11ef-93e1-0242ac1d0004_Insti/MO/00014/23', '2024-11-25 13:21:45.688649', NULL, 'Vorgezogen', 0, NULL, 142),
(226, '2024-11-25 13:21:46.864745', 'Activity_WZB_Rueckmeldung_Basis_d69333dd-ab27-11ef-93e1-0242ac1d0004_Insti/MO/00014/23', '2024-11-25 13:22:14.540555', NULL, NULL, 0, NULL, 142),
(234, '2024-11-25 13:22:15.653485', 'Activity_WZB_Rueckmeldung_Basis_e770ac93-ab27-11ef-93e1-0242ac1d0004_Insti/MO/00014/23', '2024-11-25 13:25:04.970795', NULL, NULL, 0, NULL, 142),
(239, '2024-11-25 13:24:07.082042', 'Activity_0279ebn_2a3d3322-ab28-11ef-93e1-0242ac1d0004_Insti/MO/00017/26', '2024-11-25 13:24:11.207341', NULL, '', 0, NULL, 145),
(243, '2024-11-25 13:24:11.905259', 'Activity_WZB_Rueckmeldung_Basis_2cfbde2d-ab28-11ef-93e1-0242ac1d0004_Insti/MO/00017/26', '2024-11-25 13:28:57.086070', NULL, NULL, 0, NULL, 145),
(250, '2024-11-25 13:25:05.660882', 'Activity_WZB_Rueckmeldung_Basis_4d07e8ce-ab28-11ef-93e1-0242ac1d0004_Insti/MO/00014/23', '2024-11-25 13:29:15.566924', NULL, NULL, 0, NULL, 142),
(258, '2024-11-25 13:29:16.333613', 'Activity_WZB_Rueckmeldung_Basis_e26472ee-ab28-11ef-93e1-0242ac1d0004_Insti/MO/00014/23', '2024-11-25 13:32:36.925847', NULL, NULL, 0, NULL, 142),
(265, '2024-11-25 13:32:56.228493', 'Activity_0279ebn_65adc3a7-ab29-11ef-93e1-0242ac1d0004_Insti/MO/00015/24', '2024-11-25 13:33:01.535155', NULL, '', 0, NULL, 148),
(270, '2024-11-25 13:33:02.138064', 'Activity_WZB_Rueckmeldung_Basis_690616a2-ab29-11ef-93e1-0242ac1d0004_Insti/MO/00015/24', '2024-11-25 13:33:09.237263', NULL, NULL, 0, NULL, 148);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `SuspensionType`
--

CREATE TABLE `SuspensionType` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `SuspensionType`
--

INSERT INTO `SuspensionType` (`id`, `createDateTime`, `externalId`, `updateDateTime`, `versionNr`, `description`, `name`) VALUES
(217, '2024-11-25 13:20:05.806048', NULL, '2024-11-25 13:20:05.806069', NULL, 'Generated break type for BREAK_OrderChange', 'BREAK_OrderChange');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `TimeRecord`
--

CREATE TABLE `TimeRecord` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `endDateTime` datetime(6) DEFAULT NULL,
  `startDateTime` datetime(6) DEFAULT NULL,
  `responseUser_id` bigint DEFAULT NULL,
  `subProductionStep_id` bigint DEFAULT NULL,
  `suspensionType_id` bigint DEFAULT NULL,
  `timeRecordType_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `TimeRecord`
--

INSERT INTO `TimeRecord` (`id`, `createDateTime`, `externalId`, `updateDateTime`, `versionNr`, `endDateTime`, `startDateTime`, `responseUser_id`, `subProductionStep_id`, `suspensionType_id`, `timeRecordType_id`) VALUES
(214, '2024-11-25 13:19:24.230059', NULL, '2024-11-25 13:20:05.830725', NULL, '2024-11-25 13:20:05.808251', '2024-11-25 13:19:24.133306', 1, 213, NULL, 212),
(216, '2024-11-25 13:19:24.242938', NULL, '2024-11-25 13:20:05.831922', NULL, '2024-11-25 13:20:05.808188', '2024-11-25 13:19:24.134021', 1, NULL, NULL, 212),
(218, '2024-11-25 13:20:05.825875', NULL, '2024-11-25 13:20:05.825894', NULL, NULL, '2024-11-25 13:20:05.808358', 1, 213, 217, NULL),
(219, '2024-11-25 13:20:05.827616', NULL, '2024-11-25 13:20:05.827628', NULL, NULL, '2024-11-25 13:20:05.808204', 1, NULL, 217, NULL),
(223, '2024-11-25 13:21:10.587001', NULL, '2024-11-25 13:21:45.690412', NULL, '2024-11-25 13:21:45.673258', '2024-11-25 13:21:10.489756', 4, 222, NULL, 221),
(225, '2024-11-25 13:21:10.591742', NULL, '2024-11-25 13:21:45.692657', NULL, '2024-11-25 13:21:45.683714', '2024-11-25 13:21:10.489784', 4, NULL, NULL, 221),
(227, '2024-11-25 13:21:46.866880', NULL, '2024-11-25 13:22:14.541878', NULL, '2024-11-25 13:22:14.514827', '2024-11-25 13:21:46.737784', 4, 226, NULL, 212),
(229, '2024-11-25 13:21:46.871739', NULL, '2024-11-25 13:22:14.544686', NULL, '2024-11-25 13:22:14.525410', '2024-11-25 13:21:46.737933', 4, NULL, NULL, 212),
(235, '2024-11-25 13:22:15.662274', NULL, '2024-11-25 13:25:04.972448', NULL, '2024-11-25 13:25:04.950897', '2024-11-25 13:22:15.479033', 4, 234, NULL, 212),
(237, '2024-11-25 13:22:15.674204', NULL, '2024-11-25 13:25:04.973989', NULL, '2024-11-25 13:25:04.955800', '2024-11-25 13:22:15.479118', 4, NULL, NULL, 212),
(240, '2024-11-25 13:24:07.083282', NULL, '2024-11-25 13:24:11.208650', NULL, '2024-11-25 13:24:11.196181', '2024-11-25 13:24:06.988081', 3, 239, NULL, 221),
(242, '2024-11-25 13:24:07.085540', NULL, '2024-11-25 13:24:11.210408', NULL, '2024-11-25 13:24:11.203320', '2024-11-25 13:24:06.988105', 3, NULL, NULL, 221),
(244, '2024-11-25 13:24:11.914090', NULL, '2024-11-25 13:28:57.087064', NULL, '2024-11-25 13:28:57.066230', '2024-11-25 13:24:11.713558', 3, 243, NULL, 212),
(246, '2024-11-25 13:24:11.922883', NULL, '2024-11-25 13:28:57.088332', NULL, '2024-11-25 13:28:57.070515', '2024-11-25 13:24:11.713588', 3, NULL, NULL, 212),
(251, '2024-11-25 13:25:05.662458', NULL, '2024-11-25 13:29:15.568469', NULL, '2024-11-25 13:29:15.546316', '2024-11-25 13:25:05.573061', 4, 250, NULL, 212),
(253, '2024-11-25 13:25:05.664963', NULL, '2024-11-25 13:29:15.570155', NULL, '2024-11-25 13:29:15.551480', '2024-11-25 13:25:05.573090', 4, NULL, NULL, 212),
(259, '2024-11-25 13:29:16.334800', NULL, '2024-11-25 13:32:36.926771', NULL, '2024-11-25 13:32:36.909296', '2024-11-25 13:29:16.234908', 4, 258, NULL, 212),
(261, '2024-11-25 13:29:16.339810', NULL, '2024-11-25 13:32:36.928517', NULL, '2024-11-25 13:32:36.913395', '2024-11-25 13:29:16.234949', 4, NULL, NULL, 212),
(266, '2024-11-25 13:32:56.229790', NULL, '2024-11-25 13:33:01.536012', NULL, '2024-11-25 13:33:01.527137', '2024-11-25 13:32:56.178306', 4, 265, NULL, 221),
(268, '2024-11-25 13:32:56.231715', NULL, '2024-11-25 13:33:01.537140', NULL, '2024-11-25 13:33:01.532414', '2024-11-25 13:32:56.178334', 4, NULL, NULL, 221),
(271, '2024-11-25 13:33:02.139152', NULL, '2024-11-25 13:33:09.238321', NULL, '2024-11-25 13:33:09.219651', '2024-11-25 13:33:01.982060', 4, 270, NULL, 212),
(273, '2024-11-25 13:33:02.141082', NULL, '2024-11-25 13:33:09.240420', NULL, '2024-11-25 13:33:09.225700', '2024-11-25 13:33:01.982087', 4, NULL, NULL, 212);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `TimeRecordType`
--

CREATE TABLE `TimeRecordType` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `TimeRecordType`
--

INSERT INTO `TimeRecordType` (`id`, `createDateTime`, `externalId`, `updateDateTime`, `versionNr`, `description`, `name`) VALUES
(212, '2024-11-25 13:19:24.123413', NULL, '2024-11-25 13:19:24.123433', NULL, 'Default manufacturing type for ConfirmProductionTask', 'Manufacturing_ConfirmProductionTask'),
(221, '2024-11-25 13:21:10.478468', NULL, '2024-11-25 13:21:10.478487', NULL, 'Default manufacturing type for SetParameterTask', 'Manufacturing_SetParameterTask');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Time_Durations`
--

CREATE TABLE `Time_Durations` (
  `machineOccupation` bigint NOT NULL,
  `duration` bigint DEFAULT NULL,
  `timeType` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `Time_Durations`
--

INSERT INTO `Time_Durations` (`machineOccupation`, `duration`, `timeType`) VALUES
(139, 41, 'Manufacturing_ConfirmProductionTask'),
(139, 41, 'work'),
(142, 645, 'Manufacturing_ConfirmProductionTask'),
(142, 35, 'Manufacturing_SetParameterTask'),
(142, 680, 'work'),
(145, 285, 'Manufacturing_ConfirmProductionTask'),
(145, 4, 'Manufacturing_SetParameterTask'),
(145, 289, 'work'),
(148, 7, 'Manufacturing_ConfirmProductionTask'),
(148, 5, 'Manufacturing_SetParameterTask'),
(148, 12, 'work');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Time_DurationsStep`
--

CREATE TABLE `Time_DurationsStep` (
  `subProductionStep` bigint NOT NULL,
  `duration` bigint DEFAULT NULL,
  `timeType` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `Time_DurationsStep`
--

INSERT INTO `Time_DurationsStep` (`subProductionStep`, `duration`, `timeType`) VALUES
(213, 41, 'work'),
(222, 35, 'work'),
(226, 27, 'work'),
(234, 169, 'work'),
(239, 4, 'work'),
(243, 285, 'work'),
(250, 249, 'work'),
(258, 200, 'work'),
(265, 5, 'work'),
(270, 7, 'work');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ToolSetting`
--

CREATE TABLE `ToolSetting` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `amount` double NOT NULL,
  `unitString` varchar(255) DEFAULT NULL,
  `machine_id` bigint DEFAULT NULL,
  `tool_id` bigint DEFAULT NULL,
  `toolSettingParameter_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `User`
--

CREATE TABLE `User` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `fullUserName` varchar(255) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `User`
--

INSERT INTO `User` (`id`, `createDateTime`, `externalId`, `updateDateTime`, `versionNr`, `fullUserName`, `userName`) VALUES
(1, '2024-11-25 12:17:54.608327', '001', '2024-11-25 12:17:54.608535', NULL, NULL, 'admin'),
(2, '2024-11-25 12:17:54.678503', '002', '2024-11-25 12:17:54.678635', NULL, NULL, 'rpeinl'),
(3, '2024-11-25 12:17:54.722492', '003', '2024-11-25 12:17:54.722653', NULL, NULL, 'ffunke'),
(4, '2024-11-25 12:17:54.759034', '004', '2024-11-25 12:17:54.759179', NULL, NULL, 'llustig'),
(5, '2024-11-25 12:17:54.794414', '005', '2024-11-25 12:17:54.794745', NULL, NULL, 'bbecker');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `UserOccupation`
--

CREATE TABLE `UserOccupation` (
  `id` bigint NOT NULL,
  `createDateTime` datetime(6) DEFAULT NULL,
  `externalId` varchar(255) DEFAULT NULL,
  `updateDateTime` datetime(6) DEFAULT NULL,
  `versionNr` varchar(255) DEFAULT NULL,
  `machineOccupation_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `UserOccupation`
--

INSERT INTO `UserOccupation` (`id`, `createDateTime`, `externalId`, `updateDateTime`, `versionNr`, `machineOccupation_id`) VALUES
(211, '2024-11-25 13:19:23.319671', NULL, '2024-11-25 13:19:23.319692', NULL, 139),
(220, '2024-11-25 13:21:09.938263', NULL, '2024-11-25 13:21:09.938283', NULL, 142),
(238, '2024-11-25 13:24:06.718207', NULL, '2024-11-25 13:24:06.718245', NULL, 145),
(264, '2024-11-25 13:32:55.949634', NULL, '2024-11-25 13:32:55.949650', NULL, 148);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `UserOccupation_ActiveUsers`
--

CREATE TABLE `UserOccupation_ActiveUsers` (
  `user_occupation_id` bigint NOT NULL,
  `user_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `UserOccupation_ActiveUsers`
--

INSERT INTO `UserOccupation_ActiveUsers` (`user_occupation_id`, `user_id`) VALUES
(220, 4),
(238, 3),
(264, 4);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `UserOccupation_InactiveUsers`
--

CREATE TABLE `UserOccupation_InactiveUsers` (
  `user_occupation_id` bigint NOT NULL,
  `user_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `UserOccupation_InactiveUsers`
--

INSERT INTO `UserOccupation_InactiveUsers` (`user_occupation_id`, `user_id`) VALUES
(211, 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `UserOccupation_TimeRecords`
--

CREATE TABLE `UserOccupation_TimeRecords` (
  `user_occupation_id` bigint NOT NULL,
  `timerecord_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Daten für Tabelle `UserOccupation_TimeRecords`
--

INSERT INTO `UserOccupation_TimeRecords` (`user_occupation_id`, `timerecord_id`) VALUES
(211, 216),
(211, 219),
(220, 225),
(220, 229),
(220, 237),
(220, 253),
(220, 261),
(238, 242),
(238, 246),
(264, 268),
(264, 273);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `AllClassExtension`
--
ALTER TABLE `AllClassExtension`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `AllClassExtension_ClassExtension`
--
ALTER TABLE `AllClassExtension_ClassExtension`
  ADD UNIQUE KEY `UK_4ov0sqd9pv36j7s2jpffgpk9x` (`classes_fieldId`),
  ADD KEY `FKsl9nqf0opi6qcsfkvouc3uvls` (`AllClassExtension_id`);

--
-- Indizes für die Tabelle `AuxiliaryMaterials`
--
ALTER TABLE `AuxiliaryMaterials`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_nkycpffhxwyirhuom4w3wj480` (`externalId`),
  ADD KEY `IDXnkycpffhxwyirhuom4w3wj480` (`externalId`),
  ADD KEY `FKf7sx5xmth3q2iahypud0egnwl` (`subProductionStep_id`);

--
-- Indizes für die Tabelle `BookingEntry`
--
ALTER TABLE `BookingEntry`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_i2cw9fgwss9p2pddyw584315w` (`externalId`),
  ADD KEY `IDXi2cw9fgwss9p2pddyw584315w` (`externalId`),
  ADD KEY `FKe57614byrdted49wqd2p7dji6` (`machineOccupation_id`),
  ADD KEY `FK2su0kingqav2ycdmq5nofvv9g` (`overheadCost_id`),
  ADD KEY `FKqltonrbscwefmvvccv11pq8ij` (`subProductionStep_id`),
  ADD KEY `FKrk8p1g8i0wjcu45vhvbhdx8t6` (`user_id`);

--
-- Indizes für die Tabelle `CamundaMachineOccupation`
--
ALTER TABLE `CamundaMachineOccupation`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_jdfx8unh43wgtlrp3k4v4nsvv` (`externalId`),
  ADD KEY `FKq8s4j4t7w2q3k9agxjj74rry` (`activeProductionStep_id`),
  ADD KEY `FKc63xbi76wyvghct4ydefrommi` (`machineOccupation_id`);

--
-- Indizes für die Tabelle `CamundaSubProductionStep`
--
ALTER TABLE `CamundaSubProductionStep`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_b2rm7d6x2w8ecim33cpa8bhev` (`externalId`),
  ADD KEY `FKnjhhoiiqv41p8s56gevyywwan` (`camundaMachineOccupation_id`),
  ADD KEY `FKi2cd4bpxbv5kbvsywhwf42os7` (`subProductionStep_id`);

--
-- Indizes für die Tabelle `CD_Department`
--
ALTER TABLE `CD_Department`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_1r6kwjcm82mcqmyvhuffdvekw` (`externalId`),
  ADD KEY `IDX1r6kwjcm82mcqmyvhuffdvekw` (`externalId`);

--
-- Indizes für die Tabelle `CD_Machine`
--
ALTER TABLE `CD_Machine`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_hr3iqab554n74p064cdgt4ohc` (`externalId`),
  ADD KEY `IDXhr3iqab554n74p064cdgt4ohc` (`externalId`),
  ADD KEY `FK8lw9mactxdm38bbpevbsad5lm` (`machineType_id`);

--
-- Indizes für die Tabelle `CD_MachineType`
--
ALTER TABLE `CD_MachineType`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_kl2ctuplwq4ac7rarsvlv55oq` (`externalId`),
  ADD KEY `IDXkl2ctuplwq4ac7rarsvlv55oq` (`externalId`);

--
-- Indizes für die Tabelle `CD_MachineType_CD_Department`
--
ALTER TABLE `CD_MachineType_CD_Department`
  ADD KEY `FK199tkfduon9efd11fx49ljsxy` (`department`),
  ADD KEY `FK6bekpt5aptm36gbevhfl6lgwy` (`machineType`);

--
-- Indizes für die Tabelle `CD_OverheadCostCenter`
--
ALTER TABLE `CD_OverheadCostCenter`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_fb6deh7co5w5tck3ps4tn7yde` (`externalId`),
  ADD KEY `IDXfb6deh7co5w5tck3ps4tn7yde` (`externalId`);

--
-- Indizes für die Tabelle `CD_Product`
--
ALTER TABLE `CD_Product`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_6iwyl105cpk2ygqg2ylbs5v6t` (`externalId`),
  ADD KEY `IDX6iwyl105cpk2ygqg2ylbs5v6t` (`externalId`);

--
-- Indizes für die Tabelle `CD_ProductionStep`
--
ALTER TABLE `CD_ProductionStep`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_p8gw6l96nu65li6p642951scs` (`externalId`),
  ADD KEY `IDXp8gw6l96nu65li6p642951scs` (`externalId`),
  ADD KEY `FKbjk4kl61mdfkrgcspu5e2b78v` (`machineType_id`),
  ADD KEY `FKmr55o5mi3p20tlv64594hlqvi` (`product_id`),
  ADD KEY `FKrvi0x20nmjhjrns7mk684bnlr` (`productionStepInfo_id`),
  ADD KEY `FKjumxna5ixaku6nx666nfsemtu` (`toolType_id`);

--
-- Indizes für die Tabelle `CD_ProductionStep_CD_ToolSettingParameter`
--
ALTER TABLE `CD_ProductionStep_CD_ToolSettingParameter`
  ADD KEY `FKacp0g1is6sywjh764l7ux9nso` (`toolSettingParameter`),
  ADD KEY `FKe581p7l5x6ncl6qeqx6p97hu2` (`productionStep`);

--
-- Indizes für die Tabelle `CD_ProductRelationship`
--
ALTER TABLE `CD_ProductRelationship`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_ntvu9568cpp9f8anheia8mnyt` (`externalId`),
  ADD KEY `IDXntvu9568cpp9f8anheia8mnyt` (`externalId`),
  ADD KEY `FKk9bn38khwphcd8i8nwjnlu1aq` (`part_id`),
  ADD KEY `FKr3ja421hlnm417gvujip09mjd` (`product_id`);

--
-- Indizes für die Tabelle `CD_QualityControlFeature`
--
ALTER TABLE `CD_QualityControlFeature`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_gmh0tejmfqtyujfl3yfojko31` (`externalId`),
  ADD KEY `IDXgmh0tejmfqtyujfl3yfojko31` (`externalId`),
  ADD KEY `FKnadd80qngiko3cnbn3d2tp14c` (`product_id`);

--
-- Indizes für die Tabelle `CD_Tool`
--
ALTER TABLE `CD_Tool`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_kyrv9gs2duv7em4j7qung98v9` (`externalId`),
  ADD KEY `IDXkyrv9gs2duv7em4j7qung98v9` (`externalId`),
  ADD KEY `FK752h21a2xeipy5pik45fxtux5` (`toolType_id`);

--
-- Indizes für die Tabelle `CD_ToolSettingParameter`
--
ALTER TABLE `CD_ToolSettingParameter`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_pdyffwa6hsyswaxot0str1m4n` (`externalId`),
  ADD KEY `IDXpdyffwa6hsyswaxot0str1m4n` (`externalId`),
  ADD KEY `FKjr5o9ihndlx7wrabkd4wkl3fp` (`machineType_id`),
  ADD KEY `FKeftav1vkqik90p7w15yh10998` (`toolType_id`);

--
-- Indizes für die Tabelle `CD_ToolType`
--
ALTER TABLE `CD_ToolType`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_eo5ad83wtyp3uj1ym0dc5ty2n` (`externalId`),
  ADD UNIQUE KEY `UK_o1hdgibtmjcsl270ta0s4tee8` (`number`),
  ADD KEY `IDXeo5ad83wtyp3uj1ym0dc5ty2n` (`externalId`);

--
-- Indizes für die Tabelle `ClassExtension`
--
ALTER TABLE `ClassExtension`
  ADD PRIMARY KEY (`fieldId`);

--
-- Indizes für die Tabelle `ClassExtension_MemberExtension`
--
ALTER TABLE `ClassExtension_MemberExtension`
  ADD UNIQUE KEY `UK_sobie9ohpokq1sxu834elb3tq` (`members_id`),
  ADD KEY `FKoar6p2ya1ktyqmno9pmydwtyo` (`ClassExtension_fieldId`);

--
-- Indizes für die Tabelle `CsvReaderParserConfig`
--
ALTER TABLE `CsvReaderParserConfig`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `CustomerOrder`
--
ALTER TABLE `CustomerOrder`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_65ufwkhi2uw8m1c4yhgaj1p7d` (`externalId`),
  ADD KEY `IDX65ufwkhi2uw8m1c4yhgaj1p7d` (`externalId`);

--
-- Indizes für die Tabelle `DataReader`
--
ALTER TABLE `DataReader`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `DataWriter`
--
ALTER TABLE `DataWriter`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `HicumesSettings`
--
ALTER TABLE `HicumesSettings`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_gik9mabqtm3yrq1rdgp3upef7` (`externalId`);

--
-- Indizes für die Tabelle `KeyValueMapProductionOrder`
--
ALTER TABLE `KeyValueMapProductionOrder`
  ADD PRIMARY KEY (`productionOrder`,`keyString`);

--
-- Indizes für die Tabelle `KeyValueMapSubProductionStep`
--
ALTER TABLE `KeyValueMapSubProductionStep`
  ADD PRIMARY KEY (`subProductionStep`,`keyString`);

--
-- Indizes für die Tabelle `MachineOccupation`
--
ALTER TABLE `MachineOccupation`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_3xcyi8n2dljdcecsewtgle3u8` (`externalId`),
  ADD KEY `IDX3xcyi8n2dljdcecsewtgle3u8` (`externalId`),
  ADD KEY `FKl0eis6d23p4ufhooiqbbaoc6t` (`department_id`),
  ADD KEY `FK8rafq5vg60f11lbjnjbr8112u` (`machine_id`),
  ADD KEY `FKbcknes1rbi1eldfa1b6eg6pa0` (`parentMachineOccupation_id`),
  ADD KEY `FK71cuuk7wt8so2o6wds6wl2o9a` (`productionOrder_id`),
  ADD KEY `FK9ra0cssg869tj1gfb83nanctb` (`tool_id`),
  ADD KEY `FK3yji2srysnd57nnk562oeubd0` (`totalProductionNumbers_id`),
  ADD KEY `FKkriatktdbgnd7lfwsf8uxus99` (`userOccupation_id`);

--
-- Indizes für die Tabelle `MachineOccupation_ActiveToolSetting`
--
ALTER TABLE `MachineOccupation_ActiveToolSetting`
  ADD KEY `FKtqtu7tmbcfexydwyh43wilmex` (`toolSetting`),
  ADD KEY `FKg2oc9prf9tcav5rqvjx7u1m8k` (`machineOccupation`);

--
-- Indizes für die Tabelle `MachineOccupation_CD_ProductionStep`
--
ALTER TABLE `MachineOccupation_CD_ProductionStep`
  ADD KEY `FKhaawoaio20mes04uc46rsb1j4` (`productionStep`),
  ADD KEY `FKampj3ryea75d0rj4y7u8l5l7q` (`machineOccupation`);

--
-- Indizes für die Tabelle `MachineOccupation_CD_Tool`
--
ALTER TABLE `MachineOccupation_CD_Tool`
  ADD KEY `FKfbaawecmlecy8sc50sh4xv78o` (`tool`),
  ADD KEY `FKn0fi8tjlw6q9cyh1xlie6wsti` (`machineOccupation`);

--
-- Indizes für die Tabelle `MachineOccupation_MachineOccupation`
--
ALTER TABLE `MachineOccupation_MachineOccupation`
  ADD UNIQUE KEY `UK_ija76x9clcdjwhxioqp8e9id5` (`subMachineOccupations_id`),
  ADD KEY `FKpooy52fnqrmlo9rjxt0qkkr5u` (`MachineOccupation_id`);

--
-- Indizes für die Tabelle `MachineStatus`
--
ALTER TABLE `MachineStatus`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_la7dijvden5bwt9i7anspo1oo` (`externalId`),
  ADD KEY `IDXla7dijvden5bwt9i7anspo1oo` (`externalId`);

--
-- Indizes für die Tabelle `MachineStatusHistory`
--
ALTER TABLE `MachineStatusHistory`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_78fn6n46l8ua9kx1o6j6q99fa` (`externalId`),
  ADD KEY `IDX78fn6n46l8ua9kx1o6j6q99fa` (`externalId`),
  ADD KEY `FK930w6pvtvjkgho0opl49p831e` (`machine_id`),
  ADD KEY `FKcejxxsdtm136smxell90ho65m` (`machineStatus_id`);

--
-- Indizes für die Tabelle `MappingAndDataSource`
--
ALTER TABLE `MappingAndDataSource`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK3jvm5cwc8bjx01qj7425da0y6` (`dataReader_id`),
  ADD KEY `FK9q1h2asdtruwh0kgvm880ufse` (`dataWriter_id`),
  ADD KEY `FK7e3wx6ddpad5vb7menb6a54r3` (`mappingConfiguration_id`),
  ADD KEY `FKoslde01e9uvfcsa6vcrged1sp` (`readerResult_id`);

--
-- Indizes für die Tabelle `MappingConfiguration`
--
ALTER TABLE `MappingConfiguration`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `MappingConfiguration_MappingConfiguration`
--
ALTER TABLE `MappingConfiguration_MappingConfiguration`
  ADD UNIQUE KEY `UK_fofyof6ibiywpjvijmai6s15b` (`loops_id`),
  ADD KEY `FK68pg626gb6i9exnt74a0t936g` (`MappingConfiguration_id`);

--
-- Indizes für die Tabelle `MappingConfiguration_MappingRule`
--
ALTER TABLE `MappingConfiguration_MappingRule`
  ADD UNIQUE KEY `UK_ef78odnj9l2qyrmvnar18yg00` (`mappings_id`),
  ADD KEY `FK58hhu7m8euyh3yklyktirdrt4` (`MappingConfiguration_id`);

--
-- Indizes für die Tabelle `MappingRule`
--
ALTER TABLE `MappingRule`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `Note`
--
ALTER TABLE `Note`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_9l2gnbkopst0al8q9wkfs69f1` (`externalId`),
  ADD KEY `IDX9l2gnbkopst0al8q9wkfs69f1` (`externalId`);

--
-- Indizes für die Tabelle `OverheadCost`
--
ALTER TABLE `OverheadCost`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_qsgpjhvs212mxbkdfm7hvsl8n` (`externalId`),
  ADD KEY `IDXqsgpjhvs212mxbkdfm7hvsl8n` (`externalId`),
  ADD KEY `FKpqltdd4uddr6vl4lchp84s1iu` (`overheadCostCenter_id`),
  ADD KEY `FKjfwq0ofxb3sftuw60h0vbsx7u` (`timeRecord_id`),
  ADD KEY `FKqb8mkux22v5i5qsvsl1yts4sm` (`user_id`);

--
-- Indizes für die Tabelle `ProductionNumbers`
--
ALTER TABLE `ProductionNumbers`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_9n875m5j3nnnvj4sfk1j2090i` (`externalId`),
  ADD KEY `IDX9n875m5j3nnnvj4sfk1j2090i` (`externalId`),
  ADD KEY `FK4nf7dlw8dgj6x5tr37qlitl13` (`subProductionStep_id`);

--
-- Indizes für die Tabelle `ProductionOrder`
--
ALTER TABLE `ProductionOrder`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_8e8qe7q92ugbkr5f8iyae90g9` (`externalId`),
  ADD KEY `IDX8e8qe7q92ugbkr5f8iyae90g9` (`externalId`),
  ADD KEY `FK552vq4xmu35hcqkafd73yc7gr` (`customerOrder_id`),
  ADD KEY `FK75qygmafsjjk2su018wgntfbl` (`parentProductionOrder_id`),
  ADD KEY `FKq2refjqg82s38bj9yr8ktoqk4` (`product_id`);

--
-- Indizes für die Tabelle `ProductionOrder_Note`
--
ALTER TABLE `ProductionOrder_Note`
  ADD UNIQUE KEY `UK_6ufet95f3av1oas3t5bydsj5y` (`notes_id`),
  ADD KEY `FKtmo8e5b1n2y3ukfabyae66a4u` (`ProductionOrder_id`);

--
-- Indizes für die Tabelle `ProductionOrder_ProductionOrder`
--
ALTER TABLE `ProductionOrder_ProductionOrder`
  ADD UNIQUE KEY `UK_pmx4fs8e3qjqt6vnw899je6ee` (`subProductionOrders_id`),
  ADD KEY `FKphqkki5fl6oqv9btpc1rfhmtm` (`ProductionOrder_id`);

--
-- Indizes für die Tabelle `ProductionStepInfo`
--
ALTER TABLE `ProductionStepInfo`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_r229svqoyg312bo7pjyy804ah` (`externalId`),
  ADD KEY `IDXr229svqoyg312bo7pjyy804ah` (`externalId`);

--
-- Indizes für die Tabelle `QualityManagement`
--
ALTER TABLE `QualityManagement`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_r7ajptobxu1y3yc3rayit29t7` (`externalId`),
  ADD KEY `IDXr7ajptobxu1y3yc3rayit29t7` (`externalId`),
  ADD KEY `FKb6r6p170m75adxm7dbfq1bmme` (`qualityControlFeature_id`),
  ADD KEY `FKkvqh03inro2bwr922unurnixa` (`subProductionStep_id`);

--
-- Indizes für die Tabelle `ReaderResult`
--
ALTER TABLE `ReaderResult`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `SetUp`
--
ALTER TABLE `SetUp`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_evanv4l23n7bw1f4t3l6xb3ok` (`externalId`),
  ADD KEY `IDXevanv4l23n7bw1f4t3l6xb3ok` (`externalId`),
  ADD KEY `FK5qp0v4rdx3yc3m3dvqx2wsy0b` (`subProductionStep_id`);

--
-- Indizes für die Tabelle `SingleFileReaderConfig`
--
ALTER TABLE `SingleFileReaderConfig`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `SubProductionStep`
--
ALTER TABLE `SubProductionStep`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_5y3d6s5rjk1a4h7mq5snlyiso` (`externalId`),
  ADD KEY `IDX5y3d6s5rjk1a4h7mq5snlyiso` (`externalId`),
  ADD KEY `FKi4mr81mnur112pq3bwvrt2uuy` (`machineOccupation_id`);

--
-- Indizes für die Tabelle `SuspensionType`
--
ALTER TABLE `SuspensionType`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_a11bbwceychq04k0jhayfnkv0` (`externalId`),
  ADD UNIQUE KEY `UK_mq1yap4v1dlxlx11sjmmh8uss` (`name`),
  ADD KEY `IDXa11bbwceychq04k0jhayfnkv0` (`externalId`);

--
-- Indizes für die Tabelle `TimeRecord`
--
ALTER TABLE `TimeRecord`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_ochw1gewvtbeyixwlh7ygidn4` (`externalId`),
  ADD KEY `IDXochw1gewvtbeyixwlh7ygidn4` (`externalId`),
  ADD KEY `FKtjoc22405hi1xd3x6hp9l0cna` (`responseUser_id`),
  ADD KEY `FKkht0wcxmqrry62qpkdfxcoeuv` (`subProductionStep_id`),
  ADD KEY `FKspdh3ry0153ot0dy0sdkfhs3g` (`suspensionType_id`),
  ADD KEY `FKc4r6bc780sga59tv3256wnxog` (`timeRecordType_id`);

--
-- Indizes für die Tabelle `TimeRecordType`
--
ALTER TABLE `TimeRecordType`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_6fl0d99lpw1omdcw20qitso0o` (`externalId`),
  ADD UNIQUE KEY `UK_c72bw80yt7gbgwuro9atysavy` (`name`),
  ADD KEY `IDX6fl0d99lpw1omdcw20qitso0o` (`externalId`);

--
-- Indizes für die Tabelle `Time_Durations`
--
ALTER TABLE `Time_Durations`
  ADD PRIMARY KEY (`machineOccupation`,`timeType`);

--
-- Indizes für die Tabelle `Time_DurationsStep`
--
ALTER TABLE `Time_DurationsStep`
  ADD PRIMARY KEY (`subProductionStep`,`timeType`);

--
-- Indizes für die Tabelle `ToolSetting`
--
ALTER TABLE `ToolSetting`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_2oxkkvhqjt9m086nmxupp0gah` (`externalId`),
  ADD KEY `IDX2oxkkvhqjt9m086nmxupp0gah` (`externalId`),
  ADD KEY `FKda7u57okrn7bcd4s8t8o3oili` (`machine_id`),
  ADD KEY `FKbprwp6w2b72drca4khe2gxuri` (`tool_id`),
  ADD KEY `FKjpykeo7mcb57vdi216kxj5217` (`toolSettingParameter_id`);

--
-- Indizes für die Tabelle `User`
--
ALTER TABLE `User`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_3p8a52yq3023p357hb6b4jalv` (`externalId`),
  ADD UNIQUE KEY `UK_hl8fftx66p59oqgkkcfit3eay` (`userName`),
  ADD KEY `IDX3p8a52yq3023p357hb6b4jalv` (`externalId`);

--
-- Indizes für die Tabelle `UserOccupation`
--
ALTER TABLE `UserOccupation`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_1nkxk322axqgitvevh7srsism` (`externalId`),
  ADD KEY `FKr4du9qm90505xk5jcmwxoofvn` (`machineOccupation_id`);

--
-- Indizes für die Tabelle `UserOccupation_ActiveUsers`
--
ALTER TABLE `UserOccupation_ActiveUsers`
  ADD KEY `FKl6v4fh9qt1dhy3524twg4cwhj` (`user_id`),
  ADD KEY `FK5w8yjonf40efq0cmj8gib1a4c` (`user_occupation_id`);

--
-- Indizes für die Tabelle `UserOccupation_InactiveUsers`
--
ALTER TABLE `UserOccupation_InactiveUsers`
  ADD KEY `FKcx86nmgjet6ivvrv42ip9mj9p` (`user_id`),
  ADD KEY `FKhnsotr1ni0rrsa8qc9lsrw4lw` (`user_occupation_id`);

--
-- Indizes für die Tabelle `UserOccupation_TimeRecords`
--
ALTER TABLE `UserOccupation_TimeRecords`
  ADD UNIQUE KEY `UK_23ro6yhxysrawnl115ylvucjd` (`timerecord_id`),
  ADD KEY `FKfk7nm5v50vgklxmi576m46d0l` (`user_occupation_id`);

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `AllClassExtension_ClassExtension`
--
ALTER TABLE `AllClassExtension_ClassExtension`
  ADD CONSTRAINT `FKbmts0lnwomhe9542su3qj3tnf` FOREIGN KEY (`classes_fieldId`) REFERENCES `ClassExtension` (`fieldId`),
  ADD CONSTRAINT `FKsl9nqf0opi6qcsfkvouc3uvls` FOREIGN KEY (`AllClassExtension_id`) REFERENCES `AllClassExtension` (`id`);

--
-- Constraints der Tabelle `AuxiliaryMaterials`
--
ALTER TABLE `AuxiliaryMaterials`
  ADD CONSTRAINT `FKf7sx5xmth3q2iahypud0egnwl` FOREIGN KEY (`subProductionStep_id`) REFERENCES `SubProductionStep` (`id`);

--
-- Constraints der Tabelle `BookingEntry`
--
ALTER TABLE `BookingEntry`
  ADD CONSTRAINT `FK2su0kingqav2ycdmq5nofvv9g` FOREIGN KEY (`overheadCost_id`) REFERENCES `OverheadCost` (`id`),
  ADD CONSTRAINT `FKe57614byrdted49wqd2p7dji6` FOREIGN KEY (`machineOccupation_id`) REFERENCES `MachineOccupation` (`id`),
  ADD CONSTRAINT `FKqltonrbscwefmvvccv11pq8ij` FOREIGN KEY (`subProductionStep_id`) REFERENCES `SubProductionStep` (`id`),
  ADD CONSTRAINT `FKrk8p1g8i0wjcu45vhvbhdx8t6` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`);

--
-- Constraints der Tabelle `CamundaMachineOccupation`
--
ALTER TABLE `CamundaMachineOccupation`
  ADD CONSTRAINT `FKc63xbi76wyvghct4ydefrommi` FOREIGN KEY (`machineOccupation_id`) REFERENCES `MachineOccupation` (`id`),
  ADD CONSTRAINT `FKq8s4j4t7w2q3k9agxjj74rry` FOREIGN KEY (`activeProductionStep_id`) REFERENCES `CD_ProductionStep` (`id`);

--
-- Constraints der Tabelle `CamundaSubProductionStep`
--
ALTER TABLE `CamundaSubProductionStep`
  ADD CONSTRAINT `FKi2cd4bpxbv5kbvsywhwf42os7` FOREIGN KEY (`subProductionStep_id`) REFERENCES `SubProductionStep` (`id`),
  ADD CONSTRAINT `FKnjhhoiiqv41p8s56gevyywwan` FOREIGN KEY (`camundaMachineOccupation_id`) REFERENCES `CamundaMachineOccupation` (`id`);

--
-- Constraints der Tabelle `CD_Machine`
--
ALTER TABLE `CD_Machine`
  ADD CONSTRAINT `FK8lw9mactxdm38bbpevbsad5lm` FOREIGN KEY (`machineType_id`) REFERENCES `CD_MachineType` (`id`);

--
-- Constraints der Tabelle `CD_MachineType_CD_Department`
--
ALTER TABLE `CD_MachineType_CD_Department`
  ADD CONSTRAINT `FK199tkfduon9efd11fx49ljsxy` FOREIGN KEY (`department`) REFERENCES `CD_Department` (`id`),
  ADD CONSTRAINT `FK6bekpt5aptm36gbevhfl6lgwy` FOREIGN KEY (`machineType`) REFERENCES `CD_MachineType` (`id`);

--
-- Constraints der Tabelle `CD_ProductionStep`
--
ALTER TABLE `CD_ProductionStep`
  ADD CONSTRAINT `FKbjk4kl61mdfkrgcspu5e2b78v` FOREIGN KEY (`machineType_id`) REFERENCES `CD_MachineType` (`id`),
  ADD CONSTRAINT `FKjumxna5ixaku6nx666nfsemtu` FOREIGN KEY (`toolType_id`) REFERENCES `CD_ToolType` (`id`),
  ADD CONSTRAINT `FKmr55o5mi3p20tlv64594hlqvi` FOREIGN KEY (`product_id`) REFERENCES `CD_Product` (`id`),
  ADD CONSTRAINT `FKrvi0x20nmjhjrns7mk684bnlr` FOREIGN KEY (`productionStepInfo_id`) REFERENCES `ProductionStepInfo` (`id`);

--
-- Constraints der Tabelle `CD_ProductionStep_CD_ToolSettingParameter`
--
ALTER TABLE `CD_ProductionStep_CD_ToolSettingParameter`
  ADD CONSTRAINT `FKacp0g1is6sywjh764l7ux9nso` FOREIGN KEY (`toolSettingParameter`) REFERENCES `CD_ToolSettingParameter` (`id`),
  ADD CONSTRAINT `FKe581p7l5x6ncl6qeqx6p97hu2` FOREIGN KEY (`productionStep`) REFERENCES `CD_ProductionStep` (`id`);

--
-- Constraints der Tabelle `CD_ProductRelationship`
--
ALTER TABLE `CD_ProductRelationship`
  ADD CONSTRAINT `FKk9bn38khwphcd8i8nwjnlu1aq` FOREIGN KEY (`part_id`) REFERENCES `CD_Product` (`id`),
  ADD CONSTRAINT `FKr3ja421hlnm417gvujip09mjd` FOREIGN KEY (`product_id`) REFERENCES `CD_Product` (`id`);

--
-- Constraints der Tabelle `CD_QualityControlFeature`
--
ALTER TABLE `CD_QualityControlFeature`
  ADD CONSTRAINT `FKnadd80qngiko3cnbn3d2tp14c` FOREIGN KEY (`product_id`) REFERENCES `CD_Product` (`id`);

--
-- Constraints der Tabelle `CD_Tool`
--
ALTER TABLE `CD_Tool`
  ADD CONSTRAINT `FK752h21a2xeipy5pik45fxtux5` FOREIGN KEY (`toolType_id`) REFERENCES `CD_ToolType` (`id`);

--
-- Constraints der Tabelle `CD_ToolSettingParameter`
--
ALTER TABLE `CD_ToolSettingParameter`
  ADD CONSTRAINT `FKeftav1vkqik90p7w15yh10998` FOREIGN KEY (`toolType_id`) REFERENCES `CD_ToolType` (`id`),
  ADD CONSTRAINT `FKjr5o9ihndlx7wrabkd4wkl3fp` FOREIGN KEY (`machineType_id`) REFERENCES `CD_MachineType` (`id`);

--
-- Constraints der Tabelle `ClassExtension_MemberExtension`
--
ALTER TABLE `ClassExtension_MemberExtension`
  ADD CONSTRAINT `FKoar6p2ya1ktyqmno9pmydwtyo` FOREIGN KEY (`ClassExtension_fieldId`) REFERENCES `ClassExtension` (`fieldId`);

--
-- Constraints der Tabelle `KeyValueMapProductionOrder`
--
ALTER TABLE `KeyValueMapProductionOrder`
  ADD CONSTRAINT `FKrklijthe294bnc8o9qrba7axj` FOREIGN KEY (`productionOrder`) REFERENCES `ProductionOrder` (`id`);

--
-- Constraints der Tabelle `KeyValueMapSubProductionStep`
--
ALTER TABLE `KeyValueMapSubProductionStep`
  ADD CONSTRAINT `FKiomamnm5mgfhgpl62cbgyntg7` FOREIGN KEY (`subProductionStep`) REFERENCES `SubProductionStep` (`id`);

--
-- Constraints der Tabelle `MachineOccupation`
--
ALTER TABLE `MachineOccupation`
  ADD CONSTRAINT `FK3yji2srysnd57nnk562oeubd0` FOREIGN KEY (`totalProductionNumbers_id`) REFERENCES `ProductionNumbers` (`id`),
  ADD CONSTRAINT `FK71cuuk7wt8so2o6wds6wl2o9a` FOREIGN KEY (`productionOrder_id`) REFERENCES `ProductionOrder` (`id`),
  ADD CONSTRAINT `FK8rafq5vg60f11lbjnjbr8112u` FOREIGN KEY (`machine_id`) REFERENCES `CD_Machine` (`id`),
  ADD CONSTRAINT `FK9ra0cssg869tj1gfb83nanctb` FOREIGN KEY (`tool_id`) REFERENCES `CD_Tool` (`id`),
  ADD CONSTRAINT `FKbcknes1rbi1eldfa1b6eg6pa0` FOREIGN KEY (`parentMachineOccupation_id`) REFERENCES `MachineOccupation` (`id`),
  ADD CONSTRAINT `FKkriatktdbgnd7lfwsf8uxus99` FOREIGN KEY (`userOccupation_id`) REFERENCES `UserOccupation` (`id`),
  ADD CONSTRAINT `FKl0eis6d23p4ufhooiqbbaoc6t` FOREIGN KEY (`department_id`) REFERENCES `CD_Department` (`id`);

--
-- Constraints der Tabelle `MachineOccupation_ActiveToolSetting`
--
ALTER TABLE `MachineOccupation_ActiveToolSetting`
  ADD CONSTRAINT `FKg2oc9prf9tcav5rqvjx7u1m8k` FOREIGN KEY (`machineOccupation`) REFERENCES `MachineOccupation` (`id`),
  ADD CONSTRAINT `FKtqtu7tmbcfexydwyh43wilmex` FOREIGN KEY (`toolSetting`) REFERENCES `ToolSetting` (`id`);

--
-- Constraints der Tabelle `MachineOccupation_CD_ProductionStep`
--
ALTER TABLE `MachineOccupation_CD_ProductionStep`
  ADD CONSTRAINT `FKampj3ryea75d0rj4y7u8l5l7q` FOREIGN KEY (`machineOccupation`) REFERENCES `MachineOccupation` (`id`),
  ADD CONSTRAINT `FKhaawoaio20mes04uc46rsb1j4` FOREIGN KEY (`productionStep`) REFERENCES `CD_ProductionStep` (`id`);

--
-- Constraints der Tabelle `MachineOccupation_CD_Tool`
--
ALTER TABLE `MachineOccupation_CD_Tool`
  ADD CONSTRAINT `FKfbaawecmlecy8sc50sh4xv78o` FOREIGN KEY (`tool`) REFERENCES `CD_Tool` (`id`),
  ADD CONSTRAINT `FKn0fi8tjlw6q9cyh1xlie6wsti` FOREIGN KEY (`machineOccupation`) REFERENCES `MachineOccupation` (`id`);

--
-- Constraints der Tabelle `MachineOccupation_MachineOccupation`
--
ALTER TABLE `MachineOccupation_MachineOccupation`
  ADD CONSTRAINT `FK3np88b9h7ynds5av3jb64pvlp` FOREIGN KEY (`subMachineOccupations_id`) REFERENCES `MachineOccupation` (`id`),
  ADD CONSTRAINT `FKpooy52fnqrmlo9rjxt0qkkr5u` FOREIGN KEY (`MachineOccupation_id`) REFERENCES `MachineOccupation` (`id`);

--
-- Constraints der Tabelle `MachineStatusHistory`
--
ALTER TABLE `MachineStatusHistory`
  ADD CONSTRAINT `FK930w6pvtvjkgho0opl49p831e` FOREIGN KEY (`machine_id`) REFERENCES `CD_Machine` (`id`),
  ADD CONSTRAINT `FKcejxxsdtm136smxell90ho65m` FOREIGN KEY (`machineStatus_id`) REFERENCES `MachineStatus` (`id`);

--
-- Constraints der Tabelle `MappingAndDataSource`
--
ALTER TABLE `MappingAndDataSource`
  ADD CONSTRAINT `FK3jvm5cwc8bjx01qj7425da0y6` FOREIGN KEY (`dataReader_id`) REFERENCES `DataReader` (`id`),
  ADD CONSTRAINT `FK7e3wx6ddpad5vb7menb6a54r3` FOREIGN KEY (`mappingConfiguration_id`) REFERENCES `MappingConfiguration` (`id`),
  ADD CONSTRAINT `FK9q1h2asdtruwh0kgvm880ufse` FOREIGN KEY (`dataWriter_id`) REFERENCES `DataWriter` (`id`),
  ADD CONSTRAINT `FKoslde01e9uvfcsa6vcrged1sp` FOREIGN KEY (`readerResult_id`) REFERENCES `ReaderResult` (`id`);

--
-- Constraints der Tabelle `MappingConfiguration_MappingConfiguration`
--
ALTER TABLE `MappingConfiguration_MappingConfiguration`
  ADD CONSTRAINT `FK68pg626gb6i9exnt74a0t936g` FOREIGN KEY (`MappingConfiguration_id`) REFERENCES `MappingConfiguration` (`id`),
  ADD CONSTRAINT `FKl269oqd7vl258tsq2d1j6pcap` FOREIGN KEY (`loops_id`) REFERENCES `MappingConfiguration` (`id`);

--
-- Constraints der Tabelle `MappingConfiguration_MappingRule`
--
ALTER TABLE `MappingConfiguration_MappingRule`
  ADD CONSTRAINT `FK58hhu7m8euyh3yklyktirdrt4` FOREIGN KEY (`MappingConfiguration_id`) REFERENCES `MappingConfiguration` (`id`),
  ADD CONSTRAINT `FKoexpbd19h7v8bco33iwwaor45` FOREIGN KEY (`mappings_id`) REFERENCES `MappingRule` (`id`);

--
-- Constraints der Tabelle `OverheadCost`
--
ALTER TABLE `OverheadCost`
  ADD CONSTRAINT `FKjfwq0ofxb3sftuw60h0vbsx7u` FOREIGN KEY (`timeRecord_id`) REFERENCES `TimeRecord` (`id`),
  ADD CONSTRAINT `FKpqltdd4uddr6vl4lchp84s1iu` FOREIGN KEY (`overheadCostCenter_id`) REFERENCES `CD_OverheadCostCenter` (`id`),
  ADD CONSTRAINT `FKqb8mkux22v5i5qsvsl1yts4sm` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`);

--
-- Constraints der Tabelle `ProductionNumbers`
--
ALTER TABLE `ProductionNumbers`
  ADD CONSTRAINT `FK4nf7dlw8dgj6x5tr37qlitl13` FOREIGN KEY (`subProductionStep_id`) REFERENCES `SubProductionStep` (`id`);

--
-- Constraints der Tabelle `ProductionOrder`
--
ALTER TABLE `ProductionOrder`
  ADD CONSTRAINT `FK552vq4xmu35hcqkafd73yc7gr` FOREIGN KEY (`customerOrder_id`) REFERENCES `CustomerOrder` (`id`),
  ADD CONSTRAINT `FK75qygmafsjjk2su018wgntfbl` FOREIGN KEY (`parentProductionOrder_id`) REFERENCES `ProductionOrder` (`id`),
  ADD CONSTRAINT `FKq2refjqg82s38bj9yr8ktoqk4` FOREIGN KEY (`product_id`) REFERENCES `CD_Product` (`id`);

--
-- Constraints der Tabelle `ProductionOrder_Note`
--
ALTER TABLE `ProductionOrder_Note`
  ADD CONSTRAINT `FKgg5ilmt97dfbrf28f8971peo5` FOREIGN KEY (`notes_id`) REFERENCES `Note` (`id`),
  ADD CONSTRAINT `FKtmo8e5b1n2y3ukfabyae66a4u` FOREIGN KEY (`ProductionOrder_id`) REFERENCES `ProductionOrder` (`id`);

--
-- Constraints der Tabelle `ProductionOrder_ProductionOrder`
--
ALTER TABLE `ProductionOrder_ProductionOrder`
  ADD CONSTRAINT `FK2acr870w3ppcvtppn1m1v36oj` FOREIGN KEY (`subProductionOrders_id`) REFERENCES `ProductionOrder` (`id`),
  ADD CONSTRAINT `FKphqkki5fl6oqv9btpc1rfhmtm` FOREIGN KEY (`ProductionOrder_id`) REFERENCES `ProductionOrder` (`id`);

--
-- Constraints der Tabelle `QualityManagement`
--
ALTER TABLE `QualityManagement`
  ADD CONSTRAINT `FKb6r6p170m75adxm7dbfq1bmme` FOREIGN KEY (`qualityControlFeature_id`) REFERENCES `CD_QualityControlFeature` (`id`),
  ADD CONSTRAINT `FKkvqh03inro2bwr922unurnixa` FOREIGN KEY (`subProductionStep_id`) REFERENCES `SubProductionStep` (`id`);

--
-- Constraints der Tabelle `SetUp`
--
ALTER TABLE `SetUp`
  ADD CONSTRAINT `FK5qp0v4rdx3yc3m3dvqx2wsy0b` FOREIGN KEY (`subProductionStep_id`) REFERENCES `SubProductionStep` (`id`);

--
-- Constraints der Tabelle `SubProductionStep`
--
ALTER TABLE `SubProductionStep`
  ADD CONSTRAINT `FKi4mr81mnur112pq3bwvrt2uuy` FOREIGN KEY (`machineOccupation_id`) REFERENCES `MachineOccupation` (`id`);

--
-- Constraints der Tabelle `TimeRecord`
--
ALTER TABLE `TimeRecord`
  ADD CONSTRAINT `FKc4r6bc780sga59tv3256wnxog` FOREIGN KEY (`timeRecordType_id`) REFERENCES `TimeRecordType` (`id`),
  ADD CONSTRAINT `FKkht0wcxmqrry62qpkdfxcoeuv` FOREIGN KEY (`subProductionStep_id`) REFERENCES `SubProductionStep` (`id`),
  ADD CONSTRAINT `FKspdh3ry0153ot0dy0sdkfhs3g` FOREIGN KEY (`suspensionType_id`) REFERENCES `SuspensionType` (`id`),
  ADD CONSTRAINT `FKtjoc22405hi1xd3x6hp9l0cna` FOREIGN KEY (`responseUser_id`) REFERENCES `User` (`id`);

--
-- Constraints der Tabelle `Time_Durations`
--
ALTER TABLE `Time_Durations`
  ADD CONSTRAINT `FK3nh7qlesuy1bdar5gtfqi4hke` FOREIGN KEY (`machineOccupation`) REFERENCES `MachineOccupation` (`id`);

--
-- Constraints der Tabelle `Time_DurationsStep`
--
ALTER TABLE `Time_DurationsStep`
  ADD CONSTRAINT `FKbw711s57116je969wf5c6esuc` FOREIGN KEY (`subProductionStep`) REFERENCES `SubProductionStep` (`id`);

--
-- Constraints der Tabelle `ToolSetting`
--
ALTER TABLE `ToolSetting`
  ADD CONSTRAINT `FKbprwp6w2b72drca4khe2gxuri` FOREIGN KEY (`tool_id`) REFERENCES `CD_Tool` (`id`),
  ADD CONSTRAINT `FKda7u57okrn7bcd4s8t8o3oili` FOREIGN KEY (`machine_id`) REFERENCES `CD_Machine` (`id`),
  ADD CONSTRAINT `FKjpykeo7mcb57vdi216kxj5217` FOREIGN KEY (`toolSettingParameter_id`) REFERENCES `CD_ToolSettingParameter` (`id`);

--
-- Constraints der Tabelle `UserOccupation`
--
ALTER TABLE `UserOccupation`
  ADD CONSTRAINT `FKr4du9qm90505xk5jcmwxoofvn` FOREIGN KEY (`machineOccupation_id`) REFERENCES `MachineOccupation` (`id`);

--
-- Constraints der Tabelle `UserOccupation_ActiveUsers`
--
ALTER TABLE `UserOccupation_ActiveUsers`
  ADD CONSTRAINT `FK5w8yjonf40efq0cmj8gib1a4c` FOREIGN KEY (`user_occupation_id`) REFERENCES `UserOccupation` (`id`),
  ADD CONSTRAINT `FKl6v4fh9qt1dhy3524twg4cwhj` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`);

--
-- Constraints der Tabelle `UserOccupation_InactiveUsers`
--
ALTER TABLE `UserOccupation_InactiveUsers`
  ADD CONSTRAINT `FKcx86nmgjet6ivvrv42ip9mj9p` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`),
  ADD CONSTRAINT `FKhnsotr1ni0rrsa8qc9lsrw4lw` FOREIGN KEY (`user_occupation_id`) REFERENCES `UserOccupation` (`id`);

--
-- Constraints der Tabelle `UserOccupation_TimeRecords`
--
ALTER TABLE `UserOccupation_TimeRecords`
  ADD CONSTRAINT `FKfk7nm5v50vgklxmi576m46d0l` FOREIGN KEY (`user_occupation_id`) REFERENCES `UserOccupation` (`id`),
  ADD CONSTRAINT `FKsnoac1a27oscm1wwmr9mkibmu` FOREIGN KEY (`timerecord_id`) REFERENCES `TimeRecord` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
