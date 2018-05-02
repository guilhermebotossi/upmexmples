BEGIN;
SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

CREATE SCHEMA swd;
ALTER SCHEMA swd OWNER TO climaespacial;

SET search_path = swd, pg_catalog;
--SET default_tablespace = ts_sc_swd_data;
SET default_with_oids = false;


CREATE TABLE swd.calculated_values (
    id uuid NOT NULL,
    creation_date timestamp with time zone NOT NULL,
    modification_date timestamp with time zone NOT NULL,
    dpr double precision,
    ey double precision,
    rmp double precision,
    time_tag timestamp with time zone NOT NULL
);
ALTER TABLE swd.calculated_values OWNER TO climaespacial;


CREATE TABLE swd.hello (
    id uuid NOT NULL,
    creation_date timestamp with time zone NOT NULL,
    modification_date timestamp with time zone NOT NULL
);
ALTER TABLE swd.hello OWNER TO climaespacial;


CREATE TABLE swd.hourly_average (
    id uuid NOT NULL,
    creation_date timestamp with time zone NOT NULL,
    modification_date timestamp with time zone NOT NULL,
    bt double precision,
    bx_gsm double precision,
    by_gsm double precision,
    bz_gsm double precision,
    density double precision,
    dpr double precision,
    ey double precision,
    rmp double precision,
    speed double precision,
    temperature double precision,
    time_tag timestamp with time zone NOT NULL
);
ALTER TABLE swd.hourly_average OWNER TO climaespacial;


CREATE TABLE swd.index_b (
    id uuid NOT NULL,
    creation_date timestamp with time zone NOT NULL,
    modification_date timestamp with time zone NOT NULL,
    post_index_value double precision,
    pre_index_value double precision,
    time_tag timestamp with time zone NOT NULL
);
ALTER TABLE swd.index_b OWNER TO climaespacial;


CREATE TABLE swd.index_c (
    id uuid NOT NULL,
    creation_date timestamp with time zone NOT NULL,
    modification_date timestamp with time zone NOT NULL,
    post_index_value double precision,
    pre_index_value double precision,
    time_tag timestamp with time zone NOT NULL
);
ALTER TABLE swd.index_c OWNER TO climaespacial;


CREATE TABLE swd.index_v (
    id uuid NOT NULL,
    creation_date timestamp with time zone NOT NULL,
    modification_date timestamp with time zone NOT NULL,
    post_index_value double precision,
    pre_index_value double precision,
    is_cycle_begin boolean NOT NULL,
    time_tag timestamp with time zone NOT NULL
);
ALTER TABLE swd.index_v OWNER TO climaespacial;


CREATE TABLE swd.index_z (
    id uuid NOT NULL,
    creation_date timestamp with time zone NOT NULL,
    modification_date timestamp with time zone NOT NULL,
    post_index_value double precision,
    pre_index_value double precision,
    time_tag timestamp with time zone NOT NULL
);
ALTER TABLE swd.index_z OWNER TO climaespacial;


CREATE TABLE  swd.kp_forecast (
    id uuid NOT NULL,
    creation_date timestamp with time zone NOT NULL,
    modification_date timestamp with time zone NOT NULL,
    indexes_time_tag timestamp with time zone NOT NULL,
    predicted_time_tag timestamp with time zone NOT NULL,
    inferiorlimit_1 double precision,
    inferiorlimit_2 double precision,
    inferiorlimit_3 double precision,
    probability_1 double precision,
    probability_2 double precision,
    probability_3 double precision,
    upperlimit_1 double precision,
    upperlimit_2 double precision,
    upperlimit_3 double precision
);
ALTER TABLE swd.kp_forecast OWNER TO climaespacial;


CREATE TABLE swd.mag (
    id uuid NOT NULL,
    creation_date timestamp with time zone NOT NULL,
    modification_date timestamp with time zone NOT NULL,
    bt double precision,
    bx_gsm double precision,
    by_gsm double precision,
    bz_gsm double precision,
    lat_gsm double precision,
    lon_gsm double precision,
    time_tag timestamp with time zone NOT NULL
);
ALTER TABLE swd.mag OWNER TO climaespacial;


CREATE TABLE swd.plasma (
    id uuid NOT NULL,
    creation_date timestamp with time zone NOT NULL,
    modification_date timestamp with time zone NOT NULL,
    density double precision,
    speed double precision,
    temperature double precision,
    time_tag timestamp with time zone NOT NULL
);
ALTER TABLE swd.plasma OWNER TO climaespacial;

CREATE TABLE swd.index_kp (
    id uuid NOT NULL,
    creation_date timestamp with time zone NOT NULL,
    modification_date timestamp with time zone NOT NULL,
    ap bigint,
    cp double precision,
    most_disturbed_and_quiet_days character varying(255),
    kp_sum bigint,
    kp_sum_flag character varying(255),
    time_tag timestamp with time zone NOT NULL
);

ALTER TABLE swd.index_kp OWNER TO climaespacial;

CREATE TABLE swd.kp_value (
    id uuid NOT NULL,
    creation_date timestamp with time zone NOT NULL
,    modification_date timestamp with time zone NOT NULL,
    value bigint NOT NULL,
    flag character varying(255) NOT NULL,
    time_tag timestamp with time zone NOT NULL,
    index_kp_id uuid NOT NULL
);

ALTER TABLE swd.kp_value OWNER TO climaespacial;

CREATE TABLE swd.kp_download_history (
    id uuid NOT NULL,
    creation_date timestamp with time zone NOT NULL,
    modification_date timestamp with time zone NOT NULL,
    is_complete boolean NOT NULL,
    period timestamp with time zone NOT NULL
);


ALTER TABLE kp_download_history OWNER TO climaespacial;


ALTER TABLE ONLY swd.calculated_values ADD CONSTRAINT calculated_values_pkey PRIMARY KEY (id);
ALTER TABLE ONLY swd.hourly_average ADD CONSTRAINT hourly_average_pkey PRIMARY KEY (id);
ALTER TABLE ONLY swd.index_b ADD CONSTRAINT index_b_pkey PRIMARY KEY (id);
ALTER TABLE ONLY swd.index_c ADD CONSTRAINT index_c_pkey PRIMARY KEY (id);
ALTER TABLE ONLY swd.index_v ADD CONSTRAINT index_v_pkey PRIMARY KEY (id);
ALTER TABLE ONLY swd.index_z ADD CONSTRAINT index_z_pkey PRIMARY KEY (id);
ALTER TABLE ONLY swd.kp_forecast ADD CONSTRAINT kp_forecast_pkey PRIMARY KEY (id);
ALTER TABLE ONLY swd.mag ADD CONSTRAINT mag_pkey PRIMARY KEY (id);
ALTER TABLE ONLY swd.plasma ADD CONSTRAINT plasma_pkey PRIMARY KEY (id);
ALTER TABLE ONLY swd.hello ADD CONSTRAINT hello_pkey PRIMARY KEY (id);
ALTER TABLE ONLY swd.index_kp ADD CONSTRAINT index_kp_pkey PRIMARY KEY (id);
ALTER TABLE ONLY swd.kp_value ADD CONSTRAINT kp_value_pkey PRIMARY KEY (id);
ALTER TABLE ONLY swd.kp_download_history ADD CONSTRAINT kp_download_history_pkey PRIMARY KEY (id);




ALTER TABLE ONLY swd.calculated_values ADD CONSTRAINT uk_h6rjuaisv02e9ffvoqmfwf41u UNIQUE (time_tag);
ALTER TABLE ONLY swd.hourly_average ADD CONSTRAINT uk_2lqyu28q3v9b4mio4fv582yrv UNIQUE (time_tag);
ALTER TABLE ONLY swd.index_b ADD CONSTRAINT uk_nybhnljqxbjjhgqbhwnbu4uce UNIQUE (time_tag);
ALTER TABLE ONLY swd.index_c ADD CONSTRAINT uk_r4g8s0uebnwhbbs2utiohvyj7 UNIQUE (time_tag);
ALTER TABLE ONLY swd.index_v ADD CONSTRAINT uk_ck2g6roxmasqrfls87qd7ibkc UNIQUE (time_tag);
ALTER TABLE ONLY swd.index_z ADD CONSTRAINT uk_mm0olp2fg3512yntdr10n9dwn UNIQUE (time_tag);
ALTER TABLE ONLY swd.kp_forecast ADD CONSTRAINT uk73ipysfcibo5htkwp6rqrtq1k UNIQUE (predicted_time_tag, indexes_time_tag);
ALTER TABLE ONLY swd.mag ADD CONSTRAINT uk_f3x8cotsry24mdk6g3wnnndmc UNIQUE (time_tag);
ALTER TABLE ONLY swd.plasma ADD CONSTRAINT uk_nub02b755p8i9l5t18xywwkox UNIQUE (time_tag);
ALTER TABLE ONLY swd.index_kp ADD CONSTRAINT uk_8bdv84e160ku19d0rwiwr733v UNIQUE (time_tag);
ALTER TABLE ONLY swd.kp_value ADD CONSTRAINT uk_2nsgfr1uq97osivuh1os7j9pd UNIQUE (time_tag);
ALTER TABLE ONLY swd.kp_download_history ADD CONSTRAINT uk_1vdjexburhmef1jxaivcl0tyv UNIQUE (period);


ALTER TABLE ONLY swd.kp_value ADD CONSTRAINT fklxw0qe2hvry586fd4a2aev4aw FOREIGN KEY (index_kp_id) REFERENCES swd.index_kp(id);


COMMIT;
--ROLLBACK;
