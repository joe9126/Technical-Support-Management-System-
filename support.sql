-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 13, 2020 at 01:46 PM
-- Server version: 10.4.13-MariaDB
-- PHP Version: 7.4.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `support`
--

-- --------------------------------------------------------

--
-- Table structure for table `billingrequests`
--

CREATE TABLE `billingrequests` (
  `REQNO` varchar(45) NOT NULL,
  `REFNO` varchar(45) DEFAULT NULL,
  `BILLFROM` varchar(20) DEFAULT 'N/A',
  `BILLTO` varchar(20) DEFAULT 'N/A',
  `BILLAMOUNT` varchar(45) DEFAULT NULL,
  `STATUS` varchar(45) DEFAULT NULL,
  `INVOICENO` varchar(45) DEFAULT NULL,
  `DATECREATED` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `calls`
--

CREATE TABLE `calls` (
  `CALL_DATE` date NOT NULL,
  `CALL_NO` varchar(50) NOT NULL,
  `TECHNICIAN` varchar(50) NOT NULL,
  `CONTRACT_NO` varchar(50) NOT NULL,
  `STATUS` varchar(100) DEFAULT 'PENDING',
  `TO_DO` varchar(100) NOT NULL,
  `REPORTEDBY` varchar(200) DEFAULT 'NA',
  `LOCATION` varchar(200) DEFAULT NULL,
  `PRIORITY` varchar(100) DEFAULT 'NA',
  `RESOL_TIMELINE_DAYS` int(20) DEFAULT 1,
  `CLIENT` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `callstatus`
--

CREATE TABLE `callstatus` (
  `CALL_NO` varchar(100) NOT NULL,
  `TIMEOPENED` time NOT NULL,
  `STATUS` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `calltechs`
--

CREATE TABLE `calltechs` (
  `ID` int(11) NOT NULL,
  `CALLNO` varchar(100) DEFAULT NULL,
  `TECH` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `ci_sessions`
--

CREATE TABLE `ci_sessions` (
  `id` varchar(40) NOT NULL,
  `ip_address` varchar(45) NOT NULL,
  `timestamp` int(10) UNSIGNED NOT NULL DEFAULT 0,
  `data` blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ci_sessions`
--

INSERT INTO `ci_sessions` (`id`, `ip_address`, `timestamp`, `data`) VALUES
('t4dspdk9galiu6ee7mcu1dq0qsmcrirq', '::1', 1585474860, 0x5f5f63695f6c6173745f726567656e65726174657c693a313538353437343836303b),
('gthr1afsporqsg21uv60csep4d58cm53', '::1', 1585475858, 0x5f5f63695f6c6173745f726567656e65726174657c693a313538353437353835383b),
('e166r2n82lpiic2u8ghv9o8fbt05tm8k', '::1', 1585476041, 0x5f5f63695f6c6173745f726567656e65726174657c693a313538353437353835383b),
('j4hq5um21bvrtpidibfov7hjph39pqul', '::1', 1585757984, 0x5f5f63695f6c6173745f726567656e65726174657c693a313538353735373935323b),
('8kv6vhe48ll9883mp2ohvg39gv0kpepi', '::1', 1585810768, 0x5f5f63695f6c6173745f726567656e65726174657c693a313538353831303736343b),
('dotl93469hfobuhg0ifcas2qndbh1r29', '::1', 1585814973, 0x5f5f63695f6c6173745f726567656e65726174657c693a313538353831343937333b),
('8pms9sdm8m4dvf4ae59713r3hjqe8mh0', '::1', 1585817979, 0x5f5f63695f6c6173745f726567656e65726174657c693a313538353831343937333b),
('g81at8nqn6vrk93t4mlgctb21gjmcv0l', '::1', 1586160459, 0x5f5f63695f6c6173745f726567656e65726174657c693a313538363136303433323b);

-- --------------------------------------------------------

--
-- Table structure for table `claims`
--

CREATE TABLE `claims` (
  `CALL_NO` varchar(20) NOT NULL,
  `SERVICE_NO` varchar(20) NOT NULL,
  `KM` double DEFAULT 0,
  `PSVFARE` int(11) DEFAULT 0,
  `BFAST` int(11) DEFAULT 0,
  `LUNCH` int(11) DEFAULT 0,
  `DINNER` int(11) DEFAULT 0,
  `ACCOMOD` int(11) DEFAULT 0,
  `LAUNDRY` int(11) DEFAULT 0,
  `PETTIES` int(11) DEFAULT 0,
  `OTHERS` int(11) DEFAULT 0,
  `STATUS` varchar(20) DEFAULT 'UNCLAIMED',
  `DATE_CLAIMED` varchar(20) DEFAULT 'NA',
  `CLAIMNO` varchar(45) DEFAULT 'NA'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `clients`
--

CREATE TABLE `clients` (
  `CLIENT_NO` varchar(50) NOT NULL,
  `CLIENTNAME` varchar(500) NOT NULL,
  `POBOX` varchar(500) NOT NULL,
  `TOWN` varchar(500) NOT NULL,
  `CONT_PERSON` varchar(500) NOT NULL,
  `MOBILE` varchar(100) NOT NULL,
  `EMAIL` varchar(500) NOT NULL,
  `DATE_ADDED` date NOT NULL,
  `STATUS` varchar(45) DEFAULT NULL,
  `EMAIL_1` varchar(200) DEFAULT NULL,
  `EMAIL_2` varchar(200) DEFAULT NULL,
  `EMAIL_3` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `clientschedule`
--

CREATE TABLE `clientschedule` (
  `CLIENT_NO` varchar(50) NOT NULL,
  `CONTRACT_NO` varchar(100) NOT NULL,
  `SERVICEPERIOD_ID` varchar(50) NOT NULL,
  `SERVICE_FROM` date NOT NULL,
  `SERVICE_TO` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `contracts`
--

CREATE TABLE `contracts` (
  `CONTRACT_NO` varchar(50) NOT NULL,
  `CLIENT_NO` varchar(30) NOT NULL,
  `CONT_DESCRIP` varchar(500) NOT NULL,
  `START` date NOT NULL,
  `END` date NOT NULL,
  `CURRENCY` varchar(3) NOT NULL,
  `VALUE` double NOT NULL,
  `PM_SCHEDULE` varchar(30) NOT NULL,
  `BILL_SCHEDULE` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `csrapproval`
--

CREATE TABLE `csrapproval` (
  `CSRNO` varchar(100) NOT NULL,
  `SALESAPPROVAL` varchar(45) DEFAULT NULL,
  `DEPTAPPROVAL` varchar(45) DEFAULT NULL,
  `FINAPPROVAL` varchar(45) DEFAULT NULL,
  `DIRAPPROVAL` varchar(45) DEFAULT NULL,
  `SALESAPPROVALDATE` datetime DEFAULT NULL,
  `DEPTAPPROVALDATE` datetime DEFAULT NULL,
  `FINAPPROVALDATE` datetime DEFAULT NULL,
  `DIRAPPROVALDATE` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `csritems`
--

CREATE TABLE `csritems` (
  `CSRNO` varchar(20) NOT NULL,
  `PARTNO` varchar(50) NOT NULL,
  `DESCRIPTION` varchar(300) NOT NULL,
  `QTY` int(11) NOT NULL,
  `UNITCOST` double NOT NULL DEFAULT 16,
  `ID` int(45) NOT NULL,
  `VAT` double DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `csr_attachments`
--

CREATE TABLE `csr_attachments` (
  `CSRNO` varchar(100) NOT NULL,
  `LPO` longblob DEFAULT NULL,
  `COSTING` longblob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `equipment`
--

CREATE TABLE `equipment` (
  `SERIAL_NO` varchar(50) NOT NULL,
  `CONTRACT_NO` varchar(70) NOT NULL,
  `MODEL` varchar(70) NOT NULL,
  `DESCRIPTION` varchar(100) NOT NULL,
  `LOCATION` varchar(70) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `gatepass`
--

CREATE TABLE `gatepass` (
  `ITEMNUM` int(11) NOT NULL,
  `SERIAL_NO` varchar(100) NOT NULL,
  `MODEL` varchar(100) NOT NULL,
  `DESCRIPTION` varchar(100) NOT NULL,
  `FAULT` varchar(100) NOT NULL,
  `WARRANTY` varchar(10) NOT NULL,
  `DATEIN` date NOT NULL,
  `GINNO` varchar(100) NOT NULL,
  `RECEIVEDBY` varchar(100) NOT NULL,
  `CLIENTNO` varchar(30) NOT NULL,
  `BROUGHTBY` varchar(100) NOT NULL,
  `IDNO` varchar(10) NOT NULL,
  `PHONE` varchar(10) NOT NULL,
  `DATEOUT` varchar(20) NOT NULL,
  `TAKENBY` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `maintenancelist`
--

CREATE TABLE `maintenancelist` (
  `CONTRACT_NO` varchar(100) NOT NULL,
  `Q1PM` varchar(20) NOT NULL,
  `Q2PM` varchar(20) NOT NULL,
  `Q3PM` varchar(20) NOT NULL,
  `Q4PM` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `mileagesettings`
--

CREATE TABLE `mileagesettings` (
  `STAFFLEVEL` int(11) NOT NULL,
  `TOWN` varchar(100) NOT NULL,
  `FIRSTKM` int(11) NOT NULL,
  `RATE_FIRSTKM` int(11) NOT NULL,
  `RATE_EXTRAKM` int(11) NOT NULL,
  `ACCOM_RECEIPTED` int(11) NOT NULL,
  `ACCOM_NORECEPT` int(11) NOT NULL,
  `BREAKF_RECEIPT` int(11) NOT NULL,
  `BREAKF_NORECEPT` int(11) NOT NULL,
  `LUNCH_RECEIPT` int(11) NOT NULL,
  `LUNCH_NORECEPT` int(11) NOT NULL,
  `DINNER_RECEIPT` int(11) NOT NULL,
  `DINNER_NORECEPT` int(11) NOT NULL,
  `LAUNDRY` int(11) NOT NULL,
  `PETTIES` int(11) NOT NULL,
  `OTHERS` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

CREATE TABLE `notifications` (
  `EMAILSENDER` varchar(200) DEFAULT NULL,
  `SENDERMAILPWORD` varchar(200) DEFAULT NULL,
  `GROUPRECIPMAIL` varchar(200) DEFAULT NULL,
  `SMTPHOST` varchar(200) DEFAULT NULL,
  `SMTPPORT` varchar(200) DEFAULT NULL,
  `SIGNATURE` longblob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `partstock`
--

CREATE TABLE `partstock` (
  `ENTRY_ID` varchar(20) NOT NULL,
  `CLIENT_NO` varchar(50) NOT NULL,
  `REFNO` varchar(100) NOT NULL,
  `TICKETNO` varchar(100) NOT NULL,
  `PARTNO` varchar(100) NOT NULL,
  `DESCRIPTION` varchar(300) NOT NULL,
  `QUANTITY` int(11) NOT NULL,
  `DATEIN` date NOT NULL,
  `RECEIVEDBY` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `partstockout`
--

CREATE TABLE `partstockout` (
  `ENTRY_ID` varchar(100) NOT NULL,
  `PARTNO` varchar(100) NOT NULL,
  `TICKETOUT` varchar(50) NOT NULL,
  `QTY_OUT` int(11) NOT NULL,
  `DATE_OUT` date NOT NULL,
  `COLLECTEDBY` varchar(50) NOT NULL,
  `ISSUEDBY` varchar(100) NOT NULL,
  `CLIENT` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sales_supplyreq`
--

CREATE TABLE `sales_supplyreq` (
  `NUM` int(11) NOT NULL,
  `CLIENTNO` varchar(20) NOT NULL,
  `CSRNO` varchar(20) NOT NULL,
  `DESCRIPTION` varchar(100) NOT NULL,
  `CSRDATE` date NOT NULL,
  `PODATE` date NOT NULL,
  `PONUM` varchar(100) NOT NULL,
  `CURRENCY` varchar(20) NOT NULL,
  `CSRVALUE` double NOT NULL,
  `SOLDBY` varchar(100) NOT NULL,
  `INVOICE_NO` varchar(45) DEFAULT NULL,
  `DEPT_APPROVAL` varchar(100) DEFAULT NULL,
  `FIN_APPROVAL` varchar(100) DEFAULT NULL,
  `DIR_APPROVAL` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `service`
--

CREATE TABLE `service` (
  `CALL_NO` varchar(20) NOT NULL,
  `SERVICE_NO` varchar(50) NOT NULL,
  `SERVICE_DATE` date NOT NULL,
  `FROM2` varchar(6) NOT NULL,
  `TO2` varchar(6) NOT NULL,
  `LOCATION` varchar(50) NOT NULL,
  `TOWN` varchar(50) NOT NULL,
  `EQUIP_DESCRIP` varchar(100) NOT NULL,
  `EQUIP_MODEL` varchar(200) NOT NULL,
  `SERIAL` varchar(200) NOT NULL,
  `ACTION` varchar(10000) NOT NULL,
  `STATUS` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `serviceschedule`
--

CREATE TABLE `serviceschedule` (
  `SERVICEPERIOD_ID` varchar(100) NOT NULL,
  `EQUIP_SERIAL` varchar(100) NOT NULL,
  `SERVICE_DATE` varchar(100) NOT NULL,
  `TIME` varchar(10) NOT NULL,
  `TECH` varchar(20) NOT NULL,
  `PHONE` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `service_findings`
--

CREATE TABLE `service_findings` (
  `service_no` varchar(100) NOT NULL,
  `findings` varchar(1000) DEFAULT NULL,
  `ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `service_recommendations`
--

CREATE TABLE `service_recommendations` (
  `service_no` varchar(100) DEFAULT NULL,
  `recommendations` varchar(1000) DEFAULT NULL,
  `ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `session`
--

CREATE TABLE `session` (
  `USER` varchar(30) NOT NULL,
  `username` varchar(30) NOT NULL,
  `password` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `settings`
--

CREATE TABLE `settings` (
  `NAME` varchar(50) NOT NULL,
  `MOTTO` varchar(50) NOT NULL,
  `ADDRESS` varchar(30) NOT NULL,
  `TOWN` varchar(20) NOT NULL,
  `LOGO` longblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `STAFFNO` varchar(100) NOT NULL,
  `STAFFNAME` varchar(100) NOT NULL,
  `PHONE` varchar(20) DEFAULT NULL,
  `DEPT` varchar(50) NOT NULL,
  `POST` varchar(50) NOT NULL,
  `STAFFLEVEL` int(11) NOT NULL,
  `PASSPORT` longblob DEFAULT NULL,
  `EMAIL` varchar(200) DEFAULT 'NA'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`STAFFNO`, `STAFFNAME`, `PHONE`, `DEPT`, `POST`, `STAFFLEVEL`, `PASSPORT`, `EMAIL`) VALUES
('28788529', 'Joe Prime', '0702907485', 'Tech', 'Dev', 13, '', 'joeasewe@symphony.co.ke');

-- --------------------------------------------------------

--
-- Table structure for table `stafftemp`
--

CREATE TABLE `stafftemp` (
  `MEMBERPHOTO` longblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `supply_requests`
--

CREATE TABLE `supply_requests` (
  `NUM` int(11) NOT NULL,
  `CLIENTNO` varchar(20) NOT NULL,
  `CSRNO` varchar(20) NOT NULL,
  `DESCRIPTION` varchar(100) NOT NULL,
  `CSRDATE` date NOT NULL,
  `PODATE` date NOT NULL,
  `PONUM` varchar(100) NOT NULL,
  `CURRENCY` varchar(20) NOT NULL,
  `CSRVALUE` double NOT NULL,
  `SOLDBY` varchar(100) NOT NULL,
  `INVOICE_NO` varchar(45) DEFAULT NULL,
  `DEPT_APPROVAL` varchar(100) DEFAULT NULL,
  `FIN_APPROVAL` varchar(100) DEFAULT NULL,
  `DIR_APPROVAL` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `system`
--

CREATE TABLE `system` (
  `LOGO` blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tempphoto`
--

CREATE TABLE `tempphoto` (
  `LOGO` longblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `userlog`
--

CREATE TABLE `userlog` (
  `LOGIN_ID` varchar(50) NOT NULL,
  `USER_ID` varchar(100) NOT NULL,
  `USER` varchar(50) NOT NULL,
  `LOGIN_DATE` varchar(50) NOT NULL,
  `LOGIN_TIME` varchar(20) NOT NULL,
  `LOGOUT_DATE` varchar(50) NOT NULL,
  `LOGOUT_TIME` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `TYPE` varchar(20) NOT NULL,
  `USERNAME` varchar(50) NOT NULL,
  `PASSWORD` varchar(50) NOT NULL,
  `OWNER` varchar(50) NOT NULL,
  `STATUS` varchar(45) DEFAULT 'UNLOCKED'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`TYPE`, `USERNAME`, `PASSWORD`, `OWNER`, `STATUS`) VALUES
('ADMIN', 'PRIME', 'PRIME', '28788529', 'UNLOCKED');

-- --------------------------------------------------------

--
-- Table structure for table `webusers`
--

CREATE TABLE `webusers` (
  `type` varchar(20) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(200) NOT NULL,
  `owner` varchar(100) NOT NULL,
  `status` varchar(50) NOT NULL,
  `role` varchar(45) DEFAULT 'Technician'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `webusers`
--

INSERT INTO `webusers` (`type`, `username`, `password`, `email`, `owner`, `status`, `role`) VALUES
('ADMIN', 'PRIME', 'PRIME', 'joeasewe@symphony.co.ke', '28788529', 'Active', 'Technician');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `billingrequests`
--
ALTER TABLE `billingrequests`
  ADD PRIMARY KEY (`REQNO`),
  ADD UNIQUE KEY `REQNO_UNIQUE` (`REQNO`);

--
-- Indexes for table `calls`
--
ALTER TABLE `calls`
  ADD PRIMARY KEY (`CALL_NO`),
  ADD UNIQUE KEY `CALL_NO` (`CALL_NO`);

--
-- Indexes for table `callstatus`
--
ALTER TABLE `callstatus`
  ADD PRIMARY KEY (`CALL_NO`);

--
-- Indexes for table `calltechs`
--
ALTER TABLE `calltechs`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `ci_sessions`
--
ALTER TABLE `ci_sessions`
  ADD KEY `ci_sessions_timestamp` (`timestamp`);

--
-- Indexes for table `claims`
--
ALTER TABLE `claims`
  ADD PRIMARY KEY (`SERVICE_NO`),
  ADD UNIQUE KEY `SERVICE_NO` (`SERVICE_NO`);

--
-- Indexes for table `clients`
--
ALTER TABLE `clients`
  ADD PRIMARY KEY (`CLIENT_NO`,`POBOX`),
  ADD UNIQUE KEY `CLIENT_NO` (`CLIENT_NO`),
  ADD UNIQUE KEY `CLIENTNAME` (`CLIENTNAME`);

--
-- Indexes for table `clientschedule`
--
ALTER TABLE `clientschedule`
  ADD PRIMARY KEY (`SERVICEPERIOD_ID`);

--
-- Indexes for table `contracts`
--
ALTER TABLE `contracts`
  ADD PRIMARY KEY (`CONTRACT_NO`),
  ADD UNIQUE KEY `CONTRACT_NO_UNIQUE` (`CONTRACT_NO`);

--
-- Indexes for table `csrapproval`
--
ALTER TABLE `csrapproval`
  ADD PRIMARY KEY (`CSRNO`);

--
-- Indexes for table `csritems`
--
ALTER TABLE `csritems`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `csr_attachments`
--
ALTER TABLE `csr_attachments`
  ADD PRIMARY KEY (`CSRNO`);

--
-- Indexes for table `equipment`
--
ALTER TABLE `equipment`
  ADD PRIMARY KEY (`SERIAL_NO`);

--
-- Indexes for table `gatepass`
--
ALTER TABLE `gatepass`
  ADD PRIMARY KEY (`ITEMNUM`),
  ADD UNIQUE KEY `ITEMNUM_UNIQUE` (`ITEMNUM`);

--
-- Indexes for table `maintenancelist`
--
ALTER TABLE `maintenancelist`
  ADD PRIMARY KEY (`CONTRACT_NO`);

--
-- Indexes for table `partstock`
--
ALTER TABLE `partstock`
  ADD PRIMARY KEY (`ENTRY_ID`),
  ADD UNIQUE KEY `ENTRY_ID_UNIQUE` (`ENTRY_ID`);

--
-- Indexes for table `sales_supplyreq`
--
ALTER TABLE `sales_supplyreq`
  ADD PRIMARY KEY (`NUM`);

--
-- Indexes for table `service`
--
ALTER TABLE `service`
  ADD PRIMARY KEY (`SERVICE_NO`),
  ADD UNIQUE KEY `SERVICE_NO` (`SERVICE_NO`);

--
-- Indexes for table `service_findings`
--
ALTER TABLE `service_findings`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `service_recommendations`
--
ALTER TABLE `service_recommendations`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`STAFFNO`),
  ADD UNIQUE KEY `STAFFNO` (`STAFFNO`);

--
-- Indexes for table `supply_requests`
--
ALTER TABLE `supply_requests`
  ADD PRIMARY KEY (`NUM`);

--
-- Indexes for table `userlog`
--
ALTER TABLE `userlog`
  ADD UNIQUE KEY `LOGIN_ID` (`LOGIN_ID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD UNIQUE KEY `OWNER` (`OWNER`);

--
-- Indexes for table `webusers`
--
ALTER TABLE `webusers`
  ADD PRIMARY KEY (`owner`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `calltechs`
--
ALTER TABLE `calltechs`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `csritems`
--
ALTER TABLE `csritems`
  MODIFY `ID` int(45) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `gatepass`
--
ALTER TABLE `gatepass`
  MODIFY `ITEMNUM` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sales_supplyreq`
--
ALTER TABLE `sales_supplyreq`
  MODIFY `NUM` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `service_findings`
--
ALTER TABLE `service_findings`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `service_recommendations`
--
ALTER TABLE `service_recommendations`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `supply_requests`
--
ALTER TABLE `supply_requests`
  MODIFY `NUM` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
