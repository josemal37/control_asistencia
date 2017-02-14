/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     13/02/2017 14:31:05                          */
/*==============================================================*/


/*==============================================================*/
/* Table: ASISTENCIA                                            */
/*==============================================================*/
create table ASISTENCIA (
   ID_ASISTENCIA        SERIAL               not null,
   CI_EMPLEADO          NUMERIC(15)          not null,
   FECHA_ASISTENCIA     DATE                 null,
   INGRESO_MANIANA      TIME                 null,
   SALIDA_MANIANA       TIME                 null,
   INGRESO_TARDE        TIME                 null,
   SALIDA_TARDE         TIME                 null,
   constraint PK_ASISTENCIA primary key (ID_ASISTENCIA)
);

-- set table ownership
alter table ASISTENCIA owner to FUNDATCA_PER
;
/*==============================================================*/
/* Index: ASSIST_PK                                             */
/*==============================================================*/
create unique index ASSIST_PK on ASISTENCIA (
ID_ASISTENCIA
);

/*==============================================================*/
/* Index: EMPLOYEE_ASSIST_FK                                    */
/*==============================================================*/
create  index EMPLOYEE_ASSIST_FK on ASISTENCIA (
CI_EMPLEADO
);

/*==============================================================*/
/* Table: EMPLEADO                                              */
/*==============================================================*/
create table EMPLEADO (
   CI_EMPLEADO          NUMERIC(15)          not null,
   ID_TIPO_EMPLEADO     INT4                 not null,
   NOMBRE_EMPLEADO      VARCHAR(256)         null,
   APELLIDO_PATERNO_EMPLEADO VARCHAR(128)         null,
   APELLIDO_MATERNO_EMPLEADO VARCHAR(128)         null,
   constraint PK_EMPLEADO primary key (CI_EMPLEADO)
);

-- set table ownership
alter table EMPLEADO owner to FUNDATCA_PER
;
/*==============================================================*/
/* Index: EMPLOYEE_PK                                           */
/*==============================================================*/
create unique index EMPLOYEE_PK on EMPLEADO (
CI_EMPLEADO
);

/*==============================================================*/
/* Index: EMPLEADO_ES_DE_TIPO_FK                                */
/*==============================================================*/
create  index EMPLEADO_ES_DE_TIPO_FK on EMPLEADO (
ID_TIPO_EMPLEADO
);

/*==============================================================*/
/* Table: OBSERVACION                                           */
/*==============================================================*/
create table OBSERVACION (
   ID_OBSERVACION       SERIAL               not null,
   ID_ASISTENCIA        INT4                 not null,
   DESCRIPCION_OBSERVACION TEXT                 null,
   constraint PK_OBSERVACION primary key (ID_OBSERVACION)
);

-- set table ownership
alter table OBSERVACION owner to FUNDATCA_PER
;
/*==============================================================*/
/* Index: OBSERVACION_MANIANA_PK                                */
/*==============================================================*/
create unique index OBSERVACION_MANIANA_PK on OBSERVACION (
ID_OBSERVACION
);

/*==============================================================*/
/* Index: ASIS_TIENE_OBS_MANIANA_FK                             */
/*==============================================================*/
create  index ASIS_TIENE_OBS_MANIANA_FK on OBSERVACION (
ID_ASISTENCIA
);

/*==============================================================*/
/* Table: TIPO_EMPLEADO                                         */
/*==============================================================*/
create table TIPO_EMPLEADO (
   ID_TIPO_EMPLEADO     SERIAL               not null,
   NOMBRE_TIPO_EMPLEADO VARCHAR(1024)        null,
   constraint PK_TIPO_EMPLEADO primary key (ID_TIPO_EMPLEADO)
);

-- set table ownership
alter table TIPO_EMPLEADO owner to FUNDATCA_PER
;
/*==============================================================*/
/* Index: TIPO_EMPLEADO_PK                                      */
/*==============================================================*/
create unique index TIPO_EMPLEADO_PK on TIPO_EMPLEADO (
ID_TIPO_EMPLEADO
);

alter table ASISTENCIA
   add constraint FK_ASISTENC_EMPLEADO__EMPLEADO foreign key (CI_EMPLEADO)
      references EMPLEADO (CI_EMPLEADO)
      on delete restrict on update restrict;

alter table EMPLEADO
   add constraint FK_EMPLEADO_EMPLEADO__TIPO_EMP foreign key (ID_TIPO_EMPLEADO)
      references TIPO_EMPLEADO (ID_TIPO_EMPLEADO)
      on delete restrict on update restrict;

alter table OBSERVACION
   add constraint FK_OBSERVAC_ASIS_TIEN_ASISTENC foreign key (ID_ASISTENCIA)
      references ASISTENCIA (ID_ASISTENCIA)
      on delete restrict on update restrict;

