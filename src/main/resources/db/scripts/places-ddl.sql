create table IF NOT EXISTS public.places (
	id serial not null,
	name varchar(100) not null,
	slug varchar(100) not null,
	description TEXT,
    category_id int not null,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by int not null,
    updated_by int not null,
	primary key (id),
	UNIQUE(slug),
	constraint fk_place_category foreign key (category_id) references categories (id),
	constraint fk_place_admin_created_by foreign key (created_by) references admins (id),
	constraint fk_place_admin_updated_by foreign key (updated_by) references admins (id)
);
