create table IF NOT EXISTS public.admins (
	id serial not null,
	name varchar(100) not null,
	email varchar(100) not null,
	passwd varchar(100) not null,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	primary key (id),
	UNIQUE(email)
);
