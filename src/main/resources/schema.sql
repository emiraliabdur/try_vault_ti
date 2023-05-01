create table fund_load_attempt
(
    id BIGINT not null,
    customer_id BIGINT not null,
    amount DECFLOAT not null,
    date_time TIMESTAMP not null,
    primary key(id)
);

create table fund_load_result
(
    id BIGINT not null,
    customer_id BIGINT not null,
    accepted BOOLEAN not null,
    primary key(id)
);

create table period_load_limits
(
    period_type varchar(64) not null,
    number_of_loads INT not null,
    total_load_amount DECFLOAT not null,
    primary key(period_type)
)