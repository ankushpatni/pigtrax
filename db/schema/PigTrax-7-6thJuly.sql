-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.8.0
-- PostgreSQL version: 9.4
-- Project Site: pgmodeler.com.br
-- Model Author: ---

-- object: pitraxadmin | type: ROLE --
DROP ROLE IF EXISTS pitraxadmin;
CREATE ROLE pitraxadmin WITH LOGIN
	SUPERUSER
	UNENCRYPTED PASSWORD 'k3rm1t';
-- ddl-end --


-- Database creation must be done outside an multicommand file.
-- These commands were put in this file only for convenience.
-- -- object: new_database | type: DATABASE --
-- -- DROP DATABASE IF EXISTS new_database;
-- CREATE DATABASE new_database
-- ;
-- -- ddl-end --
-- 

-- object: pigtrax | type: SCHEMA --
DROP SCHEMA IF EXISTS pigtrax CASCADE;
CREATE SCHEMA pigtrax;
-- ddl-end --
ALTER SCHEMA pigtrax OWNER TO pitraxadmin;
-- ddl-end --

-- object: pigtraxrefdata | type: SCHEMA --
DROP SCHEMA IF EXISTS pigtraxrefdata CASCADE;
CREATE SCHEMA pigtraxrefdata;
-- ddl-end --
ALTER SCHEMA pigtraxrefdata OWNER TO pitraxadmin;
-- ddl-end --

SET search_path TO pg_catalog,public,pigtrax,pigtraxrefdata;
-- ddl-end --

-- object: pigtrax."Company" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."Company" CASCADE;
CREATE TABLE pigtrax."Company"(
	id serial NOT NULL,
	"companyId" varchar(4) NOT NULL,
	name varchar(50) NOT NULL,
	address varchar(255) NOT NULL,
	city varchar(30) NOT NULL,
	country varchar(30) NOT NULL,
	"otherCity" varchar(30),
	"registrationNumber" varchar(20) NOT NULL,
	email varchar(50) NOT NULL,
	phone varchar(15) NOT NULL,
	"contactName" varchar(50) NOT NULL,
	payment numeric(12,2),
	"paymentDate" date,
	"isActive" bool,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"litterId" int default 0,
	CONSTRAINT "Company_U_CI" UNIQUE ("companyId"),
	CONSTRAINT "Company_PK_CI" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtrax."Company" OWNER TO postgres;
-- ddl-end --

-- object: pigtrax."Premise" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."Premise" CASCADE;
CREATE TABLE pigtrax."Premise"(
	id serial NOT NULL,
	"permiseId" varchar(10) NOT NULL,
	"id_Company" integer,
	name varchar(50) NOT NULL,
	address varchar(255),
	city varchar(30),
	"otherCity" varchar(30),
	state varchar(50),
	zipcode varchar(9),
	"isActive" bool,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"gpsLatittude" varchar(30),
	"gpsLongitude" varchar(30),
	"id_PremiseType" smallint,
	"sowSource" varchar(5),
	"lactationLength" smallint,
	CONSTRAINT "Premise_U_PI" UNIQUE ("permiseId"),
	CONSTRAINT "Premise_PK_PI" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtrax."Premise" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "Company_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."Premise" DROP CONSTRAINT IF EXISTS "Company_fk" CASCADE;
ALTER TABLE pigtrax."Premise" ADD CONSTRAINT "Company_fk" FOREIGN KEY ("id_Company")
REFERENCES pigtrax."Company" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtrax."Barn" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."Barn" CASCADE;
CREATE TABLE pigtrax."Barn"(
	id serial NOT NULL,
	"barnId" varchar(15) NOT NULL,
	"id_Premise" integer,
	"id_PhaseType" integer,
	location varchar(30),
	area numeric(12,2),
	"feederCount" smallint,
	"waterAccessCount" smallint,
	"isActive" bool,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_VentilationType" integer,
	"id_BarnOrientation" smallint,
	"id_BarnLocation" smallint,
	"id_WaterType" smallint,
	"id_BarnPosition" smallint,
	"id_FeederType" smallint,
	"holesPerFeeder" integer,
	"remarks" varchar(255),
	"year" int,
	CONSTRAINT "BARN_U_BI" UNIQUE ("barnId"),
	CONSTRAINT "BARN_PK" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtrax."Barn" OWNER TO pitraxadmin;
-- ddl-end --



-- object: "Premise_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."Barn" DROP CONSTRAINT IF EXISTS "Premise_fk" CASCADE;
ALTER TABLE pigtrax."Barn" ADD CONSTRAINT "Premise_fk" FOREIGN KEY ("id_Premise")
REFERENCES pigtrax."Premise" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;

-- object: pigtrax."Silo" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."Silo" CASCADE;
CREATE TABLE pigtrax."Silo"(
	id serial NOT NULL,
	"siloId" varchar(20) NOT NULL,
	"id_Barn" integer,
	location varchar(30),
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_SiloType" integer,
	CONSTRAINT "SILO_PK" PRIMARY KEY (id),
	CONSTRAINT "SILO_U_SI" UNIQUE ("siloId")

);
-- ddl-end --
ALTER TABLE pigtrax."Silo" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "Barn_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."Silo" DROP CONSTRAINT IF EXISTS "Barn_fk" CASCADE;
ALTER TABLE pigtrax."Silo" ADD CONSTRAINT "Barn_fk" FOREIGN KEY ("id_Barn")
REFERENCES pigtrax."Barn" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtrax."Room" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."Room" CASCADE;
CREATE TABLE pigtrax."Room"(
	id serial NOT NULL,
	"roomId" varchar(20) NOT NULL,
	location varchar(30),
	"isActive" bool,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_Barn" integer,
	"id_roomPosition" integer,
	"pigSpaces" integer,
	CONSTRAINT "ROOM_PK" PRIMARY KEY (id),
	CONSTRAINT "ROOM_U_RI" UNIQUE ("roomId")

);
-- ddl-end --
ALTER TABLE pigtrax."Room" OWNER TO pitraxadmin;
-- ddl-end --

-- object: pigtrax."Pen" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."Pen" CASCADE;
CREATE TABLE pigtrax."Pen"(
	id serial NOT NULL,
	"penId" varchar(18) NOT NULL,
	"id_Room" integer,
	location varchar(30),
	"isActive" bool,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "PEN_PI" PRIMARY KEY (id),
	CONSTRAINT "PEN_U_PI" UNIQUE ("penId")

);
-- ddl-end --
ALTER TABLE pigtrax."Pen" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "Room_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."Pen" DROP CONSTRAINT IF EXISTS "Room_fk" CASCADE;
ALTER TABLE pigtrax."Pen" ADD CONSTRAINT "Room_fk" FOREIGN KEY ("id_Room")
REFERENCES pigtrax."Room" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: pigtrax."Employee" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."Employee" CASCADE;
CREATE TABLE pigtrax."Employee"(
	id serial NOT NULL,
	"employeeId" varchar(20),
	"id_Company" integer,
	name varchar(30) NOT NULL,
	"ptPassword" varchar(200),
	"isActive" bool,
	"isPortalUser" boolean NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_RoleType" integer,
	"email" varchar(100) NOT NULL,
	"id_FunctionType" integer,
	"phoneNumber" varchar(15) NOT NULL, 
	"id_JobFunctionRole" integer,
	CONSTRAINT "EMPLOYEE_PK" PRIMARY KEY (id),
	CONSTRAINT "EMPLOYEE_U_EI" UNIQUE ("employeeId")

);
-- ddl-end --
ALTER TABLE pigtrax."Employee" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "Company_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."Employee" DROP CONSTRAINT IF EXISTS "Company_fk" CASCADE;
ALTER TABLE pigtrax."Employee" ADD CONSTRAINT "Company_fk" FOREIGN KEY ("id_Company")
REFERENCES pigtrax."Company" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: pigtrax."TransportDestination" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."TransportDestination" CASCADE;
CREATE TABLE pigtrax."TransportDestination"(
	id serial NOT NULL,
	name varchar(30) NOT NULL,
	address varchar(255),
	state varchar(100),
	city varchar(100),	
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_Company" integer,
	"id_MarketType" integer,
	CONSTRAINT "DESTINATIONS_PK" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtrax."TransportDestination" OWNER TO pitraxadmin;
-- ddl-end --

-- object: pigtrax."TransportJourney" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."TransportJourney" CASCADE;
CREATE TABLE pigtrax."TransportJourney"(
	id serial NOT NULL,
	"trailerFunction" varchar(20),
	"journeyStartTime" timestamp,
	"journeyEndTime" timestamp,
	"id_TransportDestination" integer,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_TransportTruck" integer,
	"id_TransportTrailer" integer,
	CONSTRAINT "TRANSPORT_PK" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtrax."TransportJourney" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "TransportDestination_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."TransportJourney" DROP CONSTRAINT IF EXISTS "TransportDestination_fk" CASCADE;
ALTER TABLE pigtrax."TransportJourney" ADD CONSTRAINT "TransportDestination_fk" FOREIGN KEY ("id_TransportDestination")
REFERENCES pigtrax."TransportDestination" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtrax."BreedingEvent" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."BreedingEvent" CASCADE;
CREATE TABLE pigtrax."BreedingEvent"(
	id serial NOT NULL,
	"id_PigInfo" integer,
	"id_BreedingServiceType" integer,
	"serviceGroupId" varchar(30),
	"serviceStartDate" timestamp,
	"sowCondition" smallint,
	"id_Pen" int,
	"weightInKgs" numeric(20,2),
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_Premise" integer,
	"currentParity" integer default 0,
	CONSTRAINT "BREEDING_PK" PRIMARY KEY (id)
);
ALTER TABLE pigtrax."BreedingEvent" OWNER TO pitraxadmin;
-- ddl-end --


-- object: pigtrax."FarrowEvent" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."FarrowEvent" CASCADE;
CREATE TABLE pigtrax."FarrowEvent"(
	id serial NOT NULL,
	"farrowDateTime" timestamp NOT NULL,
	"id_Pen" integer,
	"liveBorns" smallint,
	"stillBorns" smallint,
	mummies smallint,
	"maleBorns" smallint,
	"femaleBorns" smallint,
	"weightInKgs" numeric(20,2),
	"inducedBirth" bool,
	"assistedBirth" bool,
	remarks varchar(255),
	"sowCondition" smallint,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_EmployeeGroup" integer,
	"id_PigInfo" integer,
	"id_PregnancyEvent" integer,
	"teats" smallint,
	"litterId" int,
	"id_PigletCondition" smallint,
	"weakBorns" smallint,
	"id_Premise" int,
	"id_BreedingEvent" integer,
 	CONSTRAINT "FAROW_PK" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtrax."FarrowEvent" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "Pen_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."FarrowEvent" DROP CONSTRAINT IF EXISTS "Pen_fk" CASCADE;
ALTER TABLE pigtrax."FarrowEvent" ADD CONSTRAINT "Pen_fk" FOREIGN KEY ("id_Pen")
REFERENCES pigtrax."Pen" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "Premise_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."FarrowEvent" DROP CONSTRAINT IF EXISTS "Premise_fk" CASCADE;
ALTER TABLE pigtrax."FarrowEvent" ADD CONSTRAINT "Premise_fk" FOREIGN KEY ("id_Premise")
REFERENCES pigtrax."Premise" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --



-- object: pigtrax."PregnancyEvent" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."PregnancyEvent" CASCADE;
CREATE TABLE pigtrax."PregnancyEvent"(
	id serial NOT NULL,
	"id_PigInfo" integer,
	"id_BreedingEvent" integer NOT NULL,
	"id_EmployeeGroup" integer,
	"id_PregnancyEventType" integer,
	"id_PregnancyExamResultType" integer,
	"examDate" timestamp,
	"resultDate" timestamp  NOT NULL,
	"sowCondition" smallint,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_Premise" integer,
	CONSTRAINT "PREGNANCYEXAM_PK" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtrax."PregnancyEvent" OWNER TO pitraxadmin;
-- ddl-end --

-- object: pigtrax."PigletStatus" | type: TABLE --
DROP TABLE IF EXISTS pigtrax."PigletStatus" CASCADE;
CREATE TABLE pigtrax."PigletStatus"(
	id serial NOT NULL, 
	"id_PigInfo" integer not null,
	"fosterFrom" integer,
	"fosterTo" integer,
	"id_PigletStatusEventType" integer NOT NULL,
	"eventDateTime" timestamp NOT NULL,
	"numberOfPigs" smallint,
	"weightInKgs" numeric(20,2),
	"eventReason" varchar(100),
	remarks varchar(255),
	"sowCondition" smallint,
	"weanGroupId" varchar(30),
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_FarrowEvent" int NOT NULL,
	"id_fosterFarrowEvent" int,
	"id_GroupEvent" int,
	"id_MortalityReasonType" integer,
	"id_Pen" int,
	"id_Premise" int,
	CONSTRAINT "PIGLETSTATUS_PK" PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE pigtrax."PigletStatus" OWNER TO pitraxadmin;
-- ddl-end --


-- object: "Company_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigletStatus" DROP CONSTRAINT IF EXISTS "Pen_fk" CASCADE;
ALTER TABLE pigtrax."PigletStatus" ADD CONSTRAINT "Pen_fk" FOREIGN KEY ("id_Pen")
REFERENCES pigtrax."Pen" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "Premise_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigletStatus" DROP CONSTRAINT IF EXISTS "Premise_fk" CASCADE;
ALTER TABLE pigtrax."PigletStatus" ADD CONSTRAINT "Premise_fk" FOREIGN KEY ("id_Premise")
REFERENCES pigtrax."Premise" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "Company_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."TransportDestination" DROP CONSTRAINT IF EXISTS "Company_fk" CASCADE;
ALTER TABLE pigtrax."TransportDestination" ADD CONSTRAINT "Company_fk" FOREIGN KEY ("id_Company")
REFERENCES pigtrax."Company" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtrax."RemovalEvent" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."RemovalEvent" CASCADE;
CREATE TABLE pigtrax."RemovalEvent"(
	id serial NOT NULL,
	"removalId" varchar(30) NOT NULL,
	"id_RemovalType" integer NOT NULL,
	remarks varchar(255),
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "REMOVAL_PK" PRIMARY KEY (id),
	CONSTRAINT "REMOVAL_U_RI" UNIQUE ("removalId")

);
-- ddl-end --
ALTER TABLE pigtrax."RemovalEvent" OWNER TO pitraxadmin;
-- ddl-end --

-- object: pigtraxrefdata."SaleEventType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."SaleEventType" CASCADE;
CREATE TABLE pigtraxrefdata."SaleEventType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "SALETYPE_PK" PRIMARY KEY (id),
	CONSTRAINT "SALETYPE_U_DE" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."SaleEventType" OWNER TO pitraxadmin;
-- ddl-end --

-- object: pigtrax."FeedEvent" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."FeedEvent" CASCADE;
CREATE TABLE pigtrax."FeedEvent"(
	id serial NOT NULL,
	"ticketNumber" varchar(30) NOT NULL,
	"feedContentId" varchar(30),
	"initialFeedEntryDateTime" timestamp ,
	"batchId" integer,
	"initialFeedQuantityKgs" numeric(20,2) ,
	"feedCost" numeric(20,2),
	"feedMedication" varchar(255),
	"id_TransportJourney" integer,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_Premise" int,
	CONSTRAINT "FEED_PK" PRIMARY KEY (id),
	CONSTRAINT "FEED_U_TN" UNIQUE ("ticketNumber","id_Premise")

);
-- ddl-end --
ALTER TABLE pigtrax."FeedEvent" OWNER TO pitraxadmin;
-- ddl-end --


-- object: "Premise_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."FeedEvent" DROP CONSTRAINT IF EXISTS "Premise_fk" CASCADE;
ALTER TABLE pigtrax."FeedEvent" ADD CONSTRAINT "Premise_fk" FOREIGN KEY ("id_Premise")
REFERENCES pigtrax."Premise" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: pigtrax."GroupEvent" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."GroupEvent" CASCADE;
CREATE TABLE pigtrax."GroupEvent"(
	id serial NOT NULL,
	"groupId" varchar(30) NOT NULL,
	"groupStartDateTime" timestamp NOT NULL,
	"groupCloseDateTime" timestamp,
	"isActive" bool NOT NULL,
	remarks varchar(255),
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_Company" integer,
	"currentInventory" smallint,
	"previousGroupId" varchar(20),
	"id_PhaseOfProductionType" integer,
	"id_Premise" integer NOT NULL,
	CONSTRAINT "PIGEVENT_PK" PRIMARY KEY (id),
	CONSTRAINT "PIGEVENT_GI_U" UNIQUE ("groupId","id_Premise")

);
-- ddl-end --
ALTER TABLE pigtrax."GroupEvent" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "Company_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."Company" DROP CONSTRAINT IF EXISTS "Company_fk" CASCADE;
ALTER TABLE pigtrax."GroupEvent" ADD CONSTRAINT "Company_fk" FOREIGN KEY ("id_Company")
REFERENCES pigtrax."Company" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: "Premise_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."Premise" DROP CONSTRAINT IF EXISTS "Premise_fk" CASCADE;
ALTER TABLE pigtrax."GroupEvent" ADD CONSTRAINT "Premise_fk" FOREIGN KEY ("id_Premise")
REFERENCES pigtrax."Premise" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "PigletStatus_GroupEvent_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigletStatus" DROP CONSTRAINT IF EXISTS "PigletStatus_GroupEvent_fk" CASCADE;
ALTER TABLE pigtrax."PigletStatus" ADD CONSTRAINT "PigletStatus_GroupEvent_fk" FOREIGN KEY ("id_GroupEvent")
REFERENCES pigtrax."GroupEvent" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: "Barn_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."Room" DROP CONSTRAINT IF EXISTS "Barn_fk" CASCADE;
ALTER TABLE pigtrax."Room" ADD CONSTRAINT "Barn_fk" FOREIGN KEY ("id_Barn")
REFERENCES pigtrax."Barn" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: pigtrax."Genetics" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."Genetics" CASCADE;
CREATE TABLE pigtrax."Genetics"(
	id serial NOT NULL,
	"id_PigInfo" integer,
	"id_GeneticsFunctionType" integer,
	"geneticCo" varchar(10) NOT NULL,
	"geneticLine" varchar(10) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT id PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtrax."Genetics" OWNER TO pitraxadmin;
-- ddl-end --

-- object: pigtrax."TransportTruck" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."TransportTruck" CASCADE;
CREATE TABLE pigtrax."TransportTruck"(
	id serial NOT NULL,
	"truckId" varchar(20) NOT NULL,
	"id_Company" integer,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"purchaseYear" integer  NOT NULL,
	"make" varchar(30),
	CONSTRAINT "TRANSPORTTRUCK_PK" PRIMARY KEY (id),
	CONSTRAINT "TANSPORTTRUCK_TI" UNIQUE ("truckId","id_Company")

);
-- ddl-end --
ALTER TABLE pigtrax."TransportTruck" OWNER TO pitraxadmin;
-- ddl-end --

-- object: pigtrax."TransportTrailer" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."TransportTrailer" CASCADE;
CREATE TABLE pigtrax."TransportTrailer"(
	id serial NOT NULL,
	"trailerId" varchar(20) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_Company" integer,
	"id_TrailerType" integer,
	"id_TrailerFunction" integer,
	"trailerYear" integer,
	"trailerMake" varchar(50),
	CONSTRAINT "TANSPORTTAILER_PK" PRIMARY KEY (id),
	CONSTRAINT "TANSPORTRAILER_TI" UNIQUE ("trailerId","id_Company")

);
-- ddl-end --
ALTER TABLE pigtrax."TransportTrailer" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "Company_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."TransportTruck" DROP CONSTRAINT IF EXISTS "Company_fk" CASCADE;
ALTER TABLE pigtrax."TransportTruck" ADD CONSTRAINT "Company_fk" FOREIGN KEY ("id_Company")
REFERENCES pigtrax."Company" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "Company_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."TransportTrailer" DROP CONSTRAINT IF EXISTS "Company_fk" CASCADE;
ALTER TABLE pigtrax."TransportTrailer" ADD CONSTRAINT "Company_fk" FOREIGN KEY ("id_Company")
REFERENCES pigtrax."Company" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "TransportTruck_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."TransportJourney" DROP CONSTRAINT IF EXISTS "TransportTruck_fk" CASCADE;
ALTER TABLE pigtrax."TransportJourney" ADD CONSTRAINT "TransportTruck_fk" FOREIGN KEY ("id_TransportTruck")
REFERENCES pigtrax."TransportTruck" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "TransportTrailer_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."TransportJourney" DROP CONSTRAINT IF EXISTS "TransportTrailer_fk" CASCADE;
ALTER TABLE pigtrax."TransportJourney" ADD CONSTRAINT "TransportTrailer_fk" FOREIGN KEY ("id_TransportTrailer")
REFERENCES pigtrax."TransportTrailer" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "TransportJourney_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."FeedEvent" DROP CONSTRAINT IF EXISTS "TransportJourney_fk" CASCADE;
ALTER TABLE pigtrax."FeedEvent" ADD CONSTRAINT "TransportJourney_fk" FOREIGN KEY ("id_TransportJourney")
REFERENCES pigtrax."TransportJourney" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtrax."EmployeeGroup" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."EmployeeGroup" CASCADE;
CREATE TABLE pigtrax."EmployeeGroup"(
	id serial NOT NULL,
	"groupId" varchar(30) NOT NULL,
	"isActive" bool,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_EmployeeJobFunction" integer,
	CONSTRAINT "EMPLOYEEGROUP_PK" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtrax."EmployeeGroup" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "EmployeeGroup_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."FarrowEvent" DROP CONSTRAINT IF EXISTS "EmployeeGroup_fk" CASCADE;
ALTER TABLE pigtrax."FarrowEvent" ADD CONSTRAINT "EmployeeGroup_fk" FOREIGN KEY ("id_EmployeeGroup")
REFERENCES pigtrax."EmployeeGroup" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "EmployeeGroup_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PregnancyEvent" DROP CONSTRAINT IF EXISTS "EmployeeGroup_fk" CASCADE;
ALTER TABLE pigtrax."PregnancyEvent" ADD CONSTRAINT "EmployeeGroup_fk" FOREIGN KEY ("id_EmployeeGroup")
REFERENCES pigtrax."EmployeeGroup" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtrax."PigTraxEventMaster" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."PigTraxEventMaster" CASCADE;
CREATE TABLE pigtrax."PigTraxEventMaster"(
	id serial NOT NULL,
	"eventTime" timestamp NOT NULL,
	"id_GroupEvent" integer,
	"id_BreedingEvent" integer,
	"id_PregnancyEvent" integer,
	"id_FarrowEvent" integer,
	"id_PigletStatus" integer,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_PigInfo" integer,
	"id_FeedEvent" integer,
	"id_RemovalEventExceptSalesDetails" integer,
	"id_SalesEventDetails" integer,
	CONSTRAINT "PIGTRAXEVENTMASTER_PK" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtrax."PigTraxEventMaster" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "GroupEvent_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigTraxEventMaster" DROP CONSTRAINT IF EXISTS "GroupEvent_fk" CASCADE;
ALTER TABLE pigtrax."PigTraxEventMaster" ADD CONSTRAINT "GroupEvent_fk" FOREIGN KEY ("id_GroupEvent")
REFERENCES pigtrax."GroupEvent" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "BreedingEvent_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigTraxEventMaster" DROP CONSTRAINT IF EXISTS "BreedingEvent_fk" CASCADE;
ALTER TABLE pigtrax."PigTraxEventMaster" ADD CONSTRAINT "BreedingEvent_fk" FOREIGN KEY ("id_BreedingEvent")
REFERENCES pigtrax."BreedingEvent" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "PregnancyEvent_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigTraxEventMaster" DROP CONSTRAINT IF EXISTS "PregnancyEvent_fk" CASCADE;
ALTER TABLE pigtrax."PigTraxEventMaster" ADD CONSTRAINT "PregnancyEvent_fk" FOREIGN KEY ("id_PregnancyEvent")
REFERENCES pigtrax."PregnancyEvent" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "FarrowEvent_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigTraxEventMaster" DROP CONSTRAINT IF EXISTS "FarrowEvent_fk" CASCADE;
ALTER TABLE pigtrax."PigTraxEventMaster" ADD CONSTRAINT "FarrowEvent_fk" FOREIGN KEY ("id_FarrowEvent")
REFERENCES pigtrax."FarrowEvent" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "PigletStatus_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigTraxEventMaster" DROP CONSTRAINT IF EXISTS "PigletStatus_fk" CASCADE;
ALTER TABLE pigtrax."PigTraxEventMaster" ADD CONSTRAINT "PigletStatus_fk" FOREIGN KEY ("id_PigletStatus")
REFERENCES pigtrax."PigletStatus" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: "FeedEvent_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigTraxEventMaster" DROP CONSTRAINT IF EXISTS "FeedEvent_fk" CASCADE;
ALTER TABLE pigtrax."PigTraxEventMaster" ADD CONSTRAINT "FeedEvent_fk" FOREIGN KEY ("id_FeedEvent")
REFERENCES pigtrax."FeedEvent" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtrax."PigInfo" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."PigInfo" CASCADE;
CREATE TABLE pigtrax."PigInfo"(
	id serial NOT NULL,
	"pigId" varchar(30),
	"sireId" varchar(30),
	"damId" varchar(30),
	"entryDate" timestamp,
	origin varchar(30),
	gline smallint,
	gcompany smallint,
	"birthDate" timestamp,
	tattoo varchar(30),
	"alternateTattoo" varchar(30),
	remarks varchar(255),
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_Company" integer NOT NULL,
	"id_Room" integer,
	"id_Barn" integer,
	"id_SexType" integer,
	"parity" integer default 0,
	"id_GfunctionType" smallint,
	"isActive" boolean,
	"id_Origin" smallint,
	"id_Premise" integer,
	"lastGestationLength" integer default 0,
	CONSTRAINT "PIGINFO_PK" PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE pigtrax."PigInfo" OWNER TO pitraxadmin;
-- ddl-end --

-- Index: pigtrax."PIGINFO_U_PI"
-- DROP INDEX pigtrax."PIGINFO_U_PI";
CREATE UNIQUE INDEX "PIGINFO_U_PI"  ON pigtrax."PigInfo"  USING btree  ("pigId" COLLATE pg_catalog."default", "id_Premise", "isActive")   WHERE "isActive" IS TRUE;

-- Index: pigtrax."PIGINFO_U_TA"
-- DROP INDEX pigtrax."PIGINFO_U_TA";
CREATE UNIQUE INDEX "PIGINFO_U_TA"  ON pigtrax."PigInfo"  USING btree  (tattoo COLLATE pg_catalog."default", "id_Premise", "isActive")  WHERE "isActive" IS TRUE AND "tattoo" IS NOT NULL and "tattoo" <> '';



-- object: "PigInfo_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."Genetics" DROP CONSTRAINT IF EXISTS "PigInfo_fk" CASCADE;
ALTER TABLE pigtrax."Genetics" ADD CONSTRAINT "PigInfo_fk" FOREIGN KEY ("id_PigInfo")
REFERENCES pigtrax."PigInfo" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "Premise_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigInfo" DROP CONSTRAINT IF EXISTS "Premise_fk" CASCADE;
ALTER TABLE pigtrax."PigInfo" ADD CONSTRAINT "Premise_fk" FOREIGN KEY ("id_Premise")
REFERENCES pigtrax."Premise" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --



-- object: "Genetics_uq" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."Genetics" DROP CONSTRAINT IF EXISTS "Genetics_uq" CASCADE;
ALTER TABLE pigtrax."Genetics" ADD CONSTRAINT "Genetics_uq" UNIQUE ("id_PigInfo");
-- ddl-end --

-- object: "PigInfo_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigletStatus" DROP CONSTRAINT IF EXISTS "PigInfo_fk" CASCADE;
ALTER TABLE pigtrax."PigletStatus" ADD CONSTRAINT "PigInfo_FosterFrom_fk" FOREIGN KEY ("fosterFrom")
REFERENCES pigtrax."PigInfo" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "PigInfo_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigletStatus" DROP CONSTRAINT IF EXISTS "PigInfo_fk" CASCADE;
ALTER TABLE pigtrax."PigletStatus" ADD CONSTRAINT "PigInfo_FosterTo_fk" FOREIGN KEY ("fosterTo")
REFERENCES pigtrax."PigInfo" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "PigInfo_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigTraxEventMaster" DROP CONSTRAINT IF EXISTS "PigInfo_fk" CASCADE;
ALTER TABLE pigtrax."PigTraxEventMaster" ADD CONSTRAINT "PigInfo_fk" FOREIGN KEY ("id_PigInfo")
REFERENCES pigtrax."PigInfo" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "PigInfo_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."BreedingEvent" DROP CONSTRAINT IF EXISTS "PigInfo_fk" CASCADE;
ALTER TABLE pigtrax."BreedingEvent" ADD CONSTRAINT "PigInfo_fk" FOREIGN KEY ("id_PigInfo")
REFERENCES pigtrax."PigInfo" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "Pen_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigInfo" DROP CONSTRAINT IF EXISTS "Room_fk" CASCADE;
ALTER TABLE pigtrax."PigInfo" ADD CONSTRAINT "Room_fk" FOREIGN KEY ("id_Room")
REFERENCES pigtrax."Room" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "PigInfo_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PregnancyEvent" DROP CONSTRAINT IF EXISTS "PigInfo_fk" CASCADE;
ALTER TABLE pigtrax."PregnancyEvent" ADD CONSTRAINT "PigInfo_fk" FOREIGN KEY ("id_PigInfo")
REFERENCES pigtrax."PigInfo" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "BreedingEvent_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PregnancyEvent" DROP CONSTRAINT IF EXISTS "BreedingEvent_fk" CASCADE;
ALTER TABLE pigtrax."PregnancyEvent" ADD CONSTRAINT "BreedingEvent_fk" FOREIGN KEY ("id_BreedingEvent")
REFERENCES pigtrax."BreedingEvent" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "Barn_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigInfo" DROP CONSTRAINT IF EXISTS "Barn_fk" CASCADE;
ALTER TABLE pigtrax."PigInfo" ADD CONSTRAINT "Barn_fk" FOREIGN KEY ("id_Barn")
REFERENCES pigtrax."Barn" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "PigInfo_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."FarrowEvent" DROP CONSTRAINT IF EXISTS "PigInfo_fk" CASCADE;
ALTER TABLE pigtrax."FarrowEvent" ADD CONSTRAINT "PigInfo_fk" FOREIGN KEY ("id_PigInfo")
REFERENCES pigtrax."PigInfo" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtrax."EmployeeJobFunction" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."EmployeeJobFunction" CASCADE;
CREATE TABLE pigtrax."EmployeeJobFunction"(
	id serial NOT NULL,
	"id_JobFunctionRole" integer,
	"functionFrom" timestamp NOT NULL,
	"functionTo" timestamp,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(30) NOT NULL,
	"id_Employee" integer,
	CONSTRAINT "EMPLOYEEJOBFUNCTION_PK" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtrax."EmployeeJobFunction" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "Employee_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."EmployeeJobFunction" DROP CONSTRAINT IF EXISTS "Employee_fk" CASCADE;
ALTER TABLE pigtrax."EmployeeJobFunction" ADD CONSTRAINT "Employee_fk" FOREIGN KEY ("id_Employee")
REFERENCES pigtrax."Employee" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "EmployeeJobFunction_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."EmployeeGroup" DROP CONSTRAINT IF EXISTS "EmployeeJobFunction_fk" CASCADE;
ALTER TABLE pigtrax."EmployeeGroup" ADD CONSTRAINT "EmployeeJobFunction_fk" FOREIGN KEY ("id_EmployeeJobFunction")
REFERENCES pigtrax."EmployeeJobFunction" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtrax."IndividualPigletStatus" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."IndividualPigletStatus" CASCADE;
CREATE TABLE pigtrax."IndividualPigletStatus"(
	id serial NOT NULL,
	"tattooId" varchar(30) ,
	"groupId" varchar(30) ,
	"weightAtBirth" numeric(20,2),
	"weightAtWeaning" numeric(20,2),
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_FarrowEvent" integer,
	"id_Premise" integer,
	"litterId" integer,
	"id_PigInfo" integer,
	"weight1" numeric(20,2),
	"weight2" numeric(20,2),
	"weight3" numeric(20,2),
	"weight4" numeric(20,2),
	"weight5" numeric(20,2),
	"weight6" numeric(20,2),
	"weight7" numeric(20,2),
	"weight8" numeric(20,2),
	"pigId" varchar(30) not null,
	"date1" timestamp,
	"date2" timestamp,
	"date3" timestamp,
	"date4" timestamp,
	"date5" timestamp,
	"date6" timestamp,
	"date7" timestamp,
	"date8" timestamp,
	CONSTRAINT "INDIPIGSTATUS_PK" PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE pigtrax."IndividualPigletStatus" OWNER TO pitraxadmin;
-- ddl-end --

-- Index: pigtrax."INDIVIDUALSTATUS_U_TA"
-- DROP INDEX pigtrax."INDIVIDUALSTATUS_U_TA";
CREATE UNIQUE INDEX "INDIVIDUALSTATUS_U_TA"  ON pigtrax."IndividualPigletStatus"  USING btree  ("tattooId" COLLATE pg_catalog."default")  WHERE "tattooId" IS NOT NULL and "tattooId" <> '';


-- object: "PigInfo_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."IndividualPigletStatus" DROP CONSTRAINT IF EXISTS "PigInfo_fk" CASCADE;
ALTER TABLE pigtrax."IndividualPigletStatus" ADD CONSTRAINT "PigInfo_fk" FOREIGN KEY ("id_PigInfo")
REFERENCES pigtrax."PigInfo" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "FarrowEvent_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."IndividualPigletStatus" DROP CONSTRAINT IF EXISTS "FarrowEvent_fk" CASCADE;
ALTER TABLE pigtrax."IndividualPigletStatus" ADD CONSTRAINT "FarrowEvent_fk" FOREIGN KEY ("id_FarrowEvent")
REFERENCES pigtrax."FarrowEvent" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: "Premise_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."IndividualPigletStatus" DROP CONSTRAINT IF EXISTS "Premise_fk" CASCADE;
ALTER TABLE pigtrax."IndividualPigletStatus" ADD CONSTRAINT "Premise_fk" FOREIGN KEY ("id_Premise")
REFERENCES pigtrax."Premise" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."VentilationType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."VentilationType" CASCADE;
CREATE TABLE pigtraxrefdata."VentilationType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "VENTILATIONTYPE_PK" PRIMARY KEY (id),
	CONSTRAINT "VENTILATIONTYPE_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."VentilationType" OWNER TO pitraxadmin;
-- ddl-end --

-- object: pigtraxrefdata."PhaseType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."PhaseType" CASCADE;
CREATE TABLE pigtraxrefdata."PhaseType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "PHASETYPE_PK" PRIMARY KEY (id),
	CONSTRAINT "PHASETYPE_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."PhaseType" OWNER TO pitraxadmin;
-- ddl-end --

-- object: pigtraxrefdata."RoleType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."RoleType" CASCADE;
CREATE TABLE pigtraxrefdata."RoleType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "ROLETYPE_FC_U" UNIQUE ("fieldCode"),
	CONSTRAINT "ROLETYPE_PK" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."RoleType" OWNER TO pitraxadmin;
-- ddl-end --

-- Appended SQL commands --
--INSERT INTO pigtraxrefdata."RoleType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (1,'PigTrax SuperAdmin User',current_time,'pigtraxadmin');
--INSERT INTO pigtraxrefdata."RoleType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (2,'PigTrax Data Config Mgr',current_time,'pigtraxadmin');
--INSERT INTO pigtraxrefdata."RoleType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (3,'PigFarm SuperAdmin User',current_time,'pigtraxadmin');
--INSERT INTO pigtraxrefdata."RoleType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (4,'PigFarm Data Config Mgr',current_time,'pigtraxadmin');
-- ddl-end --

-- object: pigtraxrefdata."TrailerType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."TrailerType" CASCADE;
CREATE TABLE pigtraxrefdata."TrailerType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "TRAILERTYPE_PK" PRIMARY KEY (id),
	CONSTRAINT "TRAILERTYPE_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."TrailerType" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "TrailerType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."TransportTrailer" DROP CONSTRAINT IF EXISTS "TrailerType_fk" CASCADE;
ALTER TABLE pigtrax."TransportTrailer" ADD CONSTRAINT "TrailerType_fk" FOREIGN KEY ("id_TrailerType")
REFERENCES pigtraxrefdata."TrailerType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."PhaseOfProductionType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."PhaseOfProductionType" CASCADE;
CREATE TABLE pigtraxrefdata."PhaseOfProductionType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "PHASEOFPROD_PK" PRIMARY KEY (id),
	CONSTRAINT "PHASEOFPROD_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."PhaseOfProductionType" OWNER TO pitraxadmin;
-- ddl-end --


-- object: pigtraxrefdata."BreedingServiceType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."BreedingServiceType" CASCADE;
CREATE TABLE pigtraxrefdata."BreedingServiceType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "BREEDINGSERVICETYPE_PK" PRIMARY KEY (id),
	CONSTRAINT "BREEDINGSERVICETYPE_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."BreedingServiceType" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "BreedingServiceType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."BreedingEvent" DROP CONSTRAINT IF EXISTS "BreedingServiceType_fk" CASCADE;
ALTER TABLE pigtrax."BreedingEvent" ADD CONSTRAINT "BreedingServiceType_fk" FOREIGN KEY ("id_BreedingServiceType")
REFERENCES pigtraxrefdata."BreedingServiceType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."PregnancyExamResultType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."PregnancyExamResultType" CASCADE;
CREATE TABLE pigtraxrefdata."PregnancyExamResultType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "PREGEXAMRESULT_PK" PRIMARY KEY (id),
	CONSTRAINT "PREGEXAMRESULT_FC_U" UNIQUE ("fieldCode",id)

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."PregnancyExamResultType" OWNER TO pitraxadmin;
-- ddl-end --

-- object: pigtraxrefdata."PregnancyEventType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."PregnancyEventType" CASCADE;
CREATE TABLE pigtraxrefdata."PregnancyEventType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "PREGNANCYEVENTTYPE_PK" PRIMARY KEY (id),
	CONSTRAINT "PREGNANCYEVENTYPE_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."PregnancyEventType" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "PregnancyEventType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PregnancyEvent" DROP CONSTRAINT IF EXISTS "PregnancyEventType_fk" CASCADE;
ALTER TABLE pigtrax."PregnancyEvent" ADD CONSTRAINT "PregnancyEventType_fk" FOREIGN KEY ("id_PregnancyEventType")
REFERENCES pigtraxrefdata."PregnancyEventType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "PregnancyExamResultType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PregnancyEvent" DROP CONSTRAINT IF EXISTS "PregnancyExamResultType_fk" CASCADE;
ALTER TABLE pigtrax."PregnancyEvent" ADD CONSTRAINT "PregnancyExamResultType_fk" FOREIGN KEY ("id_PregnancyExamResultType")
REFERENCES pigtraxrefdata."PregnancyExamResultType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."PigletStatusEventType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."PigletStatusEventType" CASCADE;
CREATE TABLE pigtraxrefdata."PigletStatusEventType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "PIGLETSTATUSETYPE_PK" PRIMARY KEY (id),
	CONSTRAINT "PIGLETSTATUSETYPE_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."PigletStatusEventType" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "PigletStatusEventType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigletStatus" DROP CONSTRAINT IF EXISTS "PigletStatusEventType_fk" CASCADE;
ALTER TABLE pigtrax."PigletStatus" ADD CONSTRAINT "PigletStatusEventType_fk" FOREIGN KEY ("id_PigletStatusEventType")
REFERENCES pigtraxrefdata."PigletStatusEventType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."FeedEventType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."FeedEventType" CASCADE;
CREATE TABLE pigtraxrefdata."FeedEventType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "FEEDEVENTTYPE_PK" PRIMARY KEY (id),
	CONSTRAINT "FEEDEVENTYPE_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."FeedEventType" OWNER TO pitraxadmin;
-- ddl-end --


-- object: pigtraxrefdata."GeneticsFunctionType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."GeneticsFunctionType" CASCADE;
CREATE TABLE pigtraxrefdata."GeneticsFunctionType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "GENETICSFUNCSTYPE_PK" PRIMARY KEY (id),
	CONSTRAINT "GENETICSFUNCTYPE_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."GeneticsFunctionType" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "GeneticsFunctionType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."Genetics" DROP CONSTRAINT IF EXISTS "GeneticsFunctionType_fk" CASCADE;
ALTER TABLE pigtrax."Genetics" ADD CONSTRAINT "GeneticsFunctionType_fk" FOREIGN KEY ("id_GeneticsFunctionType")
REFERENCES pigtraxrefdata."GeneticsFunctionType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."SexType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."SexType" CASCADE;
CREATE TABLE pigtraxrefdata."SexType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "SEXTYPE_PK" PRIMARY KEY (id),
	CONSTRAINT "SEXTYPE_FL_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."SexType" OWNER TO pitraxadmin;
-- ddl-end --

-- Appended SQL commands --
--INSERT INTO pigtraxrefdata."SexType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (1,'Male',CURRENT_TIMESTAMP,'pigtraxadmin');
--INSERT INTO pigtraxrefdata."SexType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (2,'Female',CURRENT_TIMESTAMP,'pigtraxadmin');
-- ddl-end --

-- object: "SexType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigInfo" DROP CONSTRAINT IF EXISTS "SexType_fk" CASCADE;
ALTER TABLE pigtrax."PigInfo" ADD CONSTRAINT "SexType_fk" FOREIGN KEY ("id_SexType")
REFERENCES pigtraxrefdata."SexType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."RemovalType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."RemovalType" CASCADE;
CREATE TABLE pigtraxrefdata."RemovalType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "REMOVALTYPE_PK" PRIMARY KEY (id),
	CONSTRAINT "REMOVALTYPE_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."RemovalType" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "RemovalType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."RemovalEvent" DROP CONSTRAINT IF EXISTS "RemovalType_fk" CASCADE;
ALTER TABLE pigtrax."RemovalEvent" ADD CONSTRAINT "RemovalType_fk" FOREIGN KEY ("id_RemovalType")
REFERENCES pigtraxrefdata."RemovalType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "PhaseType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."Barn" DROP CONSTRAINT IF EXISTS "PhaseType_fk" CASCADE;
ALTER TABLE pigtrax."Barn" ADD CONSTRAINT "PhaseType_fk" FOREIGN KEY ("id_PhaseType")
REFERENCES pigtraxrefdata."PhaseType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."PhaseTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."PhaseTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."PhaseTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20),
	"id_PhaseType" integer,
	CONSTRAINT "PHASETYPETRANLATION_PK" PRIMARY KEY (id),
	CONSTRAINT "PHASETYPETRANSLATION_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."PhaseTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "PhaseType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."PhaseTypeTranslation" DROP CONSTRAINT IF EXISTS "PhaseType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."PhaseTypeTranslation" ADD CONSTRAINT "PhaseType_fk" FOREIGN KEY ("id_PhaseType")
REFERENCES pigtraxrefdata."PhaseType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."VentilationTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."VentilationTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."VentilationTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_VentilationType" integer,
	CONSTRAINT "VENTILATIONTYPETRANSLATION_PK" PRIMARY KEY (id),
	CONSTRAINT "VENTILATIONTYPETRANS_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."VentilationTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "VentilationType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."VentilationTypeTranslation" DROP CONSTRAINT IF EXISTS "VentilationType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."VentilationTypeTranslation" ADD CONSTRAINT "VentilationType_fk" FOREIGN KEY ("id_VentilationType")
REFERENCES pigtraxrefdata."VentilationType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "VentilationType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."Barn" DROP CONSTRAINT IF EXISTS "VentilationType_fk" CASCADE;
ALTER TABLE pigtrax."Barn" ADD CONSTRAINT "VentilationType_fk" FOREIGN KEY ("id_VentilationType")
REFERENCES pigtraxrefdata."VentilationType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."SiloType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."SiloType" CASCADE;
CREATE TABLE pigtraxrefdata."SiloType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "SILOTYPE_PK" PRIMARY KEY (id),
	CONSTRAINT "SILOTYPE_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."SiloType" OWNER TO pitraxadmin;
-- ddl-end --

-- object: pigtraxrefdata."SiloTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."SiloTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."SiloTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_SiloType" integer,
	CONSTRAINT "SILOTYPE_TANSLATION_PK" PRIMARY KEY (id),
	CONSTRAINT "SILOTYPETANS_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."SiloTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "SiloType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."SiloTypeTranslation" DROP CONSTRAINT IF EXISTS "SiloType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."SiloTypeTranslation" ADD CONSTRAINT "SiloType_fk" FOREIGN KEY ("id_SiloType")
REFERENCES pigtraxrefdata."SiloType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "SiloType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."Silo" DROP CONSTRAINT IF EXISTS "SiloType_fk" CASCADE;
ALTER TABLE pigtrax."Silo" ADD CONSTRAINT "SiloType_fk" FOREIGN KEY ("id_SiloType")
REFERENCES pigtraxrefdata."SiloType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."RoleTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."RoleTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."RoleTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(50) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_RoleType" integer,
	CONSTRAINT "ROLETYPE_PK_1" PRIMARY KEY (id),
	CONSTRAINT "ROLETYPE_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."RoleTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- Appended SQL commands --
--INSERT INTO pigtraxrefdata."RoleTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RoleType") VALUES ('PT SuperAdmin','en',current_timestamp,'pigtraxadmin',1);
--INSERT INTO pigtraxrefdata."RoleTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RoleType") VALUES ('PT Data Config Mgr','en',current_timestamp,'pigtraxadmin',2);
--INSERT INTO pigtraxrefdata."RoleTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RoleType") VALUES ('Farm SuperAdmin','en',current_timestamp,'pigtraxadmin',1);
--INSERT INTO pigtraxrefdata."RoleTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RoleType") VALUES ('Farm Data Config Mgr','en',current_timestamp,'pigtraxadmin',2);

--INSERT INTO pigtraxrefdata."RoleTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RoleType") VALUES ('PT SuperAdmin_ES','es',current_timestamp,'pigtraxadmin',1);
--INSERT INTO pigtraxrefdata."RoleTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RoleType") VALUES ('PT Data Config Mgr_ES','es',current_timestamp,'pigtraxadmin',2);
--INSERT INTO pigtraxrefdata."RoleTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RoleType") VALUES ('Farm SuperAdmin_ES','es',current_timestamp,'pigtraxadmin',1);
--INSERT INTO pigtraxrefdata."RoleTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RoleType") VALUES ('Farm Data Config Mgr_ES','es',current_timestamp,'pigtraxadmin',2);

--INSERT INTO pigtraxrefdata."RoleTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RoleType") VALUES ('PT SuperAdmin_PR','pr',current_timestamp,'pigtraxadmin',1);
--INSERT INTO pigtraxrefdata."RoleTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RoleType") VALUES ('PT Data Config Mgr_PR','pr',current_timestamp,'pigtraxadmin',2);
--INSERT INTO pigtraxrefdata."RoleTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RoleType") VALUES ('Farm SuperAdmin_PR','pr',current_timestamp,'pigtraxadmin',1);
--INSERT INTO pigtraxrefdata."RoleTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RoleType") VALUES ('Farm Data Config Mgr_PR','pr',current_timestamp,'pigtraxadmin',2);
-- ddl-end --

-- object: "RoleType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."RoleTypeTranslation" DROP CONSTRAINT IF EXISTS "RoleType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."RoleTypeTranslation" ADD CONSTRAINT "RoleType_fk" FOREIGN KEY ("id_RoleType")
REFERENCES pigtraxrefdata."RoleType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."TrailerTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."TrailerTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."TrailerTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(100) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_TrailerType" integer,
	CONSTRAINT "TRAILERTYPETRANS_PK" PRIMARY KEY (id),
	CONSTRAINT "TRAILERTYPETRANS_FL_FC_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."TrailerTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "TrailerType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."TrailerTypeTranslation" DROP CONSTRAINT IF EXISTS "TrailerType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."TrailerTypeTranslation" ADD CONSTRAINT "TrailerType_fk" FOREIGN KEY ("id_TrailerType")
REFERENCES pigtraxrefdata."TrailerType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."PhaseOfProductionTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."PhaseOfProductionTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."PhaseOfProductionTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(100) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_PhaseOfProductionType" integer,
	CONSTRAINT "PHASEOFPROD_PK_1" PRIMARY KEY (id),
	CONSTRAINT "PHASEOFPROD_FL_FC_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."PhaseOfProductionTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "PhaseOfProductionType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."PhaseOfProductionTypeTranslation" DROP CONSTRAINT IF EXISTS "PhaseOfProductionType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."PhaseOfProductionTypeTranslation" ADD CONSTRAINT "PhaseOfProductionType_fk" FOREIGN KEY ("id_PhaseOfProductionType")
REFERENCES pigtraxrefdata."PhaseOfProductionType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."BreedingServiceTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."BreedingServiceTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."BreedingServiceTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_BreedingServiceType" integer,
	CONSTRAINT "BREEDINGSERVTRANS_PK" PRIMARY KEY (id),
	CONSTRAINT "BREEDINGSERVTRANS_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."BreedingServiceTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "BreedingServiceType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."BreedingServiceTypeTranslation" DROP CONSTRAINT IF EXISTS "BreedingServiceType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."BreedingServiceTypeTranslation" ADD CONSTRAINT "BreedingServiceType_fk" FOREIGN KEY ("id_BreedingServiceType")
REFERENCES pigtraxrefdata."BreedingServiceType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."PregnancyEventTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."PregnancyEventTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."PregnancyEventTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone,
	"userUpdated" varchar(20) NOT NULL,
	"id_PregnancyEventType" integer
);
-- ddl-end --
ALTER TABLE pigtraxrefdata."PregnancyEventTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "PregnancyEventType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."PregnancyEventTypeTranslation" DROP CONSTRAINT IF EXISTS "PregnancyEventType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."PregnancyEventTypeTranslation" ADD CONSTRAINT "PregnancyEventType_fk" FOREIGN KEY ("id_PregnancyEventType")
REFERENCES pigtraxrefdata."PregnancyEventType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."PregnancyExamResultTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."PregnancyExamResultTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."PregnancyExamResultTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_PregnancyExamResultType" integer,
	CONSTRAINT "PREGEXAMRESULTTYPETANS_PK" PRIMARY KEY (id),
	CONSTRAINT "PREGEXAMRESULTTYPETANS_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."PregnancyExamResultTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "PregnancyExamResultType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."PregnancyExamResultTypeTranslation" DROP CONSTRAINT IF EXISTS "PregnancyExamResultType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."PregnancyExamResultTypeTranslation" ADD CONSTRAINT "PregnancyExamResultType_fk" FOREIGN KEY ("id_PregnancyExamResultType")
REFERENCES pigtraxrefdata."PregnancyExamResultType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."PigletStatusEventTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."PigletStatusEventTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."PigletStatusEventTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_PigletStatusEventType" integer,
	CONSTRAINT "PIGLETSTATUSEVENTTYPE_PK" PRIMARY KEY (id),
	CONSTRAINT "PIGLETSTATUSTYPE_FL_FV_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."PigletStatusEventTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "PigletStatusEventType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."PigletStatusEventTypeTranslation" DROP CONSTRAINT IF EXISTS "PigletStatusEventType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."PigletStatusEventTypeTranslation" ADD CONSTRAINT "PigletStatusEventType_fk" FOREIGN KEY ("id_PigletStatusEventType")
REFERENCES pigtraxrefdata."PigletStatusEventType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."SexTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."SexTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."SexTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_SexType" integer,
	CONSTRAINT "SEXTYPETRANSLATION_PK" PRIMARY KEY (id),
	CONSTRAINT "SEXTYPETANSLATION_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."SexTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- Appended SQL commands --
--INSERT INTO pigtraxrefdata."SexTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_SexType") VALUES ('Male','en',CURRENT_TIMESTAMP,'pigtraxadmin',1);
--INSERT INTO pigtraxrefdata."SexTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_SexType") VALUES ('Female','en',CURRENT_TIMESTAMP,'pigtraxadmin',2);

--INSERT INTO pigtraxrefdata."SexTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_SexType") VALUES ('Male_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',1);
--INSERT INTO pigtraxrefdata."SexTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_SexType") VALUES ('Female_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',2);

--INSERT INTO pigtraxrefdata."SexTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_SexType") VALUES ('Male_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',1);
--INSERT INTO pigtraxrefdata."SexTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_SexType") VALUES ('Female_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',2);

-- ddl-end --

-- object: "SexType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."SexTypeTranslation" DROP CONSTRAINT IF EXISTS "SexType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."SexTypeTranslation" ADD CONSTRAINT "SexType_fk" FOREIGN KEY ("id_SexType")
REFERENCES pigtraxrefdata."SexType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."RemovalEventTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."RemovalEventTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."RemovalEventTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_RemovalType" integer,
	CONSTRAINT "REMOVALEVENTTRANS_PK" PRIMARY KEY (id),
	CONSTRAINT "REMOVALEVENTTYPE_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."RemovalEventTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "RemovalType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."RemovalEventTypeTranslation" DROP CONSTRAINT IF EXISTS "RemovalType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."RemovalEventTypeTranslation" ADD CONSTRAINT "RemovalType_fk" FOREIGN KEY ("id_RemovalType")
REFERENCES pigtraxrefdata."RemovalType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."SaleEventTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."SaleEventTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."SaleEventTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_SaleEventType" integer,
	CONSTRAINT "SALEEVENTTYPETRANS_PK" PRIMARY KEY (id),
	CONSTRAINT "SALEEVENTTYPETRANS_FL_FV_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."SaleEventTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "SaleEventType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."SaleEventTypeTranslation" DROP CONSTRAINT IF EXISTS "SaleEventType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."SaleEventTypeTranslation" ADD CONSTRAINT "SaleEventType_fk" FOREIGN KEY ("id_SaleEventType")
REFERENCES pigtraxrefdata."SaleEventType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "SaleEventTypeTranslation_uq" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."SaleEventTypeTranslation" DROP CONSTRAINT IF EXISTS "SaleEventTypeTranslation_uq" CASCADE;
ALTER TABLE pigtraxrefdata."SaleEventTypeTranslation" ADD CONSTRAINT "SaleEventTypeTranslation_uq" UNIQUE ("id_SaleEventType");
-- ddl-end --

-- object: pigtraxrefdata."FeedEventTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."FeedEventTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."FeedEventTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_FeedEventType" integer,
	CONSTRAINT "FEEDEVENTTYPETRANS_PK" PRIMARY KEY (id),
	CONSTRAINT "FEEDEVENTTYPETRANS_FL_FV_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."FeedEventTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "FeedEventType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."FeedEventTypeTranslation" DROP CONSTRAINT IF EXISTS "FeedEventType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."FeedEventTypeTranslation" ADD CONSTRAINT "FeedEventType_fk" FOREIGN KEY ("id_FeedEventType")
REFERENCES pigtraxrefdata."FeedEventType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."GeneticFunctionTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."GeneticFunctionTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."GeneticFunctionTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone,
	"userUpdated" varchar(20) NOT NULL,
	"id_GeneticsFunctionType" integer,
	CONSTRAINT "GENETICFUNCTYPETRANS_PK" PRIMARY KEY (id),
	CONSTRAINT "GENETICFUNCTYPETRANS_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."GeneticFunctionTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "GeneticsFunctionType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."GeneticFunctionTypeTranslation" DROP CONSTRAINT IF EXISTS "GeneticsFunctionType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."GeneticFunctionTypeTranslation" ADD CONSTRAINT "GeneticsFunctionType_fk" FOREIGN KEY ("id_GeneticsFunctionType")
REFERENCES pigtraxrefdata."GeneticsFunctionType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtrax."EventLog" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."EventLog" CASCADE;
CREATE TABLE pigtrax."EventLog"(
	id serial NOT NULL,
	"eventDate" date NOT NULL,
	remarks varchar(255) NOT NULL,
	notes varchar(255) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_EventAreaType" integer,
	"id_GroupEvent" integer,
	"id_Barn" integer,
	CONSTRAINT "EVENTLOG_PK" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtrax."EventLog" OWNER TO pitraxadmin;
-- ddl-end --

-- object: pigtraxrefdata."EventAreaType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."EventAreaType" CASCADE;
CREATE TABLE pigtraxrefdata."EventAreaType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdate" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "EVENTAREATYPE_PK" PRIMARY KEY (id),
	CONSTRAINT "EVENTAREATYPE_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."EventAreaType" OWNER TO pitraxadmin;
-- ddl-end --

-- object: pigtraxrefdata."EventAreaTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."EventAreaTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."EventAreaTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_EventAreaType" integer,
	CONSTRAINT "EVENTAREATYPETANS_PK" PRIMARY KEY (id),
	CONSTRAINT "EVENTAREATYPETRANS_FC_FV_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."EventAreaTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "EventAreaType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."EventAreaTypeTranslation" DROP CONSTRAINT IF EXISTS "EventAreaType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."EventAreaTypeTranslation" ADD CONSTRAINT "EventAreaType_fk" FOREIGN KEY ("id_EventAreaType")
REFERENCES pigtraxrefdata."EventAreaType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "EventAreaType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."EventLog" DROP CONSTRAINT IF EXISTS "EventAreaType_fk" CASCADE;
ALTER TABLE pigtrax."EventLog" ADD CONSTRAINT "EventAreaType_fk" FOREIGN KEY ("id_EventAreaType")
REFERENCES pigtraxrefdata."EventAreaType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "GroupEvent_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."EventLog" DROP CONSTRAINT IF EXISTS "GroupEvent_fk" CASCADE;
ALTER TABLE pigtrax."EventLog" ADD CONSTRAINT "GroupEvent_fk" FOREIGN KEY ("id_GroupEvent")
REFERENCES pigtrax."GroupEvent" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "Barn_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."EventLog" DROP CONSTRAINT IF EXISTS "Barn_fk" CASCADE;
ALTER TABLE pigtrax."EventLog" ADD CONSTRAINT "Barn_fk" FOREIGN KEY ("id_Barn")
REFERENCES pigtrax."Barn" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "COMPANY_COID" | type: INDEX --
-- DROP INDEX IF EXISTS pigtrax."COMPANY_COID" CASCADE;
CREATE INDEX "COMPANY_COID" ON pigtrax."Company"
	USING btree
	(
	  "companyId" ASC NULLS LAST
	);
-- ddl-end --

-- object: "RoleType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."Employee" DROP CONSTRAINT IF EXISTS "RoleType_fk" CASCADE;
ALTER TABLE pigtrax."Employee" ADD CONSTRAINT "RoleType_fk" FOREIGN KEY ("id_RoleType")
REFERENCES pigtraxrefdata."RoleType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "EMPLOYEE_CI_IN" | type: INDEX --
-- DROP INDEX IF EXISTS pigtrax."EMPLOYEE_CI_IN" CASCADE;
CREATE INDEX "EMPLOYEE_CI_IN" ON pigtrax."Employee"
	USING btree
	(
	  "employeeId" ASC NULLS LAST
	);
-- ddl-end --

-- object: "EMPLOYEE_CI_C_IN" | type: INDEX --
-- DROP INDEX IF EXISTS pigtrax."EMPLOYEE_CI_C_IN" CASCADE;
CREATE INDEX "EMPLOYEE_CI_C_IN" ON pigtrax."Employee"
	USING btree
	(
	  "employeeId" ASC NULLS LAST,
	  "id_Company" ASC NULLS LAST
	);
-- ddl-end --

-- object: pigtraxrefdata."Country" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."Country" CASCADE;
CREATE TABLE pigtraxrefdata."Country"(
	id serial NOT NULL,
	name varchar(100) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "COUNTRY_PK" PRIMARY KEY (id),
	CONSTRAINT "COUNTRY_NAME_U" UNIQUE (name)

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."Country" OWNER TO pitraxadmin;
-- ddl-end --

-- object: pigtraxrefdata."City" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."City" CASCADE;
CREATE TABLE pigtraxrefdata."City"(
	id serial NOT NULL,
	name varchar(100) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_Country" integer,
	CONSTRAINT "CITY_PK" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."City" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "Country_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."City" DROP CONSTRAINT IF EXISTS "Country_fk" CASCADE;
ALTER TABLE pigtraxrefdata."City" ADD CONSTRAINT "Country_fk" FOREIGN KEY ("id_Country")
REFERENCES pigtraxrefdata."Country" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "CITY_NAME_COUNTRY_U" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."City" DROP CONSTRAINT IF EXISTS "CITY_NAME_COUNTRY_U" CASCADE;
ALTER TABLE pigtraxrefdata."City" ADD CONSTRAINT "CITY_NAME_COUNTRY_U" UNIQUE (name,"id_Country");
-- ddl-end --

-- object: "Company_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigInfo" DROP CONSTRAINT IF EXISTS "Company_fk" CASCADE;
ALTER TABLE pigtrax."PigInfo" ADD CONSTRAINT "Company_fk" FOREIGN KEY ("id_Company")
REFERENCES pigtrax."Company" (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --


-- object: pigtrax."GroupEventDetails" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."GroupEventDetails" CASCADE;
CREATE TABLE pigtrax."GroupEventDetails"(
	id serial NOT NULL,
	"id_Barn" integer,
	"dateOfEntry" timestamp NOT NULL,
	"numberOfPigs" smallint NOT NULL,
	"weightInKgs" numeric(10,2) NOT NULL,
	"indeventoryAdjustment" smallint,
	remarks varchar(255),
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_Room" integer,
	"id_EmployeeGroup" integer,	
	"id_GroupEvent" integer,
	"id_TransportDestination" integer,
	"id_SowSource" integer,
	"id_Premise" integer,
	"id_PigletStatusEvent" integer,
	"id_FromGroup" integer,
    "id_RemovalEventExceptSalesDetails" integer,
    "id_SalesEventDetails" integer,
    "groupEventActionType" integer,
	CONSTRAINT "GROUPDEVENTDETAIL_PK" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtrax."GroupEventDetails" OWNER TO pitraxadmin;
-- ddl-end --


-- object: "Room_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."GroupEventDetails" DROP CONSTRAINT IF EXISTS "Room_fk" CASCADE;
ALTER TABLE pigtrax."GroupEventDetails" ADD CONSTRAINT "Room_fk" FOREIGN KEY ("id_Room")
REFERENCES pigtrax."Room" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "Room_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."GroupEventDetails" DROP CONSTRAINT IF EXISTS "Barn_fk" CASCADE;
ALTER TABLE pigtrax."GroupEventDetails" ADD CONSTRAINT "Barn_fk" FOREIGN KEY ("id_Barn")
REFERENCES pigtrax."Barn" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "EmployeeGroup_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."GroupEventDetails" DROP CONSTRAINT IF EXISTS "EmployeeGroup_fk" CASCADE;
ALTER TABLE pigtrax."GroupEventDetails" ADD CONSTRAINT "EmployeeGroup_fk" FOREIGN KEY ("id_EmployeeGroup")
REFERENCES pigtrax."EmployeeGroup" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "GroupEvent_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."GroupEventDetails" DROP CONSTRAINT IF EXISTS "GroupEvent_fk" CASCADE;
ALTER TABLE pigtrax."GroupEventDetails" ADD CONSTRAINT "GroupEvent_fk" FOREIGN KEY ("id_GroupEvent")
REFERENCES pigtrax."GroupEvent" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "PhaseOfProductionType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."GroupEventDetails" DROP CONSTRAINT IF EXISTS "PhaseOfProductionType_fk" CASCADE;
ALTER TABLE pigtrax."GroupEvent" ADD CONSTRAINT "PhaseOfProductionType_fk" FOREIGN KEY ("id_PhaseOfProductionType")
REFERENCES pigtraxrefdata."PhaseOfProductionType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- ALTER TABLE pigtrax."GroupEventDetails" DROP CONSTRAINT IF EXISTS "TransportDestination_fk" CASCADE;
ALTER TABLE pigtrax."GroupEventDetails" ADD CONSTRAINT "TransportDestination_fk" FOREIGN KEY ("id_TransportDestination")
REFERENCES pigtrax."TransportDestination" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: "SowSource_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."GroupEventDetails" DROP CONSTRAINT IF EXISTS "SowSource_fk" CASCADE;
ALTER TABLE pigtrax."GroupEventDetails" ADD CONSTRAINT "SowSource_fk" FOREIGN KEY ("id_SowSource")
REFERENCES pigtrax."Premise" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;


-- object: "Premise_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."GroupEventDetails" DROP CONSTRAINT IF EXISTS "Premise_fk" CASCADE;
ALTER TABLE pigtrax."GroupEventDetails" ADD CONSTRAINT "Premise_fk" FOREIGN KEY ("id_Premise")
REFERENCES pigtrax."Premise" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;

-- ddl-end --


-- object: pigtrax."FeedEventDetails" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."FeedEventDetails" CASCADE;
CREATE TABLE pigtrax."FeedEventDetails"(
	id serial NOT NULL,
	"feedEventDate" timestamp NOT NULL,
	"weightInKgs" numeric(20,2),
	remarks varchar(255),
	"id_FeedEvent" integer NOT NULL,
	"id_Silo" integer NOT NULL,
	"id_GroupEvent" integer,
	"id_FeedEventType" integer,
	"userUpdated" varchar(20) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"feedMill" varchar(50),
	"feedCost" numeric(20,2),
	CONSTRAINT "FEEDEVENTDETAILS_PK" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtrax."FeedEventDetails" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "FeedEvent_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."FeedEventDetails" DROP CONSTRAINT IF EXISTS "FeedEvent_fk" CASCADE;
ALTER TABLE pigtrax."FeedEventDetails" ADD CONSTRAINT "FeedEvent_fk" FOREIGN KEY ("id_FeedEvent")
REFERENCES pigtrax."FeedEvent" (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: "Silo_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."FeedEventDetails" DROP CONSTRAINT IF EXISTS "Silo_fk" CASCADE;
ALTER TABLE pigtrax."FeedEventDetails" ADD CONSTRAINT "Silo_fk" FOREIGN KEY ("id_Silo")
REFERENCES pigtrax."Silo" (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: "GroupEvent_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."FeedEventDetails" DROP CONSTRAINT IF EXISTS "GroupEvent_fk" CASCADE;
ALTER TABLE pigtrax."FeedEventDetails" ADD CONSTRAINT "GroupEvent_fk" FOREIGN KEY ("id_GroupEvent")
REFERENCES pigtrax."GroupEvent" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "FeedEventType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."FeedEventDetails" DROP CONSTRAINT IF EXISTS "FeedEventType_fk" CASCADE;
ALTER TABLE pigtrax."FeedEventDetails" ADD CONSTRAINT "FeedEventType_fk" FOREIGN KEY ("id_FeedEventType")
REFERENCES pigtraxrefdata."FeedEventType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: pigtrax."RemovalEventExceptSalesDetails" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."RemovalEventExceptSalesDetails" CASCADE;
CREATE TABLE pigtrax."RemovalEventExceptSalesDetails"(
	id serial NOT NULL,
	"numberOfPigs" smallint NOT NULL,
	"removalDateTime" timestamp NOT NULL,
	"id_PigInfo" integer,
	"id_GroupEvent" integer,
	"weightInKgs" numeric(20,2) NOT NULL,
	"id_RemovalEvent" integer NOT NULL,
	"id_Premise" integer,
	"id_DestPremise" integer,	
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_TransportJourney" integer,
	"id_MortalityReason" integer,
	remarks varchar(255),
	"revenueUsd" numeric(20,2),
	"id_Room" integer,
	CONSTRAINT "REMOVALEVENTXSALESDETAILS_PK" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtrax."RemovalEventExceptSalesDetails" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "RemovalEvent_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."RemovalEventExceptSalesDetails" DROP CONSTRAINT IF EXISTS "RemovalEvent_fk" CASCADE;
ALTER TABLE pigtrax."RemovalEventExceptSalesDetails" ADD CONSTRAINT "RemovalEvent_fk" FOREIGN KEY ("id_RemovalEvent")
REFERENCES pigtraxrefdata."RemovalType" (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: "PigInfo_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."RemovalEventExceptSalesDetails" DROP CONSTRAINT IF EXISTS "PigInfo_fk" CASCADE;
ALTER TABLE pigtrax."RemovalEventExceptSalesDetails" ADD CONSTRAINT "PigInfo_fk" FOREIGN KEY ("id_PigInfo")
REFERENCES pigtrax."PigInfo" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "GroupEvent_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."RemovalEventExceptSalesDetails" DROP CONSTRAINT IF EXISTS "GroupEvent_fk" CASCADE;
ALTER TABLE pigtrax."RemovalEventExceptSalesDetails" ADD CONSTRAINT "GroupEvent_fk" FOREIGN KEY ("id_GroupEvent")
REFERENCES pigtrax."GroupEvent" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "Premise_fk_source" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."RemovalEventExceptSalesDetails" DROP CONSTRAINT IF EXISTS "Premise_fk_source" CASCADE;
ALTER TABLE pigtrax."RemovalEventExceptSalesDetails" ADD CONSTRAINT "Premise_fk_source" FOREIGN KEY ("id_Premise")
REFERENCES pigtrax."Premise" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "Premise_fk_source" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."RemovalEventExceptSalesDetails" DROP CONSTRAINT IF EXISTS "Premise_fk_source" CASCADE;
ALTER TABLE pigtrax."RemovalEventExceptSalesDetails" ADD CONSTRAINT "DestPremise_fk_source" FOREIGN KEY ("id_DestPremise")
REFERENCES pigtrax."Premise" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "RemovalEventExceptSalesDetails_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."GroupEventDetails" DROP CONSTRAINT IF EXISTS "RemovalEventExceptSalesDetails_fk" CASCADE;
ALTER TABLE pigtrax."GroupEventDetails" ADD CONSTRAINT "RemovalEventExceptSalesDetails_fk" FOREIGN KEY ("id_RemovalEventExceptSalesDetails")
REFERENCES pigtrax."RemovalEventExceptSalesDetails" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --



-- object: pigtrax."SalesEventDetails" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."SalesEventDetails" CASCADE;
CREATE TABLE pigtrax."SalesEventDetails"(
	id serial NOT NULL,
	"invoiceId" varchar(20),
	"ticketNumber" varchar(20),
	"numberOfPigs" smallint NOT NULL,
	"revenueUsd" numeric(20,2),
	"weightInKgs" numeric(20,2) NOT NULL,
	"salesDateTime" timestamp NOT NULL,
	"id_PigInfo" integer,
	"id_GroupEvent" integer,
	"soldTo" varchar(75),
	"id_RemovalEvent" integer,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_TransportJourney" integer,
	remarks varchar(255),
	"salesTypes" varchar(200),
	"salesReasons" varchar(200),
	CONSTRAINT "SALESDETAILS_PK" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtrax."SalesEventDetails" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "RemovalEvent_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."SalesEventDetails" DROP CONSTRAINT IF EXISTS "RemovalEvent_fk" CASCADE;
ALTER TABLE pigtrax."SalesEventDetails" ADD CONSTRAINT "RemovalEvent_fk" FOREIGN KEY ("id_RemovalEvent")
REFERENCES pigtraxrefdata."RemovalType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "PigInfo_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."SalesEventDetails" DROP CONSTRAINT IF EXISTS "PigInfo_fk" CASCADE;
ALTER TABLE pigtrax."SalesEventDetails" ADD CONSTRAINT "PigInfo_fk" FOREIGN KEY ("id_PigInfo")
REFERENCES pigtrax."PigInfo" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "GroupEvent_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."SalesEventDetails" DROP CONSTRAINT IF EXISTS "GroupEvent_fk" CASCADE;
ALTER TABLE pigtrax."SalesEventDetails" ADD CONSTRAINT "GroupEvent_fk" FOREIGN KEY ("id_GroupEvent")
REFERENCES pigtrax."GroupEvent" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: "SalesEventDetails_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."SalesEventDetails" DROP CONSTRAINT IF EXISTS "SalesEventDetails_fk" CASCADE;
ALTER TABLE pigtrax."GroupEventDetails" ADD CONSTRAINT "SalesEventDetails_fk" FOREIGN KEY ("id_SalesEventDetails")
REFERENCES pigtrax."SalesEventDetails" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: pigtraxrefdata."GfunctionType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."GfunctionType" CASCADE;
CREATE TABLE pigtraxrefdata."GfunctionType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "GFUNCTIONTYPE_PK" PRIMARY KEY (id),
	CONSTRAINT "GFUNCTIONTYPE_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."GfunctionType" OWNER TO pitraxadmin;


-- object: "GfunctionType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigInfo" DROP CONSTRAINT IF EXISTS "GfunctionType_fk" CASCADE;
ALTER TABLE pigtrax."PigInfo" ADD CONSTRAINT "GfunctionType_fk" FOREIGN KEY ("id_GfunctionType")
REFERENCES pigtraxrefdata."GfunctionType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."GfunctionTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."GfunctionTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."GfunctionTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20),
	"id_GfunctionType" integer,
	CONSTRAINT "GFUNCTIONTYPETRANLATION_PK" PRIMARY KEY (id),
	CONSTRAINT "GFUNCTIONTYPETRANSLATION_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."GfunctionTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "GfunctionType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."GfunctionTypeTranslation" DROP CONSTRAINT IF EXISTS "GfunctionType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."GfunctionTypeTranslation" ADD CONSTRAINT "GfunctionType_fk" FOREIGN KEY ("id_GfunctionType")
REFERENCES pigtraxrefdata."GfunctionType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: pigtraxrefdata."MortalityReasonType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."MortalityReasonType" CASCADE;
CREATE TABLE pigtraxrefdata."MortalityReasonType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "MORTALITYREASONTYPE_PK" PRIMARY KEY (id),
	CONSTRAINT "MORTALITYREASONTYPE_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."MortalityReasonType" OWNER TO pitraxadmin;


-- object: "MortalityReasonType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigletStatus" DROP CONSTRAINT IF EXISTS "MortalityReasonType_fk" CASCADE;
ALTER TABLE pigtrax."PigletStatus" ADD CONSTRAINT "MortalityReasonType_fk" FOREIGN KEY ("id_MortalityReasonType")
REFERENCES pigtraxrefdata."MortalityReasonType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: "MortalityReason_source" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."RemovalEventExceptSalesDetails" DROP CONSTRAINT IF EXISTS "MortalityReason_source" CASCADE;
ALTER TABLE pigtrax."RemovalEventExceptSalesDetails" ADD CONSTRAINT "MortalityReason_source" FOREIGN KEY ("id_MortalityReason")
REFERENCES pigtraxrefdata."MortalityReasonType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."MortalityReasonTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."MortalityReasonTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."MortalityReasonTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(100) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20),
	"id_MortalityReasonType" integer,
	CONSTRAINT "MORTALITYREASONTYPETRANLATION_PK" PRIMARY KEY (id),
	CONSTRAINT "MORTALITYREASONTYPETRANSLATION_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."MortalityReasonTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "MortalityReasonType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."MortalityReasonTypeTranslation" DROP CONSTRAINT IF EXISTS "MortalityReasonType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."MortalityReasonTypeTranslation" ADD CONSTRAINT "MortalityReasonType_fk" FOREIGN KEY ("id_MortalityReasonType")
REFERENCES pigtraxrefdata."MortalityReasonType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "TransportJourney_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."RemovalEventExceptSalesDetails" DROP CONSTRAINT IF EXISTS "TransportJourney_fk" CASCADE;
ALTER TABLE pigtrax."RemovalEventExceptSalesDetails" ADD CONSTRAINT "TransportJourney_fk" FOREIGN KEY ("id_TransportJourney")
REFERENCES pigtrax."TransportJourney" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- ALTER TABLE pigtrax."SalesEventDetails" DROP CONSTRAINT IF EXISTS "TransportJourney_fk" CASCADE;
ALTER TABLE pigtrax."SalesEventDetails" ADD CONSTRAINT "TransportJourney_fk" FOREIGN KEY ("id_TransportJourney")
REFERENCES pigtrax."TransportJourney" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: pigtrax."MatingDetails" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."MatingDetails" CASCADE;
CREATE TABLE pigtrax."MatingDetails"(
	id serial NOT NULL,
	"matingDate" timestamp NOT NULL,
	"semenId" varchar(30) ,
	"matingQuality" smallint,
	"id_BreedingEvent" integer NOT NULL,
	"id_EmployeeGroup" integer,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"semenDate" timestamp,
	CONSTRAINT "MATINGS_DETAILS_PK" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtrax."MatingDetails" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "BreedingEvent_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."MatingDetails" DROP CONSTRAINT IF EXISTS "BreedingEvent_fk" CASCADE;
ALTER TABLE pigtrax."MatingDetails" ADD CONSTRAINT "BreedingEvent_fk" FOREIGN KEY ("id_BreedingEvent")
REFERENCES pigtrax."BreedingEvent" (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;
-- ddl-end --

-- object: "EmployeeGroup_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."MatingDetails" DROP CONSTRAINT IF EXISTS "EmployeeGroup_fk" CASCADE;
ALTER TABLE pigtrax."MatingDetails" ADD CONSTRAINT "EmployeeGroup_fk" FOREIGN KEY ("id_EmployeeGroup")
REFERENCES pigtrax."EmployeeGroup" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "Pen_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."BreedingEvent" DROP CONSTRAINT IF EXISTS "Pen_fk" CASCADE;
ALTER TABLE pigtrax."BreedingEvent" ADD CONSTRAINT "Pen_fk" FOREIGN KEY ("id_Pen")
REFERENCES pigtrax."Pen" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: pigtrax."CompanyTarget" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."CompanyTarget" CASCADE;
CREATE TABLE pigtrax."CompanyTarget"(
	id serial NOT NULL,
	"id_TargetType" int NOT NULL,
	"targetValue" varchar(30) NOT NULL,
	"completionDate" timestamp NOT NULL,
	"remarks" varchar(255),
	"id_Company" int not null,
	"lastUpdated" timestamp not null,
	"userUpdated" varchar(30) not null,
	"id_Premise" int not null,
	"id_Ration" int,
	CONSTRAINT "COMPANY_TARGET_PK" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtrax."CompanyTarget" OWNER TO pitraxadmin;
-- ddl-end --


-- object: "Premise_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."CompanyTarget" DROP CONSTRAINT IF EXISTS "Premise_fk" CASCADE;
ALTER TABLE pigtrax."CompanyTarget" ADD CONSTRAINT "Premise_fk" FOREIGN KEY ("id_Premise")
REFERENCES pigtrax."Premise" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: pigtraxrefdata."TargetType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."TargetType" CASCADE;
CREATE TABLE pigtraxrefdata."TargetType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "TARGETTYPE_PK" PRIMARY KEY (id),
	CONSTRAINT "TARGETTYPE_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."TargetType" OWNER TO pitraxadmin;


-- object: "TargetType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."CompanyTarget" DROP CONSTRAINT IF EXISTS "TargetType_fk" CASCADE;
ALTER TABLE pigtrax."CompanyTarget" ADD CONSTRAINT "TargetType_fk" FOREIGN KEY ("id_TargetType")
REFERENCES pigtraxrefdata."TargetType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."TargetTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."TargetTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."TargetTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(100) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20),
	"id_TargetType" integer,
	CONSTRAINT "TARGETTYPETRANLATION_PK" PRIMARY KEY (id),
	CONSTRAINT "TARGETTYPETRANSLATION_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."TargetTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "TargetType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."TargetTypeTranslation" DROP CONSTRAINT IF EXISTS "TargetType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."TargetTypeTranslation" ADD CONSTRAINT "TargetType_fk" FOREIGN KEY ("id_TargetType")
REFERENCES pigtraxrefdata."TargetType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtrax."ChangedPigId" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."ChangedPigId" CASCADE;
CREATE TABLE pigtrax."ChangedPigId"(
	id serial NOT NULL,
	"id_PigInfo" int not null,
	"oldSowId" varchar(30) NOT NULL,
	"changedSowId" varchar(30) NOT NULL,
	"changeDateTime" timestamp not null,
	"id_Company" int not null,
	"id_Premise" int not null,
	"lastUpdated" timestamp not null,
	"userUpdated" varchar(30) not null,
	CONSTRAINT "CHANGEDPIGID_PK" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtrax."ChangedPigId" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "Company_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."ChangedPigId" DROP CONSTRAINT IF EXISTS "Company_fk" CASCADE;
ALTER TABLE pigtrax."ChangedPigId" ADD CONSTRAINT "Company_fk" FOREIGN KEY ("id_Company")
REFERENCES pigtrax."Company" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;


-- object: "Premise_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."ChangedPigId" DROP CONSTRAINT IF EXISTS "Premise_fk" CASCADE;
ALTER TABLE pigtrax."ChangedPigId" ADD CONSTRAINT "Premise_fk" FOREIGN KEY ("id_Premise")
REFERENCES pigtrax."Premise" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;

-- ddl-end --


-- object: "PigInfo_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."ChangedPigId" DROP CONSTRAINT IF EXISTS "PigInfo_fk" CASCADE;
ALTER TABLE pigtrax."ChangedPigId" ADD CONSTRAINT "PigInfo_fk" FOREIGN KEY ("id_PigInfo")
REFERENCES pigtrax."PigInfo" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtrax."ProductionLog" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."ProductionLog" CASCADE;
CREATE TABLE pigtrax."ProductionLog"(
	id serial NOT NULL,	
	"observation" varchar(500) NOT NULL,	
	"id_Company" int not null,
	"lastUpdated" timestamp not null,
	"userUpdated" varchar(30) not null,
	"id_LogEventType" int null,
	"id_Room" int null,
	"eventId" varchar(30),
	"observationDate" timestamp,
	"groupId" varchar(30),
	"id_Premise" integer,
	CONSTRAINT "PRODUCTIONLOG_PK" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtrax."ProductionLog" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "Company_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."ProductionLog" DROP CONSTRAINT IF EXISTS "Company_fk" CASCADE;
ALTER TABLE pigtrax."ProductionLog" ADD CONSTRAINT "Company_fk" FOREIGN KEY ("id_Company")
REFERENCES pigtrax."Company" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: "Room_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."ProductionLog" DROP CONSTRAINT IF EXISTS "Room_fk" CASCADE;
ALTER TABLE pigtrax."ProductionLog" ADD CONSTRAINT "Room_fk" FOREIGN KEY ("id_Room")
REFERENCES pigtrax."Room" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- DROP TABLE pigtrax."MasterRation";

CREATE TABLE pigtrax."MasterRation"
(
  id serial NOT NULL,
  "rationValue" character varying(20) NOT NULL,
  "id_FeedEventType" integer,
  "lastUpdated" timestamp without time zone NOT NULL,
  "userUpdated" character varying(20) NOT NULL,
  "rationDescription" varchar(200),
  "id_RationType" int,
  CONSTRAINT "MasterRation_PK" PRIMARY KEY (id)
);

ALTER TABLE pigtrax."MasterRation"
  OWNER TO pitraxadmin;

  -- object: "FeedEvent_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."MasterRation" DROP CONSTRAINT IF EXISTS "FeedEventType_fk" CASCADE;
ALTER TABLE pigtrax."MasterRation" ADD CONSTRAINT "FeedEventType_fk" FOREIGN KEY ("id_FeedEventType")
REFERENCES pigtraxrefdata."FeedEventType" (id) MATCH FULL
ON DELETE RESTRICT ON UPDATE CASCADE;




-- object: pigtraxrefdata."GcompanyType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."GcompanyType" CASCADE;
CREATE TABLE pigtraxrefdata."GcompanyType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "GCOMPANYTYPE_PK" PRIMARY KEY (id),
	CONSTRAINT "GCOMPANYTYPE_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."GcompanyType" OWNER TO pitraxadmin;


-- object: "GcompanyType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigInfo" DROP CONSTRAINT IF EXISTS "GcompanyType_fk" CASCADE;
ALTER TABLE pigtrax."PigInfo" ADD CONSTRAINT "GcompanyType_fk" FOREIGN KEY ("gcompany")
REFERENCES pigtraxrefdata."GcompanyType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."GcompanyTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."GcompanyTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."GcompanyTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20),
	"id_GcompanyType" integer,
	CONSTRAINT "GCOMPANYTYPETRANLATION_PK" PRIMARY KEY (id),
	CONSTRAINT "GCOMPANYTYPETRANSLATION_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."GcompanyTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "GcompanyType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."GcompanyTypeTranslation" DROP CONSTRAINT IF EXISTS "GcompanyType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."GcompanyTypeTranslation" ADD CONSTRAINT "GcompanyType_fk" FOREIGN KEY ("id_GcompanyType")
REFERENCES pigtraxrefdata."GcompanyType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --



-- object: pigtraxrefdata."GlineType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."GlineType" CASCADE;
CREATE TABLE pigtraxrefdata."GlineType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "GLINETYPE_PK" PRIMARY KEY (id),
	CONSTRAINT "GLINETYPE_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."GlineType" OWNER TO pitraxadmin;


-- object: "GlineType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigInfo" DROP CONSTRAINT IF EXISTS "GlineType_fk" CASCADE;
ALTER TABLE pigtrax."PigInfo" ADD CONSTRAINT "GlineType_fk" FOREIGN KEY ("gline")
REFERENCES pigtraxrefdata."GlineType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."GlineTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."GlineTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."GlineTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20),
	"id_GlineType" integer,
	CONSTRAINT "GLINETYPETRANLATION_PK" PRIMARY KEY (id),
	CONSTRAINT "GLINETYPETRANSLATION_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."GlineTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "GlineType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."GlineTypeTranslation" DROP CONSTRAINT IF EXISTS "GlineType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."GlineTypeTranslation" ADD CONSTRAINT "GlineType_fk" FOREIGN KEY ("id_GlineType")
REFERENCES pigtraxrefdata."GlineType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: pigtraxrefdata."LogEventType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."LogEventType" CASCADE;
CREATE TABLE pigtraxrefdata."LogEventType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "LOG_EVNT_TYPE_PK" PRIMARY KEY (id),
	CONSTRAINT "LOG_EVNT_TYPE_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."LogEventType" OWNER TO pitraxadmin;


-- object: "GlineType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigInfo" DROP CONSTRAINT IF EXISTS "LogEventType_fk" CASCADE;
ALTER TABLE pigtrax."ProductionLog" ADD CONSTRAINT "LogEventType_fk" FOREIGN KEY ("id_LogEventType")
REFERENCES pigtraxrefdata."LogEventType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."LogEventTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."LogEventTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."LogEventTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20),
	"id_LogEventType" integer,
	CONSTRAINT "LOGEVENTTYPETRANLATION_PK" PRIMARY KEY (id),
	CONSTRAINT "LOGEVENTTYPETRANSLATION_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."LogEventTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "LogEventType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."LogEventTypeTranslation" DROP CONSTRAINT IF EXISTS "LogEventType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."LogEventTypeTranslation" ADD CONSTRAINT "LogEventType_fk" FOREIGN KEY ("id_LogEventType")
REFERENCES pigtraxrefdata."LogEventType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --



-- object: "RemovalEvent_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigTraxEventMaster" DROP CONSTRAINT IF EXISTS "SalesEventDetails_fk" CASCADE;
ALTER TABLE pigtrax."PigTraxEventMaster" ADD CONSTRAINT "SalesEventDetails_fk" FOREIGN KEY ("id_SalesEventDetails")
REFERENCES pigtrax."SalesEventDetails" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "RemovalEvent_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigTraxEventMaster" DROP CONSTRAINT IF EXISTS "RemovalEventExceptSalesDetails_fk" CASCADE;
ALTER TABLE pigtrax."PigTraxEventMaster" ADD CONSTRAINT "RemovalEventExceptSalesDetails_fk" FOREIGN KEY ("id_RemovalEventExceptSalesDetails")
REFERENCES pigtrax."RemovalEventExceptSalesDetails" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- object: pigtrax."Origin" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."Origin" CASCADE;
CREATE TABLE pigtrax."Origin"(
	id serial NOT NULL,	
	"name" varchar(500) NOT NULL,
	"lastUpdated" timestamp not null,
	"userUpdated" varchar(30) not null,
	CONSTRAINT "ORIGIN_PK" PRIMARY KEY (id)

);


-- object: "Origin_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."PigInfo" DROP CONSTRAINT IF EXISTS "Origin_fk" CASCADE;
ALTER TABLE pigtrax."PigInfo" ADD CONSTRAINT "Origin_fk" FOREIGN KEY ("id_Origin")
REFERENCES pigtrax."Origin" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --




-- object: pigtraxrefdata."PigletCondition" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."PigletCondition" CASCADE;
CREATE TABLE pigtraxrefdata."PigletCondition"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "PigletCondition_PK" PRIMARY KEY (id),
	CONSTRAINT "PigletCondition_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."PigletCondition" OWNER TO pitraxadmin;


-- object: "PigletCondition_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."FarrowEvent" DROP CONSTRAINT IF EXISTS "PigletCondition_fk" CASCADE;
ALTER TABLE pigtrax."FarrowEvent" ADD CONSTRAINT "PigletCondition_fk" FOREIGN KEY ("id_PigletCondition")
REFERENCES pigtraxrefdata."PigletCondition" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."PigletConditionTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."PigletConditionTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."PigletConditionTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20),
	"id_PigletCondition" integer,
	CONSTRAINT "PigletConditionTranslation_PK" PRIMARY KEY (id),
	CONSTRAINT "PigletConditionTranslation_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."PigletConditionTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "PigletCondition_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."PigletConditionTranslation" DROP CONSTRAINT IF EXISTS "PigletCondition_fk" CASCADE;
ALTER TABLE pigtraxrefdata."PigletConditionTranslation" ADD CONSTRAINT "PigletCondition_fk" FOREIGN KEY ("id_PigletCondition")
REFERENCES pigtraxrefdata."PigletCondition" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --





-- object: pigtraxrefdata."SalesType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."SalesType" CASCADE;
CREATE TABLE pigtraxrefdata."SalesType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "SalesType_PK" PRIMARY KEY (id),
	CONSTRAINT "SalesType_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."SalesType" OWNER TO pitraxadmin;


-- object: pigtraxrefdata."SalesTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."SalesTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."SalesTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20),
	"id_SalesType" integer,
	CONSTRAINT "SalesTypeTranslation_PK" PRIMARY KEY (id),
	CONSTRAINT "SalesTypeTranslation_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."SalesTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "SalesType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."SalesTypeTranslation" DROP CONSTRAINT IF EXISTS "SalesType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."SalesTypeTranslation" ADD CONSTRAINT "SalesType_fk" FOREIGN KEY ("id_SalesType")
REFERENCES pigtraxrefdata."SalesType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --




-- object: pigtraxrefdata."SalesReasons" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."SalesReasons" CASCADE;
CREATE TABLE pigtraxrefdata."SalesReasons"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "SalesReasons_PK" PRIMARY KEY (id),
	CONSTRAINT "SalesReasons_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."SalesReasons" OWNER TO pitraxadmin;

-- object: pigtraxrefdata."SalesReasonsTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."SalesReasonsTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."SalesReasonsTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(100) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20),
	"id_SalesReason" integer,
	CONSTRAINT "SalesReasonsTranslation_PK" PRIMARY KEY (id),
	CONSTRAINT "SalesReasonsTranslation_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."SalesReasonsTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "SalesType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."SalesReasonsTranslation" DROP CONSTRAINT IF EXISTS "SalesReason_fk" CASCADE;
ALTER TABLE pigtraxrefdata."SalesReasonsTranslation" ADD CONSTRAINT "SalesReason_fk" FOREIGN KEY ("id_SalesReason")
REFERENCES pigtraxrefdata."SalesReasons" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --





-- object: pigtraxrefdata."PremiseType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."PremiseType" CASCADE;
CREATE TABLE pigtraxrefdata."PremiseType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "PremiseType_PK" PRIMARY KEY (id),
	CONSTRAINT "PremiseType_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."PremiseType" OWNER TO pitraxadmin;


-- object: "GlineType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."Premise" DROP CONSTRAINT IF EXISTS "LogEventType_fk" CASCADE;
ALTER TABLE pigtrax."Premise" ADD CONSTRAINT "PremiseType_fk" FOREIGN KEY ("id_PremiseType")
REFERENCES pigtraxrefdata."PremiseType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."PremiseTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."PremiseTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."PremiseTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20),
	"id_PremiseType" integer,
	CONSTRAINT "PremiseTypeTranslation_PK" PRIMARY KEY (id),
	CONSTRAINT "PremiseTypeTranslation_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."PremiseTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "PremiseType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."PremiseTypeTranslation" DROP CONSTRAINT IF EXISTS "PremiseType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."PremiseTypeTranslation" ADD CONSTRAINT "PremiseType_fk" FOREIGN KEY ("id_PremiseType")
REFERENCES pigtraxrefdata."PremiseType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --





-- object: pigtraxrefdata."BarnLocation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."BarnLocation" CASCADE;
CREATE TABLE pigtraxrefdata."BarnLocation"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "BarnLocation_PK" PRIMARY KEY (id),
	CONSTRAINT "BarnLocation_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."BarnLocation" OWNER TO pitraxadmin;


-- object: "BarnLocation_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."Barn" DROP CONSTRAINT IF EXISTS "BarnLocation_fk" CASCADE;
ALTER TABLE pigtrax."Barn" ADD CONSTRAINT "BarnLocation_fk" FOREIGN KEY ("id_BarnLocation")
REFERENCES pigtraxrefdata."BarnLocation" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."BarnLocationTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."BarnLocationTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."BarnLocationTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20),
	"id_BarnLocation" integer,
	CONSTRAINT "BarnLocationTranslation_PK" PRIMARY KEY (id),
	CONSTRAINT "BarnLocationTranslation_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."BarnLocationTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "BarnLocation_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."BarnLocationTranslation" DROP CONSTRAINT IF EXISTS "BarnLocation_fk" CASCADE;
ALTER TABLE pigtraxrefdata."BarnLocationTranslation" ADD CONSTRAINT "BarnLocation_fk" FOREIGN KEY ("id_BarnLocation")
REFERENCES pigtraxrefdata."BarnLocation" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --






-- object: pigtraxrefdata."WaterType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."WaterType" CASCADE;
CREATE TABLE pigtraxrefdata."WaterType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "WaterType_PK" PRIMARY KEY (id),
	CONSTRAINT "WaterType_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."WaterType" OWNER TO pitraxadmin;


-- object: "WaterType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."Barn" DROP CONSTRAINT IF EXISTS "WaterType_fk" CASCADE;
ALTER TABLE pigtrax."Barn" ADD CONSTRAINT "WaterType_fk" FOREIGN KEY ("id_WaterType")
REFERENCES pigtraxrefdata."WaterType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."WaterTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."WaterTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."WaterTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20),
	"id_WaterType" integer,
	CONSTRAINT "WaterTypeTranslation_PK" PRIMARY KEY (id),
	CONSTRAINT "WaterTypeTranslation_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."WaterTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "WaterType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."WaterTypeTranslation" DROP CONSTRAINT IF EXISTS "WaterType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."WaterTypeTranslation" ADD CONSTRAINT "WaterType_fk" FOREIGN KEY ("id_WaterType")
REFERENCES pigtraxrefdata."WaterType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --





-- object: pigtraxrefdata."BarnPosition" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."BarnPosition" CASCADE;
CREATE TABLE pigtraxrefdata."BarnPosition"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "BarnPosition_PK" PRIMARY KEY (id),
	CONSTRAINT "BarnPosition_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."BarnPosition" OWNER TO pitraxadmin;


-- object: "BarnPosition_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."Barn" DROP CONSTRAINT IF EXISTS "BarnPosition_fk" CASCADE;
ALTER TABLE pigtrax."Barn" ADD CONSTRAINT "BarnPosition_fk" FOREIGN KEY ("id_BarnPosition")
REFERENCES pigtraxrefdata."BarnPosition" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."BarnPositionTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."BarnPositionTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."BarnPositionTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20),
	"id_BarnPosition" integer,
	CONSTRAINT "BarnPositionTranslation_PK" PRIMARY KEY (id),
	CONSTRAINT "BarnPositionTranslation_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."BarnPositionTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "BarnPosition_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."BarnPositionTranslation" DROP CONSTRAINT IF EXISTS "BarnPosition_fk" CASCADE;
ALTER TABLE pigtraxrefdata."BarnPositionTranslation" ADD CONSTRAINT "BarnPosition_fk" FOREIGN KEY ("id_BarnPosition")
REFERENCES pigtraxrefdata."BarnPosition" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --






-- object: pigtraxrefdata."FeederType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."FeederType" CASCADE;
CREATE TABLE pigtraxrefdata."FeederType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "FeederType_PK" PRIMARY KEY (id),
	CONSTRAINT "FeederType_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."FeederType" OWNER TO pitraxadmin;


-- object: "FeederType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."Barn" DROP CONSTRAINT IF EXISTS "FeederType_fk" CASCADE;
ALTER TABLE pigtrax."Barn" ADD CONSTRAINT "FeederType_fk" FOREIGN KEY ("id_FeederType")
REFERENCES pigtraxrefdata."FeederType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."FeederTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."FeederTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."FeederTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20),
	"id_FeederType" integer,
	CONSTRAINT "FeederTypeTranslation_PK" PRIMARY KEY (id),
	CONSTRAINT "FeederTypeTranslation_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."FeederTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "FeederType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."FeederTypeTranslation" DROP CONSTRAINT IF EXISTS "FeederType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."FeederTypeTranslation" ADD CONSTRAINT "FeederType_fk" FOREIGN KEY ("id_FeederType")
REFERENCES pigtraxrefdata."FeederType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --




-- object: pigtraxrefdata."FunctionType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."FunctionType" CASCADE;
CREATE TABLE pigtraxrefdata."FunctionType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "FunctionType_PK" PRIMARY KEY (id),
	CONSTRAINT "FunctionType_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."FunctionType" OWNER TO pitraxadmin;


-- object: "FeederType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."Employee" DROP CONSTRAINT IF EXISTS "FunctionType_fk" CASCADE;
ALTER TABLE pigtrax."Employee" ADD CONSTRAINT "FunctionType_fk" FOREIGN KEY ("id_FunctionType")
REFERENCES pigtraxrefdata."FunctionType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."FunctionTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."FunctionTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."FunctionTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20),
	"id_FunctionType" integer,
	CONSTRAINT "FunctionTypeTranslation_PK" PRIMARY KEY (id),
	CONSTRAINT "FunctionTypeTranslation_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."FunctionTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "FunctionType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."FunctionTypeTranslation" DROP CONSTRAINT IF EXISTS "FunctionType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."FunctionTypeTranslation" ADD CONSTRAINT "FunctionType_fk" FOREIGN KEY ("id_FunctionType")
REFERENCES pigtraxrefdata."FunctionType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --




-- object: pigtraxrefdata."JobFunctionRole" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."JobFunctionRole" CASCADE;
CREATE TABLE pigtraxrefdata."JobFunctionRole"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "JobFunctionRole_PK" PRIMARY KEY (id),
	CONSTRAINT "JobFunctionRole_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."JobFunctionRole" OWNER TO pitraxadmin;


-- object: "FeederType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."Employee" DROP CONSTRAINT IF EXISTS "JobFunctionRole_fk" CASCADE;
ALTER TABLE pigtrax."Employee" ADD CONSTRAINT "JobFunctionRole_fk" FOREIGN KEY ("id_JobFunctionRole")
REFERENCES pigtraxrefdata."JobFunctionRole" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."JobFunctionRoleTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."JobFunctionRoleTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."JobFunctionRoleTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20),
	"id_JobFunctionRole" integer,
	CONSTRAINT "JobFunctionRoleTranslation_PK" PRIMARY KEY (id),
	CONSTRAINT "JobFunctionRoleTranslation_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."JobFunctionRoleTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "JobFunctionRole_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."JobFunctionRoleTranslation" DROP CONSTRAINT IF EXISTS "JobFunctionRole_fk" CASCADE;
ALTER TABLE pigtraxrefdata."JobFunctionRoleTranslation" ADD CONSTRAINT "JobFunctionRole_fk" FOREIGN KEY ("id_JobFunctionRole")
REFERENCES pigtraxrefdata."JobFunctionRole" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --






-- object: pigtraxrefdata."TrailerFunction" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."TrailerFunction" CASCADE;
CREATE TABLE pigtraxrefdata."TrailerFunction"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "TrailerFunction_PK" PRIMARY KEY (id),
	CONSTRAINT "TrailerFunction_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."TrailerFunction" OWNER TO pitraxadmin;


-- object: "FeederType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."TransportTrailer" DROP CONSTRAINT IF EXISTS "TrailerFunction_fk" CASCADE;
ALTER TABLE pigtrax."TransportTrailer" ADD CONSTRAINT "TrailerFunction_fk" FOREIGN KEY ("id_TrailerFunction")
REFERENCES pigtraxrefdata."TrailerFunction" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."TrailerFunctionTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."TrailerFunctionTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."TrailerFunctionTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(100) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20),
	"id_TrailerFunction" integer,
	CONSTRAINT "TrailerFunctionTranslation_PK" PRIMARY KEY (id),
	CONSTRAINT "TrailerFunctionTranslation_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."TrailerFunctionTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "TrailerFunction_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."TrailerFunctionTranslation" DROP CONSTRAINT IF EXISTS "TrailerFunction_fk" CASCADE;
ALTER TABLE pigtraxrefdata."TrailerFunctionTranslation" ADD CONSTRAINT "TrailerFunction_fk" FOREIGN KEY ("id_TrailerFunction")
REFERENCES pigtraxrefdata."TrailerFunction" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --






-- object: pigtraxrefdata."MarketType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."MarketType" CASCADE;
CREATE TABLE pigtraxrefdata."MarketType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "MarketType_PK" PRIMARY KEY (id),
	CONSTRAINT "MarketType_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."MarketType" OWNER TO pitraxadmin;


-- object: "FeederType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."TransportTrailer" DROP CONSTRAINT IF EXISTS "MarketType_fk" CASCADE;
ALTER TABLE pigtrax."TransportDestination" ADD CONSTRAINT "MarketType_fk" FOREIGN KEY ("id_MarketType")
REFERENCES pigtraxrefdata."MarketType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."MarketTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."MarketTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."MarketTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20),
	"id_MarketType" integer,
	CONSTRAINT "MarketTypeTranslation_PK" PRIMARY KEY (id),
	CONSTRAINT "MarketTypeTranslation_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."MarketTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "MarketType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."MarketTypeTranslation" DROP CONSTRAINT IF EXISTS "MarketType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."MarketTypeTranslation" ADD CONSTRAINT "MarketType_fk" FOREIGN KEY ("id_MarketType")
REFERENCES pigtraxrefdata."MarketType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --




-- object: pigtraxrefdata."RationType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."RationType" CASCADE;
CREATE TABLE pigtraxrefdata."RationType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "RationType_PK" PRIMARY KEY (id),
	CONSTRAINT "RationType_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."RationType" OWNER TO pitraxadmin;


-- object: "RationType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."MasterRation" DROP CONSTRAINT IF EXISTS "RationType_fk" CASCADE;
ALTER TABLE pigtrax."MasterRation" ADD CONSTRAINT "RationType_fk" FOREIGN KEY ("id_RationType")
REFERENCES pigtraxrefdata."RationType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtraxrefdata."RationTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."RationTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."RationTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(30) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20),
	"id_RationType" integer,
	CONSTRAINT "RationTypeTranslation_PK" PRIMARY KEY (id),
	CONSTRAINT "RationTypeTranslation_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."RationTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "RationType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."RationTypeTranslation" DROP CONSTRAINT IF EXISTS "RationType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."RationTypeTranslation" ADD CONSTRAINT "RationType_fk" FOREIGN KEY ("id_RationType")
REFERENCES pigtraxrefdata."RationType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --



-- object: pigtrax."GroupEventPhaseChange" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."GroupEventPhaseChange" CASCADE;
CREATE TABLE pigtrax."GroupEventPhaseChange"(
	id serial NOT NULL,
	"id_GroupEvent" integer NOT NULL,
	"id_PhaseOfProductionType" integer NOT NULL,
	"phaseStartDate" timestamp with time zone NOT NULL,
	"phaseEndDate" timestamp with time zone,
	"id_Premise" integer NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20),
	CONSTRAINT "GroupEventPhaseChange_PK" PRIMARY KEY (id)
	
);


-- object: "GroupEvent_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."GroupEventPhaseChange" DROP CONSTRAINT IF EXISTS "GroupEvent_fk" CASCADE;
ALTER TABLE pigtrax."GroupEventPhaseChange" ADD CONSTRAINT "GroupEvent_fk" FOREIGN KEY ("id_GroupEvent")
REFERENCES pigtrax."GroupEvent" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


-- ALTER TABLE pigtrax."GroupEventPhaseChange" DROP CONSTRAINT "PhaseOfProductionType_fk";
ALTER TABLE pigtrax."GroupEventPhaseChange"  ADD CONSTRAINT "PhaseOfProductionType_fk" FOREIGN KEY ("id_PhaseOfProductionType")     
REFERENCES pigtraxrefdata."PhaseOfProductionType" (id) MATCH FULL 
ON UPDATE CASCADE ON DELETE SET NULL;


-- object: "Premise_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."Premise" DROP CONSTRAINT IF EXISTS "Premise_fk" CASCADE;
ALTER TABLE pigtrax."GroupEventPhaseChange" ADD CONSTRAINT "Premise_fk" FOREIGN KEY ("id_Premise")
REFERENCES pigtrax."Premise" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtrax."GroupEventRoom" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."GroupEventRoom" CASCADE;
CREATE TABLE pigtrax."GroupEventRoom"(
	"id" serial NOT NULL,
	"id_GroupEventPhaseChange" integer NOT NULL,
	"id_Room" integer NOT NULL,
	CONSTRAINT "GroupEventRoom_PK" PRIMARY KEY (id)	
);

-- object: "GroupEventPhaseChange_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."GroupEventRoom" DROP CONSTRAINT IF EXISTS "GroupEventPhaseChange_fk" CASCADE;
ALTER TABLE pigtrax."GroupEventRoom" ADD CONSTRAINT "GroupEventPhaseChange_fk" FOREIGN KEY ("id_GroupEventPhaseChange")
REFERENCES pigtrax."GroupEventPhaseChange" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "Room_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."GroupEventRoom" DROP CONSTRAINT IF EXISTS "Room_fk" CASCADE;
ALTER TABLE pigtrax."GroupEventRoom" ADD CONSTRAINT "Room_fk" FOREIGN KEY ("id_Room")
REFERENCES pigtrax."Room" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: pigtrax."MatingDetails" | type: TABLE --
-- DROP TABLE IF EXISTS pigtrax."SowMovement" CASCADE;
CREATE TABLE pigtrax."SowMovement"(
	id serial NOT NULL,
	"id_PigInfo" integer NOT NULL,
	"id_Room" integer,
	"id_Premise" integer NOT NULL,
	"movementDate" timestamp NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	"id_Company" integer,
	CONSTRAINT "SOW_MOVEMENT_PK" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtrax."SowMovement" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "Room_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."SowMovement" DROP CONSTRAINT IF EXISTS "Room_fk" CASCADE;
ALTER TABLE pigtrax."SowMovement" ADD CONSTRAINT "Room_fk" FOREIGN KEY ("id_Room")
REFERENCES pigtrax."Room" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;

-- ALTER TABLE pigtrax."SowMovement" DROP CONSTRAINT IF EXISTS "Premise_fk" CASCADE;
ALTER TABLE pigtrax."SowMovement" ADD CONSTRAINT "Premise_fk" FOREIGN KEY ("id_Premise")
REFERENCES pigtrax."Premise" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;

-- object: "PigInfo_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."SowMovement" DROP CONSTRAINT IF EXISTS "PigInfo_fk" CASCADE;
ALTER TABLE pigtrax."SowMovement" ADD CONSTRAINT "PigInfo_fk" FOREIGN KEY ("id_PigInfo")
REFERENCES pigtrax."PigInfo" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

--report table---
-- DROP TABLE IF EXISTS pigtrax."PigInfoParityLog" CASCADE;
CREATE TABLE pigtrax."PigInfoParityLog"
(
  id serial NOT NULL,
 "id_PigInfo" integer NOT NULL,
  "parity" integer DEFAULT 0,
  "farrowDateTime" timestamp without time zone NOT NULL,
  "id_Premise" integer not null,
  "lastUpdated" timestamp without time zone NOT NULL,
  "userUpdated" character varying(20) NOT NULL
)
;

--DROP TABLE IF EXISTS pigtrax."DataIntegrityLog" CASCADE;
CREATE TABLE pigtrax."DataIntegrityLog"
(
  "id" serial NOT NULL,
  "eventType" varchar(50) NOT NULL,
  "errorType" varchar(50) NOT NULL,
  "eventDate" timestamp without time zone NOT NULL,
  "errorDescription" varchar(1000) not null,
  "id_Company" integer,
  "userId"varchar(50),
  "relevantField" varchar(50),
  "id_Premise" integer,
  CONSTRAINT "DataIntegrityLog_PK" PRIMARY KEY (id)
)
;


-- object: pigtraxrefdata."EventType" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."EventType" CASCADE;
CREATE TABLE pigtraxrefdata."EventType"(
	id serial NOT NULL,
	"fieldCode" smallint NOT NULL,
	"fieldDescription" varchar(100) NOT NULL,
	"lastUpdated" timestamp NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "EventType_PK" PRIMARY KEY (id),
	CONSTRAINT "EventType_FC_U" UNIQUE ("fieldCode")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."EventType" OWNER TO pitraxadmin;


-- object: pigtraxrefdata."EventTypeTranslation" | type: TABLE --
-- DROP TABLE IF EXISTS pigtraxrefdata."EventTypeTranslation" CASCADE;
CREATE TABLE pigtraxrefdata."EventTypeTranslation"(
	id serial NOT NULL,
	"fieldValue" varchar(100) NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20),
	"id_EventType" integer,
	CONSTRAINT "EventTypeTranslation_PK" PRIMARY KEY (id),
	CONSTRAINT "EventTypeTranslation_FV_FL_U" UNIQUE ("fieldValue","fieldLanguage")

);
-- ddl-end --
ALTER TABLE pigtraxrefdata."EventTypeTranslation" OWNER TO pitraxadmin;
-- ddl-end --

-- object: "EventType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxrefdata."EventTypeTranslation" DROP CONSTRAINT IF EXISTS "EventType_fk" CASCADE;
ALTER TABLE pigtraxrefdata."EventTypeTranslation" ADD CONSTRAINT "EventType_fk" FOREIGN KEY ("id_EventType")
REFERENCES pigtraxrefdata."EventType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --




-- object: pigtrax."GroupStatusReportData" | type: TABLE --
DROP TABLE IF EXISTS pigtrax."GroupStatusReportData" CASCADE;
CREATE TABLE pigtrax."GroupStatusReportData"(
	id serial NOT NULL, 
	"calendarWk" integer,
	"id_GroupEvent" integer not null,
	"groupId" varchar(50),
	"eventStartDate" date,
	"eventCloseDate" date,
	"StartHd" integer,
	"StartWt" numeric(20,2),
	"W1" integer ,
	"W2" integer,
	"W3" integer,
	"W4" integer,
	"W5" integer,
	"W6" integer,
	"W7" integer,
	"W8" integer,
	"W9" integer,
	"W10" integer,
	"W11" integer,
	"W12" integer,
	"W13" integer,
	"W14" integer,
	"W15" integer,
	"W16" integer,
	"W17" integer,
	"W18" integer,
	"W19" integer,
	"W20" integer,
	"W21" integer,
	"W22" integer,
	"W23" integer,
	"W24" integer,
	"W25" integer,
	"W26" integer,	
	"type" varchar(10),
	"sowSource" varchar(50),
	"id_PhaseType" integer,
	"roomId" varchar(50),
	"barnId" varchar(50),
	"premiseId" varchar(50),
	"inventory" integer,
	"deads" integer,
	"mortalityPercentage" numeric(20,2),
	"density" numeric(20,2),
	"sales" integer,
	"projectedSaleDate" date,
	"projectedSaleWk" integer,	
	"id_SowSource" integer,
	"id_Premise" integer,
	"lastUpdatedOn" timestamp,
	CONSTRAINT "GroupStatusReportData_PK" PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE pigtrax."GroupStatusReportData" OWNER TO pitraxadmin;
-- ddl-end --


-- object: pigtrax."GroupPerformanceReportData" | type: TABLE --
DROP TABLE IF EXISTS pigtrax."GroupPerformanceReportData" CASCADE;
CREATE TABLE pigtrax."GroupPerformanceReportData"(
	 id serial NOT NULL, 
	"permiseId" integer,
	"premise" varchar(100),
	"id_GroupEvent" integer,
	"isActive" boolean,
	"performanceData" TEXT,
	"groupStartDate" timestamp,
	"groupEndDate" timestamp,
	CONSTRAINT "GroupPerformanceReportData_PK" PRIMARY KEY (id)
);
ALTER TABLE pigtrax."GroupPerformanceReportData" OWNER TO pitraxadmin;
-- ddl-end --



-- object: "Company_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtrax."GroupStatusReportData" DROP CONSTRAINT IF EXISTS "GroupEvent_fk" CASCADE;
ALTER TABLE pigtrax."GroupStatusReportData" ADD CONSTRAINT "GroupEvent_fk" FOREIGN KEY ("id_GroupEvent")
REFERENCES pigtrax."GroupEvent" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --


--Views
CREATE OR REPLACE VIEW pigtrax."CompPremBarnSiloVw"
as(
SELECT co.id AS companyserialid, co."companyId", pr.id AS premiseserialid, pr."permiseId", ba.id AS barnserialid, ba."barnId", si.id AS siloserrialid, si."siloId"
FROM
pigtrax."Company" AS co
LEFT OUTER JOIN pigtrax."Premise" AS pr ON co.id=pr."id_Company"
LEFT OUTER JOIN pigtrax."Barn" AS ba ON pr.id = ba."id_Premise"
LEFT OUTER JOIN pigtrax."Silo" AS si ON ba.id = si."id_Barn"
ORDER BY co.id, pr.id
);


CREATE OR REPLACE VIEW pigtrax."CompPremBarnRoomPenVw"
as(
SELECT co.id AS companyserialid, co."companyId", pr.id AS premiseserialid, pr."permiseId", ba.id AS barnserialid, ba."barnId", ro.id AS roomserrialid, ro."roomId", pe.id AS penserialid, pe."penId" 
FROM
pigtrax."Company" AS co
LEFT OUTER JOIN pigtrax."Premise" AS pr ON co.id=pr."id_Company"
LEFT OUTER JOIN pigtrax."Barn" AS ba ON pr.id = ba."id_Premise"
LEFT OUTER JOIN pigtrax."Room" AS ro ON ba.id = ro."id_Barn"
LEFT OUTER JOIN pigtrax."Pen" AS pe ON ro.id = pe."id_Room"
ORDER BY co.id, pr.id
);
