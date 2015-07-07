
--Roles
INSERT INTO pigtraxrefdata."RoleType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (1,'PigTrax SuperAdmin User',current_timestamp,'pigtraxadmin');
INSERT INTO pigtraxrefdata."RoleType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (2,'PigTrax Data Config Mgr',current_timestamp,'pigtraxadmin');
INSERT INTO pigtraxrefdata."RoleType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (3,'PigFarm SuperAdmin User',current_timestamp,'pigtraxadmin');
INSERT INTO pigtraxrefdata."RoleType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (4,'PigFarm Data Config Mgr',current_timestamp,'pigtraxadmin');

INSERT INTO pigtraxrefdata."RoleTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RoleType") VALUES ('PT SuperAdmin','en',current_timestamp,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."RoleTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RoleType") VALUES ('PT Data Config Mgr','en',current_timestamp,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."RoleTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RoleType") VALUES ('Farm SuperAdmin','en',current_timestamp,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."RoleTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RoleType") VALUES ('Farm Data Config Mgr','en',current_timestamp,'pigtraxadmin',4);

INSERT INTO pigtraxrefdata."RoleTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RoleType") VALUES ('PT SuperAdmin_ES','es',current_timestamp,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."RoleTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RoleType") VALUES ('PT Data Config Mgr_ES','es',current_timestamp,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."RoleTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RoleType") VALUES ('Farm SuperAdmin_ES','es',current_timestamp,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."RoleTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RoleType") VALUES ('Farm Data Config Mgr_ES','es',current_timestamp,'pigtraxadmin',4);

INSERT INTO pigtraxrefdata."RoleTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RoleType") VALUES ('PT SuperAdmin_PR','pr',current_timestamp,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."RoleTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RoleType") VALUES ('PT Data Config Mgr_PR','pr',current_timestamp,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."RoleTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RoleType") VALUES ('Farm SuperAdmin_PR','pr',current_timestamp,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."RoleTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RoleType") VALUES ('Farm Data Config Mgr_PR','pr',current_timestamp,'pigtraxadmin',4);


INSERT INTO pigtraxrefdata."SexType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (1,'Male',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."SexType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (2,'Female',CURRENT_TIMESTAMP,'pigtraxadmin');

INSERT INTO pigtraxrefdata."SexTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_SexType") VALUES ('Male','en',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."SexTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_SexType") VALUES ('Female','en',CURRENT_TIMESTAMP,'pigtraxadmin',2);

INSERT INTO pigtraxrefdata."SexTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_SexType") VALUES ('Male_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."SexTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_SexType") VALUES ('Female_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',2);

INSERT INTO pigtraxrefdata."SexTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_SexType") VALUES ('Male_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."SexTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_SexType") VALUES ('Female_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',2);
