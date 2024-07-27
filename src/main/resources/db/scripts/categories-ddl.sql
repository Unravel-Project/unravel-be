create table IF NOT EXISTS public.categories (
	id serial not null,
	name varchar(100) not null,
	slug varchar(100) not null,
	description varchar(255),
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by int not null,
    updated_by int not null,
	primary key (id),
	UNIQUE(slug),
	constraint fk_category_admin_created_by foreign key (created_by) references admins (id),
	constraint fk_category_admin_updated_by foreign key (updated_by) references admins (id)
);
