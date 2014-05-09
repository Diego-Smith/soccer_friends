# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "CATEGORY" ("ID" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"USERNAME" VARCHAR NOT NULL);
create table "FRIENDSHIP" ("ID" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"ID_USER_A" INTEGER NOT NULL,"ID_USER_B" INTEGER NOT NULL);
create unique index "uniqueFriendshipIndex" on "FRIENDSHIP" ("ID_USER_A","ID_USER_B");
create table "INTEREST" ("ID" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"USERNAME" VARCHAR NOT NULL,"COUNTER" INTEGER DEFAULT 0 NOT NULL,"ID_CATEGORY" INTEGER NOT NULL);
create table "PAGE_VISITED" ("ID" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"PAGE_NAME" VARCHAR NOT NULL,"IP" VARCHAR NOT NULL,"DATE" DATE NOT NULL,"ID_USER" INTEGER NOT NULL);
create table "USER" ("ID" INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,"USERNAME" VARCHAR NOT NULL,"PASSWORD" VARCHAR NOT NULL,"NAME" VARCHAR NOT NULL,"SURNAME" VARCHAR NOT NULL,"AUTH_METHOD" VARCHAR NOT NULL,"PROVIDER_ID" VARCHAR NOT NULL);
create unique index "UNIQUE_USERNAME" on "USER" ("USERNAME");
alter table "FRIENDSHIP" add constraint "FK_USER_A" foreign key("ID_USER_A") references "USER"("ID") on update NO ACTION on delete NO ACTION;
alter table "FRIENDSHIP" add constraint "FK_USER_B" foreign key("ID_USER_B") references "USER"("ID") on update NO ACTION on delete NO ACTION;
alter table "INTEREST" add constraint "CAT_FK" foreign key("ID_CATEGORY") references "CATEGORY"("ID") on update NO ACTION on delete NO ACTION;
alter table "PAGE_VISITED" add constraint "FK_USER" foreign key("ID_USER") references "USER"("ID") on update NO ACTION on delete NO ACTION;

# --- !Downs

alter table "FRIENDSHIP" drop constraint "FK_USER_A";
alter table "FRIENDSHIP" drop constraint "FK_USER_B";
alter table "INTEREST" drop constraint "CAT_FK";
alter table "PAGE_VISITED" drop constraint "FK_USER";
drop table "CATEGORY";
drop table "FRIENDSHIP";
drop table "INTEREST";
drop table "PAGE_VISITED";
drop table "USER";

