create table IF NOT EXISTS public.business_users (
	id serial not null,
	email varchar(100) not null,
	passwd varchar(100) not null,
	primary key (id),
	UNIQUE(email)
);
