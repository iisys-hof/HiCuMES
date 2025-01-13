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
-- Datenbank: `hicumesFP`
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
(2, NULL, '2', '2024-11-25 12:10:18.556152', NULL, 'Institut für Informationssysteme der Hochschule Hof - iisys');

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
(24, NULL, '14', '2024-11-25 12:10:20.546660', NULL, 'Handmontage', 16),
(25, NULL, '10', '2024-11-25 12:10:20.535775', NULL, 'Spritzgießen A', 17),
(26, NULL, '12', '2024-11-25 12:10:20.613056', NULL, 'Wickeln', 18),
(27, NULL, '11', '2024-11-25 12:10:20.603509', NULL, 'Spritzgießen B', 17),
(28, NULL, '9', '2024-11-25 12:10:20.621619', NULL, 'Tiefziehen', 19),
(29, NULL, '15', '2024-11-25 12:10:20.557612', NULL, 'Endmontage', 20),
(30, NULL, '20', '2024-11-25 12:10:20.570210', NULL, 'QS Prüfung', 21),
(31, NULL, '18', '2024-11-25 12:10:20.582113', NULL, 'Verpackung', 22),
(32, NULL, '19', '2024-11-25 12:10:20.592514', NULL, 'Versand', 23);

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
(16, NULL, '004', '2024-11-25 12:10:20.001519', NULL, 'Handmontage'),
(17, NULL, '001', '2024-11-25 12:10:20.068586', NULL, 'Spritzgießen'),
(18, NULL, '002', '2024-11-25 12:10:20.080572', NULL, 'Wickeln'),
(19, NULL, '003', '2024-11-25 12:10:20.092397', NULL, 'Tiefziehen'),
(20, NULL, '005', '2024-11-25 12:10:20.013837', NULL, 'Endmontage'),
(21, NULL, '006', '2024-11-25 12:10:20.026768', NULL, 'QS Prüfung'),
(22, NULL, '007', '2024-11-25 12:10:20.040971', NULL, 'Verpackung'),
(23, NULL, '008', '2024-11-25 12:10:20.055392', NULL, 'Versand');

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
(16, 2),
(20, 2),
(21, 2),
(22, 2),
(23, 2),
(17, 2),
(18, 2),
(19, 2);

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
(3, '2024-11-25 12:10:18.576580', 'Insti/MO/00004_84', '2024-11-25 12:10:18.576618', NULL, 'Griffrohr Holz', NULL),
(4, '2024-11-25 12:10:18.594148', 'Insti/MO/00014_69', '2024-11-25 12:10:18.594182', NULL, 'Spitze Kunststoff', NULL),
(5, '2024-11-25 12:10:18.615601', 'Insti/MO/00015_63', '2024-11-25 12:10:18.615640', NULL, 'Druckfeder Metall', NULL),
(6, '2024-11-25 12:10:18.653295', 'Insti/MO/00017_71', '2024-11-25 12:10:18.654055', NULL, 'Griffrohr B blau', NULL),
(7, '2024-11-25 12:10:18.695867', 'Insti/MO/00018_87', '2024-11-25 12:10:18.695905', NULL, 'Griffrohr blau, Clip Kunststoff', NULL),
(8, '2024-11-25 12:10:18.739073', 'Insti/MO/00019_72', '2024-11-25 12:10:18.739162', NULL, 'Griffrohr B blau', NULL),
(9, NULL, 'Insti/MO/00025_106', '2024-11-25 12:10:18.935387', NULL, 'Kugelschreiber blau/rot, Kunststoff', NULL),
(10, NULL, 'Insti/MO/00027_106', '2024-11-25 12:10:19.096436', NULL, 'Kugelschreiber blau/rot, Kunststoff', NULL),
(11, NULL, 'Insti/MO/00028_106', '2024-11-25 12:10:19.234915', NULL, 'Kugelschreiber blau/rot, Kunststoff', NULL),
(12, NULL, 'Insti/MO/00046_107', '2024-11-25 12:10:19.305006', NULL, 'Kugelschreiber grau (FT)', NULL),
(13, '2024-11-25 12:10:19.321402', 'Insti/MO/00047_111', '2024-11-25 12:10:19.321491', NULL, 'Druckhülse weiß', NULL),
(14, '2024-11-25 12:10:19.337851', 'Insti/MO/00049_63', '2024-11-25 12:10:19.337900', NULL, 'Druckfeder Metall', NULL),
(15, '2024-11-25 12:10:19.355556', 'Insti/MO/00050_64', '2024-11-25 12:10:19.355597', NULL, 'Spitze Metall', NULL);

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
(33, '2024-11-25 12:10:20.659020', 'Insti/MO/00004/20', '2024-11-25 12:10:20.659192', NULL, 'Subprocess_Montage', 'Montage Griff', 362, 100, 0, 16, 3, NULL, NULL),
(34, '2024-11-25 12:10:20.683414', 'Insti/MO/00014/23', '2024-11-25 12:10:20.683446', NULL, 'Subprocess_Basis', 'Spritzgießen Spitze', 30, 100, 0, 17, 4, NULL, NULL),
(35, '2024-11-25 12:10:20.698906', 'Insti/MO/00015/24', '2024-11-25 12:10:20.698936', NULL, 'Subprocess_Basis', 'Wickeln', 1809, 100, 0, 18, 5, NULL, NULL),
(36, '2024-11-25 12:10:20.715436', 'Insti/MO/00017/26', '2024-11-25 12:10:20.715468', NULL, 'Subprocess_Basis', 'Spritzgießen Griffrohr A', 24, 100, 0, 17, 6, NULL, NULL),
(37, '2024-11-25 12:10:20.730713', 'Insti/MO/00018/27', '2024-11-25 12:10:20.730745', NULL, 'Subprocess_Montage', 'Montage Griff', 127, 100, 0, 16, 7, NULL, NULL),
(38, '2024-11-25 12:10:20.747204', 'Insti/MO/00019/28', '2024-11-25 12:10:20.747234', NULL, 'Subprocess_Basis', 'Spritzgießen Griffrohr B', 423, 100, 0, 17, 8, NULL, NULL),
(39, '2024-11-25 12:10:20.765254', 'Insti/MO/00025/41', '2024-11-25 12:10:20.765288', NULL, 'Subprocess_Basis', 'Spritzgießen Griffrohr A', 2, 100, 0, 17, 9, NULL, NULL),
(40, '2024-11-25 12:10:20.781447', 'Insti/MO/00025/42', '2024-11-25 12:10:20.781476', NULL, 'Subprocess_Basis', 'Spritzgießen Griffrohr B', 2, 101, 0, 17, 9, NULL, NULL),
(41, '2024-11-25 12:10:20.797046', 'Insti/MO/00025/43', '2024-11-25 12:10:20.797075', NULL, 'Subprocess_Basis', 'Spritzgießen Druckhülse', 2, 102, 0, 17, 9, NULL, NULL),
(42, '2024-11-25 12:10:20.812002', 'Insti/MO/00025/44', '2024-11-25 12:10:20.812038', NULL, 'Subprocess_Basis', 'Wickeln', 5, 103, 0, 18, 9, NULL, NULL),
(43, '2024-11-25 12:10:20.828391', 'Insti/MO/00025/45', '2024-11-25 12:10:20.828428', NULL, 'Subprocess_Basis', 'Tiefziehen', 2, 104, 0, 19, 9, NULL, NULL),
(44, '2024-11-25 12:10:20.845161', 'Insti/MO/00025/46', '2024-11-25 12:10:20.845195', NULL, 'Subprocess_Montage', 'Montage Griff', 45, 105, 0, 16, 9, NULL, NULL),
(45, '2024-11-25 12:10:20.860365', 'Insti/MO/00025/47', '2024-11-25 12:10:20.860393', NULL, 'Subprocess_Montage', 'Montage Kugelschreiber', 60, 106, 0, 20, 9, NULL, NULL),
(46, '2024-11-25 12:10:20.877493', 'Insti/MO/00025/48', '2024-11-25 12:10:20.877523', NULL, 'Subprocess_QS', 'QS Stiftprüfung', 9, 107, 0, 21, 9, NULL, NULL),
(47, '2024-11-25 12:10:20.893240', 'Insti/MO/00025/49', '2024-11-25 12:10:20.893268', NULL, 'Subprocess_Verpackung', 'Verpacken', 1, 108, 0, 22, 9, NULL, NULL),
(48, '2024-11-25 12:10:20.908744', 'Insti/MO/00025/50', '2024-11-25 12:10:20.908773', NULL, 'Subprocess_Versand', 'Versand', 30, 109, 0, 23, 9, NULL, NULL),
(49, '2024-11-25 12:10:20.924938', 'Insti/MO/00027/51', '2024-11-25 12:10:20.924971', NULL, 'Subprocess_Basis', 'Spritzgießen Griffrohr A', 2, 100, 0, 17, 10, NULL, NULL),
(50, '2024-11-25 12:10:20.941379', 'Insti/MO/00027/52', '2024-11-25 12:10:20.941412', NULL, 'Subprocess_Basis', 'Spritzgießen Griffrohr B', 3, 101, 0, 17, 10, NULL, NULL),
(51, '2024-11-25 12:10:20.957398', 'Insti/MO/00027/53', '2024-11-25 12:10:20.957427', NULL, 'Subprocess_Basis', 'Spritzgießen Druckhülse', 7, 102, 0, 17, 10, NULL, NULL),
(52, '2024-11-25 12:10:20.973353', 'Insti/MO/00027/54', '2024-11-25 12:10:20.973383', NULL, 'Subprocess_Basis', 'Wickeln', 5, 103, 0, 18, 10, NULL, NULL),
(53, '2024-11-25 12:10:20.991912', 'Insti/MO/00027/55', '2024-11-25 12:10:20.991945', NULL, 'Subprocess_Basis', 'Tiefziehen', 10, 104, 0, 19, 10, NULL, NULL),
(54, '2024-11-25 12:10:21.008913', 'Insti/MO/00027/56', '2024-11-25 12:10:21.008950', NULL, 'Subprocess_Montage', 'Montage Griff', 45, 105, 0, 16, 10, NULL, NULL),
(55, '2024-11-25 12:10:21.024827', 'Insti/MO/00027/57', '2024-11-25 12:10:21.024857', NULL, 'Subprocess_Montage', 'Montage Kugelschreiber', 60, 106, 0, 20, 10, NULL, NULL),
(56, '2024-11-25 12:10:21.041954', 'Insti/MO/00027/58', '2024-11-25 12:10:21.041989', NULL, 'Subprocess_QS', 'QS Stiftprüfung', 10, 107, 0, 21, 10, NULL, NULL),
(57, '2024-11-25 12:10:21.057939', 'Insti/MO/00027/59', '2024-11-25 12:10:21.057970', NULL, 'Subprocess_Verpackung', 'Verpacken', 30, 108, 0, 22, 10, NULL, NULL),
(58, '2024-11-25 12:10:21.074180', 'Insti/MO/00027/60', '2024-11-25 12:10:21.074216', NULL, 'Subprocess_Versand', 'Versand', 30, 109, 0, 23, 10, NULL, NULL),
(59, '2024-11-25 12:10:21.090782', 'Insti/MO/00028/61', '2024-11-25 12:10:21.090813', NULL, 'Subprocess_Basis', 'Spritzgießen Griffrohr A', 2, 100, 0, 17, 11, NULL, NULL),
(60, '2024-11-25 12:10:21.108478', 'Insti/MO/00028/62', '2024-11-25 12:10:21.108510', NULL, 'Subprocess_Basis', 'Spritzgießen Griffrohr B', 3, 101, 0, 17, 11, NULL, NULL),
(61, '2024-11-25 12:10:21.124203', 'Insti/MO/00028/63', '2024-11-25 12:10:21.124332', NULL, 'Subprocess_Basis', 'Spritzgießen Druckhülse', 6, 102, 0, 17, 11, NULL, NULL),
(62, '2024-11-25 12:10:21.145342', 'Insti/MO/00028/64', '2024-11-25 12:10:21.145376', NULL, 'Subprocess_Basis', 'Wickeln', 5, 103, 0, 18, 11, NULL, NULL),
(63, '2024-11-25 12:10:21.162285', 'Insti/MO/00028/65', '2024-11-25 12:10:21.162331', NULL, 'Subprocess_Basis', 'Tiefziehen', 9, 104, 0, 19, 11, NULL, NULL),
(64, '2024-11-25 12:10:21.179299', 'Insti/MO/00028/66', '2024-11-25 12:10:21.179331', NULL, 'Subprocess_Montage', 'Montage Griff', 45, 105, 0, 16, 11, NULL, NULL),
(65, '2024-11-25 12:10:21.195159', 'Insti/MO/00028/67', '2024-11-25 12:10:21.195191', NULL, 'Subprocess_Montage', 'Montage Kugelschreiber', 60, 106, 0, 20, 11, NULL, NULL),
(66, '2024-11-25 12:10:21.211068', 'Insti/MO/00028/68', '2024-11-25 12:10:21.211099', NULL, 'Subprocess_QS', 'QS Stiftprüfung', 9, 107, 0, 21, 11, NULL, NULL),
(67, '2024-11-25 12:10:21.228310', 'Insti/MO/00028/69', '2024-11-25 12:10:21.228342', NULL, 'Subprocess_Verpackung', 'Verpacken', 30, 108, 0, 22, 11, NULL, NULL),
(68, '2024-11-25 12:10:21.245625', 'Insti/MO/00028/70', '2024-11-25 12:10:21.245656', NULL, 'Subprocess_Versand', 'Versand', 30, 109, 0, 23, 11, NULL, NULL),
(69, '2024-11-25 12:10:21.262301', 'Insti/MO/00046/139', '2024-11-25 12:10:21.262340', NULL, 'Subprocess_Basis', 'Spritzgießen Schaft grau', 240, 103, 0, 17, 12, NULL, NULL),
(70, '2024-11-25 12:10:21.278930', 'Insti/MO/00046/140', '2024-11-25 12:10:21.278963', NULL, 'Subprocess_Montage', 'Montage Griff', 117, 105, 0, 16, 12, NULL, NULL),
(71, '2024-11-25 12:10:21.295679', 'Insti/MO/00046/141', '2024-11-25 12:10:21.295712', NULL, 'Subprocess_Montage', 'Montage Kugelschreiber gesamt', 132, 106, 0, 20, 12, NULL, NULL),
(72, '2024-11-25 12:10:21.313241', 'Insti/MO/00046/142', '2024-11-25 12:10:21.313276', NULL, 'Subprocess_QS', 'QS Stiftprüfung', 9, 107, 0, 21, 12, NULL, NULL),
(73, '2024-11-25 12:10:21.333319', 'Insti/MO/00046/143', '2024-11-25 12:10:21.333443', NULL, 'Subprocess_Verpackung', 'Verpacken', 114, 108, 0, 22, 12, NULL, NULL),
(74, '2024-11-25 12:10:21.368949', 'Insti/MO/00046/144', '2024-11-25 12:10:21.368979', NULL, 'Subprocess_Versand', 'Versand', 30, 109, 0, 23, 12, NULL, NULL),
(75, '2024-11-25 12:10:21.384736', 'Insti/MO/00047/145', '2024-11-25 12:10:21.384769', NULL, 'Subprocess_Basis', 'Spritzgießen Druckhülse weiß', 180, 100, 0, 17, 13, NULL, NULL),
(76, '2024-11-25 12:10:21.401938', 'Insti/MO/00049/146', '2024-11-25 12:10:21.401971', NULL, 'Subprocess_Basis', 'Wickeln', 1800, 100, 0, 18, 14, NULL, NULL),
(77, '2024-11-25 12:10:21.418580', 'Insti/MO/00050/147', '2024-11-25 12:10:21.418613', NULL, 'Subprocess_Basis', 'Tiefziehen', 634, 100, 0, 19, 15, NULL, NULL);

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

--
-- Daten für Tabelle `CustomerOrder`
--

INSERT INTO `CustomerOrder` (`id`, `createDateTime`, `externalId`, `updateDateTime`, `versionNr`, `customerName`, `deadline`, `name`, `priority`) VALUES
(78, NULL, '', '2024-11-25 12:10:21.780466', NULL, '', NULL, '', 0);

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
(92);

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
(1, NULL, NULL, '2024-11-25 12:17:26.779342', NULL, NULL, NULL, NULL, 'Insti/MO/00004', '2024-11-20 23:06:10.504000', '2024-11-20 08:01:10.504000', 'PLANNED', NULL, NULL, NULL, 79, NULL, NULL, NULL),
(2, NULL, NULL, '2024-11-25 12:17:26.786778', NULL, NULL, NULL, NULL, 'Insti/MO/00014', '2024-11-20 07:26:45.004000', '2024-11-20 07:01:45.004000', 'PLANNED', NULL, 25, NULL, 80, NULL, NULL, NULL),
(3, NULL, NULL, '2024-11-25 12:17:26.794272', NULL, NULL, NULL, NULL, 'Insti/MO/00017', '2024-11-20 07:22:44.453000', '2024-11-20 07:02:44.453000', 'PLANNED', NULL, 27, NULL, 82, NULL, NULL, NULL),
(4, NULL, NULL, '2024-11-25 12:17:26.801448', NULL, NULL, NULL, NULL, 'Insti/MO/00015', '2024-11-21 09:08:11.684000', '2024-11-20 08:00:41.684000', 'PLANNED', NULL, 26, NULL, 81, NULL, NULL, NULL),
(5, NULL, NULL, '2024-11-25 12:17:26.814417', NULL, NULL, NULL, NULL, 'Insti/MO/00028', '2024-11-22 07:01:46.556000', '2024-11-22 07:00:06.556000', 'PLANNED', NULL, 25, NULL, 87, NULL, NULL, NULL),
(6, NULL, NULL, '2024-11-25 12:17:26.830491', NULL, NULL, NULL, NULL, 'Insti/MO/00028', '2024-11-22 07:03:02.033000', '2024-11-22 07:00:32.033000', 'PLANNED', NULL, 27, NULL, 87, NULL, NULL, NULL),
(7, NULL, NULL, '2024-11-25 12:17:26.838572', NULL, NULL, NULL, NULL, 'Insti/MO/00028', '2024-11-22 07:06:58.594000', '2024-11-22 07:01:58.594000', 'PLANNED', NULL, 25, NULL, 87, NULL, NULL, NULL),
(8, NULL, NULL, '2024-11-25 12:17:26.844936', NULL, NULL, NULL, NULL, 'Insti/MO/00028', '2024-11-22 07:16:08.594000', '2024-11-22 07:11:58.594000', 'PLANNED', NULL, 26, NULL, 87, NULL, NULL, NULL),
(9, NULL, NULL, '2024-11-25 12:17:26.851996', NULL, NULL, NULL, NULL, 'Insti/MO/00028', '2024-11-22 07:28:38.594000', '2024-11-22 07:21:08.594000', 'PLANNED', NULL, 28, NULL, 87, NULL, NULL, NULL),
(10, NULL, NULL, '2024-11-25 12:17:26.859369', NULL, NULL, NULL, NULL, 'Insti/MO/00028', '2024-11-22 08:11:08.594000', '2024-11-22 07:33:38.594000', 'PLANNED', NULL, 24, NULL, 87, NULL, NULL, NULL),
(11, NULL, NULL, '2024-11-25 12:17:26.866633', NULL, NULL, NULL, NULL, 'Insti/MO/00028', '2024-11-22 09:06:08.594000', '2024-11-22 08:16:08.594000', 'PLANNED', NULL, 29, NULL, 87, NULL, NULL, NULL),
(12, NULL, NULL, '2024-11-25 12:17:26.873255', NULL, NULL, NULL, NULL, 'Insti/MO/00028', '2024-11-22 09:18:38.594000', '2024-11-22 09:11:08.594000', 'PLANNED', NULL, 30, NULL, 87, NULL, NULL, NULL),
(13, NULL, NULL, '2024-11-25 12:17:26.879723', NULL, NULL, NULL, NULL, 'Insti/MO/00028', '2024-11-22 09:48:38.594000', '2024-11-22 09:23:38.594000', 'PLANNED', NULL, 31, NULL, 87, NULL, NULL, NULL),
(14, NULL, NULL, '2024-11-25 12:17:26.886885', NULL, NULL, NULL, NULL, 'Insti/MO/00028', '2024-11-22 10:18:38.594000', '2024-11-22 09:53:38.594000', 'PLANNED', NULL, 32, NULL, 87, NULL, NULL, NULL),
(15, NULL, NULL, '2024-11-25 12:17:26.547785', NULL, NULL, NULL, NULL, 'Insti/MO/00027', '2024-11-26 08:15:00.190000', '2024-11-26 08:10:00.190000', 'PLANNED', NULL, NULL, NULL, 86, NULL, NULL, NULL),
(16, NULL, NULL, '2024-11-25 12:17:26.567787', NULL, NULL, NULL, NULL, 'Insti/MO/00027', '2024-11-26 08:18:53.771000', '2024-11-26 08:11:23.771000', 'PLANNED', NULL, NULL, NULL, 86, NULL, NULL, NULL),
(17, NULL, NULL, '2024-11-25 12:17:26.585010', NULL, NULL, NULL, NULL, 'Insti/MO/00027', '2024-11-26 08:30:05.218000', '2024-11-26 08:12:35.218000', 'PLANNED', NULL, NULL, NULL, 86, NULL, NULL, NULL),
(18, NULL, NULL, '2024-11-25 12:17:26.601264', NULL, NULL, NULL, NULL, 'Insti/MO/00027', '2024-11-26 08:47:35.218000', '2024-11-26 08:35:05.218000', 'PLANNED', NULL, 26, NULL, 86, NULL, NULL, NULL),
(19, NULL, NULL, '2024-11-25 12:17:26.616326', NULL, NULL, NULL, NULL, 'Insti/MO/00027', '2024-11-26 10:55:08.536000', '2024-11-26 09:02:38.536000', 'PLANNED', NULL, 24, NULL, 86, NULL, NULL, NULL),
(20, NULL, NULL, '2024-11-25 12:17:26.629924', NULL, NULL, NULL, NULL, 'Insti/MO/00027', '2024-11-26 13:25:23.611000', '2024-11-26 10:55:23.611000', 'PLANNED', NULL, 29, NULL, 86, NULL, NULL, NULL),
(21, NULL, NULL, '2024-11-25 12:17:26.642892', NULL, NULL, NULL, NULL, 'Insti/MO/00027', '2024-11-26 12:04:16.381000', '2024-11-26 11:39:16.381000', 'PLANNED', NULL, 30, NULL, 86, NULL, NULL, NULL),
(22, NULL, NULL, '2024-11-25 12:17:26.663557', NULL, NULL, NULL, NULL, 'Insti/MO/00027', '2024-11-26 15:00:11.853000', '2024-11-26 13:45:11.853000', 'PLANNED', NULL, 31, NULL, 86, NULL, NULL, NULL),
(23, NULL, NULL, '2024-11-25 12:17:26.677326', NULL, NULL, NULL, NULL, 'Insti/MO/00027', '2024-11-26 16:26:50.747000', '2024-11-26 15:11:50.747000', 'PLANNED', NULL, 32, NULL, 86, NULL, NULL, NULL),
(24, NULL, NULL, '2024-11-25 12:17:26.690639', NULL, NULL, NULL, NULL, 'Insti/MO/00027', '2024-11-26 09:02:56.551000', '2024-11-26 08:37:56.551000', 'PLANNED', NULL, 28, NULL, 86, NULL, NULL, NULL);

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
(1, 33),
(2, 34),
(3, 36),
(4, 35),
(5, 59),
(6, 60),
(7, 61),
(8, 62),
(9, 63),
(10, 64),
(11, 65),
(12, 66),
(13, 67),
(14, 68),
(15, 49),
(16, 50),
(17, 51),
(18, 52),
(19, 54),
(20, 55),
(21, 56),
(22, 57),
(23, 58),
(24, 53);

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
(79, '2024-11-25 12:10:21.802742', 'Insti/MO/00004', '2024-11-25 12:10:21.802774', '', '2024-05-24 22:06:00.000000', 150, 'pcs', 'Insti/MO/00004', 0, 'PLANNED', 78, NULL, 3),
(80, '2024-11-25 12:10:21.819612', 'Insti/MO/00014', '2024-11-25 12:10:21.819645', '', '2024-05-27 10:47:47.000000', 50, 'pcs', 'Insti/MO/00014', 0, 'PLANNED', 78, NULL, 4),
(81, '2024-11-25 12:10:21.836818', 'Insti/MO/00015', '2024-11-25 12:10:21.836856', '', '2024-05-28 11:02:52.000000', 50, 'pcs', 'Insti/MO/00015', 0, 'PLANNED', 78, NULL, 5),
(82, '2024-11-25 12:10:21.855132', 'Insti/MO/00017', '2024-11-25 12:10:21.855166', '', '2024-05-27 10:16:19.000000', 50, 'pcs', 'Insti/MO/00017', 0, 'PLANNED', 78, NULL, 6),
(83, '2024-11-25 12:10:21.872846', 'Insti/MO/00018', '2024-11-25 12:10:21.872878', '', '2024-05-27 11:43:31.000000', 50, 'pcs', 'Insti/MO/00018', 0, 'PLANNED', 78, NULL, 7),
(84, '2024-11-25 12:10:21.890536', 'Insti/MO/00019', '2024-11-25 12:10:21.890575', '', '2024-05-27 15:51:30.000000', 50, 'pcs', 'Insti/MO/00019', 0, 'PLANNED', 78, NULL, 8),
(85, NULL, 'Insti/MO/00025', '2024-11-25 12:10:22.022238', '', '2024-05-30 09:05:45.000000', 500, 'pcs', 'Insti/MO/00025', 0, 'PLANNED', 78, NULL, 9),
(86, NULL, 'Insti/MO/00027', '2024-11-25 12:10:22.163670', '', '2024-05-31 15:37:59.000000', 150, 'pcs', 'Insti/MO/00027', 0, 'PLANNED', 78, NULL, 10),
(87, NULL, 'Insti/MO/00028', '2024-11-25 12:10:22.300399', '', '2024-05-31 12:39:41.000000', 50, 'pcs', 'Insti/MO/00028', 0, 'PLANNED', 78, NULL, 11),
(88, NULL, 'Insti/MO/00046', '2024-11-25 12:10:22.379143', '', '2024-07-08 09:53:38.000000', 5, 'pcs', 'Insti/MO/00046', 0, 'PLANNED', 78, NULL, 12),
(89, '2024-11-25 12:10:22.395381', 'Insti/MO/00047', '2024-11-25 12:10:22.395417', '', '2024-06-27 07:15:01.000000', 5, 'pcs', 'Insti/MO/00047', 0, 'PLANNED', 78, NULL, 13),
(90, '2024-11-25 12:10:22.413781', 'Insti/MO/00049', '2024-11-25 12:10:22.413842', '', '2024-12-05 17:08:00.000000', 688, 'pcs', 'Insti/MO/00049', 0, 'PLANNED', 78, NULL, 14),
(91, '2024-11-25 12:10:22.432138', 'Insti/MO/00050', '2024-11-25 12:10:22.432171', '', '2024-11-26 12:19:28.000000', 700, 'pcs', 'Insti/MO/00050', 0, 'PLANNED', 78, NULL, 15);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ProductionOrder_Note`
--

CREATE TABLE `ProductionOrder_Note` (
  `ProductionOrder_id` bigint NOT NULL,
  `notes_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Time_Durations`
--

CREATE TABLE `Time_Durations` (
  `machineOccupation` bigint NOT NULL,
  `duration` bigint DEFAULT NULL,
  `timeType` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Time_DurationsStep`
--

CREATE TABLE `Time_DurationsStep` (
  `subProductionStep` bigint NOT NULL,
  `duration` bigint DEFAULT NULL,
  `timeType` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `UserOccupation_ActiveUsers`
--

CREATE TABLE `UserOccupation_ActiveUsers` (
  `user_occupation_id` bigint NOT NULL,
  `user_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `UserOccupation_InactiveUsers`
--

CREATE TABLE `UserOccupation_InactiveUsers` (
  `user_occupation_id` bigint NOT NULL,
  `user_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `UserOccupation_TimeRecords`
--

CREATE TABLE `UserOccupation_TimeRecords` (
  `user_occupation_id` bigint NOT NULL,
  `timerecord_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `MachineOccupation`
--
ALTER TABLE `MachineOccupation`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

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
