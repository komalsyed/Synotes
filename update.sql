alter table presentation_slide drop foreign key presentation_slide_ibfk_1;
alter table presentation_slide drop key presentation_id;
alter table presentation_slide add constraint foreign key (presentation_id) references resource (id) on delete cascade;
alter table resource add flag varchar(255);

create table user_profile
(
	id bigint(20) primary key auto_increment,
	version bigint(20) not null,
	owner_id bigint(20) not null,
	name varchar(255) not null,
	default_profile bit(1) not null,
	unique (owner_id, name),
	foreign key (owner_id) references user (id) on delete cascade
) engine = innodb default charset = utf8;

create table profile_entry
(
	id bigint(20) primary key auto_increment,
	version bigint(20) not null,
	profile_id bigint(20) not null,
	resource_id bigint(20),	
	name varchar(255) not null,
	value varchar(255) not null,
	unique (profile_id, resource_id, name),
	foreign key (profile_id) references user_profile (id) on delete cascade,
	foreign key (resource_id) references resource (id) on delete cascade
) engine = innodb default charset = utf8;

create table newtest
(
	multid bigint(20),
	question varchar(255),
	answer1 varchar(255),
	answer2 varchar(255),
	answer3 varchar(255),
	answer4 varchar(255),
	correct_answer bigint(20),
	diagram varchar(255),
	height bigint(20),
	width bigint(20)
	
	
) engine = innodb default charset = utf8;