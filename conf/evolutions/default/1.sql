# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "CATEGORY" ("ID" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"USERNAME" VARCHAR NOT NULL);
create table "FRIENDSHIP" ("ID" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"ID_USER_A" INTEGER NOT NULL,"ID_USER_B" INTEGER NOT NULL);
create unique index "uniqueFriendshipIndex" on "FRIENDSHIP" ("ID_USER_A","ID_USER_B");
create table "INTEREST" ("ID" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"NAME" VARCHAR NOT NULL,"CREATED_BY_USER_ID" INTEGER NOT NULL,"ID_CATEGORY" INTEGER NOT NULL);
create table "PAGE_VISITED" ("ID" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"PAGE_NAME" VARCHAR NOT NULL,"IP" VARCHAR NOT NULL,"DATE" DATE NOT NULL,"ID_USER" INTEGER);
create table "USER" ("ID" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"USERNAME" VARCHAR NOT NULL,"PASSWORD" VARCHAR NOT NULL,"NAME" VARCHAR,"SURNAME" VARCHAR,"AUTH_METHOD" VARCHAR NOT NULL,"PROVIDER_ID" VARCHAR NOT NULL);
create unique index "UNIQUE_USERNAME" on "USER" ("USERNAME");
create table "USER_INTEREST" ("ID" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"ID_USER" INTEGER NOT NULL,"ID_INTEREST" INTEGER NOT NULL);
create table "USER_OAUTH2INFO" ("USER_ID" INTEGER NOT NULL PRIMARY KEY,"ACCESS_TOKEN" VARCHAR NOT NULL,"TOKEN_TYPE" VARCHAR,"EXIPIRES_IN" INTEGER,"REFRESH_TOKEN" VARCHAR);
create table "USER_PENDENT_REQUEST" ("EMAIL" VARCHAR NOT NULL,"TOKEN" VARCHAR NOT NULL,"CREATION_TIME" TIMESTAMP NOT NULL,"EXPIRATION_TIME" TIMESTAMP NOT NULL,"IS_SIGNUP" BOOLEAN NOT NULL);
create unique index "UNIQUE_TOKEN" on "USER_PENDENT_REQUEST" ("TOKEN");
alter table "FRIENDSHIP" add constraint "FK_USER_A" foreign key("ID_USER_A") references "USER"("ID") on update NO ACTION on delete NO ACTION;
alter table "FRIENDSHIP" add constraint "FK_USER_B" foreign key("ID_USER_B") references "USER"("ID") on update NO ACTION on delete NO ACTION;
alter table "INTEREST" add constraint "USER_FK" foreign key("CREATED_BY_USER_ID") references "USER"("ID") on update NO ACTION on delete NO ACTION;
alter table "INTEREST" add constraint "CAT_FK" foreign key("ID_CATEGORY") references "CATEGORY"("ID") on update NO ACTION on delete NO ACTION;
alter table "PAGE_VISITED" add constraint "FK_USER" foreign key("ID_USER") references "USER"("ID") on update NO ACTION on delete NO ACTION;
alter table "USER_INTEREST" add constraint "UI_USER_FK" foreign key("ID_USER") references "USER"("ID") on update NO ACTION on delete NO ACTION;
alter table "USER_INTEREST" add constraint "UI_INTEREST_FK" foreign key("ID_INTEREST") references "INTEREST"("ID") on update NO ACTION on delete NO ACTION;
alter table "USER_OAUTH2INFO" add constraint "FK_USER_ID" foreign key("USER_ID") references "USER"("ID") on update NO ACTION on delete NO ACTION;

# --- !Downs

alter table "FRIENDSHIP" drop constraint "FK_USER_A";
alter table "FRIENDSHIP" drop constraint "FK_USER_B";
alter table "INTEREST" drop constraint "USER_FK";
alter table "INTEREST" drop constraint "CAT_FK";
alter table "PAGE_VISITED" drop constraint "FK_USER";
alter table "USER_INTEREST" drop constraint "UI_USER_FK";
alter table "USER_INTEREST" drop constraint "UI_INTEREST_FK";
alter table "USER_OAUTH2INFO" drop constraint "FK_USER_ID";
drop table "CATEGORY";
drop table "FRIENDSHIP";
drop table "INTEREST";
drop table "PAGE_VISITED";
drop table "USER";
drop table "USER_INTEREST";
drop table "USER_OAUTH2INFO";
drop table "USER_PENDENT_REQUEST";

