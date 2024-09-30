-- INSERT INTO auth_user (id, name, email, role) VALUES
--                                                   ('marija.eftimova','Marija Eftimova', 'marija.eftimova@students.finki.ukim.mk','STUDENT'),
--                                                   ('ana.terzieva', 'Ana Terzieva', 'ana.terzieva@students.finki.ukim.mk', 'STUDENT'),
--                                                   ('hristina.sekuloska', 'Hristina Sekuloska', 'hristina.sekuloska@students.finki.ukim.mk', 'STUDENT'),
--                                                   ('anja.redzovska', 'Anja Redzovska', 'anja.redzovska@students.finki.ukim.mk', 'STUDENT'),
--                                                   ('admin', 'Sample Admin', 'admin@example.mk','ADMIN'),
--                                                   ('company1', 'Sample Company', 'info@samplecompany.com','COMPANY');
--
-- INSERT INTO student (name, surname, email, phone,index,image) VALUES
--                                                                                                  ('marija.eftimova','eftimova','marija.eftimova@students.finki.ukim.mk', '077-000-000', '213080', null),
--                                                                                                  ('ana.terzieva', 'terzieva','ana.terzieva@students.finki.ukim.mk', '077-000-000', '213080', null),
--                                                                                                  ('hristina.sekulovska', 'sekuloska','hristina.sekuloska@students.finki.ukim.mk', '077-000-000', '213080', null),
--                                                                                                  ('anja.redzovska', 'redzovska','anja.redzovska@students.finki.ukim.mk', '077-000-000', '213080', null);
--
-- INSERT INTO company (name, email,phone, description, address, image) VALUES
--     ('company1', 'info@samplecompany.com', '123-456-7890','This is a sample company for testing purposes.', 'http://www.samplecompany.com', null);
--


INSERT INTO advertisement (description, date_expired, type, number_of_applicants, company_id, active) VALUES
                                                                                                          ('Позиција за софтверски инженер во TechCorp.', '2024-08-31 23:59:59', 'FULL_TIME', 10, 1, true),
                                                                                                          ('Позиција за маркетинг специјалист во TechCorp.', '2024-07-30 23:59:59', 'PART_TIME', 3, 1, true),
                                                                                                          ('Позиција за систем администратор во TechCorp.', '2024-10-01 23:59:59', 'FULL_TIME', 2, 1, false),
                                                                                                          ('Оглас за менаџер на проекти во TechCorp.', '2024-12-01 23:59:59', 'FULL_TIME', 4, 1, false),
                                                                                                          ('Позиција за графички дизајнер во TechCorp.', '2024-08-15 23:59:59', 'PART_TIME', 6, 1, true),
                                                                                                          ('Позиција за човечки ресурси во TechCorp.', '2024-10-15 23:59:59', 'FULL_TIME', 1, 1, true);


-- INSERT INTO advertisement (date_expired, description, type, number_of_applicants, company_id, active) VALUES
--     ('2024-12-31 23:59:59', 'This is a sample advertisement for testing purposes.', 'FULL_TIME', 10, 1, true);

--
-- INSERT INTO internship_coordinator (id, professor_id) VALUES
--     (1, 'vesna.dimitrievska'),
--     (2, 'andreja.naumoski'),
--     (3, 'georgina.mirceva');
--
-- INSERT INTO internship_posting (id, description, planned_weeks, position, company_id) VALUES
--     (1, 'PostgreSQL, Spring, React', 12, 'Software Engineer', 'company1');
--
-- INSERT INTO internship(id, status, professor_id, posting_id, student_student_index, supervisor_id) VALUES
--      (1, 'ONGOING', null, 1, '211999', null),
--      (2, 'ONGOING', null, 1, '213999', null),
--      (3, 'ONGOING', null, 1, '214999', null),
--      (4, 'ONGOING', null, 1, '216999', null);
--
--
-- INSERT INTO internship_week(id, description, end_date, start_date, internship_id) VALUES
--                                                                                        (1, 'Seeded a PostgreSQL database', '2024-06-21', '2024-06-17', 1),
--                                                                                        (2, 'Developed a REST API with Flask', '2024-06-28', '2024-06-24', 1),
--                                                                                        (3, 'Implemented user authentication', '2024-07-05', '2024-07-01', 1),
--                                                                                        (4, 'Designed frontend components with React', '2024-07-12', '2024-07-08', 1),
--                                                                                        (5, 'Conducted unit testing for the backend', '2024-07-19', '2024-07-15', 2),
--                                                                                        (6, 'Optimized SQL queries for performance', '2024-07-26', '2024-07-22', 2),
--                                                                                        (7, 'Integrated third-party APIs', '2024-08-02', '2024-07-29', 2),
--                                                                                        (8, 'Deployed application to AWS', '2024-08-09', '2024-08-05', 2),
--                                                                                        (9, 'Implemented CI/CD pipeline', '2024-08-16', '2024-08-12', 3),
--                                                                                        (10, 'Conducted code reviews and refactoring', '2024-08-23', '2024-08-19', 3),
--                                                                                        (11, 'Worked on bug fixes and improvements', '2024-08-30', '2024-08-26', 3),
--                                                                                        (12, 'Created project documentation', '2024-09-06', '2024-09-02', 3),
--                                                                                        (13, 'Tested project', '2024-09-06', '2024-09-02', 4),
--                                                                                        (14, 'Deployed app to server', '2024-09-06', '2024-09-02', 4),
--                                                                                        (15, 'Analyzed telemetry data', '2024-09-06', '2024-09-02', 4),
--                                                                                        (16, 'Wrote scripts', '2024-09-06', '2024-09-02', 4);


