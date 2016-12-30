/*==============================================================*/
/* DBMS name:      PostgreSQL 9.x                               */
/* Created on:     29/12/2016 17:48:03                          */
/*==============================================================*/


drop index EMPLOYEE_ASSIST_FK;

drop index ASSIST_PK;

drop table ASISTENCIA;

drop index EMPLOYEE_PK;

drop table EMPLEADO;

/*==============================================================*/
/* Table: ASISTENCIA                                            */
/*==============================================================*/
create table ASISTENCIA (
   ID_ASISTENCIA        SERIAL               not null,
   CI_EMPLEADO          NUMERIC(15)          null,
   FECHA_ASISTENCIA     DATE                 null,
   INGRESO_MANIANA      TIME                 null,
   SALIDA_MANIANA       TIME                 null,
   INGRESO_TARDE        TIME                 null,
   SALIDA_TARDE         TIME                 null,
   constraint PK_ASISTENCIA primary key (ID_ASISTENCIA)
);

ALTER TABLE asistencia OWNER TO fundatca_per;

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
   NOMBRE_EMPLEADO      VARCHAR(256)         null,
   APELLIDO_PATERNO_EMPLEADO VARCHAR(128)         null,
   APELLIDO_MATERNO_EMPLEADO VARCHAR(128)         null,
   constraint PK_EMPLEADO primary key (CI_EMPLEADO)
);

ALTER TABLE empleado OWNER TO fundatca_per;

/*==============================================================*/
/* Index: EMPLOYEE_PK                                           */
/*==============================================================*/
create unique index EMPLOYEE_PK on EMPLEADO (
CI_EMPLEADO
);

alter table ASISTENCIA
   add constraint FK_ASISTENC_EMPLOYEE__EMPLEADO foreign key (CI_EMPLEADO)
      references EMPLEADO (CI_EMPLEADO)
      on delete restrict on update restrict;

