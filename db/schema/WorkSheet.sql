DROP SCHEMA IF EXISTS pigtraxstaging CASCADE;

CREATE SCHEMA pigtraxstaging;
-- ddl-end --
ALTER schema pigtraxstaging OWNER to pigtraxadmin;
-- ddl-end --

DROP SCHEMA IF EXISTS pigtraxmetadata CASCADE;
CREATE SCHEMA pigtraxmetadata;

ALTER schema pigtraxmetadata OWNER to pigtraxadmin;

SET search_path TO pg_catalog,public,pigtraxstaging;
-- ddl-end --

------------------------------------

DROP TABLE IF EXISTS pigtraxstaging."StagingLog" CASCADE;
CREATE TABLE pigtraxstaging."StagingLog"(
	id serial NOT NULL,
	"eventName" varchar(30),
	"logCode" varchar(50),
	"logMessage" varchar(1000) NOT NULL,
	"logData" varchar(10000),
	"logDateTime" timestamp NOT NULL,
	CONSTRAINT "STAGINGLOG_PK" PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE pigtraxstaging."StagingLog" OWNER TO pigtraxadmin;
-- ddl-end --


DROP TABLE IF EXISTS pigtraxstaging."PigInfoCountTemp" CASCADE;
CREATE TABLE pigtraxstaging."PigInfoCountTemp"(
	"pigId" varchar(30),
	count integer
);
-- ddl-end --
ALTER TABLE pigtraxstaging."PigInfoCountTemp" OWNER TO pigtraxadmin;
-- ddl-end --

DROP TABLE IF EXISTS pigtraxstaging."PigInfoDuplicateDetailsTemp" CASCADE;
CREATE TABLE pigtraxstaging."PigInfoDuplicateDetailsTemp"(
	"pigId" varchar(30),
	"srcLineNumber" varchar(20)
);
-- ddl-end --
ALTER TABLE pigtraxstaging."PigInfoDuplicateDetailsTemp" OWNER TO pigtraxadmin;
-- ddl-end --


-- object: pigtraxstaging."PigInfo" | type: TABLE --
DROP TABLE IF EXISTS pigtraxstaging."PigInfo" CASCADE;
CREATE TABLE pigtraxstaging."PigInfo"(
	id serial NOT NULL,
	"srcLineNumber" varchar(20),
	"pigId" varchar(30),
	"eventDateString" varchar(20),
	"eventNameString" varchar(30),
	"sexTypeString" varchar(15),
	parity smallint,
	gline varchar(30),
	"birthDateString" varchar(30),
	"alternateId" varchar(30),
	origin varchar(72),
	"entryGroup" varchar(30),
	remark varchar(255),
	gstatus varchar(30),
	"sireId" varchar(30),
	"damId" varchar(30),
	user1 varchar(30),
	user2 varchar(30),
	location varchar(30),
	tattoo varchar(30),
	"id_SexType" integer,
	"eventDate" date,
	"birthDate" date,
	CONSTRAINT "PIGINFO_PK" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtraxstaging."PigInfo" OWNER TO pigtraxadmin;
-- ddl-end --

DROP INDEX IF EXISTS pigtraxstaging."PIGINFO_PIGID_IN" CASCADE;
CREATE INDEX "PIGINFO_PIGID_IN" ON pigtraxstaging."PigInfo"
	USING btree
	(
	  "pigId" ASC NULLS LAST
	);

DROP INDEX IF EXISTS pigtraxstaging."PIGINFO_EVENTDATE_IN" CASCADE;
CREATE INDEX "PIGINFO_EVENTDATE_IN" ON pigtraxstaging."PigInfo"
	USING btree
	(
	  "eventDate" ASC NULLS LAST
	);

-- object: pigtraxmetadata."SexType" | type: TABLE --
DROP TABLE IF EXISTS pigtraxmetadata."SexType" CASCADE;
CREATE TABLE pigtraxmetadata."SexType"(
	id serial NOT NULL,
	"fieldLanguage" char(2) NOT NULL,
	"fieldValue" varchar(72) NOT NULL,
	"lastUpdated" timestamp with time zone NOT NULL,
	"userUpdated" varchar(20) NOT NULL,
	CONSTRAINT "SEXTYPE_PK" PRIMARY KEY (id),
	CONSTRAINT "SEXTYPE_FL_U" UNIQUE ("fieldLanguage","fieldValue")

);
-- ddl-end --
ALTER TABLE pigtraxmetadata."SexType" OWNER TO pigtraxadmin;
-- ddl-end --

-- Appended SQL commands --
INSERT INTO pigtraxmetadata."SexType" ("fieldLanguage", "fieldValue", "lastUpdated", "userUpdated") VALUES ('en','MALE',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxmetadata."SexType" ("fieldLanguage", "fieldValue", "lastUpdated", "userUpdated") VALUES ('en','FEMALE',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxmetadata."SexType" ("fieldLanguage", "fieldValue", "lastUpdated", "userUpdated") VALUES ('en','INVALID_VALUE',CURRENT_TIMESTAMP,'pigtraxadmin');
-- ddl-end --

-- object: "SexType_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxstaging."PigInfo" DROP CONSTRAINT IF EXISTS "SexType_fk" CASCADE;
ALTER TABLE pigtraxstaging."PigInfo" ADD CONSTRAINT "SexType_fk" FOREIGN KEY ("id_SexType")
REFERENCES pigtraxmetadata."SexType" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;


CREATE OR REPLACE FUNCTION pigtraxstaging."PigInfoDataValidation"() RETURNS int AS
$$
DECLARE
	piginfocountoriginal integer = 0;
	duplicateids varchar(10000) = null;
	nulleventdate varchar(10000) = null;
BEGIN
	SELECT COUNT(1) INTO piginfocountoriginal FROM pigtraxstaging."PigInfo";
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('PigInfo','INIT_COUNT', 'Total Number of rows copied in PigInfoTable: ' || piginfocountoriginal, '' || piginfocountoriginal, CURRENT_TIMESTAMP);

	INSERT INTO pigtraxstaging."PigInfoCountTemp" ( SELECT "pigId", count(*) FROM pigtraxstaging."PigInfo" 
							GROUP BY "pigId"
							HAVING count(*) > 1
							);
	INSERT INTO pigtraxstaging."PigInfoDuplicateDetailsTemp" ( SELECT pi."pigId", pi."srcLineNumber" 
								   FROM pigtraxstaging."PigInfo" pi, "PigInfoCountTemp" pict
								   WHERE pict."pigId" = pi."pigId"
								 );
	SELECT string_agg("pigId", ', ') into duplicateids FROM pigtraxstaging."PigInfoDuplicateDetailsTemp";
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('PigInfo', 'DUP_ID', 'Following pigIds have more than 1 occurences and eliminated from processing. They will be deleted ' , duplicateids, CURRENT_TIMESTAMP);

	DELETE FROM pigtraxstaging."PigInfo" USING pigtraxstaging."PigInfoDuplicateDetailsTemp" 
	WHERE pigtraxstaging."PigInfo"."pigId" =  pigtraxstaging."PigInfoDuplicateDetailsTemp"."pigId";

	SELECT COUNT(1) INTO piginfocountoriginal FROM pigtraxstaging."PigInfo";
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('PigInfo','POST_DUP_DEL_COUNT', 'Total Number of rows after deleting dups in PigInfoTable: ' || piginfocountoriginal, '' || piginfocountoriginal, CURRENT_TIMESTAMP);

	UPDATE pigtraxstaging."PigInfo" set "eventDate"=to_date("eventDateString",'DD/MM/YYYY') WHERE "eventDateString" is NOT NULL;

	SELECT string_agg("pigId", ', ') INTO nulleventdate FROM pigtraxstaging."PigInfo" pi where pi."eventDate" IS NULL;
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('PigInfo', 'NULL_EVENT_DATE', 'Following pigIds records have null event date. They will be deleted' , nulleventdate, CURRENT_TIMESTAMP);

	DELETE FROM pigtraxstaging."PigInfo" WHERE "eventDate" IS NULL;

	SELECT COUNT(1) INTO piginfocountoriginal FROM pigtraxstaging."PigInfo";
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('PigInfo','POST_NULL_DATE_DEL_COUNT', 'Total Number of rows which have null event date: ' || piginfocountoriginal, '' || piginfocountoriginal, CURRENT_TIMESTAMP);
					 
	RETURN 0;
END;   
$$
LANGUAGE plpgsql VOLATILE NOT LEAKPROOF;
ALTER FUNCTION pigtraxstaging."PigInfoDataValidation"()
OWNER TO pitraxadmin;

--At this point the excel has line numbers added as the first column and pigIds seperate from tattoos
--COPY pigtraxstaging."PigInfo" ("srcLineNumber","pigId","eventDateString","eventNameString","sexTypeString",parity,gline,"birthDateString","alternateId",origin,"entryGroup",remark,gstatus,"sireId","damId",user1,user2,location,tattoo) from 'C:\Users\Kshitij\Documents\PigTrax\DB-BulkMigration\CSV-1\Entry.csv' delimiter '|' CSV NULL AS '';

---Test SQL BEGIN
select pigtraxstaging."PigInfoDataValidation"();

--select * from  pigtraxstaging."StagingLog";
--select * from  pigtraxstaging."PigInfoCountTemp";
--select * from  pigtraxstaging."PigInfoDuplicateDetailsTemp";
--select string_agg("pigId",', ')  from  pigtraxstaging."PigInfoDuplicateDetailsTemp";
--select * from pigtraxstaging."PigInfo" limit 100;

---Test SQL END

--At this point we have good data in pigInfo


--BreedingEvent
-- object: pigtraxstaging."BreedingEvent" | type: TABLE --
DROP TABLE IF EXISTS pigtraxstaging."BreedingEvent" CASCADE;
CREATE TABLE pigtraxstaging."BreedingEvent"(
	id serial NOT NULL,
	"srcLineNumber" varchar(20),
	"pigId" varchar(30),
	"eventDateString" varchar(20),
	"eventNameString" varchar(30),
	"servGroup" varchar(30),
	"servType" varchar(10),
	"servLocation" varchar(30),
	boar1 varchar(30),
	tech1 varchar(72),
	"date2String" varchar(20),
	boar2 varchar(30),
	tech2 varchar(72),
	"date3String" varchar(20),
	boar3 varchar(30),
	tech3 varchar(72),
	remarks varchar(255),
	"semenAge1" varchar(10),
	"semenAge2" varchar(10),
	"semenAge3" varchar(30),
	"servScore1" varchar(10),
	"servTime1" varchar(10),
	"servTime2" varchar(10),
	"servTime3" varchar(10),
	"servScore2" varchar(10),
	"servScore3" varchar(10),
	"weightInKgs" numeric(20,2),
	"eventDate" date,
	"id_PigInfo" integer,
	CONSTRAINT "BREEDINGEVENT_PK" PRIMARY KEY (id)

);-- ddl-end --
ALTER TABLE pigtraxstaging."BreedingEvent" OWNER TO pigtraxadmin;
-- ddl-end --

-- object: "PigInfo_fk" | type: CONSTRAINT --
ALTER TABLE pigtraxstaging."BreedingEvent" DROP CONSTRAINT IF EXISTS "PigInfo_fk" CASCADE;
ALTER TABLE pigtraxstaging."BreedingEvent" ADD CONSTRAINT "PigInfo_fk" FOREIGN KEY ("id_PigInfo")
REFERENCES pigtraxstaging."PigInfo" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: "BREEDINGEVENT_PI_IN" | type: INDEX --
DROP INDEX IF EXISTS pigtraxstaging."BREEDINGEVENT_PI_IN" CASCADE;
CREATE INDEX "BREEDINGEVENT_PI_IN" ON pigtraxstaging."BreedingEvent"
	USING btree
	(
	  "pigId" ASC NULLS LAST
	);
-- ddl-end --

-- object: "BREEDINGEVENT_ED_IN" | type: INDEX --
DROP INDEX IF EXISTS pigtraxstaging."BREEDINGEVENT_ED_IN" CASCADE;
CREATE INDEX "BREEDINGEVENT_ED_IN" ON pigtraxstaging."BreedingEvent"
	USING btree
	(
	  "eventDate" ASC NULLS LAST
	);
-- ddl-end --

DROP INDEX IF EXISTS pigtraxstaging."BREEDINGEVENT_PIFK_IN" CASCADE;
CREATE INDEX "BREEDINGEVENT_PIFK_IN" ON pigtraxstaging."BreedingEvent"
	USING btree
	(
	  "id_PigInfo" ASC NULLS LAST
	);

	
CREATE OR REPLACE FUNCTION pigtraxstaging."BreedingEventDataValidation"() RETURNS int AS
$$
DECLARE
	breedingeventcount integer = 0;
	nullpiginfoid varchar(10000) = null;
	nullpiginfoidfk varchar(10000) = null;
	nulleventdate varchar(10000) = null;
BEGIN
--Log initial count
	SELECT COUNT(1) INTO breedingeventcount FROM pigtraxstaging."BreedingEvent";
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('BreedingEvent','INIT_COUNT', 'Total Number of rows copied in BreedingEvent Table: ' || breedingeventcount, '' || breedingeventcount, CURRENT_TIMESTAMP);

--Delete rows which have null pigIds in the source sheet
	SELECT string_agg("srcLineNumber", ', ') into nullpiginfoid FROM pigtraxstaging."BreedingEvent" WHERE "pigId" IS NULL;
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('BreedingEvent', 'NULL_PIGINFO_ID', 'Following line numbers in origin sheet have null piginfo ids. These will be deleted: ' , nullpiginfoid, CURRENT_TIMESTAMP);
	DELETE FROM pigtraxstaging."BreedingEvent" WHERE "pigId" IS NULL;

--Log total number of rows left after deleting the rows with null piginfo Ids
	SELECT COUNT(1) INTO breedingeventcount FROM pigtraxstaging."BreedingEvent";
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('BreedingEvent','POST_NULL_PIGINGO_DEL_COUNT', 'Total Number of rows in BreedingEvent table after deleting null pigInfo Ids: ' || breedingeventcount, '' || breedingeventcount, CURRENT_TIMESTAMP);

--Create the FK references for pigId and log + delete rows which cannot be linked back to pigInfo Table
	UPDATE pigtraxstaging."BreedingEvent" AS fe set "id_PigInfo"= pi.id FROM pigtraxstaging."PigInfo" AS pi 
	WHERE fe."pigId" = pi."pigId";
	SELECT string_agg("srcLineNumber", ', ') into nullpiginfoidfk FROM pigtraxstaging."BreedingEvent" WHERE "id_PigInfo" IS NULL;
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('BreedingEvent', 'NULL_PIGINFO_ID_FK', 'For following line numbers, the pigId cannot be linked back to pigInfo table. These will be deleted: ' , nullpiginfoidfk, CURRENT_TIMESTAMP);
	DELETE FROM pigtraxstaging."BreedingEvent" WHERE "id_PigInfo" IS NULL;
	
--Log the number of rows after delete
	SELECT COUNT(1) INTO breedingeventcount FROM pigtraxstaging."BreedingEvent";
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('BreedingEvent','POST_NULL_ID_PIGINFO_FK_DEL_COUNT', 'Total Number of rows after deleting rows for which a pigId cannot be traced back to pigInfo table ' || breedingeventcount, '' || breedingeventcount, CURRENT_TIMESTAMP);

--Do the date operations
	UPDATE pigtraxstaging."BreedingEvent" set "eventDate"=to_date("eventDateString",'DD/MM/YYYY') WHERE "eventDateString" is NOT NULL;

	SELECT string_agg("srcLineNumber", ', ') INTO nulleventdate FROM pigtraxstaging."BreedingEvent" where "eventDate" IS NULL;
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('BreedingEvent', 'NULL_EVENT_DATE', 'Following line numbers have null event date. They will be deleted' , nulleventdate, CURRENT_TIMESTAMP);

	DELETE FROM pigtraxstaging."BreedingEvent" WHERE "eventDate" IS NULL;
-- Log the number of rows after delete
	SELECT COUNT(1) INTO breedingeventcount FROM pigtraxstaging."BreedingEvent";
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('BreedingEvent','POST_NULL_DATE_DEL_COUNT', 'Total Number of rows which have null event date: ' || breedingeventcount, '' || breedingeventcount, CURRENT_TIMESTAMP);
					 
	RETURN 0;
END;   
$$
LANGUAGE plpgsql VOLATILE NOT LEAKPROOF;
ALTER FUNCTION pigtraxstaging."BreedingEventDataValidation"()
OWNER TO pitraxadmin;


--At this point the excel has line numbers added as the first column and pigIds seperated from tattoos
--\COPY pigtraxstaging."BreedingEvent" ("srcLineNumber","pigId","eventDateString","eventNameString","servGroup","servType","servLocation",boar1,tech1,"date2String",boar2,tech2,"date3String",boar3,tech3,remarks,"semenAge1","semenAge2","semenAge3","servScore1","servTime1","servTime2","servTime3","servScore2","servScore3","weightInKgs") from 'C:\Users\Kshitij\Documents\PigTrax\DB-BulkMigration\CSV-1\BreedingEvent.csv' delimiter '|' CSV NULL AS '';
---Test SQL BEGIN
--select pigtraxstaging."BreedingEventDataValidation"();
--select * from  pigtraxstaging."StagingLog";
---Test SQL END

--FarrowEvent
DROP TABLE IF EXISTS pigtraxstaging."FarrowEvent" CASCADE;
CREATE TABLE pigtraxstaging."FarrowEvent"(
	id serial NOT NULL,
	"srcLineNumber" varchar(20),
	"pigId" varchar(30),
	"eventDateString" varchar(20),
	"eventDate" date,
	"eventNameString" varchar(30),
	"liveBorns" smallint,
	"stillBorns" smallint,
	mummies smallint,
	"maleBorns" smallint,
	"femaleBorns" smallint,
	"weightInKgs" numeric(10,2),
	condition smallint,
	barn varchar(30),
	room varchar(30),
	crate varchar(30),
	remark varchar(255),
	"litterId" varchar(30),
	induced bool,
	assisted bool,
	"timeStr" varchar(10),
	monitor varchar(30),
	teat varchar(30),
	setter varchar(30),
	head varchar(30),
	mixed varchar(10),
	"weakBorn" varchar(10),
	"id_PigInfo" integer,
	CONSTRAINT "FARROWEVENT_PK" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtraxstaging."FarrowEvent" OWNER TO pigtraxadmin;
-- ddl-end --


-- object: "PigInfo_fk" | type: CONSTRAINT --
-- ALTER TABLE pigtraxstaging."FarrowEvent" DROP CONSTRAINT IF EXISTS "FarrowEvent_fk" CASCADE;
ALTER TABLE pigtraxstaging."FarrowEvent" ADD CONSTRAINT "FarrowEvent_fk" FOREIGN KEY ("id_PigInfo")
REFERENCES pigtraxstaging."PigInfo" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;

-- object: "FARROWEVENT_PI_IN" | type: INDEX --
DROP INDEX IF EXISTS pigtraxstaging."FARROWEVENT_PI_IN" CASCADE;
CREATE INDEX "FARROWEVENT_PI_IN" ON pigtraxstaging."FarrowEvent"
	USING btree
	(
	  "pigId" ASC NULLS LAST
	);
-- ddl-end --

-- object: "FARROWEVENT_ED_IN" | type: INDEX --
DROP INDEX IF EXISTS pigtraxstaging."FARROWEVENT_ED_IN" CASCADE;
CREATE INDEX "FARROWEVENT_ED_IN" ON pigtraxstaging."FarrowEvent"
	USING btree
	(
	  "eventDate" ASC NULLS LAST
	);
-- ddl-end --

DROP INDEX IF EXISTS pigtraxstaging."FARROWEVENT_PIFK_IN" CASCADE;
CREATE INDEX "FARROWEVENT_PIFK_IN" ON pigtraxstaging."FarrowEvent"
	USING btree
	(
	  "id_PigInfo" ASC NULLS LAST
	);
	
	
CREATE OR REPLACE FUNCTION pigtraxstaging."FarrowEventDataValidation"() RETURNS int AS
$$
DECLARE
	farroweventcount integer = 0;
	nullpiginfoid varchar(10000) = null;
	nullpiginfoidfk varchar(10000) = null;
	nulleventdate varchar(10000) = null;
BEGIN
--Log initial count
	SELECT COUNT(1) INTO farroweventcount FROM pigtraxstaging."FarrowEvent";
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('FarrowEvent','INIT_COUNT', 'Total Number of rows copied in FarrowEvent Table: ' || farroweventcount, '' || farroweventcount, CURRENT_TIMESTAMP);

--Delete rows which have null pigIds in the source sheet
	SELECT string_agg("srcLineNumber", ', ') into nullpiginfoid FROM pigtraxstaging."FarrowEvent" WHERE "pigId" IS NULL;
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('FarrowEvent', 'NULL_PIGINFO_ID', 'Following line numbers in origin sheet have null piginfo ids. These will be deleted: ' , nullpiginfoid, CURRENT_TIMESTAMP);
	DELETE FROM pigtraxstaging."FarrowEvent" WHERE "pigId" IS NULL;

--Log total number of rows left after deleting the rows with null piginfo Ids
	SELECT COUNT(1) INTO farroweventcount FROM pigtraxstaging."FarrowEvent";
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('FarrowEvent','POST_NULL_PIGINGO_DEL_COUNT', 'Total Number of rows in FarrowEvent table after deleting null pigInfo Ids: ' || farroweventcount, '' || farroweventcount, CURRENT_TIMESTAMP);

--Create the FK references for pigId and log + delete rows which cannot be linked back to pigInfo Table
	UPDATE pigtraxstaging."FarrowEvent" AS fe set "id_PigInfo"= pi.id FROM pigtraxstaging."PigInfo" AS pi 
	WHERE fe."pigId" = pi."pigId";
	SELECT string_agg("srcLineNumber", ', ') into nullpiginfoidfk FROM pigtraxstaging."FarrowEvent" WHERE "id_PigInfo" IS NULL;
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('FarrowEvent', 'NULL_PIGINFO_ID_FK', 'For following line numbers, the pigId cannot be linked back to pigInfo table. These will be deleted: ' , nullpiginfoidfk, CURRENT_TIMESTAMP);
	DELETE FROM pigtraxstaging."FarrowEvent" WHERE "id_PigInfo" IS NULL;
	
--Log the number of rows after delete
	SELECT COUNT(1) INTO farroweventcount FROM pigtraxstaging."FarrowEvent";
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('FarrowEvent','POST_NULL_ID_PIGINFO_FK_DEL_COUNT', 'Total Number of rows after deleting rows for which a pigId cannot be traced back to pigInfo table ' || farroweventcount, '' || farroweventcount, CURRENT_TIMESTAMP);

--Do the date operations
	UPDATE pigtraxstaging."FarrowEvent" set "eventDate"=to_date("eventDateString",'DD/MM/YYYY') WHERE "eventDateString" is NOT NULL;

	SELECT string_agg("srcLineNumber", ', ') INTO nulleventdate FROM pigtraxstaging."FarrowEvent" where "eventDate" IS NULL;
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('FarrowEvent', 'NULL_EVENT_DATE', 'Following line numbers have null event date. They will be deleted' , nulleventdate, CURRENT_TIMESTAMP);

	DELETE FROM pigtraxstaging."FarrowEvent" WHERE "eventDate" IS NULL;
-- Log the number of rows after delete
	SELECT COUNT(1) INTO farroweventcount FROM pigtraxstaging."FarrowEvent";
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('FarrowEvent','POST_NULL_DATE_DEL_COUNT', 'Total Number of rows which have null event date: ' || farroweventcount, '' || farroweventcount, CURRENT_TIMESTAMP);
					 
	RETURN 0;
END;   
$$
LANGUAGE plpgsql VOLATILE NOT LEAKPROOF;
ALTER FUNCTION pigtraxstaging."FarrowEventDataValidation"()
OWNER TO pitraxadmin;

--WE do the data copy now. We introduce line nums in original data and split the pigs ids to remove tattoos
--\COPY pigtraxstaging."FarrowEvent" ("srcLineNumber","pigId","eventDateString","eventNameString","liveBorns","stillBorns",mummies,"maleBorns","femaleBorns", "weightInKgs",condition,barn,room,crate,remark,"litterId",induced,assisted,"timeStr",monitor,teat,setter,head,mixed,"weakBorn") from 'C:\Users\Kshitij\Documents\PigTrax\DB-BulkMigration\CSV-1\FarrowEvent.csv' delimiter '|' CSV NULL AS '';

--select pigtraxstaging."FarrowEventDataValidation"();
--select * from  pigtraxstaging."StagingLog";
--select * from pigtraxstaging."FarrowEvent" limit 20;
---Test SQL END


--PigletStatus

DROP TABLE IF EXISTS pigtraxstaging."PigletStatusEvent" CASCADE;
CREATE TABLE pigtraxstaging."PigletStatusEvent"(
	id serial NOT NULL,
	"srcLineNumber" varchar(30),
	"pigId" varchar(30),
	"eventDateString" varchar(20),
	"eventDate" date,
	"eventNameString" varchar(20),
	"numberOfPigs" smallint,
	"weightInKgs" numeric(10,2),
	reason varchar(255),
	remarks varchar(255),
	"id_PigInfo" integer,
	CONSTRAINT "PIGLETSTATUS_PK" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtraxstaging."PigletStatusEvent" OWNER TO pigtraxadmin;
-- ddl-end --

-- object: "PigInfo_fk" | type: CONSTRAINT --
ALTER TABLE pigtraxstaging."PigletStatusEvent" DROP CONSTRAINT IF EXISTS "PigletStatusEvent_fk" CASCADE;
ALTER TABLE pigtraxstaging."PigletStatusEvent" ADD CONSTRAINT "PigletStatusEvent_fk" FOREIGN KEY ("id_PigInfo")
REFERENCES pigtraxstaging."PigInfo" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;

--Upload the data using copy now

-- object: "PIGLETSTATUSEVENT_PI_IN" | type: INDEX --
DROP INDEX IF EXISTS pigtraxstaging."PIGLETSTATUSEVENT_PI_IN" CASCADE;
CREATE INDEX "PIGLETSTATUSEVENT_PI_IN" ON pigtraxstaging."PigletStatusEvent"
	USING btree
	(
	  "pigId" ASC NULLS LAST
	);
-- ddl-end --
-- object: "FARROWEVENT_ED_IN" | type: INDEX --
DROP INDEX IF EXISTS pigtraxstaging."FARROWEVENT_ED_IN" CASCADE;
CREATE INDEX "PIGLETSTATUSEVENT_ED_IN" ON pigtraxstaging."PigletStatusEvent"
	USING btree
	(
	  "eventDate" ASC NULLS LAST
	);
-- ddl-end --


DROP INDEX IF EXISTS pigtraxstaging."PIGLETSTATUSEVENT_PIFK_IN" CASCADE;
CREATE INDEX "PIGLETSTATUSVENT_PIFK_IN" ON pigtraxstaging."PigletStatusEvent"
	USING btree
	(
	  "id_PigInfo" ASC NULLS LAST
	);

CREATE OR REPLACE FUNCTION pigtraxstaging."PigletStatusEventDataValidation"() RETURNS int AS
$$
DECLARE
	pigletstatuseventcount integer = 0;
	nullpiginfoid varchar(10000) = null;
	nullpiginfoidfk varchar(10000) = null;
	nulleventdate varchar(10000) = null;
BEGIN
--Log initial count
	--RAISE notice 'Before Log Initial Count: %', CURRENT_TIMESTAMP;
	SELECT COUNT(1) INTO pigletstatuseventcount FROM pigtraxstaging."PigletStatusEvent";
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('PigletStatusEvent','INIT_COUNT', 'Total Number of rows copied in PigletStatusEvent Table: ' || pigletstatuseventcount, '' || pigletstatuseventcount, CURRENT_TIMESTAMP);
	--RAISE notice 'After Log Initial Count: %', CURRENT_TIMESTAMP;
--Delete rows which have null pigIds in the source sheet
	
	SELECT string_agg("srcLineNumber", ', ') into nullpiginfoid FROM pigtraxstaging."PigletStatusEvent" WHERE "pigId" IS NULL;
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('PigletStatusEvent', 'NULL_PIGINFO_ID', 'Following line numbers in origin sheet have null piginfo ids. These will be deleted: ' , nullpiginfoid, CURRENT_TIMESTAMP);
	DELETE FROM pigtraxstaging."PigletStatusEvent" WHERE "pigId" IS NULL;
	--RAISE notice 'After deleting rows with null pigIds: %', CURRENT_TIMESTAMP;
--Log total number of rows left after deleting the rows with null piginfo Ids
	SELECT COUNT(1) INTO pigletstatuseventcount FROM pigtraxstaging."PigletStatusEvent";
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('PigletStatusEvent','POST_NULL_PIGINGO_DEL_COUNT', 'Total Number of rows in PigletStatusEvent table after deleting null pigInfo Ids: ' || pigletstatuseventcount, '' || pigletstatuseventcount, CURRENT_TIMESTAMP);
	--RAISE notice 'After logging total number of rows after deleting pigs ids: %', CURRENT_TIMESTAMP;

--Create the FK references for pigId and log + delete rows which cannot be linked back to pigInfo Table
	UPDATE pigtraxstaging."PigletStatusEvent" AS fe set "id_PigInfo"= pi.id FROM pigtraxstaging."PigInfo" AS pi 
	WHERE fe."pigId" = pi."pigId";
	SELECT string_agg("srcLineNumber", ', ') into nullpiginfoidfk FROM pigtraxstaging."PigletStatusEvent" WHERE "id_PigInfo" IS NULL;
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('PigletStatusEvent', 'NULL_PIGINFO_ID_FK', 'For following line numbers, the pigId cannot be linked back to pigInfo table. These will be deleted: ' , nullpiginfoidfk, CURRENT_TIMESTAMP);
	DELETE FROM pigtraxstaging."PigletStatusEvent" WHERE "id_PigInfo" IS NULL;
	--RAISE notice 'After updating FKs for pigs ids: %', CURRENT_TIMESTAMP;

--Log the number of rows after delete
	SELECT COUNT(1) INTO pigletstatuseventcount FROM pigtraxstaging."PigletStatusEvent";
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('PigletStatusEvent','POST_NULL_ID_PIGINFO_FK_DEL_COUNT', 'Total Number of rows after deleting rows for which a pigId cannot be traced back to pigInfo table ' || pigletstatuseventcount, '' || pigletstatuseventcount, CURRENT_TIMESTAMP);
	--RAISE notice 'After deleting rows with null FK pigIds: %', CURRENT_TIMESTAMP;

--Do the date operations
	UPDATE pigtraxstaging."PigletStatusEvent" set "eventDate"=to_date("eventDateString",'DD/MM/YYYY') WHERE "eventDateString" is NOT NULL;
	--RAISE notice 'After updating dateStr to date: %', CURRENT_TIMESTAMP;

	SELECT string_agg("srcLineNumber", ', ') INTO nulleventdate FROM pigtraxstaging."PigletStatusEvent" where "eventDate" IS NULL;
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('PigletStatusEvent', 'NULL_EVENT_DATE', 'Following line numbers have null event date. They will be deleted' , nulleventdate, CURRENT_TIMESTAMP);

	DELETE FROM pigtraxstaging."PigletStatusEvent" WHERE "eventDate" IS NULL;
	--RAISE notice 'After deleting rows with null FK date: %', CURRENT_TIMESTAMP;

-- Log the number of rows after delete
	SELECT COUNT(1) INTO pigletstatuseventcount FROM pigtraxstaging."PigletStatusEvent";
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('PigletStatusEvent','POST_NULL_DATE_DEL_COUNT', 'Total Number of rows which have null event date: ' || pigletstatuseventcount, '' || pigletstatuseventcount, CURRENT_TIMESTAMP);
	--RAISE notice 'Final count log: %', CURRENT_TIMESTAMP;
			 
	RETURN 0;
END;   
$$
LANGUAGE plpgsql VOLATILE NOT LEAKPROOF;
ALTER FUNCTION pigtraxstaging."PigletStatusEventDataValidation"()
OWNER TO pitraxadmin;

--WE do the data copy now. We introduce line nums in original data and split the pigs ids to remove tattoos
--\COPY pigtraxstaging."PigletStatusEvent" ("srcLineNumber","pigId","eventDateString","eventNameString","numberOfPigs","weightInKgs",reason,remarks) from 'C:\Users\Kshitij\Documents\PigTrax\DB-BulkMigration\CSV-1\PigletStatusEvent.csv' delimiter '|' CSV NULL AS '';
--select pigtraxstaging."PigletStatusEventDataValidation"();
--select * from  pigtraxstaging."StagingLog";
--select * from pigtraxstaging."PigletStatusEvent" limit 100;
---Test SQL END


--RemovalEvent

-- object: pigtraxstaging."RemovalEvent" | type: TABLE --
DROP TABLE IF EXISTS pigtraxstaging."RemovalEvent" CASCADE;
CREATE TABLE pigtraxstaging."RemovalEvent"(
	id serial NOT NULL,
	"srcLineNumber" varchar(20),
	"pigId" varchar(30),
	"eventDateString" varchar(30),
	"eventDate" date,
	"eventNameString" varchar(30),
	"sexTypeString" varchar(15),
	type smallint,
	reason1 varchar(30),
	reason2 varchar(30),
	"changeId" varchar(30),
	remarks varchar(255),
	weight numeric(10,2),
	"id_PigInfo" integer,
	CONSTRAINT "REMOVALEVENT_PK" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtraxstaging."RemovalEvent" OWNER TO pigtraxadmin;
-- ddl-end --

-- object: "PigInfo_fk" | type: CONSTRAINT --
ALTER TABLE pigtraxstaging."RemovalEvent" DROP CONSTRAINT IF EXISTS "RemovalEvent_fk" CASCADE;
ALTER TABLE pigtraxstaging."RemovalEvent" ADD CONSTRAINT "RemovalEvent_fk" FOREIGN KEY ("id_PigInfo")
REFERENCES pigtraxstaging."PigInfo" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;

-- object: "REMOVALEVENT_PI_IN" | type: INDEX --
DROP INDEX IF EXISTS pigtraxstaging."REMOVALEVENT_PI_IN" CASCADE;
CREATE INDEX "REMOVALEVENT_PI_IN" ON pigtraxstaging."RemovalEvent"
	USING btree
	(
	  "pigId" ASC NULLS LAST
	);
-- ddl-end --

-- object: "FARROWEVENT_ED_IN" | type: INDEX --
DROP INDEX IF EXISTS pigtraxstaging."REMOVALEVENT_ED_IN" CASCADE;
CREATE INDEX "REMOVALEVENT_ED_IN" ON pigtraxstaging."RemovalEvent"
	USING btree
	(
	  "eventDate" ASC NULLS LAST
	);
-- ddl-end --

DROP INDEX IF EXISTS pigtraxstaging."REMOVALEVENT_PIFK_IN" CASCADE;
CREATE INDEX "REMOVALEVENT_PIFK_IN" ON pigtraxstaging."RemovalEvent"
	USING btree
	(
	  "id_PigInfo" ASC NULLS LAST
	);

CREATE OR REPLACE FUNCTION pigtraxstaging."RemovalEventDataValidation"() RETURNS int AS
$$
DECLARE
	removaleventcount integer = 0;
	nullpiginfoid varchar(10000) = null;
	nullpiginfoidfk varchar(10000) = null;
	nulleventdate varchar(10000) = null;
BEGIN
--Log initial count
	--RAISE notice 'Before Log Initial Count: %', CURRENT_TIMESTAMP;
	SELECT COUNT(1) INTO removaleventcount FROM pigtraxstaging."RemovalEvent";
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('RemovalEvent','INIT_COUNT', 'Total Number of rows copied in RemovalEvent Table: ' || removaleventcount, '' || removaleventcount, CURRENT_TIMESTAMP);
	--RAISE notice 'After Log Initial Count: %', CURRENT_TIMESTAMP;
--Delete rows which have null pigIds in the source sheet
	
	SELECT string_agg("srcLineNumber", ', ') into nullpiginfoid FROM pigtraxstaging."RemovalEvent" WHERE "pigId" IS NULL;
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('RemovalEvent', 'NULL_PIGINFO_ID', 'Following line numbers in origin sheet have null piginfo ids. These will be deleted: ' , nullpiginfoid, CURRENT_TIMESTAMP);
	DELETE FROM pigtraxstaging."RemovalEvent" WHERE "pigId" IS NULL;
	--RAISE notice 'After deleting rows with null pigIds: %', CURRENT_TIMESTAMP;
--Log total number of rows left after deleting the rows with null piginfo Ids
	SELECT COUNT(1) INTO removaleventcount FROM pigtraxstaging."RemovalEvent";
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('RemovalEvent','POST_NULL_PIGINGO_DEL_COUNT', 'Total Number of rows in RemovalEvent table after deleting null pigInfo Ids: ' || removaleventcount, '' || removaleventcount, CURRENT_TIMESTAMP);
	--RAISE notice 'After logging total number of rows after deleting pigs ids: %', CURRENT_TIMESTAMP;

--Create the FK references for pigId and log + delete rows which cannot be linked back to pigInfo Table
	UPDATE pigtraxstaging."RemovalEvent" AS fe set "id_PigInfo"= pi.id FROM pigtraxstaging."PigInfo" AS pi 
	WHERE fe."pigId" = pi."pigId";
	SELECT string_agg("srcLineNumber", ', ') into nullpiginfoidfk FROM pigtraxstaging."RemovalEvent" WHERE "id_PigInfo" IS NULL;
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('RemovalEvent', 'NULL_PIGINFO_ID_FK', 'For following line numbers, the pigId cannot be linked back to pigInfo table. These will be deleted: ' , nullpiginfoidfk, CURRENT_TIMESTAMP);
	DELETE FROM pigtraxstaging."RemovalEvent" WHERE "id_PigInfo" IS NULL;
	--RAISE notice 'After updating FKs for pigs ids: %', CURRENT_TIMESTAMP;

--Log the number of rows after delete
	SELECT COUNT(1) INTO removaleventcount FROM pigtraxstaging."RemovalEvent";
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('RemovalEvent','POST_NULL_ID_PIGINFO_FK_DEL_COUNT', 'Total Number of rows after deleting rows for which a pigId cannot be traced back to pigInfo table ' || removaleventcount, '' || removaleventcount, CURRENT_TIMESTAMP);
	--RAISE notice 'After deleting rows with null FK pigIds: %', CURRENT_TIMESTAMP;

--Do the date operations
	UPDATE pigtraxstaging."RemovalEvent" set "eventDate"=to_date("eventDateString",'DD/MM/YYYY') WHERE "eventDateString" is NOT NULL;
	--RAISE notice 'After updating dateStr to date: %', CURRENT_TIMESTAMP;

	SELECT string_agg("srcLineNumber", ', ') INTO nulleventdate FROM pigtraxstaging."RemovalEvent" where "eventDate" IS NULL;
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('RemovalEvent', 'NULL_EVENT_DATE', 'Following line numbers have null event date. They will be deleted' , nulleventdate, CURRENT_TIMESTAMP);

	DELETE FROM pigtraxstaging."RemovalEvent" WHERE "eventDate" IS NULL;
	--RAISE notice 'After deleting rows with null FK date: %', CURRENT_TIMESTAMP;

-- Log the number of rows after delete
	SELECT COUNT(1) INTO removaleventcount FROM pigtraxstaging."RemovalEvent";
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('RemovalEvent','POST_NULL_DATE_DEL_COUNT', 'Total Number of rows which have null event date: ' || removaleventcount, '' || removaleventcount, CURRENT_TIMESTAMP);
	--RAISE notice 'Final count log: %', CURRENT_TIMESTAMP;
			 
	RETURN 0;
END;   
$$
LANGUAGE plpgsql VOLATILE NOT LEAKPROOF;
ALTER FUNCTION pigtraxstaging."RemovalEventDataValidation"()
OWNER TO pitraxadmin;

--WE do the data copy now. We introduce line nums in original data and split the pigs ids to remove tattoos

--\COPY pigtraxstaging."RemovalEvent" ("srcLineNumber","pigId","eventDateString","eventNameString","sexTypeString",type,reason1,reason2, "changeId", remarks, weight) from 'C:\Users\Kshitij\Documents\PigTrax\DB-BulkMigration\CSV-1\RemovalEvent.csv' delimiter '|' CSV NULL AS '';select pigtraxstaging."RemovalEventDataValidation"();
--select * from  pigtraxstaging."StagingLog";
--select * from pigtraxstaging."RemovalEvent" limit 100;
---Test SQL END


--SalesEvent
DROP TABLE IF EXISTS pigtraxstaging."SalesEvent" CASCADE;
CREATE TABLE pigtraxstaging."SalesEvent"(
	id serial NOT NULL,
	"srcLineNumber" varchar(20),
	"pigId" varchar(30),
	"eventDateString" varchar(30),
	"eventDate" date,
	"eventNameString" varchar(30),
	"salesType" varchar(5),
	revenue varchar(15),
	weight numeric(20,2),
	reason varchar(50),
	client varchar(150),
	destination varchar(150),
	ticket varchar(100),
	driver varchar(150),
	truck varchar(50),
	invoice varchar(50),
	"exitOrder" varchar(50),
	"acctInvoice" varchar(50),
	"totalWeight" numeric(10,2),
	"netWeight" numeric(10,2),
	remarks varchar(255),
	"id_PigInfo" integer,
	CONSTRAINT "SALESEVENT_PK" PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE pigtraxstaging."SalesEvent" OWNER TO pigtraxadmin;
-- ddl-end --

-- object: "PigInfo_fk" | type: CONSTRAINT --
ALTER TABLE pigtraxstaging."SalesEvent" DROP CONSTRAINT IF EXISTS "SalesEvent_fk" CASCADE;
ALTER TABLE pigtraxstaging."SalesEvent" ADD CONSTRAINT "SalesEvent_fk" FOREIGN KEY ("id_PigInfo")
REFERENCES pigtraxstaging."PigInfo" (id) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;

-- object: "SALESEVENT_PI_IN" | type: INDEX --
DROP INDEX IF EXISTS pigtraxstaging."SALESEVENT_PI_IN" CASCADE;
CREATE INDEX "SALESEVENT_PI_IN" ON pigtraxstaging."SalesEvent"
	USING btree
	(
	  "pigId" ASC NULLS LAST
	);
-- ddl-end --

-- object: "SALESEVENT_ED_IN" | type: INDEX --
DROP INDEX IF EXISTS pigtraxstaging."SALESEVENT_ED_IN" CASCADE;
CREATE INDEX "SALESEVENT_ED_IN" ON pigtraxstaging."SalesEvent"
	USING btree
	(
	  "eventDate" ASC NULLS LAST
	);
-- ddl-end --

DROP INDEX IF EXISTS pigtraxstaging."SALESEVENT_PIFK_IN" CASCADE;
CREATE INDEX "SALESEVENT_PIFK_IN" ON pigtraxstaging."SalesEvent"
	USING btree
	(
	  "id_PigInfo" ASC NULLS LAST
	);

CREATE OR REPLACE FUNCTION pigtraxstaging."SalesEventDataValidation"() RETURNS int AS
$$
DECLARE
	saleseventcount integer = 0;
	nullpiginfoid varchar(10000) = null;
	nullpiginfoidfk varchar(10000) = null;
	nulleventdate varchar(10000) = null;
BEGIN
--Log initial count
	--RAISE notice 'Before Log Initial Count: %', CURRENT_TIMESTAMP;
	SELECT COUNT(1) INTO saleseventcount FROM pigtraxstaging."SalesEvent";
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('SalesEvent','INIT_COUNT', 'Total Number of rows copied in SalesEvent Table: ' || saleseventcount, '' || saleseventcount, CURRENT_TIMESTAMP);
	--RAISE notice 'After Log Initial Count: %', CURRENT_TIMESTAMP;
--Delete rows which have null pigIds in the source sheet
	
	SELECT string_agg("srcLineNumber", ', ') into nullpiginfoid FROM pigtraxstaging."SalesEvent" WHERE "pigId" IS NULL;
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('SalesEvent', 'NULL_PIGINFO_ID', 'Following line numbers in origin sheet have null piginfo ids. These will be deleted: ' , nullpiginfoid, CURRENT_TIMESTAMP);
	DELETE FROM pigtraxstaging."SalesEvent" WHERE "pigId" IS NULL;
	--RAISE notice 'After deleting rows with null pigIds: %', CURRENT_TIMESTAMP;
--Log total number of rows left after deleting the rows with null piginfo Ids
	SELECT COUNT(1) INTO saleseventcount FROM pigtraxstaging."SalesEvent";
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('SalesEvent','POST_NULL_PIGINGO_DEL_COUNT', 'Total Number of rows in SalesEvent table after deleting null pigInfo Ids: ' || saleseventcount, '' || saleseventcount, CURRENT_TIMESTAMP);
	--RAISE notice 'After logging total number of rows after deleting pigs ids: %', CURRENT_TIMESTAMP;

--Create the FK references for pigId and log + delete rows which cannot be linked back to pigInfo Table
	UPDATE pigtraxstaging."SalesEvent" AS fe set "id_PigInfo"= pi.id FROM pigtraxstaging."PigInfo" AS pi 
	WHERE fe."pigId" = pi."pigId";
	SELECT string_agg("srcLineNumber", ', ') into nullpiginfoidfk FROM pigtraxstaging."SalesEvent" WHERE "id_PigInfo" IS NULL;
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('SalesEvent', 'NULL_PIGINFO_ID_FK', 'For following line numbers, the pigId cannot be linked back to pigInfo table. These will be deleted: ' , nullpiginfoidfk, CURRENT_TIMESTAMP);
	DELETE FROM pigtraxstaging."SalesEvent" WHERE "id_PigInfo" IS NULL;
	--RAISE notice 'After updating FKs for pigs ids: %', CURRENT_TIMESTAMP;

--Log the number of rows after delete
	SELECT COUNT(1) INTO saleseventcount FROM pigtraxstaging."SalesEvent";
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('SalesEvent','POST_NULL_ID_PIGINFO_FK_DEL_COUNT', 'Total Number of rows after deleting rows for which a pigId cannot be traced back to pigInfo table ' || saleseventcount, '' || saleseventcount, CURRENT_TIMESTAMP);
	--RAISE notice 'After deleting rows with null FK pigIds: %', CURRENT_TIMESTAMP;

--Do the date operations
	UPDATE pigtraxstaging."SalesEvent" set "eventDate"=to_date("eventDateString",'DD/MM/YYYY') WHERE "eventDateString" is NOT NULL;
	--RAISE notice 'After updating dateStr to date: %', CURRENT_TIMESTAMP;

	SELECT string_agg("srcLineNumber", ', ') INTO nulleventdate FROM pigtraxstaging."SalesEvent" where "eventDate" IS NULL;
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('SalesEvent', 'NULL_EVENT_DATE', 'Following line numbers have null event date. They will be deleted' , nulleventdate, CURRENT_TIMESTAMP);

	DELETE FROM pigtraxstaging."SalesEvent" WHERE "eventDate" IS NULL;
	--RAISE notice 'After deleting rows with null FK date: %', CURRENT_TIMESTAMP;

-- Log the number of rows after delete
	SELECT COUNT(1) INTO saleseventcount FROM pigtraxstaging."SalesEvent";
	INSERT INTO pigtraxstaging."StagingLog" ("eventName", "logCode", "logMessage", "logData", "logDateTime") VALUES ('SalesEvent','POST_NULL_DATE_DEL_COUNT', 'Total Number of rows which have null event date: ' || saleseventcount, '' || saleseventcount, CURRENT_TIMESTAMP);
	--RAISE notice 'Final count log: %', CURRENT_TIMESTAMP;
			 
	RETURN 0;
END;   
$$
LANGUAGE plpgsql VOLATILE NOT LEAKPROOF;
ALTER FUNCTION pigtraxstaging."SalesEventDataValidation"()
OWNER TO pitraxadmin;

--WE do the data copy now. We introduce line nums in original data and split the pigs ids to remove tattoos
--\COPY pigtraxstaging."PigletStatusEvent" ("srcLineNumber","pigId","eventDateString","eventNameString","numberOfPigs","weightInKgs",reason,remarks) from 'C:\Users\Kshitij\Documents\PigTrax\DB-BulkMigration\CSV-1\PigletStatusEvent.csv' delimiter '|' CSV NULL AS '';
--select pigtraxstaging."SalesEventDataValidation"();
--select * from  pigtraxstaging."StagingLog";
--select * from pigtraxstaging."SalesEvent" order by id asc;
---Test SQL END
