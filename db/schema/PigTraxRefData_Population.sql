
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


--Countries
INSERT INTO pigtraxrefdata."Country" (name, "lastUpdated", "userUpdated") VALUES ('Brazil', CURRENT_TIMESTAMP, 'pigtraxadmin');
INSERT INTO pigtraxrefdata."Country" (name, "lastUpdated", "userUpdated") VALUES ('Spain', CURRENT_TIMESTAMP, 'pigtraxadmin');
INSERT INTO pigtraxrefdata."Country" (name, "lastUpdated", "userUpdated") VALUES ('USA', CURRENT_TIMESTAMP, 'pigtraxadmin');

--City
--Brazil Cities
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Alegre', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Aracaju', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Belem', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Belo_Horizonte', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Boa_Vista', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Botucatu', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Brasilandia', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Campinas', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Campo Grande', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Canoas', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Cuiaba', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Curitiba', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Diadema', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Florianopolis', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Fortaleza', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Goiania', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Guarulhos', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Joao Pessoa', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Juiz de Fora', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Jundiai', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Londrina', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Macapa', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Maceio', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Manaus', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Natal', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Niteroi', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Nova Hamburgo', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Palmas', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Paranagua', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Pelotas', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Porto Alegre', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Porto Velho', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Recife', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Ribeirao Preto', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Rio de Janeiro', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Salvador', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Santo Andre', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Santos', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Sao Bernardo do Campo', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Sao Jose dos Campos', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Sao Luiz', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Sao Paulo', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Teresina', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Vitoria', CURRENT_TIMESTAMP, 'pigtraxadmin', 1);

--Spain Cities
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Madrid', CURRENT_TIMESTAMP, 'pigtraxadmin', 2);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Barcelona', CURRENT_TIMESTAMP, 'pigtraxadmin', 2);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Alicante', CURRENT_TIMESTAMP, 'pigtraxadmin', 2);
-- US cities
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Dallas', CURRENT_TIMESTAMP, 'pigtraxadmin', 3);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('Los Angeles', CURRENT_TIMESTAMP, 'pigtraxadmin', 3);
INSERT INTO pigtraxrefdata."City" (name, "lastUpdated", "userUpdated", "id_Country") VALUES ('San Fransisco', CURRENT_TIMESTAMP, 'pigtraxadmin', 3);

--BarnType/PhaseType
INSERT INTO pigtraxrefdata."PhaseType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (1,'Nursery', CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."PhaseType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (2,'Finishing',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."PhaseType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (3,'Wean to finish',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."PhaseType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (4,'GDU',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."PhaseType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (5,'Farrow',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."PhaseType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (6,'Gestation',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."PhaseType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (7,'Unknown',CURRENT_TIMESTAMP,'pigtraxadmin');

INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Nursery','en',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Finishing','en',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Wean to finish','en',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('GDU','en',CURRENT_TIMESTAMP,'pigtraxadmin',4);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Farrow','en',CURRENT_TIMESTAMP,'pigtraxadmin',5);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Gestation','en',CURRENT_TIMESTAMP,'pigtraxadmin',6);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Unknown','en',CURRENT_TIMESTAMP,'pigtraxadmin',7);

INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Nursery_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Finishing_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Wean to finish_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('GDU_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',4);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Farrow_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',5);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Gestation_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',6);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Unknown_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',7);

INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Nursery_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Finishing_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Wean to finish_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('GDU_ES_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',4);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Farrow_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',5);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Gestation_Pr','pr',CURRENT_TIMESTAMP,'pigtraxadmin',6);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Unknown_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',7);


--VentilatoinType
INSERT INTO pigtraxrefdata."VentilationType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (1,'Power', CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."VentilationType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (2,'Natural',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."VentilationType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (3,'Tunnel',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."VentilationType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (4,'Unknown',CURRENT_TIMESTAMP,'pigtraxadmin');

INSERT INTO pigtraxrefdata."VentilationTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_VentilationType") VALUES ('Power','en',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."VentilationTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_VentilationType") VALUES ('Natural','en',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."VentilationTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_VentilationType") VALUES ('Tunnel','en',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."VentilationTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_VentilationType") VALUES ('Unknown','en',CURRENT_TIMESTAMP,'pigtraxadmin',4);

INSERT INTO pigtraxrefdata."VentilationTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_VentilationType") VALUES ('Power_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."VentilationTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_VentilationType") VALUES ('Natural_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."VentilationTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_VentilationType") VALUES ('Tunnel_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."VentilationTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_VentilationType") VALUES ('Unknown_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',4);

INSERT INTO pigtraxrefdata."VentilationTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_VentilationType") VALUES ('Power_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."VentilationTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_VentilationType") VALUES ('Natural_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."VentilationTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_VentilationType") VALUES ('Tunnel_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."VentilationTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_VentilationType") VALUES ('Unknown_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',4);

-----------------------Breeding service Type
INSERT INTO pigtraxrefdata."BreedingServiceType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (1,'AI', CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."BreedingServiceType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (2,'Natural',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."BreedingServiceType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (3,'Unknown',CURRENT_TIMESTAMP,'pigtraxadmin');

INSERT INTO pigtraxrefdata."BreedingServiceTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_BreedingServiceType") VALUES ('AI','en',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."BreedingServiceTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_BreedingServiceType") VALUES ('Natural','en',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."BreedingServiceTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_BreedingServiceType") VALUES ('Unknown','en',CURRENT_TIMESTAMP,'pigtraxadmin',3);

INSERT INTO pigtraxrefdata."BreedingServiceTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_BreedingServiceType") VALUES ('AI_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."BreedingServiceTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_BreedingServiceType") VALUES ('Natural_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."BreedingServiceTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_BreedingServiceType") VALUES ('Unknown_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',3);

INSERT INTO pigtraxrefdata."BreedingServiceTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_BreedingServiceType") VALUES ('AI_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."BreedingServiceTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_BreedingServiceType") VALUES ('Natural_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."BreedingServiceTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_BreedingServiceType") VALUES ('Unknown_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',3);

------------------ PregnancyExamResultType

INSERT INTO pigtraxrefdata."PregnancyExamResultType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (1,'Positive', CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."PregnancyExamResultType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (2,'Negative',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."PregnancyExamResultType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (3,'Inconclusive',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."PregnancyExamResultType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (4,'Unknown',CURRENT_TIMESTAMP,'pigtraxadmin');

INSERT INTO pigtraxrefdata."PregnancyExamResultTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PregnancyExamResultType") VALUES ('Positive','en',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."PregnancyExamResultTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PregnancyExamResultType") VALUES ('Negative','en',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."PregnancyExamResultTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PregnancyExamResultType") VALUES ('Inconclusive','en',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."PregnancyExamResultTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PregnancyExamResultType") VALUES ('Unknown','en',CURRENT_TIMESTAMP,'pigtraxadmin',4);

INSERT INTO pigtraxrefdata."PregnancyExamResultTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PregnancyExamResultType") VALUES ('Positive_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."PregnancyExamResultTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PregnancyExamResultType") VALUES ('Negative_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."PregnancyExamResultTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PregnancyExamResultType") VALUES ('Inconclusive_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."PregnancyExamResultTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PregnancyExamResultType") VALUES ('Unknown_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',4);

INSERT INTO pigtraxrefdata."PregnancyExamResultTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PregnancyExamResultType") VALUES ('Positive_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."PregnancyExamResultTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PregnancyExamResultType") VALUES ('Negative_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."PregnancyExamResultTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PregnancyExamResultType") VALUES ('Inconclusive_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."PregnancyExamResultTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PregnancyExamResultType") VALUES ('Unknown_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',4);

--------------------PregnancyEventType
INSERT INTO pigtraxrefdata."PregnancyEventType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (1,'Pregnancy Exam', CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."PregnancyEventType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (2,'Abortion',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."PregnancyEventType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (3,'Not In Pig',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."PregnancyEventType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (4,'Unknown',CURRENT_TIMESTAMP,'pigtraxadmin');

INSERT INTO pigtraxrefdata."PregnancyEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PregnancyEventType") VALUES ('Pregnancy Exam','en',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."PregnancyEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PregnancyEventType") VALUES ('Abortion','en',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."PregnancyEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PregnancyEventType") VALUES ('Non in Pig','en',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."PregnancyEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PregnancyEventType") VALUES ('Unknown','en',CURRENT_TIMESTAMP,'pigtraxadmin',4);

INSERT INTO pigtraxrefdata."PregnancyEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PregnancyEventType") VALUES ('Pregnancy Exam_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."PregnancyEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PregnancyEventType") VALUES ('Abortion_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."PregnancyEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PregnancyEventType") VALUES ('Non in Pig_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."PregnancyEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PregnancyEventType") VALUES ('Unknown_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',4);

INSERT INTO pigtraxrefdata."PregnancyEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PregnancyEventType") VALUES ('Pregnancy Exam_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."PregnancyEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PregnancyEventType") VALUES ('Abortion_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."PregnancyEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PregnancyEventType") VALUES ('Non in Pig_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."PregnancyEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PregnancyEventType") VALUES ('Unknown_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',4);
------SiloType
INSERT INTO pigtraxrefdata."SiloType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (1,'Single', CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."SiloType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (2,'Manual',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."SiloType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (3,'Double',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."SiloType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (4,'Unknown',CURRENT_TIMESTAMP,'pigtraxadmin');

INSERT INTO pigtraxrefdata."SiloTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_SiloType") VALUES ('Single','en',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."SiloTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_SiloType") VALUES ('Manual','en',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."SiloTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_SiloType") VALUES ('Double','en',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."SiloTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_SiloType") VALUES ('Unknown','en',CURRENT_TIMESTAMP,'pigtraxadmin',4);

INSERT INTO pigtraxrefdata."SiloTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_SiloType") VALUES ('Single_es','es',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."SiloTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_SiloType") VALUES ('Manual_es','es',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."SiloTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_SiloType") VALUES ('Double_es','es',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."SiloTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_SiloType") VALUES ('Unknown_es','es',CURRENT_TIMESTAMP,'pigtraxadmin',4);


INSERT INTO pigtraxrefdata."SiloTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_SiloType") VALUES ('Single_pr','pr',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."SiloTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_SiloType") VALUES ('Manual_pr','pr',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."SiloTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_SiloType") VALUES ('Double_pr','pr',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."SiloTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_SiloType") VALUES ('Unknown_pr','pr',CURRENT_TIMESTAMP,'pigtraxadmin',4);


-----------------PhaseOfPRoductionType

INSERT INTO pigtraxrefdata."PhaseOfProductionType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (1,'Nursery', CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."PhaseOfProductionType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (2,'Finishing',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."PhaseOfProductionType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (3,'Wean To Finish',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."PhaseOfProductionType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (4,'GDU',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."PhaseOfProductionType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (5,'Extra Flow',CURRENT_TIMESTAMP,'pigtraxadmin');

INSERT INTO pigtraxrefdata."PhaseOfProductionTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseOfProductionType") VALUES ('Nursery','en',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."PhaseOfProductionTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseOfProductionType") VALUES ('Finishing','en',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."PhaseOfProductionTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseOfProductionType") VALUES ('Wean To Finish','en',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."PhaseOfProductionTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseOfProductionType") VALUES ('GDU','en',CURRENT_TIMESTAMP,'pigtraxadmin',4);
INSERT INTO pigtraxrefdata."PhaseOfProductionTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseOfProductionType") VALUES ('Extra Flow','en',CURRENT_TIMESTAMP,'pigtraxadmin',5);

INSERT INTO pigtraxrefdata."PhaseOfProductionTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseOfProductionType") VALUES ('Nursery_es','es',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."PhaseOfProductionTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseOfProductionType") VALUES ('Finishing_es','es',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."PhaseOfProductionTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseOfProductionType") VALUES ('Wean To Finish_es','es',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."PhaseOfProductionTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseOfProductionType") VALUES ('GDU_es','es',CURRENT_TIMESTAMP,'pigtraxadmin',4);
INSERT INTO pigtraxrefdata."PhaseOfProductionTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseOfProductionType") VALUES ('Extra Flow_es','es',CURRENT_TIMESTAMP,'pigtraxadmin',5);

INSERT INTO pigtraxrefdata."PhaseOfProductionTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseOfProductionType") VALUES ('Nursery_pr','pr',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."PhaseOfProductionTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseOfProductionType") VALUES ('Finishing_pr','pr',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."PhaseOfProductionTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseOfProductionType") VALUES ('Wean To Finish_pr','pr',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."PhaseOfProductionTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseOfProductionType") VALUES ('GDU_pr','pr',CURRENT_TIMESTAMP,'pigtraxadmin',4);
INSERT INTO pigtraxrefdata."PhaseOfProductionTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseOfProductionType") VALUES ('Extra Flow_pr','pr',CURRENT_TIMESTAMP,'pigtraxadmin',5);

------PigletstatuevebntType
INSERT INTO pigtraxrefdata."PigletStatusEventType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (1,'Foster In', CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."PigletStatusEventType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (2,'Foster Out', CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."PigletStatusEventType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (3,'Wean',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."PigletStatusEventType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (4,'Pig Death',CURRENT_TIMESTAMP,'pigtraxadmin');


INSERT INTO pigtraxrefdata."PigletStatusEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PigletStatusEventType") VALUES ('Foster In','en',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."PigletStatusEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PigletStatusEventType") VALUES ('Foster Out','en',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."PigletStatusEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PigletStatusEventType") VALUES ('Wean','en',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."PigletStatusEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PigletStatusEventType") VALUES ('Pig Death','en',CURRENT_TIMESTAMP,'pigtraxadmin',4);

INSERT INTO pigtraxrefdata."PigletStatusEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PigletStatusEventType") VALUES ('Foster In_es','es',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."PigletStatusEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PigletStatusEventType") VALUES ('Foster Out_es','es',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."PigletStatusEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PigletStatusEventType") VALUES ('Wean_es','es',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."PigletStatusEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PigletStatusEventType") VALUES ('Pig Death_es','es',CURRENT_TIMESTAMP,'pigtraxadmin',4);

INSERT INTO pigtraxrefdata."PigletStatusEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PigletStatusEventType") VALUES ('Foster In_pr','pr',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."PigletStatusEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PigletStatusEventType") VALUES ('Foster Out_pr','pr',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."PigletStatusEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PigletStatusEventType") VALUES ('Wean_pr','pr',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."PigletStatusEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PigletStatusEventType") VALUES ('Pig Death_pr','pr',CURRENT_TIMESTAMP,'pigtraxadmin',4);

------PigletstatuevebntType
INSERT INTO pigtraxrefdata."FeedEventType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (1,'Feed Assigned', CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."FeedEventType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (2,'Feed Transferred', CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."FeedEventType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (4,'Feed Destroyed',CURRENT_TIMESTAMP,'pigtraxadmin');


INSERT INTO pigtraxrefdata."FeedEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_FeedEventType") VALUES ('Feed Assigned','en',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."FeedEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_FeedEventType") VALUES ('Feed Destroyed','en',CURRENT_TIMESTAMP,'pigtraxadmin',2);

INSERT INTO pigtraxrefdata."FeedEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_FeedEventType") VALUES ('Feed Assigned_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."FeedEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_FeedEventType") VALUES ('Feed Destroyed_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',2);


INSERT INTO pigtraxrefdata."FeedEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_FeedEventType") VALUES ('Feed Assigned_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."FeedEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_FeedEventType") VALUES ('Feed Destroyed_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',2);

---------
INSERT INTO pigtraxrefdata."RemovalType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (1,'Death', CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."RemovalType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (2,'Euthanized',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."RemovalType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (3,'Transferred',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."RemovalType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (4,'Sales',CURRENT_TIMESTAMP,'pigtraxadmin');

INSERT INTO pigtraxrefdata."RemovalEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RemovalType") VALUES ('Death','en',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."RemovalEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RemovalType") VALUES ('Euthanized','en',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."RemovalEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RemovalType") VALUES ('Transferred','en',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."RemovalEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RemovalType") VALUES ('Sales','en',CURRENT_TIMESTAMP,'pigtraxadmin',4);

INSERT INTO pigtraxrefdata."RemovalEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RemovalType") VALUES ('Death_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."RemovalEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RemovalType") VALUES ('Euthanized_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."RemovalEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RemovalType") VALUES ('Transferred_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."RemovalEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RemovalType") VALUES ('Sales_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',4);

INSERT INTO pigtraxrefdata."RemovalEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RemovalType") VALUES ('Death_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."RemovalEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RemovalType") VALUES ('Euthanized_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."RemovalEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RemovalType") VALUES ('Transferred_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."RemovalEventTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_RemovalType") VALUES ('Sales_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',4);

----------


---------
INSERT INTO pigtraxrefdata."GfunctionType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (1,'GGP', CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."GfunctionType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (2,'GP',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."GfunctionType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (3,'Parent',CURRENT_TIMESTAMP,'pigtraxadmin');

INSERT INTO pigtraxrefdata."GfunctionTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_GfunctionType") VALUES ('GGP','en',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."GfunctionTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_GfunctionType") VALUES ('GP','en',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."GfunctionTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_GfunctionType") VALUES ('Parent','en',CURRENT_TIMESTAMP,'pigtraxadmin',3);


INSERT INTO pigtraxrefdata."GfunctionTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_GfunctionType") VALUES ('GGP_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."GfunctionTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_GfunctionType") VALUES ('GP_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."GfunctionTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_GfunctionType") VALUES ('Parent_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',3);

INSERT INTO pigtraxrefdata."GfunctionTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_GfunctionType") VALUES ('GGP_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."GfunctionTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_GfunctionType") VALUES ('GP_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."GfunctionTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_GfunctionType") VALUES ('Parent_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',3);

----------

INSERT INTO pigtraxrefdata."MortalityReasonType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (1,'Reason1', CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."MortalityReasonType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (2,'Reason2',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."MortalityReasonType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (3,'Reason3',CURRENT_TIMESTAMP,'pigtraxadmin');

INSERT INTO pigtraxrefdata."MortalityReasonTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_MortalityReasonType") VALUES ('Reason1','en',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."MortalityReasonTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_MortalityReasonType") VALUES ('Reason2','en',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."MortalityReasonTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_MortalityReasonType") VALUES ('Reason3','en',CURRENT_TIMESTAMP,'pigtraxadmin',3);


INSERT INTO pigtraxrefdata."MortalityReasonTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_MortalityReasonType") VALUES ('Reason1_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."MortalityReasonTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_MortalityReasonType") VALUES ('Reason2_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."MortalityReasonTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_MortalityReasonType") VALUES ('Reason3_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',3);

INSERT INTO pigtraxrefdata."MortalityReasonTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_MortalityReasonType") VALUES ('Reason1_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."MortalityReasonTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_MortalityReasonType") VALUES ('Reason2_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."MortalityReasonTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_MortalityReasonType") VALUES ('Reason3_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',3);
----------
INSERT INTO pigtraxrefdata."TrailerType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (1,'American 2 Deck', CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TrailerType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (2,'American Pot Belly',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TrailerType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (3,'Climate Controlled',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TrailerType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (4,'EU Adj 3 Deck', CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TrailerType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (5,'EU Adj 4 Deck',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TrailerType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (6,'Goose Neck Double Deck',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TrailerType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (7,'Goose Neck Single Deck', CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TrailerType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (8,'MX/BZ Open 2 Deck',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TrailerType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (9,'MX/BZ Open 3 Deck',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TrailerType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (10,'Other', CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TrailerType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (11,'Single Deck',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TrailerType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (12,'Quarantine',CURRENT_TIMESTAMP,'pigtraxadmin');


INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('American 2 Deck','en',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('American Pot Belly','en',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('Climate Controlled','en',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('EU Adj 3 Deck','en',CURRENT_TIMESTAMP,'pigtraxadmin',4);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('EU Adj 4 Deck','en',CURRENT_TIMESTAMP,'pigtraxadmin',5);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('Goose Neck Double Deck','en',CURRENT_TIMESTAMP,'pigtraxadmin',6);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('Goose Neck Single Deck','en',CURRENT_TIMESTAMP,'pigtraxadmin',7);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('MX/BZ Open 2 Deck','en',CURRENT_TIMESTAMP,'pigtraxadmin',8);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('MX/BZ Open 3 Deck','en',CURRENT_TIMESTAMP,'pigtraxadmin',9);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('Other','en',CURRENT_TIMESTAMP,'pigtraxadmin',10);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('Single Deck','en',CURRENT_TIMESTAMP,'pigtraxadmin',11);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('Quarantine
','en',CURRENT_TIMESTAMP,'pigtraxadmin',12);


INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('American 2 Deck','es',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('American Pot Belly','es',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('Climate Controlled','es',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('EU Adj 3 Deck','es',CURRENT_TIMESTAMP,'pigtraxadmin',4);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('EU Adj 4 Deck','es',CURRENT_TIMESTAMP,'pigtraxadmin',5);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('Goose Neck Double Deck','es',CURRENT_TIMESTAMP,'pigtraxadmin',6);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('Goose Neck Single Deck','es',CURRENT_TIMESTAMP,'pigtraxadmin',7);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('MX/BZ Open 2 Deck','es',CURRENT_TIMESTAMP,'pigtraxadmin',8);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('MX/BZ Open 3 Deck','es',CURRENT_TIMESTAMP,'pigtraxadmin',9);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('Other','es',CURRENT_TIMESTAMP,'pigtraxadmin',10);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('Single Deck','es',CURRENT_TIMESTAMP,'pigtraxadmin',11);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('Quarantine
','es',CURRENT_TIMESTAMP,'pigtraxadmin',12);

INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('American 2 Deck','pr',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('American Pot Belly','pr',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('Climate Controlled','pr',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('EU Adj 3 Deck','pr',CURRENT_TIMESTAMP,'pigtraxadmin',4);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('EU Adj 4 Deck','pr',CURRENT_TIMESTAMP,'pigtraxadmin',5);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('Goose Neck Double Deck','pr',CURRENT_TIMESTAMP,'pigtraxadmin',6);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('Goose Neck Single Deck','pr',CURRENT_TIMESTAMP,'pigtraxadmin',7);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('MX/BZ Open 2 Deck','pr',CURRENT_TIMESTAMP,'pigtraxadmin',8);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('MX/BZ Open 3 Deck','pr',CURRENT_TIMESTAMP,'pigtraxadmin',9);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('Other','pr',CURRENT_TIMESTAMP,'pigtraxadmin',10);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('Single Deck','pr',CURRENT_TIMESTAMP,'pigtraxadmin',11);
INSERT INTO pigtraxrefdata."TrailerTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TrailerType") VALUES ('Quarantine
','pr',CURRENT_TIMESTAMP,'pigtraxadmin',12);
----------------

INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (1,'Total Services', CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (2,'1st services',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (3,'Repeat services',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (4,'% 1st services',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (5,'% 1st services: gilts',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (6,'% 1st services: sows',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (7,'% Repeat services',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (8,'Services with >1 matings',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (9,'% Services with 1+ matings',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (10,' Matings per Service',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (11,'Arrival to 1st serv interval',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (12,'Wean sows bred by 7 days',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (13,'% Wean sows bred by 7 days',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (14,'Wean to 1st service interval',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (15,'Conception rate at 30d Presumed Pregnant',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (16,'Conception rate at day42',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (17,'Avg parity of served females',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (18,'Service to fallout interval',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (19,'Service Capacity (Services/crate/year)',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (20,'Litters farrowed',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (21,'Avg parity',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (22,'Avg parity',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (23,'Gilts farrowed',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (24,'Farrowing rate',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (25,'Adj farrowing rate',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (26,'Total totalborn',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (27,'Avg totalborn',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (28,'Total viableborn',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (29,'Avg viableborn',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (30,'Total liveborn',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (31,'Total liveborn', CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (32,'Total deadborn',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (33,'Avg deadborn',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (34,'% Deadborn',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (35,'Total stillborn',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (36,'Avg stillborn',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (37,'% Stillborn',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (38,'Total mummies',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (39,'Avg mummies',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (40,'% Mummies',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (41,'Litters with less 7 liveborn',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (42,'% Litters with less 7 liveborn',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (43,'Total weakborn',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (44,'Avg weakborn',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (45,'Weakborn/totalborn(%)',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (46,'Litters with birth weight',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (47,'Avg birth weight',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (48,'Avg birth weight/liveborn',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (49,'Litters/crate/year',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (50,'Farrow Capacity (Farrows / crate / year)',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (51,'Litters weaned',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (52,'Litters weaning 12+',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (53,'% Litters weaning 12+',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (54,'Sows weaning 0 pig',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (55,'% Sows weaning 0 pigs',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (56,'Pigs weaned/litter weaned',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (57,'Total pigs weaned',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (58,'Pigs weaned/sow weaned',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (59,'Pigs weaned/totalborn(%)',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (60,'Net foster',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (61,'PWM(%)',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (62,'Litters with weaning weight',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (63,'Piglets with weaning weight',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (64,'Total weaning weight',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (65,'Weaning weight/litter',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (66,'Weaning weight/piglet',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (67,' Litters with weaning age',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (68,'Avg weaning age',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (69,'Litters weaned less than 17 days',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (70,'% Litters weaned less than 17 days',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (71,'Adj 21day litter weight',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (72,'Pigs weaned/crate/year',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."TargetType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (73,'Weaning Capacity%',CURRENT_TIMESTAMP,'pigtraxadmin');


INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total services','en',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('1st services','en',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Repeat services','en',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% 1st services','en',CURRENT_TIMESTAMP,'pigtraxadmin',4);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% 1st services: gilts','en',CURRENT_TIMESTAMP,'pigtraxadmin',5);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% 1st services: sows','en',CURRENT_TIMESTAMP,'pigtraxadmin',6);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Repeat services','en',CURRENT_TIMESTAMP,'pigtraxadmin',7);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Services with >1 matings','en',CURRENT_TIMESTAMP,'pigtraxadmin',8);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Services with 1+ matings','en',CURRENT_TIMESTAMP,'pigtraxadmin',9);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Matings per Service','en',CURRENT_TIMESTAMP,'pigtraxadmin',10);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Arrival to 1st serv interval','en',CURRENT_TIMESTAMP,'pigtraxadmin',11);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Wean sows bred by 7 days','en',CURRENT_TIMESTAMP,'pigtraxadmin',12);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Wean sows bred by 7 days','en',CURRENT_TIMESTAMP,'pigtraxadmin',13);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Wean to 1st service interval','en',CURRENT_TIMESTAMP,'pigtraxadmin',14);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Conception rate at 30d Presumed Pregnant','en',CURRENT_TIMESTAMP,'pigtraxadmin',15);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Conception rate at day42','en',CURRENT_TIMESTAMP,'pigtraxadmin',16);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg parity of served females','en',CURRENT_TIMESTAMP,'pigtraxadmin',17);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Service to fallout interval','en',CURRENT_TIMESTAMP,'pigtraxadmin',18);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Service Capacity (Services/crate/year)','en',CURRENT_TIMESTAMP,'pigtraxadmin',19);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters farrowed','en',CURRENT_TIMESTAMP,'pigtraxadmin',20);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg parity','en',CURRENT_TIMESTAMP,'pigtraxadmin',21);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg gest','en',CURRENT_TIMESTAMP,'pigtraxadmin',22);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Gilts farrowed','en',CURRENT_TIMESTAMP,'pigtraxadmin',23);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Farrowing rate','en',CURRENT_TIMESTAMP,'pigtraxadmin',24);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Adj farrowing rate','en',CURRENT_TIMESTAMP,'pigtraxadmin',25);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total totalborn','en',CURRENT_TIMESTAMP,'pigtraxadmin',26);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg totalborn','en',CURRENT_TIMESTAMP,'pigtraxadmin',27);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total viableborn','en',CURRENT_TIMESTAMP,'pigtraxadmin',28);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg viableborn','en',CURRENT_TIMESTAMP,'pigtraxadmin',29);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total liveborn','en',CURRENT_TIMESTAMP,'pigtraxadmin',30);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg liveborn','en',CURRENT_TIMESTAMP,'pigtraxadmin',31);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total deadborn','en',CURRENT_TIMESTAMP,'pigtraxadmin',32);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg deadborn','en',CURRENT_TIMESTAMP,'pigtraxadmin',33);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Deadborn','en',CURRENT_TIMESTAMP,'pigtraxadmin',34);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total stillborn','en',CURRENT_TIMESTAMP,'pigtraxadmin',35);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg stillborn','en',CURRENT_TIMESTAMP,'pigtraxadmin',36);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Stillborn','en',CURRENT_TIMESTAMP,'pigtraxadmin',37);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total mummies','en',CURRENT_TIMESTAMP,'pigtraxadmin',38);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg mummies','en',CURRENT_TIMESTAMP,'pigtraxadmin',39);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Mummies','en',CURRENT_TIMESTAMP,'pigtraxadmin',40);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters with less 7 liveborn','en',CURRENT_TIMESTAMP,'pigtraxadmin',41);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Litters with less 7 liveborn','en',CURRENT_TIMESTAMP,'pigtraxadmin',42);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total weakborn','en',CURRENT_TIMESTAMP,'pigtraxadmin',43);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg weakborn','en',CURRENT_TIMESTAMP,'pigtraxadmin',44);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Weakborn/totalborn(%)','en',CURRENT_TIMESTAMP,'pigtraxadmin',45);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters with birth weight','en',CURRENT_TIMESTAMP,'pigtraxadmin',46);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg birth weight','en',CURRENT_TIMESTAMP,'pigtraxadmin',47);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg birth weight/liveborn','en',CURRENT_TIMESTAMP,'pigtraxadmin',48);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters/crate/year','en',CURRENT_TIMESTAMP,'pigtraxadmin',49);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Farrow Capacity (Farrows / crate / year)','en',CURRENT_TIMESTAMP,'pigtraxadmin',50);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters weaned','en',CURRENT_TIMESTAMP,'pigtraxadmin',51);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters weaning 12+','en',CURRENT_TIMESTAMP,'pigtraxadmin',52);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Litters weaning 12+','en',CURRENT_TIMESTAMP,'pigtraxadmin',53);
 INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('weaning 0 pig','en',CURRENT_TIMESTAMP,'pigtraxadmin',54);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Sows weaning 0 pigs','en',CURRENT_TIMESTAMP,'pigtraxadmin',55);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Pigs weaned/litter weaned','en',CURRENT_TIMESTAMP,'pigtraxadmin',56);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total pigs weaned','en',CURRENT_TIMESTAMP,'pigtraxadmin',57);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Pigs weaned/sow weaned','en',CURRENT_TIMESTAMP,'pigtraxadmin',58);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Pigs weaned/totalborn(%)','en',CURRENT_TIMESTAMP,'pigtraxadmin',59);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Net foster','en',CURRENT_TIMESTAMP,'pigtraxadmin',60);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('PWM(%)','en',CURRENT_TIMESTAMP,'pigtraxadmin',61);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters with weaning weight','en',CURRENT_TIMESTAMP,'pigtraxadmin',62);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Piglets with weaning weight','en',CURRENT_TIMESTAMP,'pigtraxadmin',63);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total weaning weight','en',CURRENT_TIMESTAMP,'pigtraxadmin',64);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Weaning weight/litter','en',CURRENT_TIMESTAMP,'pigtraxadmin',65);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Weaning weight/piglet','en',CURRENT_TIMESTAMP,'pigtraxadmin',66);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters with weaning age','en',CURRENT_TIMESTAMP,'pigtraxadmin',67);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg weaning age','en',CURRENT_TIMESTAMP,'pigtraxadmin',68);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters weaned less than 17 days','en',CURRENT_TIMESTAMP,'pigtraxadmin',69);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Litters weaned less than 17 days','en',CURRENT_TIMESTAMP,'pigtraxadmin',70);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Adj 21day litter weight','en',CURRENT_TIMESTAMP,'pigtraxadmin',71);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Pigs weaned/crate/year','en',CURRENT_TIMESTAMP,'pigtraxadmin',72);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Weaning Capacity%','en',CURRENT_TIMESTAMP,'pigtraxadmin',73);


INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total services_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('1st services_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Repeat services_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% 1st services_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',4);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% 1st services: gilts_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',5);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% 1st services: sows_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',6);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Repeat services_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',7);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Services with >1 matings_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',8);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Services with 1+ matings_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',9);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Matings per Service_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',10);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Arrival to 1st serv interval_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',11);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Wean sows bred by 7 days_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',12);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Wean sows bred by 7 days_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',13);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Wean to 1st service interval_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',14);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Conception rate at 30d Presumed Pregnant_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',15);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Conception rate at day42_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',16);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg parity of served females_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',17);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Service to fallout interval_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',18);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Service Capacity (Services/crate/year)_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',19);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters farrowed_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',20);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg parity_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',21);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg gest_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',22);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Gilts farrowed_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',23);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Farrowing rate_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',24);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Adj farrowing rate_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',25);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total totalborn_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',26);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg totalborn_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',27);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total viableborn_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',28);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg viableborn_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',29);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total liveborn_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',30);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg liveborn_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',31);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total deadborn_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',32);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg deadborn_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',33);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Deadborn_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',34);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total stillborn_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',35);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg stillborn_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',36);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Stillborn_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',37);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total mummies_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',38);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg mummies_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',39);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Mummies_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',40);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters with less 7 liveborn_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',41);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Litters with less 7 liveborn_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',42);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total weakborn_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',43);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg weakborn_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',44);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Weakborn/totalborn(%)_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',45);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters with birth weight_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',46);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg birth weight_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',47);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg birth weight/liveborn_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',48);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters/crate/year_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',49);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Farrow Capacity (Farrows / crate / year)_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',50);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters weaned_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',51);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters weaning 12+_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',52);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Litters weaning 12+_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',53);
 INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('weaning 0 pig_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',54);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Sows weaning 0 pigs_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',55);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Pigs weaned/litter weaned_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',56);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total pigs weaned_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',57);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Pigs weaned/sow weaned_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',58);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Pigs weaned/totalborn(%)_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',59);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Net foster_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',60);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('PWM(%)_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',61);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters with weaning weight_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',62);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Piglets with weaning weight_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',63);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total weaning weight_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',64);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Weaning weight/litter_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',65);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Weaning weight/piglet_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',66);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters with weaning age_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',67);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg weaning age_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',68);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters weaned less than 17 days_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',69);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Litters weaned less than 17 days_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',70);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Adj 21day litter weight_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',71);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Pigs weaned/crate/year_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',72);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Weaning Capacity%_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',73);


INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total services_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('1st services_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Repeat services_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% 1st services_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',4);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% 1st services: gilts_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',5);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% 1st services: sows_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',6);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Repeat services_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',7);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Services with >1 matings_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',8);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Services with 1+ matings_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',9);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Matings per Service_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',10);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Arrival to 1st serv interval_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',11);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Wean sows bred by 7 days_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',12);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Wean sows bred by 7 days_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',13);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Wean to 1st service interval_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',14);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Conception rate at 30d Presumed Pregnant_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',15);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Conception rate at day42_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',16);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg parity of served females_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',17);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Service to fallout interval_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',18);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Service Capacity (Services/crate/year)_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',19);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters farrowed_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',20);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg parity_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',21);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg gest_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',22);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Gilts farrowed_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',23);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Farrowing rate_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',24);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Adj farrowing rate_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',25);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total totalborn_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',26);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg totalborn_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',27);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total viableborn_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',28);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg viableborn_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',29);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total liveborn_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',30);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg liveborn_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',31);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total deadborn_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',32);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg deadborn_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',33);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Deadborn_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',34);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total stillborn_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',35);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg stillborn_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',36);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Stillborn_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',37);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total mummies_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',38);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg mummies_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',39);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Mummies_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',40);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters with less 7 liveborn_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',41);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Litters with less 7 liveborn_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',42);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total weakborn_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',43);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg weakborn_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',44);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Weakborn/totalborn(%)_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',45);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters with birth weight_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',46);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg birth weight_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',47);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg birth weight/liveborn_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',48);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters/crate/year_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',49);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Farrow Capacity (Farrows / crate / year)_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',50);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters weaned_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',51);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters weaning 12+_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',52);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Litters weaning 12+_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',53);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('weaning 0 pig_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',54);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Sows weaning 0 pigs_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',55);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Pigs weaned/litter weaned_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',56);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total pigs weaned_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',57);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Pigs weaned/sow weaned_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',58);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Pigs weaned/totalborn(%)_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',59);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Net foster_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',60);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('PWM(%)_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',61);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters with weaning weight_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',62);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Piglets with weaning weight_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',63);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Total weaning weight_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',64);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Weaning weight/litter_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',65);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Weaning weight/piglet_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',66);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters with weaning age_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',67);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Avg weaning age_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',68);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Litters weaned less than 17 days_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',69);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('% Litters weaned less than 17 days_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',70);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Adj 21day litter weight_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',71);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Pigs weaned/crate/year_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',72);
INSERT INTO pigtraxrefdata."TargetTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_TargetType") VALUES ('Weaning Capacity%_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',73);

----------



INSERT INTO pigtrax."Company"(

            "companyId", name, address, city, country, "registrationNumber", email,

            phone, "contactName", payment, "paymentDate", "isActive", "lastUpdated",

            "userUpdated")

VALUES ('1A2B', 'PigTrax', '1 Sovereign Street', 'Alegre','Brazil', '12345678', 'jimihendrix@gmail.com',

            '+1-111-111-1111', 'Jimmy Hendrix', 3450.50, CURRENT_DATE, true, CURRENT_DATE,

            'pigtraxadmin');

INSERT INTO pigtrax."Employee"(

            "employeeId", "id_Company", name, "ptPassword", "isActive", "isPortalUser",

            "lastUpdated", "userUpdated", "id_RoleType")

VALUES ('pigtraxsuperadmin',1,'PT 

SuperAdmin','$2a$10$iF4BCj2.AvqVcvbf7aTb2u6fVNK/YxQ8ltPQt2N0n0csBb5rCIin.',true, 

true, CURRENT_DATE,'pigtraxadmin',1);

INSERT INTO pigtrax."Employee"(

            "employeeId", "id_Company", name, "ptPassword", "isActive", "isPortalUser",

            "lastUpdated", "userUpdated", "id_RoleType")

VALUES ('pigtraxdatacfgmgr',1,'PT Data Cfg 

Mgr','$2a$10$iF4BCj2.AvqVcvbf7aTb2u6fVNK/YxQ8ltPQt2N0n0csBb5rCIin.',true, true, 

CURRENT_DATE,'pigtraxadmin',2);

INSERT INTO pigtrax."Company"(

            "companyId", name, address, city, country, "registrationNumber", email,

            phone, "contactName", payment, "paymentDate", "isActive", "lastUpdated",

            "userUpdated")

VALUES ('1Y2Z', 'PigFarm', '1 Shire Oak Street', 'Alegre','Brazil', '34567899', 'jimmypage@gmail.com',

            '+1-111-111-1111', 'Jimmy Page', 13450.50, CURRENT_DATE, true, CURRENT_DATE,

            'pigtraxadmin');

INSERT INTO pigtrax."Employee"(

            "employeeId", "id_Company", name, "ptPassword", "isActive", "isPortalUser",

            "lastUpdated", "userUpdated", "id_RoleType")

VALUES ('pigfarmsuperadmin',2,'PF 

SuperAdmin','$2a$10$iF4BCj2.AvqVcvbf7aTb2u6fVNK/YxQ8ltPQt2N0n0csBb5rCIin.',true, 

true, CURRENT_DATE,'pigtraxadmin',3);

INSERT INTO pigtrax."Employee"(

            "employeeId", "id_Company", name, "ptPassword", "isActive", "isPortalUser",

            "lastUpdated", "userUpdated", "id_RoleType")

VALUES ('pigfarmdatacfgmgr',2,'PF Data Cfg 

Mgr','$2a$10$iF4BCj2.AvqVcvbf7aTb2u6fVNK/YxQ8ltPQt2N0n0csBb5rCIin.',true, true, 

CURRENT_DATE,'pigtraxadmin',4);

INSERT INTO pigtrax."Company"(

            "companyId", name, address, city, country, "registrationNumber", email,

            phone, "contactName", payment, "paymentDate", "isActive", "lastUpdated",

            "userUpdated")

VALUES ('1Y2Y', 'PigFarmPP', '1 Sovereign Street', 'Alegre','Brazil', '12345678', 'jimihendrix@gmail.com',

            '+1-111-111-1111', 'Praveen Parasiya', 3450.50, CURRENT_DATE, true, CURRENT_DATE,

            'pigtraxadmin');

INSERT INTO pigtrax."Employee"(

            "employeeId", "id_Company", name, "ptPassword", "isActive", "isPortalUser",

            "lastUpdated", "userUpdated", "id_RoleType")

VALUES ('praveensuperadmin',3,'PT 

SuperAdmin','$2a$10$iF4BCj2.AvqVcvbf7aTb2u6fVNK/YxQ8ltPQt2N0n0csBb5rCIin.',true, 

true, CURRENT_DATE,'pigtraxadmin',3);

INSERT INTO pigtrax."Employee"(

            "employeeId", "id_Company", name, "ptPassword", "isActive", "isPortalUser",

            "lastUpdated", "userUpdated", "id_RoleType")

VALUES ('praveendatacfgmgr',3,'PT Data Cfg 

Mgr','$2a$10$iF4BCj2.AvqVcvbf7aTb2u6fVNK/YxQ8ltPQt2N0n0csBb5rCIin.',true, true, 

CURRENT_DATE,'pigtraxadmin',4);

INSERT INTO pigtrax."Employee"(

            "employeeId", "id_Company", name, "ptPassword", "isActive", "isPortalUser",

            "lastUpdated", "userUpdated", "id_RoleType")

VALUES ('praveenfarmemployee1',3, 'Alan', null, true, false, CURRENT_DATE,'pigtraxadmin',null);

INSERT INTO pigtrax."Employee"(

            "employeeId", "id_Company", name, "ptPassword", "isActive", "isPortalUser",

            "lastUpdated", "userUpdated", "id_RoleType")

VALUES ('praveenfarmemployee2',3, 'Tom', null, true, false, CURRENT_DATE,'pigtraxadmin',null);

INSERT INTO pigtrax."Company"(

            "companyId", name, address, city, country, "registrationNumber", email,

            phone, "contactName", payment, "paymentDate", "isActive", "lastUpdated",

            "userUpdated")

VALUES ('1Z2Z', 'PigFarmPRC', '1 Sovereign Street', 'Alegre','Brazil', '12345678', 'jimihendrix@gmail.com',

            '+1-111-111-1111', 'Praveen Parasiya', 3450.50, CURRENT_DATE, true, CURRENT_DATE,

            'pigtraxadmin');

INSERT INTO pigtrax."Employee"(

            "employeeId", "id_Company", name, "ptPassword", "isActive", "isPortalUser",

            "lastUpdated", "userUpdated", "id_RoleType")

VALUES ('roysuperadmin',4,'PT 

SuperAdmin','$2a$10$iF4BCj2.AvqVcvbf7aTb2u6fVNK/YxQ8ltPQt2N0n0csBb5rCIin.',true, 

true, CURRENT_DATE,'pigtraxadmin',3);

INSERT INTO pigtrax."Employee"(

            "employeeId", "id_Company", name, "ptPassword", "isActive", "isPortalUser",

            "lastUpdated", "userUpdated", "id_RoleType")

VALUES ('roydatacfgmgr',4,'PT Data Cfg 

Mgr','$2a$10$iF4BCj2.AvqVcvbf7aTb2u6fVNK/YxQ8ltPQt2N0n0csBb5rCIin.',true, true, 

CURRENT_DATE,'pigtraxadmin',4);

INSERT into pigtrax."EmployeeJobFunction" ("functionName", "functionFrom", "functionTo", "lastUpdated", "userUpdated", "id_Employee") values ('ANY',CURRENT_TIMESTAMP,null,CURRENT_TIMESTAMP,'pigtraxadmin',7);

INSERT into pigtrax."EmployeeJobFunction" ("functionName", "functionFrom", "functionTo", "lastUpdated", "userUpdated", "id_Employee") values ('ANY',CURRENT_TIMESTAMP,null,CURRENT_TIMESTAMP,'pigtraxadmin',8);

INSERT INTO pigtrax."MasterRation"(
            "rationValue", "id_FeedEventType", "lastUpdated", "userUpdated")
    VALUES ( 'Carrot', '1', current_timestamp, 'pigTraxAdmin');

INSERT INTO pigtrax."MasterRation"(
            "rationValue", "id_FeedEventType", "lastUpdated", "userUpdated")
    VALUES ( 'Carrot and Water', '1', current_timestamp, 'pigTraxAdmin');

