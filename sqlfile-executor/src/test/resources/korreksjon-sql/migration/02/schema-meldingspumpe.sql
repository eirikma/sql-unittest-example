drop table work_item cascade constraints;
create table work_item (
    id number(19,0) not null,
    entity_id_long_part number(19,0),
    entity_id_discriminator_part number(19,0),
    processing_state number(10,0),
    created_by_server_id varchar2(255 char),
    processing_server_id varchar2(255 char),
    created_time timestamp,
    start_time timestamp,
    finished_time timestamp,
    earliest_execution_time timestamp,
    thread_id varchar2(255 char),
    error_message varchar2(3999 char),
    number_of_retries number(19,0),
    work_queue number(19,0),
    work_queue_name varchar2(255 char),
    entity_description varchar2(255 char),
    CONSTRAINT PK_work_item PRIMARY KEY (id)
);

drop table work_queue cascade constraints;
create table work_queue (
    id number(19,0) not null,
    entity_service_name varchar2(255 char) not null ,
    consumer_id varchar2(255 char) not null,
    status number(10,0),
    start_time timestamp,
    sort number(19,0),
    beskrivelse varchar2(255 char),
    work_queue_group number(19,0),
    CONSTRAINT PK_work_queue PRIMARY KEY (id),
    unique (entity_service_name)
);

drop table work_queue_group cascade constraints;
create table work_queue_group (
    id number(19,0) not null,
    group_name varchar2(255 char) not null ,
    sort number(19,0) not null,
    CONSTRAINT PK_work_queue_group PRIMARY KEY (id),
    unique (group_name)
);

create index work_item_work_queue_index on work_item (work_queue);
create index work_item_index2 on work_item (processing_state, earliest_execution_time);

alter table work_item
    add constraint fk_work_item_work_queue
    foreign key (work_queue)
    references work_queue;

alter table work_queue
    add constraint fk_work_queue_work_group
    foreign key (work_queue_group)
    references work_queue_group;

drop sequence work_item_id_sequence;
create sequence work_item_id_sequence;

drop sequence work_queue_group_id_sequence;
create sequence work_queue_group_id_sequence;

drop sequence work_queue_id_sequence;
create sequence work_queue_id_sequence;
