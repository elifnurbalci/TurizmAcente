--
-- PostgreSQL database dump
--

-- Dumped from database version 14.11 (Postgres.app)
-- Dumped by pg_dump version 14.11 (Homebrew)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: hotel; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.hotel (
    hotel_id integer NOT NULL,
    hotel_name text NOT NULL,
    hotel_city text,
    hotel_region text,
    hotel_address text NOT NULL,
    hotel_email text NOT NULL,
    hotel_phone text NOT NULL,
    hotel_star_rating text NOT NULL,
    hotel_car_park boolean NOT NULL,
    hotel_wifi boolean NOT NULL,
    hotel_pool boolean NOT NULL,
    hotel_fitness boolean NOT NULL,
    hotel_concierge boolean NOT NULL,
    hotel_spa boolean NOT NULL,
    hotel_room_service boolean NOT NULL
);


ALTER TABLE public.hotel OWNER TO postgres;

--
-- Name: hotel_hotel_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.hotel ALTER COLUMN hotel_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.hotel_hotel_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: hotel_pension_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.hotel_pension_type (
    hotel_id integer NOT NULL,
    pension_type_id integer NOT NULL
);


ALTER TABLE public.hotel_pension_type OWNER TO postgres;

--
-- Name: hotel_season; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.hotel_season (
    hotel_id integer,
    season_id integer
);


ALTER TABLE public.hotel_season OWNER TO postgres;

--
-- Name: pension_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pension_type (
    pension_type_id integer NOT NULL,
    pension_type_name text NOT NULL
);


ALTER TABLE public.pension_type OWNER TO postgres;

--
-- Name: reservation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reservation (
    reservation_id integer NOT NULL,
    hotel_id integer NOT NULL,
    room_id integer NOT NULL,
    reservation_check_in date NOT NULL,
    reservation_check_out date NOT NULL,
    reservation_total_price double precision NOT NULL,
    "reservation_guestCount" integer NOT NULL,
    reservation_guest_name text NOT NULL,
    reservation_guest_email text NOT NULL,
    reservation_guest_phone text NOT NULL
);


ALTER TABLE public.reservation OWNER TO postgres;

--
-- Name: reservation_reservation_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.reservation ALTER COLUMN reservation_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.reservation_reservation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: room; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.room (
    room_id integer NOT NULL,
    room_type text,
    room_stock integer,
    room_capacity integer,
    room_square_meter integer,
    television boolean,
    minibar boolean,
    game_console boolean,
    cash_box boolean,
    projection boolean
);


ALTER TABLE public.room OWNER TO postgres;

--
-- Name: room_hotel; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.room_hotel (
    room_id integer NOT NULL,
    hotel_id integer NOT NULL
);


ALTER TABLE public.room_hotel OWNER TO postgres;

--
-- Name: room_price; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.room_price (
    room_id integer NOT NULL,
    hotel_id integer NOT NULL,
    season_id integer NOT NULL,
    pension_type_id integer NOT NULL,
    adult_price double precision,
    child_price double precision
);


ALTER TABLE public.room_price OWNER TO postgres;

--
-- Name: room_room_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.room ALTER COLUMN room_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.room_room_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: season; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.season (
    season_id integer NOT NULL,
    season_name text NOT NULL,
    season_start_date date,
    season_end_date date
);


ALTER TABLE public.season OWNER TO postgres;

--
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."user" (
    user_id integer NOT NULL,
    user_name text NOT NULL,
    user_password text NOT NULL,
    user_role text NOT NULL
);


ALTER TABLE public."user" OWNER TO postgres;

--
-- Name: user_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."user" ALTER COLUMN user_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.user_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Data for Name: hotel; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.hotel (hotel_id, hotel_name, hotel_city, hotel_region, hotel_address, hotel_email, hotel_phone, hotel_star_rating, hotel_car_park, hotel_wifi, hotel_pool, hotel_fitness, hotel_concierge, hotel_spa, hotel_room_service) FROM stdin;
11	ertrt	trt	rtrt	rtrt	rtrt	rtrt	3 Stars	f	f	f	t	f	f	f
23	enyenotel	123	123	123	123	123	5 Stars	f	f	f	f	t	f	f
2	Rixos Premium	Antalya	Serik	Belek	info@rixos.com	+902427102000	1 Star	t	t	t	t	t	t	t
10	Test	Test	Test	Test	Test	Test	3 Stars	f	f	f	f	t	t	t
1	Han Deluxe Hotel	Mugla	Fethiye	Hisaronu	info@hanhotel.com	+902526167103	4 Stars	t	t	t	f	f	f	t
24	yeni otel	city	region	address	email	phone	5 Stars	t	t	t	f	f	f	f
\.


--
-- Data for Name: hotel_pension_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.hotel_pension_type (hotel_id, pension_type_id) FROM stdin;
23	4
2	4
10	2
10	3
1	4
24	1
24	2
\.


--
-- Data for Name: hotel_season; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.hotel_season (hotel_id, season_id) FROM stdin;
23	2
2	1
2	2
10	1
10	2
1	1
24	1
24	2
\.


--
-- Data for Name: pension_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pension_type (pension_type_id, pension_type_name) FROM stdin;
1	Ultra All Inclusive
2	All Inclusive
3	Bed&Breakfast
4	Full Board
5	Half Board
6	Bed
7	Full Credit (without alcohol)
\.


--
-- Data for Name: reservation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.reservation (reservation_id, hotel_id, room_id, reservation_check_in, reservation_check_out, reservation_total_price, "reservation_guestCount", reservation_guest_name, reservation_guest_email, reservation_guest_phone) FROM stdin;
\.


--
-- Data for Name: room; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.room (room_id, room_type, room_stock, room_capacity, room_square_meter, television, minibar, game_console, cash_box, projection) FROM stdin;
14	Suite	11	11	11	f	t	f	t	f
16	Single	9	9	9	t	f	f	f	f
22	Single	10	2	15	f	t	f	t	f
23	Suite	9	8	89	f	t	f	t	f
24	Double	7	66	66	f	t	f	t	f
25	Single	11	1	111	t	t	t	t	t
26	Double	3	8	14	t	t	t	t	t
\.


--
-- Data for Name: room_hotel; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.room_hotel (room_id, hotel_id) FROM stdin;
14	1
16	1
22	24
23	1
24	1
25	2
26	1
\.


--
-- Data for Name: room_price; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.room_price (room_id, hotel_id, season_id, pension_type_id, adult_price, child_price) FROM stdin;
14	1	1	1	90	80
24	1	1	1	90	80
\.


--
-- Data for Name: season; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.season (season_id, season_name, season_start_date, season_end_date) FROM stdin;
1	Summer	2025-01-05	2025-01-10
2	Winter	2024-01-11	2025-01-05
\.


--
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."user" (user_id, user_name, user_password, user_role) FROM stdin;
1	elif	1	admin
6	salih	4	admin
4	irem	2	employee
7	ayse	3	admin
8	userlar	123	admin
5	elly	2	employee
2	nur	123	employee
\.


--
-- Name: hotel_hotel_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hotel_hotel_id_seq', 26, true);


--
-- Name: reservation_reservation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.reservation_reservation_id_seq', 1, false);


--
-- Name: room_room_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.room_room_id_seq', 27, true);


--
-- Name: user_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.user_user_id_seq', 13, true);


--
-- Name: hotel_pension_type hotel_pension_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hotel_pension_type
    ADD CONSTRAINT hotel_pension_type_pkey PRIMARY KEY (hotel_id, pension_type_id);


--
-- Name: hotel hotel_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hotel
    ADD CONSTRAINT hotel_pkey PRIMARY KEY (hotel_id);


--
-- Name: pension_type pension_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pension_type
    ADD CONSTRAINT pension_pkey PRIMARY KEY (pension_type_id);


--
-- Name: reservation reservation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (reservation_id);


--
-- Name: room_hotel room_hotel_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.room_hotel
    ADD CONSTRAINT room_hotel_pkey PRIMARY KEY (room_id, hotel_id);


--
-- Name: room room_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.room
    ADD CONSTRAINT room_pkey PRIMARY KEY (room_id);


--
-- Name: room_price room_price_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.room_price
    ADD CONSTRAINT room_price_pkey PRIMARY KEY (room_id, hotel_id, season_id, pension_type_id);


--
-- Name: season season_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.season
    ADD CONSTRAINT season_pkey PRIMARY KEY (season_id);


--
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (user_id);


--
-- Name: hotel_pension_type hotel_pension_type_hotel_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hotel_pension_type
    ADD CONSTRAINT hotel_pension_type_hotel_id_fkey FOREIGN KEY (hotel_id) REFERENCES public.hotel(hotel_id);


--
-- Name: hotel_pension_type hotel_pension_type_pension_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hotel_pension_type
    ADD CONSTRAINT hotel_pension_type_pension_type_id_fkey FOREIGN KEY (pension_type_id) REFERENCES public.pension_type(pension_type_id) NOT VALID;


--
-- Name: hotel_season hotel_season_hotel_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hotel_season
    ADD CONSTRAINT hotel_season_hotel_id_fkey FOREIGN KEY (hotel_id) REFERENCES public.hotel(hotel_id);


--
-- Name: hotel_season hotel_season_season_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.hotel_season
    ADD CONSTRAINT hotel_season_season_id_fkey FOREIGN KEY (season_id) REFERENCES public.season(season_id) NOT VALID;


--
-- Name: reservation reservation_hotel_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_hotel_id_fkey FOREIGN KEY (hotel_id) REFERENCES public.hotel(hotel_id) NOT VALID;


--
-- Name: reservation reservation_room_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_room_id_fkey FOREIGN KEY (room_id) REFERENCES public.room(room_id) NOT VALID;


--
-- Name: room_hotel room_hotel_hotel_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.room_hotel
    ADD CONSTRAINT room_hotel_hotel_id_fkey FOREIGN KEY (hotel_id) REFERENCES public.hotel(hotel_id) NOT VALID;


--
-- Name: room_hotel room_hotel_room_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.room_hotel
    ADD CONSTRAINT room_hotel_room_id_fkey FOREIGN KEY (room_id) REFERENCES public.room(room_id) NOT VALID;


--
-- Name: room_price room_price_hotel_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.room_price
    ADD CONSTRAINT room_price_hotel_id_fkey FOREIGN KEY (hotel_id) REFERENCES public.hotel(hotel_id) NOT VALID;


--
-- Name: room_price room_price_pension_type_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.room_price
    ADD CONSTRAINT room_price_pension_type_id_fkey FOREIGN KEY (pension_type_id) REFERENCES public.pension_type(pension_type_id) NOT VALID;


--
-- Name: room_price room_price_room_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.room_price
    ADD CONSTRAINT room_price_room_id_fkey FOREIGN KEY (room_id) REFERENCES public.room(room_id);


--
-- Name: room_price room_price_season_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.room_price
    ADD CONSTRAINT room_price_season_id_fkey FOREIGN KEY (season_id) REFERENCES public.season(season_id) NOT VALID;


--
-- PostgreSQL database dump complete
--

