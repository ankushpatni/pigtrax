
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
