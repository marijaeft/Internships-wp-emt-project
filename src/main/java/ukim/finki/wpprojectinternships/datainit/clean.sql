DROP TABLE student cascade;
DROP TABLE company cascade ;
DROP  TABLE advertisement cascade;
DROP TABLE application cascade;
DELETE FROM auth_user WHERE role='STUDENT' or role='ADMIN' or role='COMPANY';
-- DELETE FROM student WHERE student_index LIKE '%999';
-- DELETE FROM company WHERE id='company1';