CREATE TABLE IF NOT EXISTS public.navigation
(
    id       bigint,
    link     varchar(255),
    position integer,
    section  varchar(255),
    title    varchar(255),
    usr      varchar(255)
);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (1, 'rating', 'Рейтинг', 'Информация', 'USER', 2);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (2, 'forecast', 'Прогнозы других участников', 'Информация', 'USER', 2);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (3, 'groups', 'Группы', 'Информация', 'USER', 2);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (4, 'champion', 'Чемпион', 'Прогнозы', 'USER', 1);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (5, 'bombardier', 'Бомбардир', 'Прогнозы', 'USER', 2);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (6, 'placing', 'Место команды в группе', 'Прогнозы', 'USER', 3);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (7, 'registered', 'Зарегистрированные участники', 'Информация', 'USER', 2);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (8, 'chat', '', 'Чат', 'NONE', 3);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (9, 'matches', 'Получить матчи', 'Работа с данными', 'ADMIN', 1);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (10, 'prognosis', 'Прогноз', 'Прогнозы', 'USER', 4);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (11, 'next', 'Выход в playoff', 'Прогнозы', 'USER', 5);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (12, 'set_team_placing', 'Установить место в группе', 'Тест', 'ADMIN', 2);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (13, 'set_prognosis', 'Установить прогноз счета', 'Тест', 'ADMIN', 2);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (14, 'set_matches', 'Установить матчи', 'Тест', 'ADMIN', 2);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (15, 'set_prognosis_points', 'Установить очки за прогноз', 'Работа с данными', 'ADMIN', 2);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (16, 'get_rating', 'Получить рейтинг', 'Работа с данными', 'ADMIN', 2);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (18, 'set_matches_po', 'Установить матчи для playoff', 'Тест', 'ADMIN', 2);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (20, 'settings', '', 'Настройки', 'ADMIN', 4);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (21, 'news', '', 'Новости', 'NONE', 10);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (22, 'get_player', 'Получить составы', 'Работа с данными', 'ADMIN', 10);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (23, 'journal', 'Журнал', 'Информация', 'ADMIN', 4);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (24, 'edit_user', 'Изменить участника', 'Работа с данными', 'ADMIN', 1);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (25, 'score', 'Ввести счёт', 'Работа с данными', 'ADMIN', 2);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (26, 'look_style', 'Посмотреть стиль', 'Информация', 'NONE', 6);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (27, 'profile', 'Настройки', 'Профиль', 'NONE', 1);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (28, 'logout', 'Выйти', 'Профиль', 'NONE', 2);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (29, 'get_teams', 'Получить команды', 'Работа с данными', 'ADMIN', 5);
INSERT INTO public.navigation (id, link, title, section, usr, position) VALUES (30, 'standings', 'Получить сводную таблицу', 'Работа с данными', 'ADMIN', 3);
