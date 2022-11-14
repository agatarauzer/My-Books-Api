insert into users (user_id, email, first_name, last_name, password, username, enabled, registration_date) values
	(1, 'user1@test.com', 'Adam', 'Monday', '$2a$10$1XCd1hHx4wXf71/2k1SaZu2FdNWDtNJcVm097KAD9zDfBbg2fJB1C', 
	'adamon', true, '2022-01-23'),
	(2, 'user2@test.com', 'Barbara', 'Tuesday', '$3g$54$rrrenVoK9h7OadrUtd7wU.dMGf6amRFjiP7X5OKjuhqAAeGyWzRVe', 
	'bartue', true, '2022-02-02'),
	(3, 'user3@test.com', 'Connel', 'Wednesday', '$2a$10$R5uEvRzi78IXR8Dif4Klze3fM7N4YizTcdN0NuPfAMsAGyRxCSzr6', 
	'conwed', true, '2022-03-11'),
	(4, 'user4@test.com', 'David', 'Thursday', '$2a$10$7WXJw.k.xVBdEME4su3bluOoQk0GKqilvZ64caErD9kC.OmMBFyGq', 
	'davthu', true, '2022-04-18'),
	(5, 'admin@test.com', 'Anthony', 'Smith', '$2a$10$hN7slczAV5QRI6NTEIHyIuqUcWvs9d9se4X2w5e1pr61tpcL3YfrS', 
	'admin', true, '2022-01-10');
	
insert into roles (role_id, name) values 
	(1, 'ROLE_USER_PAID'),
	(2, 'ROLE_USER_LIMITED'),
	(3, 'ROLE_ADMIN');
	
insert into user_roles (user_id, role_id) values
	(1, 1),
	(2, 1),
	(3, 1),
	(4, 2),
	(5, 3);

insert into books (book_id, title, authors, isbn_numbers, publisher, publishing_date, language, pages, description,
			image_link, version, copies, creation_date, user_id, reading_id, rental_id, purchase_id) values
			
    /* user 1  */
	(1, 'JavaTM podstawy', 'Cay S. Horstmann', '832835778X, 9788328357785', 'Helion', '2020', 'pl', 768,
	'description_1','link_1', 'BOOK', 1, '2022-02-05', 1, 1, null, 1),
	(2, 'Skazanie', 'Remigiusz Mróz', '836717626X, 9788367176262', 'Czwarta strona', '2022', 'pl', null,
	'description_2', 'link_2', 'E-BOOK', 1, '2022-03-30', 1, 2, null, 2),
	(3, 'Data Stewardship An Actionable Guide to Effective Data Management and Data Governance', 'David Plotkin', 
	'9780128221679, 0128221674', 'Academic Press', '2020-10-31', 'en', 322, 
	'description_3', 'link_3', 'BOOK', 1, '2022-01-23', 1, 3, null, 3),
	(4, 'The 7 Habits of Highly Effective People Powerful Lessons in Personal Change', 'Stephen R. Covey', '1471131823, 9781471131820', 'Gardners', '2021',
	'en', 391, 'description_4', 'link_4', 'BOOK', 1, '2022-05-23', 1, 4, null, 4),
	(5, 'Good Economics for Hard Times', 'Abhijit V. Banerjee, Esther Duflo', '154178894X, 9781541788947', 'Public Affairs', '2021-08-10',
	'en', 432, 'description_5', 'link_5', 'BOOK', 1, '2022-05-02', 1, 5, 2, 5),
	
	/* user 2  */
	(6, 'Czuła przewodniczka kobieca droga do siebie', 'Natalia Barbaro', '8326836443, 9788326836442', 'Wydawnictwo Agora',
	'2021', 'pl', 256, 'description_6', 'link_6', 'BOOK', 1, '2022-04-26', 2, 6, null, 6),
	(7, 'The Power of Your Subconscious Mind', 'Joseph Murphy', '9781625580740, 1625580746', 'Simon and Schuster', '2012',
	'en', 180, 'description_7', 'link_7', 'BOOK', 1, '2022-02-16', 2, 7, null, 7),
	(8, 'Jak przestac sie martwic i zaczac zyc', 'Dale Carnegie', '8365068869, 9788365068866', 'Wydawnictwo Studio EMKA', '2022-03-24',
	'pl', 392, 'description_8', 'link_8', 'E_BOOK', 1, '2022-02-23', 2, 8, null, 8),
	
	/* user 3  */
	(9, 'Kubuś Puchatek', 'A. A. Milne', '8310137745, 9788310137746', 'Wydawnictwo Nasza Księgarnia', '2022-01-26',
	'pl', 144, 'description_9', 'link_9', 'BOOK', 1, '2022-08-23', 3, 9, null, 9),
	(10, 'Pucio mówi dzień dobry', 'Marta Galewska-Kustra', '8310135831, 9788310135834', 'Wydawnictwo Nasza Księgarnia', '2020-05-06',
	'pl', 28, 'description_10', 'link_10', 'BOOK', 1, '2022-07-15', 3, 10, 1, null),
	
	/* user 4  limited */
	(11, 'Czas zdrajców', 'Marek Krajewski', '9788324065486, 8324065482', 'Społeczny Instytut Wydawniczy Znak', '2022',
	'pl', 400, 'description_11', 'link_11', 'BOOK', 1, '2022-10-02', 4, 11, null, null),
	(12, 'Początek', 'Dan Brown', '9788381101462, 8381101468', 'Sonia Draga Sp. z o.o.', '2017-11-09',
	'pl', 576, 'description_12', 'link_12', 'E_BOOK', 1, '2022-06-03', 4, 12, null, null);
	
insert into purchases (purchase_id, price, purchase_date, bought_from) values
	(1, 79.50, '2022-01-20', 'local bookshop'),
	(2, 49.90, '2022-03-27', 'dont remember'),
	(3, 108, '2022-01-22', 'amazon.com'),
	(4, 158.50, '2022-05-01', 'amazon.com'),
	(5, 78.50, '2022-05-01', 'amazon.com'),
	(6, 39.99, '2022-03-10', 'empik.com'),
	(7, 64.99, '2022-02-06', 'empik.com'),
	(8, 25.70, '2022-02-12', 'taniaksiazka.pl'),
	(9, 22.90, '2022-05-18', 'bookshop in warsaw');
	
insert into readings (reading_id, status, start_date, end_date, readed_pages, rate, notes) values
	(1, 'READED', '2022-02-05', '2022-07-10', 700, 4, 'Java bible'),
    (2, 'READED', '2022-05-20', '2022-07-30', 450, 5, 'really fascinating!'),
	(3, 'IN_READING', '2022-01-07', null, 213, 4, 'usable in work'),
	(4, 'READED', '2022-06-08', '2022-07-02', 391, 4, 'classic...'),
	(5, 'READED', '2022-04-08', '2022-05-26', 432, 4, 'interesting book'),
	(6, 'READED', '2022-05-19', '2022-07-12', 256, 3, 'nice, but too obviuos'),
	(7, 'READED', '2022-02-30', '2022-11-09', 180, 5, null),
	(8, 'READED', '2022-06-03', '2022-07-28', 392, 4, 'nice, fast to read'),
	(9, 'IN_READING', '2022-05-24', null, 15, 5, 'child book; children really love it and read every single day');
	(10, 'IN READING', '2022-07-30', null, 15, 5, 'ideal book for children'),
	(11, 'LEFT', '2022-11-10', null, 160, null, 'maybe I will back to it in future'),
	(12, 'READED', '2022-08-27', '2022-10-06', 576, 4, 'not bad, but quite predictable');
	
insert into rentals (rental_id, status, name, start_date, end_date, notes) values
	(1, 'BORROWED_FROM', 'Jack gave it to children', '2022-10-01', '2023-03-22', null);
	(2, 'LENDED_TO', 'to Kate', '2022-07-10', '2023-01-10', 'she will return it after christmas');
	
	

	
	