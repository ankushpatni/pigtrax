
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
INSERT INTO pigtraxrefdata."PhaseType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (3,'Weaned Finished',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."PhaseType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (4,'GDU',CURRENT_TIMESTAMP,'pigtraxadmin');
INSERT INTO pigtraxrefdata."PhaseType" ("fieldCode","fieldDescription","lastUpdated","userUpdated") VALUES (5,'Unknown',CURRENT_TIMESTAMP,'pigtraxadmin');

INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Nursery','en',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Finishing','en',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Weaned Finished','en',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('GDU','en',CURRENT_TIMESTAMP,'pigtraxadmin',4);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Unknown','en',CURRENT_TIMESTAMP,'pigtraxadmin',5);

INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Nursery_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Finishing_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Weaned Finished_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('GDU_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',4);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Unknown_ES','es',CURRENT_TIMESTAMP,'pigtraxadmin',5);

INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Nursery_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',1);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Finishing_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',2);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Weaned Finished_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',3);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('GDU_ES_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',4);
INSERT INTO pigtraxrefdata."PhaseTypeTranslation" ("fieldValue","fieldLanguage","lastUpdated","userUpdated","id_PhaseType") VALUES ('Unknown_PR','pr',CURRENT_TIMESTAMP,'pigtraxadmin',5);


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


-----------------
INSERT INTO pigtrax."Company"(

            "companyId", name, address, city, "registrationNumber", email,

            phone, "contactName", payment, "paymentDate", "isActive", "lastUpdated",

            "userUpdated")

VALUES ('1A2B', 'PigTrax', '1 Sovereign Street', 'Leeds', '12345678', 'jimihendrix@gmail.com',

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

            "companyId", name, address, city, "registrationNumber", email,

            phone, "contactName", payment, "paymentDate", "isActive", "lastUpdated",

            "userUpdated")

VALUES ('1Y2Z', 'PigFarm', '1 Shire Oak Street', 'Leeds', '34567899', 'jimmypage@gmail.com',

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