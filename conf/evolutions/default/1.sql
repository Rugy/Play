# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table partie (
  p_id                      bigint not null,
  home_team                 varchar(255),
  guest_team                varchar(255),
  play_date                 timestamp,
  stadium                   varchar(255),
  constraint pk_partie primary key (p_id))
;

create table stadium (
  s_id                      bigint not null,
  name                      varchar(255),
  location                  varchar(255),
  seats                     integer,
  entries                   integer,
  sponsor                   varchar(255),
  constraint pk_stadium primary key (s_id))
;

create sequence partie_seq;

create sequence stadium_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists partie;

drop table if exists stadium;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists partie_seq;

drop sequence if exists stadium_seq;

